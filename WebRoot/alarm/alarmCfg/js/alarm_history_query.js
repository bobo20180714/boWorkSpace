var gridManager = null;
var sat_pkid = null;
var sat_name = null;
var sat_code = null;
$(function () {
	init_toptoolbar();
	initQueryForm();
	
/*	//初始化卫星输入框
	$("#sat_id").ligerPopupEdit({
		onButtonClick : search_sat_click,
		width : 150
	});
	$("#param_name").ligerTextBox({
		width : 140
	});*/

//    $("#beginDate").ligerDateEditor({ showTime: true, labelWidth: 100, labelAlign: 'left' });
//    $("#endDate").ligerDateEditor({ showTime: true, labelWidth: 100, labelAlign: 'left' });
    //maingrid initiliase
    gridManager = $("#maingrid").ligerGrid({
        columns: [
					{display: '卫星名称', name: 'satnamecode',align: 'center',width:150 }
					,
					{display: '参数名称', name: 'tmnamecode',align: 'center',width:300,
						render:function(e){
							if(e.tmnamecode != null){
								return '<p title="'+e.tmnamecode+'">'+e.tmnamecode+'</p>';
							}
						}
					}
					,
					{display: '报警时间', name: 'alarmtime',align: 'center',width:180 }
					,
					{display: '级别', name: 'alarmlevel',align: 'center',width:100,
						render: function (item){//alarmlevel 报警级别1：重度, 2:中度, 3:轻度
					           switch(parseInt(item.alarmlevel, 10)){
					           case 1:
					        	   return '重度';
					           case 2:
					        	   return '中度';
					           case 3:
					        	   return '轻度';
					           case 0:
					        	   return '正常';
					           }
					      }
					}
					,
					{display: '报警值', name: 'tmvalue',align: 'center',width:120 ,
						 render: function(e){
								if(e.tmvalue){
									var va = e.tmvalue;
									return "<p title='"+ va+"'>"+va+"</p>";
								}
			        	 	}
					}
					,
					{display: '报警信息', name: 'alarmmsg',align: 'left',width:450,
						render:function(e){
							if(e.alarmmsg != null){
								return '<p title="'+e.alarmmsg+'">'+e.alarmmsg+'</p>';
							}
						}
					 }
      ], 
        width: '99.8%', 
        height: '100%', 
        pageSize: 30,
        rownumbers:true,
        checkbox : false,
        rowHeight:27,
        frozen:false,
        usePager:true,
        delayLoad:true,
        dataAction:'server',
        url:basePath+"rest/alarmHistory/findAlarmHistory"
    });
    
    $("#searchbtn").ligerButton({ type:'one',click: search_btn_click}); 
    
	
  	//重置搜索条件
	$("#resetbtn").ligerButton({ type:'two',click: clearForm});	
	
});

function initQueryForm(){
    formData1 = $("#form1").ligerForm({
    		labelAlign:'right',
        	inputWidth: 140, 
            labelWidth: 80, 
            space: 5,
            validate:true,
            fields: [
				{display: '卫星', id: 'sat_id',name:'sat_id',type:"popup",newline: false,onButtonClick : search_sat_click},
				{display: '参数关键字', id: 'param_name',name:'param_name',type:"text",newline: false},
				{display: '开始时间', id: 'beginDate',name:'beginDate',type:"date",newline: false,showTime: true},
				{display: '结束时间', id: 'endDate',name:'endDate',type:"date",newline: false,showTime: true}
			]
    });
}

//初始化toptoolbar 组件
function init_toptoolbar(){
	var baseImgPath3 = basePath+'alarm/images/';
	$("#toptoolbar").ligerToolBar(
		{ items:
		  [
          { text: '导出', id:'export', click: itemclick,img: baseImgPath3+'export.png'}
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
							{ text: '选择', type:'save',width: 80 ,onclick:function(item, dialog){
								var flag=dialog.frame.submitForm();
								if(flag){
									liger.get('sat_id').setText(sat_name+"("+sat_code+")");
								 	f_closeDlg();
//								 	search_btn_click();
								}else{
									liger.get('sat_id').setText("");
								}
								
							 }},
							{ text: '取消', type:'close',width: 80, onclick:function(item, dialog){
								f_closeDlg();
							}}
				      	 ]
				});
}

//历史报警界面上，“查询”按钮执行历史报警信息的查询
function search_btn_click(){
	var queryData = formData1.getData();
	var paramName = queryData.param_name;
	var begin_date = queryData.beginDate;
	var end_date = queryData.endDate;
	var begin_time = new Date(begin_date);
	var end_time = new Date(end_date);
	
	if(begin_time > end_time){
		parent.Alert.tip("开始时间必须早于结束时间！");
		return;
	}
	//设置参数
	gridManager.set('parms', {
		satID:sat_pkid,
		paramName:paramName,
		alarmStartTime:begin_date,
		alarmEndTime:end_date
	});
	gridManager.set({newPage:1});
	//刷新
	gridManager.loadData();

}  


//Clear the search form
function clearForm(){
	
	formData1.setData({
		sat_id:"",
		param_name:"",
		beginDate:"",
		endDate:""
	});
	liger.get('sat_id').setText("");
	sat_pkid = "";
	search_btn_click();
}

//遥测报警参数行的lickitem process
function itemclick(item)
{ 
    if(item.id)
    {
        switch (item.id)
        {
            case "export":
    			parent.$.ligerDialog.confirm('确定要批量导出吗？',
					function (yes){
	    			    if(yes){
	    			    	var formData = formData1.getData();
	    			    	var paramName = formData.param_name;
	    			    	var begin_date = formData.beginDate;
	    			    	var end_date = formData.endDate;
	    			    	var begin_time = new Date(begin_date);
	    			    	var end_time = new Date(end_date);
	    			    	
	    			    	if(begin_time > end_time){
	    			    		parent.Alert.tip("开始时间必须早于结束时间！");
	    			    		return;
	    			    	}
	    			    	/*if('0' == alarm_level){ //所有报警信息查询
	    			    		alarm_level = '-1';
	    			    	}*/
	    			    	$.ajax({
	    			    		url:basePath+"rest/alarmHistory/exportAlarmHistory",
	    			    		data:{
	    			    			satID:sat_pkid,
	    			    			paramName:paramName,
	    			    			alarmStartTime:begin_date,
	    			    			alarmEndTime:end_date
	    			    		},
	    			    		async:false,
	    			    		success:function(data){
	    			    			var jsobj = eval('('+data+')');
	    			    			if(jsobj.success == "true"){
	    			    				window.location = basePath+"rest/download/fileDownload?fileUrl="+jsobj.data;
	    			    			}else{
	    			    				Alert.tip(jsobj.message);
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
	currWin = window;
}
//关闭对话框
function f_closeDlg() {
	xdlg.close();
}

function remainSix(value){
	//保留6位小数
	if(value == null){
		return "";
	}
	if(value.split(".").length > 1){
		return parseFloat(value).toFixed(6);
	}else{
		return value;
	}
}
