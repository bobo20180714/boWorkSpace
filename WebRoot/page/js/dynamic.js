var h=$('.main').height();
$("#layout").ligerLayout({
	leftWidth:360,
	height:h
});
var dynamicGrid=$("#dynamic-grid").ligerGrid({
    height:'100%',
    url:'rest/dynamic/getDynamic',
    columns: [
		{ display: '图标', name:'icon', width: 60,render:function(rowdata, index, value){
			return '<img src="'+getUrl(rowdata.icon)+'" class="x-icon">';
		} },
        { display: '图元名', name: 'name',align: 'left', width: 115 },
        { display: '状态', name: 'state', width: 60, minWidth: 30,render:function(rowdata, index, value){
	    	return value==0?'未完成':'已完成';
	    } },
	    { display: '操作', isSort: false, width: 90, minWidth: 30,render:function(rowdata, index, value){
	    	return '<a href="javascript:editDynamic('+rowdata.id+');">编辑</a>&nbsp;<a href="javascript:delDynamic('+rowdata.id+',\''+rowdata.icon+'\');">删除</a>';
	    } }	    
    ], 
    heightDiff:30,
    usePager:false,
    detail:{height:'auto', onShowDetail: function (r, p){
        $(p).append($('<div>【说明】&nbsp;&nbsp;'+r.date+'<p>' + (r.comment?r.comment:'') + '</p></div>').css('margin','0 4px'));
    }},
    toolbar:{items:[{text:'添加图元',click:function(){//添加图元
    	_opener=$.ligerDialog.open({
			title:'添加图元',
			url:'page/adddynamic-dialog.html?isEdit=0',
			width:400,
			height:280,
			buttons:[
			         { text: '保存',type:'save', width: 80 ,onclick:function(item, dialog){
					 	dialog.frame.save();
					 }},
					 { text: '关闭',type:'close', width: 80, onclick:function(item, dialog){
						 _opener.close();
					 }}
				]
		});	
    },icon:'add'}]},
    onError:goback
});
$('#dynamic-grid .l-panel-topbar').css('margin','0');

//编辑图元
function editDynamic(id){
	_dlgInfo=dynamicGrid.getSelected();
	_opener=$.ligerDialog.open({
		title:'修改图元',
		url:'page/adddynamic-dialog.html?isEdit=1',
		width:400,
		height:300,
		buttons:[
		         { text: '保存',type:'save', width: 80 ,onclick:function(item, dialog){
				 	dialog.frame.save();
				 }},
				 { text: '关闭',type:'close', width: 80, onclick:function(item, dialog){
					 _opener.close();
				 }}
			]
	});	
}
//删除图元
function delDynamic(id,icon){
	$.ligerDialog.confirm('确认删除吗？', '系统提示',function (yes) { 
		if(yes){
			$.ajax({  
		          url: 'rest/dynamic/delDynamic',  
		          type: 'POST',  
		          data: {
		        	  id:id,
		        	  icon:icon
		       	  },  
		          success: function (ret) {  
		             if(ret=='T'){
		            	 parent.Alert.tip('删除图元成功！');
		            	 dynamicGrid.loadServerData({
		  					 url:'rest/dynamic/getDynamic'
		  				 });
		             }  
		             else{
		            	 parent.Alert.tip('删除图元失败！');
		             }
		          }
		    });
		}					
	});	
}
//图元工具栏
$("#x-toolbar").ligerToolBar({ items: [
    {id:'test',icon: 'x-clock',tooltip:'测试',text: '测试'},{ line:true },
	{id:'x-btn',text: '绑定参数', click: function (){
		//测试数据
		var devData=[{id:1,text:'设备1'},{id:2,text:'设备2'},{id:3,text:'设备3'}];
		var devices={
				1:{Rows:[{id:1,name:'参数1',code:'param1'},{id:2,name:'参数2',code:'param2'},{id:3,name:'参数3',code:'param3'},{id:4,name:'参数4',code:'param4'}]},
				2:{Rows:[{id:1,name:'参数1',code:'param1'},{id:2,name:'参数2',code:'param2'},{id:3,name:'参数3',code:'param3'}]},
				3:{Rows:[{id:1,name:'参数1',code:'param1'},{id:2,name:'参数2',code:'param2'}]},
				hidden:{},
				show:function(id,row){
					if(this.hidden.hasOwnProperty(id))
						this.hidden[id].splice($.inArray(row.id||row,this.hidden[id]),1);
				},
				hide:function(id,row){
					if(!this.hidden.hasOwnProperty(id))
						this.hidden[id]=[];
					this.hidden[id].push(row.id||row);
				},
				filter:function(id){
					var k=$find.getValue();
					var ret=[];
					var rows=this[id].Rows;
					for(var i=0;i<rows.length;i++){
						if($.inArray(rows[i].id,this.hidden[id])>-1)
							continue;
						if(!k || rows[i].name.indexOf(k)>-1 || rows[i].code.indexOf(k)>-1)
							ret.push(rows[i]);
					}
					return {Rows:ret};
				},
				getParamName:function(id,parId){
					var rows=this[id].Rows;
					for(var i=0;i<rows.length;i++){
						if(rows[i].id==parId)
							return rows[i].name;
					}
					return null;
				}
			};
		//已绑定参数
		var bindParams=clone(resize.getBindParam());
		for(var i=0;i<bindParams.length;i++){
			var e=bindParams[i];
			devices.hide(e.devId,e.parId);
		}
		
		var _devId=null;
		var $layout=$(['<div class="resize-layout">',
			               '<div style="width:240px;">',
				               '<div name="title">数据源</div>',
				               '<input name="device">',
				               '<input name="find">',
				               '<div name="param"></div>',
			               '</div>',
			               '<div style="left:240px;width:80px;padding-left:10px;">',
				               '<div name="add" style="margin-top:180px;"></div>',
				               '<div name="remove" style="margin-top:10px;"></div>',
				               '<div name="removeAll" style="margin-top:10px;"></div>',
			               '</div>',
			               '<div style="right:0;width:330px;">',
				               '<div>&nbsp;<b>已选择参数</b>',
					               '<div name="selected"></div>',
				               '</div>',			               
			               '</div>',
		               '</div>'].join(''));	
		_dlg=$.ligerDialog.open({
			title:'参数绑定',
			width:680,
			height:500,
			target:$layout,
	        buttons: [
	        	{text: '确定', onclick: function (item, dialog) {     	        	 
	        		var rows=$selected.rows;
            		$selected.endEdit(rows);
            		var params=[];
            		for(var i=0;i<rows.length;i++){
            			var param={};
            			for(var k in rows[i]){
            				if(/^__/g.test(k))continue;
            				param[k]=rows[i][k];
            			}
            			params.push(param);
            		}
            		resize.bindParam(params);
	     			_dlg.close();
	        	}},
	         	{text: '取消', onclick: function (item, dialog) { _dlg.close();}}
	        ]
		});		
		var $param=$layout.find('[name=param]').ligerGrid({
		    width:240,
			height:370,
		    columns: [
		        { display:'名称',name:'name',width: 120,align:'left'},
		        { display:'代号',name:'code',align:'left'}    
		    ], 
		    rownumbers:true,
		    usePager:false,
		    delayLoad:true
		});
		var $find=$layout.find('[name=find]').ligerTextBox({
			label:'关键字',
			labelWidth:50,
			labelAlign:'right',
			width:100,
			onKeyup:function(){
				$param.loadData(devices.filter(_devId));
			}
		});	
		var $device=$layout.find('[name=device]').ligerComboBox({
			label:'设备',
			labelWidth:50,
			labelAlign:'right',
			width:100,
			data:devData,
			selectedIndex:0,
			onSelected:function(id){
				_devId=id;
				$param.loadData(devices.filter(_devId));
			}
		});		
		var $selected=$layout.find('[name=selected]').ligerGrid({
		    width:330,
			height:400,
		    columns: [
		        { display: '设备名称', name:'device', width: 100,align: 'left'},
		        { display: '参数名称', name:'name', width: 120,align: 'left',editor: { type: 'text' }},
		        { display: '参数代号', name: 'code',align: 'left'}    
		    ], 
		    rownumbers:true,
		    usePager:false,
		    data:{Rows:bindParams},
		    enabledEdit:true
		}); 
		$layout.find('[name=add]').ligerButton({
			text:'添加 >',
		    click: function (){
		    	var rowdata=$param.getSelected();
		    	if(!rowdata)return;
		    	$selected.addRow({
		    		devId:$device.getValue(),
		    		parId:rowdata.id,
		    		device:$device.getText(),
		    		name:rowdata.name,
		    		code:rowdata.code
		    	});
		    	devices.hide(_devId,rowdata);
		    	$param.loadData(devices.filter(_devId));
		    }
		});
		$layout.find('[name=remove]').ligerButton({
			text:'移除 <',
		    click: function (){
		    	var rowdata=$selected.getSelected();
		    	if(!rowdata)return;
		    	$device.setValue(rowdata.devId);
		    	rowdata.name=devices.getParamName(rowdata.devId,rowdata.parId);		    		    	
		    	$selected.remove(rowdata);
		    	devices.show(rowdata.devId,rowdata.parId);
		    	$param.loadData(devices.filter(_devId));
		    }
		});
		$layout.find('[name=removeAll]').ligerButton({
			text:'全部移除',
		    click: function (){
		    	var rowdatas=$selected.rows;
		    	for(var i=0;i<rowdatas.length;i++){
		    		var rowdata=rowdatas[i];
		    		rowdata.name=devices.getParamName(rowdata.devId,rowdata.parId);
		    		$device.setValue(rowdata.devId);	    	
			    	$selected.remove(rowdata);
			    	devices.show(rowdata.devId,rowdata.parId);
			    	$param.loadData(devices.filter(_devId));
		    	}	    	
		    }
		});
    }},
    {text: '预览', click: function (){
    	if(window._intervalId)return;
    	_intervalId=setInterval(function(){
    		resize.setData([{
    			devId:1,
    			parId:1,
    			val:Math.round(Math.random()*(2))
    		},{
    			devId:1,
    			parId:2,
    			val:Math.round(Math.random()*(100))
    		},{
    			devId:1,
    			parId:3,
    			val:Math.round(Math.random()*(100))
    		},{
    			devId:1,
    			parId:4,
    			val:Math.round(Math.random()*(100))
    		},{
    			devId:2,
    			parId:1,
    			val:Math.round(Math.random()*(100))
    		},{
    			devId:2,
    			parId:2,
    			val:Math.round(Math.random()*(100))
    		},{
    			devId:2,
    			parId:3,
    			val:Math.round(Math.random()*(100))
    		}]);
    	},1000);
    }},
    {text: '停止预览', click: function (){
    	clearInterval(_intervalId);
    	_intervalId=null;
    }},
    {text: '保存数据', click: function (){
    	setCookie('test',resize.getData());
    	alert('ok');
    }},
    {text: '清除数据', click: function (){
    	delCookie('test');
    	alert('ok');
    }},
    { line:true },
    { text: '导出JAR包', click: function(){
    	$.ajax({  
		     url: 'rest/dynamic/exportJar',  
		     type: 'POST',  
		     async: false,  
		     success: function (ret) {
		         if(ret=='T'){
		        	 location.href="tmp/myjs.jar";
		         }
		     }
		});    	
    } , icon:'ok' }
]
});