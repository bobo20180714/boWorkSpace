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
            { display: "用户账号", name: "userAccount",id:"userAccount",
			  type: "text", newline: false,validate:{required:true,minlength:3,maxlength:50,identifierNO:true},onblur:checkAccount
			}
			,
			{ display: "用户名称", name: "userName",id:"userName",
			    newline: true, type: "text" ,validate:{required:true,specialText:true,maxlength:50}
          	}
        	,
        	{ display: "密码", name: "userPwd",id:"userPwd",type: "password" ,
        		validate:{required:true,minlength:6,maxlength:30}, newline: true
            }
        	,
          	{ display: "确认密码", name: "confirmPass",id:"confirmPass",
			  type: "password",validate:{required:true},onblur:checkPWd
        	, newline: true
			}
        ]
	});
});
function submitForm() {
	if(toValidForm()){
		var data = formData.getData();
		$.ajax({
			url:basePath+"rest/UserAction/addUser",
			data:{
				userAccount:data.userAccount,
				userName:data.userName,
				userPwd:data.userPwd,
				state:"0"
			},
			asynsc:false,
			success:function(data){
				var jsobj=eval('('+data+')');
				if(jsobj.success=='true'){
					win.Alert.tip("新建信息成功!");
					win.closeDlgAndReload();
				}else{
					win.Alert.tip("新建信息失败!");
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
	if (liger.get("confirmPass").getValue() != liger.get("userPwd").getValue()) {
		win.Alert.tip("两次密码输入不一致！");
		liger.get("confirmPass").setValue("");
		return false;
	}
	return true;
}

//验证账号是否存在
function checkAccount(){
	var account = liger.get("userAccount").getValue().replace(/\s+/g, "");
	$.ajax({
		url:basePath+"rest/UserAction/checkAccount",
		data:{
			userAccount:account
		},
		asynsc:false,
		success:function(data){
			var jsobj=eval('('+data+')');
			if(jsobj.success=='true'){
				win.Alert.tip("账号已存在，请重新输入!");
				liger.get("userAccount").setValue("");
			}
		}
	});
}