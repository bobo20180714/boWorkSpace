Ext.define('query.store.GetStandAloneInfoStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	proxy: {
        type: 'ajax',
        url: 'getstandaloneinfobysub.edq',
        reader: {
        	type:'json',
            root: 'result',
            totalProperty: 'result_count'
        }
    },
    autoLoad: false,
    fields: [
             {name:'stand_alone_id',type:'string'},
             {name:'stand_alone_name',type:'string'},
             {name:'stand_alone_code',type:'string'},
             {name:'stand_alone_type',type:'string'},
             {name:'stand_alone_type_name',type:'string'},
             {name:'status',type:'string'},
             {name:'create_user',type:'string'},
             {name:'create_user_name',type:'string'},
             {name:'create_time',type:'string'}
      ]
});