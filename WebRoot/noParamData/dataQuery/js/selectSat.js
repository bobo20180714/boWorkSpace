var gridManager = null;
var formData1 = null;
$(function(){
	initQueryForm();
    $("#searchbtn").ligerButton({type:'one',click: search_btn_click});	
	$("#resetbtn").ligerButton({ type:'two',click: clearForm}); 
	gridManager = $("#satGrid").ligerGrid({
        columns: [
                  {name: 'sys_resource_id',width:0.1,hide:true }
                  ,
                	{display: '任务代号', name: 'mid',width:90 }
                	,
                	{display: '卫星编号', name: 'code',width:120 }
                	,
					{display: '卫星名', name: 'name',width:150 }
      ],
      height: '98%',
      pageSize: 10,
      rownumbers:true,
      checkbox : false,        
      frozen:false,
      usePager:true,
      rowHeight:27,
      url:basePath+"rest/satinfoLimit/satList"
  });
});

function initQueryForm(){
    formData1 = $("#form1").ligerForm({
    		labelAlign:'right',
        	inputWidth: 120, 
            labelWidth: 80, 
            space: 5,
            validate:true,
            fields: [
				{display: '关键字', id: 'sat_id',name:'sat_id',type:"text",newline: false},
			]
    });
}

//查找按钮的单击事件处理
function search_btn_click(){
	var queryData = formData1.getData();
	var sat_id = queryData.sat_id;	
	gridManager.set('parms', {
		key:sat_id
	});
	gridManager.set({newPage:1});
	gridManager.loadData();
}

//Clear the search form
function clearForm(){
	formData1.setData({
		sat_id:""
	});
}

function getSelectData(){
	if(gridManager.selected.length == 0){
		return null;
	}
	return {
		satId:gridManager.selected[0].sys_resource_id,
		satName:gridManager.selected[0].name,
		satCode:gridManager.selected[0].code,
		satMid:gridManager.selected[0].mid
	}
}
