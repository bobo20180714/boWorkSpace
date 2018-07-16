var gridManager = null;
var baseImgPath = basePath + 'lib/ligerUI/skins/icons/';
var xdlg = null;
var device_type_combo = null;
//类型（枚举：0 固定站，1地面移动站，2测控船）
var typeDataArr = [{id:'0',text:'固定站'},{id:'1',text:'地面移动站'},{id:'2',text:'测控船'}];
$(function() {
	//初始化地面站类型
	device_type_combo = $("#device_type").ligerComboBox({
		width : 150,
		data:typeDataArr
	});
	// 工具条
	$("#toptoolbar").ligerToolBar({
		items : [ {
			text : '新建',
			id : 'add',
			click : itemclick,
			icon:'add'
//			img : baseImgPath + 'add.gif'
		}, {
			text : '修改',
			id : 'alter',
			click : itemclick,
			icon:'update'
//			img : baseImgPath + 'modify.gif'
		}, {
			text : '删除',
			id : 'delete',
			click : itemclick,
			icon:'delete'
//			img : baseImgPath + 'delete.gif'
		}/*, {
			text : '导入',
			id : 'import',
			click : itemclick,
			img : baseImgPath + 'back.gif'
		}*/]
	});
	initForm2();
	// 重置搜索条件
	$("#resetbtn").ligerButton({
		type:'two',
		click : function() {
			clearForm();
		}
	});
	// 表格
	gridManager = $("#maingrid").ligerGrid({
		columns : [{display : '地面站ID', name : 'pk_id',width:0.1,hide:true},  
		           {
						display : '地面站编号',
						name : 'device_code',
						align : 'center',width:120
					}, {
						display : '地面站名称',
						name : 'name',
						align : 'center',width:150
					},{
						display : '地面站识别码',
						name : 'device_sid',
						align : 'center',width:150
					},{
						display : '地面站类型',
						name : 'device_type',
						align : 'center',width:150,
						render:function(item){
							if(item.device_type == "0"){
								return "固定站";
							}else if(item.device_type == "1"){
								return "地面移动站";
							}else if(item.device_type == "2"){
								return "测控船";
							}
						}
					},{
						display : '地面站描述',
						name : 'remark',
						align : 'center',width:250
					}],
		url : basePath + 'rest/deviceInfo/queryDeviceList',
		height : '100%',
		pageSize : 30,
		rowHeight:27,
		rownumbers:false,
		checkbox:true,
        cssClass: 'l-grid-gray', 
        heightDiff :0
	});
	$("#searchbtn").ligerButton({
		type:'one',
		click : function() {
			var fromDATA = formData1.getData();
			gridManager.set('parms', {
				device_code : fromDATA.device_code,
				device_name :fromDATA.device_name,
				device_type : fromDATA.device_type
			});
			gridManager.loadData();
		}
	});
});


function initForm2(){
	
    formData1 = $("#form2").ligerForm({
    		labelAlign:'right',
        	inputWidth: 100, 
            labelWidth: 80, 
            space: 5,
            validate:true,
            fields: [
				{display: '编号', id: 'device_code',name:'device_code',type:"text",newline: false},
				{display: '名称', id: 'device_name',name:'device_name',type:"text",newline: false},
				{display: '地面站类型', id: 'device_type',name:'device_type',type:"select",
					data:typeDataArr
					,newline: false}
			]
    });
}

function itemclick(item) {
	if (item.id) {
		switch (item.id) {
		case "add":
			setCurrWindows();
			xdlg = $.ligerDialog.open({
				width : 450,
				height : 340,
				title : '新建地面站',
				url : basePath + 'deviceManager/device_add.jsp',
				buttons : [ {
					text : '保存',
					type:'save',
					width : 80,
					onclick : function(item, dialog) {
						dialog.frame.submitForm();
					}
				}, {
					text : '关闭',
					type:'close',
					width : 80,
					onclick : function(item, dialog) {
						f_closeDlg();
					}
				} ]
			});
			break;
		case "alter":
			if (gridManager.selected.length != 1) {
				Alert.tip('请选择一条数据！');
				return;
			}
			setCurrWindows();
			var device_id = gridManager.selected[0].pk_id;
			xdlg = $.ligerDialog.open({
				width : 450,
				height : 340,
				title : '修改地面站',
				url : basePath + 'deviceManager/device_alter.jsp?device_id='
						+ device_id,
				buttons : [ {
					text : '保存',
					type:'save',
					width : 80,
					onclick : function(item, dialog) {
						dialog.frame.submitForm();
					}
				}, {
					text : '关闭',
					type:'close',
					width : 80,
					onclick : function(item, dialog) {
						f_closeDlg();
					}
				} ]
			});
			break;
		case "delete":
			if (gridManager.selected.length == 0) {
				Alert.tip('请至少选择一条数据！');
				return;
			}
			setCurrWindows();
			var deviceIds = "";
			for (var i = 0; i < gridManager.selected.length; i++) {
				if(i == 0){
					deviceIds = deviceIds + gridManager.selected[i].pk_id;
				}else{
					deviceIds = deviceIds + ";" + gridManager.selected[i].pk_id;
				}
			}
			$.ligerDialog.confirm('确定要【删除】选中的记录吗？',function (yes){
				if(yes){
					deleteDevices(deviceIds);
				}
			});
			break;
		case "import":
			setCurrWindows();
			xdlg = parent.$.ligerDialog
					.open({
						width : 400,
						height : 200,
						title : '[导入]地面站信息',
						url : basePath
								+ "baseInfoConfig/common/upload_excel_file.jsp",
						buttons : [ {
							text : '导入',
							width : 60,
							onclick : function(item, dialog) {
								var objData = dialog.frame.getData();
								if(!objData){
									return;
								}
								 xdlg.close();
								 var dialogTip = parent.$.ligerDialog.open({ 
									 width: 300,
									 height:100,  
									 title:"提示",
									 content:"<p>正在导入,请稍等。。。</p>"
								});
								setTimeout(function(){
									$.ajax({
										url:basePath+"rest/deviceInfo/importDevice",
										data:{
											filePath:objData.filePath
										},
										async:false,
										success:function(data){
											Alert.tip(data);
											dialogTip.close();
											gridManager.loadData();
										}
									});	
								}, 2000)
							 }
						} ]
					});
			break;
		}
	}
}

/**
 * 删除地面站
 * @param channelIds
 */
function deleteDevices(deviceIds){
	$.ajax({
		url:basePath+'rest/deviceInfo/deleteDevices',
		data:{
			deviceIds:deviceIds
		},
		async:false,
		success:function(rsData){
			if(rsData){
				var data = eval('('+rsData+')');
				if(data.success == "true"){
					Alert.tip('删除数据成功！');
					f_reload();
				}else{
					Alert.tip('删除数据失败！');
				}
			}
		}
	});
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	currWin = window;
}
// 关闭对话框刷新grid
function closeDlgAndReload() {
	f_closeDlg();
	f_reload();
}

// 关闭对话框
function f_closeDlg() {
	xdlg.close();
}
// 刷新grid
function f_reload() {
	gridManager.set({
		newPage : 1
	});
	gridManager.set('parms', {
		device_code : "",
		device_name :"",
		device_type : ""
	});
	gridManager.loadData();
}

function clearForm(){
	formData1.setData({
		device_code:"",
		device_name:"",
		device_type:""
	});
}