var dataArr=[       //0：阶梯型   2：点型    
             {id:0,text:"阶梯型"},
             {id:2,text:"点型"}
            ];
var PrecisionArr=[  //小数位数，取值为0-9，
				  {id:0,text:0},
				  {id:1,text:1},
				  {id:2,text:2},
				  {id:3,text:3},
				  {id:4,text:4},
				  {id:5,text:5},
				  {id:6,text:6},
				  {id:7,text:7},
				  {id:8,text:8},
				  {id:9,text:9}
            ];
var lineHeightArr=[ //线宽
				{id:1,text:1},
				{id:2,text:2},
				{id:3,text:3},
				{id:4,text:4},
				{id:5,text:5},
				{id:6,text:6}
            ];
/**
 * 线型管理区域
 */
var linearManageArea={
	linearGrid:null,       //线型管理表格
	showInfo:function(){
		var t = this;
		t.linearGrid = $("#lineManagerGrid").ligerGrid({
		    columns: [   
			    { display: '主轴', name: 'Main', align:'center',width:50,mintWidth:50,
			    	render:function(item){
			    		if(item.Main != null){
	        				if(item.Main == true){
	        					return "<input style='margin-top:6px;' type='radio' name='main' checked='checked' onclick='isMain("+JSON2.stringify(item)+")'/>";
			        		}
		        			return "<input style='margin-top:6px;' type='radio' name='main' onclick='isMain("+JSON2.stringify(item)+")'/>";
						}
					}
			    }, 
			    { display: '设备名称', name: 'Name',align:'left',width:150,mintWidth:70 },
			    { display: '参数名称', name: 'Code',align:'left',width:250,mintWidth:100 },
			    { display: '查询效率(ms)', name: 'Efficiency',width:100,mintWidth:100},
			    { display: '图例', name: 'Color',align:'center',width:70,mintWidth:70,
			    	render:function(item){
						if(item.Color != null){
							var color=item.Color;	
		        			return '<div  id="Color_'+item.__index+'" onclick="colorWindow(this,\''+item.Id+'\')" style="width:60px;height:15px;background-color:'+color+';margin-top: 5px;" ></div>'
						}
					}
			    },
			    { display: '显示', name: 'Show',align:'center',width:50,mintWidth:50,
			    	render:function(item){
						if(item.Show != null){
							if(item.Show == true){
								return "<input style='margin-top:6px;'  type='checkbox' onclick='isShow("+JSON2.stringify(item)+")' checked='checked'/>";
			        		}
							return "<input style='margin-top:6px;'  type='checkbox' onclick='isShow("+JSON2.stringify(item)+")'/>";
						}
					}
			    },
			    { display: '线宽', name: 'Width',align:'left',width:70,mintWidth:70,
			    	render:function(item){
						if(item.Width != null){
		        			  for(var i = 0;i<lineHeightArr.length;i++){
		        				  if(item.Width == lineHeightArr[i].id){
				        			  return '<div id="Width_'+item.__index+'"  style="width:100%;height:100%;" onclick="alertWindow(this,\''+item.Id+'\')"><div style="width:70px;height:'+lineHeightArr[i].text+'px;background-color: gray;margin-top: 10px;"></div></div>';
				        		   } 
		        			  }
						}
					}
			    },
			    { display: '开始时间', name: 'Begin',align:'center',width:150,mintWidth:120,editor:{
					type:'text'
					} 
			    },
			    { display: '结束时间', name: 'End',align:'center',width:150,mintWidth:120,editor:{
					type:'text'
					}  
			    },
			    { display: '坐标轴上限', name: 'Max',align:'center',width:100,mintWidth:70,editor:{
					type:'text'
					}  
				},
			    { display: '坐标轴下限', name: 'Min',align:'center',width:100,mintWidth:70,editor:{
					type:'text'
					} 
				},
			    { display: '线型', name: 'Type',align:'center',width:100,mintWidth:50,editor:{
					type:'select',
					data:dataArr
					} ,
					render:function(item){//渲染表格(如果不设置，表格中显示的是id值，设置让它显示text值)
						if(item.Type != null){
		        			  for(var i = 0;i<dataArr.length;i++){
		        				  if(item.Type == dataArr[i].id){
				        			   return dataArr[i].text;
				        		   }
		        			  }
						}
					}
			    },
			    { display: '小数位数', name: 'Precision',align:'center',width:70,mintWidth:70,editor:{
					type:'select',
					data:PrecisionArr
					} 
			    },
			    { display: '', name: 'operation',align:'center',width:50,mintWidth:50,
			    	render:function(item){
						var url=basePath + 'relateQuery/img/delete.gif';
						//点击删除按钮，删除所在行数据(根据对象去删)
						var button="<input style='margin-top:5px;'  type='image' src='"+url+"' " +
								"onclick='deleteLine("+JSON2.stringify(item)+")'/>"
						return button;
					}
			    }
		    ],  
		    usePager:false,
		    rownumbers:true,     
		    checkbox:false,
		    heightDiff:30,
		    width:"100%",
		    enabledEdit: true,
		    onAfterEdit:l_onAfterEdit,
		    delayLoad:true,
		    frozen:false,
		    height:"100%"
		});
	},
	//获取行数据
	getRowDataByLineId:function(lineId){
		var t = this;
		for(var i = 0;i<t.linearGrid.rows.length;i++){
			if(t.linearGrid.rows[i].Id == lineId){
				return t.linearGrid.rows[i];
			}
		}
		return null;
	},
	//重新加载内存数据
	reloadGridData:function(){
		var t = this;
		var gridDataArr = new Array();
		//获取缓存中曲线数据
		var lineArr = lineCache.lineCacheArr;
		for(var i = 0;i<lineArr.length;i++){
			gridDataArr.push(lineArr[i].data);
		}
		t.linearGrid.loadData({
			"Rows":gridDataArr
		});
	}
};

//删除数据
function deleteLine(obj){   
	//从缓存中获取line数据
	if(lineCache.lineCacheArr.length==1 && relateInfoCache.relateInfoArr.length != 0){
		Alert.tip("对不起,您还有未移除的航天器相关信息不能进行移除操作!");
		return;
	}
	var lineCacheData = lineCache.getLineFromCache(obj.Id);
	var color=lineCacheData.data.Color;
	freeColor.push(color);
	if(lineCacheData && lineCacheData.data.Main == true){//删除的是主轴
		lineCache.mainLine = null;
		linearManageArea.linearGrid.deleteRow(obj.__id);  //从表格中删除
		lineCache.removeLineFromCache(obj.Id); //从缓存中删除
		var lineObjTemp = lineCache.getCanBeMainLine();
		if(lineObjTemp){
			linearManageArea.linearGrid.updateCell("Main",true,
					linearManageArea.getRowDataByLineId(lineObjTemp.data.Id));
		}
	}else{
		linearManageArea.linearGrid.deleteRow(obj.__id);  //从表格中删除
		lineCache.removeLineFromCache(obj.Id);		  //从缓存中删除
	}
	
	removeExtLine(null,obj);
}

function removeExtLine(grid, obj){ 
	
	_relation_cmd.Add({Type: '移除曲线', Id: obj.Id});
	
    if (lineCache.getLineCount() == 0){
    	
    	queryToolBar.setEnable(false);
    	
    	Ext.getCmp('rtiye1').setValue('');
		Ext.getCmp('rtiye2').setValue('');
 	    Ext.getCmp('tiaojian').setEnable(false);
    }
    if(rightUpTab.getSelectedTabItemID() != "listQuery"){
    	 return;
    }
    /*
	var lineCmpStore=lineCmp.getStore();
	var count=lineCmpStore.getCount();
	var gridOne=Ext.getCmp("ListSearchGridOne");
	gridOne.headerCt.removeAll();
	if(count==0){
		gridOne.headerCt.add({id:'blank_info',text: '暂无查询信息列表内容',sortable: true, dataIndex: '',align:'left',width:200});
		gridOne.view.refresh();
		gridOne.getStore().proxy.extraParams={};
 		gridOne.getStore().removeAll();
 		return;
	}
	var colList=[];
	var header=[{dataIndex: 'time',text: '时间',align:'center',width:200}];
	var fields=[{name:'time'}];
	for (var i = 0; i < count; i++) {
		var rc=lineCmpStore.getAt(i);
		header.push({dataIndex: rc.get("SatId")+"_"+rc.get("TmId"),text: rc.get("SatCode")+"_"+rc.get("TmCode"),align:'center',flex:1});
		fields.push({name:rc.get("SatId")+"_"+rc.get("TmId")});
		colList.push(rc.get("SatId")+"_"+rc.get("TmId"));
	}
	gridOne.headerCt.add(header);
	gridOne.getStore().model.setFields(fields);
	gridOne.view.refresh();
	var timeGrid=Ext.getCmp("TimeGrid");
	if(!timeGrid) return;
		var records=timeGrid.getSelectionModel().getSelection();
		if(records.length==0) return;
		var data = [];
	Ext.Array.each(records, function(model) {
		data.push({'start':model.get("beginTimeStr"),'end':model.get("endTimeStr")});
	});
	data.sort(Arrarycompare("start"));
	
	var json=Ext.JSON.encode(data);
	gridOne.getStore().proxy.extraParams={
			'colList' : colList.join(","),
			'times' : json
		};
	gridOne.getStore().load();*/
}


//点击复选框时触发的事件
function isShow(t){
	var obj=lineCache.getLineFromCache(t.Id);
	if(obj.data.Show == true){
		if(obj != null ){
			obj.data.Show=false;
			linearManageArea.linearGrid.updateCell("Show",false,t);
			_relation_cmd.Add({ Type: '显示曲线', Id: t.Id, Show: false});
		}
	}else
	if(obj.data.Show == false){
		if(obj != null ){
			obj.data.Show=true;
			linearManageArea.linearGrid.updateCell("Show",true,t);
			_relation_cmd.Add({ Type: '显示曲线', Id: t.Id, Show: true});
		}
	}
}

//点击单选按钮时触发的事件
function isMain(t){
	var obj=lineCache.getLineFromCache(t.Id);
	if(obj != null ){
		if(obj.data.Main == true){
			linearManageArea.linearGrid.updateCell("Main",true,t);
		}else if(obj.data.Main == false && obj.data.hasData){
			lineCache.mainLine = null;
			obj.data.Main = true;
			lineCache.mainLine = t.Id;
			linearManageArea.linearGrid.updateCell("Main",true,t);
			
			_relation_cmd.Add({ Type: '设置主轴', Id: t.Id });
		}else{
			var id=lineCache.mainLine;
			var mainObj=lineCache.getLineFromCache(id);
			var rowData = linearManageArea.getRowDataByLineId(mainObj.data.Id);
			linearManageArea.linearGrid.updateCell("Main",true,rowData);
		    linearManageArea.linearGrid.updateCell("Main",false,t);
		}
	}
}


//编辑后事件
function l_onAfterEdit(e){
	var obj=lineCache.getLineFromCache(e.record.Id);//从缓存中获取一条线对象（编辑之后，单元格中的数据改变，缓存中没有改变）
	var rowData = linearManageArea.getRowDataByLineId(e.record.Id);//从表格中获取一条线对象
	if(e.column.name == "Begin" || e.column.name == "End"){//改变时间
		if(e.record.Begin > e.record.End){
			linearManageArea.linearGrid.updateCell("Begin",obj.data.Begin,rowData);
			linearManageArea.linearGrid.updateCell("End",obj.data.End,rowData);
			Alert.tip("开始时间不能大于结束时间");
			return;
		}else{
			obj.data.Begin=e.record.Begin;
			obj.data.End=e.record.End;
			lineShow.setTimeRange(null,e.record.Begin,e.record.End,e.record.Id);
		}
	}else if(e.column.name == "Max" || e.column.name == "Min"){//该变坐标轴上下限
		if(e.record.Max*1 < e.record.Min*1){
			linearManageArea.linearGrid.updateCell("Max",obj.data.Max,rowData);
			linearManageArea.linearGrid.updateCell("Min",obj.data.Min,rowData);
			Alert.tip("最大值不能小于最小值");
			return;
		}else{
			obj.data.Max=e.record.Max;
			obj.data.Min=e.record.Min;
			lineShow.setValRange(this,e.record.Max*1,e.record.Min*1,e.record.Id,e.column.name,obj.data.Max);   
		}
		
	}else if(e.column.name == "Type"){
		obj.data.Type = e.record.Type * 1;
        _relation_cmd.Add({Type:'选择曲线显示方式',Id:e.record.Id});
	}else if(e.column.name == "Precision"){
		obj.data.Precision=e.record.Precision;  
	}
}

//弹出选择线宽的窗口
function alertWindow(t,id){
	var os = $("#"+t.id).offset();//t.id表示当前对象的id,id表示线对象的id
	$("#lineWidthWin").css({top:os.top - 100+'px',left:os.left+'px'});
	$("#lineWidthWin").show();
	$("#selectLineId").val(id);
	
	//单击页面选择器以外的地方时选择器隐藏
	$(document).bind('mousedown',function(event){
		if(!($(event.target).parents().andSelf().is('#lineWidthWin'))){
			$("#lineWidthWin").hide();
		}
	});
}
//选择线宽
function selectLineWidth(t){
	var ID=$("#selectLineId").val();
	var obj=lineCache.getLineFromCache(ID);
	obj.data.Width=t.attributes.lineHeight.value;
	var rowData = linearManageArea.getRowDataByLineId(obj.data.Id);
	linearManageArea.linearGrid.updateCell("Width",obj.data.Width,rowData);
	$("#lineWidthWin").hide();

	if(obj.data.Width)_relation_cmd.Add({ Type: '设置曲线宽度', Id: ID, Width: obj.data.Width });
//	if(Max&&Min)_relation_cmd.Add({ Type: '设置曲线上下值', Id: ID, Max: Max, Min: Min });

}

//选择颜色
function colorWindow(t,id){
	$("#"+t.id).bigColorpicker(function(now,hex){
		var ID=$("#selectLineId").val();
		var obj=lineCache.getLineFromCache(ID);
		var color=obj.data.Color;
		obj.data.Color=hex;
		$("#"+t.id).css("background-color",hex);
		for(var i = 0;i < colorArr.length;i++){
			if((color != hex)  && (color == colorArr[i])){
				freeColor.push(color);
			}
		}
		
		if(color){
			_relation_cmd.Add({ Type: '设置曲线颜色', Id: ID, Color: color});
		}
		
//		$.fn.bigColorpickerHide();//选择颜色之后，隐藏
	});
	$("#selectLineId").val(id);
	$.fn.setCurrentPicker(t);//设置当前对象为CurrentPicker
	var os = $("#"+t.id).offset();
	$.fn.pickerShow(os.top - 200,os.left - 50);
}
