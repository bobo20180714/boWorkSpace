var formData = null;
var win=parent.currWin;
$(function(){
	formData = $("#form1").ligerForm({
		labelAlign:'right',
        inputWidth: 170, 
        labelWidth: 80, 
        space: 20,
        validate:true,
        fields: [
            {display:"userId",name:"userId",id:"userId",type: "hidden"},
            { display: "用户账号", name: "userAccount",id:"userAccount",
			  type: "text", newline: false,readonly:true
			}
			,
			{ display: "用户名称", name: "userName",id:"userName",
			    newline: true, type: "text" ,validate:{required:true,specialText:true,maxlength:50}
          	}
        ]
	});
	setFormData();
});

//表单赋值
function setFormData(){
	$.ajax({
		url:basePath+'rest/UserAction/getUserListByUser',
		data:{
			pk_id:pk_id
		},
		asynsc:false,
		success:function(data){
			var jsobj=eval('('+data+')');
			formData.setData(jsobj);
		}
	});

}
function submitForm() {
	if(toValidForm()){
		var data = formData.getData();
		$.ajax({
			url:basePath+"rest/UserAction/updateUser",
			data:{
				userAccount:data.userAccount,
				userName:data.userName,
				userId:data.userId
			},
			asynsc:false,
			success:function(data){
				var jsobj=eval('('+data+')');
				if(jsobj.success=='true'){
					win.Alert.tip("修改信息成功!");
					win.closeDlgAndReload();
				}else{
					win.Alert.tip("修改信息失败!");
				}
			}
		});
	}
}
//验证
function toValidForm() {
	var vboolean = $("#form1").valid();
	if (vboolean) {
		return true;
	} else {
		return false;
	}
}
//验证两次密码是否一致
function checkPWd(){
	if ($("#confirmPass").val() != $("#userPwd").val()) {
		win.Alert.tip("两次密码输入不一致！");
		$("#confirmPass").val("");
	}
}