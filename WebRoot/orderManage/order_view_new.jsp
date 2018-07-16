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
<title>订单查看</title>
<jsp:include page="/ligerUI.jsp" />
		<jsp:include page="/css.jsp" />
<script src="js/order_view_new.js" type="text/javascript"></script>
<script src="<%=basePath %>resources/js/jquery-form/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">
			var orderId = '<%=request.getParameter("orderId")%>';
	</script>
<style type="text/css">
	.x-fieldset {
		border: 1px solid #b5b8c8;
		padding: 0 10px;
		margin: 0 0 10px;
		}
		.x-fieldset-header-text {
		font: 11px/14px bold tahoma,arial,verdana,sans-serif;
		color: #15428b;
		padding: 1px 0;
		}
</style>
</head>
<body style="overflow: hidden;" >
	<div id="addTab">
		<div title="订单基本信息">
			<form style="margin-left:20px;margin-top: 30px;" name="orderForm" id="orderForm"></form>
		</div>
		<div title="数据获取">
			<form style="margin-left:10px;" name="getDataForm" id="getDataForm"></form>
			<fieldset class="x-fieldset x-fieldset-with-title"
				style="border-width: 1px; width: 420px; height: 210px;margin-left: 10px;overflow: auto;">
				<legend class="x-fieldset-header x-fieldset-header-default"
					id="fieldset-1144-legend">
					<span id="fieldset-1144-legend-outerCt" style="display:table;">
						<div id="fieldset-1144-legend-innerCt"
							style="display:table-cell;height:100%;vertical-align:top;"
							class="">
							<div
								class="x-component x-fieldset-header-text x-component-default"
								id="fieldset-1144-legendTitle">计算模块参数</div>
						</div>
					</span>
				</legend>
				<div id="fieldset-1144-body" class="x-fieldset-body "
					style="width: 465px; overflow: auto;">
						<table id="paramTable_getData" style="margin-left: 15px;margin-top: 10px;">
							
						</table>
				</div>
			</fieldset>
			<div>
				<table style="margin-left: 10px;">
					<tr>
						<td>参数组合：</td>
						<td><input name="getData_paramxml" id="getData_paramxml" /></td>
					</tr>
				</table>
			</div>
		</div>
		<div title="数据计算">
			<form style="margin-left:10px;" name="computerForm" id="computerForm"></form>
			<fieldset class="x-fieldset x-fieldset-with-title"
				style="border-width: 1px; width: 420px; height: 210px;margin-left: 10px;overflow: auto;">
				<legend class="x-fieldset-header x-fieldset-header-default"
					id="fieldset-1144-legend">
					<span id="fieldset-1144-legend-outerCt" style="display:table;">
						<div id="fieldset-1144-legend-innerCt"
							style="display:table-cell;height:100%;vertical-align:top;"
							class="">
							<div
								class="x-component x-fieldset-header-text x-component-default"
								id="fieldset-1144-legendTitle">函数参数</div>
						</div>
					</span>
				</legend>
				<div id="fieldset-1144-body" class="x-fieldset-body "
					style="width: 465px; overflow: auto;">
						<table id="paramTable_comput" style="margin-left: 15px;margin-top: 10px;">
							
						</table>
				</div>
			</fieldset>
			<table style="margin-left: 10px;">
					<tr>
						<td>参数组合：</td>
						<td><input name="comput_paramxml" id="comput_paramxml" /></td>
					</tr>
				</table>
		</div>
		<div title="结果处理">
			<form style="margin-left:10px;" name="resultForm" id="resultForm"></form>
			<fieldset class="x-fieldset x-fieldset-with-title"
				style="border-width: 1px; width: 420px; height: 210px;margin-left: 10px;overflow: auto;">
				<legend class="x-fieldset-header x-fieldset-header-default"
					id="fieldset-1144-legend">
					<span id="fieldset-1144-legend-outerCt" style="display:table;">
						<div id="fieldset-1144-legend-innerCt"
							style="display:table-cell;height:100%;vertical-align:top;"
							class="">
							<div
								class="x-component x-fieldset-header-text x-component-default"
								id="fieldset-1144-legendTitle">函数参数</div>
						</div>
					</span>
				</legend>
				<div id="fieldset-1144-body" class="x-fieldset-body "
					style="width: 465px; overflow: auto;">
						<table id="paramTable_result" style="margin-left: 15px;margin-top: 10px;">
							
						</table>
				</div>
			</fieldset>
			<table style="margin-left: 10px;">
					<tr>
						<td>参数组合：</td>
						<td><input name="result_paramxml" id="result_paramxml" /></td>
					</tr>
				</table>
		</div>
	</div>
	
	<form id="hideForm" name="hideForm" method="post" style="display: none;">
		<input name="pkId" id="pkId" type="hidden"/>
		<input name="orderName" id="orderName" type="hidden"/>
		<input name="satMid" id="satMid" type="hidden"/>
		<input name="orderContent" id="orderContent"  type="hidden"/>
		<input name="isGetData" id="isGetData" type="hidden"/>
		<input name="getDataId" id="getDataId" type="hidden"/>
		<input name="getDataParam" id="getDataParam" type="hidden"/>
		<input name="computId" id="computId" type="hidden"/>
		<input name="computParam" id="computParam" type="hidden"/>
		<input name="isResult" id="isResult" type="hidden"/>
		<input name="resultId" id="resultId" type="hidden"/>
		<input name="resultParam" id="resultParam" type="hidden"/>
	</form>
</body>

</html>
