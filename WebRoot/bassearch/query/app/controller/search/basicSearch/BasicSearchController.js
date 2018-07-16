Ext.define('query.controller.search.basicSearch.BasicSearchController', {
	extend : 'Ext.app.Controller',
	init : function() {
		this.control({
			'BasicSearchView > tabpanel panel[id=Chart]':{
				'render':function(){
					layoutFlag=true;
				}
			}
		});
	},
	addCond:function(mv){
		var cgxs=Ext.getCmp('CGXS'),
			cond=mv.condtion;
		if (cond.isTile) {
            cgxs.setIcon('img/pp.ico');
            cgxs.setTooltip('平铺显示');
        }
        else {
            cgxs.setIcon('img/cg.ico');
            cgxs.setTooltip('常规显示');
        }
		//添加查询曲线
		var data=cond.data,
			lineStore=Ext.getCmp('baseLineCmpGrid').getStore();
		for (var i = 0; i < data.length; i++) {
			var rec=Ext.create('Record',data[i]); 
            if (_cmd.Add({ Type: '添加条件查询', Param: rec ,IsTile: cond.isTile,isRelativeQuery:cond.isRelativeQuery})) {
            	lineStore.add(rec);					            	
            }  
		}
		Ext.getCmp('XTB').enable();
	},
	getLastLineId:function(mv){
		var data=mv.condtion.data,id=-1;
		for (var i = 0; i < data.length; i++) {
			if(data[i].Id-id>0)id=data[i].Id;
		}
		return id;
	},
	views  : [ 'query.view.search.basicSearch.BasicSearchView' ]
});