var pWin = parent.parent.currWinFront;
var paramManager = null;
var flyerTree = null;
var satId = "";//当前所选航天器ID
var satName = "";//当前所选航天器NAME
var satCode = "";//当前所选航天器code
var sys_resource_id = "";//当前选择节点ID
$(function(){
	$("#key_1").ligerTextBox({
		width:70
	});
	$("#key_2").ligerTextBox({
		width:70
	});
	
	//飞行器树
	flyerTree = $("#flyerTree").ligerTree({
		url:basePath+'rest/satinfoLimit/findSatTree',
        checkbox: false,
        slide: false,
        isExpand:false,
        nodeWidth: 120,
		height: "100%",
//		nodeWidth:"100%",
		topIcon:'sat',
		parentIcon: null,
        childIcon: null,
        textFieldName: 'name',
    	idFieldName: 'sys_resource_id',
    	parentIDFieldName:'owner_id',
    	onSelect:onSelectTree,
        onCancelselect : onCancelselectTree
    });
	
	//参数列表
	paramManager = $("#paramGrid").ligerGrid({
 	   columns: [
  	            {display: 'tm_param_id', name: 'tm_param_id',align: 'center',hide:true ,width:0.1},
 	            {display: '名称', name: 'tm_param_name',align: 'center',width:130,
 	            	render: function(item){
 						var data=item.tm_param_name;
 		            	if(data != undefined){
 							data = data.substr(0,18);
 		            		var h = "<span style ='height:28px;line-height:28px;color:black;' title = "+item.tm_param_name+">"+data+"</span>";
 		             	 	return h;
 		            	}
 	            	} 
  	            },
 	            {display: '代号', name: 'tm_param_code',align: 'center',width:105}
       ], 
       url:basePath+'rest/tmparams/findgrantusergrouptmparambyid?querybykey=true',
       height: 235,
       width:300,
       pageSize: 10,
       rownumbers:true,
       checkbox : false,
       frozen:false,
       //应用灰色表头
       cssClass: 'l-grid-gray',
       dataAction:'server',
       onlyOneCheck: true/*,
       onDblClickRow : onDblClickRow*/
     }); 
});

/**
 * 表格双击事件
 */
function onDblClickRow(obj){
	pWin.closeWin_2(satCode,satName,obj.tm_param_code,obj.tm_param_name);
}

/**
 * 点击树节点事件
 */
function onSelectTree(obj){
	satId = obj.data.sat_id;
	satName = obj.data.sat_name;
	satCode = obj.data.sat_code;
	sys_resource_id = obj.data.sys_resource_id;
	paramManager.set('parms', {
		sat_id:sys_resource_id
	});
	paramManager.set({newPage:1});
	paramManager.loadData();
}

/**
 * 树节点取消事件
 */
function onCancelselectTree(){
	paramManager.set('parms', {
		sat_id:""
	});
	paramManager.set({newPage:1});
	paramManager.loadData();
}

/**
 * 航天器查询
 */
function doSearch_1(){
	var key_1 = $("#key_1").val();
	$.ajax({
		url:basePath+'rest/satinfoLimit/findSatTree',
		data:{
			key:key_1
		},
		async:false,
		success:function(data){
			var jsobj=eval('('+data+')');
			flyerTree.clear();
			flyerTree.setData(jsobj);
		}
	});
}

/**
 * 参数查询
 */
function doSearch_2(){
	paramManager.set('parms', {
		sat_id:sys_resource_id,
		tm_param_name:$("#key_2").val(),
		tm_param_code:$("#key_2").val(),
		tm_param_type:$("#key_2").val()
	});
	paramManager.set({newPage:1});
	paramManager.loadData();
}