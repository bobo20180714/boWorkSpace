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
Ext.BLANK_IMAGE_URL ='ext/resources/ext-theme-classic/images/tree/s.gif';
var ua = navigator.userAgent.toLowerCase();
//下载数据最大天数
var limitday = 3;
//浏览器类型为IE8以下
var isIE = ua.match(/msie\s[34567]\.\d/);
//数值宽度=10,有效位=7
var numWidth = 10;
//恢复次数
var restoreNum = 10;
//登录信息,状态栏
var login;
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
//禁止浏览器Backspace后退快捷键
document.onkeydown=document.onkeypress=function(e){
	var ev=e||window.event;
	var obj=ev.target||ev.srcElement;
	var t=obj.type||obj.getAttribute('type');
	if(ev.keyCode==8&&t!='password'&&t!='text'&&t!='textarea') return false;
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
//时间字符串转换为Date
Date.parse=function(ts,isFmt){
	return isFmt==undefined?new Date(ts.replace(/-/g, '/')):Ext.Date.parse(ts,'Y-m-d H:i:s.u');
}
//时间字符串转换为毫秒（ms）
String.prototype.getTime=function(isFmt){
	return Date.parse(this,isFmt).getTime();
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
//定义线型选择列表  mxc  关联查询 专用线型管理列表
Ext.define('LineWidthPanel',{
	constructor:function(handler){
		var sp = Ext.getDom('RSP');
	    var el, e;
	    for (var y = 12, w = 1; y < 100; y += 15, w += 0.5) {
	        el = document.createElement("div");
	        el.w = w;
	        with (el.style) {
	            width = '108px'; height = '12px'; overflow = "hidden"; position = "absolute";
	            left = '4px'; top = y - 7 + 'px'; border = "1px solid #fff";
	        }
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

//用于session失效后返回登录页面
Ext.Ajax.on('requestexception',function(conn,response,options){
	var sessionstatus=response.getResponseHeader("sessionstatus");
	if(sessionstatus=='timeout' || response.status=='999'){
		window.location.href = 'login.jsp';
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

var getColor=function(i){
	var color = ["#fafdff", "#e3f0ff"];
	if(i%2!=0) return color[0];
	return color[1];
};
//判断元素是否在数组中
var contains=function(a,obj){
	for(var i=0;i<a.length;i++){
		if(a[i]==obj)return true;
	}
	return false;
}
//获取随即数组
var getRandom=function(start,end,count){
	var original=new Array;
	for(var i=0;i<count;i++){
		original[i]=(Math.random()*(end-start)+start).toFixed(1);
	}
	original.sort(function(){return (Math.random()*(end-start)+start).toFixed(1);});
	return original;
};
Array.prototype.remove=function(dx){
	if(typeof dx == 'object'){
		Ext.Array.remove(this,dx);
		return;
	}
	if(isNaN(dx)||dx>this.length) return false;
	for(var i=0,n=0;i<this.length;i++){
		if(this[i]!=this[dx]) this[n++]=this[i];
	}
	this.length-=1;
};
var Arrarycompare=function(prop){
	return function(obj1,obj2){
		var val1=obj1[prop];
		var val2=obj2[prop];
		if(!isNaN(Number(val1))&&!isNaN(Number(val2))){
			val1=Number(val1);
			val2=Number(val2);
		}
		if(val1<val2){
			return -1;
		}else if(val1>val2){
			return 1;
		}else{
			return 0;
		}
	}
}
var getColorByStr=function(str){
	var v="";
	for(var i=0;i<str.length;i++){
		 v+=str.charCodeAt(i).toString(16).split("").reverse().join("");
	}
	v+="000000";
	return "#"+v.substr(0,6);
};
var getColorByNum=function(i){
	var color = ["#e26368", "#0eb1ae", "#995aad",  "#f48057", "#f3c412", "#c79e00", "#068397", "#005f6e","#bd8f7d", "#4d7da2"];
	var color2 = ["#982327", "#017875",  "#723286","#cb4e21", "#c79e00", "#4f3d6b", "#005f6e", "#1570af", "#714636", "#2c587a"];
	return [color[i],color2[i]];
};
//获取个人信息
var userInfo=null;
var roles = null;
/*Ajax.send('login/findloginuser.edq',null,function (obj, opts) {
	if(obj.success){
		userInfo=obj.user;
		roles = obj.roles;
		limitday = userInfo.org_limitday;
	}else{
		Ext.example.msg('系统提示', '获取个人信息失败请联系管理员!');
	}
},false);*/
function logOut(){
	Ajax.send('login/logout.edq',null,function (obj, opts) {
		if(obj.isLogout){
			window.location.href = 'login.jsp';
		}else{
			Ext.example.msg('系统提示', '注销失败!');
		}
	});
}
function download(url,isRelationQuery){
	if (!window._df) {
    	_df = document.createElement('iframe');    	
    	_df.style.visibility = "hidden";
    	document.body.appendChild(_df);	
    }
	var el=Ext.getCmp(isRelationQuery?'Relation_MaskRegion':'MaskRegion').getEl();
	if(el){
		_download_timeout=setInterval(function(){
			Ajax.send('tmdata/isDownloaded.edq',null,function(json, opts){
				if(json.isDownloaded){
					el.unmask();
					clearInterval(_download_timeout);
				}
			});
		},1000);
		el.mask("数据下载中……");
	}	
	_df.src=url;
}
//xjt-2015年1月24日
var _precondId=Ext.urlDecode(location.search.substring(1))['id'];
Ext.application({
	name : 'query',
	appFolder : 'query/app',
	controllers : ['search.basicSearch.BasicSearchController','query.controller.relation.relationSearch.RelationSearchController' ],
	launch : function() {
		Ext.create('query.view.queryView');
	}
});
