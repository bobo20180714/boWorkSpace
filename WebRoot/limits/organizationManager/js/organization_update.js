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
			{ display: "pk_id", name: "pk_id",id:"pk_id",type: "hidden"
			}
			,
			{ display: "机构编号", name: "org_code",id:"org_code",
	  			  newline: true, type: "text" ,readonly:true
	        }
  			,
  			{ display: "机构名称", name: "org_name",id:"org_name",
	  			  newline: true, type: "text" ,validate:{required:true,maxlength:50}
	        }
  			,
  			{ display: "法人代表", name: "regist_corporation",id:"regist_corporation",
	  			  newline: true, type: "text" ,validate:{required:false,maxlength:20}
	        }
  			,
  			{ display: "机构地址", name: "org_adress",id:"org_adress",
	  			  newline: true, type: "text" ,validate:{required:false,maxlength:100}
	        }
			,
  			{ display: "联系电话", name: "org_link_no",id:"org_link_no",
	  			  newline: true, type: "text" ,validate:{required:false,telephone:true}
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
//保存数据
function submitForm(){
	if(toValidForm()){
		var data = formData.getData();
		$.ajax({
			url:basePath+"rest/orgStrcture/updateStructure",
			data:{
				pkId:data.pk_id,
				parentId:data.parent_id,
				orgCode:data.org_code,
				orgName:data.org_name,
				orgAdress:data.org_adress,
				registCorporation:data.regist_corporation,
				orgLinkNo:data.org_link_no,
				companyId:data.company_id
			},
			success:function(data){
				var jsobj=eval('('+data+')');
				if(jsobj.success=='true'){
					win.Alert.tip("修改信息成功!");
					win.closeAndRefresh("createWindow");
					win.getTreeData();//刷新左边树结构;
				}else{
					win.Alert.tip("修改信息失败!");
					win.closeAndRefresh("createWindow");
				}
			}
		});
	}
}