var leftGrid = null; 
var rightGrid = null; 
$(function (){ 
	//左边表格
	leftGrid = $("#leftGrid").ligerGrid({
		columns : [ 
       	{ name : 'sat_id',width : 0.1,hide:true },
		    { display : '卫星代号',name : 'sat_code',width : 100 },
			{ display : '卫星名称',name : 'sat_name',width : 120 }
		],
		url:basePath+'rest/LimitController/satList?ug_id='+ug_id,
		height : 325,
		width : 265,
		pageSize : 10,
		rownumbers:false,
		checkbox:true,
		rowHeight:27,
		colDraggable:true,
		rowDraggable:true,
		heightDiff :13,
		frozen:false 
	});
	//右边表格
	rightGrid = $("#rightGrid").ligerGrid({
		columns : [ 
          	{ name : 'sat_id',width : 0.1,hide:true },
		    { display : '卫星代号',name : 'sat_code',width : 100 },
			{ display : '卫星名称',name : 'sat_name',width : 120 }
		],
		height : 355,
		width : 265,
		pageSize : 30,
		rownumbers:false,
		checkbox:true,
		colDraggable:true,
		rowDraggable:true,
		heightDiff :13,
		rowHeight:27,
		usePager:false,
      	frozen:false 
	});
	  setGrightGridData();
});
 

function setGrightGridData(){
	 //获取已经授权的卫星数据
	 $.ajax({
			url:basePath+'rest/LimitController/queryAlreadyGrantSat',
			data:{
				ug_id : ug_id
			},
			async:false,
			success:function(data){
				var jsobj = eval('('+data+')');
				if(jsobj.satList.length > 0){
					//已选择航天器列表
					rightGrid.loadData({"Rows":jsobj.satList});
				}else{
					rightGrid.loadData({"Rows":[]});
				}
			}
	});
}

//向右移
function moveToRight(){
	var sysResourceId = "";
	var grantTypes = "";
	//左边选择的数据
	var selectDatas = leftGrid.selected;
	//右边所有数据
	var rightDatas = rightGrid.rows;
	for ( var i = 0;selectDatas!=null && i < selectDatas.length; i++) {
		var flag = true;
		for ( var j = 0;rightDatas!=null && j < rightDatas.length; j++) {
			//若已选择，不在添加
			if(rightDatas[j].sat_code == selectDatas[i].sat_code){
				flag = false;
				break;
			}
		}
		if(flag){
			sysResourceId = sysResourceId + "," + selectDatas[i].sat_id;
			grantTypes = grantTypes + ",0";
		}
	}
	if(sysResourceId){
		sysResourceId = sysResourceId.substring(1, sysResourceId.length);
		grantTypes = grantTypes.substring(1, grantTypes.length);
		//增加授权
		resourceAuthorizationAdd(sysResourceId,grantTypes);
		//右边表格刷新
		setGrightGridData();
		
		leftGrid.loadData();
	}
}
//全部向右移
function moveAllToRight(){
	var sysResourceId = "";
	var grantTypes = "";
	//左边选择的数据
	var selectDatas = leftGrid.rows;
	//右边所有数据
	var rightDatas = rightGrid.rows;
	for ( var i = 0;selectDatas!=null && i < selectDatas.length; i++) {
		var flag = true;
		for ( var j = 0;rightDatas!=null && j < rightDatas.length; j++) {
			//若已选择，不在添加
			if(rightDatas[j].sat_code == selectDatas[i].sat_code){
				flag = false;
				break;
			}
		}
		if(flag){
			sysResourceId = sysResourceId + "," + selectDatas[i].sat_id;
			grantTypes = grantTypes + ",0";
		}
	}
	if(sysResourceId){
		sysResourceId = sysResourceId.substring(1, sysResourceId.length);
		grantTypes = grantTypes.substring(1, grantTypes.length);
		//增加授权
		resourceAuthorizationAdd(sysResourceId,grantTypes);
		//右边表格刷新
		setGrightGridData();
		leftGrid.loadData();
	}
}
//向左移
function moveToLeft(){
	var sysResourceId = "";
	var grantTypes = "";
	//需要移除的数据
	var selectDatas = rightGrid.selected;
	for ( var i = 0;selectDatas!=null && i < selectDatas.length; i++) {
		sysResourceId = sysResourceId + "," + selectDatas[i].sat_id;
		grantTypes = grantTypes + ",0";
	}
	if(sysResourceId){
		sysResourceId = sysResourceId.substring(1, sysResourceId.length);
		grantTypes = grantTypes.substring(1, grantTypes.length);
	}
	//移除授权
	deleteDataGrant(sysResourceId,grantTypes);
	//右边表格刷新
	setGrightGridData();
	leftGrid.loadData();
	
}
//全部向左移
function moveAllToLeft(){ 
	var sysResourceId = "";
	var grantTypes = "";
	//需要移除的数据
	var selectDatas = rightGrid.rows;
	for ( var i = 0;selectDatas!=null && i < selectDatas.length; i++) {
		sysResourceId = sysResourceId + "," + selectDatas[i].sat_id;
		grantTypes = grantTypes + ",0";
	}
	if(sysResourceId){
		sysResourceId = sysResourceId.substring(1, sysResourceId.length);
		grantTypes = grantTypes.substring(1, grantTypes.length);
	}
	//移除授权
	deleteDataGrant(sysResourceId,grantTypes);
	//右边表格刷新
	setGrightGridData();
	leftGrid.loadData();
}

/**
 * 增加授权
 */
function resourceAuthorizationAdd(sysResourceIds,grantTypes){
	 $.ajax({
			url:basePath+'rest/LimitController/resourceAuthorizationAdd',
			data:{
				sys_resource_id:sysResourceIds,
				ug_id:ug_id,
				grant_type:grantTypes,
				grant_manage_type:"0",
				end_time:""
			},
			async:false,
			success:function(data){
				var jsobj = eval('('+data+')');
				if(jsobj.success == "true"){
					parent.Alert.tip("授权成功！");
				}else{
					parent.Alert.tip("授权失败，请联系管理员！");
				}
			}
		});
}

/**
 * 移除授权
 */
function deleteDataGrant(sysResourceIds,grantTypes){
	$.ajax({
		url:basePath+'rest/LimitController/resourceAuthorizationDelete',
		data:{
			sys_resource_id:sysResourceIds,
			ug_id:ug_id,
			grant_type:grantTypes
		},
		async:false,
		success:function(data){
			var jsobj = eval('('+data+')');
			if(jsobj.success == "true"){
				parent.Alert.tip("移除数据权限成功！");
			}else{
				parent.Alert.tip("移除数据权限失败，请联系管理员！");
			}
		}
	});
}

