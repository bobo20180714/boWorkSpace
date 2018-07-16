<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>欢迎页面</title>
	<jsp:include page="/ligerUI.jsp" />
<jsp:include page="/css.jsp" />
	<link href="<%=basePath%>lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body{
		margin: 0;
		}
		.out_a1{
			padding:0;
			margin:0;
			width:100%;
			height: 100%;
			overflow: auto;
		}
		.wel_a1{
		width: 654px; 
		height: 264px;
		margin: 160px auto;
		<%-- background:url('<%=basePath%>images/main.png') repeat-x center; --%>
		color:#024ab8; 
		font-family: "隶书", Arial, sans-serif; 
		font-size: 34px;
		}
	</style>
</head>
<body>
	<div class="out_a1">
		<div class="wel_a1"></div>
	</div>
</body>
</html>
