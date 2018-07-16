var gridManager = null;
var sonOpenWin;
$(function() {
	var baseImgPath=basePath+'lib/ligerUI/skins/icons/';
	var baseImgPath2=basePath+'icons/';
	//工具条
	$("#toptoolbar").ligerToolBar({
		items : [ 
         	{ text :'打开', id :'open', click:itemclick, 
         		icon:'open'
//         		img:baseImgPath+'search.gif'
         		},
			{ text :'新增', id :'add', click:itemclick, 
             		icon:'add'
//         			img:baseImgPath+'add.gif'
 			},
			{ text : '修改',id : 'modify',click : itemclick,
                 		icon:'update'
//             			img : baseImgPath+'modify.gif'
 			},
			{ text : '删除',id : 'delete',click : itemclick,
         		icon:'delete'
//         		img : baseImgPath+'delete.gif'
     		}
		]
	});
	 
	//表格
	gridManager = $("#maingrid").ligerGrid({
		columns : [
		    { name:"alarmpageid",id:"alarmpageid",hide:true,width:0.1},
			{ display : '航天器组名称',name : 'pagename',id:"pagename",width : 200,
				render: function(e){
					   return "<p title='"+ e.pagename+"'>"+e.pagename+"</p>";
	        	 	}  
			},
			{ display : '航天器',name : 'satsname',id:"satsname",width : 400},
			{ name : 'satsid',id:"satsid",hide:true,width : 0.1}
		],
		url : basePath+'rest/alarmInfo/queryAlarmPageList?tableSpace=alarm&sqlId=queryAlarmPageList',
		height : '100%',
		pageSize : 30,
		rownumbers:true,
		checkbox:true,
		colDraggable:true,
        rowDraggable:true,
//        heightDiff :13,
        rowHeight:27,
        frozen:false ,
        cssClass: 'l-grid-gray'
	});
	
	$("#pageloading").hide();
});

function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "open": 
				if(gridManager.selected.length != 1){
					Alert.tip("请选择一条航天器组！");
					return;
				}
				var satsid = gridManager.selected[0].satsid;
				/*var b = new Base64();
				var url = "alarm/realTimeAlarm/list.jsp?satsid="+satsid;
				url = b.encode(url);
				var wind = window.open(basePath+'main/topIndex.jsp?dUrl=\''+url+'\' &title=\'实时监视\'');
				
				wind.document.title = gridManager.selected[0].pagename;*/
				

				var url = "alarm/realTimeAlarm/list.jsp?satsid="+satsid;
				window.open(basePath + "mainPage/main.jsp?firstPageCode=alarm&secondMenuCode=monitorAlarm&secondMenuText=报警监视&menuUrl="+url);
				
				break;
			case "add": 
				setCurrWindows()
				sonOpenWin = $.ligerDialog.open({
						width : 450,
						height : 400,
						title : "新建航天器组",
						isResize : false,
						url : basePath+"alarm/realTimeAlarm/multipleManageSet.jsp",
						buttons:[
					         { text: '保存', type:'save',width: 80 ,onclick:function(item, dialog){
							 	var returnData = dialog.frame.save();
							 	if(!returnData){
							 		return;
							 	}
							 	if(returnData == null || returnData.success == "false"){
							 		Alert.tip("保存信息失败！");
							 		return ;
							 	}
							 	if(returnData.success == 'true'){
							 		Alert.tip("保存信息成功!");
							 		closeDlgAndReload();
							 	}
							 }},
							 { text: '关闭', type:'close',width: 80, onclick:function(item, dialog){
								f_closeDlg();
							 }}
						]
				});
				break;
			case "modify":
				if (!gridManager.getSelectedRow()) {
				     Alert.tip('请至少选择一条数据！'); 
				 	return; 
				}
				if (gridManager.selected.length > 1) { 
				    Alert.tip('只能选择一条数据！'); 
				 	return; 
				}
				 
				setCurrWindows();
				var pageId = gridManager.selected[0].alarmpageid;
				sonOpenWin = $.ligerDialog.open({
					title : '修改航天器组',
					width : 430,
					height : 420,
					url : basePath+'alarm/realTimeAlarm/multipleManageSet.jsp?opt=modify&pageId='+pageId,
					buttons :[
								{ text: '保存',type:'save', width: 80 ,onclick:function(item, dialog){
								 	var returnData = dialog.frame.save();
								 	if(!returnData){
								 		return;
								 	}
								 	if(returnData == null || returnData.success == "false"){
								 		Alert.tip("保存信息失败！");
								 		return ;
								 	}
								 	if(returnData.success == 'true'){
								 		Alert.tip("保存信息成功!");
								 		closeDlgAndReload();
								 	}
								 }},
								{ text: '关闭', type:'close',width: 80, onclick:function(item, dialog){
									f_closeDlg();
								}}
					      	 ]
				});
				return;	
			case "delete":
				if (!gridManager.getSelectedRow()) { 
					Alert.tip("请至少选择一条数据！");
					return; 
				}
				var satPageIds = "";
				for ( var i = 0; i < gridManager.selected.length; i++) {
					if(i == 0){
						satPageIds = gridManager.selected[0].alarmpageid;
						continue;
					}
					satPageIds = satPageIds + "," + gridManager.selected[i].alarmpageid;
				}
				var url = basePath+"rest/alarmInfo/deletePage";
				parent.$.ligerDialog.confirm('确定要【删除】选中的航天器组吗？',function (yes){
					if(yes){
						$.ajax({ 
							url:url,
							data:{
								pageIds:satPageIds
							}, 					
							async:false,
							success:function(data){ 
								var rsObj = eval('('+data+')');
								if(rsObj.success == "true"){
									Alert.tip("删除航天器组成功!");
									gridManager.loadData();
								}else{
									Alert.tip("删除航天器组失败!");
								}
							}
						});
					}
				});
			}
		}
	}
//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.SatGroupWin = window;
}
//关闭对话框
function f_closeDlg() {
	sonOpenWin.close();
}
//刷新grid
function f_reload() {
	gridManager.set({
		newPage : 1
	});
	gridManager.loadData();
}
function closeDlgAndReload(){
      f_closeDlg();
      f_reload();
  }
function clearForm(){
   $('#form1').find(':input').each(  
   function(){
	   switch(this.type){
		   case 'passsword': 
		   case 'select-multiple':  
		   case 'select-one':  
		   case 'text':
		   case 'textarea': 
			    if(!this.readOnly){
		          $(this).val('');  
		          break;
		        }
		   case 'checkbox':  
		   case 'radio': 
		        this.checked = false;  
	   }
	}     
	);  
}