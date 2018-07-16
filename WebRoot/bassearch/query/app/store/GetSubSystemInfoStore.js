Ext.define('query.store.GetSubSystemInfoStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	proxy: {
        type: 'ajax',
        url: 'getsubsysteminfobysat.edq',
        reader: {
        	type:'json',
            root: 'result',
        	totalProperty: 'result_count'
        }
    },
    autoLoad: false,
    fields: [
             {name:'sub_system_id',type:'string'},
             {name:'sub_system_name',type:'string'},
             {name:'sub_system_code',type:'string'},
             {name:'sub_system_type',type:'string'},
             {name:'sub_system_type_name',type:'string'},
             {name:'status',type:'string'},
             {name:'create_user',type:'string'},
             {name:'create_user_name',type:'string'},
             {name:'create_time',type:'string'}
      ]
});