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
		<title>页面关联文件列表功能</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="js/sat_relate_file_list.js" type="text/javascript"></script>
	 <style type="text/css">
			.l-form {
			  margin: 0px;
			}
	  </style>
	</head>
	<body oncontextmenu="return false" >

 	<div id="layout1" style="overflow: hidden;">
		  <div position="left">
		    <div id ="scollDivTree" style="height:100%;overflow: auto;"  class="l-tree-body">
		      <div id="tree1"></div>
		    </div>
		  </div>
		 <div position="center" >
			<div id="toolbar"></div> 
		    <div id="filegrid" style="margin:0; padding:0">
		    </div>
		</div>  
	</body>

</html>
