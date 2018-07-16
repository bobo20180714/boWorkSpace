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
<title>订单查看</title>
<jsp:include page="/ligerUI.jsp" />
		<jsp:include page="/css.jsp" />
<script src="js/order_view.js" type="text/javascript"></script>
<script type="text/javascript">
			var orderId = '<%=request.getParameter("orderId")%>';
	</script>
</head>
<body style="overflow: hidden;">
	<form style="margin-left:20px;margin-top: 20px;" name="orderForm" id="orderForm"></form>
	<form style="margin-left:20px;margin-top: -20px;" name="getDataForm" id="getDataForm"></form>
	<form style="margin-left:20px;margin-top: -20px;" name="computForm" id="computForm"></form>
	<form style="margin-left:20px;margin-top: -20px;" name="resultForm" id="resultForm"></form>
</body>

</html>
