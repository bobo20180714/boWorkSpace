/**
 * 跳转到主页
 */
function toMain(pageCode){
	//判断是否有该权限
	if(firstMenu){
		var flag = judgeHaveLimit(pageCode);
		if(!flag){
			Alert.tip("您没有该菜单权限！");
			return;
		}
	}
	//判断是否没有子节点并且当前节点是窗口打卡方式
	var firstMenuTemp = getFirstMenu(pageCode);
	if(firstMenuTemp.resValue != "" && firstMenuTemp.showType == "2"){
		window.open(basePath + "mainPage/main.jsp?firstPageCode="+pageCode+"&contuneOpen=false");	
	}else{
		window.open(basePath + "mainPage/main.jsp?firstPageCode="+pageCode);
	}
}

function judgeHaveLimit(pageCode){
	var flag = false;
	for(var i = 0;firstMenu != null && i<firstMenu.length;i++){
		if(firstMenu[i].resCode == pageCode){
			flag = true;
		}
	}
	return flag;
}

//拥有的一级权限
var firstMenu = null;

//首页固定菜单
var showMenu = [{code:"monitor"},{code:"comput"},{code:"query"},
                {code:"alarm"},{code:"process"},{code:"satConfig"},
                {code:"base_info"},{code:"sys"}];

$(function() {
	//获取当前时间
	current();
	//获取权限菜单
	var allLimitMenu = getLimitMenus();
	firstMenu = allLimitMenu.firstList;
	//添加一级横向菜单
	addFistMenu();
	//添加一级中间菜单
	addCenterMenu();
	/*//根据菜单权限，设置显示图标
	for(var i = 0;showMenu != null && i<showMenu.length;i++){
		var resCode = showMenu[i].code;
		var flag = judgeHaveLimit(resCode);
		if(!flag){
			//没有权限
			$("#"+resCode).find('img').attr('src','images/zxjcbj_dis.png');
			return;
		}
	}*/
	//每一秒执行一次
	setInterval("current()",100);
});

/**
 * 显示一级菜单
 */
function addFistMenu(){
	//查询所有菜单
	var allFirstMenu = queryAllFirstMenu();
	for (var i = 0; i < allFirstMenu.length; i++) {
		var flag = false;
		for (var j = 0; j < firstMenu.length; j++) {
			if(allFirstMenu[i].pk_id == firstMenu[j].pkId){
				flag = true;
				break;
			}
		}
		var code = allFirstMenu[i].res_code;
		var id = allFirstMenu[i].pk_id;
		var name = allFirstMenu[i].res_name;
		var temp = '<img src="images/line.png" width="1" height="48" /> '+
		'<a href="javascript:toMain(\''+code+'\')"><p id="'+id+'" class="menulink" lev="first" >'+
		'<img style="margin-top:12px;margin-left:15px;" src="'+basePath+'mainPage/menuImg/'+code+'_t.png" /><span style="margin-left:5px;">'+name+'</span></p></a>';
		if(!flag){
			temp = '<img src="images/line.png" width="1" height="48" /> '+
			'<a href="javascript:toMain(\''+code+'\')" ><p id="'+id+'" class="menulink" lev="first"  style="color:#dddddd"><span style="margin-left:5px;">'+
			'<img style="margin-top:12px;margin-left:15px;" src="'+basePath+'mainPage/menuImg/'+code+'_t.png" />'+
			name+'</span></p></a>';
		}
		$("#fistMenu").append(temp);
	}
}

/**
 * 添加中间一级菜单
 */
function addCenterMenu(){
	//查询所有菜单
	var allFirstMenu = queryAllFirstMenu();
	for (var i = 0; i < allFirstMenu.length; i++) {
		var flag = false;
		for (var j = 0; j < firstMenu.length; j++) {
			if(allFirstMenu[i].pk_id == firstMenu[j].pkId){
				flag = true;
				break;
			}
		}
		var code = allFirstMenu[i].res_code;
		var id = allFirstMenu[i].pk_id;
		var name = allFirstMenu[i].res_name;
		
		var style="";
		if(i >= 5){
			style = "margin-top:50px;";
		}
		
		var temp = '<div class="one" id="'+code+'" style="'+style+'">'+
			       '	<div><a href="javascript:toMain(\''+code+'\')"><img src="images/'+code+'_c.png" width="155" height="137" /></a></div>'+
			       '     <p><a href="javascript:toMain(\''+code+'\')">'+name+'</a></p>'+
			       '</div>';
		if(!flag){
			temp = '<div class="one" id="'+code+'" style="'+style+'">'+
		       '	<div><a href="javascript:toMain(\''+code+'\')"><img src="images/'+code+'_c_dis.png" width="155" height="137" /></a></div>'+
		       '     <p><a href="javascript:toMain(\''+code+'\')">'+name+'</a></p>'+
		       '</div>';
		}
		$("#centerMenu").append(temp);
	}
}

/**
 * 获取搜有一级菜单
 */
function queryAllFirstMenu(){
	var allFirstMenu = new Array();
	$.ajax({
		url:basePath+"rest/ResourcesAction/getList",
		async:false,
		success:function(rsData){
			var menuData = eval('('+rsData+')');
			for(var i = 0;i<menuData.Rows.length;i++){
				var dataTemp = menuData.Rows[i];
				if(dataTemp.res_father == ""){
					allFirstMenu.push({
						pk_id:dataTemp.pk_id,
						res_code:dataTemp.res_code,
						res_name:dataTemp.res_name
					});
				}
			}
		}
	});
	return allFirstMenu;
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