<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <title>状态字报警配置</title>
    <jsp:include page="/ligerUI.jsp" />
    <script src="<%=basePath %>resources/js/jquery-form/jquery.form.js" type="text/javascript"></script>
    <script src="js/statebit_rule_list.js" type="text/javascript"></script>
    <script src="js/ligerGrid.js" type="text/javascript"></script>
  	 <style type="text/css">
	  	.l-dialog-buttons{
	  		margin-top:0px;
	  		margin-bottom:0px;
	  	}
			.l-form {
			  margin: 0px;
			}
	  </style>
    <!--  
        <script type="text/javascript">
    		var ylCompId = '<%=request.getParameter("ylCompId")%>';
    </script>
    -->
  </head>
  <body style="overflow: hidden;">
    <div id="layout1">
            <div position="left" title="参数列表">
            	<!-- <div class="l-panel-search-box">
					<form id="form1" > 
					  	<table style="margin-left: 0px;width: 375px;" border=0>
					    	<tr>
					    		<td width="70px" align="right">
					    			卫星：
					    		</td>
					    		<td width="155px" >
					    			<input type="text" ltype="popup" id="sat_id" name="sat_id" />
					    		</td>
					    		<td colspan="2"  width="140px" >
					    		</td>
					    	</tr>
					    	<tr>
					    		<td width="130px" align="right">
					    			遥测参数：
					    		</td>
					    		<td>
					    			<input type="text" ltype="text" id="tm_param" name="tm_param" class="field" />
					    		</td>
					    		<td width="80px" align="center">
					    			<div id="searchbtn" >查询</div>
					    		</td>
					    		<td>
					    			<div id="resetbtn" align="center" >重置</div>
					    		</td>
					    	</tr>
					    </table>
					</form>
		   		</div> -->
		   		<div style="height: 60px; background-color: var(--grid-search-bg-color)">
						<form id="form1" style="margin-left: 10px;float:left;height: 60px"></form>
						<div style="padding-top: 31px;" >
								<div class="l-panel-search-item">
									<div id="searchbtn"></div>
								</div>
							
								<div class="l-panel-search-item">
									<div id="resetbtn"></div>
								</div>
							</div>
		   			<!-- <table width="100%">
		   				<tr>
		   					<td colspan="2">
		   					</td>
		   					<td>
		   						
		   					</td>
		   				</tr>
		   				<tr>
		   					<td colspan="1">
		   					
		   					</td>
		   				</tr>
		   			</table> -->
				</div>
		   		<div id="toptoolbar"></div> 
            	<div id="statebit_listgrid" style="margin:0; padding:0;"></div>
            </div>
            <div position="center" title="状态字列表" style="width:100%">
				<table id="state_bit_rule_table" style="width:100%" border="0">
			    	<tr>
				    	<td>
						    <div id="right_div" style="width: 99.9%;">
				    			<div class="l-panel-header" style="width: 99%;">
					    	         <table style="width:100%">
					    	         	<tr>
					    	         		<td width="80%">
					    	         			<span id="state_param_name" style="float: left;" ></span>
					    	         		</td>
					    	         		<td>
					    	         			<a href="javascript:openAddWin('add')">新增状态</a>
					    	         		</td>
					    	         	</tr>
					    	         </table>
				    	         </div>
				    	    </div>
				    	</td>
			    	</tr>
			    	<tr>
		    			<td valign="top" width="95%" colspan="2">
		    				<div id="statebit_grid" align="right"> </div>
				   	  	</td>
			    	</tr>
		    	</table>
            </div>  
     </div> 
    	
	 <form id="hideForm" name="hideForm" method="post" style="display: none;">
		<input type="hiden" name="stateInfoStr" id="stateInfoStr"></input>
	 </form>
  </body>
</html>
