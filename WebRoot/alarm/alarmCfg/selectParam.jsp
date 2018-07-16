<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <title>选择参数</title>
    <jsp:include page="/ligerUI.jsp" />
 	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var satid = "<%=request.getParameter("satid")%>";
	</script>
    <script src="js/selectParam.js" type="text/javascript"></script>
  </head>
  
   <body> 
        <div style="height: 30px; background-color: var(--grid-search-bg-color)">
			<form id="form1" > 
				<table width="100%" >
					<tr>
						<td width="90px">
							<label>遥测名称/编号：</label>
						</td>
						<td>
							<input type="text" ltype="text" id="param_name" name="param_name" />
						</td>
						<td>
							<div id="searchbtn"  style="margin-top: 3px;"></div>
						</td>
						<td>
					    	<div id="resetbtn"  style="margin-top: 3px;"></div>
						</td>
					</tr>
				</table>
			</form>
   		</div>	 	
    	<div id="maingrid" style="margin:0; padding:0;"></div>
     </body>    
</html>