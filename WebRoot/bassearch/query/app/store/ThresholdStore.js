Ext.define('query.store.ThresholdStore', {
	extend : 'Ext.data.Store',
	pageSize : 25,
	proxy: {
        type: 'ajax',
        url: 'threshold/findthresholdquerypage.edq',
        reader: {
        	type:'json',
            root: 'records',
            totalProperty: 'rowCount'
        }
    },
    autoLoad: false,
    fields: [
             {name:'threshold_id',type:'string'},
             {name:'tm_param_id',type:'string'},
             {name:'threshold_type',type:'string'},
             {name:'threshold_title',type:'string'},
             {name:'threshold_min',type:'string'},
             {name:'threshold_max',type:'string'},
             {name:'create_time',type:'string'},
             {name:'create_user',type:'string'},
             {name:'create_user_name',type:'string'}
      ]
});