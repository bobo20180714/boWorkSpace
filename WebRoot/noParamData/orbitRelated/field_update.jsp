<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>修改字段信息</title> 
	<jsp:include page="/ligerUI.jsp" />
	<script src="js/field_update.js" type="text/javascript"></script>
	<script type="text/javascript">
			var jsjg_id = "<%=request.getParameter("jsjg_id")%>";
			var field_id = "<%=request.getParameter("field_id")%>";
	</script>
</head>
<body>
	<form  id="form1" style="margin-left: 20px;margin-top: 20px">
		
 	</form>
</body>
</html>