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
	<script src="js/device_list.js" type="text/javascript"></script>
	<style type="text/css">
			.l-form {
			  margin: 0px;
			}
		</style>
</head>
<body> 
<!-- <div class="l-panel-search-box">
		    <form id="form1" > 
		    <table style="margin-left: 30px;">
		    	<tr>
		    		<td width="70px">
		    			编号：
		    		</td>
		    		<td width="150px">
		    			<input type="text" ltype="text" id="device_code" name="device_code" />
		    		</td>
		    		<td width="70px">
		    			名称：
		    		</td>
		    		<td width="150px">
		    			<input type="text" ltype="text" id="device_name" name="device_name" class="field" />
		    		</td>
		    		<td width="80px">
		    			地面站类型：
		    		</td>
		    		<td width="200px">
		    			<input type="text" ltype="text" id="device_type" name="device_type" class="field" />
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
			<form id="form2" style="margin-left: 10px;float:left;height: 30px">
			</form>
			<div style="padding-top: 3px;" >
				<div class="l-panel-search-item">
					<div id="searchbtn" style="width: 60px;">
						</div>
					</div>
			
					<div class="l-panel-search-item">
						<div id="resetbtn" style="width: 60px;">
						</div>
					</div>
				</div>
			</div>
  	<div id="toptoolbar"></div> 
    <div id="maingrid" style="margin:0; padding:0;"></div>
 
</body>
</html>
