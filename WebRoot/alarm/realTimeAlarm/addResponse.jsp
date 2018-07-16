<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>添加响应意见</title>
	<jsp:include page="/ligerUI.jsp" />
	<script src="js/addResponse.js" type="text/javascript"></script>
	<script  type="text/javascript">
	<%
		String deviceid="";
		String tmid="";
		if(request.getParameter("deviceid")!=null){
			deviceid=(String)request.getParameter("deviceid");
		}
		if(request.getParameter("tmid")!=null){
			tmid=(String)request.getParameter("tmid");
		}
	%>
	var deviceid='<%=deviceid%>';
	var tmid='<%=tmid%>';
	</script>
	</head>
	<body>
 		<form name="form1" method="post" id="form1"></form>
	</body>
</html>
