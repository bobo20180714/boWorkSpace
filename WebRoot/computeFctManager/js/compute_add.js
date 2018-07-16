//表单对象
var functionForm = null;
//返回值类型
var returnTypeArr = [{id:'11',text:'整型'},{id:'12',text:'浮点型'},
                     {id:'24',text:'数据列表'}];

//函数功能类型
var fctTypeArr = [{id:'0',text:'数据获取'},{id:'1',text:'数据运算'},
                     {id:'2',text:'计算结果处理'}];

$(function(){

	functionForm = $("#addFunction").ligerForm({
		labelAlign:'left',
        inputWidth: 200, 
        labelWidth: 110, 
        space: 20,
        validate:true,
        fields: [
             { display: "控制量计算名称", name: "computeName",id:"computeName",
            	 type: "text", newline: true,validate:{required:true,maxlength:50}
             },
             { display: "关联计算模块", name: "fctId",id:"fctId",onButtonClick:openFunctionWin,
            	 type: "popup", newline: true,validate:{required:false}
             },
			{ display: "控制量计算描述", name: "computeDesc",id:"computeDesc",
				width:200,height:120,
				type: "textarea", newline: true,validate:{required:false,maxlength:200}
			}
        ]
	});
	
});

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}



/**
 * 选择函数窗口
 */
function openFunctionWin(){
	var e = this;
	var fieldWin = parent.$.ligerDialog.open({
		width : 730,
		height : 450,
		title : "计算模块列表",
		isResize : false,
		url : basePath+"functionManage/select_function_list.jsp",
		buttons:[
	         { text: '确定', width: 60 ,onclick:function(item, dialog){
			 	var funcObj = dialog.frame.okWin();
			 	
			 	if(funcObj != null){
			 		fieldWin.close();
			 		liger.get(e.id).setText(funcObj.fct_name);
			 		liger.get(e.id).setValue(funcObj.fct_id);
			 	}
			 	
			 }},
			 { text: '关闭', width: 60, onclick:function(item, dialog){
				 fieldWin.close();
			 }}
		]
	});
}

function submitForms() {
	var data = functionForm.getData();
	if(toValidForm(data)){
		$.post(basePath+"rest/ComputeFunc/add",data,function(rsData){
			var jsobj = eval('('+rsData+')');
			if(jsobj.success=='true'){
				parent.Alert.tip("新增控制量计算成功!");
				parent.f_closeAndLoad();
			}else{
				parent.Alert.tip("新增控制量计算失败!");
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
