var win = parent.currWin;

$(function (){
	getFormData();
}); 


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
			//赋给全局变量
			paramData = jsobj;
			var thrid_validity = (1==(jsobj.rangevalidity & 0x1)? true:false);
			var second_validity = (1==((jsobj.rangevalidity & 0x2)>>1)? true:false);
			var first_validity = (1==((jsobj.rangevalidity & 0x4)>>2)? true:false);
			var can_alarm = ((0 == jsobj.canalarm) ? true:false);
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
			if (jsobj.canalarm == "0"){
				$("#can_alarm").ligerCheckBox().setValue(true);
			}
			//报警次数
			$("#juddge_count").ligerGetComboBoxManager().setValue(jsobj.judgecount);
			
			//关联条件
			$("#relation_condition").val(jsobj.relation);
			//关联条件是否有效
			if(jsobj.relationValid == "0"){
				$("#isValid").ligerCheckBox().setValue(true);
			}
		}
	});
}
	
// 取消
function cancel(){
	win.f_closeDlg();
}

/**
 * 
 * 获取url里的参数
 * 
 * @param 参数名
 */
function getParam(name){

	var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)","i");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null){
		return unescape(r[2]);
	}
	return null;

}