<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <title>报警日志信息查询(多星测控系统不用)</title>
    <jsp:include page="/ligerUI.jsp" />
    <script src="js/ligerGrid.js" type="text/javascript"></script>
    <script src="js/alarm_query_page.js" type="text/javascript"></script>
    	 <style type="text/css">
	  	.l-dialog-buttons{
	  		margin-top:0px;
	  		margin-bottom:0px;
	  	}
	  </style>
  </head>
  <body>
          <div class="l-panel-search-box">
			<form id="form1" > 
			 <table style="margin-left: 30px;">
		    	<tr>
		    		<td width="40px">
		    			卫星：
		    		</td>
		    		<td width="170px">
		    			<input type="text" ltype="popup" id="sat_id" name="sat_id" />
		    		</td>
		    		<td width="80px">
		    			参数关键字：
		    		</td>
		    		<td width="150px">
		    			<input type="text" ltype="text" id="param_name" name="param_name"/>
		    		</td>
		    		<td width="70px">
		    			开始时间：
		    		</td>
		    		<td width="150px">
		    			<input type="text" ltype="date" id="beginDate" name="beginDate" />
		    		</td>
		    		<td width="30px">
		    			至：
		    		</td>
		    		<td width="200px">
		    			<input type="text" ltype="date" id="endDate" name="endDate" />
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
   		</div>
	  	<div id="toptoolbar"></div> 
    	<div id="maingrid"></div>
  </body>
</html>
