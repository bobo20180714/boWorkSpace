//订单日志表格对象
var orderLogGridObj = null;

$(function(){
	//初始化时间控件
	initTimeInput();
	//初始化查询、重置
	initQueryBar();
	//初始化日志表格
	initLogGrid();
});

//初始化时间控件
function initTimeInput(){
	$("#orderId").ligerTextBox({
		width:146
	});
	var nowDate = new Date();
	var startTime = nowDate.getTime() - 24*60*60*1000;
	var endTime = nowDate.getTime() + 24*60*60*1000;
	$("#startTime").ligerDateEditor({
		showTime:true,
		width:146,
		value:new Date(startTime)
	});
	$("#endTime").ligerDateEditor({
		showTime:true,
		width:146,
		value:new Date(endTime)
	});
}


//初始化查询、重置
function initQueryBar(){
	$("#searchbtn").ligerButton({
		click : function() {
			orderLogGridObj.set('parms', {
				orderId : $("#orderId").val(),
				startTime : $("#startTime").val(),
				endTime : $("#endTime").val()
			});
			orderLogGridObj.loadData();
		}
	});
	
	// 重置搜索条件
	$("#resetbtn").ligerButton({
		click : function() {
			$("#orderId").val("");
			$("#startTime").val("");
			$("#endTime").val("");
		}
	});
}

//初始化日志表格
function initLogGrid() {
	orderLogGridObj = $("#logGrid").ligerGrid({
		columns : [
		           {
		        	   display : '时间',
		        	   name : 'time',
		        	   align : 'center',
		        	   width : 200
		           },
		           {
		        	   display : '订单编号',
		        	   name : 'orderid',
		        	   align : 'center',
		        	   width : 200
		           },
		           {
		        	   display : '日志信息',
		        	   name : 'log_msg',
		        	   align : 'left',
		        	   width : 400,
		        	   render:function(item){
		        		   if(item.log_msg){
		        			   return "<p title='"+item.log_msg+"'>"+item.log_msg+"</p>";
		        		   }
		        	   }
		           },
		           {
		        	   display : '异常信息',
		        	   name : 'err_msg',
		        	   align : 'left',
		        	   width : 400,
		        	   render:function(item){
		        		   if(item.err_msg){
		        			   return "<p title='"+item.err_msg+"'>"+item.err_msg+"</p>";
		        		   }
		        	   }
		           }
		           ],
		           height:'100%',
		           rownumbers : true,
		           checkbox : false,
		           pageSize:30,
		           rowHeight : 27,
		           heightDiff : -30,
		           frozen : false ,
		   		   url:basePath + 'rest/orderDealLog/list'
	});
	
	orderLogGridObj.set('parms', {
		orderId : $("#orderId").val(),
		startTime : $("#startTime").val(),
		endTime : $("#endTime").val()
	});
	orderLogGridObj.loadData();
}

