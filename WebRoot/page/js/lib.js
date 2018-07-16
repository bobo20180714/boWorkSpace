var h=$('.main').height(),
	plug_id=null,
	content=null;
$("#layout").ligerLayout({
	leftWidth:400,
	height:h,
	maxLeftWidth:400,
	onCollapsed :function(){
		plugGrid.reRender();
	}
});
//图元库列表
var libGrid=$("#lib-grid").ligerGrid({
    height:'100%',
    columns: [
       { display: '图元库名称', name: 'name',align: 'left', width: 100 },
       { display: '说明', name: 'comment',align: 'left', minWidth: 100 }
    ], 
    url:'rest/lib/getLib',  
    rownumbers:true,
    usePager:false,
    heightDiff:30,
    onSelectRow :function(rowdata, rowid, rowobj){
    	var selected=libGrid.getSelected();
    	plugGrid.loadServerData({libId:selected.id});
    },
    toolbar:{items:[{text:'注册',click:function(){//注册图元库
    	showDlg('注册图元库','','');
    },icon:'add'},{text:'编辑',click:function(){//编辑图元库
    	var selected=libGrid.getSelected();
    	if(!selected)return;
    	showDlg('编辑图元库',selected.name,selected.comment);
    	_id=selected.id;
    },icon:'update'},{text:'删除',click:function(){//删除图元库
    	var selected=libGrid.getSelected();
    	if(!selected)return;
    	$.ligerDialog.confirm('确认删除吗？', '系统提示',function (yes) { 
    		if(yes){
    			$.ajax({  
    		          url: 'rest/lib/delLib',  
    		          type: 'POST',  
    		          data: {
    		        	  id:selected.id
    		       	  },  
    		          success: function (ret) {  
    		             if(ret=='T'){
    		            	 parent.Alert.tip('删除图元库成功！');
    		            	 libGrid.loadServerData(true);
    		            	 plugGrid.loadData({Rows:[]});
    		             }  
    		             else{
    		            	 parent.Alert.tip('删除图元库失败！');
    		             }
    		          }
    		    });
    		}					
    	});
    },icon:'delete'},{line:true}]},
    onError:goback
});
function showDlg(title,name,comment){
	_dlg=$.ligerDialog.open({
		title:title,
		width:400,
		height:210,
		content:$('#tpl_dlg').html(),
		buttons:[
		         { text: '保存',type:'save', width: 80 ,onclick:function(item, dialog){
		        	 var name=$('#lib-dlg [name=name]').val();
		 			if(name==''){
		 				parent.Alert.tip('图元库名称不能空！');
		 				return;
		 			}
		 			var comment=$('#lib-dlg [name=comment]').val();
		 			var url,data;
		 			if(window._id){
		 				url='rest/lib/editLib';
		 				data={
		 				   id:_id,
		 		           name:name,
		 		           comment:comment
		 		       	};
		 			}
		 			else{
		 				url='rest/lib/addLib';
		 				data={
		 		           name:name,
		 		           comment:comment
		 		       	};
		 			}
		 			$.ajax({  
		 		          url: url,  
		 		          type: 'POST',  
		 		          data: data,  
		 		          success: function (ret) {  
		 		             if(ret){
		 		            	 if(window._id){
		 		            		parent.Alert.tip("编辑图元库成功！");
		 			        		 libGrid.updateRow(libGrid.getSelected(),{
		 			        			 name:name,
		 			        			 comment:comment
		 			        		 });
		 		            	 }
		 		            	 else{
		 		            		parent.Alert.tip("新增图元库成功！");
		 		            		 libGrid.addRow({id:ret,name:name,comment:comment});
		 		            	 }
		 		            	 _dlg.close();
		 		             }  
		 		             else{
		 		            	parent.Alert.tip(window._id?'编辑图元库失败！':'注册图元库失败！');
		 		             }
		 		             _id=null;
		 		          }
		 		    });
				 }},
				 { text: '关闭',type:'close', width: 80, onclick:function(item, dialog){
					 _dlg.close();
				 }}
			]
	}); 
	$('#lib-dlg input[name=name]').ligerTextBox({width:250,value:name});
	$('#lib-dlg [name=comment]').val(comment);
	$('#lib-dlg').find('input[type=button]').click(function(){
		if($(this).val()=='确定'){
			 
		}
		else{//取消
			_dlg.close();
		}
	});
}
//搜索框
$('#lib-grid .l-panel-topbar').css('margin','0').append('<div class="left l-text" style="width:110px;margin-top: 3px;"><input id="x-key" placeholder="请输入关键字" class="l-text-field"></div>');
$('#x-key').keyup(function(){
	var key=$(this).val();
	libGrid.loadServerData({key:key});
});
//插件列表
var plugGrid=$("#content").ligerGrid({
    height:'100%',
    width:'100%',
    heightDiff:30,               
    frozen: false,  
    url:'rest/plug/getPlugByLibId',
    delayLoad:true,
    columns: [
       { display: '图标', name:'icon', width: 60, minWidth: 30,render:function(rowdata, index, value){
			return '<img src="'+getUrl(rowdata.icon)+'" class="x-icon">';
       } },
       { display: '插件名', name: 'name',align: 'left', width: 120 },
       { display: '图元类型', name: 'type', width: 60, minWidth: 30,render:function(rowdata, index, value){
		   return value==0?'静态图元':'动态图元';
	   } },
	   { display: '说明', name: 'comment',align: 'left', minWidth: 100 }
    ],       
    rownumbers:true,
    usePager:false,
    toolbar:{items:[{text:'添加',click:function(){//添加插件
    	var selected=libGrid.getSelected();
    	if(selected)
    		addPlug(selected.id);
    	else
    		parent.Alert.tip('请选择图元库！');
    },icon:'add'},{text:'删除',click:function(){//删除插件
    	var selected=plugGrid.getSelected();
    	if(!selected)return;
    	$.ligerDialog.confirm('确认删除吗？', '系统提示',function (yes) { 
    		if(yes){
    			$.ajax({  
    		          url: 'rest/lib/delLibPlug',  
    		          type: 'POST',  
    		          data: {
    		        	  id:selected.aid
    		       	  },  
    		          success: function (ret) {  
    		             if(ret=='T'){
    		            	var selected1=libGrid.getSelected();
 		   	            	plugGrid.loadServerData({url:'rest/plug/getPlugByLibId',libId:selected1.id});
    		             }  
    		             else{
    		            	 parent.Alert.tip('删除图元失败！');
    		             }
    		          }
    		    });
    		}					
    	});
    },icon:'delete'},{line:true}]},
    onError:goback
});
$('#content .l-panel-topbar').css('margin','0');
function addPlug(id){
	_plugDlg=$.ligerDialog.open({
		title:'选择插件',
		width:680,
		height:420,
		content:'<div id="select-grid" style="margin:0;width:100%;height:100%;overflow: hidden;"></div>',
        buttons: [
         { text: '选择',type:'save',width:80, onclick: function (item, dialog) { 
        	 var selected=selectGrid.getSelecteds();
        	 var vals='';
        	 $(selected).each(function (i, rowdata){
        		 vals+=','+'('+id+','+rowdata.id+')';
        	 });
        	 $.ajax({  
	   	          url: 'rest/lib/addLibPlug',  
	   	          type: 'POST',  
	   	          data: {values:vals.substr(1)},  
	   	          success: function (ret) {  
	   	             if(ret){
	   	            	plugGrid.loadServerData({libId:id});
	   	            	_plugDlg.close();
	   	             }  
	   	             else{
	   	            	parent.Alert.tip('数据库操作异常！');
	   	             }
	   	          }
	   	    });
         } },
         { text: '取消',type:'close',width:80, onclick: function (item, dialog) { _plugDlg.close(); } }
        ]
	});	
	var selectGrid=$('#select-grid').ligerGrid({
		height:340,
		width:667,
	    columns: [
	       { display: '图标', name:'icon', width: 60, minWidth: 30,render:function(rowdata, index, value){
				return '<img src="'+getUrl(rowdata.icon)+'" class="x-icon">';
	       } },
	       { display: '插件名', name: 'name',align: 'left', width: 100 },
	       { display: '图元类型', name: 'type', width: 100, minWidth: 30,render:function(rowdata, index, value){
			   return value==0?'静态图元':'动态图元';
		   } },
	       { display: '说明', name: 'comment',align: 'left', width: 200}
	    ],       
	    checkbox:true,                 
        frozen: false, 
        url:'rest/plug/getSelectPlug',
        delayLoad:true,
	    usePager:false
	});	
	selectGrid.loadServerData({libId:id});	
}
