<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jianshi.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<base href="<%=Common.getPath(request)%>"/>
<title>实时多对象数据在线监视平台</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
<link href="Aqua/css/ligerui-all.css" rel="stylesheet"/>
<link href="ligerUI/skins/ligerui-icons.css" rel="stylesheet"/>
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="page/css/lib.css">
<style type="text/css">
	.l-bar-separator{
		margin-top:6px;
	}
	.l-panel-topbar{
	    width: 100%;
	    height: 28px;
	    background-color: #E3F3FF;
	}
	.l-dialog-win .l-dialog-content {
 	  padding-left: 0px;
	}
	.l-dialog-win .l-dialog-content{
		line-height: 30px;
	}
</style>
</head>
<body>
	<jsp:include page="/page/head.jsp"></jsp:include>
	<jsp:include page="/page/nav.jsp"></jsp:include>    
    <div class="main">
    	<div id="layout">
            <div position="left" title="图元库">
            	<div id="lib-grid"></div>
            </div>
            <div position="center" title="包含插件">
            	<div id="content"></div>
            </div>
        </div>	
    </div>
<script type="text/html" id="tpl_dlg">
<div id="lib-dlg">
	<table>
		<tr>
			<td>图元库名称：</td>
			<td><input name="name"></td>
			<td align="left" style="padding-left: 0px;" ><span class="l-star">*</span></td>
		</tr>
		<tr>
			<td>说明：</td>
			<td><textarea name="comment" style="width:250px;height:60px;"></textarea></td>
			<td align="left" style="padding-left: 0px;" ></td>
		</tr>
	</table>
	<!-- <input type="button" value="确定" class="l-button" style="margin-left:110px;"/> 
	<input type="button" value="取消" class="l-button"/> -->
</div>
</script>
<script src="ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script>
<script type="text/javascript" src="page/js/lib.js"></script>
</body>
</html>