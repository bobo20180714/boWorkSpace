var gridManager = null;
var xdlg;
	$(function() {
		var baseImgPath=basePath+'lib/ligerUI/skins/icons/';
		//工具条
		$("#toptoolbar").ligerToolBar({
			items : [ 
				{ text : '新建',id : 'add',click : itemclick,img : baseImgPath+'add.gif'}, 
				{ text : '修改',id : 'alter',click : itemclick,img : baseImgPath+'modify.gif'},
				{ text : '删除',id : 'delete',click : itemclick,img : baseImgPath+'delete.gif'},  
				{ text : '启用',id : 'start',click : itemclick,img : baseImgPath+'true.gif'},  
				{ text : '禁用',id : 'stop',click : itemclick,img : baseImgPath+'busy.gif'}, 
				{ text : '授权',id : 'bind',click : itemclick,img : baseImgPath+'role.gif'} 
			]
		});
		//表格
		$("#maingrid").ligerGrid({
			columns : [ 
			    { display : "",name:"pk_id",id:"pk_id",hide:true},
				{ display : '用户账号',name : 'user_account',width : '15%',
					render: function(e){
		            	if(e.user_account !=undefined){
							var data="";
							data = e.user_account;
							data = data.substr(0,15);
		            		var h = "";
		                    h += "<a style ='cursor: none;text-decoration: none;color:black' title = "+e.user_account+" href=\"#\">"+data+"</a>";
		             	 	return h;
		            	}
					}}, 
				{ display : '用户名称',name : 'user_name',width : '15%',
					render: function(e){
		            	if(e.user_name !=undefined){
							var data="";
							data = e.user_name;
							data = data.substr(0,11);
		            		var h = "";
		                    h += "<a style ='cursor: none;text-decoration: none;color:black' title = "+e.user_name+" href=\"#\">"+data+"</a>";
		             	 	return h;
		            	}
					}
				},
				{ display : '状态',name : 'state',width : '10%',
				render:function(data){
                	if(data.state=="0"){
						return "<font color='gray'>未启用<font>";
					}else if(data.state=="1"){
						return "<font color='green'>启用<font>";
					}else if(data.state=="2"){
						return "<font color='red'>禁用<font>";
					}
                }
			},
			   { display : '创建人',name : 'create_user_code',width : '10%'}, 
			   { display : '创建时间',name : 'create_time'} 
			],
			url : basePath+'rest/UserAction/getUserList',
			height : '97%',
			pageSize : 30,
			rownumbers:true,
			checkbox:true,
			colDraggable:true,
            rowDraggable:true,
            heightDiff :13,
            frozen:false 
		});
		gridManager = $("#maingrid").ligerGetGridManager();
		$("#searchbtn").ligerButton({
			click : function() {
				gridManager.set('parms', {
					account:$("#account").val(),
					name:$("#name").val()
				});
				gridManager.loadData();
			}
		});
		//重置搜索条件
		$("#resetbtn").ligerButton({
			click : function() {
				clearForm();
			}
		});
		$("#pageloading").hide();
});

function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "bind":
				var rowsdata = gridManager.getCheckedRows();
				if (rowsdata.length != 1) {
					Alert.tip("请选择一条数据！");
					return;
				} 
				if (gridManager.selected[0].state != 1) {
					Alert.tip('数据启用状态下才能进行授权！');
					return;
				}
					setCurrWindows();
					xdlg = $.ligerDialog
							.open({
								title : '授权',
								width : 500,
								height : 400,
								isResize : false,
								url : basePath+'limits/user/user_bind.jsp'
							});
				return;
			case "add":
				setCurrWindows();
				xdlg = $.ligerDialog
						.open({
							title : '新建用户',
							width : 450,
							height : 300,
							url : basePath+'limits/user/user_add.jsp',
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
			case "alter":
				if (!gridManager.getSelectedRow()) {
				     Alert.tip('请至少选择一条数据！'); 
				 	return; 
				}
				if (gridManager.selected.length > 1) { 
				    Alert.tip('只能选择一条数据！'); 
				 	return; 
				}
				if (gridManager.selected[0].state == 1) {
					Alert.tip('数据启用状态下不能修改！');
					return;
				}
				setCurrWindows();
				var pk_id = gridManager.selected[0].pk_id;
				xdlg = $.ligerDialog
				.open({
					title : '修改用户',
					width : 400,
					height : 200,
					url : basePath+'limits/user/user_alter.jsp?pk_id='+pk_id,
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
			case "delete":
				if (!gridManager.getSelectedRow()) { 
					Alert.tip("请勾选一条信息！");
					return; 
				}
				
				for ( var i = 0; i < gridManager.selected.length; i++) {
					if (gridManager.selected[i].state == 1) {
						Alert.tip("只能删除未启用或禁用的用户");
						return;
					}
				}
	    	   
				$.ligerDialog.confirm('您确定要删除该用户信息吗？',function(yes){
					if(yes){
						var selectKeyID = '';
						for ( var i = 0; i < gridManager.selected.length; i++) {
							if (i==0)
							{
								selectKeyID = gridManager.selected[i].user_account;
							} else{
							    selectKeyID = selectKeyID + ","
									+ gridManager.selected[i].user_account;
							}
							
						}
						
						var retObj = $.ajax({
							url : basePath + 'rest/UserAction/deleteUser',
							data :{
								userCodes:selectKeyID
							},
							async : false
						});
						
						var dataObj = eval("(" + retObj.responseText + ")");
						if (dataObj.success == 'true') {
							Alert.tip("删除成功！");
							f_reload();
							gridManager.loadData(true);
						} else {
							Alert.tip(dataObj.message);
						}
					}

					});
				return;

			case "start":
				if (!gridManager.getSelectedRow()) { 
					Alert.tip("请勾选一条信息！");
					return; 
				}
	    	   for ( var i = 0; i < gridManager.selected.length; i++) {
					if (gridManager.selected[i].state == 1) {
						Alert.tip("只能启用状态为未启用或禁用的用户");
						return;
					}
				}
	    	   
				$.ligerDialog.confirm('您确定要启用选择的用户信息吗？',function(yes){
					if(yes){
						var selectKeyID = '';
						for ( var i = 0; i < gridManager.selected.length; i++) {
							if (i==0)
							{
								selectKeyID = gridManager.selected[i].user_account;
							} else{
							    selectKeyID = selectKeyID + ","
									+ gridManager.selected[i].user_account;
							}
							
						}
						
						var retObj = $.ajax({
							url : basePath + 'rest/UserAction/startUser',
							data :{
								userCodes:selectKeyID
							},
							async : false
						});
						
						var dataObj = eval("(" + retObj.responseText + ")");
						if (dataObj.success == 'true') {
							Alert.tip("启用成功！");
							f_reload();
						} else {
							Alert.tip(dataObj.message);
						}
					}

					});
				return;
			case "stop":
				if (!gridManager.getSelectedRow()) { 
					Alert.tip("请勾选一条信息！");
					return; 
				}
				
				for ( var i = 0; i < gridManager.selected.length; i++) {
					if (gridManager.selected[i].state != 1) {
						Alert.tip("只能禁用状态为启用的用户");
			    		return;
					}
				}
	    	   
				$.ligerDialog.confirm('您确定要禁用选择的用户信息吗？',function(yes){
					if(yes){
						var selectKeyID = '';
						for ( var i = 0; i < gridManager.selected.length; i++) {
							if (i==0)
							{
								selectKeyID = gridManager.selected[i].user_account;
							} else{
							    selectKeyID = selectKeyID + ","
									+ gridManager.selected[i].user_account;
							}
							
						}
						
						var retObj = $.ajax({
							url : basePath + 'rest/UserAction/stopUser',
							data :{
								userCodes:selectKeyID
							},
							async : false
						});
						
						var dataObj = eval("(" + retObj.responseText + ")");
						if (dataObj.success == 'true') {
							Alert.tip("禁用成功！");
							f_reload();
						} else {
							Alert.tip(dataObj.message);
						}
					}
					});
				return;
			}
		}
	
	}
//把当前操作句柄传递给父窗口
function setCurrWindows() {
	currWin = window;
}
//关闭对话框
function f_closeDlg() {
	xdlg.close();
}
//刷新grid
function f_reload() {
	gridManager.set({
		newPage : 1
	});
	gridManager.loadData(true);
}
function closeDlgAndReload(){
      f_closeDlg();
      f_reload();
  }
function clearForm(){
   $('#form1').find(':input').each(  
   function(){
	   switch(this.type){
		   case 'passsword': 
		   case 'select-multiple':  
		   case 'select-one':  
		   case 'text':
		   case 'textarea': 
			    if(!this.readOnly){
		          $(this).val('');  
		          break;
		        }
		   case 'checkbox':  
		   case 'radio': 
		        this.checked = false;  
	   }
	}     
	);  
}