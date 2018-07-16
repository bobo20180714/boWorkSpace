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
		<title>遥测源码查询</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="<%=basePath%>bassearch/sourceSearch/js/SourceSearchView.js" type="text/javascript"></script>
		<script src="<%=basePath%>bassearch/sourceSearch/js/ligerGrid.js" type="text/javascript"></script>
		 <style type="text/css">
			.l-form {
			  margin: 0px;
			}
	  </style>
	</head>
	<body style="overflow: hidden;">
	  <!-- <div class="l-panel-search-box">
	    	<form id="form1" > 
				    <table style="margin-left: 30px;">
				    	<tr>
				    		<td width="90px">
				    			航天器名称：
				    		</td>
				    		<td width="200px">
				    			<input type="text"  id="sat_id" name="sat_id" />
				    		</td>
				    		<td width="70px">
				    			开始时间：
				    		</td>
				    		<td width="200px">
				    			<input type="text" ltype="date" id="beginTime" name="beginTime" class="field" />
				    		</td>
				    		<td width="70px">
				    			结束时间：
				    		</td>
				    		<td width="200px">
				    			<input type="text" ltype="date" id="endTime" name="endTime" class="field" width="110px" />
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
	  	<div id="toptoolbar"></div> 
    	<div id="maingrid" style="margin:0; padding:0;"></div>
	</body>

</html>
