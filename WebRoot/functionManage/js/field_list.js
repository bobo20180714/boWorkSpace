//字段表格对象
var fieldGridObj = null;

$(function(){
	//初始化工具条
	initToolBar();
	//初始字段信息表格
	initFieldGrid();
	
	//设置表格里面的值
	setGridData();
	
});

function setGridData(){
	var gridDataArr = parent.currWin.fieldDataArr;
	for (var i = 0; gridDataArr != null && i < gridDataArr.length; i++) {
		fieldGridObj.addRow({
 			field_name:gridDataArr[i].fieldName,
 			field_type:gridDataArr[i].fieldType,
 			field_length:gridDataArr[i].fieldLength,
 			field_comment:gridDataArr[i].fieldComment,
 			field_scale:gridDataArr[i].fieldScale
 		});
	}
}

//初始化工具条
function initToolBar() {
	$("#fieldbar").ligerToolBar({
		items : [ {
			text : '新增',
			id : 'addField',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/add.gif'
		}, {
			text : '删除',
			id : 'deleteField',
			click : itemclick,
			img : basePath + 'lib/ligerUI/skins/icons/delete.gif'
		}]
	});
}

//初始化字段信息表格
function initFieldGrid() {
	fieldGridObj = $("#fieldGrid").ligerGrid({
		columns : [
		           {
		        	   display : '字段名称',
		        	   name : 'field_name',
		        	   align : 'center',
		        	   width : 150
		           },
		           {
		        	   display : '字段类型',
		        	   name : 'field_type',
		        	   align : 'center',
		        	   width : 120,
		        	   render:function(item){
		        		   if(item.field_type == 1){
		        			   return '整型';
		        		   }else if(item.field_type == 2){
		        			   return '浮点型';
		        		   }else if(item.field_type == 3){
		        			   return '字符串';
		        		   }else if(item.field_type == 4){
		        			   return '时间戳';
		        		   }
		        	   }
		           },
		           {
		        	   display : "字段长度",
		        	   name : "field_length",
		        	   align : 'center',
		        	   width : 100
		           },
		           {
		        	   display : "字段精度",
		        	   name : "field_scale",
		        	   align : 'center',
		        	   width : 100
		           },
		           {
		        	   display : "字段说明",
		        	   name : "field_comment",
		        	   align : 'center',
		        	   width : 150,
		        	   render:function(item){
		        		   if(item.field_comment){
		        			   return "<p title='"+item.field_comment+"'>"+item.field_comment+"</p>";
		        		   }
		        	   }
		           }
		           ],
		           height:'99%',
		           rownumbers : false,
		           checkbox : true,
		           isSingleCheck:true,
		           rowHeight : 27,
		   			heightDiff : 30,
		           usePager:false,
		           frozen : false 
	});
}

//工具条点击事件
function itemclick(item){
	if (item.id) {
		switch (item.id) {
			case "addField":
				var addFieldWin = $.ligerDialog.open({
					width : 400,
					height : 250,
					title : "新增字段信息",
					isResize : false,
					url : basePath+"functionManage/field_add.jsp",
					buttons:[
				         { text: '保存', width: 80 ,onclick:function(item, dialog){
						 	var fieldData = dialog.frame.submitForms();
						 	if(fieldData != null){
						 		addFieldWin.close();
						 		fieldGridObj.addRow({
						 			field_name:fieldData.fieldName,
						 			field_type:fieldData.fieldType,
						 			field_length:fieldData.fieldLength,
						 			field_comment:fieldData.fieldComment,
						 			field_scale:fieldData.fieldScale
						 		});
						 	}
						 }},
						 { text: '关闭', width: 80, onclick:function(item, dialog){
							 addFieldWin.close();
						 }}
					]
				});
				break;
			case "deleteField":
				if (fieldGridObj.selected.length != 1) {
					Alert.tip('请选择一条数据！');
					return;
				}
				fieldGridObj.deleteSelectedRow();
				break;
		}
	}
}

function okWin(){
	var data = new Array();
	var gridData = fieldGridObj.rows;
	for (var i = 0; i < gridData.length; i++) {
		data.push({
			fieldName:gridData[i].field_name,
			fieldType:gridData[i].field_type,
			fieldLength:gridData[i].field_length,
			fieldComment:gridData[i].field_comment,
			fieldScale:gridData[i].field_scale
		});
	}
	return data;
}
