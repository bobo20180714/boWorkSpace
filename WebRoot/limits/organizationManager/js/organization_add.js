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
			{ display: "上级ID", name:"parent_id",id:"parent_id",type: "hidden" 
			}
			,
			{ display: "公司ID", name:"company_id",id:"company_id",type: "hidden" 
			}
			,
  			{ display: "机构编号", name: "org_code",id:"org_code",
	  			  newline: true, type: "text" ,validate:{required:true,identifierNO:true,maxlength:50},onblur:checkCode
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
    $("#parent_id").val(pk_id);
    $("#company_id").val(company_id);
}); 

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
			url:basePath+"rest/orgStrcture/addStructure",
			data:{
				parentId:data.parent_id,
				orgCode:data.org_code,
				orgName:data.org_name,
				orgAdress:data.org_adress,
				registCorporation:data.regist_corporation,
				orgLinkNo:data.org_link_no,
				companyId:data.company_id,
				state:"1"
			},
			success:function(data){
				var jsobj=eval('('+data+')');
				if(jsobj.success=='true'){
					win.Alert.tip("新建信息成功!");
					win.closeAndRefresh("createWindow");
					win.getTreeData();//刷新左边树结构
				}else{
					win.Alert.tip("新建信息失败!");
				}
			}
		});
	}
}

function checkCode(){
	var org_code = liger.get("org_code").getValue().replace(/\s+/g, "");
	$.ajax({
		url:basePath+"rest/orgStrcture/checkCode",
		data:{
			org_code:org_code
		},
		success:function(data){
			var jsobj=eval('('+data+')');
			if(jsobj.success=='false'){
				win.Alert.tip("组织机构信息编号重复！");
				liger.get("org_code").setValue("");
			}
		}
	});
}