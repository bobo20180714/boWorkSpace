var formData = null; 
var leftGrid = null; 
var rightGrid = null; 
$(function (){ 
	//左边表格
	leftGrid = $("#leftGrid").ligerGrid({
		columns : [ 
       	{ name : 'sys_resource_id',width : 0.1,hide:true },
		    { display : '卫星代号',name : 'code',width : 100 },
			{ display : '卫星名称',name : 'name',width : 120 }
		],
//		url:basePath+'rest/satinfoLimit/satList',
		height : 265,
		width : 280,
		usePager:false,
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
	/*rightGrid = $("#rightGrid").ligerGrid({
		columns : [ 
           	{ name : 'sys_resource_id',width : 0.1,hide:true },
		    { display : '卫星代号',name : 'code',width : 100 },
			{ display : '卫星名称',name : 'name',width : 120 }
		],
		height : 272,
		width : 265,
		rownumbers:false,
		checkbox:true,
		colDraggable:true,
		rowDraggable:true,
		heightDiff :13,
		rowHeight:27,
		usePager:false,
      	frozen:false 
	});*/
        
	 //表单
	 formData = $("#form1").ligerForm({
			labelAlign:'right',
	        inputWidth: 280, 
	        labelWidth: 90, 
	        space: 20,
	        validate:true,
	        fields: [
				{ display: "航天器组名称", name: "pageName",id:"pageName",
				    newline: true, type: "text" ,validate:{required:true ,maxlength:20}
	          	}
	        ]
	  });
	   
	 //设置未配置的卫星
	 setLeftGridData();
	  // 赋值
	  setFormData();
	  
});
 
function setLeftGridData(){
	 $.ajax({
			url:basePath+'rest/satinfoLimit/findgrantusergroupequipmenttree?sys_resource_id=-1',
			async:false,
			success:function(data){
				var jsobj = eval('('+data+')');
				//已选择航天器列表
				leftGrid.loadData({"Rows":jsobj});
			}
	});
}

//赋值
function setFormData(){
	//判断打开的是否是编辑页面
	if(opt == 'modify'){
		  //获取航天器组中已选择的航天器列表
		 $.ajax({
				url:basePath+'rest/alarmInfo/queryAlarmInfo',
				data:{
					pageId : pageId
				},
				async:false,
				success:function(data){
					var jsobj = eval('('+data+')');
					//航天器组名
					$.ligerui.get('pageName').setValue(jsobj.pageName);
					var haveData = jsobj.satList;
					for (var i = 0; i < leftGrid.rows.length; i++) {
						for (var j = 0; j < haveData.length; j++) {
							if(leftGrid.rows[i].sys_resource_id == haveData[j].sys_resource_id){
								leftGrid['select'](leftGrid.rows[i]);
								break;
							}
						}
					}
				}
		});
	 }
}
 
 /**
 * 保存
 * 
 * @returns {Boolean}
 */
function save(){
	 var returnData = null;
	 var vboolean = $("#form1").valid();
	 if (!vboolean) {
		 return false;
	 }
	 if(leftGrid.getCheckedRows() <= 0){
		 parent.Alert.tip("请勾选卫星数据！");
		 return false;
	 }
	 //获取选择的航天器设备ID
	 var satIDArr = new Array();
	 for( var i=0; i<leftGrid.getCheckedRows().length;i++){
		 satIDArr.push(leftGrid.getCheckedRows()[i].sys_resource_id)
	 }
	 //航天器组名
	 var pageName =  $.ligerui.get('pageName').getValue();
	 //判断名称是否存在
	if(judgePageIsexit(pageName,pageId)){
		 parent.Alert.tip("航天器组名称已经存在！");
		return;
	}
	 
	 var reqData = {
				pageId:pageId,
				pageName:pageName,
				satIdArr:satIDArr
			};
	 var pageInfo = JSON.stringify(reqData);
	 $.ajax({
		url:basePath+'rest/alarmInfo/savePageAlam',
		data:{
			pageInfo:pageInfo
		},
		async:false,
		success:function(data){
			var jsobj = eval('('+data+')');
			jsobj.pageName = pageName;
			if(pageId !=null && pageId != "" ){
				jsobj.pageId = pageId;
			}
			returnData = jsobj;
			returnData.pageName = pageName;
			returnData.satsid = satIDArr.join(",");
		}
	});
	return returnData;
}

/**
 * 判断名称是否存在
 */
function judgePageIsexit(pageName,pageId){
	var flag = false;
	 $.ajax({
			url:basePath+'rest/alarmInfo/judgePageIsexit',
			data:{
				pageName:pageName,
				pageId:pageId
			},
			async:false,
			success:function(data){
				var jsobj = eval('('+data+')');
				if(jsobj.success == "true"){
					flag = true;
				}
			}
		});
	 return flag;
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
			if(rightDatas[j].code == selectDatas[i].code){
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
//全部向右移
function moveAllToRight(){ 
	rightGrid.loadData({"Rows":leftGrid.rows});
      
}
//向左移
function moveToLeft(){
	rightGrid.deleteSelectedRow();
}
//全部向左移
function moveAllToLeft(){ 
	rightGrid.loadData({"Rows":[]}) 
}

