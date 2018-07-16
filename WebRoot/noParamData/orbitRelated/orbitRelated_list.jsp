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
		<title>航天器相关信息管理</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="js/orbitRelated_list.js" type="text/javascript"></script>
		<style type="text/css">
			.l-panel-search-box{
				padding:0px;
			}
		</style>
	</head>
	<body>
	 	<!-- <div id="layout1" style="overflow: hidden;width: 100%;">
		  	 <div position="left" title="【航天器】信息">
			    <div id ="scollDivTree">
			      <div id="tree1"></div>
			    </div>
	  		 </div>
			 <div position="center" >
				<div id="relatedtoolbar"></div> 
			    <div id="relatedGrid" style="margin:0; padding:0"></div> 
				<div id="fieldtoolbar"></div> 
			    <div id="fieldGrid" style="margin:0; padding:0"></div>
			</div>  
		</div> -->	
		<div id="relatedtoolbar"></div> 
	    <div id="relatedGrid" style="margin:0; padding:0"></div> 
		<div id="fieldtoolbar"></div> 
	    <div id="fieldGrid" style="margin:0; padding:0"></div>
	</body>
</html>
