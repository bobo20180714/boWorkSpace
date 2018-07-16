/*
 *绘图数管理器
 */

Ext.define('js.DrawDataManage',{
	//2016.3.29 孟祥超修改
	constructor:function(_dr,_li,canvas){
		return new DrawDataManage(_dr,_li,canvas);
	}
});	

function DrawDataManage(_dr,_li,canvas) {
    var buf = [], 
    	dataBuf=[],
    	amax, 
    	amin, 
    	aheight, 
    	data=[],
    	getX1=function(i){//返回数组buf的索引号为i的x1坐标值
	    	if(i<0)
	    		return buf[0].type>0?buf[0].x1:buf[0].x;
	    	else if(i<buf.length)
	    		return buf[i].type>0?buf[i].x1:buf[i].x;
			else
				return buf[buf.length-1].type>0?buf[buf.length-1].x1:buf[buf.length-1].x;    	
        },
        getX2=function(i){//返回数组buf的索引号为i的x2坐标值
        	if(i<0)
        		return buf[0].type>0?buf[0].x2:buf[0].x;
        	else if(i<buf.length)
        		return buf[i].type>0?buf[i].x2:buf[i].x;
    		else
    			return buf[buf.length-1].type>0?buf[buf.length-1].x2:buf[buf.length-1].x;        	
        },
        getY1=function(i){//返回数组buf的索引号为i的y1坐标值
        	if(i<0)
        		return buf[0].type>0?buf[0].y1<buf[0].y2?buf[0].y1:buf[0].y2:buf[0].y;
        	else if(i<buf.length)
        		return buf[i].type>0?buf[i].y1<buf[i].y2?buf[i].y1:buf[i].y2:buf[i].y;
    		else
    			return buf[buf.length-1].type>0?buf[buf.length-1].y1<buf[buf.length-1].y2?buf[buf.length-1].y1:buf[buf.length-1].y2:buf[buf.length-1].y;        	
        },
        getY2=function(i){//返回数组buf的索引号为i的y2坐标值
        	if(i<0)
        		return buf[0].type>0?buf[0].y1>buf[0].y2?buf[0].y1:buf[0].y2:buf[0].y;
        	else if(i<buf.length)
        		return buf[i].type>0?buf[i].y1>buf[i].y2?buf[i].y1:buf[i].y2:buf[i].y;
    		else
    			return buf[buf.length-1].type>0?buf[buf.length-1].y1>buf[buf.length-1].y2?buf[buf.length-1].y1:buf[buf.length-1].y2:buf[buf.length-1].y;         	
        },
        tiye = [],
        tag = [],
        me=this;
    this.Max = null;
    this.Min = null;
    this.ClearBuffer = function(){
    	buf = [];
    };
    this.Clear = function () {
        this.Max = null;
        this.Min = null;
        buf = [];
        tiye = [];
        for (var i = 0; i < tag.length; i++) {
        	Ext.getDom(tag[i].id).destroy();
        }
        tag = [];
    };
    //景科文新增
    this.DataBufAdd=function(data){
    	dataBuf=data;
    };
    this.getDataBuf=function(){
    	return dataBuf;
    };
    this.Add = function (data) {
        buf.push(data);
    };
    this.AddData = function (data) {
        this.Max = data.max;
        this.Min = data.min;
        buf = data.items;       
    };
    this.AddRealData = function(type,data,point,dt,isMove) {
    	var dx=Math.ceil((data.End.getTime()-point.t)/dt);
    	var bx=_dr.Width-dx;
    	if(isMove||isMove==undefined)moveDx(-dx);
		else{
			if(buf.length>0&&type<2){
				if(buf[buf.length-1].type>0)buf.push({type:1,x1:bx,y1:buf[buf.length-1].y2,x2:bx,y2:point.y});
				else buf.push({type:1,x1:bx,y1:buf[buf.length-1].y,x2:bx,y2:point.y});
			}			
		}
        switch(type){
        	case 0:       		
        		buf=buf.concat(trans(data.Data,point,bx,dt));      		
        		break;
    		case 1:
        		buf=buf.concat(transLine(data.Data,point,bx,dt));
        		break;
    		case 2:
        		buf=buf.concat(transPoint(data.Data,point,bx,dt));
        		break;  
        }  
    };
    this.TransY = function (max, min) {        
        initTrans(max,min);
        for (var i = 0; i < buf.length; i++) {
            var e = buf[i];
            switch (e.type) {
                case 0:
//                    if (e.y >= max || e.y <= min) continue;
                    data.push({ type: 0, x: e.x, y: f(e.y) });
                    break;
                case 1:
                    if (e.x1 == e.x2) {
//                        if (e.y1 >= max || e.y2 <= min) continue;
                        var x1 = e.x1;
                        data.push({ type: 1, x1: x1, y1: f(e.y1), x2: x1, y2: f(e.y2) });
                    }
                    else {
//                        if (e.y1 >= max || e.y1 <= min) continue;
                        var y1 = f(e.y1);
                        data.push({ type: 1, x1: e.x1, y1: y1, x2: e.x2, y2: y1 });
                    }
                    break;
                case 2:
                    //if (e.y2 >= max || e.y1 <= min) continue;
                    data.push({ type: 2, x1: e.x1, y1: f(e.y1), x2: e.x2, y2: f(e.y2)  });
                    break;
                case 9:
                	data.push({ type: 9, x1: e.x1, y1: e.y1, x2: e.x2, y2: e.y2 });
                	break;
            }
        }
        return data;
    };
    this.TransLine = function (max, min) {
        initTrans(max,min);
        for (var i = 0; i < buf.length; i++) {
            var e = buf[i];
            if(e.type!=1) continue;
            data.push({ type: 1, x1: e.x1, y1: f(e.y1), x2: e.x2, y2: f(e.y2) });
        }
        return data;
    };
    this.TransPoint = function (max, min) {
    	initTrans(max,min);
    	var PoData=[];
        for (var i = 0; i < buf.length; i++) {
            var e = buf[i];
//            if(e.type!=3) continue;
            PoData.push({ type: 3, x: e.x, y: f(e.y)});
        }
        return PoData;
    };
    this.IsExpand = function (max, min, abegin, aend) {
        for (var i = 0; i < buf.length && getX2(i) < abegin; i++);
        for (; i < buf.length && getX1(i) <= aend; i++) {
            if (!(min > getY2(i) || max < getY1(i))) return true;
        }
        return false;
    };
    this.GetTiye = function(t1, t2, tf,limit){
    	if(tf)tiye.push(tf);
    	for (var rst=[],i = 0; i<tiye.length; i++){
    		if(!(tiye[i].t1>t2||tiye[i].t2<t1))
    			rst.push([
			          new Date(tiye[i].t1).format('YmdHisu'),
			          new Date(tiye[i].t2).format('YmdHisu'),
			          tiye[i].v1,
			          tiye[i].v2
		          ].join('&'));
    	} 
    	//xjt-15.5.18
    	/*var v1=Ext.getCmp(src?'rtiye1':'tiye1').getValue(),
			v2=Ext.getCmp(src?'rtiye2':'tiye2').getValue();
    	var v1=null,v2=null;
    	if(limit=='NOPIPE')return rst.join('~');
    	if(limit==undefined){
    		v1=Ext.getCmp('tiye1').getValue();
    		v2=Ext.getCmp('tiye2').getValue();
    	}
    	else if(limit instanceof Array){
    		v1=limit[0];
    		v2=limit[1];
    	}
    	else{
    		v1=Ext.getCmp('rtiye1').getValue();
    		v2=Ext.getCmp('rtiye2').getValue();
    	}
    	if(v1!=''){
    		rst.push([
		          new Date(t1).format('YmdHisu'),
		          new Date(t2).format('YmdHisu'),
		          Number.NEGATIVE_INFINITY,
		          v1
	          ].join('&'));
    		var pipe = {t1:t1,t2:t2,v1:Number.NEGATIVE_INFINITY,v2:v1};
    		tiye.push(pipe);
    	}
    	if(v2!=''){
    		rst.push([
		          new Date(t1).format('YmdHisu'),
		          new Date(t2).format('YmdHisu'),
		          v2,
		          Number.POSITIVE_INFINITY		          
	          ].join('&'));
    		var pipe = {t1:t1,t2:t2,v1:v2,v2:Number.POSITIVE_INFINITY};
    		tiye.push(pipe);
    	}*/
    	//xjt-15.5.18
    	return rst.join('~');
    };
    this.NoTiye=function(){    	
    	tiye.pop();
    };
    this.setNoTiyeEnable=function(src){
    	Ext.getCmp(src?'RTiyeChexiao':'TiyeChexiao').setEnable(tiye.length>0);
    };
    this.GetPointInfo=function(type,x,y,dv){
		switch(type){
        	case 0:    		
        		for (var i = 0; i<buf.length&&getX2(i)<x; i++);
        		for (;i<buf.length&&getX1(i)<=x+8; i++){
        			switch(buf[i].type){
						case 0://点
							if(x<=buf[i].x&&buf[i].x<=x+8&&y-8*dv<=buf[i].y&&buf[i].y<=y)return {x:buf[i].x,y:buf[i].y};
							break;
						case 1:
							if(buf[i].y1==buf[i].y2){//水平线
								if(y-8*dv<=buf[i].y1&&buf[i].y1<=y)return {x:x,y:buf[i].y1+4*dv};
								break;
							}
							else{//垂直线
								if(y-8*dv>getY2(i)||y<getY1(i))break;
								else return {x:buf[i].x1-4,y:y+4*dv};
							}
						case 2://矩形
							if(y-8*dv>getY2(i)||y<getY1(i))break;
							else return {x:x,y:y+4*dv};
					}	
        		}       		
        		break;
    		case 1:
    			for (var i = 0; i<buf.length; i++){
    				switch(buf[i].type){
	    				case 3://点    景科文新增
							if(Math.abs(x-buf[i].x)<=8&&Math.abs(y-buf[i].y)<=8)return {x:buf[i].x,y:buf[i].y};
							break;
	    				case 9://矩形    景科文新增
	    					if(x>=buf[i].x1&&x<=buf[i].x2&&y>=buf[i].y1&&y<=buf[i].y2)return {x:x,y:y};
	    					break;
    				}
    			}
        		break;
    		case 2:
        		var preSelects=[];
    			for (var i = 0; i<buf.length; i++){
    				if(buf[i].x-4<=x&&x<=buf[i].x+4)
    					preSelects.push(buf[i]);
    			}
    			if(preSelects.length==0)break;
    			for(var j=0;j<preSelects.length;j++){
    				if(preSelects[j].y-4*dv<=y&&y<=preSelects[j].y+4*dv)
    					return {x:preSelects[j].x,y:preSelects[j].y,t:preSelects[j].t};
    			}    			
        		break;   
        }
		return null;		
    };
    this.GetDataPointInfo=function(ax,ay,index){
    	for(var i=0;i<dataBuf.length;i++){
    		switch(dataBuf[i].type){
				case 3://点    
					if(Math.abs(ax-dataBuf[i].x)<=8&&Math.abs(ay-(index*25+12))<=8){
						 var t = new Date(dataBuf[i].t).format('Y-m-d H:i:s.u');
						 return {'type':3,'x':dataBuf[i].x,'index':index,'t':t,'v':dataBuf[i].v,'typeName':dataBuf[i].typeName};
					}
					break;
				case 9://矩形    
					if(ax>=dataBuf[i].x1&&ax<=dataBuf[i].x2&&ay>=(index*25+12-3)&&ay<=(index*25+12+3)){
						var t = new Date(dataBuf[i].t).format('Y-m-d H:i:s.u');
						return {'type':9,'x1':dataBuf[i].x1,'x2':dataBuf[i].x2,'index':index,'t':t,'v':dataBuf[i].v,'typeName':dataBuf[i].typeName};
					} 
					break;
			}
    	}
    };
    this.GetStats = function(x1,x2){   	
    	for (var i = 0; i<buf.length&&getX2(i)<x1; i++);
    	var aver=0,
    		max=getY2(i),
    		min=getY1(i),
    		n=1;
		i++;
    	for (;i<buf.length&&getX1(i)<=x2;i++){   		
    		aver+=(getY1(i)+getY2(i))/2;
    		if(max<getY2(i))max=getY2(i);
    		if(min>getY1(i))min=getY1(i);
    		n++;
    	} 
    	return {aver:aver/n,max:max,min:min};
    };
    this.AddTag = function(id){
    	tag.push({id:id,lock:false});
    };
    this.LockTag = function(id,t,v,aw,txt,c){
    	for (var i = 0; i < tag.length; i++) {
    		if(tag[i].id==id)break;
    	}
    	if(t){
    		tag[i].lock=true;
    		tag[i].t=t;
    		tag[i].v=v;
    		tag[i].aw=aw;
    		tag[i].txt=txt;
    		tag[i].c=c;
    	}
    	else tag[i].lock=false;
    };
    this.DelTag = function(id){
    	for (var i = 0; i < tag.length; i++) {
    		if(tag[i].id==id){
    			tag.remove(i);
    			return;
    		}
    	}
    };
    this.SetTagXY = function (t1,t2,v1,v2,top,type){
    	var dt = (t2 - t1) / _dr.Width,
    	 	dv = (v2 - v1) / _dr.Height;
    	 	
	 	if (_li.IsTile) dv *= _li.ShowCount;
    	for (var i = 0; i < tag.length; i++) {
    		if(!tag[i].lock)continue;
    		if(t1<tag[i].t&&tag[i].t<t2&&v1<tag[i].v&&tag[i].v<v2){    			   			
    		var obj=Ext.getDom(tag[i].id);
    		//当绘图区改变时，标注因绘图区重绘而消失问题
        		if(!obj){
        		//2016.3.29 孟祥超修改
        			obj=Ext.create('js.tag',tag[i].c,tag[i].id,tag[i].aw,type,tag[i].txt,tag[i].lock);   
        	    	obj.onlock_=function(id,x,y,aw,txt,c){
        	    		var t = _li.Begin, 
        		    		dt = (_li.End - t) / _dr.Width,
        		    	 	v = _li.Max,
        		    	 	n = _li.IsTile? _li.ShowCount : 1,
        		    	 	dv = (v - _li.Min) / _dr.Height * n;	
        	    		me.LockTag(id,dt*x+t,v-dv*(y-canvas.GetTileTop()),aw,txt,c);
        	    	};
        	    	obj.onunlock_=function(id){
        	    		me.LockTag(id);
        	    	};
        	    	obj.ondel_=function(id){
        	    		me.DelTag(id);
        	    	};
        		}
    			obj.setXY(tag[i].aw,(tag[i].t-t1)/dt,(v2-tag[i].v)/dv+top);
    			obj.show();
    		}
    		else obj.hide();
    	}
    };
    this.SetTagColor=function(color){    	
        for (var i = 0; i < tag.length; i++) {
    		Ext.getDom(tag[i].id).setColor(color);
    	}
    };
    this.GetTagData=function (t1,t2,v1,v2){
    	var tagDatas=[];
    	for (var i = 0; i < tag.length; i++) {
    		if(!tag[i].lock)continue;
    		if(t1<tag[i].t&&tag[i].t<t2&&v1<tag[i].v&&tag[i].v<v2){    			   			
    			tagDatas.push([tag[i].t,tag[i].v,tag[i].aw,tag[i].txt,tag[i].c].join('&'));
    		}
		}
    	return tagDatas.join('~');
    };
    this.Expand = function (max, min, abegin, aend) {
        //TODO:
    };
    this.Compress = function (abegin, aend) {
        var ds = [], scale = (aend - abegin) / _dr.Width, x1, x2, lx = null, cx;
        for (var i = 0; i < data.length; i++) {
            var e = data[i];
            if (lx) {
                cx = e.x || e.x1;
                x1 = (cx - lx) * scale + x2;
                lx = e.x || e.x2;
            }
            else {
                lx = e.x || e.x2;
                cx = e.x || e.x1;
                x1 = abegin + cx * scale;
            }
            switch (e.type) {
                case 0:
                    x2 = x1;
                    break;
                case 1:
                    if (e.y1 == e.y2) {
                        x2 = (e.x2 - e.x1) * scale + x1;
                        ds.push({ type: 1, x1: x1, y1: e.y1, x2: x2, y2: e.y2 });
                    }
                    else {
                        x2 = x1;
                        ds.push({ type: 1, x1: x2, y1: e.y1, x2: x2, y2: e.y2 });
                    }
                    break;
                case 2:
                    x2 = (e.x2 - e.x1) * scale + x1;
                    ds.push({ type: 2, x1: x1, y1: e.y1, x2: x2, y2: e.y2 });
                    break;
            }
        }
        return ds;
    };
    this.DataLineCompress=function(abegin, aend){
    	var ds = [], scale = (aend - abegin) / _dr.Width;
        for (var i = 0; i < dataBuf.length; i++) {
            var e = dataBuf[i];
            switch (e.type) {
            	case 1:
            		ds.push(e);
            	case 3:
            		ds.push({type:3,x:e.x * scale+abegin});
            		break;
            	case 9:
            		ds.push({type:9,x1:e.x1 * scale+abegin,x2:e.x2 * scale+abegin});
            		break;
            }
        }
        return ds;
    };
    function initTrans(max,min){
    	data = [];
    	amax = max;
        amin = min;
        aheight = _li.IsTile ? _dr.Height / _li.ShowCount : _dr.Height;
    }
    function f(y){
    	return aheight * (amax - y) / (amax - amin);
    }
    function moveDx(dx){
    	for(var i in buf){
    		if(buf[i].type>0){
	            buf[i].x1+=dx;
	            buf[i].x2+=dx;
    		}
    		else buf[i].x+=dx;
		}
		while(buf.length>0&&getX2(0)<0)buf.shift();
    }
    function trans(data,point,bx,dt){
    	var rst=[],lx=bx,y1,y2;
    	y1=y2=point.y;
		for (var i = 0; i < data.length; i++) {
			var x=Math.ceil((data[i].time.getTime()-point.t)/dt)+bx;
			var y=data[i].val;
			if(lx==x){
				if(y1>y)y1=y;
				if(y2<y)y2=y;
				if(i==data.length-1)rst.push({type:1,x1:x,y1:y1,x2:x,y2:y2});
			}
			else{
				if(y1==y2){
					if(y1==y){
						rst.push({type:1,x1:lx,y1:y,x2:x,y2:y});
					}
					else{
						rst.push({type:1,x1:lx,y1:y1,x2:x,y2:y1});
						rst.push({type:1,x1:x,y1:y,x2:x,y2:y1});
					}
				}
				else{
					rst.push({type:1,x1:lx,y1:y1,x2:lx,y2:y2});
					rst.push({type:1,x1:lx,y1:y,x2:x,y2:y});//???        						
				}
				lx=x;
				y1=y2=y;
			}
		}
		return rst;
    }
    function transLine(data,point,bx,dt){
    	var rst=[],lx=bx,ly=point.y;
    	for (var i = 0; i < data.length; i++) {
    		var x=Math.ceil((data[i].time.getTime()-point.t)/dt)+bx;
			var y=data[i].val;
			rst.push({type:1,x1:lx,y1:ly,x2:x,y2:y});
			lx=x;
			ly=y;
    	}
    	return rst;
    }
    function transPoint(data,point,bx,dt){
    	var rst=[];
    	for (var i = 0; i < data.length; i++) {
    		var x=Math.ceil((data[i].time.getTime()-point.t)/dt)+bx;
			var y=data[i].val;
			rst.push({type:0,x:x,y:y});
    	}
    	return rst;
    }
    /**
     * 15-12-28周星陆添加管道剔野数据设置接口
     * 添加管道剔野数据
     * pipeTiyes.begin //起始时间
     * pipeTiyes.end //截止时间
     * pipeTiyes.lower //下限
     * pipeTiyes.upper //上限
     */
    this.setPipeTiyes = function (pipeTiyes){
    	if(pipeTiyes.lower){
    		var pipe = {t1:pipeTiyes.begin,t2:pipeTiyes.end,v1:Number.NEGATIVE_INFINITY,v2:pipeTiyes.lower};
    		tiye.push(pipe);
    	}
    	if(pipeTiyes.upper){
    		var pipe = {t1:pipeTiyes.begin,t2:pipeTiyes.end,v1:pipeTiyes.upper,v2:Number.POSITIVE_INFINITY};
    		tiye.push(pipe);
    	}
    };
}