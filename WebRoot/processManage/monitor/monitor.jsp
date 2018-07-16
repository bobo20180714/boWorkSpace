<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>进程监视</title>
		<jsp:include page="/ligerUI.jsp" />
		<jsp:include page="/css.jsp" />
		<script src="js/monitor.js" type="text/javascript"></script>
		<script src="<%=basePath %>resources/js/jquery-form/jquery.form.js" type="text/javascript"></script>
		<style type="text/css">
			.l-listbox{
				border:0px;
			}
		</style>
	</head>
	<body >
		<div id="layout" style="overflow: hidden;">
		  	<!-- <div position="left" title="卫星列表">
		    	<div id ="scrollDivTree" style="overflow: auto;height:700px;">
		     	 	<div id="satGroupTree"></div> 
		   		</div>
		  	</div> -->
		 	<div position="center"  id="tab_center">
				<div id="toptoolbar"></div> 
				<div id="processGrid" style="margin: 0; padding: 0px;overflow: hidden;"></div>
		    </div>
		   <!--  <div position="centerbottom" id="tab_bottom" title="日志输出">
	    		<div title="日志输出">
	    			<div id="logGrid" style="margin: 0; padding: 0px;overflow: hidden;"></div>
	    		</div>
    		</div> -->
    		<!-- <div position="right" id="tab_right"  title="运行参数">
    			<div id="layout_right" style="overflow: hidden;">
				    <div position="top">
			    		
		    		</div>
				    <div position="bottom" title="运行结果">
			    		
		    		</div>
    			</div>
    		</div> -->
    	</div>
		</div>
		
		<form name="hideForm" id="hideForm" method="post">
			<input type="hidden" name = "processInfoArrStr" id="processInfoArrStr" />
			<input type="hidden" name = "satMids" id="satMids" />
		</form>
	</body>

</html>
