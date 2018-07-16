var gridManager = null;
var sat_pkid = null;
var sat_name = null;
var sat_code = null;
$(function () {  
/*	//初始化卫星输入框
	$("#sat_id").ligerPopupEdit({
		onButtonClick : search_sat_click,
		width : 150
	});
	//遥测参数
	$("#tm_param").ligerTextBox({
		width : 150
	});*/
	
	//初始化工具条
	init_toptoolbar();
	initQueryForm();
	//初始化表格
	init_maingrid();
    
    //查询按钮的单击事件响应函数
    $("#searchbtn").ligerButton({type:'one', click: search_btn_click});
	
  	//重置搜索条件
	$("#resetbtn").ligerButton({ type:'two',click: clearForm});
	
});

function initQueryForm(){
    formData1 = $("#form1").ligerForm({
    		labelAlign:'right',
        	inputWidth: 120, 
            labelWidth: 80, 
            space: 5,
            validate:true,
            fields: [
				{display: '卫星', id: 'sat_id',name:'sat_id',type:"popup",newline: false,onButtonClick : search_sat_click},
				{display: '遥测参数', id: 'tm_param',name:'tm_param',type:"text",newline: false}
			]
    });
}


//查找卫星的按钮单击事件响应处理，弹出一个对话框，可供选择卫星
function search_sat_click(){
	setCurrWindows();
	xdlg = $.ligerDialog
			.open({
				title : '查找卫星',
				width : 550,
				height : 480,
				url : basePath+'alarm/alarmCfg/sat_query.jsp',
				buttons :[
							{ text: '选择', type:'save', width: 80 ,onclick:function(item, dialog){
								var flag = dialog.frame.submitForm();	
								if(flag){
									liger.get('sat_id').setText(sat_name+"("+sat_code+")");
								 	f_closeDlg();
								 	search_btn_click();
								}else{
									liger.get('sat_id').setText("");
								}
								
							 }},
							{ text: '取消', type:'close', width: 80, onclick:function(item, dialog){
								f_closeDlg();
							}}
				      	 ]
				});
}


//初始设计toptoolbar组件
function init_toptoolbar(){
	$("#toptoolbar").ligerToolBar(
		{ items: [
			{ text: '新增', id:'add', click: itemclick,
         		icon:'add'
//				img: basePath+'lib/ligerUI/skins/icons/add.gif'
			}
			,  
          	{ text: '配置', id:'config', click: itemclick,
				icon:'update'
//				img: basePath+'lib/ligerUI/skins/icons/edit.gif'
			}
			,        			
			{ text: '删除', id:'delete', click: itemclick,
				icon:'delete'
//				img: basePath+'lib/ligerUI/skins/icons/delete.gif' 
			}
			,
			{ text: '查看', id: 'view', click: itemclick, 
				icon:'view'
//				img: basePath+'lib/ligerUI/skins/icons/comment.gif' 
			}
			,
			{ text: '转为状态字报警', id: 'changeto_statebit', click: itemclick, 
				icon:'change'
//				img: basePath+'lib/ligerUI/skins/icons/back.gif' 
			}
        ]
    });
}

//初始设置maingrid组件
function init_maingrid(){
	gridManager = $("#maingrid").ligerGrid({
        columns: [
                  	{ name: 'ruleid',hide:true,width:0.1}
                  	,
                    {display: 'TMID', name: 'tmid',align: 'center',width:50 }
					,
					{display: '参数名称', name: 'tmname',align: 'left',width:160,
						render:function(items){
							return '<p title="'+items.tmname+'">'+items.tmname+'</p>'
						}
					}
					,
					{display: '参数类型', name: 'tmtype',align: 'center',width:80,
						render:function(items){
							if(items.tmtype == "0"){
								return "浮点型";
							}else if(items.tmtype == "2"){
								return "整型";
							}else if(items.tmtype == "3"){
								return "字符型";
							}
						}
					}
					,
					{display: '参数编号', name: 'tmcode',align: 'center',width:80 }
					,
					{display: '有效值下限', name: 'rangevaluelower',align: 'center',width:80 }
					,
					{display: '重度下限', name: 'lowerfirst',align: 'center',width:80 }
					,
					{display: '中度下限', name: 'lowersecond',align: 'center',width:80 }
					,
					{display: '轻度下限', name: 'lowerthrid',align: 'center',width:80 }
					,
					{display: '轻度上限', name: 'upperthrid',align: 'center',width:80 }
					,
					{display: '中度上限', name: 'uppersecond',align: 'center',width:80 }
					,
					{display: '重度上限', name: 'upperfirst',align: 'center',width:80 }
					,
					{display: '有效值上限', name: 'rangevalueupper',align: 'center',width:80 }
					,
					{display: '是否报警', name: 'canalarm',align: 'center',width:80,
						render: function (item){//canalarm是否报警0：是；1：否
						           if (item.canalarm == "0"){
						        	   return '是';
						           }
						           else if(item.canalarm == "1"){
						        	   return '否';
						           }
						}
					}
					,
					{display: '判断次数', name: 'judgecount',align: 'center',width:80 }
					,
					{display: '关联条件', name: 'relation',align: 'left',width:180,
						render:function(item){
							if(item.relation != null ){
								return '<p title="'+item.relation+'">'+item.relation+'</p>';
							}
						}
					}
        ],
        dataAction:'server',
        url:basePath+"rest/limitRule/findTmLimitRule",
        width: '99.9%',
        cssClass: 'l-grid-gray',
        delayLoad:true,//第一次不加载
        height : '100%',
		pageSize : 30,
		rownumbers:true,
		checkbox:true,
		 rowHeight:27,
//        heightDiff :13,
        frozen:false 
    });
}

//UI界面上的查找按钮单击事件处理
function search_btn_click(){
	var queryData = formData1.getData();
	var tm_param = queryData.tm_param;
	//设置表格参数
	gridManager.set('parms', {
		key:tm_param,
		satid:sat_pkid
	});
	gridManager.set({newPage:1});
	//刷新表格
	gridManager.loadData(true);
}  

//Clear the search form
function clearForm(){
	//卫星id设置为空
	sat_pkid = null;
	formData1.setData({
		tm_param:"",
		sat_id:""
	});

	liger.get('sat_id').setText("");
}

//遥测报警参数行的lickitem process
function itemclick(item)
{ 
    if(item.id)
    {
        switch (item.id)
        {
        	case "add":
        		if(sat_pkid == null || sat_pkid == ""){
        			Alert.tip("请选择卫星设备！");
        			return ;
        		}
        		setCurrWindows();
				xdlg = $.ligerDialog.open({
							title : '新增遥测参数门限报警配置',
							width : 600,
							height : 515,
							url : basePath+'alarm/alarmCfg/alarm_rule_config.jsp?tmid='+tmid+'&ruleid='+ruleid+'&satid='+sat_pkid+'&operate=add',
							buttons :[
										{ text: '保存', type:'save',width: 80 ,onclick:function(item, dialog){
											dialog.frame.submitForm();
										 }},
										{ text: '关闭',type:'close', width: 80, onclick:function(item, dialog){
											f_closeDlg();
										}}
							      	 ]
							});
        	break;
            case "config":  //配置遥测参数的门限报警
            	if (!gridManager.getSelectedRow()) { 
					Alert.tip("请至少选择一条数据！");
					return; 
				}
				if (gridManager.selected.length > 1) { 
					Alert.tip("只能选择一条数据！");
					return; 
				}
				var tmtype = gridManager.selected[0].tmtype;
				if(tmtype == "3"){
					Alert.tip("暂不支持字符串类型的参数报警！");
					return;
				}
				var tmid = gridManager.selected[0].tmid;
				var ruleid = gridManager.selected[0].ruleid;
				if(ruleid == undefined){
					ruleid = "";
				}
            	setCurrWindows();
				xdlg = $.ligerDialog.open({
							title : '遥测参数门限报警配置',
							width : 600,
							height : 450,
							url : basePath+'alarm/alarmCfg/alarm_rule_config.jsp?tmid='+tmid+'&ruleid='+ruleid+'&satid='+sat_pkid,
							buttons :[
										{ text: '保存', type:'save', width: 80,onclick:function(item, dialog){
										 	dialog.frame.submitForm();
										 }},
										{ text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
											f_closeDlg();
										}}
							      	 ]
							});
				return;

            //查看资源
            case "view":
				if (!gridManager.getSelectedRow()) { 
					Alert.tip("请至少选择一条数据！");
					return; 
				}
				if (gridManager.selected.length > 1) { 
					Alert.tip("只能选择一条数据！");
					return; 
				}
				var tmid = gridManager.selected[0].tmid;
				var ruleid = gridManager.selected[0].ruleid;
				if(ruleid == undefined){
					ruleid = "";
				}
				setCurrWindows();
				xdlg = $.ligerDialog.open({
							title : '遥测参数门限报警查看',
							width : 600,
							height : 450,
							url : basePath + "alarm/alarmCfg/alarm_rule_view.jsp?ruleid="+ruleid+"&tmid="+tmid,
							buttons :[
										{ text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
											f_closeDlg();
										}}
							      	 ]
							});
                return;
            case "changeto_statebit"://将选择的门限报警修改为状态字报警
            	if (!gridManager.getSelectedRow()) { 
					Alert.tip("请至少选择一条数据！");
					return; 
				}
				if (gridManager.selected.length > 1) { 
					Alert.tip("只能选择一条数据！");
					return; 
				}
				if(sat_pkid == null || sat_pkid == ""){
        			Alert.tip("请选择卫星设备！");
        			return ;
        		}
				if(gridManager.selected[0].tmtype != "2"){
					Alert.tip("请选择类型是整型的参数！");
        			return ;
				}
				var tmid = gridManager.selected[0].tmid ;				
				
				var url=basePath+'rest/alarmInfo/updateTmAlarmForState';
    			parent.$.ligerDialog.confirm('确定选中的记录转换为状态字报警吗？',function (yes){
    		    if(yes){
    				var retObj = $.ajax({    					
    					url:url,
    					data:{
    						device_id:sat_pkid,
    						tmserialid:tmid
    					},
    					async:false});
    				var dataObj=eval("("+retObj.responseText+")");
    	  		   	if (dataObj.success == true){
    					Alert.tip("选择的门限报警，已经成功转换为状态字报警！"); 
    					f_reload();
    					//需要重新加载当前的Grid分页数据
                 	} else {
                 		Alert.tip("转换失败！"); 
    			  	}
    		    }
    			});
            	
            	return;
            case "delete":
            	if (!gridManager.getSelectedRow()) { 
					Alert.tip("请至少选择一条数据！");
					return; 
				}
				if (gridManager.selected.length > 1) { 
					Alert.tip("只能选择一条数据！");
					return; 
				}
				var ruleInfoId = gridManager.selected[0].ruleid ;
				if(ruleInfoId == undefined){
					Alert.tip("该参数还未配置规则！");
					return; 
				}
				var url = basePath+"rest/alarmInfo/deleteRule";
				
				var canalarm = gridManager.selected[0].canalarm ;
				var tmid = gridManager.selected[0].tmid ;
				
				parent.$.ligerDialog.confirm('确定要【删除】选中的门限报警记录吗？',function (yes){
					if(yes){
						$.ajax({ 
							url:url,
							data:{
								ruleInfoId:ruleInfoId,
								canalarm:canalarm,
								satid:sat_pkid,
								tmid:tmid
							}, 					
							async:false,
							success:function(data){ 
								var rsObj = eval('('+data+')');
								if(rsObj.success = true){
									Alert.tip("删除门限报警信息成功!");
									gridManager.loadData();
								}else{
									Alert.tip("删除门限报警信息失败!");
								}
							}
						});
					}
				});
				return ;
        }   
    }
} 

//重新加载grid中的数据
function f_reload(){
	gridManager.loadData();
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	currWin = window;
}
//关闭对话框
function f_closeDlg() {
	xdlg.close();
}
