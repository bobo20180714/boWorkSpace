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
<!-- <link rel="stylesheet" href="bootstrap/css/bootstrap.css"> -->
<link href="Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/resize.css">
<link rel="stylesheet" href="page/css/monitor.css">
<style type="text/css">
	.l-toolbar {
	    height: 30px;
	    background: url(Aqua/images/grid/bar-bg.png);
	    margin-top: 3px;
	}
	.l-toolbar-item .l-icon, .l-toolbar-item img {
	    top: 2px;
    }
	.l-panel{
		border:1px solid #AECAF0;
	}
   div[class=l-tree] span{
    	font-size: 14px;
    }
</style>
</head>
<body oncontextmenu="return false" onselectstart="return false">
	<jsp:include page="/page/head.jsp"></jsp:include>
	<jsp:include page="/page/nav.jsp"></jsp:include>    
    <div class="main">    	
    	<div id="layout">
            <div position="left" title="" style="background-color: #E3F3FF">
            	<div style="overflow:auto;height: 100%;"  class="l-tree-body"><div class="l-tree"></div></div>
            </div>
            <div position="center">
            	<div id="toolbar"></div>
            	<div style="padding:0;margin:0;" class="container"></div>
            </div>            
        </div>
    </div>
<script src="ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerSatTree.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jqueryrotate.js"></script>
<script type="text/javascript" src="js/jquery.timers-1.1.2.js"></script>
<script type="text/javascript" src="js/resize.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript" src="js/bind.js"></script>
<script type="text/javascript" src="page/js/monitor.js"></script>
<script src="lib/Alert.js" type="text/javascript"></script>
</body>
</html>