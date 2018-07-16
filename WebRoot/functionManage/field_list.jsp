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
		<title>字段信息管理</title>
		<jsp:include page="/ligerUI.jsp" />
		<jsp:include page="/css.jsp" />
		<script src="js/field_list.js" type="text/javascript"></script>
		<style type="text/css">
			.l-listbox{
				border:0px;
			}
		</style>
	</head>
	<body>
   		<div id="fieldbar"></div> 
	    <div id="fieldGrid" style="margin:0; padding:0"></div> 
	</body>

</html>
