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
        title:'[新增]组织机构节点信息',
        fields: [
			{ display: "机构编号", name: "org_code",id:"org_code",type: "text",newline: true,validate:{maxlength:15}},
			{ display: "机构名称", name: "org_name",id:"org_name",type: "text",newline: true,validate:{required:true,maxlength:15}},
			{ display: "机构描述", name: "org_desc",id:"org_desc",type: "text",newline: true,validate:{maxlength:60}}
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
	data.org_id=getParam("id");
//	data.parent_id=getParam("id");
	$.post(basePath+"rest/orguser/orgadd",
			data, 
			function(result,textStatus) {
		if (result.success == "true") {
			parent.currWin.Alert.tip(result.message);
			parent.currWin.tree_fresh();
			parent.currWin.f_reload();
			parent.currWin.f_closeDlg();
		} else {
			parent.currWin.Alert.tip(result.message);
			parent.currWin.closeDlgAndReload();
		}
	}, "json");
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
		if(isEmpty(data.org_name)){
			parent.Alert.tip("机构名称不能为空！");
			return false;
		}
		
		if(data.org_code.length>15){
			parent.Alert.tip("机构编号不能超过15个字符！");
			return false;
		}
		if(data.org_name.length>15){
			parent.Alert.tip("机构名称不能超过15个字符！");
			return false;
		}
		if(data.org_desc.length>60){
			parent.Alert.tip("机构描述不能超过60个字符！");
			return false;
		}
	}else{
		parent.Alert.tip("未获得表单数据！");
		return false;
	}
	return true;
}
/**
 * 判断字符串是否为空
 * @param param
 * @returns {Boolean}
 */
function isEmpty(param){
	if(param!=null&&$.trim(param)!=""){
		return false;
	}else{
		return true;
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