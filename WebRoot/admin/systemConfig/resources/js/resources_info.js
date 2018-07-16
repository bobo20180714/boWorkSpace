//表格数据
var xdlg;
var tree=null;
var re_statue = null;
var gridManager = null;
var editSelectId = "";
var childNodes = null;
var stateData=[
     { name: '未启用', id: '0' },
     { name: '启用', id: '1' },
     { name: '禁用', id: '2' }	        
 ];
var baseImgPath=basePath+'lib/ligerUI/skins/icons/';
$(function ()
{
	$("#layout1").ligerLayout({ leftWidth: 190,allowLeftCollapse:false,heightDiff:0,space:0, onHeightChanged: f_heightChanged });
	var height = $(".l-layout-center").height();
	$("#scollDivTree")[0].style.height = height - 24 + "px";
	//功能菜单
	$("#toptoolbar").ligerToolBar({ items: [
	{ text : '新建',id : 'add',click : itemclick,
		icon:'add'
//		img : baseImgPath+'add.gif'
		}, 
	{ text : '修改',id : 'alter',click : itemclick,
			icon:'update'
//				img : baseImgPath+'edit.gif'
				},
	{ text : '删除',id : 'delete',click : itemclick,

					icon:'delete'
//					img : baseImgPath+'delete.gif'
					}
//	{ text : '启用',id : 'start',click : itemclick,img : baseImgPath+'true.gif'},  
//	{ text : '禁用',id : 'stop',click : itemclick,img : baseImgPath+'busy.gif'}
	]
	});
	
	gridManager = $("#maingrid").ligerGrid({
		columns: [
		          {display: "pk_id",name:"pk_id",id:"pk_id",hide:true,width:0.1},
			{ display: '资源编码', name: 'res_code',width:200},
			{ display: '资源名称', name: 'res_name',width:200},
			{ display: '资源类型', name: 'res_type',width:200,
			render:function (rowdata, rowindex, value){                   		 
           		 switch(value){
           		 	case '0':
           		 		return "资源菜单";
           			case '1':
           				return "页面元素"; 
           		 } 	
       		}},
       		{ display: '显示类型', name: 'show_type',width:200,
       			render:function (rowdata, rowindex, value){                   		 
       				switch(value){
       				case '1':
       					return "子界面";
       				case '2':
       					return "选项卡"; 
       				case '3':
       					return "弹出窗口"; 
       				} 	
       			}},
			{ display: '上级资源id', name: 'res_father',hide:arguments,width:0.1,hide:true},
			{ display: '资源表达式', name: 'res_value',align:'left',width:300},
			{ display: '排序序号', name: 'order_num',width:80}
  		], 
  		height : '100%',
  		width:'99.8%',
		pageSize : 30,
		rownumbers:true,
		checkbox:true,
        cssClass: 'l-grid-gray', 
        heightDiff :0,
        url:basePath+'rest/ResourcesAction/getList',
        frozen : false ,
        delayLoad:true
  	});
	
	  gridManager.set(
      		'parms', {
      			state :"1",
      			resCode:"-1"});
      gridManager.loadData();
	
	$("#pageloading").hide();
   
    //grid查询
    $("#searchbtn").ligerButton({ 
		type:'one',
		click: function ()
	{
    	var resCode = "";
    	var childNodes = tree.getSelected();
    	if (childNodes != null) {
    	 	resCode = childNodes.data.pkId;
    	}else{
    		resCode = "-1";
    	}
    	var queryData = formData1.getData();
        gridManager.set(
        		'parms', { res_name: queryData.resName,
        			state :"1",
        			resCode:resCode});
        gridManager.loadData();
   	}  
   	}); 
	
	var menu_1 = $.ligerMenu({ 
		top: 100, left: 100, width: 120, items:
	    [
	        { text: '新建一级菜单', click: itemclick,id:'addRoot' }
	    ]
	});
	var menu_2 = $.ligerMenu({ 
		top: 100, left: 100, width: 120, items:
			[
			 { text: '新建一级菜单', click: itemclick,id:'addRoot' },
			 { text: '修改信息', click: itemclick, id: 'alterRoot' },
			 { text: '删除节点', click: itemclick,id:'deleteRoot' }
			 ]
	});
    
  //资源管理树形菜单
    tree= $("#tree1").ligerTree({
    	url:basePath+'rest/ResourcesAction/getResourcesTree',
    	textFieldName:'resName',
    	idFieldName : 'pkId',
    	parentIDFieldName : 'resFather',
		topIcon:'folder',
    	checkbox:false,
    	nodeWidth: 200,
    	onContextmenu:function(node, e){
	       	node = this.getSelected();
	       	if(node==null){
	       		return;
	       	}
	       	
	       	if(node.data.resFather == ""){
	       		menu_1.hide();
	       		menu_2.show({ top: e.pageY, left: e.pageX });
	       	}else{
	       		menu_2.hide();
	       		menu_1.show({ top: e.pageY, left: e.pageX });
	       	}
	       	return false;
       
      	},
    	onClick:onCheck,
    	onCancelSelect:onCancelSelects
    });
    
	//重置搜索条件
	$("#resetbtn").ligerButton({
		type:'two',
		click : function() {
			clearForm();
		}
	});
	initForm2();
});

function onCancelSelects(){
	 gridManager.set(
	      		'parms', {
	      			state :"1",
	      			resCode:"-1"});
	      gridManager.loadData();
}

function initForm2(){
    formData1 = $("#form1").ligerForm({
    		labelAlign:'right',
        	inputWidth: 100, 
            labelWidth: 80, 
            space: 5,
            validate:true,
            fields: [
				{display: '资源名称', id: 'resName',name:'resName',type:"text",newline: false}
			]
    });
}


function itemclick(item) {

	if (item.id) {
		switch (item.id) {
		
			//新建
	case "add" :
		setCurrWindows();
		childNodes = tree.getSelected();
		/*if (childNodes == null) {
			Alert.tip('请选择左边上级资源！');
			return;
		}*/
		xdlg = $.ligerDialog.open({
			width : 400,
			height : 300,
			title : '新建资源信息',
			isResize : false,
			url : basePath + 'admin/systemConfig/resources/resources_add.jsp',
			buttons :[
	            { text: '保存', type:'save',width: 80 ,onclick:function(item, dialog){
	             	dialog.frame.submitForm();
	             }},
	            { text: '关闭', type:'close',width: 80 , onclick:function(item, dialog){
	            	dialog.frame.colseWindow();
	            	xdlg.close();
	            }}
			]
		});
		return;
		//新建
	case "addRoot" :
		setCurrWindows();
		childNodes = null;
		xdlg = $.ligerDialog.open({
			width : 400,
			height : 300,
			title : '新建一级菜单',
			isResize : false,
			url : basePath + 'admin/systemConfig/resources/resources_add.jsp',
			buttons :[
			          { text: '保存',  type:'save',width: 80 ,onclick:function(item, dialog){
			        	  dialog.frame.submitForm();
			          }},
			          { text: '关闭',  type:'close',width: 80 , onclick:function(item, dialog){
			        	  dialog.frame.colseWindow();
			        	  xdlg.close();
			          }}
			          ]
		});
		return;
		
	//修改
	case "alter" :
		var rowsdata = gridManager.getCheckedRows();
		if (gridManager.selected.length != 1) {
			Alert.tip('请选择一条数据！');
			return;
		}
		/*if (gridManager.selected[0].state == 1) {
			Alert.tip('数据在启用状态下不能修改！');
			return;
		} else {*/
			setCurrWindows();
			editSelectId = gridManager.selected[0].pk_id;
			xdlg = $.ligerDialog.open({
				width : 400,
				height : 300,
				title : '修改资源信息',
				url : basePath + 'admin/systemConfig/resources/resources_alter.jsp',
				buttons :[
		            { text: '保存',  type:'save',width: 80 ,onclick:function(item, dialog){
		             	dialog.frame.submitForm();
		             }},
		            { text: '关闭',  type:'close',width: 80 , onclick:function(item, dialog){
		            	dialog.frame.colseWindow();
		            	xdlg.close();
		            }}
				]
			});
		/*}*/
		return;
	case "alterRoot" :
		setCurrWindows();
		childNodes = tree.getSelected();
		editSelectId = childNodes.data.pkId;
		xdlg = $.ligerDialog.open({
			width : 400,
			height : 300,
			title : '修改资源信息',
			url : basePath + 'admin/systemConfig/resources/resources_alter.jsp',
			buttons :[
			          { text: '保存',  type:'save',width: 80 ,onclick:function(item, dialog){
			        	  dialog.frame.submitForm();
			          }},
			          { text: '关闭',  type:'close',width: 80 , onclick:function(item, dialog){
			        	  dialog.frame.colseWindow();
			        	  xdlg.close();
			          }}
			          ]
		});
		return;
		
	//删除资源
	case "delete" :
		if (!gridManager.getSelectedRow()) {
			Alert.tip('请至少选择一条数据！');
			return;
		}
		
		/*for ( var i = 0; i < gridManager.selected.length; i++) {
			if (gridManager.selected[i].state == 1) {
				Alert.tip("只能删除状态为未启用或禁用的用户");
				return;
			}
		}*/
		
			$.ligerDialog.confirm('您确定要删除该资源信息吗？',
				function(yes) {
					if (yes) {
						var selectKeyID = '';
						for (var i = 0; i < gridManager.selected.length; i++) {
								selectKeyID += gridManager.selected[i].pk_id +";";
						}
						var retObj = $.ajax({
							url : basePath + 'rest/ResourcesAction/delete?resCodes='
									+ selectKeyID,
							async : false
						});

						var dataObj = eval("(" + retObj.responseText + ")");
						if (dataObj.success == 'true') {
							Alert.tip('删除成功！');
							f_reload();
							gridManager.loadData(true);
						} else {
							Alert.tip(dataObj.message);
						}
					}
				}
			);
		return;
		
	case "deleteRoot" :
		
		//判断是否有子节点
		childNodes = tree.getSelected();
		var children = childNodes.data.children;
		if(children != null && children.length != 0){
			Alert.tip('有子节点不能删除！');
			return;
		}
		$.ligerDialog.confirm('您确定要删除该资源信息吗？',
				function(yes) {
			if (yes) {
				var selectKeyID = childNodes.data.pkId;
				var retObj = $.ajax({
					url : basePath + 'rest/ResourcesAction/delete?resCodes='
					+ selectKeyID,
					async : false
				});
				
				var dataObj = eval("(" + retObj.responseText + ")");
				if (dataObj.success == 'true') {
					Alert.tip('删除成功！');
					f_reload();
					gridManager.loadData(true);
				} else {
					Alert.tip(dataObj.message);
				}
			}
		}
		);
		return;
		
	//禁用
	case "stop" :
		if (!gridManager.getSelectedRow()) {
			Alert.tip('请至少选择一条数据！');
			return;
		}
		
		for ( var i = 0; i < gridManager.selected.length; i++) {
			if (gridManager.selected[i].state != 1) {
				Alert.tip('只能禁用启用状态的资源信息！');
				return;
			}
		}
		
		
			$.ligerDialog.confirm('您确定要禁用选择的资源信息吗？', 
				function(yes) {
					if (yes) {
						var selectKeyID = '';
						for (var i = 0; i < gridManager.selected.length; i++) {
							selectKeyID += gridManager.selected[i].pk_id+";";
						}

						var retObj = $.ajax({
							url : basePath + 'rest/ResourcesAction/stop?resCodes='
									+ selectKeyID,
							async : false
						});

						var dataObj = eval("(" + retObj.responseText + ")");
						if (dataObj.success == 'true') {
							Alert.tip('禁用成功！');
							gridManager.loadData(true);
						} else {
							Alert.tip(dataObj.message);
						}
					}
				}
			);
		return;

	// 启用
	case "start" :
		if (!gridManager.getSelectedRow()) {
			Alert.tip('请至少选择一条数据！');
			return;
		}
		
		for ( var i = 0; i < gridManager.selected.length; i++) {
			if (gridManager.selected[i].state == 1) {
				Alert.tip('只能启用未启用或禁用状态的资源信息！');
				return;
			}
		}
		
			$.ligerDialog.confirm('您确定要启用选择的资源信息吗？', 
				function(yes) {
					if (yes) {
						var selectKeyID = '';
						for (var i = 0; i < gridManager.selected.length; i++) {
							selectKeyID += gridManager.selected[i].pk_id+";";
						}

						var retObj = $.ajax({
							url : basePath + 'rest/ResourcesAction/start?resCodes='
									+ selectKeyID,
							async : false
						});

						var dataObj = eval("(" + retObj.responseText + ")");
						if (dataObj.success == 'true') {
									Alert.tip('启用成功！');
									gridManager.loadData(true);
								} else {
									Alert.tip(dataObj.message);
								}
							}
						}
					);
				return;
		}
	}
}
	      
function clearForm(){
	formData1.setData({
		resName:""
	});
}


function onCheck() {
	var childNodes = tree.getSelected();
	if(childNodes==null){
		return;
	}
	var resCode = childNodes.data.pkId;
	  gridManager.set(
      		'parms', { 
      			state :"1",
      			resCode:resCode});
	gridManager.loadData();
	/*$("#maingrid").ligerGrid({
		url: basePath+'rest/ResourcesAction/byIdGetList?resCode='+resCode
	});*/
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	currWin = window;
}

//刷新grid
function f_reload() {
	gridManager.set({
		newPage : 1
	});
	
	gridManager.loadData(true);
	refreshTree();
}
//关闭对话框刷新grid
function closeDlgAndReload() {
	f_closeDlg();
	f_reload();
}
//关闭对话框
function f_closeDlg() {
	xdlg.close();
}
function refreshTree() {
	tree.clear();
	tree.loadData("", basePath + "rest/ResourcesAction/getResourcesTree", "");
}

//窗口变化事件
function f_heightChanged(options) {
	var treeHeight = $("#scollDivTree")[0].style.height;
	$("#scollDivTree")[0].style.height = parseFloat(treeHeight.substring(0,treeHeight.length-2))+options.diff + "px";
	
}