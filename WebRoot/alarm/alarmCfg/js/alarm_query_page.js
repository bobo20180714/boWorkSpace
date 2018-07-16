var gridManager = null;
var sat_pkid = null;
var sat_name = null;
var sat_code = null;
$(function () {
	//初始化卫星输入框
	$("#sat_id").ligerPopupEdit({
		onButtonClick : search_sat_click,
		width : 150
	});
	$("#param_name").ligerTextBox({
		width : 140
	});
	init_toptoolbar();
	
    //query area initiliase
	var alarmLevelData =[
	                     {id:'0', text:'正常'},
	                     {id:'3', text:'轻度'},
	                     {id:'2', text:'中度'},
	                     {id:'1', text:'重度'}
	                     ];

    $("#beginDate").ligerDateEditor({
    	format: "yyyy-MM-dd hh:mm:ss",
    	showTime: true,
    	width: 145,
    	labelAlign: 'left'
    });
    $("#endDate").ligerDateEditor({
    	format: "yyyy-MM-dd hh:mm:ss",
    	showTime: true,
    	width: 145,
    	labelAlign: 'left'
    });
    
    //maingrid initiliase
    gridManager = $("#maingrid").ligerGrid({
        columns: [
                    {display: 'alarmid', name: 'alarmid', hide:true,width:0.1 }
					,
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
					{display: '报警时间', name: 'begintime',align: 'center',width:180 }
					,
					{display: '级别', name: 'alarmlevel',align: 'center',width:80,
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
					{display: '报警值', name: 'alarmvalue',align: 'center',width:150 }
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
        height: '99.7%', 
        pageSize: 30,
        rownumbers:true,
        checkbox : false,
        frozen:false,
        usePager:true,
        rowHeight:27,
        delayLoad:true,
        cssClass: 'l-grid-gray', 
        url:basePath+"rest/alarmHistory/findAlarmHistory",
        detail: { 
        	//显示
        	onShowDetail: f_showChildren,
        	height:380
        }
    });
    
    //search and reset button click_event
    $("#searchbtn").ligerButton({ click: search_btn_click}); 
    
	
  	//重置搜索条件
	$("#resetbtn").ligerButton({ click: clearForm});	
	
});

//显示明细
function f_showChildren(row, detailPanel,callback){
    var grid = document.createElement('div'); 
    grid.id = "inner1";
    $(detailPanel).append(grid);
    $(grid).css('margin',10).ligerGrid({
        columns:[
                 {display: 'pk_id', name: 'pk_id', hide:true,width:0.1 },
					{display: '报警时间', name: 'alarmtime',align: 'center',width:180 }
					,
					{display: '级别', name: 'alarmlevelzh',align: 'center',width:80}
					,
					{display: '报警值', name: 'tmvalue',align: 'center',width:150 }
					,
					{display: '报警信息', name: 'alarmmsg',align: 'left',width:450,
						render:function(e){
							if(e.alarmmsg != null){
								return '<p title="'+e.alarmmsg+'">'+e.alarmmsg+'</p>';
							}
						}
					 }
					,
					{display: '跳变信息', name: 'alarmlevelinfo',align: 'left',width:200,
						render:function(e){
							if(e.alarmlevelinfo != null){
								return '<p title="'+e.alarmlevelinfo+'">'+e.alarmlevelinfo+'</p>';
							}
						}
					 }
        ], 
        isScroll: true, 
        showToggleColBtn: true, 
        width: 1340,
        url:basePath+"rest/alarmHistory/findAlarmInfoLog?alarmid="+row.alarmid,
        usePager:true,
        frozen:false
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
					{ text: '选择', type:'save', width: 80,onclick:function(item, dialog){
						var flag=dialog.frame.submitForm();
						if(flag){
							liger.get('sat_id').setText(sat_name+"("+sat_code+")");
						 	f_closeDlg();
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

//历史报警界面上，“查询”按钮执行历史报警信息的查询
function search_btn_click(){
	
	if(sat_pkid == ""){
		Alert.tip("请选择卫星！");
		return;
	}
	
	var begin_date = $("#beginDate").val();
	var end_date = $("#endDate").val();
	var begin_time = new Date(begin_date);
	var end_time = new Date(end_date);
	
	if(begin_time > end_time){
		Alert.tip("开始时间必须早于结束时间！");
		return;
	}
	//设置参数
	gridManager.set('parms', {
		satID:sat_pkid,
		paramName:$("#param_name").val(),
		alarmStartTime:begin_date,
		alarmEndTime:end_date
	});
	gridManager.set({newPage:1});
	//刷新
	gridManager.loadData();

}  


//Clear the search form
function clearForm(){
	   $('#form1').find(':input').each(  
	   	function(){
		  if(this.type=="radio"||this.type=="checkbox"){
		    		this.checked=false;
			}else{
				$(this).val('');  
			}
		}     
		);
//		$(".l-selected").removeClass();
		sat_pkid = "";
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
    			    	var begin_date = $("#beginDate").val();
    			    	var end_date = $("#endDate").val();
    			    	var begin_time = new Date(begin_date);
    			    	var end_time = new Date(end_date);
    			    	
    			    	if(begin_time > end_time){
    			    		parent.Alert.tip("开始时间必须早于结束时间！");
    			    		return;
    			    	}
    			    	$.ajax({
    			    		url:basePath+"rest/alarmHistory/exportAlarmLog",
    			    		data:{
    			    			satID:sat_pkid,
    			    			paramName:$("#param_name").val(),
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

/*
function setCurrWindows(){
    parent.currWin = window;
}
*/