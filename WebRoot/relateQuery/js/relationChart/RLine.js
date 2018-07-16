/*
 *曲线
 */


Ext.define('js.relationChart.RLine',{
	constructor:function(rec,_dr,_li,isCond){
		return new RLine(rec,_dr,_li,isCond);
	}
});	
 
function RLine(rec,_dr,_li,isCond) {
        var tmID = rec.get('Num'),//rec.get('TmId'),
        canvas = Ext.create('js.relationChart.RCanvas'),
        drawData = Ext.create('js.DrawDataManage',_dr,_li,canvas),
        me = this,
        dt,
        stack,
        hp,
        tp;
        
      //xjt-2015.1.24
//        var sb=Ext.getCmp("sb");
//    	if(!sb)sb=parent.Ext.getCmp("sb");

    this.Push = function (type) {
        if (hp > restoreNum - 1) {
            stack.shift();
            hp--;
        }
        stack.length = hp + 1;
        stack.push({
            Begin: me.Begin,
            End: me.End,
            Max: me.Max,
            Min: me.Min,
            Color: rec.get('Color'),
            Weight: rec.get('Weight'),
            Show: rec.get('Show')
        });
        if (type != '撤销') hp++;
        tp = -1;
    };
    this.IsEqual = function () {
        return stack[hp].Max == me.Max && stack[hp].Min == me.Min&& stack[hp].Begin == me.Begin && stack[hp].End == me.End;
    };
    this.Clear = function () {
        stack = [];
        tp = hp = -1;
    };
    function withdraw() {
        tp = hp + 1;
        return stack[hp--];
    }
    function restore() {
        hp = tp - 1;
        return stack[tp++];
    }

    this.Begin = rec.get('Begin').getTime();
    this.End = rec.get('End').getTime();
    this.Max = isCond?rec.get('Max')*1:null;
    this.Min = isCond?rec.get('Min')*1:null;
    this.Completed = false;
    this.ID = rec.get('Id');
    this.getTmId=function(){
    	return rec.get('TmId');//tmID;
    };
    this.getShow=function(){
    	return rec.get('Show');
    };
    this.getColor=function(){
    	return rec.get('Color');
    };
    this.getWidth=function(){
    	return rec.get('Width');
    };
    this.getSatId=function(){
    	return rec.get('Mid');
    	//return rec.get('SatId');
    };
    this.getPort=function(){
    	return rec.get('Port');
    };
    this.getType=function(){
    	return rec.get('Type');
    };
    this.getNum=function(){
    	return rec.get('Num');
    };
    this.getCode=function(real){
    	return real!=undefined?rec.get("Code"):rec.get("Code").replace(/\(.*\)/g, "");
    };
    this.getTiye=function(tiye){
    	//if(tiye!=undefined)tiye=tiye[rec.get('Id')];
    	return drawData.GetTiye(this.Begin, this.End,null,tiye?tiye:'R');
    };
    this.getName=function(){
    	return rec.get('Name');
    };
    this.getDataType=function(){
    	return rec.get('DataType');
    };
    this.getTagData=function(){
    	return drawData.GetTagData(this.Begin,this.End,this.Min,this.Max);
    };
    this.getPrecision=function(){
    	return rec.get('Precision');
    };
    this.getParamType=function(){
    	return rec.get('ParamType');
    };
    this.getFormula=function(){
    	return rec.get('Formula');
    };
    canvas.SetColor(rec.get('Color'));
    var dts = new Date().getTime();
    var url = 'drawdata/getdrawdata';
    var sendObj;
    if(rec.get('ParamType')!=1){
    	sendObj = {
	        type: rec.get('Type'),
	        width: _dr.Width,
	        tmId: tmID,
	        begin: Date.toStr(this.Begin),
	        end: Date.toStr(this.End),
	        Begins:this.Begin,
	        dataType:rec.get('DataType'),
	        tiye:this.getTiye(),
	        mid:this.getSatId(),
            start:Date.toStr(this.Begin)
	    };
    }else{
    	url = 'drawdata/customdata.edq';
    	sendObj={
    			type: rec.get('Type'),
    		    width: _dr.Width,
		        begin: Date.toStr(this.Begin),
		        end: Date.toStr(this.End),
		        tiye:this.getTiye(),
		        formula:rec.get('Formula')
    	};
    }
    //var el = Ext.getCmp("centertabpanel").getEl();
	//if(el)el.mask("正在数据交互中……");
    _li.mask();
    Ajax.send(url,sendObj, function (json, opts) {
    	var dte = new Date().getTime();
        if(!json.drawData){
        	_li.unmask();
        	drawData.ClearBuffer();
        	//mxc add 无数据 设置main为false
        	rec.set('Main', false);
        	rec.set('Efficiency', '无数据');
//        	sb.setText();
        	return;
        }
          rec.set('Efficiency', (dte - dts)+'ms');
        drawData.AddData(json.drawData);
        if (_li.IsTile) {
            if(isCond){
                if(rec.get('Main'))_li.SetMainAxis(me.ID);
            }
            else{
            	setRange();
            	if (!_li.MainLine)_li.SetMainAxis(me.ID);				  
            }  
            _dr.DelYCoord();
            _li.ReAllPos(getLeft(opts.Begins));
        }
        else {
            if(isCond){
                if(rec.get('Main'))_li.SetMainAxis(me.ID);
                canvas.Clear();
            }
            else{
            	
            	setRange();
            	if (!_li.MainLine)_li.SetMainAxis(me.ID);
            	else {
            		//_li.CompressedLine(this.Begin,this.End,_li.MainLine.ID);
            	}
	            update();
	            //canvas.Clear(getLeft(opts.Begins));
	            
            }                                   
            draw(trans());                     
        }
//        sb.setText();
        me.Completed = true;
//        _li.unmask();
    });
    this.IsExpand = function (max, min, begin, end) {
        var v = this.Max, dv = (v - this.Min) / _dr.Height;
        if (_li.IsTile) dv *= _li.ShowCount;
        return drawData.IsExpand(v - dv * max, v - dv * min, begin, end);
    };
    this.GetPointInfo=function(x,y){   	
		var v = this.Max, dv = (v - this.Min) / _dr.Height;
		if (_li.IsTile) dv *= _li.ShowCount;
		var ret=drawData.GetPointInfo(rec.get('Type'),x,v - dv * y,dv);
		if(ret){						
			switch(rec.get('Type')){
				case 0:
					ret.v=this.GetFmtVal(ret.y,10);
					var t = this.Begin, dt = (this.End - t) / _dr.Width;
					ret.t=Date.toStr(ret.x* dt + t);
					break;
				case 2:					
					ret.v=new Number(ret.y).toFixed(this.getPrecision());
					ret.t=Date.toStr(ret.t);
					break;
			}
			ret.y=Math.floor((v-ret.y)/dv);
			ret.name=rec.get('Code');
			ret.id=tmID;
			ret.color=rec.get('Color');
		}		
		return ret;
    };
    this.ExpandLine = function (arg) {
        this.Completed = false;
        //计算开始结束时间
        var t = this.Begin, dt = (this.End - t) / _dr.Width;
        this.Begin = arg.Begin * dt + t;
        this.End = arg.End * dt + t;
        //2015-12-29 zxl添加放大曲线限制在100毫秒以上
        if(this.End - this.Begin < 100 ){
//        	sb.setText();
        	return;
        }
        //计算最大最小值
        if(!_relation_lines.IsTile){//xjt-15.5.19
        	var v = this.Max, dv = (v - this.Min) / _dr.Height;
            if (_li.IsTile) dv *= _li.ShowCount;
            this.Max = v - dv * arg.Max;
            this.Min = v - dv * arg.Min;
        }        
        var url = 'drawdata/getdrawdata';
        var sendObj;
        if(rec.get('ParamType')!=1){
        	sendObj = {
    	        type: rec.get('Type'),
    	        width: _dr.Width,
    	        tmId: tmID,
    	        begin: Date.toStr(this.Begin),
    	        end: Date.toStr(this.End),
    	        Begins:this.Begin,
    	        dataType:rec.get('DataType'),
    	        tiye:this.getTiye(),
    	        mid:this.getSatId(),
                start:Date.toStr(this.Begin)
    	    };
        }else{
        	url = 'drawdata/customdata.edq';
        	sendObj={
        			type: rec.get('Type'),
        		    width: _dr.Width,
    		        begin: Date.toStr(this.Begin),
    		        end: Date.toStr(this.End),
    		        tiye:this.getTiye(),
    		        formula:rec.get('Formula')
        	};
        }
        
        //
        //var el = Ext.getCmp("centertabpanel").getEl();
    	//el.mask("正在数据交互中……");
        _li.mask();
        Ajax.send(url,sendObj, function (json) {//处理绘图
        	if(!json.drawData){
        		drawData.ClearBuffer();
        		_li.unmask();
        		return;
        	}
        	drawData.AddData(json.drawData);
            draw(trans());
            me.Completed = true;
//            sb.setText();
//            _li.unmask();
        });
        //清除曲线、复位
        canvas.Clear();
        //更新最大最小值
        update('tv');
    };
    this.CompressedLine = function (t1,t2) {
        this.Completed = false;
        if (t1!=undefined){
        	this.Begin=t1;
        	this.End=t2;
        	update('t');
        }       
        var url = 'drawdata/getdrawdata';
        var sendObj;
        if(rec.get('ParamType')!=1){
        	sendObj = {
    	        type: rec.get('Type'),
    	        width: _dr.Width,
    	        tmId: tmID,
    	        begin: Date.toStr(this.Begin),
    	        end: Date.toStr(this.End),
    	        Begins:this.Begin,
    	        dataType:rec.get('DataType'),
    	        tiye:this.getTiye(),
    	        mid:this.getSatId(),
                start:Date.toStr(this.Begin)
    	    };
        }else{
        	url = 'drawdata/customdata.edq';
        	sendObj={
        			type: rec.get('Type'),
        		    width: _dr.Width,
    		        begin: Date.toStr(this.Begin),
    		        end: Date.toStr(this.End),
    		        tiye:this.getTiye(),
    		        formula:rec.get('Formula')
        	};
        }
        var dts = new Date().getTime();
        //
        //var el = Ext.getCmp("centertabpanel").getEl();
    	//el.mask("正在数据交互中……");
        _li.mask();
        Ajax.send(url,sendObj, function (json) {
        	canvas.Clear();
        	if(!json.drawData){
        		drawData.ClearBuffer();
            	_li.unmask();
//            	sb.setText();
            	//mxc add 无数据 设置main为false
            	rec.set('Main', false);
            	rec.set('Efficiency', '无数据');
            	return;
            }
        	var dte = new Date().getTime();
        	rec.set('Efficiency', (dte - dts)+'ms');
        	drawData.AddData(json.drawData);
        	if(!me.Max && !me.Min){
        		setRange();
        		update();
        	}
        	if (!_li.MainLine){
        		_li.SetMainAxis(me.ID);
        	}
            var ds = trans();
            //canvas.Clear();
            draw(ds);
            me.Completed = true;
           /* sb.setText();
            _li.unmask();*/
        });
    };
    this.Move = function (dx, dy) {
        this.Completed = false;
        var dt = ((this.End - this.Begin) / _dr.Width) * dx;
        this.Begin -= dt;
        this.End -= dt;
        var dv = ((this.Max - this.Min) / _dr.Height) * dy;
        if (_li.IsTile) dv *= _li.ShowCount;
        this.Max += dv;
        this.Min += dv;
        canvas.Move(dx, dy);
        update('tv');
        drawData.SetTagXY(this.Begin,this.End,this.Min,this.Max,canvas.GetTileTop(),1);
    };
    this.Moved = function (dt) {
    	var url = 'drawdata/getdrawdata';
        var sendObj;
        if(rec.get('ParamType')!=1){
        	sendObj = {
    	        type: rec.get('Type'),
    	        width: _dr.Width,
    	        tmId: tmID,
    	        begin: Date.toStr(this.Begin),
    	        end: Date.toStr(this.End),
    	        Begins:this.Begin,
    	        dataType:rec.get('DataType'),
    	        tiye:this.getTiye(),
    	        mid:this.getSatId(),
                start:Date.toStr(this.Begin)
    	    };
        }else{
        	url = 'drawdata/customdata.edq';
        	sendObj={
        			type: rec.get('Type'),
        		    width: _dr.Width,
    		        begin: Date.toStr(this.Begin),
    		        end: Date.toStr(this.End),
    		        tiye:this.getTiye(),
    		        formula:rec.get('Formula')
        	};
        }
        
        //
        //var el = Ext.getCmp("centertabpanel").getEl();
    	//el.mask("正在数据交互中……");
        _li.mask();
        Ajax.send(url,sendObj, function (json, opts) {///处理绘图
	        if(!json.drawData){
	        	drawData.ClearBuffer();
	        	_li.unmask();
	        	return;
	        }
            drawData.AddData(json.drawData);
            var ds = trans();
            canvas.Clear(getLeft(opts.Begins));
            draw(ds);
            me.Completed = true;
          /*  sb.setText();
            _li.unmask();*/
        });
    };
    this.Zoom = function () {
        this.Completed = false;
        setRange();
        var ds = trans();
        canvas.Clear();
        draw(ds);
        update();
        this.Completed = true;
    };
    this.SetMainAxis = function (top) {
    	if(top===true)drawData.setNoTiyeEnable(1);
        if(top==undefined)top=true;
        if(rec.data.Main!=top){
        	rec.data.Main=top;
        	rec.commit();
        }  
        canvas.SetZindex(top);
        _Rrecord=rec;
    };
    this.SetColor = function (color) {
        this.Completed = false;        
        canvas.SetColor(color);
        if (_li.IsTile) {
            Ext.getDom('MAX' + this.ID).style.color = Ext.getDom('MIN' + this.ID).style.color = color;
        }
        var ds = trans();
        canvas.Clear();
        draw(ds);
        drawData.SetTagColor(color);
        this.Completed = true;
    };
    this.SetWidth = function (width) {
        this.Completed = false;       
        canvas.SetWeight(width);
        var ds = trans();
        canvas.Clear();
        draw(ds);
        this.Completed = true;
    };
    this.ShowLine = function (show) {
        canvas.Show(show);
    };
    this.SetMaxMinVal = function (max, min) {
        this.Completed = false;
        setRange(max, min);
        update();
        var ds = trans();
        canvas.Clear();
        draw(ds);
        this.Completed = true;
    };
    this.DelLine = function () {
        canvas.Destroy();
        drawData.Clear();
        drawData.ClearBuffer();
    };
    this.RePos = function (left, top, height) {
        this.Completed = false;
        canvas.RePos(left, top, height);
        update();
        draw(trans());
        this.Completed = true;
    };
    this.MoveUpDown = function (fx) {
        this.Completed = false;
        var dv = (this.Max - this.Min) / 20 * fx;
        setRange(this.Max - dv, this.Min - dv);
        update();
        var ds = trans();
        canvas.Clear();
        draw(ds);
        this.Completed = true;
    };
    this.ZoomYLine = function (fx) {
        this.Completed = false;
        var dv = (this.Max - this.Min) / 20;
        if (this.Max - this.Min * 1 - dv * fx * 2 > getPrecise()) {//7位有效
            setRange(this.Max - dv * fx, this.Min * 1 + dv * fx);
            update();
            var ds = trans();
            canvas.Clear();
            draw(ds);
        }
        this.Completed = true;
    };
    this.SelMove = function (dy) {
        var dv = ((this.Max - this.Min) / _dr.Height) * dy;
        if (_li.IsTile) dv *= _li.ShowCount;
        this.Max += dv;
        this.Min += dv;
        update();
        var ds = trans();
        canvas.Clear();
        draw(ds);
    };
    this.Withdraw = function (isWithdraw) {
        this.Completed = false;
        var data = isWithdraw ? withdraw() : restore();
        var isLocalData = this.Begin == data.Begin && this.End == data.End;       
        this.Max = data.Max;
        this.Min = data.Min;
        this.Begin=data.Begin;
        this.End=data.End;
        update('tv');
        rec.set('Color', data.Color);
        rec.set('Weight', data.Weight);
        rec.set('Show', data.Show);
        rec.commit();
        canvas.SetColor(data.Color);
        canvas.SetWeight(data.Weight);
        canvas.Show(data.Show);        
        if (isLocalData) {
            var ds = trans();
            canvas.Clear();
            draw(ds);
            this.Completed = true;
        }
        else {
            this.Begin =data.Begin;
        	this.End =data.End;
//        	sb.showBusy();
        	var url = 'drawdata/getdrawdata';
            var sendObj;
            if(rec.get('ParamType')!=1){
            	sendObj = {
        	        type: rec.get('Type'),
        	        width: _dr.Width,
        	        tmId: tmID,
        	        begin: Date.toStr(this.Begin),
        	        end: Date.toStr(this.End),
        	        Begins:this.Begin,
        	        dataType:rec.get('DataType'),
        	        tiye:this.getTiye(),
        	        mid:this.getSatId(),
                    start:Date.toStr(this.Begin)
        	    };
            }else{
            	url = 'drawdata/customdata.edq';
            	sendObj={
            			type: rec.get('Type'),
            		    width: _dr.Width,
        		        begin: Date.toStr(this.Begin),
        		        end: Date.toStr(this.End),
        		        tiye:this.getTiye(),
        		        formula:rec.get('Formula')
            	};
            }
            
            //
//            var el = Ext.getCmp("centertabpanel").getEl();
//        	el.mask("正在数据交互中……");
            Ajax.send(url,sendObj, function (json) {
            	if(!json.drawData){
            		drawData.ClearBuffer();
            		el.unmask();
            		return;
            	}
            	drawData.AddData(json.drawData);
                var ds = trans();
                canvas.Clear();
                draw(ds);
                me.Completed = true;
//                sb.setText();
//                el.unmask();
            });
        }
    };
    this.CompressingLine = function (fx,x) {
        var begin,end,dt1;
        switch (fx) {
            case 'L':
                dt1=dt/(_dr.Width-x);
                this.Begin = this.End - dt1 * _dr.Width;              
                begin=x;
                end=_dr.Width;
                break;
            case 'R':
            	dt1=dt/x;
            	this.End = this.Begin + dt1 * _dr.Width;
            	begin=1;
                end=x;                
                break;
        }
        update('t');
        var ds = drawData.Compress(begin, end);
        canvas.Clear();
        draw(ds);
    };
    this.BeforeCompress=function(){
    	dt=this.End-this.Begin;
    };
    this.GetFmtVal = function (v, ps) {
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
    	
        if(v==undefined||v==null)return '';
        var nums = v > 0 ? numWidth - 1 : numWidth - 2,
            vs = new Number(v).toExponential() + '',
            exp = vs.split('e')[1] * 1 + 1;
        if (exp > nums || exp < -(nums - 1)) {
            var w = ps == undefined ? numWidth : numWidth + 1;
            return vs.length <= w ? vs : new Number(v).toExponential(w - 2 - vs.length + vs.indexOf('e'));
        }
        var dv = me.Max - me.Min;
        for (var i = 10, p = 0, j = exp; j <= nums; i /= 10, p++, j++) {
            if (dv >= i) break;
        }        
        for(;youxw()<3&&p<8;p++);//有效位至少为3
        var ret = ps == undefined ? new Number(v).toFixed(p) : new Number(v).toFixed(p + 1);
        return ret.replace(/\.{1}0*$/g,'');
    };
    this.Resize = function () {
        this.Completed = false;
        canvas = Ext.create('js.relationChart.RCanvas',this.getShow());
        var url = 'drawdata/getdrawdata';
        var sendObj;
        if(rec.get('ParamType')!=1){
        	sendObj = {
    	        type: rec.get('Type'),
    	        width: _dr.Width,
    	        tmId: tmID,
    	        begin: Date.toStr(this.Begin),
    	        end: Date.toStr(this.End),
    	        Begins:this.Begin,
    	        dataType:rec.get('DataType'),
    	        tiye:this.getTiye(),
    	        mid:this.getSatId(),
                start:Date.toStr(this.Begin)
    	    };
        }else{
        	url = 'drawdata/customdata.edq';
        	sendObj={
        			type: rec.get('Type'),
        		    width: _dr.Width,
    		        begin: Date.toStr(this.Begin),
    		        end: Date.toStr(this.End),
    		        tiye:this.getTiye(),
    		        formula:rec.get('Formula')
        	};
        }
        
        //
//        var el = Ext.getCmp("centertabpanel").getEl();
//    	if(el)el.mask("正在数据交互中……");
        Ajax.send(url,sendObj, function (json,opts) {
            if (!json.drawData) {
            	drawData.ClearBuffer();
//            	if(el)el.unmask();
            	return;
            }
            drawData.AddData(json.drawData);
            var x = getLeft(opts.begins);
            if (_li.IsTile) _li.ReAllPos(x);
            else {
                canvas.Clear(x);
                draw(trans());               
            }
            me.Completed = true;
//            if(el)el.unmask();
        });
    };
    this.SetLineType=function(){
    	this.Completed = false;
//        sb.showBusy();
        var url = 'drawdata/getdrawdata';
        var sendObj;
        if(rec.get('ParamType')!=1){
        	sendObj = {
    	        type: rec.get('Type'),
    	        width: _dr.Width,
    	        tmId: tmID,
    	        begin: Date.toStr(this.Begin),
    	        end: Date.toStr(this.End),
    	        Begins:this.Begin,
    	        dataType:rec.get('DataType'),
    	        tiye:this.getTiye(),
    	        mid:this.getSatId(),
                start:Date.toStr(this.Begin)
    	    };
        }else{
        	url = 'drawdata/customdata.edq';
        	sendObj={
        			type: rec.get('Type'),
        		    width: _dr.Width,
    		        begin: Date.toStr(this.Begin),
    		        end: Date.toStr(this.End),
    		        tiye:this.getTiye(),
    		        formula:rec.get('Formula')
        	};
        }
        
//        var el = Ext.getCmp("centertabpanel").getEl();
//    	el.mask("正在数据交互中……");
        Ajax.send(url,sendObj, function (json,opts) {
            if(!json.drawData){
            	drawData.ClearBuffer();
//            	el.unmask();
            	return;
            }
            drawData.AddData(json.drawData);
            var ds=trans();            
            canvas.Clear();
            draw(ds);
            me.Completed = true;
//            sb.setText();
//            el.unmask();
        });
    };
    var realPoint;
    this.SetRealShow=function(){
    	realPoint=null;
    };
    this.RealShow=function(realData){
    	if(!realData.Begin)return;
    	dt=(this.End-this.Begin)/_dr.Width;
    	this.Begin=realData.End.getTime()-(this.End-this.Begin);
		this.End=realData.End.getTime();	
		update('t');
        if(!realPoint){//第一次读取          
        	var url = 'drawdata/getdrawdata';
            var sendObj;
            if(rec.get('ParamType')!=1){
            	sendObj = {
        	        type: rec.get('Type'),
        	        width: _dr.Width,
        	        tmId: tmID,
        	        begin: Date.toStr(this.Begin),
        	        end: Date.toStr(this.End),
        	        Begins:this.Begin,
        	        dataType:rec.get('DataType'),
        	        tiye:this.getTiye(),
        	        mid:this.getSatId(),
                    start:Date.toStr(this.Begin)
        	    };
            }else{
            	url = 'drawdata/customdata.edq';
            	sendObj={
            			type: rec.get('Type'),
            		    width: _dr.Width,
        		        begin: Date.toStr(this.Begin),
        		        end: Date.toStr(this.End),
        		        tiye:this.getTiye(),
        		        formula:rec.get('Formula')
            	};
            }
            
            //
            var el = Ext.getCmp("centertabpanel").getEl();
        	el.mask("正在数据交互中……");
            Ajax.send(url,sendObj, function (json) {
	            if(!json.drawData){
	            	drawData.ClearBuffer();
	            	el.unmask();
	            	return;
	            }
	            drawData.AddData(json.drawData);
	            handleRealData(realData,{t:realData.Begin.getTime(),y:realData.Data[0].val},false);
	            el.unmask();
	        });	        
        }
        else handleRealData(realData);
    };
    this.Tiye = function(x1, x2, y1, y2){
    	var t = this.Begin, 
    		dt = (this.End - t) / _dr.Width,
    	 	v = this.Max, 
    	 	dv = (v - this.Min) / _dr.Height;
		if (_li.IsTile) dv *= _li.ShowCount;
    	var tiye=drawData.GetTiye(this.Begin, this.End, x1?{t1:dt*x1+t,t2:dt*x2+t,v1:v-dv*y1,v2:v-dv*y2}:null,x1?'NOPIPE':'R');	
    	drawData.setNoTiyeEnable('R');
    	var url = 'drawdata/getdrawdata';
        var sendObj;
        if(rec.get('ParamType')!=1){
        	sendObj = {
    	        type: rec.get('Type'),
    	        width: _dr.Width,
    	        tmId: tmID,
    	        begin: Date.toStr(this.Begin),
    	        end: Date.toStr(this.End),
    	        Begins:this.Begin,
    	        dataType:rec.get('DataType'),
    	        tiye:tiye,
    	        mid:this.getSatId(),
                start:Date.toStr(this.Begin)
    	    };
        }else{
        	url = 'drawdata/customdata.edq';
        	sendObj={
        			type: rec.get('Type'),
        		    width: _dr.Width,
    		        begin: Date.toStr(this.Begin),
    		        end: Date.toStr(this.End),
    		        tiye:tiye,
    		        formula:rec.get('Formula')
        	};
        }
        
//        var el = Ext.getCmp("centertabpanel").getEl();
//    	el.mask("正在数据交互中……");
        Ajax.send(url,sendObj, function (json,opts) {
            if(!json.drawData){
            	drawData.ClearBuffer();
//            	el.unmask();
            	return;
            }
            drawData.AddData(json.drawData);
            var ds=trans();            
            canvas.Clear();
            draw(ds);
            me.Completed = true;
//            sb.setText();
//            el.unmask();
        });
    };
    this.GetStats = function(x1,x2,tiye){
    	var code=rec.raw.Code.split('('),
    		//stats=drawData.GetStats(x1,x2),
    		t = this.Begin, 
    		dt = (this.End - t) / _dr.Width,
    		t1 = t+dt*x1,
    		t2 = t+dt*x2;
//    	var el = _STATS.down('#grid').getEl();
//    	el.mask("正在数据交互中……");
    	 if(rec.get('ParamType')!=1){
    		 Ajax.send('drawdata/statsinfo.edq',{
    	            tmId: tmID,
    	            begin: Date.toStr(t1),
    	            end: Date.toStr(t2),
    	            dataType:rec.get('DataType'),
    	            mid:this.getSatId(),
    	            tiyes:this.getTiye(tiye)
    	        }, function (json,opts) {
    	            if(!json.stats){
    	            	el.unmask();
    	            	return;
    	            }
    	            
    	            tongjiWin.addGridData({
    		    		'name':rec.raw.Name, 
    		    		'param':code[0].replace(")",""), 
    		    		'code':code[1].replace(")",""), 
    		    		'type':'模拟量', 
    		    		'aver':me.GetFmtVal(json.stats.avg,10), 
    		    		'max':me.GetFmtVal(json.stats.max,10), 
    		    		'min':me.GetFmtVal(json.stats.min,10), 
    		    		'begin':new Date(t1).format('Y-m-d H:i'), 
    		    		'end':new Date(t2).format('Y-m-d H:i')
    				})
    	            
    	           /* _STATS.down('#grid').getStore().add({
    		    		'name':rec.raw.Name, 
    		    		'param':code[0].replace(")",""), 
    		    		'code':code[1].replace(")",""), 
    		    		'type':'模拟量', 
    		    		'aver':me.GetFmtVal(json.stats.avg,10), 
    		    		'max':me.GetFmtVal(json.stats.max,10), 
    		    		'min':me.GetFmtVal(json.stats.min,10), 
    		    		'begin':new Date(t1).format('Y-m-d H:i'), 
    		    		'end':new Date(t2).format('Y-m-d H:i')
    				});*/
//    	            el.unmask();
    	        },false);
    	 }
    	 else{
    		 code=rec.raw.Code.split(':');
    		 Ajax.send('drawdata/customstatsinfo.edq',{
    	            begin: Date.toStr(t1),
    	            end: Date.toStr(t2),
    	            dataType:0,
    	            tiyes:this.getTiye(),
    	            formula:rec.get('Formula')
    	        }, function (json,opts) {
    	            if(!json.stats){
    	            	el.unmask();
    	            	return;
    	            }
    	            
    	            tongjiWin.addGridData({
    		    		'name':rec.raw.Name, 
    		    		'param':code[0], 
    		    		'code':code[1], 
    		    		'type':'模拟量', 
    		    		'aver':me.GetFmtVal(json.stats.avg,10), 
    		    		'max':me.GetFmtVal(json.stats.max,10), 
    		    		'min':me.GetFmtVal(json.stats.min,10), 
    		    		'begin':new Date(t1).format('Y-m-d H:i'), 
    		    		'end':new Date(t2).format('Y-m-d H:i')
    				})
    	            
    	           /* _STATS.down('#grid').getStore().add({
    		    		'name':rec.raw.Name, 
    		    		'param':code[0], 
    		    		'code':code[1], 
    		    		'type':'模拟量', 
    		    		'aver':me.GetFmtVal(json.stats.avg,10), 
    		    		'max':me.GetFmtVal(json.stats.max,10), 
    		    		'min':me.GetFmtVal(json.stats.min,10), 
    		    		'begin':new Date(t1).format('Y-m-d H:i'), 
    		    		'end':new Date(t2).format('Y-m-d H:i')
    				});*/
//    	            el.unmask();
    	        },false);
    	 }
    };
    this.AddTag = function () {
    	var tag=Ext.create('js.relationChart.Rtag',rec.get('Color'));   	 	
    	tag.onlock_=function(id,x,y,aw,txt,c){
    		var t = me.Begin, 
	    		dt = (me.End - t) / _dr.Width,
	    	 	v = me.Max,
	    	 	n = _li.IsTile? _li.ShowCount : 1,
	    	 	dv = (v - me.Min) / _dr.Height * n;	    	 	
			
    		drawData.LockTag(id,dt*x+t,v-dv*(y-canvas.GetTileTop()),aw,txt,c);
    	};
    	tag.onunlock_=function(id){
    		drawData.LockTag(id);
    	};
    	tag.ondel_=function(id){
    		drawData.DelTag(id);
    	};
    	drawData.AddTag(tag.id);
    };
    this.HistoryShow=function(endTime){
    	this.Begin=endTime-(this.End-this.Begin);
		this.End=endTime;	
		update('t');
		
    	Ajax.send('historydata',{
            type: rec.get('Type'),
            width: _drawarea.Width,
            tmId: tmID,
            begin: Date.toStr(this.Begin),
            end: Date.toStr(this.End),
            dataType:rec.get('DataType')
            //tiye: tiye
        }, function (json,opts) {
            if(!json.drawData){
            	drawData.ClearBuffer();
            	return;
            }
            drawData.AddData(json.drawData);
            var ds=trans();            
            canvas.Clear();
            draw(ds);
        });
    };
    this.RealShow2=function(){
    	var dt=(this.End-this.Begin)/_drawarea.Width;
    	Ajax.send('realdata',{
            type: rec.get('Type'),
            width: _drawarea.Width,
            tmId: tmID,
            begin: Date.toStr(this.End-dt),
            frequency: dt
            //tiye: tiye
        }, function (json,opts) {
            if(!json.realDrawData)return;
            var dt=opts.frequency*json.realDrawData.shift;
            me.Begin+=dt;
            me.End+=dt;
            update('t');
            drawData.AddData(json.realDrawData);
            var ds=trans();            
            canvas.Clear();
            draw(ds);
        });
    };
    var timerCount;
    this.ResetTimer=function(){
    	timerCount=this.End-this.Begin;
    };
    this.RealShow=function(now){
    	//_drawarea.Width;
    	var dt=this.End-this.Begin;
    	if(dt>43200000)dt=43200000;
    	this.Begin=now-dt;
		this.End=now;	
		update('t');
		if(timerCount)timerCount++;
		else timerCount = this.End-this.Begin;
		if(timerCount*1000>dt/_relation_drawarea.Width){
			var url = 'drawdata/getdrawdata';
	        var sendObj;
	        if(rec.get('ParamType')!=1){
	        	sendObj = {
	    	        type: rec.get('Type'),
	    	        width: _dr.Width,
	    	        tmId: tmID,
	    	        begin: Date.toStr(this.Begin),
	    	        end: Date.toStr(this.End),
	    	        Begins:this.Begin,
	    	        dataType:rec.get('DataType'),
	    	        tiye:this.getTiye(),
	    	        mid:this.getSatId(),
	                start:Date.toStr(this.Begin)
	    	    };
	        }else{
	        	url = 'drawdata/customdata.edq';
	        	sendObj={
	        			type: rec.get('Type'),
	        		    width: _dr.Width,
	    		        begin: Date.toStr(this.Begin),
	    		        end: Date.toStr(this.End),
	    		        tiye:this.getTiye(),
	    		        formula:rec.get('Formula')
	        	};
	        }
	        
	        //
	        //var el = Ext.getCmp("centertabpanel").getEl();
	    	//el.mask("正在数据交互中……");
	        Ajax.send(url,sendObj, function (json,opts) {
	        	if(!json.drawData){
	        		drawData.ClearBuffer();
	        		//el.unmask();
	        		return;
	        	}
	        	drawData.AddData(json.drawData);
	            var ds=trans();            
	            canvas.Clear();
	            draw(ds);
	            //el.unmask();
	        });
			timerCount=0;
		}		
    };
    this.NoTiye=function(){
    	drawData.NoTiye();
    	drawData.setNoTiyeEnable('R');
    	this.Tiye();
    };
    this.setRecord=function(arec){
    	rec=arec;
    };
    
    function handleRealData(realData,point,isMove){
    	drawData.AddRealData(rec.get('Type'),realData,realPoint||point,dt,isMove);
    	var ds=trans();            
        canvas.Clear();
        draw(ds);
        realPoint={
        	t:realData.End.getTime(),
        	y:realData.Data[realData.Data.length-1].val
        };
    }
    
    function trans(){
    	var ds;
    	switch(rec.get('Type')){
        	case 0:
        		ds = drawData.TransY(me.Max, me.Min);
        		break;
    		case 1:
        		ds = drawData.TransLine(me.Max, me.Min);
        		break;
    		case 2:
        		ds = drawData.TransPoint(me.Max, me.Min);
        		break;  
        }
        return ds;
    }

    function draw(datas,top) {//top-用于平铺时处理标注
        canvas.SetColor(rec.get('Color'));
        canvas.SetWeight(rec.get('Width'));
        canvas.Begin();
        for (var i = 0; i < datas.length; i++) {
            var data = datas[i];
            switch (data.type) {
                case 0:
                    canvas.Point(data.x, data.y);
                    break;
                case 1:
                    canvas.Line(data.x1, data.y1, data.x2, data.y2);
                    break;
                case 2:
                    canvas.DrawLine();
                    canvas.DrawRect(data.x1, data.y1, data.x2, data.y2);
                    canvas.Begin();
                    break;
                case 3:
                    canvas.DrawPoint(data.x, data.y);
                    break;
            }
        }
        canvas.DrawLine();
        drawData.SetTagXY(me.Begin,me.End,me.Min,me.Max,canvas.GetTileTop(),1);
    }

    function update(type) {
        switch(type){
        	case 't':
        		rec.data.Begin = new Date(me.Begin).format('Y-m-d H:i');
        		rec.data.End = new Date(me.End).format('Y-m-d H:i');
        		break;
        	case 'tv':
        		rec.data.Begin = new Date(me.Begin).format('Y-m-d H:i');
        		rec.data.End = new Date(me.End).format('Y-m-d H:i');
        	default:
        		rec.data.Max = me.GetFmtVal(me.Max);
        		rec.data.Min = me.GetFmtVal(me.Min);	
        }
        rec.commit();
        if (_li.IsTile) {
            var max_ = Ext.getDom('MAX' + me.ID);
            if (!max_) return;
            var min_ = Ext.getDom('MIN' + me.ID);
            max_.style.color = min_.style.color = rec.get('Color');
            max_.innerHTML = me.GetFmtVal(me.Max);
            min_.innerHTML = me.GetFmtVal(me.Min);           
        }
    }

    function setRange(amax, amin) {   
        if (amax == undefined) {
            if (_li.Asyn) {
	            me.Max = _li.MainLine.Max;
	            me.Min = _li.MainLine.Min;
	        }
	        else{
	            var max = drawData.Max;
	            var min = drawData.Min;
	            var dy = max - min;
	            dy = dy == 0 ? max != 0 ? max / 2 : 0.5 : dy / 20;
	            me.Max = max + dy;
	            me.Min = min - dy;
	        }
        }
        else {
            me.Max = amax;
            me.Min = amin;
        }
    }

    function getPrecise() {
        var v = (me.Max != 0 ? me.Max : me.Min).toExponential();
        var exp = (v + '').split('e')[1] * 1 + 3 - numWidth;
        if (v < 0) exp++;
        return Math.pow(10, exp);
    }

    function getLeft(oldTime) {
        return (oldTime - me.Begin) * _dr.Width / (me.End - me.Begin);
    }
    
    /**
     * 15-12-28周星陆添加管道剔野数据设置接口
     * 添加管道剔野数据
     * lower //下限
     * upper //上限
     */
    this.setPipeTiyes = function (lower, upper){
    	var pipeTiyes = {begin:this.Begin,end:this.End,lower:lower,upper:upper};
    	drawData.setPipeTiyes(pipeTiyes);
    };
}