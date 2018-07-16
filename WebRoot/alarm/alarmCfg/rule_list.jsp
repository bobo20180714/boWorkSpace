<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <title>报警规则管理</title>
    <jsp:include page="/ligerUI.jsp" />
    <script type="text/javascript" src="js/rule_list.js"></script>
    <script type="text/javascript" >
   		var rspType = pageName='<%=request.getParameter("type")%>';
    </script>
  </head>
  <body style="overflow: hidden;">
   	<div id="layout1" style="width:99.9%; margin:0;"> 
	    <div position="center" id="framecenter">
	         <div tabid="limitRule1" title="报警门限配置" >
	            <iframe frameborder="0" name="limitRule" id="limitRule" src="<%=basePath%>alarm/alarmCfg/alarm_rule_list.jsp"></iframe>
	         </div> 
	         <div tabid="stateRule2" title="状态参数配置" >
	            <iframe frameborder="0" name="stateRule" id="stateRule" src="<%=basePath%>alarm/alarmCfg/statebit_rule_list.jsp"></iframe>
	        </div> 
	    </div> 
	</div>
  </body>
</html>
