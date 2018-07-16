//用于AJAX通讯
Ext.Ajax.timeout = 600000;
var Ajax={
	send: function (url, data, callback, asyn) {
        Ext.Ajax.request({
		    url: url,
		    params: data,
		    method : 'POST',  
            async: asyn==undefined||asyn,
		    success: function(response,options ){
		        if(callback!=undefined){
		        	var text=response.responseText;
		        	if(!text)return;
		        	var json=Ext.JSON.decode(text);
		        	if(json.err!=undefined){
		        		Ext.Msg.show({ title: 'Java调试信息', msg: json.err, buttons: Ext.Msg.OK });
		        	}else{
		        		callback(json,options.params);
		        	}		        	
		        }		        			        	
		    }
		});
    }
};
//返回指定格式字符串
Date.prototype.format=function(fmt){
	return Ext.Date.format(this,fmt);
};
//得到标准时间:Y-m-d H:i:s.u
Date.toStr = function (ms) {
    return new Date(ms*1).format('Y-m-d H:i:s.u');
};
//格式化Y值表示
function fmtVal(v, max, min) {
	function youxw(){//获取有效位
        for(var rst=new Number(v).toFixed(p),i=0,n=0,isFirst=true;i<rst.length;i++){
        	if(rst[i]=='-'||rst[i]=='.')continue;
        	if(isFirst){
        		if(rst[i]*1>0)isFirst=false;//第一个非0
        		else continue;//0打头
        	}
        	n++;
        }
        return n;
    }
	
    if ((v + '').indexOf('e') > 0)//科学计数法
        return (v + '').length <= info.numWidth ? v : new Number(v).toExponential(info.numWidth - 2 - (v + '').length + (v + '').indexOf('e'));
    for (var i = 10, p = 0, j = ((v > 0 ? Math.floor(v) : Math.ceil(v)) + '').length; j < info.numWidth; i /= 10, p++, j++) {
        if (max - min >= i) break;
    }
    if (j > info.numWidth) {//科学计数法
        v = new Number(v).toExponential();
        return (v + '').length <= info.numWidth ? v : new Number(v).toExponential(info.numWidth - 2 - (v + '').length + (v + '').indexOf('e'));
    }
    else {//小数形式
    	for(;youxw()<3&&p<8;p++);//有效位至少为3
        return new Number(v).toFixed(p);
    }
}
//标题处理函数
function _focus(o){
	if(o.value=="编辑标题"){
		o.value="";
		o.style.color="#000";
	}
}
function _blur(o){
	if(o.value.length==0){
		o.value="编辑标题";
		o.style.color="#808080";
	}
}
//标题处理函数
Ext.onReady(function () {
	var chartHeight=document.documentElement.clientHeight - 100;
    Ext.create('Ext.container.Viewport', {
        layout: 'border',
        bodyStyle:'background:-color:#fff;',
        items: [
            {
                region: 'north',
                height: 70,
                border:false,
                bodyStyle: 'text-align:center;',
                html: '<br><input type="text" style="width:800px;height:50px;border-width:0px;font-size:40px;text-align:center;background:transparent;line-height:50px;color:#B0B0B0;" onfocus="_focus(this)" onblur="_blur(this)" value="编辑标题"/>'         
            }, {
                region: 'center',
                border:false,
                height: chartHeight,
                id: 'Chart'
            }/*,{
            	region:'east',
            	border:false,
            	width:150,
            	bodyStyle:'padding:60px 0px;',
            	html:'<div style="width:145px;height:'+(chartHeight-120)+'px;border:1px solid #99BBE8;padding:5px;">aaa</div>'
            }*/
        ]
    });

    owner = navigator.userAgent.toLowerCase().match(/msie\s[34567]\.\d/)?Ext.getDom('Chart').childNodes[1].childNodes[0].firstChild : Ext.getDom('Chart').childNodes[0].childNodes[0].firstChild;
    var canvas = document.createElement("canvas");
    w = owner.clientWidth - 270, h = document.documentElement.clientHeight - 110, left_ = 80, top_ = 10;
    owner.appendChild(canvas);
    canvas.width = owner.clientWidth;
    canvas.height = owner.clientHeight;
    if (window.isVml) canvas = G_vmlCanvasManager.initElement(canvas);
    var ctx = canvas.getContext("2d");
    ctx.MoveTo = function (x, y) { ctx.moveTo(x + left_, y + top_); };
    ctx.LineTo = function (x, y) { ctx.lineTo(x + left_, y + top_); };
    ctx.FillRect = function (x, y, aw, ah) { ctx.fillRect(x + left_, y + top_, aw, ah); };
    ctx.DrawPoint=function(x, y){
    	ctx.beginPath();
    	ctx.arc(x + left_, y + top_, 5, 0, Math.PI*2);
    	if(Ext.isIE8m)ctx.stroke(true);
    	else ctx.stroke();
    	ctx.fill();
    };
    ctx.StrokeText = function (str, x, y, color) {
        if (color == undefined) color = '#000';
        var el = document.createElement("div");
        x = x * 1 + left_ - (str.indexOf('-')>-1?70:80);
        y = y * 1 + top_;
        el.style.cssText = 'position:absolute;overflow:hidden;text-align:center;font-size:12px;left:' + x + 'px;top:' + y + 'px;width:'+(str.indexOf('-')>-1?w/10+30:120)+'px;height:14px;color:' + color+';white-space:nowrap;';
        el.innerHTML = str;
        owner.appendChild(el);
    };
    ctx.StrokeLegend=function(str,x,y){
    	var el = document.createElement("div");
        x = x * 1 + left_;
        y = y * 1 + top_;
        el.style.cssText = 'position:absolute;overflow:hidden;padding:5px;font-family:"楷体","楷体_GB2312";font-size:18px;left:' + (x+1) + 'px;top:' + y + 'px;width:155px;height:'+(h/10-2)+'px;color:#000;';
        el.innerHTML = str;
        owner.appendChild(el);
    };
    ctx.RoundRect=function (x, y, w, h, r) {
    	ctx.beginPath();
    	ctx.moveTo(x+r, y);
    	ctx.arcTo(x+w, y, x+w, y+h, r);
    	ctx.arcTo(x+w, y+h, x, y+h, r);
    	ctx.arcTo(x, y+h, x, y, r);
    	ctx.arcTo(x, y, x+w, y, r);
    	ctx.stroke();
    };
    parseUrl();   
    var ml = info.getMainLine();
    var dt = (ml.End - ml.Begin) / w;
    var adt = (ml.End - ml.Begin) / 10;
    var dv = (ml.Max - ml.Min) / h;
    var adv = (ml.Max - ml.Min) / 10;
    if(info.IsTile)dv*=info.Lines.length;
    ctx.strokeStyle = '#808080';
    ctx.beginPath();
    for (var i = 0; i <= 10; i++) {
        var x = w / 10 * i - (ml.Begin - info.Left) / dt;
        if(x>=0){
        	ctx.MoveTo(x, 0);
            ctx.LineTo(x, h);
            //ctx.StrokeText(ml.End - ml.Begin > 86400000 ? new Date(info.Left * 1 + adt * i).format('m-d H:i') : new Date(info.Left * 1 + adt * i).format('H:i:s'), x, h + 4);
            if(i%2==0){
            	ctx.StrokeText(new Date(info.Left * 1 + adt * i).format('Y-m-d H:i:s'), x, h + 4);
            } 
        }
        var y = h / 10 * i + (ml.Max - info.Top) / dv;
        if(y>=0){        	
            ctx.MoveTo(0, y);
            ctx.LineTo(w, y);
            if (!info.IsTile)
                ctx.StrokeText(fmtVal(info.Top - adv * i, ml.Max, ml.Min), -20, y - 10, ml.Color);
        }        
    }
    ctx.stroke();
    var ah = info.IsTile ? h / info.Lines.length : h;
    var url = 'drawdata/drawdata.edq';
    for (i = 0; i < info.Lines.length; i++) {
        if (info.IsTile) {
            ctx.StrokeText(fmtVal(info.Lines[i].Max, info.Lines[i].Max, info.Lines[i].Min), -20, ah * i - 10, info.Lines[i].Color);
            ctx.StrokeText(fmtVal(info.Lines[i].Min, info.Lines[i].Max, info.Lines[i].Min), -20, ah * (i + 1) - 22, info.Lines[i].Color);
        }
        if(info.Lines[i].ParamType==1){
        	url = 'drawdata/customdata.edq';
        }else{
        	url = 'drawdata/drawdata.edq';
        }
        
        Ajax.send(url,{
            type: info.Lines[i].Type,
            width: w,
            tmId: info.Lines[i].TmId,
            begin: Date.toStr(info.Lines[i].Begin),
            end: Date.toStr(info.Lines[i].End),
            dataType:info.Lines[i].DataType,
            tiye:info.Lines[i].Tiye,
            mid:info.Lines[i].Mid,
            Max: info.Lines[i].Max,
            Min: info.Lines[i].Min,
            Color: info.Lines[i].Color,
            Width: info.Lines[i].Width,
            Height: ah,
            Top: info.IsTile ? ah * i : 0,
    		IsLast:i>=info.Lines.length-1,
    		TagData:info.Lines[i].TagData,
    		Begin:info.Lines[i].Begin,
    		End:info.Lines[i].End,
    		formula:info.Lines[i].Formula,
    		show:info.Lines[i].Show
        }, function (json, opts) {
        	if(!json.drawData)return;
            var drawData = new DrawData(opts.Height, opts.Top);
            drawData.AddData(json.drawData);
            if(opts.show=='true'){
            	var ds = drawData.Trans(opts);
                draw(ds, opts);
            }            
            if(opts.TagData.length>0)addTag(opts);
            if(opts.IsLast){
            	ctx.strokeStyle = '#000';
                ctx.lineWidth = 2.5;
                ctx.strokeRect(left_, top_, w, h); 
            }
        });
        ctx.fillStyle=info.Lines[i].Color;
        ctx.FillRect(w+20,h/10*(1.5+i)-6,12,12);
        ctx.StrokeLegend(info.Lines[i].Name+'['+info.Lines[i].Code+']',w+32,h/10*(1+i));
    }        
    ctx.strokeStyle = '#EE7600';
    ctx.lineWidth = 1.5;
    if (window.isVml) ctx.strokeRect(left_+w+14, top_+h/10-10, 170, h/10*8+20); 
    else ctx.RoundRect(left_+w+14, top_+h/10-10, 170, h/10*8+20,10);   
    function addTag(args){
    	var dt=(args.End-args.Begin)/w,
    		dv=(args.Max-args.Min)/h;
    	if(info.IsTile) dv *= info.Lines.length;
    	var tags = args.TagData.split('~');
    	for(var i=0;i<tags.length;i++){
    		var tag=tags[i].split('&'),
    			x=(tag[0]-args.Begin)/dt,
    			y=(args.Max-tag[1])/dv+args.Top,
        		el = document.createElement("div"),
    			txt = document.createElement("div"),
    			aw=tag[2];
    		if(aw.match(/l/)){
        		if(x+140>w){
    	    		x-=158;
    	    		aw=aw.replace('l','r');
    	    	}
    	    	else{
    	    		x-=6;
    	    	}
        	}
        	else{
        		if(x<140){
        			x-=6;
        			aw=aw.replace('r','l');
        		}
        		else{
        			x-=158;
        		}
        	}
        	if(aw.match(/t/)){
        		if(y+84>h){
    	    		y-=98;
    	    		aw=aw.replace('t','b');
    	    	}
    	    	else{
    	    		y-=6;
    	    	}
        	}
        	else{
        		if(y<84){
        			y-=6;
        			aw=aw.replace('b','t');
        		}
        		else{
        			y-=98;
        		}
        	}
    		with (el.style) {
                position = "absolute";
                left = left_+x+'px';
                top = top_+y+'px';
                width = '160px';
                height = '100px';
                background = 'url(img/tip-'+aw+'.ico) no-repeat center center';     
                zIndex = 2;
            }    
            owner.appendChild(el);
            with (txt.style) {
                margin = "13px 19px";
                wordWrap = 'break-word';
                wordBreak = 'break-all';
                height = '73px';
                overflowY = 'auto';  
                color = tag[4];
                padding = '5px';
            }  
            txt.innerHTML=tag[3];
            el.appendChild(txt);
    	}
    }
    function draw(ds, args) {
        ctx.beginPath();
        for (var i = 0; i < ds.length; i++) {
            ctx.strokeStyle = ctx.fillStyle = args.Color;
            ctx.lineWidth = args.Width;
            var e = ds[i];
            switch (ds[i].type) {
                case 0:
                    ctx.MoveTo(ds[i].x, ds[i].y);
                    ctx.LineTo(ds[i].x, ds[i].y + 1);
                    break;
                case 1:
                    ctx.MoveTo(ds[i].x1, ds[i].y1);
                    ctx.LineTo(ds[i].x2, ds[i].y2);
                    break;
                case 2:
                    ctx.stroke();
                    ctx.FillRect(ds[i].x1, ds[i].y1, ds[i].x2 - ds[i].x1, ds[i].y2 - ds[i].y1);
                    ctx.beginPath();
                    break;
                case 3:
			    	ctx.DrawPoint(ds[i].x,ds[i].y);
                	break;
            }
        }
        ctx.stroke();
    }
    function parseUrl() {
        info = Ext.urlDecode(window.location.search.substr(1));
        info.IsTile = info.IsTile == 'true';
        var lines = info.Lines.split('_-_');
        info.Lines = [];
        for (var j = 0; j < lines.length; j++) {
            var line = lines[j].split('|');
            info.Lines.push({
                TmId: line[0],
                Begin: line[1],
                End: line[2],
                Max: line[3],
                Min: line[4],
                Color: line[5],
                Width: line[6],
                Type:line[7],
                ID:line[8],
                Name:line[9],
                Code:line[10],
                DataType:line[11],
                Tiye:line[12],
                TagData:line[13],
                Mid:line[14],
            	ParamType:line[15],
            	Formula:line[16],
            	Show:line[17]
            });
        }
        info.getMainLine = function () {
            for (var i = 0; i < info.Lines.length; i++) {
                if (info.Lines[i].ID == info.MID)
                    return info.Lines[i];
            }
        };
    }
});
function DrawData(aheight, atop) {
    var buf = [], amax, amin;
    this.AddData = function (data) {
        buf = data.items; 
    };
    this.Trans = function (args){
    	var ds;
    	switch(args.type*1){
        	case 0:
        		ds = this.TransY(args.Max, args.Min);
        		break;
    		case 1:
        		ds = this.TransLine(args.Max, args.Min);
        		break;
    		case 2:
    		case 3:
        		ds = this.TransPoint(args.Max, args.Min);
        		break;  
        }
        return ds;
    };
    this.TransY = function (max, min) {
        var data = [];
        amax = max;
        amin = min;
        for (var i = 0; i < buf.length; i++) {
            var e = buf[i];
            switch (e.type) {
                case 0:
                    if (e.y >= max || e.y <= min) continue;
                    data.push({ type: 0, x: e.x, y: f(e.y) });
                    break;
                case 1:
                    if (e.x1 == e.x2) {
                        if (e.y1 >= max || e.y2 <= min) continue;
                        var x1 = e.x1;
                        if(e.y2>max)e.y2=max;
                        if(e.y1<min)e.y1=min;
                        data.push({ type: 1, x1: x1, y1: f(e.y1), x2: x1, y2: f(e.y2) });
                    }
                    else {
                        if (e.y1 >= max || e.y1 <= min) continue;
                        var y1 = f(e.y1);
                        data.push({ type: 1, x1: e.x1, y1: y1, x2: e.x2, y2: y1 });
                    }
                    break;
                case 2:
                    if (e.y2 >= max || e.y1 <= min) continue;
                    if(e.y2>max)e.y2=max;
                    if(e.y1<min)e.y1=min;
                    data.push({ type: 2, x1: e.x1, y1: f(e.y1), x2: e.x2, y2: f(e.y2) });
                    break;
            }
        }
        return data;
    };
    this.TransLine = function (max, min) {
        var data = [];
        amax = max;
        amin = min;
        for (var i = 0; i < buf.length; i++) {
            var e = buf[i];
            data.push({ type: 1, x1: e.x1, y1: f(e.y1), x2: e.x2, y2: f(e.y2) });
        }
        return data;
    };
    this.TransPoint = function (max, min) {
        var data = [];
        amax = max;
        amin = min;
        for (var i = 0; i < buf.length; i++) {
            var e = buf[i];
            if(e.y-min>=0&&max-e.y>=0)
            	data.push({ type: 3, x: e.x, y: f(e.y) });
        }
        return data;
    };
    function f(y) {
        var ay=aheight * (amax - y) / (amax - amin) + atop;
        if(ay>h)ay=h;
        if(ay<0)ay=0;
    	return ay;
    }
}