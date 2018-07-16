//日志表格对象
var logGridObj = null;
//查询日志数据循环
var logInterval = null;
//表格列
var columns = null;
//获取列头循环，如果获取到停止
var getGridColumnInterval = null;

$(function(){

	//获取列头
	getGridColumns();
	
	if(columns == null){
		getGridColumnInterval = setInterval("getGridColumns()",1000);
	}
	
	//循环执行查询进程信息
	logInterval = setInterval("queryLog()",1000);
});

function getGridColumns(){
	$.ajax({
		url:basePath+'rest/relationInfo/queryLogGridColumns',
		data:{
			processCode:processCode
		},
		async:false,
		success:function(rsStr){
			if(rsStr){
				var rsData = eval('('+rsStr+')');
				columns = new Array();
				for(var i=0;rsData != null && i<rsData.length;i++){
					columns.push({
						display : rsData[i].display,
						name : rsData[i].name,
						align : rsData[i].align==null?'center':rsData[i].align,
								width : rsData[i].width==0?200:rsData[i].width
					});
				}
			}
		}
	});
	if(columns != null && columns.length > 0){
		//初始化日志表格
		initLogGrid();
		//停止循环
		clearInterval(getGridColumnInterval);
	}
}

//初始化日志表格
function initLogGrid() {
	logGridObj = $("#logGrid").ligerGrid({
			   columns : columns,
	           height:'100%',
	           usePager:false,
	           rownumbers : true,
	           checkbox : false,
	           rowHeight : 27,
	           heightDiff : 35,
	           frozen : false 
	});
}
function queryLog(){
	$.ajax({
		url:basePath+'rest/relationInfo/processLogList',
		data:{
			processCode:processCode
		},
		async:false,
		success:function(rsStr){
			var runParamData = eval('('+rsStr+')');
			if(columns != null && columns.length > 0){
				logGridObj.loadData(runParamData);
			}
		}
	});
}