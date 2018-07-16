var node = "";
var id = "";
var type = getParam("type");
var node_name = getParam("node_name");
var page="";
var pagesize=20;
var gridManager1 = null ;
var gridManager2 = null ;
//var key = $("#txtKey").val();
//alert(key);
//var gridManager1="";
//var currentTreeNode;//当前点击右键的节点
$(function (){
	initBtn();
	initGridData();
	eventBind();
//	sch1_loadData();
	$("#layout1").ligerLayout({ leftWidth: 190,allowLeftCollapse:false,heightDiff:0,space:0/*, onHeightChanged: f_heightChanged */});
	var height = $(".l-layout-center").height();
});
/**
 * 给页面中节点绑定事件
 */
function eventBind(){
	$("#btnOK1").bind('click',function(){
		sch1_loadData();
		});
	$("#btnOK2").bind('click',function(){
		sch2_loadData();
		});
	
}

function initGridData(){
	gridManager1=$("#maingrid1").ligerGrid({
	title: '未分配参数',	
    columns: [ 
              	{display: '参数id', id: 'tm_param_id',name:'tm_param_id',type:"text",hide:true,width:0.1},
               	{display: '参数编码', id: 'tm_param_code',name:'tm_param_code',type:"text",newline: false,width:140},
              	{display: '参数名称', id: 'tm_param_name', name: 'tm_param_name',type:"text",newline: false,align: 'center',width:190,
               		render:function(item){
               			if(item.tm_param_name != null && item.tm_param_name.length > 12){
               				return '<div title="'+item.tm_param_name+'">'+item.tm_param_name.substring(0,12)+'</div>';
               			}
               			return '<div title="'+item.tm_param_name+'">'+item.tm_param_name+'</div>';
               		}
               	},
    			{display: '参数序号', id: 'tm_param_num',name:'tm_param_num',type:"text",newline: false,width:130}
    ],
    dataAction:'server',
    width: '500', 
    height: '460', 
    pageSize: 20,
    rownumbers:true,
    checkbox : true,
    frozen: false, 
    //应用灰色表头
    cssClass: 'l-grid-gray',
    url:basePath+'rest/tmparams/findsubnoparambysubquerypage?id='+getParam("id")+"&owner_id="+cur_id
    
});
	gridManager2=$("#maingrid2").ligerGrid({
		title: '已分配参数',
	    columns: [ 
	              	{display: '参数id', id: 'tm_param_id',name:'tm_param_id',type:"text",hide:true,width:0.1},
	               	{display: '参数编码', id: 'tm_param_code',name:'tm_param_code',type:"text",newline: false,width:140},
	              	{display: '参数名称', id: 'tm_param_name', name: 'tm_param_name',type:"text",newline: false,align: 'center',width:190,
	               		render:function(item){
                   			if(item.tm_param_name != null && item.tm_param_name.length > 12){
                   				return '<div title="'+item.tm_param_name+'">'+item.tm_param_name.substring(0,12)+'</div>';
                   			}
                   			return '<div title="'+item.tm_param_name+'">'+item.tm_param_name+'</div>';
	               		}
	               	},
	    			{display: '参数序号', id: 'tm_param_num',name:'tm_param_num',type:"text",newline: false,width:130}
	    ],
//	    top :200,
	    dataAction:'server',
	    width: '500', 
	    height: '460', 
	    pageSize: 20,
	    rownumbers:true,
	    checkbox : true,
	    frozen: false, 
	    //应用灰色表头
	    cssClass: 'l-grid-gray', 
	    url:basePath+'rest/tmparams/findsubhasparambysubquerypage?id='+cur_id
	    
	});
}


/**
 * 按钮初始化
 */
function initBtn(){
	$("#searchbtn1").ligerButton({ click: function ()
    {
		l_loadData();
    }  
	});
	
	$("#searchbtn2").ligerButton({ click: function ()
	    {
		r_loadData();
	    }  
		});
	
	$("#assign_all").ligerButton({ 
		width:30,
		text: '>>',
		click: function ()
	    {
			loadData1();
			gridManager1.loadData();
			gridManager2.loadData();
			parent.currWin.grid_refresh();
	    }  
		});
	$("#param_assign").ligerButton({
		width:30,
		text: '>',
		click: function ()
	    {
			loadData2();
			gridManager1.reload();
			gridManager2.loadData();
			parent.currWin.grid_refresh();
	    }  
		});
	$("#del_param").ligerButton({ 
		width:30,
		text: '<',
		click: function ()
	    {
			loadData3();
			gridManager1.reload();
			gridManager2.reload();
			parent.currWin.grid_refresh();
	    }  
		});
	$("#del_all").ligerButton({ 
		width:30,
		text: '<<',
		click: function ()
	    {
			loadData4();
			gridManager1.loadData();
			gridManager2.loadData();
			parent.currWin.grid_refresh();
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

//未分配参数条件查询实现
function sch1_loadData(){
	var key = $("#txtKey1").val();
	gridManager1.setParm("key",key);
	gridManager1.loadData();
}


//已分配参数条件查询实现
function sch2_loadData(){
	var key = $("#txtKey2").val();
	gridManager2.setParm("key1",key);
	gridManager2.loadData();
}
//全部分配
function loadData1(){
	var satid = getParam("id");
	var key = $("#txtKey1").val();
	$.ajax({
		url:basePath+"rest/tmparams/paramdroptorightall",
		async:false,
		data:{
			id:satid,
			key:key,
			owner_id:cur_id
		}
	});
}

function loadData2(){
	var records = gridManager1.getSelecteds();
	var ids=[];
	for (var i = 0; i < records.length; i++){
		if(i==(records.length-1)) {
			ids = ids + records[i].tm_param_id;				    			
		} else {
			ids = ids+ records[i].tm_param_id + ',';
		}
	}
	var key='';
	$.ajax({
		url:basePath+"rest/tmparams/paramdrop",
		async:false,
		data:{
			ids:ids,
			key:key,
			owner_id:cur_id
		}
	});
}

function loadData3(){
	var records = gridManager2.getSelecteds();
	var ids=[];
	for(var i=0;i<records.length;i++){
		ids=ids+"'"+records[i].tm_param_id+"'"+",";
	}
	var key='';
	$.ajax({
		url:basePath+"rest/tmparams/paramdroptoleft",
		async:false,
		data:{
			ids:ids,
			key:key,
			owner_id:cur_id
		}
	});
}

function loadData4(){
	var key='';
	$.ajax({
		url:basePath+"rest/tmparams/paramdroptoleftall",
		async:false,
		data:{
			id:id,
			key:key,
			owner_id:cur_id
		}
	});
}


/**
 * 加载未分配遥测参数
 */
function l_loadData(){
	var key="";
	$.post(
			basePath+"rest/tmparams/findsubnoparambysubquerypage",
			{
				id:id,
				pagesize:20,
				page:page
//				key:key,
//				owner_id:id
			},"json");
}
/**
 * 加载已分配遥测参数
 */
function r_loadData(){
	var id = getParam("cur_id");
	$.post(
			basePath+"rest/tmparams/findsubnoparambysubquerypage",
			{
				id:id,
				page:page,
				pagesize:pagesize
			},"json");
}

function l_reload(){
	var id = getParam("id");
//	var key='';
	$.post(
			basePath+"rest/tmparams/findsubnoparambysubquerypage",
			{
				id:id,
				pagesize:20,
				page:page
//				key:key,
//				owner_id:id
			},"json");
}

function r_reload(){
	var id = getParam("cur_id");
	$.post(
			basePath+"rest/tmparams/findsubnoparambysubquerypage",
			{
				id:id,
				page:page,
				pagesize:pagesize
			},"json");
}


