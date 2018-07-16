Ext.define('query.store.GetTmParamStore', {
	extend : 'Ext.data.Store',
	pageSize : 50,
	proxy: {
        type: 'ajax',
        url: 'tmparams/findtmparamallquerypage.edq',
        reader: {
        	type:'json',
            root: 'records',
            totalProperty: 'rowCount'
        }
    },
    autoLoad: false,
    fields: [
             {name:'tm_param_id',type:'string'},
             {name:'tm_param_name',type:'string'},
             {name:'tm_param_code',type:'string'},
             {name:'tm_param_type',type:'string'},
             {name:'tm_param_bdh',type:'string'},
             {name:'status',type:'string'},
             {name:'owner_id',type:'string'},
             {name:'owner_name',type:'string'},
             {name:'create_user',type:'string'},
             {name:'create_user_name',type:'string'},
             {name:'create_time',type:'string'},
             {name:'tm_param_num',type:'string'}
      ]
});