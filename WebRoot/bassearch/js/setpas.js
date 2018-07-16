/*
 *密码修改
 */

Ext.define('js.setpas',{
	constructor:create
});	

function create(){
	var errCmp=Ext.create('Ext.form.Label', {
		style:{
			color:'#f00'
		}
	}),
	pasForm = Ext.widget('form', {
		frame:true,
	    width: 320,
	    height:140,
	    border:false,
		defaultType: 'textfield',
	    buttonAlign:'center',
	    renderTo:Ext.getBody(),
	    bodyPadding:'5 20 20 10',
	    defaults: {
	        anchor: '100%'
	    },	
	    fieldDefaults: {
	    	labelAlign: 'right',
	        labelWidth: 60
	    },
	    dockedItems: [{
	    	xtype: 'container',
	    	dock: 'top',
	    	height:18,
	    	padding: '0 35 0 35',
	    	items: [errCmp]
	    }],
	    items: [{
            allowBlank: false,
            fieldLabel: '修改密码',
            name: 'password',
            inputType: 'password'
        },{
            fieldLabel: '确认密码',
            name: 'repassword',
            inputType: 'password',
            listeners:{
            	'blur':function(){
            		if(this.previousNode().getValue()!=this.value){
            			errCmp.setText('两次密码输入不一致!请重新输入!');
            		}else{
            			errCmp.setText('');
            		}
            	}	
            }  
        }],
		buttons: [{ 
        	text:'确认' ,
        	handler: function(btn) {
        		var password=pasForm.getValues()["password"];
        		var repassword=pasForm.getValues()["repassword"];
        		if(password == ''){
        			Ext.Msg.alert('提示','密码不可为空!');
        			return;
        		}
        		if(password!=repassword){
        			errCmp.setText('两次密码输入不一致!请重新输入!');
        			return;
        		}
        		Ajax.send('orguser/setpassword.edq',pasForm.getValues(), function (obj, opts) {
        			btn.up("window").close();
        	    	if(obj.success){
        	    		Ext.Msg.alert('提示','修改密码成功!');
        	    	}else{
        	    		Ext.Msg.alert('提示','修改密码失败!');
        	    	}
        	    });
            }
        },{ 
	        text:'关闭' ,
        	handler: function(btn) {
        		btn.up("window").close();
        	}
	    }]
	});
	
	var pasWin=Ext.create('Ext.window.Window',{
	 	title: '密码修改',
	 	border:false,
		closable : true,
		layout : 'fit',
		modal : true,
		constrain : true,
		items : pasForm
	});
	return pasWin;
}