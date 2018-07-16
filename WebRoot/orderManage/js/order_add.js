//tab对象
var addTab = null;
//表单对象
var orderForm = null;
//结果处理表单对象
var resultForm = null;
//数据获取表单对象
var getDataForm = null;
//数据计算表单对象
var computerForm = null;
//是否
var selectData = [{id:'0',text:'否'},{id:'1',text:'是'}];

//函数参数类型
var fctParamTypeArr = [{id:'0',text:'遥测参数'},{id:'1',text:'常量类型'},{id:'2',text:'时间区间'}];

//计数器
var index = 1;

//当前选择的tabId
var nowSelectTabId = null;

//当前选择的卫星
var satData = null;

$(function(){
	
	addTab = $("#addTab").ligerTab({
		onAfterSelectTabItem:selectTabItem
	});
	
	//初始化订单基本信息form
	initOrderForm();
});

function selectTabItem(e){
	nowSelectTabId = e;
	if(e == "tabitem2"){
		if(!getDataForm){
			index++;
			//初始化获取数据form
			initGetDataForm();
			//默认添加一行
			addTr("paramTable_getData");
			
			initFuncParam("param_type_"+index,"param_value_"+index);
		}
	}else if(e == "tabitem3"){
		if(!computerForm){
			index++;
			//初始化计算form
			initComputerForm();
			//默认添加一行
			addTr("paramTable_comput");
			//初始化控件
			initFuncParam("param_type_"+index,"param_value_"+index);
		}
	}else if(e == "tabitem4"){
		if(!resultForm){
			index++;
			//初始化计算form
			initResultForm();
			//默认添加一行
			addTr("paramTable_result");
			//初始化控件
			initFuncParam("param_type_"+index,"param_value_"+index);
		}
	}
}


/**
 * 添加行
 * @param paramTableId
 */
function addTr(paramTableId){
	$("#"+paramTableId).append('<tr  id="tr_'+index+'"  style="height:30px;">'+
			'	<td width="110px"><input id="param_type_'+index+'"'+
			'		name="param_type_'+index+'" /><input id="hide_param_value_'+index+'"'+
			'		name="hide_param_value_'+index+'" type="hidden"/></td>'+
			'	<td id="td_value_'+index+'" width="260px"><input id="param_value_'+index+'"'+
			'		name="param_value_'+index+'" /></td>'+
			'	<td id="td_add_'+index+'" width="30px">'+
			'		<button id="add_'+index+'" onclick="addParam(\''+paramTableId+'\','+index+')">+</button>'+
			'	</td>'+
			'	<td id="td_delete_'+index+'" width="20px">'+
			'	</td>'+
			'</tr>');
}

function initFuncParam(paramTypeSelectId,paramValueSelectId){
    $("#"+paramTypeSelectId).ligerComboBox({
            data: fctParamTypeArr,
            valueField : 'id',
            textField: 'text',
            selectBoxWidth: 100,
            autocomplete: true,
            width: 100,
            value:1,
            onSelected:onSelectedParamType
    });
    $("#"+paramValueSelectId).ligerTextBox({
    	width: 250
    });
}

function onSelectedParamType(){
	var e = this;
	var typeHtmlId = e.id;//"param_type_1"
	var typeIdArr = typeHtmlId.split("_");
	$("#td_value_"+typeIdArr[2]).empty();
	if(e.selectedValue == 0){
		$("#td_value_"+typeIdArr[2]).append('<input id="param_value_'+typeIdArr[2]+'"'+
				'		name="param_value_'+typeIdArr[2]+'" />');
		$("#param_value_"+typeIdArr[2]).ligerPopupEdit({
			width: 250,
			onButtonClick:openSelectParamWin
		});
	}else if(e.selectedValue == 1){
		$("#td_value_"+typeIdArr[2]).append('<input id="param_value_'+typeIdArr[2]+'"'+
				'		name="param_value_'+typeIdArr[2]+'" />');
		$("#param_value_"+typeIdArr[2]).ligerTextBox({
	    	width: 250
	    });
	}else if(e.selectedValue == 2){
		$("#td_value_"+typeIdArr[2]).append('<table><tr><td width="130px"><input id="1_param_value_'+typeIdArr[2]+'"'+
				'		name="1_param_value_'+typeIdArr[2]+'" /></td><td><input id="2_param_value_'+typeIdArr[2]+'"'+
				'		name="2_param_value_'+typeIdArr[2]+'" /></tr></table>');
		$("#1_param_value_"+typeIdArr[2]).ligerDateEditor({
	    	width: 120
	    });
		$("#2_param_value_"+typeIdArr[2]).ligerDateEditor({
			width: 120
		});
	}
}

/**
 * 新增参数
 */
function addParam(paramTableId,num){
	index++;
	$("#"+paramTableId).append('<tr id="tr_'+index+'" style="height:30px;">'+
			'	<td width="110px"><input id="param_type_'+index+'"'+
			'		name="param_type_'+index+'" /><input id="hide_param_value_'+index+'"'+
			'		name="hide_param_value_'+index+'" type="hidden"/></td>'+
			'	<td id="td_value_'+index+'" width="260px"><input id="param_value_'+index+'"'+
			'		name="param_value_'+index+'" /></td>'+
			'	<td id="td_add_'+index+'" width="30px">'+
			'		<button id="add_'+index+'" onClick="addParam(\''+paramTableId+'\','+index+')">+</button>'+
			'	</td>'+
			'	<td id="td_delete_'+index+'" width="20px">'+
			'		<button id="delete_'+index+'" onclick="deleteParam('+index+')">-</button>'+
			'	</td>'+
			'</tr>');
	initFuncParam("param_type_"+index,"param_value_"+index);
	
	var preObj = $("#tr_"+num)[0].previousSibling;
	if(preObj == null){
		$("#td_delete_"+num).empty();
		$("#td_delete_"+num).append('<button id="delete_'+num+'" onclick="deleteParam('+num+')">-</button>');
	}
	$("#td_add_"+num).hide();
	
	//组合参数
	getParamXml_2(num,nowSelectTabId);
}

function deleteParam(num){
	var preObj = $("#tr_"+num)[0].previousSibling;
	var nextObj = $("#tr_"+num)[0].nextSibling;
	if(preObj == null && nextObj == null){
		return;
	}
	
	var numTemp = null;
	if(preObj != null){
		var preNodeName = preObj.nodeName
		if(preNodeName == "TR" && nextObj == null){
			var trId = preObj.id;
			var trIdArr = trId.split("_");
			numTemp = trIdArr[1]
			$("#td_add_"+numTemp).show();
		}
	}
	
	if(nextObj != null){
		var trId = nextObj.id;
		var trIdArr = trId.split("_");
		numTemp = trIdArr[1]
	}
	
	$("#tr_"+num).remove();
	
	getParamXml_2(numTemp,nowSelectTabId);
	
}

/**
 * 组合参数xml
 * @param nowRowNum
 */
function getParamXml_2(nowRowNum,tableItemId){
	var paramXmlStr = "";
	var parentNode = $("#tr_"+nowRowNum)[0].parentNode;
	var childNodesArr = parentNode.childNodes;
	for (var i = 0; i < childNodesArr.length; i++) {
		var trId = childNodesArr[i].id;
		var trIdArr = trId.split("_");
		var trNum = trIdArr[1];
		var xmlStr = "";
		//参数类型
		var paramType = $("#param_type_"+trNum).ligerGetComboBoxManager().getValue();
		if(paramType == 0){
			//遥测参数
			var hideValue = $("#hide_param_value_"+trNum).val();
			if(hideValue){
				var hideValueArr = hideValue.split("|");
				var satMid = satData.satMid;
				var tmIdStrs = hideValueArr[1];
				var tmIdArr = tmIdStrs.split(";")
				xmlStr = xmlStr + '<TM><satMid>'+satMid+'</satMid><tmList>';
				for (var j = 0; j < tmIdArr.length; j++) {
					xmlStr = xmlStr + '<tmId>'+tmIdArr[j]+'</tmId>';
				}
				xmlStr = xmlStr + '</tmList></TM>';
			}else{
				continue;
			}
		}else if(paramType == 1){
			//常量类型
			var value = $("#param_value_"+trNum).val();
			if(value == null || value == ""){
				continue;
			}
			xmlStr = xmlStr + '<paramValue>'+value+'</paramValue>';
		}else if(paramType == 2){
			//时间区间
			var start = $("#1_param_value_"+trNum).val();
			var end = $("#2_param_value_"+trNum).val();
			if(start == null || start == "" || end == null || end == ""){
				continue;
			}
			xmlStr = xmlStr + '<timeStart>'+start+'</timeStart><timeEnd>'+end+'</timeEnd>';
		}
		
		paramXmlStr = paramXmlStr + xmlStr;
	}
	paramXmlStr = "<param>"+paramXmlStr+"</param>";
	if(tableItemId == "tabitem2"){
		$("#getData_paramxml").ligerGetTextBoxManager().setValue(paramXmlStr);
	}else if(tableItemId == "tabitem3"){
		$("#comput_paramxml").ligerGetTextBoxManager().setValue(paramXmlStr);
	}else if(tableItemId == "tabitem4"){
		$("#result_paramxml").ligerGetTextBoxManager().setValue(paramXmlStr);
	}
}

//初始化订单基本信息form
function initOrderForm(){
	orderForm = $("#orderForm").ligerForm({
        inputWidth: 170, 
        labelWidth: 140, 
        space: 0,
        validate:true,
        fields: [
             { display: "订单名称", name: "orderName_1",id:"orderName_1",
            	 type: "text", newline: true,validate:{required:true,maxlength:200}
             },
			{ display: "是否需要数据获取模块", name: "isGetData_1",id:"isGetData_1",
				type: "radiolist", newline: true,value:1,onSelect:selectIsGetData,
				data:[{id:"0",text:"否"},{id:"1",text:"是"}]
			},
			{ display: "是否需要结果处理模块", name: "isResult_1",id:"isResult_1",
				type: "radiolist", newline: true,value:1,onSelect:selectIsResult,
				data:[{id:"0",text:"否"},{id:"1",text:"是"}]
			},
			{ display: "所属卫星", name: "sat_name",id:"sat_name",
				type: "popup", newline: true,validate:{required:true},
        		onButtonClick:openSatWin
			},
			{ display: "订单描述", name: "orderContent_1",id:"orderContent_1",
				width:325,
				type: "textarea", newline: true,validate:{required:false,maxlength:200}
			}
        ]
	});
}
//初始化计算form
function initComputerForm(){
	computerForm = $("#computerForm").ligerForm({
		labelAlign:'right',
		inputWidth: 140, 
		labelWidth: 80, 
		space: 0,
		validate:true,
		fields: [
		         { display: "计算模块", name: "comput_func_name",id:"comput_func_name",
		        	 type: "popup", newline: false,validate:{required:true},
		        		onButtonClick:openFunctionWin
		         }
		         ]
	});

	$("#comput_paramxml").ligerTextBox({
    	width: 425
    });
}
//初始化获取数据form
function initGetDataForm(){
	getDataForm = $("#getDataForm").ligerForm({
		labelAlign:'right',
		inputWidth: 140, 
		labelWidth: 80, 
		space: 0,
		validate:true,
		fields: [
		         { display: "计算模块", name: "get_data_func_name",id:"get_data_func_name",
		        	 type: "popup", newline: false,validate:{required:true},
		        		onButtonClick:openFunctionWin
		         }
		         ]
	});
	
	$("#getData_paramxml").ligerTextBox({
    	width: 425
    });
	
}
//初始化处理结果form
function initResultForm(){
	resultForm = $("#resultForm").ligerForm({
		labelAlign:'right',
		inputWidth: 140, 
		labelWidth: 80, 
		space: 0,
		validate:true,
		fields: [
		         { display: "计算模块", name: "result_func_name",id:"result_func_name",
		        	 type: "popup", newline: false,validate:{required:true},
		        		onButtonClick:openFunctionWin
		         }
		         ]
	});

	$("#result_paramxml").ligerTextBox({
    	width: 425
    });
}

/**
 * 选择卫星
 */
function openSatWin(){
	var satSatWin = parent.$.ligerDialog.open({
		title : '选择卫星',
		width : 560,
		height : 450,
		url : basePath+'orderManage/selectSat.jsp',
		buttons :[
		          { text: '确定', width: 60 ,onclick:function(item, dialog){
		        	  
		        	    var data = dialog.frame.getSelectData();
						if (data == null) { 
							parent.Alert.tip("请选择卫星数据！");
							return; 
						}
						satData = data;
						liger.get('sat_name').setText(data.satName);
						liger.get('sat_name').setValue(data.satMid);
						
						satSatWin.close();
		          }},
		          { text: '关闭', width: 60, onclick:function(item, dialog){
		        	  satSatWin.close();
		          }}
		          ]
	});
}

function selectIsGetData(e){
	if(e.value == 0){
		addTab.hideTabItem("tabitem2");
	}else{
		addTab.showTabItem("tabitem2");
	}
}

function selectIsResult(e){
	if(e.value == 0){
		addTab.hideTabItem("tabitem4");
	}else{
		addTab.showTabItem("tabitem4");
	}
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}

/**
 * 选择函数窗口
 */
function openFunctionWin(){
	var e = this;
	setCurrWindows();
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

/**
 * 选择多参数窗口
 */
function openSelectParamWin(){
	if(satData == null){
		parent.Alert.tip("请先选择卫星！");
	}
	var e = this;
	setCurrWindows();
	var fieldWin = parent.$.ligerDialog.open({
		width : 620,
		height : 495,
		title : "选择参数",
		isResize : false,
		url : basePath+"orderManage/selectMultiParam.jsp?satId="+satData.satId,
		buttons:[
		         { text: '确定', width: 60 ,onclick:function(item, dialog){
		        	 var result = dialog.frame.okWin();
		        	 var tmArray =  result.paramArr;
		        	 if(tmArray != null){
		        		 fieldWin.close();
		        		 var paramStrs = "";
		        		 var paramIdStrs = "";
		        		 for (var i = 0; i < tmArray.length; i++) {
		        			 if(i == 0){
		        				 paramStrs = tmArray[0].tm_param_code;
		        				 paramIdStrs = tmArray[0].tm_param_id;
		        			 }else{
		        				 paramStrs = paramStrs + ";" + tmArray[i].tm_param_code;
		        				 paramIdStrs = paramIdStrs + ";" + tmArray[i].tm_param_id;
		        			 }
						}
		        		 liger.get(e.id).setText(paramStrs);
		        		 $('#hide_'+e.id).val(result.satMid+'|'+paramIdStrs);
		        	 }
		        	 
		         }},
		         { text: '关闭', width: 60, onclick:function(item, dialog){
		        	 fieldWin.close();
		         }}
		         ]
	});
}

/**
 * 提交表单
 */
function submitForms(){
	var vboolean = orderForm.valid();
	var orderInfo = orderForm.getData();
	if (vboolean) {
		if(orderInfo.isGetData_1 == "1"){
			if(getDataForm == null){
				parent.Alert.tip("请输入数据获取计算模块信息！");
				return false;
			}
			
			//重新获取获取数据paramxml
			reGetParamXml("paramTable_getData","tabitem2");
			
			//获取数据方法信息
			var getDataFucObj = getDataForm.getData();
			orderInfo.isGetData_1 = "1";
			orderInfo.getDataId = getDataFucObj.get_data_func_name;//获取数据方法ID
			orderInfo.getDataParam = $("#getData_paramxml").ligerGetTextBoxManager().getValue();
		}

		//重新获取计算函数paramxml
		if(computerForm == null){
			parent.Alert.tip("请输入数据计算计算模块信息！");
			return false;
		}
		var computerFucObj = computerForm.getData();
		if(!computerFucObj.comput_func_name){
			parent.Alert.tip("请输入数据计算计算模块信息！");
			return false;
		}
		reGetParamXml("paramTable_comput","tabitem3");
		orderInfo.computId = computerFucObj.comput_func_name;//计算方法ID
		orderInfo.computParam = $("#comput_paramxml").ligerGetTextBoxManager().getValue();
		if(orderInfo.isResult_1 == "1"){
			if(resultForm == null){
				parent.Alert.tip("请输入结果处理计算模块信息！");
				return false;
			}
			//重新获取数据处理函数paramxml
			reGetParamXml("paramTable_result","tabitem4");
			//结果处理方法信息
			var resultFucObj = resultForm.getData();
			orderInfo.isResult_1 = "1";
			orderInfo.resultId = resultFucObj.result_func_name;//结果处理方法ID
			orderInfo.resultParam = $("#result_paramxml").ligerGetTextBoxManager().getValue();
		}
		setOrderInfoToInput(orderInfo);
		saveOrderInfo();
	} else {
		if(!orderInfo.sat_name){
			parent.Alert.tip("请选择所属卫星！");
		}
		return false;
	}
}

function setOrderInfoToInput(orderInfo){
	$("#satMid").val(satData.satMid);
	$("#orderName").val(orderInfo.orderName_1);
	$("#orderContent").val(orderInfo.orderContent_1);
	$("#isGetData").val(orderInfo.isGetData_1);
	$("#getDataId").val(orderInfo.getDataId);
	$("#getDataParam").val(orderInfo.getDataParam);
	$("#computId").val(orderInfo.computId);
	$("#computParam").val(orderInfo.computParam);
	$("#isResult").val(orderInfo.isResult_1);
	$("#resultId").val(orderInfo.resultId);
	$("#resultParam").val(orderInfo.resultParam);
}

function saveOrderInfo(){
	$("#hideForm").ajaxSubmit({
		url:basePath+'rest/orderManager/add',
		success:function(data){
			if(data == ""){
				parent.Alert.tip("请求出错！");
				return;
			}
			var jsobj = eval('('+data+')');
			if(jsobj.success == "true"){
				parent.Alert.tip("生成订单成功！");
         		//刷新表格
         		parent.closeAddWinAndLoadGrid();
			}else{
				parent.Alert.tip("生成订单失败！");
			}
		}
	});
}

/**
 *  保存时重新获取参数xml
 * @param paramTableId
 */
function reGetParamXml(paramTableId,tableItemId){
	var tableBody = $("#"+paramTableId)[0].childNodes;
	var trNodeList = tableBody[1].childNodes;
	if(trNodeList == null || trNodeList.length == 0){
		return;
	}
	var trId = trNodeList[trNodeList.length-1].id;
	var trIdArr = trId.split("_");
	getParamXml_2(trIdArr[1],tableItemId)
}