/**
 * 动态图元基类，自定义图元必须实现该类
 * xjt 2017.1.20
 */
function IDynamicBase(){	
	/**
	 * this.jsjg=1 该属性为1表示计算结果图元，缺失表示遥测图元
	 * this.ver 该属性缺失表示初始版本1.0，新版本从2.0开始
	 * this.menu 自定义右键菜单及属性对话框(参考ligerUI)
	 */
	/**
	 * 动态图元唯一标识
	 */
	this.gid=null;	
	/**
	 * 通过该方法创建动态图元物理节点
	 * owner:图元容器，说明：owner.data()提供{x:x坐标,y:y坐标,w:宽度,h:高度,isResize:1-容器大小发生变化 0-未变化}
	 * data:{
	 * 		gid:图元标识,
	 * 		pid:插件ID,
	 * 		x:x坐标,
	 * 		y:y坐标,
	 * 		w:宽度,
	 * 		h:高度,
	 * 		title:标题,
	 * 		属性名:属性值,
	 * 		···,
	 * 		params:[{devId:设备ID,parId:参数ID,device:设备名,name:参数名,属性名:属性值,···},···]
	 * }
	 * 说明：在图元首次创建时，data:{
	 * 		gid:图元标识,
	 * 		pid:插件ID, 		
	 * 		title:插件名+序号,
	 * 		params:[]
	 * }
	 * return:图元jquery|dom对象
	 */
	this.create=function(owner,data){		
	};
	/**
	 * 获取图元数据
	 * return:{
	 * 		gid:图元标识,
	 * 		pid:插件ID,
	 * 		x:x坐标,
	 * 		y:y坐标,
	 * 		w:宽度,
	 * 		h:高度,
	 * 		title:标题,
	 * 		属性名:属性值,
	 * 		···,
	 * 		params:[{devId:设备ID,parId:参数ID,device:设备名,name:参数名,属性名:属性值,···},···]
	 * }
	 */
	this.getData=function(){		
	};
	/**
	 * 显示数据
	 * data：[{devId:设备ID,parId:参数ID,val:显示数据},···]
	 */
	this.setData=function(data){		
	};
	/**
	 * 绑定图元参数
	 * params:[{devId:设备ID,parId:参数ID,device:设备名,name:参数名},···]
	 */
	this.bindParam=function(params){		
	};
	/**
	 * 获取绑定参数
	 * return:同上
	 */
	this.getBindParam=function(){		
	};
	/**
	 * 获取图元属性
	 * return:{
	 * 		props:[{name:属性名,code:属性代号,val:属性值,range:取值(可选)},···],
	 * 		parProps(当参数具有属性时选择此项):{
	 * 			设备ID+参数ID:{
	 *				属性代号:属性值
	 * 			},
	 * 			···
	 * 		}
	 * }
	 */
	this.getProperties=function(){		
	};
	/**
	 * 设置图元属性
	 * data:{
	 * 		props:[{属性代号:属性值},···],
	 * 		parProps(当参数具有属性时选择此项):[{
	 * 			props:[{name:属性名,code:属性代号,val:初始值,range:取值(可选),xtype:属性框类型},···],
	 * 			name:参数名,
	 * 			code:参数代号,
	 * 			params:[
	 * 				{
	 * 					name:设备名+参数名,
	 * 					code:设备ID+参数ID,
	 *					propVals:[{属性代号:属性值},···]
	 * 				},
	 * 				···
	 * 			]
	 * 		},···]
	 * }
	 */
	this.setProperties=function(data){		
	};
}
//存储接口方法
IDynamicBase.methods={};
//设置接口方法
var DynamicBase=new IDynamicBase();
for(var method in DynamicBase){
	IDynamicBase.methods[method]=DynamicBase[method];
}
//检查对象是否实现接口
IDynamicBase.ensureImplements = function(instance){
	if(typeof instance!=='object'){
		DynamicBaseError('该对象未实例化(new)！');
	}
	var methods=IDynamicBase.methods;
	for(var method in methods){
		if(typeof instance[method]==='function'){//方法
			if(!instance[method] || typeof instance[method] !== 'function'){
				DynamicBaseError("方法名："+method+"没找到！");
			}
		}
		else{//属性
			if(instance[method]===undefined){
				DynamicBaseError("属性名："+method+"没找到！");
			}
		}		
	}
};
//当对象未正确实现接口时抛出该异常
function DynamicBaseError(msg){
	alert(msg);
	throw new Error(msg);
}