var gridManager = null;
$(function () {   
	
	//遥测参数
	$("#param_name").ligerTextBox({
		width : 150
	});
	
	//maingrid initiliase
    init_maingrid();
    
    //search and reset button click_event
    $("#searchbtn").ligerButton({ type:'one',click: search_btn_click});	
  	//重置搜索条件
	$("#resetbtn").ligerButton({type:'two', click: clearForm}); 
	
});

function init_maingrid(){
	gridManager = $("#maingrid").ligerGrid({
        columns:[
                 {display: 'TMID', name: 'tmid',align: 'center',width:100 }
     			,
     			{display: '遥测名', name: 'tmname',align: 'left',width:120 }
     			,
     			{display: '遥测编号', name: 'tmcode',align: 'center',width:100 },
     			{display: '参数类型', name: 'tmtype',align: 'center',width:80,
					render:function(items){
						if(items.tmtype == "0"){
							return "浮点型";
						}else if(items.tmtype == "2"){
							return "整型";
						}else if(items.tmtype == "3"){
							return "字符型";
						}
					}
				}
             ], 
             width: "99.5%", 
             height: "99.3%",
             pageSize: 20,
             rownumbers:true,
             checkbox :false,
             frozen:false,
             usePager:true,
             cssClass: 'l-grid-gray' ,
             url:basePath+"rest/stateRule/findTmStateRule?satid="+satid+"&judgetype=0"
         });
}

//查找按钮的单击事件处理
function search_btn_click(){
	gridManager.set('parms', {
		key:$("#param_name").val()
	});
	gridManager.loadData();
}

//Clear the search form
function clearForm(){
	   $('#form1').find(':input').each(  
	   	function(){
		  if(this.type=="radio"||this.type=="checkbox"){
		    		this.checked=false;
			}else{
				$(this).val('');  
			}
		}     
		);
	}

//将卫星的选择信息，返回到调用的窗口;
function submitForm(){
	if (!gridManager.getSelectedRow()) { 
		Alert.tip("请至少选择一条数据！");
		return; 
	}
	if (gridManager.selected.length > 1) { 
		Alert.tip("只能选择一条数据！");
		return; 
	}
	
	//取得参数
	var tmid = gridManager.selected[0].tmid;
	var tmtype = gridManager.selected[0].tmtype;
	var tmname = gridManager.selected[0].tmname;
	var tmcode = gridManager.selected[0].tmcode;
	return {
		tmid:tmid,
		tmname:tmname,
		tmcode:tmcode,
		tmtype:tmtype
	}
}
