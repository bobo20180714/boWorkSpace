Ext.define('query.store.GetSatBasicInfoStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	proxy: {
        type: 'ajax',
        url: 'satinfo/findsatbasicinfoquerypage.edq',
        reader: {
        	type:'json',
            root: 'records',
            totalProperty: 'rowCount'
        }
    },
    autoLoad: true,
    fields: ['sat_id','sat_name','sat_code','design_org', 'user_org', 'design_life', 'launch_time','over_life','location','domain','platform','first_designer','team','duty_officer','create_user','create_time','status','mid','multicast_address','udp_port']
});