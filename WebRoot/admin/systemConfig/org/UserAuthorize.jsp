<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>用户角色授权</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="<%=basePath%>admin/systemConfig/org/js/UserAuthorize.js" type="text/javascript"></script>
		<style type="text/css">
			.l-panel{
			border:1px solid #AECAF0;
		}
		</style>
</head>
<body style="padding:10px">
<table>
<tr>
<td style="position: top">
     <div style="margin:4px;float:left;width: 318px">
         <div id="maingrid1"></div>
     </div>
</td>
<td>
     <div style="margin:4px;float:left;" class="middle">  
	     <div id="role_assign"></div>
	     <div id="del_role" style="margin-top: 3px;"></div>
</td>
<td style="position: top;">
     <div style="margin:4px;float:left;width: 318px">
     	<div id="maingrid2"></div> 
     </div>
</td>
</tr>
</table>
</body>

</html>
