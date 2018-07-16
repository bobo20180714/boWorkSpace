Ext.define('query.controller.main', {
	extend  : 'Ext.app.Controller',
	doCommonControl : Ext.create("query.util.PreCondition"),
	init : function() {
		this.control({
			'mainView toolbar [controllerUrl]':{
				'click':function(btn){
					btn.up("menu").hide();
					var app=query.getApplication();
					var controller = app.controllers.get(btn.controllerUrl);
					if (!controller) {
						controller = app.getController(btn.controllerUrl);
					}
					if(btn.id=='preSearchSave'){
						if(this.doCommonControl.getLineCmp()==null){
							Ext.example.msg('系统提示', "您所处页面无法完成预制条件创建！");
							return;
						}
						if(this.doCommonControl.getStore().getCount()>0){
							win = Ext.create('query.view.search.preSearch.PreSearchSaveWin');
							if(win){
								win.show();								
							}						
						}
						else{
							Ext.example.msg('系统提示', "您还未选择查询参数，无法完成预制条件创建！");
						}
						return;
					}
					if(btn.id=='ResultSearch'){
						win = Ext.create('query.view.search.resultSearch.ResultSearchWin');
						if(win){
							win.show();
							return;
						}
					}
					//获得tabpanel对象
					var tabpanel = Ext.getCmp('center_tab');
					var panelId=btn.id+tabpanel.id;
					//通过编号获得面板
					var tab = tabpanel.getComponent(panelId);
					Ext.getBody().mask('正在加载页面....');  
					if (!tab) {//添加
						tab = tabpanel.add({
							layout : 'fit',
							title : btn.text,
							id : panelId,
							border:false,
							closable : true,
							items:[{
								xtype:btn.aliasName
							}]
						});
					}
					tabpanel.setActiveTab(tab);
					Ext.getBody().unmask();
				}
			},
			'mainView':{
				'afterlayout':function(obj,opts){
					if(isInit){
						var app=query.getApplication();
						//获得tabpanel对象
						var tabpanel = Ext.getCmp('center_tab');
						var controller = app.getController('query.controller.search.basicSearch.BasicSearchController');
						var view = Ext.create('query.view.search.basicSearch.BasicSearchView');
						if(_condtion)view.condtion=_condtion;
						var panelId='BasicSearch'+tabpanel.id;
	//					var controller = app.getController('query.controller.relation.relationSearch.RelationSearchController');
	//					var view = Ext.create('query.view.relation.relationSearch.RelationSearchView');
	//					var panelId='RelationSearch'+tabpanel.id;
						//通过编号获得面板
						var tab = tabpanel.getComponent(panelId);
						Ext.getBody().mask('正在加载页面....');  
						if (!tab) {//添加
							tab = tabpanel.add({
								layout : 'fit',
								title : '工程值曲线查询',
								id : panelId,
								border:false,
								closable : true,
								items:[view]
							});
						}
						tabpanel.setActiveTab(tab);
						Ext.getBody().unmask();
						isInit = false;
					}
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
