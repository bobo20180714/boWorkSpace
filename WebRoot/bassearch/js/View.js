Ext.define('js.View',{
	constructor:create
});	
function create(){
	//创建主界面
	var view = Ext.create('query.view.search.basicSearch.BasicSearchView');
	Ext.create('Ext.container.Viewport', {
		layout:'fit',
		items:{
			layout:'card',
			border:false,
			activeItem:0,
				items:[view],
			bbar:sb
		}
	});  
	
	Ext.EventManager.onWindowResize(function (w,h) {
		_cmd.Add({ Type: '调整窗口尺寸', Width: w, Height: h});
	});
}
