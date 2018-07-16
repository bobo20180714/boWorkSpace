Ext.define('query.store.SatTreeStore', {
	extend : 'Ext.data.TreeStore',
	expanded:false,
	 fields : [
          {name : 'id',type : 'string',convert:function(v,record){
        	  return record.get("sat_id");
          	}
          },
          {name : 'text',type : 'string',convert:function(v,record){
        	  return record.get("sat_name");
        	}
          },
          {name : 'sat_id',type : 'string'},
          {name : 'sat_name',type : 'string'},
          {name : 'sat_code',type : 'string'},
          {name : 'mid',type : 'string'},
          {name : 'iconCls',type : 'string',defaultValue:'x-tree-icon-parent'}
    ],
	proxy : {
		type: 'ajax',
        url: 'sattreestore.edq',
        reader: {
            root: 'nodes'
        }
	},
	root:{
		sat_id:'groupRoot',
		sat_name:'航天器信息',
		expanded:true,
		leaf:false
	}
});