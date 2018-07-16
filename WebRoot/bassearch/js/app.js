
Ext.Loader.setConfig({ enabled: true });

Ext.require([
	'Ext.container.Viewport',
	'Ext.tab.*',
	'Ext.grid.*',
    'Ext.data.*',
    'Ext.toolbar.Paging',
    'Ext.form.*',
    'Ext.panel.Panel'
]);
_url=(location+'').replace('bassearch/bassearch.html','rest/');
var ua = navigator.userAgent.toLowerCase();
//浏览器类型为IE8以下
var isIE = ua.match(/msie\s[34567]\.\d/);
//数值宽度=10,有效位=7
var numWidth = 10;
//恢复次数
var restoreNum = 10;
//登录信息,状态栏
var login,sb;
//用于获取浏览器可见区尺寸
var pageInfo = new function(){ 
	var w,h,cw,ch;
	if(typeof window.innerWidth == 'number'){
	 	w=window.innerWidth;
	 	h=window.innerHeight;
	}
	else if(document.documentElement&&(document.documentElement.clientWidth||document.documentElement.clientHeight)){
	 	w=document.documentElement.clientWidth;
	 	h=document.documentElement.clientHeight;
	}
	else if(document.body&&(document.body.clientWidth||document.body.clientHeight))	{
	 	w=document.body.clientWidth;
	 	h=document.body.clientHeight;
	}
	if(document.documentElement&&(document.documentElement.scrollHeight||document.documentElement.offsetHeight)){
	 	if(document.documentElement.scrollHeight>document.documentElement.offsetHeight){
	  		cw=document.documentElement.scrollWidth;
	  		ch=document.documentElement.scrollHeight;
	 	}
	 	else {
	  		cw=document.documentElement.offsetWidth;
	  		ch=document.documentElement.offsetHeight;
	 	}
	}
	else if(document.body&&(document.body.scrollHeight||document.body.offsetHeight)){
	 	if(document.body.scrollHeight>document.body.offsetHeight) {
	  		cw=document.body.scrollWidth;
	  		ch=document.body.scrollHeight;
	 	}
	 	else{
	  		cw=document.body.offsetWidth;
	  		ch=document.body.offsetHeight;
	 	}
	}
	else {
	 	cw=w;
	 	ch=h;
	} 
 	this.width=cw;
 	this.height=ch;
};
//根据不同浏览器选择窗口打开方式
var openPage=function(url){	
	if (!ua.match(/msie\s[3456789]\.\d|firefox\/([\d\.]+)/)) {
	    var a = document.createElement('a');
	    a.target = '_blank';
	    a.href = url;
        a.click();
	}
    else {
        window.open(url, "_blank", 'left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-100) +',toolbar=yes,menubar=yes');
    }
};
//用于获取当前时间
var Now = new function () {
	var t;
	this.getBegin=function(ms){
		if(ms==undefined)ms=86400000;//默认最近1天
		t = new Date();
		return Ext.Date.format(new Date(t.getTime() - ms),'Y-m-d H:i');
	},
	this.getEnd=function(){
		return Ext.Date.format(t,'Y-m-d H:i');
	}
};
//Web时钟对象,用于获取WEB时间
var WebTime = function () {
	var t,label;
	/*setInterval(function () {
		Ajax.send('webtime',{},function(json){
			t=json.WebTime;
			if(label)label.setText(new Date(t).format('Y-m-d H:i:s'));
		});	
	},500);*/
	this.getMs=function(){
		if(t)return t;
		return new Date().getTime();
	};
	this.setLabel=function(o){
		label=o;
	};
}
//时间字符串转换为Date
Date.parse=function(ts,isFmt){
	return isFmt==undefined?new Date(ts.replace(/-/g, '/')):Ext.Date.parse(ts,'Y-m-d H:i:s.u');
}
//时间字符串转换为毫秒（ms）
String.prototype.getTime=function(){
	return Date.parse(this).getTime();
}
//去除前后空格
String.prototype.trim=function(){
	return this.replace(/(^\s*)|(\s*$)/g,'');
}
//返回指定格式字符串
Date.prototype.format=function(fmt){
	return Ext.Date.format(this,fmt);
}
//返回标准时间:Y-m-d H:i:s.u
Date.toStr = function (ms) {
    return new Date(ms*1).format('Y-m-d H:i:s.u');
}
//移除数组元素
Array.prototype.remove=function(i){
	typeof i == 'number'? this.splice(i,1):Ext.Array.remove(this,i);
}
//添加数组元素到指定位置
Array.prototype.add=function(i,e){
	e==undefined?this.push(i):this.splice(i,0,e);
}
//把长整型时间（毫秒）精确到秒
Number.prototype.trunc=function(){
	return Math.floor(this/1000)*1000;
}
//ID生成器
var myId=new function(){
	var id=1;
	this.getVal=function(){
		return id++;
	}
};
//阻止事件冒泡
function stopBubble(e) {
	if ( e.stopPropagation )
   		e.stopPropagation();
 	else
   		event.cancelBubble = true;
}
//自定义BUTTON
Ext.define('Ext.ux.MyButton', {
	extend:'Ext.button.Button',
	alias: 'widget.mybutton', 
	setEnable : function (enable) {	    
	    if (enable==undefined) enable = true;
	    var icon=this.icon;
	    if (enable) {
	    	if(icon){
	        if(icon.indexOf('/b')>-1)icon=icon.replace('/b', '/');
	        this.setIcon(icon);
	        this.setDisabled(false);
	    	}else{
	    		this.setDisabled(false);
	    	}
	    }
	    else {
	    	if(icon){
	        if(icon.indexOf('/b')<0)icon=icon.replace('/', '/b');
	        this.setIcon(icon);
	 	        if(icon.indexOf('shield')>-1||icon.indexOf('clearAll')>-1){
	 	        	this.setDisabled(false);
	 	        }else{
	        this.setDisabled(true);
	 	        }
	    	}else{
	    		this.setDisabled(true);
	    	}
	    }
	}
});
//定义线型选择列表
Ext.define('LineWidthPanel_search',{
	constructor:function(handler){
		var sp = Ext.getDom('SP');
	    var el, e;
	    for (var y = 12, w = 1; y < 100; y += 15, w += 0.5) {
	        el = document.createElement("div");
	        el.w = w;
	        with (el.style) {
	            width = '108px'; height = '12px'; overflow = "hidden"; position = "absolute";
	            left = '4px'; top = y - 7 + 'px'; border = "1px solid #fff";
	        }
	        el.style.top = y - 7 + 'px'
	        el.onmouseover = function () { this.style.borderColor = "#99BBE8"; this.style.cursor = "pointer" }
	        el.onmouseout = function () { this.style.borderColor = "#fff"; this.style.cursor = "" }
	        el.onclick = function () {if (handler) handler(this.w);}
	        sp.appendChild(el);
	        e = document.createElement("div");
	        with (e.style) {
	            width = '102px'; height = '12px'; overflow = "hidden"; position = "absolute";
	            left = '4px'; top = '-2px'; backgroundImage = 'url(img/xk' + w + '.ico)';
	        }
	        el.appendChild(e);
	    }
	    return sp;
	}
});

//用于AJAX通讯
var Ajax={
	send: function (url, data, callback, asyn) {
        Ext.Ajax.request({
		    url: url,
		    params: data,
		    method : 'POST',  
		    timeout : 600000,
            async: asyn==undefined||asyn,
		    success: function(response,options ){
		    	var sessionstatus=response.getResponseHeader("sessionstatus");
		    	if(sessionstatus=='timeout'){
		    		window.location.href = 'login.jsp';
		    		return;
		    	}
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

Ext.onReady(function () {	
	Ext.Ajax.timeout = 600000;//Ajax超时时间（10分钟）
	//创建登录界面
	login=Ext.create('js.login');
	login.success.add(function(user){
		login.hide();					
		if(!window._owner)init();
		login.setInfo(user);
	});
	//判断网络类型
	/*Ajax.send('nettype',{},function(json){
		login.type=json.NetType;
		switch(login.type){
			case 'lan'://内网
				init();
				break;
			case 'wan'://外网				
				login.show();
				break;
		}
	});	*/
	//主程序初始化
	function init(){
		//创建状态栏
		sb = Ext.create('Ext.toolbar.Toolbar', {
			id:'sb',
	    	padding:0,
	    	height:20,
	    	items:['<div id="_LOAD" style="width:18px;height:18px;background:url(img/loading.gif) no-repeat center center transparent;display:none;"/>',{
	        	itemId:'txt',
	        	xtype:'tbtext',
	        	text: '就绪'        
	        },'->',''],
	        setText:function(txt,isIcon){
	        	Ext.getDom('_LOAD').style.display=isIcon?'block':'none';
	        	this.down('#txt').setText(txt!=undefined?txt:'就绪');
	        },
	        showBusy:function(txt){
	        	Ext.getDom('_LOAD').style.display='block';
	        	this.down('#txt').setText(txt||'查询中...');
	        }
	    });
		//创建Web时钟对象
		_WebTime=new WebTime();
		//创建主界面
		Ext.create('js.View');
	    //绘图区DOM节点
	    _owner =isIE?Ext.getDom('Chart').childNodes[1].childNodes[0].firstChild : Ext.getDom('Chart').childNodes[0].childNodes[0].firstChild;
		//创建绘图区管理器实例
	    _drawarea = Ext.create('js.DrawAreaManage');
	    //创建曲线管理器实例
	    _lines = Ext.create('js.LineManage',_drawarea);
	    //创建命令管理器实例
	    _cmd = Ext.create('js.CommandManage');
	}
	init();
});
//解决IE下关键字无法选择问题（无效）
function onsel(e){
	e = e || window.event; 
	var obj = e.target || e.srcElement;
	if(obj.tagName=='INPUT') return true;
	return false;
}
//禁止浏览器Backspace后退快捷键（无效）
document.onkeydown=document.onkeypress=function(e){
	e = e || window.event;
	var obj = e.target || e.srcElement;
	if(obj.tagName=='INPUT'&&obj.value.length==0&&e.keyCode==8)
		return false;
}
Ext.application({
	name : 'query',
	appFolder : 'query/app',
	controllers : ['query.controller.search.basicSearch.BasicSearchController' ]
});