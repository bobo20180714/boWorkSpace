<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/ligerUI.jsp" />
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<%=basePath%>bassearch/css/style.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>bassearch/top/css/style.css" type="text/css" />
    <script type="text/javascript">
$(function(){
    $("#layout1").ligerLayout({
        minLeftWidth:80,
        minRightWidth:80
    });
          
	});

    </script> 

</head>
  <body >
<body style="padding:10px">
        <div id="layout1">
            <div position="center" >
            <h4>加载页面 </h4>
		<div position="top" >
           <img alt="" src=""> 
        </div> 
</div>
 <%-- <%@include file="top/index.jsp" %> --%>
  <jsp:include page="top/index.jsp" />    
  <%-- <jsp:include page="../bassearch/MenuButton.jsp" /> --%>
</body>
</html>
