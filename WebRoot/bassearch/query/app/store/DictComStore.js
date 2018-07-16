Ext.define('query.store.DictComStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	proxy: {
        type: 'ajax',
        url: 'dict/finddictcom.edq',
        reader: {
        	type:'json',
            root: 'result'
        }
    },
    autoLoad: true,
    fields: [
          {name : 'dict_id',type : 'string'},
          {name : 'type_name',type : 'string'},
          {name : 'type_value',type : 'string'},
          {name : 'remark',type : 'string'},
          {name : 'status',type : 'string'},
          {name : 'parent_dict_id',type : 'string'}
     ]
});