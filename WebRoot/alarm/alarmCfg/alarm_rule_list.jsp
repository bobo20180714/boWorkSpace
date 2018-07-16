<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <title>遥测参数门限报警配置</title>
    <jsp:include page="/ligerUI.jsp" />
    <script src="js/alarm_rule_list.js" type="text/javascript"></script>
     <style type="text/css">
	  	.l-dialog-buttons{
	  		margin-top:0px;
	  		margin-bottom:0px;
	  	}
			.l-form {
			  margin: 0px;
			}
	  </style>
  </head>
  <body>
       <!--  <div class="l-panel-search-box">
		    <form id="form1" > 
		    <table style="margin-left: 30px;">
		    	<tr>
		    		<td width="50px">
		    			卫星：
		    		</td>
		    		<td width="235px">
		    			<input type="text" ltype="popup" id="sat_id" name="sat_id" />
		    		</td>
		    		<td width="70px">
		    			遥测参数：
		    		</td>
		    		<td width="200px">
		    			<input type="text" ltype="text" id="tm_param" name="tm_param" class="field" />
		    		</td>
		    		<td width="80px">
		    			<div id="searchbtn" >查询</div>
		    		</td>
		    		<td>
		    			<div id="resetbtn" >重置</div>
		    		</td>
		    	</tr>
		    </table>
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
	 	<div id="topmenu"></div> 
	  	<div id="toptoolbar"></div> 
    	<div id="maingrid" style="margin:0; padding:0;"></div>
<!--    	<br/>-->
<!--       	<span style="font-size: 18;margin-left: 5px;">说明：本页面用于遥测参数门限报警配置信息的列表显示，可以进行遥测参数门限报警配置、修改、查看.</span>-->
     </body>
</html>