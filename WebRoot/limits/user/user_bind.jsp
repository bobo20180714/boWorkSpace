<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
	<jsp:include page="/ligerUI.jsp" />
	<script type="text/javascript" src="js/user_bind.js"></script>
	<style type="text/css">
		.middle input {
			display: block;
			width: 30px;
			margin: 5px
		}
	</style>
	</head>
	<body style="margin-top: 10px;">
		<table align="center" width="70%" border="0">
			<tr>
				<td align="left" colspan="5">
					<span style="margin-left: 10px;margin-right: 10px">用户账号：</span><span id="userAccount"></span>
				</td>
			</tr>

			<tr>
				<td colspan="5" align="center">
					用户授权
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<div id="listbox1"></div>
				</td>

				<td align="center">
					<div style="float: left; margin-top: 20px" class="middle">
						<input type="button" onclick="moveToRight()" value=">" />
						<input type="button" onclick="moveToLeft()" value="<" />
						<input type="button" onclick="moveAllToRight()" value=">>" />
						<input type="button" onclick="moveAllToLeft()" value="<<" />
						
					</div>
				</td>
				<td align="center" colspan="2">
					<div id="listbox2"></div>
				</td>
			</tr>
			<tr>
				<td colspan="5" align="center">
					<div style="padding-top: 5px">
						<input type="submit" value="确定" class="l-button"
							onclick="btnOkOnclick()" />
						<input type="reset" value="取消" class="l-button"
							onclick="closeWindow()" />
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>