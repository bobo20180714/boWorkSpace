<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>数据授权</title>
	<jsp:include page="/ligerUI.jsp" />
	<jsp:include page="/css.jsp" />
	<script src="js/multipleManageSet.js" type="text/javascript"></script>
	<script  type="text/javascript">
		var ug_id = '<%=request.getParameter("ug_id")%>';
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
	 <div style="margin:4px;margin-top:10px;margin-left:20px;float:none; width:600px; height: 260px;">
		  <div style="margin:4px;float:left;">
		  	  <div>卫星数据列表</div>	
	          <div id="listbox1">
	           		<div id="leftGrid"></div>
	          </div>  
	      </div>
	      <div style="float:left; margin-top: 150px;" class="middle">
	           	<input type="button" onclick="moveToRight()" value=">" />
	           	<input type="button" onclick="moveToLeft()" value="<" />
	          	<input type="button" onclick="moveAllToRight()" value=">>" />
	           	<input type="button" onclick="moveAllToLeft()" value="<<" />
	      </div>
	     <div style="margin:4px;float:left;">
	    	 <div>已授权卫星</div>	
	         <div id="listbox2">
	         	<div id="rightGrid"></div>
	         </div> 
	     </div>
     </div>
	</body>
</html>
