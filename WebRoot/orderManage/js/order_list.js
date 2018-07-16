//订单表格对象
var orderGridObj = null;

//新增窗口
var addWin = null;

//编辑窗口
var updateWin = null;

var orderStatus = [{id:"0",text:"废弃"},{id:"1",text:"新建"},{id:"2",text:"待处理"},
                   {id:"3",text:"处理中"},{id:"4",text:"处理完成"},{id:"5",text:" 处理失败"},{id:"9",text:"删除"}];

$(function(){
	
	//初始化布局
	layoutObj = $("#layout").ligerLayout({ 
		leftWidth: 200,
		centerBottomHeight:250,
		heightDiff:0,
		space:0, 
		allowBottomCollapse:true,
		allowLeftResize: false,   
		onHeightChanged:f_heightChanged
	});
	
	//初始化时间控件
	initTimeInput();
	//初始化查询、重置
	initQueryBar();
	//初始化工具条
	initToolBar();
	//初始订单表格
	initOrderGrid();
	//初始化日志表格
	initLogGrid();
	
	//循环执行查询订单信息
	orderGridInterval = setInterval("refreshGrid_2()",10000);
	//循环执行查询日志信息
	logGridInterval = setInterval("refreshGrid_log()",5000);
});

//设置左边高度自适应
function f_heightChanged(options){/*
	$("#scrollDivTree")[0].style.height = options.middleHeight-50 +27 +"px";
	if(satGroupTree){
		$("#satGroupTree")[0].style.height = options.middleHeight - 67 +27 +"px";
	}
*/}

//初始化时间控件
function initTimeInput(){
	$("#orderName").ligerTextBox({
		width:146
	});
	var nowDate = new Date();
	var startTime = nowDate.getTime() - 24*60*60*1000;
	var endTime = nowDate.getTime() + 24*60*60*1000;
	$("#startTime").ligerDateEditor({
		showTime:true,
		width:146,
		value:new Date(startTime)
	});
	$("#endTime").ligerDateEditor({
		showTime:true,
		width:146,
		value:new Date(endTime)
	});
}


//初始化查询、重置
function initQueryBar(){
	$("#searchbtn").ligerButton({
		click : function() {
			orderGridObj.set('parms', {
				orderName : $("#orderName").val(),
				timeStart : $("#startTime").val(),
				timeEnd : $("#endTime").val()
			});
			orderGridObj.loadData();
		}
	});
	
	// 重置搜索条件
	$("#resetbtn").ligerButton({
		click : function() {
			$("#orderName").val("");
			$("#startTime").val("");
			$("#endTime").val("");
		}
	});
}

//初始化工具条
function initToolBar() {
	$("#orderbar").ligerToolBar({
		items : [ {
			text : '新建',
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
			text : '转为待处理',
			id : 'start',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/outbox.gif'
		}, {
			text : '发布',
			id : 'up',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/up.gif'
		},{
			text : '删除',
			id : 'delete',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/delete.gif'
		}]
	});
}

//初始订单表格
function initOrderGrid(){
	orderGridObj = $("#orderGrid").ligerGrid({
		columns : [
	           {
	        	   display : '主键',
	        	   name : 'pk_id',
	        	   hide:true,
	        	   width : 0.1
	           },
	           {
	        	   display : '卫星任务代号',
	        	   name : 'sat_mid',
	        	   hide:true,
	        	   width : 0.1
	           },
				{
					display : '订单编号',
					name : 'order_id',
					align : 'center',
					width : 160
				},
				{
					display : '订单名称',
					name : 'order_name',
					align : 'center',
					width : 180
				},
				{
					display : "订单时间",
					name : "time",
					align : 'center',
					width : 160
				},
				{
					display : "所属卫星",
					name : "sat_name",
					align : 'center',
					width : 130
				},
				{
					display : '订单状态',
					name : 'order_state',
					align : 'center',
					width : 110,
					render:function(item){
	            		  if(item.order_state){
	            			  for (var i = 0; i < orderStatus.length; i++) {
								if(orderStatus[i].id == item.order_state){
									return orderStatus[i].text;
								}
							}
	            		  }
	            	  }
				},
				{
					display : "数据获取类",
					name : "getdata_class_path",
					align : 'center',
					width : 250,
					render:function(item){
	            		  if(item.getdata_class_path){
	            			  return '<p title="'+item.getdata_class_path+'">'+item.getdata_class_path+'</p>';
	            		  }
	            	  }
				},
				{
					display : "数据计算类",
					name : "comput_class_path",
					align : 'center',
					width : 250,
					render:function(item){
	            		  if(item.comput_class_path){
	            			  return '<p title="'+item.comput_class_path+'">'+item.comput_class_path+'</p>';
	            		  }
	            	  }
				},
				{
					display : "结果处理类",
					name : "result_class_path",
					align : 'center',
					width : 250,
					render:function(item){
	            		  if(item.result_class_path){
	            			  return '<p title="'+item.result_class_path+'">'+item.result_class_path+'</p>';
	            		  }
	            	  }
				}/*,
				{
					display : "订单处理失败信息",
					name : "order_err",
					align : 'center',
					width : 320,
					render:function(item){
	            		  if(item.order_err){
	            			  return '<p title="'+item.order_err+'">'+item.order_err+'</p>';
	            		  }
	            	  }
				}*/
		],
		height:'100%',
		rownumbers : false,
		checkbox : true,
		isSingleCheck:true,
		rowHeight : 27,
		pageSize:20,
		heightDiff : -5,
		frozen : false ,
		delayLoad:true,
		url:basePath + 'rest/orderManager/list'
	});
	
	orderGridObj.set('parms', {
		orderName : $("#orderName").val(),
		timeStart : $("#startTime").val(),
		timeEnd : $("#endTime").val()
	});
	orderGridObj.loadData();
}


//初始化日志表格
function initLogGrid() {
	logGridObj = $("#logGrid").ligerGrid({
		columns : [
		           {
		        	   display : '时间',
		        	   name : 'logTime',
		        	   align : 'center',
		        	   width : 200
		           },
		           {
		        	   display : '订单编号',
		        	   name : 'orderCode',
		        	   align : 'center',
		        	   width : 200
		           },
		           {
		        	   display : '内容',
		        	   name : 'logContent',
		        	   align : 'left',
		        	   width : 600,
		        	   render:function(item){
		        		   if(item.logContent){
		        			   return "<p title='"+item.logContent+"'>"+item.logContent+"</p>";
		        		   }
		        	   }
		           }
		           ],
		           height:'100%',
		           usePager:false,
		           rownumbers : true,
		           checkbox : false,
		           rowHeight : 27,
		           delayLoad:true,
		           heightDiff : 30,
		           frozen : false 
	});
}


//工具条点击事件
function itemclick(item){
	if (item.id) {
		switch (item.id) {
			case "add":
				addWin = $.ligerDialog.open({
					width : 520,
					height : 400,
					title : "创建订单",
					isResize : false,
					url : basePath+"orderManage/order_add.jsp",
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
			case "view":
				if (orderGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var orderId = orderGridObj.selected[0].order_id;
				var viewWin = $.ligerDialog.open({
					width : 520,
					height : 400,
					title : "查看订单",
					isResize : false,
					url : basePath+"orderManage/order_view_new.jsp?orderId="+orderId,
					buttons:[
					         { text: '关闭', width: 60, onclick:function(item, dialog){
					        	 viewWin.close();
					         }}
					         ]
				});
				break;
			case "update":
				if (orderGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var orderId = orderGridObj.selected[0].order_id;
				updateWin = $.ligerDialog.open({
					width : 520,
					height : 400,
					title : "编辑订单",
					isResize : false,
					url : basePath+"orderManage/order_update.jsp?orderId="+orderId,
					buttons:[
				         { text: '保存', width: 60 ,onclick:function(item, dialog){
						 	dialog.frame.submitForms();
						 }},
						 { text: '关闭', width: 60, onclick:function(item, dialog){
							 updateWin.close();
						 }}
					]
				});
				break;
			case "start":
				if (orderGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var state = orderGridObj.selected[0].order_state;
				if(state != "1"){
					Alert.tip('请选择状态为新建的订单！');
					return;
				}
				$.ligerDialog.confirm('进入待处理状态将无法编辑订单，确定继续此操作吗？',function (yes){
					if(yes){
						$.ajax({
							url:basePath+'rest/orderManager/start',
							data:{
								orderId:orderGridObj.selected[0].order_id,
							},
							async:false,
							success:function(rsStr){
								var rsData = eval('('+rsStr+')');
								if(rsData.success == "true"){
									Alert.tip('操作成功！');
									orderGridObj.loadData();
								}
							}
						});
					}
				});
				break;
			case "delete":
				if (orderGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var state = orderGridObj.selected[0].order_state;
				if(state == "3"){
					Alert.tip('状态为处理中的无法删除！');
					return;
				}
				$.ligerDialog.confirm('确定要移除该订单吗？',function (yes){
					if(yes){
						$.ajax({
							url:basePath+'rest/orderManager/deleteOrder',
							data:{
								orderId:orderGridObj.selected[0].order_id,
							},
							async:false,
							success:function(rsStr){
								var rsData = eval('('+rsStr+')');
								if(rsData.success == "true"){
									Alert.tip('移除成功！');
									orderGridObj.loadData();
								}
							}
						});
					}
				});
				break;
			case "up":
				if (orderGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var state = orderGridObj.selected[0].order_state;
				if(state != "2"){
					Alert.tip('请选择状态为待处理的订单！');
					return;
				}
				$.ligerDialog.confirm('确定要发布该函数吗？',function (yes){
					if(yes){
						$.ajax({
							url:basePath+'rest/orderManager/publish',
							data:{
								orderId:orderGridObj.selected[0].order_id,
								satMid:orderGridObj.selected[0].sat_mid,
								orderName:orderGridObj.selected[0].order_name,
							},
							async:false,
							success:function(rsStr){
								var rsData = eval('('+rsStr+')');
								if(rsData.success == "true"){
									Alert.tip('发布成功！');
									orderGridObj.loadData();
								}else{
									Alert.tip('发布失败！');
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
	orderGridObj.loadData();
}

function closeUpdateWinAndLoadGrid(){
	updateWin.close();
	orderGridObj.loadData();
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}

function refreshGrid_2(){
	orderGridObj.loadData();
}

function refreshGrid_log(){
	$.ajax({
		url:basePath+'rest/orderDealLog/queryMonitorLog',
		async:false,
		success:function(e){
			var data = eval('('+e+')');
			logGridObj.loadData({
				"Rows":data
			});
		}
	});
}
