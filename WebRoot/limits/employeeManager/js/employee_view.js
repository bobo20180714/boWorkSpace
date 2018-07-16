var formData = null;

$(function(){
	var groupicon = basePath+"lib/ligerUI/skins/icons/communication.gif";
	formData = $("#form1").ligerForm({
		labelAlign:'right',
        inputWidth: 170, 
        labelWidth: 100, 
        space: 10,
        validate:true,
        fields: [
			{ display: "照片", name: "staff_photo",id:"staff_photo",
			  type: "hidden"
			},
            { display: "员工工号", name: "staff_code",
			  type: "text", newline: false,group:"基础信息",groupicon:groupicon,readonly:true
			}
			,
			{ display: "员工姓名", name: "staff_name",id:"staff_name",
			    newline: true, type: "text" ,readonly:true
          	}
        	,
        	{ display: "员工性别", name: "sex_name",type: "text" ,
        		newline: true,readonly:true
            }
        	,
        	{ display: "手机号码", name: "mobel_no",id:"mobel_no",
    			  type: "text",validate:{required:true},newline: true,readonly:true
    		}
            ,
          	{ display: "QQ", name: "staff_qq",id:"staff_qq",
			  type: "text",validate:{required:false}
        	, newline: true,group:"详细信息",groupicon:groupicon,readonly:true
			}
          	,
            { display: "身份证号", name: "id_card",id:"id_card",
        		type: "text",validate:{required:false},newline: false,readonly:true
          	}
        	,
        	{ display: "出生日期", name: "birthday",id:"birthday",
      		  type: "date",validate:{required:false}, newline: true,readonly:true
        	}
        	,
        	{ display: "邮箱", name: "email",id:"email",
  			  type: "text",validate:{required:false}
          	, newline: false,readonly:true
  			}
          	,
          	{ display: "家庭住址", name: "family_adress",id:"family_adress",
  			  type: "text",validate:{required:false},width:450
          	, newline: false,readonly:true
  			}
          	,
            { display: "岗位", name: "job_name",type: "text",
        		newline: true,readonly:true
            }
        	,
			{ display: "所属机构", name: "org_name",readonly:true,
			  type: "text",newline: false
			}
        	,
			{ display: "技术水平", name: "skill_name",
			  type: "text",newline: true,readonly:true
			}
        	,
			{ display: "入职日期", name: "arrive_date",id:"arrive_date",
      		  type: "date",validate:{required:false}
        	, newline: false,readonly:true
			}
        	,
			{ display: "备注", name: "remark",id:"remark",type: "textarea",
        		validate:{required:false},width:450,height:150
        	, newline: true,readonly:true
			}
        ]
	});
	 //表单赋值
    setFormData();
});

function setFormData(){
	$.ajax({
		url:basePath+'rest/StaffInfoAction/getStaffByCode',
		data:{staffCode:parent.currWin.editSelectId},
		async:false,
		success:function(data){
			var jsobj = eval('('+data+')');
			if (jsobj.staff_photo == "") {
				$("#img").attr("src","js/default.jpg");
			} else {
				$("#img").attr("src",basePath+jsobj.staff_photo);
			}
			formData.setData(jsobj);
//			obtainName("岗位","job_code",jsobj.job_code);
//			obtainName("技术水平","skill_level",jsobj.skill_level);
		}
	});
}

function obtainName(type_name,param_code,param){
	$.ajax({
		url:basePath+'rest/GetParameterAction/getParameter',
		data:{
			typeName:type_name,
			paramCode:param
		},
		async:false,
		success:function(data){
			var jsobj=eval('('+data+')');
			if (jsobj[0] != null) {
				liger.get(param_code).setText(jsobj[0].text);
			}
		}
	});
}