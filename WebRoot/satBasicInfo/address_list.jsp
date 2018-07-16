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
		<title>组播地址管理</title>
		<jsp:include page="/ligerUI.jsp" />
		<style type="text/css">
			.l-form {
			  margin: 0px;
			}
		</style>
		<script src="js/address_list.js" type="text/javascript"></script>
		<script type="text/javascript">
			var satId = '<%=request.getParameter("satId")%>';
		</script>
	</head>
	<body style="overflow: hidden;">
		<div id="toptoolbar"></div> 
		<div id="maingrid" style="margin: 0; padding: 0px;overflow: hidden;"></div>
	</body>

</html>
