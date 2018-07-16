<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <title>设置报警级别颜色</title>
    <jsp:include page="/ligerUI.jsp" />
    <script src="js/alarm_define_page.js" type="text/javascript"></script>
    <style type="text/css">
         .middle input {
             display: block;width:30px; margin:2px;
         }
   </style>    
</head>
<body>
    <br/>
    
    <table>
    <tr>
    <p align="center">自定义报警设置</p>
    </tr>
    <tr>
    <td>
        <div style="margin:4px;float:left;">
	          <p align="left">卫星信息</p>
	          <div id="listboxSatInfo"></div>  
	    </div>
	  </td>
	  <td valign="middle">
	      <!--  div style="margin-top:120px; margin:4px; float:left;" class="middle">-->
	      <div class="middle">
	           <input type="button" onclick="moveToRight()" value="-->" />
	           <input type="button" onclick="moveToLeft()" value="<--" />
	           <input type="button" onclick="moveAllToLeft()" value="<<--" />
	      </div>
	  </td>
	  <td>
	     <div style="margin:4px;float:left;">
	         <p align="left">报警卫星信息</p>
	         <div id="listboxSelectedSatInfo"></div> 
	     </div>
	  </td>
    </tr>
    <tr>
    <td>
         <div>
	        <label for="customDefineAlarm">输入自定义报警名称：</label>
			<input type="text" ltype="text" id="customDefineAlarm" name="clientele_name"   class="field l-text-field" />
	     </div>
    </td>
    </tr>
    <tr>
    <td>
    <div id="confirmbtn"
			style="margin-top: 20px; margin-left: 80px; float: left;"
			class="l-dialog-btn" onclick="SaveDefineInfo()" align="center">
			保存
		</div>
		<div id="confirmbtn"
			style="margin-top: 20px; margin-left: 20px; float: left;"
			class="l-dialog-btn" onclick="cancel()" align="center">
			返回
		</div>
    </td>
    </tr>
    </table>

    <p><span style="font-size: 18;margin-left: 5px;">说明：本页面用于不同报警级别的报警显示颜色设置。</span></p>
</body>
</html>
