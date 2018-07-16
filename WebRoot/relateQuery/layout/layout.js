//是否显示关键参数
var showKeyParam = false;
//是否显示相关信息表格
var showRelateInfoGrid = false;
//是否显示卫星相关信息统计表格
var showRelateDataGrid = false;
//是否显示条件查询
var showRelateCondition = false;

//线型管理Tab
var lineManagerTab=null;

//总布局对象
var mainLayout = null;
//曲线查询、列表查询tab
var rightUpTab = null;
$(function(){
	mainLayout = $("#mainLayout").ligerLayout({
			leftWidth:  280,
			allowLeftResize: false, 
			centerBottomHeight: 250,
			space:1,
			onEndResize:onEndResizes
	});
	
//    $("#selectAreaTab").ligerTab(); 
    
    rightUpTab = $("#showDataTab").ligerTab({
        changeHeightOnResize: true,
    	onAfterSelectTabItem:onAfterSelectTabItem
    }); 
    
    lineManagerTab = $("#lineManagerTab").ligerTab();
    //初始化树结构
    satArea.initSatQuery();
    satArea.initSatTree();
    satArea.initButtons();
    //显示卫星参数表格
    tmArea.initParamQuery();
    
    var tmGridHeight = 300;
    if(showRelateInfoGrid){
    	
    }else{
    	tmGridHeight = mainLayout.left.height() - 377 + 30;
    }
    
    tmArea.showTm(tmGridHeight);
    tmArea.initButtons();
    //显示线型管理表格
    linearManageArea.showInfo();
    //曲线查询区域
    queryToolBar.init();
    lineShow.show();
    
    //设置某些组件是否显示
    setIsShow();
    
    onWinresize();
});


window.onresize = function(e){
	onWinresize(e);
}

function onWinresize(e){
	var winHeight = window.innerHeight;
	var winWidth = window.innerWidth;
	//设置曲线图的高度和宽度
	var h = mainLayout.middleHeight - mainLayout.centerBottom.height() - 65;
	var w = winWidth - mainLayout.leftWidth;
	
	lineShow.resizes(w,h);
}

function setIsShow(){
	//隐藏关键参数
	if(!showKeyParam){
		$("#selectAreaTab").find("li[tabid=keyParam]").hide();
	}
	//隐藏相关信息表格
	if(!showRelateInfoGrid){
		$("#relateInfoDiv").hide();
		$("#paramGrid").height("420px");
		$("#paramGrid").find("div[id=paramGridgrid]").height("388px");
	}else{
		 //显示航天器相关信息表格
		$("#paramGrid").height("300px");
		$("#paramGrid").find("div[id=paramGridgrid]").height("268px");
	    jsjgArea.showJsjg();
	}
}

function onEndResizes(){
//	$("#selectAreaTab").find("div[class=l-tab-links]").width("100%");
	$("#showDataTab").find("div[class=l-tab-links]").width("100%");
	$("#lineManagerTab").find("div[class=l-tab-links]").width("100%");
	$("#listDataTab").find("div[class=l-tab-links]").width("100%");

	var h = mainLayout.middleHeight - mainLayout.centerBottom.height() - 65;
	var w = mainLayout.centerWidth;
	lineShow.resizes(w,h);
}

//切换到列表查询区域
function onAfterSelectTabItem(e){
	if(e=="listQuery"){
		if(!showRelateDataGrid){
			//隐藏相关信息统计表格
			$("#relateDataGridTr").hide();
		}else{
			$("#relateDataGridTr").empty();
			$("#relateDataGridTr").append('<div class="l-layout-header" >航天器相关信息查询统计</div>'+
					'<div id="relateDataGrid"></div>');
			listQueryArea.showStatisticalInfo();
			listQueryArea.loadDataToGrid();
		}
		
		var tmDataGridHeight = 300;
		if(showRelateDataGrid){
			tmDataGridHeight = mainLayout.center.height() - 60 - 288;
		}else{
			tmDataGridHeight = mainLayout.center.height() - 60;
		}
		//显示工程值表格组件
		$.fn.listQueryAreaInit($("div[tabid=listQuery]"),{
			tmDataGridHeight:tmDataGridHeight
		});
		var beginTimeStr = 
			_relation_lines.MainLine?new Date(_relation_lines.MainLine.Begin).format('Y-m-d H:i:s.000'):"";
		var endTimeStr = 
			_relation_lines.MainLine?new Date(_relation_lines.MainLine.End).format('Y-m-d H:i:s.000'):"";
		//工程值数据表格赋值
		$.fn.listQueryAreaLoadTmData(null,beginTimeStr,endTimeStr);
		
		/*var listGridTab = $("#listDataTab").find("div[id=listGridTab]");
		listGridTab.height("300px");
		listGridTab.empty();
		listGridTab.append('<div id="dataListGrid"></div>');
		//显示工程值数据表格
		listQueryArea.showdataList();
		if(lineCache.lineCacheArr.length != 0 &&
				lineCache.mainLine != null){
			var mainLine = lineCache.getLineFromCache(lineCache.mainLine);
			
			
			listQueryArea.beginTimeStr = beginTimeStr;
			listQueryArea.endTimeStr = endTimeStr;
			
			var time = beginTimeStr+"至"+endTimeStr;
			t_setHeader(time);
			listQueryArea.putDataToGrid(beginTimeStr,endTimeStr);
		}
		else{
			
//			t_setHeader("");
		}*/
	}
}

function t_setHeader(time){ 
	$("#listDataTab").ligerTab().setHeader("listGridTab", time);

}

var lineManagerArea={
	relateResultGrid:null,
	init:function(data){
		this.addQueryResultGrid();
		this.showGrid();
		this.loadGridData(data);
	},
	addQueryResultGrid:function (){
		lineManagerTab.removeTabItem("lineManagerTab");
		lineManagerTab.addTabItem({
			tabid:"lineManagerTab",
			content:'<div id="relateResultGrid"></div>',
			text:"查询结果时间段"
		});
	},
	showGrid:function(){
		var t = this;
		t.relateResultGrid=$("#relateResultGrid").ligerGrid({
		    columns: [
	  		    { display: '开始时间', name: 'beginTimeStr',width:300,mintWidth:200 },
	  		    { display: '结束时间', name: 'endTimeStr',width:300,mintWidth:200  }
	  		],
	  	    rownumbers:true,
	  	    height:'100%',
	  	    heightDiff:30,
	  	    checkbox:true,
	  	    usePager:false
	  	});	
	},
	loadGridData:function(data){
		var t = this;
		t.relateResultGrid.loadData({
			"Rows":data
		});
		
	}
};
