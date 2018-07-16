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
		<title>非遥测查询</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="js/query_condition.js" type="text/javascript"></script>
	<script src="js/Exporter2Excel-all.js" type="text/javascript"></script>
	<script src="js/Base64.js" type="text/javascript"></script>
		<script src="<%=basePath %>resources/js/jquery-form/jquery.form.js" type="text/javascript"></script>
		<style type="text/css">
			.x-fieldset {
				border: 1px solid #AECAF0;
				padding: 0 10px;
				margin: 0 0 10px;
				}
				.x-fieldset-header-text {
				color: #000;
				font-family: "微软雅黑", "宋体", Arial, sans-serif;
				padding: 1px 0;
				}
				button{
					    height: 23px;
					    overflow: hidden;
					    width: 25px;
					    line-height: 23px;
					    cursor: pointer;
					    position: relative;
					    text-align: center;
					    border: solid 1px #3789C3;
					    color: #F3F7FC;
					    background: #3789C3;
				}
		</style>
	</head>
	<body >
 	 	<form style="margin-left:35px;margin-top: 10px;" name="form1" id="form1"></form>
		<fieldset class="x-fieldset x-fieldset-with-title"
			style="border-width: 1px; width: 490px; height: 210px;margin-left: 35px;overflow: auto;">
			<legend class="x-fieldset-header x-fieldset-header-default"
				id="fieldset-1144-legend">
				<span id="fieldset-1144-legend-outerCt" style="display:table;">
					<div id="fieldset-1144-legend-innerCt"
						style="display:table-cell;height:100%;vertical-align:top;"
						class="">
						<div
							class="x-component x-fieldset-header-text x-component-default"
							id="fieldset-1144-legendTitle">查询条件</div>
					</div>
				</span>
			</legend>
			<div id="fieldset-1144-body" class="x-fieldset-body "
				style="width: 480px; overflow: auto;">
					<table id="paramTable" style="margin-left: 15px;margin-top: 10px;">
						
					</table>
			</div>
		</fieldset>
		<table style="margin-left: 35px;">
			<tr>
				<td width="65px">
					组合条件：
				</td>
				<td>
				    <input type="text"  id="whereCondition" name="whereCondition" />
				</td>
			</tr>
		</table>
		<!-- <table style="margin-left: 35px;">
			<tr>
				<td width="70px">
					组合条件：
				</td>
				<td>
					<textarea  rows="5" cols="50" style="width:416px;height: 50px;"  id="whereCondition" name="whereCondition"></textarea>
				</td>
			</tr>
		</table> -->
	</body>

</html>
