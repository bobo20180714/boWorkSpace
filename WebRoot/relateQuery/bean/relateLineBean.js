var relateLineBean = function (options){
    this.raw = this.data = {
		ID : options.ID,
		satId : options.satId,
		mid : options.mid,
		deviceName : options.deviceName,
		deviceCode : options.deviceCode,
		
		jsjgId : options.jsjgId,
		typeName : options.typeName,
		dataTypeId : options.dataTypeId,
		
		jsjg_id : options.jsjgId,
		jsjg_code : options.typeName,
		jsjg_name : options.typeName,
		Type : options.Type,
		Begin : options.startTime,
		End : options.endTime,
		startCol : options.startCol,
		endCol : options.endCol,
		viewCol : options.viewCol,
		Color : options.Color,
		RectColor : options.RectColor
    };
	this.get = function(name){
		return this.data[name];
	};
	this.set = function(name,value){
		this.data[name] = value;
	};
};
