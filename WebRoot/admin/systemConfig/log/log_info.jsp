<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>

    <title>日志列表</title>
    <jsp:include page="/ligerUI.jsp" />
    <script src="js/list.js" type="text/javascript"></script>

</head>
<body style="padding:0px; overflow:hidden;"> 
	<div class="l-panel-search-box">
	    	<form id="form1" > 
				    <table style="margin-left: 30px;">
				    	<tr>
				    		<td width="70px">
				    			用户名称：
				    		</td>
				    		<td width="200px">
				    			<input type="text"  id="user_name" name="user_name"  class="field"/>
				    		</td>
				    		<td width="70px">
				    			时间区间：
				    		</td>
				    		<td width="155px">
				    			<input type="text" ltype="date" id="begin_time" name="begin_time" class="field" />
				    		</td>
				    		<td width="20px">
				    			-
				    		</td>
				    		<td width="200px">
				    			<input type="text" ltype="date" id="end_time" name="end_time" class="field" width="110px" />
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
	<div id="maingrid" style="margin:0; padding:0;"></div>
</body>
</html>
