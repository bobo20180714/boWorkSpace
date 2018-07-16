//布局对象
var layoutObj = null;
//进程表格对象
var processGridObj = null;
//卫星表格对象
var satGroupTree = null;
//状态数据字典
var statusValue = {
		"0":"停止","1":"工作中","4":"异常","5":"未知","6":"连接中断"
};
//状态颜色字典
var statusColor = {
		"0":"#000","1":"#1894DE","4":"#FF7F50","5":"#FFFF00","6":"#FF0000"
};

var orderStatus = [{id:"0",text:"废弃"},{id:"1",text:"新建"},{id:"2",text:"待处理"},
                   {id:"3",text:"处理中"},{id:"4",text:"处理完成"},{id:"5",text:" 处理失败"},{id:"9",text:"删除"}];

//循环进程
var processGridInterval = null;
//日志窗口
var logWin = null;
//运行参数窗口
var runParamWin = null;
//结果窗口
var resultWin = null;

$(function(){
	//初始化布局
	layoutObj = $("#layout").ligerLayout({ 
		leftWidth: 200,
//		centerBottomHeight:200,
//		rightWidth:200,
		heightDiff:0,
		space:0, 
		allowBottomCollapse:true,
		allowLeftResize: false,   
		onHeightChanged:f_heightChanged
	});
	//初始化布局
	$("#layout_right").ligerLayout({ 
        topHeight: '50%',
        bottomHeight: '50%',
		heightDiff:0,
		space:0, 
		allowBottomCollapse:true
	});
	
	/*//获取中间总高度
	var height = $(".l-layout-center").height() + $(".l-layout-centerbottom").height();
	//设置左边高度自适应
	$("#scrollDivTree")[0].style.height = height - 50 +25 +"px";
	$("#satGroupTree")[0].style.height = height - 67 +25 +"px";*/
	
	//初始化卫星列表
	initSatList();
	//初始化工具条
	initToolBar();
	//初始化进程表格
	initProcessGrid();
	
	getProcessData();
	
	//循环执行查询进程信息
	processGridInterval = setInterval("refreshGrid_2()",5000);
});

//初始化卫星列表
function initSatList(){
	
	$("#selectSatToolbar").ligerToolBar({
		items : [/* {
			text : '全选',
			id : 'selectAll',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/up.gif'
		},*/ {
			text : '清空选择',
			id : 'clearAll',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/refresh.gif'
		}]
	});
	
	satGroupTree = $("#satGroupTree").ligerTree({
		checkbox: true,
		isExpand:false,
		onCheck :onCheck,
		treeLine : false,
        textFieldName:'name',
		idFieldName : 'id',
		parentIDFieldName : 'parent_id',
		height: "100%",
		nodeWidth:280,
		url:basePath+'rest/satController/getSatGroupTree'

	});
}

//初始化工具条
function initToolBar() {
	$("#toptoolbar").ligerToolBar({
		items : [ {
			text : '注册',
			id : 'add',
			click : itemclick,
			icon:'add'
//			img : basePath + 'lib/ligerUI/skins/icons/add.gif'
		}, {
			text : '启动',
			id : 'start',
			click : itemclick,
			icon:'start'
//			img : basePath + 'lib/ligerUI/skins/icons/up.gif'
		}, /*{
			text : '暂停',
			id : 'pause',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/modify.gif'
		},*/{
			text : '停止',
			id : 'stop',
			click : itemclick,
			icon:'end'
//			img : basePath + 'lib/ligerUI/skins/icons/delete.gif'
		}, {
			text : '修改',
			id : 'update',
			click : itemclick,
			icon:'update'
//			img : basePath + 'lib/ligerUI/skins/icons/edit.gif'
		}, {
			text : '查看',
			id : 'view',
			click : itemclick,
			icon:'view'
//			img : basePath + 'lib/ligerUI/skins/icons/comment.gif'
		},{
			text : '删除',
			id : 'delete',
			click : itemclick,
			icon:'delete'
//			img : basePath + 'lib/ligerUI/skins/icons/candle.gif'
		}/*,{
			text : '查看日志',
			id : 'sendLog',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/pager.gif'
		},{
			text : '查看运行参数',
			id : 'sendParam',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/outbox.gif'
		},{
			text : '查看输出结果',
			id : 'sendResult',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/back.gif'
		}*//*,{
			text : '颜色配置',
			id : 'colorChange',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/communication.gif'
		}*/]
	});
}

//初始化进程表格
function initProcessGrid() {
	processGridObj = $("#processGrid").ligerGrid({
			columns : [
		           {
		        	   display : '进程Id',
		        	   name : 'processId',
		        	   align : 'center',
		        	   hide:true,
		        	   width : 0.1
		           },
		           {
		        	   display : '卫星任务代号',
		        	   name : 'satMid',
		        	   align : 'center',
		        	   hide:true,
		        	   width : 0.1
		           },
		           {
		        	   display : '进程类型',
		        	   name : 'processType',
		        	   align : 'center',
		        	   hide:true,
		        	   width : 0.1
		           },
		           {
		        	   display : "进程名称",
		        	   name : "processName",
		        	   align : 'center',
		        	   width : 180,
		        	   render:function(item){
		        		   if(item.processName){
		        			   return "<div style='color:"+statusColor[item.processState]+"'>"+item.processName+"</div>";
		        		   }
		        	   }
		           },
					{
						display : '进程标识',
						name : 'processCode',
						align : 'center',
						width : 150,
						render:function(item){
							return "<div style='color:"+statusColor[item.processState]+"'>"+item.processCode+"</div>";
						}
					},
					{
						display : '所属卫星',
						name : 'satName',
						align : 'center',
						width : 200,
						render:function(item){
							if(item.satName){
								return "<div style='color:"+statusColor[item.processState]+"'>"+item.satName+"</div>";
							}
						}
					},
					{
						display : '进程类型',
						name : 'processTypeName',
						align : 'center',
						width : 200,
						render:function(item){
							if(item.processTypeName){
								return "<div style='color:"+statusColor[item.processState]+"'>"+item.processTypeName+"</div>";
							}
						}
					},
					{
						display : "运行状态",
						name : "processState",
						align : 'center',
						width : 150,
						render:function(item){
							return "<div style='color:"+statusColor[item.processState]+"'>"+statusValue[item.processState]+"</div>";
						}
					},
					{
						display : "是否是备用进程",
						name : "isBei",
						align : 'center',
						width : 120,
						render:function(item){
							if(item.isBei == 1){
								return "<div style='color:"+statusColor[item.processState]+"'>是</div>";
							}else{
								return "<div style='color:"+statusColor[item.processState]+"'>否</div>";
							}
						}
					},
					/*{
						display : "主进程标识",
						name : "mainProcessCode",
						align : 'center',
						width : 150,
						render:function(item){
							if(item.mainProcessCode){
								return "<div style='color:"+statusColor[item.processState]+"'>"+item.mainProcessCode+"</div>";
							}
						}
					},*/
					{
						display : "服务器",
						name : "agencyProcessName",
						align : 'center',
						width : 150,
						render:function(item){
							if(item.agencyProcessName){
								return "<div style='color:"+statusColor[item.processState]+"'>"+item.agencyProcessName+"</div>";
							}
						}
					},
					{
						display : "服务器IP",
						name : "computerIp",
						align : 'center',
						width : 180,
						render:function(item){
							if(item.computerIp){
								return "<div style='color:"+statusColor[item.processState]+"'>"+item.computerIp+"</div>";
							}
						}
					},
					{
						display : "异常信息",
						name : "exceptionInfo",
						align : 'left',
						width : 380,
						render:function(item){
							if(item.exceptionInfo){
								return "<div style='color:"+statusColor[item.processState]+"'>"+item.exceptionInfo+"</div>";
							}
						}
					}/*,
					{
						display : "其他操作",
						name : "other_operate",
						align : 'left',
						width : 300,
						render:function(item){
							if(item.processState == "1" || item.processState == "4" ){
								return "<table><tr>" +
								"<td width='70px'><input type='checkbox' value='sendLog' />发送日志</td>" +
								"<td width='70px'><input type='checkbox' value='sendParam' />运行参数</td>" +
								"<td width='70px'><input type='checkbox' value='sendResult' />输出结果</td>" +
								"</tr></table>";
							}
						}
					}*/
			],
			height:'100%',
			usePager:false,
	        isSingleCheck:true,
			rownumbers : true,
			checkbox : true,
			rowHeight : 27,
			heightDiff : 30,
			frozen : false 
		});
}

//刷新进程表格
function refreshGrid(){
	var nodes = satGroupTree.getChecked();
	var selectSatMidArr = new Array();
	for (var i = 0; i < nodes.length; i++) {
		var nodeData = nodes[i].data;
		if(nodeData.type == "sat"){
			var flag = false;
			for ( var selectSatMid in selectSatMidArr) {
				if(selectSatMid != nodeData.satMid){
					continue;
				}
				flag = true;
			}
			if(!flag){
				//不存在添加
				selectSatMidArr.push(nodeData.satMid);
			}
		}
	}
	processGridObj.set('parms', {
		satMids:selectSatMidArr.join(";")
	});
	processGridObj.loadData();
}

/**
 * 第一次请求获取进程数据
 */
function getProcessData(){

//	var nodes = satGroupTree.getChecked();
	var nodes = new Array();
	var selectSatMidArr = new Array();
	for (var i = 0; i < nodes.length; i++) {
		var nodeData = nodes[i].data;
		if(nodeData.type == "sat"){
			var flag = false;
			for ( var selectSatMid in selectSatMidArr) {
				if(selectSatMid != nodeData.satMid){
					continue;
				}
				flag = true;
			}
			if(!flag){
				//不存在添加
				selectSatMidArr.push(nodeData.satMid);
			}
		}
	}
	$("#satMids").val(selectSatMidArr.join(";"));
	$("#processInfoArrStr").val("");
	$("#hideForm").ajaxSubmit({
		url:basePath+'rest/monitor/queryChangeProcess',
		success:function(data){
			if(data == ""){
				parent.Alert.tip("请求出错！");
				return;
			}
			var jsobj = eval('('+data+')');
			processGridObj.loadData({
				"Rows":jsobj
			});
			
		}
	});
}

//监视进程
function refreshGrid_2(){
	//进程标识集合
	var processInfoArr = new Array();
	var processDataList = processGridObj.rows;
	for(var i = 0;i < processDataList.length;i++){
		var processInfo = {
				processId:processDataList[i].processId,
				processState:processDataList[i].processState,
				processType:processDataList[i].processType,
				processName:processDataList[i].processName,
				serviceCode:processDataList[i].agencyProcessCode,
				satId:processDataList[i].satMid
		}
		processInfoArr.push(processInfo);
	}
	$("#processInfoArrStr").val(JSON.stringify(processInfoArr));
	$("#hideForm").ajaxSubmit({
		url:basePath+'rest/monitor/queryChangeProcess',
		success:function(data){
			if(data == ""){
				parent.Alert.tip("请求出错！");
				return;
			}
			var jsobj = eval('('+data+')');
			for(var j = 0;j < jsobj.length;j++){
				if(processGridObj.rows.length == 0){
					processGridObj.addRow(jsobj[j]);
				}else{
					if(jsobj[j].isAdd == "0"){
						processGridObj.addRow(jsobj[j]);
					}else{
						for(var i = 0;i < processGridObj.rows.length;i++){
							var processId = processGridObj.rows[i].processId;
							if(processId == jsobj[j].processId){
								if(jsobj[j].toDelete == "0"){
									processGridObj.deleteRow(processGridObj.rows[i]);
								}else{
									processGridObj.updateRow(processGridObj.rows[i],jsobj[j]);
								}
							}
						}
					}
				}
			}
			
		}
	});
}

/**
 * 选择卫星节点事件
 */
function onCheck(){
	getProcessData();
}

//设置左边高度自适应
function f_heightChanged(options){/*
	$("#scrollDivTree")[0].style.height = options.middleHeight-50 +27 +"px";
	if(satGroupTree){
		$("#satGroupTree")[0].style.height = options.middleHeight - 67 +27 +"px";
	}
*/}

//工具条点击事件
function itemclick(item){
	if (item.id) {
		switch (item.id) {
		case "add":
			addWin = $.ligerDialog.open({
				width : 650,
				height : 330,
				title : "注册进程",
				isResize : false,
				url : basePath+"processManage/processRegister/process_add.jsp",
				buttons:[
			         { text: '保存', type:'save', width: 80 ,onclick:function(item, dialog){
					 	dialog.frame.submitForms();
					 }},
					 { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
						 addWin.close();
					 }}
				]
			});
			break;
		case "update":
			if (processGridObj.selected.length != 1) {
				Alert.tip('请选择一条数据！');
				return;
			}
			/*var processInfoState = processGridObj.selected[0].process_info_state;
			if(processInfoState != 1){
				Alert.tip('请选择状态为未启用的进程信息！');
				return;
			}*/
			var processState = processGridObj.selected[0].processState;
			if(processState == 1){
				Alert.tip('工作中的进程不能修改！');
				return;
			}
			var processCode = processGridObj.selected[0].processCode;
			updateWin = $.ligerDialog.open({
				width : 650,
				height : 330,
				title : "编辑进程",
				isResize : false,
				url : basePath+"processManage/processRegister/process_update.jsp?processCode="+processCode,
				buttons:[
			         { text: '保存', type:'save', width: 80 ,onclick:function(item, dialog){
					 	dialog.frame.submitForms();
					 }},
					 { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
						 updateWin.close();
					 }}
				]
			});
			break;
		case "delete":
			if (processGridObj.selected.length != 1) {
				Alert.tip('请选择一条数据！');
				return;
			}
			var processState = processGridObj.selected[0].processState;
			if(processState == 1){
				Alert.tip('工作中的进程不能删除！');
				return;
			}
			$.ligerDialog.confirm('确定要删除该进程吗？',function (yes){
				if(yes){
					$.ajax({
						url:basePath+'rest/processManager/deleteProcess',
						data:{
							processCode:processGridObj.selected[0].processCode,
						},
						async:false,
						success:function(rsStr){
							var rsData = eval('('+rsStr+')');
							if(rsData.success == "true"){
								Alert.tip('操作成功！');
								//processGridObj.loadData();
							}
						}
					});
				}
			});
			break;
		case "view":
			if (processGridObj.selected.length != 1) {
				Alert.tip('请选择一条数据！');
				return;
			}
			var processCode = processGridObj.selected[0].processCode;
			var viewWin = $.ligerDialog.open({
				width : 650,
				height : 330,
				title : "查看进程",
				isResize : false,
				url : basePath+"processManage/processRegister/process_view.jsp?processCode="+processCode,
				buttons:[
				         { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
				        	 viewWin.close();
				         }}
				         ]
			});
			break;
		case "clearAll":
			satGroupTree.reload();
			getProcessData();
			break;
		case "start":
				if (processGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var process_type = processGridObj.selected[0].processType;
				if(process_type != 3 && process_type != 5 && process_type != 4 && process_type != 6){
					Alert.tip('该进程无法进行启动操作！');
					return;
				}
				var status = processGridObj.selected[0].processState;
				if(status != 0 && status != 6){
					Alert.tip('请选择状态为停止或连接中断的数据！');
					return;
				}
				if(status == 0 || status == 6){
					
					//如果是备用，判断主进程是否已启动，判断主进程信息和备用进程信息是否一致
					var isBei = processGridObj.selected[0].isBei;
					if(isBei == 1){
						var info = judgeCanStarted(processGridObj.selected[0].processCode,
								processGridObj.selected[0].mainProcessCode); 
						if(info.success == "false"){
							Alert.tip(info.message);
							return;
						}
					}
					
					//启动时判断服务器是否已启动
					if(!judgeServiceStarted(processGridObj.selected[0].agencyProcessCode)){
						Alert.tip('请先启动服务器['+processGridObj.selected[0].agencyProcessName+']！');
						return;
					}
					
					$.ligerDialog.confirm('确定要启动吗？',function (yes){
						if(yes){
							
							var process_id = processGridObj.selected[0].processId;
							var satMid = processGridObj.selected[0].satMid;
							var process_code = processGridObj.selected[0].processCode;
							var agencyProcessCode = processGridObj.selected[0].agencyProcessCode;
							

							startProcess(satMid,process_code,agencyProcessCode,"");
							
							/*var processData = null;
							$.ajax({
								url:basePath+'rest/processManager/viewProcess',
								data:{
									processCode:process_code
								},
								async:false,
								success:function(rsStr){
									processData = eval('('+rsStr+')');
								}
							});
							if(processData != null){
								var startup_param = processData.startupParam;
								if(startup_param){
									//弹出录入启动参数输入窗口
									openStartupParamWin(satMid,process_code,agencyProcessCode,process_type);
								}else{
									//启动进程
									startProcess(satMid,process_code,agencyProcessCode,"");
								}
							}*/
						}
					});
				}
				break;
			case "stop":
				if (processGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var process_type = processGridObj.selected[0].processType;
				if(process_type != 3 && process_type != 5 && process_type != 4 && process_type != 6){
					Alert.tip('该进程无法进行停止操作！');
					return;
				}
				var status = processGridObj.selected[0].processState;
				if(status != 1 && status != 4){
					Alert.tip('请选择状态为工作中、异常的数据！');
					return;
				}
				var agencyProcessCode = processGridObj.selected[0].agencyProcessCode;
				$.ligerDialog.confirm('确定要停止吗？',function (yes){
					if(yes){
						$.ajax({
							url:basePath+'rest/monitor/stop',
							data:{
								satMid:processGridObj.selected[0].satMid,
								processId:processGridObj.selected[0].processId,
								agencyProcessCode:agencyProcessCode
							},
							async:false,
							success:function(rsStr){
								var rsData = eval('('+rsStr+')');
								if(rsData.success == "true"){
									Alert.tip('操作成功！');
								}
							}
						});
					}
				});
				break;
			case "sendLog":
				if (processGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var status = processGridObj.selected[0].processState;
				if(status != 1 && status != 4){
					Alert.tip('请选择状态为工作中或异常的数据！');
					return;
				}
				var sat_mid = processGridObj.selected[0].sat_mid;
				var process_id = processGridObj.selected[0].processId;
				$.ajax({
					url:basePath+'rest/monitor/sendLog',
					data:{
						satMid:sat_mid,
						processId:process_id
					},
					async:false,
					success:function(rsStr){
						/*var rsData = eval('('+rsStr+')');
						if(rsData.success == "true"){
							Alert.tip('操作成功！');
						}*/
					}
				});
				var process_code = processGridObj.selected[0].processCode;
				logWin = parent.$.ligerDialog.open({
					width : 650,
					height : 400,
					title : "进程["+process_code+"]日志列表",
					isResize : true,
					url : basePath+"processManage/monitor/showLog.jsp?processCode="+process_code,
					buttons:[
						 { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
							 logWin.close();
						 }}
					]
				});
				$(".l-window-mask").hide();
				break;
			case "sendParam":
				if (processGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var status = processGridObj.selected[0].processState;
				if(status != 1){
					Alert.tip('请选择状态为工作中的数据！');
					return;
				}
				var sat_mid = processGridObj.selected[0].sat_mid;
				var process_id = processGridObj.selected[0].processId;
				$.ajax({
					url:basePath+'rest/monitor/sendParam',
					data:{
						satMid:sat_mid,
						processId:process_id
					},
					async:false,
					success:function(rsStr){
						/*var rsData = eval('('+rsStr+')');
						if(rsData.success == "true"){
							Alert.tip('操作成功！');
						}*/
					}
				});
				var process_code = processGridObj.selected[0].processCode;
				runParamWin = parent.$.ligerDialog.open({
					width : 400,
					height : 300,
					title : "进程["+process_code+"]运行参数列表",
					isResize : true,
					url : basePath+"processManage/monitor/showRunParam.jsp?processCode="+process_code,
					buttons:[
						 { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
							 runParamWin.close();
						 }}
					]
				});
				$(".l-window-mask").hide();
				break;
			case "sendResult":
				if (processGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var status = processGridObj.selected[0].processState;
				if(status != 1){
					Alert.tip('请选择状态为工作中的数据！');
					return;
				}
				var sat_mid = processGridObj.selected[0].sat_mid;
				var process_id = processGridObj.selected[0].processId;
				$.ajax({
					url:basePath+'rest/monitor/sendResult',
					data:{
						satMid:sat_mid,
						processId:process_id
					},
					async:false,
					success:function(rsStr){
					}
				});
				var process_code = processGridObj.selected[0].processCode;
				resultWin = parent.$.ligerDialog.open({
					width : 400,
					height : 300,
					title : "进程["+process_code+"]输出结果列表",
					isResize : true,
					url : basePath+"processManage/monitor/showResult.jsp?processCode="+process_code,
					buttons:[
						 { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
							 resultWin.close();
						 }}
					]
				});
				$(".l-window-mask").hide();
				break;
			case "colorChange":
				setCurrWindows();
				sonOpenWin = $.ligerDialog.open({
					width : 540,
					height : 330,
					title : "颜色配置",
					isResize : false,
					url : basePath+"processManage/monitor/set_color_page.jsp",
					buttons:[
				         { text: '保存', type:'save',width: 80 ,onclick:function(item, dialog){
						 	var flag = dialog.frame.Confirm();
						 	if(flag == true){
						 		Alert.tip("保存成功！");
						 		f_closeDlg();
						 		getColorArr();
						 		gridManager.loadData();
						 	}else{
						 		Alert.tip("保存失败！");
						 	}
						 }},
						 { text: '关闭', width: 80, onclick:function(item, dialog){
							f_closeDlg();
						 }}
					]
				});
			break;
		}
	}
}

//如果是备用，判断主进程是否已启动，判断主进程信息和备用进程信息是否一致
function judgeCanStarted(beiProcessCode,mainProcessCode){
	var resObj = {success:"true",message:""};
	$.ajax({
		url:basePath+'rest/monitor/judgeCanStarted',
		data:{
			beiProcessCode:beiProcessCode,
			mainProcessCode:mainProcessCode
		},
		async:false,
		success:function(rsStr){
			var rsData = eval('('+rsStr+')');
			resObj = rsData
		}
	});
	return resObj;
}

/**
 * 判断服务器是否已启动
 * @param serviceCode
 * @returns {Boolean}
 */
function judgeServiceStarted(serviceCode){
	var flag = false;
	$.ajax({
		url:basePath+'rest/monitor/judgeServiceStarted',
		data:{
			servcieCode:serviceCode
		},
		async:false,
		success:function(rsStr){
			var rsData = eval('('+rsStr+')');
			if(rsData.success == "true"){
				flag = true
			}
		}
	});
	return flag;
}

/**
 * 打开启动参数窗口
 */
function openStartupParamWin(satMid,process_code,agencyProcessCode,processType){
	var startParamWin = parent.$.ligerDialog.open({
		width : 400,
		height : 300,
		title : '输入启动参数',
		isResize : true,
		url : basePath+'processManage/monitor/sartParamWin.jsp?processCode='+process_code,
		buttons:[
	         { text: '确定', width: 80 ,onclick:function(item, dialog){
	        	    var paramData = dialog.frame.getParamData();
					if (paramData == null) { 
						parent.Alert.tip("请完整输入启动参数！");
						return; 
					}
					//启动进程
					startProcess(satMid,process_code,agencyProcessCode,paramData);
					startParamWin.close();
	          }},
			 { text: '关闭', width: 80, onclick:function(item, dialog){
				 startParamWin.close();
			 }}
		]
	});
}

/**
 * 启动进程
 * @param satMid
 * @param processId
 * @param agencyProcessCode
 * @param startupParamData
 */
function startProcess(satMid,processId,agencyProcessCode,startupParamData){
	$.ajax({
		url:basePath+'rest/monitor/start',
		data:{
			satMid:satMid,
			agencyProcessCode:agencyProcessCode,
			startupParamData:startupParamData,
			processId:processId
		},
		async:false,
		success:function(rsStr){
			var rsData = eval('('+rsStr+')');
			if(rsData.success == "true"){
				Alert.tip('操作成功！');
			}
		}
	});
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}
function closeAddWinAndLoadGrid(){
	addWin.close();
	processGridObj.loadData();
}

function closeUpdateWinAndLoadGrid(){
	updateWin.close();
	processGridObj.loadData();
}

//关闭对话框
function f_closeDlg() {
	sonOpenWin.close();
}