/*
 *canvas绘图对象
 */

Ext.define('js.relationChart.RCanvas',{
	constructor:function(isShow){
		return new RCanvas(isShow);
	}
});	

function RCanvas(isShow) {
    var el = document.createElement("div");
    with (el.style) {
        width = _relation_chart.clientWidth + 'px'; height = _relation_chart.clientHeight + 'px'; 
        overflow = "hidden"; position = "absolute";
        left = '0px'; top = '0px';
    }
    _relation_chart.appendChild(el);
    var c = document.createElement("canvas");
    el.appendChild(c);
    c.width = _relation_chart.clientWidth;
    c.height = _relation_chart.clientHeight;
    c.style.position = "absolute";
    c.style.left = '0px';
    c.style.top = '0px';
    if (window.isVml) c = G_vmlCanvasManager.initElement(c);
    var ctx = c.getContext("2d");
    if(isShow!=undefined&&isShow==false)c.style.display = 'none';
    //清除曲线、复位
    this.Clear = function (x) {
        ctx.clearRect(0, 0, c.width, c.height);
        if(x) c.style.left= x+'px';
        else  c.style.left= '0px';
       // c.style.left = (x!=undefined? x : 0) + 'px';
        c.style.top = '0px';
    };
    this.SetColor = function (color) {
        ctx.strokeStyle = ctx.fillStyle = color;
    };
    this.SetWeight = function (weight) {
        ctx.lineWidth = weight;
    };
    this.Show = function (show) {
        c.style.display = show?'':'none';
    };
    this.Begin = function () {
        ctx.beginPath();
    };
    this.DrawLine = function () {
        ctx.closePath();
        ctx.stroke();
    };
    this.Point = function (x, y) {
        with (ctx) {
            moveTo(x, y - 1);
            lineTo(x, y);
        }
    };
    this.Line = function (x1, y1, x2, y2) {
        with (ctx) {
            moveTo(x1, y1+1);
            lineTo(x2, y2);
        }
    };
    this.DrawRect = function (x1, y1, x2, y2) {
    	ctx.globalCompositeOperation="source-over";
        ctx.fillRect(x1, y1, x2 - x1, y2 - y1);
        ctx.strokeRect(x1, y1, x2 - x1, y2 - y1);
    };
    this.DrawPoint=function(x, y){
    	ctx.beginPath();
    	//if(Ext.isIE8m)ctx.arc(x, y, 0, 0, Math.PI*2);
    	ctx.arc(x,y, 5, 0, Math.PI*2);
    	if(Ext.isIE8m)ctx.stroke(true);
    	else ctx.stroke();
    	ctx.fill();
    };
    this.Move = function (dx, dy) {
        c.style.left = parseInt(c.style.left) + dx + 'px';
        c.style.top = parseInt(c.style.top) + dy + 'px';
    };
    this.Destroy = function () {
        _relation_chart.removeChild(el);
    };
    this.SetZindex = function (top) {
        el.style.zIndex = top||top == undefined ? 1 : 0;
    };
    this.RePos = function (left, top, height) {
        if (top == undefined) {
            el.style.top = '0px';
            el.style.height = _relation_chart.clientHeight + 'px';
            c.height = _relation_chart.clientHeight;
        }
        else {
            el.style.top = top + 'px';
            el.style.height = height + 'px';
            if (left != undefined) c.style.left = left + 'px';
            c.height = height;
        }
    };
    this.GetEl= function () {
        return el;
    };
    this.GetTileTop= function () {
        return parseInt(el.style.top);
    }
}