Ext.define('query.store.GrantUserGroupTmParam', {
	extend : 'Ext.data.Store',
	pageSize : 50,
	proxy : {
		type : 'ajax',
		url : 'tmparams/findgrantusergrouptmparamquerypage.edq',
		reader : {
			root : 'records',
			totalProperty : 'rowCount'
		}
	},
	autoLoad:false,
	fields : [ 'tm_param_id', 'tm_param_name', 'tm_param_code',
			'tm_param_type', 'tm_param_num', 'create_user', 'create_time',
			'struct', 'type', 'sat_id', 'formula' ]
});