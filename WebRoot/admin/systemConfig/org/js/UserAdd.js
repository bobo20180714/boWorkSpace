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
			{ display: "登录名称", name: "login_name",id:"login_name",type: "text",newline: true,
				validate:{required:true,identifierNO:true,maxlength:25},abelAlign:"left",onFocus:function(item){}},
			{ display: "用户名称", name: "user_name",id:"user_name",type: "text",newline: true,validate:{required:true,maxlength:16}},
			{ display: "联系方式", name: "telephone",id:"telephone",type: "text",newline: true,validate:{telephone:true}},
//			{ display: "用户单位", name: "danwei",id:"danwei",type: "text",newline: true,validate:{maxlength:50}},
//			{ display: "用户部门", name: "bumen",id:"bumen",type: "text",newline: true,validate:{maxlength:50}},
			{ display: "用户职位", name: "zw",id:"zw",type: "text",newline: true,validate:{maxlength:50}},
			{ display: "截止日期", name: "end_time",id:"end_time",type: "date",newline: true,validate:{required:true}}
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
	$.post(basePath+"rest/orguser/userinfoadd",
			data,function(result,textStatus) {
		if (result.success == "true") {
			parent.currWin.Alert.tip(result.message);
			parent.currWin.f_reload();
			parent.currWin.f_closeDlg();
		} else {
			parent.Alert.tip(result.message);
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
	var integerReg = /^(13[0-9]|14[57]|15[0-3,5-9]|18[0,2,5-9]|17[6,7,8])\d{8}$/; 
	if(data!=null){
//		if(isEmpty(data.login_name)){
//			parent.Alert.tip("用户登录名称不能为空！");
//			return false;
//		}
//		if(isHanZi(data.login_name)){
//			parent.Alert.tip("用户登录名称只能输入字母、数字、下划线！");
//			return false;
//		}
//		if(isReName(data.login_name)){
//			parent.Alert.tip("用户登录名称不能重复，请修改后重新提交！");
//			return false;
//		}
//		if(isEmpty(data.user_name)){
//			parent.Alert.tip("用户名称不能为空！");
//			return false;
//		}
//		if(data.login_name.length>25){
//			parent.Alert.tip("用户登录名称不能超过25个字符！");
//			return false;
//		}
//		if(data.user_name.length>16){
//			parent.Alert.tip("用户名称不能超过16个字符！");
//			return false;
//		}
//		if(!(data.telephone=="") && !integerReg.test(data.telephone)){
//			parent.Alert.tip("联系方式不正确！");
//			return false;
//		}
//		if(data.danwei.length>50){
//			parent.Alert.tip("用户单位不能超过50个字符！");
//			return false;
//		}
//		if(data.bumen.length>50){
//			parent.Alert.tip("用户部门不能超过50个字符！");
//			return false;
//		}
//		if(data.zw.length>50){
//			parent.Alert.tip("用户职位不能超过50个字符！");
//			return false;
//		}
		if(dateVer(data.end_time)){
			parent.Alert.tip("截止时间不能小于等于当前时间");
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

function isReName(name){
	var flag = false;
	$.ajax({url:basePath+"rest/orguser/findUserByLoginName",
		dataType:"json",
		data:{"login_name":name,org_id:getParam("id")},
		type:"POST",
		async:false,
		success:function (data){
			if(data.success=="true"){
				flag = true;
			}
		}
	});
	return flag;
}

/**
 * 判断输入是否为汉字
 * @param name
 * @returns {Boolean}
 */
function isHanZi(name){
	var flag = false;
	if(/^[A-Za-z0-9_]+$/.test(name)){
		flag=flag;
	}
	return true;
}

/**
 * 判断截止日期是否小于当前日期
 * @param param
 * @returns {Boolean}
 */
function dateVer(date){
var now=new Date();
var nowDate =$.trim(now.getFullYear()+'-'+(now.getMonth()+1<10?'0'+(now.getMonth()+1):(now.getMonth()+1))+'-'+(now.getDate()<10?'0'+now.getDate():now.getDate()))
	if(date!="" && date<nowDate){
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