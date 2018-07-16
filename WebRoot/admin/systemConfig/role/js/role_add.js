var formData = null;
var win=parent.currWin;
$(function(){
	formData = $("#form1").ligerForm({
		labelAlign:'center',
        inputWidth: 200, 
        labelWidth: 100, 
        space: 20,
        validate:true,
        fields: [
            { display: "角色名称", name: "role_name",id:"role_name",
			  type: "text", newline: false,validate:{required:true,maxlength:15}
			},
            { display: "角色描述", name: "role_desc",id:"role_desc",
			  type: "textarea", newline: false,validate:{required:false,maxlength:100}
			}
        ]
	});
});
function submitForm() {
	if(toValidForm()){
		var data = formData.getData();
		$.ajax({
			url:basePath+"rest/RoleAction/add",
			data:{
				roleName:data.role_name,
				roleDesc:data.role_desc,
				state:"0"
			},
			asynsc:false,
			success:function(data){
				var jsobj=eval('('+data+')');
				if(jsobj.success=='true'){
					win.Alert.tip("新建信息成功!");
					win.closeDlgAndReload();
				}else{
					win.Alert.tip(jsobj.message);
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
