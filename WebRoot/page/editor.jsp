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
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="colorpicker/css/jquery.bigcolorpicker.css">
<link rel="stylesheet" href="css/resize.css">
<link rel="stylesheet" href="page/css/editor.css">
<style type="text/css">
	.l-dialog-win .l-dialog-content {
		    padding-top: 0px;
		    margin-left: 0px;
		    padding-left: 0px;
		}
	.l-layout-content{
	    background-color: #E3F3FF;
	}
/* 	.l-toolbar-item-hasicon{
		width: 15px;
     padding-left: 15px;
	} */
	.l-bar-separator{
		margin-top:6px;
	}
		.l-panel-topbar{
	    width: 100%;
	    height: 28px;
	    background-color: #E3F3FF;
	}
	.l-accordion-content{
		background-color: #E3F3FF;
	}
	.l-dialog-inputtext{
		margin-left:12px;
		margin-top: 5px;
	}
	.l-tab-links{
		display: none;
	}
	.x-accordion-margin{
		width: 200%;
	}
			.l-panel{
			border:1px solid #AECAF0;
		}
		.pageButton{
			width:30px;
			    overflow: hidden;
			    cursor: pointer;
			    position: relative;
			    text-align: center;
			    border: solid 1px #3789C3;
			    color: #F3F7FC;
			    background: #3789C3;
		}
		.l-dialog-win .l-dialog-content{
		line-height: 30px;
	}
	.l-menu-item-icon {
    	left: 5px;
    	top: 4px;
    }
    div[class=l-tree] span{
    	font-size: 14px;
    }
</style>
</head>
<body>
	<jsp:include page="/page/head.jsp"></jsp:include>    
    <jsp:include page="/page/nav.jsp"></jsp:include>
	<div class="main">
		<div id="layout">
            <div position="left" title="工作空间">
            	<div style="overflow:auto;height: 100%;"  class="l-tree-body"><div class="l-tree"></div></div>
            </div>
            <div position="center">
            	<div id="navtab" style="padding:0;margin:0;" class="liger-tab"></div>
            </div>            
        </div> 
	</div>
<input type="file" id="jar-file" name="upfile" style="display:none"/>

<script type="text/html" id="tpl_content">
<div class="l-layout-header" id="editAreaText"></div>
<div class="content">
	<div position="left">
		<div name="accordion"></div>
	</div>
	<div class="bg" position="center">
		<div name="toolbar"></div>
		<div class="abs resize-container"></div>
		<div class="abs doc-title"></div>
	</div>
	<div position="right"  title="属性">            
	  	<table style="margin-left:5px;">
	  		<tr class="tr">
				<td style="width:90px;" align="left" >数据源：</td>
				<td style="width:98px;"><input name="dataSource"></td>
				<td align="center"><input type="button" onclick="removeSource()" value="移除" class="pageButton"/></td>
			</tr>
			<tr class="tr">
				<td align="left">是否显示测站：</td>
				<td colspan="2"><input type="checkbox" id="isShowDevice" onchange="showDeviceChange()"/></td>
			</tr>
	  		<tr class="tr">
				<td align="left" >图元名称：</td>
				<td name="graphBind" width="128px;" colspan="2"></td>
			</tr>
	  		<tr class="tr">
				<td align="left" valign="top">图元描述：</td>
				<td align="left" colspan="2"><div name="commentGraph" class="comment"></div></td>
			</tr>
	  	</table>
	  	<div class="btn-bind">
	  		<div name="bindButton" class="left"></div>
	  	</div>            	          	
	</div>
</div>
</script>
<script src="ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerTab.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerSatTree.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerAccordion.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerPopupEdit.js" type="text/javascript"></script> 
<script src="ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
<script type="text/javascript" src="ligerUI/js/plugins/ligerDrag.js"></script>
<script type="text/javascript" src="js/jqueryrotate.js"></script>
<script type="text/javascript" src="js/jquery.timers-1.1.2.js"></script>
<script type="text/javascript" src="colorpicker/js/jquery.bigcolorpicker.js"></script>
<script type="text/javascript" src="js/resize.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript" src="js/bind.js"></script>
<script type="text/javascript" src="page/js/editor.js"></script>
<script src="lib/Alert.js" type="text/javascript"></script>
</body>
</html>