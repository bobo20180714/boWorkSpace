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
		<title>修改用户机构信息</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="<%=basePath%>admin/systemConfig/org/js/OrgChose.js" type="text/javascript"></script>
	</head>
	<body>
	<div>
		<form id="form4" style="margin-left: 20px;margin-top: 20px"></form>
<!-- 	<form id="dd" method="post">
	<tr>
	<td>组织机构：</td>
	<td><select class = "easyui-combotree" url="basePath+'rest/orguser/findorgtree?parent_id=' + data.id" name="" style=""></select></td>
	</tr>
	<table>
	</table>
	</form> -->
	</div>
<!-- 	<div style="margin:20px;margin-top:20px">
		组织机构<input type="text" id="txt2" />
	</div> -->
	</body>

</html>
