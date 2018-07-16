Ext.define('query.view.userWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.userWin',
	initComponent : function() {
		var individual = {
			xtype : 'fieldset',
			defaults : {
				anchor : '100%',
				style : 'padding-top:4px;'
			},
			collapsed : false,
			items : [ {
				xtype : 'container',
				layout : 'hbox',
				items : [ {
					xtype : 'container',
					flex : 1,
					layout : 'anchor',
					defaultType : 'textfield',
					items : [ {
						name : 'userId',
						hidden : true
					}, {
						fieldLabel : '用户',
						name : 'userName',
						anchor : '95%'
					}, {
						fieldLabel : '创建时间',
						name : 'userCreatetime',
						xtype : 'datefield',
						format : 'Y-m-d H:i:s',
						anchor : '95%'
					} ]
				}, {
					xtype : 'container',
					flex : 1,
					layout : 'anchor',
					defaultType : 'textfield',
					items : [ {
						fieldLabel : '密码',
						name : 'userPassword',
						anchor : '95%'
					}, {
						fieldLabel : '修改时间',
						name : 'userUpdatetime',
						xtype : 'datefield',
						format : 'Y-m-d H:i:s',
						anchor : '95%'
					} ]
				} ]
			} ]
		};
		var checkGroup = {
			xtype : 'fieldset',
			title : '自我展示',
			layout : 'anchor',
			defaults : {
				anchor : '100%',
				labelStyle : 'padding-left:4px;'
			},
			collapsible : true,
			collapsed : false,
			items : [ {
				xtype : 'textarea',
				name : 'txt-test3',
				fieldLabel : '自我介绍'
			}, {
				xtype : 'textarea',
				name : 'txt-test3',
				fieldLabel : '自我评论'
			} ]
		};
		var form = Ext.widget('form', {
			frame:true,
			fieldDefaults : {
				labelWidth : 70
			},
			width : 650,
			bodyPadding : 10,
			items : [ individual,checkGroup ],
			buttons : [ {
				text : '保存',
				action : 'save'
			}, {
				text : '重置',
				action : 'reset'
			} ]
		});
		Ext.apply(this, {
			title : '用户新增',
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