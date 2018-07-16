//进程表格对象
var processGridObj = null;

//新增窗口
var addWin = null;

//编辑窗口
var updateWin = null;

var orderStatus = [{id:"0",text:"废弃"},{id:"1",text:"新建"},{id:"2",text:"待处理"},
                   {id:"3",text:"处理中"},{id:"4",text:"处理完成"},{id:"5",text:" 处理失败"},{id:"9",text:"删除"}];

//进程类型
var processTypeArr = [{id:'0',text:'进程调度'},{id:'3',text:'遥测处理'},{id:'4',text:'门限报警'}
						,{id:'5',text:'控制量计算'},{id:'6',text:'数据存储'},{id:'10',text:'服务代理进程'}];

$(function(){
	//初始化时间控件
	initTimeInput();
	//初始化查询、重置
	initQueryBar();
	//初始化工具条
	initToolBar();
	//初始进程表格
	initProcessGrid();
});

//初始化时间控件
function initTimeInput(){
	$("#process_code").ligerTextBox({
		width:146
	});
	$("#process_name").ligerTextBox({
		width:146
	});
}

//初始化查询、重置
function initQueryBar(){
	$("#searchbtn").ligerButton({
		click : function() {
			processGridObj.set('parms', {
				processCode : $("#process_code").val(),
				processName : $("#process_name").val()
			});
			processGridObj.loadData();
		}
	});
	
	// 重置搜索条件
	$("#resetbtn").ligerButton({
		click : function() {
			$("#process_code").val("");
			$("#process_name").val("");
		}
	});
}

//初始化工具条
function initToolBar() {
	$("#toptoolbar").ligerToolBar({
		items : [ {
			text : '注册',
			id : 'add',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/add.gif'
		}, {
			text : '修改',
			id : 'update',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/edit.gif'
		}, {
			text : '查看',
			id : 'view',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/comment.gif'
		}, {
			text : '启用',
			id : 'start',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/ok.gif'
		},{
			text : '删除',
			id : 'delete',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/delete.gif'
		}]
	});
}

//初始进程表格
function initProcessGrid(){
	processGridObj = $("#processGrid").ligerGrid({
		columns : [
	           {
	        	   display : '主键',
	        	   name : 'pk_id',
	        	   hide:true,
	        	   width : 0.1
	           },
				{
					display : '进程标识',
					name : 'process_code',
					align : 'center',
					width : 150
				},
				{
					display : '进程名称',
					name : 'process_name',
					align : 'center',
					width : 280
				},
				{
					display : "进程类型",
					name : "process_type",
					align : 'center',
					width : 220,
					render:function(item){
						if(item.process_type){
							for(var i = 0;i<processTypeArr.length;i++){
								if(processTypeArr[i].id == item.process_type){
									return processTypeArr[i].text;
								}
							}
						}
					}
				},
				{
					display : "进程信息状态",
					name : "process_info_state",
					align : 'center',
					width : 130,
					render:function(item){
						if(item.process_info_state == 1){
							return "未启用";
						}else if(item.process_info_state == 2){
							return "已启用";
						}
					}
				},
				{
					display : "服务器编号",
					name : "agency_process_code",
					align : 'center',
					width : 180
				},
				{
					display : "是否是主进程",
					name : "is_main_process",
					align : 'center',
					width : 120,
					render:function(item){
						if(item.is_main_process == "0"){
							return "是";
						}
					}
				},
				{
					display : "主进程编号",
					name : "main_process_code",
					align : 'center',
					width : 180
				},
				{
					display : "进程启动路径",
					name : "startup_path",
					align : 'center',
					width : 350
				},
				{
					display : "服务器IP",
					name : "computer_ip",
					align : 'center',
					width : 200
				}
		],
		height:'100%',
		rownumbers : false,
		checkbox : true,
		isSingleCheck:true,
		rowHeight : 27,
		pageSize:20,
		heightDiff : -34,
		frozen : false,
		url:basePath + 'rest/processManager/list'
	});
}

//工具条点击事件
function itemclick(item){
	if (item.id) {
		switch (item.id) {
			case "add":
				addWin = $.ligerDialog.open({
					width : 650,
					height : 380,
					title : "注册进程",
					isResize : false,
					url : basePath+"processManage/processRegister/process_add.jsp",
					buttons:[
				         { text: '保存', width: 80 ,onclick:function(item, dialog){
						 	dialog.frame.submitForms();
						 }},
						 { text: '关闭', width: 80, onclick:function(item, dialog){
							 addWin.close();
						 }}
					]
				});
				break;
			case "view":
				if (processGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var processCode = processGridObj.selected[0].process_code;
				var viewWin = $.ligerDialog.open({
					width : 650,
					height : 360,
					title : "查看进程",
					isResize : false,
					url : basePath+"processManage/processRegister/process_view.jsp?processCode="+processCode,
					buttons:[
					         { text: '关闭', width: 80, onclick:function(item, dialog){
					        	 viewWin.close();
					         }}
					         ]
				});
				break;
			case "update":
				if (processGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var processInfoState = processGridObj.selected[0].process_info_state;
				if(processInfoState != 1){
					Alert.tip('请选择状态为未启用的进程信息！');
					return;
				}
				var processCode = processGridObj.selected[0].process_code;
				updateWin = $.ligerDialog.open({
					width : 650,
					height : 380,
					title : "编辑订单",
					isResize : false,
					url : basePath+"processManage/processRegister/process_update.jsp?processCode="+processCode,
					buttons:[
				         { text: '保存', width: 80 ,onclick:function(item, dialog){
						 	dialog.frame.submitForms();
						 }},
						 { text: '关闭', width: 80, onclick:function(item, dialog){
							 updateWin.close();
						 }}
					]
				});
				break;
			case "start":
				if (processGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var processInfoState = processGridObj.selected[0].process_info_state;
				if(processInfoState != 1){
					Alert.tip('请选择状态为未启用的进程信息！');
					return;
				}
				$.ligerDialog.confirm('启用后将无法修改，确定要启用该进程吗？',function (yes){
					if(yes){
						$.ajax({
							url:basePath+'rest/processManager/startProcess',
							data:{
								processCode:processGridObj.selected[0].process_code,
							},
							async:false,
							success:function(rsStr){
								var rsData = eval('('+rsStr+')');
								if(rsData.success == "true"){
									Alert.tip('启用成功！');
									processGridObj.loadData();
								}
							}
						});
					}
				});
				break;
			case "delete":
				if (processGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				$.ligerDialog.confirm('确定要移除该进程吗？',function (yes){
					if(yes){
						$.ajax({
							url:basePath+'rest/processManager/deleteProcess',
							data:{
								processCode:processGridObj.selected[0].process_code,
							},
							async:false,
							success:function(rsStr){
								var rsData = eval('('+rsStr+')');
								if(rsData.success == "true"){
									Alert.tip('移除成功！');
									processGridObj.loadData();
								}
							}
						});
					}
				});
				break;
 		}
	}
}

function closeAddWinAndLoadGrid(){
	addWin.close();
	processGridObj.loadData();
}

function closeUpdateWinAndLoadGrid(){
	updateWin.close();
	processGridObj.loadData();
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}
