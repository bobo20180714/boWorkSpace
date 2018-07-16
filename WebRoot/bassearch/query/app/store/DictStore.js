Ext.define('query.store.DictStore', {
	extend : 'Ext.data.TreeStore',
	expanded:false,
	pageSize:100,
	 fields : [
          {name : 'dict_id',type : 'string'},
          {name : 'type_name',type : 'string'},
          {name : 'type_value',type : 'string'},
          {name : 'remark',type : 'string'},
          {name : 'status',type : 'string'},
          {name : 'parent_dict_id',type : 'string'}
    ],
	proxy : {
		type: 'ajax',
        url: 'dict/finddicttree.edq',
        reader: {
            root: 'nodes'
        }
	},
	autoLoad:false,
	nodeParam:'dict_id'
});