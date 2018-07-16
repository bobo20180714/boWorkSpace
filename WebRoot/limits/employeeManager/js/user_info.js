var gridManager = null;
var xdlg;
var win = parent.currWin;
$(function() {
	//表格
	$("#maingrid").ligerGrid({
		columns : [ {
			display : '用户账号',
			name : 'user_account',
			width : '20%',
			render: function(e){
            	if(e.user_account !=undefined){
					var data="";
					data = e.user_account;
					data = data.substr(0,15);
            		var h = "";
                    h += "<a style ='cursor: none;text-decoration: none;color:black' title = "+e.user_account+" href=\"#\">"+data+"</a>";
             	 	return h;
            	}
			}
		}, {
			display : '用户名称',
			name : 'user_name',
			width : '20%',
			render: function(e){
            	if(e.user_name !=undefined){
					var data="";
					data = e.user_name;
					data = data.substr(0,10);
            		var h = "";
                    h += "<a style ='cursor: none;text-decoration: none;color:black' title = "+e.user_name+" href=\"#\">"+data+"</a>";
             	 	return h;
            	}
			}
		}, 
		{
			display : '状态',
			name : 'state',
			width : '15%',
			render : function(data) {
				if (data.state == "0") {
					return "<font color='gray'>未启用<font>";
				} else if (data.state == "1") {
					return "<font color='green'>启用<font>";
				} else if (data.state == "2") {
					return "<font color='red'>禁用<font>";
				}
			}
		}, {
			display : '创建人',
			name : 'create_user_code',
			width : '15%'
		}, {
			display : '创建时间',
			name : 'create_time',
			width : '20%'
		} ],
		url : basePath+'rest/UserAction/getLinkUserList?staffId='+parent.currWin.editSelectId,
		height : '96%',
		pageSize : 30,
		rownumbers:true,
		checkbox:true,
		colDraggable:true,
        rowDraggable:true,
        onAfterShowData :setGridData,
        heightDiff :17
	});
	gridManager = $("#maingrid").ligerGetGridManager();
	$("#searchbtn").ligerButton({
		click : function() {
			gridManager.set('parms', {
				account:$("#account").val(),
				name:$("#name").val(),
				staffId:parent.currWin.editSelectId
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


//该id员工关联了哪些用户
function setGridData(){
	var staff_id = parent.currWin.editSelectId;
	
	for ( var j = 0; j < gridManager.data.Total; j++) {
		if(staff_id == gridManager.data.Rows[j].staff_id){
			gridManager.select(gridManager.data.Rows[j]);
		}
	}
}

//关联
function submitForm(){
	var staff_id = parent.currWin.editSelectId;
	  var select_userIds = '';
	  for (var i = 0; i < gridManager.selected.length; i++){
		  select_userIds +=  gridManager.selected[i].pk_id+";" ;
	  }
	$.ajax({
		type : 'post',
		url : basePath+"rest/StaffInfoAction/linkStaff",
		data : {
			staff_id : staff_id,
			userIds : select_userIds
		},
		success : function(reqMessage) {
			var reqMessage=eval('('+reqMessage+')');
			if (reqMessage.success=="true") {
				win.Alert.tip('关联成功！');
				win.f_closeDlg();
			}
		}
	});
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