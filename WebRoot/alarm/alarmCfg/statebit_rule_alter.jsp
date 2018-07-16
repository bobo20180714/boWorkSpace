<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title>设置状态字--网页对话框</title>
		<jsp:include page="/ligerUI.jsp" />
		<script type="text/javascript">
			var basePath="<%=basePath%>"
		</script>
		<script src="js/statebit_rule_alter.js" type="text/javascript"></script>
		<style type="text/css">
		.l-button-submit,.l-button-reset {
			width: 80px;
			float: left;
			margin-left: 30px;
			padding-bottom: 2px;
			}
		</style>
	</head>
	<body>
		<table id="statebit_rule_alter">
		    <tr>
            <td colspan="2">
                <table style="width:100%">
                    <tr>
                        <td style="text-align:center;width:14%;">状态名称</td>
                        <td style="text-align:left;width:47%;"><input type="text" id="Head" style="width:98%"/></td>
                        <td style="text-align:center;width:14%;">判断次数</td>
                        <td style="text-align:left;width:12%;">
                            <select id="JudgeCount" style="width:53px;position:absolute;clip:rect(2px 100% 90% 35px);" onchange="document.getElementById('txtJudgeCount').value=this.value"></select>
                            <input id="txtJudgeCount" style="width:45px;"/>
                        </td>
                        <td style="text-align:right;width:7%;">禁用</td>
                        <td style="width:6%;"><input id="Valid" type="checkbox"/></td>
                    </tr>
                </table>
            </td>
            </tr>
	    	<tr>
	    	<td>
	    	<div id="ascend_order" class="liger-button" data-click="setAscendOrder" style="margin-top:9px;">反序</div>
		    <div id="set_from_value" class="liger-button" data-click="setFromValue" style="margin-top:4px;">从0开始</div>
		    <div id="set_clear_all" class="liger-button" data-click="setClearAll" style="margin-top:4px;">全部清除</div>
	        </td>
	    	<td>
	    	<div id="checkboxlist1"></div>
	    	</td>
	    	</tr>
	    	<tr>
	    	<td colspan="2"><div id="maingrid" style="margin:0; padding:0;"></div></td>	    	
	    	</tr>
	    </table>	
		<div id="confirmbtn"
			style="margin-top: 20px; margin-left: 453px; float: left;"
			class="l-dialog-btn" onclick="addAlarmRuleInfo()" align="center">
			保存
		</div>
		<div id="confirmbtn"
			style="margin-top: 20px; margin-left: 20px; float: left;"
			class="l-dialog-btn" onclick="cancel()" align="center">
			返回
		</div>
		<br/>
		<p>
		<span style="font-size: 18;margin-left: 5px;">说明：可以勾选Bit位，设置不同Bit位或Bit位组合的状态字报警规则。</span>
	</body>
	
</html>