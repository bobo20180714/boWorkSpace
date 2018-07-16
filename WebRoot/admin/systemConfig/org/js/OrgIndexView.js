var tree = null;
var bar = null;
var node;
var currentTreeNode;//当前点击右键的节点
$(function (){
	initToolBarController();
	initForm2();
	initBtn();	
	initGridData();
	initTreeController();
	$("#layout1").ligerLayout({ leftWidth: 150,allowLeftCollapse:false,heightDiff:0,space:0,height:'100%'});
	var height = $(".l-layout-center").height();
	$("#scollDivTree")[0].style.height = height - 24 + "px";
	 menu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
         [
         { text: '添加下级机构', click: itemclick, id: 'sub_add' },
         ]
         });
	
	 menu1 = $.ligerMenu({ top: 100, left: 100, width: 130, items:
         [
         { text: '添加下级机构', click: itemclick, id: 'sub_org_add' },
		 { text: '机构节点信息编辑', click: itemclick, id: 'org_edit' },
       /*  { text: '机构角色授权', click: itemclick,id:'org_grant' },*/
		 { text: '机构数据授权', click: itemclick,id:'org_data_grant' },
         { text: '机构移除', click: itemclick,id:'org_delete' }
         ]
         });

});
function initTreeController(){
	var owner_id = -1;
//	$("#tree1").remove();
//	$("#scollDivTree").append('<div id="tree1"></div>');
	tree = $("#tree1").ligerTree({
		checkbox: false,
		isExpand: true,
		topIcon:'folder',
		onClick :onselect,
//		onExpand:onexpand,
		toolbar:bar,
        textFieldName:'org_name',
		idFieldName : 'id',
		parentIDFieldName :'org_parent_id',
		nodeWidth:250,
		url:basePath+'rest/orguser/findorgtree'
		
		/*isLeaf : function(data){
			return data.type == 6;
		},
		delay: function(e)
		{
		     var data = e.data;
		     return { url: basePath+'rest/orguser/findorgtree?parent_id=' + data.id };
		 }*/

	});
	     $("#tree1").ligerTree({ onContextmenu: function (node, e)
	    	 {
	    	 currentTreeNode = node.data;
	    	 if(node.org_id==-1){
		    	 menu.show({ top: e.pageY, left: e.pageX });
	    	 }else 
	    		 menu1.show({ top: e.pageY, left: e.pageX });
	    	 return false;
	    	 }
	     });
}

function isEmpty(param){
	if(param==null||$.trim(param)==""){
		return true;
	}else{
		return false;
	}
	
}

function onexpand(data,target){
	tree_manager = $("#tree1");
	$.ajax({
		url:basePath+'rest/orguser/findorgtree?parent_id=' + data.id,
		success:function(data){
			var nodes=eval('('+data+')');
//			tree_manager.clear();
//			tree_manager.setData(nodes);
		}
	});
}

function initToolBarController(){
	$("#toptoolbar").ligerToolBar(
			{ items: [
				{ text: '添加', id:'add', click: itemclick,
					icon:'add'
//					img: basePath+'lib/ligerUI/skins/icons/add.gif'
				},
				{ text: '修改', id:'modify', click: itemclick,
						icon:'update'
//						img: basePath+'lib/ligerUI/skins/icons/modify.gif'
						}
	    			,
				{ text: '删除', id:'delete', click: itemclick,
						icon:'delete'
//	    				img: basePath+'lib/ligerUI/skins/icons/delete.gif' 
	    				}
	    			,
	    		{ text: '密码重置', id:'reset_pas', click: itemclick,
	    				icon:'rpas'
//	    				img: basePath+'lib/ligerUI/skins/icons/back.gif' 
	    				}
	    			,
	    		{ text: '用户导入', id:'daoru', click: itemclick,
	    				icon:'in'
//	    				img: basePath+'lib/ligerUI/skins/icons/down.gif'
	    				}
	    			,
	    		/*{ text: '转换机构', id:'org_transform', click: itemclick,img: basePath+'lib/ligerUI/skins/icons/communication.gif' }
	    			,*/
	    		{ text: '用户角色授权', id:'role_grant', click: itemclick,
	    				icon:'accredit'
//	    				img: basePath+'lib/ligerUI/skins/icons/role.gif' 
	    				}
	    			,
    			{ text: '用户数据授权', id:'data_grant', click: itemclick,
	    				icon:'dataAccredit'
//	    				img: basePath+'lib/ligerUI/skins/icons/customers.gif'
	    				}
    			,
	    		{ text: '用户导出', id:'daochu', click: itemclick,
    				icon:'out'
//    				img: basePath+'lib/ligerUI/skins/icons/up.gif' 
    				}
	        ]
	    });
}					
/**
 * 按钮初始化
 */
function initBtn(){
	$("#searchbtn").ligerButton({type:'one', click: function ()
    {
		loadData();
    }  
	});
  	//重置搜索条件
	$("#resetbtn").ligerButton({ type:'two',click: function (){
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
            validate:true,
            fields: [
				{display: '登录名称', id: 'login_name',name:'login_name',type:"text",newline: false},
				{display: '用户名称', id: 'user_name',name:'user_name',type:"text",newline: false},
//				{display: '用户部门', id: 'bumen',name:'bumen',type:"text",newline: false},
				{display: '截止时间', id: 'end_time_start',name:'end_time_start',type:"date",newline: false},
				{display: '至', id: 'end_time_end',name:'end_time_end',type:"date",newline: false,labelWidth: 30,}
			]
    });
	}

function initGridData(){
	gridManager=$("#maingrid").ligerGrid({
    columns: [ 
               	{display: '用户id', id: 'user_id',name:'user_id',type:"text",newline: false,hide:true,width:1},
              	{display: '登录名称', name: 'login_name',align: 'center',width:140,render:function(item){
              		var data = item.login_name;
              		return '<div title="'+item.login_name+'">'+data+'</div>';	
              	}
               	},
    			{display: '用户名称', name: 'user_name',align: 'center',width:140,render:function(item){
              		var data = item.user_name;
              		return '<div title="'+item.user_name+'">'+data+'</div>';	
              	}
               	},
    			{display: '用户职位', name: 'zw',align: 'center',width:140},
    			{display: "联系电话", name: "telephone",align: 'center',width:140},
//    			{ display: "用户单位", name: "danwei",align: 'center',width:140},
//    			{ display: "用户部门", name: "bumen",align: 'center',width:140},
    			{ display: "截止日期", name: "end_time",align: 'center',width:140},
    			{ display: "所属机构", name: "org_name",align: 'center',width:220}
    ],
    width: '100%', 
    height: '100%', 
    pageSize: 20,
    rownumbers:true,
    checkbox : true,
    rowHeight:27,
    //应用灰色表头
    cssClass: 'l-grid-gray', 
    url:basePath+'rest/orguser/finduserquerypage',
    frozen:false
});
}

var xdlg = null;
//工具条按钮事件
function itemclick(item){
	 if(item.id){
		 switch (item.id){
		 
		 //添加下级机构
		 case "sub_org_add":
			 node = tree.getSelected();
	         	if(node==null){
	        		parent.Alert.tip("请选择机构！");
					return; 
	        	}
				var	url = basePath+"admin/systemConfig/org/OrgAdd.jsp?id="+node.data.id;
				setCurrWindows();
				xdlg= parent.$.ligerDialog.open( 
	            { 
			          	 width: 400,
						 height:200, 
	                title:"新增组织机构节点信息",
					url: url,
					buttons :[
		            { text: '保存', type:'save',width: 80 ,onclick:function(item, dialog){
		             	dialog.frame.submitForm();
		             }},
		            { text: '关闭', type:'close',width: 80 , onclick:function(item, dialog){
		            	dialog.close();
		            }}
				 ]
	            });
	            xdlg.show();
	            gridManager.loadData();
	            return;
   		 //机构节点信息编辑
		 case "org_edit":
			 node = tree.getSelected();
	         	if(node==null){
	         		parent.Alert.tip("请选择机构！");
					return; 
	        	}
				var	url = basePath+"admin/systemConfig/org/OrgUpdate.jsp?id="+node.data.id;
				setCurrWindows();
				xdlg= parent.$.ligerDialog.open( 
	            { 
			          	 width: 400,
						 height:200, 
	                title:"[修改]机构节点信息",
					url: url,
					buttons :[
		            { text: '保存', type:'save',width: 80 ,onclick:function(item, dialog){
		             	dialog.frame.submitForm();
		             }},
		            { text: '关闭', type:'close',width: 80 , onclick:function(item, dialog){
		            	dialog.close();
		            }}
				 ]
	            });
	            xdlg.show();
	            gridManager.loadData(); 
	            return;
 		//机构角色授权		
		 case "org_grant":
			 node = tree.getSelected();
	         	if(node==null){
	         		parent.Alert.tip("请选择机构！");
					return; 
	        	}
				var	url = basePath+"admin/systemConfig/org/OrgAuthorize.jsp?id="+node.data.id;
				setCurrWindows();
				xdlg= parent.$.ligerDialog.open( 
	            { 
			          	 width: 720,
						 height:550, 
	                title:"机构角色授权信息",
					url: url,
					buttons :[
		            { text: '关闭', type:'close',width: 80 , onclick:function(item, dialog){
		            	dialog.close();
		            }}
				 ]
	            });
	            xdlg.show();
	            gridManager.loadData(); 
	            return;
		 //添加用户
		 case "add":
			 node = tree.getSelected();
	         	if(node==null){
	         		parent.Alert.tip("请选择机构！");
					return; 
	        	}
				var	url = basePath+"admin/systemConfig/org/UserAdd.jsp?id="+node.data.id;
				setCurrWindows();
				xdlg= parent.$.ligerDialog.open( 
	            { 
			          	 width: 400,
						 height:260, 
	                title:"[新增]机构用户信息",
					url: url,
					buttons :[
		            { text: '保存', type:'save',width: 80 ,onclick:function(item, dialog){
		             	dialog.frame.submitForm();
		             }},
		            { text: '关闭', type:'close',width: 80 ,onclick:function(item, dialog){
		            	dialog.close();
		            }}
				 ]
	            });
	            xdlg.show();
	            return;
   		 //用户信息修改
		 case "modify":
			 	var record = gridManager.getSelectedRows();
			 	if(record.length==0){
			 		parent.Alert.tip("请至少选择一条数据！");
					return; 
			 	}else if(record.length>1){
			 		parent.Alert.tip("只能选择一条数据修改！");
					return;	
			 	}
				var	url = basePath+"admin/systemConfig/org/UserUpdate.jsp?id="+record[0].user_id+"&login_name="+record[0].login_name;
				setCurrWindows();
				xdlg= parent.$.ligerDialog.open( 
	            { 
			          	 width: 400,
						 height:260, 
	                title:"[修改]机构用户信息",
					url: url,
					buttons :[
		            { text: '保存', type:'save',width: 80 ,onclick:function(item, dialog){
		             	dialog.frame.submitForm();
		             }},
		            { text: '关闭', type:'close',width: 80 ,onclick:function(item, dialog){
		            	dialog.close();
		            }}
				 ]
	            });
	            xdlg.show();
	            return;
	      //密码重置      
         case "reset_pas":
 			if(gridManager.getSelectedRows().length != 1){
 				parent.Alert.tip("请选择一条数据！");
 					return;
 			}
          	var pkId = "";
				pkId = gridManager.selected[0].user_id;
 			  	var url = basePath+'rest/orguser/resetpassword';
 				$.ligerDialog.confirm('是否重置为初始密码？',function (yes){
 			    if(yes){
 					var retObj = $.post(url,{user_id:pkId},function(dataObj){
 						if (dataObj.success == "true"){
 							Alert.tip(dataObj.message);
 							gridManager.loadData();
 		             	} 
 					},"json");
 			    }
 				});
 				return ;
		//用户导入
         case "daoru":
 				 node = tree.getSelected();
 	         	if(node==null){
 	         		parent.Alert.tip("请选择组织机构节点！");
 					return; 
 	        	}
 				var	url = basePath+"admin/systemConfig/org/importFile.jsp?org_id="+node.data.id;
 				setCurrWindows();
 				xdlg= parent.$.ligerDialog.open( 
 	            { 
 			          	 width: 400,
 						 height:200, 
 	                title:"文件导入信息",
 					url: url,
 					buttons :[
					{ text: '导入', type:'save',width: 80 , onclick:function(item, dialog){
					 	dialog.frame.importFile();
					 }},
 		            { text: '关闭', type:'close',width: 80 ,onclick:function(item, dialog){
 		            	dialog.close();
 		            }}
 				 ]
 	            });
 	            xdlg.show();
 	            gridManager.loadData(); 
 	            return;
		//用户导出
         case "daochu":
			 node = tree.getSelected();
 	         	if(node==null){
 	         		parent.Alert.tip("请选择组织机构节点！");
 					return; 
 	        	}
        	 var records = gridManager.data;
        	 var data = $("#form2").ligerForm().getData();
        	 if (records.Total==0) { 
        		 parent.Alert.tip("当前列表无信息！");
        		 return; 
        	 }
        	 var userInfoView = {};
        	 userInfoView.org_id = node.data.id;
        	 userInfoView.login_name = data.login_name;
        	 userInfoView.user_name = data.user_name;
        	 userInfoView.bumen= data.bumen;
        	 userInfoView.create_time_start = data.create_time_start==null?"":data.create_time_start;
        	 userInfoView.create_time_end ==null?"":data.create_time_end;
 				var	url = basePath+"rest/orguser/userExport";
					$.ajax({
						url:url,
						data:userInfoView,
						async:false,
						success:function(data){
							var rsObj = eval('('+data+')');
							window.location.href = basePath+"rest/download/fileDownload?fileUrl="+rsObj.fileUrl;
						}
					});
 	            return;
 				
   		 //转换机构
		 case "org_transform":
			 node = tree.getSelected();
	         	if(node==null){
	         		parent.Alert.tip("请选择机构！");
					return; 
	        	}
			 	var record = gridManager.getSelectedRows();
			 	if(record.length==0){
	        		parent.Alert.tip("请至少选择一条数据！");
					return; 
			 	}
	           	var pkId = "";
  				for (var i = 0; i < record.length; i++){
  					if(i==(record.length-1)) {
  						pkId = pkId + record[i].user_id;				    			
  					} else {
  						pkId = pkId+ record[i].user_id + ',';
  					}
  				}
				var	url = basePath+"admin/systemConfig/org/OrgChose.jsp?ids="+pkId+"&org_id="+node.data.id;
				setCurrWindows();
				xdlg= parent.$.ligerDialog.open( 
	            { 
			          	 width: 360,
						 height:240, 
	                title:"[修改]用户机构信息",
					url: url,
					buttons :[
		            { text: '保存', type:'save',width: 80 ,onclick:function(item, dialog){
		             	dialog.frame.submitForm();
		             }},
		            { text: '关闭', type:'close',width: 80 , onclick:function(item, dialog){
		            	dialog.close();
		            }}
				 ]
	            });
	            xdlg.show();
	            return;
	        
        //机构移除
		 case "org_delete":
			 node = tree.getSelected();
	         	if(node==null){
	        		parent.Alert.tip("请选择机构！");
					return; 
	        	}else if(node.data.org_parent_id==-1){
  					parent.Alert.tip("不能删除根节点！");
  					return;	
	        	}
  			  	var url = basePath+'rest/orguser/orgdelete';
  				$.ligerDialog.confirm('确定要删除选中的记录吗？',function (yes){
  			    if(yes){
  					var retObj = $.post(url,{org_id:node.data.id},function(dataObj){
  						if (dataObj.success == "true"){
  							Alert.tip(dataObj.message);
  							tree.reload();
  							loadData();
  		             	}else Alert.tip(dataObj.message); 
  					},"json");
  			    }
  				});
  				return ;
 		//机构用户删除		
         case "delete":
        	 var records = gridManager.getSelectedRows();
           	if (records.length < 1) { 
  					parent.Alert.tip("请至少选择一条数据！");
  					return; 
  				}
           	var pkId = "";
  				for (var i = 0; i < records.length; i++){
  					if(i==(records.length-1)) {
  						pkId = pkId + records[i].user_id;				    			
  					} else {
  						pkId = pkId+ records[i].user_id +',';
  					}
  				}
  			  	var url = basePath+'rest/orguser/userinfodelete';
  				$.ligerDialog.confirm('确定要删除选中的记录吗？',function (yes){
  			    if(yes){
  					var retObj = $.post(url,{ids:pkId},function(dataObj){
  						if (dataObj.success == "true"){
  							Alert.tip(dataObj.message);
  							gridManager.set({newPage:1});
  							gridManager.loadData();
  		             	} else Alert.tip(dataObj.message);
  					},"json");
  			    }
  				});
  				return ;
 		//用户角色授权		
		 case "role_grant":
			 	var record = gridManager.getSelectedRows();
			 	if(record.length != 1){
	        		parent.Alert.tip("请选择一条数据授权！");
					return; 
			 	}
				var	url = basePath+"admin/systemConfig/org/UserAuthorize.jsp?id="+record[0].user_id;
				setCurrWindows();
				xdlg= parent.$.ligerDialog.open( 
	            { 
			          	 width: 740,
						 height:550, 
	                title:"用户角色授权信息",
					url: url,
					buttons :[
		            { text: '关闭', type:'close',width: 80 , onclick:function(item, dialog){
		            	dialog.close();
		            }}
				 ]
	            });
	            xdlg.show();
	            return;
	            //用户数据授权		
		 case "data_grant":
			 var record = gridManager.getSelectedRows();
			 if(record.length != 1){
				 parent.Alert.tip("请选择一条数据授权！");
				 return; 
			 }
			 if(record[0].login_name == managerAccount){
				 parent.Alert.tip("超级管理员不用进行数据授权！");
				 return; 
			 }
			 var url = basePath+"admin/systemConfig/dataGrant/multipleManageSet.jsp?ug_id="+record[0].user_id;
			 $.ligerDialog.open({ 
				 width : 635,
				 height : 450,
				 title:"用户数据授权",
				 url: url,
				 buttons :[{ text: '关闭', type:'close',width: 80 , onclick:function(item, dialog){
				        	   dialog.close();
				           }}]
			  });
			 return;
			 //机构数据授权		
		 case "org_data_grant":
			 node = tree.getSelected();
         	 if(node == null){
        		parent.Alert.tip("请选择机构！");
				return; 
        	 }
			 var url = basePath+"admin/systemConfig/dataGrant/multipleManageSet.jsp?ug_id="+node.data.id;
			 $.ligerDialog.open({ 
				 width : 635,
				 height : 450,
				 title:"机构数据授权",
				 url: url,
				 buttons :[{ text: '关闭', type:'close',width: 80 , onclick:function(item, dialog){
					 dialog.close();
				 }}]
			 });
			 return;
		 
		 }
	 }
}

function onselect(){
	node = tree.getSelected();
	var gridManager = $("#maingrid").ligerGetGridManager();
	gridManager.setParm("org_id","");
	gridManager.setParm("user_id","");
	if(node!=null){
		var ids = node.data.id;
			if(ids!="-1"){
				gridManager.setParm("id",ids);
			}
	}else{
		gridManager.setParm("id","");
	}
	gridManager.set({newPage:1});
	gridManager.loadData(true);
}

function loadData(){
	var gridManager = $("#maingrid").ligerGetGridManager();
	var form = $("#form2").ligerForm();
	var data = form.getData();
	gridManager.setParm("login_name",data.login_name);
	gridManager.setParm("user_name",data.user_name);
	gridManager.setParm("bumen",data.bumen);
	gridManager.setParm("end_time_start",data.end_time_start);
	gridManager.setParm("end_time_end",data.end_time_end);
	$("#maingrid").ligerGrid({
		url:basePath+'rest/orguser/finduserquerypage'
	});
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
	   /* var treeDataTemp = tree.getSelected();
	    tree.cancelSelect(treeDataTemp.data);*/
	}
//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}
//关闭对话框刷新gridManager
function closeDlgAndReload(){
    f_closeDlg();
    gridManager.loadData(); 

}

//关闭对话框
function f_closeDlg(){
    xdlg.close();
}

function f_reload(){
	gridManager.loadData();
}

function closeDlg(){
	xdlg.close();
}

function tree_fresh(){
	tree.reload();
}