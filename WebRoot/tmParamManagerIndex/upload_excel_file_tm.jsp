<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<jsp:include page="/ligerUI.jsp" />
	<script type="text/javascript" src="js/upload_excel_file_tm.js"></script>
	<script type="text/javascript" src="<%=basePath %>resources/js/jquery-form/jquery.form.js"></script>
	
	<style type="text/css">
		#pageloading{position:absolute; left:0px; top:0px; background:white url('<%=basePath%>lib/ligerUI/skins/Tab/images/ui/loading.gif') no-repeat center; width:100%; height:100%;z-index:99999;}
	</style>
	</head>
	<body>
		<form id="form1" style="margin-left:30px;margin-top: 30px;" action="<%=basePath%>rest/baseConfig/uploadFile"  method="post" enctype="multipart/form-data">
			<table>
				<tr  style="line-height: 35px;">
	  		 		<td><span>选择文件:</span></td>
	  		 		<td style="width:148px;">
	  		 			<div style="width:100%;height: 20px;">
		  		 			<input style="width:148px;" disabled="true" id="fileName"/>
	  		 			</div>
	  		 		</td>
	  		 		<td>
	  		 			<div style="height:36px;">
	  		 				<span style="color: red">* </span>
	  		 				<input style="border:0px #ffffff solid;position:absolute;padding-left:9px;padding-top:0;width:60px;height:22px;z-index:10;opacity:0;filter:alpha(opacity=0);" 
	  		 					type="file" id="excel_file" name="file" onchange="valiFile()"/>
	  		 				<input type="button" style="width:60px; height:22px;position:relative; border:1px #C9C9C9 solid; background:#ffffff;" value="浏览..."/>
	  		 			</div>
	  		 		</td>
	  		 	</tr>
	  		 </table>
  		 </from>
  		 <div id="pageloading" style="display: none;" ></div>
	</body>
</html>
