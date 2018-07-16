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
		<title>修改机构用户信息</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="<%=basePath%>admin/systemConfig/org/js/UserUpdate.js" type="text/javascript"></script>
		<script type="text/javascript">
			var user_id = '<%=request.getParameter("id")%>';
		</script>
	</head>
	<body>
	<div>
		<form id="form3" style="margin-left: 20px;margin-top: 20px"></form>
	
	</div>
	
	</body>

</html>
