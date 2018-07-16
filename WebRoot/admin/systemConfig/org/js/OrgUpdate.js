var formdata = null;
var groupicon;
$(function (){
	initForm();
	getData();
});

function initForm(){
	formData = $("#form2").ligerForm({
		width:360,
    	labelAlign:'center',
        inputWidth: 200, 
        labelWidth: 100, 
        space: 20,
        validate:true,//验证
        title:'[修改]机构节点信息',
        fields: [
			{ display: "机构编号", name: "org_code",id:"org_code",type: "text",newline: true,validate:{maxlength:15},readonly:true},
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

function getData(){
	var org_id = getParam("id");
	$.post(
			basePath+"rest/orguser/findOrgById",
			{
				org_id:org_id
			},
			function(data)
			{
				var row = data;
				formData.setData(row);
			},"json");
}

//页面提交
function submitForm(){
	var data = formData.getData();
	if(toValidForm()){
	//数据提交
	data.org_id=getParam("id");
	$.post(basePath+"rest/orguser/orgupdate",data, 
			function(result,textStatus) {
		if (result.success == "true") {
			parent.currWin.Alert.tip("修改成功!");
			parent.currWin.tree.reload();
			parent.currWin.f_closeDlg();
		} else {
			parent.currWin.Alert.tip("修改失败!");
			parent.currWin.closeDlgAndReload();
		}
	}, "json");
	}
}

//验证
function toValidForm() {
	var vboolean = $("#form2").valid();
	if (vboolean) {
		return true;
	} else {
		return false;
	}
}

