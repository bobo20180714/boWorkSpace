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
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>服务器新增</title>
<jsp:include page="/ligerUI.jsp" />
<jsp:include page="/css.jsp" />
<script src="js/compute_add.js" type="text/javascript"></script>
</head>
<body style="overflow: hidden;">
	<form style="margin-left:20px;margin-top: 20px;" name="processForm" id="processForm"></form>
</body>

</html>
