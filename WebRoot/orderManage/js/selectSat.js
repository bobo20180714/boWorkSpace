var gridManager = null;
$(function(){
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
