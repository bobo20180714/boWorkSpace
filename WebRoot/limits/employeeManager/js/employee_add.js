var formData = null;
var win = parent.currWin;
var xdlg = null;
$(function(){
	var sex_data = null;
	var job_code_data = null;
	var skill_level_data = null;
	$.ajax({
		url:basePath+'rest/GetParameterAction/byTypeGetParameter',
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		data:{
			typeName:"性别"
		},
		async:false,
		success:function(data){
			var jsobj=eval('('+data+')');
			sex_data = jsobj;
		}
	});
	
	$.ajax({
		url:basePath+'rest/GetParameterAction/byTypeGetParameter',
		data:{
			typeName:"岗位"
		},
		async:false,
		success:function(data){
			var jsobj=eval('('+data+')');
			job_code_data = jsobj;
		}
	});
	
	$.ajax({
		url:basePath+'rest/GetParameterAction/byTypeGetParameter',
		data:{
			typeName:"技术水平"
		},
		async:false,
		success:function(data){
			var jsobj=eval('('+data+')');
			skill_level_data = jsobj;
		}
	});
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
            { display: "员工工号", name: "staff_code",id:"staff_code",validate:{required:true,identifierNO:true},
			  type: "text", newline: false,onblur:IsExistStaffCode,group:"基础信息",groupicon:groupicon
			}
			,
			{ display: "员工姓名", name: "staff_name",id:"staff_name",
			    newline: true, type: "text" ,validate:{required:true,maxlength:25}
          	}
        	,
        	{ display: "员工性别", name: "sex",id:"sex",type: "radiolist" ,
        		validate:{required:true}, newline: true,emptyText:null,
        		valueField: 'id',textField: 'text',data:sex_data
            }
        	,
        	{ display: "手机号码", name: "mobel_no",id:"mobel_no",
    			  type: "text",validate:{required:true,mobileNumber:true},newline: true
    		}
            ,
          	{ display: "QQ", name: "staff_qq",id:"staff_qq",
			  type: "text",validate:{required:false,number:true}
        	, newline: true,group:"详细信息",groupicon:groupicon
			}
          	,
            { display: "身份证号", name: "id_card",id:"id_card",onblur:identityCardBlur,
        		type: "text",validate:{required:false,identityCard:true},newline: false
          	}
        	,
        	{ display: "出生日期", name: "birthday",id:"birthday",
      		  type: "date",validate:{required:false}, newline: true
        	}
        	,
        	{ display: "邮箱", name: "email",id:"email",
  			  type: "text",validate:{required:false,email:true}
          	, newline: false
  			}
          	,
          	{ display: "家庭住址", name: "family_adress",id:"family_adress",
  			  type: "text",validate:{required:false,maxlength:100},width:450
          	, newline: false
  			}
          	,
            { display: "岗位", name: "job_code",id:"job_code",type: "select",
        		validate:{required:false},newline: true,emptyText:null,
        		valueField: 'id',textField: 'text',data:job_code_data
            }
        	,
			{ display: "所属机构", name: "org_text",id:"org_text",
			  type: "select",validate:{required:false}, newline: false,
			 selectBoxWidth:'auto',
			  tree : {
				url: basePath+'rest/organization/getStrctureList',
				textFieldName:'text',
				idFieldName : 'id',
				parentIDFieldName : 'pid',
				treeLeafOnly:false,
				nodeWidth:120, 
				isExpand:true,
				checkbox:false,
				single:true,
				onselect:onSelectOrg
			  }
			}
        	,
        	{ display: "", name: "org_id",id:"org_id",
        		type: "hidden"
        	}
        	,
			{ display: "技术水平", name: "skill_level",id:"skill_level",
			  type: "select",validate:{required:false}, newline: true,emptyText:null,
      		  valueField: 'id',textField: 'text',data:skill_level_data
			}
        	,
			{ display: "入职日期", name: "arrive_date",id:"arrive_date",
      		  type: "date",validate:{required:false}
        	, newline: false
			}
        	,
			{ display: "备注", name: "remark",id:"remark",type: "textarea",
        		validate:{required:false,maxlength:400},width:450,height:150
        	, newline: true
			}
        ]
	});
	liger.get("sex").setValue(sex_data[0].id);
});

function onSelectOrg(e){
	liger.get("org_text").setText(e.data.text);
	$("#org_id").val(e.data.id);
}

//光标离开时获得出生日期
function identityCardBlur(){

   var iIdNo = liger.get("id_card").getValue();
   var isiIdNo = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(iIdNo);

   if(isiIdNo){
       var tmpStr;
       if (iIdNo.length == 15) {
			tmpStr = iIdNo.substring(6, 14);
			tmpStr = tmpStr.substring(0, 4) + "-" + tmpStr.substring(4, 6) + "-" + tmpStr.substring(6)
		}
		else {
			tmpStr = iIdNo.substring(6, 14);
			tmpStr = tmpStr.substring(0, 4) + "-" + tmpStr.substring(4, 6) + "-" + tmpStr.substring(6)
       }
       
       liger.get("birthday").setValue(tmpStr);
   }
}
function submitForm(){
	if(toValidForm()){
		//数据提交
		var data = formData.getData();
		
		var date = (data.birthday).replace(/-/g,"");
		if (date != "") {
			if(!checkDate(date)){
				return;
			}
		}
		$.ajax({
			url:basePath+"rest/StaffInfoAction/addStaff",
			data:{
				staffCode:data.staff_code,
				staffName:data.staff_name,
				sex:data.sex,
				staffQQ:data.staff_qq,
				mobelNo:data.mobel_no,
				idCard:data.id_card,
				birthday:data.birthday,
				staffPhoto:data.staff_photo,
				jobCode:data.job_code,
				orgId:data.org_id,
				skillLevel:data.skill_level,
				arriveDate:data.arrive_date,
				email:data.email,
				familyAdress:data.family_adress,
				remark:data.remark,
				state:"1"
			},
			async:false,
			success:function(data){
				var jsobj=eval('('+data+')');
				if(jsobj.success=='true'){
					win.Alert.tip('添加员工信息成功！');
					win.closeDlgAndReload();
				}else{
					parent.Alert.tip("新建信息失败!");
				}
			}
		});
	}
}

//页面验证
function toValidForm() {
	vboolean = $("#form1").valid();
	if (vboolean) {
		return true;
	} else {
		return false;
	}
}

function checkDate(date){
	var myDate = new Date();
	var month = myDate.getMonth()+1;
	var day = myDate.getDate();
	if (month <10) {
		month = "0"+month;
	}
	if (day <10) {
		day = "0"+day;
	}
	var today = myDate.getFullYear()+""+month+day;
		if (date >= today) {
			win.Alert.tip("请选择合法的出生日期！");
			return false;
		} else {
			return true;
		}
}
//验证员工工号是否存在
function IsExistStaffCode(){
	  var staffCode = liger.get("staff_code").getValue().replace(/\s+/g, "");
	   var retObj = $.ajax({
		   				url:basePath+'rest/StaffInfoAction/getStaffByCode',
		   				data:{staffCode:staffCode},
		   				async:false,
		   				success:function(data){
		   				 var dataObj=eval('('+data+')');
			   			   if(dataObj.success =="true"){
			   			       win.Alert.tip("员工工号在系统已存在,请重新输入！");
			   			     liger.get("staff_code").setValue("");
			   			   }
		   				}
					});
	}

function selectPhoto(){
	xdlg=$.ligerDialog.open({
		target:$("#appendDiv"),
		title:"选择图片",
		width:360,
		height:170,
		buttons:[
		        {text:"上传",onclick:function(){
		        	appendPath();
		        }},
		        {text:"取消",onclick:function(){
		        	xdlg.hide();
		        }}
		        ]
	});
}

function appendPath(){
	var img_file = $("#fileupload").val();
	var arr = img_file.split("\.");
	if(arr!=null && arr.length > 1 && arr[1] != "jpg" && arr[1] != "png"  && arr[1] != "jpeg" 
		 && arr[1] != "bmp" && arr[1] != "JPG" && arr[1] != "PNG"  && arr[1] != "JPEG" 
			 && arr[1] != "BMP"){
		Alert.tip('请选择正确格式(如:jpg,png,jpeg,bmp)的图片！');
		return;
	}
	$("#form2").ajaxSubmit({
		success:function(response, status){
			var result1 = response.replace(/\\/ig, '/');
			var result = result1.replace(/<pre.*>(.*)<\/pre>/ig, '$1');
			 json = eval("(" + result + ")");
          if(json.success == true){
        	  var  img_path = json.fileUrl;
        	  $("#img").attr("src",basePath+img_path);
        	  $("#staff_photo").val(img_path);
        	  xdlg.hide();
          }
		}
	});
}