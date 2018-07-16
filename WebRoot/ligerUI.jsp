<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
   <!-- 
    <link href="<%=basePath%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="" rel="stylesheet" type="text/css" />
     
     			"aqua": "lib/ligerUI/skins/Aqua/css/ligerui-all.css",
                "gray": "lib/ligerUI/skins/Gray/css/all.css",
                "silvery": "lib/ligerUI/skins/Silvery/css/style.css",
                "gray2014": "lib/ligerUI/skins/Gray2014/css/all.css"
     --> 
    <link href="<%=basePath%>Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" id="mylink"/>
    <link href="<%=basePath%>lib/search-box.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> 

    <script src="<%=basePath%>lib/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/core/inject.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerAccordion.js" type="text/javascript"></script> 
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerCheckBoxList.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerEasyTab.js" type="text/javascript"></script> 
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerFilter.js" type="text/javascript"></script> 
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script> 
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerListBox.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerMenuBar.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerMessageBox.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerPanel.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerPopupEdit.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerPortal.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerRadio.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerRadioList.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerSpinner.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerTab.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerTip.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerSatTree.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/ligerUI/js/plugins/ligerWindow.js" type="text/javascript"></script>
    
    
    <script src="<%=basePath%>lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=basePath%>lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
     <script src="<%=basePath%>lib/jquery-validation/validate_function.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
    
    <script src="<%=basePath%>lib/submitForm.js" type="text/javascript"></script>
	<script src="<%=basePath%>lib/LG.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/json2.js" type="text/javascript"></script>
    <script src="<%=basePath%>lib/Alert.js" type="text/javascript"></script>
    
    <script type="text/javascript">
    	var basePath='<%=basePath%>';
    	function byId(id){
    		return document.getElementById(id);
    	}
    </script>
