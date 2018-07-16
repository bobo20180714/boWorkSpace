<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <title>查找卫星</title>
    <jsp:include page="/ligerUI.jsp" />
    <script src="js/sat_query.js" type="text/javascript"></script>
 <style type="text/css">
			.l-form {
			  margin: 0px;
			}
	  </style>
  </head>
   <body> 
       <!--  <div class="l-panel-search-box" style="height: 23px;background-color: var(--grid-search-bg-color)">
			<form id="form1" > 
				<div class="l-panel-search-box-item">
		      		<label for="sat_id" style="color: black;">关键字：</label>
		      		<input type="text" ltype="text" id="sat_id" name="sat_id"   class="field l-text-field" />
			    </div>
			</form>
			<div class="l-panel-search-item-btn">
				<div class="l-panel-search-item"><div id="searchbtn" ></div></div>
		    	<div class="l-panel-search-item"><div id="resetbtn" ></div></div>
			</div>
   		</div>	 --> 
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
<!--       	<span style="font-size: 18;margin-left: 5px;">说明：本页面用于遥测参数门限报警配置信息的列表显示，可以进行遥测参数门限报警配置、修改、查看.</span>-->
     </body>    
</html>