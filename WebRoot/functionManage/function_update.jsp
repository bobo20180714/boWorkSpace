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
		<title>修改计算模块</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="js/function_update.js" type="text/javascript"></script>
		<style type="text/css">
			.l-textarea{
				border:1px solid #AECAF0;
			}
			.l-text{
				width:200px;
			}
			.l-panel{
				border:1px solid #AECAF0;
			}
		</style>
		<script type="text/javascript">
			var computFuncId = '<%=request.getParameter("computFuncId")%>';
	</script>
	</head>
	<body>
		<div id="addTab">
			<div title="基本信息">
				<form style="margin-top: 40px;margin-left:30px; " name="computeInfoForm" id="computeInfoForm"></form>
			</div>
			<div title="运行参数">
				 <form style="margin-top: 20px;margin-left:30px; " name="runParamForm" id="runParamForm"></form>
				 <div id="parambar" style="width: 550px;border-left: 1px solid #AECAF0;border-right: 1px solid #AECAF0;margin-left: 32px;"></div> 
	    		 <div id="paramGrid" style="margin:0; padding:0;margin-left: 32px;"></div>
			</div>
	    </div>
	
		<!-- <table>
			<tr>
				<td colspan="2">
					<form style="margin-top: 20px;margin-left:20px; " name="addFunction" id="addFunction"></form>
				</td>
			</tr>
			<tr>
				<td width="110px" valign="top">
					<span style="margin-left: 20px;">配置参数：</span>
				</td>
				<td>
					<div id="parambar" style="width: 507px;border-left: 1px solid #AECAF0;border-right: 1px solid #AECAF0;"></div> 
		    		<div id="paramGrid" style="margin:0; padding:0"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<form style="margin-top: 5px;margin-left:20px;" name="descForm" id="descForm" ></form>
				</td>
			</tr>
		</table> -->
	</body>

</html>
