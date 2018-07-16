<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String urlPath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()+ "/";
%>
<html>
<head>
<title>多星测控管理平台</title>
<jsp:include page="/ligerUI.jsp" />
<link href="<%=basePath%>mainPage/Aqua/main.css" rel="stylesheet" type="text/css" id="mainlink"/>
<script src="js/main.js" type="text/javascript"></script>
<script src="js/Base64.js" type="text/javascript"></script>
<script type="text/javascript">
	var firstPageCode = '<%=request.getParameter("firstPageCode")%>';
	var contuneOpen = '<%=request.getParameter("contuneOpen")%>';
	var secondMenuCode = '<%=request.getParameter("secondMenuCode")%>';
	var secondMenuText = '<%=request.getParameter("secondMenuText")%>';
	var menuUrl = '<%=request.getParameter("menuUrl")%>';
	var urlPath = '<%=basePath%>';
</script>
<style type="text/css">
	#framecenter{
		background:var(--grid-toolbar-color)/* #0D4A79 */;
	}
	.l-menubar-item{
		right:0;
	}
	.l-tab-content{
		overflow:hidden;
	}
	
</style>
<style type="text/css">
body{margin:0;padding:0;}
img,p{margin:0;padding:0;list-style:none;}
/*top*/
.top{width:100%;height:116px;background:url(images/bg.png) repeat-x;}
.top .left{height:116px;width:481px;float:left;background:url(images/bgLeft.png) repeat-x;}
.top .right{height:116px;width:485px;float:right;background:url(images/pic.png) no-repeat right top;}
.topContentRB img{float:left;display:block;padding-top:4px;}
.topContentRB p{float:left;color:#fff;height:50px;line-height:50px;font-size:15px;width:138px;/* text-align:center; */}
.topContentRB p a:link,.topContentRB p a:visited{color:#fff;text-decoration:none;}

.topContentRB .select{background:url(images/bg_2.png) no-repeat bottom;}
.topContentRB .over{background:#162A3F no-repeat bottom;}
.topContentRB .menulink{margin: 0px auto;}

.topContentRT{width:60%;height:47px;/* background:url(../images/bg_3.png) no-repeat top left; */float:left;padding-top:16px;}
.topContentRT .quit{width:30px;height:20px;line-height:20px;padding-left:25px;background:url(images/quit.png) no-repeat;float:right;}
.topContentRT .password{width:60px;height:20px;line-height:20px;padding-left:25px;background:url(images/password.png) no-repeat;float:right;color:#fff;}
.topContentRT .admin{width:70px;height:20px;line-height:20px;padding-left:25px;background:url(images/admin.png) no-repeat;float:right;color:#fff;}
.topContentRT .time{width:130px;height:20px;line-height:20px;padding-left:25px;background:url(images/time.png) no-repeat;float:right;color:#fff;}
.topContentRT a:link,.topContentRT a:visited{color:#fff;text-decoration:none;}

.m-second-menu{height:30px;position:absolute;top:0px;min-width:600px; z-index: 999999;display:none;}
.m-second-menu .m-second-menu-item{}
.m-second-menu .menulink{}
.m-second-menu .left{margin-top:2px; width:16px;height:28px;float:left;background:url(images/left.png) no-repeat;}
.m-second-menu .center{margin-top:2px; height:28px;float:left;background:url(images/center.png) repeat-x;}
.m-second-menu .center .title_2{height:28px;line-height:28px;padding:0 22px;float:left;}
.m-second-menu .center .this{height:28px;float:left;}
.m-second-menu .center .this .bg_left{height:28px;width:12px;float:left;}
.bg_left_over{background:url(images/bg_left.png) no-repeat right top;}
.m-second-menu .center .this .bg_center{height:28px;line-height:28px;padding:0 10px;float:left;color:#fff;font-size: 14px;}
.bg_center_over{background:url(images/bg_center.png) repeat-x;}
.m-second-menu .center .this .bg_right{height:28px;width:12px;float:left;}
.bg_right_over{background:url(images/bg_right.png) no-repeat right top;}
.m-second-menu .right{margin-top:2px; width:16px;height:28px;float:left;background:url(images/right.png) no-repeat;}
</style>
</head>
<body>
	<div class="wrap" style="position:relative;">
	    <div class="top">
	        <div class="left"><img src="images/name.png" /></div>
	        <div class="right">
	        </div>
	    </div>
	    
	    <div class="topContentRT" style="position:absolute;top:0px;right:15px;">
	        <div class="quit"><a href="javascript:loginOut()">退出</a></div>
	        <div class="password"><a href="javascript:alterPwd()">修改密码</a></div>
	        <div class="admin" id="userName">${sessionScope.LoginUser.userName}</div>
	        <div class="time">2015-07-20   10：23：02</div>
	    </div>
	    <div id="fistMenu" class="topContentRB" style="position:absolute;top:65px;left:170px;"></div>
	</div>
	<div id="layout1" style="width:100%;margin:0 auto; margin-top:0px; min-height: 300px;overflow: hidden;">
        <div position="center">
			<div id="framecenter" style="overflow: hidden;">
			</div>
		</div>
	</div>
</body>
</html>
