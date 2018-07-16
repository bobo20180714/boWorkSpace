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

var order_class_data = [{id:'1',text:'单次运算'},{id:'2',text:'按次数循环'},{id:'3',text:'按截止时间'},{id:'4',text:'按次数和截止时间'}];

//输入类型
var paramTypeArr = [{id:'1',text:'整型'},{id:'2',text:'浮点型'},
                    {id:'3',text:'字符串'},{id:'4',text:'时间'},{id:'5',text:'遥测参数'}];

//超时时长
var overTimeArr = [{id:'1',text:'1'},{id:'2',text:'2'},{id:'3',text:'3'},{id:'4',text:'4'},{id:'5',text:'5'},{id:'6',text:'6'},
					{id:'7',text:'7'},{id:'8',text:'8'},{id:'9',text:'9'},{id:'10',text:'10'},{id:'20',text:'20'},
					{id:'30',text:'30'},{id:'50',text:'50'},{id:'100',text:'100'}];
//计算失败次数
var computCountArr = [{id:'1',text:'1'},{id:'2',text:'2'},{id:'3',text:'3'},{id:'4',text:'4'},{id:'5',text:'5'},{id:'6',text:'6'},
					{id:'7',text:'7'},{id:'8',text:'8'},{id:'9',text:'9'},{id:'10',text:'10'}];


//计数器
var index = 1;

//当前选择的tabId
var nowSelectTabId = null;

//当前选择的卫星
var satData = null;

var paramList = null;

$(function(){
	//初始化订单基本信息form
	initOrderForm();
	//获取输入参数信息
	getParamInfo();

});

//初始化订单基本信息form
function initOrderForm(){
	orderForm = $("#orderForm").ligerForm({
        inputWidth: 150, 
        labelWidth: 100, 
        space: 10,
        validate:true,
        fields: [
             { display: "控制量计算名称", name: "orderName_1",id:"orderName_1",
            	 type: "text", newline: true,validate:{required:true,maxlength:200},
            	 group:"控制量计算信息"
             },
             { display: "执行时间", name: "orderStartTime",id:"orderStartTime",
            	 type: "date", newline: false,validate:{required:true},showTime:true
             },
             { display: "循环类型", name: "order_class_1",id:"order_class_1",
	        	 type: "select", newline: true,validate:{required:true},
					cancelable:false,value:1,
	        	 data:order_class_data,onselected:selectedClass
	         },
	         { display: "循环间隔(毫秒)", name: "loop_space_1",id:"loop_space_1",
	        	 type: "text", newline: false,validate:{required:false}
	         },
             { display: "循环次数", name: "loop_maxnum_1",id:"loop_maxnum_1",
	        	 type: "text", newline: true,validate:{required:false}
	         },
	         { display: "循环截止时间", name: "loop_endtime_1",id:"loop_endtime_1",showTime:true,
	        	 type: "date",format:"yyyy-MM-dd hh:mm:ss", newline: false,validate:{required:false}
	         },
             { display: "超时时间(分钟)", name: "overTime_1",id:"overTime_1",
	        	 type: "select", newline: true,validate:{required:true},
					cancelable:false,
	        	 data:overTimeArr
	         },
	         { display: "最大失败次数", name: "computeCount_1",id:"computeCount_1",
	        	 type: "select", newline: false,validate:{required:true},
					cancelable:false,
	        	 data:computCountArr
	         },
             { display: "控制量计算描述", name: "orderContent_1",id:"orderContent_1",
					width:410,
					type: "textarea", newline: true,validate:{required:false,maxlength:200}
				}
        ]
	});
	$("#orderContent_1")[0].style.borderColor = "#AECAF0";
	
	//获取最大失败次数和超时时间
	getRelateTypeAndFunc();
	
	selectedClass("1");
}

/**
 * 选择循环类型事件
 * @param e
 */
function selectedClass(e){
	if(e == "1"){
		//单次
		liger.get("loop_space_1").setValue("");
		liger.get("loop_maxnum_1").setValue("");
		liger.get("loop_endtime_1").setValue("");
		liger.get("loop_space_1").setDisabled(true);
		liger.get("loop_maxnum_1").setDisabled(true);
		liger.get("loop_endtime_1").setDisabled(true);
	}else if(e == "2"){

		liger.get("loop_endtime_1").setValue("");
		liger.get("loop_space_1").setEnabled(true);
		liger.get("loop_maxnum_1").setEnabled(true);
		liger.get("loop_endtime_1").setDisabled(true);
	}else if(e == "3"){
		liger.get("loop_maxnum_1").setValue("");
		liger.get("loop_space_1").setEnabled(true);
		liger.get("loop_maxnum_1").setDisabled(true);
		liger.get("loop_endtime_1").setEnabled(true);
	}else if(e == "4"){
		liger.get("loop_space_1").setEnabled(true);
		liger.get("loop_maxnum_1").setEnabled(true);
		liger.get("loop_endtime_1").setEnabled(true);
	}
}

/**
 * 获取类型和计算功能关系表中的信息
 * @returns
 */
function getRelateTypeAndFunc(){
	$.ajax({
		url:basePath+'rest/ComputeType/getRelateTypeAndFunc',
		data:{
			computeTypeId:typeId,
			computeId:computeId
		},
		async:false,
		success:function(data){
			var relateData = eval('('+data+')'); 
			liger.get('overTime_1').setValue(relateData.over_time);
			liger.get('computeCount_1').setValue(relateData.compute_count);
		}
	});

}

function getParamInfo(){
	$.ajax({
		url:basePath+'rest/ComputeFunc/view',
		data:{
			computeId:computeId
		},
		async:false,
		success:function(data){
			var haveData = eval('('+data+')');
			paramList = haveData.fieldList;
			var inputArray = new Array();

			/*var paramShowTypeArr = [{id:'1',text:'常量'},{id:'2',text:'遥测参数'},
			                        {id:'3',text:'枚举'},{id:'4',text:'计算结果'},{id:'5',text:'时间'}];*/

			//循环参数列表
			for (var i = 0; i < paramList.length; i++) {
				var newLine = i%2==0?true:false;//当时偶数时，换行
				var param = paramList[i];
				if(param.param_show_type == "2"){
					//遥测参数
					inputArray.push({
						display: param.param_name, name: param.param_code,id:param.param_code,
						type: "popup", newline: newLine,paramShowType:param.param_show_type,
						onButtonClick:openSelectParamWin,validate:{required:false},
		            	 group:"输入参数"
					});
				}else if(param.param_show_type == "3"){
					//枚举
					var comboBoxData = new Array();
					var comboBoxDataStr = param.param_value;
					var comboBoxShowDataStr = param.param_text;
					if(comboBoxDataStr){
						var comboBoxDataArr = comboBoxDataStr.split(";");
						var comboBoxShowDataArr = comboBoxShowDataStr.split(";");
						for(var c = 0;c < comboBoxDataArr.length;c++){
							comboBoxData.push({
								id:comboBoxDataArr[c],
								text:comboBoxShowDataArr[c]
							});
						}
					}
					inputArray.push({
						display: param.param_name, name: param.param_code,id:param.param_code,
						type: "select", newline: newLine,paramShowType:param.param_show_type,
						data:comboBoxData,validate:{required:false},selectedIndex:0,
						cancelable:false,
		            	 group:"输入参数"
					});
				}else if(param.param_show_type == "4"){
					//计算结果
					inputArray.push({
						display: param.param_name, name: param.param_code,id:param.param_code,
						type: "popup", newline: newLine,paramShowType:param.param_show_type,
						onButtonClick:openNoParamQueryWindow,validate:{required:false},
		            	 group:"输入参数"
					});
				}else if(param.param_show_type == "5"){
					//时间
					inputArray.push({
						display: param.param_name, name: param.param_code,id:param.param_code,
						type: "date", newline: newLine,paramShowType:param.param_show_type,
						validate:{required:false},
						maxValue:param.max_value,minValue:param.min_value,isValid:param.is_valid,
		            	 group:"输入参数"
					})
				}else{
					var validateTemp = {required:false};
					var titleTemp = null;
					if(param.param_type == "1"){
						titleTemp = "请输入整数数字";
						if(param.max_value != "" && param.min_value != ""){
							titleTemp = "请输入"+param.min_value+"和"+param.max_value+"之间的整数数字";
						}
					}else if(param.param_type == "2"){
						titleTemp = "请输入浮点型数字";
						if(param.max_value != "" && param.min_value != ""){
							titleTemp = "请输入"+param.min_value+"和"+param.max_value+"之间的浮点型数字";
						}
					}else if(param.param_type == "3"){
						titleTemp = "请输入字符串";
					}
					//常量
					inputArray.push({
						display: param.param_name, name: param.param_code,id:param.param_code,
						type: "text", newline: newLine,paramShowType:param.param_show_type,
						validate:validateTemp,helpTip:{img:null,title:titleTemp},
						maxValue:param.max_value,minValue:param.min_value,isValid:param.is_valid,
		            	 group:"输入参数"
					})
				}
			}
			paramForm = $("#paramForm").ligerForm({
				inputWidth: 150, 
				labelWidth: 100, 
				space: 10,
				validate:true,
				fields: inputArray
			});
		}
	});

}
   
//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}

/**
 * 提交表单
 */
function submitForms(){
	var vboolean = orderForm.valid();
	var orderInfo = orderForm.getData();
	if (vboolean) {
		if(paramForm){
			/*vboolean = paramForm.valid();
			if (!vboolean) {
				for (var i = 0; fields != null && i < fields.length; i++) {
					var display = fields[i].display;
					if(fields[i].type == "popup"){
						var text = liger.get(fields[i].id).getText();
						if(text == ""){
							parent.Alert.tip(display+"不能为空!");
							return;
						}
					}
				}
				return;
			}*/
			var paramDataInfo = paramForm.getData();
			var fields = paramForm.options.fields;
			for (var i = 0; fields != null && i < fields.length; i++) {
				var name = fields[i].name;
				var display = fields[i].display;
				var maxValue = fields[i].maxValue;
				var minValue = fields[i].minValue;
				var paramType = fields[i].paramType;
				var value = paramDataInfo[name];
				if(paramType == 1){
					//整型
					if(value != 0){
						var exp= /^-?[1-9][0-9]*$/;
						if(!exp.test(value)){
							parent.Alert.tip(display+"只能输入整数!");
							return;
						}
					}
				}else if(paramType == 2){
					//浮点型
					 var exp= /^\d+.?\d+$/;
					 if(!exp.test(value)){
						parent.Alert.tip(display+"只能输入整数或浮点数!");
						 return;
					 }
				}
				if(fields[i].isValid == 0){
					if(maxValue && parseFloat(value) >= parseFloat(maxValue)){
						parent.Alert.tip(display+"不能大于"+maxValue+"!");
						return;
					}
					if(minValue && parseFloat(value) <= parseFloat(minValue)){
						parent.Alert.tip(display+"不能小于"+minValue+"!");
						return;
					}
				}
			}
			orderInfo.computParam = getParamXmlStr();
		}
		
		if(!validLoop(orderInfo)){
			return;
		}
		
		setOrderInfoToInput(orderInfo);
		saveOrderInfo();
	}
}

function validLoop(orderInfo){
	if(orderInfo.order_class_1 != "1"){
		//验证循环间隔
		if(!orderInfo.loop_space_1){
			parent.Alert.tip("循环间隔不能为空!");
			return false;
		}
		if(orderInfo.loop_space_1 != 0){
			var exp= /^[1-9][0-9]*$/;
			if(!exp.test(orderInfo.loop_space_1)){
				parent.Alert.tip("循环间隔只能输入整数!");
				return false;
			}
		}
	}
	if(orderInfo.order_class_1 == "2" || orderInfo.order_class_1 == "4"){
		//验证循环次数
		if(!orderInfo.loop_maxnum_1){
			parent.Alert.tip("循环次数不能为空!");
			return false;
		}
		if(orderInfo.loop_maxnum_1 != 0){
			var exp= /^[1-9][0-9]*$/;
			if(!exp.test(orderInfo.loop_maxnum_1)){
				parent.Alert.tip("循环次数只能输入整数!");
				return false;
			}
		}
	}
	if(orderInfo.order_class_1 == "3" || orderInfo.order_class_1 == "4"){
		//验证截止时间
		if(!orderInfo.loop_endtime_1){
			parent.Alert.tip("循环截止时间不能为空!");
			return false;
		}
	}
	return true;
}

/**
 * 获取运行参数
 */
function getParamXmlStr(){
	var paramXmlStr = "";
	var paramData = paramForm.getData();
	var fields = paramForm.options.fields;
	for (var i = 0; fields != null && i < fields.length; i++) {
		var name = fields[i].name;
		var paramShowType = fields[i].paramShowType;
		if(paramShowType == 2){
			//遥测参数
			var tms = paramData[name];
			if(tms){
				paramXmlStr = paramXmlStr + "<TM><tmList>";
				var tmArr = tms.split(";");
				for (var j = 0; j < tmArr.length; j++) {
					paramXmlStr = paramXmlStr + "<tmId>"+tmArr[j]+"</tmId>"
				}
				paramXmlStr = paramXmlStr + "</tmList></TM>";
			}
		}else if(paramShowType == 4){
			//非遥测
			var tms = paramData[name];
			if(tms){
				//tms  格式：类型编号:10,20;类型编号:5,6
				paramXmlStr = paramXmlStr + "<UNTM>";
				var tmArr = tms.split(";");
				for (var j = 0; j < tmArr.length; j++) {
					var oneType = tmArr[j];
					if(oneType){
						var oneTypeArr = oneType.split(":")
						paramXmlStr = paramXmlStr + "<"+oneTypeArr[0]+">";
						var valueArr = oneTypeArr[1].split(",");
						for(var v = 0;v < valueArr.length ;v++){
							if(valueArr[v]){
								paramXmlStr = paramXmlStr + "<valueID>" + valueArr[v] + "</valueID>";
							}
						}
						paramXmlStr = paramXmlStr + "</"+oneTypeArr[0]+">";
					}
				}
				paramXmlStr = paramXmlStr + "</UNTM>";
			}
		}else{
			paramXmlStr = paramXmlStr + "<"+name+">" + paramData[name] + "</"+name+">"
		}
	}
	paramXmlStr = "<param><satMid>"+satMid+"</satMid><satName>"+satName+"</satName>"+paramXmlStr+"</param>";
	
	return paramXmlStr;
}

function setOrderInfoToInput(orderInfo){
	$("#orderName").val(orderInfo.orderName_1);
	$("#orderContent").val(orderInfo.orderContent_1);
	$("#time").val(orderInfo.orderStartTime);
	$("#overTime").val(orderInfo.overTime_1);
	

	$("#order_class").val(orderInfo.order_class_1);
	$("#loop_space").val(orderInfo.loop_space_1);
	$("#loop_maxnum").val(orderInfo.loop_maxnum_1);
	$("#loop_endtime").val(orderInfo.loop_endtime_1);
	
	$("#computeCount").val(orderInfo.computeCount_1);
	$("#satId").val(satId);
	$("#computId").val(computeId);
	$("#computTypeId").val(typeId);
	$("#computParam").val(orderInfo.computParam);
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
				parent.Alert.tip("控制量计算新增成功！");
         		//刷新表格
         		parent.closeAddWinAndLoadGrid();
			}else{
				parent.Alert.tip("控制量计算新增失败！");
			}
		}
	});
}

/**
 * 选择多参数窗口
 */
function openSelectParamWin(){
	var e = this;
	setCurrWindows();
	var fieldWin = parent.$.ligerDialog.open({
		width : 620,
		height : 495,
		title : "选择参数",
		isResize : false,
		url : basePath+"orderManage/selectMultiParam.jsp?satId="+satId,
		buttons:[
		         { text: '确定',type:'save', width: 80 ,onclick:function(item, dialog){
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
		        		 liger.get(e.id).setValue(paramIdStrs);
		        	 }
		        	 
		         }},
		         { text: '关闭',type:'close', width: 80, onclick:function(item, dialog){
		        	 fieldWin.close();
		         }}
		         ]
	});
}

/**
 * 选择非遥测数据窗口
 */
var conditionDialog = null;
function openNoParamQueryWindow(){
	var e = this;
	if(conditionDialog){
		conditionDialog.show();
		return;
	}
	conditionDialog = parent.$.ligerDialog.open({ 
		 width: 1084,
		 height:521, 
		 isResize:true,
		 showMax:true,
		 title:"非遥测数据查询",
		 url: basePath+"noParamData/dataQuery/noparam_query_list.jsp",
		 buttons :[
		           { text: '确定',type:'save', width: 80, onclick:function(item, dialog){
		        	   var conditionData = dialog.frame.getSelectData(); 
		        	   if(conditionData == false){
		        		   return;
		        	    }
		        	   var dataStrs = conditionData.code+":"+conditionData.value;
		        		liger.get(e.id).setText(dataStrs);
		        		liger.get(e.id).setValue(dataStrs);
		        	    conditionDialog.hide();
		           }},
		           { text: '关闭',type:'close', width: 80, onclick:function(item, dialog){
		        	   conditionDialog.hide();
		           }}
		           ]
	 });
}
