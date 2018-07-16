//表单对象
var paramForm = null;
//返回值类型
var returnTypeArr = [{id:'10',text:'数据列表'},{id:'11',text:'整型'},{id:'12',text:'浮点型'},{id:'13',text:'字符串'}];
$(function(){
	paramForm = $("#updateParam").ligerForm({
		labelAlign:'center',
        inputWidth: 200, 
        labelWidth: 100, 
        space: 20,
        validate:true,
        fields: [
             { display: "参数名称", name: "paramName",id:"paramName",
            	 type: "text", newline: false,validate:{required:true,maxlength:50}
             },
			{ display: "参数类型", name: "paramType",id:"paramType",
            	 data:returnTypeArr,initValue:"13",cancelable:false,
				type: "select", newline: true,validate:{required:true}
			},
			{ display: "参数说明", name: "paramContent",id:"paramContent",
				type: "textarea", newline: true,validate:{required:false,maxlength:50}
			}
        ]
	});
	getParamData();
});

function getParamData(){
	$.ajax({
		url:basePath+"rest/functionManage/getFunctionParam",
		data:{
			fieldId:fieldId
		},
		async:false,
		success:function(rsData){
			var jsobj = eval('('+rsData+')');
			paramForm.setData(jsobj);
		}
	});
}

function submitForms() {
	var flag = false;
	var data = paramForm.getData();
	data.fieldId = fieldId;
	if(toValidForm(data)){
		
		/*//判断编号是否重复
		if(judgeCode(data.code,null)){
			win.Alert.tip("地面站编号已经存在!");
			return;
		}
		//判断sid是否重复
		if(judgeSid(data.device_sid,null)){
			win.Alert.tip("地面站识别码已经存在!");
			return;
		}*/
		$.ajax({
			url:basePath+"rest/functionManage/updateParam",
			data:data,
			async:false,
			success:function(rsData){
				var jsobj = eval('('+rsData+')');
				if(jsobj.success=='true'){
					flag = true;
				}
			}
		});
	}
	return flag;
}

/**
 * 判断编号是否已经存在
 */
function judgeCode(code,deviceId){
	var flag = false;
	$.ajax({
		url:basePath+"rest/deviceInfo/judgeCode",
		data:{
			code:code,
			deviceId:deviceId
		},
		async:false,
		success:function(rsData){
			var jsobj = eval('('+rsData+')');
			if(jsobj.success=='true'){
				//已经存在
				flag = true;
			}
		}
	});
	return flag;
}

/**
 * 判断sid是否已经存在
 */
function judgeSid(device_sid,deviceId){
	var flag = false;
	$.ajax({
		url:basePath+"rest/deviceInfo/judgeSid",
		data:{
			device_sid:device_sid,
			deviceId:deviceId
		},
		async:false,
		success:function(rsData){
			var jsobj = eval('('+rsData+')');
			if(jsobj.success=='true'){
				//已经存在
				flag = true;
			}
		}
	});
	return flag;
}


//验证
function toValidForm(data) {
	var vboolean = paramForm.valid();
	if (vboolean) {
		return true;
	} else {
		return false;
	}
}
