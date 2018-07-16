var formdata = null;
$(function (){
	initForm();
});

function initForm(){
	formData = $("#form1").ligerForm({
		width:360,
    	labelAlign:'center',
        inputWidth: 200, 
        labelWidth: 100, 
        space: 20,
        validate:true,//验证
        fields: [
            { display: "系统编号", name: "sub_system_code",id:"sub_system_code",type: "text",newline: true,validate:{required:true,maxlength:30 }},
			{ display: "系统名称", name: "sub_system_name",id:"sub_system_name",type: "text",newline: true,validate:{required:true,maxlength:30 }}
        ]
    }); 
	
}

/**
*
*获取url里的参数
*@param 参数名
*/
function getParam(name){

	var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)","i");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null){
		return unescape(r[2]);
	}
	return null;

}

//页面提交
function submitForm(){
	var data = formData.getData();
	if(toValidForm()){
		//数据提交
		data.sat_id=getParam("sat_id");
		$.ajax({
			url:basePath+"rest/subinfo/subsysteminfoadd",
			data:data,
			async:false,
			success:function(rsData) {
				var result = eval("("+rsData+")");
				if (result.success == "true") {
					parent.currWin.Alert.tip("新增成功!");
					parent.currWin.tree_fresh();
					parent.currWin.f_reload();
					parent.currWin.f_closeDlg();
				} else {
					parent.Alert.tip(result.message);
				}
			}
		});
	}
}

function check(data){
	if(data!=null){
		
		if(isEmpty(data.sub_system_name)){
			parent.alert("系统名称不能为空！");
			return false;
			}
		
		if(isEmpty(data.sub_system_code)){
			parent.alert("系统代号不能为空！");
			return false;
			}
		
		if((data.sub_system_name).length>30){
			parent.alert("分系统名称长度不能超过30！");
			return false;
			}
		
		if((data.sub_system_code).length>30){
			parent.alert("分系统代号长度不能超过30！");
			return false;
			}
		}else{
		$.ligerDialog.warn("未获得表单数据！");
		return false;
	}
	return true;
	}

function isEmpty(param){
	if(param==null||$.trim(param)==""){
		return true;
	}else{
		return false;
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


