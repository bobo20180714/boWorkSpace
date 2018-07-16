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
		<title>新增组织机构节点信息</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="<%=basePath%>admin/systemConfig/org/js/OrgAdd.js" type="text/javascript"></script>
	</head>
	<body>
	<div >
		<form id="form1" style="margin-left: 20px;margin-top: 20px">
			<input id="org_id" name="org_id" type="hidden" />
		</form>
	</div>

	</body>
</html>
