//列表查询区域
(function($){
	var listQueryArea = new function(){
		//配置信息
		var option = {
			tmDataGridHeight:300 //工程值表格高度
		};
		//工程值数据表格
		var dataListGrid = null;	
		//航天器相关信息查询统计表格
		var statisticalGrid = null;
		//点击下一页时记录当前页的第一条数据时间
		var pageBeginTime = "";
		//点击下一页时记录当前页的最后一条数据时间
		var pageEndTime = ""; 
		//当前页数
		var nowPage = 1;
		//点击下一页(next)或者上一页(prev)标识
		var changePageFlag = "next";
		//当前查询方式
		var nowQueryType = "0";
		
		/**
		 * 存放当前页及其对应的开始时间和结束时间
		 * {
		 * 	 page:[{beginTime:"",endTime:""}]
		 * }
		 */
		var timeCon = {};
		
		//工程值数据，请求参数
		var colListParam = null;
		var timesParam = null;
		
		var beginTimeStr=null;
		var endTimeStr=null;
		//初始化
		this.init = function(fatherNodeObj,options){
			option = options;
			//生成html
			render(fatherNodeObj);
			//初始化查询条件
			initQueryCondition();
			//初始化工程值表格
			initTmDataGrid();
		};
		/**
		 * 生成html
		 * @param fatherNodeObj  父节点jquery对象
		 * 
		 */
		function render(fatherNodeObj){
			if(!fatherNodeObj){
				return;
			}
            var htmlArr = [];
            htmlArr.push(' <div style="height: 30px;background-color: var(--grid-search-bg-color);"> ');
            htmlArr.push(' 		<div style="width:200px;float:left;"> ');
            htmlArr.push(' 			<form id="queryTypeForm"></form> ');
            htmlArr.push(' 		</div> ');
            htmlArr.push(' 		<div style="float: left;width: 50px;height: 30px;"> ');
            htmlArr.push(' 			<div id="searchbtn" style="margin-top:3px;"></div> ');
            htmlArr.push(' 		</div> ');
            htmlArr.push(' </div>');
            htmlArr.push(' 	<div id="listGridTab" title=""> ');
            htmlArr.push(' 		<div id="dataListGrid"></div>');
            htmlArr.push(' 	</div>');
			fatherNodeObj.append(htmlArr.join(''));
		};
		function initQueryCondition(){
			$("#queryTypeForm").ligerForm({
				labelAlign:'center',
		        inputWidth: 100, 
		        labelWidth: 90, 
		        space: 10,
		        validate:true,
		        fields: [
		             { display: "查询方式", name: "queryType",id:"queryType",
		            	 onSelected:changeQueryType,value:"0",cancelable:false,
		            	 type: "select", data:[{id:"0",text:"变化查询"},{id:"1",text:"全量查询"}]
		             }
		        ]
			});
			
			$("#searchbtn").ligerButton({
				type:'one',
				click : function() {
					dataListGrid.loadData();
					dataListGrid.set({newPage:1});
				}
			});
		};
		//切换查询方式
		function changeQueryType(e){
			if(dataListGrid){
				if(e == "0"){
					//变化查询
					nowQueryType = "0";
					dataListGrid.loadData();
				}else if(e == "1"){
					//全量查询
					nowQueryType = "1";
					dataListGrid.loadData();
				}
			}
		};
		//初始化工程值数据表格
		function initTmDataGrid(){
			$("#listGridTab").empty();
			$("#listGridTab").append('<div id="dataListGrid"></div>');
			dataListGrid = $("#dataListGrid").ligerGrid({
				columns: getTmDataGridColumn(),
			    delayLoad:true,
			    rownumbers:true,
			    usePager: true,
			    isOnlyTurnPage:true,
			    width:"100%",
			    pageSize:50,
			    height:option.tmDataGridHeight,
			    url:basePath+'rest/QueryDataAction/tmDataList',
			    delayLoad:true,
			    onExportDataList:onExportDataLists,
			    onLoadData:function(){
			    	var temppage = this.options.page;
			    	if(changePageFlag == "prev"){
			    		if(timeCon[temppage-1] != null){
			    			pageBeginTime = timeCon[temppage-1][0].pageBeginTime;
			    			pageEndTime = timeCon[temppage-1][0].pageEndTime;	
			    		}
			    	}
					var timesParam = [{"start":beginTimeStr,"end":endTimeStr}];
			    	this.set('parms', {
			    		colList:getRequestParam_colList(),
						times:JSON2.stringify(timesParam),
						"beginTime_prev":pageBeginTime,
						"endTime_prev":pageEndTime,
						queryType:nowQueryType
			    	});
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
			    	
			    	//如果时间大于结束时间;如果条数小于pageSize,置灰下一页
			    	if(pageEndTime > $("#endTime").val() || length < this.options.pageSize){
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
		};
		//获取工程值列头数据
		function getTmDataGridColumn(){
			var column = new Array();
			//获取线缓存中的信息
			if(lineCache.lineCacheArr.length == 0
					|| lineCache.mainLine == null){
				column.push(
					{ display: '暂无查询信息列表内容', align:'left',width:150,mintWidth:150 }
				);
			}else{
				column.push(
					{ display: '时间', name: 'time', width:180,mintWidth:150 }
				);
				for ( var i = 0;i<lineCache.lineCacheArr.length;i++) {
					var lineData = lineCache.lineCacheArr[i].data;
					var name = lineData.Mid+'_'+lineData.Num;
					column.push(
							{ display: lineData.Name+'-'+lineData.tm_param_code, name: name,width:150,mintWidth:150,
								render:function(item,t1,t2,t3){
									var v = item[t3.name];
									return v!=undefined?new Number(v).toFixed(lineData.Precision):'';
								}
							}
					);
				}
			}
			return column;
		};
		/**
		 * 组装工程值查询请求参数
		 * @returns
		 */
		function getRequestParam_colList(){
			if(lineCache.lineCacheArr.length == 0
					||lineCache.mainLine == null){
				return null;
			}
			var column=new Array();
			for (var i = 0; i < lineCache.lineCacheArr.length; i++) {
				var lineData = lineCache.lineCacheArr[i];
				if(lineData == null){
					continue;
				}
				column.push(lineData.data.Mid+"_-_"+lineData.data.Num+"_-_"+lineData.data.DataType+"_-_0");
			}
			return column.join(";");
		};
		function onExportDataLists(){
			var s = new Date(beginTimeStr);
			var e = new Date(endTimeStr);
			/*if((e.getTime() - s.getTime()) > 3 * 24 * 60 * 60 * 1000){
				Alert.tip('提示',"请下载3天以内的数据！");
				return;
			}*/
			var colList = getRequestParam_colList();
			var header=[{dataIndex: 'time',text: '时间',align:'center',width:200}];
			//缓存中获取
			var count = lineCache.getLineCount();
			for (var i = 0; i < count; i++) {
				var rc = lineCache.lineCacheArr[i];
				var paramType = rc.get('DataType');
				if(paramType=='0'){
					header.push({dataIndex: rc.get("Mid")+"_"+rc.get("Num"),text: rc.get("Name")+"_"+rc.get("Code"),align:'center',flex:1,
						precision:rc.get('Precision')});
				}else if(paramType=='2'){
					header.push({dataIndex: rc.get("Mid")+"_"+rc.get("Num"),text: rc.get("Name")+"_"+rc.get("Code"),align:'center',flex:1});
				}
			}

			var re = /_+/g;
			download("../rest/exportTmData/export?" + Ext.urlEncode({
				'colList' : colList,
	 			'times' : '[{"start":"'+beginTimeStr+'","end":"'+endTimeStr+'"}]',
	 			'headerList' : Ext.JSON.encode(header).replace(re,"_-_"),
	 			queryType:nowQueryType
	        }));	
		};
		//加载工程值数据
		this.loadTmData = function(queryType,beginTimeStrTemp,endTimeStrTemp){
			//为空 设置为变化查询
			queryType = queryType?queryType:"0";
			beginTimeStr = beginTimeStrTemp;
			endTimeStr = endTimeStrTemp;
			if(dataListGrid){
				changeQueryType(queryType)
			}
		}
		
	};
	$.fn.listQueryAreaInit = listQueryArea.init;//初始化
	$.fn.listQueryAreaLoadTmData = listQueryArea.loadTmData;//初始化
	
})(jQuery);
/*
var listQueryArea={
	
	//把工程值数据加载到表格中
	putDataToGrid:function(start,end){
		var t = this;
		var colList = t.getColumnDataStr();
		if(colList == null){
			t.dataListGrid.set('parms', { 
				colList:"",
				times:""
			});
			t.dataListGrid.loadData(false);
			return;
		}
		var times = [{"start":start,"end":end}];
		t.colListParam = colList;
		t.timesParam = times;
		t.dataListGrid.set('parms', { 
			colList:colList,
			times:JSON2.stringify(times)
		});
		t.dataListGrid.loadData(false);
	},
	*//**
	 * 获取工程值表格头数据
	 * @returns {Array}
	 *//*
	getColumn:function(){
		var column=new Array();
		if(lineCache.lineCacheArr.length == 0
				|| lineCache.mainLine == null){
			column.push(
				{ display: '暂无查询信息列表内容', align:'left',width:150,mintWidth:150 }
			);
		}else{
			column.push(
				{ display: '时间', name: 'time', width:180,mintWidth:150 }
			);
			for ( var i = 0;i<lineCache.lineCacheArr.length;i++) {
				var lineData = lineCache.lineCacheArr[i].data;
				column.push(
						{ display: lineData.Name+'-'+lineData.tm_param_code, name: lineData.Mid+'_'+lineData.Num,width:150,mintWidth:150,
							render:function(item){
								var v = item[lineData.Mid+'_'+lineData.Num];
								return v!=undefined?new Number(v).toFixed(lineData.Precision):'';
							}
						}
				);
			}
		}
		return column;
	},
	*//**
	 * 组装工程值查询请求参数
	 * @returns
	 *//*
	getColumnDataStr:function(){
		if(lineCache.lineCacheArr.length == 0
				||lineCache.mainLine == null){
			return null;
		}
		var column=new Array();
		for (var i = 0; i < lineCache.lineCacheArr.length; i++) {
			var lineData = lineCache.lineCacheArr[i];
			if(lineData == null){
				continue;
			}
			column.push(lineData.data.Mid+"_-_"+lineData.data.Num+"_-_"+lineData.data.DataType+"_-_0");
		}
		return column.join(";");
	},
	
	//航天器相关信息统计
	showStatisticalInfo:function(){
		var t = this;
		t.statisticalGrid = $("#relateDataGrid").ligerGrid({
			columns: [
			    { display: '类型', name: 'type',width:500,mintWidth:500,
			    	render:function(item){
			    		return item.deviceName+"-"+item.dataTypeName;
					}
			    }, 
			    { display: '查询结果', name:'recordCount',width:500,mintWidth:500
			    	
			    } 
			],
		    usePager: false,
		    width:"100%",
		    height:"100%",
		    heightDiff:0,
		    delayLoad:true,
		    url:basePath+'rest/relationSearch/findCommonListSearch'
		});
	},
	//把卫星相关事件信息加载到表格中
	loadDataToGrid:function(){
		var t = this;
		//获取请求参数
		var commonList = t.getInfoDataStr();
		if(commonList == null){
			t.statisticalGrid.loadData({
				"Rows":new Array()
			});
			return;
		}
		var times = [{"start":"2017-07-14 13:49:00.000","end":"2017-07-17 13:49:00.000"}];
		t.statisticalGrid.set('parms', { 
			commonList:commonList,  
			times:JSON2.stringify(times)
		});
		t.statisticalGrid.loadData(false);
		
	},
	//组装查询相关信息请求参数
	getInfoDataStr:function(){
		if(relateInfoCache.relateInfoArr.length == 0){
			return null;
		}
		var column=new Array();
		for (var i = 0; i < relateInfoCache.relateInfoArr.length; i++) {
			var infoData = relateInfoCache.relateInfoArr[i];
			if(infoData == null){
				continue;
			}
			column.push(infoData.deviceCode+"&&&"+infoData.dataTypeId+"&&&"+infoData.jsjgId+"&&&"+infoData.deviceName+"&&&"+infoData.typeName);
		}
		return column.join(",");
	},
	onExportDataLists:function(){
		var t = listQueryArea;
		var s = new Date(t.beginTimeStr);
		var e = new Date(t.endTimeStr);
		if((e.getTime() - s.getTime()) > 3 * 24 * 60 * 60 * 1000){
			Ext.Msg.alert('提示',"请下载3天以内的数据！");
			return;
		}
//		this.up().up().getEl().mask("正在数据交互中……");
		var colList = t.getColumnDataStr();
		var header=[{dataIndex: 'time',text: '时间',align:'center',width:200}];
		//缓存中获取
		var count = lineCache.getLineCount();
		for (var i = 0; i < count; i++) {
			var rc = lineCache.lineCacheArr[i];
			var paramType = rc.get('DataType');
			if(paramType=='0'){
				header.push({dataIndex: rc.get("Mid")+"_"+rc.get("Num"),text: rc.get("Name")+"_"+rc.get("Code"),align:'center',flex:1,
					precision:rc.get('Precision')});
			}else if(paramType=='2'){
				header.push({dataIndex: rc.get("Mid")+"_"+rc.get("Num"),text: rc.get("Name")+"_"+rc.get("Code"),align:'center',flex:1});
			}
		}

	    
		
		var re = /_+/g;
		download("../rest/tmdataNew/export?" + Ext.urlEncode({
			'colList' : colList,
 			'times' : '[{"start":"'+t.beginTimeStr+'","end":"'+t.endTimeStr+'"}]',
 			'headerList' : Ext.JSON.encode(header).replace(re,"_-_")
        }));	
//		this.up().up().getEl().unmask();
	}

};*/