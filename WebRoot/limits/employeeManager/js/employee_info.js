var gridManager = null;
$(function ()
{
	var baseImgPath=basePath+'lib/ligerUI/skins/icons/';
    //工具条
    $("#toptoolbar").ligerToolBar({ items: [
		{ id: 'add', text: '新建', click: itemclick, img: baseImgPath+'add.gif' },
		{ id: 'modify', text: '修改', click: itemclick, img: baseImgPath+'modify.gif' },
		{ id: 'delete', text: '删除', click: itemclick, img: baseImgPath+'delete.gif' },
        { id: 'view', text: '查看', click: itemclick, img: baseImgPath+'comment.gif' },
        { id: 'accredit', text: '关联用户', click: itemclick, img: baseImgPath+'communication.gif'  }
    ]
    });
	$("#staff_code").ligerTextBox({
		
	});
	
	$("#staff_name").ligerTextBox({
		
	});
    //搜索
    $("#searchbtn").ligerButton({ click: function ()
        {
            gridManager.set('parms', { 
            	staffCode: $("#staffCode").val(),
            	staffName:$("#staffName").val()});
            gridManager.loadData();
        }
        
    }); 
    $("#resetbtn").ligerButton({ click: function ()
        {  
           $("#form1").get(0).reset();
        }
    }); 
    
    //表格
    $("#maingrid").ligerGrid({
        columns: [ 
        {display: 'pk_id',name: 'pk_id',hide:true},
		{ display: '员工工号', name: 'staff_code',type: 'text',
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
		{ display: '员工姓名', name: 'staff_name',type: 'text',
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
		{ display: '员工性别', name: 'sex_name',type: 'text'
		},
		{ display: '入职日期', name: 'arrive_date'},
		{ display: '联系方式', name: 'mobel_no',type: 'text',
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
		{ display: '岗位', name: 'job_name',type: 'text',
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
         
        url:basePath+'rest/GetStaffInfoAction/staffList', 
        height : '97%',
		pageSize : 30,
		rownumbers:true,
		checkbox:true,
		colDraggable:true,
        rowDraggable:true,
        heightDiff :13
    });
    gridManager = $("#maingrid").ligerGetGridManager();
    $("#pageloading").hide();
});

function itemclick(item)
{ 
    if(item.id)
    {
        switch (item.id)
        {
            case "Aqua":
                $("#maingrid").removeClass("l-grid-gray");
                return;
                
            case "add":
              	setCurrWindows();
				xdlg = $.ligerDialog.open({ 
				          width: 700,
				          height: 550,
	                      title:'新建员工信息',
	                      url:basePath+'limits/employeeManager/employee_add.jsp',
	                      buttons :[
							{ text: '保存', width: 60 ,onclick:function(item, dialog){
							 	dialog.frame.submitForm();
							 }},
							{ text: '取消', width: 60, onclick:function(item, dialog){
								f_closeDlg();
							}}
				      	 ]
						});
                return;
                
            case "modify":
				if (!gridManager.getSelectedRow()) {
				     Alert.tip('请至少选择一条数据！'); 
				 	return; 
				}
				if (gridManager.selected.length > 1) { 
				    Alert.tip('只能选择一条数据！'); 
				 	return; 
				}
			  	setCurrWindows();
				editSelectId = gridManager.selected[0].staff_code;
				xdlg = $.ligerDialog.open({ 
							width: 700,
							height: 550,
							title:'修改员工信息',
							url: basePath+'limits/employeeManager/employee_alter.jsp',
            				buttons :[
						        { text: '保存', width: 60 ,onclick:function(item, dialog){
						         	dialog.frame.submitForm();
						         }},
						        { text: '取消', width: 60, onclick:function(item, dialog){
						        	f_closeDlg();
						        }}
						    ]
						});
		     	xdlg.show();  
                return;
                
            case "delete":
                 if (!gridManager.getSelectedRow()) { 
					  Alert.tip('请至少选择一条数据！');  
					  return; 
			     }
				  var select_staff_code = '';
				  for (var i = 0; i < gridManager.selected.length; i++){
		              select_staff_code +=  gridManager.selected[i].staff_code+";" ;
				  }
		          $.ligerDialog.confirm('你确定要删除选中的记录吗？', function (yes){
                          if(yes){
                        	  var retObj = $.ajax({
							  				url:basePath+'rest/StaffInfoAction/deleteStaffByCode',
											data:{staffCodes:select_staff_code},
											async : false,
											success:function(data){
												var dataObj = eval('( '+ data +' )');
												if (dataObj.success == 'true') {
													Alert.tip('删除数据成功！');
													gridManager.set({
														newPage : 1
													});
													gridManager.loadData(true);
												} else {
													Alert.tip('删除数据失败！');
												}
											}
										});
						}
						});
				return;
				
			case "view":
				if (!gridManager.getSelectedRow()) {
					Alert.tip('请至少选择一条数据！');
					return;
				}
				if (gridManager.selected.length > 1) {
					Alert.tip('只能选择一条数据！');
					return;
				}
				setCurrWindows();
				editSelectId = gridManager.selected[0].staff_code;
				xdlg = $.ligerDialog.open({
					width : 700,
					height : 550,
					title : '查看员工信息',
					url : basePath+'limits/employeeManager/employee_view.jsp',
					buttons :[
						        { text: '返回', width: 60, onclick:function(item, dialog){
						        	f_closeDlg();
						        }}
						    ]
				});
				xdlg.show();
				return;
				
			case "accredit":
				if (!gridManager.getSelectedRow()) {
					Alert.tip('请至少选择一条数据！');
					return;
				}
				if (gridManager.selected.length > 1) {
					Alert.tip('只能选择一条数据！');
					return;
				}
				setCurrWindows();
				editSelectId = gridManager.selected[0].pk_id;
				//user_id = gridManager.selected[0].user_id;
				xdlg = $.ligerDialog.open({
					width : 800,
					height : 580,
					title : '关联用户信息',
					url : basePath+'limits/employeeManager/user_info.jsp',
					buttons :[
						        { text: '确定', width: 60 ,onclick:function(item, dialog){
						         	dialog.frame.submitForm();
						         }},
						        { text: '取消', width: 60, onclick:function(item, dialog){
						        	f_closeDlg();
						        }}
						      ]
				});
				xdlg.show();
				return;
			}
	}
}
//调用查询
function f_search() {
	maingrid.options.data = CustomersData1;
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

function getStrctureByID(org_id){
	var jsobj = null;
	$.ajax({
		url:basePath+"rest/organization/getStrctureByID",
		data:{
			org_id:org_id
		},
		async:false,
		success:function(data){
			jsobj = eval('('+data+')');
		}
	});
	if(jsobj != null){
		return jsobj[0].text;
	}
}