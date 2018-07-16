<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title>航天器配置</title>
		<jsp:include page="/ligerUI.jsp" />
		<script src="js/TmParamManagerIndex.js" type="text/javascript"></script>
		<style type="text/css">
			.l-panel-search-box{
				padding:0px;
			}
			.l-form {
			  margin: 0px;
			}
				.l-dialog-win .l-dialog-content{
					line-height: 30px;
				}
		</style>
	</head>
	<body oncontextmenu="return false" >

 <div id="layout1" style="overflow: hidden;">
		  <div position="left" title="【航天器】信息" >
		    <div id ="scollDivTree" class="l-tree-body">
		      <div id="tree1" ></div>
		    </div>
		  </div>
		 <div position="center" >
			 <!-- <table width="100%" style="background-color: var(--bg-color);">
			 	<tr>
			 		<td >
						<form id="form2" > 
							<input id="owner_id" name="owner_id" type="hidden" />
						</form>
						<div style="padding-top: 9px;" >
							<div class="l-panel-search-item" >
								<div id="searchbtn"></div>
							</div>
							<div class="l-panel-search-item">
								<div id="resetbtn"></div>
							</div>
						</div>
					</td>
				</tr>
			</div>
			</table> -->
			<div style="height: 30px; background-color: var(--grid-search-bg-color)">
				<form id="form2" style="margin-left: 10px;float:left;height: 30px">
					<input id="owner_id" name="owner_id" type="hidden" />
				</form>
				<div style="padding-top: 3px;" >
					<div class="l-panel-search-item">
						<div id="searchbtn"></div>
					</div>
				
					<div class="l-panel-search-item">
						<div id="resetbtn"></div>
					</div>
				</div>
			</div>
			<div id="toptoolbar"></div> 
		    <div id="maingrid" style="margin:0; padding:0">
		    </div>
		</div>  
	</body>

</html>
