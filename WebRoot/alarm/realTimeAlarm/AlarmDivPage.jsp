<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>实时报警页</title>
	<jsp:include page="/ligerUI.jsp" />
	<script src="js/AlarmDivPage.js" type="text/javascript"></script>
    <script type="text/javascript">
    		var pageId = '<%=request.getParameter("pageId")%>';
    </script>
	<style type="text/css">
		<!--
		.tit1 {
		float: left;
		width: 120px;
		height: 28px;
		border: 1px solid #eeeeee; 
		background:#BFE2EA;
		text-align: center;
		line-height: 28px;
		overflow: hidden;
		}
		.tit7 {
		float: left;
		width: 220px;
		height: 28px;
		border: 1px solid #eeeeee; 
		background:#BFE2EA;
		text-align: center;
		line-height: 28px;
		overflow: hidden;
		}
		.cont1 {
		float: left;
		width: 120px;
		height: 28px;
		border: 1px solid #eeeeee; 
		 
		text-align: center;
		line-height: 28px;
		overflow: hidden;
		}
		.cont7 {
		float: left;
		width: 220px;
		height: 28px;
		border: 1px solid #eeeeee; 
		text-align: center;
		line-height: 28px;
		overflow: hidden;
		}
		.alarm_1{
			display: block;
			width: 100px;
			height: 25px;
			line-height: 25px;
			color: #fff;
			background: #C00; overflow: hidden;
		}
		.alarm_2{
			display: block;
			width: 100px;
			height: 25px;
			line-height: 25px;
			color: #fff;
			background: #060;overflow: hidden;
		}
		.alarm_3{
			display: block;
			width: 100px;
			height: 25px;
			line-height: 25px;
			color: #fff;
			background:#069;overflow: hidden;
		}
		.alarm_4{
			display: block;
			width: 100px;
			height: 25px;
			line-height: 25px;
			color: #fff;
			background: #666;
			overflow: hidden;
		}
		-->
	</style>
	</head>
	<body>
		<div id="toptoolbar"></div>
		<div id="maingrid"></div>
		<div>
		 <div id="title_tb" style=" width:100% ; height:30px;" >
			 <div class="tit1">报警开始时间</div>
			 <div class="tit1">卫星名</div>
			 <div class="tit1">参数代号</div>
			 <div class="tit1">级别</div>
			 <div class="tit1">当前值</div>
			 <div class="tit1">报警值</div>
			 <div class="tit7">报警信息</div>
			 <div class="tit1">报警结束时间</div>
			 <div class="tit1">确认时间</div>
			 <div class="tit1">确认人</div>
		 </div>
		 
	 	<div id="lastPois" style=" width:100% ; height:30px;" >
			 <div class="cont1">--</div>
			 <div class="cont1">--</div>
			 <div class="cont1">--</div>
			 <div class="cont1">--</div>
			 <div class="cont1">--</div>
			 <div class="cont1">--</div>
			 <div class="cont7">--</div>
			 <div class="cont1">--</div>
			 <div class="cont1">--</div>
		  	 <div class="cont1">--</div>
		 </div>
		 </div>
		 
		</div>
	</body>
</html>
