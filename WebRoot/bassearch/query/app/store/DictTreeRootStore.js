Ext.define('query.store.DictTreeRootStore', {
	extend : 'Ext.data.TreeStore',
	expanded:false,
	 fields : [
		  {name : 'id',type : 'string',convert:function(v,record){
				  return record.get("dict_id");
				}
		  },
		  {name : 'text',type : 'string',convert:function(v,record){
				  return record.get("type_name");
				}
		   },
          {name : 'dict_id',type : 'string'},
          {name : 'type_name',type : 'string'},
          {name : 'type_value',type : 'string'},
          {name : 'remark',type : 'string'},
          {name : 'status',type : 'string'},
          {name : 'parent_dict_id',type : 'string'}
    ],
	proxy : {
		type: 'ajax',
        url: 'dict/finddicttreeroot.edq',
        reader: {
            root: 'nodes'
        }
	},
	autoLoad:true
});