<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>卫星详细信息</title> 
	<jsp:include page="/ligerUI.jsp" />
	<script src="js/CheckDetails.js"
			type="text/javascript"></script>
</head>
<body>
   		<form name="form3" method="post" id="form3" style="margin-left: 30px;margin-top: 10px;">

    	</form>
</body>
</html>