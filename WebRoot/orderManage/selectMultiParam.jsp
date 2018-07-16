<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<!-- 
		@author 孟祥超
		@date 2015.11.18
	 -->
	<title>选择单个卫星的多个遥测参数</title> 
	<jsp:include page="/ligerUI.jsp" />
		<jsp:include page="/css.jsp" />
	<script src="js/ligerGrid.js" type="text/javascript"></script>
	<script src="js/selectMultiParam.js" type="text/javascript"></script>
	<script type="text/javascript">
		var basePath = '<%=basePath %>';
	</script>
	<style type="text/css">
		.table{
			border: 1px solid #AECAF0;
			margin-left:3px;
			margin-top: 5px;
		}
		.table td{
			border-bottom-color: #AECAF0;
		}
		.title{
			color: #04408c;
			font-size: 12px;
			font-weight: bold;
			font-family: tahoma,arial,verdana,sans-serif;
			line-height: 20px;
			vertical-align: middle;
			background-color: #D4E3F4;
		}
		.l-form {
			margin: 0px;
			margin-left:7px;
		}
		.searchLine{
			line-height: 30px;
			vertical-align: middle;
		}
		.searchImg :ACTIVE  
		{
			width: 16px;
			height: 16px;
			border: 1px solid #c1c1c1;
			cursor: pointer;
		}
	</style>
	<script type="text/javascript">
			var satId = '<%=request.getParameter("satId")%>';
	</script>
</head>
<body style="overflow: hidden;">
	<table width="635px">
		<tr>
			<td width="310px">
				<table border="1" class="table" width="278px">
					<tr>
						<td class="title">
							未选择遥测参数
						</td>
					</tr>
					<tr>
						<td class="searchLine">
							<form id="form_2" > 
							    <table>
							    	<tr>
							    		<td width="50">
							    			关键字：
							    		</td>
							    		<td width="95">
							    			<input type="text" ltype="text" id="key_2" 
							    				name="key_2"/>
							    		</td>
							    		<td width="20">
											<div id="searchbtn_2" class="searchImg" onclick="doSearch_2()">
												<img src="<%=basePath %>resources/images/search.png"
														style="cursor: pointer;"/>
											</div>
							    		</td>
							    	</tr>
							    </table>
							</form>
						</td>
					</tr>
					<tr>
						<td valign="top">
							<div id="paramGrid"></div>
						</td>
					</tr>
				</table>
			</td>
			<td width="30px">
				<div style="float:left; margin-top: 60px;margin-left: 3px;" class="middle">
	           		<input type="button" onclick="moveToRight()" value=">" style="width:22px;"/>
		           	<input type="button" onclick="moveToLeft()" value="<"  style="width:22px;margin-top:5px;" />
		      <%--     	<input type="button" onclick="moveAllToRight()" value=">>" style="margin-top:5px;"/>
		           	<input type="button" onclick="moveAllToLeft()" value="<<" style="margin-top:5px;"/> --%>
	      		</div>
			</td>
			<td width="450px">
				<table border="1" class="table" width="278px">
					<tr>
						<td class="title">
							已选择遥测参数
						</td>
					</tr>
					<tr rowspan="2">
						<td valign="top">
							<div id="paramGrid2"></div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>