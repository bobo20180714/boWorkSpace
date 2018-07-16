var node = "";
var id = "";
var type = getParam("type");
var node_name = getParam("node_name");
var page="";
var pagesize=20;
var gridManager1 ;
var gridManager2 ;

$(function (){
	initBtn();
	initGridData();
//	sch1_loadData();
	$("#layout1").ligerLayout({ leftWidth: 190,allowLeftCollapse:false,heightDiff:0,space:0/*, onHeightChanged: f_heightChanged */});
	var height = $(".l-layout-center").height();
});

function initGridData(){
	gridManager1=$("#maingrid1").ligerGrid({
	title: '未分配角色',	
    columns: [ 
              	{display: '角色id', id: 'role_id',name:'role_id',type:"text",hide:true,width:'0'},
    			{display: '角色名称', id: 'role_name',name:'role_name'}
    ],
//    top :200,
    dataAction:'server',
//    width: '100%', 
    height: 440, 
    pageSize: 20,
    rownumbers:true,
    checkbox : true,
    //应用灰色表头
//    cssClass: 'l-grid-gray',
    url:basePath+'rest/newrole/findnorole?org_id='+getParam("id")
    
});
	gridManager2=$("#maingrid2").ligerGrid({
		title: '已分配角色',
	    columns: [
	              	{display: '角色id', id: 'role_id',name:'role_id',type:"text",hide:true,width:'0'},
	    			{display: '角色名称', id: 'role_name',name:'role_name',type:"text"}
	    ],
//	    top :200,
	    dataAction:'server',
//	    width: '100%', 
	    height: 440, 
	    pageSize: 20,
	    rownumbers:true,
	    checkbox : true,
	    //应用灰色表头
//	    cssClass: 'l-grid-gray', 
	    url:basePath+'rest/newrole/findhasrole?org_id='+getParam("id")
	    
	});
}


/**
 * 按钮初始化
 */
function initBtn(){
	
	$("#role_assign").ligerButton({
		width:30,
		text: '>',
		click: function ()
	    {
			var records = gridManager1.getSelectedRows();
           	var ids = "";
				for (var i = 0; i < records.length; i++){
					if(i==(records.length-1)) {
						ids = ids  + records[i].role_id;				    			
					} else {
						ids = ids+ records[i].role_id +',';
					}
				}
			$.post(
			basePath+"rest/newrole/usergrouproleupdate",
			{
				ids:ids,
				org_id:getParam("id"),//此org_id的实际值为user_id
				pagesize:20,
				page:1,
			},"json");
			gridManager1.deleteRange(records);
			gridManager1.loadData(true);
			gridManager2.reload() ;
			Alert.tip("授权成功！");
	    }  
		});
	$("#del_role").ligerButton({ 
		width:30,
		text: '<',
		click: function ()
	    {
			var records = gridManager2.getSelectedRows();
           	var ids = "";
			for (var i = 0; i < records.length; i++){
				if(i==(records.length-1)) {
					ids = ids  + records[i].role_id;				    			
				} else {
					ids = ids+ records[i].role_id +',';
				}
			}
			$.post(
			basePath+"rest/newrole/usergrouproledelete",
			{
				ids:ids,
				org_id:getParam("id"),//此org_id的实际值为user_id
				pagesize:20,
				page:1,
			},"json");
			gridManager2.deleteRange(records);
			gridManager1.loadData(true);
			gridManager2.reload() ;

			Alert.tip("移除角色成功！");
	    }  
		});
}
/**
*
*获取url里的参数
*@param 参数名
*/
function getParam(str){
	var reg = new RegExp("(^|&)"+str+"=([^&]*)(&|$)","i");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null){
		return unescape(r[2]);
	}
	return null;
}
//function sch1_loadData(){
//	var key = $("#txtKey1").val();
//	var id=getParam("id");
////	alert(id);
//	$.post(
//			basePath+"rest/orguser/findgrantusergrouptmparamquerypage",
//			{
//				id:id,
//				pagesize:20,
//				page:1,
//				key:key
//			},"json");
//	
//}


//分配
function loadData2(){
	var records = gridManager1.getSelecteds();
	var ids=[];
	for(var i=0;i<records.length;i++){
		ids=ids+"'"+records[i].user_id+"'"+",";
	}
	$.post(
			basePath+"rest/newrole/usergrouproleupdate",
			{
				ids:ids
			},"json");
	loadData();
	l_reload();
	r_reload();
}
//移除
function loadData3(){
	var records = gridManager2.getSelecteds();
	var ids="";
	for(var i=0;i<records.length;i++){
		ids=ids+"'"+records[i].tm_param_id+"'"+",";
	}
	var id = getParam("id");
	var key='';
	$.post(
			basePath+"rest/orguser/paramdroptoleft",
			{
				ids:ids,
				key:key,
				owner_id:id
			},"json");
	l_reload();
	r_reload();
}


/**
 * 保存按钮点击事件
 */
function save(){
	var recs= gridManager2.getData();
	var ids="";
	for(var i=0;i<recs.length;i++){
		if(i==recs.length-1){
			ids = ids + recs[i].role_id;
		}else{
			ids = ids + recs[i].role_id +",";	
		}
	}
	$.post(basePath+"rest/newrole/usergrouproleupdate",
			{org_id:getParam("id"),ids:ids},
			function(dataObj){
				if (dataObj.success == "true"){
					parent.Alert.tip("授权成功!");
					parent.currWin.closeDlg();
					} 
				},"json");
}

