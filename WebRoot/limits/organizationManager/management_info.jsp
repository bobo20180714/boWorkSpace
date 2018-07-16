<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String staffCode="sys";
 %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String pagePath = request.getServletPath().substring(1);
	
%>
    <title>部门管理</title>
    <jsp:include page="/ligerUI.jsp" />
	<script src="js/management_info.js" type="text/javascript"></script> 
</head>
<body>

	<div id="layout1" style="overflow: hidden;">
		  <div position="left"  title="组织机构数据(鼠标右键操作)">
		    <div id ="scollDivTree" style="overflow: scroll;">
		      <div id="tree1"></div>
		    </div>
		  </div>
		  <div position="center" >
					<form id="form1"  > 
					</form>
				<div id="toptoolbar"></div> 
			    <div id="maingrid" style="margin:0; padding:0"></div>
		</div>
	</div>
</body>
</html>
