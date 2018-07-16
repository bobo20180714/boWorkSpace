<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title>选择非遥测数据</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="js/selectNoParam.js" type="text/javascript"></script>
		<style type="text/css">
			.l-form {
			    margin: 0px;
			}
		</style>
		<script type="text/javascript">
			var satId = '<%=request.getParameter("satId")%>';
	</script>
	</head>
	<body oncontextmenu="return false" >
		 	 <div  style="height: 60px;background-color: var(--grid-search-bg-color);">
			 		<form id="form1" style="margin-left: 10px;float:left;height: 60px"></form>
			 		<div style="padding-top: 3px;" >
						<div class="l-panel-search-item">
							<div id="searchbtn"></div>
						</div>
					
						<div class="l-panel-search-item">
							<div id="resetbtn"></div>
						</div>
					</div>
			 </div>
			 <div id="grid">
			    <div id="dataGrid" style="margin:0; padding:0">
			 </div>
		    </div>
	</body>

</html>
