//空间环境条件
Ext.define('query.store.GetSpaceParamStore', {
	extend : 'Ext.data.Store',
	pageSize : 10,
	proxy: {
        type: 'ajax',
        url: 'getspaceparam.edq',
        reader: {
        	type:'json',
            root: 'items',
            totalProperty: 'count'
        }
    },
    fields: ['spaceParma_value', 'code', 'name', 'time','group_code']
});