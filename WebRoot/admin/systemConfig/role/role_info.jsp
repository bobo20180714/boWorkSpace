<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <jsp:include page="/ligerUI.jsp" />
	<script src="js/role_info.js" type="text/javascript"></script>
	 <style type="text/css">
			.l-form {
			  margin: 0px;
			}
		</style>
</head>
<body> 
	<!-- <div class="l-panel-search-box">
			<form id="form1" > 
			        <div class="l-panel-search-box-item" >
				        	<label for="name">角色名称：</label><input type="text" ltype="text" id="name" name="name" class="field l-text-field" style="width: 100px"/>
				    </div>
			</form>
			
			<div class="l-panel-search-item-btn">
				<div class="l-panel-search-item"><div id="searchbtn" >查询</div></div>
		    	<div class="l-panel-search-item"><div id="resetbtn" >重置</div></div>
			</div>
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
 	<div id="topmenu"></div> 
  	<div id="toptoolbar"></div> 
    <div id="maingrid" style="margin:0; padding:0;"></div>
 
</body>
</html>
