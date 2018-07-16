<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>test</title>
<script type="text/javascript">
	function openQuery(){
		$.ligerDialog.open( 
	            { 
		          	 width: 550,
					 height:410, 
	                title:"查询条件",
					url: basePath+"noParamData/dataQuery/query_condition.jsp",
					buttons :[
			            { text: '保存', width: 60, onclick:function(item, dialog){
			            	var formData = dialog.frame.getData();
							window.open(basePath+"noParamData/dataQuery/data_list.jsp?relateId="+formData.relateName);
			            	dialog.close();
			            }},
			            { text: '关闭', width: 60, onclick:function(item, dialog){
			            	dialog.close();
			            }}
				 ]
	            });
	}
</script>
<jsp:include page="/ligerUI.jsp" />
</head>
<body style="overflow: hidden;">
    <button onclick="openQuery()">test</button>
</body>

</html>
