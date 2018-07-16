/*
 *曲线管理器
 */

Ext.define('js.LineManage',{
	constructor:function(_drawarea){
		return new LineManage(_drawarea);
	}
});	

function LineManage(_drawarea) {
    var lineList = [],
        me = this,
        stack,
        hp,
        tp,
        isMoveBefore = true,
        isCompressed = true,
        tcx = Ext.getCmp('TCX'),
        thf = Ext.getCmp('THF'),
        tl = Ext.getCmp('TL'),
        tlp = Ext.getCmp('TLP'),
        ts = Ext.getCmp('TS'),
        trp = Ext.getCmp('TRP'),
        tr = Ext.getCmp('TR'),
        sb=Ext.getCmp("sb");
        //txz = Ext.getCmp('TXZ');
    	if(!sb)sb=parent.Ext.getCmp("sb");

    function push(type) {
        switch (type) {
            case '添加曲线':
            case '删除曲线':
            case '平铺曲线':
            case '播放曲线':
                stack = [];
                tp = hp = -1;
                tcx.setEnable(false);
                thf.setEnable(false);
                for (var i = 0; i < lineList.length; i++) {
                    lineList[i].Clear();
                }
                break;
            default:
                if (hp > -1 && stack[hp].Type == type) {
                    for (i = 0; i < lineList.length; i++) {
                        if (!lineList[i].IsEqual()) break;
                    }
                    if (i >= lineList.length) return;
                }
                if (hp > restoreNum - 1) {
                    stack.shift();
                    hp--;
                }
                stack.length = hp + 1;
                stack.push({
                    Type: type,
                    IsTile: me.IsTile,
                    MainLine: me.MainLine,
                    ShowCount: me.ShowCount,
                    Asyn: me.Asyn
                });
                if (type != '撤销') hp++;
                tp = -1;
                for (i = 0; i < lineList.length; i++) {
                    lineList[i].Push(type);
                }
                thf.setEnable(type == '撤销');
                tcx.setEnable();
                break;
        }
    }
    function withdraw() {
        if (hp > -1 && tp == -1) push('撤销');
        tp = hp + 1;
        if (tp > 0) thf.setEnable();
        if (hp == 0) tcx.setEnable(false);
        return stack[hp--];
    }
    function restore() {
        hp = tp - 1;
        if (hp > -1) tcx.setEnable();
        if (tp + 1 > stack.length - 1) thf.setEnable(false);
        return stack[tp++];
    }
    lineList.getLine = function (id) {
        for (var i = 0; i < lineList.length; i++)
            if (lineList[i].ID == id)
                return lineList[i];
        return null;
    };
    lineList.getAvailId = function (id) {
        for (var i = 0; i < lineList.length; i++)
            if (lineList[i].ID != id)
                return lineList[i].ID;
        return null;
    };
    lineList.getSatParam=function () {               
        var satParam=[];
        for (var i = 0; i < lineList.length; i++){
        	var id=lineList[i].getTmId();
        	var satId=lineList[i].getSatId();
        	var port=19737;//lineList[i].getPort();
        	var el=getEl(satParam,satId);
        	if(el){
        		if(!getEl(el.idList,id))el.idList.push(id);
        	}
        	else{
        		satParam.push({id:satId,port:port,idList:[id]});
        	}
        }
        var args=[];
        for(var i=0;i<satParam.length; i++){       	
        	var arg=[];
        	for(var p in satParam[i]){
        		if(p=='idList'){
	        		var ids=[];
	        		for(var id=0;id<satParam[i][p].length; id++){
	        			ids.push(satParam[i][p][id]);
	        		}
	        		arg.push(ids.join('-'));
	        	}
	        	else{
	        		arg.push(satParam[i][p]);
	        	}
        	}
        	args.push(arg.join('&'));
        }
        return args.join(' ');//satParam;
    };

    this.ShowCount = 0;
    this.MainLine = null;    
    this.Asyn = false;
    this.IsTile = false;
    //xjt-15.5.20
    var isLoad=0,timoutHandler=null;
    this.mask=function(){
    	if(isLoad==0){
    		if(!Ext.getCmp("MaskRegion").getEl())return;
    		Ext.getCmp("MaskRegion").getEl().mask("正在数据交互中……");
    		timoutHandler=setTimeout(function(){
    			Ext.getCmp("MaskRegion").getEl().unmask();
    			isLoad=0;
    			timoutHandler=null;
    		},60000000);
    	}
    	isLoad++;
    };
    this.unmask=function(){
    	if(isLoad>0)isLoad--;
    	if(isLoad==0) {
    		if(!Ext.getCmp("MaskRegion").getEl())return;
    		Ext.getCmp("MaskRegion").getEl().unmask();
    		if(timoutHandler){
    			clearTimeout(timoutHandler);
    			_condtion=timoutHandler=null;
    		}
    	}
    };
    //xjt-15.5.20

    this.Update = function () {
        var at1 = new Date(this.MainLine.Begin).format('Y-m-d H:i'),
            at2 = new Date(this.MainLine.End).format('Y-m-d H:i');
        beginCmp.setValue(at1);
        endCmp.setValue(at2);
        beginCmp.Begin=new Date(this.MainLine.Begin).format('Y-m-d H:i:s.u');
        endCmp.End=new Date(this.MainLine.End).format('Y-m-d H:i:s.u');
    };
    this.CheckState = function () {
        for (var i = 0; i < lineList.length; i++) {
            if (lineList[i].Completed) continue;
            return false;
        }
        return true;
    };
    this.AddLine = function (rec) {
    	sb.showBusy();
        var line = Ext.create('js.Line',rec,_drawarea,this);
        lineList.push(line);
        this.ShowCount++;
        push('添加曲线');
    };
    this.ExpandLine = function (arg) {
        /*if (this.IsTile && !this.MainLine.IsExpand(arg.Max, arg.Min, arg.Begin, arg.End)) 
            return;*/
    	if(!this.IsTile){
    		for (var i = 0; i < lineList.length; i++) {
                if (lineList[i].IsExpand(arg.Max, arg.Min, arg.Begin, arg.End)) break;
            }
            if (i >= lineList.length) return; //无效选择框
    	}        
        push('放大曲线');
        /*var isRemoteData = arg.End - arg.Begin > timePrecise;
        if (isRemoteData) {//选择差值大于10ms才有效
            var t = this.Begin, dt = (this.End - t) / _drawarea.Width;
            this.Begin = arg.Begin * dt + t;
            this.End = arg.End * dt + t;
        }*/
        sb.showBusy();
        for (i = 0; i < lineList.length; i++) {
            lineList[i].ExpandLine(arg);
        }
        _drawarea.Update();
        this.Update();
    };
    this.CompressingLine = function (fx,x) {
        if (isCompressed) {
            push('压缩曲线');
            for (var i = 0; i < lineList.length; i++) {
	            lineList[i].BeforeCompress();
	        }
            isCompressed = false;
            sb.showBusy();
        }
        _drawarea.Update(); //更新时间坐标值
        this.Update(); //更新时间选择框
        for (var i = 0; i < lineList.length; i++) {
            lineList[i].CompressingLine(fx,x);
        }
    };
    this.CompressedLine = function (t1,t2,id) {
        isCompressed = true;
        if(id){
        	lineList.getLine(id).CompressedLine(t1,t2);
        	if(this.MainLine && id==this.MainLine.ID){
        		this.Update();
        		_drawarea.Update();
        	}
        }
        else{
        	for (var i = 0; i < lineList.length; i++) {
	            lineList[i].CompressedLine(t1,t2);
	        }
	        if(t1){
	        	this.Update();//xjt-2015.5.18
	        	_drawarea.Update();
	        }
        }               
    };
    this.Move = function (arg) {
        if (arg.moved) {
            for (var i = 0; i < lineList.length; i++) {
                lineList[i].Moved();
            }
            isMoveBefore = true;
        }
        else {
            if (isMoveBefore && !window._IsPlay) {
                push('移动曲线');
                isMoveBefore = false;
                sb.showBusy();
            }           
            for (i = 0; i < lineList.length; i++) {
                lineList[i].Move(arg.dx, arg.dy);
            }
            _drawarea.Move(arg.dx, arg.dy);
            this.Update();
        }
    };
    this.ZoomAllLine = function () {
        push('缩放曲线');
        this.Asyn = false;
        for (var i = 0; i < lineList.length; i++) {
            lineList[i].Zoom();
        }
        _drawarea.Update();
    };
    this.SetMainAxis = function (id) {
        if (this.MainLine&&this.MainLine.ID==id) return;
        this.MainLine = lineList.getLine(id);
        this.Update();//更新时间选择框
        _drawarea.SetColor(this.MainLine.getColor());//更新坐标颜色
        if (this.IsTile) {
            for (var i = 0, j = 0; i < lineList.length; i++) {
                if (lineList[i].getShow()) {
                    if (lineList[i].ID == id) {
                        _drawarea.UpdateYRuler(j);//更新水平标尺
                        break;
                    }
                    j++;
                }
            }
        }
        _drawarea.Update(this.IsTile);//更新坐标值
        for (i = 0; i < lineList.length; i++) {
            lineList[i].SetMainAxis(lineList[i].ID == id);//设置主轴曲线
        }
    };
    //设置主轴曲线 mxc add 同SetMainAxis方法，把第一行判断去掉了
    this.SetMainLine = function (id) {
//        if (this.MainLine&&this.MainLine.ID==id) return;
        this.MainLine = lineList.getLine(id);
        this.Update();//更新时间选择框
        _drawarea.SetColor(this.MainLine.getColor());//更新坐标颜色
        if (this.IsTile) {
            for (var i = 0, j = 0; i < lineList.length; i++) {
                if (lineList[i].getShow()) {
                    if (lineList[i].ID == id) {
                        _drawarea.UpdateYRuler(j);//更新水平标尺
                        break;
                    }
                    j++;
                }
            }
        }
        _drawarea.Update(this.IsTile);//更新坐标值
        for (i = 0; i < lineList.length; i++) {
            lineList[i].SetMainAxis(lineList[i].ID == id);//设置主轴曲线
        }
    };
    this.MoveUpLine=function(id,rec){
    	this.MainLine.setRecord(rec);
    	for (var i = 0; i < lineList.length; i++) {
            if(lineList[i].ID == id)break;
        }
    	var t=lineList[i];
    	lineList[i]=lineList[i-1];
    	lineList[i-1]=t;
    	if(this.IsTile){
    		_drawarea.DelYCoord();
    		this.ReAllPos();    		
    	}
    };
    this.MoveDownLine=function(id,rec){
    	this.MainLine.setRecord(rec);
    	for (var i = 0; i < lineList.length; i++) {
            if(lineList[i].ID == id)break;
        }
    	var t=lineList[i];
    	lineList[i]=lineList[i+1];
    	lineList[i+1]=t;
    	if(this.IsTile){
    		_drawarea.DelYCoord();
    		this.ReAllPos();    		
    	}
    };
    this.SetColor = function (id, color) {
        var line = lineList.getLine(id);
        if (line) {
            line.SetColor(color);
            if(line == this.MainLine)
                _drawarea.SetColor(color);
        }
    };
    this.SetWidth = function (id, width) {
        var line = lineList.getLine(id);
        if (line) line.SetWidth(width);
    };  
    this.SetMaxMinVal = function (id, max, min) {
        this.Asyn = false;
        lineList.getLine(id).SetMaxMinVal(max, min);
        if(this.MainLine && id==this.MainLine.ID)_drawarea.Update();
    };
    this.DelLine = function (id) {
        if (this.MainLine && id == this.MainLine.ID) {
            var aid = lineList.getAvailId(id);
            if (aid) this.SetMainAxis(aid);
            else {
            	this.MainLine=null;
            	_drawarea.Clear();
            	if(window._IsPlay)this.PlayLine(0);
    			if(window._IsReal)this.RealShow();
            }
        }
        var line = lineList.getLine(id);
        line.DelLine();
        lineList.remove(line);
        if(line.getShow())this.ShowCount--;
        delete line;
        //this.ShowCount--;
        if (this.IsTile) {
            _drawarea.DelYCoord();
            if (this.ShowCount > 0) this.ReAllPos();
        }
        push('删除曲线');
    };
    this.AsynLine = function () {
        push('同步曲线');
        this.Asyn = true;
        var ml=this.MainLine;
        for (var i = 0; i < lineList.length; i++) {
            if (lineList[i].ID != ml.ID)
                lineList[i].SetMaxMinVal(ml.Max, ml.Min);
        }
    };
    this.PlayLine = function (arg) {
        var i = 0;
        switch (arg) {
            case 0:
                clearInterval(_IsPlay);
                _IsPlay = null;
                me.Move({ moved: true });
                tl.setEnable(false);
                ts.setEnable(false);
                tr.setEnable(false);
                tlp.setEnable();
                trp.setEnable();
                //txz.setEnable();
                break;
            case 1:
            case -1:
                _fx = arg;
                if (!window._IsPlay) {
                    _speed = 1;
                    _IsPlay = setInterval(function () {
                        me.Move({ moved: false, dx: _speed * _fx, dy: 0 });
                        i++;
                        if (i + _speed > 9) {
                            me.Move({ moved: true });
                            i = 0;
                        }
                    }, 100);
                    tl.setEnable();
                    ts.setEnable();
                    tr.setEnable();
                    push('播放曲线');
                    //txz.setEnable(false);
                    //txz.setIcon('img/bqx.ico');
                }
                tlp.setEnable(arg == 1);
                trp.setEnable(!(arg == 1));
                break;
            case 2:
            case -2:
                if (window._IsPlay) {
                    if (_fx * arg > 0) {//右播放
                        if (_speed <= 8) _speed *= 2;
                    }
                    else {//左播放
                        if (_speed > 1) _speed /= 2;
                    }
                }
                break;
        }
    };
    this.ShowLine = function (id, show) {
        var info=_chart.getPointInfo();
        if(info&&!show&&info.id==id)_chart.hidePoint();
        lineList.getLine(id).ShowLine(show);
        show?this.ShowCount++:this.ShowCount--;
        if (this.IsTile&&this.ShowCount>0) {
            _drawarea.DelYCoord();
            this.ReAllPos();
        }
    };
    this.TileShow = function (isTile) {
        this.IsTile=isTile;
        if(this.ShowCount == 0) return;
        //若绘图区存在曲线
        if (isTile) this.ReAllPos();
        else {
            _drawarea.DelYCoord();
            _drawarea.Update();
            for (var i = 0; i < lineList.length; i++) {
                lineList[i].RePos();
            } 
        }
        push('平铺曲线');
    };
    this.ReAllPos = function (left) {
        var h = _drawarea.Height / this.ShowCount;
        for (var i = 0, j = 0; i < lineList.length; i++) {
            if (lineList[i].getShow()) {
                _drawarea.CreateYCoord(lineList[i].ID, j * h, h);
                if (this.MainLine&&lineList[i].ID == this.MainLine.ID)
                    _drawarea.UpdateYRuler(j * h, h);
                lineList[i].RePos(left, j * h, h);
                j++;
            }
        }
    };
    this.ZoomLine = function () {
        if (this.MainLine) {
            push('缩放曲线');
            this.Asyn = false;
            this.MainLine.Zoom();
            _drawarea.Update();
        }
    };
    this.MoveUpDown = function (fx) {
        if (this.MainLine) {
            push('上下移动');
            this.Asyn = false;
            this.MainLine.MoveUpDown(fx);
            _drawarea.Update();
        }
    };
    this.ZoomYLine = function (fx) {
        if (this.MainLine) {
            push('缩放曲线');
            this.Asyn = false;
            this.MainLine.ZoomYLine(fx);
            _drawarea.Update();
        }
    };
    this.MoveAllUpDown = function (fx) {
        push('上下移动');
        for (var i = 0; i < lineList.length; i++) {
            lineList[i].MoveUpDown(fx);
            _drawarea.Update();
        }
    };
    this.ZoomAllYLine = function (fx) {
        push('缩放曲线');
        for (var i = 0; i < lineList.length; i++) {
            lineList[i].ZoomYLine(fx);
            _drawarea.Update();
        }
    };
    this.SelectLine = function (isSel) {
        if (isSel) push('选择曲线');
        _drawarea.SelectLine(isSel);
    };
    this.SelMove = function (dy) {
        if (this.MainLine) {
            this.MainLine.SelMove(dy);
            _drawarea.Update();
        }
    };
    this.Withdraw = function (isWithdraw) {	
        var data = isWithdraw ? withdraw() : restore();        
        this.ShowCount = data.ShowCount;
        this.MainLine = data.MainLine;      
        this.Asyn = data.Asyn;
        this.IsTile = data.IsTile;
        for (var i = 0; i < lineList.length; i++) {
            lineList[i].Withdraw(isWithdraw);
        }
        this.Update();
        _drawarea.Update(); 
        _drawarea.SetColor(this.MainLine.getColor());
        //设置主轴曲线 孟祥超 add 解决bug7959 
        this.SetMainLine(data.MainLine.ID)
        if (this.IsTile) {
            for (i = 0, j = 0; i < lineList.length; i++) {
                if (lineList[i].getShow()) {
                    if (this.MainLine && lineList[i].ID == this.MainLine.ID) {
                        _drawarea.UpdateYRuler(j);
                        break;
                    }
                    j++;
                }
            }
        }
    };
    this.Print = function () {
        var lines = [];
        for (i = 0, j = 0; i < lineList.length; i++)
            /*if (lineList[i].getShow())*/ {
                var line = [];
                line.push(
                        lineList[i].getNum(),
                        lineList[i].Begin,
                        lineList[i].End,
                        lineList[i].Max,
                        lineList[i].Min,
                        lineList[i].getColor(),
                        lineList[i].getWidth(),
                        lineList[i].getType(),
                        lineList[i].ID,
                        lineList[i].getName(),
                        lineList[i].getCode(true),
                        lineList[i].getDataType(),
                        lineList[i].getTiye('NOPIPE'),
                        lineList[i].getTagData(),
                        lineList[i].getSatId(),
                        0,null,
                        lineList[i].getShow()
                    );
                lines.push(line.join('|'));
            }
        var params = Ext.urlEncode({
            MID: this.MainLine.ID,
            IsTile: this.IsTile,
            Left: _chart.getLeft(),
            Top: _chart.getTop(),
            numWidth: numWidth,
            Lines: lines.join('_-_')
        });
        //openPage("Print.html?" + params);
        window.open("Print.html?" + params, "_blank", 'left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-10)+',scrollbars=no,resizable=no,toolbar=no,menubar=no,location=no,status=no');
    };
    this.Resize = function (w,h) {
        _drawarea.Resize(w,h);
        for (var i = 0; i < lineList.length; i++) {
            lineList[i].Resize();
        }
    };
    this.SetLineType=function(id){
    	lineList.getLine(id).SetLineType();
    	_drawarea.Update();
    	this.Update();
    };
    this.RealShow=function(){
    	/*if(!window._IsReal){
    		for (var i = 0; i < lineList.length; i++) lineList[i].SetRealShow();
    		var satParam=lineList.getSatParam();
    		_IsReal=setInterval(function () {
	        	Ajax.send('realdata.edq',{
		            satParam: satParam
		        }, function (json) {
		            for (var i = 0; i < lineList.length; i++) {
			            lineList[i].RealShow(getTmData(json.realData,lineList[i].getSatId(),lineList[i].getTmId()));
			        }
			        _drawarea.Update();
        			me.Update();
		        });
	        },1000);
    	}
    	else{
    		clearInterval(_IsReal);
    		_IsReal=null;
    		Ajax.send('realdatastop.edq');
    	}*/
    	if(!window._IsReal){    		
    		for (var i = 0; i < lineList.length; i++) lineList[i].ResetTimer();
    		_IsReal=setInterval(function () {
    			var now=Now.getMs();//获取Web当前时间
    			for (var i = 0; i < lineList.length; i++) {
		            lineList[i].RealShow(now);
		        }
		        _drawarea.Update();
    			me.Update();
	        },1000);
    	}
    	else{
    		clearInterval(_IsReal);
    		_IsReal=null;
    		//Ajax.send('realdatastop.edq');
    	}    
    };
    this.GetPointInfo=function(x,y,src){
    	if(!this.MainLine) return null;
    	var info=null;
    	if(!src){
    		if(this.MainLine.getShow())info=this.MainLine.GetPointInfo(x,y);
        	if(info)return info;    	
        	for (var i = 0; i < lineList.length; i++) {
        		if (lineList[i].getShow()&&lineList[i].ID!=this.MainLine.ID) {
        			info=lineList[i].GetPointInfo(x,y);
        			if(info)return info;
        		}
        	}  	
    	}
    	else{
    		if(this.MainLine.getShow()&&this.MainLine.getType()==2)info=this.MainLine.GetPointInfo(x,y);
        	if(info)return info; 
        	if(this.IsTile) return;
        	for (var i = 0; i < lineList.length; i++) {
        		if (lineList[i].getShow()&&lineList[i].ID!=this.MainLine.ID&&lineList[i].getType()==2) {
        			info=lineList[i].GetPointInfo(x,y);
        			if(info)return info;
        		}
        	}  	
    	}    		
		return null;
    };
    this.AddCondLine = function (rec,isTile,isRelativeQuery) {
        this.IsTile = isTile;
        if(isRelativeQuery){//加载相对预制条件
        	var dt=rec.get('End1').getTime(0)-rec.get('Begin1').getTime(0),
        		now=new Date().getTime();
        	rec.set('End1',Date.toStr(now));
        	rec.set('Begin1',Date.toStr(now-dt));
        	rec.set('End',new Date(now).format('Y-m-d H:i'));
        	rec.set('Begin',new Date(now-dt).format('Y-m-d H:i'));
        }       
        var line = Ext.create('js.Line',rec,_drawarea,this,true);
        lineList.push(line);
        if(rec.get('Show'))this.ShowCount++;
        else lineList.getLine(rec.get('Id')).ShowLine(0);
        push('添加曲线');
    };
    this.ShowOrigin = function () {
        var info=_chart.getPointInfo();
        Ajax.send('framedata.edq',{
            tmId: info.id,
            time: info.t,
            radius: 5
        }, function (json) {
            if(!json.frameData)return;
            _drawarea.ShowOrigin(info,json.frameData);
        });
    };
    this.LineTiye = function(x1, x2, y1, y2){
    	if(this.MainLine){
    		this.MainLine.Tiye(x1, x2, y1, y2); 
    	}   			
    };
    this.ShowStats = function(ty,tiye){
    	var x1,x2;
    	if(ty==1){
    		var x=_drawarea.GetXRulerInfo();
    		x1=x.x1;
    		x2=x.x2;
    	}
    	else{
    		x1=0;
    		x2=_drawarea.Width;
    	}
    	_drawarea.ShowStats(ty);
    	for (var i = 0; i < lineList.length; i++) {
    		lineList[i].GetStats(x1,x2,tiye);
    	}    	
    };
    this.AddTag = function () {
    	if(this.MainLine){
    		this.MainLine.AddTag(); 
    	} 
    };
    this.IsLongQuery = function () {
        for (var i = 0; i < lineList.length; i++) {
            if (lineList[i].End-lineList[i].Begin>43200000) 
            	return true;            
        }
        return false;
    };
    this.IsLongPoint=function(id){
    	var line=lineList.getLine(id);
    	return line.End-line.Begin>900000;
    };
    this.getRsts=function(type,tiye){
    	var rsts=[],exists='',
	    	getBegin=function(line){
	    		if(type==0)return line.Begin.trunc();
	    		var dt=(line.End-line.Begin)/_drawarea.Width,
	    			x=_drawarea.GetXRulerInfo();
	    		return (line.Begin+dt*x.x1).trunc();
	    	},
	    	getEnd=function(line){
	    		if(type==0)return line.End.trunc();
	    		var dt=(line.End-line.Begin)/_drawarea.Width,
	    			x=_drawarea.GetXRulerInfo();
	    		return (line.Begin+dt*x.x2).trunc();
	    	};
    	for(var i = 0; i < lineList.length; i++){
        	if(exists.indexOf('a'+lineList[i].ID)>0)continue;
        	var rst={Begin:getBegin(lineList[i]),End:getEnd(lineList[i]),Tms:[{Id:lineList[i].ID,TmId:lineList[i].getTmId(),Code:lineList[i].getCode(),Precision:lineList[i].getPrecision(),Tiye:lineList[i].getTiye(tiye),Mid:lineList[i].getSatId(),Num:lineList[i].getNum(),Type:lineList[i].getDataType(),Name:lineList[i].getName(),TmCode:lineList[i].getTmCode()}]};
        	exists+='a'+lineList[i].ID;
        	for(var j=i+1;j<lineList.length;j++){
        		if(exists.indexOf('a'+lineList[j].ID)>0)continue;
        		if(rst.Begin==getBegin(lineList[j])&&rst.End==getEnd(lineList[j])){
        			rst.Tms.push({Id:lineList[j].ID,TmId:lineList[j].getTmId(),Code:lineList[j].getCode(),Precision:lineList[j].getPrecision(),Tiye:lineList[j].getTiye(tiye),Mid:lineList[j].getSatId(),Num:lineList[j].getNum(),Type:lineList[j].getDataType(),Name:lineList[j].getName(),TmCode:lineList[j].getTmCode()});
        			exists+='a'+lineList[j].ID;
        		}
        	}
        	rsts.push(rst);
        }
    	return rsts;
    };
    this.GetRst=function(id){
    	var line=lineList.getLine(id);
    	return {
    		tmNum:line.getNum(),
    		begin:line.Begin.trunc(),
    		end:line.End.trunc(),
    		tiye:line.getTiye()
		};
    };
    this.GetQueryInfo=function(id, tiye){
    	if(id){
    		var line=lineList.getLine(id);
    		return [
    		    line.getSatId(),
    		    line.getNum(),
    		    new Date(line.Begin.trunc()).format('YmdHisu'),
    		    new Date(line.End.trunc()).format('YmdHisu'),
    		    line.getTiye(),
    		    line.getPrecision()
	        ].join('|');
    	}
    	var rst=[];
    	for(var i = 0; i < lineList.length; i++){
    		rst.push([
    		    lineList[i].getSatId(),
    		    lineList[i].getNum(),
    		    new Date(lineList[i].Begin.trunc()).format('YmdHisu'),
    		    new Date(lineList[i].End.trunc()).format('YmdHisu'),
    		    lineList[i].getTiye(tiye),
    		    lineList[i].getPrecision()
            ].join('|'));
    	}
    	return rst.join(';');
    };
    this.NoTiye=function(){
    	this.MainLine.NoTiye(); 
    };
    this.IsExceedTimeSpan=function(days,lineId){
    	if(lineId){
    		var line=lineList.getLine(lineId);
    		return line.End-line.Begin>86400000*days;
    	}
    	for(var i = 0; i < lineList.length; i++){
    		if(lineList[i].End-lineList[i].Begin>86400000*days)
    			return true;
    	}
    	return false;
    };
    
    function getTmData(realData,sid,tid){
    	var sat=getEl(realData,sid);
    	return {
    		Begin:sat.begin,
    		End:sat.end,
    		Data:getEl(sat.tmList,tid).dataList
		};
    }
    
    function getEl(arr,id){
    	for (var i = 0; i < arr.length; i++){
    		if(arr[i].id==id)return arr[i];
    	}
    	return null;
    }
    
    /**
     * 15-12-28周星陆添加管道剔野数据设置接口
     * 添加管道剔野数据
     * lower //下限
     * upper //上限
     */
    this.setPipeTiyes = function (lower, upper){
    	if(this.MainLine){
    		this.MainLine.setPipeTiyes(lower, upper); 
    	}   			
    };
}