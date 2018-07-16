Ext.define('query.store.StatusStore', {
	extend : 'Ext.data.Store',
	pageSize : 25,
	proxy: {
        type: 'ajax',
        url: 'statusword/findstatusquerypage.edq',
        reader: {
        	type:'json',
            root: 'records',
            totalProperty: 'rowCount'
        }
    },
    autoLoad: false,
    fields: [
//             {name:'status_word_id',type:'string'},
//             {name:'tm_param_id',type:'string'},
//             {name:'status_word_name',type:'string'},
//             {name:'status_word_code',type:'string'},
//             {name:'status_word_val',type:'string'},
//             {name:'status_word_type',type:'string'},
//             {name:'create_time',type:'string'},
//             {name:'create_user',type:'string'},
//             {name:'create_user_name',type:'string'}
			 {name:'tm_param_id',type:'string'},
			 {name:'mask',type:'string'},
			 {name:'cap',type:'string'},
			 {name:'create_user',type:'string'},
			 {name:'create_user_name',type:'string'},
             {name:'create_time',type:'string'},
             {name:'units',type:'string'}
      ]
});