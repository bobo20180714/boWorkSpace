var formData = null;

$(function (){
	initForm();
});

/**
 * 加载渲染jsp页面
 */
function initForm(){
	formData = $("#form1").ligerForm({
    	labelAlign:'center',
        inputWidth: 170, 
        labelWidth: 90, 
        space: 10,//间隔宽度
        validate:true,//验证
        fields: [
	         { display: "字段英文编号", name: "field_code",id:"field_code",type: "text",
	        	 newline: true,validate:{required:true,maxlength:20}},
        	 { display: "字段名称", name: "field_name",id:"field_name",type: "text",
        		 newline: false,validate:{required:true,maxlength:50}},
        	 { display: "字段类型", name: "field_type",id:"field_type",
        		 type: "select",newline: true,
        		 value:3,cancelable:false,onSelected:onSelecteds,
        		 data:[{id:0,text:"整型"},{id:1,text:"浮点型"},{id:3,text:"字符串"}/*,{id:5,text:"时间戳"}*/],
        		 validate:{required:true}},
        	 { display: "字段长度", name: "field_length",id:"field_length",type: "text",
        			 value:50,
        		 newline: false,validate:{required:true,maxlength:5}},
    		 { display: "字段精度", name: "fiel_dscale",id:"fiel_dscale",type: "text",
        			 value:0,
    			 newline: true,validate:{required:false,maxlength:5}},
			 { display: "排序序号", name: "field_order",id:"field_order",type: "text",
				 value:1,
				 newline: false,validate:{required:false,maxlength:3}},
			/*{ display: "是否为摘要信息", name: "is_display_flag",id:"is_display_flag",
        			 cancelable:false,
    			 type: "select",data:[{id:0,text:"否"},{id:1,text:"是"}],
    			 value:0,newline: true,validate:{required:true}},*/
			{ display: "字段说明", name: "field_comment",id:"field_comment",
					 width:440,
				 type: "textarea",newline: true,validate:{maxlength:100}}
        ]
    }); 
	
}

function onSelecteds(e){
	if(e == 3){
		liger.get('field_length').setValue("50");
	}else{
		liger.get('field_length').setValue("20");
	}
}

//页面提交
function submitForm(){
	var rsObj = {
			success:false,
			message:''
	};
	var data = formData.getData();
	if(toValidForm()){
		//数据提交
		var exp= /^[a-zA-Z][a-zA-Z0-9_]*$/;
		if(!exp.test(data.field_code)){
			return {
				success:false,
				message:'字段英文编号只允许输入字母、数字以及下划线,且以字母开头！'
			};
		}
		exp = /^[0-9]*$/;
		if(!exp.test(data.field_length)){
			return {
				success:false,
				message:'字段长度只能输入的数字！'
			};
		}
		if(data.fiel_dscale){
			exp = /^[0-9]*$/;
			if(!exp.test(data.fiel_dscale)){
				return {
					success:false,
					message:'字段精度只能输入的数字！'
				};
			}
		}
		if(data.field_type == "0" || data.field_type == "1"){
			if(data.field_length > 38){
				return {
					success:false,
					message:'整型、浮点类型的字段长度不能大于38！'
				};
			}
		}
		data.jsjg_id = jsjg_id;
		$.ajax({
			url:basePath+"rest/orbitrelated/addField",
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
