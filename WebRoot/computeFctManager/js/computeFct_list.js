//布局对象
var layoutObj = null;
//表格对象
var computeGridObj = null;
var addWin = null;
$(function(){
	//初始化查询、重置
	initQueryBar();
	//初始化工具条
	initToolBar();
	//初始计算表格
	initComputeGrid();
});

//初始化查询、重置
function initQueryBar(){
	$("#searchbtn").ligerButton({
		click : function() {
			computeGridObj.set('parms', {
				computeName : $("#computeName").val()
			});
			computeGridObj.loadData();
		}
	});
	
	// 重置搜索条件
	$("#resetbtn").ligerButton({
		click : function() {
			$("#computeName").val("");
		}
	});
}

//初始化工具条
function initToolBar() {
	functionbarObj = $("#computetoolbar").ligerToolBar({
		items : [ {
			text : '新增',
			id : 'add',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/add.gif'
		}, {
			text : '修改',
			id : 'update',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/edit.gif'
		}, {
			text : '删除',
			id : 'delete',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/delete.gif'
		}]
	});
}

//初始计算功能表格
function initComputeGrid(){
	computeGridObj = $("#computeGrid").ligerGrid({
		columns : [
	           {
	        	   display : '主键',
	        	   name : 'pk_id',
	        	   align : 'center',
	        	   hide:true,
	        	   width : 0.1
	           },
				{
					display : '控制量计算名称',
					name : 'compute_name',
					align : 'center',
					width : 200
				},
				{
					display : "功能描述",
					name : "compute_desc",
					align : 'left',
					width : 400,
					render:function(item){
						if(item.compute_desc){
							return "<p title='"+item.compute_desc+"'>"+item.compute_desc+"</p>";
						}
					}
				},
				{
					display : '计算模块',
					name : 'compute_function',
					align : 'center',
					width : 200,
					render:function(item){
						if(item.fct_name){
							return "<p title='"+item.fct_name+"'>"+item.fct_name+"</p>";
						}
					}
				}
		],
		height:'100%',
		rownumbers : false,
		checkbox : true,
		isSingleCheck:true,
		rowHeight : 27,
		pageSize:20,
		heightDiff : -5,
		frozen : false ,
		url:basePath + 'rest/ComputeFunc/list'
	});
}

//工具条点击事件
function itemclick(item){
	if (item.id) {
		switch (item.id) {
			case "add":
				setCurrWindows();
				addWin = $.ligerDialog.open({
					width : 400,
					height : 250,
					title : "新增",
					isResize : false,
					url : basePath+"computeFctManager/compute_add.jsp",
					buttons:[
				         { text: '保存', width: 60 ,onclick:function(item, dialog){
						 		dialog.frame.submitForms();
						 }},
						 { text: '关闭', width: 60, onclick:function(item, dialog){
							 addWin.close();
						 }}
					]
				});
				break;
			case "update":
				if (computeGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				setCurrWindows();
				var computeId = computeGridObj.selected[0].pk_id;
				addWin = $.ligerDialog.open({
					width : 400,
					height : 250,
					title : "修改",
					isResize : false,
					url : basePath+"computeFctManager/compute_update.jsp?computeId="+computeId,
					buttons:[
					         { text: '保存', width: 60 ,onclick:function(item, dialog){
					        	 dialog.frame.submitForms();
					         }},
					         { text: '关闭', width: 60, onclick:function(item, dialog){
					        	 addWin.close();
					         }}
					         ]
				});
				break;
			case "queryField":
				if (functionGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var fid = functionGridObj.selected[0].fct_id;
				var fieldWin = $.ligerDialog.open({
					width : 610,
					height : 400,
					title : "存储表字段信息",
					isResize : false,
					url : basePath+"functionManage/query_field_list.jsp?fid="+fid,
					buttons:[
					         { text: '关闭', width: 60, onclick:function(item, dialog){
					        	 fieldWin.close();
					         }}
					         ]
				});
				break;
			case "delete":
				if (computeGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				$.ligerDialog.confirm('确定要删除吗？',function (yes){
					if(yes){
						$.ajax({
							url:basePath+'rest/ComputeFunc/delete',
							data:{
								computeId:computeGridObj.selected[0].pk_id,
							},
							async:false,
							success:function(rsStr){
								var rsData = eval('('+rsStr+')');
								if(rsData.success == "true"){
									Alert.tip('删除成功！');
									computeGridObj.loadData();
								}
							}
						});
					}
				});
				break;
			case "updateParam":
				if (paramGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var fieldId = paramGridObj.selected[0].field_id;
				var updateParamWin = $.ligerDialog.open({
					width : 430,
					height : 280,
					title : "编辑参数信息",
					isResize : false,
					url : basePath+"functionManage/param_update.jsp?fieldId="+fieldId,
					buttons:[
				         { text: '保存', width: 60 ,onclick:function(item, dialog){
						 	var flag = dialog.frame.submitForms();
						 	if(flag == true){
						 		Alert.tip("修改参数成功!");
						 		updateParamWin.close();
						 		paramGridObj.loadData();
						 	}else{
						 		Alert.tip("修改参数失败!");
						 	}
						 }},
						 { text: '关闭', width: 60, onclick:function(item, dialog){
							 updateParamWin.close();
						 }}
					]
				});
				break;
			case "up":
				if (paramGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				if(paramGridObj.selected[0].__index == 0){
					Alert.tip('第一条不能上移！');
					return;
				}
				var fieldId = paramGridObj.selected[0].field_id;
				var fid = paramGridObj.selected[0].fid;
				var paramOrder = paramGridObj.selected[0].param_order;
				$.ajax({
					url:basePath+'rest/functionManage/upParam',
					data:{
						fieldId:fieldId,
						fid:fid,
						paramOrder:paramOrder
					},
					async:false,
					success:function(rsStr){
						paramGridObj.loadData();
					}
				});
				break;
			case "down":
				if (paramGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				if(paramGridObj.selected[0].__index == paramGridObj.recordNumber - 1){
					Alert.tip('最后一条不能下移！');
					return;
				}
				var fieldId = paramGridObj.selected[0].field_id;
				var fid = paramGridObj.selected[0].fid;
				var paramOrder = paramGridObj.selected[0].param_order;
				$.ajax({
					url:basePath+'rest/functionManage/downParam',
					data:{
						fieldId:fieldId,
						fid:fid,
						paramOrder:paramOrder
					},
					async:false,
					success:function(rsStr){
						paramGridObj.loadData();
					}
				});
				break;
			case "addField":
				var addFieldWin = $.ligerDialog.open({
					width : 400,
					height : 250,
					title : "新增字段信息",
					isResize : false,
					url : basePath+"functionManage/field_add.jsp?fid="+fidTemp,
					buttons:[
				         { text: '保存', width: 60 ,onclick:function(item, dialog){
						 	var flag = dialog.frame.submitForms();
						 	if(flag == true){
						 		Alert.tip("新增字段信息成功!");
						 		addFieldWin.close();
								fieldGridObj.loadData();
						 	}else{
						 		Alert.tip("新增字段信息失败!");
						 	}
						 }},
						 { text: '关闭', width: 60, onclick:function(item, dialog){
							 addFieldWin.close();
						 }}
					]
				});
				break;
			case "deleteField":
				if (fieldGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var fieldId = fieldGridObj.selected[0].field_id;
				$.ajax({
					url:basePath+'rest/field/delete',
					data:{
						fieldId:fieldId
					},
					async:false,
					success:function(rsStr){
						var rsData = eval('('+rsStr+')');
						if(rsData.success == "true"){
					 		Alert.tip("删除字段信息成功!");
							fieldGridObj.loadData();
					 	}else{
					 		Alert.tip("删除字段信息失败!");
					 	}
					}
				});
				break;
		}
	}
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}
//关闭对话框
function f_closeDlg() {
	addWin.close();
}
//关闭对话框
function f_closeAndLoad() {
	addWin.close();
	computeGridObj.loadData();
}