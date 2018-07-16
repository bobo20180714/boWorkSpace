var gridManager=null;
$(function (){
//	initToolBarController();
	initGridData();
});

function initToolBarController(){
	$("#toptoolbar").ligerToolBar({
		items:[
	       { text: '新增', id:'add', click: itemclick,img: basePath+'lib/ligerUI/skins/icons/add.gif'},
	       { text: '删除', id:'delete', click: itemclick,img: basePath+'lib/ligerUI/skins/icons/delete.gif'}
		]});
}

function initGridData(){
	gridManager=$("#maingrid").ligerGrid({
	    columns: [
	              	{display: '主键', name: 'jsjg_id',align: 'center',width:0.1,hide:true},
	              	{display: '信息编号', name: 'jsjg_code',align: 'center',width:150},
	              	{display: '信息名称', name: 'jsjg_name',align: 'center',width:150},
	    			{ display: "备注", name: "jsjg_desc",align: 'center',width:300,
	    				render: function(e){
	              			if(e.jsjg_desc != null){
	              				return "<p title='"+ e.jsjg_desc+"'>"+e.jsjg_desc+"</p>";
	              			}
	              		} 	
	    			}
	        			
	    ], 
	    height: '100%', 
	    usePager: false,
	    rownumbers:false,
	    checkbox : true,
	    hideHeadCheckbox:true,
	    rowHeight:27,
		heightDiff : 30,
	    onCheckRow:onCheckRows,
	    onAfterShowData:onAfterShowDatas,
	    url:basePath+'rest/orbitrelated/findAllOrbitrelatedList'
	});
}

function onAfterShowDatas(){
	//获取已经关联的相关信息，并选中
	getRelatedBySatId();
}

function getRelatedBySatId(){
	$.ajax({
		url:basePath+'rest/orbitrelated/getRelatedBySatId',
		data:{
			satId:satId
		},
		async:false,
		success:function(data){
			var haveData = eval('('+data+')');
			for (var i = 0; i < gridManager.rows.length; i++) {
				for (var j = 0; j < haveData.length; j++) {
					if(gridManager.rows[i].jsjg_id == haveData[j].jsjg_id){
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
		addSatRelatedInfo(data.jsjg_id);
	}else{
		//移除关联关系
		removeSatRelatedInfo(data.jsjg_id);
	}
}

function addSatRelatedInfo(jsjgId){
	$.ajax({
		url:basePath+'rest/orbitrelated/addSatRelatedInfo',
		data:{
			satId:satId,
			jsjgId:jsjgId
		},
		async:false,
		success:function(e){
			var eData = eval('('+e+')');
			if(eData.success == "true"){
				Alert.tip("关联成功！");
			}
		}
	});
}

function removeSatRelatedInfo(jsjgId){
	$.ajax({
		url:basePath+'rest/orbitrelated/removeSatRelatedInfo',
		data:{
			satId:satId,
			jsjgId:jsjgId
		},
		async:false,
		success:function(e){
			var eData = eval('('+e+')');
			if(eData.success == "true"){
				Alert.tip("取消关联成功！");
			}
		}
	});
}

var xdlg = null;
//工具条按钮事件
function itemclick(item){
	 if(item.id){
		 switch (item.id){
		 case "add":
			 setCurrWindows();
			 xdlg= parent.$.ligerDialog.open( 
					 { 
						 width: 350,
						 height:265, 
						 title:'新增组播信息',
						 url: basePath+"satBasicInfo/address_add.jsp?satId="+satId,
						 buttons :[
						           { text: '保存', width: 60 ,onclick:function(item, dialog){
						        	   dialog.frame.submitForm();
						           }},
						           { text: '关闭', width: 60, onclick:function(item, dialog){
						        	   dialog.close();
						           }}
						           ]
					 });
			 return;
         case "delete":
         	if (!gridManager.getSelectedRow()) { 
					$.ligerDialog.warn("请至少选择一条数据！");
					return; 
				}
         	var pkId = "";
				for (var i = 0; i < gridManager.selected.length; i++){
					if(i==(gridManager.selected.length-1)) {
						pkId = pkId + gridManager.selected[i].pk_id;				    			
					} else {
						pkId = pkId+ gridManager.selected[i].pk_id + ',';
					}
				}
			  	var url = basePath+'rest/addressManager/delete';
				$.ligerDialog.confirm('确定要删除选中的记录吗？',function (yes){
			    if(yes){
					  $.post(url,{ids:pkId},function(dataObj){
						if (dataObj.success == "true"){
							parent.Alert.tip(dataObj.message);
							gridManager.loadData();
		             	}else parent.Alert.tip(dataObj.message); 
					},"json");
			    }
				});
				return ;
		 }
		 
	 }
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
