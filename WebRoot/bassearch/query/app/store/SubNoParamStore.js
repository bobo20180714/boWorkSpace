Ext.define('query.store.SubNoParamStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	proxy: {
        type: 'ajax',
        url: 'tmparams/findsubnoparambysubquerypage.edq',
        reader: {
        	type:'json',
            root: 'records',
            totalProperty: 'rowCount'
        }
    },
    autoLoad: true,
    fields: [
		{name:'sys_resource_id',type:'string'},
		{name:'name',type:'string'},
		{name:'tm_param_id',type:'string'},
		{name:'owner_id',type:'string'},
		{name:'tm_param_num',type:'string'},
		{name:'tm_param_name',type:'string'},
		{name:'tm_param_code',type:'string'},
		{name:'tm_param_type',type:'string'},
		{name:'create_user',type:'string'},
		{name:'create_time',type:'string'},
		{name:'struct',type:'string'}
    ]
});