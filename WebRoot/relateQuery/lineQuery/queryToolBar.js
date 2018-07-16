var queryToolBar = {
	beginCmp:null,
	endCmp:null,
	timeCmp:null,
	toolbar:null,
	conditionWin:null,//条件查询窗口
	pipeTiyes:{},
    init:function(options){
		this.initTimeEdit();
		this.initToobar((options==null || options.isShowRelateCondition==null)?false:options.isShowRelateCondition);
		this.setEnable(false);
    },
    initTimeEdit:function(){
		var tObj = this;
		tObj.beginCmp = Ext.create('Ext.form.field.Text', {
    		id:'relation_beginCmp',
    	    width:110,
    	    value: Now.getBegin(),//Ext.Date.format(new Date(2009,0,1,0,0,0),'Y-m-d H:i'),
    	    allowBlank: false,
    	    xtype:'textfield',
    	    vtype: 'time',
    	    vtypeText:'请输入正确的时间',
    	    listeners: { 
    	    	focus: function (obj) {            
    	            this.oldVal=obj.value;
    	        },blur:function(obj){
    	        	if (!obj.validate()) {
    	                obj.setValue(obj.oldVal);
    	            }
    	        }, specialkey: function (obj, e) {
    	            if (e.getKey() == e.ENTER){
    	            	tObj.setTimeRange(obj,obj.value,tObj.endCmp.getValue()); 
    	            }                
    	        },change:function(){
    	        	if(!window._relation_isTimeChange){
    	        		_relation_isTimeChange=true;
    	        		return;
    	        	}
    	        	if(tObj.timeCmp.getValue()!='自定义'){
    	        		tObj.beginCmp.setDisabled(false);
    	        		tObj.endCmp.setDisabled(false);
    	        		tObj.timeCmp.setValue('自定义');
    	        	}	        	
    	        }
    	    }
    	});
		tObj.endCmp = Ext.create('Ext.form.field.Text', {
			id:'relation_endCmp',
		    width:110,
		    value: Now.getEnd(),//Ext.Date.format(new Date(2009,0,5,0,0,0),'Y-m-d H:i'),
		    allowBlank: false,
		    vtype: 'time',
		    xtype:'textfield',
		    vtypeText:'请输入正确的时间',
		    listeners: {focus: function (obj) {            
		        this.oldVal=obj.value;
		    }, blur: function (obj) {
		    	if (!obj.validate()) {
		            obj.setValue(obj.oldVal);
		        }
		    }, specialkey: function (obj, e) {
		        if (e.getKey() == e.ENTER)
		        	tObj.setTimeRange(obj,tObj.beginCmp.getValue(),obj.value);
		    }}
		});
		tObj.timeCmp = Ext.create('Ext.form.ComboBox', {
			id:'relation_toolbar_TIMECMP',
			width: 100,       
		    store: [[0, '自定义'],[1, '最近1分钟'], [5, '最近5分钟'], [10, '最近10分钟'], [15, '最近15分钟'], [30, '最近30分钟'], [60, '最近1小时'], [120, '最近2小时'], [240, '最近4小时'], [480, '最近8小时'], [720, '最近12小时'], [1440, '最近1天'], [2880, '最近2天'], [10080, '最近1周'], [20160, '最近2周'], [43200, '最近1月'], [129600, '最近3月'], [259200, '最近6月'], [518400, '最近1年']],
		    triggerAction: 'all',
		    editable: false,
		    lazyRender: true,
		    value:'自定义',
		    listeners: { select: function () {
		        if(this.value>0){
		        	tObj.beginCmp.setDisabled(true);
		        	tObj.endCmp.setDisabled(true);
		        	_relation_isTimeChange=false;
		        	tObj.refreshFn(this.value);
		        }
		        else{
		        	tObj.beginCmp.setDisabled(false);
		        	tObj.endCmp.setDisabled(false);
		        }
		    
		    }}
		});
    },
    //初始化工具条按钮
	initToobar:function(isShowRelateCondition){
		var tObj = this;
		tObj.toolbar = Ext.create('Ext.toolbar.Toolbar',{
		    margin: '-1 -1 0 -1',
		    enableOverflow: true,
		    items:[tObj.timeCmp,tObj.beginCmp,'到',tObj.endCmp, {
		    icon: 'img/page-refresh.gif',
		    xtype: 'mybutton',
		    tooltip: '刷新',
		    handler: function () {
		    	var sel=tObj.timeCmp.getValue();
		    	if(sel=='自定义'||sel==0){
		    		tObj.setTimeRange(null,tObj.beginCmp.getValue(),tObj.endCmp.getValue());
		    	}
		    	else{
		    		_relation_isTimeChange=false;
		    		tObj.refreshFn(sel);
		    	}
		    }
		}, '-', {
		    id: 'RTCX',
		    icon: 'img/cx.ico',
		    xtype: 'mybutton',
		    tooltip: '撤销',
		    handler: function () { _relation_cmd.Add({ Type: '撤销曲线' });}
		}, {
		    id: 'RTHF',
		    icon: 'img/hf.ico',
		    xtype: 'mybutton',
		    tooltip: '恢复',
		    handler: function () { _relation_cmd.Add({ Type: '恢复曲线' });}
		},'-', {	        
		    id:'RCGXS',
		    icon: 'img/cg.ico',
		    xtype: 'mybutton',
		    tooltip: '常规显示',
		    handler: function () {
		        if (this.icon.indexOf('cg')>-1) {
		            this.setIcon('img/pp.ico');
		            this.setTooltip('平铺显示');
		            _relation_cmd.Add({ Type: '平铺显示' ,IsTile:true});
		        }
		        else {
		            this.setIcon('img/cg.ico');
		            this.setTooltip('常规显示');
		            _relation_cmd.Add({ Type: '平铺显示' ,IsTile:false});
		        }
		    }
		},{
			icon: 'img/discnn.ico',
		    xtype: 'mybutton',
		    tooltip: '历史显示曲线',
		    handler: function (obj) {
		        if (this.icon.indexOf('discnn')>-1) {
		        	if(_relation_lines.IsLongQuery()){
		        		Ext.Msg.confirm('提示信息','您有长时间历史模式查询,是否更改<br>为12小时实时模式开始查询?',function(btn){
		            		if(btn=='yes'){	    
		            			obj.setIcon('img/cnn.ico');
		    	                obj.setTooltip('实时模式');
		    	                _relation_cmd.Add({Type: '实时显示曲线'});
		            		}
		        		});  
		        	}
		        	else{
		        		obj.setIcon('img/cnn.ico');
		                obj.setTooltip('实时模式');
		                _relation_cmd.Add({Type: '实时显示曲线'});
		        	}
		        }
		        else {
		            this.setIcon('img/discnn.ico');
		            this.setTooltip('历史模式');
		            _relation_cmd.Add({Type: '实时显示曲线'});
		        }	            
		    }
		},{
			icon: 'img/tiaojian.ico',
			itemId:'relationButton',
			id:'tiaojian',
		    xtype: 'mybutton',
		    tooltip: '条件查询',
		    hidden:!isShowRelateCondition,
		    handler:function(){
		    	if(tObj.conditionWin != null){
		    		tObj.conditionWin.show();
					return;
				}
		    	tObj.conditionWin = $.ligerDialog.open({
					title : '关联条件',
					width : 680,
					height : 440,
					url : basePath+'relateQuery/selectCondition/GraphicalIn.jsp',
					buttons:[{ text: '确定',type:"save", width: 80 ,onclick:function(item, dialog){
						//获取条件
		        	    var rsData = dialog.frame.getConditions();
		        	    if(rsData){
		        	    	
		        	    	rsData.sql = 'between "'+ tObj.beginCmp.getValue() +':00" and "'+ tObj.endCmp.getValue() +':00" ; select time where '+rsData.sql;
		        	    	rsData.asyc = false;
		        	    	var queryRs = tObj.queryRelateReuslt(rsData);
		        	    	if(queryRs.success){
			        	    	lineManagerArea.init(queryRs.records);
			        	    	dialog.hide();
			        	    }else{
			        	    	Alert.tip(queryRs.message);
			        	    }
		        	    }
		          }},
		          { text: '关闭',type:"close", width: 80, onclick:function(item, dialog){
		        	  dialog.hide();
		          }}]
				});
		    }
		},{
		    id:'shield_id',
		    icon: 'img/shield.png',
		    xtype: 'mybutton',
		    tooltip: '屏蔽',
		    hidden:!isShowRelateCondition,
		    handler: function (){
		    	if (this.icon.indexOf('no')>-1) {
		    		this.setIcon('img/shield.png');
		            this.setTooltip('屏蔽');
		            _relation_cmd.Shield(false);
		        }else {
		        	var tG=Ext.getCmp("TimeGrid");
		        	if(!tG||tG.getSelectionModel().getSelection().length==0){
			    		Ext.example.msg('系统提示', "请至少选择一条查询结果时间段信息!");
						return;
			    	}
		        	this.setIcon('img/noshield.png');
		            this.setTooltip('去除屏蔽');
		            _relation_cmd.Shield(true);
		        }	 
		    }
		}, '-',{
			id:'RSJTY',
		    icon: 'img/tiye.ico',
		    xtype: 'mybutton',
		    tooltip: '数据剔野',
		    handler: function () {
		    	this.toggle();
		    	var rtiyeCmp1=Ext.getCmp('rtiye1'),
		    		rtiyeCmp2=Ext.getCmp('rtiye2'),
		    		rtiyeBtnCmp=Ext.getCmp('RTiyeBtn'),
		    		rchexiaoCmp=Ext.getCmp('RTiyeChexiao');
		    	if(this.pressed){
		    		_relation_chart.hidePoint();
		    		rtiyeCmp1.show();
		    		rtiyeCmp2.show();
		    		rtiyeBtnCmp.show();
		    		rchexiaoCmp.show();
		    	}
		    	else{
		    		rtiyeCmp1.hide();
		    		rtiyeCmp2.hide();
		    		rtiyeBtnCmp.hide();
		    		rchexiaoCmp.hide();
		    	}
		    	_relation_drawarea.ShowRuler(!this.pressed);
		    }
		}, {
		    id: 'RTiyeChexiao',
		    icon: 'img/cx.ico',
		    xtype: 'mybutton',
		    tooltip: '撤销最近一次剔野',
		    hidden:true,
		    style:{
				borderLeft:'1px solid #99BBE8',
				borderTop:'1px solid #99BBE8',
				borderBottom:'1px solid #99BBE8',
				marginRight:0
			},
		    handler: function () { 
		    	_relation_cmd.Add({ Type: '撤销剔野'});
		    }
		},{
			xtype:'textfield',
			id:'rtiye1',
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
			id:'rtiye2',
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
			id:'RTiyeBtn',
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
				var v1=Ext.getCmp('rtiye1').getValue(),
					v2=Ext.getCmp('rtiye2').getValue();
				if(v1!=''&&v2!=''&&v1-v2>=0){
					Ext.Msg.alert('提示','剃野下限不能大于剃野上限！');
					return;
				}
				tObj.pipeTiyes[_Rrecord.get('Id')]=
					[Ext.getCmp('rtiye1').getValue(),Ext.getCmp('rtiye2').getValue()];
				//周星陆15-12-28 添加设置管道剔野数据
				_relation_cmd.setPipeTiyes(v1,v2);
				_relation_cmd.Add({ Type: '曲线剔野'});
			}
		},'-', {
		    icon: 'img/zsy.ico',
		    xtype: 'mybutton',
		    tooltip: '缩放当前曲线',
		    handler: function () { _relation_cmd.Add({ Type: '缩放当前曲线' });}
		}, {
		    icon: 'img/sy.ico',
		    xtype: 'mybutton',
		    tooltip: '上移当前曲线',
		    hidden:true,
		    handler: function () { _relation_cmd.Add({ Type: '上移当前曲线' });}
		}, {
		    icon: 'img/xy.ico',
		    xtype: 'mybutton',
		    tooltip: '下移当前曲线',
		    handler: function () { _relation_cmd.Add({ Type: '下移当前曲线' });}
		}, {
		    icon: 'img/fd.ico',
		    xtype: 'mybutton',
		    tooltip: '放大当前曲线',
		    handler: function () { _relation_cmd.Add({ Type: '放大当前曲线' });}
		}, {
		    icon: 'img/sx.ico',
		    xtype: 'mybutton',
		    tooltip: '缩小当前曲线',
		    handler: function () { _relation_cmd.Add({ Type: '缩小当前曲线' });}
		}, '-', {
		    icon: 'img/zsy0.ico',
		    xtype: 'mybutton',
		    tooltip: '缩放所有曲线',
		    handler: function () { _relation_cmd.Add({ Type: '缩放所有曲线' });}
		}, {
		    icon: 'img/sy0.ico',
		    xtype: 'mybutton',
		    tooltip: '上移所有曲线',
		    handler: function () { _relation_cmd.Add({ Type: '上移所有曲线' });}
		}, {
		    icon: 'img/xy0.ico',
		    xtype: 'mybutton',
		    tooltip: '下移所有曲线',
		    handler: function () { _relation_cmd.Add({ Type: '下移所有曲线' });}
		}, {
		    icon: 'img/fd0.ico',
		    xtype: 'mybutton',
		    tooltip: '放大所有曲线',
		    handler: function () { _relation_cmd.Add({ Type: '放大所有曲线' });}
		}, {
		    icon: 'img/sx0.ico',
		    xtype: 'mybutton',
		    tooltip: '缩小所有曲线',
		    handler: function () { _relation_cmd.Add({ Type: '缩小所有曲线' });}
		}, '-', {
		    icon: 'img/tag.ico',
		    xtype: 'mybutton',
		    tooltip: '添加标注',
		    handler: function () {_relation_cmd.Add({ Type: '添加标注'}); }
		},{
		    icon: 'img/tongji.ico',
		    xtype: 'mybutton',
		    tooltip: '统计分析',
		    menu: [{
		        text: '统计分析',
		        ty_: 0,
		        icon: 'img/tongji1.ico',
		        handler: function () { _relation_cmd.Add({ Type: '统计分析', Method:this.ty_,Tiye:tObj.pipeTiyes});}
		    },
		    {
		        text: '按照游标区间统计',
		        ty_: 1,
		        icon: 'img/tongji2.ico',
		        handler: function () { _relation_cmd.Add({ Type: '统计分析', Method:this.ty_,Tiye:tObj.pipeTiyes});}
		    }]
		}/*,'-',{
		    icon: 'img/dy.ico',
		    xtype: 'mybutton',
		    tooltip: '打印',
		    handler: function () { _relation_cmd.Add({ Type: '打印曲线' }); }
		}*/]
		});
		//显示tip
		Ext.QuickTips.init();
		tObj.toolbar.render('queryToolBar');
	},
	setEnable:function(flag){
		var tObj = this;
    	if(flag==undefined)flag=true;
    	var items = tObj.toolbar.items.items;
    	if(flag){
    		var disables = ['bcx', 'bhf', 'bsbf', 'bljs', 'brjs','bpointline'];
		    for (var i = 0; i < disables.length; i++) disables[i] = 'img/' + disables[i] + '.ico';
		    disables=disables.join('');		    
		    for (i = 0; i < items.length; i++) {
		        if (!items[i].icon||disables.indexOf(items[i].icon) > -1) continue;
		        items[i].setEnable(true);
		    }  
		    Ext.getCmp('tiaojian').setEnable(true);
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
    
	},
	/**
	 * 获取开始时间
	 */
	getStartTimeStr:function(){
		return this.beginCmp.getValue();
	},
	/**
	 * 获取结束时间
	 */
	getEndTimeStr:function(){
		return this.endCmp.getValue();
	},
	setTimeRange:function(obj,t1,t2,id){
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
	        if(obj){
	        	obj.setValue(obj.oldVal);
	        }
	        return;
	    }
		//判断内存中是否有曲线
	    if(lineCache.getLineCount() > 0 &&(obj==null||obj.value!=obj.oldVal)){
	    	_relation_cmd.Add({ Type: '压缩曲线', Begin:t1.getTime(), End:t2.getTime(), Id: id});
		}       
	},
	/**
	 * 曲线查询工具栏
	 */
	refreshFn:function(v){
		//var now=new Date().getTime();
		var now=window._WebTime?_WebTime.getMs():new Date().getTime();
		var bt1=new Date(now-v * 60000);
		var bt2=new Date(now*1);
		this.beginCmp.setValue(bt1.format('Y-m-d H:i'));
		this.endCmp.setValue(bt2.format('Y-m-d H:i'));
		if(lineCache.getLineCount() > 0){
			_relation_cmd.Add({ Type: '压缩曲线', Begin:bt1.getTime(), End:bt2.getTime()});	
		}
//		reloadTimeGrid(this.beginCmp.getValue(),this.endCmp.getValue());
	},

	/**
	 * 查询结果集
	 */
	queryRelateReuslt:function(condition){
		var rs = null;
		//查询
		$.ajax({
			url:basePath + 'rest/relationSearch/relationsearch',
			data:condition,
			async:false,
			success:function(ret){
				rs = eval('('+ret+')');
			}
		});
		return rs;
	}
};

//统计窗口
var tongjiWin = {
	win:null,
	tongjiGrid:null,
	openWin : function(title,width,height,gridDivId){
		var t = this;
		
		if( t.win){
			 t.win.close();
		}
		
		t.win = $.ligerDialog.open({
			title : title,
			width : width,
			height : height,
			content : '<div style="width:'+width+',height:'+height+'" id="'+gridDivId+'"></div>',
			buttons:[{
				 text: '关闭',  type:'close',width: 80, onclick:function(item, dialog){
					 t.win.close();
				 }
			}]
		});
		t.tongjiGrid = $("#"+gridDivId).ligerGrid({
			columns : [
						{
							display : '名称',
							name : 'name',
							width : 100,
							render:function(item){
								if(item.name){
									return "<p title='"+item.name+"'>"+item.name+"</p>";
								}
							}
						},
						{
							display : '参数名称',
							name : 'param',
							width : 150,
							render:function(item){
								if(item.param){
									return "<p title='"+item.param+"'>"+item.param+"</p>";
								}
							}
						},
						{
							display : "参数代号",
							name : "code",
							width : 100
						},
						{
							display : "最小值",
							name : "min",
							width : 90
						},
						{
							display : "最大值",
							name : "max",
							width : 90
						},
						{
							display : "开始时间",
							name : "begin",
							width : 130
						},
						{
							display : "结束时间",
							name : "end",
							width : 130
						}
				],
				rownumbers : false,
				checkbox : false,
				rowHeight : 27,
				usePager:false,
				heightDiff : 30,
				frozen : false ,
				delayLoad:true
			});
	},
	addGridData:function(rowData){
		var t = this;
		t.tongjiGrid.addRow(rowData);
	}
};
