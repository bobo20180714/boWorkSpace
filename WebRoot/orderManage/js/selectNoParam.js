var formData = null;
var satData = null;
var relateData = null;
var relateId = null;
var relateType = null;
$(function (){
	 initForm();
	 initQueryBar();
});

function initForm(){
	conditionForm = $("#form1").ligerForm({
        inputWidth: 140, 
        labelWidth: 95, 
        space: 40,
        validate:true,
        fields: [
             { 
            	 display: "所属卫星", name: "sat_name",id:"sat_name",
 				type: "popup", newline: true,
         		onButtonClick:openSatWin
             },
			{ display: "数据类型", name: "relateName",id:"relateName",
				type: "select", newline: false
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

//初始化查询、重置
function initQueryBar(){
	$("#searchbtn").ligerButton({
		type:"one", 
		click : function() {
			queryResult();
		}
	});
	
	// 重置搜索条件
	$("#resetbtn").ligerButton({
		type:"two", 
		click : function() {
			formObj1.setData({
				computeName:"",
			});
		}
	});
}

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

function getCodeFromRelateData(relateIdTemp){
	if(relateData != null && relateData.length > 0){
		for(var i = 0;i<relateData.length;i++){
			if(relateData[i].id == relateIdTemp){
				return relateData[i].code;
			}
		}
	}
}

function getQueryCondition(satCode,relateType){
	return "*";
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
					name:data[i].field_name,
					display:data[i].field_comment,
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
		height:350,
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
			selectValue = selectData[i].PK_ID;
		}else{
			selectValue = selectValue + "," + selectData[i].PK_ID;
		}
	}
	return {code:relateType,value:selectValue};
}
