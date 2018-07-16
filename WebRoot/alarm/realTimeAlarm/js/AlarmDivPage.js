var gridManager = null;
var xdlg;
	$(function() {
		var baseImgPath=basePath+'lib/ligerUI/skins/icons/';
		var baseImgPath2=basePath+'icons/';
		//工具条
		$("#toptoolbar").ligerToolBar({
			items : [ 
			    { text :'报警颜色配置', id :'colorSet', click:itemclick, img:baseImgPath+'settings.gif'} , 
				{ text :'导出全部', id :'export', click:itemclick, img:baseImgPath2+'16X16/arrow_down.gif'}  
			]
		});
		var lineColor='gray';
 		
		$("#pageloading").hide();
		//定时查询
		 getInfo();
});

	//定时查询
	function getInfo(){
		$.ajax({
			url:basePath+'rest/alarmRealTimeAction/getRealTimePageAlam',
			data:{
				pageId:pageId
			},
			asynsc:false,
			success:function(data){
				if(data!=null && data!=''){
					var jsobj = eval('('+data+')');
					var newCont=''; 
					newCont='<div  style=" width:100% ; height:30px;" >'+
					'<div class="cont1">'+jsobj.alarmStartTime+'</div>'+
					'<div class="cont1">'+jsobj.starName+'</div>'+
					'<div class="cont1">'+jsobj.paramCode+'</div>';
					if(jsobj.alramLevel=='1'){
						newCont=newCont+'<div class="cont1"><div class="alarm_1">重度</div></div>';
					}
					else if(jsobj.alramLevel=='2'){
						newCont=newCont+'<div class="cont1"><div class="alarm_2">中度</div></div>';
					}else if(jsobj.alramLevel=='3'){
						newCont=newCont+'<div class="cont1"><div class="alarm_3">轻度</div></div>';
					}else if(jsobj.alramLevel=='4'){
						newCont=newCont+'<div class="cont1"><div class="alarm_4">正常</div></div>';
					}else{
						newCont=newCont+'<div class="cont1"><div class="alarm_4">正常</div></div>';
					}
					newCont=newCont+
					'<div class="cont1">'+jsobj.currentValue+'</div>'+
					'<div class="cont1">'+jsobj.alarmValue+'</div>'+
					'<div class="cont7">'+jsobj.alarmMsg+'</div>'+
					'<div class="cont1">'+jsobj.alarmEndTime+'</div>';
					
					if (jsobj.alarmEnsuredTime==null || jsobj.alarmEnsuredTime==''){
						newCont=newCont+'<div class="cont1"><input type="button" name="button" id="button" value="等待确认"  style="background-color:#C00; color:#FFF" onclick="saveEnsure('+jsobj.pk_id+')"/></div>';	
						newCont=newCont+'</div>';
					}else{
						newCont=newCont+'<div class="cont1">'+jsobj.alarmEnsuredTime+'</div>';
						newCont=newCont+'</div>';	
					}
					 
					
					$("#title_tb").after(newCont);
				}
				
			}
		});
		
		setTimeout(function(){
			getInfo();
		},12000);
	}
	
	function saveEnsure(pkid){
		alert("确认"+pkid);
	}
function itemclick(item) {
		if (item.id) {
			switch (item.id) {
				case "colorSet":
					setCurrWindows()
					sonOpenWin = $.ligerDialog.open({
						width : 515,
						height : 300,
						title : "报警颜色配置",
						isResize : false,
						url : basePath+"alarm/alarmCfg/set_color_page.jsp",
						buttons:[
					         { text: '保存', type:'save',width: 80 ,onclick:function(item, dialog){
							 	var flag = dialog.frame.Confirm();
							 	if(flag == true){
							 		Alert.tip("保存成功！");
							 		f_closeDlg();
							 	}else{
							 		Alert.tip("保存失败！");
							 	}
							 }},
							 { text: '关闭', type:'close',width: 80, onclick:function(item, dialog){
								f_closeDlg();
							 }}
						]
					});
				break;
			case "export":
				Alert.tip("尚未实现，敬请期待！");
				break;
			}
		}
	
	}
//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}
//关闭对话框
function f_closeDlg() {
	sonOpenWin.close();
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