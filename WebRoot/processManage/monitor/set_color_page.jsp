<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <title>颜色配置</title>
    <jsp:include page="/ligerUI.jsp" />
		<jsp:include page="/css.jsp" />
    <script src="js/set_color_page.js" type="text/javascript"></script>
    <script src="js/iColorPicker.js" type="text/javascript"></script>
    <style type="text/css">
        body{
            color:black;
            font-size:12px;
            text-align:center;
        }
        #Color{
            width:100%;
            border-layout:fixed;
            border-collapse:collapse;
        }
        #Color tr{
            height:30px;
        }
        #Color td{
            border:solid 1px #A3C0E8;
        }
     </style>
</head>
<body >
    <table style="width:80%;margin-left: 50px;">
        <tr style="height:10px;">
            <td>
            </td>
        </tr>
        <tr>
            <td>
                <table id="Color">
                    <tr style="background-color: #E3EDF9">
                        <td style="width:160px;">状态</td><td style="width:220px;">颜色</td>
                    </tr>
                    <tr>
                        <td>空闲</td><td><input id="color_0" name="color_0" readonly type="text" value="#9d382d" class="iColorPicker" /></td>
                    </tr>
                    <tr>
                        <td>工作中</td><td><input id="color_1" name="color_1" readonly type="text" value="#313199" class="iColorPicker" /></td>
                    </tr>
                    <tr>
                        <td>暂停</td><td><input id="color_2" name="color_2" readonly type="text" value="#9d922d" class="iColorPicker" /></td>
                    </tr>
                    <tr>
                        <td>停止</td><td><input id="color_3" name="color_3" readonly type="text" value="#000000" class="iColorPicker" /></td>
                    </tr>
                    <tr>
                        <td>异常</td><td><input id="color_4" name="color_4" readonly type="text" value="#f00" class="iColorPicker" /></td>
                    </tr>
                    <tr>
                        <td>未知</td><td><input id="color_5" name="color_5" readonly type="text" value="#000000" class="iColorPicker" /></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr style="height:10px;">
            <td>
            </td>
        </tr>
        <tr style="height:20px;">
            <td>
<!--                <input id="Confirm" type="button" value="确定" style="width: 50px" onclick="Confirm()"/>&nbsp; &nbsp; &nbsp; &nbsp;-->
<!--                <input id="Cancel" type="button" value="取消" style="width: 50px" onclick="Cancel()"/>-->
            </td>
        </tr>
    </table>
   <!--  <span style="font-size: 18;margin-left: 5px;">说明：本页面用于不同报警级别的报警显示颜色设置。</span> -->
</body>
</html>
