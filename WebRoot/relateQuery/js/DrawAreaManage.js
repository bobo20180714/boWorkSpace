/*
 *绘图区管理器
 */

Ext.define('js.DrawAreaManage',{
	constructor:function(){
		return new DrawAreaManage();
	}
});	

function DrawAreaManage() {
    var left_ = 60,
        top_ = 20,
        w = _owner.clientWidth - left_ * 2 - 8,
        h = _owner.clientHeight - top_ * 2,
        dt,
        timeFormat = 'Y-m-d H:i:s',//'H:i:s',
        dv,
        adx = w / 10,
        ady = h / 10,
        offLeft_ = getOffsetLeft(_owner) + 1, //256
        offTop_ = getOffsetTop(_owner) + 3, //28
        me = this,
        adt,
        adv,
        tileTop,
        tileHeight,
        isSel_ = false,
        left_btn = Ext.isIE8m ? 1 : 0,
        right_btn = 2,
        _drag,
        searchNeedTiye = false,//mxc add true:需要执行剔野，false:不需要执行剔野
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
	    	var t1=new Date(xRuler1.getValue() * dt + _lines.MainLine.Begin),
	    		t2=new Date(xRuler2.getValue() * dt + _lines.MainLine.Begin),
	    		span=Math.floor((t2-t1)/1000);
	    	if(span<0)span=-span;
	    	return ['时间差值：',Math.floor(span/86400),'天'
	    	        ,Math.floor(span%86400/3600),'时'
	    	        ,Math.floor(span%3600/60),'分'
	    	        ,span%60,'秒'].join('');
	    },
	    getValueSpan=function(){
	    	var v1=_lines.IsTile ?
                   _lines.MainLine.GetFmtVal(_lines.MainLine.Max - (_lines.MainLine.Max - _lines.MainLine.Min) / tileHeight * (yRuler1.getValue() - tileTop),10) :
                   _lines.MainLine.GetFmtVal(_lines.MainLine.Max - dv * yRuler1.getValue(),10),
                v2=_lines.IsTile ?
                   _lines.MainLine.GetFmtVal(_lines.MainLine.Max - (_lines.MainLine.Max - _lines.MainLine.Min) / tileHeight * (yRuler2.getValue() - tileTop),10) :
                   _lines.MainLine.GetFmtVal(_lines.MainLine.Max - dv * yRuler2.getValue(),10),
                span=v2-v1;
            if(span<0)span=-span;
            return '数据差值：'+_lines.MainLine.GetFmtVal(span,10);
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

    var Canvas = {
        createChart: function () {
            //创建绘图区元素
            var el = document.createElement("div");
            with (el.style) {
                width = w + 'px'; height = h + 'px'; overflow = "hidden"; position = "absolute";
                left = left_ + 'px'; top = top_ + 'px'; border = "2px solid #000";
            }
            _owner.appendChild(el);
            //创建绘图区对象
            var xGrid = this.createXGrid(el);
            var yGrid = this.createYGrid(el);
            var sel = this.createSeletor(el);
            var xCoord = this.createXCoord();
            var yCoord = this.createYCoord();
            var tileYCoord = this.createTileYCoord();
            //xjt-15.2.28 
            var el1 = document.createElement("div");
            with (el1.style) {
                overflow = "hidden"; position = "absolute";
                width = '59px'; height = '14px';
                left = '60px'; top = '3px';
                visibility = 'visible';color='#000';whiteSpace='nowrap';
                backgroundColor='#fff';
            } 
            el1.innerHTML='游标1时间：';
            _owner.appendChild(el1);
            var el2 = document.createElement("div");
            with (el2.style) {
                overflow = "hidden"; position = "absolute";
                width = '59px'; height = '14px';
                left = '250px'; top = '3px';
                visibility = 'visible';color='#000';whiteSpace='nowrap';
                backgroundColor='#fff';zIndex=1;
            } 
            el2.innerHTML='游标2时间：';
            _owner.appendChild(el2);
            timeEl= document.createElement("div");
            with (timeEl.style) {
                overflow = "hidden"; position = "absolute";
                width = '170px'; height = '14px';
                left = '440px'; top = '3px';
                visibility = 'visible';color='#000';whiteSpace='nowrap';
                backgroundColor='#fff';zIndex=1;
            } 
            timeEl.innerHTML='时间差值：';
            _owner.appendChild(timeEl);
            valueEl= document.createElement("div");
            with (valueEl.style) {
                overflow = "hidden"; position = "absolute";
                width = '200px'; height = '14px';
                right = '80px'; top = '3px';
                visibility = 'visible';color='#000';whiteSpace='nowrap';
                backgroundColor='#fff';
            } 
            valueEl.innerHTML='数据差值：';
            _owner.appendChild(valueEl);
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
                yGrid.style.top = ady / 2 + 'px';
                xCoord.style.left = left_ - adx / 2 + 'px';
                yCoord.style.top = top_ - 6 + 'px';
            }
            el.updateTime = function (abegin) {
                begin = abegin;
                var nodes = xCoord.childNodes;
                for (var i = 0; i < nodes.length; i++) {
                	if(i%2==1)continue;
                    nodes[i].innerHTML = getTimeVal(begin + adt * i);
                }
            }
            el.updateVal = function (amax) {
            	max = amax;
                if (_lines.IsTile) return;                
                var nodes = yCoord.childNodes;
                for (var i = 0; i < nodes.length; i++) {
                    nodes[i].innerHTML = _lines.MainLine.GetFmtVal(max - adv * i);
                }
            }
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
            }
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
            }
            el.delYCoord = function () {
                tileYCoord.innerHTML = '';
                yCoord.style.visibility = 'visible';
            }
            el.getLeft = function () {
                return begin;
            }
            el.getTop = function () {
                return max;
            }
            el.hidePoint = function () {
                if(sel.isShow)sel.hide();
            }
            el.getPointInfo = function () {
                return sel.pointInfo;
            }
            el.getOffsetLeft = function () {
                return left_ + offLeft_;
            }
            el.getOffsetTop = function () {
                return top_ + offTop_;
            }
            //事件
            el.onmousedown = function () {
                if (!_lines.MainLine || window._IsPlay) return;
                bindEvent(function (evt) {
                    var ty = evt.clientY - top_ - offTop_;
                    /*if (_lines.IsTile && (ty < tileTop + 5 || ty > tileTop + tileHeight - 5)) return;*/
                    lx = x = evt.clientX;
                    ly = y = evt.clientY;
                    if (evt.button == right_btn) {
                        _owner.style.cursor = 'pointer';
                        right_drag = true;
                        sel.hide();
                        //popMenu.showAt(x,y);                        
                    }
                    else if (evt.button == left_btn) {
                        left_drag = true;                       
                        if(Ext.getCmp('SJTY').pressed){
                        	_owner.style.cursor = 'pointer';
                        	sel.begin(x - left_ - offLeft_,y - top_ - offTop_);
                        }
                        else {
                        	//sel.point(x - left_ - offLeft_,y - top_ - offTop_);
                        	//popMenu.hide();
                        }
                    }                  
                }, function (evt) {
                    if (left_drag) {
                        if (isSel_) return;
                        if(Ext.getCmp('SJTY').pressed){
                        	sel.line(evt.clientX - left_ - offLeft_,evt.clientY - top_ - offTop_);
                        }
                        else{
                        	var sy = (y < evt.clientY ? y : evt.clientY) - top_ - offTop_;
	                        var sh = Math.abs(y - evt.clientY);
	                        /*if (_lines.IsTile) {
	                            if (sy < tileTop + 5) {
	                                sh -= tileTop + 5 - sy;
	                                sy = tileTop + 5;
	                            }
	                            if (sy + sh > tileTop + tileHeight - 5) sh = tileTop + tileHeight - 5 - sy;
	                        }
	                        else */{
	                            if (sy < 5) {
	                                sh -= 5 - sy;
	                                sy = 5;
	                            }
	                            if (sy + sh > h + 15/*-5*/){
	                            	sh = h + 15/*- 5*/ - sy;
	                            }
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
                        var dx = evt.clientX - lx, dy = evt.clientY - ly;
                        if (isSel_) {
                            if (el.onselmove_)
                                el.onselmove_(dy);
                        }
                        else if (el.onmove_)el.onmove_(dx, dy);
                        lx = evt.clientX;
                        ly = evt.clientY;
                    }
                }, function (evt) {
                    if (evt.button == right_btn) {
                        _owner.style.cursor = '';
                        if (el.onmoved_)
                            el.onmoved_();
                        right_drag = false;
                    }
                    else {
                    	if(left_drag&&Ext.getCmp('SJTY').pressed){//剔野
                			_owner.style.cursor = '';
                			/*
                			 * Ext.Msg.confirm('提示','确认剔除选择的线段吗?',function(btn){
			        			if(btn=='yes'&&el.ontiye_)el.ontiye_(sel.x1, sel.x2, sel.y);
			        			sel.line(); 
			        		});  	
                			 */	
                			//mxc add true:需要执行剔野，false:不需要执行剔野
                			if(searchNeedTiye){
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
            }           
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
            		info=_lines.GetPointInfo(
            				Ext.isIE8m?evt.clientX - left_ - offLeft_:evt.clientX - left_ - offLeft_-4,
            				_lines.IsTile ? evt.clientY - top_ - offTop_ - tileTop:evt.clientY - top_ - offTop_,
            				1
            			);
            	if(!info){
            		closeInfoTip1();
            		return;
            	}
            	if(_lines.IsTile)info.y+=tileTop;
            	var x=info.x+10,y=info.y+10;
            	if(x+210>w)x-=220;	    				
				if(y+100>h)y-=110;
				showTip1(x+left_+offLeft_,y+top_+offTop_,info); 
            }
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
                    left = adx / 2 + adx * i + 'px'; top = 0 + 'px'; borderRight = "1px dashed #808080";
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
                    left = 0 + 'px'; top = ady / 2 + ady * i + 'px'; borderBottom = "1px dashed #808080";
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
            	//Ext.getCmp('XSYSSJ').setEnable(false);
            	el.isPoint=true;
            }
            el.point=function(ax,ay){
            	ax-=8;
            	//ay-=18;//xjt-150518
            	ay=_lines.IsTile ? ay - tileTop - 4: ay - 4;
            	var info_=_lines.GetPointInfo(ax,ay);
            	if(info_)info=info_;
            	el.pointInfo=info_;
            	if(!info_)return;
            	closeAllTip();
            	if(_lines.IsTile)info.y+=tileTop;
            	with(point.style){
            		visibility='visible';
            		left = info.x + 'px';
		            top = info.y + 'px';
            	}
            	el.isShow=true;
            	//Ext.getCmp('XSYSSJ').setEnable(true);
            	el.isPoint=true;
            }
            el.rect=function(ax,ay,aw,ah){  
	            ax-=4;
	            ay-=4;//xjt-150518
	            with(el.style){
	            	visibility='visible';
	            	left = ax + 'px';
		            top = ay + 'px';
		            width = aw + 'px';
		            height = ah + 'px';		            
	            }
	            el.x = ax;
    			el.y =/*_lines.IsTile ? ay - tileTop :*/ ay;
    			el.w = aw;
    			el.h = ah;
	            el.isPoint=false;
            }
            el.begin=function(ax,ay){
            	//ctx.clearRect(0, 0, w, h);
//            	el.x1=el.x2=lx=ax;
//            	ly=ay; 
//            	if(_lines.IsTile)ay-=tileTop;
//            	el.y1=el.y2=ay; 
            	ax-=4;
            	ay-=4;//18;//xjt-150518
            	x0=ax;
            	y0=ay;
            }
            el.end=function(){
            	ctx.clearRect(0, 0, w, h);
            }
            el.line=function(ax,ay){		 
            	ctx.clearRect(0, 0, w, h);
            	ax-=4;
            	ay-=4;//18;//xjt-150518
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
            	if(_lines.IsTile){
            		el.y1-=tileTop;
            		el.y2-=tileTop;
            	}
//            	ctx.beginPath();
//            	ctx.moveTo(lx,ly);
//            	ctx.lineTo(ax,ay);
//            	ctx.stroke();
//            	lx=ax;
//            	ly=ay;            	
//            	if(el.x1>ax)el.x1=ax;
//            	if(el.x2<ax)el.x2=ax;
//            	if(_lines.IsTile)ay-=tileTop;
//            	if(el.y1<ay)el.y1=ay;
//            	if(el.y2>ay)el.y2=ay;            	
            	/*
            	 * if(ax==undefined){
            		x0=null;
            		ctx.clearRect(0, 0, w, h);
            		return;
            	}
            	ax-=4;
            	if(x0){
            		ctx.clearRect(0, 0, w, h);
            		ctx.beginPath();
            		ctx.moveTo(x0,y0);
            		ctx.lineTo(ax,y0);
            		ctx.stroke();
            		if(x0>ax){
            			el.x1=ax;
            			el.x2=x0;
            		}
            		else{
            			el.x1=x0;
            			el.x2=ax;
            		}
            	}
            	else{
            		el.x1=null;
            		x0=ax;
            		el.y=y0=ay;
            	}
            	 */
            }
            point.onmouseover=function(){
            	//point.style.cursor='crosshair';
            	if(info==null)return;
            	var x=info.x+10,y=info.y+10;
            	if(x+210>w)x-=216;	    				
				if(y+100>h)y-=86;//106;
				showTip(x+left_+offLeft_,y+top_+offTop_,info); 
            }
            point.onmouseout=function(){
            	//el.style.cursor='';
				closeInfoTip();
            }
            
            return el;
        },
        createXCoord: function () {
            var el = document.createElement("div");
            with (el.style) {
                width = left_ * 2 + w +15+ 'px'; height = top_ + 'px'; overflow = "hidden"; position = "absolute";
                left = left_ - adx / 2 + 'px'; top = top_ + h + 3 + 'px'; textAlign = "center"; fontSize = 12 + 'px';
            }
            _owner.appendChild(el);
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
            _owner.appendChild(el);
            for (var i = 0; i < 11; i++) {
                var yVal = document.createElement("div");
                with (yVal.style) {
                    width = left_ + 'px'; height = 14 + 'px'; overflow = "hidden"; position = "absolute";
                    left = 0 + 'px'; top = ady * i + 'px';
                }
                el.appendChild(yVal);
            }
            el.setColor = function (color) {
                this.style.color = color;
            }
            return el;
        },
        createTileYCoord: function () {
            var el = document.createElement("div");
            with (el.style) {
                width = left_ + 'px'; height = h + 'px'; overflow = "hidden"; position = "absolute";
                left = 0 + 'px'; top = top_ + 'px'; textAlign = "center"; fontSize = 12 + 'px';
            }
            _owner.appendChild(el);
            return el;
        },
        createXRuler: function (pos,c) {
        	function setTop(){
            	if(window._selXRuler){
                	_selXRuler.style.zIndex=2;
                }
            	_selXRuler=xr;
            	xr.style.zIndex=3;
            }
        	
            var xr = document.createElement("div");
            with (xr.style) {
                width = adx+30 + 'px'; height = h + 16 + 'px'; overflow = "hidden"; position = "absolute";
                left = left_ - adx / 2 - 15 + pos + 'px'; top = top_ - 16 + 'px'; textAlign = "center"; fontSize = 12 + 'px';
                visibility = 'hidden'; zIndex = 2;
            }
            _owner.appendChild(xr);

            var xv = document.createElement("div");
            with (xv.style) {
                overflow = "hidden"; position = "absolute";
                width = adx+30 + 'px'; height = '14px';
                left = pos==20?'122px':'312px'; top = '3px';
                visibility = 'visible';color=c;whiteSpace='nowrap';
                backgroundColor='#fff';
            }            
//            xr.appendChild(xv);
            _owner.appendChild(xv);
            xv.pos = pos;

            var el = document.createElement("div"), dw = 3;
            with (el.style) {
                overflow = "hidden"; position = "absolute";
                width = dw * 2 + 'px'; height = h + 'px';
                left = adx / 2 +15- dw + 'px'; top = '16px';
                visibility = 'visible';
            }
            xr.appendChild(el);

            var l = document.createElement("div"),hl=h+4;
            with (l.style) {
                overflow = "hidden";position = "absolute";
                width = '0px'; height = hl + 'px';
                left = adx / 2+15 + 'px'; top = '12px'; borderRight = "3px solid "+c;
                visibility = 'visible';
            }
            xr.appendChild(l);

            el.onmouseover = l.onmouseover = function () {
                this.style.cursor = 'col-resize';
                l.style.borderLeft = "1px solid "+c;
            }
            el.onmouseout = l.onmouseout = function () {
                this.style.cursor = '';
                l.style.borderLeft = "0px solid #000";
            }
            var x0, _left;
            el.onmousedown = l.onmousedown = function () {
                if (!_lines.MainLine) return;
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
                        /*
                        if (v < 20) {
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
                        }
                        */
                        xr.setValue();
                    }
                }, function () {
                    _drag = false;
                    l.style.borderLeft = "0px solid #000";
                    xr.style.visibility = 'hidden';
                })
            }

            xr.setValue = function (v) {
                if (v == undefined) {
                    xv.innerHTML = getTimeVal(xv.pos * dt + _lines.MainLine.Begin, 1);
                    timeEl.innerHTML=getTimeSpan();
                    return;
                }
                xv.innerHTML = v;
            }
            xr.getValue = function () { return xv.pos; }
            xr.reset = function () {
                xv.pos = pos;
                this.style.left = left_ - adx / 2 -15+ pos + 'px';
                xv.innerHTML = getTimeVal(pos * dt + _lines.MainLine.Begin, 1);
                timeEl.innerHTML=getTimeSpan();
            }
            xr.setCenter=function(){
            	pos=xv.pos=w/2;
            	this.style.left = left_ - adx / 2 -15+ pos + 'px';
                xv.innerHTML = getTimeVal(pos * dt + _lines.MainLine.Begin, 1);
                timeEl.innerHTML=getTimeSpan();
            }
            xr.show = function (b) { 
            	l.style.visibility = xv.style.visibility = el.style.visibility = b? 'visible' : 'hidden';
            }

            return xr;
        },
        createYRuler: function (pos) {
        	function setTop(){
            	if(window._selYRuler){
            		_selYRuler.style.zIndex=2;
                }
            	_selYRuler=yr;
            	yr.style.zIndex=3;
            }
        	
            var yr = document.createElement("div");
            with (yr.style) {
                width = w + left_ + 8 + 'px'; height = 14 + 'px'; overflow = "hidden"; position = "absolute";
                left = left_ + 'px'; top = top_ - 7 + pos + 'px'; textAlign = "center"; fontSize = 12 + 'px';
                visibility = 'hidden'; zIndex = 2;
            }
            _owner.appendChild(yr);

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
                width = w + 6 + 'px'; height = '0px';
                left = '0px'; top = '6px'; borderBottom = "3px solid #000";
                visibility = 'visible';
            }
            yr.appendChild(l);
            el.onmouseover = l.onmouseover = function () {
                this.style.cursor = 'row-resize';
                l.style.borderTop = "1px solid #000";
            }
            el.onmouseout = l.onmouseout = function () {
                this.style.cursor = '';
                l.style.borderTop = "0px solid #000";
            }
            var y0, _top;
            el.onmousedown = l.onmousedown = function () {
                if (!_lines.MainLine) return;
                yr.style.visibility = 'visible';
                bindEvent(function (evt) {
                    y0 = evt.clientY;
                    _top = y0 - yv.pos;
                    if (evt.button == left_btn) _drag = true;
                    setTop();
                }, function (evt) {
                    if (_drag) {
                        var dy = evt.clientY - y0;
                        var v = yv.pos + dy;
                        if (_lines.IsTile) {
                            if (v < tileTop) {
                                yv.pos = tileTop;
                                yr.style.top = tileTop + top_ - 6 + 'px';
                                y0 = _top + tileTop;
                            }
                            else if (v <= tileTop + tileHeight) {
                                yv.pos = v;
                                yr.style.top = parseInt(yr.style.top) + dy + 'px';
                                y0 = evt.clientY;
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
                                y0 = evt.clientY;
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
            }

            yr.setValue = function (v) {
                if (v == undefined) {
                    yv.innerHTML =
                    _lines.IsTile ?
                    _lines.MainLine.GetFmtVal(_lines.MainLine.Max - (_lines.MainLine.Max - _lines.MainLine.Min) / tileHeight * (yv.pos - tileTop), 10) :
                    _lines.MainLine.GetFmtVal(_lines.MainLine.Max - dv * yv.pos, 10);
                    valueEl.innerHTML=getValueSpan();
                    return;
                }
                yv.innerHTML = v;
            }
            yr.getValue = function () { return yv.pos; }
            yr.reset = function () {
                yv.pos = pos;
                var ty = top_ - 6 + pos;
                this.style.top = _lines.IsTile ? pos == 20 ? tileTop + ty + 'px' : tileTop + tileHeight - 20 + top_ - 6 + 'px' : ty + 'px';
                this.setValue();
            }
            yr.show = function (b) { 
            	l.style.visibility = yv.style.visibility = el.style.visibility = b? 'visible' : 'hidden';
            }

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
            _owner.appendChild(el);

            var leftBorder = document.createElement("div"), dw = 3;
            with (leftBorder.style) {
                overflow = "hidden"; position = "absolute";
                width = dw * 2 + 'px'; height = h + 'px';
                left = left_ - dw + 'px'; top = top_ + 'px';
                visibility = 'visible';
            }
            _owner.appendChild(leftBorder);
            var rightBorder = document.createElement("div");
            with (rightBorder.style) {
                overflow = "hidden"; position = "absolute";
                width = dw * 2 + 'px'; height = h + 'px';
                left = left_ + w + 4 - dw + 'px'; top = top_ + 'px';
                visibility = 'visible';
            }
            _owner.appendChild(rightBorder);

            var _left, _dt, _pos;
            leftBorder.onmouseover = function () {
                if (_drag || window._IsPlay) return;
                this.style.cursor = 'col-resize';
                el.style.left = left_ - dw + 'px';
                el.style.display = '';
                _pos = 0;
            }
            rightBorder.onmouseover = function () {
                if (_drag || window._IsPlay) return;
                this.style.cursor = 'col-resize';
                el.style.left = left_ + w - dw + 'px';
                el.style.display = '';
                _pos = 20;
            }
            leftBorder.onmouseout = rightBorder.onmouseout = function () {
                if (!_drag) el.style.display = 'none';
            }
            leftBorder.onmousedown = rightBorder.onmousedown = function () {
                if (!_lines.MainLine||window._IsPlay) return;
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
                })
            }

            el.onchange_ = null;
            el.onmove_ = null;
            return el;
        }
    }
    //成员变量
    _chart = null; //绘图边界+网格线+坐标值
    var xRuler1, //X标尺
        xRuler2, //X标尺
        yRuler1, //Y标尺
        yRuler2, //Y标尺
        border; //时间边界
    //方法
    this.Update = function (isHidePoint) {
    	if(isHidePoint==undefined||isHidePoint)_chart.hidePoint();
    	
        adt = (_lines.MainLine.End - _lines.MainLine.Begin) / 10;
        dt = (_lines.MainLine.End - _lines.MainLine.Begin) / w;
        var dv1 = _lines.MainLine.End - _lines.MainLine.Begin;
//        if (dv1 > 31536000000) timeFormat = 'Y-m-d';
//        else if (dv1 > 86400000) timeFormat = 'm-d H:i';
//        else if (dv1 > 10000) timeFormat = 'H:i:s';
//        else timeFormat = 'i:s.u';
//        if (dv1 > 31536000000) timeFormat = 'Y-m-d';
//        else if (dv1 > 86400000) timeFormat = 'Y-m-d H:i';
//        else timeFormat = 'Y-m-d H:i:s';

        adv = (_lines.MainLine.Max - _lines.MainLine.Min) / 10;
        dv = (_lines.MainLine.Max - _lines.MainLine.Min) / h;

        _chart.setOrigin();
        _chart.updateTime(_lines.MainLine.Begin);
        _chart.updateVal(_lines.MainLine.Max);

        xRuler1.reset();
        xRuler2.reset();
        yRuler1.reset();
        yRuler2.reset();
    }
    this.Move = function (dx, dy) {
        _chart.hidePoint();
        _chart.move(dx, dy);
        
        xRuler1.setValue();
        xRuler2.setValue();
        yRuler1.setValue();
        yRuler2.setValue();
    }
    this.SetColor = function (color) {
        _chart.yCoord.setColor(color);
    }
    this.Clear = function () {
        var nodes = _chart.xCoord.childNodes;
        for (var i = 0; i < nodes.length; i++) nodes[i].innerHTML = '';
        nodes = _chart.yCoord.childNodes;
        for (i = 0; i < nodes.length; i++) nodes[i].innerHTML = '';
        xRuler1.setValue('');
        xRuler2.setValue('');
        yRuler1.setValue('');
        yRuler2.setValue('');
        _chart.hidePoint();
    }
    this.CreateYCoord = function (aid, atop, aheight) {
        _chart.hidePoint();
        _chart.newYCoord(aid, atop, aheight);
    }
    this.DelYCoord = function () {
        _chart.delYCoord();
    }
    this.UpdateYRuler = function (atop, aheight) {
        if (aheight == undefined) {
            tileHeight = h / _lines.ShowCount;
            tileTop = atop * tileHeight;
        }
        else {
            tileTop = atop;
            tileHeight = aheight;
        }
        yRuler1.reset();
        yRuler2.reset();
    }
    this.SelectLine = function (isSel) {
        isSel_ = isSel;
        if (isSel_) {
            _chart.style.borderStyle = 'dashed';
        }
        else {
            _chart.style.borderStyle = 'solid';
        }
    }
    this.Resize = function (aw,ah) {
    	w = _owner.clientWidth - left_ * 2;
        h = _owner.clientHeight - top_-20;
//        w = aw-offLeft_ - left_ * 2;
//        h = ah-155-offTop_ - top_ * 2;
        adx = w / 10;
        ady = h / 10;
        offLeft_ = getOffsetLeft(_owner) + 1;
        offTop_ = getOffsetTop(_owner) + 3;
        this.Width = w;
        this.Height = h;
        if (_lines.IsTile) {
            var bh = h / _lines.ShowCount;
            tileTop = bh * tileTop / tileHeight;
            tileHeight = bh;
        }
        _owner.innerHTML = '';
        init();
        if (_lines.MainLine) {
            this.SetColor(_lines.MainLine.getColor());
            this.Update(); 
        }
    }
    this.ShowOrigin = function (info,data) {
        closeAllTip()
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
	    ctx.MoveTo = function (x, y) { ctx.moveTo(x + oleft_, y) }
	    ctx.LineTo = function (x, y) { ctx.lineTo(x + oleft_, y) }
	    ctx.DrawPoint=function(x, y, color){
	    	ctx.strokeStyle = ctx.fillStyle = color;
	    	ctx.beginPath();
	    	if(Ext.isIE8m)ctx.arc(x + oleft_, y, 1, 0, Math.PI*2);
	    	ctx.arc(x + oleft_, y, 2, 0, Math.PI*2);
	    	ctx.stroke();
	    	ctx.fill();
	    }
	    ctx.StrokeText = function (str, x, y, color) {
	        if (color == undefined) color = '#000';
	        var el = document.createElement("div");
	        x = x * 1 + oleft_ - 40;
	        y = y * 1;
	        el.style.cssText = 'position:absolute;overflow:hidden;text-align:center;font-size:12px;left:' + x + 'px;top:' + y + 'px;width:80px;height:14px;color:' + color;
	        el.innerHTML = str;
	        oc.appendChild(el);
	    }
	    ctx.strokeStyle = '#000';
	    ctx.lineWidth = 1;
	    ctx.MoveTo(0, 0);
    	ctx.LineTo(0, oh);
    	ctx.MoveTo(0, oh);
        ctx.LineTo(ow, oh);
        ctx.stroke();
        //ctx.StrokeText(new Date(ot1).format('i:s.u'),0,oh);
        ctx.StrokeText(_lines.MainLine.GetFmtVal(omax),-20,2,info.color);
        ctx.StrokeText(_lines.MainLine.GetFmtVal(omin),-20,oh-18,info.color);
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
    }
    this.GetXRulerInfo = function(){
    	return {x1:xRuler1.getValue(),x2:xRuler2.getValue()};
    }
    this.ShowStats = function(ty){
    	closeAllTip();
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
		        },/* {
		            header: "类型",
		            dataIndex: 'type',
		            menuDisabled: true,
		            width:50
		        }, {
		            header: "平均值",
		            dataIndex: 'aver',
		            menuDisabled: true,
		            width:80
		        },*/ {
		            header: "最小值",
		            dataIndex: 'min',
		            menuDisabled: true,
		            width:80
		        }, {
		            header: "最大值",
		            dataIndex: 'max',
		            menuDisabled: true,
		            width:80
		        }, {
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
    	_STATS.show();
    }
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
    }
    this.setCenter=function(){
    	xRuler1.setCenter();
    }

    function init() {
        //绘图边界+网格线+坐标值
        _chart = Canvas.createChart();
        _chart.onselected_ = function (ax, ay, aw, ah) {
            _cmd.Add({
                Type: '放大曲线',
                Begin: ax,
                End: ax + aw,
                Max: ay,
                Min: ay + ah
            });
        }
        _chart.onmove_ = function (dx, dy) {
            _cmd.Add({ Type: '移动曲线', moved: false, dx: dx, dy: dy });
        }
        _chart.onmoved_ = function (dx, dy) {
            _cmd.Add({ Type: '移动曲线', moved: true });
        }
        _chart.onselmove_ = function (dy) {
            _lines.SelMove(dy);
        }
        _chart.onwheel_ = function (fx) {
            if (fx == -1) _cmd.Add({ Type: '放大当前曲线' });
            else _cmd.Add({ Type: '缩小当前曲线' });
        }
        _chart.ontiye_=function(ax1,ax2,ay1,ay2){
        	_cmd.Add({ Type: '曲线剔野', x1: ax1, x2: ax2, y1: ay1, y2: ay2});
        }
        //X标尺
        xRuler1 = Canvas.createXRuler(20,'#f00');
        xRuler2 = Canvas.createXRuler(w - 20,'#00f');
        //Y标尺
        yRuler1 = Canvas.createYRuler(20);
        yRuler2 = Canvas.createYRuler(h - 20);
        //时间边界
        border = Canvas.createBorder();
        border.onmove_ = function (fx, x1) {
            _cmd.Add({ Type: '开始压缩曲线',fx:fx,x:x1});
        }
        border.onchange_ = function () {
            _cmd.Add({ Type: '压缩曲线'});
        }
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
        _owner.onmousedown = function (e) {
        	//mxc add 每次点击的时候，将是否需要执行剔野置为false;
        	searchNeedTiye = false;
        	//mxc add 记录当前鼠标点击位置
        	xValue = e.clientX;
        	yValue = e.clientY;
            downfn(e || event);
            if (_owner.setCapture) _owner.setCapture();
            else if(window.captureEvents){//xjt-15.5.19
            	window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
            }
        }
        _owner.onmousemove = function (e) { movefn(e || event) }
        if (_owner.releaseCapture) {
            _owner.onmouseup = function (e) {
                _owner.releaseCapture();
                upfn(e || event);
                _owner.onmousedown = null;
                _owner.onmousemove = null;
                _owner.onmouseup = null;
            }
        }
        else if(window.captureEvents){//xjt-15.5.19        	
            _owner.onmouseup=window.onmouseup = function (e) {
            	window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
            	//mxc add 若松开鼠标时，位置和按下鼠标时位置一样则不需要剔野
            	  if(e.clientX != xValue || e.clientY != yValue){
            		  if(Ext.getCmp('SJTY').pressed){
            			  searchNeedTiye = true;
            		  }
            	  }
                upfn(e || event);
                window.onmousedown = null;
                window.onmousemove = null;
                window.onmouseup = null;
            }
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
    function closeInfoTip(){
    	if(window._TIP)_TIP.close();
    }
    function closeAllTip(){
    	if(window._YSSJT)_YSSJT.close();
    	if(window._STATS)_STATS.close();
    }
    /*var refreshFn=function(v){
    	var now=_WebTime.getMs(),
			bt1=Ext.Date.format(new Date(now-v * 60000),'Y-m-d H:i'),
			bt2=Ext.Date.format(new Date(now*1),'Y-m-d H:i');
		beginCmp.setValue(bt1);
		endCmp.setValue(bt2);
		if(lineStore.getCount()>0)_cmd.Add({ Type: '压缩曲线', Begin:bt1.getTime(), End:bt2.getTime()});
    };
    var popMenu = new Ext.menu.Menu({
        items: [{
        	itemId:'lock',
        	text:'统计分析',
        	icon:'img/unlock.ico'
        },'-',{
        	itemId:'unlock',
        	text:'按照游标区间统计',
        	icon:'img/lock.ico'
        }],
        setDisabled_:function(b){
        	var items=this.query('menuitem');
        	for(var i=3;i<items.length;i++){
        		items[i].setDisabled(b);
        	}
        },
    	listeners:{
    		'click':function(menu, item, e, eOpts){   
    			switch(item.text){
        		 	case '锁定':
        		 		item.hide();
	                	menu.down('#unlock').show();  
	                	lock = true;
	                	menu.setDisabled_(true);
	                	if(el.onlock_)el.onlock_(id,getX(),getY(),aw);
        		 		break;
    		 		case '解锁':
        		 		item.hide();
	                	menu.down('#lock').show();
	                	lock = false;
	                	menu.setDisabled_(false);
	                	menu.show_=true;
	                	if(el.onunlock_)el.onunlock_(id);
        		 		break;    		 		
		 		}
    		}
    	}
    });*/
}
