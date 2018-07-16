var gridManager = null;
var floadTree = null;
var win = parent.currWin;
$(function () {   
	
	$("#layout").ligerLayout({ 
		leftWidth: 190,
		allowLeftCollapse:false,
		hideLeftHeader:true,
		heightDiff:0,
		space:0/*, 
		onHeightChanged: f_heightChanged*/ 
	});
	
	initTree();
	
    init_maingrid();
    initQueryForm();
    $("#searchbtn").ligerButton({type:'one',click: search_btn_click});	
  	//重置搜索条件
	$("#resetbtn").ligerButton({ type:'two',click: clearForm}); 
	
});

function initTree(){
	floadTree = $("#projectTree").ligerTree({
		url: basePath+'rest/project/getFloadTree', 
		checkbox: false,
		height: "100%",
		nodeWidth:"100%",
		topIcon:'sat',
		parentIcon: null,
	    childIcon: null,
		parentIDFieldName:'owner',
		textFieldName:'name',
		isLeafFieldName:'isleaf',
	    isExpand:false,
	    onCancelselect:function(){
	    	gridManager.set('parms', {
				proId:-1,
				ownerId:super_id,
				key:""
			});
			gridManager.set({newPage:1});
			gridManager.loadData();
	    },
		onSelect:function (node, e){
			gridManager.set('parms', {
				proId:node.data.id,
				ownerId:super_id,
				key:""
			});
			gridManager.set({newPage:1});
			gridManager.loadData();
		}
	});
}

function initQueryForm(){
    formData1 = $("#form1").ligerForm({
    		labelAlign:'right',
        	inputWidth: 120, 
            labelWidth: 70, 
            space: 5,
            validate:true,
            fields: [
				{display: '关键字', id: 'key',name:'key',type:"text",newline: false}
			]
    });
}

function init_maingrid(){
	gridManager = $("#maingrid").ligerGrid({
        columns: [
                  	{ name: 'id',hide:true,width:0.1}
					,
					{display: '模板页面名称', name: 'name',width:180 ,
						render:function(item){
							if(item.name){
								return "<p title='"+item.name+"'>"+item.name+"</p>";
							}
						},
					}
					,
					{display: '页面别名(可编辑)', name: 'page_name',width:200 ,
						render:function(item){
							if(item.page_name){
								return "<p title='"+item.page_name+"'>"+item.page_name+"</p>";
							}else{
								return "<p title='"+item.name+"'>"+item.name+"</p>";
							}
						},
						editor:{
							type:'text'
						}
					}
					,
					/*{display: '打开方式(可编辑)', name: 'open_mode',width:100 ,
						editor:{
							type:'select',
							data:[{id:"1",text:"新窗口"},{id:"2",text:"选项卡"}]
						},
						render:function(item){
							if(item.isleaf == "0"){
								return "";
							}
							if(item.open_mode == "1"){
								return "新窗口";
							}else if(item.open_mode == "2"){
								return "选项卡";
							}
						}
					},*/
					{display: '类型', name: 'type',width:100 ,
						render:function(item){
							if(item.isleaf == "0"){
								return "文件夹";
							}else if(item.isleaf == "1"){
								return "文件";
							}
						}
					}
        ],
        isChecked:function(e){
        	if(e.isChecked == "true"){
        		return true;
        	}
    		return false;
        },
        width: '100%',
        height: '100%',
        rownumbers:false,
        checkbox : true,      
        heightDiff:30,
        frozen:false,
        rowHeight:27,
	    enabledEdit: true, 
	    delayLoad:true,
	    usePager:false,
	    onCheckRow:onCheckRows,
	    onCheckAllRow:onCheckAllRows,
        url:basePath+"rest/file/getFileListByFload"
    });
	
	gridManager.set('parms', {
		proId:"-1",
		ownerId:super_id,
		key:""
	});
	gridManager.loadData();
	
}

function onCheckRows(flag,data){
	if(flag){
		addSatFileRelate(data);
	}else{
		removeSatFileRelate(data);
	}
}

function onCheckAllRows(flag){
	if(flag){
		addAllSatFileRelate();
	}else{
		removeAllSatFileRelate();
	}
}

//查找按钮的单击事件处理
function search_btn_click(){
	var fload = floadTree.getSelected();
	var queryData = formData1.getData();
	var key = queryData.key;	
	gridManager.set('parms', {
		proId:fload==null?"-1":fload.data.id,
		ownerId:super_id,
		key:key
	});
	gridManager.set({newPage:1});
	gridManager.loadData();
}

function clearForm(){
	formData1.setData({
		key:""
	});
	gridManager.set({newPage:1});
	gridManager.loadData();
}

function addSatFileRelate(rowData){
	var fileIds = new Array();
	if(rowData.page_name != rowData.name){
		rowData.isalias = 0;
	}
	fileIds.push({
		"obj_id":rowData.id,
		"page_name":rowData.page_name,
		"isalias":rowData.isalias,
		"open_mode":rowData.open_mode,
		"type":rowData.isleaf=="0"?"4":"5",
		"isleaf":rowData.isleaf=="0"?"1":"0"
	});
	
	submitForms(fileIds);
}


function removeSatFileRelate(rowData){
	$.ajax({
		url:basePath+'rest/SatRelateFile/deleteRelate',
		data:{
			ownerId:super_id,
			fileIds:rowData.id
		},
		async:false,
		success:function(rsStr){
			var rsData = eval('('+rsStr+')');
			if(rsData.success == "true"){
				Alert.tip('删除成功！');
     			win.loadGridData(super_id);
			}
		}
	});
}

function addAllSatFileRelate(){
	var fileIds = new Array();
	for (var i = 0; i < gridManager.selected.length; i++) {
		var rowData = gridManager.selected[i];
		fileIds.push({
			"obj_id":rowData.id,
			"page_name":rowData.page_name,
			"open_mode":rowData.open_mode,
			"type":rowData.isleaf=="0"?"4":"5",
			"isleaf":rowData.isleaf=="0"?"1":"0"
		});
	}
	
	submitForms(fileIds);
}

function submitForms(fileIds){
	var reqParam = JSON.stringify(fileIds);
	
	$("#fileListStr").val(reqParam);
	$("#ownerId").val(super_id);
	$("#hideForm").ajaxSubmit({
		url:basePath+'rest/SatRelateFile/addSatFileRelate',
		success:function(data){
			if(data == ""){
				parent.Alert.tip("请求出错！");
				return;
			}
			var jsobj = eval('('+data+')');
			if(jsobj.success == "true"){
				parent.Alert.tip(jsobj.message);
         		//刷新表格
         		if(super_id != ""){
         			win.loadGridData(super_id);
         		}
			}else if(jsobj.success == "false"){
				parent.Alert.tip(jsobj.message);
			}
		}
	});
}

function removeAllSatFileRelate(){
	var fileIds = new Array();
	for (var i = 0; i < gridManager.rows.length; i++) {
		var rowData = gridManager.rows[i];
		fileIds.push(rowData.id);
	}
	$("#fileListStr").val(fileIds.join(";"));
	$("#ownerId").val(super_id);
	$("#hideForm").ajaxSubmit({
		url:basePath+'rest/SatRelateFile/removeAllSatFileRelate',
		success:function(data){
			if(data == ""){
				parent.Alert.tip("请求出错！");
				return;
			}
			var jsobj = eval('('+data+')');
			if(jsobj.success == "true"){
				parent.Alert.tip(jsobj.message);
         		//刷新表格
         		if(super_id != ""){
         			win.loadGridData(super_id);
         		}
			}else{
				parent.Alert.tip(jsobj.message);
			}
		}
	});
}
