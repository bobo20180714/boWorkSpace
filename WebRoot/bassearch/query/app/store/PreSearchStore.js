//预制条件管理
Ext.define('query.store.PreSearchStore', {
	extend : 'Ext.data.Store',
	pageSize : 10,
	proxy: {
        type: 'ajax',
        url: 'precondition/getprecond.edq',
        extraParams:{
        	userId:userInfo.user_id
        },
        reader: {
        	type:'json',
            root: 'conditions'
        }
    },
    autoLoad: true,
    fields: ['id','user_id', 'name', 'descript', 'type','create_time','owner']
});