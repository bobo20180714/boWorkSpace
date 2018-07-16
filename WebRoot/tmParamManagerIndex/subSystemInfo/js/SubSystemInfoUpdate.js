var formData = null;
var sub_system_id = "";
var groupicon;
$(function (){
	initForm();
	getData();
});

function isEmpty(param){
	if(param==null||$.trim(param)==""){
		return true;
	}else{
		return false;
	}
	
}

/**
 * 加载渲染jsp页面
 */
function initForm(){
	formData = $("#form3").ligerForm({
		width:360,
    	labelAlign:'center',
        inputWidth: 200, 
        labelWidth: 100, 
        space: 20,//间隔宽度
        validate:true,//验证
        fields: [
//            { display: "系统id", name: "sub_system_id",id:"sub_system_id",type: "text",hide: true},
			{ display: "系统编号", name: "sub_system_code",id:"sub_system_code",type: "text",newline: true,readonly:true},
			{ display: "系统名称", name: "sub_system_name",id:"sub_system_name",type: "text",newline: true,validate:{required:true}}
        ]
    }); 
	
}

/**
*
*获取url里的参数
*@param 参数名
*/
function getParam(str){
	var reg = new RegExp("(^|&)"+str+"=([^&]*)(&|$)","i");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null){
		return unescape(r[2]);
	}
	return null;
}

function getData(){
	var id = getParam("sub_system_id");
	$.post(
			basePath+"rest/subinfo/subsysteminfobyid",
			{
				sub_system_id:id
			},
			function(data)
			{
				var row = data;
				formData.setData(row);
			},"json");
}
//function setFieldValidate(name,validate,messages){
//	if(){
//		
//	}
//}
//页面提交
function submitForm(){
	var data = formData.getData();
	if(toValidForm()){
	//数据提交
	data.sub_system_id=getParam("sub_system_id");
	data.sat_id = getParam("satId");
	$.ajax({
		url:basePath+"rest/subinfo/subsysteminfoupdate",
		data:data,
		async:false,
		success:function(rsData) {
			var result = eval("("+rsData+")");
			if (result.success == "true") {
				parent.currWin.Alert.tip("修改信息成功!");
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

/**
 * 表单校验
 * @param data 表单数据
 * @returns {为true则表单校验通过，false为不通过。}
 */
function check(data){
	var integerReg = /^[1-9]+[0-9]*]*$/; 
	if(data!=null){

	}
	return true;
}

//验证
function toValidForm(){
	var vboolean = $("#form3").valid();
	if (vboolean) {
		return true;
	} else {
		return false;
	}
}


