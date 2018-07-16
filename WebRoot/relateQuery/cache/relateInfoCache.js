var relateInfoCache={
	relateInfoArr:new Array(),
	putRelateInfoToCache:function(infoObj){//增加一条相关信息到缓存中
		var t = this;
		t.relateInfoArr.push(infoObj);
	},
	removeRelateInfoFromCache:function(infoId){//从缓存中删除一条相关信息
		var t = this;
		for (var i = 0; i < t.relateInfoArr.length; i++) {
			if(t.relateInfoArr[i].infoId == infoId){
				t.relateInfoArr.splice(i,1);
				break;
			}
		}
	},
	removeRelateInfoFromCache:function(mid,relateCode){
		var t = this;
		for (var i = 0; i < t.relateInfoArr.length; i++) {
			if(t.relateInfoArr[i].mid == mid
					&& t.relateInfoArr[i].dataTypeId == relateCode){
				t.relateInfoArr.splice(i,1);
				break;
			}
		}
	},
	getRelateInfoFromCache:function(infoId){//从缓存中获取相关信息
		var t = this;
		for (var i = 0; i < t.relateInfoArr.length; i++) {
			if(t.relateInfoArr[i].infoId == infoId){
				return t.relateInfoArr[i];
			}
		}
		return null;
	},
	getRelateLineCount:function(){
		var t = this;
		return t.relateInfoArr.length;
	}
};