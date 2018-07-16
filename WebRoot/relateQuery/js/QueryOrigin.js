var pageSize = 40;
Ext.onReady(function () {
	var satStore=Ext.create('Ext.data.JsonStore',{
	    proxy: {
	        type: 'ajax',
	        url: 'satparam.edq',
	        reader: {
	            root: 'nodes'
	        }
	    },
	    fields: ['name','id'],
		listeners:{
			'load':function(obj,rec){
	            Ext.getCmp('SatCmp').setValue(rec.length > 0?rec[0].get('id'):'');
			}									
		}
	});
	var ds = Ext.create('Ext.data.JsonStore',{
	    pageSize: pageSize,
	    proxy: {
	        type: 'ajax',
	        url: 'origindata.edq',
	        reader: {
	            root: 'items',
	            totalProperty: 'count'
	        }
	    },
	    fields: ['T','V'],
	    listeners: { load: function () {
	    	Ext.getCmp('DownloadBtn').setDisabled(this.getCount()==0);
	    }}
	});
	Ext.apply(Ext.form.field.VTypes, {
	    time: function(val) {
	        //匹配时间：yyyy-MM-dd HH:mm
	        return /^((\d{2}(([02468][048])|([13579][26]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|([1-2][0-9])))))|(\d{2}(([02468][1235679])|([13579][01345789]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\s(((0?[0-9])|(2[0-3])|(1[0-9]))\:([0-5]?[0-9])))?$/i.test(val);
	    },
	    timeMask:  /[\d\:\s\-]/i
	});
	Ext.apply(Ext.form.field.VTypes, {
	    integer: function(val) {
	        //匹配数字
	        return /^[-\+]?\d+(\.\d+)?([\e\E][-\+]\d+)?$/i.test(val);
	    },
	    integerMask:  /[\d]/i
	});
//	var Now = function () {
//	    var t2 = new Date();
//	    var t1 = new Date(t2.getTime() - 86400000);
//	    this.t1 = Ext.Date.format(t1,'Y-m-d H:i');
//	    this.t2 = Ext.Date.format(t2,'Y-m-d H:i');
//	};
//	var now=new Now();
    Ext.create('Ext.container.Viewport', {
		layout: 'border',		
		items:[{
			region: 'west',
			title: '源码查询条件',
		    width: 270,
		    bodyPadding:15,		    
		    bodyStyle: 'background-color:#DFE8F6;',
		    margin: '0 5 0 0',
		    collapsible: true,
		    items: [{
		        xtype: 'fieldset',
		        title: '位置查询',
		        defaultType: 'textfield',				        
		        defaults: {
		            margin:5,
		            anchor: '100%'		            
		        },
		        fieldDefaults: {
			    	labelAlign: 'right',
			        labelWidth: 70
			    },		
		        items: [{ 
		            	xtype: 'combobox',
		            	id:'SatCmp',
		            	fieldLabel: '模型飞机' ,
		            	displayField: 'name',
		            	valueField: 'id',
		            	editable: false,
		            	emptyText: '请选择卫星...',
		            	store:satStore
		            },{ 
		            	id:'Begin',
		            	name: 'begin',
		            	fieldLabel: '开始时间' ,
		            	vtype: 'time',
		            	value:Now.getBegin()
		            },{ 
		            	id:'End',
		            	name: 'end',
		            	fieldLabel: '结束时间' ,
		            	vtype: 'time',
		            	value:Now.getBegin()
		       	}]
		    },{
	            xtype:'fieldset',
	            id:'FrameQuery',
	            title: '帧方式查询',
	            collapsible: true,
	            collapsed: true,	            
	            defaultType: 'textfield',
	            defaults: {
					margin:5,
		            anchor: '100%',
		            vtype:'integer'
				},
		        fieldDefaults: {
			    	labelAlign: 'right',
			        labelWidth: 70
			    },
	            items :[{
	                    fieldLabel: '源码包号',
	                    name: 'pkgNum'
	                },{
	                    fieldLabel: '源码帧号',
	                    name: 'frameNum'
	                },{
	                    fieldLabel: '源码路号',
	                    name: 'pathNum'
	                },{
	                    fieldLabel: '源码长度',
	                    name: 'length'
	                },{
	                    fieldLabel: '左截断位数',
	                    name: 'lefTrunc'
	                },{
	                    fieldLabel: '右截断位数 ',
	                    name: 'rightTrunc'
	                }
	            ]
	        },{
	        	border:false,
	        	bodyStyle: 'background-color:transparent;',
	        	height:20
	        },{
	        	buttonAlign: 'center',
	        	border:false,	        	
	        	buttons: [{
		            text: '查询',
		            handler:function(){	
		            	ds.proxy.extraParams=getParams();
		            	ds.load({ params: { start: 0, limit: pageSize} });
		            }
		        },{
			        text: '重置',
			        handler:function(){			        	
			        	//now=new Now();
			        	fields[0].setValue(Now.getBegin());
			        	fields[1].setValue(Now.getBegin());
			        	for(var i=2;i<fields.length;i++){
			        		fields[i].setValue('');
			        	}
			        }
		        }]
	        }]	        
		},{
			region: 'center',
			layout:'fit',
			border: false,
			items:Ext.create('Ext.grid.Panel',{
		        sortableColumns:false,
		        columns: [{ 
		        	header: "时间", 
		        	dataIndex: 'T', 
		        	width: 160
		        },{
		        	header: "值", 
		        	dataIndex:'V',
		        	flex:1
		        }],
		        store: ds,
		        tbar:['源码查询结果',{
		        	xtype:'tbfill'
	        	},{
		        	id:'DownloadBtn',
		        	xtype:'button',
		        	text:'下载源码',
		        	disabled : true,
		        	handler:function(){
		        		if (!window._df) {
							_df = document.createElement('iframe');
			            	document.body.appendChild(_df);	
			            	_df.style.visibility = "hidden";
						}
			            _df.src = "download?" + Ext.urlEncode(getParams(true));	
		        	}
		        }],
		        bbar: {
		            xtype: 'pagingtoolbar',
		            store: ds,
		            displayInfo: true,
		            displayMsg: '当前显示{0}-{1}条记录/共{2}条记录',
		            emptyMsg: '无显示数据'
		        }
		    })	
		}]
	});
	var fields = [Ext.getCmp('Begin'),Ext.getCmp('End')];
	fields = fields.concat(Ext.ComponentQuery.query('textfield',Ext.getCmp('FrameQuery')));
	var getParams=function(isDown){
		var ty=Ext.getCmp('FrameQuery').collapsed?0:1,params={
			type:isDown?ty+10:ty,
			satId:Ext.getCmp('SatCmp').getValue()
		};
		for(var i=0;i<fields.length;i++){
			var v=fields[i].getValue();
			if(i>1)v*=1;
			params[fields[i].name]=v;
		}
		return params;
	};
	
	satStore.load();
});
/*
 * var win=Ext.create('Ext.window.Window', {
	    title: '源码查询',
	    width: 400,
	    bodyPadding:15,
	    buttonAlign: 'center',
	    items: [{
	        xtype: 'fieldset',
	        title: '位置查询',
	        defaultType: 'textfield',				        
	        defaults: {
	            margin:'5 30 5 30',
	            anchor: '100%'
	        },
	        fieldDefaults: {
		    	labelAlign: 'center',
		        labelWidth: 70
		    },		
	        items: [{ 
	            	xtype: 'combobox',
	            	id:'SatCmp',
	            	fieldLabel: '模型飞机' ,
	            	displayField: 'name',
	            	valueField: 'id',
	            	editable: false,
	            	emptyText: '请选择卫星...',
	            	store:Ext.create('Ext.data.JsonStore',{
					    proxy: {
					        type: 'ajax',
					        url: 'satparam.edq',
					        reader: {
					            root: 'nodes'
					        }
					    },
					    fields: ['name','id'],
						listeners:{
							'load':function(obj,rec){
					            //Ext.getCmp('SatCmp').setValue(rec.length > 0?rec[0].get('id'):'');
							}									
						}
					})
	            },{ 
	            	fieldLabel: '开始时间' 
	            },{ 
	            	fieldLabel: '结束时间' 
	       	}]
	    },{
            xtype:'fieldset',
            title: '帧方式查询',
            collapsible: true,
            collapsed: true,
            autoHeight:true,
            defaultType: 'textfield',
            defaults: {
				margin:'5 10 5 10',
	            anchor: '100%'
			},
	        fieldDefaults: {
		    	labelAlign: 'right',
		        labelWidth: 70
		    },
            items :[{
                    fieldLabel: 'Home',
                    name: 'home',
                    value: '(888) 555-1212'
                },{
                    fieldLabel: 'Business',
                    name: 'business'
                },{
                    fieldLabel: 'Mobile',
                    name: 'mobile'
                },{
                    fieldLabel: 'Fax',
                    name: 'fax'
                }
            ]
        }],
        buttons: [{
            text: '确定'
        },{
            text: '取消'
        }],
        listeners:{
        	'beforeclose':function(){
        		win.hide();
        		return false;
        	}
        }
	});
 * 
 */