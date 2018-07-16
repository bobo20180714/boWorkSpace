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
		<title>关联控制量计算功能</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="js/relate_func_list.js" type="text/javascript"></script>
	<script src="<%=basePath %>resources/js/jquery-form/jquery.form.js" type="text/javascript"></script>
		<style type="text/css">
			.l-form {
			    margin: 0px;
			}
		</style>
	</head>
	<body oncontextmenu="return false" >

 <div id="layout1" style="overflow: hidden;">
		  <div position="left" title="计算类型">
		    <div id ="scollDivTree" style="height:100%;overflow: auto;"  class="l-tree-body">
		      <div id="tree1"></div>
		    </div>
		  </div>
		 <div position="center" >
		 	 <!-- <div  style="height: 30px;background-color: var(--grid-search-bg-color);">
			 		<form id="form1" style="margin-left: 10px;float:left;height: 30px"></form>
			 		<div style="padding-top: 3px;" >
						<div class="l-panel-search-item">
							<div id="searchbtn"></div>
						</div>
					
						<div class="l-panel-search-item">
							<div id="resetbtn"></div>
						</div>
					</div>
			 </div> -->
			 <div class="l-layout-header">请选择计算模块</div>
		    <div id="maingrid" style="margin:0; padding:0">
		    </div>
		</div>  
		
		<form method="post" id="hideForm" style="display: none;">
			<input name = "typeId" id = "typeId" type="hidden" />
			<input name = "funcListStr" id = "funcListStr" type="hidden"  />
		</form>
	</body>

</html>
