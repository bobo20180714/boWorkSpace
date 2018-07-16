Ext.define('query.model.GrantUserGroupOrbitRelatedModel', {
	extend : 'Ext.data.Model',
	 fields: [
	          {name : 'jsjg_id',type : 'string'},
	          {name : 'jsjg_name',type : 'string'},
	          {name : 'jsjg_code',type : 'string'},
	          {name : 'jsjg_desc',type : 'string'},
	          {name : 'is_time_range',type : 'int'},
	          {name : 'start_time',type : 'string'},
	          {name : 'end_time',type : 'string'},
	          {name : 'view_col',type : 'string'},
	          {name : 'jsjg_status',type : 'string'},
	          {name : 'create_user',type : 'string'},
	          {name : 'create_user_name',type : 'string'},
	          {name : 'create_time',type : 'string'},
	          
	          {name : 'Type',type : 'int'},
	          {name : 'dataTypeId',type : 'string'},
	          {name : 'mid',type : 'int'},
	          {name : 'Begin',type : 'string'},
	          {name : 'End',type : 'string'},
	          {name : 'startCol',type : 'string'},
	          {name : 'endCol',type : 'string'},
	          {name : 'viewCol',type : 'string'},
	          {name : 'Color',type : 'string'},
	          {name : 'RectColor',type : 'string'},
	          {name : 'typeName',type : 'string'}
	      ]
});