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
		<title>订单序列执行</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="js/computeRun_list.js" type="text/javascript"></script>
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
		 	 <div  style="height: 30px;background-color: var(--grid-search-bg-color);">
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
			<div id="orderbar"></div> 
		    <div id="orderGrid" style="margin:0; padding:0">
		    </div>
    		<div title="日志输出" class="l-toolbar" style="height:25px;line-height:25px;font-size:14px;padding-left: 15px;color:#2170a6;">日志输出</div>
   			<div id="logGrid" style="margin: 0; padding: 0px;overflow: hidden;"></div>
		</div>  
		<!-- <div position="centerbottom" id="tab_bottom" title="日志输出">
    		</div> -->
</div>
	</body>

</html>
