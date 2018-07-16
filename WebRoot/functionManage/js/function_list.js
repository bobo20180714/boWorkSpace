//布局对象
var layoutObj = null;
//函数表格对象
var functionGridObj = null;
//参数表格对象
var paramGridObj = null;
//参数表格对象
var fieldGridObj = null;

var functionbarObj = null;

//当前选择的方法
var fidTemp = -1;

var addWin = null;

$(function(){
	/*//初始化布局
	layoutObj = $("#layout").ligerLayout({ 
        topHeight: '50%',
        bottomHeight: '50%',
		heightDiff:0,
		space:0, 
		allowBottomCollapse:true,
		allowLeftResize: false,   
		onHeightChanged:f_heightChanged
	});*/
	initForm();
	//初始化查询、重置
	initQueryBar();
	//初始化工具条
	initToolBar();
	//初始函数表格
	initFunctionGrid();
/*	//初始参数表格
	initParamGrid();
	//初始字段信息表格
	initFieldGrid();*/
	
	/*resizes();*/
});

function initForm(){
    formObj1 = $("#form1").ligerForm({
    		labelAlign:'right',
        	inputWidth: 100, 
            labelWidth: 80, 
            space: 5,
            fields: [
				{display: '功能名称', id: 'computeName',name:'computeName',type:"text",newline: false},
				{display: 'java类名称', id: 'className',name:'className',type:"text",newline: false}
			]
    });
}

/*window.onresize = function(){
	resizes();
}

function resizes(){
	var winHeight = window.innerHeight;
	var winWidth = window.innerWidth;
	//两个表格高度
	var totalHeight = winHeight - 90 - 50;
	if(functionGridObj){
		var functionGridHeight = totalHeight*2/3;
		if(fieldGridObj){
			paramGridObj.setWidth(winWidth/2);
			fieldGridObj.setWidth(winWidth - winWidth/2);
		}
		functionGridObj.setHeight(functionGridHeight);
		paramGridObj.setHeight(totalHeight - functionGridHeight);
		fieldGridObj.setHeight(totalHeight - functionGridHeight + 25);
	}
}*/

//初始化查询、重置
function initQueryBar(){
	$("#searchbtn").ligerButton({
		type:"one", 
		click : function() {
			var formData = formObj1.getData();
			functionGridObj.set('parms', {
				computeName : formData.computeName,
				className : formData.className
			});
			functionGridObj.loadData();
		}
	});
	
	// 重置搜索条件
	$("#resetbtn").ligerButton({
		type:"two", 
		click : function() {
			formObj1.setData({
				computeName:"",
				className:""
			});
		}
	});
}

//初始化工具条
function initToolBar() {
	functionbarObj = $("#functionbar").ligerToolBar({
		items : [ {
			text : '注册',
			id : 'register',
			click : itemclick,
			icon:'add'
//			img : basePath + 'lib/ligerUI/skins/icons/add.gif'
		}, {
			text : '修改',
			id : 'updateFct',
			click : itemclick,
			icon:'update'
//			img : basePath + 'lib/ligerUI/skins/icons/edit.gif'
		}, {
			text : '移除',
			id : 'deleteFunction',
			click : itemclick,
			icon:'delete'
//			img : basePath + 'lib/ligerUI/skins/icons/delete.gif'
		}]
	});
	/*$("#parambar").ligerToolBar({
		items : [ {
			text : '编辑',
			id : 'updateParam',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/edit.gif'
		}, {
			text : '上移',
			id : 'up',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/up.gif'
		}, {
			text : '下移',
			id : 'down',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/down.gif'
		}]
	});*/
}

//初始函数表格
function initFunctionGrid(){
	functionGridObj = $("#functionGrid").ligerGrid({
		columns : [
	           {
	        	   display : '主键',
	        	   name : 'pk_id',
	        	   align : 'center',
	        	   hide:true,
	        	   width : 0.1
	           },
				{
					display : '功能名称',
					name : 'compute_name',
					align : 'center',
					width : 200
				},
				{
					display : '版本号',
					name : 'version',
					align : 'center',
					width : 200
				},
				{
					display : "java类名称",
					name : "fct_class_name",
					align : 'center',
					width : 200
				},
				{
					display : "所在包名",
					name : "fct_pck_name",
					align : 'center',
					width : 300
				},
				{
					display : "java类全路径",
					name : "fct_all_path_namej",
					align : 'left',
					width : 350,
					render:function(item){
						if(item.fct_all_path_namej){
							return "<p title='"+item.fct_all_path_namej+"'>"+item.fct_all_path_namej+"</p>";
						}
					}
				},
				{
					display : "功能描述",
					name : "compute_desc",
					align : 'left',
					width : 300,
					render:function(item){
						if(item.compute_desc){
							return "<p title='"+item.compute_desc+"'>"+item.compute_desc+"</p>";
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
//		heightDiff : -5,
		frozen : false ,
		url:basePath + 'rest/ComputeFunc/list'
	});
}

//初始化参数表格
function initParamGrid() {
	paramGridObj = $("#paramGrid").ligerGrid({
			columns : [
		           {
		        	   display : '函数主键',
		        	   name : 'fid',
		        	   align : 'center',
		        	   hide:true,
		        	   width : 0.1
		           },
		           {
		        	   display : '主键',
		        	   name : 'field_id',
		        	   align : 'center',
		        	   hide:true,
		        	   width : 0.1
		           },
		           {
		        	   display : '序号',
		        	   name : 'param_order',
		        	   align : 'center',
		        	   hide:true,
		        	   width : 0.1
		           },
					{
						display : '参数名称',
						name : 'param_name',
						align : 'center',
						width : 200
					},
					{
						display : '参数类型',
						name : 'param_type',
						align : 'center',
						width : 200,
						render:function(item){
							if(item.param_type == 10){
								return '数据列表';
							}else if(item.param_type == 11){
								return '整型';
							}else if(item.param_type == 12){
								return '浮点型';
							}
						}
					},
					{
						display : "参数说明",
						name : "param_content",
						align : 'center',
						width : 380,
						render:function(item){
							if(item.param_content){
								return "<p title='"+item.param_content+"'>"+item.param_content+"</p>";
							}
						}
					}
			],
			rownumbers : true,
			checkbox : true,
			isSingleCheck:true,
			rowHeight : 27,
			// 应用灰色表头
			cssClass : 'l-grid-gray',
			frozen : false ,
			url:basePath + 'rest/functionManage/paramList'
		});
}

//初始化字段信息表格
function initFieldGrid() {
	fieldGridObj = $("#fieldGrid").ligerGrid({
		columns : [
		           {
		        	   display : '函数主键',
		        	   name : 'fid',
		        	   align : 'center',
		        	   hide:true,
		        	   width : 0.1
		           },
		           {
		        	   display : '主键',
		        	   name : 'field_id',
		        	   align : 'center',
		        	   hide:true,
		        	   width : 0.1
		           },
		           {
		        	   display : '字段名称',
		        	   name : 'field_name',
		        	   align : 'center',
		        	   width : 200
		           },
		           {
		        	   display : '字段类型',
		        	   name : 'field_type',
		        	   align : 'center',
		        	   width : 150,
		        	   render:function(item){
		        		   if(item.field_type == 1){
		        			   return '整型';
		        		   }else if(item.field_type == 2){
		        			   return '浮点型';
		        		   }else if(item.field_type == 3){
		        			   return '字符串';
		        		   }else if(item.field_type == 4){
		        			   return '时间戳';
		        		   }
		        	   }
		           },
		           {
		        	   display : "字段长度",
		        	   name : "field_length",
		        	   align : 'center',
		        	   width : 150
		           },
		           {
		        	   display : "字段说明",
		        	   name : "field_comment",
		        	   align : 'center',
		        	   width : 300,
		        	   render:function(item){
		        		   if(item.field_comment){
		        			   return "<p title='"+item.field_comment+"'>"+item.field_comment+"</p>";
		        		   }
		        	   }
		           }
		           ],
		           rownumbers : true,
		           checkbox : true,
		           isSingleCheck:true,
		           rowHeight : 27,
		           // 应用灰色表头
		           cssClass : 'l-grid-gray',
		           frozen : false ,
		           url:basePath + 'rest/field/list'
	});
}

/**
 * 单机函数行事件
 */
function onSelectFunction(rowData){
	
	if(rowData.table_name){
		functionbarObj.removeItem('queryField');
		functionbarObj.addItem({
			text : '查看存储表字段信息',
			id : 'queryField',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/view.gif'
		});
	}else{
		functionbarObj.removeItem('queryField');
	}
	
	/*if(rowData.return_type == 10){
		$("#feildTd").show();
		resizes();
	}else{
		$("#feildTd").hide();
	}
	fidTemp = rowData.fct_id;
	paramGridObj.set('parms',{
		functionId:fidTemp
	});
	paramGridObj.loadData();
	if(fieldGridObj){
		fieldGridObj.set('parms',{
			fid:fidTemp
		});
		fieldGridObj.loadData();
	}*/
}
/**
 * 取消函数行事件
 */
function onUnSelectRow(){
	
	functionbarObj.removeItem('queryField');
	

	/*fidTemp = -1;
	
	$("#feildTd").hide();
	
	paramGridObj.set('parms',{
		functionId:-1
	});
	paramGridObj.loadData();*/
}

//工具条点击事件
function itemclick(item){
	if (item.id) {
		switch (item.id) {
			case "register":
				setCurrWindows();
				addWin = $.ligerDialog.open({
					width : 635,
					height : 390,
					title : "注册",
					isResize : false,
					url : basePath+"functionManage/function_add.jsp",
					buttons:[
				         { text: '保存', type:'save',width: 80 ,onclick:function(item, dialog){
						 		dialog.frame.submitForms();
						 }},
						 { text: '关闭',  type:'close',width: 80, onclick:function(item, dialog){
							 addWin.close();
						 }}
					]
				});
				break;
			case "updateFct":
				if (functionGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				setCurrWindows();
				var fid = functionGridObj.selected[0].pk_id;
				addWin = $.ligerDialog.open({
					width : 635,
					height : 390,
					title : "修改",
					isResize : false,
					url : basePath+"functionManage/function_update.jsp?computFuncId="+fid,
					buttons:[
					         { text: '保存',type:'save', width: 80 ,onclick:function(item, dialog){
					        	 dialog.frame.submitForms();
					         }},
					         { text: '关闭',type:'close', width: 80, onclick:function(item, dialog){
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
					         { text: '关闭', type:'close',width: 80, onclick:function(item, dialog){
					        	 fieldWin.close();
					         }}
					         ]
				});
				break;
			case "deleteFunction":
				if (functionGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				$.ligerDialog.confirm('确定要移除该计算模块吗？',function (yes){
					if(yes){
						$.ajax({
							url:basePath+'rest/ComputeFunc/delete',
							data:{
								computeId:functionGridObj.selected[0].pk_id,
							},
							async:false,
							success:function(rsStr){
								var rsData = eval('('+rsStr+')');
								if(rsData.success == "true"){
									Alert.tip('移除成功！');
									functionGridObj.loadData();
//									onUnSelectRow();
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
				         { text: '保存',type:'save', width: 80 ,onclick:function(item, dialog){
						 	var flag = dialog.frame.submitForms();
						 	if(flag == true){
						 		Alert.tip("修改参数成功!");
						 		updateParamWin.close();
						 		paramGridObj.loadData();
						 	}else{
						 		Alert.tip("修改参数失败!");
						 	}
						 }},
						 { text: '关闭',type:'close', width: 80, onclick:function(item, dialog){
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
				         { text: '保存', type:'save', width: 80,onclick:function(item, dialog){
						 	var flag = dialog.frame.submitForms();
						 	if(flag == true){
						 		Alert.tip("新增字段信息成功!");
						 		addFieldWin.close();
								fieldGridObj.loadData();
						 	}else{
						 		Alert.tip("新增字段信息失败!");
						 	}
						 }},
						 { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
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
	functionGridObj.loadData();
}