//表单对象
var orderForm = null;
//表单对象
var getDataForm = null;
//表单对象
var computForm = null;
//表单对象
var resultForm = null;
//是否
var selectData = [{id:'0',text:'否'},{id:'1',text:'是'}];

//函数参数类型
var fctParamTypeArr = [{id:'0',text:'遥测参数'},{id:'1',text:'常量类型'},{id:'2',text:'时间区间'}];

$(function(){
	//初始化订单基本信息form
	initOrderForm();
	
	queryInfo();
});

//初始化订单基本信息form
function initOrderForm(){
	orderForm = $("#orderForm").ligerForm({
        inputWidth: 180, 
        labelWidth: 140, 
        space: 20,
        fields: [
             { display: "订单名称", name: "order_name",id:"order_name",
            	 type: "text", newline: true,readonly:true
             },
             { display: "所属卫星", name: "sat_name",id:"sat_name",
            	 type: "text", newline: false,readonly:true
             },
			{ display: "是否需要数据获取模块", name: "is_get_data",id:"is_get_data",
				type: "select", newline: true,readonly:true,
				data:[{id:"0",text:"否"},{id:"1",text:"是"}]
			},
			{ display: "是否需要结果处理模块", name: "is_result",id:"is_result",
				type: "select", newline: false,readonly:true,
				data:[{id:"0",text:"否"},{id:"1",text:"是"}]
			},
			{ display: "订单描述", name: "order_content",id:"order_content",
				width:524,type: "textarea", newline: true,readonly:true
			}
        ]
	});
	
	getDataForm = $("#getDataForm").ligerForm({
		inputWidth: 180, 
		labelWidth: 140, 
		space: 20,
		fields: [
		         { display: "数据获取类", name: "getdata_class_name",id:"getdata_class_name",
		        	 type: "text", newline: true,readonly:true
		         },
		         { display: "数据获取函数", name: "getdata_func_code",id:"getdata_func_code",
		        	 type: "text", newline: false,readonly:true
		         },
		         { display: "数据获取函数参数", name: "get_data_param",id:"get_data_param",
		        	 type: "textarea", newline: true,readonly:true,
		        	 width:524
		         }
		         ]
	});
	
	
	computForm = $("#computForm").ligerForm({
		inputWidth: 180, 
		labelWidth: 140, 
		space: 20,
		fields: [
		         { display: "数据计算类", name: "comput_class_name",id:"comput_class_name",
		        	 type: "text", newline: true,readonly:true
		         },
		         { display: "数据计算函数", name: "comput_func_code",id:"comput_func_code",
		        	 type: "text", newline: false,readonly:true
		         },
		         { display: "数据计算函数参数", name: "comput_param",id:"comput_param",
		        	 type: "textarea", newline: true,readonly:true,
		        	 width:524
		         }
		         ]
	});
	
	resultForm = $("#resultForm").ligerForm({
		inputWidth: 180, 
		labelWidth: 140, 
		space: 20,
		fields: [
		         { display: "数据处理类", name: "result_class_name",id:"result_class_name",
		        	 type: "text", newline: true,readonly:true
		         },
		         { display: "数据处理函数", name: "result_func_code",id:"result_func_code",
		        	 type: "text", newline: false,readonly:true
		         },
		         { display: "数据处理函数参数", name: "result_param",id:"result_param",
		        	 type: "textarea", newline: true,readonly:true,
		        	 width:524
		         }
		         ]
	});
}

function queryInfo(){
	$.ajax({
		url:basePath+'rest/orderManager/view',
		data:{
			orderId:orderId,
		},
		async:false,
		success:function(rsStr){
			var rsData = eval('('+rsStr+')');
			orderForm.setData(rsData);
			getDataForm.setData(rsData);
			computForm.setData(rsData);
			resultForm.setData(rsData);
		}
	});
}