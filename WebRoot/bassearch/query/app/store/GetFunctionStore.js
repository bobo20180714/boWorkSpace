//门限条件
Ext.define('query.store.GetFunctionStore', {
	extend : 'Ext.data.Store',
	pageSize : 10,
	proxy: {
        type: 'ajax',
        url: 'getfunctionlist.edq',
        reader: {
        	type:'json',
            root: 'function_list'
        }
    },
    autoLoad: true,
    fields: ['package_name', 'function_name', 'argument', 'overview','example_doc']
});