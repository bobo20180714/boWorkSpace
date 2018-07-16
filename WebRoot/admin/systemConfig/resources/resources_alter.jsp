<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html >
<head>
    
	<jsp:include page="/ligerUI.jsp" />
    <script type="text/javascript" src="js/resources_alter.js"></script>
</head>
<body>
    <form name="form1" method="post" id="form1" style="margin-left: 20px;margin-top: 20px;"></form>
</body>
</html>