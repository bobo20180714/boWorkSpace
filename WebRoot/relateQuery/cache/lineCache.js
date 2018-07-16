var lineCache={
	lineCacheArr:new Array(),
	mainLine:null,//主轴ID
	putLineToCache:function(lineObj){//增加一条线的数据到缓存中
		var t = this;
		if(lineObj.Main == 0){
			t.mainLine = lineObj.data.Id;
		}
		t.lineCacheArr.push(lineObj);
	},
	removeLineFromCache:function(lineId){//从缓存中删除一条数据
		
		var t = this;
		for (var i = 0; i < t.lineCacheArr.length; i++) {
			if(t.lineCacheArr[i].data.Id == lineId){
				t.lineCacheArr.splice(i,1);
				break;
			}
		}
	},
	getLineFromCache:function(lineId){//从缓存中获取线数据
		var t = this;
		for (var i = 0; i < t.lineCacheArr.length; i++) {
			if(t.lineCacheArr[i].data.Id == lineId){
				return t.lineCacheArr[i];
			}
		}
		return null;
	},
	getCanBeMainLine:function(){
		var t = this;
		for(var i=0;i<t.lineCacheArr.length;i++){
			var object=t.lineCacheArr[i];
			if(object != null && object.data.hasData){
				object.data.Main = true;
				t.mainLine = object.data.Id;
				return object;
			}
		}
		return null;
	},
	getLineCount:function(){
		var t = this;
		return t.lineCacheArr.length;
	}
};