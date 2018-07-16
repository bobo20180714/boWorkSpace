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
		<title>遥测参数门限报警--查看</title>
		<jsp:include page="/ligerUI.jsp" />
		<style type="text/css">
			.tableClass input{
				width: 160px;
				text-align: center;
			}
			.tableClass td{
				border: 1px solid #AECAF0;
			}
		</style>
		<script type="text/javascript">
			var basePath="<%=basePath%>";
			var ruleid = "<%=request.getParameter("ruleid")%>";
			var tmid = "<%=request.getParameter("tmid")%>";
		</script>
		<script src="js/alarm_rule_view.js" type="text/javascript"></script>		
	</head>
	<body>
		<form id="form1" name="form1" method="post"
			style="margin-top: 15px; margin-left: 22px;">
			<table width="500" border="0">
				<tr>
					<td width="100">
						遥测参数：
					</td>
					<td width="300">
						<input name="TM_PARAM_NAME" id="tm_param_name" type="text" value="" style="width:280px;"
							readonly="readonly" class="field liger-textbox" /> 
					</td>
				</tr>
				<tr>
					<td width="100">
						关联条件：
					</td>
					<td width="300">
						<input name="relation_condition" id="relation_condition" type="text"  style="width:280px;" 
						readonly="readonly" class="field liger-textbox" />
					</td>
					<td valign="middle">
						&nbsp;&nbsp;<input value="0" type="checkbox" name="isValid" id="isValid" 
								readonly="readonly" class="liger-checkbox"/>是否有效
					</td>
				</tr>
			</table>
			<br />
			<table class="tableClass" width="540"  border="1" style="border: 1px solid #AECAF0;">
				<tr style="line-height: 30px;background-color:#E3EDF9;">
					<td width="100">&nbsp;</td><td width="104" align="center">下限</td>
					<td width="96" align="center">上限</td>
					<td width="72" align="center">是否有效</td>
				</tr>
				<tr style="line-height: 30px;">
					<td align="center">
						有效值
					</td>
					<td align="center">
						<input name="RangeValueLower" id="range_value_lower" type="text" value=""
							readonly="readonly" class="field liger-textbox" />
					</td>
					<td align="center">
						<input name="RangeValueUpper" id="range_value_upper" type="text" value=""
							readonly="readonly" class="field liger-textbox" />
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr style="line-height: 30px;">
					<td align="center">
						重度
					</td>
					<td align="center">
						<input name="LowerFirst" id="lower_first" type="text" value=""
							readonly="readonly" class="field liger-textbox" />
					</td>
					<td align="center">
						<input name="UpperFirst" id="upper_first" type="text" value=""
							readonly="readonly" class="field liger-textbox" />
					</td>
					<td align="center">
						<input name="FirstValidity" id="first_validity" type="checkbox" readonly="readonly" class="liger-checkbox" /> 
					</td>
				</tr>
				<tr style="line-height: 30px;">
					<td align="center">
						中度
					</td>
					<td align="center">
						<input name="LowerSecond" id="lower_second" type="text" value=""
							readonly="readonly" class="field liger-textbox" />
					</td>
					<td align="center">
						<input name="UpperSecond" id="upper_second" type="text" value=""
							readonly="readonly" class="field liger-textbox" />
					</td>
					<td align="center">
						<input name="SecondValidity" id="second_validity" type="checkbox" readonly="readonly" class="liger-checkbox" /> 
					</td>
				</tr>
				<tr style="line-height: 30px;">
					<td align="center">
						轻度
					</td>
					<td align="center">
						<input name="LowerThrid" id="lower_thrid" type="text" value=""
							readonly="readonly" class="field liger-textbox" />
					</td>
					<td align="center">
						<input name="UpperThrid" id="upper_thrid" type="text" value=""
							readonly="readonly" class="field liger-textbox" />
					</td>
					<td align="center">
						<input name="ThridValidity" id="thrid_validity" type="checkbox" readonly="readonly" class="liger-checkbox" /> 
					</td>
				</tr>
			</table>
			<br />
			<table class="tableClass" width="540"  border="1" style="border: 1px solid #AECAF0;">
				<tr style="line-height: 30px;background-color:#E3EDF9;">
					<td width="134" align="center">
						是否报警
					</td>
					<td width="118" align="center">
						判断次数
					</td>
				</tr>
				<tr style="line-height: 30px;">
					<td align="center">
						<input name="CanAlarm" id="can_alarm" type="checkbox" readonly="readonly" class="liger-checkbox" /> 
					</td>
					<td align="center">
						<input name="JudgeCount" id="juddge_count" type="text"  readonly="readonly" class="liger-textbox" /> 
					</td>
				</tr>
			</table>
			
		</form>
		
		<br/>
		<span style="font-size: 18;margin-left: 18px;">备注：门限报警阈值需要符合要求：有效值上限≥重度上限≥中度上限≥轻度上限≥轻度下限≥中度下限≥</span>
		<br/>
		<span style="font-size: 18;margin-left: 18px;">重度下限≥有效值下限。</span>
	</body>
</html>