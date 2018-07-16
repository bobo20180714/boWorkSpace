<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>修改密码</title>
		<jsp:include page="/ligerUI.jsp" />
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	var form = null;
	$(function() {
		form = $("#form1").ligerForm({
			inputWidth: 160, 
       		labelWidth: 80, 
       		space: 20,
       		labelAlign:"right",
            validate:true,
            fields: [
             { type: "password", display: "原密码",id:"old_pwd", name: "old_pwd",validate: {required: true}},
             { type: "password" ,display: "新密码",id:"new_pwd", name: "new_pwd",validate: {required: true,minlength:5,maxlength:30}},
             { type: "password" ,display: "确认新密码",id:"new_pwd_confirm", name: "new_pwd_confirm",validate: {required: true}}
		]});
		$("#userAccount").val("${sessionScope.userAccount}");
	});
    function submitForm(){
		if (form.valid()) {
			//账号
		    var userAccount = $("#userAccount").val();
		    //旧密码
		    var oldPwd = liger.get("old_pwd").getValue();
		    //新密码
		    var new_pwd = liger.get("new_pwd").getValue();
		    if (new_pwd != liger.get("new_pwd_confirm").getValue()) {
				parent.pwdWin.Alert.tip("两次密码输入不一致！");
				return;
			}
			$.ajax({
				url:basePath+"rest/UserAction/updatePwd",
		  		data:{
		  			userAccount:userAccount,
		  			oldPwd:oldPwd,
		  			new_pwd:new_pwd
		  		},
		  		async : false,
		  		success:function(data){
		  			var jsobj = eval('('+data+')');
					parent.pwdWin.Alert.tip(jsobj.message);
					if(jsobj.success == "true"){
		  				parent.pwdWin.changePwd.close();
					}
		  		}
			});
		}
    }
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
    <form name="form1" method="post" id="form1" style="margin-left:50px;margin-top:20px;"></form>
    <input type="hidden" id="userAccount"  name="userAccount"/>
</body>
</html>
