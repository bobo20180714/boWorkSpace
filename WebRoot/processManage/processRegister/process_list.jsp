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
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>进程管理列表</title>
		<jsp:include page="/ligerUI.jsp" />
		<jsp:include page="/css.jsp" />
		<script src="js/process_list.js" type="text/javascript"></script>
	</head>
	<body >
		<div class="l-panel-search-box">
				    <form id="form1" > 
				    <table style="margin-left: 30px;">
				    	<tr>
				    		<td width="70px">
				    			进程标识：
				    		</td>
				    		<td width="200px">
				    			<input type="text" ltype="text" id="process_code" name="process_code" class="field" />
				    		</td>
				    		<td width="70px">
				    			进程名称：
				    		</td>
				    		<td width="200px">
				    			<input type="text"  id="process_name" name="process_name" class="field" />
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
			<div id="processGrid" style="margin: 0; padding: 0px;overflow: hidden;"></div>
	</body>

</html>
