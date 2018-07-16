//表单对象
var functionForm = null;
//是否是叶子节点类型
var fieldTypeArr = [{id:'0',text:'是'},{id:'1',text:'否'}];
$(function(){
	functionForm = $("#addField").ligerForm({
		labelAlign:'left',
        inputWidth: 200, 
        labelWidth: 110, 
        space: 20,
        validate:true,
        fields: [
             { display: "计算类型名称", name: "typeName",id:"typeName",
            	 type: "text", newline: false,validate:{required:true,maxlength:50}
             },
 			/*{ display: "是否是末级", name: "isLeaf",id:"isLeaf",
 				data:fieldTypeArr,value:"0",cancelable:false,
 				readonly:true,
 				type: "select", newline: true,validate:{required:true}
 			},*/
 			{ display: "计算类型描述", name: "typeDesc",id:"typeDesc",
 				type: "textarea", newline: true,validate:{required:false,maxlength:200}
 			}
        ]
	});
	
	//获取信息
	getFormData();
});

function getFormData(){
	$.ajax({
		url:basePath+"rest/ComputeType/view",
		data:{
			computeTypeId:typeId
		},
		async:false,
		success:function(rsData){
			var jsobj = eval('('+rsData+')');
			functionForm.setData(jsobj);
		}
	});
}

function submitForms() {
	var data = functionForm.getData();
	if(toValidForm(data)){
		data.pkId = typeId;
		data.isLeaf = 0;
		$.post(basePath+"rest/ComputeType/update",data,function(rsData){
			var jsobj = eval('('+rsData+')');
			if(jsobj.success=='true'){
				parent.Alert.tip("修改计算类型成功!");
				parent.currWin.tree_fresh();
				parent.currWin.f_closeDlg();
			}else{
				parent.Alert.tip("修改计算类型失败!");
			}
		});
	}
}

//验证
function toValidForm(data) {
	var vboolean = functionForm.valid();
	if (vboolean) {
		return true;
	} else {
		return false;
	}
}
