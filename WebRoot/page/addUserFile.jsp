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
<title>新建自定义文件</title>
<jsp:include page="/ligerUI.jsp" />
<script src="js/addUserFile.js" type="text/javascript"></script>
<script type="text/javascript">
	var ownerId = '<%=request.getParameter("ownerId") %>';
</script>
</head>
<body style="overflow: hidden;">
	<form style="margin-left:10px;margin-top: 20px;" name="floadForm" id="floadForm"></form>
</body>

</html>
