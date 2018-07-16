<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>关联查询</title>
    <link rel="stylesheet" type="text/css" href="colorpicker/css/jquery.bigcolorpicker.css">
    <jsp:include page="/ligerUI.jsp" />
    <script type="text/javascript" src="layout/layout.js"></script>
    <script type="text/javascript" src="satTree/satTree.js"></script>
    <script type="text/javascript" src="tmGrid/tmGrid.js"></script>
    <script type="text/javascript" src="relateGrid/relateGrid.js"></script>
    <script type="text/javascript" src="linearManage/linearManage.js"></script>
    <script type="text/javascript" src="queryData/queryLineData.js"></script>
    <script type="text/javascript" src="uuid/uuid.js"></script>
    <script type="text/javascript" src="cache/lineCache.js"></script>
    <script type="text/javascript" src="cache/relateInfoCache.js"></script>
    <script type="text/javascript" src="listQuery/listQuery.js"></script>
    <script type="text/javascript" src="colorpicker/js/jquery.bigcolorpicker.js"></script>
    
    <link rel="stylesheet" type="text/css" href="ext/resources/css/ext-all.css">
    <script type="text/javascript" src="ext/ext-all-debug.js"></script>
    <script type="text/javascript" src="ext/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="ext/theme/include-ext.js"></script>
    <link  rel="stylesheet"  href="ext/src/ux/example.css"/>
	<script type="text/javascript" src="ext/src/ux/examples.js"></script>    
    <script type="text/javascript" src="query/lineShow.js"></script> 
     
    <script type="text/javascript" src="bean/lineBean.js"></script>
    <script type="text/javascript" src="bean/relateLineBean.js"></script>
    
    <script type="text/javascript" src="query/app.js"></script>
    <script type="text/javascript" src="js/CharUtil.js"></script>
    
    <script type="text/javascript" src="lineQuery/queryToolBar.js"></script>
    
      <style type="text/css">
    	.td{
    		height: 15px;
    	}
    	.d{
    		position: relative;
    		left: 15px;
    	}
    	.l-toolbar-item-hasicon {
    		padding-left: 18px;
		}
		.l-panel-btn{
			margin-left: 0;
			
		}
		.l-table-nocheckbox{
			font-size: 3;
		}
		.l-form{
			margin:0px;
		}
		.l-dialog-content-noimage{
			padding-left:0px;
		}
    </style>
  </head>
  
  <body oncontextmenu="return false" onselectstart="return false">
    <div id="mainLayout" style="overflow-y: hidden;overflow-x:auto; ">
    	<div position="left" title="选择区域">
    		<div id="selectAreaTab" style="height: 300px;">
    			<!-- 选择卫星区域 -->
				<div title="卫星" lselected="true">
					<div style="height: 260px;">
						<div style="height: 30px;background-color: var(--grid-search-bg-color);">
					 		<div style="float:left;height: 30px;width: 175px;padding-left: 10px;" >
					 			<div style="width: 60px;height:30px;float: left;line-height: 30px;">
					 				关键字：
				 				</div>
					 			<div style="width: 100px;height:30px;float: left;margin-top: 4px;">
					 				<input type="text" id="satKeyInput" name="satKeyInput" />
				 				</div>
					 		</div>
					 		<div style="padding-top: 6px;height: 25px;width: 20px;float: left;position: relative;"  >
								<div id="satKeySearch"  style="background-image:url('<%=basePath %>relateQuery/img/search.png');width:20px;height:20px;background-repeat: no-repeat;"></div>
							</div>
						</div>
			        	<div style="overflow: auto;" class="l-tree-body"> 
			            	<div id="satTree"></div> 
			        	</div> 
					</div>
		    		 
				</div>
			</div>
			<!-- 遥测参数区域 -->
			<div style="margin-top: -9px;">
				<div style="height: 30px;background-color: var(--grid-search-bg-color);">
					<div style="float:left;height: 30px;width: 175px;padding-left: 10px;" >
			 			<div style="width: 60px;height:30px;float: left;line-height: 30px;">
			 				关键字：
		 				</div>
			 			<div style="width: 100px;height:30px;float: left;margin-top: 4px;">
			 				<input type="text" id="paramKeyInput" name="paramKeyInput" />
		 				</div>
			 		</div>
			 		<div style="padding-top: 6px;height: 25px;width: 20px;float: left;position: relative;"  >
						<div id="paramKeySearch"  style="background-image:url('<%=basePath %>relateQuery/img/search.png');width:20px;height:20px;background-repeat: no-repeat;"></div>
					</div>
				</div>
				<div id="paramGrid">
    			</div>
    			
			</div>
			<!-- 航天器相关信息区域 -->
			<div id="relateInfoDiv">
				<div class="l-layout-header" >航天器相关信息</div>
		   		<div id="relateGrid">
		   		</div>
			</div>
    	</div>
        <div  position="center"  style="min-width: 1266px;">
        	<div id="showDataTab">
        		<!-- 曲线查询区域 -->
				<div tabid="lineQuery" title="曲线查询">
					<div id="queryToolBar"></div>
					<div id="RelationChart-PEl-body"  style="overflow: auto; width: 1656px; height: 531px; left: 0px; top: 0px;">
						<div id="RelationChart-PEl-innerCt" class="x-box-inner " role="presentation" style=" width: 100%;height:100%; ">
							<div id="RelationChart-PEl-targetEl" class="x-box-target" style="width: 1656px;">
								<div class="x-panel x-box-item x-panel-default" id="RelationChart" style="right: auto; left: 0px; top: 0px; margin: 0px; height: 531px; width: 1656px;">
									<div id="RelationChart-body" style="width: 100%;height:100%; left: 0px; top: 0px;">
										<span id="RelationChart-outerCt" style="display: table; width: 100%; table-layout: fixed; height: 100%;">
											<div id="RelationChart-innerCt" style="display:table-cell;height:100%;vertical-align:top;" class="">
											</div>
										</span>
									</div>
								</div>
							</div>
						</div>
					</div> 
				</div>
				<!-- 列表查询区域 -->
				<div tabid="listQuery" title="列表查询" >
					
					<!-- 显示工程值数据 -->
					<!-- <div id="listDataTab">
						<div tabid="listGridTab" title="">
							<div id="dataListGrid"></div>
						</div>
					</div> -->
					<!-- <div style="height: 30px;background-color: var(--grid-search-bg-color);">
						<div id="queryType"></div>
					</div>
					<div id="listGridTab" title="">
						<div id="dataListGrid"></div>
					</div> -->
				
					<!-- 航天器相关信息统计 -->
				<!-- 	<div id="relateDataGridTr">
						<div class="l-layout-header" >航天器相关信息查询统计</div>
						<div id="relateDataGrid"></div>
					</div> -->
				</div>	
			</div>
	    </div>
	    <!-- 线型管理区域 -->
       <div position="centerbottom">
      	 <div id="lineManagerTab">
       		<div title="线型管理" >
				<div id="lineManagerGrid">
    			</div>
			</div>
       	 </div>
       </div>
    </div>
    <!--显示线宽的窗口  -->
    <div id="lineWidthWin" style="z-index:1000;position:absolute;width: 100px;height: 100px;display: none;background-color: white;">
    	<table>
    		<tr>
    			<td  onclick="selectLineWidth(this)" class="td" lineHeight=1 >
    				<div class="d" style="width:70px;height:1px;background-color: gray;">
    				</div>
    			</td>
    		</tr>
    		<tr>
	    		<td  onclick="selectLineWidth(this)" class="td" lineHeight=2>
	    			<div class="d" style="width:70px;height:2px;background-color: gray;">
	    			</div>
	    		</td>
    		</tr>
    		<tr>
	    		<td  onclick="selectLineWidth(this)" class="td" lineHeight=3>
	    			<div class="d" style="width:70px;height:3px;background-color: gray;"></div>
	    		</td>
    		</tr>
    		<tr>
	    		<td  onclick="selectLineWidth(this)" class="td" lineHeight=4>
	    			<div class="d" style="width:70px;height:4px;background-color: gray;"></div>
	    		</td>
    		</tr>
    		<tr>
	    		<td  onclick="selectLineWidth(this)" class="td" lineHeight=5>
	    			<div class="d" style="width:70px;height:5px;background-color: gray;" ></div>
	    		</td>
    		</tr>
    		<tr>
	    		<td  onclick="selectLineWidth(this)" class="td" lineHeight=6>
	    			<div class="d" style="width:70px;height:6px;background-color: gray;" ></div>
	    		</td>
    		</tr>
    	</table>
    	<input type="hidden" id="selectLineId" />
    	
    </div>
  </body>
</html>
