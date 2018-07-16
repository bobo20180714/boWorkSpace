var formData = null;
var addressData = null;
var typeData = [{id:"1",text:"遥测数据收发地址"},{id:"2",text:"非遥测数据收发地址"},{id:"3",text:"源码数据收发地址"}];
$(function (){
	initForm();
	getAddressData();
});

/**
 * 加载渲染jsp页面
 */
function initForm(){
	formData = $("#form1").ligerForm({
    	labelAlign:'left',
        inputWidth: 150, 
        labelWidth: 80, 
        space: 0,//间隔宽度
        validate:true,//验证
        fields: [
			{ display: "地址类型", name: "type",id:"type",type: "select",newline: false,
				data:typeData,value:1
			},
			{ display: "组播地址", name: "address",id:"address",type: "text",newline: true,
					validate:{required:true,maxlength:50 }/*,
            		helpTip:{img:basePath+'resources/images/help.png',
            			title:'组播地址范围224.0.2.0~238.255.255.255'}*/},
			{ display: "组播端口", name: "port",id:"port",type: "text",
						newline: true,validate:{required:true,maxlength:30,number:true }/*,
	            		helpTip:{img:basePath+'resources/images/help.png',
	            			title:'组播端口范围1~65534'}*/},
			{ display: "描述", name: "content",id:"content",type: "textarea",
				newline: true,validate:{maxlength:200 }},
        ]
    }); 
	
}

function getAddressData(){
	$.ajax({
		url:basePath+"rest/addressManager/view",
		data:{
			pkId:pkId
		},
		async:false,
		success:function(rsData) {
			addressData = eval("("+rsData+")");
			formData.setData(addressData);
		}
	});
}

//页面提交
function submitForm(){
	var data = formData.getData();
	if(toValidForm()){
		
		//判断卫星是否已经存在该类型的组播地址
		if(judgeType(data.type)){
			parent.Alert.tip("卫星已经存在该类型的组播地址！");
			return false;
		}
		data.pk_id = pkId;
		$.ajax({
			url:basePath+"rest/addressManager/update",
			data:data,
			async:false,
			success:function(rsData) {
				var result = eval("("+rsData+")");
				if (result.success == "true") {
					parent.Alert.tip("修改信息成功!");
					parent.currWin.closeDlgAndReload();
				} else {
					parent.Alert.tip("修改信息失败!");
				}
			}
		});
	}
}

/**
 * 判断卫星是否已经存在该类型的组播地址
 * @param type
 */
function judgeType(type){
	var flag = false;
	$.ajax({
		url:basePath+"rest/addressManager/judgeType",
		data:{
			satId:addressData.sat_id,
			addressId:pkId,
			type:type
		},
		async:false,
		success:function(rsData) {
			var result = eval("("+rsData+")");
			if (result.success == "true") {
				flag = true;
			}
		}
	});
	return flag;
}

//验证
function toValidForm() {
	var vboolean = $("#form1").valid();
	if (vboolean) {
		return true;
	} else {
		return false;
	}
}



	