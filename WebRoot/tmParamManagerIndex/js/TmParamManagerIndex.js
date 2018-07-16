var formData = null;
var tree = null;
var sat_id = "";
var node = null;
var bar = null;
var currentTreeNode;//当前点击右键的节点
$(function (){
	initToolBarController();
	initForm2();
	initBtn();	
	initGridData();
	initTreeController();
	$("#layout1").ligerLayout({ leftWidth: 190,allowLeftCollapse:false,heightDiff:0,space:2, onHeightChanged: f_heightChanged });
	var height = $(".l-layout-center").height();
	$("#scollDivTree")[0].style.height = height - 23 + "px";
	
	 menu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
         [
         { text: '新增分系统', click: itemclick, id: 'sub_add' }/*,
         { text: '管理注入文件', click: itemclick, id: 'filemanager' },
         { text: '指令序列管理', click: itemclick, id: 'orderRule' }*/
         ]
         });
	
	 menu1 = $.ligerMenu({ top: 100, left: 100, width: 120, items:
         [
		 { text: '新增单机', click: itemclick, id: 'alone_add' },
		 { text: '参数配置', click: itemclick, id: 'sub_grant_param' },
         { text: '修改分系统', click: itemclick,id:'sub_modify' },
         { line: true },
         { text: '删除分系统', click: itemclick,id:'sub_delete' }
         ]
         });
	 menu2 = $.ligerMenu({ top: 100, left: 100, width: 120, items:
         [
         { text: '修改单机', click: itemclick,id:'alone_modify' },
         { text: '参数配置', click: itemclick, id: 'alone_grant_param' },
         { line: true },
         { text: '删除单机', click: itemclick,id:'alone_delete' }
         ]
         });

});

function f_heightChanged(option){
	var height = $("#scollDivTree")[0].style.height;
	$("#scollDivTree")[0].style.height = parseFloat(height) + option.diff + "px";
}

function isEmpty(param){
	if(param==null||$.trim(param)==""){
		return true;
	}else{
		return false;
	}
	
}

function initTreeController(){
	var owner_id = -1;
	$("#tree1").remove();
	$("#scollDivTree").append('<div id="tree1"></div>');
	$("#scollDivTree").append('<div class="l-span-line"></div>');
	tree = $("#tree1").ligerTree({
		checkbox: false,
//		isExpand: true,
		//onselect:onselect,
		onClick :onselect,
		onExpand:onexpand,
		topIcon:'sat',
		parentIcon: null,
        childIcon: null,
        textFieldName:'name',
		idFieldName : 'sys_resource_id',
		parentIDFieldName : 'owner_id',
		height: "100%",
		nodeWidth:"100%",
		url:basePath+'rest/satinfoLimit/findgrantusergroupequipmenttree?sys_resource_id=-1',
		isLeaf : function(data){
			return data.type == 6;
		},
		delay: function(e)
		{
		     var data = e.data;
		     return { url: basePath+'rest/satinfoLimit/findgrantusergroupequipmenttree?sys_resource_id=' + data.sys_resource_id };
		 }

	});
	     $("#tree1").ligerTree({ onContextmenu: function (node, e)
	    	 {
	    	 currentTreeNode = node.data;
//	    	 var selNode = tree.getSelected();
//	    	 tree.cancelSelect(selNode.data); 
	    	 tree.selectNode(currentTreeNode); 
	    	 if(node.data.type==5){
	    		 menu.hide();
	    		 menu2.hide();
		    	 menu1.show({ top: e.pageY, left: e.pageX });
	    	 }else if(node.data.type==6){
	    		 menu.hide();
	    		 menu1.hide();
	    		 menu2.show({ top: e.pageY, left: e.pageX });
	    	 }else if(node.data.type==0){
	    		 menu1.hide();
	    		 menu2.hide();
	    		 menu.show({ top: e.pageY, left: e.pageX });
	    	 }
	    	 return false;
	    	 }
	     });
}

function onexpand(data,target){
	$.ajax({
		url:basePath+'rest/satinfoLimit/findgrantusergroupequipmenttree?sys_resource_id=' + data.sys_resource_id,
		success:function(data){
			var nodes=eval('('+data+')');
			tree_manager.clear();
			tree_manager.setData(nodes);
		}
	});
}

function onselect(){
	node = tree.getSelected();
	var gridManager = $("#maingrid").ligerGetGridManager();
	gridManager.setParm("tm_param_id","");
	gridManager.setParm("sat_id","");
	if(node!=null){
		var ids = node.data.sys_resource_id;
			if(ids!="-1"){
				loadData();
			}
	}else{
		gridManager.setParm("sat_id","");
	}
	gridManager.set({newPage:1});
	gridManager.loadData(false);
}

function initToolBarController(){
	$("#toptoolbar").ligerToolBar({
		items:[
	        { text: '新增', id:'addTm', click: itemclick,
	        	icon:'add'
//	        	img: basePath+'lib/ligerUI/skins/icons/add.gif'
	        	},
	        { text: '修改', id:'modify', click: itemclick,
		        	icon:'update'
//	        		img: basePath+'lib/ligerUI/skins/icons/modify.gif'
	        		},
			{ text: '删除', id:'delete', click: itemclick,
			        	icon:'delete'
//	        			img: basePath+'lib/ligerUI/skins/icons/delete.gif' 
	        			},
	        { text: '导入', id:'import', click: itemclick,
	        				icon:'in'
//	        				img: basePath+'lib/ligerUI/skins/icons/back.gif'
	        				}
		]});
}



/**
 * 按钮初始化
 */
function initBtn(){
	$("#searchbtn").ligerButton({type:"one", click: function ()
    {
		loadData();
    }  
	});
  	//重置搜索条件
	$("#resetbtn").ligerButton({type:"two", click: function (){
        clearForm();
    }
	}); 
}


function initForm2(){
	
    formData = $("#form2").ligerForm({
    		labelAlign:'center',
        	inputWidth: 100, 
            labelWidth: 60, 
            space: 10,
//            validate:true,
            fields: [
				{display: '参数名称', id: 'tm_param_name',name:'tm_param_name',type:"text",newline: false,width:130},
				{display: '参数编号', id: 'tm_param_code',name:'tm_param_code',type:"text",newline: false,width:130},
				{display: '参数类型', id: 'tm_param_type',name:'tm_param_type',type:"select",
					data:[
					      		{id:'0',text:'浮点型'},
					            {id:'2',text:'整型'},
					            {id:'3',text:'字符串'}
					            ]
					,newline: false,width:130}
//				{display: '创建时间', id: 'create_time',name:'create_time',type:"date",newline: false}
//				{display: '创建时间', id: 'create_time_start',name:'create_time_start',type:"date",newline: false},
//				{display: '至', id: 'create_time_end',name:'create_time_end',type:"date",newline: false,labelWidth: 30,}
			]
    });
//	$("#form2").attr("style","margin-left:10px;float:left;");
}

function initGridData(){
	gridManager=$("#maingrid").ligerGrid({
    columns: [ 
               	{display: '航天器id', id: 'tm_param_id',name:'tm_param_id',type:"text",newline: false,hide:true,width:2},
               	{display: '参数序号', name: 'tm_param_num',align: 'center',width:200	},
              	{display: '参数名称', name: 'tm_param_name',align: 'center',width:400,
               		render:function(item){
               			if(item.tm_param_name != null){
               				return '<div title="'+item.tm_param_name+'">'+item.tm_param_name+'</div>';
               			}
               		}
               	},
    			{display: '参数编号', name: 'tm_param_code',align: 'center',width:255},
//    			{display: '所属卫星', name: 'owner_name',align: 'center',width:255},
    			{display: "参数类型", name: "tm_param_type",align: 'center',width:235,
        			render: function (item){
						var val = item.tm_param_type;
						if(val=="0"){
							return "浮点型";
						}else if(val=="2"){
							return "整型";
						}else if(val=="3"){
							return "字符串";
						}
					}
    			}
    ],
    top :200,
    dataAction:'server',
    width: '100%', 
    height: '100%', 
    pageSize: 20,
    rownumbers:true,
    checkbox : true,
    rowHeight:27,
    frozen: false,  
    //应用灰色表头
    cssClass: 'l-grid-gray', 
    url:basePath+'rest/tmparams/findgrantusergrouptmparambyid',
    onSelectRow:function(){
    	window.parent.selectParams = gridManager.selected;
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
		 
		 case "sub_grant_param":
	         	if(node==null){
	        		$.ligerDialog.warn("请选择节点！");
					return; 
	        	}
				var	url = basePath+"tmParamManagerIndex/subSystemInfo/SubTmParams.jsp?cur_id="+node.data.sys_resource_id+"&id="+node.data.owner_id;
				setCurrWindows();
				xdlg= parent.$.ligerDialog.open( 
	            { 
			          	 width: 1110,
						 height:600, 
	                title:"参数配置",
					url: url,
					buttons :[
		            { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
		            	gridManager.loadData();
		            	dialog.close();
		            }}
				 ]
	            });
	            xdlg.show();
//	           $("#tree1").ligerTree.loadData(); 
	            return;
	            
		 case "alone_grant_param":
	         	if(node==null){
	        		$.ligerDialog.warn("请选择节点！");
					return; 
	        	}
	         	var parentNodes = $(".l-selected").parent().parent().parent().parent().parent();
	 		    var satId = tree._getDataNodeByTreeDataIndex(tree.data,$(parentNodes[0]).attr("treedataindex")).sys_resource_id;
	         	var	url = basePath+"tmParamManagerIndex/subSystemInfo/SubTmParams.jsp?cur_id="+node.data.sys_resource_id+"&id="+satId;
				setCurrWindows();
				xdlg= parent.$.ligerDialog.open( 
	            { 
			          	 width: 1110,
						 height:600, 
	                title:"参数配置",
					url: url,
					buttons :[
		            { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
		            	gridManager.loadData();
		            	dialog.close();
		            }}
				 ]
	            });
	            xdlg.show(); 
	            return;
	            
		 case "sub_add":
         	if(node==null){
        		$.ligerDialog.warn("请选择节点！");
				return; 
        	}
			var	url = basePath+"tmParamManagerIndex/subSystemInfo/SubSystemInfoAdd.jsp?sat_id="+node.data.sys_resource_id;
			setCurrWindows();
			xdlg= parent.$.ligerDialog.open( 
            { 
		          	 width: 400,
					 height:180, 
                title:"新增分系统信息",
				url: url,
				buttons :[
	            { text: '保存', type:'save',width: 80 ,onclick:function(item, dialog){
	             	dialog.frame.submitForm();
	             }},
	            { text: '关闭', type:'close',width: 80, onclick:function(item, dialog){
	            	dialog.close();
	            }}
			 ]
            });
            xdlg.show(); 
//            tree.reload();
            return;
		 case "filemanager":
			 if(node==null){
	        		$.ligerDialog.warn("请选择节点！");
					return; 
        	 }
			 parent.$.ligerDialog.open({ 
		          	width: 550,
				    height:420, 
	                title:"注入文件管理",
					url: basePath+"admin/infileManager/infile_list.jsp?satid="+node.data.sys_resource_id+"&satcode="+node.data.code,
					buttons :[
		            { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
		            	dialog.close();
		            }}
				 ]
             });
			 break;
		 case "orderRule":
			 if(node==null){
				 $.ligerDialog.warn("请选择节点！");
				 return; 
			 }
			 parent.$.ligerDialog.open({ 
				 width: 550,
				 height:420, 
				 title:"指令序列管理",
				 url: basePath+"admin/seqFileManager/seq_info.jsp?satid="+node.data.sys_resource_id,
				 buttons :[
				           { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
				        	   dialog.close();
				           }}
				           ]
			 });
			 break;
		 case "alone_add":
	         	if(node==null){
	        		$.ligerDialog.warn("请选择节点！");
					return; 
	        	}
				var	url = basePath+"tmParamManagerIndex/standAloneInfo/StandAloneInfoAdd.jsp?sub_system_id="+node.data.sys_resource_id;
				setCurrWindows();
				ckxq= parent.$.ligerDialog.open( 
	            { 
			          	 width: 400,
						 height:180, 
	                title:"新增单机信息",
					url: url,
					buttons :[
		            { text: '保存', type:'save', width: 80 ,onclick:function(item, dialog){
		             	dialog.frame.submitForm();
//		             	tree.reload();
		             }},
		            { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
		            	dialog.close();
		            }}
				 ]
	            });
				ckxq.show();
//				tree.reload();
				return;
	            
         case "alone_modify":
        	 debugger;
			var	url = basePath+"tmParamManagerIndex/standAloneInfo/StandAloneInfoUpdate.jsp?stand_alone_id="+currentTreeNode.sys_resource_id
			+'&sub_system_id='+currentTreeNode.owner_id;
			setCurrWindows();
			xdlg= parent.$.ligerDialog.open( 
	            { 
		          	width: 400,
				    height:180, 
	                title:'[修改]单机信息',
					url: url,
					buttons :[
		            { text: '保存', type:'save', width: 80 ,onclick:function(item, dialog){
		             	dialog.frame.submitForm();
//		             	tree.reload();
		             }},
		            { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
		            	dialog.close();
		            }}
				 ]
	            });
	            xdlg.show();
				return;
				
				
         case "sub_modify":
 			var	url = basePath+"tmParamManagerIndex/subSystemInfo/SubSystemInfoUpdate.jsp?satId="+currentTreeNode.owner_id
 					+"&sub_system_id="+currentTreeNode.sys_resource_id;
 			setCurrWindows();
 			xdlg= parent.$.ligerDialog.open( 
 	            { 
 		          	width: 400,
 				    height:180, 
 	                title:'[修改]分系统信息',
 					url: url,
 					buttons :[
 		            { text: '保存', type:'save', width: 80 ,onclick:function(item, dialog){
 		             	dialog.frame.submitForm();
 		             }},
 		            { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
 		            	dialog.close();
 		            }}
 				 ]
 	            });
 	            xdlg.show();
 				return;
         	
			 
         case "modify":
				if (!gridManager.getSelectedRow()) { 
					$.ligerDialog.warn("请至少选择一条数据！");
					return; 
				}
				if (gridManager.selected.length > 1) { 
					$.ligerDialog.warn("只能选择一条数据！");
					return; 
				}
					
				var lmId= gridManager.selected[0].tm_param_id ;
				setCurrWindows();
 			xdlg= parent.$.ligerDialog.open( 
	            { 
		          	width: 400,
				    height:248, 
	                title:'[修改]遥测参数信息',
					url: basePath+"tmParamManagerIndex/TmParamManagerUpdate.jsp?tm_param_id="+lmId,
					buttons :[
		            { text: '保存', type:'save', width: 80 ,onclick:function(item, dialog){
		             	dialog.frame.submitForm();
		             }},
		            { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
		            	dialog.close();
		            }}
				 ]
	            });
	            xdlg.show();
				return;
         case "addTm":
        	 if(node == null){
        		 Alert.tip("请选择卫星！");
        		 return;
        	 }
        	 var satId = node.data.sys_resource_id;
        	 setCurrWindows();
        	 xdlg= parent.$.ligerDialog.open( 
        			 { 
        				 width: 400,
        				 height:248, 
        				 title:'[新增]遥测参数信息',
        				 url: basePath+"tmParamManagerIndex/tm_add.jsp?satId="+satId,
        				 buttons :[
        				           { text: '保存', type:'save', width: 80 ,onclick:function(item, dialog){
        				        	   dialog.frame.submitForm();
        				           }},
        				           { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
        				        	   dialog.close();
        				           }}
        				           ]
        			 });
        	 return;
         case "import":
        	 if(node == null){
        		 Alert.tip("请选择卫星！");
        		 return;
        	 }
        	 var satId = node.data.sys_resource_id;
        	 setCurrWindows();
        	 xdlg = parent.$.ligerDialog.open({
 				width : 400,
 				height : 200,
 				title : '[导入]遥测参数信息',
 				url : basePath + "tmParamManagerIndex/upload_excel_file_tm.jsp",
 				buttons : [ {
 					text : '导入',
 					type:'save',
 					width : 80,
 					onclick : function(item, dialog) {
 						var dataObj = dialog.frame.getData();
 						if(!dataObj){
 							return;
 						}
 						dialog.close();
 						 var dialogTip = $.ligerDialog.open({ 
 							 width: 300,
 							 height:100,  
 							 title:"提示",
 							 content:"<p>正在导入,请稍等。。。</p>"
 						});
 						setTimeout(function(){
 							$.ajax({
 								url:basePath+"rest/tmparams/tmparaminput",
 								data:{
 									filePath:dataObj.filePath,
 									sat_id:satId
 								},
 								async:false,
 								success:function(data){
 									Alert.tip(data);
 									dialogTip.close();
 									loadData();
 								}
 							});	
 						}, 2000)
 					 }
 				} ]
 			});
  			return ;
         case "sub_delete":
          	var pkId = "";
          	pkId = pkId + node.data.sys_resource_id;
 			  	var url = basePath+'rest/subinfo/subsysteminfodeletebyid';
 				$.ligerDialog.confirm('确定要删除选中的记录吗？',function (yes){
	 			    if(yes){
	 			    	
	 			    	$.ajax({
	 						url:url,
	 						data:{sub_system_id:pkId},
	 						async:false,
	 						success:function(rsData) {
	 							var result = eval("("+rsData+")");
	 							if (result.success == 'true') {
	 								Alert.tip("删除分系统成功");
	 	 							tree.reload();
	 	 							loadData();
	 							} else {
	 								Alert.tip(result.message);
	 							}
	 						}
	 					});
	 			    }
 				});
 				return ;
 				
         case "alone_delete":
        	 var pkId = "";
        	 pkId = pkId + currentTreeNode.sys_resource_id;
		  	var url = basePath+'rest/aloneinfo/standaloneinfodeletebyid';
			$.ligerDialog.confirm('确定要删除选中的记录吗？',function (yes){
		    if(yes){
		    	$.ajax({
						url:url,
						data:{stand_alone_id:pkId},
						async:false,
						success:function(rsData) {
							var result = eval("("+rsData+")");
							if (result.success == 'true') {
								tree.reload();
								loadData();
								Alert.tip("删除单机成功");
							} else {
								Alert.tip("删除单机失败");
							}
						}
					});
		    }
			});
			return ;
         case "delete":
         	if (!gridManager.getSelectedRow()) { 
					$.ligerDialog.warn("请至少选择一条数据！");
					return; 
				}
         	var pkId = "";
				for (var i = 0; i < gridManager.selected.length; i++){
					if(i==(gridManager.selected.length-1)) {
						pkId = pkId + gridManager.selected[i].tm_param_id;				    			
					} else {
						pkId = pkId+ gridManager.selected[i].tm_param_id + ',';
					}
				}
			  	var url = basePath+'rest/tmparams/tmparamdelete';
				$.ligerDialog.confirm('确定要删除选中的记录吗？',function (yes){
			    if(yes){
			    	$.ajax({
						url:url,
						data:{ids:pkId},
						async:false,
						success:function(rsData) {
							var result = eval("("+rsData+")");
							if (result.success == true) {
								loadData();
								Alert.tip("删除参数成功");
							} else {
								Alert.tip("删除参数失败");
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
//关闭对话框刷新gridManager
function closeDlgAndReload(){
    f_closeDlg();
    ckxq.close();
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

function getData(){
	var id = getParam("sat_id");
	$.post(
			basePath+"rest/tmparams/findgrantusergrouptmparamquerypage",
			{
				sat_id:id
			},
			function(data)
			{
				var row = data;
				formData.setData(row);
			},"json");
}
//樹刷新
function tree_fresh(){
	tree.reload();
}


function loadData(){
	var form = $("#form2").ligerForm();
	var data = form.getData();
	gridManager.set('parms', {
			sat_id:node.data.sys_resource_id,
			tm_param_name : data.tm_param_name,
			tm_param_code : data.tm_param_code,
			tm_param_type : data.tm_param_type
	});
	gridManager.set({newPage:1});
	gridManager.loadData();
}

function grid_refresh(){
	var gridManager = $("#maingrid").ligerGetGridManager();
	gridManager.set('parms',{
		sat_id:node.data.sys_resource_id
	});
	gridManager.set({newPage:1});
	gridManager.loadData(false);
}