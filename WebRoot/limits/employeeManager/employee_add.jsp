<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.print("basePath=="+basePath);
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>员工信息新增页</title>
	<jsp:include page="/ligerUI.jsp" />
	<script type="text/javascript" src="js/employee_add.js"></script>
	<script src="<%=basePath%>limits/employeeManager/js/jquery.form.js" type="text/javascript"></script>
</head>
<body>
	<form name="form1" method="post" id="form1" style="margin-left: 20px;margin-top: 20px;">
	<div style="border:#AECAF0 solid 1px;width:100px;height:110px;left:430px;margin-top:45px;position:absolute" onclick=selectPhoto();>
			<img src="js/default.jpg" width="100px" height="110px" alt="" id="img"/>
		</div>
	</form>
	<div id="appendDiv" style="display:none; margin-left: 70px;margin-top: 40px;">
		<form id="form2" action="<%=basePath%>rest/upload/fileUpload"  method="post" enctype="multipart/form-data">
			<input type="file" style="width:200px;" id="fileupload" name="file"/>
		</form>
	</div>
</body>
</html>