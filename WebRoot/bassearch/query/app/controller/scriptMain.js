Ext.define('query.controller.scriptMain', {
	extend  : 'Ext.app.Controller',
	init : function() {
		this.control({
			'mainView > panel':{
				'render':function(){
					var app=query.getApplication();
					var controller = app.controllers.get('query.controller.relation.relationSearch.RelationSearchController');
					if (!controller) {
						controller = app.getController('query.controller.relation.relationSearch.RelationSearchController');
					}
					var tabpanel = Ext.getCmp('center_tab');
					var panelId='view_'+tabpanel.id;
					var view = Ext.create('query.view.relation.relationSearch.RelationSearchView');
					var panel = {
							layout : 'fit',
							title : '关联查询',
							id : panelId,
							border:false,
							closable : true,
							autoRender : true,
							items:view
						};
					tab = tabpanel.add(panel);
					tabpanel.setActiveTab(tab);
				}
			}
		});
	},
	/**
	 * 每个一级菜单渲染时触发
	 */
	onMenuRender : function() {
		
	},
	/**
	 * 该控制器执行完成后触发
	 */
	onLaunch : function() {
		
	}
});
