var user = null;
	$(function() {
		user = parent.currWin.gridManager.getCheckedRows();
		var listDataAll, listbox2Data = null;

		var listbox1Data = new Array();

		$("#userAccount").html(user[0].user_account);
		$.ajax({
					type : 'post',
					url : basePath+'rest/RoleAction/getRoleListByUserCode?userAccount='
							+ user[0].pk_id,
					dataType : 'json',
					success : function(listbox2Data) {
						$.ajax({
									type : 'post',
									url : basePath+'rest/RoleAction/getRoleList?state='+'1',
									dataType : 'json',
									success : function(listDataAll) {
										//所有角色
										var listDataAll2 = listDataAll.Rows;
										//已授权角色
										var listbox2Data2 = listbox2Data.Rows;
										for ( var i = 0; i < listDataAll2.length; i++) {
											var flage = false;
											for ( var j = 0; j < listbox2Data2.length; j++) {
												if (listDataAll2[i].pk_id == listbox2Data2[j].role_code) {
													flage = true; //从所有角色中过滤掉已经授权的角色
													break;
												}
											}
											if (!flage) {
												listbox1Data.push(listDataAll2[i]);
											}
										}
										$("#listbox1").ligerListBox({
											data : listbox1Data,
											isShowCheckBox : true,
											isMultiSelect : true,
											valueField : 'role_code',
											textField : 'role_name',
											height : 200
										});
										$("#listbox2").ligerListBox({
											data : listbox2Data2,
											isShowCheckBox : true,
											isMultiSelect : true,
											valueField : 'role_code',
											textField : 'role_name',
											height : 200
										});
									}
								});
					}

				});
	});

	function moveToLeft() {
		var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
		var selecteds = box2.getSelectedItems();
		if (!selecteds || !selecteds.length)
			return;
		box2.removeItems(selecteds);
		box1.addItems(selecteds);
	}
	function moveToRight() {
		var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
		var selecteds = box1.getSelectedItems();
		if (!selecteds || !selecteds.length)
			return;
		box1.removeItems(selecteds);
		box2.addItems(selecteds);
	}
	function moveAllToLeft() {
		var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
		var selecteds = box2.data;
		if (!selecteds || !selecteds.length)
			return;
		box1.addItems(selecteds);
		box2.removeItems(selecteds);
	}
	function moveAllToRight() {
		var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
		var selecteds = box1.data;
		if (!selecteds || !selecteds.length)
			return;
		box2.addItems(selecteds);
		box1.removeItems(selecteds);
	}
	function btnOkOnclick() {
		var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
		var selectKeyID = '';
		for ( var i = 0; i < box2.data.length; i++) {
			if (i==0)
			{
				selectKeyID = box2.data[i].pk_id;
			} else{
				selectKeyID = selectKeyID + ","+box2.data[i].pk_id;
			}
		}
		$.ajax({
					type : 'post',
					url : basePath+'rest/RoleAction/alterUserRole?userAccount='+ user[0].pk_id+'&&roleCodes='+selectKeyID,
					dataType : 'json',
					success : function(result) {
						if (result.success == 'true') {
							window.parent.currWin.Alert.tip(result.message);
							window.parent.currWin.closeDlgAndReload();
						} else {
							window.parent.currWin.Alert.tip(result.message);
						}
					}
				});
	}

	function closeWindow() {
		window.parent.currWin.f_closeDlg();
	}