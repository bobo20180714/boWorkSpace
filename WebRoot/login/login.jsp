<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=basePath%>login/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
<meta http-equiv="x-ua-compatible" content="ie=7" />

<script src="<%=basePath%>lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script> 
<script src="<%=basePath%>lib/json2.js" type="text/javascript"></script>
<script src="<%=basePath%>lib/Alert.js" type="text/javascript"></script>
<script src="<%=basePath%>lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=basePath%>lib/ligerUI/js/core/inject.js" type="text/javascript"></script>

<script src="<%=basePath%>lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="<%=basePath%>lib/ligerUI/js/plugins/ligerTip.js" type="text/javascript"></script>
<script type="text/javascript">
	var basePath='<%=basePath%>';
</script>
<script type="text/javascript" src="<%=basePath%>login/js/login.js"></script>
	<title>多星测控管理平台</title>
</head>
<body>
<div id="pic">
<div class="login">
	<form id="loginform" name="loginform" method="post" >
		<table > 
			<tr>
				<td align="center">
					登录账号：&nbsp; &nbsp;
				</td>		
				<td>
					<input type="text" id="name" name="name" placeholder=" " required="required">
				</td>
			</tr>
			<tr>
				<td align="center">
					登录密码：  &nbsp;&nbsp;
				</td>		
				<td>
					<input type="password"  id="password" name="password" placeholder=" " required="required">
				</td>
			</tr>
		<!-- 	<tr>
				<td align="center">
					选择语言： &nbsp;&nbsp;
				</td>		
				<td>
					<select>
						<option value="cn">中文简体</option>
						<option value="en">English</option>
					</select>
				</td>
			</tr> -->
			<tr>
				<td>&nbsp;</td>
				<td>
					<button class="btn" type="submit" value="Submit" onClick="submitForms();return false;">登录</button>
					<button class="btn" id="btn2" type="reset" value="Reset">重置</button>
				</td>
			</tr>
		</table>
		
	</form>

</div>

</div>
<div id="com">Copyright © 2016 航天恒星科技有限公司 (503所)&nbsp Space Star Technology Co.,Ltd </div>
</body>
</html>