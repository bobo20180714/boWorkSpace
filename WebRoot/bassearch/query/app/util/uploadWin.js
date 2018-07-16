Ext.define("query.util.uploadWin", {
	extend : 'Ext.window.Window',
	alias : 'widget.uploadWin',
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
		            emptyText: '上传.xls.csv文件',
		            fieldLabel: '上传文件',
		            name: 'uploadFile',
		            id:'paramUploadId',
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
});
