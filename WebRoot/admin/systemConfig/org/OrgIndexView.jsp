<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.xpoplarsoft.framework.parameter.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	 
	 //超级管理员账号
	 String managerAccount = SystemParameter.getInstance().getParameter("systemAccount");
	 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<title>机构用户管理</title>
<script type="text/javascript">
			var managerAccount = '<%=managerAccount%>';
	</script>
<jsp:include page="/ligerUI.jsp" />
<link href="<%=basePath%>Aqua/css/ligerui-tree.css" rel="stylesheet" type="text/css" id="mylink"/>
<script src="<%=basePath%>lib/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
<script src="<%=basePath%>admin/systemConfig/org/js/OrgIndexView.js"
	type="text/javascript"></script>
<style type="text/css">
		.l-form {
		  margin: 0px;
		}
	</style>
</head>
<body  style="overflow: hidden;">
	<div id="layout1" style="overflow: hidden;">
		<div position="left" title="[组织机构]信息">
			<div id="scollDivTree" class="l-tree-body">
				<div id="tree1"></div>
			</div>
		</div>
		<div position="center">
			<!-- <table width="100%">
				<tr>
					<td>
						<form id="form2">
							<input id="org_id" name="org_id" type="hidden" />
						</form>
						<div style="padding-top: 9px;">
							<div class="l-panel-search-item" style="width: 80px;">
								<a id="" href=""> </a>
								<div id="searchbtn" style="width: 35px;">查询</div>
							</div>

							<div class="l-panel-search-item" style="width: 45px;">
								<div id="resetbtn" style="width: 35px;">重置</div>
							</div>
						</div>
					</td>
				</tr>
			</table> -->
			<div style="height: 30px; background-color: var(--grid-search-bg-color)">
				<form id="form2" style="margin-left: 10px;float:left;height: 30px"></form>
				<div style="padding-top: 3px;" >
					<div class="l-panel-search-item">
						<div id="searchbtn"></div>
					</div>
				
					<div class="l-panel-search-item">
						<div id="resetbtn"></div>
					</div>
				</div>
			</div>
			<div id="toptoolbar"></div>
			<div id="maingrid" style="margin:0; padding:0"></div>
		</div>
	</div>
</body>
</html>
