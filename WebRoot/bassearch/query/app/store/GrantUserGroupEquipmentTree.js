Ext.define('query.store.GrantUserGroupEquipmentTree', {
	extend : 'Ext.data.TreeStore',
	expanded:false,
	 fields : [
		  {name : 'id',type : 'string',convert:function(v,record){
				  return record.get("sys_resource_id");
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
    ],
	proxy : {
		async: true,
		type: 'ajax',
        url: _url+'satinfoLimit/findgrantusergroupequipmenttree',
        reader: {
            root: 'nodes'
        }
	},
	root:{
		sys_resource_id:-1
	},
	nodeParam:'sys_resource_id',
	autoLoad:true
});