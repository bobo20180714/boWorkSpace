<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>多星测控管理平台</title>
<jsp:include page="/ligerUI.jsp" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="js/index.js" type="text/javascript"></script>
</head>

<body>
<div class="wrap">
    <div class="top">
        <div class="topContentL">
       		<img src="images/name.png" />
        </div>
        <div class="topContentR">
        	<div class="topContentRT">
                <div class="quit"><a href="javascript:loginOut()">退出</a></div>
                <div class="password"><a href="javascript:alterPwd()">修改密码</a></div>
                <div class="admin">${sessionScope.LoginUser.userName}</div>
                <div class="time">2015-07-20   10：23：02.002</div>
            </div>
            <div id="fistMenu" class="topContentRB" style="position:absolute;top:65px;left:170px;min-width:1390px;">
            </div>
        </div>
    </div>
    <div class="center" id="centerMenu">
		<!-- <div class="one" id="monitor" >
      		<div><a href="javascript:toMain('monitor')"><img src="images/zhxxzs.png" width="155" height="137" /></a></div>
            <p><a href="javascript:toMain('monitor')">综合信息展示</a></p>
		</div>
		<div class="two" id="comput" >
      		<div><a href="javascript:toMain('comput')"><img src="images/kzljs.png" width="155" height="137" /></a></div>
            <p><a href="javascript:toMain('comput')">控制量计算</a></p>
		</div>
		<div class="three" id="query" >
      		<div><a href="javascript:toMain('query')"><img src="images/sjcx.png" width="155" height="137" /></a></div>
            <p><a href="javascript:toMain('query')">数据查询</a></p>
		</div>
		<div  class="three"  id="alarm" >
      		<div><a href="javascript:toMain('alarm')"><img src="images/zxjcbj.png" width="155" height="137" /></a></div>
            <p><a href="javascript:toMain('alarm')">在线监测报警</a></p>
		</div>
		<div class="five"  id="process" >
      		<div><a href="javascript:toMain('process')"><img src="images/jchjs.png" width="155" height="137" /></a></div>
            <p><a href="javascript:toMain('process')">进程监视</a></p>
		</div>
		<div class="one" id="control"  style="margin-top: 50px;">
      		<div><a href="javascript:toMain('control')"><img src="images/jchjs.png" width="155" height="137" /></a></div>
            <p><a href="javascript:toMain('control')">遥控处理</a></p>
		</div>
		<div class="two" id="plan"  style="margin-top: 50px;">
      		<div><a href="javascript:toMain('plan')"><img src="images/jchjs.png" width="155" height="137" /></a></div>
            <p><a href="javascript:toMain('plan')">任务规划</a></p>
		</div>
		<div class="two"  id="satConfig"  style="margin-top: 50px;">
      		<div><a href="javascript:toMain('satConfig')"><img src="images/wxxxpz.png" width="155" height="137" /></a></div>
            <p><a href="javascript:toMain('satConfig')">卫星信息配置</a></p>
		</div>
		<div class="three" id="base_info"  style="margin-top: 50px;">
      		<div><a href="javascript:toMain('base_info')"><img src="images/jcxxpz.png" width="155" height="137" /></a></div>
            <p><a href="javascript:toMain('base_info')">基础信息配置</a></p>
		</div>
		<div class="five" id="sys"   style="margin-top: 50px;">
        	<div><a href="javascript:toMain('sys')"><img src="images/ssxxxs.png" width="155" height="137" /></a></div>
            <p><a href="javascript:toMain('sys')">系统配置管理</a></p>
		</div> -->
	</div>
</div>
</body>
</html>
