Ext.define('query.store.AllSatHasParamStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	proxy: {
        type: 'ajax',
        url: 'limit/findUserLimits.edq',
        reader: {
        	type:'json',
            totalProperty: 'rowCount'
        }
    },
    autoLoad: false,
    fields: [
        {name:'pk_id',type:'string'},
		{name:'owner_id',type:'string'},
		{name:'owner_name',type:'string'},
		{name:'res_id',type:'string'},
		{name:'res_name',type:'string'},
		{name:'res_code',type:'string'},
		{name:'res_type',type:'string'},
		{name:'res_url',type:'string'},
		{name:'end_time',type:'string'}
    ]
});