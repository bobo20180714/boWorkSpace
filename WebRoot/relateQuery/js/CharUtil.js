var charUtil={
		RemoveLine:function(lineId,mid,satId){//移除航天器相关信息
			_relation_cmd.Add({Type: '移除航天器相关信息',Param:mid+"&&&"+lineId+"&&&"+satId});
		},
	    LineDetail:function(id){// 线上标记信息
	    	var line=_dataType_relation_lines.getLine(id);//TODO:需要修改
	    	window.open("jsp/LineDetail.jsp?spaceParma_code="+line.SpaceParmaCode+"&type_code="+line.Code+"&start_time="+line.Begin+"&end_time="+line.End);
	    },
	    showDetail:function(dataTypeId,dataTypeName,deviceName,dataTypeCode,deviceId){
	    	var data = [];
	    	data.push({'start':Ext.getCmp('relation_beginCmp').getValue()+":00.000",'end':Ext.getCmp('relation_endCmp').getValue()+":00.000"});
	    	var tabpanel = Ext.getCmp('center_tab');
	    	//孟祥超 修改 
	    	var count = document.getElementById("titl_detail_num_"+dataTypeCode).innerText;
	    	if(count != null){
	    		count = count.replace(/(^\s*)|(\s*$)/g,'');
	    	}
	    	var tabTitle = deviceName+"-"+dataTypeName+" 共"+(count==null?0:count)+"条记录";
	    	var _tab = tabpanel.down('panel[title='+tabTitle+']');
	    	if(_tab){
	    		tabpanel.setActiveTab(_tab);
	    		return;
	    	}
	    	data.sort(Arrarycompare("start"));
	    	var json=Ext.JSON.encode(data);
	    	var commonStore= Ext.create("query.store.OrbitRelatedFieldStore");
	    	commonStore.proxy.extraParams.jsjg_id=dataTypeId;
	    	commonStore.load();
	    	commonStore.on('load',function(){
	    		Ajax.send("relationSearch/findResultListSearch.edq",{
	    			deviceCode : deviceId,
	    			datatypeCode : dataTypeCode,
	    			times:json,
	    			pageNo:1,
	    			pageSize:20
	    		}, function (obj, opts) {
	    			var panel = {
	    				layout : 'fit',
	    				title : deviceName+"-"+dataTypeName+" 共"+count+"条记录",
	    				border:false,
	    				closable : true,
	    				autoRender : true,
	    				items:[{
	    					xtype:'ResultSearchView'
	    				}]
	    			};
	    			var tab = tabpanel.add(panel);
	    			tabpanel.setActiveTab(tab);
	    			var ResultSearchGrid=tab.down('gridpanel');
	    			ResultSearchGrid.down('label[itemId="start"]').setText(Ext.getCmp('relation_beginCmp').getValue()+":00");
					ResultSearchGrid.down('label[itemId="end"]').setText(Ext.getCmp('relation_endCmp').getValue()+":00");
	    			var header=[];
	    			var fields=[];
	    			var data =[];
	    	    	for(var k = 0;k<commonStore.getCount();k++){
	    				var fieldName = commonStore.getAt(k).data['field_name'];
	    				var fieldComment = commonStore.getAt(k).data['field_comment'];
	    				fields.push({name:fieldName});
	    				header.push({dataIndex:fieldName,text:fieldComment,align:'center'});
	    			}
	    	    	Ext.define('model', {
	    			     extend: 'Ext.data.Model',
	    			     fields: fields
	    			 });
	    	    	var _length = obj.records.dataContext.length;
	    	    	for(var i=0;i<_length;i++){
	    	    		
	    	    		var _obj = obj.records.dataContext[i];
	    				var model = Ext.create('model');
	    				for(var k = 0;k<fields.length;k++){
	    					model.set(fields[k].name,_obj[fields[k].name].valueString);
	    				}
	    				
	    				data.push(model);
	    	    	}
	    			var newStore=Ext.create('Ext.data.Store',{
	        			model:'model',
	        			data:data
	        		});
	    			// newStore.model.setFields(fields);
	    			// ResultSearchGrid.removeAll();
	    			// ResultSearchGrid.headerCt.removeAll();
	    			ResultSearchGrid.headerCt.add(header);
	    			ResultSearchGrid.bindStore(newStore);
	    		});
	    	});
	    }
};