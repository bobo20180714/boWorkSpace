var statusWordWin;

var gridStateBitList = null; //具有状态字的遥测参数列表
var gridStateBit = null; //遥测参数列表状态的报警设置表格
var contextMenu = null;

//卫星的ID和卫星名称 
var sat_pkid = null;
var sat_name = null;
var sat_code = null;

//当前选择的规则
var nowSelectTmid = "";
var nowSelectTmcode = "";
var nowSelectTmname = "";

var alarmLevelData = [{"id":"0","text":"正常"},{"id":"1","text":"重度"},
                                      {"id":"2","text":"中度"},{"id":"3","text":"轻度"}];
$(function () {
	
	$("#layout1").ligerLayout({
		leftWidth: 400,
		allowLeftResize:false,
		allowLeftCollapse:false
	});
	
	//初始化卫星输入框
	$("#sat_id").ligerPopupEdit({
		onButtonClick : search_sat_click,
		width : 150
	});
	//遥测参数
	$("#tm_param").ligerTextBox({
		width : 150
	});
	//初始化toptoolbar
    init_toptoolbar(); 
    initQueryForm();
	
	//初始化左边表格
	init_statebitlist_grid();	
    
	//初始化右边表格
    init_statebit_grid();	
    
    //查询按钮的单击事件响应函数
    $("#searchbtn").ligerButton({ type:'one',click: search_btn_click});
	
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
				{display: '遥测参数', id: 'tm_param',name:'tm_param',type:"text",newline: true}
			]
    });
}

function init_toptoolbar(){
	$("#toptoolbar").ligerToolBar({
		items: 
		[
		{ text: '改为门限报警', id:'modify_alarmrule', click: itemclick,
				icon:'change'
//			img: basePath+'lib/ligerUI/skins/icons/back.gif'
		}
		]
		});
}

//遥测报警参数行的单击事件处理
function itemclick(item)
{ 
    if(item.id)
    {
        switch (item.id)
        {
            case "modify_alarmrule":
            	if (!gridStateBitList.getSelectedRow()) { 
					Alert.tip("请至少选择一条数据！");
					return; 
				}
				if (gridStateBitList.selected.length > 1) { 
					Alert.tip("只能选择一条数据！");
					return; 
				}
				if(sat_pkid == "" || sat_pkid == null){
					Alert.tip("请选择卫星设备！");
					return; 
				}
				var tmid = gridStateBitList.selected[0].tmid ;				
				
				var url=basePath+'rest/alarmInfo/updateTmAlarmForLimit';
    			parent.$.ligerDialog.confirm('确定选中的记录转换为门限报警吗？',function (yes){
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
    					Alert.tip("选择的状态字，已经成功转换为门限报警！"); 
    					gridStateBitList.loadData();
    					removeTableData();
                 	} else {
                 		Alert.tip("转换失败！"); 
    			  	}
    		    }
    			});
         }   
    }
} 


function init_statebitlist_grid(){
	 //maingrid initiliase
	gridStateBitList = $("#statebit_listgrid").ligerGrid({
        columns:[
            {display: 'TMID', name: 'tmid',align: 'center',width:90 }
			,
			{display: '遥测名', name: 'tmname',align: 'left',width:160,
				render:function(item){
					if(item.tmname != null ){
						return '<p title="'+item.tmname+'">'+item.tmname+'</p>';
					}
				}
			 }
			,
			{display: '遥测编号', name: 'tmcode',align: 'center',width:90 }					
        ], 
        width: 415, 
        height: "100%",
        pageSize: 20,
        rownumbers:true,
        checkbox :false,
        frozen:false,
        usePager:true,
        onSelectRow:update_state_bit_param,
        cssClass: 'l-grid-gray' ,
        delayLoad:true,
        rowHeight:27,
        url:basePath+"rest/stateRule/findTmStateRule"
    });
}

function init_statebit_grid(){
	gridStateBit = $("#statebit_grid").ligerGrid({
        columns: [
          	{ name: 'ruleid',hide:true,width:0.1}
          	,
            {display: '状态名', name: 'status_name',align: 'left',width:190 }
			,
			{display: '掩码', name: 'mask',align: 'center',width:100 }
			,
			{display: '是否报警', name: 'canalarm',align: 'center',width:100 ,
				render: function(item){
					if(item.canalarm == "0"){
						return "是";
					}else if(item.canalarm == "1"){
						return "否";
					}
				}
			}
			,
			{display: '操作', align: 'center',width:100, 
				render:function(item){
					return '<a href="javascript:openAddWin(\'edit\',\''+item.ruleid+'\')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:deleteRule(\''+item.ruleid+'\',\''+item.canalarm+'\')">删除</a>';
				}
			}
			,
			{name: '',align: 'center',width:400 }
		],
        width: '99.9%', 
        height: '100%', 
        pageSize: 20,
        rownumbers:true,
        checkbox : false,
        frozen:false,
        usePager:false,
        cssClass: 'l-grid-gray', 
        rowHeight:27,
        detail: { 
        	//显示拆分状态
        	onShowDetail: f_showOrder 
        },
        delayLoad:true,
        url:basePath+"rest/stateRule/getStateRuleListByTM"
    });
}

//显示明细
function f_showOrder(row, detailPanel,callback){
    var grid = document.createElement('div'); 
    grid.id = "inner1";
    $(detailPanel).append(grid);
    $(grid).css('margin',10).ligerGrid({
        columns:[
                { display: '状态字', name: 'data_0',width:80},
                { display: '内容', name: 'text_0',width:80,
                	render:function(item){
                		if(item.text_0 != null){
                			var text0 = item.text_0;
                			if(text0.length > 5){
                				text0 = text0.substring(0,5);
                			}
                			return '<div title="'+item.text_0+'">'+text0+'</div>';
                		}
    				}
                },
                { display: '级别', name: 'alarmLevel_0',width:40,
                	render:function(item){
                		for ( var i = 0; i < alarmLevelData.length; i++) {
                			if(item.alarmLevel_0 == alarmLevelData[i].id){
                				return alarmLevelData[i].text;
                			}
						}
                	}
                },
                { display: '状态字', name: 'data_1',width:80 },
                { display: '内容', name: 'text_1',width:80,
                	render:function(item){
                		if(item.text_1 != null){
                			var text1 = item.text_1;
                			if(text1.length > 5){
                				text1 = text1.substring(0,5);
                			}
                			return '<div title="'+item.text_1+'">'+text1+'</div>';
                		}
    				} },
                { display: '级别', name: 'alarmLevel_1',width:40,
                	render:function(item){
                		for ( var i = 0; i < alarmLevelData.length; i++) {
                			if(item.alarmLevel_1 == alarmLevelData[i].id){
                				return alarmLevelData[i].text;
                			}
						}
                	}},
                { display: '状态字', name: 'data_2',width:80 },
                { display: '内容', name: 'text_2',width:80 ,
                	render:function(item){
                		if(item.text_2 != null){
                			var text2 = item.text_2;
                			if(text2.length > 5){
                				text2 = text2.substring(0,5);
                			}
                			return '<div title="'+item.text_2+'">'+text2+'</div>';
                		}
    				}},
                { display: '级别', name: 'alarmLevel_2',width:40,
                	render:function(item){
                		for ( var i = 0; i < alarmLevelData.length; i++) {
                			if(item.alarmLevel_2 == alarmLevelData[i].id){
                				return alarmLevelData[i].text;
                			}
						}
                	}},
                { display: '状态字', name: 'data_3',width:80 },
                { display: '内容', name: 'text_3',width:80 ,
                	render:function(item){
                		if(item.text_3 != null){
                			var text3 = item.text_3;
                			if(text3.length > 5){
                				text3 = text3.substring(0,5);
                			}
                			return '<div title="'+item.text_3+'">'+text3+'</div>';
                		}
    				}},
                { display: '级别', name: 'alarmLevel_3',width:40,
                	render:function(item){
                		for ( var i = 0; i < alarmLevelData.length; i++) {
                			if(item.alarmLevel_3 == alarmLevelData[i].id){
                				return alarmLevelData[i].text;
                			}
						}
                	}}
                ], 
        isScroll: true, 
        showToggleColBtn: true, 
        width: 813,
        rowHeight:27,
        data: {
        	Rows:row.RULECONTENT
        }, 
        usePager:false,
        frozen:false
    });
//    var count = 0;
//    $("#inner1 .l-grid-row-cell").each(function(){
//    	if (count%3 == 0) {
//    		$(this).css("backgroundColor","red");
//    	}
//    	count++;
//    });
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

//界面上的"查询"按钮单击事件处理
function search_btn_click(){
	var queryData = formData1.getData();
	var tm_param = queryData.tm_param;
	
	gridStateBitList.set('parms', {
		key:tm_param,
		satid:sat_pkid,
		judgetype:"2"
	});
	gridStateBitList.loadData();
	
	removeTableData();
}  

//去掉右边表格数据
function removeTableData(){
	$("#state_param_name").html("");
	gridStateBit.set("parms",{
		"tmid":""
	});
	gridStateBit.loadData();
}

//选择某个状态字遥测参数后，更新右侧的状态字参数设置信息
function update_state_bit_param(data, rowindex, rowobj)
{
	//当前选择的规则
	nowSelectTmid = data.tmid;
	nowSelectTmcode = data.tmcode;
	nowSelectTmname = data.tmname;
	
	//显示规则名称和编号
	var state_bit_name = "  >>" + data.tmname + "(" + data.tmcode + ")";
	$("#state_param_name").html(state_bit_name);
	
	gridStateBit.set("parms",{
		"tmid":data.tmid
	});
	gridStateBit.loadData();
}

//Clear the search form
function clearForm(){
	formData1.setData({
		tm_param:"",
		sat_id:""
	});

	liger.get('sat_id').setText("");
		
		sat_pkid = "";
		nowSelectTmid = "";
		//清空左边表格
		search_btn_click();
		//清空右边表格数据
		removeTableData();
	}

//弹出条件选择窗口（新增）
function openAddWin(type,ruleid){
	setCurrWindows()
	var tabName = "[新增拆分状态字]";
	if(type == "add"){
		tabID = "addStateBit";
		if(nowSelectTmid == "" || nowSelectTmid == null){
			Alert.tip("请选择参数！");
			return;
		}
	}else{
		tabName = "[编辑拆分状态字]";
	}
	statusWordWin = $.ligerDialog.open({
			width : 840,
			height : 620,
			title : tabName,
			isResize : false,
			url : basePath+'alarm/alarmCfg/statusWord/statusWord_add.jsp?type='+type+'&nowSelectTmid='+nowSelectTmid+'&ruleid='+ruleid+'&satid='+sat_pkid,
			buttons:[
		         { text: '保存', type:'save', width: 80,onclick:function(item, dialog){
				 	dialog.frame.saveStatusWord();
				 }},
				 { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
					 closeWin();
				 }}
			]
	});
	
	/*var tabID = "updateStateBit";
	var tabName = "[新增拆分状态字]";
	if(type == "add"){
		tabID = "addStateBit";
		if(nowSelectTmid == "" || nowSelectTmid == null){
			Alert.tip("请选择参数！");
			return;
		}
	}else{
		tabName = "[编辑拆分状态字]";
	}
	setCurrWindow();
	parent.f_addTab(tabID,tabName,basePath+'alarm/alarmCfg/statusWord/statusWord_add.jsp?type='+type+'&nowSelectTmid='+nowSelectTmid+'&ruleid='+ruleid+'&satid='+sat_pkid);
	*/
}

//删除
function deleteRule(ruleId,canalarm){
	parent.$.ligerDialog.confirm('确定要【删除】选中的拆分状态记录吗？',function (yes){
		if(yes){
			$.ajax({
				url:basePath+'rest/alarmInfo/deleteRule',
				data:{
					ruleInfoId:ruleId,
					canalarm:canalarm,
					satid:sat_pkid,
					tmid:nowSelectTmid
				},
				success:function(data){
					var jsobj = eval('('+data+')');
					if(jsobj.success == true){
						//刷新表格
		         		if(nowSelectTmid != ""){
		         			gridStateBit.set("parms",{
		         				"tmid":nowSelectTmid
		         			});
		         			gridStateBit.loadData();
		         		}
					}
				}
			});
		}
	});
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	currWin = window;
}
//关闭对话框
function f_closeDlg() {
	xdlg.close();
}


//当前窗口对象传递给子窗口
function setCurrWindow(){
  parent.currWin = window;
}

function closeWin(){
	statusWordWin.close();
}

/**
 * 关闭并刷新
 */
function closeAndLoad(){
	statusWordWin.close();
	gridStateBit.loadData();
}

//移除tab
function removeTab(operateType){
	if(operateType == "add"){
		parent.tab.removeTabItem("addStateBit");
	}else if(operateType == "edit"){
		parent.tab.removeTabItem("updateStateBit");
	}
}
