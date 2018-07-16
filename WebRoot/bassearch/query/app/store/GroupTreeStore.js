Ext.define('query.store.GroupTreeStore', {
	extend : 'Ext.data.TreeStore',
	expanded:false,
	 fields : [
          {name : 'id',type : 'string',convert:function(v,record){
        	  return record.get("group_id");
          	}
          },
          {name : 'text',type : 'string',convert:function(v,record){
        	  return record.get("group_name");
        	}
          },
          {name : 'group_id',type : 'string'},
          {name : 'group_name',type : 'string'},
          {name : 'group_desc',type : 'string'},
          {name : 'group_status',type : 'string'},
          {name : 'create_user',type : 'string'},
          {name : 'create_time',type : 'string'},
          {name : 'iconCls',type : 'string',defaultValue:'x-tree-icon-parent'}
    ],
	proxy : {
		type: 'ajax',
        url: 'group/findgrouptree.edq',
        reader: {
            root: 'nodes'
        }
	},
	root:{
		group_id:'groupRoot',
		group_name:'用户组列表',
		expanded:true,
		leaf:false
	}
});