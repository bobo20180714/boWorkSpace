var filegridObj = null;
var treeObj = null;
var nowSelectNode = null;

var menuClickNode = null;

var relateWin = null;

var toolbar = null;

$(function (){
	initTreeController();
	$("#layout1").ligerLayout({ 
		leftWidth: 190,
		allowLeftCollapse:false,
		hideLeftHeader:true,
		heightDiff:0,
		space:1, 
		onHeightChanged: f_heightChanged 
	});
	var height = $(".l-layout-center").height();
	$("#scollDivTree")[0].style.height = height-24;
	
	addFileMenu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
		 [
		  { text: '新建自定义文件', click: itemclick, id: 'addFile' }
		  ]
	 });
	
	
	userFileMenu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
		 [
		  { text: '修改', click: itemclick, id: 'updateUserFile' },
		  { text: '删除', click: itemclick, id: 'deleteUserFile' }
		  ]
	 });
	
	//初始化工具条
	initToolBar();
	//初始表格
	initOrderGrid();

});

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

/**
 * 定义左侧树结构
 */
function initTreeController(){
	treeObj = $("#tree1").ligerTree({
		checkbox: false,
		onselect :onselects,
        textFieldName:'text',
		idFieldName : 'id',
		parentIDFieldName : 'pid',
		height: "100%",
		nodeWidth:"100%",
		url:basePath+'rest/SatRelateFile/queryMonitorTree?ownerId=-1',
		isLeaf : function(data){
			return data.isleaf == 0;
		},
		delay: function(e)
		{
		     var data = e.data;
		     return { url: basePath+'rest/SatRelateFile/queryMonitorTree?ownerId=' + data.id };
		 },
		 onContextmenu: function (nodeTemp, e){
    		 hiddenMenu();
			 menuClickNode = nodeTemp;
	    	 if(isCanCreate(nodeTemp.data) == "2"){
	    		 addFileMenu.show({ top: e.pageY, left: e.pageX });
	    	 }else if(nodeTemp.data.node_type == "3"){
	    		 userFileMenu.show({ top: e.pageY, left: e.pageX });
	    	 }else{
	    		 hiddenMenu();
	    	 }
	    	 return false;
    	 }

	});
}

/**
 * 是否可以弹出右键菜单
 * @param data
 * @returns {Boolean}
 */
function isCanCreate(data){
	if((data.node_type == "1" && data.isroot == "1")
			 || data.node_type == "4"){
		//弹出新建文件夹菜单
			return "1";
	}
	if(data.node_type == "0" ){
		//弹出新建文件菜单
		return "2";
	}
	return "false";
}

/**
 * 隐藏右键菜单
 */
function hiddenMenu(){
	addFileMenu.hide();
	userFileMenu.hide();
}

/**
 * 展开节点事件
 * @param e
 */
function onExpands(e){
/*	$.ajax({
		url:basePath+'rest/SatRelateFile/queryMonitorTree?ownerId=' + e.data.id,
		success:function(data){
			var nodes=eval('('+data+')');
			treeObj.setData(nodes);
		}
	});*/
}

function onselects(e){
	nowSelectNode = e;
	
	if(e.data.node_type == "0" || e.data.node_type == "3"){
		//综合
		updateToolbar(0);
	}else{
		updateToolbar();
	}
	
	if(isCanCreate(e.data)){
		filegridObj.set('parms', {
			ownerId : e.data.id,
			ownerType : e.data.node_type
		});
	}else{
		filegridObj.set('parms', {
			ownerId :"-10"
		});
	}
	filegridObj.set({newPage:1});
	filegridObj.loadData(false);
}

//初始化工具条
function initToolBar() {
	toolbar = $("#toolbar").ligerToolBar({
		items : [ {
			text : '关联页面',
			id : 'addRelateFile',
			click : itemclick,
			icon:'add'
		},{
			text : '删除关联',
			id : 'deleteRelate',
			click : itemclick,
			icon:'delete'
		}]
	});
}

function updateToolbar(type){
	removeAllItem();
	if(type == 0){
		var items = [
			{
				text : '新增',
				id : 'addUserFileBar',
				click : itemclick,
				icon:'add'
			}, {
				text : '修改',
				id : 'updateUserFileBar',
				click : itemclick,
				icon:'update'
			}, {
				text : '删除',
				id : 'deleteUserFileBar',
				click : itemclick,
				icon:'delete'
			}
		];
		//点击综合
		toolbar.addItem({
			text : '新增',
			id : 'addUserFileBar',
			click : itemclick,
			icon:'add'
		});
		toolbar.addItem({
			text : '修改',
			id : 'updateUserFileBar',
			click : itemclick,
			icon:'update'
		});
		toolbar.addItem({
			text : '删除',
			id : 'deleteUserFileBar',
			click : itemclick,
			icon:'delete'
		});
	}else{
		var items = [{
			text : '关联页面',
			id : 'addRelateFile',
			click : itemclick,
			icon:'add'
		},{
			text : '删除关联',
			id : 'deleteRelate',
			click : itemclick,
			icon:'delete'
		}];
		toolbar.addItem({
			text : '关联页面',
			id : 'addRelateFile',
			click : itemclick,
			icon:'add'
		});
		toolbar.addItem({
			text : '删除关联',
			id : 'deleteRelate',
			click : itemclick,
			icon:'delete'
		});
	}
}

function removeAllItem(){
	toolbar.removeItem("addRelateFile");
	toolbar.removeItem("deleteRelate");
	toolbar.removeItem("addUserFileBar");
	toolbar.removeItem("updateUserFileBar");
	toolbar.removeItem("deleteUserFileBar");
}

//初始订单表格
function initOrderGrid(){
	filegridObj = $("#filegrid").ligerGrid({
		columns : [
	           {
	        	   display : '主键',
	        	   name : 'pk_id',
	        	   hide:true,
	        	   width : 0.1
	           },
	           {
	        	   name : 'obj_id',
	        	   hide:true,
	        	   width : 0.1
	           },
				{
					display : '页面名称',
					name : 'name',
					align : 'center',
					width : 300,
					render:function(item){
						if(item.page_name){
							return '<p title="'+item.page_name+'">'+item.page_name+'</p>';
						}else{
							return '<p title="'+item.name+'">'+item.name+'</p>';
						}
					}
				},
				{
					display : '页面类型',
					name : 'type',
					align : 'center',
					width : 300,
					render:function(item){
						if(item.type == "4"){
							return "文件夹";
						}else if(item.type == "5" || item.type == "3"){
							return "文件";
						}
					}
				},
				{
					display : '自定义页面打开方式',
					name : 'open_mode',
					align : 'center',
					width : 150,
					render:function(item){
						if(item.type == "3"){
							if(item.open_mode == "1"){
								return "新窗口";
							}else if(item.open_mode == "2"){
								return "选项卡";
							}
						}
					}
				}
		],
		rownumbers : false,
		checkbox : true,
		rowHeight : 27,
		pageSize:20,
		height:'100%',
		delayLoad:true,
		url:basePath + 'rest/SatRelateFile/queryRelateFileList'
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
		 case "addRelateFile":
        	 if(nowSelectNode == null){
        		 Alert.tip("请选择树节点！");
        		 return; 
        	 }
        	 if((nowSelectNode.data.node_type != "1"
        		 || nowSelectNode.data.isroot != "1")
        		 && nowSelectNode.data.node_type != "4"){
        		 Alert.tip("请选择卫星节点或文件夹节点！");
        		 return;
        	 }
        	 setCurrWindows();
        	 relateWin = parent.$.ligerDialog.open({
					width : 820,
					height : 500,
					title : "关联页面",
					isResize : false,
					url : basePath+"page/select_file.jsp?super_id="+nowSelectNode.data.id,
					buttons:[
				         /*{ text: '保存',  type:'save' ,width: 80 , onclick:function(item, dialog){
						 	dialog.frame.submitForms();
						 }},*/
						 { text: '关闭',  type:'close', width: 80 ,  onclick:function(item, dialog){
							 relateWin.close();
						 }}
					]
				});
        	 return;
		 
         case "addFload":
        	 if(menuClickNode == null){
        		 Alert.tip("请鼠标右键点击树节点！");
        		 return; 
        	 }
        	 if(isCanCreate(menuClickNode.data) != "2"){
        		 Alert.tip("请鼠标右键点击卫星信息、文件夹节点！");
        		 return;
        	 }
        	 var addWin = $.ligerDialog.open({
					width : 350,
					height :150,
					title : "新建文件夹",
					isResize : false,
					url : basePath+"page/addFload.jsp?ownerId="+menuClickNode.data.id,
					buttons:[
				         { text: '保存',  type:'save' ,width: 80 , onclick:function(item, dialog){
						 	var flag = dialog.frame.submitForms();
						 	if(flag.success == "true"){
						 		Alert.tip("保存成功！");
								addWin.close();
								 treeObj.append(menuClickNode.target, [{
					        		   id:flag.data.pk_id,
					        		   text:flag.data.name,
					        		   pid:menuClickNode.data.id,
					        		   node_type:4,
					        		   isroot:1,
					        		   isleaf:1
					        	   }]);
						 	}else{
						 		Alert.tip("保存失败！");
						 	}
						 }},
						 { text: '关闭',  type:'close', width: 80 ,  onclick:function(item, dialog){
							 addWin.close();
						 }}
					]
				});
        	 return;
        	 
         case "addFile":
        	 if(menuClickNode == null){
        		 Alert.tip("请鼠标右键点击树节点！");
        		 return; 
        	 }
        	 if(isCanCreate(menuClickNode.data) != "2" ){
        		 Alert.tip("请鼠标右键点击综合节点！");
        		 return;
        	 }
        	 var addWin = $.ligerDialog.open({
					width : 500,
					height :250,
					title : "新建自定义文件",
					isResize : false,
					url : basePath+"page/addUserFile.jsp?ownerId="+menuClickNode.data.id,
					buttons:[
				         { text: '保存',  type:'save' ,width: 80 , onclick:function(item, dialog){
						 	var flag = dialog.frame.submitForms();
						 	if(flag.success == "true"){
						 		Alert.tip("保存成功！");
								addWin.close();
								 treeObj.append(menuClickNode.target, [{
					        		   id:flag.data.pk_id,
					        		   text:flag.data.name,
					        		   pid:menuClickNode.data.id,
					        		   isleaf:0,
					        		   node_type:"3"
					        	   }]);
								 loadGridData1(2);
						 	}else{
						 		Alert.tip("保存失败！");
						 	}
						 }},
						 { text: '关闭',  type:'close', width: 80 ,  onclick:function(item, dialog){
							 addWin.close();
						 }}
					]
				});
        	 return;
         case "addUserFileBar":
        	 if(nowSelectNode == null){
        		 Alert.tip("请选择树节点！");
        		 return; 
        	 }
        	 if(isCanCreate(nowSelectNode.data) != "2" ){
        		 Alert.tip("请选择综合节点！");
        		 return;
        	 }
        	 var addWin = $.ligerDialog.open({
					width : 500,
					height :250,
					title : "新建自定义文件",
					isResize : false,
					url : basePath+"page/addUserFile.jsp?ownerId="+nowSelectNode.data.id,
					buttons:[
				         { text: '保存',  type:'save' ,width: 80 , onclick:function(item, dialog){
						 	var flag = dialog.frame.submitForms();
						 	if(flag.success == "true"){
						 		Alert.tip("保存成功！");
								addWin.close();
								 treeObj.append(nowSelectNode.target, [{
					        		   id:flag.data.pk_id,
					        		   text:flag.data.name,
					        		   pid:nowSelectNode.data.id,
					        		   isleaf:0,
					        		   node_type:"3"
					        	   }]);
								 loadGridData1(2);
						 	}else{
						 		Alert.tip("保存失败！");
						 	}
						 }},
						 { text: '关闭',  type:'close', width: 80 ,  onclick:function(item, dialog){
							 addWin.close();
						 }}
					]
				});
        	 return;
         case "updateUserFile":
        	 if(menuClickNode == null){
        		 Alert.tip("请鼠标右键点击树节点！");
        		 return; 
        	 }
        	 var addWin = $.ligerDialog.open({
					width : 500,
					height :250,
					title : "修改自定义文件",
					isResize : false,
					url : basePath+"page/updateUserFile.jsp?pkId="+menuClickNode.data.id,
					buttons:[
				         { text: '保存',  type:'save' ,width: 80 , onclick:function(item, dialog){
						 	var flag = dialog.frame.submitForms();
						 	if(flag.success == "true"){
						 		Alert.tip("保存成功！");
								addWin.close();
								treeObj.update(menuClickNode.target, {
				            		text:flag.data.name,
				            		page_url:flag.data.page_url,
				            		open_mode:flag.data.open_mode
				            	});
						 	}else{
						 		Alert.tip("保存失败！");
						 	}
						 }},
						 { text: '关闭',  type:'close', width: 80 ,  onclick:function(item, dialog){
							 addWin.close();
						 }}
					]
				});
        	 return;
         case "updateUserFileBar":
        	 if(nowSelectNode == null){
        		 Alert.tip("请选择树节点！");
        		 return; 
        	 }
        	 if (filegridObj.selected.length != 1) {
					Alert.tip('请选择一条表格数据！');
					return;
			 }
        	var fileId =  filegridObj.selected[0].pk_id;
					
        	 var addWin = $.ligerDialog.open({
					width : 500,
					height :250,
					title : "修改自定义文件",
					isResize : false,
					url : basePath+"page/updateUserFile.jsp?pkId="+fileId,
					buttons:[
				         { text: '保存',  type:'save' ,width: 80 , onclick:function(item, dialog){
						 	var flag = dialog.frame.submitForms();
						 	if(flag.success == "true"){
						 		Alert.tip("保存成功！");
								addWin.close();
								reloadTreeAndGrid(2);
						 	}else{
						 		Alert.tip("保存失败！");
						 	}
						 }},
						 { text: '关闭',  type:'close', width: 80 ,  onclick:function(item, dialog){
							 addWin.close();
						 }}
					]
				});
        	 return;
         case "deleteUserFile":
	        	 if(menuClickNode == null){
	        		 Alert.tip("请鼠标右键点击树节点！");
	        		 return; 
	        	 }
				$.ligerDialog.confirm('确定要删除自定义文件吗？',function (yes){
					if(yes){
						$.ajax({
							url:basePath+'rest/SatRelateFile/deleteUserFile',
							data:{
								pkId:menuClickNode.data.id
							},
							async:false,
							success:function(rsStr){
								var rsData = eval('('+rsStr+')');
								if(rsData.success == "true"){
									Alert.tip('删除成功！');
									afterDeleteLoadData(2);
								}
							}
						});
					}
				});
				break;
         case "deleteUserFileBar":
        	 if(nowSelectNode == null){
        		 Alert.tip("请选择树节点！");
        		 return; 
        	 }
        	 if (filegridObj.selected.length != 1) {
					Alert.tip('请选择一条表格数据！');
					return;
			 }
			$.ligerDialog.confirm('确定要删除自定义文件吗？',function (yes){
				if(yes){
					$.ajax({
						url:basePath+'rest/SatRelateFile/deleteUserFile',
						data:{
							pkId:filegridObj.selected[0].pk_id
						},
						async:false,
						success:function(rsStr){
							var rsData = eval('('+rsStr+')');
							if(rsData.success == "true"){
								Alert.tip('删除成功！');
								reloadTreeAndGrid(2);
							}
						}
					});
				}
			});
			break;
         case "deleteRelate":
				if (filegridObj.selected.length < 1) {
					Alert.tip('请选择数据！');
					return;
				}
				var fileIds = "";
				for (var i = 0; i < filegridObj.selected.length; i++) {
					var rowData = filegridObj.selected[i];
					if(rowData.type != "4" && rowData.type != "5"){
						Alert.tip('请选择关联的文件夹或文件！');
						return;
					}
					
					/*//判断文件夹下是否有文件或文件夹
					if(rowData.type == "4"){
						if(haveSon(rowData.pk_id)){
							Alert.tip('文件夹['+rowData.name+']下存在文件夹或文件，请先删除子项！');
							return;
						}
					}*/
					
					if(i == 0){
						fileIds = rowData.obj_id;
					}else{
						fileIds = fileIds + ";" + rowData.obj_id;
					}
				}
				
				$.ligerDialog.confirm('确定要删除关联吗？',function (yes){
					if(yes){
						$.ajax({
							url:basePath+'rest/SatRelateFile/deleteRelate',
							data:{
								ownerId:nowSelectNode.data.id,
								fileIds:fileIds
							},
							async:false,
							success:function(rsStr){
								var rsData = eval('('+rsStr+')');
								if(rsData.success == "true"){
									Alert.tip('删除成功！');
									loadGridData(nowSelectNode.data.id);
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
 * 右键删除时，刷新树
 * @param ownerId
 */
function afterDeleteLoadData(ownerId){
	var nodeTypeTemp = menuClickNode.data.node_type;
	if(nodeTypeTemp == "3"){
		treeObj.remove(menuClickNode);
		if(nowSelectNode && nowSelectNode.data.node_type == "0"){
			filegridObj.set("parms",{
				ownerId:ownerId,
				ownerType:"0"
			});
			filegridObj.loadData();
		}
	}
}

function loadGridData1(ownerId){
	filegridObj.set('parms', {
		ownerId : ownerId,
		ownerType :"0"
	});
	filegridObj.loadData();
}

function haveSon(id){
	var flag = false;
	$.ajax({
		url:basePath+'rest/SatRelateFile/queryMonitorTree',
		data:{
			ownerId:id
		},
		async:false,
		success:function(data){
			var haveData = eval('('+data+')');
			if(haveData.length > 0){
				flag = true;
			}
		}
	});
	return flag;
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
function c_closeDlg(){
	relateWin.close();
}

/**
 * 移除子节点，刷新树节点，刷新表格数据
 * @param ownerId
 */
function loadGridData(ownerId){
	//移除子节点
	var childNodes = nowSelectNode.target.childNodes;
	for(var i = 1;i<childNodes.length;i++){
		treeObj.remove(childNodes[i]);
	}
	treeObj.loadData(nowSelectNode,basePath+'rest/SatRelateFile/queryMonitorTree?ownerId=' + ownerId);
	filegridObj.set("parms",{
		"ownerId":ownerId
	});
	filegridObj.loadData();
}

/**
 * 修改表格自定义文件后刷新树和表格
 * @param ownerId
 */
function reloadTreeAndGrid(ownerId){
	//移除子节点
	var childNodes = nowSelectNode.target.childNodes;
	for(var i = 1;i<childNodes.length;i++){
		treeObj.remove(childNodes[i]);
	}
	treeObj.loadData(nowSelectNode,basePath+'rest/SatRelateFile/queryMonitorTree?ownerId=' + ownerId);
	filegridObj.set("parms",{
		"ownerId":ownerId,
		ownerType:0
	});
	filegridObj.loadData();
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
