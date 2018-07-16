var pageSize = 40;
Ext.onReady(function () {   
    var params = Ext.urlDecode(window.location.search.substr(1)),
    	cms=[{xtype: 'rownumberer'}],
    	fields=[],
    	caps=[],
    	tmParams=[];
    for(var i in params){
    	if (typeof params[i] == 'string') continue;
    	var tmParam=[],tmIds=[];
    	tmParam.push(params[i][0]);
    	tmParam.push(params[i][1]);
    	fields.push('T'+i);
		cms.push({ header: "时间", dataIndex: 'T'+i, width: 160});
		var cap=['时间'];
		var tms=params[i][2].split('&');
		for(var j=0;j<tms.length;j++){			
			var tm=tms[j].split('-');
			fields.push('V' +i+ tm[1]);
			cms.push({ header: tm[2]+'['+tm[0]+']', dataIndex: 'V' +i+ tm[1], width: 140 });
			cap.push(tm[2]+'['+tm[0]+']');
			tmIds.push(tm[1]);
		}
		tmParam.push(tmIds.join('-'));
		caps.push(cap.join('\t'));
		tmParams.push(tmParam.join('&'));
	}
    var ds = Ext.create('Ext.data.JsonStore',{
	    pageSize: pageSize,
	    proxy: {
	        type: 'ajax',
	        url: 'tmdata.edq',
	        extraParams: {
	        	type:0,//0-变化查询 1-步长查询 2-管道查询
	        	tmParam:tmParams.join(' ')
	        },
	        reader: {
	            root: 'items',
	            totalProperty: 'count'
	        }
	    },
	    fields: fields,
	    listeners: { load: function () {
	    	Ext.getCmp('DownloadBtn').setDisabled(this.getCount()==0);
	    }}
	});
    Ext.create('Ext.container.Viewport', {
		layout: 'border',
		items:[{
			region: 'north',
			height:26,
			border: false,
            xtype: 'radiogroup',
            anchor: 'none',
            layout: {
                autoFlex: false
            },
            defaults: {
                name: 'group'
            },
            items: [{
		        	inputValue: '0',
		        	boxLabel: '变化查询',
		        	margin: '0 15 0 0',
	                checked: true,
	                handler:function(){
	                	if(this.value){
	                		ds.proxy.extraParams.type=0;
	                		ds.load({ params: { start: 0, limit: pageSize}});
	                	}                	
	                }
		        },{
		        	id:'BCCX',
		        	inputValue: '1',
		        	boxLabel: '步长查询(单位：秒)',
		        	margin: '0 4 0 0',
	                handler:function(){
	                	if(this.value){
	                		ds.proxy.extraParams.type=1;
		                	ds.proxy.extraParams.timeSpan=Ext.getCmp('TimeSpan').getValue();
		                	ds.load({ params: { start: 0, limit: pageSize}});
	                	}                	
	                }
				},{
		        	xtype:'numberfield',
		        	id:'TimeSpan',
		        	width:45,
		        	value: 10,
	                minValue: 1,
	                maxValue: 120,
	                margin: '2 15 0 0',
	                listeners: {
                        specialkey: function (obj, e) {
                            if (Ext.getCmp('BCCX').getValue()&&e.getKey() == e.ENTER){
                            	ds.proxy.extraParams.type=1;
		                		ds.proxy.extraParams.timeSpan=obj.value;
		                		ds.load({ params: { start: 0, limit: pageSize}});
                            }                         
                        }
                    }
		        },{
		        	inputValue: '2',
		        	boxLabel: '管道查询',
	                handler:function(){
	                	if(this.value){
	                		ds.proxy.extraParams.type=2;
	                		ds.load({ params: { start: 0, limit: pageSize}});
	                		Ext.Msg.alert('提示','功能未明确！！！');
	                	}	                	
	                }
				},{
		        	id:'DownloadBtn',
		        	xtype:'button',
	                margin: '2 5 5 '+(document.body.clientWidth-430),
		        	text:'下载文本数据',
		        	handler:function(){
		        		var tps=[];
		        		for(var i=0;i<tmParams.length;i++){
							tps.push(tmParams[i]+'&'+caps[i]);
						}
						if (!window._df) {
							_df = document.createElement('iframe');
			            	document.body.appendChild(_df);	
			            	_df.style.visibility = "hidden";
						}
			            _df.src = "download?" + Ext.urlEncode({
			                type:ds.proxy.extraParams.type,
			                timeSpan:ds.proxy.extraParams.timeSpan,
			                tmParam: tps.join(' ')
			            });			            	
		        	}
		        }]
		},{
			region: 'center',
			layout:'fit',
			border: false,
			items:Ext.create('Ext.grid.Panel',{
		        sortableColumns:false,
		        columns: cms,
		        store: ds,
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
    ds.load({ params: { start: 0, limit: pageSize} });
});