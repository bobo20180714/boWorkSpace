var formData = null;
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
	formData = $("#form2").ligerForm({
		width:360,
    	labelAlign:'center',
        inputWidth: 200, 
        labelWidth: 100, 
        space: 20,
        validate:true,//验证
        fields: [
			{ display: "单机编号", name: "stand_alone_code",id:"stand_alone_code",type: "text",newline: true,readonly:true},
			{ display: "单机名称", name: "stand_alone_name",id:"stand_alone_name",type: "text",newline: true,validate:{required:true,maxlength:30 }}
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
	var stand_alone_id = getParam("stand_alone_id");
	
	$.post(
			basePath+"rest/aloneinfo/standaloneinfobyid",
			{
				stand_alone_id:stand_alone_id
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
	data.stand_alone_id=getParam("stand_alone_id");
	data.sub_system_id=getParam("sub_system_id");
	$.ajax({
		url:basePath+"rest/aloneinfo/standaloneinfoupdate",
		data:data,
		async:false,
		success:function(rsData) {
			var result = eval("("+rsData+")");
			if (result.success == "true") {
				parent.currWin.tree_fresh();
				parent.currWin.f_reload();
				parent.currWin.Alert.tip("修改信息成功!");
				parent.currWin.f_closeDlg();
			} else {
				parent.currWin.Alert.tip(result.message);
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
function toValidForm() {
	var vboolean = $("#form2").valid();
	if (vboolean) {
		return true;
	} else {
		return false;
	}
}


