<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%String[] fileNames = new File(session.getServletContext().getRealPath("img/skin")).list(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
 	.nav1{
    	background:transparent;
    	border-width:0 !important;
    	box-shadow:none !important;
    	margin:0 2%;
    }
    .btn1 {
	  display: inline-block;
	  *display: inline;
	  padding: 4px 12px;
	  margin-bottom: 0;
	  *margin-left: .3em;
	  font-size: 14px;
	  line-height: 20px;
	  color: #fff;
	  text-align: center;
	  text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
	  vertical-align: middle;
	  cursor: pointer;
	  background-color: #4770AE;
	  *background-color: #e6e6e6;
	  background-image: -moz-linear-gradient(top, #ffffff, #e6e6e6);
	  background-image: -o-linear-gradient(top, #ffffff, #e6e6e6);
	  //background-image: linear-gradient(to bottom, #ffffff, #e6e6e6);
	  background-repeat: repeat-x;
	  border: 1px solid #cccccc;
	  *border: 0;
	  border-color: #e6e6e6 #e6e6e6 #bfbfbf;
	  border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	  border-bottom-color: #b3b3b3;
	  -webkit-border-radius: 4px;
	     -moz-border-radius: 4px;
	          border-radius: 4px;
	  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff', endColorstr='#ffe6e6e6', GradientType=0);
	  filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
	  *zoom: 1;
	  -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
	     -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
	          box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
	}
 </style>
</head>
<body>
	<%-- <div class="navbar navbar-fixed-top">
      <div id="nav1" class="navbar-inner nav1">
        <a class="brand" href="page/editor.jsp"><img src="img/logo.png"></a>
          <div class="btn-group pull-right" style="margin-top:20px;">
          	<% if (session.getAttribute("LoginUser")!=null) { %>
			    <button class="btn1">${loginInfo}</button>
		      	<button class="btn" data-toggle="modal" data-target="#HMLSZ"><i class="icon-envelope icon-white"></i>&nbsp;消息</button>
			  	<button class="btn" data-toggle="modal" data-target="#edit-dlg"><i class="icon-edit icon-white"></i>&nbsp;修改密码</button>
			  	<button class="btn" data-toggle="modal" data-target="#skin-dlg"><i class="icon-heart icon-white"></i>&nbsp;换肤</button>
			  	<button id="login-out" class="btn"><i class="icon-off icon-white"></i>&nbsp;退出</button>
			<% } else { %>
		      	<button class="btn" data-toggle="modal" data-target="#HMLSZ"><i class="icon-envelope icon-white"></i>&nbsp;消息</button>
			  	<button class="btn" data-toggle="modal" data-target="#skin-dlg"><i class="icon-heart icon-white"></i>&nbsp;换肤</button>
			<% } %>          	  
		  </div>        
      </div>
    </div>
<div id="mask" class="mask">
	<div>
		<div class="weui_loading">
            <div class="weui_loading_leaf weui_loading_leaf_0"></div>
            <div class="weui_loading_leaf weui_loading_leaf_1"></div>
            <div class="weui_loading_leaf weui_loading_leaf_2"></div>
            <div class="weui_loading_leaf weui_loading_leaf_3"></div>
            <div class="weui_loading_leaf weui_loading_leaf_4"></div>
            <div class="weui_loading_leaf weui_loading_leaf_5"></div>
            <div class="weui_loading_leaf weui_loading_leaf_6"></div>
            <div class="weui_loading_leaf weui_loading_leaf_7"></div>
            <div class="weui_loading_leaf weui_loading_leaf_8"></div>
            <div class="weui_loading_leaf weui_loading_leaf_9"></div>
            <div class="weui_loading_leaf weui_loading_leaf_10"></div>
            <div class="weui_loading_leaf weui_loading_leaf_11"></div>
        </div>
        <p class="weui_toast_content">加载中···</p>
	</div>
</div>
<div id="edit-dlg" class="modal hide fade" style="width: 340px; margin-left: -170px;">
	<div class="modal-header" style="border-bottom: 1px solid #04c">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<h3 style="color: #04c;">修改密码</h3>
	</div>
	<div class="modal-body">
		<p style="font-size: 14px;">
			<span>*</span>原密码：<input type="password">
		</p>
		<p style="font-size: 14px;">
			<span>*</span>新密码：<input type="password">
		</p>
		<p style="font-size: 14px;">
			<span>*</span>确认密码：<input type="password">
		</p>
		<p name=err></p>
	</div>
	<div class="modal-footer" style="border-top: 1px solid #04c">
		<a class="btn btn-primary">确定</a> <a data-dismiss="modal" class="btn btn-primary">取消</a>
	</div>
</div>
<div id="skin-dlg" class="modal hide fade" style="width: 510px; margin-left:-255px;">
	<div class="modal-header" style="border-bottom: 1px solid #04c">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<h3 style="color: #04c;">更换皮肤</h3>
	</div>
	<div class="modal-body" style="height:240px;overflow-y:auto">
		<ul class="thumbnails">
		  <%for (String name : fileNames){ %>
		  <li class="span2">
		    <a href="javascript:;" class="thumbnail">
		      <img src="img/skin/<%= name %>" alt="" style="width:130px;height:100px;">
		    </a>
		  </li>
		  <%}%>
		</ul>
	</div>
</div> --%>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript">
var url=getCookie('bg');
url=url?url:'img/skin/bg.jpg';
$(document.body).css({
	//backgroundImage:'url('+url+')'
});
$('#skin-dlg img').click(function(){
	var url=$(this)[0].src;
	$('body').css({
		backgroundImage:'url('+url+')'
	});
	setCookie('bg',url);
	$('#skin-dlg .close').click();	
});
$('#login-out').click(function(){
	location.href="login.jsp";
});
$('#edit-dlg a').click(function(){
	var inputs=$('#edit-dlg input');
	var psd0=inputs.eq(0).val().trim();
	var psd1=inputs.eq(1).val().trim();
	var psd2=inputs.eq(2).val().trim();
	var err=$('#edit-dlg p[name=err]');
	if(psd0==''){
		err.text('原密码不能空！');
		return;
	}
	if(psd1==''){
		err.text('新密码不能空！');
		return;
	}
	if(psd2==''){
		err.text('确认密码不能空！');
		return;
	}
	if(psd1.length<6){
		err.text('密码至少6位！');
		return;
	}
	if(psd1.indexOf(' ')>-1){
		err.text('密码不能包含空格！');
		return;
	}
	if(psd1!=psd2){
		err.text('两次密码输入不一致！');
		return;
	}
	err.text('');
	showMask();
	$.ajax({
		url:"login/editPsd.do",
		type:"post",
		data:{
			oldPsd:psd0,
			newPsd:psd1
		},
		success:function(json){
			hideMask();
			if(json=='T'){
				$('#edit-dlg').modal('hide');
			}
			else{
				err.text(json);
			}
		}
	});
});
$('#edit-dlg').on('show',function(){
	$('#edit-dlg p[name=err]').text('');
});
</script>
</body>
</html>