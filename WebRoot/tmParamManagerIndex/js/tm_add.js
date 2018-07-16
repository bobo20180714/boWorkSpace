var dataType = [ 
                 { text: '浮点型', id: '0' },
                 { text: '整型', id: '2' },
                 { text: '字符型', id: '3' }];
var formData = null;
var sat_id = "";
var groupicon;
$(function (){
	initForm();
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
	formData = $("#form1").ligerForm({
		width:360,
    	labelAlign:'center',
        inputWidth: 200, 
        labelWidth: 100, 
        space: 20,//间隔宽度
        validate:true,//验证
        fields: [
//             { display: "参数序号", name: "tm_param_num",id:"tm_param_num",type: "text",newline: true,validate:{required:true,maxlength:30}},
			{ display: "参数编号", name: "tm_param_code",id:"tm_param_code",type: "text",newline: true,validate:{required:true,maxlength:30}},
			{ display: "参数名称", name: "tm_param_name",id:"tm_param_name",type: "text",newline: true,validate:{required:true,maxlength:30}},
			{ display: "参数类型", name: "tm_param_type",id:"tm_param_type",type: "select",data:dataType,newline: true,value:0}
        ]
    }); 
	
}

//页面提交
function submitForm(){
	var data = formData.getData();
	if(toValidForm()){
		//判断代号是否重复
		if(judgeTmCode(data.tm_param_code)){
			parent.Alert.tip("参数编号已经存在！");
			return false;
		}
		//判断序号是否重复
		if(judgeTmNum(data.tm_param_num)){
			parent.Alert.tip("参数序号已经存在！");
			return false;
		}
		data.sat_id = satId;
		$.ajax({
			url:basePath+"rest/tmparams/add",
			data:data,
			async:false,
			success:function(rsData) {
				var result = eval("("+rsData+")");
				if (result.success == "true") {
					parent.Alert.tip("新增信息成功!");
					parent.currWin.f_reload();
					parent.currWin.f_closeDlg();
				} else {
					parent.Alert.tip("新增信息失败!");
				}
			}
		});
	}
}


/**
 * 判断参数编号是否重复
 * @param tmCode
 */
function judgeTmCode(tmCode){
	var flag = false;
	$.ajax({
		url:basePath+"rest/tmparams/judgeTmCode",
		data:{
			satId:satId,
			tmCode:tmCode
		},
		async:false,
		success:function(rsData) {
			var result = eval("("+rsData+")");
			if (result.success == "true") {
				flag = true;
			}
		}
	});
	return flag;
}

/**
 *	判断序号是否重复
 * @param tmNum
 */
function judgeTmNum(tmNum){
	var flag = false;
	$.ajax({
		url:basePath+"rest/tmparams/judgeTmNum",
		data:{
			satId:satId,
			tmNum:tmNum
		},
		async:false,
		success:function(rsData) {
			var result = eval("("+rsData+")");
			if (result.success == "true") {
				flag = true;
			}
		}
	});
	return flag;
}

/**
 * 表单校验
 * @param data 表单数据
 * @returns {为true则表单校验通过，false为不通过。}
 */
function check(data){
	if(data!=null){
		if(data.tm_param_bdh.length>15){
			$.ligerDialog.warn("波道号长度不能超过15！");
			return false;
		}
		
		if((data.tm_param_name).length>60){
			$.ligerDialog.warn("参数名称长度不能超过60！");
			return false;
		}
	}
	return true;
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


