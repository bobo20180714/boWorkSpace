var isPlay=true;
var $sat = null;
var $time = null;
var $cezhan=null;
var file=null;//图元文件
var devData=null;//设备数据
var jars=null;//动态加载外部数据接收JAR文件 
var webSocket=null;//web通信客户端
var dataManager={//把数据源转换无图元标准数据
	getDevId:function(){//获取设备物理ID
		return this[this.dataSource+''+this.devId].jarId;
	},
	getParId:function(parId){//获取参数物理ID
		return this[this.dataSource+''+this.devId].parIds[parId];
	},
	getParIds:function(){//加载所有参数物理ID
		var k=this.dataSource+''+this.devId,me=this;
		if(!this.hasOwnProperty(k))//未配置数据源
			return false;
        if(!this[k].hasOwnProperty('parIds')){
        	$.ajax({  
    		    url: 'rest/jar/getParIds',  
    		    type: 'POST',
    		    data:{
    		    	jarId:this[k].jarId
    		    },
    		    async: false,  
    		    success: function (ret) {
    		    	me[k].parIds=eval("("+ret+")");
    		    }
    		});	
        }
        return true;
	},
	getData:function(data,satIdTemp){//数据解析  data:一包数据(DataPackage)
		if(data.packageType == 3){
			return data.netLink;
		}
		var graphs=[];
		this.dataSource=data.dataSource;//数据来源：0-基础 1-外部、2-计算结果 3链路监视
		if(this.dataSource*1<2){
			var devId=data.deviceId;
			/*var params=data.params;			
			for(var p in params){        	
				var val=params[p];
				graphs.push({
					devId:devId,
					parId:p,
					code:code,
					val:val
				});
			}*/
			
			var paramList=data.paramList;	
			for (var i = 0; i < paramList.length; i++) {
				var param = paramList[i];
				graphs.push({
					devId:param.devId,
					parId:param.paramId,
					code:param.paramCode,
					val:param.paramValue
				});
			}
		}
		else{
			var satIdTemp2=data.satId;
			if(satIdTemp != satIdTemp2){
				return graphs;
			}
			var jsjgCode=data.jsjgCode;
			var params=data.params;
			for(var p in params){        	
				var val=params[p];
				graphs.push({
					satId:satIdTemp2,
					jsjgCode:jsjgCode,
					code:p,
					val:val
				});
			}
		}		
		return graphs;
	}
};
var w=$('.main').width(),h=$('.main').height();
$("#layout").ligerLayout({
	leftWidth:300,
	allowLeftCollapse:true,
	hideLeftHeader:false,
	heightDiff:0,
	space:1, 
	//allowLeftCollapse :false,
	onCollapsed:function(){
    	/*if(this.left.css('display')=='none'){//收起
    		resize.addDx(205);
    	}
    	else{//展开
    		resize.addDx(-205);
    	}*/
    }
});
//$(".l-tree").parent().height(h-44+16);

var addFileMenu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
	 [
	  { text: '挂接界面文件', click: itemclick, id: 'addRelateFile' }
	  ]
});

var menuClickNode = null;

var fileTreeObj = $(".l-tree").ligerTree({
	checkbox: false,
    needCancel:false,
    onSelect:function (node, e){
    	
    	//判断是否是自定义界面
    	if(node.data.node_type == "3"){
    		if(node.data.open_mode == "1"){
    			//打开窗口
    			$.ligerDialog.open({
					width : 500,
					height : 400,
					title : node.data.page_name,
					isResize : true,
					url : node.data.page_url,
			});
    		}else if(node.data.open_mode == "2"){
    			//打开新选项卡
    			window.open(node.data.page_url);
    		}
    		return;
    	}
    	
		showMask();
		
		//获取卫星节点ID
		var satData = getSatNodeId(node);
		if(satData == null){
			//展开节点
			expendNode(node);
			return;
		}
		if(node.data.node_type == 5 && load(node.data.obj_id,satData.obj_id)) {
			connect();
			resize.hideSelected();
		}else{
			expendNode(node); 
		}
	},
    textFieldName:'text',
	idFieldName : 'id',
	parentIDFieldName : 'pid',
	height: "100%",
	nodeWidth:"100%",
	url:'rest/SatRelateFile/queryMonitorTree?ownerId=-1',
	isLeaf : function(data){
		return data.isleaf == 0;
	},
	delay: function(e)
	{
	     var data = e.data;
	     return { url: 'rest/SatRelateFile/queryMonitorTree?ownerId=' + data.id };
	 },
	 onContextmenu: function (nodeTemp, e){
		 hiddenMenu();
		 menuClickNode = nodeTemp;
    	 if(isCanCreate(nodeTemp.data) == "1"){
    		 addFileMenu.show({ top: e.pageY, left: e.pageX });
    	 }
    	 return false;
	 }

});

function expendNode(node){
	//展开节点
	 var treeitembtn = $(node.target).find("div.l-expandable-open:first,div.l-expandable-close:first");
	 if(treeitembtn.length > 0){
		 $(treeitembtn[0]);
		 if(treeitembtn[0].className == "l-box l-expandable-close"){
			 $(".l-expandable-close", $(node.target.firstChild)).click();
		 }else{
			 //关闭
			 $(".l-expandable-open", $(node.target)).click();
		 }
	 }
}

/**
 * 是否可以弹出右键菜单
 * @param data
 * @returns {Boolean}
 */
function isCanCreate(data){
	if((data.node_type == "1" && data.isroot == "1")
			 || data.node_type == "4"){
		//弹出新建文件夹菜单
			return "1";
	}
	if(data.node_type == "0" ){
		//弹出新建文件菜单
		return "2";
	}
	return "false";
}

/**
 * 隐藏右键菜单
 */
function hiddenMenu(){
	addFileMenu.hide();
}

//工具条按钮事件
function itemclick(item){
	 if(item.id){
		 switch (item.id){
		 case "addRelateFile":
        	 if((menuClickNode.data.node_type != "1"
        		 || menuClickNode.data.isroot != "1")
        		 && menuClickNode.data.node_type != "4"){
        		 Alert.tip("请选择卫星节点或文件夹节点！");
        		 return;
        	 }
        	 setCurrWindows();
        	 var relateWin = parent.$.ligerDialog.open({
					width : 820,
					height : 500,
					title : "关联界面文件",
					isResize : false,
					url : "../page/select_file.jsp?super_id="+menuClickNode.data.id,
					buttons:[
						 { text: '关闭',  type:'close', width: 80 ,  onclick:function(item, dialog){
							 relateWin.close();
						 }}
					]
				});
        	 return;
		 }
	 }
}

/**
 * 移除子节点，刷新树节点，刷新表格数据
 * @param ownerId
 */
function loadGridData(ownerId){
	//移除子节点
	var childNodes = menuClickNode.target.childNodes;
	for(var i = 1;i<childNodes.length;i++){
		fileTreeObj.remove(childNodes[i]);
	}
	fileTreeObj.loadData(menuClickNode,'rest/SatRelateFile/queryMonitorTree?ownerId=' + ownerId);
}
//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}

/*$(".l-tree").ligerTree({ 
	url: 'rest/project/getAllProj', 
	checkbox: false,
	parentIDFieldName:'owner',
	isLeafFieldName:'isleaf',
	height: "100%",
	//nodeWidth:"100%",
	topIcon:'sat',
	parentIcon: null,
    childIcon: null,
    isExpand:false,
    needCancel:false,
	onSelect:function (node, e){
		showMask();
		if(load(node.data.id)) {
			connect();
			resize.hideSelected();
		}else{
			//展开节点
			 var treeitembtn = $(node.target).find("div.l-expandable-open:first,div.l-expandable-close:first");
			 if(treeitembtn.length > 0){
				 if(treeitembtn[0].className == "l-box l-expandable-close"){
					 //打开
					 treeitembtn.removeClass("l-expandable-close").addClass("l-expandable-open");
					 $("> .l-children", node.target).show();
				 }else{
					 //关闭
					 $(".l-expandable-open", $(node.target)).click();
				 }
			 }
		}
	},
	onError:goback
});*/




//判断是否显示测站
/*var str='<div style="width:450px; float:right;height:22px;margin-right:10px;">'
		+ '<div id="cezhanid" style="width:130px;float:left;display:none;"><input name="cezhan"/></div>'
		+ '<div style="float:left;padding-top:2px;margin-left:10px;width:288px;">'
		+'<span name="sat"></span>'
		+'<span name="time" style="margin-left:10px;"></span>'
		+'</div>'
		+'</div>';*/

var str1='<div style="width:450px; float:right;height:22px;margin-right:10px;">';
var str2='<div id="cezhanid" style="float:left;"><input name="cezhan"/></div>';
var str3='<div style="float:left;padding-top:2px;margin-left:10px;width:288px;">'
	+'<span name="sat"></span>'
	+'<span name="time" style="margin-left:10px;"></span>'
	+'</div>'
	+'</div>';

var toolbar=$('#toolbar').ligerToolBar({
	items: [/*{
		tooltip:'保存',
	    click: function(){
	    	if(resize.isUpdate()){
	    		name=file.name;
	    		var formData=new FormData();
	    		formData.append('proId',file.proj_id);
	    		formData.append('name',file.name);
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
	    	            	  resize.setBackgoundAlready();
	    	            	  $.ligerDialog.success('文件保存成功！');
	    	              }  
	    	              else{
	    	            	  $.ligerDialog.error('文件保存失败！');
	    	              }
	    	          }
	    	     });
	    	}
	    },
	    icon:'save'
	},*/{
		id:'player',
		tooltip:'停止播放',
	    click: function(){
	    	var $item=$('div[toolbarid=player]','#toolbar');
	    	var $btn=$item.find('.l-icon');
	    	if($btn.hasClass('l-icon-x-play')){
	    		$item.attr('title','停止播放');
	    		$item.find("span")[0].innerText = "停止播放";
	    		$btn.removeClass('l-icon-x-play').addClass('l-icon-x-stop');
	    		isPlay=true;
	    	}
	    	else{
	    		$item.attr('title','播放');
	    		$item.find("span")[0].innerText = "播放";
	    		$btn.removeClass('l-icon-x-stop').addClass('l-icon-x-play');
	    		isPlay=false;
	    	}
	    },
	    text:"停止播放",
	    icon:'x-stop'
	}]
})


/*var $cezhan=$('[name=cezhan]','#toolbar').ligerComboBox({
	width:95,
	labelWidth:35,
	label:'测站',
	url:'rest/jar/getCezhan',
	noTriggerSelectedEvent:true
	onSelected:function(id,text){
		webSocket && id && webSocket.send(JSON.stringify({cmd:'sid',idTemp:nowIdTemp,param:id}));
	}
});*/

var $container=$('.container').data({id:1}).click(function(e){
	if($(e.target).hasClass('container'))
		resize.hideSelected(1);
	else
		resize.showSelected(1);
});//mode:0-编辑 1-运行
var id=getUrlParam('id');
//获取卫星ID
var satId=getUrlParam('satId');
var _curTabId=1;//兼容多文档编辑模式

var resize=new Resize($container,{onBind:function(){
	if(resize.isJsjg()){
		bindJsjg(resize);
	}
	else{
		if(!devData.length){
			$.ligerDialog.warn('请选择数据源！');
			return;
		}		
		bindParam(devData,resize);
	}
}},'monitor');


$.ajax({  
    url: 'rest/jar/getSats',  
    type: 'POST',  
    async: false,
    success: function (ret) {  
        sats=eval("("+ret+")");
    }
});

if(id && satId){
	showMask();
	if(load(null,satId)) {
		connect();
		resize.hideSelected();
	}
}

/**
 * 获取卫星节点ID
 * @param node
 */
function getSatNodeId(node){
	var pItem = fileTreeObj.getParentTreeItem(node);
	if(pItem == null) return null;
	var pNode = pItem.firstChild;
    var parentIndex = $(pNode).parent("li").attr("treedataindex");
    var treeData = fileTreeObj.getData();
    var pData = fileTreeObj._getDataNodeByTreeDataIndex(treeData,parentIndex);
    if(pData.node_type == "1" && pData.isroot == "1"){
    	return pData;
    }
    if(pData.isroot == "0" && pData.node_type == "1"){
    	return node.data;
    }
    if(pData.isroot == "0"){
    	return null;
    }
    return getSatNodeId($(pNode).parent("li"));
}

//加载图元文件及数据接收JAR包，加载成功返回true，否则false
function load(proId,satId){	
	var url=null;
	var data={};
	if(proId){
		url='rest/file/getLastFile';
		data.proId=proId;
	}
	else{
		url='rest/file/getFileById';
		data.id=id;
	}
	$.ajax({  
	     url: url,  
	     type: 'POST',  
	     async: false,
	     data: data,  
	     success: function (ret) {  
	    	 file = getObject(ret);	    	 
	     }
	});
	if(!file)return false;
	resize=new Resize($container,{/*onBind:function(){
		if(resize.isJsjg()){
			bindJsjg(resize);
		}
		else{
			if(!devData.length){
				$.ligerDialog.warn('请选择数据源！');
				return;
			}		
			bindParam(devData,resize);
		}
	},*/width:file.width,height:file.height});
	resize.setSatId(satId);
	resize.clear();
	resize.load(file);
	removeCeZhan();
	if(file.isShowDevice=="Y"){
		var str=str1+str2+str3;
		toolbar.toolBar.append(str);
		$cezhan=$('[name=cezhan]','#toolbar').ligerComboBox({
			width:95,
			labelWidth:35,
			label:'测站',
			url:'rest/jar/getCezhan',
			noTriggerSelectedEvent:true,
			onSelected:function(id,text){
				webSocket && id && webSocket.send(JSON.stringify({cmd:'sid',param:id}));
			}
		});
	}else{
		var str=str1+str3;
		toolbar.toolBar.append(str);
	}
	
	$sat=$('[name=sat]','#toolbar');
	$time=$('[name=time]','#toolbar');
	
	jars={
			baseData:[],
			jarData:[]
	};
	//设置卫星ID
	jars.baseData.push(satId);
	
	var sat=getElementByKey(sats,'id',satId);
	$sat.text('航天器：'+(sat?sat.text:''));
	
	/*$.ajax({  
	    url: 'rest/jar/getSelectedJar',  
	    type: 'POST',
	    data:{
	    	proId:file.proj_id
	    },
	    async: false,  
	    success: function (ret) {
	    	devData=eval("("+ret+")");
	    	jars={
    			baseData:[],
    			jarData:[]
	    	};
	        for(var i=0;i<devData.length;i++){
	        	var jar=devData[i];
	        	if(jar.type*1){
	        		jars.jarData.push(jar.device_id);
	        	}
	        	else{
	        		jars.baseData.push(jar.device_id);
	        	}
	        	var k=jar.type+''+jar.device_id;
	        	if(!dataManager.hasOwnProperty(k))
	        		dataManager[k]={};
	        	dataManager[k].jarId=jar.id;
	        }
	    }
	});*/
	return true;
}

function removeCeZhan(){
	var childNodes = toolbar.toolBar[0].childNodes;
	if(childNodes && childNodes.length > 1){
		childNodes[1].remove();
	}
}

//连接服务器通信
function connect(){
	if(webSocket){
		webSocket.close();
	}
	webSocket = new WebSocket(_url.replace('http','ws')+'websocket');
	webSocket.onerror = function(event) {
//		alert(event.data);
	};
	webSocket.onopen = function(event) {
		this.send(JSON.stringify({cmd:'start',param:jars}));
	};
	webSocket.onmessage = function(event) {
		var pack=getObject(event.data);
		if(pack.dataSource*1 == 0){
    		
    		$time.text('时间：'+pack.dataTime);
		}
		if(pack.dataSource*1<2){
			if($cezhan && !$cezhan.getValue()){
    			$cezhan.setValue(pack.sid);
    		}
		}
		if(!isPlay) return;		
		
		var dat=dataManager.getData(pack,resize.getSatId());
		resize.setData(dat);
	};
}
