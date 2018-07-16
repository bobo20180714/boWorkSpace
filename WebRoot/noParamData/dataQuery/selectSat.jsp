<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>选择卫星</title> 
	<jsp:include page="/ligerUI.jsp" />
	<script src="js/selectSat.js" type="text/javascript"></script>
	<script type="text/javascript">
		var basePath = '<%=basePath %>';
	</script>
	 <style type="text/css">
			.l-form {
			  margin: 0px;
			}
	  </style>
</head>
<body>
		<div style="height: 30px; background-color: var(--grid-search-bg-color)">
			<form id="form1" style="margin-left: 10px;float:left;height: 30px"></form>
			<div style="padding-top: 3px;" >
					<div class="l-panel-search-item">
						<div id="searchbtn"></div>
					</div>
				
					<div class="l-panel-search-item">
						<div id="resetbtn"></div>
					</div>
				</div>
		</div>	
	    <div id="satGrid"></div>
</body>
</html>