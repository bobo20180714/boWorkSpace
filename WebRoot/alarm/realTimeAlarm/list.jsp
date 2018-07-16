<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>实时报警页</title>
	<jsp:include page="/ligerUI.jsp" />
	<style type="text/css">
		.waitOK{
			margin-top: 2px;
			margin-left:50px;
              display: block;width:60px;
                  height: 23px;
			    overflow: hidden;
			    line-height: 23px;
			    cursor: pointer;
			    position: relative;
			    text-align: center;
			    border: solid 1px #3789C3;
			    color: #F3F7FC;
			    background: #3789C3;
         
		}
		.l-grid-row-alt .l-grid-row-cell-rownumbers{
			background-color:rgb(224, 236, 255);
		}
	</style>
	
	<script src="js/ligerToolBar.js" type="text/javascript"></script>
	<script src="js/Base64.js" type="text/javascript"></script>
	<script src="js/Exporter2Excel-all.js" type="text/javascript"></script>
	<script src="<%=basePath %>resources/js/jquery-form/jquery.form.js" type="text/javascript"></script>
	<script type="text/javascript">
		var satsid = '<%=request.getParameter("satsid")%>';
	</script>
	<script src="js/list.js" type="text/javascript"></script>
	</head>
	<body>
		<div id="toptoolbar"></div>
		<div id="maingrid" style="margin: 0; padding: 0;"></div>
		<form id="hideForm" name="hideForm" style="display: none;" method="post">
			<input type="hidden" name="reqParamsStr" id="reqParamsStr"/>
		</form>
	</body>
</html>
