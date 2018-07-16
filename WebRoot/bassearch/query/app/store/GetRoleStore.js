Ext.define('query.store.GetRoleStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	proxy: {
        type: 'ajax',
        url: 'role/findrolequerypage.edq',
        reader: {
        	type:'json',
            root: 'records',
            totalProperty: 'rowCount'
        }
    },
    autoLoad: true,
    fields: ['role_id','role_name','role_desc','role_status','create_user','create_user_name', 'create_time']
});