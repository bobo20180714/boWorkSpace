/*var colorList=new Array();//声明一个数组，存放不同颜色
for(var i=0;i<50;i++){
	var flag=true;//假设颜色没有重复
	var color='#'+Math.floor(Math.random()*16777215).toString(16);
	if(i==0){
		colorList[0]=color;
	}else{
		for(var j=0;j<i;j++){
			if(color==colorList[j]){
				flag=false;//颜色重复
				break;
			}
		}
		if(flag){
			colorList[i]=color;
		}
	}
}
*/
var colorList=["#E26368","#0EB1AE","#995AAD","#800000","#FF6600","#808000","#008000","#008080",
               "#0000FF","#666699","#808080","#FF0000","#FF9900","#99CC00","#339966","#33CCCC",
               "#3366FF","#800080","#969696","#FF00FF","#FFCC00","#FFFF00","#00FF00","#00FFFF",
               "#00CCFF","#00CCFF","#C0C0C0","#993300","#333300","#003300","#003366","#99CCFF",
               "#000080","#333399","#333399","#FF99CC","#FFCC99","#FFFF99","#CCFFCC","#CCFFFF",
               "#CC99FF"];
/**
 * 参数列表区域对象
 */

var jsjgArea={
	relateGrid:null,
	showJsjg:function(){
		var t = this;
		t.relateGrid = $("#relateGrid").ligerGrid({
			url:basePath+'rest/ShowJSJGAction/jsjgList',
		    columns: [
			    { display: '类型', name: 'jsjg_name', align:'left' ,width:150,
			    	render:function(item){
			    		return '<p title="'+item.jsjg_name+'">'+item.jsjg_name+'</p>';
			    	}
			    }, 
			    { display: '标记颜色', name: 'color',align:'left',width:112,
			    	render:function(item){
			    		var i=item.__index;
						if(item.jsjg_name != null){
							var bgcolor=colorList[i];
							item.color = bgcolor;
		        			return '<div style="height:15px;background-color:'+bgcolor+';margin-top:5px;margin-left:10px;margin-right:10px;"></div>'
						}
					}
			    }
		    ],  
		    usePager:false,
		    width:"100%",
		    delayLoad:true,//设置延迟加载
		    onDblClickRow :t.DblClickRows,
		    height:"100%",
		    heightDiff:30
		});
	},
	DblClickRows:function(data){
		if($("#showDataTab").ligerGetTabManager().getSelectedTabItemID()=='listQuery'){
			Alert.tip("请在曲线查询下进行操作！");
			return;
		}else if(lineCache.mainLine == null){
			Alert.tip("请先选择遥测参数信息进行操作！");
			return;
		}else{
			var isTimeRange = 3;
			if(data.is_time_range==0){
				isTimeRange=3;
			}else if(data.is_time_range==1){
				isTimeRange=9;
			}
			
			var startTime = queryToolBar.getStartTimeStr();
			var endTime = queryToolBar.getEndTimeStr();
			
			var infoObj = {
				ID : new UUID().id,
				satId : data.sat_id,
				mid : data.mid,
				deviceName : data.sat_name,
				deviceCode : data.sat_code,
				jsjgId : data.jsjg_id,
				typeName : data.jsjg_name,
				dataTypeId : data.jsjg_code,
				Type : isTimeRange,
				startTime : startTime,
				endTime : endTime,
				startCol : data.start_time,
				endCol : data.end_time,
				viewCol : data.view_col,
				Color : data.color,
				RectColor : data.color
			}
			var relateLine = new relateLineBean(infoObj);
			relateInfoCache.putRelateInfoToCache(infoObj);//将信息放入缓存中
			if(rightUpTab.getSelectedTabItemID()=='listQuery'){
				Alert.tip("请在曲线查询下进行操作");
				return;
			}
			if(lineCache.getCanBeMainLine() == 0 || lineCache.mainLine == null){
				Alert.tip("请先绘制参数曲线！");
				return;
			}
			if(relateInfoCache.getRelateLineCount() >= 5){
				Alert.tip("对不起!您最多可选5个航天器相关信息!");
	    	    return;
			}
			lineShow.drawSatRelate(relateLine);
		}
	}
};
