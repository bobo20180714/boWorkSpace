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
	<title>新增状态字</title> 
	<jsp:include page="/ligerUI.jsp" />
	<script src="<%=basePath %>resources/js/jquery-form/jquery.form.js" type="text/javascript"></script>
	<script src="js/ligerPopupEdit.js" type="text/javascript"></script>
	<script src="js/statusWord_add.js" type="text/javascript"></script>
	<script type="text/javascript">
		var basePath = '<%=basePath %>';
		var satid = '<%=request.getParameter("satid") %>';
		var type = '<%=request.getParameter("type") %>';
		var nowSelectTmid = '<%=request.getParameter("nowSelectTmid") %>';
		var ruleid = '<%=request.getParameter("ruleid") %>';
	</script>
	<style type="text/css">
		.input{
			border:0px;
			vertical-align: middle;
			height: 20px;
		}
		.l-dialog-btn{
			margin-right:30px; 
		}
		.btn{
			height: 25px;
			overflow: hidden;
			width: 70px;
			cursor: pointer;
			margin-right: 5px;
			align:center;
			display: inline-block;
			zoom: 1;
			border: 1px solid #c1c1c1;
			border-radius: 2px;
			box-shadow: 0 1px 1px rgba(0,0,0,0.15);
			background: #ffffff;
			background: -moz-linear-gradient(top, #ffffff, #f4f4f4);
			background: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff), to(#f4f4f4));
			background: -o-linear-gradient(top, #ffffff, #f4f4f4);
			background: -ms-linear-gradient(top, #ffffff 0%,#f4f4f4 100%);
			background: linear-gradient(top, #ffffff, #f4f4f4);
			font: 12px 宋体b8b\4f53;
			color: #555;
			line-height: 25px;
			vertical-align:middle;
		}
	</style>
</head>
<body style="padding: 20px 10px 10px 10px">
	<center>
    <form name="form_1" method="post" id="form_1" enctype="multipart/form-data">
		<table width="780px" border=0>
			<tr style="line-height: 35px;">
				<td width="80px" align="center">
					状态名称
				</td>
				<td width="265px">
					<input name="status_word_name" id="status_word_name" type="text" 
						validate="{required:true}" />
				</td>
				<td valign="middle" width="2px" align="right">
					<input value="0" type="checkbox" name="canalarm" id="canalarm" />
				</td>
				<td valign="middle" width="59px" align="left">
					是否报警
				</td>
				<td valign="middle" width="70px" align="center">
					报警次数:
				</td>
				<td valign="middle" width="100px">
					<input name="JudgeCount" id="juddge_count" type="text" digits="true" class="liger-combox" />
				</td>
				<td width="150px"></td>
			</tr>
			<tr style="line-height: 35px;">
				<td width="80px" align="center">
					关联条件
				</td>
				<td width="500px"  colspan="5">
					<input name="relation_condition" id="relation_condition" type="text" 
						validate="{required:true}" />
				</td>
				<td valign="middle" align="left">
					&nbsp;&nbsp;<input value="0" type="checkbox" name="isValid" id="isValid" />是否有效
				</td>
			</tr>
			<tr style="border: 1px solid #AECAF0;height: 90px;">
				<td width="120px" align="center">
					<div id="orderBtn_add"
						class="l-button" onclick="orderBtnClick('1')" align="center">
						正序
					</div>
					<div id="orderBtn_reduce"
						class="l-button" onclick="orderBtnClick('2')" align="center"
						style="display: none;">
						反序
					</div>
					<div id="startWhereBtn_0"
						class="l-button" onclick="startWhereBtnClick('0')" align="center"
						style="margin-top: 15px;display: none;">
						从0开始
					</div>
					<div id="startWhereBtn_1"
						class="l-button" onclick="startWhereBtnClick('1')" align="center"
						style="margin-top: 15px;">
						从1开始
					</div>
					<div id="startWhereBtn_-1"
						class="l-button" onclick="startWhereBtnClick('-1')" align="center"
						style="margin-top: 15px;display: none;">
						全部清除
					</div>
				</td>
				<td colspan="6">
					<div id="checkboxDiv" style="height: 88px;margin-left: 5px;">
						<table id="checkboxTable">
							
						</table>
					</div>
				</td>
			</tr>
			<tr style="border: 1px solid #AECAF0;">
				<td colspan="7" id="stateTd">
					<div id="stateGrid"></div>
				</td>
			</tr>
		</table>
	 <!--    <div style="margin-top: 10px;padding-top: 7px;" >
	    	<div id="confirmbtn" class="btn" onclick="saveStatusWord()">
					保存
				</div>
			<div  id="closeBtn" class="btn"  onclick="colseTab()">关闭</div>
		</div> -->
	</form>
	</center>
	<form id="hideForm" name="hideForm" method="post" style="display: none;">
		<input type="hiden" name="stateInfoStr" id="stateInfoStr"></input>
		<input type="hiden" name="toCach" id="toCach"></input>
	</form>
</body>
</html>