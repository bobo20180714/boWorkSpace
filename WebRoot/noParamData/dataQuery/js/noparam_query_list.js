//布局对象
var layoutObj = null;

var dataGridObj = null;

var relateId = null;
var relateType = null;

var columnDataArr = null;

//表单对象
var conditionForm = null;

//比较类型
var compareTypeArr = [{id:'0',text:'>'},{id:'1',text:'='},{id:'2',text:'<'}];

//计数器
var index = 1;

//当前选择的卫星
var satData = null;

var fieldData = null;

var relateData = null;

$(function(){
	//初始化布局
	layoutObj = $("#layout").ligerLayout({ 
		leftWidth: 540,
		heightDiff:0,
		space:0, 
		allowBottomCollapse:true,
		allowLeftResize: false,   
		onHeightChanged:f_heightChanged
	});
	
	//获取中间总高度
	var height = $(".l-layout-center").height() + $(".l-layout-centerbottom").height();
	//设置左边高度自适应
	$("#scrollDivTree")[0].style.height = height - 50 +25 +"px";
	
	initConditionForm();
	
	$("#searchbtn").ligerButton({
		click : function() {
			queryResult();
		}
	});
	$("#clearbtn").ligerButton({
		click : function() {
			satData = null;
			liger.get('sat_name').setText("");
			liger.get('sat_name').setValue("");
			conditionForm.setData({
				"sat_name":"",
				"relateName":"",
				"startTime":"",
				"endTime":""
			});
			liger.get('relateName').setData(getRelateData(-1));
			$("#paramTable").empty();
			$("#grid").empty();
			$("#grid").append('<div id="dataGrid" style="margin: 0; padding: 0px;overflow: hidden;"></div>');
		}
	});
//	initToolBar();
});

function queryResult(){
	var formData = conditionForm.getData();
	relateId = formData.relateName;
	relateType = getCodeFromRelateData(relateId);
	var sql = '\''+satData.satCode+'\'.\''+relateType+'\'.';
	
	//获取查询条件
	sql = sql + getQueryCondition(satData.satCode,relateType);
	
	$("#grid").empty();
	$("#grid").append('<div id="dataGrid" style="margin: 0; padding: 0px;overflow: hidden;"></div>');
	columnDataArr = new Array();
	//查询列
	getColumnData();
	if(columnDataArr != null && columnDataArr.length > 0){
		initDataGrid();
		getGridData(sql,formData.startTime+":00",formData.endTime+":00");
	}
}

function getQueryCondition(satCode,relateType){
	var tableBody = $("#paramTable")[0].childNodes;
	var trNodeList = tableBody[0].childNodes;
	if(trNodeList == null || trNodeList.length == 0){
		return "*;";
	}
	var trId = trNodeList[trNodeList.length-1].id;
	var nowRowNum = trId.split("_")[1];
	
	var paramStr = "";
	var parentNode = $("#tr_"+nowRowNum)[0].parentNode;
	var childNodesArr = parentNode.childNodes;
	if(childNodesArr == null && childNodesArr.length > 0){
		return "*;";
	}
	for (var i = 0; i < childNodesArr.length; i++) {
		var trId = childNodesArr[i].id;
		var trIdArr = trId.split("_");
		var trNum = trIdArr[1];
		var xmlStr = "";
		 
		//字段名称
		var fieldName = $("#field_name_"+trNum).ligerGetComboBoxManager().getText();
		if(fieldName == null || fieldName == ""){
			continue;
		}
		//比较类型
		var compareType = $("#compare_type_"+trNum).ligerGetComboBoxManager().getText();
		if(compareType == null || compareType == ""){
			continue;
		}
		var value = $("#param_value_"+trNum).val();
		if(value == null || value == ""){
			continue;
		}
		
		var preObj = $("#tr_"+trNum)[0].previousSibling;
		var numTemp = null;
		if(preObj != null){
			var preNodeName = preObj.nodeName
			if(preNodeName == "TR"){
				var trId = preObj.id;
				var trIdArr = trId.split("_");
				numTemp = trIdArr[1]
			}
		}
		
		if($("#lianjie_"+numTemp)){
			var lianjie = $("#lianjie_"+numTemp).val();
			if(lianjie){
				paramStr = paramStr + " "+lianjie+" ";
			}
		}
		paramStr = paramStr + '\''+satData.satCode+'\'.\''+relateType+'\'.\''+fieldName+'\''  + " " + compareType + " " +value;
	}
	if(paramStr.length == 0){
		return "*;";
	}
	return "* WHERE  " + paramStr;
}

function getCodeFromRelateData(relateIdTemp){
	if(relateData != null && relateData.length > 0){
		for(var i = 0;i<relateData.length;i++){
			if(relateData[i].id == relateIdTemp){
				return relateData[i].code;
			}
		}
	}
}

function getColumnData(){
	$.ajax({
		url:basePath+'rest/orbitrelated/findFieldList',
		data:{
			relateId:relateId
		},
		async:false,
		success:function(rsData){
			var data = eval('('+rsData+')');
			for (var i = 0; i < data.length; i++) {
				columnDataArr.push({
					name:data[i].field_code.toLowerCase(),
					display:data[i].field_name,
					align : 'center',
					width : 180
				});
			}
		}
	});
	
}


//初始表格
function initDataGrid(){
	dataGridObj = $("#dataGrid").ligerGrid({
		columns : columnDataArr,
		height:'100%',
		rownumbers : true,
		checkbox : true,
		rowHeight : 27,
		usePager:false,
		pageSize:20,
		heightDiff : 25,
		frozen : false ,
		delayLoad:true
	});
}

function getGridData(sql,startTime,endTime){
	$.ajax({
		url:basePath+'rest/noParamDataQuery/query',
		data:{
			sql:sql,
			start:startTime,
			end:endTime
		},
		async:false,
		success:function(rsData){
			var data = eval('('+rsData+')');
			if(data.success){
				dataGridObj.loadData(data.records);
			}
		}
	});
}

//初始化工具条
function initToolBar() {
	$("#toptoolbar").ligerToolBar({
		items : [ {
			text : '导出',
			id : 'export',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/back.gif'
		}]
	});
}

function itemclick(item){
	if (item.id) {
		switch (item.id) {
		case "export":
			if (dataGridObj == null || dataGridObj.rows.length == 0) {
				Alert.tip('没有数据可以导出！');
				return;
			}
			var url = getExcelUrl.getExcelUrl(dataGridObj, "查询结果");
        	window.location = url;
			break;
		}
	}
}

//设置左边高度自适应
function f_heightChanged(options){
	$("#scrollDivTree")[0].style.height = options.middleHeight-50 +27 +"px";
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}
//关闭对话框
function f_closeDlg() {
	sonOpenWin.close();
}


/**
 * 添加行
 */
function addTr(){
	$("#paramTable").append('<tr  id="tr_'+index+'"  style="height:30px;">'+
			'	<td width="160px"><input id="field_name_'+index+'"'+
			'		name="field_name_'+index+'" /><input id="hide_field_name_'+index+'"'+
			'		name="hide_field_name_'+index+'" type="hidden"/></td>'+
			'	<td id="td_compare_'+index+'" width="110px"><input id="compare_type_'+index+'"'+
			'		name="compare_type_'+index+'" /></td>'+
			'	<td id="td_value_'+index+'" width="110px"><input id="param_value_'+index+'"'+
			'		name="param_value_'+index+'" /><input id="lianjie_'+index+'" type="hidden" /></td>'+
			'	<td id="td_add_and_'+index+'" width="35px">'+
			'		<button id="add_and_'+index+'" onclick="addParam(\'and\','+index+')">与</button>'+
			'	</td>'+
			'	<td id="td_add_or_'+index+'" width="35px">'+
			'		<button id="add_or_'+index+'" onclick="addParam(\'or\','+index+')">或</button>'+
			'	</td>'+
			'	<td id="td_delete_'+index+'" width="20px">'+
			'	</td>'+
			'</tr>');
	
	initFuncParam("field_name_"+index,"compare_type_"+index,"param_value_"+index);
}

function initFuncParam(fieldNameId,compareTypeId,valueId){
    $("#"+fieldNameId).ligerComboBox({
    	data:fieldData,
            valueField : 'id',
            textField: 'text',
            selectBoxWidth: 150,
            autocomplete: true,
            width: 150,
            value:1
    });
    $("#"+compareTypeId).ligerComboBox({
    	data: compareTypeArr,
    	valueField : 'id',
    	textField: 'text',
    	selectBoxWidth: 100,
    	autocomplete: true,
    	width: 100,
    	value:0
    });
    $("#"+valueId).ligerTextBox({
    	width: 100
    });
}

/**
 * 新增参数
 */
function addParam(lianjieType,num){
	index++;
	
	$("#lianjie_"+num).val(lianjieType);
	
	$("#paramTable").append('<tr id="tr_'+index+'" style="height:30px;">'+
			'	<td width="160px"><input id="field_name_'+index+'"'+
			'		name="field_name_'+index+'" /><input id="hide_field_name_'+index+'"'+
			'		name="hide_field_name_'+index+'" type="hidden"/></td>'+
			'	<td id="td_compare_'+index+'" width="110px"><input id="compare_type_'+index+'"'+
			'		name="compare_type_'+index+'" /></td>'+
			'	<td id="td_value_'+index+'" width="110px"><input id="param_value_'+index+'"'+
			'		name="param_value_'+index+'" /><input id="lianjie_'+index+'" type="hidden" /></td>'+
			'	<td id="td_add_and_'+index+'" width="35px">'+
			'		<button id="add_and_'+index+'" onClick="addParam(\'and\','+index+')">与</button>'+
			'	</td>'+
			'	<td id="td_add_or_'+index+'" width="35px">'+
			'		<button id="add_or_'+index+'" onclick="addParam(\'or\','+index+')">或</button>'+
			'	</td>'+
			'	<td id="td_delete_'+index+'" width="20px">'+
			'		<button id="delete_'+index+'" onclick="deleteParam('+index+')">-</button>'+
			'	</td>'+
			'</tr>');
	initFuncParam("field_name_"+index,"compare_type_"+index,"param_value_"+index);
	
	var preObj = $("#tr_"+num)[0].previousSibling;
	if(preObj == null){
		$("#td_delete_"+num).empty();
		$("#td_delete_"+num).append('<button id="delete_'+num+'" onclick="deleteParam('+num+')">-</button>');
	}
	$("#td_add_and_"+num).hide();
	$("#td_add_or_"+num).hide();
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
			$("#td_add_and_"+numTemp).show();
			$("#td_add_or_"+numTemp).show();
		}
	}
	
	if(nextObj != null){
		var trId = nextObj.id;
		var trIdArr = trId.split("_");
		numTemp = trIdArr[1]
	}
	
	$("#tr_"+num).remove();
		
}


//初始化查询条件基本信息form
function initConditionForm(){
	conditionForm = $("#form1").ligerForm({
        inputWidth: 140, 
        labelWidth: 90, 
        space: 25,
        validate:true,
        fields: [
             { 
            	 display: "所属卫星", name: "sat_name",id:"sat_name",
 				type: "popup", newline: true,
         		onButtonClick:openSatWin
             },
			{ display: "数据类型", name: "relateName",id:"relateName",
				type: "select", newline: false,onSelected:selectRelateData
			},
			{ display: "开始时间", name: "startTime",id:"startTime",showTime:true,
				type: "date", newline: true,validate:{required:false}
			},
			{ display: "结束时间", name: "endTime",id:"endTime",showTime:true,
				type: "date", newline: false,validate:{required:false}
			}
        ]
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
		url : basePath+'noParamData/dataQuery/selectSat.jsp',
		buttons :[
		          { text: '确定', type:'save', width: 80,onclick:function(item, dialog){
		        	  
		        	    var data = dialog.frame.getSelectData();
						if (data == null) { 
							parent.Alert.tip("请选择卫星数据！");
							return; 
						}
						satData = data;
						liger.get('sat_name').setText(data.satName);
						liger.get('sat_name').setValue(data.satMid);
						
						//根据卫星ID获取数据类型
						liger.get('relateName').setData(getRelateData(data.satId));
						
						satSatWin.close();
		          }},
		          { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
		        	  satSatWin.close();
		          }}
		          ]
	});
}

function getRelateData(satId){
	relateData = new Array();
	$.ajax({
		url:basePath+'rest/orbitrelated/findOrbitrelated',
		data:{
			satId:satId
		},
		async:false,
		success:function(rsData){
			var data = eval('('+rsData+')');
			for (var i = 0; i < data.length; i++) {
				relateData.push({
					id:data[i].jsjg_id,
					text:data[i].jsjg_name,
					code:data[i].jsjg_code
				});
			}
		}
	});
	return relateData;
}

function selectRelateData(relateId){
	fieldData = new Array();
	$.ajax({
		url:basePath+'rest/orbitrelated/findFieldList',
		data:{
			relateId:relateId
		},
		async:false,
		success:function(rsData){
			var data = eval('('+rsData+')');
			for (var i = 0; i < data.length; i++) {
				fieldData.push({
					id:data[i].field_id,
					text:data[i].field_name
				});
			}
			
			$("#paramTable").empty();
			addTr();
		}
	});
}

/**
 * 返回选择的数据
 *  {code:"",value:""}
 */
function getSelectData(){
	if(relateType == null){
		return false;
	}
	var selectData = dataGridObj.getCheckedRows();
	if(selectData.length == 0){
		return false;
	}
	var selectValue = "";
	for(var i = 0;i<selectData.length;i++){
		if(i == 0){
			selectValue = selectData[i].pk_id;
		}else{
			selectValue = selectValue + "," + selectData[i].pk_id;
		}
	}
	return {code:relateType,value:selectValue};
}
