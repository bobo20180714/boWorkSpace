//表单对象
var processForm = null;
//启动参数表格对象
var paramGridObj = null;

//进程类型
var processTypeArr = [{id:'3',text:'遥测处理'},{id:'4',text:'门限报警'}
,{id:'5',text:'控制量计算'},{id:'6',text:'数据存储'}/*,{id:'10',text:'服务代理进程'}*/];

//进程启动类型
var startupTypeArr = [{id:'exe',text:'exe'},{id:'bat',text:'bat'},{id:'4',text:'sh'}
					,{id:'class',text:'class'},{id:'jar',text:'jar'}];

//选择的父进程信息
var serviceProcessData = null;

$(function(){
	//初始化进程基本信息form
	initProcessForm();
	
	 getProcessData();
});

//初始化进程基本信息form
function initProcessForm(){
	processForm = $("#processForm").ligerForm({
        inputWidth: 170, 
        labelWidth: 110, 
        space: 20,
        validate:true,
        fields: [
             { display: "进程ID", name: "pkId",id:"pkId",
            	 type: "hidden"
             },
             { display: "进程标识", name: "processCode",id:"processCode",
            	 type: "hidden"
             },
             { display: "进程类型", name: "processType",id:"processType",
            	 type: "select", newline: true,data:processTypeArr,
            	 value:3,cancelable:false,readonly:true,
            	 validate:{required:false},onselected:selectProcessType
             },
             { display: "进程名称", name: "processName",id:"processName",
            	 type: "text", newline: false,validate:{required:false,maxlength:50}
             },
             { display: "服务器", name: "serviceProcessCode",id:"serviceProcessCode",
            	 type: "popup", newline: true,onButtonClick:openProcessWin
             },
             { display: "服务器IP", name: "computerIp",id:"computerIp",
            	 type: "text", newline: false,readonly:true
             },
             { display: "所属卫星编号", name: "satMid",id:"satMid",
            	 type: "popup", newline: true,onButtonClick:selectSat
             },
             { display: "进程启动类型", name: "startupType",id:"startupType",
            	 type: "text", newline: false
            	 ,validate:{required:false},readonly:true
             },
             { display: "是否是备用进程", name: "isMainProcess",id:"isMainProcess",
 				type: "select", newline: true,validate:{required:false},
 				value:0,data:[{id:"1",text:"是"},{id:"0",text:"否"}]
 				,readonly:true,onselected:selectIsMainProcess
 			},
 			{ display: "主进程标识", name: "mainProcessCode",id:"mainProcessCode",
 				type: "text", newline: false,readonly:true
 			},
			{ display: "进程启动路径", name: "startupPath",id:"startupPath",
				type: "text", newline: true,width:470
				,validate:{required:false}
			},
			{ display: "启动参数", name: "startupParam",id:"startupParam",
				type: "textarea", newline: true,width:470,helpTip:{img:basePath+'resources/images/help.png',
					title:"填写启动进程需要的参数值，多个参数值用英文分号(;)分割"}
			}
        ]
	});
//	selectProcessType(3);
//	selectIsMainProcess(0);
}

//工具条点击事件
function itemclick(item){
	if (item.id) {
		switch (item.id) {
			case "add":
				paramGridObj.addRow();
				break;
			case "delete":
				paramGridObj.deleteRow(paramGridObj.getSelectedRow());
				break;
			case "up":
				paramGridObj.up(paramGridObj.getSelectedRow());
				break;
			case "down":
				paramGridObj.down(paramGridObj.getSelectedRow());
				break;
 		}
	}
}

/**
 * 选择进程类型事件
 */
function selectProcessType(e){
	if(e == 10){
		liger.get("serviceProcessCode").setDisabled(true);
//		liger.get("startupType").setDisabled(true);
		liger.get("startupPath").setDisabled(true);
		liger.get("isMainProcess").setDisabled(true);
		liger.get("satMid").setDisabled(true);
		$("#startupParam")[0].disabled = true;
		
//		liger.get("computerIp").setEnabled(true);
	}else{
		liger.get("serviceProcessCode").setEnabled(true);
//		liger.get("startupType").setEnabled(true);
		liger.get("startupPath").setEnabled(true);
		liger.get("isMainProcess").setEnabled(true);
		$("#startupParam")[0].disabled = false;
		
		if(e == 4 || e == 5){
			liger.get("satMid").setText("");
			liger.get("satMid").setValue("");
			liger.get("satMid").setDisabled(true);
		}else{
			liger.get("satMid").setEnabled(true);
		}
		
//		liger.get("computerIp").setDisabled(true);
	}
	
	liger.get("isMainProcess").setValue(0);
	liger.get("mainProcessCode").setValue("");
}

/**
 * 选择是否是主进程事件
 */
function selectIsMainProcess(e){
	if(e == 1){
		//判断卫星是否有数据
		var pType = liger.get("processType").getValue();
		if(pType == 3 || pType == 6){
			var satMid = liger.get("satMid").getValue();
			if(!satMid){
				liger.get("isMainProcess").setValue(0);
				parent.Alert.tip("请先选择所属卫星！");
				return false;
			}
		}
		//查询主进程标识
		if(processCode == getMainProcessCode()){
			liger.get("mainProcessCode").setValue("");
		}else{
			liger.get("mainProcessCode").setValue(getMainProcessCode());
		}
	}else{
		liger.get("mainProcessCode").setValue("");
	}
}

function getMainProcessCode(){
	var main_process_code = null;
	var processInfo = processForm.getData();
	$.ajax({
		url:basePath+'rest/processManager/getMainProcess',
		data:{
			processType:processInfo.processType,
			satMid:liger.get("satMid").getValue()
		},
		async:false,
		success:function(data){
			if(data == ""){
				parent.Alert.tip("请求出错！");
				return;
			}
			var jsobj = eval('('+data+')');
			main_process_code = jsobj.process_code;
		}
	});
	return main_process_code;
}

/**
 * 选择服务器
 */
function openProcessWin(){
	var e = this;
	var processWin = parent.$.ligerDialog.open({
		title : '选择服务器',
		width : 600,
		height : 450,
		url : basePath+'processManage/processRegister/selectProcess.jsp?processType=10',
		buttons :[
		          { text: '确定', type:'save', width: 80,onclick:function(item, dialog){
		        	  
		        	    var data = dialog.frame.getSelectData();
						if (data == null) { 
							parent.Alert.tip("请选择服务器数据！");
							return; 
						}
						serviceProcessData = data;
						liger.get(e.id).setText(data.processName);
						liger.get(e.id).setValue(data.processCode);

						liger.get("computerIp").setValue(data.computerIp);
						
						processWin.close();
		          }},
		          { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
		        	  processWin.close();
		          }}
		          ]
	});
}

/**
 * 选择卫星
 */
function selectSat(){
	var satSatWin = parent.$.ligerDialog.open({
		title : '选择卫星',
		width : 560,
		height : 450,
		url : basePath+'processManage/processRegister/selectSat.jsp',
		buttons :[
		          { text: '确定', type:'save', width: 80,onclick:function(item, dialog){
		        	  
		        	    var data = dialog.frame.getSelectData();
						if (data == null) { 
							parent.Alert.tip("请选择卫星数据！");
							return; 
						}
						liger.get("satMid").setText(data.satCode);
						liger.get("satMid").setValue(data.satId);
						
						var isMain = liger.get("isMainProcess").getValue();
						var pType = liger.get("processType").getValue();
						if(isMain == "1" && (pType == 3 || pType == 6)){
							liger.get("mainProcessCode").setValue(getMainProcessCode());
						}
						satSatWin.close();
		          }},
		          { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
		        	  satSatWin.close();
		          }}
		          ]
	});
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}

/**
 * 提交表单
 */
function submitForms(){
	var vboolean = processForm.valid();
	if (vboolean) {
		var processInfo = processForm.getData();
		if(processInfo.processType == null){
			parent.Alert.tip("进程类型信息不能为空！");
			return false;
		}
		if(processInfo.processName == ""){
			parent.Alert.tip("进程名称信息不能为空！");
			return false;
		}
		
		if(processInfo.processType == 10){
			//进程类型---服务代理进程
			processInfo.isMainProcess = "";
			if(processInfo.computerIp == ""){
				parent.Alert.tip("服务代理进程所在的服务器IP信息不能为空");
				return false;
			}
		}else{
			if(processInfo.isMainProcess == null){
				parent.Alert.tip("是否是备用信息不能为空！");
				return false;
			}
			if(processInfo.isMainProcess == 1){
				//备用进程
				if(processInfo.mainProcessCode == ""){
					parent.Alert.tip("请先创建主进程！");
					return false;
				}
			}
			if(processInfo.serviceProcessCode == ""){
				parent.Alert.tip("服务代理进程信息不能为空！");
				return false;
			}
			if(processInfo.processType == 6 || processInfo.processType == 3){
				if(processInfo.satMid == ""){
					parent.Alert.tip("注册数据存储或遥测处理进程时，所属卫星任务代号信息不能为空！");
					return false;
				}
			}
			if(processInfo.startupType == ""){
				parent.Alert.tip("进程启动类型信息不能为空！");
				return false;
			}
			if(processInfo.startupPath == ""){
				parent.Alert.tip("进程启动路径信息不能为空！");
				return false;
			}
		}
		processInfo.satId = processInfo.satMid;
		saveProcessInfo(processInfo);
	} else {
		return false;
	}
}

function saveProcessInfo(processInfo){
	$.ajax({
		url:basePath+'rest/processManager/update',
		data:processInfo,
		async:false,
		success:function(data){
			if(data == ""){
				parent.Alert.tip("请求出错！");
				return;
			}
			var jsobj = eval('('+data+')');
			if(jsobj.success == "true"){
				parent.Alert.tip("进程修改成功！");
         		//刷新表格
         		parent.closeUpdateWinAndLoadGrid();
			}else{
				parent.Alert.tip("进程修改失败！");
			}
		}
	});
}


/**
 * 获取进程信息
 */
function getProcessData(){
	$.ajax({
		url:basePath+'rest/processManager/viewProcess',
		data:{
			processCode:processCode
		},
		async:false,
		success:function(data){
			if(data == ""){
				parent.Alert.tip("请求出错！");
				return;
			}
			var jsobj = eval('('+data+')');
			processForm.setData(jsobj);
			liger.get("startupType").setValue(jsobj.startupType);
			liger.get("startupPath").setValue(jsobj.startupPath);
			liger.get("serviceProcessCode").setText(jsobj.serviceProcessName);
			liger.get("serviceProcessCode").setValue(jsobj.serviceProcessCode);
			
			liger.get("satMid").setText(jsobj.satCode);
			liger.get("satMid").setValue(jsobj.satId);
			

			liger.get("mainProcessCode").setValue(jsobj.mainProcessCode);
			
		}
	});
}
