<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>用户信息列表页</title>
	<jsp:include page="/ligerUI.jsp" />
	<script src="js/user_info.js" type="text/javascript"></script>
	</head>
	<body>
		<div class="l-panel-search-box">
			<form id="form1" > 
					<div class="l-panel-search-box-item">
				      		<label for="account">用户账号：</label><input type="text" ltype="text" id="account" name="account"   class="field l-text-field" />
				    </div>
				    
			        <div class="l-panel-search-box-item" >
				        	<label for="name">用户名称：</label><input type="text" ltype="text" id="name" name="name" class="field l-text-field" />
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
		<div class="l-loading" style="display: block" id="pageloading"></div>
	</body>
</html>
