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
<title>列表数据</title>
<jsp:include page="/ligerUI.jsp" />
<script src="js/data_list.js" type="text/javascript"></script>
	<script src="js/Exporter2Excel-all.js" type="text/javascript"></script>
	<script src="js/Base64.js" type="text/javascript"></script>
<script type="text/javascript">
	var sql = new Base64().decode('<%=request.getParameter("sql") %>');
	var startTime = '<%=request.getParameter("startTime") %>';
	var endTime = '<%=request.getParameter("endTime") %>';
	var relateId = '<%=request.getParameter("relateId") %>';
</script>
</head>
<body style="overflow: hidden;">
	<div id="toptoolbar"></div> 
	<div id="dataGrid" style="margin: 0; padding: 0px;overflow: hidden;"></div>
</body>

</html>
