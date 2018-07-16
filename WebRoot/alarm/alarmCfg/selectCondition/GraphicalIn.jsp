<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<!-- 
		@author 孟祥超
		@date 2015.8.7
	 -->
	<title>条件拼接</title> 
	<jsp:include page="/ligerUI.jsp" />
	<script src="<%=basePath %>resources/js/jquery-form/form-init.js"	type="text/javascript"></script>
	<script src="js/GraphicalIn.js" type="text/javascript"></script>
	<script type="text/javascript">
		var basePath = '<%=basePath %>';
	</script>
	<style type="text/css">
		.l-text-readonly {
			border-bottom: 0px; 
			border: 1px solid #AECAF0;
		}
		
		.button-class{
			    display: block;
		    width: 20px;
		    margin: 4px;
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
	</style>
</head>
<body>
	<div id="frameTab">
        <div tabid="imgInput" title="图形化输入" style="height:100%;" >
        	<div style="height: 300px;overflow:auto;margin:5px;border: 1px solid #AECAF0;">
	            <form name="form_1" method="post" id="form_1" enctype="multipart/form-data">
					<table id="conditionTable">
						<tr id="tr_1" style="height: 30px;">
							<td>
								1
							</td>
							<td width="190">
								<input name="device_input_1" id="device_input_1" type="text" ltype="popup" 
									validate="{required:true}" />
							</td>
							<td width="190">
								<input name="device_param_1" id="device_param_1" type="text" 
									validate="{required:true}" readonly="readonly"/>
								<input name="device_param_1_val" id="device_param_1_val" type="hidden"/>
							</td>
							<td width="112">
								<input name="condition_type_1" id="condition_type_1" type="text" ltype="select" validate="{required:true}" />
							</td>
							<td width="82">
								<input name="device_value_1" id="device_value_1" type="text" 
									validate="{required:true,number:true}" 
									style="width: 70px;" ltype="text" lwidth="70"/>
							</td>
							<td id="and_td_1" width="18">
								<div id="btn_and_1" class="button-class" 
									onclick="btnAddFun('1',1)" align="center" title="与"
									style="width: 17px;height: 20px;margin-left: 2px;">
									与
								</div>
							</td>
							<td id="or_td_1" width="18">
								<div id="btn_or_1" class="button-class" 
									onclick="btnAddFun('2',1)" align="center" title="或"
									style="width: 17px;height: 20px;margin-left: 0px;">
									或
								</div>
							</td>
							<td width="18" align="center">
								<div id="btn_delete_1" class="button-class" 
									onclick="deleteFun(1)" align="center"
									style="width: 17px;height: 17px;padding-top: 3px;margin-left: 0px;margin-right: 5px;">
									<img src="<%=basePath %>resources/images/delete.png"/>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div>
				<form id="sqlForm"></form>
			</div>
			<div class="l-dialog-buttons" style="margin-top: 15px;padding-top: 7px;">
				<div id="confirmbtn"
					style="margin-left: 470px; float: left;"
					class="l-dialog-save-btn" onclick="saveSql()" align="center">
				</div>
				<div id="" style="margin-left: 5px; float: left;"
					class="l-dialog-close-btn" onclick="closeWin()" align="center">
				</div>
			</div>
        </div> 
        <div tabid="scriptInput" title="脚本输入" style="height:100%;" >
            <form id="sqlFormSI">
            	<table width="100%" >
            		<tr style="line-height: 20px;">
            			<td width="57px;" align="right">
            				脚本输入
            			</td>
            			<td>
            				&nbsp;
            			</td>
            		</tr>
            		<tr>
            			<td align="center" colspan="2">
            				<textarea id="sqlArea" rows="5" cols="10" 
            					style="margin-left:5px;height: 160px;width: 98%;border-color: #AECAF0"></textarea>
            			</td>
            		</tr>
            		<tr style="line-height: 20px;">
            			<td width="57px;" align="right">
            				校验反馈
            			</td>
            			<td>
            				&nbsp;
            			</td>
            		</tr>
            		<tr>
            			<td align="center" colspan="2">
            				<textarea id="resultArea" rows="5" cols="10" style="margin-left:5px;height: 90px;width: 98%;border-color: #AECAF0"></textarea>
            			</td>
            		</tr>
            	</table>
            </form>
            <div class="l-dialog-buttons" style="margin-top: 57px;padding-top: 7px;" >
				<div id="confirmbtn"
					style="margin-left: 560px; float: left;"
					class="l-dialog-save-btn" onclick="query()" align="center">
				</div>
			</div>
        </div> 
    </div> 
</body>
</html>