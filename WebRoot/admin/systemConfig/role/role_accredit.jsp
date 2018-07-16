<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html >
<head>
<jsp:include page="/ligerUI.jsp" />
<script type="text/javascript" src="js/role_accredit.js"></script>
<link href="<%=basePath%>Aqua/css/ligerui-tree.css" rel="stylesheet" type="text/css" id="mylink"/>
<script src="<%=basePath%>lib/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
<style type="text/css">
#layout1{  width:100%;margin:0; padding:0;  } 
.overflow{overflow:scroll;height:300px;}
</style>
</head>
<body class="l-tree-body" style="padding-top: 10px;">
  <center >
	  <div>
	    	<font color="#fff">角色名称：<span id="role_name"></span></font>
	  </div>
	  <div id="roleLimitTree" style="margin-top: 10px">
	  </div>
	 <!--  <div style="margin-top: 10px">
			<input type="submit" value="确定"  id="tijiao" onclick="formSubmit()" class="l-button  " /> 
		    <input type="button" value="取消" id="cancle" class="l-button" onclick="colseWindow()"/>
	  </div> -->
  </center>
</body>
</html>
