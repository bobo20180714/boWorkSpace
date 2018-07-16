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
		<title>参数配置信息</title>
		<jsp:include page="/ligerUI.jsp" />
		<script type="text/javascript">
			//当前节点ID
			var cur_id = '<%=request.getParameter("cur_id")%>';
		</script>
		<script src="js/SubTmParams.js" type="text/javascript"></script>
		  <style type="text/css">
			  .middle div {
	              margin-top: 3px;
	         }
			.l-panel{
				border:1px solid #AECAF0;
			}
	     </style>
	</head>
<body style=" margin-left: 20px;margin-top: 20px">
	<table>
		<tr>
			<td>
				<div style="margin:4px;float:left;">
					<div id="searchbar1" style="margin-bottom: 5px">
						<table>
							<tr>
								<td>关键字：</td>
								<td width="143px"><input id="txtKey1" type="text" class="l-text"/></td>
								<td valign="middle"><div 
									class="l-button" onclick="sch1_loadData()" align="center">
									查询</div></td>
							</tr>
						</table>
					</div>
					<div id="maingrid1"></div>
				</div>
			</td>
			<td>
				<div style="margin:4px;float:left;" class="middle">
					<div id="assign_all"></div>
					<div id="param_assign"></div>
					<div id="del_param"></div>
					<div id="del_all"></div>
				</div>
			</td>
			<td>
				<div style="margin:4px;float:left;">
					<div id="searchbar2" style="margin-bottom: 5px">
						<table>
							<tr>
								<td>关键字：</td>
								<td width="143px"><input id="txtKey2" type="text" class="l-text"/></td>
								<td valign="middle"><div id="orderBtn_add"
									class="l-button" onclick="sch2_loadData()" align="center">
									查询</div></td>
							</tr>
						</table>
						<!-- 关键字：<input id="txtKey2" type="text" /> 
						<div id="orderBtn_add"
							class="l-button" onclick="sch2_loadData()" align="center">
							查询
						</div> -->
						<!-- <input id="btnOK2" 
							type="button" value="查询" onclick="" class="1-button" /> -->
					</div>
					<div id="maingrid2"></div>
				</div>
			</td>
		</tr>
	</table>
</body>

</html>
