var gridManager=null;

//超时时长
var overTimeArr = [{id:'1',text:'1'},{id:'2',text:'2'},{id:'3',text:'3'},{id:'4',text:'4'},{id:'5',text:'5'},{id:'6',text:'6'},
					{id:'7',text:'7'},{id:'8',text:'8'},{id:'9',text:'9'},{id:'10',text:'10'},{id:'20',text:'20'},
					{id:'30',text:'30'},{id:'50',text:'50'},{id:'100',text:'100'}];
//计算失败次数
var computCountArr = [{id:'1',text:'1'},{id:'2',text:'2'},{id:'3',text:'3'},{id:'4',text:'4'},{id:'5',text:'5'},{id:'6',text:'6'},
					{id:'7',text:'7'},{id:'8',text:'8'},{id:'9',text:'9'},{id:'10',text:'10'}];

var dataArr = [{id:'0',text:'是'},{id:'1',text:'否'}];

$(function (){
	initGridData();
});

function initGridData(){
	gridManager=$("#maingrid").ligerGrid({
	    columns: [
	              	{display: '主键', name: 'pk_id',align: 'center',width:0.1,hide:true},
	              	{display: '计算模块名称', name: 'compute_name',align: 'center',width:150},
	    			{ display: "java类名称", name: "compute_desc",align: 'center',width:150},
	              	{display: '超时时间', name: 'over_time',align: 'center',width:80,
	    				editor:{
	    					type:'select',
	    					data:overTimeArr
	    				}
	              	},
	              	{display: '最大失败次数', name: 'compute_count',align: 'center',width:80,
	    				editor:{
	    					type:'select',
	    					data:computCountArr
	    				}},
	              	{display: '结果时否存库', name: 'is_save_result',align: 'center',width:80,
	    					render:function(item){
	    						if(item.is_save_result){
    			        			  for(var i = 0;i<dataArr.length;i++){
    			        				  if(item.is_save_result == dataArr[i].id){
    					        			   return dataArr[i].text;
    					        		   }
    			        			  }
	    						}
	    					},
		    				editor:{
		    					type:'select',
		    					data:dataArr
		    				}},
	              	{display: '结果是否组播', name: 'is_multicast',align: 'center',width:80,
		    					render:function(item){
		    						if(item.is_multicast){
	    			        			  for(var i = 0;i<dataArr.length;i++){
	    			        				  if(item.is_multicast == dataArr[i].id){
	    					        			   return dataArr[i].text;
	    					        		   }
	    			        			  }
		    						}
		    					},
			    				editor:{
			    					type:'select',
			    					data:dataArr
			    				}}
	        			
	    ], 
	    height: '100%', 
	    rownumbers:false,
	    enabledEdit: true, 
//	    pageSize:10,
	    checkbox : true,
	    usePager:false,
	    rowHeight:27,
	    onCheckRow:onCheckRows,
	    onAfterShowData:onAfterShowDatas,
	    url:basePath+'rest/ComputeFunc/queryAllComputeList'
	});
}

function onAfterShowDatas(){
	//获取已经关联的相关信息，并选中
	getRelatedByTypeId();
}

function getRelatedByTypeId(){
	$.ajax({
		url:basePath+'rest/ComputeType/getRelatedByTypeId',
		data:{
			satId:satId,
			typeId:typeId
		},
		async:false,
		success:function(data){
			var haveData = eval('('+data+')');
			for (var i = 0; i < gridManager.rows.length; i++) {
				for (var j = 0; j < haveData.length; j++) {
					if(gridManager.rows[i].pk_id == haveData[j].compute_func_id){
						gridManager['select'](gridManager.rows[i]);
						break;
					}
				}
			}
		}
	});
}

function onCheckRows(flag,data){
	if(flag){
		//添加关联关系
		addSatRelatedInfo(data);
	}else{
		//移除关联关系
		removeSatRelatedInfo(data.pk_id);
	}
}

function addSatRelatedInfo(rowData){
	$.ajax({
		url:basePath+'rest/ComputeType/addRelation',
		data:{
			typeId:typeId,
			computeId:rowData.pk_id,
			overTime:rowData.over_time,
			computeCount:rowData.compute_count,
			isSaveResult:rowData.is_save_result,
			isMulticast:rowData.is_multicast
		},
		async:false
	});
}

function removeSatRelatedInfo(computeId){
	$.ajax({
		url:basePath+'rest/ComputeType/deleteRelation',
		data:{
			typeId:typeId,
			computeId:computeId
		},
		async:false
	});
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}
//关闭对话框刷新gridManager
function closeDlgAndReload(){
    f_closeDlg();
    gridManager.loadData();

}
function grid_reload(){
	gridManager.loadData();
}
//关闭对话框
function f_closeDlg(){
    xdlg.close();
}
//关闭对话框
function c_closeDlg(){
    ckxq.close();
}
