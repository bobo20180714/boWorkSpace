//表单对象
var floadForm = null;

$(function(){
	//初始化基本信息form
	initFloadForm();
	//表单赋值
	setFormData();
});

//初始化基本信息form
function initFloadForm(){
	floadForm = $("#floadForm").ligerForm({
        inputWidth: 140, 
        labelWidth: 80, 
        labelAlign: 'right',
        space: 0,
        validate:true,
        fields: [
             { display: "文件名称", name: "page_name",id:"page_name",
            	 type: "text", newline: true,validate:{required:true,maxlength:100}
             },
             { display: "打开方式", name: "open_mode",id:"open_mode",
            	 value:"2",
            	 type: "select", newline: false,validate:{required:true},
            	 data:[{
            		 id:"1",text:"新窗口"
            	 },{
            		 id:"2",text:"新选项卡" 
            	 }]
             },
             { display: "文件路径", name: "page_url",id:"page_url",width:365,
            	 type: "textarea", newline: true,validate:{required:true,maxlength:500}
             }
        ]
	});
	
	$("#page_url")[0].style.height = "108px";
}

function setFormData(){
	$.ajax({  
        url: basePath+'rest/SatRelateFile/queryUserFile',  
        async:false,
        data: {
      	  pkId:pkId
     	  },  
        success: function (ret) {  
      	  var rsData = eval('('+ret+')');
      	  floadForm.setData(rsData);
        }
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
	          url: basePath+'rest/SatRelateFile/updateUserFile',  
	          async:false,
	          data: {
	        	  page_name:info.page_name,
	        	  page_url:info.page_url,
	        	  open_mode:info.open_mode,
	        	  pkId:pkId
	       	  },  
	          success: function (ret) {  
	        	  var rsData = eval('('+ret+')');
	        	  if(rsData.success == "true"){
	        		  rsObj.success = "true";
	        			rsObj.data = {
        					name:info.page_name,
        					page_url:info.page_url,
        					open_mode:info.open_mode
	        			}
	        	  }
	          }
	     });
	}
	return rsObj;
}
