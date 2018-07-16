Ext.define('query.store.HasRoleStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	proxy: {
        type: 'ajax',
        url: 'role/findhasrole.edq',
        reader: {
        	type:'json',
            root: 'result'
        }
    },
    autoLoad: true,
    fields: ['role_id','role_name','user_id','group_id']
});