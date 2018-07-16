<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html >
<head>
<jsp:include page="/ligerUI.jsp" />
<script type="text/javascript" src="js/projectTree.js"></script>
<link href="<%=basePath%>Aqua/css/ligerui-tree.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>lib/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
 <style type="text/css">
			.l-form {
			  margin: 0px;
			}
	  </style>
	  <script type="text/javascript">
	var fileProId = '<%=request.getParameter("fileProId") %>';
</script>

</head>
<body style="overflow: hidden;">
  <!-- <center >
  		<div id ="scollDivTree" style="height:385px;overflow: auto;"  class="l-tree-body">
		      <div id="fileTree"></div>
	    </div>
	  </div>
  </center> -->
  
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
    	<div id="maingrid" style="margin:0; padding:0;"></div>
     </body>  
  
</body>
</html>
