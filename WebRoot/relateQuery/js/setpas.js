/*
 *密码修改
 */

Ext.define('js.setpas',{
	constructor:create
});	
var pass_length=8;
var newPassRight = false;
function create(){
	var errCmp=Ext.create('Ext.form.Label', {
		style:{
			color:'#f00'
		}
	}),
	pasForm = Ext.widget('form', {
		frame:true,
	    width: 320,
	    height:170,
	    border:false,
		defaultType: 'textfield',
	    buttonAlign:'center',
	    renderTo:Ext.getBody(),
	    bodyPadding:'30 20 20 10',
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
            inputType: 'password',
            listeners:{
            	'blur':function(){
            		if(checkPass(this.value)==0){
            			newPassRight = false;
            			errCmp.setText('密码长度低于'+pass_length+'位!请重新输入!');
            			this.focus();
            		}else if(checkPass(this.value)<3){
            			newPassRight = false;
            			errCmp.setText('密码复杂度不足，必须包含引文大写字母、英文小写字母、阿拉伯数字、特殊字符四类字符中的三类!');
            			this.focus();
            		}else if(this.nextNode().getValue() != "" &&
            				this.nextNode().getValue() != this.value){
            			newPassRight = true;
            			errCmp.setText('两次密码输入不一致!请重新输入!');
            		}else{
            			newPassRight = true;
            			errCmp.setText('');
            		}
            	}	
            }  
        },{
            fieldLabel: '确认密码',
            name: 'repassword',
            inputType: 'password',
            listeners:{
            	'blur':function(){
            		if(newPassRight){
	            		if(this.value == "" || this.previousNode().getValue()!=this.value){
	            			errCmp.setText('两次密码输入不一致!请重新输入!');
	            		}else{
	            			errCmp.setText('');
	            		}
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
	
	Ajax.send('orguser/getpasslength.edq',null, function (obj, opts) {
    	if(obj.success){
    		if(obj.length)
    		pass_length=obj.length;
    	}
    });
	return pasWin;
}

function checkPass(s){
	if(s.length<pass_length){
		return 0;
	}
	var ls =0;
	if(s.match(/([a-z])+/)){
		ls++;
	}
	if(s.match(/([0-9])+/)){
		ls++;
	}
	if(s.match(/([A-Z])+/)){
		ls++;
	}
	if(s.match(/([^a-zA-Z0-9])+/)){
		ls++;
	}
	return ls;
}