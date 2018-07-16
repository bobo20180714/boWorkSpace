<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>多星报警页面管理</title>
	<jsp:include page="/ligerUI.jsp" />
	<script src="js/multipleManageSet.js" type="text/javascript"></script>
	<script  type="text/javascript">
	<%
		String opt="";
		String pageId="";
		String pageName="";
		if(request.getParameter("opt")!=null){
			opt=(String)request.getParameter("opt");
		}
		if(request.getParameter("pageId")!=null){
			pageId=(String)request.getParameter("pageId");
		}
		if(request.getParameter("pageName")!=null){
			pageName=(String)request.getParameter("pageName");
		}
		
	%>
	var pageId='<%=pageId%>';
	var opt='<%=opt%>';
	var pageName='<%=pageName%>';
	</script>
	</head>
	<style type="text/css">
           .middle input {
              display: block;width:30px;margin: 4px;
                  height: 23px;
			    overflow: hidden;
			    line-height: 23px;
			    cursor: pointer;
			    position: relative;
			    text-align: center;
			    border: solid 1px #3789C3;
			    color: #F3F7FC;
			    background: #3789C3;
         }
		.l-panel{
			border:1px solid #AECAF0;
		}
     </style>
	<body>
	 <div style="margin-top:20px;margin-left:8px; width:410px; height: 30px; ">
	 		<form name="form1" method="post" id="form1"></form>
     </div>
     <table style="margin-left: 4px;margin-top: 5px;">
     	<tr>
     		<td valign="top" align="right">
     			<div style="width:100px;">勾选卫星：</div>	
     		</td>
     		<td>
     			<div id="leftGrid"></div>	
     		</td>
     	</tr>
     </table>
	</body>
</html>
