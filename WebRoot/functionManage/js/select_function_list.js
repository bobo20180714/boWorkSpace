//布局对象
var layoutObj = null;
//函数表格对象
var functionGridObj = null;

//当前选择的方法
var fidTemp = -1;

$(function(){
	initForm();
	//初始化查询、重置
	initQueryBar();
	//初始函数表格
	initFunctionGrid();
});

function initForm(){
    formObj1 = $("#form1").ligerForm({
    		labelAlign:'right',
        	inputWidth: 100, 
            labelWidth: 80, 
            space: 5,
            fields: [
				{display: '模块代号', id: 'functionCode',name:'functionCode',type:"text",newline: false},
				{display: '中文名称', id: 'functionName',name:'functionName',type:"text",newline: false}
			]
    });
}

//初始化查询、重置
function initQueryBar(){
	$("#searchbtn").ligerButton({
		type:"one", 
		click : function() {
			var formData = formObj1.getData();
			functionGridObj.set('parms', {
				functionCode : formData.functionCode,
				functionName : formData.functionName
			});
			functionGridObj.loadData();
		}
	});
	
	// 重置搜索条件
	$("#resetbtn").ligerButton({
		type:"two", 
		click : function() {
			formObj1.setData({
				functionCode:"",
				functionName:""
			});
		}
	});
}

//初始函数表格
function initFunctionGrid(){
	functionGridObj = $("#functionGrid").ligerGrid({
		columns : [
	           {
	        	   display : '主键',
	        	   name : 'fct_id',
	        	   align : 'center',
	        	   hide:true,
	        	   width : 0.1
	           },
				{
					display : '模块代号',
					name : 'fct_code',
					align : 'center',
					width : 120
				},
				{
					display : '中文名称',
					name : 'fct_name',
					align : 'center',
					width : 120
				},
				{
					display : "功能描述",
					name : "fct_content",
					align : 'left',
					width : 200,
					render:function(item){
						if(item.fct_content){
							return "<p title='"+item.fct_content+"'>"+item.fct_content+"</p>";
						}
					}
				},
				{
					display : "所在java类名称",
					name : "fct_class_name",
					align : 'center',
					width : 120
				},
				{
					display : "所在java类全路径",
					name : "fct_all_path_namej",
					align : 'left',
					width : 200,
					render:function(item){
						if(item.fct_all_path_namej){
							return "<p title='"+item.fct_all_path_namej+"'>"+item.fct_all_path_namej+"</p>";
						}
					}
				}
		],
		height:'99.9%',
		rownumbers : false,
		checkbox : true,
		isSingleCheck:true,
		rowHeight : 27,
		pageSize:10,
		frozen : false ,
		url:basePath + 'rest/functionManage/functionList'
	});
}

function okWin(){
	var funcObj = null;
	if(functionGridObj.selected.length > 0){
		funcObj = {
				fct_id:functionGridObj.selected[0].fct_id,
			    fct_code:functionGridObj.selected[0].fct_code,
			    fct_name:functionGridObj.selected[0].fct_name
		};
	}
	return funcObj;
}
