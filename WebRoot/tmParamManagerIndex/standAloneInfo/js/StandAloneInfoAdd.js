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
	         { display: "单机编号", name: "stand_alone_code",id:"stand_alone_code",type: "text",newline: true,validate:{required:true,maxlength:30 }},
			{ display: "单机名称", name: "stand_alone_name",id:"stand_alone_name",type: "text",newline: true,validate:{required:true,maxlength:30}}
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
		data.sub_system_id=getParam("sub_system_id");
		
		$.ajax({
			url:basePath+"rest/aloneinfo/standaloneinfoadd",
			data:data,
			async:false,
			success:function(rsData) {
				var result = eval("("+rsData+")");
				if (result.success == 'true') {
					parent.currWin.tree_fresh();
					parent.Alert.tip("新增成功!");
					parent.currWin.c_closeDlg();
				} else {
					parent.Alert.tip(result.message);
				}
			}
		});
	
	}
}

/*function check(data){
	if(data!=null){
		
		if(isEmpty(data.stand_alone_name)){
			parent.alert("单机名称不能为空！");
			return false;
			}
		
		if(isEmpty(data.stand_alone_code)){
			parent.alert("单机代号不能为空！");
			return false;
			}
		
		
		if((data.stand_alone_name).length>30){
			parent.alert("单机名称长度不能超过30！");
			return false;
			}
		
		if((data.stand_alone_code).length>30){
			parent.alert("单机代号长度不能超过30！");
			return false;
			}
	}else{
		$.ligerDialog.warn("未获得表单数据！");
		return false;
	}
	return true;
	}*/

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

