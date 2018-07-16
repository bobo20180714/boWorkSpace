<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.x-tool{
	position:absolute;
	top:60px;
	margin:5px 2%;
	width:96%;
	background-color: #4770AE;
	-webkit-border-radius:4px;
	border: 1px solid #CCC;
}
.x-tool .border{
	border-width:0 !important;
	color:#999;
}
.x-tool .select{
	color:#fff;
}
.btn{
	text-shadow:none;
}
</style>
<!-- <div class="btn-group x-tool">
	<button name="editor" class="btn border" onclick="location.href='page/editor.jsp'"><i class="x-icon-5"></i>&nbsp;页面配置</button>
	<button name="monitor" class="btn border" onclick="location.href='page/monitor.jsp'"><i class="x-icon-4"></i>&nbsp;监视</button>
	<button name="lib" class="btn border" onclick="location.href='page/lib.jsp'"><i class="x-icon-1"></i>&nbsp;图元库</button>
	<button name="dynamic" class="btn border" onclick="location.href='page/dynamic.jsp'"><i class="x-icon-2"></i>&nbsp;动态图元</button>  
</div> -->
<script type="text/javascript">
var url1=location.href+'';
$('button[name='+(url1.split('page/')[1].replace(/\.jsp.*/,''))+']').addClass('select');
</script>