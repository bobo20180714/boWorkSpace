<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>新增组织机构</title> 
	<jsp:include page="/ligerUI.jsp" />
    <script src="<%=basePath%>lib/json2.js" type="text/javascript"></script>
	<script src="<%=basePath%>lib/tools.js" type="text/javascript"></script>  
	<script src="<%=basePath%>lib/LG.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/uuid.js" type="text/javascript"></script>
	<script src="js/organization_add.js" type="text/javascript"></script>
	<script type="text/javascript">
			var basePath="<%=basePath%>";
			var pk_id = "<%=request.getParameter("pk_id")%>";
			var company_id = "<%=request.getParameter("company_id")%>";
	</script>
</head>
<body>
   		<form name="tableForm" method="post" id="tableForm" style="margin-left: 30px;margin-top: 5px;">
    	</form>
</body>
</html>