var dataGridObj = null;

var columnDataArr = null;

$(function(){
//	queryResult();
	initToolBar();
	
	columnDataArr = new Array();
	columnDataArr.push({
		display:"暂时无查询",
		width : 180
	});
	if(columnDataArr != null && columnDataArr.length > 0){
		initDataGrid();
	}
});



function setValData(formData){
	/*tab.removeTabItem("queryNoTm");
	f_addTab("queryNoTm","非遥测数据查询",basePath+"noParamData/dataQuery/data_list.jsp?sql="+
			new Base64().encode(formData.sql)
			+"&startTime="+formData.startTime+
			"&endTime="+formData.endTime+"&relateId="+formData.relateId);*/
	sql = formData.sql;
	relateId = formData.relateId;
	startTime = formData.startTime;
	endTime = formData.endTime;
}

function queryResult(){
	columnDataArr = new Array();
	//查询列
	getColumnData();
	if(columnDataArr != null && columnDataArr.length > 0){
		initDataGrid();
		getGridData(sql,startTime,endTime);
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
				var fieldCode = data[i].field_code.toLowerCase();
				var fieldType = data[i].field_type;
				columnDataArr.push({
					name:fieldCode,
					display:data[i].field_name,
					align : 'center',
					fieldType:fieldType,
					width : 180,
					render:function(item,index,value,e){
						if(value){
							if(e.fieldType == 0 && value.indexOf(".") > 0){
								//整型,去掉小数点
								value = value.substr(0,value.indexOf("."));
							}
							return value
						}
					}
				});
			}
		}
	});
	
}

var conditionDialog = null;
function openNoParamQueryWindow(){
	if(conditionDialog){
		conditionDialog.show();
		return;
	}
	conditionDialog = $.ligerDialog.open({ 
		 width: 630,
		 height:420, 
		 title:"非遥测数据查询",
		 url: basePath+"noParamData/dataQuery/query_condition.jsp",
		 buttons :[
		           { text: '查询',type:'save', width: 80, onclick:function(item, dialog){
		        	   var conditionData = dialog.frame.getConditionInfo(); 
		        	   if(conditionData == null){
		        		   return;
		        	    }
		        	   setValData(conditionData);
		        	   queryResult();
		        	   conditionDialog.hide();
		           }},
		           { text: '关闭',type:'close', width: 80, onclick:function(item, dialog){
		        	   conditionDialog.hide();
		           }}
		           ]
	 });
}

//初始表格
function initDataGrid(){
	dataGridObj = $("#dataGrid").ligerGrid({
		columns : columnDataArr,
		height:'100%',
		rownumbers : true,
		checkbox : false,
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
		items : [  {
			text : '查询',
			id : 'query',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/view.gif'
		},{
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
		case "query":
			openNoParamQueryWindow()
			break;
		}
	}
}
