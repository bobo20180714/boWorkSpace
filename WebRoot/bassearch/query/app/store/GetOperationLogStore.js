Ext.define('query.store.GetOperationLogStore', {
	extend : 'Ext.data.Store',
	pageSize : 30,
	proxy : {
		type : 'ajax',
		url : 'syslog/findoperatelogquerypage.edq',
		reader : {
			type : 'json',
			root : 'records',
			totalProperty : 'rowCount'
		}
	},
	autoLoad : true,
	fields : [ 
			{name : 'flowNo',type : 'string'},
			{name : 'startTime',type : 'string'},
		 	{name : 'endTime',type : 'string'},
		 	{name : 'appId',type : 'string'},
		 	{name : 'userCode',type : 'string'},
		 	{name : 'userName',type : 'string'},
		 	{name : 'organCode',type : 'string'},
		 	{name : 'organName',type : 'string'},
		 	{name : 'clientIp',type : 'string'},
		 	{name : 'result',type : 'string'},
		 	{name : 'operateType',type : 'string'},
		 	{name : 'entityCode',type : 'string'},
		 	{name : 'entityName',type : 'string'},
		 	{name : 'operateContent',type : 'string'}
	]
});