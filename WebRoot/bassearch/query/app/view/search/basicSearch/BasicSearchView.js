
Ext.define('query.view.search.basicSearch.BasicSearchView', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.BasicSearchView',
	initComponent : function() {
		var pageSize=50,
		sat={
			getId:function(){
				if(this.rec==undefined)return -1;
				for(var p=this.rec;p.parentNode!=null&&p.parentNode.get('id')!='-1';p=p.parentNode);
				return p.get('id');
			},
			getName:function(){
				var names=[];
				for(var p=this.rec;!p.get('root');p=p.parentNode)names.unshift(p.get('text'));
				return names.join('_');
				//return this.rec.get('text');
			},
			getNodeId:function(){
				return this.rec==undefined?-1:this.rec.get('id');
			},
			getMid:function(){
				if(this.rec==undefined)return -1;
				for(var p=this.rec;p.parentNode!=null&&p.parentNode.get('id')!='-1';p=p.parentNode);
				return p.raw.mid;
			}
		},
		color = ["#ff0000", "#0000ff", "#008000", "#800080", "#a52a2a", "#6a5acd", "#008080", "#ffb6c1", "#ffa500", "#ffff00"];    
	    color.get = function (ds) {
	        for (var i = 0; i < color.length; i++) {
	            var isExist = false;
	            ds.each(function (rd) {
	                if (rd.data.Color.toLowerCase() == color[i]) isExist = true;
	            });
	            if (isExist) continue;
	            return color[i];
	        }
	    },
	    pipeTiyes={};
	    //用于文件下载
	    if (!window._df) {
	    	_df = document.createElement('iframe');    	
	    	_df.style.visibility = "hidden";
//	    	if (_df.attachEvent){
//	    		_df.attachEvent("onload", function(){
//	    			if(window._DOWNDIALOG)_DOWNDIALOG.close();
//	    	    });
//	    	} else {
//	    		_df.onload = function(){
//	    			if(window._DOWNDIALOG)_DOWNDIALOG.close();
//	    	    };
//	    	}
	    	document.body.appendChild(_df);	
	    }
		Ext.tip.QuickTipManager.init();
		var cw=pageInfo.width,ch=pageInfo.height;
		var treeStore=Ext.create('query.store.GrantUserGroupEquipmentTree');
		//参数代号输入框定义
	    var codeCmp = Ext.create('Ext.form.TextField', {
	    	labelWidth: 40,
	    	fieldLabel: '关键字',
	    	width: 150,
	    	emptyText:'搜索关键字',
	        listeners: {
			    'specialkey':function (obj, e) {
		            if (e.getKey() == e.ENTER){
	             		var sat_id="";
						for(var p=sat.rec;p.parentNode!=null;p=p.parentNode){
							if(p.raw.type==0) {
								sat_id = p.get('id');
								break;
							}
						}
	             		tmStore.proxy.extraParams.sat_id=sat_id;
		            	tmStore.proxy.extraParams.id=sat.getNodeId();//getId();
			            tmStore.proxy.extraParams.key=obj.value;
			     		tmStore.load({ params: { page:1, start: 0, limit: pageSize} });
			     		Ext.getCmp("basetmpagebar").moveFirst();
		            }
		        },
		        'change': function (obj, newValue, oldValue, eOpts) {
		        	var sat_id="";
					for(var p=sat.rec;p.parentNode!=null;p=p.parentNode){
						if(p.raw.type==0) {
							sat_id = p.get('id');
							break;
						}
					}
             		tmStore.proxy.extraParams.sat_id=sat_id;
	            	tmStore.proxy.extraParams.id=sat.getNodeId();//getId();
		            tmStore.proxy.extraParams.key=obj.value;
		     		tmStore.load({ params: { page:1, start: 0, limit: pageSize} });
		     		Ext.getCmp("basetmpagebar").moveFirst();
		        }
	        }
	    });
	    //航天器代号输入框定义
	    var satCodeCmp = Ext.create('Ext.form.TextField', {
	    	labelWidth: 40,
	    	fieldLabel: '关键字',
	    	width: 150,
	    	emptyText:'搜索关键字',
	        listeners: { 'change': function (obj, newValue, oldValue, eOpts) {
    			treeStore.load({ params: { key: newValue} });
    			tmStore.removeAll();
	        }}
	    });
	    var tmStore = Ext.create('Ext.data.JsonStore',{
		    pageSize: 50,
		    proxy: {
		        type: 'ajax',
		        url: _url+'tmparams/findgrantusergrouptmparamquerypage',
		        reader: {
		        	type:'json',
		            root: 'Rows',
		            totalProperty: 'Total'
		        }
		    },
		    fields: ['tm_param_id', 'tm_param_name', 'tm_param_code', 'tm_param_num','tm_param_type'],
		    listeners: { load: function () {	    
			    new Ext.dd.DropTarget(lineCmp.getView().el.dom, {
			        ddGroup: 'ADD',
			        notifyDrop: function (dd, e, data) {
			            tmCmp.fireEvent('itemdblclick', tmCmp, data.records[0]);
			            return true;
			        }
			    });
			    
			    new Ext.dd.DropTarget(Ext.getDom('Chart'), {
			        ddGroup: 'ADD',
			        notifyDrop: function (dd, e, data) {
			            tmCmp.fireEvent('itemdblclick', tmCmp, data.records[0]);
			            return true;
			        }
			    });
			    new Ext.dd.DropTarget(tmCmp.getView().el.dom, {
	                ddGroup: 'DEL',
	                notifyDrop: function (dd, e, data) {
	                    removeLine(lineCmp, data.records[0]);
	                    return true;
	                }
	            });
		    }}
		});

	    var tmCmp = Ext.create('Ext.grid.Panel',{
			border:false, 
			store: tmStore,
			frame:true,
			sortableColumns:false,
			viewConfig: {
				loadMask: true,
	            plugins: {
	                ddGroup:'ADD',
	                ptype: 'gridviewdragdrop',
	                enableDrop: false
	            }
	        },
	        columns: [{
	            xtype: 'rownumberer',
	            width:25
	        }, {
	            header: "名称",
	            dataIndex: 'tm_param_name',
	            width: 120
	        }, {
	            header: "代号",
	            dataIndex: 'tm_param_code'
	        }, {
	            header: "序号",
	            dataIndex: 'tm_param_num'
	        }],
	        bbar: {
	        	displayInfo:true,
	        	pageSize:50,
	            xtype: 'pagingtoolbar',
	            id:'basetmpagebar',
	            store: tmStore
	        },
	        tbar:[codeCmp,{
	        	width:22,
	        	iconCls: 'x-icon-search',
	        	id:'bs_param_search_btn',
	        	xtype: 'button',
	        	tooltip: '查询',
	        	style:{
	        		margin:'0'
	        	},
	        	handler: function () {
	        		var sat_id="";
					for(var p=sat.rec;p.parentNode!=null;p=p.parentNode){
						if(p.raw.type==0) {
							sat_id = p.get('id');
							break;
						}
					}
             		tmStore.proxy.extraParams.sat_id=sat_id;
	            	tmStore.proxy.extraParams.id=sat.getNodeId();//getId();
		            tmStore.proxy.extraParams.key=codeCmp.value;
		     		tmStore.load({ params: { page:1, start: 0, limit: pageSize} });
		     		Ext.getCmp("basetmpagebar").moveFirst();
	        	}
	        }],
	        listeners: { itemdblclick: function (obj, rec) {
	            if(window._ActiveTab&&_ActiveTab!='曲线查询')return;
	            if(beginCmp.value>endCmp.value){
	            	Ext.Msg.show({
	                    title: '提示信息',
	                    msg: '开始时间必须小于结束时间！',
	                    buttons: Ext.Msg.OK,
	                    icon: Ext.Msg.WARNING
	                });
	            	return;
	            }
	            if(rec.get('tm_param_type')==3){//提示字符型参数无法画图
	            	Ext.Msg.show({
	                    title: '提示信息',
	                    msg: '参数"'+rec.get('tm_param_name')+'"是字符串类型，不能图形查询!',
	                    buttons: Ext.Msg.OK,
	                    icon: Ext.Msg.WARNING
	                });
	                return;
	            }
	            if (lineStore.getCount() >= 10) {
	                Ext.Msg.show({
	                    title: '提示信息',
	                    msg: '查询曲线不能超过10条!',
	                    buttons: Ext.Msg.OK,
	                    icon: Ext.Msg.WARNING
	                });
	                return;
	            }
	            rec=new Record({TmCode:rec.raw.tm_param_code,SatId:sat.getId(),Id:myId.getVal(),Main:false,TmId:rec.raw.tm_param_id, Name: sat.getName(), Code: rec.raw.tm_param_name+'('+rec.raw.tm_param_code+')', Color: color.get(lineStore), Show: true,Width:1, Begin:beginCmp.getValue(),End:endCmp.getValue(), Type: 0,Precision:rec.get('tm_param_type')==0?3:0,Num:rec.raw.tm_param_num,DataType:rec.raw.tm_param_type,Mid:sat.getMid(),Begin1:_lines.Begin?beginCmp.Begin:beginCmp.getValue()+':00.000',End1:_lines.End?endCmp.End:endCmp.getValue()+':00.000'}); 
		    if(lineStore.getCount()==0)
	            	_record=rec;
	            if (_cmd.Add({ Type: '添加曲线', Param: rec })) {
	            	lineStore.add(rec);
	            	toolBar.enable();
	            }  
	        }}
	    });
	    Ext.define('Record', {
		    extend: 'Ext.data.Model',
		    fields: ['SatId','Id','Main','TmId','Name','Code','Color','Show','Width','Begin','End','Max','Min','Type','Precision','Num','DataType','Mid','Begin1','End1','TmCode']
		});
	    var lineStore = Ext.create('Ext.data.ArrayStore',{		    
	    	model: 'Record',
		    data:[],
		    listeners: { 
		    	add: function () {
			    	Ext.getCmp('AddTxtQueryBtn').setDisabled(false);
			    	queryCondtion.down('#AddQueryCond').setDisabled(false);
			    	lineCmp.down('#AsynLineBtn').setDisabled(false);
			    }, 
		    	remove: function () {
			    	if(this.getCount()==0){
			    		Ext.getCmp('AddTxtQueryBtn').setDisabled(true);
				    	queryCondtion.down('#AddQueryCond').setDisabled(true);
				    	lineCmp.down('#AsynLineBtn').setDisabled(true);
			    	}		    	
			    } 
		    }
		});    
	    Ext.create('Ext.picker.Color', {
		    renderTo: 'CP',
		    listeners: {
		        select: function(picker, selColor) {
		            _record.set('Color', '#' + selColor);
	        		_record.commit();
		            _cmd.Add({ Type: '设置曲线颜色', Id: _record.get('Id'), Color: '#' + selColor });
	        		_cp.setVisible(false);
		        }
		    }
		});
	    Ext.create('LineWidthPanel_search',function (w) {
	    	_record.set('Width', w);
	        _record.commit();
	        _cmd.Add({ Type: '设置曲线宽度', Id: _record.get('Id'), Width: w });
	        _sp.setVisible(false);
	    });
	    document.body.onclick = function (evt) {
	        var src = evt ? evt.target : event.srcElement;
	        var id = src.getAttribute('_id');
	        if (window._cp && id != 'COLOR')
	            _cp.setVisible(false);
	        if (window._sp && id != 'STYLE')
	            _sp.setVisible(false);
	    };
	    var lineCmp = Ext.create('Ext.grid.Panel',{
	    	id:'baseLineCmpGrid',
	    	border:false, 
			store: lineStore,
			viewConfig: {
				markDirty: false,
				plugins: {
	                ddGroup:'DEL',
	                ptype: 'gridviewdragdrop',
	                enableDrop: false
	            }
			},
			sortableColumns:false,	
	        plugins: [new Ext.grid.plugin.CellEditing({
	            clicksToEdit: 1,
	            listeners:{
		        	beforeedit:function(editor, e, eOpts){
		        		_record=e.record;
	        		}
		        }
	        })],
	       // forceFit:true,
	        //title:'曲线属性',
	        //title:'线型管理',	        
	        lbar:[/*{
	            text:'设置曲线属性',
	            handler:function(){
	            	Ext.Msg.alert('提示','功能待定...')
	            }
	        }, '-',*/'<center>', {
	            itemId:'AsynLineBtn',
	            text:'同步曲线数值坐标',
	            disabled:true,
	            handler:function(){
	            	_cmd.Add({ Type: '同步曲线'});
	            }
	        },{
	        	text:'上移曲线',
	        	handler:function(){
	        		if(lineStore.getCount()<2||!_lines.MainLine)return;
	        		for(var rowIndex=0,rec;rowIndex<lineStore.getCount();rowIndex++){
	        			rec=lineStore.getAt(rowIndex);
	        			if(rec.get('Main'))break;	        			
	        		}
	        		if(rowIndex==0)return;
	        		lineStore.removeAt(rowIndex);
	        		var newRec=new Record(rec.data);
	        		lineStore.insert(rowIndex-1,newRec);
	        		lineCmp.getView().refresh();
	        		_cmd.Add({Type:'上移曲线',Id:rec.data.Id,NewRec:newRec});
	        	}
	        },{
	        	text:'下移曲线',
	        	handler:function(){
	        		if(lineStore.getCount()<2||!_lines.MainLine)return;
	        		for(var rowIndex=0,rec;rowIndex<lineStore.getCount();rowIndex++){
	        			rec=lineStore.getAt(rowIndex);
	        			if(rec.get('Main'))break;	        			
	        		}
	        		if(rowIndex==lineStore.getCount()-1)return;
	        		lineStore.removeAt(rowIndex);
	        		var newRec=new Record(rec.data);
	        		lineStore.insert(rowIndex+1,newRec);
	        		lineCmp.getView().refresh();
	        		_cmd.Add({Type:'下移曲线',Id:rec.data.Id,NewRec:newRec});
	        	}
	        },'</center>'],
	        columns: [{
	            xtype: 'rownumberer',
	            width:30
	        },{
	        	header: "主轴",        	
	        	dataIndex: 'Main',
	        	width:50,
	        	menuDisabled: true,
	        	fixed: true,
	        	renderer: function (val) {
	        		return Ext.String.format('<div style="width:16px;height:16px;background:url(img/radio-{0}.gif) no-repeat center center;"/>',val?'checked':'unchecked');
		        },
		        listeners:{
		        	click:function(obj, td, rowIndex, cellIndex, e, record){
		        		var id=record.get('Id');
		        		if(pipeTiyes.hasOwnProperty(id)){
		        			var vals=pipeTiyes[id];
		        			Ext.getCmp('tiye1').setValue(vals[0]);
		        			Ext.getCmp('tiye2').setValue(vals[1]);
		        		}
		        		else{
		        			Ext.getCmp('tiye1').setValue('');
		        			Ext.getCmp('tiye2').setValue('');
		        		}
		        		_cmd.Add({ Type: '设置主轴', Id: record.get('Id') });
		        	}
		        }
	        },{
	        	header: "航天器名称",
	            dataIndex: 'Name',
	            width: 140
	        }, {
	            header: "参数名称",
	            dataIndex: 'Code',
	            width: 320
	        }, {
	            header: "图例",
	            dataIndex: 'Color',
	            width: 70,
	            //fixed: true,
	            renderer: function (val) {
		            return "<div _id='COLOR' style='width:70px;height:15px;background-color:" + val + ";'></div>";
		        },
		        listeners:{
		        	click:function(obj, td, rowIndex, cellIndex, e, record){
		        		_record=record;
		        		_cp = Ext.get('CP');
	                    var pos = Ext.get(e.getTarget()).getXY();
	                    _cp.setX(pos[0] - 5);
	                    _cp.setY(pos[1] - 95);
	                    _cp.setVisible(true);
		        	},
		        	mouseover:function(){
		        		arguments[4].getTarget().style.cursor='pointer';
		        	},
		        	mouseout:function(){
		        		arguments[4].getTarget().style.cursor='';
		        	}
		        }
	        }, {
	            xtype: 'checkcolumn',
	            header: "显示",
	            dataIndex: 'Show',
	            width: 40,
	            align: 'left',
		        //fixed: true,
		        listeners:{
		        	checkchange:function(obj, rowIndex, checked, eOpts ){	        		
		        		_cmd.Add({ Type: '显示曲线', Id: lineStore.getAt(rowIndex).get('Id'), Show: checked});
		        	}
		        }
	        }, {
	            header: "线宽",
	            dataIndex: 'Width',
	            width: 90,
		        //fixed: true,
		        renderer: function (val) {
		            return "<div _id='STYLE' style='width:88px;height:15px;background-image:url(img/xk" + val + ".ico);'></div>";
		        },
		        listeners:{
		        	click:function(obj, td, rowIndex, cellIndex, e, record){
		        		_record=record;
		        		_sp = Ext.get('SP');
	                    var pos = Ext.get(e.getTarget()).getXY();
	                    _sp.setX(pos[0] - 5);
	                    _sp.setY(pos[1] - 103);
	                    _sp.setVisible(true);
		        	},
		        	mouseover:function(){
		        		arguments[4].getTarget().style.cursor='pointer';
		        	},
		        	mouseout:function(){
		        		arguments[4].getTarget().style.cursor='';
		        	}
		        }
	        }, {           
	            header: "开始时间",
	            dataIndex: 'Begin',
	            width: 138,
	            //fixed: true,
	            editor: {                
	                vtype: 'time',
	                allowBlank: false,
				    listeners: { 
					    focus: function () {            
				            this.oldVal=this.value;
				        },
				        blur:function(){
				        	setTimeRange(this,this.value,_record.get('End'),_record.get('Id'));   
				        }, 
				        specialkey: function (obj, e) {
				            if (e.getKey() == e.ENTER)
				                obj.fireEvent('blur', obj);
				        }
			        }
	            }
	        }, {            
	            header: "结束时间",
	            dataIndex: 'End',
	            width: 138,
	            //fixed: true,
	            editor: {                
	                vtype: 'time',
	                allowBlank: false,
				    listeners: { 
					    focus: function () {            
				            this.oldVal=this.value;
				        },
				        blur:function(){
				        	setTimeRange(this,_record.get('Begin'),this.value,_record.get('Id'));   
				        }, 
				        specialkey: function (obj, e) {
				            if (e.getKey() == e.ENTER)
				                obj.fireEvent('blur', obj);
				        }
			        }
	            }
	        }, {
	            header: "坐标轴上限",
	            dataIndex: 'Max',
	            width: 100,
	            //fixed: true,
	            editor: {                
	                vtype: 'val',
	                allowBlank: false,
				    listeners: { 
					    focus: function () {            
				            this.oldVal=this.value;
				        },
				        blur:function(){
				        	setValRange(this,this.value*1,_record.get('Min')*1,_record.get('Id'));   
				        }, 
				        specialkey: function (obj, e) {
				            if (e.getKey() == e.ENTER)
				                obj.fireEvent('blur', obj);
				        }
			        }
	            }
	        }, {
	            header: "坐标轴下限",
	            dataIndex: 'Min',
	            width: 100,
	           // fixed: true,
	            editor: {                
	                vtype: 'val',
	                allowBlank: false,
				    listeners: { 
					    focus: function () {            
				            this.oldVal=this.value;
				        },
				        blur:function(){
				        	setValRange(this,_record.get('Max')*1,this.value*1,_record.get('Id'));   
				        }, 
				        specialkey: function (obj, e) {
				            if (e.getKey() == e.ENTER)
				                obj.fireEvent('blur', obj);
				        }
			        }
	            }
	        }, {
	            header: "线型",
	            dataIndex: 'Type',
	            width: 100,
	            renderer: function (val) {
		            var ret;
		            switch(val){
		            	case 0:
		            		ret='阶梯型';
		            		break;
	            		case 1:
		            		ret='直线型';
		            		break;
	            		case 2:
		            		ret='点型';
		            		break;
		            }
		            return ret;
		        },
	            editor: new Ext.form.field.ComboBox({
	                triggerAction: 'all',
	                editable: false,
	                store: [[0,'阶梯型'],/*[1,'直线型'],*/[2,'点型']],
	                lazyRender: true,
		            listeners: { 
					    select: function (obj) {  
					    	_record.set('Type',obj.value);
				            _record.commit();
				            _cmd.Add({Type:'选择曲线显示方式',Id:_record.get('Id')});
				        }
			        }
	            })
	        }, {
	            header: "小数位数",
	            dataIndex: 'Precision',
	            width: 100,
	            editor: new Ext.form.field.ComboBox({
	                triggerAction: 'all',
	                editable: false,
	                store: [0,1,2,3,4,5,6,7,8,9],
	                lazyRender: true,
	                listeners: { 
					    select: function (obj) {  
					    	_record.set('Precision',_record.get('Type')==0?obj.value:0);
				            _record.commit();
			            }
	                }
	            })
	        }/*,{
	        	xtype: 'actioncolumn',
	            width: 30,
	            menuDisabled: true,
	            //fixed: true,
	            items: [{
	            	icon: 'img/down.ico',
	            	tooltip:'下载原始数据',
	                handler: function(grid, rowIndex){
	                	var rst=_lines.GetQueryInfo(lineStore.getAt(rowIndex).get('Id'));
	                	_df.src=_url+"tmdata/download?" + Ext.urlEncode({
			            	tmParamStr:rst,
			            	mode:0,
			            	opt:1,
			            	timeSpan:0,
			            	v1:'',
	            			v2:''
			            });	
	                }
	            }]
	        }*/, {
		        xtype: 'actioncolumn',
	            width: 40,
	            menuDisabled: true,
	            fixed: true,
	            items: [{
	                icon: 'img/delete.gif',
	            	tooltip:'删除曲线',
	                handler: function(grid, rowIndex){
	                	removeLine(grid, lineStore.getAt(rowIndex));
	                }
	            }]
	        }]
	    });
	    function removeLine(grid, rec){ 	
	    	_cmd.Add({Type: '移除曲线', Id: rec.get('Id')});
	        lineStore.remove(rec);
	        for(var i=0;i<lineStore.getCount();i++)lineStore.getAt(i).commit();
	        if (lineStore.getCount() == 0) {
	        	//同步曲线数值坐标 置为 false 孟祥超 修改
	        	_lines.Asyn = false;
	        	toolBar.enable(false);
	        	Ext.getCmp('tiye1').setValue('');
    			Ext.getCmp('tiye2').setValue('');
	        }
	    }
	    Ext.apply(Ext.form.field.VTypes, {
		    time: function(val) {
		        //匹配时间：yyyy-MM-dd HH:mm
		        return /^((\d{2}(([02468][048])|([13579][26]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|([1-2][0-9])))))|(\d{2}(([02468][1235679])|([13579][01345789]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\s(((0?[0-9])|(2[0-3])|(1[0-9]))\:([0-5]?[0-9])))?$/i.test(val);
		    },
		    timeMask:  /[\d\:\s\-]/i
		});
	    Ext.apply(Ext.form.field.VTypes, {
		    val: function(val) {
		        //匹配数字
		        return /^[-\+]?\d+(\.\d+)?([\e\E][-\+]\d+)?$/i.test(val);
		    },
		    valMask:  /[\d\.\-]/i
		});
	    function setTimeRange(obj,t1,t2,id){
	    	if (obj&&!obj.validate()) {
	            obj.setValue(obj.oldVal);
	            return;
	        }
	        t1 = Date.parse(t1);
	        t2 = Date.parse(t2);
	    	if (t1 >= t2) {
	            Ext.Msg.show({
	                title: '提示信息',
	                msg: '开始时间必须小于结束时间!',
	                buttons: Ext.Msg.OK,
	                icon: Ext.Msg.WARNING
	            });            
	            obj.setValue(obj.oldVal);
	            return;
	        }
	        if(lineStore.getCount()>0&&(obj==null||obj.value!=obj.oldVal)){
	        	_cmd.Add({ Type: '压缩曲线', Begin:t1.getTime(), End:t2.getTime(), Id: id});
	        	if(window._record)_record.commit();
	        	//timeCmp.setValue('自定义');
	    	}       
	    }
	    function setValRange(obj,v1,v2,id){
	    	if (!obj.validate()) {
	            obj.setValue(obj.oldVal);
	            return;
	        }
	        if (v1 <= v2) {
	            Ext.Msg.show({
	                title: '提示信息',
	                msg: '最大值必须大于最小值!',
	                buttons: Ext.Msg.OK,
	                icon: Ext.Msg.WARNING
	            });            
	            obj.setValue(obj.oldVal);
	            return;
	        }
	        if(obj.value!=obj.oldVal){
	        	_cmd.Add({ Type: '设置曲线上下值', Id: id, Max: v1, Min: v2 });
	        }
	    }
	    beginCmp = Ext.create('Ext.form.field.Text', {
		    width:110,
		    value: Now.getBegin(),
		    allowBlank: false,
		    vtype: 'time',
		    listeners: { focus: function (obj) {            
	            this.oldVal=obj.value;
	        },blur:function(obj){
	        	if (!obj.validate()) {
	                obj.setValue(obj.oldVal);
	            }
	        }, specialkey: function (obj, e) {
	            if (e.getKey() == e.ENTER){
	            	setTimeRange(obj,obj.value,endCmp.getValue()); 
	            }                
	        },change:function(){
	        	if(!window._isTimeChange){
	        		_isTimeChange=true;
	        		return;
	        	}
	        	if(timeCmp.getValue()!='自定义'){
	        		beginCmp.setDisabled(false);
		        	endCmp.setDisabled(false);
		        	timeCmp.setValue('自定义');
	        	}	        	
	        }}
		});
	    endCmp = Ext.create('Ext.form.field.Text', {
		    width:110,
		    value: Now.getEnd(),
		    allowBlank: false,
		    vtype: 'time',
		    listeners: {focus: function (obj) {            
	            this.oldVal=obj.value;
	        }, blur: function (obj) {            
	        	if (!obj.validate()) {
	                obj.setValue(obj.oldVal);
	            }
	        }, specialkey: function (obj, e) {
	            if (e.getKey() == e.ENTER){
	            	setTimeRange(obj,beginCmp.getValue(),obj.value);
	            }                
	        }}
		});
		//最近时间段输入框定义
	    var timeCmp = Ext.create('Ext.form.ComboBox', {
	        id:'XTB_TIMECMP',
	    	width: 100,       
	        store: [[0,'自定义'],[1, '最近1分钟'], [5, '最近5分钟'], [10, '最近10分钟'], [15, '最近15分钟'], [30, '最近30分钟'], [60, '最近1小时'], [120, '最近2小时'], [240, '最近4小时'], [480, '最近8小时'], [720, '最近12小时'], [1440, '最近1天'], [2880, '最近2天'], [10080, '最近1周'], [20160, '最近2周'], [43200, '最近1月'], [129600, '最近3月'], [262080, '最近6月'], [525600, '最近1年']],
	        triggerAction: 'all',
	        editable: false,
	        lazyRender: true,
	        value:'自定义',
	        listeners: { select: function () {
	            if(this.value>0){
	            	beginCmp.setDisabled(true);
	            	endCmp.setDisabled(true);
	            	_isTimeChange=false;
	            	refreshFn(this.value);	            	
	            }
	            else{
	            	beginCmp.setDisabled(false);
	            	endCmp.setDisabled(false);
	            }
	        }}
	    }); 
	    var refreshFn=function(v){
	    	var now=new Date(),bt1,bt2;//_WebTime.getMs(),
	    	if(v[0]=='#'){
	    	}
	    	else{
	    		now=now.getTime();
	    		bt1=Ext.Date.format(new Date(now-v * 60000),'Y-m-d H:i'),
				bt2=Ext.Date.format(new Date(now*1),'Y-m-d H:i');
	    	}	
			beginCmp.setValue(bt1);
			endCmp.setValue(bt2);
			if(lineStore.getCount()>0)_cmd.Add({ Type: '压缩曲线', Begin:bt1.getTime(), End:bt2.getTime()});
	    };
	    var toolBar = Ext.create('Ext.toolbar.Toolbar', {
		    id:'XTB',
	    	margin: '-1 -1 0 -1',
		    enableOverflow: true,
		    height:27,
		    items:[timeCmp,beginCmp,'到',endCmp,{
		        	width:22,
			        icon: 'img/page-refresh.gif',
			        xtype: 'mybutton',
			        tooltip: '刷新',
			        handler: function () {
			        	var sel=timeCmp.getValue();
			        	if(sel=='自定义'||sel==0){
			        		setTimeRange(null,beginCmp.getValue(),endCmp.getValue());
			        	}
			        	else{
			        		_isTimeChange=false;
			        		refreshFn(sel);
			        	}
			        }
		        }, '-', {
		        	id:'AddTxtQueryBtn',
		        	width:85,
		        	text:'数据列表',
		        	icon: 'img/list.ico',
			        xtype: 'mybutton',
			        disabled:true,
			        menu: [{
		                text: '绘图区查询',
		                icon: 'img/tongji1.ico',
		                handler: function () { 
				        	txtQuery(0);
		                }
		            },{
		                text: '游标间查询',
		                icon: 'img/tongji2.ico',
		                handler: function () {
				        	txtQuery(1);
		                }
		            }]
		        },'-',/*{
		        id: 'TL',
		        icon: 'img/ljs.ico',
		        tooltip: '调速',
		        xtype: 'mybutton',
		        handler: function () {
		            _cmd.Add({ Type: '曲线播放', Param: -2 });
		        }
		    }, {
		        id: 'TLP',
		        icon: 'img/lbf.ico',
		        xtype: 'mybutton',
		        tooltip: '向左播放',
		        handler: function () { _cmd.Add({ Type: '曲线播放', Param: -1 }); }
		    }, {
		        id: 'TS',
		        icon: 'img/sbf.ico',
		        xtype: 'mybutton',
		        tooltip: '停止播放',
		        handler: function () { _cmd.Add({ Type: '曲线播放', Param: 0 }); }
		    }, {
		        id: 'TRP',
		        icon: 'img/rbf.ico',
		        xtype: 'mybutton',
		        tooltip: '向右播放',
		        handler: function () { _cmd.Add({ Type: '曲线播放', Param: 1 }); }
		    }, {
		        id: 'TR',
		        icon: 'img/rjs.ico',
		        xtype: 'mybutton',
		        tooltip: '调速',
		        handler: function () { _cmd.Add({ Type: '曲线播放', Param: 2 }) }
		    }, '-',*/ {
		        id: 'TCX',
		        icon: 'img/cx.ico',
		        xtype: 'mybutton',
		        tooltip: '撤销',
		        handler: function () { _cmd.Add({ Type: '撤销曲线' }); }
		    }, {
		        id: 'THF',
		        icon: 'img/hf.ico',
		        xtype: 'mybutton',
		        tooltip: '恢复',
		        handler: function () { _cmd.Add({ Type: '恢复曲线' }); }
		    },'-', {	        
		        id:'CGXS',
		        icon: 'img/cg.ico',
		        xtype: 'mybutton',
		        tooltip: '堆叠显示',
		        handler: function () {
		            if (this.icon.indexOf('cg')>-1) {
		                this.setIcon('img/pp.ico');
		                this.setTooltip('分栏显示');
		                _cmd.Add({ Type: '平铺显示' ,IsTile:true});
		            }
		            else {
		                this.setIcon('img/cg.ico');
		                this.setTooltip('堆叠显示');
		                _cmd.Add({ Type: '平铺显示' ,IsTile:false});
		            }	            
		        }
		    },'-',{
		        id: 'TL',
		        icon: 'img/ljs.ico',
		        tooltip: '调速',
		        xtype: 'mybutton',
		        handler: function () {
		            _cmd.Add({ Type: '曲线播放', Param: -2 });
		        }
		    }, {
		        id: 'TLP',
		        icon: 'img/lbf.ico',
		        xtype: 'mybutton',
		        tooltip: '向左播放',
		        handler: function () { _cmd.Add({ Type: '曲线播放', Param: -1 }); }
		    }, {
		        id: 'TS',
		        icon: 'img/sbf.ico',
		        xtype: 'mybutton',
		        tooltip: '停止播放',
		        handler: function () { _cmd.Add({ Type: '曲线播放', Param: 0 }); }
		    }, {
		        id: 'TRP',
		        icon: 'img/rbf.ico',
		        xtype: 'mybutton',
		        tooltip: '向右播放',
		        handler: function () { _cmd.Add({ Type: '曲线播放', Param: 1 }); }
		    }, {
		        id: 'TR',
		        icon: 'img/rjs.ico',
		        xtype: 'mybutton',
		        tooltip: '调速',
		        handler: function () { _cmd.Add({ Type: '曲线播放', Param: 2 }) }
		    }/*,{
		    	icon: 'img/discnn.ico',
		        xtype: 'mybutton',
		        tooltip: '历史模式',
		        handler: function (obj) {
		            if (this.icon.indexOf('discnn')>-1) {
		            	if(_lines.IsLongQuery()){
		            		Ext.Msg.confirm('提示信息','您有长时间历史模式查询,是否更改<br>为12小时实时模式开始查询?',function(btn){
			            		if(btn=='yes'){	    
			            			obj.setIcon('img/cnn.ico');
			    	                obj.setTooltip('实时模式');
			    	                _cmd.Add({Type: '实时显示曲线'});
			            		}
			        		});  
		            	}
		            	else{
		            		obj.setIcon('img/cnn.ico');
	    	                obj.setTooltip('实时模式');
	    	                _cmd.Add({Type: '实时显示曲线'});
		            	}
		            }
		            else {
		                this.setIcon('img/discnn.ico');
		                this.setTooltip('历史模式');
		                _cmd.Add({Type: '实时显示曲线'});
		            }	            
		        }
		    }*/,'-', {
		        width:22,
		        icon: 'img/zsy.ico',
		        xtype: 'mybutton',
		        tooltip: '缩放当前曲线',
		        handler: function () { _cmd.Add({ Type: '缩放当前曲线' }); }
		    }, {
		        width:22,
		        icon: 'img/sy.ico',
		        xtype: 'mybutton',
		        tooltip: '上移当前曲线',
		        handler: function () { _cmd.Add({ Type: '上移当前曲线' }); }
		    }, {
		        width:22,
		        icon: 'img/xy.ico',
		        xtype: 'mybutton',
		        tooltip: '下移当前曲线',
		        handler: function () { _cmd.Add({ Type: '下移当前曲线' }); }
		    }, {
		        width:22,
		        icon: 'img/fd.ico',
		        xtype: 'mybutton',
		        tooltip: '放大当前曲线',
		        handler: function () { _cmd.Add({ Type: '放大当前曲线' }); }
		    }, {
		        icon: 'img/sx.ico',
		        xtype: 'mybutton',
		        tooltip: '缩小当前曲线',
		        handler: function () { _cmd.Add({ Type: '缩小当前曲线' }); }
		    }, '-', {
		        width:22,
		        icon: 'img/zsy0.ico',
		        xtype: 'mybutton',
		        tooltip: '缩放所有曲线',
		        handler: function () { _cmd.Add({ Type: '缩放所有曲线' }); }
		    }, {
		        icon: 'img/sy0.ico',
		        xtype: 'mybutton',
		        tooltip: '上移所有曲线',
		        handler: function () { _cmd.Add({ Type: '上移所有曲线' }); }
		    }, {
		        width:22,
		        icon: 'img/xy0.ico',
		        xtype: 'mybutton',
		        tooltip: '下移所有曲线',
		        handler: function () { _cmd.Add({ Type: '下移所有曲线' }); }
		    }, {
		        width:22,
		        icon: 'img/fd0.ico',
		        xtype: 'mybutton',
		        tooltip: '放大所有曲线',
		        handler: function () { _cmd.Add({ Type: '放大所有曲线' }); }
		    }, {
		        width:22,
		        icon: 'img/sx0.ico',
		        xtype: 'mybutton',
		        tooltip: '缩小所有曲线',
		        handler: function () { _cmd.Add({ Type: '缩小所有曲线' }); }
		    }, '-'/*, {
		    	id:'XSYSSJ',
		        width:22,
		        icon: 'img/pointline.ico',
		        xtype: 'mybutton',
		        tooltip: '显示原始数据点',
		        setEnable:function(b){
		        	this.setIcon(Ext.String.format('img/{0}pointline.ico',b?'':'b'));
		            this.setDisabled(!b);
		        },
		        handler: function () { _cmd.Add({ Type: '显示原始数据' })}
		    }*/, {
		    	id:'SJTY',
		        width:22,//34,
		        icon: 'img/tiye.ico',
		        xtype: 'mybutton',
		        tooltip: '采样数据剔野',
		        handler: function () {
		        	this.toggle();
		        	//xjt-15.5.18
		        	var //tiyeCmp1=Ext.getCmp('tiye1'),
		        		//tiyeCmp2=Ext.getCmp('tiye2'),
		        		//tiyeBtnCmp=Ext.getCmp('TiyeBtn'),
		        		tiyeChexiaoCmp=Ext.getCmp('TiyeChexiao');
		        	if(this.pressed){
		        		_chart.hidePoint();
		        		//tiyeCmp1.show();
		        		//tiyeCmp2.show();
		        		//tiyeBtnCmp.show();
		        		tiyeChexiaoCmp.show();
		        	}
		        	else{
		        		//tiyeCmp1.hide();
		        		//tiyeCmp2.hide();
		        		//tiyeBtnCmp.hide();
		        		tiyeChexiaoCmp.hide();
		        	}
		        	_drawarea.ShowRuler(!this.pressed);
		        }/*,
		        menu: [{
		        	xtype:'textfield',
		        	fieldLabel:'剔野上限',
	                width:140,
	                labelWidth: 55,
	                handler: function () { 
	                	
	                }
	            },{
	            	xtype:'textfield',
		        	fieldLabel:'剔野下限',
	                width:140,
	                labelWidth: 55,
	                handler: function () { 
	                	
	                }
	            }]*/
		    }, {
		        id: 'TiyeChexiao',
		        icon: 'img/cx.ico',
		        xtype: 'mybutton',
		        tooltip: '撤销最近一次剔野',
		        hidden:true,
		        width:22,
            	style:{
            		borderLeft:'1px solid #99BBE8',
            		borderTop:'1px solid #99BBE8',
            		borderBottom:'1px solid #99BBE8',
            		marginRight:0
            	},
		        handler: function () { 
		        	_cmd.Add({ Type: '撤销剔野'});
		        }
		    },{
            	xtype:'textfield',
            	id:'tiye1',
            	fieldLabel:'下限',
            	labelWidth:30,
            	hidden:true,
            	vtype: 'val',
            	width:90,
            	style:{
            		borderTop:'1px solid #99BBE8',
            		borderBottom:'1px solid #99BBE8',
            		marginLeft:0
            	}
            },{
            	xtype:'textfield',
            	id:'tiye2',
            	fieldLabel:'上限',
            	hidden:true,
            	labelWidth:30,
            	vtype: 'val',
            	width:90,
            	style:{
            		borderTop:'1px solid #99BBE8',
            		borderBottom:'1px solid #99BBE8',
            		marginRight:0
            	}
            },{
            	xtype:'mybutton',
            	id:'TiyeBtn',
            	icon:'img/add.ico',
            	width:22,
            	hidden:true,
            	style:{
            		borderTop:'1px solid #99BBE8',
            		borderRight:'1px solid #99BBE8',
            		borderBottom:'1px solid #99BBE8',
            		marginLeft:0
            	},
            	handler:function(){
            		_cmd.Add({ Type: '曲线剔野'});
            	}
            },'-',{
		    	width:22,
		        icon: 'img/tag.ico',
		        xtype: 'mybutton',
		        tooltip: '添加标注',
		        handler: function () {_cmd.Add({ Type: '添加标注'}); }
		    },{
		        width:34,
		        icon: 'img/tongji.ico',
		        xtype: 'mybutton',
		        tooltip: '统计分析',
		        menu: [{
	                text: '统计分析',
	                ty_: 0,
	                icon: 'img/tongji1.ico',
	                handler: function () { _cmd.Add({ Type: '统计分析', Method:this.ty_,Tiye:pipeTiyes});}
	            },{
	                text: '按照游标区间统计',
	                ty_: 1,
	                icon: 'img/tongji2.ico',
	                handler: function () { _cmd.Add({ Type: '统计分析', Method:this.ty_,Tiye:pipeTiyes});}
	            }]
		    },'-',{
		        width:22,
		        icon: 'img/dy.ico',
		        xtype: 'mybutton',
		        tooltip: '打印',
		        handler: function () { _cmd.Add({ Type: '打印曲线' }); }
		    }],
		    listeners:{
		    	overflowchange:function( o, count ,objs){
		    		for(var i=0;i<count;i++){
		    			if(objs[i].setText)objs[i].setText('&nbsp;');
		    		}	    		
		    	}
		    }
	    });
	    toolBar.enable=function(b){
	    	if(b==undefined)b=true;
	    	var items=this.items.items;
	    	if(b){
	    		var disables = ['bcx', 'bhf', 'bsbf', 'bljs', 'brjs','bpointline'];
			    for (var i = 0; i < disables.length; i++) disables[i] = 'img/' + disables[i] + '.ico';
			    disables=disables.join('');		    
			    for (i = 0; i < items.length; i++) {
			        if (!items[i].icon||disables.indexOf(items[i].icon) > -1) continue;
			        items[i].setEnable(true);
			    }  
	    	}else{    		
	    		for (var i = 0; i < items.length; i++){
	    			if(!items[i].icon||items[i].icon.indexOf('pp') > -1||items[i].icon.indexOf('cg') > -1||items[i].icon.indexOf('refresh') > -1||items[i].icon=='img/add.ico') continue;
	    			if(items[i].icon.indexOf('cnn') > -1){
	    				items[i].setIcon('img/discnn.ico');
	    				items[i].setDisabled(true);
	    				continue;
	    			}
	    			items[i].setEnable(false);
	    		}
	    	}    	  
	    };
	    toolBar.enable(false); 
	    function txtQuery(type) {			            
	    	var rsts=_lines.getRsts(type,pipeTiyes);
	        if(rsts.length==0)return;
	        
		    var cms=[{xtype: 'rownumberer',width:25}],
		    	fields=[],
		    	caps=[],
		    	tmParams=[],
		    	precisions=[];
	    	for(var i=0;i<rsts.length;i++){
		    	var tmParam=[],tmIds=[],tiyes=[];
		    	tmParam.push(new Date(rsts[i].Begin).format('YmdHisu'));
		    	tmParam.push(new Date(rsts[i].End).format('YmdHisu'));
		    	fields.push('T'+i);
				cms.push({ header: "时间", dataIndex: 'T'+i, width: 160});
				var cap=['时间'];
				for(var j=0;j<rsts[i].Tms.length;j++){
					var tm=rsts[i].Tms[j];
					precisions[cms.length]=tm.Precision;
					fields.push('V' +i+ tm.TmId);
					cms.push({ header: tm.Name+"_"+tm.TmCode, dataIndex: 'V' +i+ tm.TmId, width: 140 ,renderer:function(v, c, rec, rowIndex, colIndex, store){return v!=undefined?new Number(v).toFixed(precisions[colIndex]):'';}});
					cap.push(tm.Code+'['+tm.Id+']');
					tmIds.push([tm.Mid,tm.Num,tm.TmId,tm.Type].join('&'));
					tiyes.push(tm.Tiye);
				}
				tmParam.push(tmIds.join('~'));
				tmParam.push(tiyes.join('#'));
				caps.push(cap.join('\t'));
				tmParams.push(tmParam.join('|'));
			}
	    	var	txtPageSize=50,
	    	ds = Ext.create('Ext.data.JsonStore',{
			    pageSize: txtPageSize,
			    proxy: {
			        type: 'ajax',
			        url: _url+'tmdata/getTmdata',
			        extraParams: {
			        	type:0,//0-变化查询 1-步长查询 2-管道查询
			        	timeSpan:10,
			        	tmParamStr:tmParams.join(' '),
			        	limit: txtPageSize,
			        	v1:null,
			        	v2:null
			        },
			        reader: {
			            root: 'items',
			            totalProperty: 'count'
			        },
			        timeout:600000//超时10分钟
			    },
			    fields: fields,
			    listeners: { load: function (store,records,opts) {
			    	tab.down('#TxtDownBtn').setDisabled(this.getCount()==0);
			    	//if(records.length>=txtPageSize)
			    	tab.down('#PageNum').setText(stack.length+1);
			    }}
			}),
			stack=[];	
	    	var tabs=Ext.getCmp('tabs'),
        	tab=tabs.add({
	    		title:'数据列表',
	    		closable:true,
	    		height:ch-209,
	    		layout:'fit',
				items:Ext.create('Ext.grid.Panel',{
					margin:-1,
			        sortableColumns:false,
			        columns: cms,
			        store: ds,
			        tbar:[{
			        	xtype:'combobox',
			        	itemId:'QueryMode',
			        	val:0,
			        	fieldLabel:'查询方式',
			        	labelAlign:'right',
			        	labelWidth:60,
					    width:140,
					    value:'原样查询',
					    triggerAction: 'all',
					    editable:false,
					    store: [[0,'原样查询'],[1,'变化查询'],[2,'步长查询']/*,[3,'管道查询']*/],
					    lazyRender: true,
			            listeners: { 
						    select: function () {            					            
					            var ts=tab.down('#TimeSpan'),tob=tab.down('#TxtOkBtn'),limit1=tab.down('#limit1'),limit2=tab.down('#limit2');
					            if(stack.length>0){
					            	tmParams=stack[0].split(' ');
					            	ds.proxy.extraParams.tmParamStr=stack[0];
					            	stack=[];
					            }
					            ds.proxy.extraParams.type=this.val=this.value;
					            switch(this.value){
						            case 0://原样查询
						            case 1://变化查询
					            		ts.hide();
					            		ds.load({ params: { 
					            			start: 0, 
					            			limit: txtPageSize,
					            			v1:limit1.getValue(),
					            			v2:limit2.getValue()
					            		}});
						            	return;
					            	case 2://步长查询
					            		ts.show();
						            	//tob.show();
						            	//limit1.hide();
					            		//limit2.hide();
						            	//ds.proxy.extraParams.timeSpan=ts.getValue();
					            		ds.load({ params: { 
					            			start: 0, 
					            			limit: txtPageSize,
					            			timeSpan:ts.getValue() ,
					            			limit1:limit1.getValue(),
					            			limit2:limit2.getValue()
					            		}});
						            	return;
					            	case 3://管道查询
					            		limit1.show();
					            		limit2.show();
					            		ts.hide();
					            		tob.show();
					            		ds.load({ params: { 
					            			start: 0, 
					            			limit: txtPageSize,
					            			v1:limit1.getValue(),
					            			v2:limit2.getValue()
					            		}});
						            	return;
					            	default:
					            		ts.hide();
					            		tob.hide();
					            		limit1.hide();
					            		limit2.hide();
					            }
					        }
				        }
			        },{
			        	xtype:'numberfield',
			        	itemId:'TimeSpan',
			        	fieldLabel:'步长(秒)',
			        	labelWidth:50,
			        	width:135,
			        	value: 10,
		                minValue: 1,
		                //maxValue: 120,
		                hidden:true,
		                listeners: {
	                        specialkey: function (obj, e) {
	                            if (e.getKey() == e.ENTER){
	                            	tab.down('#TxtOkBtn').fireEvent('click');
	                            }                         
	                        }
	                    }
		            },{
		            	xtype:'textfield',
		            	itemId:'limit1',
		            	fieldLabel:'下限',
		            	labelWidth:30,
		            	hidden:true,
		            	vtype: 'val',
		            	width:90,
		            	value:Ext.getCmp('tiye1').getValue()
		            },{
		            	xtype:'textfield',
		            	itemId:'limit2',
		            	fieldLabel:'上限',
		            	hidden:true,
		            	labelWidth:30,
		            	vtype: 'val',
		            	width:90,
		            	value:Ext.getCmp('tiye2').getValue()
		            },{
		            	xtype:'button',
		            	itemId:'TxtOkBtn',
		            	text:'确定',
		            	width:40,
//		            	hidden:true,
		            	style:{
    						border: '1px solid #99BBE8'
		            	},
			        	listeners: {
        					click: function(){
        						if(tab.down('#limit1').getValue()>tab.down('#limit2').getValue()){
        							Ext.Msg.show({
            			                title: '提示信息',
            			                msg: '下限必须小于上限!',
            			                buttons: Ext.Msg.OK,
            			                icon: Ext.Msg.WARNING
            			            }); 
        							return;
        						}
        						if(stack.length>0){
        							tmParams=stack[0].split(' ');
        							ds.proxy.extraParams.tmParamStr=stack[0];
        							stack=[];
        						}
				        		if(ds.proxy.extraParams.type==2){//步长查询
				        			ds.load({ params: { 
				            			start: 0, 
				            			limit: txtPageSize,
				            			timeSpan:tab.down('#TimeSpan').getValue(),
				            			v1:tab.down('#limit1').getValue(),
				            			v2:tab.down('#limit2').getValue()
				            		}});
				        		}
				        		else{
				        			ds.load({ params: { 
				            			start: 0, 
				            			limit: txtPageSize,
				            			v1:tab.down('#limit1').getValue(),
				            			v2:tab.down('#limit2').getValue()
				            		}});
				        		}		                		
        					}
			        	}
		            },'->',{
		            	xtype:'button',
		            	text:'统计分析',
			        	handler:function(){
			        		_cmd.Add({ Type: '统计分析', Method:0});
			        	}
		            },'-',{
		            	xtype:'button',
		            	itemId:'TxtDownBtn',
		            	icon:'img/disk.ico',
		            	text:'下载数据',
		            	handler: function () {
			                  var urlTemp = _url+"tmdata/download?" + 
	        				  Ext.urlEncode({
					            	tmParamStr:_lines.GetQueryInfo_download(),
					            	mode:tab.down('#QueryMode').val,
					            	opt:1,
					            	timeSpan:tab.down('#TimeSpan').getValue(),
					            	v1:tab.down('#limit1').getValue(),
			            			v2:tab.down('#limit2').getValue(),
			            			'headerList' : Ext.JSON.encode(cms)
					            });
		            		if (!window._df) {
		            	    	_df = document.createElement('iframe');    	
		            	    	_df.style.visibility = "hidden";
		            	    	document.body.appendChild(_df);	
		            	    }
		            		_df.src=urlTemp;
		                }
		            	/*menu: [{
			                text: '无填充下载',
			                icon: 'img/list.ico',
			                handler: function () {
			                	_df.src = _url+"tmdata/download?" + Ext.urlEncode({
					            	tmParamStr:_lines.GetQueryInfo(),
					            	mode:tab.down('#QueryMode').val,
					            	opt:1,
					            	timeSpan:tab.down('#TimeSpan').getValue(),
					            	v1:tab.down('#limit1').getValue(),
			            			v2:tab.down('#limit2').getValue()
					            });					            
			                }
			            },{
			                text: '带填充下载',
			                icon: 'img/list.ico',
			                handler: function () {
			                	_df.src = _url+"tmdata/download?" + Ext.urlEncode({
			                		tmParamStr:_lines.GetQueryInfo(),
					            	mode:tab.down('#QueryMode').val,
					            	opt:2,
					            	timeSpan:tab.down('#TimeSpan').getValue(),
					            	v1:tab.down('#limit1').getValue(),
			            			v2:tab.down('#limit2').getValue()
					            });	
			                }
			            }]*/
		            }],
			        bbar:[{
			        	width:22,
				        icon: 'img/page-prev.gif',
				        xtype: 'button',
				        tooltip: '上一页',
				        handler: function () {
							if(stack.length>0){
								var p=stack.pop();
								tmParams=p.split(' ');
								ds.proxy.extraParams.tmParamStr=p;
								if(ds.proxy.extraParams.type==2)
									ds.proxy.extraParams.timeSpan=tab.down('#TimeSpan').getValue();
								if(ds.proxy.extraParams.type==3){
									ds.proxy.extraParams.limit1=tab.down('#limit1').getValue();
									ds.proxy.extraParams.limit2=tab.down('#limit2').getValue();
								}
								ds.load();
							}
			        	}
			        },'-','第',{
			        	itemId:'PageNum',
			        	xtype:'label',
			        	text:'0'
			        },'页','-',{
			        	width:22,
				        icon: 'img/page-next.gif',
				        xtype: 'button',
				        tooltip: '下一页',
				        handler: function () {
				        	if(ds.getCount()<txtPageSize)return;
				        	var rec=ds.getAt(ds.getCount()-1).data;
				        	stack.push(tmParams.join(' '));
							for(var k in rec){
								if(k.indexOf('T')==0){
									var i=parseInt(k.substr(1));
									if(rec[k]===''){
										tmParams[i]=null;
										continue;
									}
									var split=tmParams[i].split('|');
									split[0]=Date.parse(rec[k]).format('YmdHisu');
									tmParams[i]=split.join('|');
								}
							}
							ds.proxy.extraParams.tmParamStr=tmParams.join(' ');
							if(ds.proxy.extraParams.type==2)
								ds.proxy.extraParams.timeSpan=tab.down('#TimeSpan').getValue();
							if(ds.proxy.extraParams.type==3){
								ds.proxy.extraParams.limit1=tab.down('#limit1').getValue();
								ds.proxy.extraParams.limit2=tab.down('#limit2').getValue();
							}
							ds.load();
			        	}
			        },{
			        	width:22,
				        icon: 'img/page-refresh.gif',
				        xtype: 'button',
				        tooltip: '刷新',
				        handler: function () {
							ds.load();
			        	}
			        }]
			    })
	    	});
    	tabs.setActiveTab(tab);
    	ds.load();
    }
	    
	    
	    var queryCondtion=Ext.create('Ext.menu.Menu', {
	        items: [{
	            itemId:'AddQueryCond',
	            text: '创建...',
	            icon:'img/add.ico',
	            disabled:true
	        },{
	            text: '编辑',
	            icon:'img/edit.ico'
	        },'-'],
	        isHide_:true,
	        msg_:'查询条件名称：',
	        data_:['创建...','编辑','删除'],
	        exist_:function(name){
	        	for(var i in this.data_){
	        		if(this.data_[i]==name)return true;
	        	}
	        	return false;
	        },
	        remove_:function(name){
	        	for(var i in this.data_){
	        		if(this.data_[i]==name)this.data_.slice(i,1);
	        	}
	        },
	        hasSubMenu:function(){
	        	return this.query('menuitem').length>3;
	        },
	        showCheck:function(isShow){
		    	var items=this.query('menuitem');
		    	for(var i=3;i<items.length;i++){
		    		var txt=items[i].text;
		    		this.remove(items[i]);
		    		var item={text:txt};
		    		if(isShow)item.checked=false;
		    		this.add(item);
		    	}
		    	if(lineStore.getCount()>0)items[0].setDisabled(isShow);
		    },
		    loadQueryCond:function(){
		    	if(this.hasSubMenu())return;
		    	var menu=this;
		    	Ajax.send('queryname',{userId:login.uid},function(json){
					var names=json.names;
					for (var i = 0; i < names.length; i++) {
						menu.add({text:names[i].name});
						menu.data_.push(names[i].name);
					}
				});
		    },
		    clearQueryCond:function(){
		    	var items=this.query('menuitem');
		    	for(var i=3;i<items.length;i++){
		    		this.remove(items[i]);
		    	}
		    },
	        listeners:{
	        	'click':function(menu, item, e, eOpts){      	
        			if(!item) return;
	        		 switch(item.text){
	        		 	case '创建...':
	        		 		if(lineStore.getCount()==0)return;
	        		 		Ext.Msg.prompt('创建查询条件',menu.msg_,function(btn, text){
								if(btn=='ok'){
									if(text.trim().length==0||menu.exist_(text)){
										menu.msg_='名称非法！ 请重新输入：';
										menu.fireEvent('click',menu, item);
									}
									else{
										menu.msg_='查询条件名称：';									
										var conds={isTile:Ext.getCmp('CGXS').icon.indexOf('cg')<0,data:[]};
										for (var i = 0; i < lineStore.getCount(); i++) {
											conds.data.push(lineStore.getAt(i).data);
										}
										conds=Ext.JSON.encode(conds);
										Ajax.send('addquery',{
											userId:login.uid,
											queryName:text,
											queryConds:conds
										},function(json,opts){
											if(json.rst=='success'){
												menu.add({text:text});
											}
											else{
												Ext.Msg.alert('提示','添加查询条件失败！');
											}
										});
									}
								}
								else menu.msg_='查询条件名称：';
							});
	        		 		break;
	        		 	case '编辑':       		 		
	        		 		if(!menu.hasSubMenu())break;
	        		 		menu.isHide_=false;
	        		 		item.setText('删除');
			            	menu.showCheck(true);           	
	        		 		break;
	        		 	case '删除':
	        		 		var items=menu.query('menuitem[checked="true"]');
	        		 		if(items.length>0){
	        		 			var txts=[];
		        		 		for(var i=0;i<items.length;i++){
				            		txts.push(items[i].text);                     				                        		
				            	}
				            	Ext.Msg.confirm('删除查询条件','是否删除查询条件“'+txts.join(',')+'”?',function(btn){
				        			if(btn=='yes'){	                        				
				        				Ajax.send('delquery',{
											userId:login.uid,
											queryName:"'"+txts.join("','")+"'"
										},function(json,opts){
											if(json.rst=='success'){
												for(var i in items){
						                    		menu.remove(items[i]);
						                    		menu.remove_(items[i].text);
						                    	}
											}
											else{
												Ext.Msg.alert('提示','删除查询条件失败！');
											}
											menu.isHide_=true;
			        		 				item.setText('编辑');
			        		 				menu.showCheck(); 
										});		        							                    	
				        			}
				        		});        		 			  		 			
	        		 		}
	        		 		else{
		        		 		menu.isHide_=true;
	        		 			item.setText('编辑');
	        		 			menu.showCheck();
			        		}	       		 		
	        		 		break;
	        		 	default:
	        		 		if(!menu.isHide_)return;
	        		 		Ajax.send(_url+'querydata',{
								userId:login.uid,
								queryName:item.text
							},function(json,opts){							
								var cgxs=Ext.getCmp('CGXS');
								if (json.conds.isTile) {
					                cgxs.setIcon('img/pp.ico');
					                cgxs.setTooltip('平铺显示');
					            }
					            else {
					                cgxs.setIcon('img/cg.ico');
					                cgxs.setTooltip('常规显示');
					            }	 
								//移除所有曲线
								while(lineStore.getCount()>0){
	        						_cmd.Add({ Type: '移除曲线', Id: lineStore.getAt(0).get('Id')});
	                    			lineStore.removeAt(0);
								}
								//添加查询曲线
								var data=json.conds.data;
								for (var i = 0; i < data.length; i++) {
									var rec=new Record(data[i]); 
						            if (_cmd.Add({ Type: '添加条件查询', Param: rec ,IsTile: json.conds.isTile})) {
						            	lineStore.add(rec);					            	
						            }  
								}
								toolBar.enable();
							});		
	        		 		break;
	        		 }
	        	},
	        	'beforehide':function(menu){
	        		return menu.isHide_;
	        	},
	        	'beforeshow':function(menu){
	        		
	        	}
	        }
		});
	    var satStore=Ext.create('Ext.data.JsonStore',{
		    proxy: {
		        type: 'ajax',
		        url: _url+'satparam',
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
		}),
		originPageSize=38,
		originStore = Ext.create('Ext.data.JsonStore',{
		    pageSize: originPageSize,
		    proxy: {
		        type: 'ajax',
		        url: _url+'origindata',
		        reader: {
		            root: 'items',
		            totalProperty: 'count'
		        }
		    },
		    fields: ['T','V'],
		    listeners: { load: function () {
		    	Ext.getCmp('OriginDownBtn').setDisabled(this.getCount()==0);
		    }}
		});
		Ext.apply(this, {
			layout : 'border',
			border:false,
			items : [{
				frame:true,
				region:'west',
				collapsible:true,
				title:'选择区域',
				width: 240,
                //minWidth: 150,
                split:true,
                margin: '0 5 0 0',
                layout: {
                    type: 'vbox',
                    align : 'stretch'
                },
                items: [{
                	height:250,
                	xtype: 'treepanel',
                    rootVisible: false,
                    border:false,
                    frame:true,
                    store : treeStore,
		            root:{
		            	sys_resource_id:'-1'
		            },
		            tbar:[satCodeCmp,{
	    	        	width:22,
	    	        	iconCls: 'x-icon-search',
	    	        	id:'bs_sat_search_btn',
	    	        	xtype: 'button',
	    	        	tooltip: '查询',
	    	        	style:{
	    	        		margin:'0'
	    	        	},
	    	        	handler: function () {
	    	        		treeStore.load({ params: { key: satCodeCmp.value} });
	    	        		tmStore.removeAll();
	    	        	}
	    	        }],
		            listeners:{
		             	'itemclick':function(obj, record, item, index, e, eOpts){
		             		sat.rec=record;
		             		var sat_id="";
							for(var p=sat.rec;p.parentNode!=null;p=p.parentNode){
								if(p.raw.type==0) {
									sat_id = p.get('id');
									break;
								}
							}
		             		tmStore.proxy.extraParams.sat_id=sat_id;
		             		tmStore.proxy.extraParams.id=record.raw.sys_resource_id;
		             		tmStore.proxy.extraParams.key='';
		             		tmStore.load({ params: { page:1,start: 0, limit: pageSize} });
//		             		Ext.getCmp("di").setText("1");
		             		codeCmp.setValue('');
		             	}
		            }
            	},{xtype:'splitter',height:5},
            	{
        			border:false, 
        			layout: 'fit',
        			flex:1,
        			items: tmCmp
            	}]
			},{
				region: 'center',
				xtype:'tabpanel',
            	id:'tabs',
            	items:[{
            		title:'曲线查询',
            		id:'MaskRegion',
            		layout: {
	                    type: 'vbox',
	                    align : 'stretch'
	                },
	                items: [toolBar,{
	                    border:false,
	                    flex:1,
	                    id: 'Chart',
	                    listeners:{
	                    	'resize':function(o,w,h,ow,oh){
	                    		if(!ow) return;
	                    		_cmd.Add({ Type: '调整窗口尺寸', Width: w, Height: h});
	                    	}
	                    }
	            	},{xtype:'splitter',height:3},{
	            		height: 150,
	                    margin: '2 0 0 0',
	                    minHeight: 100,
	                    layout:'fit',
	                    border:false,
	                    title:'线型管理',
	                    collapsible:true,
	                    items:lineCmp
	            	}]
            	}],
            	listeners:{
            		'tabchange':function(tabPanel, newCard, oldCard, eOpts){
            			_ActiveTab=newCard.title;
            			if(window._YSSJT)_YSSJT.setVisible(newCard.title=='曲线查询');
            			if(window._STATS)_STATS.close();
            		}	
        		}	
			}]
		});
		this.callParent(arguments);
	}
});