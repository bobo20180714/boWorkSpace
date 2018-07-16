var gridManager = null;
var xdlg;
var colorLevel = new Array();//颜色数据
var nowTime = "";

$(function() {
	
	//获取服务器时间
	getServerDate();
	
	var baseImgPath=basePath+'lib/ligerUI/skins/icons/';
	var baseImgPath2=basePath+'icons/';
	var baseImgPath3 = basePath+'alarm/images/';
	//工具条
	$("#toptoolbar").ligerToolBar({
		items : [ 
	       /* { text :'新建航天器组', id :'addSatGroup', click:itemclick, img:baseImgPath+'add.gif'} , 
	        { text :'航天器组管理', id :'satGroupManager', click:itemclick, img:baseImgPath3+'satManger.png'} , */
		    { text :'报警颜色配置', id :'colorSet', click:itemclick, img:baseImgPath3+'colorManager.png'} , 
		   /* { text: '报警门限配置',click:itemclick  ,id:'limitRule',img: baseImgPath3+'limitRule.png'},
		    { text: '状态参数配置',click:itemclick  ,id:'stateRule',img: baseImgPath3+'stateRule.png'},
            { text: '报警历史查询',click:itemclick  ,id:'historyQuery',img: baseImgPath3+'historyQuery.png'},*/
//            { text: '报警历史查询',click:itemclick  ,id:'logQuery',img: basePath+'lib/ligerUI/skins/icons/comment.gif'},
			{ text :'导出全部', id :'export', click:itemclick, img:baseImgPath3+'export.png'}/*, 
			{ type : "label", text : "当前时间："+nowTime+"&nbsp;&nbsp;", id :'nowTime',right:true}*/
		    
		]
	});
	
	//获取颜色配置
	getColorArr();
	
	//表格
	gridManager = $("#maingrid").ligerGrid({
		columns : [ 
		    { name:"id",hide:true,width:0.1},
			{ display : '报警开始时间',name : 'startTimeStr', width : 170,
		    	render: function(e){
            		return '<div title="'+e.startTimeStr+'" >'+e.startTimeStr+'</div>';
				}	
			}, 
			{ display : '航天器名',name : 'satNameCode',width : 150,
		    	render: function(e){
            		return '<div title="'+e.satNameCode+'" >'+e.satNameCode+'</div>';
				}
			},
			{ display : '参数名称',name : 'paramNameCode',width : 340,align: 'left',
				render: function(e){
					   return "<p title='"+ e.paramNameCode+"'>"+e.paramNameCode+"</p>";
	        	 	}  
			},
		   { display : '级别',name : 'level',width : 70,
			render: function(e){
            	/*if(e.level =='1'){
            		return "<font color='"+colorLevel[3]+"'>重度</font>";
            	}else if(e.level =='2'){
            		return "<font  color='"+colorLevel[2]+"'>中度</font>";
            	}else if(e.level =='3'){
            		return "<font  color='"+colorLevel[1]+"'>轻度</font>";
            	}else{
            		return "<font  color='"+colorLevel[0]+"' >正常</font>";
            	}*/
            	if(e.level =='1'){
            		return "<font >重度</font>";
            	}else if(e.level =='2'){
            		return "<font  >中度</font>";
            	}else if(e.level =='3'){
            		return "<font  >轻度</font>";
            	}else{
            		return "<font  >正常</font>";
            	}
			}	
		   }, 
		   { display : '当前值',name : 'currentValue',width : 80,
			   render: function(e){
				   if(e.currentValue == null){
					   return "--";
				   }else{
						if(e.currentValue){
							var va = remainSix(e.currentValue);
							return "<p title='"+ va+"'>"+va+"</p>";
						}
				   }
        	 	}   
		   },
		   {name : 'lastGetValueTime',width : 0.1,hide:true},
		   { display : '报警值',name : 'alarm_value',width : 80,
			   render: function(e){
					if(e.alarm_value){
						var va = remainSix(e.alarm_value);
						return "<p title='"+ va+"'>"+va+"</p>";
					}
        	 	}   
		   } 
		   ,
		   { display : '报警信息',name : 'message',width : 400,align: 'left',
				render: function(e){
					if(e.message){
						return "<p title='"+ e.message+"'>"+e.message+"</p>";
					}
        	 	}  
		   } ,
		   { display : '报警结束时间',name : 'endTimeStr',width : 170,
			   render: function(e){
				   if(e.endTimeStr==null || e.endTimeStr==''){
					   return "-";
					   
				   }else{
					   return e.endTimeStr;
				   }
					
        	 	}   
		   }  ,
		   { display : '确认时间',name : 'responseTimeStr',width : 170,
			   render: function(e){
				   if(e.responseTimeStr==null || e.responseTimeStr==''){
					   return "<input type='button' name='waitensured' "
					   			+" value='等待确认' class='waitOK' onclick='ensureClick(\""+e.sat_id+"\",\""+e.tm_id+"\")'/>";
				   }else{
					   return e.responseTimeStr;
				   }
        	 	}   
		   }  ,
		   { display : '确认人',name : 'user_name',width :120} 
		],
		height : '97%',
		pageSize : 50,
		rownumbers:true,
		checkbox:false,
        heightDiff :13,
        frozen:false ,
        usePager:false,
        rowHeight:27,
        delayLoad : true,
        alternatingRow:false,
        cssClass: 'l-grid-gray',
        dataAction: 'server',
        //双击事件
        onDblClickRow:onDblClickRows,
        onAfterShowData:onAfterShowDatas
	});
	refreshGrid();
	
	//循环执行查询当前服务器时间
	tTime = setInterval("getServerDate()",1000);
	
	//循环执行查询报警信息
	tGradeData = setInterval("refreshGrid()",5000);
	
	//循环执行查询当前值
	tNowValue = setInterval("getNowValue()",500);
	
});

function onAfterShowDatas(e1,e2,e3){
	for(var i = 0;i< e1.Rows.length;i++){
		var rowObj = gridManager.getRowObj(e1.Rows[i]);
		var colorTemp = colorLevel[0];
    	if(e1.Rows[i].level =='1'){
    		colorTemp = colorLevel[3];
    	}else if(e1.Rows[i].level =='2'){
    		colorTemp = colorLevel[2];
    	}else if(e1.Rows[i].level =='3'){
    		colorTemp = colorLevel[1];
    	}
		rowObj.style.backgroundColor = colorTemp;
	}
}

/**
 * 双击行事件
 */
function onDblClickRows(e){
	//根据参数ID查询当前报警的报警规则类型
	var alarmRule = getAlarmType(e.tm_id);
	if(alarmRule){
		if(alarmRule.alarmType == 0){
			$.ligerDialog.open({
				title : '门限报警规则查看',
				width : 600,
				height : 450,
				url : basePath + "alarm/alarmCfg/alarm_rule_view.jsp?ruleid="+alarmRule.ruleId+"&tmid="+e.tm_id,
				buttons :[
				          { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
				        	  dialog.close();
				          }}
				          ]
			});
		}else if(alarmRule.alarmType == 2){
			setCurrWindows();
			$.ligerDialog.open({
					width : 840,
					height : 620,
					title : "查看状态字规则",
					isResize : false,
					url : basePath+'alarm/alarmCfg/statusWord/statusWord_add.jsp?type=edit&nowSelectTmid='+e.tm_id+'&ruleid='+alarmRule.ruleId,
					buttons:[
						 { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
							 dialog.close();
						 }}
					]
			});
		}else{
			Alert.tip("报警规则已被删除！");
		}
	}
}

//根据参数ID查询当前报警的报警规则类型
function getAlarmType(tmid){
	var alarmRule = null;
	$.ajax({ 
		url:basePath+"rest/alarmInfo/getAlarmType",
		data:{
			tmId:tmid
		},
		async:false,
		success:function(data){ 
			if(data!=""){
				alarmRule = eval("("+data+")");
			}
		}
	});
	return alarmRule;
}

//获取服务器时间
function getServerDate(){
	$.ajax({ 
		url:basePath+"rest/alarmRealTimeAction/getServerDate",
		async:false,
		success:function(data){ 
			if(data!=""){
				var rs = eval("("+data+")");
				nowTime = rs.nowDate;
			}
		},
		error:function(){
			//清除循环
			clearCirle();
		}
	});
	$("#nowTime").html("当前时间："+nowTime+"&nbsp;&nbsp;");
}

//获取系统配置的颜色
function getColorArr(){
	$.ajax({ 
		url:basePath+"rest/configInfo/getGlobalConfig",
		data:{
			"configItem" : "color"
		},
		async:false,
		success:function(data){ 
			var jsobj = eval('('+data+')');
			var flag = true;
			if(jsobj != null){
				//颜色配置
				var configItem = jsobj.configItem;
				if(configItem == null){
					flag = false;
				}else{
					colorLevel = configItem.split(";");
					if(colorLevel == null || colorLevel.length < 5){
						flag = false;
					}
				}
			}else{
				flag = false;
			}
			if(!flag){
				//如果从后台取得  报警显示颜色设置  失败，则以默认颜色进行设置
				colorLevel = new Array();
				colorLevel[0] = "#000000";//normal color
				colorLevel[1] = "#9d382d";//color1
				colorLevel[2] = "#313199";
				colorLevel[3] = "#9d922d";
			}
		}
	});
}

//刷新表格
function refreshGrid(){
	gridManager.set('url',basePath+'rest/alarmRealTimeAction/getRealTimePageAlam?satsid='+satsid);
	gridManager.loadData(false);
}

//获取当前值
function getNowValue(){
	var dataArr = new Array();
	var gridData = gridManager.rows;
	for ( var i = 0; i < gridData.length; i++) {
		dataArr.push({
			"sat_id" : gridData[i].sat_id,
			"tm_id" : gridData[i].tm_id,
			"lastGetValueTime" : gridData[i].lastGetValueTime
		});
	}
	var reqParamObj = {
			"paramlist":dataArr,
	};
	var reqParam = JSON.stringify(reqParamObj);
	$("#reqParamsStr").val(reqParam);
	$("#hideForm").ajaxSubmit({
		url:basePath+'rest/alarmRealTimeAction/getNowValue',
		success:function(data){
			if(data == ""){
				return;
			}
			var jsobj = eval('('+data+')');
			if(jsobj != null && jsobj.length > 0){
				//循环返回数据
				for ( var i = 0; i < jsobj.length; i++) {
					//循环表格数据与结果数据比较，修改当前值
					for ( var j = 0; j < gridData.length; j++) {
						if(jsobj[i].sat_id == gridData[j].sat_id && 
								jsobj[i].tm_id == gridData[j].tm_id 
								&& jsobj[i].newValue != "" ){
							//修改当前值
							gridData[j].currentValue = remainSix(jsobj[i].newValue);
							//修改获取当前值时间
							gridData[j].lastGetValueTime = jsobj[i].lastGetValueTime;
							break;
						}
					}
				}
				gridManager.loadData({"Rows":gridData});
			}
		}
	});
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

//清除循环
function clearCirle(){
	clearInterval(tTime);
	clearInterval(tGradeData);
	clearInterval(tNowValue);
}

function itemclick(item) {
	var b = new Base64();
	if (item.id) {
		switch (item.id) {
			case "colorSet":
				setCurrWindows();
				sonOpenWin = $.ligerDialog.open({
					width : 540,
					height : 350,
					title : "报警颜色配置",
					isResize : false,
					url : basePath+"alarm/alarmCfg/set_color_page.jsp",
					buttons:[
				         { text: '保存', type:'save',width: 80 ,onclick:function(item, dialog){
						 	var flag = dialog.frame.Confirm();
						 	if(flag == true){
						 		Alert.tip("保存成功！");
						 		f_closeDlg();
						 		getColorArr();
						 		gridManager.loadData();
						 	}else{
						 		Alert.tip("保存失败！");
						 	}
						 }},
						 { text: '关闭',type:'close', width: 80, onclick:function(item, dialog){
							f_closeDlg();
						 }}
					]
				});
			break;
		case "addSatGroup":
			setCurrWindows();
			sonOpenWin = $.ligerDialog.open({
					width : 635,
					height : 450,
					title : "新建航天器组",
					isResize : false,
					url : basePath+"alarm/realTimeAlarm/multipleManageSet.jsp",
					buttons:[
				         { text: '保存', width: 60 ,onclick:function(item, dialog){
						 	var returnData = dialog.frame.save();
						 	if(!returnData){
						 		return;
						 	}
						 	if(returnData == null || returnData.success == "false"){
						 		Alert.tip("保存信息失败！");
						 		return ;
						 	}
						 	if(returnData.success == 'true'){
						 		f_closeDlg();
						 		//打开刚才创建的航天器组
								var url = "alarm/realTimeAlarm/list.jsp?satsid="+returnData.satsid;
								url = b.encode(url);
								var wind = window.open(basePath+'main/topIndex.jsp?dUrl=\''+url+'\' &title=\'实时监视\'');
								wind.document.title = returnData.pageName;
						 	}
						 }},
						 { text: '关闭', width: 60, onclick:function(item, dialog){
							f_closeDlg();
						 }}
					]
			});
			break;
		case "satGroupManager":
			/*setCurrWindows();
			var url = "alarm/realTimeAlarm/multipleManage.jsp";
			url = b.encode(url);
			var wind = window.open(basePath+'main/topIndex.jsp?dUrl=\''+url+'\' &title=\'航天器组管理\'');*/
			

			window.open(basePath + "mainPage/main.jsp?firstPageCode=alarm&secondMenuCode=satGroup");
			
			break;
		case "limitRule":
			/*var url = "alarm/alarmCfg/rule_list.jsp";
			url = b.encode(url);
			var wind = window.open(basePath+'main/topIndex.jsp?dUrl=\''+url+'\' &title=\'报警门限配置\'');*/

			window.open(basePath + "mainPage/main.jsp?firstPageCode=alarm&secondMenuCode=limitRule");
			break;
		case "historyQuery":
			/*var url = "alarm/alarmCfg/alarm_history_query.jsp";
			url = b.encode(url);
			var wind = window.open(basePath+'main/topIndex.jsp?dUrl=\''+url+'\' &title=\'报警历史查询\'');*/

			window.open(basePath + "mainPage/main.jsp?firstPageCode=alarm&secondMenuCode=alarmHistory");
			break;
		case "logQuery":
			/*var url = "alarm/alarmCfg/alarm_query_page.jsp";
			url = b.encode(url);
			var wind = window.open(basePath+'main/topIndex.jsp?dUrl=\''+url+'\' &title=\'报警日志查询\'');*/

			window.open(basePath + "mainPage/main.jsp?firstPageCode=alarm&secondMenuCode=alarmHistory");
			break;
		case "stateRule":
			/*var url = "alarm/alarmCfg/rule_list.jsp?type=2";
			url = b.encode(url);
			var wind = window.open(basePath+'main/topIndex.jsp?dUrl=\''+url+'\' &title=\'状态参数配置\'');*/

			window.open(basePath + "mainPage/main.jsp?firstPageCode=alarm&secondMenuCode=stateRule");
			break;
		case "export":
			var url = getExcelUrl.getExcelUrl(gridManager, "报警结果",null,colorLevel);
        	window.location = url;
			break;
		}
	}
}

/**
 * 确认
 */
function ensureClick(deviceid,tmid){
	setCurrWindows();
	sonOpenWin = $.ligerDialog.open({
			width : 350,
			height : 200,
			title : "填写响应信息",
			isResize : false,
			url : basePath+"alarm/realTimeAlarm/addResponse.jsp",
			buttons:[
		         { text: '保存',  type:'save',width: 80 ,onclick:function(item, dialog){
				 	var formData = dialog.frame.formData;
				 	if(formData != null){
				 		saveResponse(deviceid,tmid,formData.getData().response);
				 	}
				 }},
				 { text: '关闭',  type:'close',width: 80 , onclick:function(item, dialog){
					f_closeDlg();
				 }}
			]
	});
}

//保存响应意见
function saveResponse(deviceid,tmid,response){
	$.ajax({
		url:basePath + 'rest/alarmRealTimeAction/toEnsure',
		data:{
			deviceid : deviceid,
			tmid : tmid,
			response : response
		},
		async:false,
		success:function(data){
			if(data != ""){
				var resObj = eval('('+data+')');
				if(resObj.success == "true"){
					f_closeDlg();
					refreshGrid();
				}
				Alert.tip(resObj.message);
			}
		}
	});
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}
//关闭对话框
function f_closeDlg() {
	sonOpenWin.close();
}