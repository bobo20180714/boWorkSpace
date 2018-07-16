var formData = null;
var tree = null;
var sat_id = "-1";
var type_id = "";
var node = null;
var currentTreeNode;//当前点击右键的节点
var gridManager=null;
//超时时长
var overTimeArr = [{id:'1',text:'1'},{id:'2',text:'2'},{id:'3',text:'3'},{id:'4',text:'4'},{id:'5',text:'5'},{id:'6',text:'6'},
					{id:'7',text:'7'},{id:'8',text:'8'},{id:'9',text:'9'},{id:'10',text:'10'},{id:'20',text:'20'},
					{id:'30',text:'30'},{id:'50',text:'50'},{id:'100',text:'100'}];
//计算失败次数
var computCountArr = [{id:'1',text:'1'},{id:'2',text:'2'},{id:'3',text:'3'},{id:'4',text:'4'},{id:'5',text:'5'},{id:'6',text:'6'},
					{id:'7',text:'7'},{id:'8',text:'8'},{id:'9',text:'9'},{id:'10',text:'10'}];

var dataArr = [{id:'0',text:'是'},{id:'1',text:'否'}];

var orderStatus = [{id:"0",text:"废弃"},{id:"1",text:"新建"},{id:"2",text:"待处理"},
                   {id:"3",text:"处理中"},{id:"4",text:"处理完成"},{id:"5",text:" 处理失败"},{id:"9",text:"删除"}];
$(function (){
	initTreeController();
	$("#layout1").ligerLayout({ 
		leftWidth: 190,
		allowLeftCollapse:false,
		heightDiff:0,
		space:1
	});
	var height = $(".l-layout-center").height();
	$("#scollDivTree")[0].style.height = height-24;
	
	 menu = $.ligerMenu({ 
		 top: 100, 
		 left: 100, 
		 width: 120, 
		 items:[
//		        { text: '新增分组', click: itemclick, id: 'addComputeType' },
		        { text: '新增计算类型', click: itemclick, id: 'addComputeType' }
		       ]
     });
	 menu1 = $.ligerMenu({ top: 100, left: 100, width: 120, items:
	         [
			 { text: '修改', click: itemclick, id: 'updateComputeType' },
			 { text: '删除', click: itemclick, id: 'deleteComputeType' },
//			 { text: '新增分组', click: itemclick, id: 'addComputeType' },
		        { text: '新增计算类型', click: itemclick, id: 'addComputeType' }
	         ]
     });
	 menu2 = $.ligerMenu({ top: 100, left: 100, width: 120, items:
		 [
			 { text: '修改', click: itemclick, id: 'updateComputeType' },
			 { text: '删除', click: itemclick, id: 'deleteComputeType' }
		  ]
	 });
	 
	initForm();
	//初始订单表格
	initGridData();

});

function initGridData(){
	//请选择计算模块的表格
	gridManager=$("#maingrid").ligerGrid({
	    columns: [
	              	{display: '主键', id:'pk_id', name: 'pk_id',width:0.1,hide:true},
	              	{display: '选中后主键', id:'relate_pk_id', name: 'relate_pk_id',width:0.1,hide:true},
	              	{display: '计算模块名称', name: 'compute_name',align: 'center',width:200},
	    			{display: "java类全路径", name: "fct_all_path_namej",align: 'left',width:340,
	              		render:function(item){
    						if(item.fct_all_path_namej){
    							return '<p title="'+item.fct_all_path_namej+'">'+item.fct_all_path_namej+'</p>';
    						}
	              		}
	    			},
					{
						display : '计算模块描述',
						name : 'compute_desc',
						align : 'left',
						width : 300,
	              		render:function(item){
    						if(item.compute_desc){
    							return '<p title="'+item.compute_desc+'">'+item.compute_desc+'</p>';
    						}
	              		}
					},
					/*{
						display : '计算类型',
						name : 'compute_type_name',
						align : 'center',
						width : 200
					},*/ 			
	              	{display: '超时时间', name: 'over_time',align: 'center',width:200,
	    				editor:{
	    					type:'select',
	    					data:overTimeArr
	    				}
	              	},
	              	{display: '最大失败次数', name: 'compute_count',align: 'center',width:200,
	    				editor:{
	    					type:'select',
	    					data:computCountArr
	    				}
	              	},
	              	/*{display: '结果是否入库', name: 'is_save_result',align: 'center',width:200,
	    					render:function(item){
	    						if(item.is_save_result){
    			        			  for(var i = 0;i<dataArr.length;i++){
    			        				  if(item.is_save_result == dataArr[i].id){
    					        			   return dataArr[i].text;
    					        		   }
    			        			  }
	    						}
	    					},
		    				editor:{
		    					type:'select',
		    					data:dataArr
		    				}
	    			},*/
	              	{display: '结果是否发出', name: 'is_multicast',align: 'center',width:200,
		    					render:function(item){
		    						if(item.is_multicast){
	    			        			  for(var i = 0;i<dataArr.length;i++){
	    			        				  if(item.is_multicast == dataArr[i].id){
	    					        			   return dataArr[i].text;
	    					        		   }
	    			        			  }
		    						}
		    					},
			    				editor:{
			    					type:'select',
			    					data:dataArr
			    				}
		    		}
	    ], 
	    height: '100%', 
	    rownumbers:true,
	    enabledEdit: true, 
//	    pageSize:10,
	    checkbox : true,
	    usePager:false,
	    rowHeight:27,
	    heightDiff:30,
	    onCheckAllRow:onCheckAllRows,//全选、全不选
	    onCheckRow:onCheckRows,//选择事件（复选框）
	    onAfterShowData:onAfterShowDatas,//显示完数据事件
	    onBeforeCheckRow:isSelectSat,//选择前事件
	    onBeforeCheckAllRow:isSelectSat,
	    onAfterEdit: f_onAfterEdit,//编辑后事件
	    url:basePath+'rest/ComputeFunc/queryAllComputeList'
	});
}

function initForm(){
    formObj1 = $("#form1").ligerForm({
    		labelAlign:'right',
        	inputWidth: 140, 
            labelWidth: 100, 
            space: 5,
            fields: [
				{display: '计算模块名称', id: 'computeName',name:'computeName',type:"text",newline: false}
			]
    });
	$("#searchbtn").ligerButton({
		type:"one", 
		click : function() {
			queryList();
		}
	});
	
	// 重置搜索条件
	$("#resetbtn").ligerButton({
		type:"two", 
		click : function() {
			formObj1.setData({
				computeName:"",
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
		url:basePath+'rest/ComputeType/queryComputeTypeTree?ownerId=-1',
		isLeaf : function(data){
			return data.is_leaf == "0";
		},
		delay: function(e)
		{
		     var data = e.data;
		     return { url: basePath+'rest/ComputeType/queryComputeTypeTree?ownerId=' + data.id };
		 },
		 onContextmenu: function (node, e){
	    	 currentTreeNode = node.data;
	    	 tree.selectNode(currentTreeNode); 
/*	    	 var selNode = tree.getSelected();
	    	 if(!selNode){
	    		 Alert.tip("请选择树节点！");
	    		 return false;
	    	 }
//	    	 tree.cancelSelect(selNode.data); 
*/	    	 if(node.data.node_type == "SAT"){
	    		 hiddenMenu();
	    		 menu.show({ top: e.pageY, left: e.pageX });
	    	 }else if(node.data.node_type == "TYPE"){
	    		 if(node.data.is_leaf == "0"){
		    		 hiddenMenu();
	    			 menu2.show({ top: e.pageY, left: e.pageX });
	    		 }else{
		    		 hiddenMenu();
	    			 menu1.show({ top: e.pageY, left: e.pageX });
	    		 }
	    	 }
	    	 return false;
    	 }

	});
}

function hiddenMenu(){
	menu.hide();
	menu1.hide();
	menu2.hide();
}

function onselect(){
	node = tree.getSelected();
	if(node!=null){
		sat_id = node.data.sat_id;
		if(node.data.node_type == "SAT"){
			type_id = "";
		}else{
			type_id = node.data.id;
		}
	}else{
		sat_id = "-1";
		type_id = "";
	}
	f_reload();
	//获取已经关联的相关信息，并选中
	getRelatedByTypeId();
}

function getTreeData(){
	$.ajax({
		url:basePath+'rest/satsubalone/findgrantusergroupequipmenttree?sys_resource_id='+'-1',
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

//树刷新
function tree_fresh(){
	tree.reload();
}


function onAfterShowDatas(){
	//获取已经关联的相关信息，并选中
	getRelatedByTypeId();
}

function getRelatedByTypeId(){
	$.ajax({
		url:basePath+'rest/ComputeType/getRelatedByTypeId',
		data:{
			satId:sat_id,
			typeId:type_id
		},
		async:false,
		success:function(data){
			var haveData = eval('('+data+')');
			for (var i = 0; i < gridManager.rows.length; i++) {
				for (var j = 0; j < haveData.length; j++) {
					if(gridManager.rows[i].pk_id == haveData[j].compute_func_id){
						gridManager.updateCell('relate_pk_id',haveData[j].pk_id,gridManager.rows[i]);
						gridManager.updateCell('over_time',haveData[j].over_time,gridManager.rows[i]);
						gridManager.updateCell('compute_count',haveData[j].compute_count,gridManager.rows[i]);
						gridManager.updateCell('is_multicast',haveData[j].is_multicast,gridManager.rows[i]);
						gridManager['select'](gridManager.rows[i]);
						break;
					}
				}
			}
		}
	});
}

function removeRelate(){
	for (var i = 0; i < gridManager.rows.length; i++) {	
		gridManager.updateCell('relate_pk_id',"",gridManager.rows[i]);
	}	
}

function isSelectSat(){
	node = tree.getSelected();
	if(node == null){
		Alert.tip("请先选择计算类型！");
		return false; 
	}
	if(node.data.is_leaf != "0"){
		Alert.tip("请先选择计算类型！");
		return false;
	}
}

function onCheckAllRows(flag){
	if(flag){
		//添加关联关系
		addAllSatRelatedInfo();
		
	}else{
		//移除关联关系
		removeAllSatRelatedInfo();
		removeRelate();
	}
}
function onCheckRows(flag,data){
	
	if(flag){
		//添加关联关系
		addSatRelatedInfo(data);
		getRelatedByTypeId();
	}else{
		//移除关联关系
		gridManager.updateCell('relate_pk_id',"",gridManager.rows[data.__index]);
		removeSatRelatedInfo(data.pk_id);
	}
}
/**
 * 关联所有计算模块
 * @param rowData
 */
function addAllSatRelatedInfo(){
	var funcList = [];
	var gridData = gridManager.rows;
	for(var i = 0;i < gridData.length;i++){
		var rowData = gridData[i];
		if(rowData.relate_pk_id == "" ||rowData.relate_pk_id == null ){
			funcList.push({
				computeId:rowData.pk_id,
				overTime:rowData.over_time,
				computeCount:rowData.compute_count,
				isSaveResult:rowData.is_save_result,
				isMulticast:rowData.is_multicast
			});
		}else{
			continue;
		}	
	}
	$("#typeId").val(type_id);
	$("#funcListStr").val(JSON2.stringify(funcList));
	$("#hideForm").ajaxSubmit({
		url:basePath+'rest/ComputeType/relateAllFunc',
		success:function(data){
			var rsData = eval('('+data+')');
			if(rsData.success == "true"){
				getRelatedByTypeId();
				Alert.tip("操作成功！");
			}
		}
	});
}
/**
 * 移除已经关联的所有计算模块
 * @param rowData
 */
function removeAllSatRelatedInfo(){
	
	$.ajax({
		url:basePath+'rest/ComputeType/remoceRelateAllFunc',
		data:{
			typeId:type_id
		},
		async:false,
		success:function(rs){
			var rsData = eval('('+rs+')');
			if(rsData.success == "true"){
				Alert.tip("操作成功！");
			}
		}
	});
}

//添加一条到关联关系表
function addSatRelatedInfo(rowData){
	$.ajax({
		url:basePath+'rest/ComputeType/addRelation',
		data:{
			typeId:type_id,
			computeId:rowData.pk_id,
			overTime:rowData.over_time,
			computeCount:rowData.compute_count,
			isSaveResult:rowData.is_save_result,
			isMulticast:rowData.is_multicast
		},
		async:false,
		success:function(rs){
			var rsData = eval('('+rs+')');
			if(rsData.success == "true"){
				Alert.tip("操作成功！");
			}
		}
	});
}

function removeSatRelatedInfo(computeId){
	$.ajax({
		url:basePath+'rest/ComputeType/deleteRelation',
		data:{
			typeId:type_id,
			computeId:computeId
		},
		async:false,
		success:function(rs){
			
			var rsData = eval('('+rs+')');
			if(rsData.success == "true"){
				Alert.tip("操作成功！");
			}
		}
	});
}

//工具条按钮事件
function itemclick(item){
	 if(item.id){
		 switch (item.id){
		 
		 case "addComputeType":
	         	if(currentTreeNode == null){
	        		Alert.tip("请选择树节点！");
					return; 
	        	}
				setCurrWindows();
				xdlg = parent.$.ligerDialog.open({ 
		          	 width: 400,
					 height:240, 
	                 title:"新增计算类型",
					 url: basePath+"computeRun/operatetype_add.jsp?ownerId="+currentTreeNode.id+"&nodeType="+currentTreeNode.node_type
					 					+"&satId="+currentTreeNode.sat_id,
					 buttons :[
					            { text: '保存',type:'save', width: 80 ,onclick:function(item, dialog){
					             	dialog.frame.submitForms();
					             }},
					            { text: '关闭', type:'close', width: 80 , onclick:function(item, dialog){
					            	dialog.close();
					            }}
							 ]
	            });
	            return;
		 case "updateComputeType":
			 if(currentTreeNode == null){
				 Alert.tip("请选择树节点！");
				 return; 
			 }
			 setCurrWindows();
			 xdlg = parent.$.ligerDialog.open({ 
				 width: 400,
				 height:240, 
				 title:"修改计算类型",
				 url: basePath+"computeRun/operatetype_update.jsp?typeId="+currentTreeNode.id,
				 buttons :[
				           { text: '保存',  type:'save' ,width: 80 , onclick:function(item, dialog){
				        	   dialog.frame.submitForms();
				           }},
				           { text: '关闭', type:'close', width: 80 ,  onclick:function(item, dialog){
				        	   dialog.close();
				           }}
				           ]
			 });
			 return;
         case "deleteComputeType":
        	 if(currentTreeNode == null){
				 Alert.tip("请选择树节点！");
				 return; 
			 }
 				$.ligerDialog.confirm('确定要删除选中的记录吗？',function (yes){
	 			    if(yes){
	 			    	$.ajax({
	 						url:basePath+'rest/ComputeType/delete',
	 						data:{computeTypeId:currentTreeNode.id},
	 						async:false,
	 						success:function(rsData) {
	 							var result = eval("("+rsData+")");
	 							if (result.success == "true") {
	 								Alert.tip("删除成功!");
	 	 							tree.reload();
	 							} else {
	 								Alert.tip("删除失败!");
	 							}
	 						}
	 					});
	 			    }
 				});
 				return ;
		 }
		 
	 }
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}


//关闭对话框
function f_closeDlg(){
  xdlg.close();
}

//表格单元格内容修改
function f_onAfterEdit(row){
	$.ajax({
		url:basePath+'rest/ComputeType/updateRelation',
		data:{
			relateId:row.record.relate_pk_id,
			overTime:row.record.over_time,
			computeCount:row.record.compute_count,
			isMulticast:row.record.is_multicast
		},
		async:false,
		success:function(rs){
			var rsData = eval('('+rs+')');
			if(rsData.success == "true"){
				Alert.tip("操作成功！");
			}
		}
	});
}