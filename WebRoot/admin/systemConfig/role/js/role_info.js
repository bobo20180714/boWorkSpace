var gridManager = null;
$(function() {
	var height = $(".l-layout-center").height();
	var baseImgPath = basePath + 'lib/ligerUI/skins/icons/';
	// 工具条
	$("#toptoolbar").ligerToolBar({
		items : [ {
			text : '新建',
			id : 'add',
			click : itemclick,
			icon:'add'
//			img : baseImgPath + 'add.gif'
		}, {
			text : '修改',
			id : 'alter',
			click : itemclick,
			icon:'update'
//			img : baseImgPath + 'modify.gif'
		}, {
			text : '删除',
			id : 'delete',
			click : itemclick,
			icon:'delete'
//			img : baseImgPath + 'delete.gif'
		}/*, {
			text : '启用',
			id : 'start',
			click : itemclick,
			img : baseImgPath + 'true.gif'
		}, {
			text : '禁用',
			id : 'stop',
			click : itemclick,
			img : baseImgPath + 'busy.gif'
		}*/, {
			text : '授权',
			id : 'accredit',
			click : itemclick,
			img : baseImgPath + 'role.gif'
		} ]
	});
	// 重置搜索条件
	$("#resetbtn").ligerButton({
		type:'two',
		click : function() {
			clearForm();
		}
	});
	// 表格
	$("#maingrid").ligerGrid({
		columns : [ {
			display : 'role_code',
			name : 'role_code',
			align : 'center',
			hide : 'true'
		}, {
			display : '角色名称',
			name : 'role_name',
			align : 'center',
			width:200
		}, {
			display : '角色描述',
			name : 'role_desc',
			align : 'left',
			width:400
		}/*, {
			display : '状态',
			name : 'state',
			render : function(data) {
				if (data.state == "0") {
					return "<font color='gray'>未启用<font>";
				} else if (data.state == "1") {
					return "<font color='green'>启用<font>";
				} else if (data.state == "2") {
					return "<font color='red'>禁用<font>";
				}
			}
		}*/ ],
		url : basePath + 'rest/RoleAction/getRoleList',
		height : '100%',
		pageSize : 30,
		rownumbers:true,
		checkbox:true,
		colDraggable:true,
        rowDraggable:true,
        cssClass: 'l-grid-gray'/*, 
        heightDiff :13*/
	});
	gridManager = $("#maingrid").ligerGetGridManager();
	$("#pageloading").hide();
	// grid查询
	$("#searchbtn").ligerButton({
		type:'one',
		click : function() {
			var queryData = formData1.getData();
			gridManager.set('parms', {
				name : queryData.name
			});

			gridManager.loadData();
		}
	});
	initForm2();
});


function initForm2(){
    formData1 = $("#form1").ligerForm({
    		labelAlign:'right',
        	inputWidth: 100, 
            labelWidth: 80, 
            space: 5,
            validate:true,
            fields: [
				{display: '角色名称', id: 'name',name:'name',type:"text",newline: false}
			]
    });
}


function itemclick(item) {
	if (item.id) {
		switch (item.id) {
		case "accredit":
			if (gridManager.selected.length != 1) {
				Alert.tip('请选择一条数据！');
				return;
			}
			
			/*if (gridManager.selected[0].state == 0) {
				Alert.tip("未启用状态下不能对角色进行授权");
				return;
			}else if (gridManager.selected[0].state == 2) {
				Alert.tip("禁用状态下不能对角色进行授权");
				return;
			}*/
			setCurrWindows();
			roleName = gridManager.selected[0].role_name;
			roleCode = gridManager.selected[0].role_code;
			xdlg = $.ligerDialog.open({
				width : 350,
				height : 500,
				title : '角色授权',
				url : basePath + 'admin/systemConfig/role/role_accredit.jsp?roleName='
						+ roleName,
				buttons : [ {
					text : '保存',
					type:'save',
					width : 80,
					onclick : function(item, dialog) {
						dialog.frame.formSubmit();
					}
				}, {
					text : '关闭',
					type:'close',
					width : 80,
					onclick : function(item, dialog) {
						f_closeDlg();
					}
				} ]
			});
			xdlg.show();
			return;
		case "add":
			setCurrWindows();
			xdlg = $.ligerDialog.open({
				width : 400,
				height : 240,
				title : '新建角色',
				url : basePath + 'admin/systemConfig/role/role_add.jsp',
				buttons : [ {
					text : '保存',
					type:'save',
					width : 80,
					onclick : function(item, dialog) {
						dialog.frame.submitForm();
					}
				}, {
					text : '关闭',
					type:'close',
					width : 80,
					onclick : function(item, dialog) {
						f_closeDlg();
					}
				} ]
			});
			xdlg.show();
			return;
		case "alter":
			if (gridManager.selected.length != 1) {
				Alert.tip('请选择一条数据！');
				return;
			}
/*			if (gridManager.selected[0].state == 1) {
				Alert.tip('数据启用状态下不能修改！');
				return;
			}*/
			setCurrWindows();
			var role_code = gridManager.selected[0].role_code;
			xdlg = $.ligerDialog.open({
				width : 400,
				height : 240,
				title : '修改角色',
				url : basePath + 'admin/systemConfig/role/role_alter.jsp?role_code='
						+ role_code,
				buttons : [ {
					text : '保存',
					type:'save',
					width : 80,
					onclick : function(item, dialog) {
						dialog.frame.submitForm();
					}
				}, {
					text : '关闭',
					type:'close',
					width : 80,
					onclick : function(item, dialog) {
						f_closeDlg();
					}
				} ]
			});
			xdlg.show();
			return;
		case "delete":
			if (!gridManager.getSelectedRow()) {
				Alert.tip('请至少选择一条数据！');
				return;
			}
			
/*			for ( var i = 0; i < gridManager.selected.length; i++) {
				if (gridManager.selected[i].state == 1) {
					Alert.tip("只能删除状态为未启用的用户");
					return;
				}
			}*/
			
				$.ligerDialog
						.confirm(
								'您确定要删除该角色信息吗？',
								function(yes) {
									if (yes) {
										var selectKeyID = '';
										for ( var i = 0; i < gridManager.selected.length; i++) {
											if (i == 0) {
												selectKeyID = gridManager.selected[i].role_code;
											} else {
												selectKeyID = selectKeyID
														+ ","
														+ gridManager.selected[i].role_code;
											}
										}

										var retObj = $
												.ajax({
													url : basePath
															+ 'rest/RoleAction/delete',
													data : {
														roleCodes : selectKeyID
													},
													async : false
												});

										var dataObj = eval("("
												+ retObj.responseText + ")");
										if (dataObj.success == 'true') {
											Alert.tip('删除成功！');
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
    	   
				$.ligerDialog
						.confirm(
								'您确定要启用选择的用户信息吗？',
								function(yes) {
									if (yes) {
										var selectKeyID = '';
										for ( var i = 0; i < gridManager.selected.length; i++) {
											if (i == 0) {
												selectKeyID = gridManager.selected[i].role_code;
											} else {
												selectKeyID = selectKeyID
														+ ","
														+ gridManager.selected[i].role_code;
											}

										}

										var retObj = $.ajax({
											url : basePath
													+ 'rest/RoleAction/start',
											data : {
												roleCodes : selectKeyID
											},
											async : false
										});

										var dataObj = eval("("
												+ retObj.responseText + ")");
										if (dataObj.success == 'true') {
											Alert.tip('启用成功！');
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
					Alert.tip("只能禁用状态为启用的用户");;
		    		return;
				}
			}
    	   
		$.ligerDialog
				.confirm(
						'你确定要禁用选择的用户信息吗？',
						function(yes) {
							if (yes) {
								var selectKeyID = '';
								for ( var i = 0; i < gridManager.selected.length; i++) {
									if (i == 0) {
										selectKeyID = gridManager.selected[i].role_code;
									} else {
										selectKeyID = selectKeyID
												+ ","
												+ gridManager.selected[i].role_code;
									}

								}

								var retObj = $
										.ajax({
											url : basePath
													+ 'rest/RoleAction/stop',
											data : {
												roleCodes : selectKeyID
											},
											async : false
										});

								var dataObj = eval("("
										+ retObj.responseText + ")");
								if (dataObj.success == 'true') {
									Alert.tip('禁用成功！');
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
// 把当前操作句柄传递给父窗口
function setCurrWindows() {
	currWin = window;
}
// 关闭对话框刷新grid
function closeDlgAndReload() {
	f_closeDlg();
	f_reload();
}

// 关闭对话框
function f_closeDlg() {
	xdlg.close();
}
// 刷新grid
function f_reload() {
	gridManager.set({
		newPage : 1
	});
	gridManager.loadData(true);
}

function clearForm(){
	formData1.setData({
		name:""
	});
}