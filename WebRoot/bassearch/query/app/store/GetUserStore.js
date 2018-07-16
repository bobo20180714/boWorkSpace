Ext.define('query.store.GetUserStore', {
	extend : 'Ext.data.Store',
	pageSize : 50,
	proxy: {
        type: 'ajax',
        url: 'orguser/finduserquerypage.edq',
        reader: {
        	type:'json',
            root: 'records',
            totalProperty: 'rowCount'
        }
    },
    autoLoad: false,
    fields: [
		{name : 'user_id',type : 'string'},
		{name : 'login_name',type : 'string'},
		{name : 'user_name',type : 'string'},
		{name : 'zw',type : 'string'},
		{name : 'password',type : 'string'},
		{name : 'org_id',type : 'string'},
		{name : 'telephone',type : 'string'},        
		{name : 'danwei',type : 'string'},        
		{name : 'bumen',type : 'string'},   
		{name : 'user_status',type : 'string'},        
		{name : 'create_user',type : 'string'},        
		{name : 'create_time',type : 'string'},        
		{name : 'zhiwu',type : 'string'},        
		{name : 'create_user_name',type : 'string'},        
		{name : 'org_name',type : 'string'},        
		{name : 'struct',type : 'string'},
		{name : 'end_time',type : 'string'}
     ]
});