/*
 * 自定义JS公共库函数
 * 2015.8.22
 */
//全局url(到weijingwu)
var _url=(window.location+'').split('base')[0]+'base/';
/*用于拦截器，不作他用*/
if(window.$){
	$.ajaxSetup({'error':function(XMLHttpRequest, textStatus, errorThrown){
		if(XMLHttpRequest.status==408){
		   window.location = _url+"login.jsp";
		}
	},beforeSend:function(xhr){
		xhr.setRequestHeader('Source',window.location.href);
	}}); 
}
function goback(){
	 window.location = _url+"login.jsp";
}
/*用于拦截器，不作他用*/
if(!window.JSON){
	 $('head').append($("<script type='text/javascript' src='js/json2.js'>"));
}
/**
 *Date转时间串
 *@param fmt:yyyy-MM-dd hh:mm:ss
 */
Date.prototype.format = function(fmt){
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
    	fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
};
/**
 * 限制INPUT输入数字
 */
function keyPress(includes,type) {   
	function exist(k){
		if(typeof includes=='string'){
			return includes.indexOf(String.fromCharCode(k))>-1;
		}
		else{
			for(var i=0;i<includes.length;i++){
				if(includes[i].charCodeAt()==k)
					return true;
			}
		}		
		return false;
	}
	if(type!=undefined&&type=='其它')
		return true;
    var keyCode = event.keyCode;    
    if (keyCode >= 48 && keyCode <= 57||includes!=undefined&&exist(keyCode)){    
        event.returnValue = true;    
    } else {    
        event.returnValue = false;    
    }    
}
/**
 * 返回满足条件数组元素
 * @param arr-数组
 * @param key-元素（Object）的成员名
 * @param val-匹配值
 * @returns 数组元素
 */
function getElementByKey(arr,key,val){
	for(var i=0;i<arr.length;i++){
		if(arr[i][key]==val)
			return arr[i];
	}
	return null;
}
/**
 * 返回url参数值
 * @param name-参数名
 * @returns 参数值
 */
function getUrlParam(name){  
    //构造一个含有目标参数的正则表达式对象  
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");  
    //匹配目标参数  
    var r = window.location.search.substr(1).match(reg);  
    //返回参数值  
    if (r!=null) return unescape(r[2]);  
    return null;  
}
/**
 * 写cookies
 * @param name
 * @param value
 */
function setCookie(name,value,days){
	var exp = null;
	if(days){
		exp=new Date();
		exp.setTime(exp.getTime() + days * 24 * 60 * 60 * 1000);
	}
	else{
		exp=new Date(2099,11,31);
	}
	document.cookie = name + "=" + escape(value) + ";path=/;expires=" + exp.toGMTString();
} 
/**
 * 读取cookies
 * @param name
 * @returns
 */
function getCookie(name){ 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)"); 
    if(arr=document.cookie.match(reg)) 
        return unescape(arr[2]); 
    else 
        return null; 
}
/**
 * 删除cookies
 * @param name
 */
function delCookie(name){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";path=/;expires="+exp.toGMTString();
}
/**
 * 去除字符串左右两端的空格
 * @author Lenovo
 *
 */
String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
/**
 * 检查身份证格式是否正确
 * @param idCard
 * @returns {Boolean}
 */
function checkIdCard(idCard){
	//15位和18位身份证号码的正则表达式
	var regIdCard=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
	//如果通过该验证，说明身份证格式正确，但准确性还需计算
	if(regIdCard.test(idCard)){
		if(idCard.length==18){
			var idCardWi=[7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]; //将前17位加权因子保存在数组里
			var idCardY=[1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2]; //这是除以11后，可能产生的11位余数、验证码，也保存成数组
			var idCardWiSum=0; //用来保存前17位各自乖以加权因子后的总和
			for(var i=0;i<17;i++){
				idCardWiSum+=idCard.substring(i,i+1)*idCardWi[i];
			}
			var idCardMod=idCardWiSum%11;//计算出校验码所在数组的位置
			var idCardLast=idCard.substring(17);//得到最后一位身份证号码
			//如果等于2，则说明校验码是10，身份证号码最后一位应该是X
			if(idCardMod==2){
				if(idCardLast=="X"||idCardLast=="x"){
					return true;
				}else{
					return false;
				}
			}else{
				//用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
				if(idCardLast==idCardY[idCardMod]){
					return true;
				}else{
					return false;
				}
			}
		} 
	}else{
		return false;
	}
}
/**
 * 设为首页
 * @param obj
 * @param vrl
 */
function setHome(obj,vrl){
    try{
        obj.style.behavior='url(#default#homepage)';
        obj.setHomePage(vrl);
    }
    catch(e){
        if(window.netscape) {
            try {
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            }
            catch (e) {
                 alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。");
            }
            var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
            prefs.setCharPref('browser.startup.homepage',vrl);
         }
    }
}
/**
 * 加入收藏
 * @param sURL
 * @param sTitle
 */
function addFavorite(sURL, sTitle){
    try
    {
        window.external.addFavorite(sURL, sTitle);
    }
    catch (e)
    {
        try
        {
            window.sidebar.addPanel(sTitle, sURL, "");
        }
        catch (e)
        {
            alert("加入收藏失败，请使用Ctrl+D进行添加");
        }
    }
}
/**
 * 用于文件下载
 * @param url
 */
function download(url){	
	if (!window._df) {
    	_df = document.createElement('iframe');    	
    	_df.style.visibility = "hidden";
    	document.body.appendChild(_df);	
    }
	_df.src=url;
}
//显示遮罩层    
function showMask(){     
    $("#mask").css("height",$(document).height());     
    $("#mask").css("width",$(document).width());     
    $("#mask").show();     
}  
//隐藏遮罩层  
function hideMask(){     
    $("#mask").hide();     
}  
//把Json字符串转换成Json对象
function getObject(json){
	if(json==""||json=="null"||json==null||json==undefined)
		return null;
	return eval("("+json+")");
}
//动态加载CSS(依赖于jquery)
function loadCss(cssFile){
	$('head').append('<link type="text/css" rel="stylesheet" href="'+cssFile+'">');
}
//动态加载JS文件
function loadJs(jsFile){
	$('head').append('<script type="text/javascript" src="'+jsFile+'"></script>');
}
//动态创建JS对象(不支持IE9以下版本)
function createJs(jsFile){
	jsFile=jsFile.indexOf('?')>-1?jsFile:jsFile+'?';
	jsFile=jsFile+'_dc='+new Date().getTime();
	var xhr = new XMLHttpRequest();	
	xhr.open('GET', jsFile, false);
	xhr.send(null);
	if(xhr.status==200){
		return eval(xhr.responseText+'\n//@ sourceURL='+jsFile);
	}
}
//生成随机字符串
function randomString(len){
	len = len || 32;
	var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
	var maxPos = $chars.length;
	var pwd = '';
	for (var i = 0; i < len; i++) {
		pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
	}
	return pwd;
}
//克隆JS对象
function clone(obj){  
    var o;  
    if(typeof obj == "object"){  
        if(obj === null){  
            o = null;  
        }else{  
            if(obj instanceof Array){  
                o = [];  
                for(var i = 0, len = obj.length; i < len; i++){  
                    o.push(clone(obj[i]));  
                }  
            }else{  
                o = {};  
                for(var k in obj){  
                    o[k] = clone(obj[k]);  
                }  
            }  
        }  
    }else{  
        o = obj;  
    }  
    return o;  
} 
/**
 * 加载中
 * @param msg
 */
function loading(msg){
	if(!$('#loadingToast').length){
		$(document.body).append([
            "<div id='loadingToast' class='weui_loading_toast' style='display:none;'>",
			"   <div class='weui_mask_transparent'>",
			"   <div class='weui_toast'>",
			"       <div class='weui_loading'>",
			"           <div class='weui_loading_leaf weui_loading_leaf_0'/>",
			"           <div class='weui_loading_leaf weui_loading_leaf_1'/>",
			"           <div class='weui_loading_leaf weui_loading_leaf_2'/>",
			"           <div class='weui_loading_leaf weui_loading_leaf_3'/>",
			"           <div class='weui_loading_leaf weui_loading_leaf_4'/>",
			"           <div class='weui_loading_leaf weui_loading_leaf_5'/>",
			"           <div class='weui_loading_leaf weui_loading_leaf_6'/>",
			"           <div class='weui_loading_leaf weui_loading_leaf_7'/>",
			"           <div class='weui_loading_leaf weui_loading_leaf_8'/>",
			"           <div class='weui_loading_leaf weui_loading_leaf_9'/>",
			"           <div class='weui_loading_leaf weui_loading_leaf_10'/>",
			"           <div class='weui_loading_leaf weui_loading_leaf_11'/>",
			"       </div>",
			"       <p class='weui_toast_content'></p>",
			"   </div>",
			"</div>"].join(''));
	}
	$('#loadingToast').show().find('.weui_toast_content').text(msg?msg:'加载中···');
}
/**
 * 停止加载
 */
function stopLoading(){
	$('#loadingToast').hide();
}
//生成UUID
function uuid(){
	if(typeof UUID !== 'function'){
		loadJs(_url+'js/uuid.js');
	}
	return new UUID().id;
}
//获取图片
function getUrl(img,blob){	
	/*if(blob){
		return blob;
	}
	else if(img.indexOf('.svg')>-1){
		$.ajax({  
		     url: 'rest/file/getSvg?id='+img,  
		     type: 'POST',					     
		     async: false
		});
		return 'svg/'+img;
	}
	else{
		return 'rest/file/getImg?id='+img;
	}*/
	return blob?blob:'rest/file/getImg?id='+img;
}
//根据表达式返回指定值对应图片url
function getImgByVal(exps,val){
	for(var i=0;i<exps.length;i++){
		var e=exps[i];
		if(!e.exp)continue;
		var parts=e.exp.split('||');
		for(var j=0;j<parts.length;j++){
			var part=parts[j].trim();
			switch(part[0]){
				case '=':
					if(val==part.val())
						return e.img;
					break;
				case '>':
					if(part[1]=='='){
						if(val>=part.val())
							return e.img;
					}
					else{
						if(val>part.val())
							return e.img;
					}
					break;
				case '<':
					if(part[1]=='='){
						if(val<=part.val())
							return e.img;
					}
					else{
						if(val<part.val())
							return e.img;
					}
					break;
				case '(':
					switch(part[part.length-1]){
						case ')':
							var v=part.split(',');
							if(v[0].val()<val && val<v[1].val())
								return e.img;
							break;
						case ']':
							var v=part.split(',');
							if(v[0].val()<val && val<=v[1].val())
								return e.img;
							break;
						default:
							throw new Error('表达式错误！'); 
					}
					break;
				case '[':
					switch(part[part.length-1]){
						case ')':
							var v=part.split(',');
							if(v[0].val()<=val && val<v[1].val())
								return e.img;
							break;
						case ']':
							var v=part.split(',');
							if(v[0].val()<=val && val<=v[1].val())
								return e.img;
							break;
						default:
							throw new Error('表达式错误！'); 
					}
					break;
				default:
					throw new Error('表达式错误！'); 
			}				
		}
	}
}
