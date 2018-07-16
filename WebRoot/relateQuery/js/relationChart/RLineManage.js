/*
 *曲线管理器
 */

Ext.define('js.relationChart.RLineManage',{
	constructor:function(_relation_drawarea){
		return new RLineManage(_relation_drawarea);
	}
});	

function RLineManage(_relation_drawarea) {
	
    var lineList = [],
    	dataLineList=[],
        me = this,
        stack,
        hp,
        tp,
        isMoveBefore = true,
        isCompressed = true,
        tcx = Ext.getCmp('RTCX'),
        thf = Ext.getCmp('RTHF'),
        tl = Ext.getCmp('RTL'),
        tlp = Ext.getCmp('RTLP'),
        ts = Ext.getCmp('RTS'),
        trp = Ext.getCmp('RTRP'),
        tr = Ext.getCmp('RTR'),
        beginCmp=Ext.getCmp("relation_beginCmp"),
        endCmp=Ext.getCmp("relation_endCmp"),
    	canvas = Ext.create('js.relationChart.DataCanvas');//景科文新增 使用同一个canvas绘画航天器相关信息
        //txz = Ext.getCmp('TXZ');
    //xjt-2015.1.24
//    var sb=Ext.getCmp("sb");
//	if(!sb)sb=parent.Ext.getCmp("sb");
    var otherDraw={//景科文新增  绘图对象
    		layerRec:[],
    		Shield:false,
    		ThresholdRec:[]
    };
    function push(type) {
        switch (type) {
            case '添加曲线':
            case '删除曲线':
            case '平铺曲线':
            case '播放曲线':
                stack = [];
                tp = hp = -1;
               /* tcx.setEnable(false);
                thf.setEnable(false);*/
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
//                thf.setEnable(type == '撤销');
//                tcx.setEnable();
                break;
        }
    };
    function withdraw() {
        if (hp > -1 && tp == -1) push('撤销');
        tp = hp + 1;
        if (tp > 0) thf.setEnable();
        if (hp == 0) tcx.setEnable(false);
        return stack[hp--];
    };
    function restore() {
        hp = tp - 1;
        if (hp > -1) tcx.setEnable();
        if (tp + 1 > stack.length - 1) thf.setEnable(false);
        return stack[tp++];
    };
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
  //xjt-15.5.21
    var isLoad=0,timoutHandler=null;
    this.mask=function(){
    	if(isLoad==0){
    		/*Ext.getCmp("Relation_MaskRegion").getEl().mask("正在数据交互中……");
    		timoutHandler=setTimeout(function(){
    			Ext.getCmp("Relation_MaskRegion").getEl().unmask();
    			isLoad=0;
    		},60000000);*/
    	}
    	isLoad++;    	
    };
    this.unmask=function(){
    	if(isLoad>0)isLoad--;
    	if(isLoad==0) {
    		/*Ext.getCmp("Relation_MaskRegion").getEl().unmask();
    		if(timoutHandler){
    			clearTimeout(timoutHandler);
    			timoutHandler=null;
    		}*/
    	}
    };
    //xjt-15.5.21

    this.Update = function () {
        var at1 = new Date(this.MainLine.Begin).format('Y-m-d H:i'),
            at2 = new Date(this.MainLine.End).format('Y-m-d H:i');
        beginCmp.setValue(at1);
        endCmp.setValue(at2);
//        reloadTimeGrid(at1,at2);
    };
    this.CheckState = function () {
        for (var i = 0; i < lineList.length; i++) {
            if (lineList[i].Completed) continue;
            return false;
        }
        return true;
    };
    this.AddLine = function (rec) {
    	
//    	sb.showBusy();
        var line = Ext.create('js.relationChart.RLine',rec,_relation_drawarea,this);
        lineList.push(line);
        this.ShowCount++;
        
        push('添加曲线');
    };
    this.AddDataLine = function (rec) {//景科文新增 添加航天器相关信息
//    	sb.showBusy();
    	_relation_drawarea.AddDataLine(rec);
    	var line = Ext.create('js.relationChart.DataRLine',rec,_relation_drawarea,this,canvas);
    	line.AddDataLine();
    	dataLineList.push(line);
    	this.ReDrowDataLine();
//    	sb.setText();
    };
    this.RemoveDataLine = function (id) {//景科文新增 移除 根据主键移除航天器相关信息
    	var lineId = id.split("&&&")[1];
    	_relation_drawarea.RemoveDataLine(lineId);
    	for (var i=0; i < dataLineList.length; i++) {
        	if(dataLineList[i].lineId==lineId){
        		dataLineList.remove(i);
        		break;
        	}
        }
    	//删除相关信息
    	relateInfoCache.removeRelateInfoFromCache(id.split("&&&")[0],id.split("&&&")[1]);
    	/*for (var i=0; i < recList.length; i++) {//全局航天器相关信息rec数组移除元素
    		if(recList[i].get('mid')+"_"+recList[i].get('dataTypeId')==id.split("_")[0]+"_"+id.split("_")[1]){
    			recList.remove(i);
    			break;
    		}
    	}
    	for (var i=0; i < commonList.length; i++) {//全局航天器相关信息commonList数组移除元素
    		if(commonList[i].indexOf(id.split("_")[1])!=-1 &&commonList[i].indexOf(id.split("_")[2])!=-1){
    			commonList.remove(i);
    			break;
    		}
    	}*/
    	this.ReDrowDataLine();
    };
    this.ExpandLine = function (arg) {
        /*if (this.IsTile && !this.MainLine.IsExpand(arg.Max, arg.Min, arg.Begin, arg.End)) 
            return false;*/
    	if (!this.IsTile){
    		for (var i = 0; i < lineList.length; i++) {
                if (lineList[i].IsExpand(arg.Max, arg.Min, arg.Begin, arg.End)) break;
            }
            if (i >= lineList.length) return false; //无效选择框
    	}    	
        push('放大曲线');
//        sb.showBusy();
        for (i = 0; i < lineList.length; i++) {
            lineList[i].ExpandLine(arg);
        }
        for (i = 0; i < dataLineList.length; i++) {//景科文新增 修改航天器相关信息x轴对应信息
        	dataLineList[i].ExpandLine(arg);
        }
        _relation_drawarea.Update();
        this.Update();
        this.ReDrowDataLine();//景科文新增 重新绘图 必须在时间变化完成后调用
    };
    this.CompressingLine = function (fx,x) {
        if (isCompressed) {
            push('压缩曲线');
            for (var i = 0; i < lineList.length; i++) {
	            lineList[i].BeforeCompress();
	        }
            isCompressed = false;
//            sb.showBusy();
        }
        _relation_drawarea.Update(); //更新时间坐标值
        this.Update(); //更新时间选择框
        for (var i = 0; i < lineList.length; i++) {
            lineList[i].CompressingLine(fx,x);
        }
        for (i = 0; i < dataLineList.length; i++) {//景科文新增 压缩航天器相关信息 特殊：创建临时数据存储空间进行数据压缩处理展示
        	if(i==0) dataLineList[i].DrawDataClear();
        	dataLineList[i].BeforeCompress();
        	dataLineList[i].CompressingLine(fx,x,i);
        }
    };
    this.CompressedLine = function (t1,t2,id) {
        isCompressed = true;
        if(id){
        	lineList.getLine(id).CompressedLine(t1,t2);
        	if(this.MainLine && id==this.MainLine.ID){
        		this.Update();
        		_relation_drawarea.Update();
        	}
        }else{
        	for (var i = 0; i < lineList.length; i++) {
	            lineList[i].CompressedLine(t1,t2);
	        }
	        if(t1){
	        	_relation_drawarea.Update();
	        }
        }     
        for (i = 0; i < dataLineList.length; i++) {//景科文新增 压缩结束后 获取真实数据进行重新绘图
        	dataLineList[i].CompressedLine(t1,t2);
        }
    	this.ReDrowDataLine();
    };
    this.Move = function (arg) {
        if (arg.moved) {
            for (var i = 0; i < lineList.length; i++) {
                lineList[i].Moved();
            }
            for (i = 0; i < dataLineList.length; i++) {//景科文新增 移动结束后获取真实数据进行填充
            	dataLineList[i].Moved();
            }
        	this.ReDrowDataLine();
            isMoveBefore = true;
        }else {
            if (isMoveBefore && !window._IsPlay) {
                push('移动曲线');
                isMoveBefore = false;
//                sb.showBusy();
            }           
            for (i = 0; i < lineList.length; i++) {
                lineList[i].Move(arg.dx, arg.dy);
            }
            for (i = 0; i < dataLineList.length; i++) {//景科文新增 移动动作中绘图操作
            	dataLineList[i].Move(arg.dx, arg.dy);
            }
            canvas.DataLineMove(arg.dx, 0);//景科文新增 航天器相关信息画布移动
            canvas.OtherDataMove(arg.dx, 0);//景科文新增 其他绘图信息layer、屏蔽进行对应的移动
            _relation_drawarea.Move(arg.dx, arg.dy);
            this.Update();
        }
    };
    this.ZoomAllLine = function () {
        push('缩放曲线');
        this.Asyn = false;
        for (var i = 0; i < lineList.length; i++) {
            lineList[i].Zoom();
        }
        _relation_drawarea.Update();
        this.DrawOther();//景科文新增 重新绘画其他信息 主要（门限  y轴发生变化）
    };
    this.SetMainAxis = function (id) {
        if (this.MainLine&&this.MainLine.ID==id) return;
        this.MainLine = lineList.getLine(id);
        this.Update();//更新时间选择框
        _relation_drawarea.SetColor(this.MainLine.getColor());//更新坐标颜色
        if (this.IsTile) {
            for (var i = 0, j = 0; i < lineList.length; i++) {
                if (lineList[i].getShow()) {
                    if (lineList[i].ID == id) {
                        _relation_drawarea.UpdateYRuler(j);//更新水平标尺
                        break;
                    }
                    j++;
                }
            }
        }
        _relation_drawarea.Update(this.IsTile);//更新坐标值
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
    		_relation_drawarea.DelYCoord();
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
    		_relation_drawarea.DelYCoord();
    		this.ReAllPos();    		
    	}
    };
    this.SetColor = function (id, color) {
        var line = lineList.getLine(id);
        if (line) {
            line.SetColor(color);
            if(line == this.MainLine)
                _relation_drawarea.SetColor(color);
        }
    };
    this.SetWidth = function (id, width) {
        var line = lineList.getLine(id);
        if (line) line.SetWidth(width);
    };
    this.SetMaxMinVal = function (id, max, min) {
        this.Asyn = false;
        lineList.getLine(id).SetMaxMinVal(max, min);
        if(this.MainLine && id==this.MainLine.ID)_relation_drawarea.Update();
    };
    this.DelLine = function (id) {        
        if (this.MainLine && id == this.MainLine.ID) {
            var aid = lineList.getAvailId(id);
            if (aid) this.SetMainAxis(aid);
            else {
            	this.MainLine=null;
            	_relation_drawarea.Clear();
            	if(window._IsPlay)this.PlayLine(0);
    			if(window._IsReal)this.RealShow();
            }
        }
        var line = lineList.getLine(id);
        line.DelLine();
        removeQueryLine(lineList,line);
        if(line.getShow())this.ShowCount--;
        delete line;
        //this.ShowCount--;
        if (this.IsTile) {
            _relation_drawarea.DelYCoord();
            if (this.ShowCount > 0) this.ReAllPos();
        }
        push('删除曲线');
    };
    
    function removeQueryLine(lineList,line){
		Ext.Array.remove(lineList,line);
    }
    
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
        var info=_relation_chart.getPointInfo();
        if(info&&!show&&info.id==id)_relation_chart.hidePoint();
        lineList.getLine(id).ShowLine(show);
        show?this.ShowCount++:this.ShowCount--;
        if (this.IsTile&&this.ShowCount>0) {
            _relation_drawarea.DelYCoord();
            this.ReAllPos();
        }
    };
    this.TileShow = function (isTile) {
        this.IsTile=isTile;
        if(this.ShowCount == 0) return;
        //若绘图区存在曲线
        if (isTile) this.ReAllPos();
        else {
            _relation_drawarea.DelYCoord();
            _relation_drawarea.Update();
            for (var i = 0; i < lineList.length; i++) {
                lineList[i].RePos();
            } 
        }
        this.ReDrowDataLine();
        push('平铺曲线');
    };
    this.ReAllPos = function (left) {
        var h = _relation_drawarea.Height / this.ShowCount;
        for (var i = 0, j = 0; i < lineList.length; i++) {
            if (lineList[i].getShow()) {
                _relation_drawarea.CreateYCoord(lineList[i].ID, j * h, h);
                if (this.MainLine && lineList[i].ID == this.MainLine.ID)
                    _relation_drawarea.UpdateYRuler(j * h, h);
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
            _relation_drawarea.Update();
        }
        this.DrawOther();//景科文新增 绘画其他信息（y轴发生变化）
    };
    this.MoveUpDown = function (fx) {
        if (this.MainLine) {
            push('上下移动');
            this.Asyn = false;
            this.MainLine.MoveUpDown(fx);
            _relation_drawarea.Update();
        }
        this.DrawOther();//景科文新增 绘画其他信息（y轴发生变化）
    };
    this.ZoomYLine = function (fx) {
        if (this.MainLine) {
            push('缩放曲线');
            this.Asyn = false;
            this.MainLine.ZoomYLine(fx);
            _relation_drawarea.Update();
        }
        this.DrawOther();//景科文新增 绘画其他信息（y轴发生变化）
    };
    this.MoveAllUpDown = function (fx) {
        push('上下移动');
        for (var i = 0; i < lineList.length; i++) {
            lineList[i].MoveUpDown(fx);
            _relation_drawarea.Update();
        }
        this.DrawOther();//景科文新增 绘画其他信息（y轴发生变化）
    };
    this.ZoomAllYLine = function (fx) {
        push('缩放曲线');
        for (var i = 0; i < lineList.length; i++) {
            lineList[i].ZoomYLine(fx);
            _relation_drawarea.Update();
        }
        this.DrawOther();//景科文新增 绘画其他信息（y轴发生变化）
    };
    this.SelectLine = function (isSel) {
        if (isSel) push('选择曲线');
        _relation_drawarea.SelectLine(isSel);
    };
    this.SelMove = function (dy) {
        if (this.MainLine) {
            this.MainLine.SelMove(dy);
            _relation_drawarea.Update();
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
        _relation_drawarea.Update(); 
        _relation_drawarea.SetColor(this.MainLine.getColor());
        if (this.IsTile) {
            for (i = 0, j = 0; i < lineList.length; i++) {
                if (lineList[i].getShow()) {
                    if (this.MainLine && lineList[i].ID == this.MainLine.ID) {
                        _relation_drawarea.UpdateYRuler(j);
                        break;
                    }
                    j++;
                }
            }
        }
        //xjt-150129
        for (i = 0; i < dataLineList.length; i++) {
        	dataLineList[i].Withdraw(this.MainLine.Begin,this.MainLine.End);
        }
        this.ReDrowDataLine();
    };
    this.Print = function () {
    	
        var lines = [];
        for (i = 0, j = 0; i < lineList.length; i++){
        	//mxc 修改 解决 如果主轴勾选不显示，打印时其他曲线也无法显示问题
//            if (lineList[i].getShow()) {
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
                        lineList[i].getParamType(),
                        lineList[i].getFormula(),
                        lineList[i].getShow()//mxc add 解决打印不显示曲线
                    );
                lines.push(line.join('|'));
            }
        var params = Ext.urlEncode({
            MID: this.MainLine.ID,
            IsTile: this.IsTile,
            Left: _relation_chart.getLeft(),
            Top: _relation_chart.getTop(),
            numWidth: numWidth,
            Lines: lines.join('_-_')
        });
        //openPage("Print.html?" + params);
        window.open("Print.html?" + params, "_blank", 'left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-10)+',scrollbars=no,resizable=no,toolbar=no,menubar=no,location=no,status=no');
    };
    this.Resize = function (w,h) {
        _relation_drawarea.Resize(w,h);
        for (var i = 0; i < lineList.length; i++) {
            lineList[i].Resize();
        }
        canvas = Ext.create('js.relationChart.DataCanvas');//景科文新增 修改了画布大小得重新赋予对象
        for (var i = 0; i < dataLineList.length; i++) {
        	_relation_drawarea.DataResize(dataLineList[i].rec);//页面布局改变
        	dataLineList[i].ChangeCanvas(canvas);//画布改变
        	dataLineList[i].Resize();//数据改变
        }
        this.ReDrowDataLine();//重新绘图
    };
    //景科文新增  -- 重新绘制图形
    this.ReDrowDataLine = function () {
    	canvas.DrawDataClear();
    	for (var i = 0; i < dataLineList.length; i++) {
    		dataLineList[i].ReDrowDataLine(i);
    	}
    	this.DrawOther();
    };
    this.SetLineType=function(id){
    	lineList.getLine(id).SetLineType();
    	_relation_drawarea.Update();
    	this.Update();
    };
    this.RealShow=function(){
    	if(!window._relation_IsReal){    		
    		for (var i = 0; i < lineList.length; i++) lineList[i].ResetTimer();
    		_relation_IsReal=setInterval(function () {
    			var now=Now.getMs();//获取Web当前时间
    			for (var i = 0; i < lineList.length; i++) {
		            lineList[i].RealShow(now);
		        }
		        _relation_drawarea.Update();
    			me.Update();
	        },1000);
    	}
    	else{
    		clearInterval(_relation_IsReal);
    		_relation_IsReal=null;
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
        	var dt=rec.get('End').getTime()-rec.get('Begin').getTime(),
        		now=new Date().getTime();        	
        	rec.set('End',new Date(now).format('Y-m-d H:i'));
        	rec.set('Begin',new Date(now-dt).format('Y-m-d H:i'));
        }    
        var line = Ext.create('js.relationChart.RLine',rec,_relation_drawarea,this,true);
        lineList.push(line);
        this.ShowCount++;
        push('添加曲线');
    };
    this.ShowOrigin = function () {
        var info=_relation_chart.getPointInfo();
        Ajax.send('framedata.edq',{
            tmId: info.id,
            time: info.t,
            radius: 5
        }, function (json) {
            if(!json.frameData)return;
            _relation_drawarea.ShowOrigin(info,json.frameData);
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
    		var x=_relation_drawarea.GetXRulerInfo();
    		x1=x.x1;
    		x2=x.x2;
    	}
    	else{
    		x1=0;
    		x2=_relation_drawarea.Width;
    	}
    	_relation_drawarea.ShowStats(ty);
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
        	var rst={Begin:getBegin(lineList[i]),End:getEnd(lineList[i]),Tms:[{Id:lineList[i].ID,TmId:lineList[i].getTmId(),Code:lineList[i].getCode(),Precision:lineList[i].getPrecision(),Tiye:lineList[i].getTiye(tiye),Mid:lineList[i].getSatId(),Num:lineList[i].getNum(),Type:lineList[i].getDataType()}]};
        	exists+='a'+lineList[i].ID;
        	for(var j=i+1;j<lineList.length;j++){
        		if(exists.indexOf('a'+lineList[j].ID)>0)continue;
        		if(rst.Begin==getBegin(lineList[j])&&rst.End==getEnd(lineList[j])){
        			rst.Tms.push({Id:lineList[j].ID,TmId:lineList[j].getTmId(),Code:lineList[j].getCode(),Precision:lineList[j].getPrecision(),Tiye:lineList[j].getTiye(tiye),Mid:lineList[j].getSatId(),Num:lineList[j].getNum(),Type:lineList[j].getDataType()});
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
    		    line.getTiye(tiye),
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
    		    lineList[i].getTiye(),
    		    lineList[i].getPrecision()
            ].join('|'));
    	}
    	return rst.join(' ');
    };
    this.AddLayer=function(layerRec){//景科文新增 添加layer
    	otherDraw.layerRec=layerRec;
    	this.DrawOther();
    };
    this.Shield=function(state){//景科文新增 屏蔽开关操作
    	otherDraw.Shield=state;
    	this.DrawOther();
    };
    this.Threshold=function(ThresholdRec){//景科文新增 门限
    	otherDraw.ThresholdRec=ThresholdRec;
    	this.DrawOther();
    };
    this.DrawOther=function(){//景科文新增 绘画所有其他内容
    	this.OtherDataClear();
    	this.DrawLayer(otherDraw.layerRec);
    	this.DrawShield(otherDraw.Shield);
    	this.DrawThreshold(otherDraw.ThresholdRec);
    };
    this.clearAllOther=function(){//景科文新增 清除所有其他信息
    	this.OtherDataClear();
    	otherDraw.layerRec=[];
    	otherDraw.Shield=false;
    	otherDraw.ThresholdRec=[];
    };
    this.DrawLayer=function(layerRec){//景科文新增   （绘图--层）
    	for(var i=0;i<layerRec.length;i++) canvas.DrawLayer(getXval(layerRec[i]["start"]),getXval(layerRec[i]["end"]));
    };
    this.DrawShield=function(state){//景科文新增   （绘图--屏蔽）
    	if(!state) return;
    	var layerRec=otherDraw.layerRec;
    	var start;
    	var end;
    	for(var i=0;i<layerRec.length;i++){
    		start=getXval(layerRec[i]["start"]);
    		end=getXval(layerRec[i]["end"]);
    		if(i==0) canvas.Shield(0,start);
    		if(i>0&&i<=layerRec.length-1) canvas.Shield(getXval(layerRec[i-1]["end"]),start);
    		if(i==layerRec.length-1) canvas.Shield(end, _relation_drawarea.Width);
    	}
    };
    this.DrawThreshold=function(ThresholdRec){//景科文新增 画门限
    	var h=_relation_drawarea.Height;
    	if(!this.MainLine) return;
    	var max=this.MainLine.Max;
    	var min=this.MainLine.Min;
    	var line1="";
    	var line2="";
    	var el=document.getElementById("YRCoord_threshold");
    	el.innerHTML="";
    	var color="";
    	for(var i=0;i<ThresholdRec.length;i++){
    		switch(ThresholdRec[i].type){
    			case '1':
    				color="green";
    				break;
    			case '2':
    				color="blue";
    				break;
    			case '3':
    				color="red";
    				break;
    		}
    		line1=h*(max-ThresholdRec[i].line1Y)/(max-min);
    		line2=h*(max-ThresholdRec[i].line2Y)/(max-min);
    		canvas.Threshold(line1,line2,color);
            var yVal = document.createElement("div");
    		with (yVal.style) {
    			width = 140 + 'px'; height = 14 + 'px'; overflow = "visible"; position = "absolute";
    			left = 1 + 'px'; top = line1 + 'px';zIndex="3";
    		}
    		yVal.innerHTML="<font style='font-size:80%;' color='"+color+"' title='"+ThresholdRec[i].line1Y+"'>"+ThresholdRec[i].title+"</font>";
    		el.appendChild(yVal);
            
            var yVal2 = document.createElement("div");
    		with (yVal2.style) {
    			width = 140 + 'px'; height = 14 + 'px'; overflow = "visible"; position = "absolute";
    			left = 1 + 'px'; top = line2 + 'px';zIndex="3";
    		}
    		yVal2.innerHTML="<font style='font-size:80%;' color='"+color+"' title='"+ThresholdRec[i].line2Y+"'>"+ThresholdRec[i].title+"</font>";
    		el.appendChild(yVal2);
    	}
    };
    this.OtherDataClear=function(){//景科文新增   清空绘图3
    	var el=document.getElementById("YRCoord_threshold");
    	el.innerHTML="";
		canvas.OtherDataClear();
    };
    this.GetDataPointInfo=function(ax,ay){//景科文新增 获取标记信息
    	var info=null;
    	for (var i = 0; i < dataLineList.length; i++) {
    		info=dataLineList[i].GetDataPointInfo(ax,ay,i);
			if(info) return info;
    	}  	
    };
    this.NoTiye=function(){
    	this.MainLine.NoTiye(); 
    };
    
    function getXval(time){//景科文新增 根据时间获取x
    	var start=me.MainLine.Begin;
    	var end=me.MainLine.End;
    	var wid= _relation_drawarea.Width;
    	var dt=(end-start)/wid;
    	var timer=isNaN(time.getTime())?Ext.Date.parse(time,'Y-m-d H:i:s.u').getTime():time.getTime();
    	if(timer<start) timer=start;
    	if(timer>end) timer=end;
    	return (timer-start)/dt;
    }
    
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
    
    //xjt-2015.2.2
    this.getDataLines=function(){
    	return dataLineList;
    };
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

/*
 * 刷新关联查询结果时间段列表
 */
function reloadTimeGrid(begin,end){
	if(!Ext.getCmp("replace_sqlCmp").getValue()) return;
	
	var sql = Ext.getCmp("replace_sqlCmp").getValue();
	sql = 'BETWEEN "'+begin+":00"+'" AND "'+end+":00"+'";' + sql.substring(sql.indexOf(";")+1);
	var timeStore = Ext.getCmp("TimeGrid").getStore();
	timeStore.proxy.extraParams.sql = sql;
	timeStore.proxy.extraParams.time = Ext.getCmp("gTime").getValue();
	timeStore.proxy.extraParams.timeOut = Ext.getCmp("timeOut").getValue();
	timeStore.proxy.extraParams.minSize = 0;
	timeStore.load();
}

