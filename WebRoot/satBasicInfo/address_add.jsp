<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>新增组播地址</title> 
	<jsp:include page="/ligerUI.jsp" />
	<script src="js/address_add.js" type="text/javascript"></script>
		<script type="text/javascript">
			var satId = '<%=request.getParameter("satId")%>';
		</script>
</head>
<body>
   		<form name="form1" method="post" id="form1" style="margin-left: 40px;">

    	</form>
</body>
</html>