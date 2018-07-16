var formData = null;
var gridManager;
var id = "";
var groupicon="";

var pagebeginTime = "";//点击下一页时记录当前页的第一条数据时间
var pageEndTime = "";//点击下一页时记录当前页的最后一条数据时间

var nowPage = 1;//当前页数
var changePageFlag = "next";//点击下一页(next)或者上一页(prev)标识

/**
 * 存放当前页极其对应的开始时间和结束时间
 * {
 * 	 page:[{beginTime:"",endTime:""}]
 * }
 */
var timeCon = {};

var satData = null;

$(function (){
/*	$("#sat_id").ligerComboBox({
		valueField: 'sys_resource_id',
        textField: 'name',
        treeLeafOnly: false,
		tree:{
			url:basePath+'rest/satinfoLimit/findSatTree',
	        checkbox: false,
	        slide: false,
	        isExpand:false,
	        nodeWidth: 120,
	        textFieldName: 'name',
	    	idFieldName: 'sys_resource_id',
	    	parentIDFieldName:'owner_id'
		}
	});*/
	
	/*$("#beginTime").ligerDateEditor({
		format: "yyyy-MM-dd hh:mm:ss",
		showTime: true
	});
	$("#endTime").ligerDateEditor({
		format: "yyyy-MM-dd hh:mm:ss",
		showTime: true
	});*/
	
	initBtn();
	initGridData();
	initQueryForm();
});

function initQueryForm(){
    formData1 = $("#form1").ligerForm({
    		labelAlign:'right',
        	inputWidth: 140, 
            labelWidth: 80, 
            space: 5,
            validate:true,
            fields: [
				{display: '卫星', id: 'sat_id',name:'sat_id',type:"popup",newline: false,onButtonClick : search_sat_click},
				{display: '开始时间', id: 'beginTime',name:'beginTime',type:"date",newline: false,
					format: "yyyy-MM-dd hh:mm:ss",
					showTime: true},
				{display: '结束时间', id: 'endTime',name:'endTime',type:"date",newline: false,
						format: "yyyy-MM-dd hh:mm:ss",
						showTime: true}
			]
    });
}


//查找卫星的按钮单击事件响应处理，弹出一个对话框，可供选择卫星
function search_sat_click(){
	xdlg = $.ligerDialog
			.open({
				title : '查找卫星',
				width : 550,
				height : 480,
				url : basePath+'noParamData/dataQuery/selectSat.jsp',
				buttons :[
							{ text: '选择', type:'save', width: 80 ,onclick:function(item, dialog){
								satData = dialog.frame.getSelectData();	
								if(satData == null){
									return;
								}
								liger.get('sat_id').setText(satData.satName+"("+satData.satCode+")");
								dialog.close();
							 }},
							{ text: '取消', type:'close', width: 80, onclick:function(item, dialog){
								dialog.close();
							}}
				      	 ]
				});
}


/**
 * 按钮初始化
 */
function initBtn(){
	$("#searchbtn").ligerButton({ type:'one',click: function ()
    {
		searchData();
    }  
	});
  	//重置搜索条件
	$("#resetbtn").ligerButton({ type:'two',click: function (){
        clearForm();
    }
	}); 
}

function initGridData(){
	gridManager=$("#maingrid").ligerGrid({
	    columns: [
          	{display: '时间', name: 'time',align: 'center',width:200},
			{display: '值', name: 'data',align: 'left',width:1200}
	    ], 
	    dataAction:'server',
	    width: '100%', 
	    height: '100%', 
	    pageSize: 10,
	    rownumbers:true,
	    heightDiff:0,
	    //应用灰色表头
	    cssClass: 'l-grid-gray', 
	    frozen:false,
	    url:basePath+'rest/origindata/originDataSearchQueryPage',
	    delayLoad:true,
//	    rowHeight:27,
	    wordWrap:'break-word',
	    fixedCellHeight:false,
	    onLoadData:function(){
	    	var temppage = this.options.page;
	    	if(changePageFlag == "prev"){
	    		if(timeCon[temppage-1] != null){
	    			pageBeginTime = timeCon[temppage-1][0].pageBeginTime;
		    		pageEndTime = timeCon[temppage-1][0].pageEndTime;	
	    		}
	    	}

	    	var queryData = formData1.getData();
	    	this.set('parms', {
//			 	"sys_resource_id":satData==null?"-1":satData.satId,
			 			"sys_resource_id":satData==null?"-1":satData.satMid,
				"beginTime":queryData.beginTime,
				"endTime":queryData.endTime,
				"beginTime_prev":pageBeginTime,
				"endTime_prev":pageEndTime
	    	});
	    },
	    onLoaded:function(t){
	    	if(nowPage == 1){
	    		$(".l-bar-btnprev span", this.toolbar).addClass("l-disabled");
	    		if(t.data && t.data.Total < t.options.pageSize){
		    		$(".l-bar-btnnext span", this.toolbar).addClass("l-disabled");
	    		}
	    	}else if(nowPage != 1 && t.data && t.data.Total == 0){
	    		$(".l-bar-btnnext span", this.toolbar).addClass("l-disabled");
	    	}
	    	
	    },
	    onToPrev : function(){
	    	changePageFlag = "prev";
	    },
	    onToNext : function(){
	    	changePageFlag = "next";
	    	//如果是置灰的，下一页不可用，返回false
	    	if($(".l-bar-btnnext span", this.toolbar).hasClass("l-disabled")){
	    		return false;
	    	}
	    },
	    onAfterShowData : function(){
	    	nowPage = this.options.page;
	    	var length = this.rows.length;
	    	if(length > 0){
	    		pageBeginTime = this.rows[0].time;
	    		pageEndTime = this.rows[length-1].time;
	    	}
	    	var arr = new Array();
	    	arr.push({"pageBeginTime":pageBeginTime,"pageEndTime":pageEndTime});
	    	//放入map中
	    	timeCon[nowPage] = arr;
	    	var queryData = formData1.getData();
	    	//如果时间大于结束时间;如果条数小于pageSize,置灰下一页
	    	if(pageEndTime > queryData.endTime || length < this.options.pageSize){
	    		$(".l-bar-btnnext span", this.toolbar).addClass("l-disabled");
	    	}else{
	    		$(".l-bar-btnnext span", this.toolbar).removeClass("l-disabled");
	    	}
	    	//如果当前页时1，前一页按钮置灰
	    	if(nowPage == 1){
	    		$(".l-bar-btnprev span", this.toolbar).addClass("l-disabled");
	    	}else{
	    		$(".l-bar-btnprev span", this.toolbar).removeClass("l-disabled");
	    	}
	    }
	});
	
	g = gridManager;
	p = gridManager.options;
	 //工具条 - 切换每页记录数事件
    $('select', g.toolbar).change(function ()
    {
        if (g.isDataChanged && p.dataAction != "local" && !confirm(p.isContinueByDataChanged))
            return false;
        p.newPage = 1;
        p.pageSize = this.value;
        changePageFlag = "";
        pageBeginTime = "";
    	pageEndTime = "";
    	g.loadData(false);
    });
	
}

/**
 * 查询方法
 */
function searchData(){
	var queryData = formData1.getData();
	if(satData == null){
		Alert.tip("请选择航天器！");
		return;
	}
	if(queryData.beginTime == ""){
		Alert.tip("请选择开始时间！");
		return;	
	}
	if(queryData.endTime == ""){
		Alert.tip("请选择结束时间！");
		return;
	}
	pageBeginTime = "";
	pageEndTime = "";
	gridManager.set({newPage:1});
	gridManager.loadData(false);
	//清空数据
	timeCon = {};
}

function clearForm(){
	formData1.setData({
		sat_id:"",
		beginTime:"",
		endTime:""
	});
	liger.get('sat_id').setText("");
	satData = null;
}

function onexpand(data,target){
	tree_manager = tree;
	$.ajax({
		url:basePath+'rest/satinfoLimit/findgrantusergroupequipmenttree?sys_resource_id=' + data.sys_resource_id ,
		success:function(data){
			var nodes=eval('('+data+')');
			tree_manager.clear();
			tree_manager.setData(nodes);
		}
	});
}

function changePageFun(ctype){
	if(ctype == "prev"){
		//上一页
		alert("sahgn");
	}else if(ctype == "next"){
		//下一页
		alert("xia");
	}
}

	

