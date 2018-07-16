//表单对象
var processForm = null;
//启动参数表格对象
var paramGridObj = null;

//进程类型
var processTypeArr = [{id:'0',text:'进程调度'},{id:'3',text:'遥测处理'},{id:'4',text:'门限报警'}
						,{id:'5',text:'控制量计算'},{id:'6',text:'数据存储'},{id:'10',text:'服务代理进程'}];

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
             { display: "进程类型", name: "processType",id:"processType",
            	 type: "select", newline: true,data:processTypeArr,
            	 value:3,cancelable:false,readonly:true,
            	 validate:{required:false},onselected:selectProcessType
             },
            /* { display: "进程标识", name: "processCode",id:"processCode",
            	 type: "text", newline: false,validate:{required:false,maxlength:25}
             },*/
             { display: "进程名称", name: "processName",id:"processName",
            	 type: "text", newline: false,validate:{required:false,maxlength:50},readonly:true
             },
             { display: "服务代理进程", name: "serviceProcessCode",id:"serviceProcessCode",
            	 type: "text", newline: true,readonly:true
             },
             { display: "服务器IP", name: "computerIp",id:"computerIp",
            	 type: "text", newline: false,readonly:true
             },
             { display: "所属卫星编号", name: "satMid",id:"satMid",
            	 type: "text", newline: true,readonly:true
             },
             { display: "进程启动类型", name: "startupType",id:"startupType",
            	 type: "text", newline: false
            	 ,validate:{required:false},readonly:true
             },
             { display: "是否是备用进程", name: "isMainProcess",id:"isMainProcess",
 				type: "select", newline: true,validate:{required:false},
 				value:0,data:[{id:"1",text:"是"},{id:"0",text:"否"}]
 				,onselected:selectIsMainProcess,readonly:true
 			},
 			{ display: "主进程标识", name: "mainProcessCode",id:"mainProcessCode",
 				type: "text", newline: false,validate:{required:false},readonly:true
 			},
			{ display: "进程启动路径", name: "startupPath",id:"startupPath",
				type: "text", newline: true,width:470
				,validate:{required:false},readonly:true
			},
			{ display: "启动参数", name: "startupParam",id:"startupParam",
				type: "textarea", newline: true,width:470,readonly:true
			}
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
		
//		liger.get("computerIp").setEnabled(true);
	}else{
		liger.get("serviceProcessCode").setEnabled(true);
		liger.get("startupType").setEnabled(true);
		liger.get("startupPath").setEnabled(true);
		liger.get("isMainProcess").setEnabled(true);
		liger.get("satMid").setEnabled(true);
		$("#startupParam")[0].disabled = false;
		
//		liger.get("computerIp").setDisabled(true);
	}
}

/**
 * 选择是否是主进程事件
 */
function selectIsMainProcess(e){
	if(e == 1){
//		liger.get("mainProcessCode").setEnabled(true);
	}else{
//		liger.get("mainProcessCode").setDisabled(true);
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
		          { text: '确定', type:'save', width: 80,onclick:function(item, dialog){
		        	  
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
						liger.get("satMid").setValue(data.satMid);
						satSatWin.close();
		          }},
		          { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
		        	  satSatWin.close();
		          }}
		          ]
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
			liger.get("satMid").setValue(jsobj.satCode);
			liger.get("serviceProcessCode").setValue(jsobj.serviceProcessName);
		}
	});
}
