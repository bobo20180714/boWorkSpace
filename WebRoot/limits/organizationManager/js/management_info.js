var projectManager = null;
var tree_manager = null;
var windows = {};  //窗口

$(function () {
	$("#layout1").ligerLayout({ leftWidth: 190,allowLeftCollapse:false,heightDiff:0,space:0, onHeightChanged: f_heightChanged });
	var height = $(".l-layout-center").height();
	$("#scollDivTree")[0].style.height = height-24;
	
	
	getTreeData();
	
	var baseImgPath=basePath+'lib/ligerUI/skins/icons/';
	$("#toptoolbar").ligerToolBar({
		items : [
		         {text : '选择员工',id : 'select',click : itemclick,img : baseImgPath+'add.gif'},
		         {text : '移除员工',id : 'remove',click : itemclick,img : baseImgPath+'delete.gif'}
		         ]
	});
	
	//参数列表
	projectManager = $("#maingrid").ligerGrid({
		 	   columns: [ 
				{display: 'pk_id',name: 'pk_id',hide:true},
				{ display: '员工工号', name: 'staff_code',type: 'text',width:150,
					render: function(e){
		            	if(e.staff_code !=undefined){
							var data="";
							data = e.staff_code;
							data = data.substr(0,11);
		            		var h = "";
		                    h += "<a style ='cursor: none;text-decoration: none;color:black' title = "+e.staff_code+" href=\"#\">"+data+"</a>";
		             	 	return h;
		            	}
		            }},
				{ display: '员工姓名', name: 'staff_name',type: 'text',width:150,
					render: function(e){
	                	if(e.staff_name !=undefined){
	    					var data="";
	    					data = e.staff_name;
	    					data = data.substr(0,11);
	                		var h = "";
	                        h += "<a style ='cursor: none;text-decoration: none;color:black' title = "+e.staff_name+" href=\"#\">"+data+"</a>";
	                 	 	return h;
	                	}
	                }},
				{ display: '员工性别', name: 'sex_name',type: 'text',width:100
				},
				{ display: '入职日期', name: 'arrive_date',width:110},
				{ display: '联系方式', name: 'mobel_no',type: 'text',width:110,
					render: function(e){
		            	if(e.mobel_no !=undefined){
							var data="";
							data = e.mobel_no;
							data = data.substr(0,11);
		            		var h = "";
		                    h += "<a style ='cursor: none;text-decoration: none;color:black' title = "+e.mobel_no+" href=\"#\">"+data+"</a>";
		             	 	return h;
		            	}
		            }},
				{ display: '岗位', name: 'job_name',type: 'text',width:150,
		    			render: function(e){
		    				if (e.job_name != undefined) {
		    					var data="";
		    					data = e.job_name;
		    					data = data.substr(0,6);
		                		var h = "";
		                        h += "<a style ='cursor: none;text-decoration: none;color:black' title = "+e.job_name+" href=\"#\">"+data+"</a>";
		                 	 	return h;
		    				}
		    			}
				}
				,
				{ display: '所属部门', name: 'org_name',type: 'text',width:200,
					render: function(e){
						if (e.org_name != undefined) {
							var data="";
							data = e.org_name;
							data = data.substr(0,12);
		            		var h = "";
		                    h += "<a style ='cursor: none;text-decoration: none;color:black' title = "+e.org_name+" href=\"#\">"+data+"</a>";
		             	 	return h;
						}
					}
				}
				],
 	           	height : '96%',
 				pageSize : 30,
 				rownumbers:true,
 				checkbox:true,
 				colDraggable:true,
 	            rowDraggable:true,
 	            heightDiff :13,
 	            frozen : false 
     }); 
	
	//grid查询
    $("#searchbtn").ligerButton({ click: function ()
    {
    	loadData();
    }  
	});
    
	$("#param_val").ligerTextBox({
		
	})
	
	$("#param_type").ligerTextBox({
		
	})
	
  //重置搜索条件
	$("#resetbtn").ligerButton({ click: function ()
         {
              clearForm();   
           }
	}); 
    
	menu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
        [
          {text : '添加组织机构',id : 'add',click : itemclick,img : baseImgPath+'add.gif'},
		  {text : '修改组织机构',id : 'alter',click : itemclick,img : baseImgPath+'edit.gif'},
		  {text : '删除组织机构',id : 'delete',click : itemclick,img : baseImgPath+'delete.gif'},
		  {text : '查看组织机构',id : 'see',click : itemclick,img : baseImgPath+'comment.gif'}
       ]
  });
	
	$("#tree1").bind("contextmenu", function (e){
		menu.show({ top: e.pageY, left: e.pageX });
		return false;
		});
});

//点击工具条事件
function itemclick(item){
	 if(item.id){
		 setCurrWindows();
		 switch (item.id){
			 case "add":
				 var nodes = tree_manager.getSelected();
		    	   	if(nodes == "" || nodes == null){
		    	   		Alert.tip("请选择组织结构树节点！");
		    	   		return;
		    	   	}
		    	 var pk_id = nodes.data.id;
		    	 var company_id = nodes.data.company_id;
				 createWindow=$.ligerDialog.open({
						title : '添加组织机构',
						width : 360,
						height : 220,
						isResize : false,
						url : basePath+'limits/organizationManager/organization_add.jsp?pk_id='+pk_id+'&company_id='+company_id,
						buttons :[
						            { text: '保存', width: 60 ,onclick:function(){
						            	createWindow.frame.submitForm();
						            }
						            },
						            { text: '取消', width: 60, onclick:function(item, dialog){
						            	closeWindow("createWindow");
						            }}
						        ]
					});
			 		windows.createWindow=createWindow;
			 		break;
				case "alter":
					var nodes = tree_manager.getSelected();
		    	   	if(nodes == "" || nodes == null){
		    	   		Alert.tip("请选择组织结构树节点！");
		    	   		return;
		    	   	}
		    	   	var pk_id = nodes.data.id;
			    	   createWindow=$.ligerDialog.open({
							title : '修改组织机构',
							width : 360,
							height : 220,
							isResize : false,
							url : basePath+'limits/organizationManager/organization_update.jsp?pk_id='+pk_id,
							buttons :[
							            { text: '保存', width: 60 ,onclick:function(){
							            	createWindow.frame.submitForm();
							            }
							            },
							            { text: '取消', width: 60, onclick:function(item, dialog){
							            	closeWindow("createWindow");
							            }}
							        ]
						});
			    	   windows.createWindow=createWindow;
			    	   break;
		       case "delete":
		    	   var nodes = tree_manager.getSelected();
		    	   	if(nodes == "" || nodes == null){
		    	   		Alert.tip("请选择组织结构树节点！");
		    	   		return;
		    	   	}
		    	   	var pk_id = nodes.data.id;
		    	   	var company_id = nodes.data.company_id;
		    	   $.ligerDialog.confirm('该节点是根节点，确定要删除吗？',
		    				function (yes){
						if(yes){
							$.ajax({
					    		url:basePath+'rest/orgStrcture/deleteStructure',
					    		data:{
					    			pk_id:pk_id,
					    			company_id:company_id
					    			},
					    		success:function(result) {
					    			var returnData=eval("("+result+")");
			    					if (returnData.success == 'true') {
			    						Alert.tip("删除成功！"); 
			    						getTreeData();
			    						
			    					} else {
			    						Alert.tip("删除失败！");
			    					}
			    				}
					    	});
						}
					});
		    	   break;
		       case "see":
		    	   var nodes = tree_manager.getSelected();
		    	   	if(nodes == "" || nodes == null){
		    	   		Alert.tip("请选择组织结构树节点！");
		    	   		return;
		    	   	}
		    	   	var pk_id = nodes.data.id;
		    	   createWindow=$.ligerDialog.open({
						title : '查看组织机构',
						width : 360,
						height : 220,
						isResize : false,
						url : basePath+'limits/organizationManager/organization_view.jsp?pk_id='+pk_id,
						buttons :[
						            { text: '返回', width: 60, onclick:function(item, dialog){
						            	closeWindow("createWindow");
						            }}
						        ]
					});
		    	   windows.createWindow=createWindow;
		    	   break;
		       case "select":
		    	   var nodes = tree_manager.getSelected();
		    	   	if(nodes == "" || nodes == null){
		    	   		Alert.tip("请选择组织结构树节点！");
		    	   		return;
		    	   	}
		    	   	var org_id = nodes.data.id;
		    	   	createWindow=$.ligerDialog.open({
						title : '选择员工',
						width : 800,
						height : 500,
						isResize : false,
						url : basePath+'limits/organizationManager/employee_info.jsp?org_id='+org_id,
						buttons :[
									{ text: '确定', width: 60 ,onclick:function(){
										var gridService = createWindow.frame.gridManager;
										if(gridService.selected.length < 1){
												Alert.tip("请选择员工！");
													return;
										}
										var pk_ids = "";
										for(var i=0;i<gridService.selected.length;i++){
											pk_ids += gridService.selected[i].pk_id+";";
										}
										createWindow.frame.submitForm(pk_ids,org_id);
									}
									},
						            { text: '返回', width: 60, onclick:function(item, dialog){
						            	closeWindow("createWindow");
						            }}
						        ]
					});
		    	   windows.createWindow=createWindow;
		    	   break;
		       case "remove":
		    	   	var nodes = tree_manager.getSelected();
		    	   	if(nodes == "" || nodes == null){
		    	   		Alert.tip("请选择组织结构树节点！");
		    	   		return;
		    	   	}
		    	   	//company_id 移除后org_id改为公司id
		    	   	var company_id = nodes.data.company_id;
		    	   	//org_id 选择的组织机构id
		    	   	var org_id = nodes.data.id;
		    	   if (!projectManager.getSelectedRow()) { 
						Alert.tip("请至少勾选一条信息！");
						return; 
					}
					var pk_ids = "";
					for(var i=0;i<projectManager.selected.length;i++){
						pk_ids += projectManager.selected[i].pk_id+";";
					}
					$.ligerDialog.confirm('确定要移除吗？',
		    				function (yes){
						if(yes){
							$.ajax({
					    		url:basePath+'rest/GetStaffInfoAction/removeStaff',
					    		data:{
					    			pk_ids:pk_ids,
					    			company_id:company_id
					    			},
					    		success:function(result) {
					    			var returnData=eval("("+result+")");
			    					if (returnData.success == 'true') {
			    						Alert.tip("移除成功！"); 
			    						loadDate(org_id);
			    					} else {
			    						Alert.tip("移除失败！");
			    					}
			    				}
					    	});
						}
					});
				break;
		 }
     }
}



//把当前操作句柄传递给父窗口
function setCurrWindows() {
	currWin = window;
}

//关闭窗口
function closeWindow(winName){
	if(windows && windows[winName]){
		windows[winName].close();
	}
}

function f_reload()
{    
  	projectManager.set({newPage:1});    
  	projectManager.loadData(true);
}

function closeAndRefresh(name) {
	closeWindow(name);
	f_reload();
}


function getTreeData(){
	tree_manager = $("#tree1").ligerTree({ 
		url:basePath+'rest/orgStrcture/getStrctureList',
       	textFieldName:'text',
		idFieldName : 'id',
		parentIDFieldName:'pid',
		checkbox:false,
		nodeWidth: 200,
		onSelect:function(data){
			if(data.data !=null){
       			var org_id = data.data.id;
       			loadDate(org_id);
       		}
       		
		} 
     });
}

function loadDate(org_id){
	$("#maingrid").ligerGrid({
			url:basePath+'rest/GetStaffInfoAction/getStaffByOrgID?org_id='+org_id
		});
}

function clearForm(){
	   $('#form').find(':input').each(  
			   function(){
			  		$(this).val("");
			 		 if(this.ltype=='select'){
				  		$(this).ligerComboBox().reload();
			  		}
			   }	 
		);  
}

//窗口变化事件
function f_heightChanged(options) {
	var treeHeight = $("#scollDivTree")[0].style.height;
	$("#scollDivTree")[0].style.height = parseFloat(treeHeight.substring(0,treeHeight.length-2))+options.diff;
}