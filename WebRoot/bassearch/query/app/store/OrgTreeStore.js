Ext.define('query.store.OrgTreeStore', {
	extend : 'Ext.data.TreeStore',
	expanded:false,
	 fields : [
          {name : 'id',type : 'string'},
          {name : 'text',type : 'string',convert:function(v,record){
         	  if(record.get("org_id")=='-1') return record.get("org_name");
         	  return "["+record.get("org_code")+"]<font color=blue>"+record.get("org_name")+"</font>";
     	  }},
          {name : 'org_id',type : 'string'},
          {name : 'org_name',type : 'string'},
          {name : 'org_code',type : 'string'},
          {name : 'org_desc',type : 'string'},
          {name : 'org_parent_id',type : 'string'},
          {name : 'struct',type : 'string'}
    ],
	proxy : {
		type: 'ajax',
        url: 'orguser/findorgtree.edq',
        reader: {
            root: 'nodes'
        }
	},
	root:{
		id:'-1',
		org_id:'-1',
		org_name:'机构列表',
		org_parent_id:'-1',
		expanded:true,
		leaf:false
	},
	nodeParam:'org_parent_id'
});