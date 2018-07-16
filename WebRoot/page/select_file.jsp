<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <title>关联文件</title>
    <jsp:include page="/ligerUI.jsp" />
	<script src="<%=basePath %>resources/js/jquery-form/jquery.form.js" type="text/javascript"></script>
    <script src="js/select_file.js" type="text/javascript"></script>
    <script type="text/javascript">
		var super_id = '<%=request.getParameter("super_id") %>';
	</script>
 <style type="text/css">
			.l-form {
			  margin: 0px;
			}
	  </style>
  </head>
   <body style="overflow: hidden;"> 
   		<div id="layout">
   		 	<div position="left" title="" style="background-color: #E3F3FF">
            	<div style="overflow:auto;height: 100%;"  class="l-tree-body"><div id="projectTree"></div></div>
            </div>
            <div position="center">
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
		    	<form id="hideForm" type="hidden">
		    		<input type="hidden" name="fileListStr" id="fileListStr" />
		    		<input type="hidden" name="ownerId" id="ownerId" />
		    	</form>
	    	</div>
   		</div>
     </body>    
</html>