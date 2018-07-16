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
		<title>卫星管理</title>
		<jsp:include page="/ligerUI.jsp" />
		
		<style type="text/css">
			.l-form {
			  margin: 0px;
			}
		</style>
		<script src="js/SatBasicInfoIndex.js" type="text/javascript"></script>
	</head>
	<body >
		<div style="height: 30px; background-color: var(--grid-search-bg-color)">
			<form id="form2" style="margin-left: 10px;float:left;height: 30px"></form>
			<div style="padding-top: 3px;" >
				<div class="l-panel-search-item">
					<div id="searchbtn"></div>
				</div>
			
				<div class="l-panel-search-item">
					<div id="resetbtn"></div>
				</div>
			</div>
		</div>
		<div id="toptoolbar"></div> 
		<div id="maingrid" style="margin: 0; padding: 0px;overflow: hidden;"></div>
	</body>

</html>
