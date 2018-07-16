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
//	$("#layout1").ligerLayout({ leftWidth: 190,allowLeftCollapse:false,heightDiff:0,space:0/*, onHeightChanged: f_heightChanged */});
//	var height = $(".l-layout-center").height();
});

function initGridData(){
	gridManager1=$("#maingrid55").ligerGrid({
	id:'grid1',
	title: '未分配角色',	
    columns: [ 
              	{display: '角色id', id: 'role_id',name:'role_id',type:"text",hide:true,width:'0'},
    			{display: '角色名称', id: 'role_name',name:'role_name'}
    ],
    dataAction:'server',
    width: 310, 
    height: 440, 
    pageSize: 20,
    rownumbers:true,
//    checkbox : true,
    //应用灰色表头
//    cssClass: 'l-grid-gray',
    frozen:false,
    url:basePath+'rest/newrole/findnorole?org_id='+getParam("id")
    
});
	gridManager2=$("#maingrid2").ligerGrid({
		title: '已分配角色',
	    columns: [
	              	{display: '角色id', id: 'role_id',name:'role_id',type:"text",hide:true,width:'0'},
	    			{display: '角色名称', id: 'role_name',name:'role_name',type:"text"}
	    ],
	    dataAction:'server',
//	    width: '100%', 
	    width: 310, 
	    height: 440, 
	    pageSize: 20,
	    rownumbers:true,
//	    checkbox : true,
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
//		space:20,
		text: '>',
		click: function ()
	    {
			var rec = gridManager1.getSelectedRows();
			role_id = rec[0].role_id;
			$.post(
			basePath+"rest/newrole/usergrouproleupdate",
			{
				ids:role_id,
				org_id:getParam("id"),
				pagesize:20,
				page:1,
			}/*,function(data){
				if(data.success=="true"){
					parent.Alert.tip(data.message);
				}else $.Alert.tip(data.message);	
			}*/,"json");
			gridManager1.loadData();
			gridManager2.loadData();
	    }  
		});
	$("#del_role").ligerButton({ 
		width:30,
//		space:10,
		text: '<',
		click: function ()
	    {
			var rec = gridManager2.getSelectedRows();
			role_id = rec[0].role_id;
			$.post(
			basePath+"rest/newrole/usergrouproledelete",
			{
				ids:role_id,
				org_id:getParam("id"),
				pagesize:20,
				page:1,
			},"json");
			gridManager1.loadData();
			gridManager2.loadData();
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

/**
*
*保存事件
*
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
					parent.Alert.tip(dataObj.message);
					parent.currWin.closeDlg();
					}else parent.Alert.tip(dataObj.message);
				},"json");
}