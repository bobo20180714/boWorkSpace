<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title></title> 
	<jsp:include page="/ligerUI.jsp" />
	<script type="text/javascript" src="<%=basePath %>relateQuery/selectCondition/js/queryResultGrid.js"></script>
	<script type="text/javascript">
		var basePath = '<%=basePath %>';
	</script>
</head>
<body>
	<div id="queryResultGrid">
</body>
</html>