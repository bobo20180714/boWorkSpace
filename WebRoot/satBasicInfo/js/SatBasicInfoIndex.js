var formData1 = null;
var sat_id = "";
var gridManager=null;
var bar;
$(function (){
	initToolBarController();
	initForm2();
	initBtn();	
	initGridData();
});

function isEmpty(param){
	if(param==null||$.trim(param)==""){
		return true;
	}else{
		return false;
	}
	
}
function initToolBarController(){
	$("#toptoolbar").ligerToolBar({
		items:[
	       { text: '新增', id:'add', click: itemclick,icon: 'add'},
	        { text: '修改', id:'modify', click: itemclick,icon: 'update'},
	       { text: '删除', id:'delete', click: itemclick,icon: 'delete'},
	       { text: '组播地址管理', id:'address', click: itemclick,icon: 'manager'},
	        { text: '关联卫星相关信息', id:'addRelate', click: itemclick,icon: 'relate'}
		]});
}

function initForm2(){
	
    formData1 = $("#form2").ligerForm({
    		labelAlign:'right',
        	inputWidth: 100, 
            labelWidth: 80, 
            space: 5,
            validate:true,
            fields: [
				{display: '卫星名称', id: 'sat_name',name:'sat_name',type:"text",newline: false,inputWidth:80},
				{display: '设计单位', id: 'design_org',name:'design_org',type:"text",newline: false},
				{display: '用户单位', id: 'user_org',name:'user_org',type:"text",newline: false},
				{display: '发射时间', id: 'launch_time_start',name:'launch_time_start',type:"date",newline: false},
				{display: '至', id: 'launch_time_end',name:'launch_time_end',type:"date",newline: false,labelWidth: 30,}
//				{display: '发射时间', id: 'launch_time',name:'launch_time',type:"date",newline: false}
			]
    });
}


/**
 * 按钮初始化
 */
function initBtn(){
	$("#searchbtn").ligerButton({type:"one", click: function ()
    {
		search();
    }  
	});
  	//重置搜索条件
	$("#resetbtn").ligerButton({type:"two", click: function (){
        clearForm();
    }
	}); 
}

function initGridData(){
	gridManager=$("#maingrid").ligerGrid({
	    columns: [
	              	{display: '卫星ID', name: 'sat_id',align: 'center',width:-1,hide:true},
	              	{display: '任务代号', name: 'mid',align: 'center',width:'8%'},
	    			{display: '卫星名称', name: 'sat_name',align: 'center',width:'15%',
	              		render: function(e){
	              			if(e.sat_name != null){
	              				return "<p title='"+ e.sat_name+"'>"+e.sat_name+"</p>";
	              			}
	             	 	} 
	    			},
	    			{ display: "卫星编号", name: "sat_code",align: 'center',width:'15%'},
	    			/*{ display: "状态", id:"sta",name: "status",align: 'center',width:80,
	    				render:function(item){
	    					if(item.status==0) return name="正常";
	    					else if(item.status==1) {return name="已删除";}
	    				}
    				},*/
	    			{display: '设计单位', name: 'design_org',align: 'center',width:'10%',
	        				render: function (e){
	        					if(e.design_org != null){
	        						return "<p title='"+ e.design_org+"'>"+e.design_org+"</p>";
	              				}
	    					}
	        			},
	        			{display: '用户单位', name: 'user_org',align: 'center',width:'10%',
	        				render: function (e){
	        					if(e.user_org != null){
	        						return "<p title='"+ e.user_org+"'>"+e.user_org+"</p>";
	              				}
	    					}
	        			}
	        			,
	        			{display: '设计寿命(年)', name: 'design_life',align: 'center',width:'8%'}
	        			,
	        			{display: '超寿时间(年)', name: 'over_life',align: 'center',width:'8%'}
	        			,
	        			{display: '发射时间', name: 'launch_time',align: 'center',width:'10%'}
	        			,
	        			{display: '查看详情',align: 'center',width:'10%',
	        				render: function (item){
	        					
	        					if(item.sat_code){
	        						
	        						return '<img src="'+basePath+'resources/images/basic_search_two.png" alt="查看详细信息" onclick=itemdbclick('+item.sat_id+')></img>';
	        					}
	        				}
	        			}
	        			
	    ], 
	    width: '99.7%', 
	    height: '100%', 
	    pageSize: 20,
	    rownumbers:true,
	    checkbox : true,
	    rowHeight:27,
	    frozen: false,  
	    url:basePath+'rest/satinfo/findsatbasicinfoquerypage'
	});
}

/**
*
*获取url里的参数
*@param 参数名
*/
function getParam(name){

	var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)","i");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null){
		return unescape(r[2]);
	}
	return null;

}

var xdlg = null;
//工具条按钮事件
function itemclick(item){
	 if(item.id){
		 switch (item.id){
		 case "addRelate":
			 if (gridManager.selected.length != 1) { 
					$.ligerDialog.warn("请选择一条数据！");
					return; 
				}
			var satId= gridManager.selected[0].sat_id ;
			 xdlg= parent.$.ligerDialog.open({ 
				 width: 700,
				 height:545, 
				 title:'请勾选需要关联的卫星相关信息',
				 url: basePath+"satBasicInfo/add_relate.jsp?satId="+satId,
				 buttons :[
				           { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
				        	   dialog.close();
				           }}]
			 });
			 return;
		 case "add":
			 setCurrWindows();
			 xdlg= parent.$.ligerDialog.open( 
					 { 
						 width: 700,
						 height:545, 
						 title:'[新增]卫星信息',
						 url: basePath+"satBasicInfo/sat_add.jsp",
						 buttons :[
						           { text: '保存',type:'save', width: 80 ,onclick:function(item, dialog){
						        	   dialog.frame.submitForm();
						           }},
						           { text: '关闭',type:'close', width: 80, onclick:function(item, dialog){
						        	   dialog.close();
						           }}
						           ]
					 });
			 xdlg.show();		 
			 return;
         case "modify":
				if (!gridManager.getSelectedRow()) { 
					$.ligerDialog.warn("请至少选择一条数据！");
					return; 
				}
				if (gridManager.selected.length > 1) { 
					$.ligerDialog.warn("只能选择一条数据！");
					return; 
				}
					
				var lmId= gridManager.selected[0].sat_id ;
				setCurrWindows();
 			xdlg= parent.$.ligerDialog.open( 
	            { 
		          	width: 700,
				    height:580, 
	                title:'[修改]卫星信息',
					url: basePath+"satBasicInfo/SatBasicInfoUpdate.jsp?sat_id="+lmId,
					buttons :[
		            { text: '保存', type:'save', width: 80 ,onclick:function(item, dialog){
		             	dialog.frame.submitForm();
		             }},
		            { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
		            	dialog.close();
		            }}
				 ]
	            });
	            xdlg.show();		 
				return;
         case "address":
        	 if (!gridManager.getSelectedRow()) { 
        		 $.ligerDialog.warn("请至少选择一条数据！");
        		 return; 
        	 }
        	 if (gridManager.selected.length > 1) { 
        		 $.ligerDialog.warn("只能选择一条数据！");
        		 return; 
        	 }
        	 
        	 var satId = gridManager.selected[0].sat_id;
        	 setCurrWindows();
        	 xdlg= parent.$.ligerDialog.open( 
        			 { 
        				 width: 700,
        				 height:450, 
        				 title:'卫星['+gridManager.selected[0].sat_code+']组播地址管理',
        				 url: basePath+"satBasicInfo/address_list.jsp?satId="+satId,
        				 buttons :[
        				           { text: '关闭', type:'close', type:'close', width: 80, onclick:function(item, dialog){
        				        	   dialog.close();
        				           }}
        				           ]
        			 });
        	 return;
				
         case "delete":
         	if (!gridManager.getSelectedRow()) { 
					$.ligerDialog.warn("请至少选择一条数据！");
					return; 
				}
         	var pkId = "";
				for (var i = 0; i < gridManager.selected.length; i++){
					if(i==(gridManager.selected.length-1)) {
						pkId = pkId + gridManager.selected[i].sat_id;				    			
					} else {
						pkId = pkId+ gridManager.selected[i].sat_id + ',';
					}
				}
			  	var url = basePath+'rest/satinfo/satbasicinfodelete';
				$.ligerDialog.confirm('确定要删除选中的记录吗？',function (yes){
			    if(yes){
					  $.post(url,{ids:pkId},function(dataObj){
						if (dataObj.success == "true"){
							Alert.tip(dataObj.message);
							gridManager.loadData();
		             	}else Alert.tip(dataObj.message); 
					},"json");
			    }
				});
				return ;
		 }
		 
	 }
}
//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}
//关闭对话框刷新gridManager
function closeDlgAndReload(){
    f_closeDlg();
    gridManager.loadData();

}
function grid_reload(){
	gridManager.loadData();
}
//关闭对话框
function f_closeDlg(){
    xdlg.close();
}
//关闭对话框
function c_closeDlg(){
    ckxq.close();
}

function clearForm(){
	   $('#form2').find(':input').each(  
	   function(){
		   if(this.type=="radio"||this.type=="checkbox"){
	    		this.checked=false;
	    	}else{
	    		$(this).val('');  
	    	}
		}     
		);
	}

var ckxq = null;
function itemdbclick(sat_id){
	setCurrWindows();
		ckxq= parent.$.ligerDialog.open( 
        { 
          	width: 680,
		    height:565, 
            title:'卫星详细信息',
			url: basePath+"satBasicInfo/CheckDetails.jsp?sat_id="+sat_id,
			buttons :[{ text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
            	dialog.close();
            }}
		 ]
        });
		ckxq.show();		 
		return;
}

function search(){
	var form = $("#form2").ligerForm();
	var data = form.getData();
	gridManager.setParm("sat_name",data.sat_name);
	gridManager.setParm("design_org",data.design_org);
	gridManager.setParm("user_org",data.user_org);
	gridManager.setParm("launch_time_start",data.launch_time_start);
	gridManager.setParm("launch_time_end",data.launch_time_end);
	gridManager.set({newPage:1});
	gridManager.loadData();
}