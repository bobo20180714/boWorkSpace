var w=$('.main').width(),
	h=$('.main').height(),
	treeManager,
	tabManager=null,
	clipboard=null;
	//当前工程ID
	_curTabId=null,
	//对象管理器
	manager={
		preview:{},
		resize:{},
		lastFile:{},
		get:function(classify){
			switch(classify){
				case 'preview':
					return this.preview.hasOwnProperty(_curTabId)?this.preview[_curTabId]:null;
				case 'resize':
					return this.resize.hasOwnProperty(_curTabId)?this.resize[_curTabId]:null;
				case 'lastFile':
					return this.lastFile.hasOwnProperty(_curTabId)?this.lastFile[_curTabId]:null;
				default:
					return null;
			}			
		}
	},
	//返回当前选项卡的jquery对象
	tab=function(selector){
		return $(selector,'div[tabid=tabitem'+_curTabId+']');
	};
var dataSourceCombox = null;
$("#layout").ligerLayout({
	leftWidth:200,
	rightWidth:210,
		space :1,
	width:w,
	height:h,
	isLeftCollapse:false,
	onCollapsed:function(){
		var center=tab('.l-layout-center'),
			right=tab('.l-layout-right'),
			dx=175;		
		if(this.left.css('display')=='none'){//收起
			center.width(center.width()+dx);
			if(right.css('left')){
				right.css('left',right.css('left').replace('px','')*1+dx+'px');
			}
    	}
    	else{//展开
    		center.width(center.width()-dx);
    		if(right.css('left')){
    			right.css('left',right.css('left').replace('px','')*1-dx+'px');
    		}
    	}
	},
	onHeightChanged: f_heightChanged
});
tabManager=$("#navtab").ligerTab({
	height:h,
	onAfterSelectTabItem:function(tabid){
		_curTabId=tabid.replace('tabitem','');
	}
});
$(".l-tree").parent().height(h-44+16);
treeManager = $(".l-tree").ligerTree({ 
	url: 'rest/project/getAllProj', 
	checkbox: false,
	height: "100%",
	nodeWidth:"100%",
	topIcon:'sat',
	parentIcon: null,
    childIcon: null,
	parentIDFieldName:'owner',
	isLeafFieldName:'isleaf',
    isExpand:false,
	onContextmenu: function (node, e){
	    _curNode = node;
	    if(node.data.isleaf*1){	    	
	    	$("[menuitemid=new],[menuitemid=add],[menuitemid=paste]", tree_menu.menu.items).hide();
	    	$("[menuitemid=copy]").show();
	    }
	    else{
	    	$("[menuitemid=new],[menuitemid=add],[menuitemid=paste]", tree_menu.menu.items).show();
	    	$("[menuitemid=copy]").hide();
	    }
	    tree_menu.show({ top: e.pageY, left: e.pageX });
	    return false;
	},
	onSelect:function (node, e){
		if(node.data.isleaf*1==0)
			return;		
		
		var flag = false;
		if(manager.get('resize') && manager.get('resize').isUpdate()){
			$.ligerDialog.confirm('您已修改，是否保存？', '系统提示',function (yes) { 
		 		if(yes){
		 			addFile(_fileName);
		 			manager.get('resize').resetIsUpdate();
		 		}
				_curTabId=node.data.id;
	 			_fileName=node.data.text;
	 			addNewTab(_curTabId,_fileName);
			});	
		}else{
			_curTabId=node.data.id;
			_fileName=node.data.text;
 			addNewTab(_curTabId,_fileName);
		}
	},
	onError:goback
});

function f_heightChanged(option){
	
}

function showDeviceChange(){
	manager.get('resize').setIsUpdate();
}

function addNewTab(_curTabId,_fileName){
	
	tabManager.removeAll();
	tabManager.addTabItem({ 
		text: _fileName,
		tabid:'tabitem'+_curTabId,
		content:$('#tpl_content').html()
	});	
	$('#editAreaText').html(_fileName);
	tab('.content').ligerLayout({
		leftWidth :120,
		rightWidth :225,
		allowLeftCollapse :false,
		space :0,
		hideLeftHeader:true
	});
	
	var oldHeight = $("#navtab .l-tab-content").height();
	$("#navtab .l-tab-content").height(oldHeight+32);
	$("#navtab .l-tab-content-item").height(oldHeight+32);
	
	showMask();
	setTimeout(function(){
		create(_curTabId);
		hideMask();
	},20);

}

//加载插件、创建绘图区、加载数据源、加载文件
function create(id){
	tab('[name=accordion]')/*.parents()*/.contextmenu(function(evt){
		lib_menu.show({top:evt.clientY,left:evt.clientX});
		return false;
	});
	var menuclick=function(){
		if(tab('[name=graphBind]').data('type')*1){
			$('.l-icon-'+this.icon,$('div[ligeruiid=dynamic-menu]')).click();
		}
		else{
			$('.l-icon-'+this.icon,$('div[ligeruiid=static-menu]')).click();
		}
		$('.l-icon-'+this.icon,$('div[ligeruiid=bg-menu]')).click();
	};	
	//创建工具栏
	tab('[name=toolbar]').ligerToolBar({
		items: [{
//			tooltip:'保存',
			text:'保存',
		    click: function(){
		    	if(manager.get('resize').isUpdate()){
	    			addFile(_fileName);
		    	}
		    		
		    },
		    icon:'dataAccredit'
		},{
			id:'preview',
//			tooltip:'预览',
			text:'预览',
		    click: function(){
		    	var $item=tab('div[toolbarid=preview]');
		    	var $btn=$item.find('.l-icon');
		    	var resize=manager.get('resize');
		    	if($btn.hasClass('l-icon-x-play')){//预览		    		
		    		_binds=resize.getBindParams();
		    		/*if(!_binds)return;
		    		if(!_binds.length){
		    			parent.Alert.tip('未绑定数据！');
		        		return;
		    		}*/
		    		$item.attr('title','停止预览');
		    		$btn.removeClass('l-icon-x-play').addClass('l-icon-x-stop');
		    		resize.hideSelected();
		    		_intervalId=setInterval(function(){
			    		var rand=[];
		    			for(var i=0;_binds && i<_binds.length;i++){
			    			var e=_binds[i];
			    			if(e.valid){
			    				rand.push({
			    					devId:e.devId*1,
			    					parId:e.parId*1,
			    					code:e.code,
			    					val:e.valid[Math.floor(Math.random()*e.valid.length)]
			    				});
			    			}
			    			else if(e.max){
			    				rand.push({
			    					devId:e.devId*1,
			    					parId:e.parId*1,
			    					code:e.code,
			    					val:Math.floor(Math.random()*(e.max*1-e.min*1)+e.min*1)
			    				});
			    			}
			    			else{
			    				if(e.jsjgCode){
			    					rand.push({
				    					satId:e.satId*1,
				    					jsjgCode:e.jsjgCode,
				    					code:e.code,
				    					val:Math.floor(Math.random()*100)
				    				});
			    				}
			    				else{
			    					rand.push({
				    					devId:e.devId*1,
				    					parId:e.parId*1,
				    					code:e.code,
				    					val:Math.floor(Math.random()*2)
				    				});
			    				}			    				
			    			}
			    		}
			    		resize.setData(rand);
		    		},1000);
		    	}
		    	else{//停止
		    		$item.attr('title','预览');
		    		$btn.removeClass('l-icon-x-stop').addClass('l-icon-x-play');
		    		resize.showSelected();
		    		clearInterval(_intervalId);
		    		_intervalId=null;
		    	}
		    },
		    icon:'x-play'
		},{ line:true },{
//			tooltip:'删除图元',
			text:'删除图元',
			click: function(){
				manager.get('resize').removeUnit();
			},
			icon:'delete'
		},{
//			tooltip:'属性',
			text:'属性',
			click: function(){
				manager.get('resize').showProperties();
			},
			icon:'x-properties'
		},{ line:true },{
//			tooltip:'上传背景',
			text:'上传背景',
		    click:function(){
				$('<input type="file">').click().change(function(){
					var file=this.files[0];
					var resize=manager.get('resize');
					resize.setBack(file);
				});
			},
		    icon:'in'
		},{
			id:'page',
//			tooltip:'底图边距',
			text:'底图边距',
			 click:function(){
			    	manager.get('resize').setBackPos();
			    },
		    icon:'x-page'
		},{
//			tooltip:'删除背景',
			text:'删除背景',
			click: function(){
		    	manager.get('resize').removeBackground();
		    },
		    icon:'busy'
		},{line:true},{
//			tooltip:'监视',
			text:'监视',
			click:function(){
				
				//选择挂接的卫星或文件夹
				openProjectTree(_curTabId);
			},
			icon:'x-monitor'
		}]
	});
	//加载插件
	loadPlug(id);
	//加载文件
	var file=getLastFile();
	//创建绘图区
	manager.resize[id]=createGraph(id,file.width*1,file.height*1);
	bindPlugEvent();
	//创建数据源下拉框
	dataSourceCombox=tab('[name=dataSource]').ligerComboBox({
		width:90,
		selectedIndex:0,
		autocomplete:true,
		cancelable:false,
		url:'rest/jar/getSelectedJar?proId='+id,
		onBrowser:function(){
			var $grid=$('<div style="margin:0;"></div>');			
			_dlg=$.ligerDialog.open({
				title:'选择设备数据',
				width:680,
				height:420,
				target:$grid,
				isDrag:false,
		        buttons: [
		         { text: '选择', type:'save',width:80,onclick: function (item, dialog) { 
		        	 var selected=_grid.getSelecteds();
		        	 if(selected.length == 0){
		        		 parent.Alert.tip('请选择设备！');
		        		 return;
		        	 }
		        	 var jarIds=[];
		        	 $(selected).each(function (i, rowdata){
		        		 jarIds.push(rowdata.id);
		        	 });
		        	 $.ajax({  
			   	          url: 'rest/jar/addProjJar',  
			   	          type: 'POST',  
			   	          data: {
			   	        	  proId:id,
			   	        	  jarIds:jarIds
			   	          },  
			   	          success: function (ret) {  
			   	             if(ret=='T'){		   	            	
			   	            	_dlg.close();
			   	            	loadDataSource(id);
			   	             }  
			   	             else{
			   	            	parent.Alert.tip('数据库操作异常！');
			   	             }
			   	          }
			   	    });
		         }},
		         { text: '取消', type:'close',width:80, onclick: function (item, dialog) { dialog.close(); } }
		        ]
			});
			_grid=$grid.ligerGrid({
				height:350,
			    columns: [
			       { display: '名称', name:'name', width: 85, align: 'left'},
			       { display: '版本', name: 'ver', width: 60, align: 'left'},
			       { display: '描述', name: 'desc',align: 'left', minWidth: 90 }
			    ],       
			    checkbox:true,
			    usePager:true,
			    pageSize:20,
			    url:'rest/jar/getDevices'
			});			
		},
		onSelected:function(id,text){
			if(this.getText()){
				for(var i=0;i<this.data.length;i++){
					if(this.data[i].id==id){
						tab('[name=commentSource]').text(this.data[i].desc);
						return;
					}
				}				
			}
			else{
				tab('[name=commentSource]').text('');
			}
		}
	});
	//绑定参数
	tab("[name=bindButton]").css('-webkit-border-radius','4px').ligerButton({
		text:'<b>绑定数据</b>',
		width:120,
	    click: function (){
	    	var resize=manager.get('resize'),
	    		$graBind=tab('[name=graphBind]');
	    	if(resize.isJsjg()){//计算结果
	    		if(!$graBind.text()){
	    			parent.Alert.tip('请选择图元！');
		    		return;
		    	}
	    		bindJsjg(resize);
	    		return;
	    	}
	    	if(!dataSourceCombox.getText()){
	    		parent.Alert.tip('请选择数据源！');
	    		return;
	    	}	    	
	    	if(!$graBind.text()){
	    		parent.Alert.tip('请选择图元！');
	    		return;
	    	}
	    	bindParam(dataSourceCombox.data,resize,_curTabId);
	    }
	});	
	/*//移除数据源
	tab(' .title a').click(function(){
		var selected=dataSourceCombox.getSelected();
		if(!selected)return;
		$.ligerDialog.confirm('确认移除吗？', '系统提示',function (yes) { 
	 		if(yes){
	 			var pid=_curTabId;
	 			$.ajax({  
	 			     url: 'rest/jar/delProjJar',  
	 			     type: 'POST',  
	 			     data: {proId:pid,jarId:selected.id},  
	 			     success: function (ret) {  
	 			         if(ret=='T'){
	 			        	loadDataSource();
	 			         }
	 			         else{
	 		            	parent.Alert.tip('移除数据源失败！');
	 		             }
	 			     }
	 			});
	 		}
		});	
	});*/
	//加载数据源
//	loadDataSource(id);
	//加载文件
//	var file=getLastFile();
	if(file){
		//tab('.doc-title').text(file.name);
		manager.get('resize').load(file);
		if(file.isShowDevice == "Y"){
			$("#isShowDevice").attr("checked",true);
		}
	}
	else{
		hideMask();
	}
	manager.lastFile[id]=file;
	//图元预览
	//manager.preview[id]=new Preview();
}

function openProjectTree(fileProId){
	var addWin = $.ligerDialog.open({
		width : 550,
		height : 480,
		title : "关联卫星",
		isResize : false,
		url : "page/projectTree.jsp?fileProId="+fileProId,
		buttons:[
	         { text: '保存',  type:'save' ,width: 80 , onclick:function(item, dialog){
			 	var flag = dialog.frame.submitForms();
			 	if(flag != false){
					parent.f_addTab(uuid(),_fileName+"_监视",'../page/monitor.jsp?id='+manager.get('lastFile').id
							+'&satId='+flag);
			 	}
			 }},
			 { text: '关闭',  type:'close', width: 80 ,  onclick:function(item, dialog){
				 addWin.close();
			 }}
		]
	});
}

//加载数据源
function loadDataSource(proIdTemp){
	dataSourceCombox.reload();
	dataSourceCombox.selectValue();
}

/**
 * 移除数据源
 */
function removeSource(){
	var selected=dataSourceCombox.getSelected();
	if(!selected)return;
	$.ligerDialog.confirm('确认移除吗？', '系统提示',function (yes) { 
 		if(yes){
 			var pid=_curTabId;
 			$.ajax({  
 			     url: 'rest/jar/delProjJar',  
 			     type: 'POST',  
 			     data: {proId:pid,jarId:selected.id},  
 			     success: function (ret) {  
 			         if(ret=='T'){
 			        	loadDataSource(pid);
 			         }
 			         else{
 			        	parent.Alert.tip('移除数据源失败！');
 		             }
 			     }
 			});
 		}
	});	
}

//打开文件
function open(){
	var $target=$('<div style="margin:0;overflow:hidden;"><div position="left" title="工作空间"></div><div position="center"></div></div>');
	_dlg=$.ligerDialog.open({
		title:'选择文件',
		width:680,
		height:420,
		target:$target,
        buttons: [
         { text: '选择', onclick: function (item, dialog) { 
        	 var selected=_grid.getSelected();
        	 if(!selected){
        		 parent.Alert.tip('请选择文件！');
        		 return;
        	 }
        	 $.ajax({  
	   	          url: 'rest/file/getFileById',  
	   	          type: 'POST',  
	   	          data: {
	   	        	  id:selected.id
	   	          },  
	   	          success: function (ret) {  
	   	             var file=getObject(ret);
	   	             manager.lastFile[_curTabId]=file;
	   	             var resize=manager.get('resize');
	   	             resize.clear();
	   	             tab('.doc-title').text(file.name);
	   	             resize.load(file);
	   	             _dlg.close();
	   	          }
	   	    });
         } },
         { text: '删除', onclick: function (item, dialog) {
        	 var selected=_grid.getSelected();
        	 if(!selected){
        		 parent.Alert.tip('请选择文件！');
        		 return;
        	 }
        	 $.ligerDialog.confirm('确认删除吗？', '系统提示',function (yes) { 
 				if(yes){
 					$.ajax({  
 				          url: 'rest/file/delFile',  
 				          type: 'POST',  
 				          data: {
 				        	  id:selected.id
 				       	  },  
 				          success: function (ret) {  
 				             if(ret=='T'){
 				            	_grid.loadServerData({
 				            		url:'rest/file/getFileByProId',
 				            		proId:_curTabId
 				            	});
 				             }else{
 				            	parent.Alert.tip('删除文件失败！');
 				             }
 				          }
 				    });
 				}					
 			});        	 
         } },
         { text: '取消', onclick: function (item, dialog) { _dlg.close(); } }
        ]
	});
	$target.ligerLayout({
		leftWidth:200,
		height:350
	});
	_grid=$('[position=center]',$target).ligerGrid({
	    columns: [
	       { display: '名称', name:'name', width: 200, align: 'left'},
	       { display: '创建时间', name: 'date',align: 'left', minWidth: 90 ,render:function(rowdata, index, value){
				return new Date(value).format('yyyy-MM-dd hh:mm:ss');
	       }}
	    ],       
	    rownumbers:true,
	    usePager:false
	});
	$('[position=left]',$target).ligerTree({ 
		url: 'rest/project/getProj', 
		checkbox: false,
		parentIDFieldName:'owner',
		isLeafFieldName:'isleaf',
		onClick:function (node, e){
			_grid.loadServerData({
				url:'rest/file/getFileByProId',
				proId:node.data.id			
			});
		},
		onError:goback
	});	
}
//存储文件到数据库
function addFile(name){
	if(name==undefined)
		name=manager.get('lastFile').name;
	var resize=manager.get('resize');
	var formData=new FormData();
	formData.append('proId',_curTabId);
	formData.append('name',name);
	formData.append('data',resize.getData());
	var blobs=resize.getBlobImg();
	for(var k in blobs){
		formData.append("files['"+k+"']",blobs[k]);
	}
	var dels=resize.getDels();
	for(var k in dels){
		formData.append('dels',dels[k]);
	}
	$.ajax({  
          url: 'rest/file/addFile',  
          type: 'POST',  
          data: formData, 
          async:false,
       	  processData: false,
          contentType:false,
          success: function (ret) {  
              if(ret=='T'){
            	  manager.get('resize').setBackgoundAlready();
            	  if(!window._isHiddenSuccess)
            		  parent.Alert.tip('文件保存成功！');
            	  _isHiddenSuccess=0;
            	  manager.lastFile[_curTabId]=getLastFile();
            	  manager.get('resize').resetIsUpdate();
              }  
              else{
            	  parent.Alert.tip('文件保存失败！');
              }
          }
     });
}
//添加工程
$('<div class="x-add" title="添加工程"></div>')
	.appendTo($('.l-layout-left .l-layout-header'))
	.click(function(){
		var addWin = null;
		addWin = $.ligerDialog.open({
			width : 400,
			height : 200,
			title : "添加工程",
			isResize : false,
			allowClose: false,
			content:'<form style="margin-top: 50px;margin-left:20px;" name="addProject" id="addProject"/>',
			buttons:[
		         { text: '确定',  type:'save' ,width: 80 , onclick:function(item, dialog){
		        	 var temp = checkName(-1,addProjectForm.getData().projectName);
		        	 if(temp){
		        		 var flag=submitAddProjectForm();
			        	 if(flag){
			        		 addWin.close();
			        	 } 
		        	 }
				 }},
				 { text: '取消',  type:'close', width: 80 ,  onclick:function(item, dialog){
					 addWin.close();
				 }}
			]
			
		});
		
	    addProjectForm = $("#addProject").ligerForm({
			labelAlign:'right',
	        inputWidth: 200, 
	        labelWidth: 100, 
	        space: 20,
	        validate:true,
	        fields: [
	             { display: "工程名", name: "projectName",id:"projectName",
	            	 type: "text", validate:{required:true,maxlength:50}
	             }
	        ]
		});
	});

//工作空间右键菜单
var tree_menu = $.ligerMenu({	
	items:[{ 
		id:'new',
		text: '新建文件夹',
		click: function(item,i){
			var addWin = null;
			addWin = $.ligerDialog.open({
				width : 400,
				height : 200,
				title : "新建文件夹",
				isResize : false,
				allowClose: false,
				content:'<form style="margin-top: 50px;margin-left:20px;" name="addFolder" id="addFolder"/>',
				buttons:[
			         { text: '确定',  type:'save' ,width: 80 , onclick:function(item, dialog){
			        	 var temp = checkName(_curNode.data.id,addFolder.getData().folderName);
			        	 if(temp){
				        	 var flag = submitAddFolderForm();
						 	 if(flag){
						 		addWin.close(); 
						 	 }
			        	 }
					 }},
					 { text: '取消',  type:'close', width: 80 ,  onclick:function(item, dialog){
						 addWin.close();
					 }}
				]
				
			});
			addFolder = $("#addFolder").ligerForm({
				labelAlign:'right',
		        inputWidth: 200, 
		        labelWidth: 100, 
		        space: 20,
		        validate:true,
		        fields: [
		             { display: "文件夹名称", name: "folderName",id:"folderName",
		            	 type: "text", validate:{required:true,maxlength:50}
		             }
		        ]
			});
		} 
	},{ line: true },{ 
		id:'add',
		text: '添加文件',
		click: function(item,i){
			var $target=$(['<form method="post">',
			    '<table cellpadding="0" cellspacing="0" class="l-table-edit">',
		            '<tr>',
		                '<td align="right" class="l-table-edit-td" style="width:60px">文件名:</td>',
		                '<td align="left" class="l-table-edit-td"><input style="width:165px" name="name" type="text"/></td>',
		                '<td align="left"></td>',
		            '</tr>',
		            '<tr>',
		                '<td align="right" class="l-table-edit-td">宽度:</td>',
		                '<td align="left" class="l-table-edit-td"><input style="width:115px" name="width" type="text" value="2482"/>&nbsp;像素</td>',
		                '<td align="left"></td>',
		            '</tr>',
		            '<tr>',
		                '<td align="right" class="l-table-edit-td">高度:</td>',
		                '<td align="left" class="l-table-edit-td"><input style="width:115px" name="height" type="text" value="1862"/>&nbsp;像素</td>',
		                '<td align="left"></td>',
		            '</tr>',
		        '</table>',
		    '</form>'].join(''));
			
			_dlg=$.ligerDialog.open({
				title:'添加文件',
				width:300,
				height:200,
				target:$target,
		        buttons: [
		         { text: '确定', type:'save',width:80,onclick: function (item, dialog) { 
		        	 var name=$target.find('[name=name]');
		        	 if(name.val().trim()==''){
		        		 name.addClass('l-error').attr('title','文件名不能空！').keyup(function(){
		        			 name.removeClass('l-error').removeAttr('title');
		        		 });
		        		 return;
		        	 }
		        	 var width=$target.find('[name=width]');
		        	 if(width.val().trim()==''){
		        		 width.addClass('l-error').attr('title','宽度不能空！').keyup(function(){
		        			 width.removeClass('l-error').removeAttr('title');
		        		 });
		        		 return;
		        	 }
		        	 if(!/^[0-9]*[1-9][0-9]*$/g.test(width.val().trim())){
		        		 width.addClass('l-error').attr('title','格式错误，必须是正整数！').keyup(function(){
		        			 width.removeClass('l-error').removeAttr('title');
		        		 });
		        		 return;
		        	 }
		        	 var height=$target.find('[name=height]');
		        	 if(height.val().trim()==''){
		        		 height.addClass('l-error').attr('title','高度不能空！').keyup(function(){
		        			 height.removeClass('l-error').removeAttr('title');
		        		 });
		        		 return;
		        	 }
		        	 if(!/^[0-9]*[1-9][0-9]*$/g.test(height.val().trim())){
		        		 height.addClass('l-error').attr('title','格式错误，必须是正整数！').keyup(function(){
		        			 height.removeClass('l-error').removeAttr('title');
		        		 });
		        		 return;
		        	 }
		        	 if(width.val()*1>4964){
		        		 width.addClass('l-error').attr('title','宽度最大4976像素！').keyup(function(){
		        			 width.removeClass('l-error').removeAttr('title');
		        		 });
		        		 return;
		        	 }
		        	 if(height.val()*1>3724){
		        		 height.addClass('l-error').attr('title','高度最大3724像素！').keyup(function(){
		        			 height.removeClass('l-error').removeAttr('title');
		        		 });
		        		 return;
		        	 }
		        	 var temp = checkName(_curNode.data.id,name.val());
		        	 if(temp){
		        		 var flag = addNode1(name.val(),width.val()*1,height.val()*1);
			        	 if(flag){
			        		 _dlg.close(); 
			        	 }} 
		        	 }
		         },
		         { text: '取消', type:'close',width:80, onclick: function (item, dialog) { 
		        	 	_dlg.close(); 
		        	 } 
		         }
		        ]
			});
		} 
	},{ 
		id:'edit',
		text: '修改文件名', 
		click: function(item,i){
			var modifyWin = null;
			modifyWin = $.ligerDialog.open({
				width : 400,
				height : 200,
				title : "修改文件名",
				isResize : false,
				allowClose: false,
				content:'<form style="margin-top: 50px;margin-left:20px;" name="modifyFileName" id="modifyFileName"/>',
				buttons:[
			         { text: '确定',  type:'save' ,width: 80 , onclick:function(item, dialog){
			        	 var temp = checkName(_curNode.data.owner,modifyFileName.getData().fileName);
			        	 if(temp){
			        		 var flag = submitModifyFileNameForm();
						 	 if(flag){
						 		modifyWin.close(); 
						 	 }
			        	 }
					 }},
					 { text: '取消',  type:'close', width: 80 ,  onclick:function(item, dialog){
						  modifyWin.close();
					 }}
				]
			});
			modifyFileName = $("#modifyFileName").ligerForm({
				labelAlign:'right',
		        inputWidth: 200, 
		        labelWidth: 100, 
		        space: 20,
		        validate:true,
		        fields: [
		             { display: "文件名", name: "fileName",id:"fileName",
		            	 type: "text",value:_curNode.data.text,validate:{required:true,maxlength:50}
		             }
		        ]
			});
		} 
	},{ line: true },{
		id:'copy',
		text:'复制',
		click:function(){
			clipboard=_curTabId;
		}
	},{
		id:'paste',
		text:'粘贴',
		click:function(){
			if(clipboard){
				var addWin = null;
				addWin = $.ligerDialog.open({
					width : 400,
					height : 200,
					title : "复制文件",
					isResize : false,
					allowClose: false,
					content:'<form style="margin-top: 50px;margin-left:20px;" name="copyFile" id="copyFile"/>',
					buttons:[
				         { text: '确定',  type:'save' ,width: 80 , onclick:function(item, dialog){
				        	 var temp = checkName(_curNode.data.id,copyFile.getData().copyFileName);
				        	 if(temp){
				        		 var flag = submitCopyFileForm();
					        	 if(flag){
					        		 addWin.close(); 
					        	 }
				        	 }
						 }},
						 { text: '取消',  type:'close', width: 80 ,  onclick:function(item, dialog){
							 addWin.close();
						 }}
					]
					
				});
				copyFile = $("#copyFile").ligerForm({
					labelAlign:'right',
			        inputWidth: 200, 
			        labelWidth: 100, 
			        space: 20,
			        validate:true,
			        fields: [
			             { display: "文件名", name: "copyFileName",id:"copyFileName",
			            	 type: "text", validate:{required:true,maxlength:50}
			             }
			        ]
				});	
			}				
		}
	},{ 
		id: 'del', 
		text: '删除', 
		click: function(item,i){
			debugger;
			$.ligerDialog.confirm('确认删除“'+_curNode.data.text+'”吗？', '系统提示',function (yes) { 
				if(yes){
					$.ajax({  
				          url: 'rest/project/delNode',  
				          type: 'POST',  
				          data: {
				        	  id:_curNode.data.id
				       	  },  
				          success: function (ret) {
				        	
				             if(ret=='T'){
				            	 var did=$(_curNode.target).siblings().eq(0).attr('id');
				            	 treeManager.remove(_curNode.target);
				            	 tabManager.removeTabItem('tabitem'+_curTabId);
				            	 parent.Alert.tip('删除成功！');
				             } else if(ret=='F'){
				            	 parent.Alert.tip('删除失败！');
				             }else{
				            	 parent.Alert.tip('该文件夹下有子文件，不能直接删除！');
				             }
				          }
				    });
				}					
			});			
		}  
	}]
});
//验证名称是否可用
function checkName(owner,data){
	var flag = null;
	if(data == ""){
		flag = false;
		parent.Alert.tip("名称不能为空！");
	}else{
		$.ajax({  
	          url: 'rest/project/getNode',  
	          type: 'POST', 
	          async:false,
	          data: {
	        	  name:data,
	        	  owner:owner,
	       	  },  
	          success: function (ret) {
	              if(ret == "T"){//工程名不重复，可以添加
	            	  flag = true;
	              }else{
	            	  flag = false;
	            	  parent.Alert.tip('名称不能重复！');
	              }
	          }
	     });
	}
	return flag;
}
//提交添加工程的表单
function submitAddProjectForm() {
	var data = addProjectForm.getData();
	var flag = false;
	$.ajax({  
      url: 'rest/project/addProj',  
      type: 'POST', 
      async:false,
      data: {
    	  name:data.projectName,
    	  isleaf:0
   	},  
      success: function (ret) {
          if(ret){
        	  treeManager.append(null, [{
        		  id:ret*1,
        		  text:data.projectName,
        		  owner:0,
        		  isleaf:0
			  }]);
        	  flag=true;
        	  parent.Alert.tip('新建工程成功！');
          }else{
        	  flag=false;
        	  parent.Alert.tip('新建工程失败！');
          }
      }
	});
	return flag;
}

//提交添加文件夹的表单
function submitAddFolderForm() {
	var flag = false;
	var data = addFolder.getData();
	$.ajax({  
	    url: 'rest/project/addNode',  
	    type: 'POST',  
	    async:false,
	    data: {
		   	name:data.folderName,
		   	owner:_curNode.data.id,
		   	isleaf:0
	  	},  
	    success: function (ret) {
	       if(ret){
	       treeManager.append(_curNode.target, [{
				id:ret*1,
	       		text:data.folderName,
				owner:_curNode.data.id,
				isleaf:0
	       }]);
	       flag = true;
	       parent.Alert.tip('新建文件夹成功！');
	       }else{
	    	   flag = false; 
	       parent.Alert.tip('新建文件夹失败！');
	       }
	    }
	});
	return flag;
} 

//提交修改文件名的表单
function submitModifyFileNameForm() {
	var flag = false;
	var data = modifyFileName.getData();
	$.ajax({  
          url: 'rest/project/editNode',  
          type: 'POST', 
          async:false,
          data: {
        	  id:_curNode.data.id,
        	  name:data.fileName
       	  },  
          success: function (ret) {
             if(ret=='T'){
            	 if(_curTabId == _curNode.data.id){
            		 _fileName = data.fileName;
            		 $('#editAreaText').html(_fileName);
            	 }
            	treeManager.update(_curNode.target, {
            		text:data.fileName
            	});
				$(".l-expandable-close", $(_curNode.target)).click();
				flag = true;
				parent.Alert.tip('修改成功！');
             }else{
            	flag = false;
            	parent.Alert.tip('修改失败！');
             }
          }
    });	
	
	return flag;
} 
//提交复制文件的表单
function submitCopyFileForm(){
	var flag = false;
	var data = copyFile.getData();
	flag = addNode(data.copyFileName,function(pid){
		var fileFlag = false;
		var formData=new FormData(),
			resize=manager.resize[clipboard];
		formData.append('proId',pid);
		formData.append('name',data.copyFileName);
		formData.append('data',resize.getData(true));
		formData.append('srcProjId',clipboard);
		var blobs=resize.getBlobImg();
		for(var k in blobs){
			formData.append("files['"+k+"']",blobs[k]);
		}
		var dels=resize.getDels();
		for(var k in dels){
			formData.append('dels',dels[k]);
		}
		$.ajax({  
	          url: 'rest/file/copyFile',  
	          type: 'POST',  
	          data: formData, 
	          async:false,
	       	  processData: false,
	          contentType:false,
	          success: function (ret) {
	              if(ret=='T'){
	            	  manager.lastFile[_curTabId]=getLastFile();
	            	  fileFlag = true;
	              }  
	              else{
//		            	  parent.Alert.tip('文件保存失败！');
	            	  fileFlag = false;
	              }
	          }
	    });
		clipboard=null;
		return fileFlag;
	});	    				
	return flag;	
}
//工具箱右键菜单
var lib_menu = $.ligerMenu({	
	items:[{ 
		text: '添加图元库',
		icon:'add',
		click: function(item,i){
			addLib(_curTabId);
		} 
	},{line:true},{
		text: '移除图元库',
		icon:'delete',
		click: function(item,i){
			var aid=null;
			tab('[name=accordion] .l-accordion-content').each(function(){
				if($(this).css('display')=='block'){
					aid=$(this).attr('aid');
					return;
				}				
			});
			if(aid){
				$.ligerDialog.confirm('确认删除吗？', '系统提示',function (yes) { 
					if(yes){
						$.ajax({  
					          url: 'rest/project/delProjLib',  
					          type: 'POST',  
					          data: {
					        	  id:aid
					       	  },  
					          success: function (ret) {  
					             if(ret=='T'){
					            	 loadPlug(_curTabId);
					            	 bindPlugEvent();
					             }  
					             else{
					            	parent.Alert.tip('移除图元失败！');
					             }
					          }
					    });
					}					
				});	
			}
		} 
	}]
});
//添加树节点
function addNode(val,fn){
	var flag = false;
	$.ajax({  
        url: 'rest/project/addNode',  
        type: 'POST',
        async:false,
        data: {
      	  	name:val,
      	  	owner:_curNode.data.id,
      	  	isleaf:1
     	},  
        success: function (ret) {  
           if(ret){        	   
        	   treeManager.append(_curNode.target, [{
        		   id:ret*1,
        		   text:val,
        		   owner:_curNode.data.id,
        		   isleaf:1
        	   }]);
        	   $(".l-expandable-close", $(_curNode.target)).click();
        	   flag = true;
        	   if(fn){
        		   flag =  fn(ret);
        	   }
        	   $('#'+ret).click();
           }else{
        	   parent.Alert.tip('增加节点失败！');
        	   flag = false;
           }
        }
  });
	return flag;
}

//添加文件
function addNode1(val,w,h){
	var flag = false;
	$.ajax({  
        url: 'rest/project/addNode1',  
        type: 'POST',
        async:false,
        data: {
      	  	name:val,
      	  	owner:_curNode.data.id,
      	  	isleaf:1,
      	  	data:JSON.stringify({width:w,height:h})
     	},  
        success: function (ret) {  
           if(ret){        	   
        	   treeManager.append(_curNode.target, [{
        		   id:ret*1,
        		   text:val,
        		   owner:_curNode.data.id,
        		   isleaf:1
        	   }]);
        	   $('#'+ret).click();
        	   flag = true;
           }else{
        	   flag = false;
        	   parent.Alert.tip('添加文件失败！');
        	   
           }
        }
	});
	return flag;
}

//添加插件库
function addLib(id){
	var $target=$('<div style="margin:0;"></div>');
	_dlg=$.ligerDialog.open({
		title:'添加图元库',
		width:600,
		height:420,
		target:$target,
        buttons: [
         { text: '选择',type:'save',width:80, onclick: function (item, dialog) { 
        	 var selected=_grid.getSelecteds();
        	 var vals='';
        	 $(selected).each(function (i, rowdata){
        		 vals+=','+'('+id+','+rowdata.id+')';
        	 });
        	 $.ajax({  
	   	          url: 'rest/project/addProjectLib',  
	   	          type: 'POST',  
	   	          data: {
	   	        	  values:vals.substr(1)
	   	          },  
	   	          success: function (ret) {  
	   	             if(ret=='T'){
	   	            	loadPlug(id);
	   	            	bindPlugEvent();
	   	            	_dlg.close();
	   	             }  
	   	             else{
	   	            	parent.Alert.tip('数据库操作异常！');
	   	             }
	   	          }
	   	    });
         } },
         { text: '关闭', type:'close',width:80,onclick: function (item, dialog) { _dlg.close(); } }
        ]
	});	
	_grid=$target.ligerGrid({
		height:340,
	    columns: [
	       { display: '图元库名称', name: 'name',align: 'left', width: 100 },
	       { display: '说明', name: 'comment',align: 'left', width: 200 }
	    ],       
	    checkbox:true,
	    url:'rest/lib/getSelectLib',
	    delayLoad:true,
	    usePager:false
	});	
	_grid.loadServerData({proId:id});
}
//加载插件库
function loadPlug(id){
	$.ajax({  
         url: 'rest/project/getProjLib',  
         type: 'POST',  
         data: {
       	  proId:id
         },  
         async:false,
         success: function (ret) {
        	 var json=getObject(ret);
             if(json){
            	var $accordion=tab('[name=accordion]').html('').removeAttr('ligeruiid');
	           	for(var k in json){
	           		var $ul=$('<ul class="x-accordion-margin"></ul>');
	           		for(var i=0;i<json[k].plugs.length;i++){
	           			var plug=json[k].plugs[i];
	           			$('<li><img src="'+getUrl(plug.icon)+'" class="x-accordion-mid x-accordion-pic"><span class="x-accordion-mid x-accordion-item">'+plug.name+'</span></li>')
	           				.appendTo($ul)
	           				.data({
	           					id:plug.id,	           					
	           					name:plug.name,
	           					comment:plug.comment,
	           					type:plug.type,
	           					img:plug.img,
	           					exps:plug.exps
	           				}); 	            			
	           		}
	           		$ul.appendTo($('<div aid="'+json[k].aid+'" title="'+json[k].name+'"></div>').appendTo($accordion));
	           	}
	           	$accordion.ligerAccordion({height:h-26});
            }
         }
   });
}
//创建绘图区

function createGraph(id,w,h){
	var container=tab('.resize-container');
	container.data('id',id);
	var $graBind=tab('[name=graphBind]');
	
	var resize=new Resize(container,{onSelected:function(id,pid,type,name,comment){
		$graBind.text(name).data({
			id:id,
			pid:pid,
			type:type,
			name:name,
			comment:comment
		});	
		tab("[name=commentGraph]").text(comment);
		if(window._intervalId)_binds=manager.get('resize').getBindParams();
	},onBind:function(){
		tab("[name=bindButton]").click();
	},width:w,height:h});
	return resize;
}
//绑定插件事件
function bindPlugEvent(){
	tab('[name=accordion]').find('ul').each(function(){
		$(this).find('li').each(function(){
			var $li=$(this),data=$li.data(),$img=$li.find('img');
			var params={
					pid:data.id,
					name:data.name,
					comment:data.comment,
					type:data.type
				};
			if(params.type*1){
				params.w=200;
				params.h=200;
			}
			else{
				params.w=100;
				params.h=100;
				params.img=data.img;
				params.exps=data.exps;
			}
			manager.get('resize').addButton($img,params);
		});
	});
}
//获取最近文件
function getLastFile(){
	var lastFile=null;
	$.ajax({  
	     url: 'rest/file/getLastFile',  
	     type: 'POST',  
	     data: {proId:_curTabId},  
	     async: false,
	     success: function (ret) {  
	    	 lastFile = getObject(ret);	         
	     }
	});
	return lastFile;
}
//根据ID获取数据
function getDataById(datas,id){
	var selected=null;
	$(datas).each(function (i, item){
        if(item.id==id){
        	selected=item;
        	return;
        }
    });
	return selected;
}


