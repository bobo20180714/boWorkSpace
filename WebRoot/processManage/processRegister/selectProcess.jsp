<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>选择进程</title> 
	<jsp:include page="/ligerUI.jsp" />
	<jsp:include page="/css.jsp" />
	<script src="js/selectProcess.js" type="text/javascript"></script>
	<script type="text/javascript">
		var processType = '<%=request.getParameter("processType") %>';
	</script>
</head>
<body>
	    <div id="processGrid"></div>
</body>
</html>