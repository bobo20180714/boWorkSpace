var lineBean = function (options){
    this.raw = this.data = {
		Mid:options.Mid,//卫星序号
		Num:options.Num,
		SatId:options.SatId,//卫星主键
		TmId:options.TmId,//参数主键
		hasData:false,     //假设不能作为主轴
		Id:options.Id,//给每条数据设置唯一标识
		Main:options.Main,            //默认不是主轴
		Name:options.Name,   //设备名
		Code:options.Code,  //参数名
		tm_param_code:options.tm_param_code,
		DataType:options.DataType,
		Efficiency:'',     //查询效率
		Color:options.Color,
		Show:true,
		Width:1,           //默认线宽是1
		Weight:1,
		Begin:options.Begin,
		End:options.End,
		Max:'',
		Min:'',
		Type:0,         // 默认线型是阶梯型
		Precision:3,   //默认小数位数是3
		beginTimeStr:"",
		endTimeStr:""
    };
	this.get = function(name){
		return this.data[name];
	};
	this.set = function(name,value){
		this.data[name] = value;
	};
	this.commit = function(){
		//刷新线性表格数据
		linearManageArea.reloadGridData();
	}
};
