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
		<title>修改参数信息</title>
		<jsp:include page="/ligerUI.jsp" />
		<jsp:include page="/css.jsp" />
		<script src="js/param_update.js" type="text/javascript"></script>
		<style type="text/css">
			.l-textarea{
				border:1px solid #AECAF0;
			}
		</style>
		<script type="text/javascript">
			var fieldId = '<%=request.getParameter("fieldId")%>';
		</script>
	</head>
	<body>
		<form style="margin-top: 30px;margin-left:20px; " name="updateParam" id="updateParam"/>
	</body>

</html>
