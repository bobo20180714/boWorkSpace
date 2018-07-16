/*
 *曲线
 */
Ext.define('js.relationChart.DataRLine',{
	constructor:function(rec,_dr,_li,canvas){
		return new DataRLine(rec,_dr,_li,canvas);
	}
});	

function DataRLine(rec,_dr,_li,canvas) {
    var drawData = Ext.create('js.DrawDataManage',_dr,_li,canvas);
    var dt=0;
    var me=this;
    this.rec=rec;
    this.lineId=rec.get("dataTypeId");
    this.Begin = rec.get('Begin').getTime();
    this.End = rec.get('End').getTime();
    function getData(){
    	//var data=[{'type':1},{'type':3,x:100},{'type':3,x:250},{'type':9,x1:350,x2:550},{'type':9,x1:600,x2:900}];
    	var data="";
    	Ajax.send('point/pointdata',{
            type: rec.get('Type'),
            width: _dr.Width,
            mid:rec.get('mid'),
            dataTypeId: rec.get('dataTypeId'),
            begin: Date.toStr(me.Begin),
            end: Date.toStr(me.End),
            startCol:rec.get('startCol'),
            endCol:rec.get('endCol'),
            viewCol:rec.get('viewCol'),
            typeName:rec.get('typeName')
        }, function (json, opts) {
        	if(!json.pointData)return;
        	data = json.pointData;
        },false);
    	return data;
    }
    //添加绘图内容
    this.AddDataLine=function(){
    	drawData.DataBufAdd(getData());
    };
    //放大
    this.ExpandLine=function(arg){
    	//计算开始结束时间
        var t = this.Begin, dt = (this.End - t) / _dr.Width;
        this.Begin = arg.Begin * dt + t;
        this.End = arg.End * dt + t;
        //2015-12-29 zxl添加放大曲线限制在100毫秒以上
        if(this.End - this.Begin < 100 ){
        	//sb.setText();
        	return;
        }
    	drawData.DataBufAdd(getData());
    };
    //压缩前
    this.BeforeCompress=function(){
    	dt=this.End-this.Begin;
    };
    //压缩状态
    this.CompressingLine = function (fx,x,index) {
        var begin=0,end=0,dt1;
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
        var ds=drawData.DataLineCompress(begin, end);
        DrawData(index,ds);
    };
    //压缩状态结束 获取数据赋值
    this.CompressedLine = function (t1,t2) {
    	if (t1!=undefined){
        	this.Begin=t1;
        	this.End=t2;
        }
    	drawData.DataBufAdd(getData());
    };
    //移动中
    this.Move = function (dx, dy) {
        var dt = ((this.End - this.Begin) / _dr.Width) * dx;
        this.Begin -= dt;
        this.End -= dt;
    };
    //移动停止
    this.Moved = function () {
    	drawData.DataBufAdd(getData());
    };
    //重新赋予canvas对象
    this.ChangeCanvas = function (c) {
    	canvas=c;
    };
    //改变大小后重新绘图
    this.Resize = function () {
    	drawData.DataBufAdd(getData());
    };
    //获取节点信息
    this.GetDataPointInfo = function (ax,ay,index) {
    	return drawData.GetDataPointInfo(ax,ay,index);
    };
    //清空画布
    this.DrawDataClear=function(){
    	canvas.DrawDataClear();
    };
    //重新绘图
    this.ReDrowDataLine=function(index){
    	DrawData(index);
    };
    //xjt-150129
    this.Withdraw = function (begin,end) {
        this.Begin = begin;
        this.End = end;
    	drawData.DataBufAdd(getData());
    }
    //数据处理并进行对应的绘图操作
    function DrawData(index,ds){
    	var dataBuf=drawData.getDataBuf();
    	if(ds) dataBuf=ds;
    	for(var i=0;i<dataBuf.length;i++){
    		switch(dataBuf[i].type){
				case 1:
					DrawLine(dataBuf[i],index);
					break;
				case 3:
					DrawPoint(dataBuf[i],index);
					break;
				case 9:
					DrawRect(dataBuf[i],index);
					break;
    		}
    	}
    	var el = Ext.getDom("titl_detail_num_a_"+rec.get("dataTypeId"));
    	el.innerHTML="<font color='red' style='text-decoration:underline'>"+(dataBuf.length-1)+"</font>";
    }
    //画线  本质（绘图--矩形）
    function DrawLine(data,index){
    	canvas.SetDataLineColor(rec.get("Color"));
    	canvas.SetDataLineWeight(rec.get('Width'));
    	canvas.DrawDataLine(0,index*25+12,_dr.Width,index*25+12);
    }
    //画点  本质（绘图--圆形）
    function DrawPoint(data,index){
    	canvas.SetDataLineColor(rec.get("RectColor"));
    	canvas.DrawDataPoint(data.x,index*25+12);
    }
    //画矩形
    function DrawRect(data,index){
    	canvas.SetDataLineColor(rec.get("RectColor"));
    	canvas.DrawDataRect(data.x1,index*25+12,data.x2,index*25+12);
    }
    function getLeft(oldTime) {
        return (oldTime - me.Begin) * _dr.Width / (me.End - me.Begin);
    }
}