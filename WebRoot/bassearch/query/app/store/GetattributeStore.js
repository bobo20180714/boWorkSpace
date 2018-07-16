Ext.define('query.store.GetattributeStore', {
	extend : 'Ext.data.Store',
	proxy: {
        type: 'ajax',
        url: 'getattribute.edq',
        reader: {
        	type:'json',
            root: 'attribute_list'
        }
    },
    fields: ['id','attribute_name','attribute_type','attribute_content','time','attribute_value']
});