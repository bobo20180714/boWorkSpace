var formData = null;
var tree = null;
var sat_id = "";
var node = null;
var currentTreeNode;//当前点击右键的节点

var orderStatus = [{id:"0",text:"废弃"},{id:"1",text:"新建"},{id:"2",text:"待处理"},
                   {id:"3",text:"处理中"},{id:"4",text:"处理完成"},{id:"5",text:" 处理失败"},{id:"9",text:"删除"}];
$(function (){
	initTreeController();
	$("#layout1").ligerLayout({ 
		leftWidth: 190,
//		centerBottomHeight:250,
		allowLeftCollapse:false,
		heightDiff:0,
		space:0, 
		onHeightChanged: f_heightChanged 
	});
	var height = $(".l-layout-center").height();
	$("#scollDivTree")[0].style.height = height-24;
	
	 computeMenu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
		 [
		  { text: '新增控制量计算', click: itemclick, id: 'addOrder' }
		  ]
	 });
	 
	 initForm();
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
	
	resizes();

});

window.onresize = function(){
	resizes();
}

function resizes(){
	var winHeight = window.innerHeight;
	//两个表格高度
	var totalHeight = winHeight;
	if(orderGridObj){
		var functionGridHeight = totalHeight*3/4;
		orderGridObj.setHeight(functionGridHeight - 100);
		logGridObj.setHeight(totalHeight - functionGridHeight + 43);
		
	}
}

function initForm(){
    formObj1 = $("#form1").ligerForm({
    		labelAlign:'right',
        	inputWidth: 140, 
            labelWidth: 80, 
            space: 5,
            fields: [
				{display: '名称', id: 'orderName',name:'orderName',type:"text",newline: false},
				{display: '时间区间', id: 'startTime',name:'startTime',type:"date",newline: false},
				{display: '至', id: 'endTime',name:'endTime',type:"date",newline: false,
		            labelWidth:30}
			]
    });
}

//初始化查询、重置
function initQueryBar(){
	$("#searchbtn").ligerButton({
		type:"one", 
		click : function() {
			onselect();
		}
	});
	
	// 重置搜索条件
	$("#resetbtn").ligerButton({
		type:"two", 
		click : function() {
			formObj1.setData({
				orderName:"",
				startTime:"",
				endTime:""
			});
		}
	});
}


//设置左边高度自适应
function f_heightChanged(options){/*
	$("#scrollDivTree")[0].style.height = options.middleHeight-50 +27 +"px";
	if(satGroupTree){
		$("#satGroupTree")[0].style.height = options.middleHeight - 67 +27 +"px";
	}
*/}

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

function initTreeController(){
	var owner_id = -1;
	$("#tree1").remove();
	$("#scollDivTree").append('<div id="tree1"></div>');
	tree = $("#tree1").ligerTree({
		checkbox: false,
		onClick :onselect,
        textFieldName:'text',
		idFieldName : 'id',
		parentIDFieldName : 'pid',
		height: "100%",
		nodeWidth:"100%",
		topIcon:'sat',
		parentIcon: null,
        childIcon: null,
		url:basePath+'rest/ComputeType/queryComputeTypeAndFuncTree?ownerId=-1',
		isLeaf : function(data){
			return data.node_type == 'compute';
		},
		delay: function(e)
		{
		     var data = e.data;
		     return { url: basePath+'rest/ComputeType/queryComputeTypeAndFuncTree?ownerId=' + data.id };
		 },
		 onContextmenu: function (node, e){
	    	 currentTreeNode = node.data;
	    	 var selNode = tree.getSelected();
	    	 if(!selNode){
	    		 Alert.tip("请选择树节点！");
	    		 return false;
	    	 }
	    	 tree.cancelSelect(selNode.data); 
	    	 tree.selectNode(currentTreeNode); 
	    	 if(node.data.node_type == "compute"){
    			 computeMenu.show({ top: e.pageY, left: e.pageX });
	    	 }else{
	    		 hiddenMenu();
	    	 }
	    	 return false;
    	 }

	});
}

function hiddenMenu(){
	 computeMenu.hide();
}

function onselect(){
	node = tree.getSelected();
	var formData = formObj1.getData();
	if(node!=null){
		if(node.data.node_type == "SAT"){
			orderGridObj.set('parms', {
				computeId : "",
				satId:node.data.sat_id,
				typeId : "",
				orderName : formData.orderName,
				timeStart : formData.startTime,
				timeEnd : formData.endTime
			});
		}else{
			orderGridObj.set('parms', {
				computeId : node.data.id,
				satId:node.data.sat_id,
				typeId : node.data.pid,
				orderName : formData.orderName,
				timeStart : formData.startTime,
				timeEnd : formData.endTime
			});
		}
	}else{
		orderGridObj.setParm("satId","-1");
	}
	orderGridObj.set({newPage:1});
	orderGridObj.loadData(false);
}

//初始化工具条
function initToolBar() {
	$("#orderbar").ligerToolBar({
		items : [ {
			text : '新增',
			id : 'addOrder',
			click : itemclick,
			icon:'add'
		}, {
			text : '修改',
			id : 'updateOrder',
			click : itemclick,
			icon:'update'
//			img : basePath + 'lib/ligerUI/skins/icons/edit.gif'
		}, {
			text : '查看',
			id : 'viewOrder',
			click : itemclick,
			icon:'view'
//			img : basePath + 'lib/ligerUI/skins/icons/comment.gif'
		}, {
			text : '删除',
			id : 'deleteOrder',
			click : itemclick,
			icon:'delete'
//			img : basePath + 'lib/ligerUI/skins/icons/delete.gif'
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
					display : '控制量计算名称',
					name : 'order_name',
					align : 'center',
					width : 200
				},
				{
					display : "执行时间",
					name : "time",
					align : 'center',
					width : 200
				},
				{
					display : '状态',
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
					display : '控制量计算描述',
					name : 'order_content',
					render:function(item){
						if(item.order_content){
							return '<p title="'+item.order_content+'">'+item.order_content+'</p>';
						}
					},
					align : 'left',
					width : 350
				},
				{
					display : '计算模块名称',
					name : 'compute_fun_name',
					align : 'center',
					width : 200
				},
				{
					display : '计算类',
					name : 'compute_fun_class',
					render:function(item){
						if(item.compute_fun_class){
							return '<p title="'+item.compute_fun_class+'">'+item.compute_fun_class+'</p>';
						}
					},
					align : 'left',
					width : 350
				},
				{
					display : '控制量计算编号',
					name : 'order_id',
					align : 'center',
					width : 200
				}
		],
//		height:'100%',
		rownumbers : false,
		checkbox : true,
		isSingleCheck:true,
		rowHeight : 27,
		pageSize:20,
//		heightDiff : -5,
		frozen : false ,
		delayLoad:true,
		url:basePath + 'rest/orderManager/list'
	});
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
		        	   display : '控制量计算编号',
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
//		           height:'100%',
		           usePager:false,
		           rownumbers : true,
		           checkbox : false,
		           rowHeight : 27,
		           delayLoad:true,
//		           heightDiff : 30,
		           frozen : false 
	});
}

function getTreeData(){
	var sys_resource_id;
	$.ajax({
		url:basePath+'rest/satsubalone/findgrantusergroupequipmenttree？sys_resource_id='+'-1',
		success:function(data){
			var nodes=eval('('+data+')');
			tree_manager.clear();
			tree_manager.setData(nodes);
		}
	});
}

//function intervalDo(){
//	gridManager.loadData(true);
//}

/**
*
*获取url里的参数
*@param 参数名
*/
function getParam(name){

	var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)","i");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null){
		return unescape(r[2]);
	}
	return null;

}

var xdlg = null;
//工具条按钮事件
function itemclick(item){
	 if(item.id){
		 switch (item.id){
		 
         case "addOrder":
        	 if(node == null){
        		 Alert.tip("请选择树节点！");
        		 return; 
        	 }
        	 if(node.data.node_type != "compute"){
        		 Alert.tip("请选择计算模块节点！");
        		 return;
        	 }
        	//根据计算模块ID查询是否是挂接界面，如果是查询出界面路径
        	var rsObj = getUserDefinedPage(node.data.id);
        	 addWin = $.ligerDialog.open({
					width : 600,
					height : 480,
					title : "新增控制量计算",
					isResize : false,
					url : basePath+rsObj.pagePath+"?typeId="+node.data.pid+"&computeId="+node.data.id+"&satMid="+node.data.sat_mid
					+"&satId="+node.data.sat_id+"&satName="+node.data.sat_name,
					buttons:[
				         { text: '保存',  type:'save' ,width: 80 , onclick:function(item, dialog){
						 	dialog.frame.submitForms();
						 }},
						 { text: '关闭',  type:'close', width: 80 ,  onclick:function(item, dialog){
							 addWin.close();
						 }}
					]
				});
        	 return;
         case "updateOrder":
				if (orderGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				//根据计算模块ID查询是否是挂接界面，如果是查询出界面路径
	        	var rsObj = getUserDefinedPage(orderGridObj.selected[0].comput_id);
	        	if(rsObj.userDefined == 0){
					Alert.tip('挂接界面无法修改！');
	        		return;
	        	}
	        	var state = orderGridObj.selected[0].order_state;
				if(state != "2"){
					Alert.tip('请选择待处理的控制量计算！');
					return;
				}
				var pk_id = orderGridObj.selected[0].pk_id;
				addWin = $.ligerDialog.open({
					width : 600,
					height : 480,
					title : "编辑控制量计算",
					isResize : false,
					url : basePath+"computeRun/runorder_update.jsp?pkId="+pk_id,
					buttons:[
				         { text: '保存',  type:'save' ,width: 80 , onclick:function(item, dialog){
						 	dialog.frame.submitForms();
						 }},
						 { text: '关闭',  type:'close', width: 80 ,  onclick:function(item, dialog){
							 addWin.close();
						 }}
					]
				});
				break;
         case "viewOrder":
				if (orderGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				//根据计算模块ID查询是否是挂接界面，如果是查询出界面路径
	        	var rsObj = getUserDefinedPage(orderGridObj.selected[0].comput_id);
	        	if(rsObj.userDefined == 0){
					Alert.tip('挂接界面无法查看！');
	        		return;
	        	}
				var pk_id = orderGridObj.selected[0].pk_id;
				addWin = $.ligerDialog.open({
					width : 600,
					height : 490,
					title : "查看控制量计算",
					isResize : false,
					url : basePath+"computeRun/runorder_view.jsp?pkId="+pk_id,
					buttons:[
						 { text: '关闭',  type:'close' ,width: 80 ,  onclick:function(item, dialog){
							 addWin.close();
						 }}
					]
				});
				break;
         case "deleteOrder":
				if (orderGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				var state = orderGridObj.selected[0].order_state;
				if(state == "3"){
					Alert.tip('状态为处理中的无法删除！');
					return;
				}
				$.ligerDialog.confirm('确定要删除该控制量计算吗？',function (yes){
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
									Alert.tip('删除成功！');
									orderGridObj.loadData();
								}
							}
						});
					}
				});
        	 break;
		 }
		 
	 }
}

/**
 * 获取用户自己定义的界面路径
 */
function getUserDefinedPage(fucnId){
	var path = {
		isUserDefine:1,
		pagePath:'computeRun/runorder_add.jsp'
	};
		$.ajax({
			url:basePath+'rest/ComputeFunc/view',
			data:{
				computeId:fucnId
			},
			async:false,
			success:function(data){
				var haveData = eval('('+data+')');
				if(haveData.userPagePath){
					path = {
							isUserDefine:0,
							pagePath:haveData.userPagePath
					};
				}
			}
		});
	return path;
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}
//关闭对话框刷新gridManager
function closeDlgAndReload(){
    f_closeDlg();
    loadData();
}

//关闭对话框
function f_closeDlg(){
    xdlg.close();
}
//关闭对话框
function c_closeDlg(){
    ckxq.close();
}

function f_reload(){
	var gridManager = $("#maingrid").ligerGetGridManager();
	gridManager.loadData();
}


function clearForm(){
	   $('#form2').find(':input').each(  
	   function(){
		   if(this.type=="radio"||this.type=="checkbox"){
	    		this.checked=false;
	    	}else{
	    		$(this).val('');  
	    	}
		}     
		);
	   liger.get("tm_param_type").setValue("");
}

//樹刷新
function tree_fresh(){
	tree.reload();
}

function closeAddWinAndLoadGrid(){
	addWin.close();
	orderGridObj.loadData();
}

function closeUpdateWinAndLoadGrid(){
	updateWin.close();
	orderGridObj.loadData();
}