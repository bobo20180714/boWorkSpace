/*
 *创建主界面
 */
Ext.define('query.view.mainView',{
	extend : 'Ext.panel.Panel',
	id:'frame',
	extend: 'Ext.Viewport',
	layout: 'fit',
	alias : 'widget.mainView',
	hideBorders: true,
	initComponent : function(){
		var center_tab = Ext.widget('tabpanel', {
			id:'center_tab',
			frame:true,
			region: 'center', 
			border: false,
			layout: 'fit',
			padding:0,
		    defaults: {
			   autoScroll:true,
			   border : false // 不要边框
		    },
		    activeTab: 0,
		    border: false,
		    items: [/*{
		    	title : '首页',
		    	autoLoad:{
		    		url:'main.html',
		    		scripts:true
		    	}
		    }*/]
		});
		var menubar=Ext.create('Ext.toolbar.Toolbar',{border:false});
		Ajax.send('menu/findmenu.edq',null,function (obj, opts) {
			for(var i=0;i<obj.length;i++){
				if(i==obj.length-1){
					menubar.add(obj[i]);
				}else{
					menubar.add(obj[i],'-');
				}
			}
	    });
//		center_tab.setActiveTab('RelationSearch');
		var sb = Ext.create('Ext.toolbar.Toolbar', {
			id:'sb',
			region:'south',
	    	padding:0,
	    	height:20,
	    	border:false,
	    	items:['<div id="_LOAD" style="width:18px;height:18px;background:url(img/loading.gif) no-repeat center center transparent;display:none;"/>',
	    	{
	    		itemId:'txt',
	        	xtype:'tbtext',
	        	text: '就绪'        
	        },'->',
	        //----------------------系统风格------start---------------------------------------
	        {
	        	 xtype: 'combo',
	             width: 170,
	             labelWidth: 50,
	             fieldLabel: '风格',
	             displayField: 'name',
	             valueField: 'value',
	             editable: false,
	             labelStyle: 'cursor:move;',
	             margin: '0 10 0 0',
	             store: Ext.create('Ext.data.Store', {
	                 fields: ['value', 'name'],
	                 data : [
	                    // { value: 'access', name: '黑色' },
	                     { value: 'default', name: '蓝色' },
	                     { value: 'gray', name: '灰色' }
	                    //,{ value: 'neptune', name: '蓝色' }
	                 	    ]
	             }),
	             value: theme || 'default',
	             listeners: {
	                 select: function(combo) {
	                     var theme = combo.getValue();
	                     if (theme !== 'default') {
	                         setParam({ theme: theme });
	                     } else {
	                         removeParam('theme');
	                     }
	                 }
	             }
	        }
	        //----------------------系统风格------end---------------------------------------
	        ,''],
	        setText:function(txt,isIcon){
	        	Ext.getDom('_LOAD').style.display=isIcon?'block':'none';
	        	this.down('#txt').setText(txt!=undefined?txt:'就绪');
	        },
	        showBusy:function(txt){
	        	Ext.getDom('_LOAD').style.display='block';
	        	this.down('#txt').setText(txt||'查询中...');
	        }
	    });
		var org_name='';
		var zhiwu='';
		var user_name='';
		if(userInfo){
			org_name=userInfo.org_name;
			if(!org_name) org_name = '未定义';
			zhiwu=userInfo.zhiwu;
			if(!zhiwu) zhiwu = '未定义';
			user_name=userInfo.user_name;
		}
        Ext.apply(this, {
        	layout: 'fit',
    		hideBorders: true,
    		layout: {  //添加top使用
                type: 'vbox',
                align : 'stretch'
            },
    		items: [{
    			padding:'0',
    			border:false,
    			html:'<div class="top">'+
    				'<div class="left"> <img src="images/logo.png" />'+
    					'<p>在轨数据管理与应用系统</p>'+
    	        	'</div>'+
    			    '<div class="right">'+
    			        	'<p class="out"><a href="javascript:logOut()">注销</a></p>'+
    			        	'<p class="password"><a style="color:#FFFFFF" href="FXSJK/FXSJK.html" target="_blank">使用说明</a></p>'+
	    			        '<p class="password"><a href="javascript:setPas()">修改密码</a></p>'+
    			            '<p class="place">单位：'+org_name+'</p>'+
    			        	'<p class="name" id="session_user_name">用户名：'+user_name+'</p>'+
    			    '</div>'+
    			'</div>'
	        },{
    			flex:1,
		        layout:'border',
		        border:false,
		        items:[{
		        	frame:true,
		        	border:false,
		        	region: 'north',
		        	xtype: 'toolbar',
				    padding:'0 10',
				    items: [menubar]
		        },center_tab,sb]
    		}]
    		
        });
        this.callParent(arguments);
	}
	
});
//系统风格----start-----------------------------------------张超-2014-6-25--------------
var theme = Ext.Object.fromQueryString(location.search).theme;
var rtl ="rtl"; 
function setParam(param) {
    var queryString = Ext.Object.toQueryString(
        Ext.apply(Ext.Object.fromQueryString(location.search), param)
    );
    location.search = queryString;
}

function removeParam(paramName) {
    var params = Ext.Object.fromQueryString(location.search);

    delete params[paramName];

    location.search = Ext.Object.toQueryString(params);
}
//系统风格----end-----------------------------------------张超-2014-6-25--------------

