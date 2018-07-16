var formData = null;

$(function (){
	initForm();
});

/**
 * 加载渲染jsp页面
 */
function initForm(){
	formData = $("#form1").ligerForm({
		width:360,
    	labelAlign:'center',
        inputWidth: 200, 
        labelWidth: 100, 
        space: 10,//间隔宽度
        validate:true,//验证
        fields: [
	         { display: "信息名称", name: "jsjg_name",id:"jsjg_name",type: "text",
	        	 newline: true,validate:{required:true,maxlength:20}},
            { display: "信息编号", name: "jsjg_code",id:"jsjg_code",
        		 type: "text",newline: true,validate:{required:true,maxlength:8}},
    		 { display: "备注", name: "jsjg_desc",id:"jsjg_desc",
			 type: "textarea",newline: true,validate:{maxlength:50}}
			/*{ display: "数据时间类型", name: "is_time_range",id:"is_time_range",
    			 type: "select",data:[{id:0,text:"点时间"},{id:1,text:"段时间"}],
    			 cancelable:false,
    			 value:0,newline: true,validate:{required:true}},
			{ display: "起始字段名", name: "start_time",id:"start_time",
				 type: "text",newline: true,validate:{maxlength:20}},
			{ display: "截止字段名", name: "end_time",id:"end_time",
				 type: "text",newline: true,validate:{maxlength:20}}*/
        ]
    }); 
	
}

//页面提交
function submitForm(){
	var rsObj = {
			success:false,
			message:'保存失败！'
	};
	var data = formData.getData();
	if(toValidForm()){
		//数据提交
		//判断jsjg_code是否符合要求，英文字母开头
		var exp= /^[A-Z][A-Z0-9_]*$/;
		if(!exp.test(data.jsjg_code)){
			return {
				success:false,
				message:'信息编号只允许输入大写字母、数字以及下划线,且以字母开头！'
			};
		}
		/*if(data.start_time != "" && !exp.test(data.start_time)){
			return {
				success:false,
				message:'起始字段名只允许输入字母、数字以及下划线,且以字母开头！'
			};
		}
		if(data.end_time != "" && !exp.test(data.end_time)){
			return {
				success:false,
				message:'截止字段名只允许输入字母、数字以及下划线,且以字母开头！'
			};
		}
		data.sys_resource_id = sat_id;*/
		data.is_time_range = "0";
		data.start_time = "time";
		$.ajax({
			url:basePath+"rest/orbitrelated/addRelated",
			data:data,
			async:false,
			success:function(data){
				var rsData = eval('('+data+')');
				if(rsData.success == "true"){
					rsObj = {
							success:true,
							message:"保存成功！"
					};
				}else{
					rsObj = {
							success:false,
							message:rsData.message
					};
				}
			}
		});
	}else{
		return null;
	}
	
	return rsObj;
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
