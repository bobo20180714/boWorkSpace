var formData = null;
var win=parent.currWin;
//类型（枚举：0 固定站，1地面移动站，2测控船）
var typeDataArr = [{id:'0',text:'固定站'},{id:'1',text:'地面移动站'},{id:'2',text:'测控船'}];
$(function(){
	formData = $("#form1").ligerForm({
		labelAlign:'center',
        inputWidth: 200, 
        labelWidth: 100, 
        space: 20,
        validate:true,
        fields: [
             {name: "pk_id",id:"pk_id",type: "hidden"},
             { display: "编号", name: "code",id:"code",
            	 type: "text", newline: true,validate:{required:true,identifierNO:true,maxlength:30}
             },
            { display: "名称", name: "name",id:"name",
			  type: "text", newline: true,validate:{required:true,maxlength:30}
			},
			{ display: "地面站识别码", name: "device_sid",id:"device_sid",
				  type: "text", newline: true,validate:{required:true,number:true,maxlength:8}
			},
			{ display: "地面站类型", name: "parent_id",id:"parent_id",
				data:typeDataArr,cancelable:false,
				type: "select", newline: true,validate:{required:true}
			},
			{ display: "描述", name: "remark",id:"remark",
				type: "textarea", newline: true,validate:{required:false,maxlength:25}
			}
        ]
	});
	
	getDeviceData();
});

function getDeviceData(){
	$.ajax({
		url:basePath+"rest/deviceInfo/queryDeviceInfo",
		data:{
			deviceId:device_id
		},
		async:false,
		success:function(rsData){
			var jsobj = eval('('+rsData+')');
			formData.setData(jsobj);
		}
	});
}

function submitForm() {
	var data = formData.getData();
	if(toValidForm(data)){
		
		//判断编号是否重复
		if(judgeCode(data.code,data.pk_id)){
			win.Alert.tip("地面站编号已经存在!");
			return;
		}
		//判断sid是否重复
		if(judgeSid(data.device_sid,data.pk_id)){
			win.Alert.tip("地面站识别码已经存在!");
			return;
		}
		
		$.ajax({
			url:basePath+"rest/deviceInfo/alterDevice",
			data:data,
			async:false,
			success:function(rsData){
				var jsobj = eval('('+rsData+')');
				if(jsobj.success=='true'){
					win.Alert.tip("修改信息成功!");
					win.closeDlgAndReload();
				}else{
					win.Alert.tip("修改信息失败!");
				}
			}
		});
	}
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
	var vboolean = $("#form1").valid();
	if (vboolean) {
		return true;
	} else {
		if(data.parent_id == ""){
			win.Alert.tip("请选择地面站类型!");
			return false;
		}
		return false;
	}
}
