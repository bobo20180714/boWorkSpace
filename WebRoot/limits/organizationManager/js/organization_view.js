var formData=null;
var win=parent.currWin;
$(function (){
	
	//创建表单结构 
    formData=$("#tableForm").ligerForm({
    	labelAlign:'right',
    	inputWidth: 160, 
        labelWidth: 80, 
        space: 40,
        validate:true,
        fields: [
			{ display: "机构编号", name: "org_code",id:"org_code",
	  			  newline: true, type: "text" ,readonly:true
	        }
  			,
  			{ display: "机构名称", name: "org_name",id:"org_name",
	  			  newline: true, type: "text" ,readonly:true
	        }
  			,
  			{ display: "法人代表", name: "regist_corporation",id:"regist_corporation",
	  			  newline: true, type: "text" ,readonly:true
	        }
  			,
  			{ display: "机构地址", name: "org_adress",id:"org_adress",
	  			  newline: true, type: "text" ,readonly:true
	        }
			,
  			{ display: "联系电话", name: "org_link_no",id:"org_link_no",
	  			  newline: true, type: "text" ,readonly:true
	        }
        ]
    }); 
    setFormData();
}); 

//表单赋值
function setFormData(){
	var url=basePath+'rest/orgStrcture/viewStructure';
	$.ajax({
		url:url,
		data:{
			pk_id:pk_id
		},
		success:function(data){
			var jsobj=eval('('+data+')');
			formData.setData(jsobj);
		}
	});

}
//验证
function toValidForm() {
	var vboolean = $("#tableForm").valid();
	if (vboolean) {
		return true;
	} else {
		return false;
	}
}
