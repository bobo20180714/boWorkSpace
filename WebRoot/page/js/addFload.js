//表单对象
var floadForm = null;

$(function(){
	//初始化基本信息form
	initFloadForm();
});

//初始化基本信息form
function initFloadForm(){
	floadForm = $("#floadForm").ligerForm({
        inputWidth: 170, 
        labelWidth: 90, 
        space: 20,
        validate:true,
        fields: [
             { display: "文件夹名称", name: "name",id:"name",
            	 type: "text", newline: true,validate:{required:true,maxlength:50}
             }
        ]
	});
}
/**
 * 提交表单
 */
function submitForms(){
	var rsObj = {
			success:"false",
			data:{
				
			}
	};
	var vboolean = floadForm.valid();
	if (vboolean) {
		var info = floadForm.getData();
		$.ajax({  
	          url: basePath+'rest/project/addFload',  
	          async:false,
	          data: {
	        	  name:info.name,
	        	  owner:ownerId,
	        	  isleaf:0
	       	  },  
	          success: function (ret) {  
	        	  var rsData = eval('('+ret+')');
	        	  if(rsData.success == "true"){
	        		  rsObj.success = "true";
	        			rsObj.data = {
        					pk_id:rsData.data,
        					name:info.name,
        					owner:ownerId,
        		        	isleaf:0
	        			}
	        	  }
	          }
	     });
	}
	return rsObj;
}
