<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>[修改]单机信息</title> 
	<jsp:include page="/ligerUI.jsp" />
	<script src="js/StandAloneInfoUpdate.js"
			type="text/javascript"></script>
</head>
<body>

		<form id="form2" style="margin-left: 20px;margin-top: 20px">
			<input id="stand_alone_id" name="stand_alone_id" type="hidden" />
		</form>
		
   	
</body>
</html>