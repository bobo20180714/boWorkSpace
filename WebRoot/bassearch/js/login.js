/*
 *登录验证
 */

Ext.define('js.login',{
	constructor:create
});	

function create(){
	var me=this,loginCmp=null;
	//属性
	this.uid=0;
	//事件
	this.success=[];
	this.failure=null;
	this.caller=null;
    //方法
	this.setInfo=function(user){//设置登录状态信息
    	if(user){
    		Ext.getCmp('UserName').setText(user.Name);
			Ext.getCmp('ZhiWu').setText(user.Zhiwu);
			Ext.getCmp('ChangSuo').setText(user.Changsuo);
			Ext.getCmp('TCDL').setText('注销');
    	}
    	else{
    		this.uid=0;
    		Ext.getCmp('UserName').setText('');
			Ext.getCmp('ZhiWu').setText('');
			Ext.getCmp('ChangSuo').setText('');
			Ext.getCmp('TCDL').setText('登录');
    	}
    };
    this.isLogin=function(check){//判断是否登录	    	
    	if(this.type=='wan'||check){	  
    		if(this.uid==0)return false;
        	Ajax.send('islogin',{userId:this.uid},function(json){
        		if(!json.isLogin)me.uid=0;
        	},false);	   
        	return me.uid!=0;
        }
        return true;
    };
    this.login=function(){
    	this.show();
    	this.setInfo(null);
    };
    this.show=function(){
    	var errCmp=Ext.create('Ext.form.Label', {
    		style:{
    			color:'#f00'
    		}
    	});
    	loginCmp=Ext.create('Ext.form.Panel',{
    		x:document.body.clientWidth/2-160,
    		y:300,
    		renderTo: Ext.getBody(),
    	    title: '用户登录',
    	    frame:true,
    	    width: 320,
    	    height:160,
    	    defaultType: 'textfield',
    	    buttonAlign:'center',
    	    renderTo:Ext.getBody(),
    	    bodyPadding:'5 20 20 20',
    	    closable:true,
    	    style:{
    	    	position:'absolute',
    	    	zIndex:2
		    },
    	    defaults: {
    	        anchor: '100%'
    	    },	
    	    fieldDefaults: {
    	    	labelAlign: 'right',
    	        labelWidth: 50
    	    },
    	    dockedItems: [{
    	    	xtype: 'container',
    	    	dock: 'top',
    	    	height:18,
    	    	padding: '0 35 0 35',
    	    	items: [errCmp]
    	    }],
    	    items: [
    	    	{
    	            allowBlank: false,
    	            fieldLabel: '登录名',
    	            name: 'loginid',
    	            value:'admin'
    	        },{
    	            itemId:'PassWord',
    	            fieldLabel: '密&nbsp;&nbsp;&nbsp;码',
    	            name: 'password',
    	            value:'123',
    	            inputType: 'password'
    	        }
    	    ],    
    	    buttons: [{ 
            	text:'登录' ,
            	handler: function() {
                    loginCmp.submit({
                        url: 'login',
                        success: function(form, action) {
                           var user=action.result.user;
                           me.uid=user.Id;
                           for(var i=0;i<me.success.length;i++){
                           		me.success[i](user);
                           }
                           if(me.caller){
                           		me.caller(user);
                           		me.caller=null;
                           }
                        },
                        failure: function(form, action) {
                           errCmp.setText('用户名或者密码不正确！请重新输入！');
                           form.owner.form.reset();                      
                           if(me.failure)me.failure();
                        }
                    });
                }
            },{ 
    	        text:'重置' ,
            	handler: function() {
            		errCmp.setText('');
            		loginCmp.form.reset();
            	}
    	    }]
    	});
    };
    this.hide=function(){
    	if(loginCmp){
    		loginCmp.close();
    		loginCmp=null;
    	}
    };
}