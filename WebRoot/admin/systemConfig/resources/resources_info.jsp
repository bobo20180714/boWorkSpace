<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>资源信息管理</title>
	<jsp:include page="/ligerUI.jsp" />
	<script src="js/resources_info.js" type="text/javascript"></script>
	<link href="<%=basePath%>Aqua/css/ligerui-tree.css" rel="stylesheet" type="text/css" id="mylink"/>
	<script src="<%=basePath%>lib/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
	<script type="text/javascript">
        
    </script>
    <style type="text/css">
			.l-form {
			  margin: 0px;
			}
		</style>
</head>
<body>
<div id="layout1" style="overflow:hidden;">
		  <div position="left" title="资源数据">
		    <div id ="scollDivTree" class="l-tree-body">
		      <div id="tree1"></div>
		    </div>
		  </div>
		 <div position="center">
		 	<!-- <div  class="l-panel-search-box">
					<form id="form1" style="height:30px;" > 
						<div class="l-panel-search-box-item" >
			        	<label for="paramType">资源名称：</label><input type="text" ltype="text" id="resName" name="resName" class="field l-text-field" />
					    </div>
					    <div class="l-panel-search-box-item" >
					        	<label for="paramVal">状态：</label><input type="text" ltype="text" id="state" name="state" class="field l-text-field" />
					    </div>
					    <div class="l-panel-search-item-btn">
							<div class="l-panel-search-item"><div id="searchbtn" >查询</div></div>
	    					<div class="l-panel-search-item"><div id="resetbtn" >重置</div></div>
						</div>
					</form>
			</div> -->
			<div style="height: 30px; background-color: var(--grid-search-bg-color)">
				<form id="form1" style="margin-left: 10px;float:left;height: 30px"></form>
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
