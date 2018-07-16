var gridManager = null;
var xdlg;
	$(function() {
		var baseImgPath=basePath+'lib/ligerUI/skins/icons/';
		var baseImgPath2=basePath+'icons/';
		//工具条
		$("#toptoolbar").ligerToolBar({
			items : [ 
				{ text :'导出', id :'export', click:itemclick, img:baseImgPath2+'16X16/arrow_down.gif'}  
			]
		});
		var lineColor='gray';
		//表格
		$("#maingrid").ligerGrid({
			columns : [ 
			    { display : "",name:"pk_id",id:"pk_id",hide:true},
				{ display : '报警开始时间',name : 'alarmStartTime', width : '12%',
					render: function(e){
		            	if(e.alramLevel =='1'){
		            		lineColor='red';
		            		var h = "";
		                    h += "<font color='"+lineColor+"'>"+e.alarmStartTime+"</font>";
		             	 	return h;
		            	}else if(e.alramLevel =='2'){
		            		lineColor='blue';
		            		var h = "";
		                    h += "<font  color='"+lineColor+"'>"+e.alarmStartTime+"</font>";
		             	 	return h;
		            	}else if(e.alramLevel =='3'){
		            		lineColor='green';
		            		var h = "";
		                    h += "<font  color='"+lineColor+"'>"+e.alarmStartTime+"</font>";
		             	 	return h;
		            	}
		            	else{
		            		lineColor='gray';
		            		var h = "";
		                    h += "<font  color='"+lineColor+"' >"+e.alarmStartTime+"</font>";
		             	 	return h;
		            	}
					}	
				}, 
				{ display : '卫星名',name : 'starName',width : '12%',
					render: function(e){
						var h  = "<font color='"+lineColor+"'>"+e.starName+"</font>";
	             	 	return h; 
             	 	}
				},
				{ display : '参数代号',name : 'paramCode',width : '12%',
					render: function(e){
						var h  = "<font color='"+lineColor+"'>"+e.paramCode+"</font>";
	             	 	return h; 
             	 	}
				},
			   { display : '级别',name : 'alramLevel',width : '12%',
				render: function(e){
	            	if(e.alramLevel =='1'){
	            		var h = "";
	                    h += "<font color='"+lineColor+"'>重度</font>";
	             	 	return h;
	            	}else if(e.alramLevel =='2'){
	            		var h = "";
	                    h += "<font  color='"+lineColor+"'>中度</font>";
	             	 	return h;
	            	}else if(e.alramLevel =='3'){
	            		var h = "";
	                    h += "<font  color='"+lineColor+"'>轻度</font>";
	             	 	return h;
	            	}
	            	else{
	            		var h = "";
	                    h += "<font  color='"+lineColor+"' >正常</font>";
	             	 	return h;
	            	}
				}	
			   }, 
			   { display : '当前值',name : 'currentValue',width : '10%',
				   render: function(e){
						var h  = "<font color='"+lineColor+"'>"+e.currentValue+"</font>";
	             	 	return h; 
            	 	}   
			   },
			   { display : '报警值',name : 'alarmValue',width : '10%',
				   render: function(e){
						var h  = "<font color='"+lineColor+"'>"+e.alarmValue+"</font>";
	             	 	return h; 
            	 	}   
			   } 
			   ,
			   { display : '报警信息',name : 'alarmMsg',width : '10%',
				   render: function(e){
						var h  = "<font color='"+lineColor+"'>"+e.alarmValue+"</font>";
	             	 	return h; 
            	 	}   
			   } ,
			   { display : '报警结束时间',name : 'alarmEndTime',width : '10%',
				   render: function(e){
					   if(e.alarmEndTime==null || e.alarmEndTime==''){
						   var h  = "<font color='"+lineColor+"'>-</font>";
		             	 return h; 
						   
					   }else{
						   var h  = "<font color='"+lineColor+"'>"+e.alarmEndTime+"</font>";
		             	 	return h; 
					   }
						
            	 	}   
			   }  ,
			   { display : '确认时间',name : 'alarmEnsuredTime',width : '10%',
				   render: function(e){
					   if(e.alarmEnsuredTime==null || e.alarmEnsuredTime==''){
						   var h  = "<input type='button' name='waitensured'  value='等待确认' />";
		             	 return h; 
						   
					   }else{
						   var h  = "<font color='"+lineColor+"'>"+e.alarmEnsuredTime+"</font>";
		             	 	return h; 
					   }
						
            	 	}   
			   } 
			],
			//url : basePath+'rest/UserAction/getUserList',
			height : '97%',
			usePager :false,
			rownumbers:true,
			checkbox:true,
			colDraggable:true,
            rowDraggable:true,
            heightDiff :13,
            frozen:false 
		});
		gridManager = $("#maingrid").ligerGetGridManager();
//		
//		var gridData={"Rows":[
//{"alarmStartTime":"2015-08-06 12:12:36","starName":"BD1002","paramCode":"TJ11","alramLevel":"1","pk_id":"1","currentValue":"52.833","alarmValue":"50","alarmMsg":"10N小发动机4B温度（TJ11）超过严重报警","alarmEndTime":""},
//	{"alarmStartTime":"2015-08-06 12:12:36","starName":"BD1002","paramCode":"ZY2_2","alramLevel":"2","pk_id":"2","currentValue":"0","alarmValue":"50","alarmMsg":"10N小发动机4B温度（TJ11）超过严重报警","alarmEndTime":""},
//	{"alarmStartTime":"2015-08-06 12:12:36","starName":"BD1002","paramCode":"TJ15","alramLevel":"3","pk_id":"3","currentValue":"48.32","alarmValue":"50","alarmMsg":"10N小发动机4B温度（TJ11）超过严重报警","alarmEndTime":""},
//	{"alarmStartTime":"2015-08-06 12:12:36","starName":"BD1002","paramCode":"TJ14","alramLevel":"4","pk_id":"4","currentValue":"41.63","alarmValue":"50","alarmMsg":"10N小发动机4B温度（TJ11）超过严重报警","alarmEndTime":""}
//
//],"Total":4};
//		gridManager.loadData(gridData);
		 
	 
		$("#pageloading").hide();
		//定时查询
		//getInfo();
});

	//定时查询
	function getInfo(){
		$.ajax({
			url:basePath+'rest/alarmRealTimeAction/getRealTimePageAlam',
			data:{
				pageId:'10023' 
			},
			asynsc:false,
			success:function(data){
				if(data!=null && data!=''){
					var jsobj=eval('('+data+')');
					gridManager.addRows(jsobj);
				}
				
			}
		});
		
		setTimeout(function(){
			getInfo();
		},1000);
	}
function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "export":
				alert("123");
				$.ajax({
					type:'POST',
					url:basePath+'rest/alarmRealTimeAction/getRealTimePageAlam',
					data:{
						pageId:'10023' 
					},
					asynsc:false,
					success:function(data){
						if(data!=null && data!=''){
							var jsobj=eval('('+data+')');
							gridManager.addRows(jsobj,0,"first");
						}
						
					}
				});
				
				return;
			 
			}
		}
	
	}
//把当前操作句柄传递给父窗口
function setCurrWindows() {
	currWin = window;
}
//关闭对话框
function f_closeDlg() {
	xdlg.close();
}
//刷新grid
function f_reload() {
	gridManager.set({
		newPage : 1
	});
	gridManager.loadData(true);
}
function closeDlgAndReload(){
      f_closeDlg();
      f_reload();
  }
function clearForm(){
   $('#form1').find(':input').each(  
   function(){
	   switch(this.type){
		   case 'passsword': 
		   case 'select-multiple':  
		   case 'select-one':  
		   case 'text':
		   case 'textarea': 
			    if(!this.readOnly){
		          $(this).val('');  
		          break;
		        }
		   case 'checkbox':  
		   case 'radio': 
		        this.checked = false;  
	   }
	}     
	);  
}