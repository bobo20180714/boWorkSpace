var leftGrid = null;
var rightGrid = null;
$(function(){
	$("#key_2").ligerTextBox({
		width:90
	});
	
	//未选择参数列表
	leftGrid = $("#paramGrid").ligerGrid({
 	   columns: [
  	            {display: 'tm_param_id', name: 'tm_param_id',align: 'center',hide:true ,width:1},
 	            {display: '名称', name: 'tm_param_name',align: 'center',width:120,
 	            	render: function(item){
 						if(item.tm_param_name != undefined){
 							return "<span title = "+item.tm_param_name+">"+item.tm_param_name+"</span>";
 						}
 	            	} 
  	            },
 	            {display: '编号', name: 'tm_param_code',align: 'center',width:120}
       ], 
       url:basePath+'rest/baseInfoQuery/queryParamsInfo',
       height: 366,
       heightDiff: -5,
       width:274,
       pageSize: 10,
       rownumbers:false,
       checkbox : true,
       frozen:false,
       dataAction:'server'
     }); 
	//已选择参数列表
	rightGrid = $("#paramGrid2").ligerGrid({
		columns: [
		          {display: 'tm_param_id', name: 'tm_param_id',align: 'center',hide:true ,width:0.1},
		          {display: '参数名称', name: 'tm_param_name',align: 'center',width:120,
		        	  render: function(item){
		        		  if(item.tm_param_name != undefined){
	 							return "<span title = "+item.tm_param_name+">"+item.tm_param_name+"</span>";
	 						}
		        	  } 
		          },
		          {display: '参数编号', name: 'tm_param_code',align: 'center',width:120}
		          ], 
		          heightDiff: 12,
		          width:274,
		          height: 378,
		          rownumbers:false,
		          rowHeight:27,
		          checkbox : true,
		          frozen:false,
		          usePager:false
	}); 
	
	leftGrid.set('parms', {
		owner_id:satId
	});
	leftGrid.set({newPage:1});
	leftGrid.loadData();
});

/**
 * 参数查询
 */
function doSearch_2(){
	leftGrid.set('parms', {
		owner_id:satId,
		key:$("#key_2").val()
	});
	leftGrid.set({newPage:1});
	leftGrid.loadData();
}


//向右移
function moveToRight(){
	//左边选择的数据
	var selectDatas = leftGrid.selected;
	//右边所有数据
	var rightDatas = rightGrid.rows;
	for ( var i = 0;selectDatas!=null && i < selectDatas.length; i++) {
		var flag = true;
		for ( var j = 0;rightDatas!=null && j < rightDatas.length; j++) {
			//若已选择，不在添加
			if(rightDatas[j].tm_param_id == selectDatas[i].tm_param_id){
				flag = false;
				break;
			}
		}
		if(flag){
			//添加到右边
			rightGrid.addRows(selectDatas[i]);
		}
	}
}
//向左移
function moveToLeft(){
	rightGrid.deleteSelectedRow();
}

function okWin(){
	var result = {};
	if(rightGrid.rows.length > 0){
		var paramArr = null;
		paramArr = new Array();
		for (var i = 0; i < rightGrid.rows.length; i++) {
			var rowData = rightGrid.rows[i];
			paramArr.push({
				"tm_param_id":rowData.tm_param_id,
				"tm_param_name":rowData.tm_param_name,
				"tm_param_code":rowData.tm_param_code
			});
		}
		result.paramArr = paramArr;
	}
	return result;
}