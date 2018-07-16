/**
 * 参数列表区域对象
 */
var tmArea={
	tmGrid:null,	
	showTm:function(tmGridHeight){
		var t = this;
		//航天器对应参数表格
		t.tmGrid = $("#paramGrid").ligerGrid({
			url:basePath+'rest/ShowTmAction/someTmList',
		    columns: [
		    { display: '', name: 'tm_param_id', align: 'left',width:0.01,hide:true},
		    { display: '名称', name: 'tm_param_name',align:'left',width:90,mintWidth:90,
		    	render:function(item){
		    		return '<p title="'+item.tm_param_name+'">'+item.tm_param_name+'</p>';
		    	}
		    },
		    { display: '代号', name: 'tm_param_code', align:'left',width:80,mintWidth:90 ,
		    	render:function(item){
		    		return '<p title="'+item.tm_param_code+'">'+item.tm_param_code+'</p>';
		    	}
		    }, 
		    { display: '序号', name: 'tm_param_num',align:'left',width:80,mintWidth:90  }
		    ],
		    width:"100%",
		    usePager: true,
		    rownumbers:false,
		    frozen:false,
		    height:tmGridHeight,
		    pageSize:20,
		    onDblClickRow :t.onDblClickRows
		});
	},
	initParamQuery:function(){
		$("#paramKeyInput").ligerTextBox({
			width: 100
		});
	},
	//参数搜索
	initButtons:function(){
		var t = this;
		$("#paramKeySearch").click(function(){
			var satData = satArea.getSelectedSatData();
			t.tmGrid.set('parms', {
				owner_id:satData.sys_resource_id,
				tm_param_name:liger.get("paramKeyInput").getValue()
			});
			t.tmGrid.set({newPage:1}
			);
			t.tmGrid.loadData();
		});
	},
	
	//双击参数后，线型表格区域显示相关信息
	onDblClickRows:function(data){
		if($("#showDataTab").ligerGetTabManager().getSelectedTabItemID()=='listQuery'){
			Alert.tip("请在曲线查询下进行操作");
			return;
		}else if($("#lineManagerGrid").ligerGetTabManager().recordNumber>=10){
			Alert.tip("对不起！您最多可以选择10个遥测参数");
			return;
		}else{
			var type=0;
			var queryCount=1500;
			var tmId=data.tm_param_id;
			var tmNum=data.tm_param_num;
			var begin = queryToolBar.getStartTimeStr();
			var end = queryToolBar.getEndTimeStr();
			var Begins=null;
			var tiye=null;
			var dataType=data.tm_param_type;
			var mid=satArea.getSelectedSatData().mid;
			var start=null;
			
			var lineObj={
					Mid:mid,//卫星序号
					Num:tmNum,//参数序号

					SatId:satArea.getSelectedSatData().sys_resource_id,//卫星主键
					TmId:tmId,//参数主键
					
					hasData:false,     //假设不能作为主轴
					Id:new UUID().id,  //给每条数据设置唯一标识
					Main:false,            //默认不是主轴
					Name:satArea.getSelectedSatData().name,   //设备名
					Code:data.tm_param_name+"("+data.tm_param_code+")",  //参数名
					tm_param_code:data.tm_param_code,
					DataType:data.tm_param_type,
					Efficiency:'',     //查询效率
					Color:getColor(),
					Show:true,
					Width:1,           //默认线宽是1
					Weight:1,
					Begin:begin,
					End:end,
					Max:'',
					Min:'',
					Type:type,         // 默认线型是阶梯型
					Precision:3};      //默认小数位数是3
			var beanTemp = new lineBean(lineObj);
			var flag=_relation_cmd.Add({ Type: '添加曲线', Param: beanTemp });
			
			linearManageArea.linearGrid.addRow(lineObj);  //表格中增加一行
			var startTime = new Date().getTime();		
			var lineData = queryLineData.getdrawdata(type, queryCount, tmId, begin, end, Begins, tiye, dataType, mid, start);
			var endTime = new Date().getTime();
			if(lineData!=null && lineData.items.length>0){
				beanTemp.data.hasData=true;  //可以作为主轴
				if(lineCache.mainLine == null){
					lineCache.mainLine = beanTemp.data.Id;
					beanTemp.data.Main = true;
					//修改Main的值
					linearManageArea.linearGrid.updateCell("Main",true,lineObj);
					
					//按钮可用
					queryToolBar.setEnable(true);
				}
			}
			//修改查询效率的值
			linearManageArea.linearGrid.updateCell("Efficiency",(endTime-startTime),lineObj);
			 //缓存中增加一条数据
			lineCache.putLineToCache(beanTemp);
			
		}
	}
};

var colorArr=["#FF0000","#0000FF","#008000","#800080","#A52A2A",
              "#0A5ACD","#008080","#FFB6C1","#FFA500","#FFFF00"];
var colorIndex=0;  
var freeColor=new Array();//存贮已经释放的颜色
function getColor(){
	
	if(lineCache.lineCacheArr.length==0){//如果线型管理表格为空，图例从第一个颜色开始
		colorIndex=0;
	}
	if(colorIndex<10){ 
		var color=colorArr[colorIndex];
	}
	if(colorIndex>=10){
		var color=freeColor[0];
		freeColor.splice(0,1);
	}
	colorIndex++;
	return color;
}


