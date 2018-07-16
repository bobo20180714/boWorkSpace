/*
 *canvas绘图对象
 */

Ext.define('js.relationChart.DataCanvas',{
	constructor:function(){
		return new DataCanvas();
	}
});	

function DataCanvas() {
    //画布2
    var c2=document.getElementById(_relation_owner.id+"-DataEl-canvas");
    c2.style.position = "absolute";
    c2.style.left = '0px';
    c2.style.top = '0px';
    if (window.isVml) c2 = G_vmlCanvasManager.initElement(c2);
    var ctx2 = c2.getContext("2d");
    //其他画布 （遮挡、屏蔽）
    var c3=document.getElementById(_relation_owner.id+"-otherCanvas");
    c3.style.position = "absolute";
    c3.style.left = '0px';
    c3.style.top = '0px';
    if (window.isVml) c3 = G_vmlCanvasManager.initElement(c3);
    var ctx3 = c3.getContext("2d");
    
    //景科文新增 设置颜色
    this.SetDataLineColor = function (color) {
    	ctx2.strokeStyle = ctx2.fillStyle = color;
    };
    this.SetDataLineWeight = function (weight) {//设置宽度
    	ctx2.lineWidth = weight;
    };
    this.DrawDataLine=function(x1, y1, x2, y2){//画线 实质（矩形）
    	y1-=1.5;
    	y2+=1.5;
    	ctx2.globalCompositeOperation="source-over";
    	ctx2.beginPath();
    	ctx2.moveTo(x1, y1);
        ctx2.lineTo(x2, y1);
        ctx2.lineTo(x2, y2);
        ctx2.lineTo(x1, y2);
        if(Ext.isIE8m)ctx2.stroke(true);
    	else ctx2.stroke();
        ctx2.closePath();
    	ctx2.fill();
    };
    this.DrawDataPoint=function(x, y){//画点
    	ctx2.globalCompositeOperation="source-over";
    	ctx2.beginPath();
    	ctx2.arc(x,y, 5, 0, Math.PI*2);
    	if(Ext.isIE8m)ctx2.stroke(true);
    	else ctx2.stroke();
    	 ctx2.closePath();
    	ctx2.fill();
    };
    this.DrawDataRect = function (x1, y1, x2, y2) {//画矩形
    	y1+=3;
    	y2-=3;
    	ctx2.globalCompositeOperation="source-over";
    	ctx2.beginPath();
        ctx2.moveTo(x1, y1);
        ctx2.lineTo(x2, y1);
        ctx2.lineTo(x2, y2);
        ctx2.lineTo(x1, y2);
        if(Ext.isIE8m)ctx2.stroke(true);
    	else ctx2.stroke();
        ctx2.closePath();
        ctx2.fill();
    };
    this.DrawLayer = function (x1,x2) {//画阴影部分
        ctx3.fillStyle="rgba(50,100,213,0.3)";
        ctx3.globalCompositeOperation="source-over";
        ctx3.beginPath();
        ctx3.moveTo(x1, 0);
        ctx3.lineTo(x2, 0);
        ctx3.lineTo(x2, parseInt(c3.height));
        ctx3.lineTo(x1, parseInt(c3.height));
        ctx3.closePath();
        ctx3.fill();
    };
    this.Shield=function(x1,x2){//画屏蔽部分
    	if(x2<=x1) return;
    	ctx3.fillStyle="#e6e6e6";
        ctx3.globalCompositeOperation="source-over";
        ctx3.beginPath();
        ctx3.moveTo(x1, 0);
        ctx3.lineTo(x2, 0);
        ctx3.lineTo(x2, parseInt(_relation_drawarea.Height-2));
        ctx3.lineTo(x1, parseInt(_relation_drawarea.Height-2));
        ctx3.closePath();
        ctx3.fill();
        if(c2.height==0) return;
        ctx3.beginPath();
        ctx3.moveTo(x1, _relation_drawarea.Height);
        ctx3.lineTo(x2, _relation_drawarea.Height);
        ctx3.lineTo(x2, parseInt(c3.height));
        ctx3.lineTo(x1, parseInt(c3.height));
        ctx3.closePath();
        ctx3.fill();
    };
    this.Threshold=function(h1,h2,color){//门限
    	ctx3.fillStyle = color;
    	ctx3.strokeStyle = color;
    	ctx3.beginPath();
    	ctx3.moveTo(0, h1-0.5);
    	ctx3.lineTo(c3.width, h1-0.5);
    	ctx3.lineTo(c3.width, h1+0.5);
    	ctx3.lineTo(0, h1+0.5);
        if(Ext.isIE8m)ctx3.stroke(true);
    	else ctx3.stroke();
        ctx3.closePath();
        ctx3.fill();
        
    	ctx3.fillStyle = color;
    	ctx3.strokeStyle = color;
    	ctx3.beginPath();
    	ctx3.moveTo(0, h2-0.5);
    	ctx3.lineTo(c3.width, h2-0.5);
    	ctx3.lineTo(c3.width, h2+0.5);
    	ctx3.lineTo(0, h2+0.5);
        if(Ext.isIE8m)ctx3.stroke(true);
    	else ctx3.stroke();
        ctx3.closePath();
        ctx3.fill();
    };
    
    this.DrawDataClear = function (x) {//清空绘图2
        ctx2.clearRect(0, 0, c2.width, c2.height);
        c2.style.left = (x!=undefined? x : 0) + 'px';
        c2.style.top = '0px';
    };
    this.DataLineMove = function (dx, dy) {//移动绘图2
        c2.style.left = parseInt(c2.style.left) + dx + 'px';
        c2.style.top = parseInt(c2.style.top) + dy + 'px';
    };
    
    this.OtherDataClear = function (x) {//清空绘图3
        ctx3.clearRect(0, 0, c3.width, c3.height);
        c3.style.left = (x!=undefined? x : 0) + 'px';
    };
    this.OtherDataMove = function (dx, dy) {//移动绘图3
        c3.style.left = parseInt(c3.style.left) + dx + 'px';
        c3.style.top = parseInt(c3.style.top) + dy + 'px';
    };
}