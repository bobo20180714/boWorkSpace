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
		@date 2015.8.7
	 -->
	<title>选择遥测参数</title> 
	<jsp:include page="/ligerUI.jsp" />
	<script src="<%=basePath %>alarm/alarmCfg/js/ligerGrid.js" type="text/javascript"></script>
	<script src="<%=basePath %>resources/js/jquery-form/form-init.js" type="text/javascript"></script>
	<script src="js/selectParam.js" type="text/javascript"></script>
	<script type="text/javascript">
		var basePath = '<%=basePath %>';
	</script>
	<style type="text/css">
		.table{
			border: 1px solid #AECAF0;
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
</head>
<body style="padding-left: 15px;padding-top: 10px;">
	<table width="570px">
		<tr>
			<td width="255px" valign="top">
				<table border="1" class="table" >
					<tr>
						<td class="title">
							航天器
						</td>
					</tr>
					<tr>
						<td  class="searchLine">
							<form id="form_1" > 
							    <table>
							    	<tr>
							    		<td width="50">
							    			关键字：
							    		</td>
							    		<td width="80">
							    			<input type="text" ltype="text" id="key_1" 
							    				name="key_1" />
							    		</td>
							    		<td>
											<div id="searchbtn_1" class="searchImg" onclick="doSearch_1()">
												<img src="<%=basePath %>resources/images/search.png"
														style="cursor: pointer;"/>
											</div>
							    		</td>
							    	</tr>
							    </table>
							</form>
						</td>
					</tr>
					<tr style="height: 240px">
						<td valign="top">
			                 <div id ="scollDivTree" style="overflow: auto;height: 240px;width:245px;"  class="l-tree-body">
		      					<div id="flyerTree"></div>
		   					 </div>
						</td>
					</tr>
				</table>
			</td>
			<td width="275px">
				<table border="1" class="table" >
					<tr>
						<td class="title">
							遥测参数信息
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
							    		<td width="80">
							    			<input type="text" ltype="text" id="key_2" 
							    				name="key_2" lwidth="70"/>
							    		</td>
							    		<td>
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
		</tr>
	</table>
</body>
</html>