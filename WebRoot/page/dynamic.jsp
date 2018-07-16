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
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="Aqua/css/ligerui-all.css" rel="stylesheet"/>
<link href="ligerUI/skins/ligerui-icons.css" rel="stylesheet"/>
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/resize.css">
<link rel="stylesheet" href="page/css/dynamic.css">
<style type="text/css">
	.l-bar-separator{
		margin-top:6px;
	}
	.l-panel-topbar{
	    width: 100%;
	    height: 28px;
	    background-color: #E3F3FF;
	}
</style>
</head>
<body>
	<jsp:include page="/page/head.jsp"></jsp:include>
	<jsp:include page="/page/nav.jsp"></jsp:include>    
    <div class="main">
    	<div id="layout">
            <div id="gridDiv" position="left" title="动态图元库">
            	<div id="dynamic-grid"></div>
            </div>
            <div position="center" title="动态图元测试">
            	<div id="x-toolbar"></div>
            	<div id="container" class="content">
            </div>
        </div>	
    </div>
<script src="ligerUI/js/core/base.js" type="text/javascript"></script>
<script type="text/javascript" src="ligerUI/js/plugins/ligerLayout.js"></script>
<script type="text/javascript" src="ligerUI/js/plugins/ligerMenu.js"></script>
<script type="text/javascript" src="ligerUI/js/plugins/ligerTextBox.js"></script>
<script type="text/javascript" src="ligerUI/js/plugins/ligerComboBox.js"></script>
<script type="text/javascript" src="ligerUI/js/plugins/ligerDialog.js"></script>
<script type="text/javascript" src="ligerUI/js/plugins/ligerGrid.js"></script>
<script type="text/javascript" src="ligerUI/js/plugins/ligerButton.js"></script>
<script type="text/javascript" src="ligerUI/js/plugins/ligerToolBar.js"></script>
<script type="text/javascript" src="ligerUI/js/plugins/ligerDrag.js"></script>
<!-- <script src="jquery-validation/jquery.validate.min.js"></script>
<script src="jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="jquery-validation/messages_cn.js" type="text/javascript"></script> -->
<script type="text/javascript" src="js/resize.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript" src="page/js/dynamic.js"></script>
<script type="text/javascript" src="debug/debug.js"></script>
<script src="lib/Alert.js" type="text/javascript"></script>
</body>
</html>