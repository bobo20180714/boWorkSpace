var formData = null; 
$(function (){ 
	 //表单
	 formData = $("#form1").ligerForm({
			labelAlign:'right',
	        inputWidth: 207, 
	        labelWidth: 70, 
	        space: 20,
	        validate:true,
	        fields: [
				{ display: "响应意见", name: "response",id:"response",
				    type: "textarea" ,validate:{required:false ,maxlength:40}
	          	}
	        ]
	  });
});