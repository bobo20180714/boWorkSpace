/*
 *绘图区管理器
 */

Ext.define('js.relationChart.RDrawAreaManage',{
	constructor:function(_relation_owner){
		return new RDrawAreaManage(_relation_owner);
	}
});	

function RDrawAreaManage(_relation_owner) {
    var left_ = 60,
        top_ = 20,
        RcharPEl=document.getElementById("RelationChart-PEl-body"),
        w = _relation_owner.clientWidth - left_ * 2 - 8,
        h = _relation_owner.clientHeight - top_ * 2,// - 70,
        dt,
        timeFormat = 'Y-m-d H:i:s',//'H:i:s',
        dv,
        adx = w / 10,
        ady = h / 10,
        offLeft_ = getOffsetLeft(_relation_owner) + 1, //256
        offTop_ = getOffsetTop(_relation_owner) + 3, //28
        me = this,
        adt,
        adv,
        tileTop,
        tileHeight,
        isSel_ = false,
        left_btn = Ext.isIE8m ? 1 : 0,
        right_btn = 2,
        _drag,
        relationNeedTiye = false,//mxc add true:需要执行剔野，false:不需要执行剔野
	   	getTimeVal = function (v, p) {
	        //if (p == undefined)
	            return new Date(v).format(timeFormat);
	        var fmt;
	        switch (timeFormat.charAt(0)) {
	            case 'Y':
	                fmt = timeFormat.substr(2) + ' H';
	                break;
	            case 'm':
	                fmt = timeFormat.substr(2) + ':s';
	                break;
	            case 'H':
	            case 'i':
	                fmt = timeFormat;//.substr(2) + '.u';
	                break;
	        }
	        return new Date(v).format(fmt);
	    },
	    timeEl=null,
	    valueEl=null,
	    getTimeSpan=function(){
	    	var t1=new Date(xRuler1.getValue() * dt + _relation_lines.MainLine.Begin),
	    		t2=new Date(xRuler2.getValue() * dt + _relation_lines.MainLine.Begin),
	    		span=Math.floor((t2-t1)/1000);
	    	if(span<0)span=-span;
	    	return ['时间差值：',Math.floor(span/86400),'天'
	    	        ,Math.floor(span%86400/3600),'时'
	    	        ,Math.floor(span%3600/60),'分'
	    	        ,span%60,'秒'].join('');
	    },
	    getValueSpan=function(){
	    	var v1=_relation_lines.IsTile ?
                   _relation_lines.MainLine.GetFmtVal(_relation_lines.MainLine.Max - (_relation_lines.MainLine.Max - _relation_lines.MainLine.Min) / tileHeight * (yRuler1.getValue() - tileTop),10) :
                   _relation_lines.MainLine.GetFmtVal(_relation_lines.MainLine.Max - dv * yRuler1.getValue(),10),
                v2=_relation_lines.IsTile ?
                   _relation_lines.MainLine.GetFmtVal(_relation_lines.MainLine.Max - (_relation_lines.MainLine.Max - _relation_lines.MainLine.Min) / tileHeight * (yRuler2.getValue() - tileTop),10) :
                   _relation_lines.MainLine.GetFmtVal(_relation_lines.MainLine.Max - dv * yRuler2.getValue(),10),
                span=v2-v1;
            if(span<0)span=-span;
            return '数据差值：'+_relation_lines.MainLine.GetFmtVal(span,10);
	    };
    /*
     * var tip=Ext.get('TIP');    
    tip.hide();
    var tpl=new Ext.XTemplate(
	    '<p>曲线名称: {name}</p>',
	    '<p>当前时间: {x}</p>',
	    '<p>当前数值: {y}</p>'
	)
     */   
    this.Width = w;
    this.Height = h;
    me.DataLineCount=0;
    this.AddDataLine=function(rec){//景科文新增 添加航天器相关信息
    	me.DataLineCount++;
    	this.DataResize(rec);
    };
    this.DataResize = function (rec) {
    	var id=rec.get("dataTypeId");
    	var mid=rec.get("mid");
    	var satId=rec.get("deviceCode");
    	this.updateDataChar();
    	var titlDataLineEl=document.getElementById(_relation_owner.id+"-titlDataLine");
    	var titlChild = document.createElement("div");
        with (titlChild.style) {
            width = left_ + 'px'; height = 25 + 'px'; overflow = "visible";
            left = 0 + 'px'; top = 0 + 'px';
        }
        titlChild.id="titl_"+id;
        titlChild.innerHTML="<font size='0.4' color='"+rec.get("Color")+"'>"+rec.get("jsjg_name")+"</font>";
        titlDataLineEl.appendChild(titlChild);
        var tileDetailDataLineEl=document.getElementById(_relation_owner.id+"-TileDetailDataLine");
        var titlDetailChild = document.createElement("div");
        with (titlDetailChild.style) {
        	width = left_ + 'px'; height = 25 + 'px'; overflow = "hidden";
        	left = 0 + 'px'; top = 0 + 'px';
        }
        titlDetailChild.id="titl_detail_"+id;
        titlDetailChild.innerHTML="<div id='titl_detail_num_"+id+"' style='float:left;display:inline;width:25px;line-height:25px;'>&nbsp;&nbsp;<a href='javascript:charUtil.showDetail(\""+rec.get('jsjg_id')+"\",\""+rec.get("jsjg_name")+"\",\""+rec.get("deviceName")+"\",\""+rec.get('jsjg_code')+"\",\""+rec.get("deviceCode")+"\")' id='titl_detail_num_a_"+id+"' style='cursor:hand' ><font color='red' style='text-decoration:underline;'>"+me.DataLineCount+"</font></a></div><div style='float:left;display:inline;width:20px;line-height:25px;padding-top:5px;'>&nbsp;&nbsp;<img src='img/delete.png' onclick='charUtil.RemoveLine(\""+id+"\",\""+mid+"\",\""+satId+"\")'/></div>";
        tileDetailDataLineEl.appendChild(titlDetailChild);
    };
    this.RemoveDataLine=function(lineId){//景科文新增 移除航天器相关信息
    	me.DataLineCount--;
    	var titlDataLineEl=document.getElementById(_relation_owner.id+"-titlDataLine");
    	var nodes = titlDataLineEl.childNodes;
    	for (var i = 0; i < nodes.length; i++) {
    		if(nodes[i].id=="titl_"+lineId){
    			titlDataLineEl.removeChild(nodes[i]);
    			break;
    		}
        }
    	var tileDetailDataLineEl=document.getElementById(_relation_owner.id+"-TileDetailDataLine");
    	nodes = tileDetailDataLineEl.childNodes;
     	for (var i = 0; i < nodes.length; i++) {
     		if(nodes[i].id=="titl_detail_"+lineId){
     			tileDetailDataLineEl.removeChild(nodes[i]);
     			break;
     		}
         }
     	var pointEl = document.getElementById("data_point");
     	if(pointEl){
     		pointEl.style.visibility="hidden";
     	}
     	this.updateLineNum();
    	this.updateDataChar();
    	//zxl添加。还原画布高度
//    	Ext.getCmp("RelationChart").setHeight(Ext.getCmp("RelationChart").getHeight()-30);
    	//mengxiangchao  
    	$("#RelationChart").height($("#RelationChart").height()-30);
    };
    this.updateLineNum=function(){
    	var tileDetailDataLineEl=document.getElementById(_relation_owner.id+"-TileDetailDataLine");
    	nodes = tileDetailDataLineEl.childNodes;
     	Ext.Array.each(nodes,function(node,index){
     		node.firstChild.firstChild.innerHTML=index+1;
     	});
    };
    this.updateDataChar=function(){//景科文新增 重新布局
    	var dataEl=document.getElementById(_relation_owner.id+"-DataEl");
		dataEl.style.height=me.DataLineCount*25+'px';
		var dataelC=document.getElementById(_relation_owner.id+"-elC");
		dataelC.style.height=me.DataLineCount*25+'px';
		if(me.DataLineCount==0){
			dataEl.style.borderTop="0px";
		}else{
			dataEl.style.borderTop="2px solid #000";
		}
		var canvas=document.getElementById(_relation_owner.id+"-DataEl-canvas");
		canvas.height=me.DataLineCount*25;
		var canvasEl=document.getElementById(_relation_owner.id+"-otherCanvasEl");
		canvasEl.style.height=me.DataLineCount*25+h+'px';
		var canvas=document.getElementById(_relation_owner.id+"-otherCanvas");
		canvas.height=me.DataLineCount*25+h;
		var dataXGrid=document.getElementById(_relation_owner.id+"-DataEl-xgrid");
		dataXGrid.style.height=me.DataLineCount*25+'px';
		var nodes = dataXGrid.childNodes;
        for (var i = 0; i < nodes.length; i++) {
        	nodes[i].style.height=me.DataLineCount*25+"px";
        }
		document.getElementById(_relation_owner.id+"-xcoord").style.top=top_+h+(me.DataLineCount*25)+"px";
		document.getElementById("RXD1").style.height=document.getElementById("RXD2").style.height=h+(me.DataLineCount*25)+"px";
		document.getElementById("RL1").style.height=document.getElementById("RL2").style.height=h+4+(me.DataLineCount*25)+"px";
		document.getElementById("REL_id").style.height=document.getElementById("R_leftBorder").style.height=document.getElementById("R_rightBorder").style.height=h+(me.DataLineCount*25)+"px";
    };
    var Canvas = {
        createChart: function () {
            //创建绘图区元素
            var el = document.createElement("div");
            with (el.style) {//景科文修改  改变div样式 改为由外部层控制边框 
                width = w + 'px'; height = h + 'px'; overflow = "hidden"; position = "absolute";
                left = left_ + 'px'; top = top_ + 'px';
            }
            _relation_owner.appendChild(el);
            var elC =  document.createElement("div");//景科文新增 添加控制航天器相关信息移动层（鼠标监听）
            with (elC.style) {
            	width = w + 'px'; overflow = "hidden"; position = "absolute";
            	left = left_ + 'px'; top = h+top_+ 'px'; zIndex=3;
            }
            elC.id=_relation_owner.id+"-elC";
            _relation_owner.appendChild(elC);
            var el2 = document.createElement("div");//景科文新增 添加航天器相关信息绘图区域
            with (el2.style) {
            	width = w + 'px'; overflow = "hidden"; position = "absolute";
            	left = left_ + 'px'; top = h+top_+ 'px'; 
            }
            el2.id=_relation_owner.id+"-DataEl";
            _relation_owner.appendChild(el2);
            //创建绘图区对象
            var xGrid = this.createXGrid(el);
            var dataxGrid = this.createDataXGrid(el2);//景科文新增 添加航天器相关信息Y线
            var yGrid = this.createYGrid(el);
            var sel = this.createSeletor(el);
            var dataSel = this.createDataSeletor(el2);//景科文新增 添加航天器相关信息绘图层
            var otherSel = this.createOtherSel(_relation_owner);//景科文新增 添加layer、屏蔽、门限层
            var xCoord = this.createXCoord();
            var yCoord = this.createYCoord();
            var tileYRCoord = this.createYRCoord();//景科文新增 添加门限右边提示框
            var tileYCoord = this.createTileYCoord();
            
            var tileDataLine=this.createTitleDataLine(_relation_owner);//景科文新增 添加航天器相关信息 左边提示信息
            var tileDetailDataLine=this.createTileDetailDataLine(_relation_owner);//景科文新增 添加航天器相关信息 右边提示信息
            //xjt-15.2.28 
            var el1 = document.createElement("div");
            with (el1.style) {
                overflow = "hidden"; position = "absolute";
                width = '59px'; height = '15px';
                left = '60px'; top = '3px';
                visibility = 'visible';color='#000';whiteSpace='nowrap';
                backgroundColor='#fff';
            } 
            el1.innerHTML='游标1时间：';
            _relation_owner.appendChild(el1);
            var el2 = document.createElement("div");
            with (el2.style) {
                overflow = "hidden"; position = "absolute";
                width = '59px'; height = '15px';
                left = '250px'; top = '3px';
                visibility = 'visible';color='#000';whiteSpace='nowrap';
                backgroundColor='#fff';zIndex=1;
            } 
            el2.innerHTML='游标2时间：';
            _relation_owner.appendChild(el2);
            timeEl= document.createElement("div");
            with (timeEl.style) {
                overflow = "hidden"; position = "absolute";
                width = '180px'; height = '15px';
                left = '440px'; top = '3px';
                visibility = 'visible';color='#000';whiteSpace='nowrap';
                backgroundColor='#fff';zIndex=1;
            } 
            timeEl.innerHTML='时间差值：';
            _relation_owner.appendChild(timeEl);
//            el1ValueEl = document.createElement("div");
//            with (el1ValueEl.style) {
//                overflow = "hidden"; position = "absolute";
//                width = '100px'; height = '14px';
//                left = '630px'; top = '3px';
//                visibility = 'visible';color='#000';whiteSpace='nowrap';
//                backgroundColor='#fff';
//            }
//            el1ValueEl.innerHTML='游标1值：';
//            _relation_owner.appendChild(el1ValueEl);
//            el2ValueEl = document.createElement("div");
//            with (el2ValueEl.style) {
//                overflow = "hidden"; position = "absolute";
//                width = '100px'; height = '14px';
//                left = '750px'; top = '3px';
//                visibility = 'visible';color='#000';whiteSpace='nowrap';
//                backgroundColor='#fff';
//            }
//            el2ValueEl.innerHTML='游标2值：';
//            _relation_owner.appendChild(el2ValueEl);
            valueEl= document.createElement("div");
            with (valueEl.style) {
                overflow = "hidden"; position = "absolute";
                width = '140px'; height = '15px';
                right = '40px'; top = '3px';
                visibility = 'visible';color='#000';whiteSpace='nowrap';
                backgroundColor='#fff';
            }
            valueEl.innerHTML='数据差值：';
            _relation_owner.appendChild(valueEl);
            //定义变量
            var x, y, lx, ly, begin, max, left_drag, right_drag;
            //属性
            el.xGrid = xGrid;
            el.yGrid = yGrid;
            el.xCoord = xCoord;
            el.yCoord = yCoord;
            el.tileYCoord = tileYCoord;
            el.sel = sel;
            //方法
            el.setOrigin = function () {
                xGrid.style.left = adx / 2 + 'px';
                dataxGrid.style.left = adx / 2 + 'px';//景科文新增 初始化Y线位置
                yGrid.style.top = ady / 2 + 'px';
                xCoord.style.left = left_ - adx / 2 + 'px';
                yCoord.style.top = top_ - 6 + 'px';
            };
            el.updateTime = function (abegin) {
                begin = abegin;
                var nodes = xCoord.childNodes;
                for (var i = 0; i < nodes.length; i++) {
                	if(i%2==1)continue;
                    nodes[i].innerHTML = getTimeVal(begin + adt * i);
                }
            };
            el.updateVal = function (amax) {
                max = amax;
                if (_relation_lines.IsTile) return;
                var nodes = yCoord.childNodes;
                for (var i = 0; i < nodes.length; i++) {
                    nodes[i].innerHTML = _relation_lines.MainLine.GetFmtVal(max - adv * i);
                }
            };
            el.move = function (dx, dy) {
                var x_ = parseInt(xGrid.style.left) + dx;
                if (x_ <= -adx / 2) {
                    xGrid.style.left = adx / 2 - (-adx / 2 - x_) % adx + 'px';
                    el.updateTime(begin + adt * (Math.floor((-adx / 2 - x_) / adx) + 1));
                }
                else if (x_ <= adx / 2) xGrid.style.left = x_ + 'px';
                else {
                    xGrid.style.left = -adx / 2 + (x_ - adx / 2) % adx + 'px';
                    el.updateTime(begin - adt * (Math.floor((x_ - adx / 2) / adx) + 1));
                }
                xCoord.style.left = parseInt(xGrid.style.left) + left_ - adx + 'px';
                if (parseInt(xGrid.style.left) < Math.floor(adx / 2)) xCoord.childNodes[0].innerHTML = '';

                var y_ = parseInt(yGrid.style.top) + dy;
                if (y_ <= -ady / 2) {
                    yGrid.style.top = ady / 2 - (-ady / 2 - y_) % ady + 'px';
                    el.updateVal(max - adv * (Math.floor((-ady / 2 - y_) / ady) + 1));
                }
                else if (y_ <= ady / 2) yGrid.style.top = y_ + 'px';
                else {
                    yGrid.style.top = -ady / 2 + (y_ - ady / 2) % ady + 'px';
                    el.updateVal(max + adv * (Math.floor((y_ - ady / 2) / ady) + 1));
                }
                yCoord.style.top = parseInt(yGrid.style.top) + top_ - ady / 2 - 6 + 'px';
                if (parseInt(yGrid.style.top) < Math.floor(ady / 2)) yCoord.childNodes[0].innerHTML = '';
            };
            el.dataMove = function (dx, dy) {//景科文新增 添加航天器相关信息绘图区域移动方法
            	var x_ = parseInt(dataxGrid.style.left) + dx;
            	if (x_ <= -adx / 2) {
            		dataxGrid.style.left = adx / 2 - (-adx / 2 - x_) % adx + 'px';
            	}
            	else if (x_ <= adx / 2) dataxGrid.style.left = x_ + 'px';
            	else {
            		dataxGrid.style.left = -adx / 2 + (x_ - adx / 2) % adx + 'px';
            	}
            	xCoord.style.left = parseInt(dataxGrid.style.left) + left_ - adx + 'px';
            	if (parseInt(dataxGrid.style.left) < Math.floor(adx / 2)) xCoord.childNodes[0].innerHTML = '';
            };
            el.newYCoord = function (aid, atop, aheight) {
                yCoord.style.visibility = 'hidden';
                var yVal = document.createElement("div");
                yVal.id = 'MAX' + aid;
                with (yVal.style) {
                    width = left_ + 'px'; height = 12 + 'px'; overflow = "hidden"; position = "absolute";
                    left = 0 + 'px'; top = atop + 'px';
                }
                tileYCoord.appendChild(yVal);
                yVal = document.createElement("div");
                yVal.id = 'MIN' + aid;
                with (yVal.style) {
                    width = left_ + 'px'; height = 12 + 'px'; overflow = "hidden"; position = "absolute";
                    left = 0 + 'px'; top = atop + aheight - 12 + 'px';
                }
                tileYCoord.appendChild(yVal);
            };
            el.delYCoord = function () {
                tileYCoord.innerHTML = '';
                yCoord.style.visibility = 'visible';
            };
            el.getLeft = function () {
                return begin;
            };
            el.getTop = function () {
                return max;
            };
            el.hidePoint = function () {
                if(sel.isShow)sel.hide();
            };
            el.hideDataPoint = function () {
            	if(dataSel.isShow) dataSel.hide();
            };
            el.getPointInfo = function () {
                return sel.pointInfo;
            };
            el.getOffsetLeft = function () {
                return left_ + offLeft_;
            };
            el.getOffsetTop = function () {
                return top_ + offTop_;
            };
            //事件
            el.onmousedown = function () {
                if (!_relation_lines.MainLine || window._IsPlay) return;
                bindEvent(function (evt) {
                    var ty = evt.clientY - top_ - offTop_;
                    /*if (_relation_lines.IsTile && (ty < tileTop + 5 || ty > tileTop + tileHeight - 5)) return;*/
                    lx = x = evt.clientX;
                    ly = y = evt.clientY+RcharPEl.scrollTop-2;
                    if (evt.button == right_btn) {
                        _relation_owner.style.cursor = 'pointer';
                        right_drag = true;
                        sel.hide();
                    }
                    else if (evt.button == left_btn) {
                        left_drag = true;                       
                        if(Ext.getCmp('RSJTY').pressed){       
                        	_relation_owner.style.cursor = 'pointer';
                        	sel.begin(x - left_ - offLeft_,y - top_ - offTop_);
                        }
                        else{
                        	//sel.point(x - left_ - offLeft_,y - top_ - offTop_);
                        } 
                    }     
                }, function (evt) {
                    if (left_drag) {
                    	   if (isSel_) return;
                           if(Ext.getCmp('RSJTY').pressed){     
                           	sel.line(evt.clientX - left_ - offLeft_,evt.clientY+RcharPEl.scrollTop - top_ - offTop_);
                           }
                           else{
                           	var sy = (y < (evt.clientY+RcharPEl.scrollTop) ? y : (evt.clientY+RcharPEl.scrollTop)) - top_ - offTop_;
   	                        var sh = Math.abs(y - (evt.clientY+RcharPEl.scrollTop));
   	                        /*if (_relation_lines.IsTile) {
   	                            if (sy < tileTop + 5) {
   	                                sh -= tileTop + 5 - sy;
   	                                sy = tileTop + 5;
   	                            }
   	                            if (sy + sh > tileTop + tileHeight - 5) sh = tileTop + tileHeight - 5 - sy;
   	                        }
   	                        else*/ {
   	                            if (sy < 5) {
   	                                sh -= 5 - sy;
   	                                sy = 5;
   	                            }
   	                            if (sy + sh > h+15 ) sh = h +15 - sy;
   	                        }
   	                        var sx = (x < evt.clientX ? x : evt.clientX) - left_ - offLeft_;
   	                        var sw = Math.abs(x - evt.clientX);
   	                        if (sx < 5) {
   	                            sw -= 5 - sx;
   	                            sx = 5;
   	                        }
   	                        if (sx + sw > w - 5) sw = w - 5 - sx;
   	                        sel.rect(sx,sy,sw,sh);
                        }                       
                    }
                    else if (right_drag) {
                        var dx = evt.clientX - lx, dy = evt.clientY+RcharPEl.scrollTop - ly;
                        if (isSel_) {
                            if (el.onselmove_)
                                el.onselmove_(dy);
                        }
                        else if (el.onmove_)el.onmove_(dx, dy);
                        lx = evt.clientX;
                        ly = evt.clientY+RcharPEl.scrollTop;
                    }
                }, function (evt) {
                    if (evt.button == right_btn) {
                        _relation_owner.style.cursor = '';
                        if (el.onmoved_)
                            el.onmoved_();
                        right_drag = false;
                    }
                    else {
                    	if(Ext.getCmp('RSJTY').pressed){//剔野     
                			_relation_owner.style.cursor = '';
            				if(relationNeedTiye){
            					if(el.ontiye_&&sel.x2-sel.x1>0)el.ontiye_(sel.x1, sel.x2, sel.y1, sel.y2);
            					sel.end(); 
            				}
                		}
                		else{//放大
                			if(!sel.isPoint){
                				if(el.onselected_&&sel.h>3)el.onselected_(sel.x, sel.y, sel.w, sel.h);
                				sel.hide();
                			}
                		}               
                        left_drag = false;
                    }
                });
            };
            elC.onmousedown=function(){//景科文新增 添加在轨绘图区域鼠标点击事件
            	if (!_relation_lines.MainLine || window._IsPlay) return;
            	bindEvent(function (evt) {//点击下
                    var ty = evt.clientY+RcharPEl.scrollTop - top_ - offTop_;
                    lx = x = evt.clientX;
                    ly = y = evt.clientY+RcharPEl.scrollTop;
                    if (evt.button == right_btn) {
                        _relation_owner.style.cursor = 'pointer';
                        right_drag = true;
                        dataSel.hide();
                    } else dataSel.point(evt.clientX-left_ - offLeft_,y - top_ - offTop_-h);
                }, function (evt) {//点击中
                   if (right_drag) {
                        var dx = evt.clientX - lx, dy = evt.clientY+RcharPEl.scrollTop - ly;
                        if (el.onmove_)el.onmove_(dx, 0);
                        lx = evt.clientX;
                        ly = evt.clientY+RcharPEl.scrollTop;
                    }
                }, function (evt) {//点击后
                    if (evt.button == right_btn) {
                        _relation_owner.style.cursor = '';
                        if (el.onmoved_)
                            el.onmoved_();
                        right_drag = false;
                    }
                });
            };
            /*el.ondblclick = function (evt) {
                if (!_lines.MainLine || !this.onselected_)
                    return;
                var t,
                    x1 = xRuler1.getValue(),
                    x2 = xRuler2.getValue(),
                    y1 = yRuler1.getValue(),
                    y2 = yRuler2.getValue();

                if (x1 > x2) { t = x1; x1 = x2; x2 = t; }
                if (y1 > y2) { t = y1; y1 = y2; y2 = t; }
                evt = evt || window.event;
                x = evt.clientX - left_ - offLeft_;
                y = evt.clientY - top_ - offTop_;
                if (x <= x1 || x >= x2 || y <= y1 || y >= y2)
                    return;
                this.onselected_(x1, _lines.IsTile ? y1 - tileTop : y1, x2 - x1, y2 - y1);
            }*/
            el.onmousewheel = function () {
                if (this.onwheel_) {
                    if (event.wheelDelta == 120) this.onwheel_(-1);
                    if (event.wheelDelta == -120) this.onwheel_(1);
                }
            };           
            if (el.addEventListener) {
                el.addEventListener('DOMMouseScroll', function (evt) {
                    if (this.onwheel_) {
                        if (evt.detail == 3) this.onwheel_(1);
                        if (evt.detail == -3) this.onwheel_(-1);
                    }
                }, false);
            }
            el.onmousemove=function(e){
            	var evt=e || event,
            		info=_relation_lines.GetPointInfo(
            				Ext.isIE8m?evt.clientX - left_ - offLeft_:evt.clientX - left_ - offLeft_-4,
            				_relation_lines.IsTile ? (evt.clientY+RcharPEl.scrollTop) - top_ - offTop_ - tileTop:(evt.clientY+RcharPEl.scrollTop) - top_ - offTop_,
            				1
            			);
            	if(!info){
            		closeInfoTip1();
            		return;
            	}
            	if(_relation_lines.IsTile)info.y+=tileTop;
            	var x=info.x+10,y=info.y+10;
            	if(x+210>w)x-=220;	    				
				if(y+100>h)y-=110;
				showTip1(x+left_+offLeft_,y+top_+offTop_,info); 
            };
            //自定义事件
            el.onselected_ = null;
            el.onmoved_ = null;
            el.onmove_ = null;
            el.onselmove_ = null;
            el.onwheel_ = null;
            el.ontiye_=null;

            return el;
        },
        createXGrid: function (aowner) {
            var el = document.createElement("div");
            with (el.style) {
                width = w + 'px'; height = h + 'px'; overflow = "hidden"; position = "absolute"; left = adx / 2 + 'px'; top = 0 + 'px';
            }
            aowner.appendChild(el);
            for (var i = 0; i < 10; i++) {
                var l = document.createElement("div");
                with (l.style) {
                    width = 1 + 'px'; height = h + 'px'; overflow = "hidden"; position = "absolute";
                    left = adx / 2 + adx * i + 'px'; top = 0 + 'px'; borderRight = "1px dashed #808080";zIndex="2";
                }
                el.appendChild(l);
            }
            return el;
        },
        createDataXGrid: function (aowner) {//景科文新增 创建Y线区域
        	var el = document.createElement("div");
            with (el.style) {
                width = w + 'px'; height = me.DataLineCount*25 + 'px'; overflow = "visible"; position = "absolute"; left = adx / 2 + 'px'; top = 0 + 'px';
                zIndex=2;
            }
            el.id=aowner.id+"-xgrid";
            aowner.appendChild(el);
            for (var i = 0; i < 10; i++) {
                var l = document.createElement("div");
                with (l.style) {
                    width = 1 + 'px'; height = me.DataLineCount*25 + 'px'; overflow = "hidden"; position = "absolute";
                    left = adx / 2 + adx * i + 'px'; top = 0 + 'px'; borderRight = "1px dashed #808080";zIndex=2;
                }
                el.appendChild(l);
            }
        	return el;
        },
        createYGrid: function (aowner) {
            var el = document.createElement("div");
            with (el.style) {
                width = w + 'px'; height = h + 'px'; overflow = "hidden"; position = "absolute"; left = 0 + 'px'; top = ady / 2 + 'px';
            }
            aowner.appendChild(el);
            for (var i = 0; i < 10; i++) {
                var l = document.createElement("div");
                with (l.style) {
                    width = w + 'px'; height = 1 + 'px'; overflow = "hidden"; position = "absolute";
                    left = 0 + 'px'; top = ady / 2 + ady * i + 'px'; borderBottom = "1px dashed #808080";zIndex="2";
                }
                el.appendChild(l);
            }
            return el;
        },
        createSeletor: function (aowner) {
            var el = document.createElement("div");
            with (el.style) {
                overflow = "hidden"; position = "absolute";
                border = "1px solid #000";zIndex=2;
                visibility='hidden';
            }
            aowner.appendChild(el);            
            var el1 = document.createElement("div");
		    with (el1.style) {
		        width = w + 'px'; height = h + 'px'; 
		        overflow = "hidden"; position = "absolute";
		        left = '0px'; top = '0px';zIndex=2;
		    }
		    aowner.appendChild(el1);
            var canvas=document.createElement("canvas");
            el1.appendChild(canvas);
		    canvas.width = w;
		    canvas.height = h;
		    if (window.isVml) canvas = G_vmlCanvasManager.initElement(canvas);
		    var ctx = canvas.getContext("2d");
		    ctx.strokeStyle = '#ff6600';
	    	ctx.lineWidth = 5;
	    	ctx.lineCap='round';
	    	var point = document.createElement("div");
            with (point.style) {
                width = '8px'; height = '8px'; overflow = "hidden"; position = "absolute";
                background = 'url(img/circle.ico) no-repeat 0 0 transparent';
                zIndex=3;visibility='hidden';  
            }
            aowner.appendChild(point);
            
            var info=null,lx=null,ly,x0=null,y0;
            el.isShow=false;
            el.isPoint=true;
            el.hide=function(){
            	el.style.visibility='hidden';
            	point.style.visibility='hidden';
            	closeAllTip();
            	el.isShow=false;
            	//Ext.getCmp('RXSYSSJ').setEnable(false);
            	el.isPoint=true;
            };
            el.point=function(ax,ay){
            	//debug
            	if(_relation_lines.IsTile)return;
            	//debug
            	//ax-=4;
            	//ay-=16;
            	ay=/*_relation_lines.IsTile ? ay - tileTop - 4:*/ ay - 4;
            	info=_relation_lines.GetPointInfo(ax,ay);
            	el.pointInfo=info;
            	if(!info)return;
            	closeAllTip();
            	if(_relation_lines.IsTile)info.y+=tileTop;
            	with(point.style){
            		visibility='visible';
            		left = info.x + 'px';
		            top = info.y + 'px';
            	}
            	el.isShow=true;
            	//Ext.getCmp('RXSYSSJ').setEnable(true);
            	el.isPoint=true;
            };
            el.rect=function(ax,ay,aw,ah){
	            //ax-=4;
	            //ay-=18;
	            with(el.style){
	            	visibility='visible';
	            	left = ax + 'px';
		            top = ay + 'px';
		            width = aw + 'px';
		            height = ah + 'px';		            
	            }
	            el.x = ax;
    			el.y =/*_relation_lines.IsTile ? ay - tileTop :*/ ay;
    			el.w = aw;
    			el.h = ah;
	            el.isPoint=false;
            };
            el.begin=function(ax,ay){
            	ax-=-1;
            	//ay-=19;
            	x0=ax;
            	y0=ay;
            };
            el.end=function(){
            	ctx.clearRect(0, 0, w, h);
            };
            el.line=function(ax,ay){ 
            	//ay-=17;          	
            	ctx.clearRect(0, 0, w, h);
            	ax-=4;
            	ctx.beginPath();
            	ctx.moveTo(x0,y0);
            	ctx.lineTo(ax,y0);
            	ctx.lineTo(ax,ay);
            	ctx.lineTo(x0,ay);
            	ctx.lineTo(x0,y0);
            	ctx.stroke();
            	if(x0<ax){
            		el.x1=x0;
            		el.x2=ax;
            	}
            	else{
            		el.x1=ax;
            		el.x2=x0;
            	}            	
            	if(y0>ay){
            		el.y1=y0;
            		el.y2=ay;
            	}
            	else{
            		el.y1=ay;
            		el.y2=y0;
            	}
            	if(_relation_lines.IsTile){
            		el.y1-=tileTop;
            		el.y2-=tileTop;
            	}
            };
            point.onmouseover=function(evt){
            	evt = evt || window.event;
            	if(info==null)return;
            	var x=info.x+10,y=info.y+10;
            	if(x+210>w)x-=216;	    				
				if(y+100>h)y-=106;
				showTip(x+left_+offLeft_,evt.clientY,info); 
            };
            point.onmouseout=function(){
				closeInfoTip();
            };
            
            return el;
        },
        createDataSeletor: function (aowner) {//景科文 新增 绘图区画布创建
        	var el = document.createElement("div");
        	with (el.style) {
        		overflow = "hidden"; position = "absolute";
        		border = "1px solid #000";zIndex=2;
        		visibility='hidden';
        	}
        	aowner.appendChild(el);            
        	var el1 = document.createElement("div");
        	with (el1.style) {
        		width = w + 'px';  overflow = "visible"; position = "absolute";
        		left = '0px'; top = '0px';
        	}
        	aowner.appendChild(el1);
        	var canvas= parent.document.createElement("canvas");
        	el1.appendChild(canvas);
        	canvas.id=aowner.id+"-canvas";
        	canvas.width = w;
        	canvas.height = me.DataLineCount*25;
        	if (window.isVml) canvas = G_vmlCanvasManager.initElement(canvas);
        	var ctx = canvas.getContext("2d");
        	ctx.strokeStyle = '#ff6600';
        	ctx.lineWidth = 5;
        	ctx.lineCap='round';
        	var point = document.createElement("div");
        	point.id="data_point";
            with (point.style) {
                width = '8px'; height = '8px'; overflow = "hidden"; position = "absolute";
                background = 'url(img/circle.ico) no-repeat 0 0 transparent';
                zIndex=3;visibility='hidden';  
            }
            aowner.appendChild(point);
            el.isShow=false;
            el.hide=function(){
            	point.style.visibility='hidden';
            	el.isShow=false;
            };
            el.pointInfo=null;
            el.point=function(ax,ay){            	
            	//ay-=22;
            	ay-=4;
            	ax-=4;
            	var dataInfo=_relation_lines.GetDataPointInfo(ax,ay);
            	if(!dataInfo) return;
            	el.pointInfo=dataInfo;
            	ax-=2;
            	ay-=2;
            	with(point.style){
            		visibility='visible';
            		left = ax + 'px';
		            top = ay + 'px';
            	}
            	el.isShow=true;
            };
            point.onmouseover=function(evt){
            	evt = evt || window.event;
            	showDataTip(evt.clientX,evt.clientY,el.pointInfo);
            };
            point.onmouseout=function(){
            	closeDataTip();
            };
        	return el;
        },
        createOtherSel:function(aowner){//景科文 新增 创建其他绘图层信息
        	var el = document.createElement("div");
        	with (el.style) {
        		width = w + 'px';height=me.DataLineCount*25+h +"px"; overflow = "hidden"; position = "absolute";
        		left = left_ + 'px'; top = top_ + 'px';zIndex=1;border="2px solid #000";//由这里控制整个绘图的边框
        	}
        	el.id=aowner.id+"-otherCanvasEl";
        	aowner.appendChild(el);
        	var canvas=document.createElement("canvas");
        	el.appendChild(canvas);
        	canvas.id=aowner.id+"-otherCanvas";
        	canvas.width = w;
        	canvas.height = me.DataLineCount*25+h;
        },
        createXCoord: function () {
            var el = document.createElement("div");
            with (el.style) {
                width = left_ * 2 + w +15+ 'px'; height = top_ + 'px'; overflow = "hidden"; position = "absolute";
                left = left_ - adx / 2 + 'px'; top = top_ + h + 3 + 'px'; textAlign = "center"; fontSize = 12 + 'px';
            }
            el.id=_relation_owner.id+"-xcoord";
            _relation_owner.appendChild(el);
            for (var i = 0; i < 11; i++) {
                var xVal = document.createElement("div");
                with (xVal.style) {
                    width = adx +30+ 'px'; height = top_ - 2 + 'px'; overflow = "hidden"; position = "absolute";
                    left = adx * i -15+ 'px'; top = 0 + 'px'; whiteSpace='nowrap';
                }
                el.appendChild(xVal);
            }
            return el;
        },
        createYCoord: function () {
            var el = document.createElement("div");
            with (el.style) {
                width = left_ + 'px'; height = h + 12 + 'px'; overflow = "hidden"; position = "absolute";
                left = 0 + 'px'; top = top_ - 6 + 'px'; textAlign = "center"; fontSize = 12 + 'px';
            }
            _relation_owner.appendChild(el);
            for (var i = 0; i < 11; i++) {
                var yVal = document.createElement("div");
                with (yVal.style) {
                    width = left_ + 'px'; height = 14 + 'px'; overflow = "hidden"; position = "absolute";
                    left = 0 + 'px'; 
                    if(i==10) top = ady * i -5 + 'px';
                    else top = ady * i + 'px';
                }
                el.appendChild(yVal);
            }
            el.setColor = function (color) {
                this.style.color = color;
            };
            return el;
        },
        createYRCoord: function () {//景科文新增 门限右边提示信息
        	var el = document.createElement("div");
        	with (el.style) {
        		width = left_ + 'px'; height = h + 12 + 'px'; overflow = "hidden"; position = "absolute";
        		left = w+left_+2 + 'px'; top = top_ - 6 + 'px'; textAlign = "left"; fontSize = 12 + 'px';
        	}
        	el.id="YRCoord_threshold";
        	_relation_owner.appendChild(el);
        	el.setColor = function (color) {
        		this.style.color = color;
        	};
        	return el;
        },
        createTileDetailDataLine: function (aowner) {//景科文新增 右边数及删除
        	var el = document.createElement("div");
        	with (el.style) {
        		width = left_ + 'px'; overflow = "visible"; position = "absolute";
        		left = w+left_+2 + 'px'; top = h+top_ + 'px'; textAlign = "left"; fontSize = 12 + 'px';
        	}
        	el.id=aowner.id+"-TileDetailDataLine";
    		_relation_owner.appendChild(el);
        	return el;
        },
        createTileYCoord: function () {
            var el = document.createElement("div");
            with (el.style) {
                width = left_ + 'px'; height = h + 'px'; overflow = "hidden"; position = "absolute";
                left = 0 + 'px'; top = top_ + 'px'; textAlign = "center"; fontSize = 12 + 'px';
            }
            _relation_owner.appendChild(el);
            return el;
        },
        createTitleDataLine: function (aowner) {//景科文新增 在轨信息数据左边提示信息
        	var el = document.createElement("div");
        	with (el.style) {
        		width = left_ + 'px'; overflow = "visible"; position = "absolute";
        		left = 0 + 'px'; top = h+top_ + 'px'; textAlign = "center"; fontSize = 12 + 'px';
        	}
        	el.id=aowner.id+"-titlDataLine";
        	_relation_owner.appendChild(el);
        	return el;
        },
        createXRuler: function (pos,c) {
        	function setTop(){
            	if(window._RselXRuler){
                	_RselXRuler.style.zIndex=2;
                }
            	_RselXRuler=xr;
            	xr.style.zIndex=3;
            }
            
            var xr = document.createElement("div");
            var lineNum;
            with (xr.style) {
                width = adx+30 + 'px'; height = h + 16 + 'px'; overflow = _relation_owner.style.overflow; position = "absolute";
                left = left_ - adx / 2 - 15 + pos + 'px'; top = top_ - 16 + 'px'; textAlign = "center"; fontSize = 12 + 'px';
                visibility = 'hidden'; zIndex = 2;
            }
            if(pos==20){ //景科文修改  分别给左右标尺提供id
            	xr.id="RXR1";
            	lineNum=1;
            }else{
            	xr.id="RXR2";
            	lineNum=2;
            }
            _relation_owner.appendChild(xr);

            var xv = document.createElement("div");
            with (xv.style) {
                overflow = "hidden"; position = "absolute";
                width = adx+30 + 'px'; height = '14px';
                left = pos==20?'122px':'312px'; top = '3px';
                visibility = 'visible';color=c;whiteSpace='nowrap';
                backgroundColor='#fff';
            }
            if(pos==20){ //景科文修改
            	xv.id="RXV1";
            }else{
            	xv.id="RXV2";
            }
            _relation_owner.appendChild(xv);
            xv.pos = pos;

            var el = document.createElement("div"), dw = 3;
            with (el.style) {
                overflow = "hidden"; position = "absolute";
                width = dw * 2 + 'px'; height = h + 'px';
                left = adx / 2 +15- dw + 'px'; top = '16px';
                visibility = 'visible';
            }
            if(pos==20){//景科文 修改
            	el.id="RXD1";
            }else{
            	el.id="RXD2";
            }
            xr.appendChild(el);

            var l = document.createElement("div"),hl=h+4;
            with (l.style) {
                overflow = "hidden";position = "absolute";
                width = '0px'; height = hl + 'px';
                left = adx / 2+15 + 'px'; top = '12px'; borderRight = "3px solid "+c;
                visibility = 'visible';
            }
            if(pos==20){//景科文修改
            	l.id="RL1";
            }else{
            	l.id="RL2";
            }
            xr.appendChild(l);

            el.onmouseover = l.onmouseover = function () {
                this.style.cursor = 'col-resize';
                l.style.borderLeft = "1px solid "+c;
            };
            el.onmouseout = l.onmouseout = function () {
                this.style.cursor = '';
                l.style.borderLeft = "0px solid #000";
            };
            var x0, _left;
            el.onmousedown = l.onmousedown = function () {
                if (!_relation_lines.MainLine) return;
                xr.style.visibility = 'visible';
                bindEvent(function (evt) {
                    x0 = evt.clientX;
                    _left = x0 - xv.pos;
                    if (evt.button == left_btn) _drag = true;
                    setTop();
                }, function (evt) {
                    if (_drag) {
                        var dx = evt.clientX - x0;
                        var v = xv.pos + dx;
                        if (v < 0) {
                            xv.pos = 0;
                            xr.style.left = left_ - adx / 2 -14 + 'px';
                            x0 = _left;
                        }
                        else if (v <= w) {
                            xv.pos = v;
                            xr.style.left = parseInt(xr.style.left) + dx + 'px';
                            x0 = evt.clientX;
                        }
                        else {
                            xv.pos = w ;
                            xr.style.left = left_ - adx / 2 + w -20  + 'px';
                            x0 = _left + w;
                        }
                        /*if (v < 20) {
                            xv.pos = 20;
                            xr.style.left = left_ - adx / 2 + 20 + 'px';
                            x0 = _left + 20;
                        }
                        else if (v <= w - 20) {
                            xv.pos = v;
                            xr.style.left = parseInt(xr.style.left) + dx + 'px';
                            x0 = evt.clientX;
                        }
                        else {
                            xv.pos = w - 20;
                            xr.style.left = left_ - adx / 2 + w - 20 + 'px';
                            x0 = _left + w - 20;
                        }*/
                        xr.setValue();
                    }
                }, function () {
                    _drag = false;
                    l.style.borderLeft = "0px solid #000";
                    xr.style.visibility = 'hidden';
                });
            };

            xr.setValue = function (v) {
                if (v == undefined) {
                    xv.innerHTML = getTimeVal(xv.pos * dt + _relation_lines.MainLine.Begin,1);
                    timeEl.innerHTML=getTimeSpan();
                    return;
                }
                xv.innerHTML = v;
            };
            xr.getValue = function () { return xv.pos; };
            xr.reset = function () {
                xv.pos = pos;
                this.style.left = left_ - adx / 2-15 + pos + 'px';
                xv.innerHTML = getTimeVal(pos * dt + _relation_lines.MainLine.Begin,1);
                timeEl.innerHTML=getTimeSpan();
            };
            xr.show = function (b) { 
            	l.style.visibility = xv.style.visibility = el.style.visibility = b? 'visible' : 'hidden';
            };

            return xr;
        },
        createYRuler: function (pos) {
        	  function setTop(){
            	if(window._RselYRuler){
            		_RselYRuler.style.zIndex=2;
                }
            	_RselYRuler=yr;
            	yr.style.zIndex=3;
            }
        	
            var yr = document.createElement("div");
            with (yr.style) {
                width = w + left_ + 8 + 'px'; height = 14 + 'px'; overflow = "hidden"; position = "absolute";
                left = left_ + 'px'; top = top_ - 7 + pos + 'px'; textAlign = "center"; fontSize = 12 + 'px';
                visibility = 'hidden'; zIndex = 2;
            }
            _relation_owner.appendChild(yr);

            var yv = document.createElement("div");
            with (yv.style) {
                overflow = "hidden"; position = "absolute";
                width = left_ + 8 + 'px'; height = '14px';
                left = w + 4 + 'px'; top = '0px';
                visibility = 'visible';backgroundColor='#fff';
            }
            yr.appendChild(yv);
            yv.pos = pos;

            var el = document.createElement("div");
            with (el.style) {
                width = w + 'px'; height = '6px';
                overflow = "hidden"; position = "absolute";
                left = '0px'; top = '4px';
                visibility = 'visible';
            }
            yr.appendChild(el);

            var l = document.createElement("div");
            with (l.style) {
                overflow = "hidden"; position = "absolute";
                width = w +6+ 'px'; height = '0px';
                left = '0px'; top = '6px'; borderBottom = "3px solid #000";
                visibility = 'visible';
            }
            yr.appendChild(l);
            el.onmouseover = l.onmouseover = function () {
                this.style.cursor = 'row-resize';
                l.style.borderTop = "1px solid #000";
            };
            el.onmouseout = l.onmouseout = function () {
                this.style.cursor = '';
                l.style.borderTop = "0px solid #000";
            };
            var y0, _top;
            el.onmousedown = l.onmousedown = function () {
                if (!_relation_lines.MainLine) return;
                yr.style.visibility = 'visible';
                bindEvent(function (evt) {
                    y0 = evt.clientY+RcharPEl.scrollTop;
                    _top = y0 - yv.pos;
                    if (evt.button == left_btn) _drag = true;
                    setTop();
                }, function (evt) {
                    if (_drag) {
                        var dy = evt.clientY+RcharPEl.scrollTop - y0;
                        var v = yv.pos + dy;
                        if (_relation_lines.IsTile) {
                            if (v < tileTop) {
                                yv.pos = tileTop;
                                yr.style.top = tileTop + top_ - 6 + 'px';
                                y0 = _top + tileTop;
                            }
                            else if (v <= tileTop + tileHeight) {
                                yv.pos = v;
                                yr.style.top = parseInt(yr.style.top) + dy + 'px';
                                y0 = evt.clientY+RcharPEl.scrollTop;
                            }
                            else {
                                yv.pos = tileTop + tileHeight;
                                yr.style.top = top_ - 6 + tileTop + tileHeight + 'px';
                                y0 = _top + tileTop + tileHeight;
                            }
                        }
                        else {
                            if (v < 0) {
                                yv.pos = 0;
                                yr.style.top = top_ - 6 + 'px';
                                y0 = _top;
                            }
                            else if (v <= h) {
                                yv.pos = v;
                                yr.style.top = parseInt(yr.style.top) + dy + 'px';
                                y0 = evt.clientY+RcharPEl.scrollTop;
                            }
                            else {
                                yv.pos = h;
                                yr.style.top = top_ - 6 + h + 'px';
                                y0 = _top + h;
                            }
                        }
                        yr.setValue();
                    }
                }, function () {
                    _drag = false;
                    l.style.borderTop = "0px solid #000";
                    yr.style.visibility = 'hidden';
                });
            };

            yr.setValue = function (v) {
                if (v == undefined) {
                    yv.innerHTML =
                    _relation_lines.IsTile ?
                    _relation_lines.MainLine.GetFmtVal(_relation_lines.MainLine.Max - (_relation_lines.MainLine.Max - _relation_lines.MainLine.Min) / tileHeight * (yv.pos - tileTop), 10) :
                    _relation_lines.MainLine.GetFmtVal(_relation_lines.MainLine.Max - dv * yv.pos, 10);
                    valueEl.innerHTML=getValueSpan();
                    return;
                }
                yv.innerHTML = v;
            };
            yr.getValue = function () { return yv.pos; };
            yr.reset = function () {
                yv.pos = pos;
                var ty = top_ - 6 + pos;
                this.style.top = _relation_lines.IsTile ? pos == 20 ? tileTop + ty + 'px' : tileTop + tileHeight - 20 + top_ - 6 + 'px' : ty + 'px';
                this.setValue();
            };
            yr.show = function (b) { 
            	l.style.visibility = yv.style.visibility = el.style.visibility = b? 'visible' : 'hidden';
            };

            return yr;
        },
        createBorder: function () {
            var el = document.createElement("div");
            with (el.style) {
                overflow = "hidden"; position = "absolute";
                width = '4px'; height = h + 'px';
                top = top_ + 'px'; backgroundColor = '#C0C0C0';
                border = '1px solid #A9A9A9'; display = 'none';
            }
            el.id="REL_id";//景科文新增 添加边框id
            _relation_owner.appendChild(el);

            var leftBorder = document.createElement("div"), dw = 3;
            with (leftBorder.style) {
                overflow = "hidden"; position = "absolute";
                width = dw * 2 + 'px'; height = h + 'px';
                left = left_ - dw + 'px'; top = top_ + 'px';
                visibility = 'visible';
            }
            leftBorder.id="R_leftBorder";//景科文修改 添加右边框id
            _relation_owner.appendChild(leftBorder);
            var rightBorder = document.createElement("div");
            with (rightBorder.style) {
                overflow = "hidden"; position = "absolute";
                width = dw * 2 + 'px'; height = h + 'px';
                left = left_ + w + 4 - dw + 'px'; top = top_ + 'px';
                visibility = 'visible';
            }
            rightBorder.id="R_rightBorder";//景科文修改 添加左边框id
            _relation_owner.appendChild(rightBorder);

            var _left, _dt, _pos;
            leftBorder.onmouseover = function () {
                if (_drag || window._IsPlay) return;
                this.style.cursor = 'col-resize';
                el.style.left = left_ - dw + 'px';
                el.style.display = '';
                _pos = 0;
            };
            rightBorder.onmouseover = function () {
                if (_drag || window._IsPlay) return;
                this.style.cursor = 'col-resize';
                el.style.left = left_ + w - dw + 'px';
                el.style.display = '';
                _pos = 20;
            };
            leftBorder.onmouseout = rightBorder.onmouseout = function () {
                if (!_drag) el.style.display = 'none';
            };
            leftBorder.onmousedown = rightBorder.onmousedown = function () {
                if (!_relation_lines.MainLine||window._IsPlay) return;
                bindEvent(function (evt) {
                    _left = evt.clientX;
                    if (evt.button == left_btn) _drag = true;
                }, function (evt) {
                    if (_drag) {
                        var x = evt.clientX - _left;
                        if (_pos == 20) x += w;
                        if (x < _pos) el.style.left = left_ + _pos + 'px';
                        else if (x <= _pos + w - 20) el.style.left = left_ + x + 'px';
                        else el.style.left = left_ + _pos + w - 20 + 'px';
                        if (el.onmove_) {
                            x = parseInt(el.style.left) - left_ + dw;
                            if (_pos == 0) el.onmove_('L', x);
                            else el.onmove_('R', x);
                        }
                    }
                }, function () {
                    if (_drag) {
                        _drag = false;
                        el.style.display = 'none';
                        if (el.onchange_) el.onchange_();
                    }
                });
            };

            el.onchange_ = null;
            el.onmove_ = null;
            return el;
        }
    };
    //成员变量
    _relation_chart = null; //绘图边界+网格线+坐标值
    var xRuler1, //X标尺
        xRuler2, //X标尺
        yRuler1, //Y标尺
        yRuler2, //Y标尺
        border; //时间边界
    //方法
    this.Update = function (isHidePoint) {
    	if(isHidePoint==undefined||isHidePoint)_relation_chart.hidePoint();
    	
        adt = (_relation_lines.MainLine.End - _relation_lines.MainLine.Begin) / 10;
        dt = (_relation_lines.MainLine.End - _relation_lines.MainLine.Begin) / w;
        var dv1 = _relation_lines.MainLine.End - _relation_lines.MainLine.Begin;
        adv = (_relation_lines.MainLine.Max - _relation_lines.MainLine.Min) / 10;
        dv = (_relation_lines.MainLine.Max - _relation_lines.MainLine.Min) / h;

        _relation_chart.setOrigin();
        _relation_chart.updateTime(_relation_lines.MainLine.Begin);
        _relation_chart.updateVal(_relation_lines.MainLine.Max);

        xRuler1.reset();
        xRuler2.reset();
        yRuler1.reset();
        yRuler2.reset();
    };
    this.Move = function (dx, dy) {
        _relation_chart.hidePoint();
        _relation_chart.hideDataPoint();
        _relation_chart.move(dx, dy);
        _relation_chart.dataMove(dx, dy);//景科文新增 添加航天器相关信息移动跟随
        
        xRuler1.setValue();
        xRuler2.setValue();
        yRuler1.setValue();
        yRuler2.setValue();
    };
    this.SetColor = function (color) {
        _relation_chart.yCoord.setColor(color);
    };
    this.Clear = function () {
        var nodes = _relation_chart.xCoord.childNodes;
        for (var i = 0; i < nodes.length; i++) nodes[i].innerHTML = '';
        nodes = _relation_chart.yCoord.childNodes;
        for (i = 0; i < nodes.length; i++) nodes[i].innerHTML = '';
        xRuler1.setValue('');
        xRuler2.setValue('');
        yRuler1.setValue('');
        yRuler2.setValue('');
        _relation_chart.hidePoint();
        _relation_chart.hideDataPoint();
    };
    this.CreateYCoord = function (aid, atop, aheight) {
        _relation_chart.hidePoint();
        _relation_chart.hideDataPoint();
        _relation_chart.newYCoord(aid, atop, aheight);
    };
    this.DelYCoord = function () {
        _relation_chart.delYCoord();
    };
    this.UpdateYRuler = function (atop, aheight) {
        if (aheight == undefined) {
            tileHeight = h / _relation_lines.ShowCount;
            tileTop = atop * tileHeight;
        }
        else {
            tileTop = atop;
            tileHeight = aheight;
        }
        yRuler1.reset();
        yRuler2.reset();
    };
    this.SelectLine = function (isSel) {
        isSel_ = isSel;
        if (isSel_) {
            _relation_chart.style.borderStyle = 'dashed';
        }
        else {
            _relation_chart.style.borderStyle = 'solid';
        }
    };
    this.Resize = function (aw,ah) {
    	w = _relation_owner.clientWidth - left_ * 2;
        h = _relation_owner.clientHeight - top_*2-(me.DataLineCount*25);//-70;
        adx = w / 10;
        ady = h / 10;
        offLeft_ = getOffsetLeft(_relation_owner) + 1;
        offTop_ = getOffsetTop(_relation_owner) + 3;
        this.Width = w;
        this.Height = h;
        if (_relation_lines.IsTile) {
            var bh = h / _relation_lines.ShowCount;
            tileTop = bh * tileTop / tileHeight;
            tileHeight = bh;
        }
        _relation_owner.innerHTML = '';
        init();
        if (_relation_lines.MainLine) {
            this.SetColor(_relation_lines.MainLine.getColor());
            this.Update(); 
        }
    };
    this.ShowOrigin = function (info,data) {
        closeAllTip();
        var x=info.x+10,y=info.y+10;
    	if(x+400>w)x-=406;	    				
		if(y+300>h)y-=306;
        _YSSJT = Ext.create('Ext.panel.Panel',{  
	        x:x+left_+offLeft_,
	        y:y+top_+offTop_,
	        width: 400,        
	        height: 300,  
	        frame: true,
	        closable:true,
	        title:'原始数据点图-'+info.name,
	        layout:'fit',
	        items:{
	        	id:'OriginChart',
	        	border:false
	        },
	        style:{
		    	position:'absolute',
		    	zIndex:2
		    },
	        padding:0,
	        renderTo: Ext.getBody()
	    });
	    var oc = isIE?Ext.getDom('OriginChart').childNodes[1].childNodes[0].firstChild : Ext.getDom('OriginChart').childNodes[0].childNodes[0].firstChild,
	    	oleft_ = 40,
	    	ow=oc.clientWidth-oleft_,
	    	obottom_=18,
	    	oh=oc.clientHeight-obottom_,
    		canvas = document.createElement("canvas"),
    		timespan = 5,//默认5分钟
    		ot1=info.t.getTime(1)-60000*timespan,
    		dy = data.max - data.min,
    		omax,
    		omin;
        dy = dy == 0 ? data.max != 0 ? data.max / 2 : 0.5 : dy / 20;
        omax = data.max + dy;
        omin = data.min - dy;
	    oc.appendChild(canvas);
	    canvas.width = oc.clientWidth;
	    canvas.height = oc.clientHeight;
	    if (window.isVml) canvas = G_vmlCanvasManager.initElement(canvas);
	    var ctx = canvas.getContext("2d");
	    ctx.MoveTo = function (x, y) { ctx.moveTo(x + oleft_, y);};
	    ctx.LineTo = function (x, y) { ctx.lineTo(x + oleft_, y);};
	    ctx.DrawPoint=function(x, y, color){
	    	ctx.strokeStyle = ctx.fillStyle = color;
	    	ctx.beginPath();
	    	if(Ext.isIE8m)ctx.arc(x + oleft_, y, 1, 0, Math.PI*2);
	    	ctx.arc(x + oleft_, y, 2, 0, Math.PI*2);
	    	ctx.stroke();
	    	ctx.fill();
	    };
	    ctx.StrokeText = function (str, x, y, color) {
	        if (color == undefined) color = '#000';
	        var el = document.createElement("div");
	        x = x * 1 + oleft_ - 40;
	        y = y * 1;
	        el.style.cssText = 'position:absolute;overflow:hidden;text-align:center;font-size:12px;left:' + x + 'px;top:' + y + 'px;width:80px;height:14px;color:' + color;
	        el.innerHTML = str;
	        oc.appendChild(el);
	    };
	    ctx.strokeStyle = '#000';
	    ctx.lineWidth = 1;
	    ctx.MoveTo(0, 0);
    	ctx.LineTo(0, oh);
    	ctx.MoveTo(0, oh);
        ctx.LineTo(ow, oh);
        ctx.stroke();
        ctx.StrokeText(_relation_lines.MainLine.GetFmtVal(omax),-20,2,info.color);
        ctx.StrokeText(_relation_lines.MainLine.GetFmtVal(omin),-20,oh-18,info.color);
	    ctx.strokeStyle = '#808080';
	    ctx.lineWidth = 0.5;
	    for (var i = 1; i < 5; i++) {
	    	var ax = ow / 5 * i;
	    	ctx.MoveTo(ax, 0);
        	ctx.LineTo(ax, oh);
        	ctx.StrokeText(new Date(ot1+24000*timespan*i).format('i:s.u'),ax,oh);
        	var ay = oh / 5 * i;
	        ctx.MoveTo(0, ay);
	        ctx.LineTo(ow, ay);
	    }
	    ctx.stroke();
	    for(i=0;i<data.items.length;i++){
	    	var t=data.items[i].time.getTime(1),
	    		v=data.items[i].val;
	    	ctx.DrawPoint((t-ot1)/(120000*timespan)*ow,oh*(omax-v)/(omax-omin),info.color);
	    }
    };
    this.GetXRulerInfo = function(){
    	return {x1:xRuler1.getValue(),x2:xRuler2.getValue()};
    };
    this.ShowStats = function(ty){
    	closeAllTip();
    	//弹出统计窗口  mengxiangchao
    	tongjiWin.openWin(ty==1?'按照游标区间统计':'统计分析', 850, 400, "tongjiGrid")
    
    	/*
    	_STATS = Ext.create('Ext.window.Window',{  
	        x:left_+offLeft_+w/2-400,
	        y:top_+offTop_+h/2-200,
	        width: 850,        
	        height: 400,  
	        frame: true,
	        closable:true,
	        title:ty==1?'按照游标区间统计':'统计分析',
	        layout:'fit',
	        items:Ext.create('Ext.grid.Panel',{
				itemId:'grid',
				border:false, 
				store: Ext.create('Ext.data.Store', {
				    fields:['name', 'param', 'code', 'type', 'aver', 'max', 'min', 'begin', 'end'],
				    data:{'items':[]},
				    proxy: {
				        type: 'memory',
				        reader: {
				            type: 'json',
				            root: 'items'
				        }
				    }
				}),
				sortableColumns:false,
				forceFit:true,
		        columns: [{
		            xtype: 'rownumberer'
		        }, {
		            header: "名称",
		            dataIndex: 'name',
		            menuDisabled: true,
		            width:100
		        }, {
		            header: "参数",
		            dataIndex: 'param',
		            menuDisabled: true,
		            width:80
		        }, {
		            header: "代号",
		            dataIndex: 'code',
		            menuDisabled: true,
		            width:70
		        }, {
		            header: "类型",
		            dataIndex: 'type',
		            menuDisabled: true,
		            width:50
		        }, {
		            header: "平均值",
		            dataIndex: 'aver',
		            menuDisabled: true,
		            width:80
		        },  {
		            header: "最小值",
		            dataIndex: 'min',
		            menuDisabled: true,
		            width:80
		        },{
		            header: "最大值",
		            dataIndex: 'max',
		            menuDisabled: true,
		            width:80
		        },{
		            header: "开始时间",
		            dataIndex: 'begin',
		            menuDisabled: true,
		            width:115
		        }, {
		            header: "结束时间",
		            dataIndex: 'end',
		            menuDisabled: true,
		            width:115
		        }]
		   	}),
	        style:{
		    	position:'absolute',
		    	zIndex:2
		    },
		    padding:0,
	        renderTo: Ext.getBody()
	    });
    	_STATS.show();*/
    };
    this.ShowRuler = function (b) { 
    	if(b == undefined || b){
    		xRuler1.show(true);
    		xRuler2.show(true);
    		yRuler1.show(true);
    		yRuler2.show(true);
    	}
    	else{
    		xRuler1.show(false);
    		xRuler2.show(false);
    		yRuler1.show(false);
    		yRuler2.show(false);
    	}
    };
    function init() {
        //绘图边界+网格线+坐标值
        _relation_chart = Canvas.createChart();
        _relation_chart.onselected_ = function (ax, ay, aw, ah) {
            var flag=_relation_cmd.Add({Type: '放大曲线',Begin: ax,End: ax + aw,Max: ay,Min: ay + ah});
        };
        _relation_chart.onmove_ = function (dx, dy) {
            _relation_cmd.Add({ Type: '移动曲线', moved: false, dx: dx, dy: dy });
        };
        _relation_chart.onmoved_ = function (dx, dy) {
            _relation_cmd.Add({ Type: '移动曲线', moved: true });
        };
        _relation_chart.onselmove_ = function (dy) {
            _relation_lines.SelMove(dy);
        };
        _relation_chart.onwheel_ = function (fx) {
            if (fx == -1) _relation_cmd.Add({ Type: '放大当前曲线' });
            else _relation_cmd.Add({ Type: '缩小当前曲线' });
        };
        _relation_chart.ontiye_=function(ax1,ax2,ay1,ay2){
        	_relation_cmd.Add({ Type: '曲线剔野', x1: ax1, x2: ax2, y1: ay1, y2: ay2});
        };
        //X标尺
        xRuler1 = Canvas.createXRuler(20,'#f00');
        xRuler2 = Canvas.createXRuler(w - 20,'#00f');
        //Y标尺
        yRuler1 = Canvas.createYRuler(20);
        yRuler2 = Canvas.createYRuler(h - 20);
        //时间边界
        border = Canvas.createBorder();
        border.onmove_ = function (fx, x1) {
            _relation_cmd.Add({ Type: '开始压缩曲线',fx:fx,x:x1});
        };
        border.onchange_ = function () {
            _relation_cmd.Add({ Type: '压缩曲线'});
        };
    }

    init();

    function getOffsetLeft(e) {
        var l = e.offsetLeft;
        while (e = e.offsetParent) {
            l += e.offsetLeft;
        }
        return l;
    }
    function getOffsetTop(e) {
        var t = e.offsetTop;
        while (e = e.offsetParent) {
            t += e.offsetTop;
        }
        return t;
    }
    function bindEvent(downfn, movefn, upfn) {
    	var xValue = null;
    	var yValue = null;
        _relation_owner.onmousedown = function (e) {
        	//mxc add 每次点击的时候，将是否需要执行剔野置为false;
        	relationNeedTiye = false;
        	//mxc add 记录当前鼠标点击位置
        	xValue = e.clientX;
        	yValue = e.clientY;
            downfn(e || event);
            if (_relation_owner.setCapture) _relation_owner.setCapture();
        };
        _relation_owner.onmousemove = function (e) { movefn(e || event); };
        if (_relation_owner.releaseCapture) {
            _relation_owner.onmouseup = function (e) {
                _relation_owner.releaseCapture();
                upfn(e || event);
                _relation_owner.onmousedown = null;
                _relation_owner.onmousemove = null;
                _relation_owner.onmouseup = null;
            };
        }
        else {
            window.onmouseup = function (e) {
            	//mxc add 若松开鼠标时，位置和按下鼠标时位置一样则不需要剔野
          	  if(e.clientX != xValue || e.clientY != yValue){
          		  if(Ext.getCmp('RSJTY').pressed){
          			relationNeedTiye = true;
          		  }
          	  }
                upfn(e || event);
                _relation_owner.onmousedown = null;
                _relation_owner.onmousemove = null;
                window.onmouseup = null;
            };
        }
    }
    function showTip(x,y,info){
    	_TIP = Ext.create('Ext.panel.Panel',{  
	        x:x,
	        y:y,
	        width: 210,        
	        height: 100,  
	        frame: true,
	        html:Ext.String.format('<p>曲线名称: {0}</p><p>当前时间: {1}</p><p>当前数值: {2}</p>',info.name,info.t,info.v),
	        style:{
		    	position:'absolute',
		    	zIndex:2
		    },
	        //padding:10,
	        header:false,
	        renderTo: Ext.getBody()
	    }); 
    }
    function showTip1(x,y,info){
    	if(window._TIP1)return;
    	_TIP1 = Ext.create('Ext.panel.Panel',{  
	        x:x,
	        y:y,
	        width: 210,        
	        height: 100,  
	        frame: true,
	        html:Ext.String.format('<p>曲线名称: {0}</p><p>当前时间: {1}</p><p>当前数值: {2}</p>',info.name,info.t,info.v),
	        style:{
		    	position:'absolute',
		    	zIndex:2
		    },
	        //padding:10,
	        header:false,
	        renderTo: Ext.getBody()
	    }); 
    }
    function closeInfoTip1(){
    	if(window._TIP1){
    		_TIP1.close();
    		_TIP1=null;
    	}
    }
    function showDataTip(x,y,info){
    	if(window._DataTip)return;
    	_DataTip = Ext.create('Ext.panel.Panel',{  
    		x:x+8,
    		y:y+8,
    		width: 210,        
    		height: 100,  
    		frame: true,
    		html:Ext.String.format('<p>曲线类型: {0}</p><p>当前时间: {1}</p><p>摘要信息: {2}</p>',info.typeName,info.t,info.v),
    		style:{
    			position:'absolute',
    			zIndex:2
    		},
    		//padding:10,
    		header:false,
    		renderTo: Ext.getBody()
    	}); 
    }
    function closeDataTip(){
    	if(window._DataTip){
    		_DataTip.close();
    		_DataTip=null;
    	}
    }
    function closeInfoTip(){
    	if(window._TIP)_TIP.close();
    }
    function closeAllTip(){
    	if(window._YSSJT)_YSSJT.close();
    	if(window._STATS)_STATS.close();
    }
}
