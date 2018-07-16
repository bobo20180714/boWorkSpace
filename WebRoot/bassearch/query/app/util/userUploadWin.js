var formdata = null;

$(function (){
	initForm();
});

function initForm(){
	formData = $("#form5").ligerForm({
		width:600,
    	labelAlign:'center',
        inputWidth: 150, 
        labelWidth: 80, 
        space: 60,
        validate:true,//验证
        title:'文件导入',
        fields: [
            { name: "userUploadFileId1",type:"hidden"},
			{ display: "上传文件", name: "uploadFile",comboxName:"userUploadFileId2",
            	newline:true,type: "select",options:{}}
        ]
    }); 
//	$ligerui.get("userUploadFileId2").set(onBeforeCheckRow:f_select);
	
}

function f_select(){
	
}

/**
*
*获取url里的参数
*@param 参数名
*/
function getParam(name){

	var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)","i");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null){
		return unescape(r[2]);
	}
	return null;

}

//页面提交
function submitForm(){
	var name = getParam("name");
	var data = formData.getData();
	alert(JSON.stringify(data));
//	if(check(data)){
	//数据提交
	data.type = getParam("type");
	data.name=getParam("name");
	data.org_id=getParam("org_id");
//	alert(name);
	$.post(basePath+"rest/orguser/userdaoru",data, 
			function(result,textStatus) {
		if (result.success == true) {
			$.ligerDialog.success("导入成功!");
//			parent.currWin.f_reload();
			parent.currWin.f_closeDlg();
		} else {
			$.ligerDialog.error("导入失败!");
			parent.currWin.closeDlgAndReload();
		}
	}, "json");
//	}
}


















/*
Ext.define("query.util.userUploadWin", {
	extend : 'Ext.window.Window',
	alias : 'widget.userUploadWin',
	constructor:function(fileid){
		this.fileid=fileid;
		this.callParent();
	},
	initComponent : function() {
		var fileid=this.fileid;
		var form = Ext.widget('form', {
			frame:true,
			fieldDefaults : {
				labelWidth : 65
			},
			width : 335,
			bodyPadding : 10,
			items : [ {
				xtype : 'container',
				layout : 'anchor',
				items : [{
					xtype:'label',
					html:'<div style="padding-bottom: 5px;">示例模板： &nbsp;&nbsp;<a href="./file/'+fileid+'">导入示例模板下载</a></div>'
				},{
		            xtype: 'filefield',
		            allowBlank:false,
		            emptyText: '上传.csv文件',
		            fieldLabel: '上传文件',
		            name: 'uploadFile',
		            id:'userUploadFileId',
		            buttonText: '',
		            anchor : '95%',
		            buttonConfig: {
		                iconCls: 'upload-icon'
		            }
		        }]
			}],
			buttons : [{
				text : '保存',
				action : 'save'
			},{
				text : '关闭',
				action : 'close'
			}]
		});
		Ext.apply(this, {
			title : '文件导入',
			closable : true,
			layout : 'fit',
			modal : true,
			constrain : true,
			border:false,
			items : form
		});
		this.callParent(arguments);
	}
});*/
