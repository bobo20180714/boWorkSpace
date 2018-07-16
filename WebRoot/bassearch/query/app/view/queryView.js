/*
 *创建一期主界面
 */
Ext.define('query.view.queryView',{
	extend: 'Ext.Viewport',
	alias : 'widget.queryView',
	initComponent : function(){	
		var alias=null,condtion=null;
        Ajax.send('precondition/getonecond.edq',{id:_precondId},function(json){
        	json=json.conditions;
        	switch(json.owner){
	    		case '图形查询':
	    			alias='RelationSearchView';
	    			break;
	    		case '图形关联查询':
	    			alias='RelationSearchView';
	    			break;
	    		case '工程值曲线查询':
	    			alias='BasicSearchView';
	    			break;
	    	}
        	condtion=Ext.JSON.decode(json.condtion);
        },false);
		Ext.apply(this, {
        	layout: 'fit',
    		hideBorders: true,
    		items:[{
    			xtype:alias ,
    			condtion:condtion
    		}]
        });
        this.callParent(arguments);
	}
});

