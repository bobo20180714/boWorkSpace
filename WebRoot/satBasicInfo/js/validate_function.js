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
		 var exp=/(^1[3|5|6|8][0-9]{8}\d$)/;
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
		 var exp=/(^1[3|5|6|8][0-9]{8}\d$)|(^[0-9]{7,8}$)|(^[0-9]{3,4}\-[0-9]{7,8}$)/;
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
	 
	 $.validator.addMethod("numAccuracy",function(v,e){
//		 var exp=  /^([1-9]{1}\d{0,4}|0)([\.]\d{1,8})$/;
		 var exp1 =  /^([1-9]{1}\d{0,4}||0)$/;
		 var exp2 =  /^(([1-9]{1}\d{0,4}||0)\.\d{1,8})$/;
		 if(this.optional(e)){
			 return this.optional(e);
		 }else{
			 return exp1.test(v) || exp2.test(v);
		 }
	 },"该字段整数位长度不能超过5,小数位长度不能超过8");
	 

	 
	 $.validator.addMethod("numAcc",function(v,e){
		 var exp1 =  /^([1-9]{1}\d{0,7}||0)$/;
		 var exp2 =  /^(([1-9]{1}\d{0,7}||0)\.\d{1,8})$/;
//		 var exp=  /^([1-9]{1}\d{0,7}|0)([\.]\d{1,8})$/;
		 if(this.optional(e)){
			 return this.optional(e);
		 }else{
			 return exp1.test(v) || exp2.test(v);
		 }
	 },"该字段整数位长度不能超过8,小数位长度不能超过8");	
	 
	 $.validator.addMethod("satCycle",function(v,e){
		 var exp=  /^([1-9]{1}\d{0,9}|0)$/;
		 if(this.optional(e)){
			 return this.optional(e);
		 }else{
			 return exp.test(v);
		 }
	 },"请输入长度小于10的正整数");
 
	  //jquery end
});