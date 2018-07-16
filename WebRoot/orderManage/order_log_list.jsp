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
		<title>订单日子管理</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="js/order_log_list.js" type="text/javascript"></script>
	</head>
	<body>
			 	<div class="l-panel-search-box">
				    <form id="form1" > 
				    <table style="margin-left: 30px;">
				    	<tr>
				    		<td width="70px">
				    			订单编号：
				    		</td>
				    		<td width="200px">
				    			<input type="text" ltype="text" id="orderId" name="orderId" class="field" />
				    		</td>
				    		<td width="70px">
				    			时间区间：
				    		</td>
				    		<td width="153px">
				    			<input type="text"  id="startTime" name="startTime" class="field" />
				    		</td>
				    		<td width="10px">
				    			-
				    		</td>
				    		<td width="200px">
				    			<input type="text"  id="endTime" name="endTime" class="field" />
				    		</td>
				    		<td width="80px">
				    			<div id="searchbtn" >查询</div>
				    		</td>
				    		<td>
				    			<div id="resetbtn" >重置</div>
				    		</td>
				    	</tr>
				    </table>
					</form>			
	   		</div>
		    <div id="logGrid" style="margin:0; padding:0"></div> 
	</body>

</html>
