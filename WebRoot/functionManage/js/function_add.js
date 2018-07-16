//基础信息表单对象
var computeInfoForm = null;
//参数类型
var paramTypeArr = [{id:'1',text:'整型'},{id:'2',text:'浮点型'},
                     {id:'3',text:'字符串'}];
//参数展示类型
var paramShowTypeArr = [{id:'1',text:'常量'},{id:'5',text:'时间'},{id:'2',text:'遥测参数'},{id:'3',text:'枚举'},{id:'4',text:'计算结果'}];

//超时时长
var overTimeArr = [{id:'1',text:'1'},{id:'2',text:'2'},{id:'3',text:'3'},{id:'4',text:'4'},{id:'5',text:'5'},{id:'6',text:'6'},
					{id:'7',text:'7'},{id:'8',text:'8'},{id:'9',text:'9'},{id:'10',text:'10'},{id:'20',text:'20'},
					{id:'30',text:'30'},{id:'50',text:'50'},{id:'100',text:'100'}];
//计算失败次数
var computCountArr = [{id:'1',text:'1'},{id:'2',text:'2'},{id:'3',text:'3'},{id:'4',text:'4'},{id:'5',text:'5'},{id:'6',text:'6'},
					{id:'7',text:'7'},{id:'8',text:'8'},{id:'9',text:'9'},{id:'10',text:'10'}];

//参数表格数据
var tableData = null;
//tab页对象
var addTab = null;

var isInitRunParam = false;

var runParamForm = null;

var paramGridObj = null;

$(function(){

	addTab = $("#addTab").ligerTab({
		onAfterSelectTabItem:selectTabItem
	});
	//初始化基础信息表单
	initComputeInfoForm();
	
});

function selectTabItem(e){
	if(e == "tabitem2"){
		if(!isInitRunParam){
			//初始化运行参数表单
			initRunParamForm();
			initToolBar();
			initParamGrid();
		}
	}
}

/**
 * 初始化基础信息表单
 */
function initComputeInfoForm(){
	computeInfoForm = $("#computeInfoForm").ligerForm({
		labelAlign:'left',
        inputWidth: 170, 
        labelWidth: 100, 
        space: 20,
        validate:true,
        fields: [
           /*  { display: "模块代号", name: "fctCode",id:"fctCode",
            	 type: "text", newline: true,validate:{required:true,maxlength:50}
             },*/
			{ display: "功能名称", name: "computeName",id:"computeName",
				type: "text", newline: true,validate:{required:true,maxlength:200}
			},
			{ display: "版本号", name: "version",id:"version",
				type: "text", newline: false,validate:{required:true,maxlength:20}
			},
			{ display: "java类名称", name: "fctClassName",id:"fctClassName",
				type: "text", newline: true,validate:{required:true,maxlength:25}
			},
             { display: "所在包名", name: "fctPckName",id:"fctPckName",
            	 type: "text", newline: false,validate:{required:true,maxlength:300}
             },
             { display: "是否自定义界面", name: "isUserDefined",id:"isUserDefined",
            	type: "radiolist", newline: true,value:1,onSelect:selectIsGetData,
 				data:[{id:"0",text:"是"},{id:"1",text:"否"}]
             },
             { display: "自定义界面路径", name: "userPagePath",id:"userPagePath",
            	 type: "text", newline: true,width:460
             },
             { display: "功能描述", name: "computeDesc",id:"computeDesc",
 				width:460,
 				type: "textarea", newline: true,validate:{required:false,maxlength:500}
 			}
        ]
	});
	liger.get('userPagePath').setDisabled(true);
	$('#computeDesc')[0].style.height = "100px";
}

/**
 * 初始化运行参数表单
 */
function initRunParamForm(){
	runParamForm = $("#runParamForm").ligerForm({
		labelAlign:'left',
		inputWidth: 146, 
		labelWidth: 120, 
		space: 20,
		validate:true,
		fields: [
		         { display: "超时时间(分钟)", name: "overTime",id:"overTime",value:1,
		        	 type: "select", newline: true,validate:{required:true},
		        	 data:overTimeArr
		         },
		         { display: "最大失败次数", name: "computeCount",id:"computeCount",value:3,
		        	 type: "select", newline: false,validate:{required:true},
		        	 data:computCountArr
		         }
	         ]
	});
}

//初始化工具条
function initToolBar() {
	var parambarObj = $("#parambar").ligerToolBar({
		items : [ {
			text : '添加',
			id : 'add',
			click : itemclick,
			icon:'add'
//			img : basePath + 'lib/ligerUI/skins/icons/add.gif'
		} ,{
			text : '删除',
			id : 'delete',
			click : itemclick,
			icon:'delete'
//			img : basePath + 'lib/ligerUI/skins/icons/delete.gif'
		}]
	});
}

//初始化参数信息表格
function initParamGrid() {
	paramGridObj = $("#paramGrid").ligerGrid({
		columns : [
		           {
			     	   display : '操作',
			     	   name : 'operate',
			     	   align : 'center',
			     	   width : 80,
			     	   render:function(item){
			     		   return "<table style='margin-top:3px;margin-left:5px;'><tr><td width='43px'><img src='"+basePath+"lib/ligerUI/skins/icons/up.gif' onclick='operate(\"up\","+
			     		   JSON.stringify(item)+")'></img></td><td><img src='"+basePath+"lib/ligerUI/skins/icons/down.gif' onclick='operate(\"down\","+
			     		   JSON.stringify(item)+")'></img></td></tr></table>";
			     	   }
			        },
		           {
		        	   display : '参数中文名称',
		        	   name : 'param_name',
		        	   align : 'center',
		        	   width : 120
		           },
		           {
		        	   display : "参数英文代号",
		        	   name : "param_code",
		        	   align : 'center',
		        	   width : 110
		           },
		           {
		        	   display : '参数类型',
		        	   name : 'param_type',
		        	   align : 'center',
		        	   width : 100,
		        	   render:function(item){
		        		   if(item.param_type){
		        			  for(var i = 0;i<paramTypeArr.length;i++){
		        				  if(item.param_type == paramTypeArr[i].id){
				        			   return paramTypeArr[i].text;
				        		   }
		        			  }
		        		   }
		        	   }
		           },
		           {
		        	   display : '参数展示类型',
		        	   name : 'param_show_type',
		        	   align : 'center',
		        	   width : 100,
		        	   render:function(item){
		        		   if(item.param_show_type){
		        			   for(var i = 0;i<paramShowTypeArr.length;i++){
		        				   if(item.param_show_type == paramShowTypeArr[i].id){
		        					   return paramShowTypeArr[i].text;
		        				   }
		        			   }
		        		   }
		        	   }
		           }
		           ],
		           height:200,
		           width:550,
		           rownumbers : false,
		           checkbox : true,
		           rowHeight : 27,
	   			   heightDiff : 30,
		           usePager:false,
		           frozen : false 
	});
}

/**
 * 选择是否自定义界面事件
 * @param e
 */
function selectIsGetData(e){
	if(e.value == 1){
		addTab.showTabItem("tabitem2");
		liger.get('userPagePath').setValue("");
		liger.get('userPagePath').setDisabled(true);
	}else{
		addTab.hideTabItem("tabitem2");
		liger.get('userPagePath').setEnabled(true);
	}
}

function itemclick(item){
	if (item.id) {
		switch (item.id) {
			case "add":
				var addFieldWin = parent.$.ligerDialog.open({
					width : 565,
					height : 260,
					title : "新增参数信息",
					isResize : false,
					url : basePath+"functionManage/field_add.jsp",
					buttons:[
				         { text: '确定', type:'save',width: 80 ,onclick:function(item, dialog){
						 	var paramData = dialog.frame.submitForms();
						 	if(paramData == false){
						 		return;
						 	}
						 	if(paramData != null){
						 		addFieldWin.close();
						 		paramGridObj.addRow({
						 			param_name:paramData.paramName,
						 			param_code:paramData.paramCode,
						 			param_type:paramData.paramType,
						 			param_show_type:paramData.paramShowType,
						 			param_value:paramData.comboBoxData,
						 			param_text:paramData.comboBoxShowData,
						 			is_valid:paramData.isValid,
						 			max_value:paramData.maxValue,
						 			min_value:paramData.minValue
						 		});
						 	}
						 }},
						 { text: '关闭', type:'close',width: 80, onclick:function(item, dialog){
							 addFieldWin.close();
						 }}
					]
				});
				break;
			case "update":
				var addFieldWin = parent.$.ligerDialog.open({
					width : 560,
					height : 260,
					title : "修改参数信息",
					isResize : false,
					url : basePath+"functionManage/field_update.jsp",
					buttons:[
					         { text: '确定', type:'save',width: 80 ,onclick:function(item, dialog){
					        	 var paramData = dialog.frame.submitForms();
					        	 if(paramData != null){
					        		 addFieldWin.close();
					        	 }
					         }},
					         { text: '关闭', type:'close',width: 80, onclick:function(item, dialog){
					        	 addFieldWin.close();
					         }}
					         ]
				});
				break;
			case "delete":
				for ( var i = 0; i < paramGridObj.selected.length; i++) {
					paramGridObj.deleteSelectedRow();
				}
				break;
		}
	}
}

function operate(type,rowParm){
	var g = paramGridObj;
	if(type == "up"){
		 var rowdata = g.getRow(rowParm);
	     var listdata = g._getParentChildren(rowdata);
	     for(var i = 0;i<listdata.length;i++ ){
	    	 if(rowParm.param_code == listdata[i].param_code
	    			 && i > 0){
	    		 var tempData = listdata[i - 1];
	    		 listdata[i - 1] = rowdata;
	    		 listdata[i] = tempData;
	    		 g.loadData({'Rows':listdata});
	    		 break;
	    	 }
	     }
	}else if(type == "down"){
	     var rowdata = g.getRow(rowParm);
	     var listdata = g._getParentChildren(rowdata);
	     for(var i = 0;i<listdata.length;i++ ){
	    	 if(rowParm.param_code == listdata[i].param_code
	    			 && i < listdata.length - 1){
	    		 var tempData = listdata[i + 1];
	    		 listdata[i + 1] = rowdata;
	    		 listdata[i] = tempData;
	    		 g.loadData({'Rows':listdata});
	    		 break;
	    	 }
	     }
	}
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}

function submitForms() {
	var flag = -1;
	var data = computeInfoForm.getData();
	if(toValidForm(data)){
		//获取参数表格数据
		var paramArr = new Array();
		if(paramGridObj && paramGridObj.rows.length > 0){
			for(var i=0;i < paramGridObj.rows.length;i++){
				paramArr.push({
					param_name:paramGridObj.rows[i].param_name,
					param_code:paramGridObj.rows[i].param_code,
					param_type:paramGridObj.rows[i].param_type,
					param_show_type:paramGridObj.rows[i].param_show_type,
		 			param_value:paramGridObj.rows[i].param_value,
		 			param_text:paramGridObj.rows[i].param_text,
		 			is_valid:paramGridObj.rows[i].is_valid,
		 			max_value:paramGridObj.rows[i].max_value,
		 			min_value:paramGridObj.rows[i].min_value
				});
			}
		}
		data.fieldList = paramArr;
		data.fctType = 1;
		if(runParamForm){
			var runParamData = runParamForm.getData();
			data.overTime = runParamData.overTime;
			data.computeCount = runParamData.computeCount;
		}
		$.post(basePath+"rest/ComputeFunc/add",data,function(rsData){
			var jsobj = eval('('+rsData+')');
			if(jsobj.success=='true'){
				parent.Alert.tip("注册计算模块成功!");
				parent.f_closeAndLoad();
			}else{
				parent.Alert.tip("注册计算模块失败!");
			}
		});
	}else{
		return false;
	}
	return flag;
}

//验证
function toValidForm(data) {
	var vboolean = computeInfoForm.valid();
	if (vboolean) {
		return true;
	} else {
		return false;
	}
}
