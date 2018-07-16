var _relation_owner = null;
var _relation_drawarea = null;
var _relation_lines = null;
var _relation_cmd = null;
var lineShow = {
	show:function(){
		_relation_owner = Ext.getDom('RelationChart');
		_relation_drawarea = Ext.create('js.relationChart.RDrawAreaManage',_relation_owner);
		_relation_lines = Ext.create('js.relationChart.RLineManage',_relation_drawarea);
		_relation_cmd = Ext.create('js.relationChart.RCommandManage');
	},
	resizes:function(w,h){
		$("#RelationChart-PEl-body").width(w);
		$("#RelationChart-PEl-body").height(h);

		$("#RelationChart").width(w);
		$("#RelationChart").height(h);
		if(_relation_cmd){
			_relation_cmd.Add({ Type: '调整窗口尺寸', Width: w, Height: h});
		}
	},
	drawSatRelate:function(rec){
		
		$("#RelationChart").height($("#RelationChart").height()+30);
		
		var _satInfo = rec.get["deviceCode"]+"_"+rec.get('jsjg_code')+"_"+rec.get('jsjg_id')+"_"+rec.get["deviceName"]+"_"+rec.get('jsjg_name');
//		if(!Ext.Array.contains(commonList,_satInfo)){
//			commonList.push(_satInfo);
//		}
		_relation_cmd.Add({ Type: '添加航天器相关信息', Param: rec });
//		recList.push(rec);// 航天器相关信息添加
	},
	setTimeRange:function(obj,t1,t2,id){
		if (obj&&!obj.validate()) {
	        obj.setValue(obj.oldVal);
	        return;
	    }
	    t1 = Date.parse(t1);
	    t2 = Date.parse(t2);
		if (t1 >= t2) {
	        Ext.Msg.show({
	            title: '提示信息',
	            msg: '开始时间必须小于结束时间!',
	            buttons: Ext.Msg.OK,
	            icon: Ext.Msg.WARNING
	        });
	        if(obj){
	        	obj.setValue(obj.oldVal);
	        }
	        return;
	    }
		//判断内存中是否有曲线
	    if(lineCache.getLineCount() > 0 &&(obj==null||obj.value!=obj.oldVal)){
	    	_relation_cmd.Add({ Type: '压缩曲线', Begin:t1.getTime(), End:t2.getTime(), Id: id});
		}       
	},
	setValRange:function(obj,v1,v2,id,type,oldValue){
		/*if (!obj.validate()) {
	        obj.setValue(obj.oldVal);
	        return;
	    }*/
	    if (v1 <= v2) {
	        Ext.Msg.show({
	            title: '提示信息',
	            msg: '最大值必须大于最小值!',
	            buttons: Ext.Msg.OK,
	            icon: Ext.Msg.WARNING
	        });            
//	        obj.setValue(obj.oldVal);
	        return;
	    }
    	if((type == "Max" && v1 != oldValue) || 
    			(type == "Min" && v2 != oldValue)){
    		_relation_cmd.Add({ Type: '设置曲线上下值', Id: id, Max: v1, Min: v2 });
    	}
	}
};
