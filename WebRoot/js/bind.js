/* 计算结果 */
function bindJsjg(resize){
	var $layout=$(['<div class="resize-layout" style="margin-left:6px;">',
	               '<div style="width:240px;">',
		               '<div name="title">数据源</div>',
		               '<table><tr height="33px"><td width="53px;" align="center">设备:</td><td><input name="device"></td></tr>',
		               '<tr><td width="53px;" align="center">关键字:</td><td><input name="find"></td></tr></table>',
		               '<div name="param"></div>',
	               '</div>',
	               '<div style="left:250px;width:330px;">',
		               '<div>&nbsp;<b>显示字段</b>',
			               '<div name="selected"></div>',
		               '</div>',			               
	               '</div>',
               '</div>'].join('')),
         _code=null;	
	_dlg=$.ligerDialog.open({
		title:'数据绑定',
		width:600,
		height:500,
		target:$layout,
        buttons: [
        	{text: '确定', type:'save',width:80, onclick: function (item, dialog) {     	        	 
        		var selected=$selected.getSelecteds();
        		var satId=$device.getValue();
        		var jsjgCode=_code;
        		var params=[];
        		$(selected).each(function (i, rowdata){
        			param={};
        			for(var k in selected[i]){
        				if(/^__/g.test(k))continue;
        				param[k]=selected[i][k];
        			}
        			param.satId=satId;
        			param.jsjgCode=jsjgCode;
        			params.push(param);
	           	});
        		resize.bindParam(params);	            		
         		_dlg.close();           		
        	}},
         	{text: '取消', type:'close',width:80, onclick: function (item, dialog) { _dlg.close();}}
        ]
	});	
	var $param=$layout.find('[name=param]').ligerGrid({
	    width:240,
		height:350,
	    columns: [
	        { display:'名称',name:'name',width: 120,align:'left'},
	        { display:'代号',name:'code',align:'left'}    
	    ], 
	    rownumbers:true,
	    usePager:false,
	    delayLoad:true,           
        frozen: false,  
	    url:'rest/jar/getJsjg',
	    onSelectRow :function(rowdata, rowid, rowobj){
	    	var selected=$param.getSelected();
	    	_code=selected.code;
	    	$selected.loadServerData({jsjgId:selected.id});
	    }
	});
	var $find=$layout.find('[name=find]').ligerTextBox({
//		label:'关键字',
		labelWidth:50,
		labelAlign:'right',
		width:100,
		onKeyup:function(){
			$param.loadServerData({satId:$device.getValue(),key:$find.getValue()});
		}
	});	
	var $device=$layout.find('[name=device]').ligerComboBox({
//		label:'设备',
		autocomplete:true, 
		cancelable:false,
		labelWidth:50,
		labelAlign:'right',
		width:100,
		url:'rest/jar/getSats',
//		selectedIndex:0,
		onSelected:function(id){
			$param.loadServerData({satId:id,key:''});
		}
	});		
	var $selected=$layout.find('[name=selected]').ligerGrid({
	    width:320,
		height:425,
	    columns: [
	        { display: '字段名称', name:'name', width: 120,align: 'left'},
	        { display: '字段代号', name: 'code',align: 'left'}    
	    ], 
	    checkbox:true,
	    usePager:false,  
	    frozen:false,    
        frozen: false,  
	    url:'rest/jar/getJsjgField',
	    delayLoad:true
	}); 
}
/* 基础数据 */
function bindParam(devData,resize,proIdTemp){	
	var devices={
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
	var $layout=$(['<div class="resize-layout" style="margin-left:6px;">',
		               '<div style="width:240px;">',
			               '<div name="title">数据源</div>',
			               '<table><tr height="33px"><td width="53px;" align="center">设备:</td><td><input name="device"></td></tr>',
			               '<tr><td width="53px;" align="center">关键字:</td><td><input name="find"></td></tr></table>',
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
		title:'数据绑定',
		width:680,
		height:500,
		target:$layout,
        buttons: [
        	{text: '确定',  type:'save',width:80,onclick: function (item, dialog) {     	        	 
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
         	{text: '取消', type:'close',width:80, onclick: function (item, dialog) { _dlg.close();}}
        ]
	});		
	var $param=$layout.find('[name=param]').ligerGrid({
	    width:240,
		height:326,
	    columns: [
	        { display:'名称',name:'name',width: 120,align:'left'},
	        { display:'代号',name:'code',align:'left'}    
	    ], 
	    rownumbers:true,
	    usePager:true,
	    pageSize:20,
	    delayLoad:true,           
        frozen: false,  
	    url: 'rest/jar/getParams'
	});
	var $find=$layout.find('[name=find]').ligerTextBox({
//		label:'关键字',
		labelWidth:50,
		labelAlign:'right',
		width:100,
		onKeyup:function(e){
//			$param.loadData(devices.filter(_devId));
			var devId = $device.getValue();
			if(!devId){
				return;
			}
			$param.set('parms', {
				jarId:devId,
				key:e
			});
			$param.loadData();
		}
	});	
	var $device=$layout.find('[name=device]').ligerComboBox({
//		label:'设备',
		autocomplete:true, 
		cancelable:false,
		labelWidth:50,
		labelAlign:'right',
		width:100,
//		data:devData,
		url:'rest/jar/getSelectedJar?proId='+proIdTemp,
//		selectedIndex:0,
		onSelected:function(id){
			_devId=id;
			/*if(!devices.hasOwnProperty(id)){
				$.ajax({  
				     url: 'rest/jar/getParams',  
				     type: 'POST',	
				     data:{jarId:id},
				     async: false,  
				     success: function (ret) {
				    	 devices[id]=eval("("+ret+")");						         
				     }
				});
			}	    			
			$param.loadData(devices.filter(_devId));*/
			$param.set('parms', {
				jarId:id
			});
			$param.loadData();
		}
	});		
	var $selected=$layout.find('[name=selected]').ligerGrid({
	    width:330,
		height:425,       
	    columns: [
	        { display: '设备名称', name:'device', width: 100,align: 'left'},
	        { display: '参数名称', name:'name', width: 120,align: 'left',editor: { type: 'text' }},
	        { display: '参数代号', name: 'code',align: 'left'}    
	    ], 
	    rownumbers:true,
	    frozen:false,
	    usePager:false,
	    data:{Rows:bindParams},
	    enabledEdit:true
	}); 
	$layout.find('[name=add]').ligerButton({
		text:'添加 >',
	    click: function (){
	    	var rowdata=$param.getSelected();
	    	if(!rowdata)return;
	    	

	    	var flag = false;
	    	var selectedDatas = $selected.rows;
	    	for (var i = 0; i < selectedDatas.length; i++) {
				if(rowdata.id != selectedDatas[i].parId){
					continue;
				}
				//已存在
				flag = true;
			}
	    	if(flag){
	    		$.ligerDialog.warn('该参数已经选择！');
	    		return;
	    	}
    		$selected.addRow({
    			devId:_devId,
    			parId:rowdata.id,
    			device:$device.getText(),
    			name:rowdata.name,
    			code:rowdata.code
    		});
    		//devices.hide(_devId,rowdata);
    		//$param.loadData(devices.filter(_devId));
	    }
	});
	$layout.find('[name=remove]').ligerButton({
		text:'移除 <',
	    click: function (){
	    	var rowdata=$selected.getSelected();
	    	if(!rowdata)return;
	    	//$device.setValue(rowdata.devId);
	    	//rowdata.name=devices.getParamName(rowdata.devId,rowdata.parId);	    	    		    	
	    	$selected.remove(rowdata);
	    	//devices.show(rowdata.devId,rowdata.parId);
	    	//$param.loadData(devices.filter(_devId));
	    }
	});
	$layout.find('[name=removeAll]').ligerButton({
		text:'全部移除',
	    click: function (){
	    	var rowdatas=$selected.rows;
	    	for(var i=0;i<rowdatas.length;i++){
	    		var rowdata=rowdatas[i];
	    		//rowdata.name=devices.getParamName(rowdata.devId,rowdata.parId);
	    		//$device.setValue(rowdata.devId);	    	
		    	$selected.remove(rowdata);
		    	//devices.show(rowdata.devId,rowdata.parId);
		    	//$param.loadData(devices.filter(_devId));
	    	}	    	
	    }
	});
}