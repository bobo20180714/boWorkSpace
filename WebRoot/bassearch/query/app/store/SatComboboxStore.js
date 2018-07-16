Ext.define('query.store.SatComboboxStore', {
	extend : 'Ext.data.Store',
	proxy: {
        type: 'ajax',
        url: 'satsubalone/satcombobox.edq',
        reader: {
            root: 'nodes'
        }
    },
    fields: [
		{name : 'id',type : 'string',convert:function(v,record){
			  return record.get("sys_resource_id")+"_"+record.get("code");
			}
		},
		{name : 'text',type : 'string',convert:function(v,record){
			  return record.get("name");
			}
		},
		{name : 'sys_resource_id',type : 'string'},
		{name : 'name',type : 'string'},
		{name : 'code',type : 'string'},
		{name : 'status',type : 'string'},
		{name : 'create_user',type : 'string'},
		{name : 'create_time',type : 'string'},
		{name : 'owner_id',type : 'string'},
		{name : 'type',type : 'string'},
		{name : 'mid',type : 'string'}
    ]
});