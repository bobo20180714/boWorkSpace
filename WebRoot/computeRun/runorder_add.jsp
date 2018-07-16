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
<title>订单创建</title>
<jsp:include page="/ligerUI.jsp" />
<jsp:include page="/css.jsp" />
<script src="js/runorder_add.js" type="text/javascript"></script>
<script src="<%=basePath %>mainPage/js/Base64.js" type="text/javascript"></script>
<script src="<%=basePath %>resources/js/jquery-form/jquery.form.js" type="text/javascript"></script>
<style type="text/css">
	.input {
		width: 175px;
	}
</style>
<script type="text/javascript">
	var typeId = '<%=request.getParameter("typeId")%>';
	var computeId = '<%=request.getParameter("computeId")%>';
	var satMid = '<%=request.getParameter("satMid")%>';
	var satId = '<%=request.getParameter("satId")%>';
	var satName = '<%=request.getParameter("satName")%>';
</script>
</head>
	<body style="overflow: auto;">
		<form style="margin-left:20px;margin-top: 20px;" name="orderForm" id="orderForm"></form>
		<form style="margin-left:20px;margin-top: 5px;" name="paramForm" id="paramForm"></form>
	</div>
	<form id="hideForm" name="hideForm" method="post" style="display: none;">
		<input name="orderName" id="orderName" type="hidden"/>
		<input name="orderContent" id="orderContent"  type="hidden"/>
		<input name="time" id="time" type="hidden"/>
		<input name="computeCount" id="computeCount" type="hidden"/>
		
		
		<input name="order_class" id="order_class" type="hidden"/>
		<input name="loop_space" id="loop_space" type="hidden"/>
		<input name="loop_maxnum" id="loop_maxnum" type="hidden"/>
		<input name="loop_endtime" id="loop_endtime" type="hidden"/>
		
		<input name="overTime" id="overTime" type="hidden"/>
		<input name="computId" id="computId" type="hidden"/>
		<input name="satId" id="satId" type="hidden"/>
		<input name="computTypeId" id="computTypeId" type="hidden"/>
		<input name="computParam" id="computParam" type="hidden"/>
	</form>
</body>

</html>
