//表单对象
var processForm = null;
//启动参数表格对象
var paramGridObj = null;

//进程类型
var processTypeArr = [{id:'3',text:'遥测处理'},{id:'4',text:'门限报警'}
						,{id:'5',text:'控制量计算'},{id:'6',text:'数据存储'},{id:'10',text:'服务代理进程'}];

//进程启动类型
var startupTypeArr = [{id:'exe',text:'exe'},{id:'bat',text:'bat'},{id:'4',text:'sh'}
					,{id:'class',text:'class'},{id:'jar',text:'jar'}];

//选择的父进程信息
var serviceProcessData = null;

$(function(){
	//初始化进程基本信息form
	initProcessForm();
});

//初始化进程基本信息form
function initProcessForm(){
	processForm = $("#processForm").ligerForm({
        inputWidth: 170, 
        labelWidth: 90, 
        space: 20,
        validate:true,
        fields: [
             /*{ display: "进程类型", name: "processType",id:"processType",
            	 type: "select", newline: true,data:processTypeArr,
            	 value:10,cancelable:false,
            	 validate:{required:false},onselected:selectProcessType
             },*/
             /*{ display: "进程标识", name: "processCode",id:"processCode",
            	 type: "text", newline: false,readonly:true
             },*/
             { display: "服务器名称", name: "processName",id:"processName",
            	 type: "text", newline: true,validate:{required:true,maxlength:50}
             },
             { display: "服务器IP", name: "computerIp",id:"computerIp",
            	 type: "text", newline: true,validate:{required:true,maxlength:20}
             }/*,
             { display: "是否是备用进程", name: "isMainProcess",id:"isMainProcess",
 				type: "select", newline: false,validate:{required:false},
 				value:0,data:[{id:"1",text:"是"},{id:"0",text:"否"}]
 				,onselected:selectIsMainProcess
 			},
 			{ display: "主进程标识", name: "mainProcessCode",id:"mainProcessCode",
 				type: "text", newline: true,validate:{required:false}
 			},
             { display: "服务代理进程", name: "serviceProcessCode",id:"serviceProcessCode",
            	 type: "popup", newline: false,onButtonClick:openProcessWin
             },
             { display: "所属卫星编号", name: "satMid",id:"satMid",
            	 type: "popup", newline: true,onButtonClick:selectSat
             },
             { display: "进程启动类型", name: "startupType",id:"startupType",
            	 type: "select", newline: false,data:startupTypeArr
            		 ,validate:{required:false}
             },
			{ display: "进程启动路径", name: "startupPath",id:"startupPath",
				type: "text", newline: true,width:470
				,validate:{required:false}
			},
			{ display: "启动参数", name: "startupParam",id:"startupParam",
				type: "textarea", newline: true,width:470
			}*/
        ]
	});
	/*liger.get("startupParam").element.parentNode.parentNode.innerHTML = '<div id="paramGrid"></div>';
	
	paramGridObj = $("#paramGrid").ligerGrid({
		columns : [
					{
						display : '参数描述',
						name : 'param_desc',
						align : 'center',
						editor:{
							type:"text"
						},
						width : 400
					}
			],
			toolbar:{
				items : [ {
					text : '新增',
					id : 'add',
					click : itemclick,
					img : basePath + 'lib/ligerUI/skins/icons/add.gif'
				}, {
					text : '删除',
					id : 'delete',
					click : itemclick,
					img : basePath + 'lib/ligerUI/skins/icons/delete.gif'
				},{
					text : '上移',
					id : 'up',
					click : itemclick,
					img : basePath + 'lib/ligerUI/skins/icons/up.gif'
				}, {
					text : '下移',
					id : 'down',
					click : itemclick,
					img : basePath + 'lib/ligerUI/skins/icons/down.gif'
				}]
			},
			height:265,
			rownumbers : false,
			checkbox : true,
			isSingleCheck:true,
			rowHeight : 27,
			enabledEdit:true,
			usePager:false,
			frozen : false
		});*/
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
		liger.get("startupType").setDisabled(true);
		liger.get("startupPath").setDisabled(true);
		liger.get("isMainProcess").setDisabled(true);
		liger.get("satMid").setDisabled(true);
		$("#startupParam")[0].disabled = true;
		
		liger.get("computerIp").setEnabled(true);
	}else{
		liger.get("serviceProcessCode").setEnabled(true);
		liger.get("startupType").setEnabled(true);
		liger.get("startupPath").setEnabled(true);
		liger.get("isMainProcess").setEnabled(true);
		liger.get("satMid").setEnabled(true);
		$("#startupParam")[0].disabled = false;
		
		liger.get("computerIp").setDisabled(true);
	}
}

/**
 * 选择是否是主进程事件
 */
function selectIsMainProcess(e){
	if(e == 1){
		liger.get("mainProcessCode").setEnabled(true);
	}else{
		liger.get("mainProcessCode").setDisabled(true);
	}
}

/**
 * 选择进程
 */
function openProcessWin(){
	var e = this;
	var processWin = parent.$.ligerDialog.open({
		title : '选择服务进程',
		width : 600,
		height : 450,
		url : basePath+'processManage/processRegister/selectProcess.jsp?processType=10',
		buttons :[
		          { text: '确定', width: 60 ,onclick:function(item, dialog){
		        	  
		        	    var data = dialog.frame.getSelectData();
						if (data == null) { 
							parent.Alert.tip("请选择进程数据！");
							return; 
						}
						serviceProcessData = data;
						liger.get(e.id).setText(data.processCode);
						liger.get(e.id).setValue(data.processCode);
						
						processWin.close();
		          }},
		          { text: '关闭', width: 60, onclick:function(item, dialog){
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
		          { text: '确定', width: 60 ,onclick:function(item, dialog){
		        	  
		        	    var data = dialog.frame.getSelectData();
						if (data == null) { 
							parent.Alert.tip("请选择卫星数据！");
							return; 
						}
						liger.get("satMid").setText(data.satCode);
						liger.get("satMid").setValue(data.satMid);
						satSatWin.close();
		          }},
		          { text: '关闭', width: 60, onclick:function(item, dialog){
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
		processInfo.processType = 10;
		if(processInfo.processName == ""){
			parent.Alert.tip("服务器名称信息不能为空！");
			return false;
		}
		//进程类型---服务代理进程
		processInfo.isMainProcess = "";
		var ip = processInfo.computerIp;
		if(ip == ""){
			parent.Alert.tip("服务器IP信息不能为空");
			return false;
		}
		/*var reg = /^(1\d{2}|2[0-4]\d|25[0-5]|[1-9]\d|[1-9])\.((1\d{2}|2[0-4]\d|25[0-5]|[1-9]\d|\d)\.){2}(1\d{2}|2[0-4]\d|25[0-5]|[1-9]\d|\d)$/;
		if(!reg.test(ip)){
			parent.Alert.tip("服务器IP格式不正确！");
			return false;
		}*/
		if(ip){
			var ipArr = ip.split("\.");
			if(ipArr.length != 4){
				parent.Alert.tip("服务器IP格式不正确");
				return false;
			}
			var reg = /^(1\d{2}|2[0-4]\d|25[0-5]|[1-9]\d|[1-9])$/;
			if(!reg.test(ipArr[0])){
				parent.Alert.tip("服务器IP格式不正确");
				return false;
			}
			var reg = /^(1\d{2}|2[0-4]\d|25[0-5]|[1-9]\d|\d)$/;
			if(!reg.test(ipArr[1]) || !reg.test(ipArr[2])){
				parent.Alert.tip("服务器IP格式不正确");
				return false;
			}
			var reg = /^(1\d{2}|2[0-4]\d|25[0-5]|[1-9]\d|\d)$/;
			if(!reg.test(ipArr[3])){
				parent.Alert.tip("服务器IP格式不正确");
				return false;
			}
		}
		saveProcessInfo(processInfo);
	} else {
		return false;
	}
}

function judgeIsHaveMain(processType){
	var flag = false;
	$.ajax({
		url:basePath+'rest/processManager/judgeIsHaveMain',
		data:{
			processType:processType
		},
		async:false,
		success:function(data){
			if(data == ""){
				parent.Alert.tip("请求出错！");
				return;
			}
			var jsobj = eval('('+data+')');
			if(jsobj.success == "true"){
				flag = true;
			}else{
				flag = false;
			}
		}
	});
	return flag;
}

function saveProcessInfo(processInfo){
	$.ajax({
		url:basePath+'rest/processManager/add',
		data:processInfo,
		async:false,
		success:function(data){
			if(data == ""){
				parent.Alert.tip("请求出错！");
				return;
			}
			var jsobj = eval('('+data+')');
			if(jsobj.success == "true"){
				parent.Alert.tip("注册服务器成功！");
         		//刷新表格
         		parent.closeAddWinAndLoadGrid();
			}else{
				parent.Alert.tip(jsobj.message);
			}
		}
	});
}
