var formdata = null;
$(function (){
	initForm();
	getData();
});

function initForm(){
	formData = $("#form3").ligerForm({
		width:360,
    	labelAlign:'center',
        inputWidth: 200, 
        labelWidth: 100, 
        space: 20,
        validate:true,//验证
        title:'修改机构用户信息',
        fields: [
			{ display: "登录名称", name: "login_name",id:"login_name",type: "text",newline: true,readonly:true},
			{ display: "用户名称", name: "user_name",id:"user_name",type: "text",newline: true,validate:{required:true}},
			{ display: "联系方式", name: "telephone",id:"telephone",type: "text",newline: true,validate:{telephone:true}},
//			{ display: "用户单位", name: "danwei",id:"danwei",type: "text",newline: true,validate:{maxlength:50}},
//			{ display: "用户部门", name: "bumen",id:"bumen",type: "text",newline: true,validate:{maxlength:50}},
			{ display: "用户职位", name: "zw",id:"zw",type: "text",newline: true,validate:{maxlength:50}},
			{ display: "截止日期", name: "end_time",id:"end_time",type: "date",newline: true,validate:{required:true}}
        ]
    }); 
	
}

/**
*
*获取url里的参数
*@param 参数名
*/
function getParam(name){

	var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)","i");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null){
		return unescape(r[2]);
	}
	return null;

}

function getData(){
	var login_name = getParam("login_name");
	$.post(
			basePath+"rest/orguser/getUserById",
			{
				user_id:user_id
			},
			function(data)
			{
				formData.setData({
					login_name:data.user_account,
					user_name:data.user_name,
					telephone:data.telephone,
					danwei:data.danwei,
					bumen:data.bumen,
					zw:data.zw,
					end_time:data.end_time
				});
			},"json");
}

//页面提交
function submitForm(){
	var data = formData.getData();

	if(toValidForm()){
		//数据提交
		data.user_id=getParam("id");
		$.post(basePath+"rest/orguser/userinfoupdate",data, 
				function(result,textStatus) {
			if (result.success == "true") {
				parent.currWin.Alert.tip(result.message);
				parent.currWin.f_reload();
				parent.currWin.f_closeDlg();
			} else {
				parent.currWin.Alert.tip(result.message);
				parent.currWin.closeDlgAndReload();
			}
		}, "json");
	}
}

//验证
function toValidForm() {
	var vboolean = $("#form3").valid();
	if (vboolean) {
		return true;
	} else {
		return false;
	}
}

function check(data){
	var telephoneReg = /(^1[3|5|8][0-9]{8}\d$)|(^[0-9]{7,8}$)|(^[0-9]{3,4}\-[0-9]{7,8}$)/; 
//	if(data!=null){
//		if(isEmpty(data.paramName)){
//			parent.Alert.tip("参数名称不能为空！");
//			return false;
//		}
		if(isReName(data.login_name)){
			parent.Alert.tip("登录名称不能重复，请修改后重新提交！");
			return false;
		}
//		if(data.paramName.length>100){
//			parent.Alert.tip("参数名称不能超过100个字符！");
//			return false;
//		}
//		if(isEmpty(data.code)){
//			parent.Alert.tip("参数代码不能为空！");
//			return false;
//		}
//		if(data.code.length>30){
//			parent.Alert.tip("参数代码不能超过30个字符！");
//			return false;
//		}
//		if(!isReCode(data.code)){
//			parent.Alert.tip("参数代码不能重复，请修改后重新提交！");
//			return false;
//		}
//		if(isEmpty(data.paramNum)){
//			parent.Alert.tip("参数编码不能为空！");
//			return false;
//		}
//		if(!telephoneReg.test(data.telephone)){
//			parent.Alert.tip("联系方式不正确！");
//			return false;
//		}
		else{
		parent.Alert.tip("未获得表单数据！");
		return false;
	}
	return true;
}
/**
 * 判断字符串是否为空
 * @param param
 * @returns {Boolean}
 */
function isEmpty(param){
	if(param!=null&&$.trim(param)!=""){
		return false;
	}else{
		return true;
	}
	
}

function isReCode(code){
	var flag = false;
//	$.ajax({url:basePath+"rest/deivceParam/getDeviceParamByCode",
//		   dataType:"json",
//		   data:{"code":code,deviceId:getParam("id")},
//		   type:"POST",
//		   async:false,
//		   success:function (data){
//			   if(data.success=="false"){
//				   flag = true;
//			   }
//		   }
//	});
//	return flag;
}


function isReNum(num){
	var flag = false;
//	$.ajax({url:basePath+"rest/deivceParam/getByNum",
//		   dataType:"json",
//		   data:{"num":num,deviceId:getParam("id")},
//		   type:"POST",
//		   async:false,
//		   success:function (data){
//			   if(data.success=="false"){
//				   flag = true;
//			   }
//		   }
//	});
//	return flag;
}

function isReName(name){
	var flag = false;
	$.ajax({url:basePath+"orguser/findUserByLoginName",
		dataType:"json",
		data:{"login_name":name,org_id:getParam("id")},
		type:"POST",
		async:false,
		success:function (data){
			if(data.success=="true"){
				flag = true;
			}
		}
	});
	return flag;
}

/*Ext.define('query.view.systemConfig.org.UserUpdate', {
	extend : 'Ext.window.Window',
	alias : 'widget.UserUpdate',
	initComponent : function() {
		var store=Ext.create("query.store.DictComStore");
		store.proxy.extraParams.dict_id=9;
		Ext.apply(Ext.form.field.VTypes, {
		    telephone: function(val) {
		        return /(^1[3|5|8][0-9]{8}\d$)|(^[0-9]{7,8}$)|(^[0-9]{3,4}\-[0-9]{7,8}$)/.test(val);
		    }
		});
		Ext.apply(Ext.form.field.VTypes, {
			nocnText: function(val) {
		        return !/[\u4E00-\u9FA5]/i.test(val);
		    }
		});
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
		var individual = {
				xtype : 'fieldset',
				defaults : {
					anchor : '100%'
				},
				title:'基本信息',
				collapsed : false,
				items : [{
					xtype : 'container',
					layout : 'anchor',
					defaultType : 'textfield',
					items : [ {
						fieldLabel : '登录名称',
						readOnly:true,
						disabled:true,
						name : 'login_name',
						id : "login_user_name",
						anchor : '95%',
						listeners:{
							blur:function(e){
								Ajax.send('orguser/findUserByLoginName.edq',{
									org_id:Ext.getCmp('org_id').getValue(),
									login_name: e.value
						        }, function (json,opts) {
						        	if(json.users.length>0){
						        		Ext.example.msg('系统提示', "用户登录名称不能重复!");
						        		Ext.getCmp('login_user_name').focus();
						        	}
						        });
							}
						}
					},{
						fieldLabel : '用户名称',
						name : 'user_name',
						maxLength:16,
						anchor : '95%',
						afterLabelTextTpl: required,
						allowBlank:false
					},{
						fieldLabel : '联系方式',
						name : 'telephone',
						vtype:'telephone',
						vtypeText:'请输入正确 的手机号码或座机号码',
						anchor : '95%'
					},{
						fieldLabel : '用户单位',
						name : 'danwei',
						maxLength:50,
						anchor : '95%'
					},{
						fieldLabel : '用户部门',
						name : 'bumen',
						maxLength:50,
						anchor : '95%'
					},{
						//xtype:'combobox',
						fieldLabel : '用户职位',
						//store:store,
						name : 'zw',
						maxLength:50,
						//editable: false,
						//displayField: 'type_name',
					    //valueField: 'dict_id',
						anchor : '95%'
					},
					{
						xtype:'datefield',
						fieldLabel : '截止日期',
						format:'Y-m-d',
						name : 'end_time',
						anchor : '95%',
						afterLabelTextTpl:required,
						allowBlank:false,
						validator:function(value){
							if(value<=Ext.Date.format(new Date(),'Y-m-d')){
								return '截止时间不能小于等于当前时间';
							}else{
								return true;
							}
						}
					},
					{xtype:'textfield',hidden:true,name:'user_id'}]
				}]
			};
		var form = Ext.widget('form', {
			border:false,
			fieldDefaults : {
				labelWidth : 70
			},
			width : 410,
			bodyPadding : 10,
			items : [individual],
			buttons : [{
				text : '保存',
				action : 'save'
			}, {
				text : '关闭',
				action : 'close'
			} ]
		});
		Ext.apply(this, {
			title:'[&nbsp;<font color=red>修改</font>&nbsp;]机构用户信息',
			closable : true,
			layout : 'fit',
			modal : true,
			constrain : true,
			items : form
		});
		this.callParent(arguments);
	}
});*/