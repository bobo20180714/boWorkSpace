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
		<script src="js/noparam_query_list.js" type="text/javascript"></script>
	<script src="js/Base64.js" type="text/javascript"></script>
		<script src="<%=basePath %>resources/js/jquery-form/jquery.form.js" type="text/javascript"></script>
		<style type="text/css">
			.x-fieldset {
				border: 1px solid #b5b8c8;
				padding: 0 10px;
				margin: 0 0 10px;
				}
				.x-fieldset-header-text {
				font: 11px/14px bold tahoma,arial,verdana,sans-serif;
				color: #000;
				padding: 1px 0;
				}
				.l-layout-content{
					background-color: #E3F3FF;
				}
		</style>
	</head>
	<body>
		<div id="layout" style="overflow: hidden;">
		  	<div position="left" title="查询条件">
		    	<div id ="scrollDivTree" style="overflow: auto;height:700px;">
		     	 	<form style="margin-left:15px;" name="form1" id="form1"></form>
					<fieldset class="x-fieldset x-fieldset-with-title"
						style="border-width: 1px; width: 420px; height: 210px;margin-left: 15px;overflow: auto;">
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
							style="width: 465px; overflow: auto;">
								<table id="paramTable" style="margin-left: 15px;margin-top: 10px;">
									
								</table>
						</div>
					</fieldset>
					
					<input type="button" id="searchbtn" value="查询" style="margin-left: 180px;margin-top: 50px;"/>
					<input type="button" id="clearbtn" value="清空" style="margin-left: 20px;margin-top: 50px;"/>
		   		</div>
		  	</div>
		 	<div position="center"  id="tab_center" title="查询结果">
				<div id="toptoolbar"></div> 
				<div id="grid">
					<div id="dataGrid" style="margin: 0; padding: 0px;overflow: hidden;"></div>
				</div> 
		    </div>
    	</div>
		</div>
		
		<form name="hideForm" id="hideForm" method="post">
			<input type="hidden" name = "processInfoArrStr" id="processInfoArrStr" />
			<input type="hidden" name = "satMids" id="satMids" />
		</form>
	</body>

</html>
