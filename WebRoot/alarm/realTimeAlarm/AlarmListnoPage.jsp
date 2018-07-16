<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>实时报警页</title>
	<jsp:include page="/ligerUI.jsp" />
	<script src="js/AlarmListnoPage.js" type="text/javascript"></script>
	</head>
	<body>
	<div>
		 北斗1号02星
   	</div>
		<div id="topmenu"></div>
		<div id="toptoolbar"></div>
		<div id="maingrid" style="margin: 0; padding: 0;"></div>
		<div class="l-loading" style="display: block" id="pageloading"></div>
	</body>
</html>
