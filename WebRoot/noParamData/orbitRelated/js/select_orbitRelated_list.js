var tree = null;
var node = null;
var currentTreeNode;//当前点击右键的节点
var relatedtoolbar;//上边工具条
var relatedGrid;//相关信息表格
var fieldtoolbar;//下边工具条
var fieldGrid;//相关信息字段信息表格

var xdlg = null;

var jsjgId = null;

$(function (){
	//初始化卫星树形结构
	initSatTree();
	//初始化工具条
//	initToolBar();
	//初始化航天器相关信息表格及字段信息表格
	initRelatedGrid();
	$("#layout1").ligerLayout({ leftWidth: 190,allowLeftCollapse:false,heightDiff:0,space:0,
		onHeightChanged: f_heightChanged });
	var height = $(".l-layout-center").height();
	$("#scollDivTree")[0].style.height = height-50 +27 +"px";
	resizes();
});

//设置左边高度自适应
function f_heightChanged(options){
	$("#scollDivTree")[0].style.height = options.middleHeight-50 +27 +"px";
}


window.onresize = function(){
	resizes();
}

function resizes(){
	var winHeight = window.innerHeight;
	var winWidth = window.innerWidth;
	//两个表格高度
	var totalHeight = winHeight - 90 - 50;
	if(relatedGrid){
		var functionGridHeight = totalHeight*2/3;
		relatedGrid.setHeight(functionGridHeight);
		fieldGrid.setHeight(totalHeight - functionGridHeight + 25 + 56);
	}
}

function initSatTree(){
	$("#tree1").remove();
	tree = $("#tree1").ligerTree({
		checkbox: false,
		expand:false,
		topIcon:'sat',
		onselect :onselect,
		oncancelselect:oncancelselects,
        textFieldName:'name',
		idFieldName : 'sys_resource_id',
		parentIDFieldName : 'owner_id',
		height: "100%",
		nodeWidth:280,
		url:basePath+'rest/satsubalone/findSatTree'

	});
}

function onselect(){
	
	onDblClickRows({"jsjg_id":""});
	
	node = tree.getSelected();
	if(node.data.owner_id != "-1"){
		relatedGrid.set('parms', {
			satId:-1
		});
		relatedGrid.loadData();
		return;
	}
	relatedGrid.set('parms', {
		satId:node.data.sat_id
	});
	relatedGrid.set({newPage:1});
	relatedGrid.loadData();
	
	resizes();
}

function loadRelatedGrid(){
	relatedGrid.set({newPage:1});
	relatedGrid.loadData();
}

function oncancelselects(){
	onDblClickRows({"jsjg_id":""});
	node = null;
	relatedGrid.set('parms', {
		satId:''
	});
	relatedGrid.set({newPage:1});
	relatedGrid.loadData();
}

function initToolBar(){
	relatedtoolbar = $("#relatedtoolbar").ligerToolBar({
		items:[
		       { text: '添加', id:'addOrelated', click: itemclick,img: basePath+'lib/ligerUI/skins/icons/add.gif'},
		       { text: '修改', id:'modifyOrelated', click: itemclick,img: basePath+'lib/ligerUI/skins/icons/modify.gif'},
		       { text: '启用', id:'start', click: itemclick,img: basePath+'lib/ligerUI/skins/icons/up.gif'},
		       { text: '删除', id:'deleteOrelated', click: itemclick,img: basePath+'lib/ligerUI/skins/icons/delete.gif' }
		]});
	fieldtoolbar = $("#fieldtoolbar").ligerToolBar({
		items:[
		       { text: '添加', id:'addField', click: itemclick,img: basePath+'lib/ligerUI/skins/icons/add.gif'},
		       { text: '修改', id:'modifyField', click: itemclick,img: basePath+'lib/ligerUI/skins/icons/modify.gif'},
		       { text: '删除', id:'deleteField', click: itemclick,img: basePath+'lib/ligerUI/skins/icons/delete.gif' }
		       ]});
}

function initRelatedGrid(){
	relatedGrid = $("#relatedGrid").ligerGrid({
		columns: [ 
               	{id: 'jsjg_id',name:'jsjg_id',hide:true,width:0.1},
	          {display: '信息名称', name: 'jsjg_name',align: 'center',width:150,
	        	  render:function(item){
	        		  if(item.jsjg_name != null){
	        			  return '<div title="'+item.jsjg_name+'">'+item.jsjg_name+'</div>';
	        		  }
	        	  }
	          },
	          {display: '信息编号', name: 'jsjg_code',align: 'center',width:120},
	          {display: '状态', name: 'jsjg_status',align: 'center',width:120,
	        	  render: function (item){
	        		  var val = item.jsjg_status;
	        		  if(val=="0"){
	        			  return "未启用";
	        		  }else if(val=="2"){
	        			  return "已启用";
	        		  }else if(val=="1"){
	        			  return "<font color='red'>删除</font>";
	        		  }
	        	  }
	          },
	          {display: "数据时间类型", name: "is_time_range",align: 'center',width:100,
	        	  render: function (item){
	        		  var val = item.is_time_range;
	        		  if(val=="1"){
	        			  return "段时间";
	        		  }else if(val=="0"){
	        			  return "点时间";
	        		  }
	        	  }
	          },
	          {display: '起始字段名', name: 'start_time',align: 'center',width:120},
	          {display: '截止字段名', name: 'end_time',align: 'center',width:120}
	    ],
	    width: '100%', 
	    pageSize: 20,
	    rownumbers:false,
	    checkbox : true,
	    delayLoad:true,
	    onSelectRow:onDblClickRows,
	    onUnSelectRow:onUnSelectRow,
	    rowHeight:27,
	    //应用灰色表头
	    cssClass: 'l-grid-gray', 
	    url:basePath+'rest/orbitrelated/queryStartOrbitRelatedByPage'
	});
	fieldGrid = $("#fieldGrid").ligerGrid({
		columns: [ 
	          { id: 'field_id',name:'field_id',hide:true,width:0.1},
	          {display: '字段名称', name: 'field_name',align: 'center',width:120,
	        	  render:function(item){
	        		  if(item.field_name != null){
	        			  return '<div title="'+item.field_name+'">'+item.field_name+'</div>';
	        		  }
	        	  }
	          },
	          {display: '字段长度', name: 'field_length',align: 'center',width:100},
	          {display: '字段类型', name: 'field_type',align: 'center',width:120,
	        	  render: function (item){
	        		  var val = item.field_type;
	        		  if(val=="0" || val=="2"){
	        			  return "整型";
	        		  }else if(val=="3"){
	        			  return "字符串";
	        		  }else if(val=="5"){
	        			  return "时间戳";
	        		  }
	        	  }
	          },
	          {display: '是否为摘要信息', name: 'is_display_flag',align: 'center',width:150,
	        	  render: function (item){
	        		  var val = item.is_display_flag;
	        		  if(val=="1"){
	        			  return "是";
	        		  }else if(val=="0"){
	        			  return "否";
	        		  }
	        	  }
	          },
	          {display: "字段说明", name: "field_comment",align: 'center',width:250,
	        	  render:function(item){
	        		  if(item.field_comment != null){
	        			  return '<div title="'+item.field_comment+'">'+item.field_comment+'</div>';
	        		  }
	        	  }
	          }
	          ],
          dataAction:'server',
          width: '100%', 
          pageSize: 20,
          rownumbers:false,
          checkbox : true,
          rowHeight:27,
          //应用灰色表头
          cssClass: 'l-grid-gray', 
          url:basePath+'rest/orbitrelated/queryOrbitFieldByPage'
	});
}

function onUnSelectRow(){
	jsjgId = "";
	fieldGrid.set('parms', {
		jsjg_id:""
	});
	fieldGrid.set({newPage:1});
	fieldGrid.loadData();

	/*fieldtoolbar.setEnabled("addField");
	fieldtoolbar.setEnabled("modifyField");
	fieldtoolbar.setEnabled("deleteField");*/
}

/**
 * 双击相关信息表格事件
 */
function onDblClickRows(obj){
	jsjgId = obj.jsjg_id;
	fieldGrid.set('parms', {
		jsjg_id:obj.jsjg_id
	});
	fieldGrid.set({newPage:1});
	fieldGrid.loadData();
	
/*	if(obj.jsjg_status == 0){
		fieldtoolbar.setEnabled("addField");
		fieldtoolbar.setEnabled("modifyField");
		fieldtoolbar.setEnabled("deleteField");
	}else{
		//已启用，不能修改
		fieldtoolbar.setDisabled("addField");
		fieldtoolbar.setDisabled("modifyField");
		fieldtoolbar.setDisabled("deleteField");
	}*/
	
}

function loadFieldGrid(){
	fieldGrid.set('parms', {
		jsjg_id:jsjgId
	});
	fieldGrid.set({newPage:1});
	fieldGrid.loadData();
}

var xdlg = null;
//工具条按钮事件
function itemclick(item){
	 if(item.id){
		 switch (item.id){
		 case "addOrelated":
	         	if(node == null){
	        		Alert.tip("请选择卫星节点！");
					return; 
	        	}
				setCurrWindows();
				xdlg= $.ligerDialog.open( 
	            { 
		          	 width: 400,
					 height:250, 
	                title:"新增卫星相关信息",
					url: basePath+"noParamData/orbitRelated/orbitRelated_add.jsp?satId="+node.data.sat_id,
					buttons :[
			            { text: '保存', width: 80, onclick:function(item, dialog){
			            	var rsObj = dialog.frame.submitForm();
			            	if(rsObj == null){
			            		return;
			            	}
			            	if(rsObj.success){
			            		Alert.tip(rsObj.message);
			            		xdlg.close();
			            		loadRelatedGrid();
			            	}else{
			            		Alert.tip(rsObj.message);
			            	}
			            }},
			            { text: '关闭', width: 80, onclick:function(item, dialog){
			            	dialog.close();
			            }}
				 ]
	            });
	            xdlg.show();
	            return;
		 case "addField":
			 if(jsjgId == null || jsjgId == ""){
				 Alert.tip("请选择卫星相关信息！");
				 return; 
			 }
			 setCurrWindows();
			 xdlg= $.ligerDialog.open( 
					 { 
						 width: 400,
						 height:340, 
						 title:"新增卫星相关字段信息",
						 url: basePath+"noParamData/orbitRelated/field_add.jsp?jsjg_id="+jsjgId,
						 buttons :[
						           { text: '保存', width: 80, onclick:function(item, dialog){
						        	   var rsObj = dialog.frame.submitForm();
						        	   if(rsObj == null){
						            		return;
						            	}
						        	   if(rsObj.success){
						        		   Alert.tip(rsObj.message);
						        		   xdlg.close();
						        		   loadFieldGrid();
						        	   }else{
						        		   Alert.tip(rsObj.message);
						        	   }
						           }},
						           { text: '关闭', width: 80, onclick:function(item, dialog){
						        	   dialog.close();
						           }}
						           ]
					 });
			 xdlg.show();
			 return;
		 case "modifyOrelated":
			 if (relatedGrid.selected.length != 1) { 
				Alert.tip("请选择一条数据！");
				return; 
			 }
			 var jsjg_status = relatedGrid.selected[0].jsjg_status;
			 if(jsjg_status != 0){
				 Alert.tip("请选择状态为未启用的数据！");
				 return;
			 }
			 setCurrWindows();
			 xdlg= $.ligerDialog.open( 
					 { 
						 width: 400,
						 height:250, 
						 title:"修改卫星相关信息",
						 url: basePath+"noParamData/orbitRelated/orbitRelated_update.jsp?jsjg_id="+relatedGrid.selected[0].jsjg_id,
						 buttons :[
						           { text: '保存', width: 80, onclick:function(item, dialog){
						        	   var rsObj = dialog.frame.submitForm();
						        	   if(rsObj == null){
						            		return;
						            	}
						        	   if(rsObj.success){
						        		   Alert.tip(rsObj.message);
						        		   xdlg.close();
						        		   loadRelatedGrid();
						        	   }else{
						        		   Alert.tip(rsObj.message);
						        	   }
						           }},
						           { text: '关闭', width: 80, onclick:function(item, dialog){
						        	   dialog.close();
						           }}
						           ]
					 });
			 xdlg.show();
			 return;
		 case "start":
			 if (relatedGrid.selected.length != 1) { 
				 Alert.tip("请选择一条数据！");
				 return; 
			 }
			 var pkId = relatedGrid.selected[0].jsjg_id;
			 var jsjg_status = relatedGrid.selected[0].jsjg_status;
			 if(jsjg_status != 0){
				 Alert.tip("请选择状态为未启用的数据！");
				 return;
			 }
			 //判断是否可以启用
			 if(!judgeIsHaveField(pkId)){
				 Alert.tip("该信息没有字段信息数据，不能启用！");
				 return;
			 }
			 
			 var url = basePath+'rest/orbitrelated/startRelated';
			 $.ligerDialog.confirm('确定要启用选中的记录吗？',function (yes){
				 if(yes){
					 $.post(url,{jsjgId:pkId},function(dataObj){
						 if (dataObj.success == 'true'){
							 Alert.tip("启用成功,表结构已创建成功！");
							 loadRelatedGrid();
						 }else{
							 Alert.tip("启用失败！");
						 }
					 },"json");
				 }
			 });
			 return ;
		 case "deleteOrelated":
			if (!relatedGrid.getSelectedRow()) { 
					Alert.tip("请至少选择一条数据！");
					return; 
			}
	      	var pkId = relatedGrid.selected[0].jsjg_id;
				for (var i = 0; i < relatedGrid.selected.length; i++){
					if(relatedGrid.selected[i].jsjg_status != "0"
						&& relatedGrid.selected[i].jsjg_status != "2"){
						Alert.tip("请选择状态为未启用或已启用的数据！");
						return ;
					}
					pkId = pkId + ',' + relatedGrid.selected[i].jsjg_id;
				}
		  	var url = basePath+'rest/orbitrelated/updateRelatedStatus';
			$.ligerDialog.confirm('确定要删除选中的记录吗？',function (yes){
		    if(yes){
				$.post(url,{jsjgIds:pkId,status:'1'},function(dataObj){
					if (dataObj.success == 'true'){
						Alert.tip("删除成功！");
						loadRelatedGrid();
	             	}else{
	             		 Alert.tip("删除失败！");
	             	}
				},"json");
		    }
			});
			return ;
		 case "deleteField":
			 if (!fieldGrid.getSelectedRow()) { 
				 Alert.tip("请至少选择一条数据！");
				 return; 
			 }
			 var pkId = fieldGrid.selected[0].field_id;
			 for (var i = 0; i < fieldGrid.selected.length; i++){
				 pkId = pkId + ',' + fieldGrid.selected[i].field_id;
			 }
			 var url = basePath+'rest/orbitrelated/updateFieldStatus';
			 $.ligerDialog.confirm('确定要删除选中的记录吗？',function (yes){
				 if(yes){
					 $.post(url,{fieldIds:pkId,status:'1'},function(dataObj){
						 if (dataObj.success == 'true'){
							 Alert.tip("删除成功！");
							 loadFieldGrid();
						 }else{
							 Alert.tip("删除失败！");
						 }
					 },"json");
				 }
			 });
			 return ;
		 case "modifyField":
			 if (fieldGrid.selected.length != 1) { 
				Alert.tip("请选择一条数据！");
				return; 
			 }
			 setCurrWindows();
			 xdlg= $.ligerDialog.open( 
					 { 
						 width: 400,
						 height:340, 
						 title:"修改卫星相关字段信息",
						 url: basePath+"noParamData/orbitRelated/field_update.jsp?jsjg_id="+fieldGrid.selected[0].jsjg_id
						 		+'&field_id='+fieldGrid.selected[0].field_id,
						 buttons :[
						           { text: '保存', width: 80, onclick:function(item, dialog){
						        	   var rsObj = dialog.frame.submitForm();
						        	   if(rsObj == null){
						            		return;
						            	}
						        	   if(rsObj.success){
						        		   Alert.tip(rsObj.message);
						        		   xdlg.close();
						        		   loadFieldGrid();
						        	   }else{
						        		   Alert.tip(rsObj.message);
						        	   }
						           }},
						           { text: '关闭', width: 80, onclick:function(item, dialog){
						        	   dialog.close();
						           }}
						           ]
					 });
			 return;   
 		 }
	 }
}

function judgeIsHaveField(relateId){
	var flag = true;
	$.ajax({
		url:basePath+"rest/orbitrelated/judgeIsHaveField",
		data:{
			relateId:relateId,
		},
		async:false,
		success:function(data){
			var rsData = eval('('+data+')');
			if(rsData.success == "true"){
				flag = true;
			}else{
				flag = false;
			}
		}
	});
	return flag;
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
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
	   liger.get("tm_param_type").setValue("");
	}


function okWin(){
	if(relatedGrid.selected.length == 0){
		Alert.tip("请选择一条卫星相关信息数据！");
		return;
	}
	return {
		relateId:relatedGrid.selected[0].jsjg_id,
		relateCode:relatedGrid.selected[0].jsjg_code
	}
}
