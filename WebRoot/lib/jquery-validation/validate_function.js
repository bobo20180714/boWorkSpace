/**
 * 扩展的验证方法
 */
$(function () {
	//jquery start
	 
	 $.validator.addMethod("identityCard",function(v,e){
		 var exp=/(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
			 if(this.optional(e)){
				 return this.optional(e);
			 }else{
				 return exp.test(v);
			 }
	 },"请输入正确格式的身份证号15位或18位");
	 
	 $.validator.addMethod("mobileNumber",function(v,e){
		 var exp=/(^1[3|5|7|6|8][0-9]{8}\d$)/;
		 if(this.optional(e)){
			 return this.optional(e);
		 }else{
			 return exp.test(v);
		 }
	 },"请输入正确的手机号码");

	 
	 $.validator.addMethod("tel",function(v,e){
		 var exp=/(^[0-9]{7,8}$)|(^[0-9]{3,4}\-[0-9]{7,8}$)/;
		 if(this.optional(e)){
			 return this.optional(e);
		 }else{
			 return exp.test(v);
		 }
	 },"请输入正确的座机号码");
		
	 
	 $.validator.addMethod("telephone",function(v,e){
		 var exp=/(^1[3|5|6|7|8][0-9]{8}\d$)|(^[0-9]{7,8}$)|(^[0-9]{3,4}\-[0-9]{7,8}$)/;
		 if(this.optional(e)){
			 return this.optional(e);
		 }else{
			 return exp.test(v);
		 }
	 },"请输入正确的座机号码或手机号码");
	 
	 $.validator.addMethod("price",function(v,e){
		 var exp= /^[0-9]+\.{0,1}[0-9]{0,2}$/;
		 if(this.optional(e)){
			 return this.optional(e);
		 }else{
			 return exp.test(v);
		 }
	 },"请输入正确的金额格式*.XX最多两位小数");
	 
	 $.validator.addMethod("justNumber",function(v,e){
		 var exp= /^[0-9]*[1-9][0-9]*$/;
		 if(this.optional(e)){
			 return this.optional(e);
		 }else{
			 return exp.test(v);
		 }
	 },"请输入正整数的数字");
	 
	 $.validator.addMethod("specialText",function(v,e){
		 var exp= /^[\u0391-\uFFE5\w]+$/;
		 if(this.optional(e)){
			 return this.optional(e);
		 }else{
			 return exp.test(v);
		 }
	 },"该字段只允许输入汉字、英文字母、数字以及下划线");
	 
	 $.validator.addMethod("cnText",function(v,e){
		 var exp= /^[\u4E00-\u9FA0\w]+$/;
		 if(this.optional(e)){
			 return this.optional(e);
		 }else{
			 return exp.test(v);
		 }
	 },"该字段不能输入汉字");
	 
	 $.validator.addMethod("identifierNO",function(v,e){
		 var exp= /^[a-zA-Z0-9_]+$/;
		 if(this.optional(e)){
			 return this.optional(e);
		 }else{
			 return exp.test(v);
		 }
	 },"该字段只允许输入英文字母、数字以及下划线");
	 
	 $.validator.addMethod("positivePrice",function(v,e){
		 var exp=  /^[+]?[0-9]+(\.+[0-9]+)*$/;
		 if(this.optional(e)){
			 return this.optional(e);
		 }else{
			 return exp.test(v);
		 }
	 },"请输入大于零的金额数字");
 
	  //jquery end
});

//获取字段验证规则
function validateInit(ruleId,form1){
    	$.ajax({
			type: "POST",
			url:basePath+"/rest/channel/service?subsystem=system&serviceid=validateRule",
			data:{
				ruleid:ruleId	 
			},
			success:function(data){
					var validateField = eval('('+data+')');
	        		// var validateField=[{field_name:"mobile",validate:{required:true,userName:true}}];
	        	 	// 字段规则
	        		for(var i=0;i<validateField.length;i++){
		        		var tempV=validateField[i];
		        		
		        		form1.setFieldValidate(tempV.field_name,eval('('+tempV.validate+')'),"1");
		        	}
			}
		});
}