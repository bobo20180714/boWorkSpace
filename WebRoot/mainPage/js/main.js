var allLimitMenu = null;
var firstMenu = null;
var secondMenu = null;
function itemclick(item) {
	if(item){
		openSecondMenu(item.id);
	}
	$("#home").css({
		height : $("#layout1")[0].clientHeight
	});
	$("#home").css({
		width : $("#layout1")[0].clientWidth - 2
	});
}

//打开二级菜单
function openSecondMenu(menuId,count){
	$(".select").removeClass("select");
	for(var i=0;i<secondMenu.length;i++){
		if(secondMenu[i].pkId == menuId){
			if(secondMenu[i].showType == "1"){
				f_addTab(secondMenu[i].pkId,secondMenu[i].resName,basePath+secondMenu[i].resValue);
			}else if(secondMenu[i].showType == "2"){
				if(count == 1){
					f_addTab(secondMenu[i].pkId,secondMenu[i].resName,basePath+secondMenu[i].resValue);
				}else{
					window.open(basePath + "mainPage/main.jsp?firstPageCode="+secondMenu[i].resFatherCode+"&secondMenuCode="+secondMenu[i].resCode+"&secondMenuText="+secondMenu[i].resName+"&menuUrl="+secondMenu[i].resValue);
				}
			}else if(secondMenu[i].showType == "3"){
				var functionName = secondMenu[i].resValue;
				var funcObj = eval(functionName);
				new funcObj();
			}
			$("#"+secondMenu[i].resFather).addClass("select");
			break;
		}
	}
}

//打开一级菜单
function openFirstMenu(firstPageCode){
	$(".select").removeClass("select");
	//如果一级菜单有表达式，查找一级节点的表达式和打开方式
	var firstMenuTemp = getFirstMenu(firstPageCode);
	if(firstMenuTemp && firstMenuTemp.resValue){
		if(firstMenuTemp.showType == "1"){
			f_addTab(firstMenuTemp.pkId,firstMenuTemp.resName,basePath+firstMenuTemp.resValue);
		}else if(firstMenuTemp.showType == "2"){
			if(contuneOpen == "false"){
				f_addTab(firstMenuTemp.pkId,firstMenuTemp.resName,basePath+firstMenuTemp.resValue);
			}else{
				window.open(basePath + "mainPage/main.jsp?firstPageCode="+firstMenuTemp.resCode+"&contuneOpen=false");
			}
		}else if(firstMenuTemp.showType == "3"){
			var functionName = firstMenuTemp.resValue;
			var funcObj = eval(functionName);
			new funcObj();
		}
		$("#"+firstMenuTemp.pkId).addClass("select");
	}else{
		//查找第一个二级节点的id值
		var secondId = getSecondMenuId(firstPageCode);
		openSecondMenu(secondId,1);
		if(secondMenuCode && secondMenuCode != 'null'){
			if(menuUrl && menuUrl != 'null'){
				f_addTab(secondMenuCode,secondMenuText,basePath+menuUrl);
			}else{
				itemclick({id:secondMenuCode});
			}
		}
	}
}


var tab = null;
//树信息
$(function() {
	
	//布局
	$("#layout1").ligerLayout({ 
		leftWidth : 190,
		height : '100%',
//		space : 2,
		onHeightChanged : f_heightChanged
	});
	var height = $(".l-layout-center").height();
	//Tab
	$("#framecenter").ligerTab({
		height : height
	});
	tab = $("#framecenter").ligerGetTabManager();
	
	//获取权限
	allLimitMenu = getLimitMenus();
	
	firstMenu = allLimitMenu.firstList;
	secondMenu = allLimitMenu.secondList;
	addFistMenu();
	addSecondMenu();
	
	if(firstPageCode && (!secondMenuCode || secondMenuCode == 'null')){
		openFirstMenu(firstPageCode);
	}
	
	if(secondMenuCode && secondMenuCode != 'null'){
		if(menuUrl && menuUrl != 'null'){
			f_addTab(secondMenuCode,secondMenuText,basePath+menuUrl);
		}else{
			itemclick({id:secondMenuCode});
		}
	}
	
	$(".menulink").live('mouseover', function ()
    {
		this.style.cursor='pointer';
		$(this).addClass("over");
		$(".m-second-menu-item[id*='"+this.id+"']").show();
        onMouseOver($(this).attr("lev"),this);
    }).live('mouseout', function ()
    {
        $(this).removeClass("over");
        $(".m-second-menu-item[id*='"+this.id+"']").hide();
    });
});

function getSecondMenuId(firstpagecode){
	var secondId = null;
	var firstpageid = null;
	for (var i = 0; i < firstMenu.length; i++) {
		if(firstMenu[i].resCode == firstpagecode){
			firstpageid = firstMenu[i].pkId;
			break;
		}
	}
	if(firstpageid){
		for(var i=0;i<secondMenu.length;i++){
			if(secondMenu[i].resFather == firstpageid){
				secondId = secondMenu[i].pkId;
				break;
			}
		}
	}
	return secondId;
}

/**
 * 查找一级菜单
 * @param firstpagecode
 * @returns
 */
function getFirstMenu(firstpagecode){
	for (var i = 0; i < firstMenu.length; i++) {
		if(firstMenu[i].resCode == firstpagecode){
			return firstMenu[i];
		}
	}
	return null;
}

/**
 * 获取菜单
 */
function getLimitMenus(){
	var allMenu = new Array();
	$.ajax({
		url:basePath+"rest/poplarLogin/getLimit",
		async:false,
		success:function(rsData){
			allMenu = eval('('+rsData+')');
		}
	});
	return allMenu;
}

/**
 * 显示一级菜单
 */
function addFistMenu(){
	$("body").css("min-width",139*firstMenu.length+170+"px");
	$(".wrap").css("min-width",139*firstMenu.length+170+"px");
	for (var i = 0; i < firstMenu.length; i++) {
		var code = firstMenu[i].resCode;
		var id = firstMenu[i].pkId;
		var temp = '<img src="images/line.png" width="1" height="48" /> '+
		'<p id="'+id+'" class="menulink" lev="first" onclick="javascript:openFirstMenu(\''+code+'\')"><img style="margin-top:12px;margin-left:20px;" src="'+basePath+'mainPage/menuImg/'+code+'_t.png" />'+
		'<span style="margin-left:5px;">'+firstMenu[i].resName+'</span></p>';
		$("#fistMenu").append(temp);
	}
	$("#fistMenu").css("min-width",139*firstMenu.length+"px");
	$(".l-layout-center").css("min-width",139*firstMenu.length+170+"px");
}

function addSecondMenu(){
	//一级菜单数量
	var firstNum = firstMenu.length;
	
	var m_left = -10;
	var L_R = 'left:0px;';
	var m_leftStyle = 'margin-left: '+m_left;
	$("p").each(function(index, domEle){
		//二级菜单数量
		var secondNum = 0;
		for(var i=0;i<secondMenu.length;i++){
			if(secondMenu[i].resFather == domEle.id){
				secondNum++;
			}
		}
		if(index > 0){
			var firstSurplus = firstNum - index;
			if(firstSurplus <= 2 && firstNum>=5){
				if(secondNum > firstSurplus * 1){
					//如果二级菜单的长度超出一级菜单，则二级菜单右对齐
					L_R = 'right:0px; min-width:100px;';
					m_leftStyle='margin-right:-10';
				}else{
					L_R = 'left:0px;';
					m_leftStyle = 'margin-left: '+m_left;
				}
				
			}else{
				L_R = 'left:0px;';
				m_leftStyle = 'margin-left: '+m_left;
			}
		}
		if(secondNum > 0){
			var smenuHtml=[];
			smenuHtml.push('<div id="'+domEle.id+'_smenu" class="m-second-menu m-second-menu-item" style="'+m_leftStyle+'px;margin-top: 50px; '+L_R+'">');
			smenuHtml.push('<div class="left"></div>');
			smenuHtml.push('<div class="center">');
			//弹出二级菜单
			for(var i=0;i<secondMenu.length;i++){
				if(secondMenu[i].resFather == domEle.id){
					var menuid = secondMenu[i].pkId;
					var menutxt = secondMenu[i].resName;
					smenuHtml.push('<div class="this" id="'+menuid+'" lev="second" onmouseover="this.style.cursor=\'pointer\'" onclick="javascript:itemclick(this)">');
					smenuHtml.push('<div class="bg_left"></div>');
					smenuHtml.push('<div class="bg_center">'+menutxt+'</div>');
					smenuHtml.push('<div class="bg_right"></div>');
					smenuHtml.push('</div>');
				}
			}
			smenuHtml.push('</div>');
			smenuHtml.push('<div class="right"></div>');
			smenuHtml.push('</div>');
			$("#fistMenu").append(smenuHtml.join(''));
			$("#"+domEle.id+"_smenu").mouseover(function () {
				$("#"+domEle.id).addClass("over");
				$(this).show();
			});
			$("#"+domEle.id+"_smenu").mouseout(function () {
				$("#"+domEle.id).removeClass("over");
				$(this).hide();
			});
			for(var i=0;i<secondMenu.length;i++){
				var menuid = secondMenu[i].pkId;
				$("#"+menuid).mouseover(function () {
					$(this).children(".bg_left").addClass("bg_left_over");
					$(this).children(".bg_center").addClass("bg_center_over");
					$(this).children(".bg_right").addClass("bg_right_over");
				});
				$("#"+menuid).mouseout(function () {
					$(this).children(".bg_left").removeClass("bg_left_over");
					$(this).children(".bg_center").removeClass("bg_center_over");
					$(this).children(".bg_right").removeClass("bg_right_over");
				});
			}
		}
		m_left = m_left + domEle.offsetWidth+1;
	});
	//$(".m-second-menu").hide();
} 

function f_heightChanged(options) {
	if (tab) {
		tab.addHeight(options.diff);
	}
}
function f_addTab(tabid, text, url) {
	tab.addTabItem({
		tabid : tabid,
		text : text,
		url : url
	});
}
function loginOut() {
	$.ligerDialog.confirm('您确定要退出吗?', function(yes) {
		if (yes) {
			window.location = basePath+"rest/poplarLogin/loginOut";
		}
	});
}
//点击foot菜单修改当前页面
function showNewWeb(menuCode) {
	var t = $(".navT p");
	for (var i = 0; i < t.length; i++) {
		//修改其他菜单背景
		t[i].style.backgroundImage = "url(Aqua/images/footStatic.png)";
	}
	$("#"+menuCode)[0].style.backgroundImage = "url(Aqua/images/footClick.png)";
	//跳转
	toMain(menuCode);
}

/**
 * 跳转到主页
 */
function toMain(pageCode){
	window.location.href = basePath + "mainPage/main.jsp?firstPageCode="+pageCode;
}

// 把当前操作句柄传递给父窗口
function setCurrWindows() {
	currWin = window;
}
//把当前操作句柄传递给父窗口
function setPwdWindows() {
	parent.pwdWin = window;
}

var conditionDialog = null;
function openNoParamQueryWindow(){
	if(conditionDialog){
		conditionDialog.show();
		return;
	}
	conditionDialog = $.ligerDialog.open({ 
		 width: 630,
		 height:420, 
		 title:"非遥测数据查询",
		 url: basePath+"noParamData/dataQuery/query_condition.jsp",
		 buttons :[
		           { text: '查询',type:'save', width: 80, onclick:function(item, dialog){
		        	   var conditionData = dialog.frame.getConditionInfo(); 
		        	   if(conditionData == null){
		        		   return;
		        	    }
		        	   queryResult(conditionData)
		        	   conditionDialog.hide();
		           }},
		           { text: '关闭',type:'close', width: 80, onclick:function(item, dialog){
		        	   conditionDialog.hide();
		           }}
		           ]
	 });
}


function queryResult(formData){
	tab.removeTabItem("queryNoTm");
	f_addTab("queryNoTm","非遥测数据查询",basePath+"noParamData/dataQuery/data_list.jsp?sql="+
			new Base64().encode(formData.sql)
			+"&startTime="+formData.startTime+
			"&endTime="+formData.endTime+"&relateId="+formData.relateId);
}

/**
 * 菜单鼠标悬停事件
 */
function onMouseOver(lev,e){
	
}

$(function() {
	//获取当前时间
	current();
	//获取权限菜单
	//getLimit();
	//每一秒执行一次
	setInterval("current()",100);
});
/*获取系统时间  */
function current(){
	var nowDate = new Date();
	//年
	var year = this.replenish0(nowDate.getFullYear());
	//月
	var month = replenish0(nowDate.getMonth()+1);
	//日
	var day = replenish0(nowDate.getDate());
	//时
	var hours = replenish0(nowDate.getHours());
	//分
	var minutes = replenish0(nowDate.getMinutes());
	//秒
	var seconds = replenish0(nowDate.getSeconds());
	
	var nowTime = "" + year + "-" + month + "-" + day + " " + 
				hours + ":" + minutes + ":" + seconds;
	//赋值			
	$(".time")[0].innerHTML = nowTime;
}

//小于10补0
function replenish0(param){
	if(param < 10){
		param = "0" + param;
	}
	return param;
}

/**
 * 注销
 */
function loginOut() {
	$.ligerDialog.confirm("您确定要注销吗?","确认注销",function(arg){
		if(arg){
			window.location=basePath+"rest/poplarLogin/loginOut";
		}
	});
}

/**
 * 修改密码
 */
function alterPwd(){
	setPwdWindows();
	changePwd = parent.$.ligerDialog.open({ 
          	width: 400,
			height:200, 
            title:'修改密码',
            isResize : false,
			url: basePath + "mainPage/modifyPWD.jsp",
			buttons :[{ 
					text: '修改', 
					width: 60 ,
					onclick:function(item, dialog){
						dialog.frame.submitForm();
             		}
			},{ 
            	 text: '取消', 
            	 width: 60, 
            	 onclick:function(item, dialog){
            		 changePwd.close();
	              }
             }]
        });
        changePwd.show(); 
}

//把当前操作句柄传递给父窗口
function setPwdWindows(){
    parent.pwdWin = window;
}