//表单对象
var functionForm = null;
//参数类型
var paramTypeArr = [{id:'1',text:'整型'},{id:'2',text:'浮点型'},
                     {id:'3',text:'字符串'}];
//参数展示类型
var paramShowTypeArr = [{id:'1',text:'常量'},{id:'5',text:'时间'},{id:'2',text:'遥测参数'},{id:'3',text:'枚举'},{id:'4',text:'计算结果'}];

var helpImg = basePath+'resources/images/help.png';

$(function(){
	functionForm = $("#addField").ligerForm({
		labelAlign:'left',
        inputWidth: 140, 
        labelWidth: 100, 
        space: 10,
        validate:true,
        fields: [
             { display: "中文名称", name: "paramName",id:"paramName",
            	 type: "text", newline: true,validate:{required:true,maxlength:50}
             },
 			{ display: "英文代号", name: "paramCode",id:"paramCode",
 				type: "text", newline: false,validate:{required:true,maxlength:20}
 			},
 			{ display: "数据类型", name: "paramType",id:"paramType",
 				data:paramTypeArr,value:"3",cancelable:false,
 				type: "select", newline: true,validate:{required:true}
 			},
 			{ display: "参数展示类型", name: "paramShowType",id:"paramShowType",
 				data:paramShowTypeArr,value:"1",cancelable:false,onSelected:selectShowType,
 				type: "select", newline: false,validate:{required:true}
 			},
			{ display: "枚举数值", name: "comboBoxData",id:"comboBoxData",
				type: "text", newline: true,helpTip:{img:helpImg,title:'多个值用英文分号连接'}
			},
			{ display: "枚举显示值", name: "comboBoxShowData",id:"comboBoxShowData",
				type: "text", newline: false,helpTip:{img:helpImg,title:'多个值用英文分号连接'}
			},
			{ display: "是否校验", name: "isValid",id:"isValid",
				type: "radiolist", newline: true,value:1,onSelect:selectIsValid,
 				data:[{id:"0",text:"是"},{id:"1",text:"否"}]
			},
			{ display: "最小值", name: "minValue",id:"minValue",
				type: "text", newline: true
			},
			{ display: "最大值", name: "maxValue",id:"maxValue",
				type: "text", newline: false
			}
        ]
	});
	
	selectIsValid({
		value:1
	});
});

/**
 * 选择是否校验
 * @param e
 */
function selectIsValid(e){
	if(e.value == 1){
		liger.get('maxValue').setValue("");
		liger.get('minValue').setValue("");
		liger.get('maxValue').setDisabled(true);
		liger.get('minValue').setDisabled(true);
		liger.get('comboBoxData').setDisabled(true);
		liger.get('comboBoxShowData').setDisabled(true);
	}else{
		/*var paramType = liger.get('paramType').getValue();
		if(paramType == 4){
			liger.get('maxValue').destroy();
			liger.get('minValue').destroy();
			liger.add({ 
				display: "最大值", name: "maxValue",id:"maxValue",
				type: "date", newline: false,disabled:true
			});
			liger.add({ 
				display: "最小值", name: "maxValue",id:"minValue",
				type: "date", newline: false,disabled:true
			});
		}*/
		liger.get('maxValue').setEnabled(true);
		liger.get('minValue').setEnabled(true);
		liger.get('comboBoxData').setEnabled(true);
		liger.get('comboBoxShowData').setEnabled(true);
	}
}

/**
 * 选择展示类型事件
 */
function selectShowType(e){
	if(e == 3){
		liger.get('comboBoxData').setEnabled(true);
		liger.get('comboBoxShowData').setEnabled(true);
	}else{
		liger.get('comboBoxData').setValue("");
		liger.get('comboBoxData').setDisabled(true);
		liger.get('comboBoxShowData').setValue("");
		liger.get('comboBoxShowData').setDisabled(true);
	}
}

function submitForms() {
	var flag = null;
	var data = functionForm.getData();
	
	if(toValidForm(data)){
		if(data.isValid == 0){
			if(data.maxValue == "" || data.minValue == "" ){
				parent.Alert.tip("最大值、最小值不能为空！");
				return false;
			}
			if(data.maxValue < data.minValue ){
				parent.Alert.tip("最大值不能小于最小值！");
				return false;
			}
		}
		flag = data;
	}
	return flag;
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
