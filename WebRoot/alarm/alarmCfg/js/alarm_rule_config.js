var win = parent.currWin;
var comboData = null;
//保存时，请求参数
var paramData = {};
var oldCanalarm = "";

var conditionWin = null;

$(function (){	
	//初始化控件
	initControls();
	
	comboData = new Array();
	for(var i=1; i<=10; i++)
    {    
		var obj= new Object();
		obj.id=i;
		obj.text=i;
		comboData.push(obj);
    }
    for(var i=20; i<=100; i=i+10)
    {
    	var obj= new Object();
		obj.id=i;
		obj.text=i;
		comboData.push(obj);
    }
	$("#juddge_count").ligerComboBox({
			data:comboData,
			selectBoxWidth: 100,
			autocomplete: true,
			cancelable:false,
			width: 100
	});
	$("#juddge_count").ligerGetComboBoxManager().setValue(1);
	
	if(operate != "add"){
		getFormData();
	}

	//判断次数失去焦点
	$("#juddge_count").blur();
}); 

function initControls(){
	$("#relation_condition").ligerPopupEdit({
		onButtonClick : add_relat_cond,
		width : 300,
		cannotEdit : false
	});
	if(operate == "add"){
		$("#tm_param_name")[0].parentElement.style.display = "none";
		$("#param_name").show();
		$("#param_name").ligerPopupEdit({
			onButtonClick : param_name_click,
			width : 300
		});
		
	}
}

//添加关联条件
function add_relat_cond(){
	if(conditionWin != null){
		conditionWin.show();
		return;
	}
	setCurrWindows2();
	conditionWin = parent.$.ligerDialog.open({
				title : '关联条件',
				width : 680,
				height : 500, 
				url : basePath+'alarm/alarmCfg/selectCondition/GraphicalIn.jsp'
				});
}

/**
 * 供弹出框调用
 * @param sqlWhere
 */
function closeWin_2(sqlWhere){
	if(sqlWhere != ""){
		//sql输入框填充值
		$("#relation_condition").val(sqlWhere);
	}
 	conditionWin.hide();
}

//选择参数
function param_name_click(){
	setCurrWindows();
	xdlg = $.ligerDialog
			.open({
				title : '选择参数',
				width : 460,
				height : 400,
				url : basePath+'alarm/alarmCfg/selectParam.jsp?satid='+satid,
				buttons :[
							{ text: '确定', type:'save', width: 80 ,onclick:function(item, dialog){
								var rsData = dialog.frame.submitForm();	
								if(rsData.tmtype == "3"){
									parent.Alert.tip("暂不支持字符串类型的参数报警！");
									return;
								}
							 	$("#param_name").val(rsData.tmname+"("+rsData.tmcode+")");
							 	$("#tmid").val(rsData.tmid);
							 	paramData.tmid = rsData.tmid;
							 	paramData.judgetype = "0";
							 	f_closeDlg();
							 }},
							{ text: '取消', type:'close', width: 80, onclick:function(item, dialog){
								f_closeDlg();
							}}
				      	 ]
				});
}

//从后台数据库取数据，给表单控件赋值
function getFormData(){
	var url=basePath+'rest/alarmInfo/getTmAlarmInfo';
	$.ajax({
		url:url,
		data:{
			tmid:tmid,
			ruleid:ruleid
		},
		async:false,
		success:function(data){
			var jsobj = eval('('+data+')');
			//修改时，旧的是否报警赋值。
			oldCanalarm = jsobj.canalarm;
			//赋给全局变量
			paramData = jsobj;
			var thrid_validity = (1==(jsobj.rangevalidity & 0x1)? true:false);
			var second_validity = (1==((jsobj.rangevalidity & 0x2)>>1)? true:false);
			var first_validity = (1==((jsobj.rangevalidity & 0x4)>>2)? true:false);
			var can_alarm = ("0" == jsobj.canalarm) ? true:false;
			//参数名称
			$("#tm_param_name").val(jsobj.tmname);
			//有效值下限
			$("#range_value_lower").val(jsobj.rangevaluelower);
			//有效值上限
			$("#range_value_upper").val(jsobj.rangevalueupper);
			//重度下限
			$("#lower_first").val(jsobj.lowerfirst);
			//重度上限
			$("#upper_first").val(jsobj.upperfirst);
			//重度是否有效
			if(first_validity){
				$("#first_validity").ligerCheckBox().setValue(true);
			}
			
			//中度下限
			$("#lower_second").val(jsobj.lowersecond);
			//中度上限
			$("#upper_second").val(jsobj.uppersecond);
			//中度是否有效
			if(second_validity){
				$("#second_validity").ligerCheckBox().setValue(true);
			}
			
			//轻度下限
			$("#lower_thrid").val(jsobj.lowerthrid);
			//轻度上限
			$("#upper_thrid").val(jsobj.upperthrid);
			//轻度是否有效
			if(thrid_validity){
				$("#thrid_validity").ligerCheckBox().setValue(true);
			}
			//是否报警
			if (can_alarm){
				$("#can_alarm").ligerCheckBox().setValue(true);
			}
			//报警次数
			$("#juddge_count").ligerGetComboBoxManager().setValue(jsobj.judgecount?jsobj.judgecount:1);
			//关联条件
			$("#relation_condition").val(jsobj.relation);
			//关联条件是否有效
			if(jsobj.relationValid == "0"){
				$("#isValid").ligerCheckBox().setValue(true);
			}
		}
	});

}
	
// 页面验证
function toValidForm() {
	var flag = true;
	var tipTemp = "数据检验失败，请检查输入数据值，是否符合要求。";
	paramData.rangevaluelower = $("#range_value_lower").val()==""?"":parseFloat($("#range_value_lower").val());
	paramData.rangevalueupper = $("#range_value_upper").val()==""?"":parseFloat($("#range_value_upper").val());	
	if(isNull(paramData.rangevaluelower) || isNull(paramData.rangevalueupper)){
		flag = false;
		tipTemp = "有效值上下限值不能为空！";
	}
	paramData.lowerfirst = $("#lower_first").val()==""?"":parseFloat($("#lower_first").val());
	paramData.upperfirst = $("#upper_first").val()==""?"":parseFloat($("#upper_first").val());	
	paramData.first_validity = $("#first_validity").ligerCheckBox().getValue();
	
	paramData.lowersecond = $("#lower_second").val()==""?"":parseFloat($("#lower_second").val());
	paramData.uppersecond = $("#upper_second").val()==""?"":parseFloat($("#upper_second").val());
	paramData.second_validity = $("#second_validity").ligerCheckBox().getValue();
	
	paramData.lowerthrid = $("#lower_thrid").val()==""?"":parseFloat($("#lower_thrid").val());
	paramData.upperthrid = $("#upper_thrid").val()==""?"":parseFloat($("#upper_thrid").val());
	paramData.thrid_validity = $("#thrid_validity").ligerCheckBox().getValue();
	
	paramData.rangevalidity = 0;
	/*检验的要求：有效值上限≥重度上限≥中度上限≥轻度上限≥轻度下限≥中度下限≥重度下限≥有效值下限*/
	if(flag && paramData.first_validity){
		if(isNull(paramData.upperfirst) || isNull(paramData.lowerfirst)){
			flag = false;
			tipTemp = "重度上下限值不能为空！";
		}else{
			if(paramData.rangevalueupper <= paramData.upperfirst
					|| paramData.rangevaluelower >= paramData.lowerfirst){
				flag = false;
				tipTemp = "重度上下限值必须在有效值范围内！";
			}else if(paramData.second_validity){
				if(paramData.lowerfirst >= paramData.lowersecond 
						|| paramData.upperfirst <= paramData.uppersecond){
					tipTemp = "中度上下限值必须在重度上下限值范围内！";
					flag = false;
				}
			}else if(paramData.thrid_validity){
				if(paramData.lowerfirst >= paramData.lowerthrid 
						|| paramData.upperfirst <= paramData.upperthrid){
					tipTemp = "轻度上下限值必须在重度上下限值范围内！";
					flag = false;
				}
			}
		}
	}
	if(flag && paramData.second_validity){
		if(isNull(paramData.uppersecond) || isNull(paramData.lowersecond)){
			flag = false;
			tipTemp = "中度上下限值不能为空！";
		}else{
			if(paramData.rangevalueupper <= paramData.uppersecond
					|| paramData.rangevaluelower >= paramData.lowersecond){
				tipTemp = "中度上下限值必须在有效值范围内！";
				flag = false;
			}
			if(paramData.thrid_validity){
				if(paramData.uppersecond <= paramData.upperthrid
						|| paramData.lowersecond >= paramData.lowerthrid){
					tipTemp = "轻度上下限值必须在中度上下限值范围内！";
					flag = false;
				}
			}
		}
	}
	if(flag && paramData.thrid_validity){
		if(isNull(paramData.upperthrid) || isNull(paramData.lowerthrid)){
			flag = false;
			tipTemp = "轻度上下限值不能为空！";
		}else{
			if(paramData.rangevalueupper <= paramData.upperthrid
					|| paramData.rangevaluelower >= paramData.lowerthrid){
				tipTemp = "轻度上下限值必须在有效值范围内！";
				flag = false;
			}
		}
	}
	if(!paramData.thrid_validity
			&& !paramData.second_validity && !paramData.first_validity){
		tipTemp = "重度、中度、轻度至少需要有一个有效！";
		flag = false;
	}
	if(flag){	
		if($("#can_alarm").ligerCheckBox().getValue()){
			paramData.canalarm = 0; //是否报警0：是；1：否
		}else{
			paramData.canalarm = 1;
		}
		if(paramData.thrid_validity){
			paramData.rangevalidity = paramData.rangevalidity | 0x1;
		}
		if(paramData.second_validity){
			paramData.rangevalidity = paramData.rangevalidity | 0x2;
		}
		if(paramData.first_validity){
			paramData.rangevalidity = paramData.rangevalidity | 0x4;
		}		
		return true;
	}else{
		win.Alert.tip(tipTemp);
		return false;
	}
}

function isNull(data){
	if(data.length == 0){
		return true;
	}
	return false;
}
	
// 取消
function cancel(){
	win.f_closeDlg();
}

//保存数据
function submitForm(){
	if(operate == "add"){
		var paramName = $("#param_name").val();
		if(paramName == ""){
			Alert.tip("请选择参数！");
			return;
		}
	}
	if(toValidForm()){
		//报警次数
		paramData.judgecount = parseFloat($("#juddge_count").ligerGetComboBoxManager().getValue());
		paramData.relation = $("#relation_condition").val();
		//获取sql是否有效
		var relationValid = $("input[name='isValid']:checked").val();
		if(relationValid == undefined){
			relationValid = "1";
		}
		paramData.relationValid = relationValid;
		
		var url = "";
		if(paramData.ruleid == undefined){
			//还没有配置规则，新增规则
			url = basePath+"rest/limitRule/addLimitRule";
		}else{
			//已配置规则，修改规则
			url = basePath+"rest/limitRule/updateLimitRule";
			
			if(oldCanalarm == "1" && paramData.canalarm == 1){
				//若旧的报警时不报警，新的也是不报警，此时不需要放入内存中	
				paramData.toCach = "false";
			}else{
				paramData.toCach = "true";
			}
		}
		saveLimitRule(url);
		return true;
	}else{
		return false;
	}
}

function saveLimitRule(url){
	paramData.satid = satid;
	$.post(url,paramData,function(data){
		var rsObj = eval('('+data+')');
		if(rsObj.success){
			parent.Alert.tip("保存信息成功!");
			parent.f_reload();
			parent.f_closeDlg();
		}else{
			parent.Alert.tip(rsObj.message);
		}
	});
}

/**
 * 下拉框加验证红框,针对页面有多个form
 * 
 * @param id
 */
function initComboValidMuti(ids){
	var flag = true;
	if(ids!=null){
		var idArray = ids.split(",");
		for ( var int = 0; int < idArray.length; int++) {
			var id = idArray[int];
			if(id == ""){
				continue;
			}
			var value = $('#'+id+'_val').val();
			if(value==''){
				var a = document.getElementById(id+'_val');
				$(a).addClass("error");
				$(a).parent().addClass("l-text-invalid");
				$( $(a).attr( "title", "该字段不能为空！" ).ligerTip( {
			         distanceX: 170,
			         distanceY: -20,
			         auto: true
			     } ));
			}
			flag = false;
			break;
		}
	}
	
} 

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWinMid = window;
}

//选择关联条件使用
function setCurrWindows2() {
	parent.currWin = window;
}

//关闭对话框
function f_closeDlg() {
	xdlg.close();
}