Ext.define('query.store.DriverModelStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	proxy: {
        type: 'ajax',
        url: 'drivermodel/finddrivermodelquerypage.edq',
        reader: {
        	type:'json',
            root: 'records',
            totalProperty: 'rowCount'
        }
    },
    autoLoad: true,
    fields: ['driver_id','driver_name','driver_type','driver_ip','driver_port','database_user_name','database_user_pass','database_name','status','create_user','create_user_name','create_time']
});