var gridManager = null;
$(function(){
	gridManager = $("#processGrid").ligerGrid({
        columns: [
                  {name: 'pk_id',width:0.1,hide:true }
                  ,
                	{display: '服务器编号', name: 'process_code',width:90 }
                	,
                	{display: '服务器名称', name: 'process_name',width:120 }
                	,
//					{display: '进程类型', name: 'process_type',width:150 }
//                	,
                	{display: '服务器IP', name: 'computer_ip',width:150 }
      ],
      height: '100%',
      pageSize: 10,
      rownumbers:true,
      checkbox : false,        
      frozen:false,
      heightDiff : -5,
      usePager:true,
      rowHeight:27,
      url:basePath+"rest/processManager/queryProcessByType?processType="+processType
  });
});

function getSelectData(){
	if(gridManager.selected.length == 0){
		return null;
	}
	return {
		processId:gridManager.selected[0].pk_id,
		processCode:gridManager.selected[0].process_code,
		processName:gridManager.selected[0].process_name,
		computerIp:gridManager.selected[0].computer_ip
	}
}
