<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String pagePath = request.getServletPath().substring(1);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户信息列表页面</title>
	<jsp:include page="/ligerUI.jsp" />
	<script type="text/javascript" src="js/employee_info.js"></script>
	<script type="text/javascript">
    	var pagePath = '<%=pagePath%>';
    </script>
</head>
<body>
	<div class="l-panel-search-box">
		<form id="form1">
					<div class="l-panel-search-box-item" >
				        	<label for="staff_code">员工工号：</label><input type="text" ltype="text" id="staffCode" name="staffCode" class="field l-text-field" />
				    </div>
					<div class="l-panel-search-box-item" >
				        	<label for="staff_name">员工姓名：</label><input type="text" ltype="text" id="staffName" name="staffName" class="field l-text-field" />
				    </div>
		</form>
	
		<div class="l-panel-search-item-btn">
				<div class="l-panel-search-item"><div id="searchbtn" >查询</div></div>
		    	<div class="l-panel-search-item"><div id="resetbtn" >重置</div></div>
		</div>
	</div>
	<div id="topmenu"></div>
	<div id="toptoolbar"></div>
	<div id="maingrid" style="margin: 0; padding: 0;"></div>
</body>
</html>
