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
		<title>导入页面</title>
		<jsp:include page="/ligerUI.jsp" />
		<script type="text/javascript">
			var org_id = '<%=request.getParameter("org_id")%>';
		</script>
		<script src="<%=basePath%>admin/js/ajaxfileupload.js"
			type="text/javascript"></script>
		<script src="<%=basePath%>admin/systemConfig/org/js/importFile.js" type="text/javascript"></script>
	</head>
	<body>
	<div style="margin-top: 20px">
		<table align="center">
			<tr style="height: 10px;">
			</tr>
			<form name="imgFile" id="imgFile" method="post"
				action="<%=basePath%>rest/upload/fileUpload"
				enctype="multipart/form-data">

				<tr style="line-height: 20px;">
					<td valign="top" align="right">导入文件：</td>
					<td style="width:90px;">
						<div style="width:90%;height: 30px;">
							<input disabled="disabled" id="fileName1" />
						</div>
					</td>
					<td>
						<div style="height:30px;">
							<span style="color: red">* </span> <input
								style="border:0px #ffffff solid;position:absolute;padding-left:9px;padding-top:0;width:60px;height:22px;z-index:10;opacity:0;filter:alpha(opacity=0);"
								type="file" id="import_file" class="business_license_accessory"
								name="file" onchange="picpathchange()" /> <input type="button"
								style="width:60px; height:22px;position:relative; border:1px #C9C9C9 solid; background:#ffffff;"
								value="浏览..." />
						</div>
					</td>
				</tr>

			</form>
		</table>
	</div>
	<div id="wait_div" style="width:260px; height:160px;  display: none;">

	</div>
</body>
</html>
