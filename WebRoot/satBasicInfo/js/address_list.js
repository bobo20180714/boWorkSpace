var gridManager=null;
var typeData = [{id:"1",text:"遥测数据收发地址"},{id:"2",text:"非遥测数据收发地址"},{id:"3",text:"源码数据收发地址"}];
$(function (){
	initToolBarController();
	initGridData();
});

function initToolBarController(){
	$("#toptoolbar").ligerToolBar({
		items:[
	       { text: '新增', id:'add', click: itemclick,
	    	   icon: 'add'
//	    	   img: basePath+'lib/ligerUI/skins/icons/add.gif'
	    	   },
	        { text: '修改', id:'modify', click: itemclick,
	    		   icon: 'update'
//	    			   img: basePath+'lib/ligerUI/skins/icons/modify.gif'
	    			   },
	       { text: '删除', id:'delete', click: itemclick,
	    				   icon: 'delete'
//	    				   img: basePath+'lib/ligerUI/skins/icons/delete.gif'
	    				   }
		]});
}

function initGridData(){
	gridManager=$("#maingrid").ligerGrid({
	    columns: [
	              	{display: '主键', name: 'pk_id',align: 'center',width:0.1,hide:true},
	              	{display: '地址类型', name: 'type',align: 'center',width:140,
	              		render: function(e){
	              			for (var i = 0; i < typeData.length; i++) {
								if(typeData[i].id == e.type){
									return typeData[i].text;
								}
							}
	              		} 
	              	},
	    			{display: '组播地址', name: 'address',align: 'center',width:140},
	    			{ display: "组播端口", name: "port",align: 'center',width:120},
	    			{ display: "描述", name: "content",align: 'center',width:200,
	    				render: function(e){
	              			if(e.content != null){
	              				return "<p title='"+ e.content+"'>"+e.content+"</p>";
	              			}
	              		} 	
	    			}
	        			
	    ], 
	    height: '100%', 
	    pageSize: 20,
	    rownumbers:false,
	    checkbox : true,
	    rowHeight:27,
		heightDiff : 0,
	    url:basePath+'rest/addressManager/list?satId='+satId
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
						           { text: '保存', type:'save', width: 80 ,onclick:function(item, dialog){
						        	   dialog.frame.submitForm();
						           }},
						           { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
						        	   dialog.close();
						           }}
						           ]
					 });
			 return;
         case "modify":
				if (!gridManager.getSelectedRow()) { 
					$.ligerDialog.warn("请至少选择一条数据！");
					return; 
				}
				if (gridManager.selected.length > 1) { 
					$.ligerDialog.warn("只能选择一条数据！");
					return; 
				}
					var pk_id = gridManager.selected[0].pk_id;
				 setCurrWindows();
				 xdlg= parent.$.ligerDialog.open( 
						 { 
							 width: 350,
							 height:265, 
							 title:'修改组播信息',
							 url: basePath+"satBasicInfo/address_update.jsp?pk_id="+pk_id,
							 buttons :[
							           { text: '保存', type:'save', width: 80 ,onclick:function(item, dialog){
							        	   dialog.frame.submitForm();
							           }},
							           { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
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

var ckxq = null;
function itemdbclick(sat_id){
	setCurrWindows();
		ckxq= parent.$.ligerDialog.open( 
        { 
          	width: 680,
		    height:565, 
            title:'卫星详细信息',
			url: basePath+"satBasicInfo/CheckDetails.jsp?sat_id="+sat_id,
			buttons :[{ text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
            	dialog.close();
            }}
		 ]
        });
		ckxq.show();		 
		return;
}

function search(){
	var form = $("#form2").ligerForm();
	var data = form.getData();
	gridManager.setParm("sat_name",data.sat_name);
	gridManager.setParm("design_org",data.design_org);
	gridManager.setParm("user_org",data.user_org);
	gridManager.setParm("launch_time_start",data.launch_time_start);
	gridManager.setParm("launch_time_end",data.launch_time_end);
	gridManager.set({newPage:1});
	gridManager.loadData();
}