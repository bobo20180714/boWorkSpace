var formData = null;
var sat_id = "";
var groupicon;
$(function (){
	initForm();
});

function isEmpty(param){
	if(param==null||$.trim(param)==""){
		return true;
	}else{
		return false;
	}
	
}

/**
 * 加载渲染jsp页面
 */
function initForm(){
	formData = $("#form1").ligerForm({
		width:650,
    	labelAlign:'left',
        inputWidth: 150, 
        labelWidth: 120, 
        space: 20,//间隔宽度
        validate:true,//验证
        fields: [
			{ display: "卫星名称", name: "sat_name",id:"sat_name",type: "text",newline: false,
				validate:{required:true,maxlength:30},
				group: "基础信息", groupicon: groupicon},
			{ display: "卫星编号", name: "sat_code",id:"sat_code",type: "text",newline: false,
					validate:{required:true,maxlength:30 }},
			{ display: "任务代号", name: "mid",id:"mid",type: "text",newline: true,validate:{required:true,maxlength:8,number:true }},
			{ display: "设计单位", name: "design_org",id:"design_org",type: "text",newline: true,validate:{maxlength:30 }, group: "详细信息", groupicon: groupicon},
			{ display: "用户单位", name: "user_org",id:"user_org",type: "text",newline: false,validate:{maxlength:30 }},
			{ display: "设计寿命(年)", name: "design_life",id:"design_life",type: "text",newline: true,validate:{maxlength:10,number:true}},
			{ display: "发射时间", name: "launch_time",id:"launch_time",type: "date",newline: false},
			{ display: "超寿时间(年)", name: "over_life",id:"over_life",type: "text",newline: true,validate:{maxlength:10 ,number:true}},
			{display: '定点位置', id: 'location',name:'location',type:"text",newline: false,validate:{maxlength:30 }},
			{display: '应用领域', id: 'domain',name:'domain',type:"text",newline: true,validate:{maxlength:30 }},
			{display: '平台', id: 'platform',name:'platform',type:"text",newline: false,validate:{maxlength:30 }},
			{display: '总师', id: 'first_designer',name:'first_designer',type:"text",newline: true,validate:{maxlength:15 }},
			{display: '团队', id: 'team',name:'team',type:"text",newline: false,validate:{maxlength:30 }},
			{display: '责任人', id: 'duty_officer',name:'duty_officer',type:"text",newline: true,validate:{maxlength:10 }},
			{display: '卫星遥测频率(Mhz)', id: 'sat_ftm',name:'sat_ftm',type:"text",newline: false,validate:{maxlength:10,number:true}},
			{display: '卫星遥控频率(Mhz)', id: 'sat_ftc',name:'sat_ftc',type:"text",newline: true,validate:{maxlength:10,number:true}},
			{display: '轨道高度(千米)', id: 'sat_orbit_height',name:'sat_orbit_height',type:"text",newline: false,validate:{maxlength:10,number:true}},
			{display: '轨道周期(秒)', id: 'sat_cycle',name:'sat_cycle',type:"text",newline: true,validate:{maxlength:10,digits:true},width:'440'},
			{display: '地理经度±180°范围', id: 'sat_longtitude',name:'sat_longtitude',type:"textarea",newline: true,validate:{maxlength:500 },width:'440'}
        ]
    }); 
	
}

//页面提交
function submitForm(){
	var data = formData.getData();
	if(toValidForm()){
		
		//判断jsjg_code是否符合要求，英文字母开头
		var exp= /^[A-Z][A-Z0-9_-]*$/;
		if(!exp.test(data.sat_code)){
			$.ligerDialog.warn("卫星编号只允许输入大写字母、数字、横线以及下划线,且以字母开头！");
			return false;
		}
		
		//判断卫星编号是否重复
		if(judgeSatCode(data.sat_code)){
			parent.Alert.tip("卫星编号已经存在！");
			return false;
		}
		//判断卫星代号是否重复
		if(judgeMid(data.mid)){
			parent.Alert.tip("任务代号已经存在！");
			return false;
		}
		
		$.ajax({
			url:basePath+"rest/satinfo/add",
			data:data,
			async:false,
			success:function(rsData) {
				var result = eval("("+rsData+")");
				if (result.success == "true") {
					if(userAccount == managerAccount){
						parent.Alert.tip("新增卫星成功!");
					}else{
						parent.$.ligerDialog.success("新增卫星成功!请联系超级管理员进行数据授权！");
					}
					parent.currWin.closeDlgAndReload();
				} else {
					parent.Alert.tip("新增卫星失败!");
				}
			}
		});
	}
}

/**
 * 判断卫星编号是否重复
 * @param satCode
 */
function judgeSatCode(satCode){
	var flag = false;
	$.ajax({
		url:basePath+"rest/satinfo/judgeSatCode",
		data:{
			satCode:satCode
		},
		async:false,
		success:function(rsData) {
			var result = eval("("+rsData+")");
			if (result.success == "true") {
				flag = true;
			}
		}
	});
	return flag;
}

/**
 *判断卫星代号是否重复
 * @param mid
 */
function judgeMid(mid){
	var flag = false;
	$.ajax({
		url:basePath+"rest/satinfo/judgeMid",
		data:{
			mid:mid
		},
		async:false,
		success:function(rsData) {
			var result = eval("("+rsData+")");
			if (result.success == "true") {
				flag = true;
			}
		}
	});
	return flag;
}

/**
 * 表单校验
 * @param data 表单数据
 * @returns {为true则表单校验通过，false为不通过。}
 */
function check(data){
	 
	var NumInterReg = /^[1-9][0-9]{13}\.[0-9]{8}$/;
	if(data!=null){
/*		if(isEmpty(data.multicast_address)){
			$.ligerDialog.warn("组播地址不能为空！");
			return false;
		}
		if(isEmpty(data.udp_port)){
			$.ligerDialog.warn("组播端口不能为空！");
			return false;
		}*/
		/*var vboolean = $("#form1").valid();
		if (vboolean) {
			return true;
		} else {
			return false;
		}*/
		if(data.sat_ftm!='' && isFloat(data.sat_ftm)){
			$.ligerDialog.warn("卫星遥测频率只能输入浮点型数字！");
			return false;
		}
		if(data.sat_ftc!='' && isFloat(data.sat_ftc)){
			$.ligerDialog.warn("卫星遥控频率只能输入浮点型数字！");
			return false;
		}
		if(data.sat_orbit_height!='' && isFloat(data.sat_orbit_height)){
			$.ligerDialog.warn("轨道高度只能输入浮点型数字！");
			return false;
		}
		if(data.sat_cycle!='' && isFloat(data.sat_cycle)){
			$.ligerDialog.warn("轨道周期只能输入浮点型数字！");
			return false;
		}
		if(data.sat_ftm!=''&& data.sat_ftm.split(".")[0]>0 && data.sat_ftm.split(".")[0].length>5){
			$.ligerDialog.warn("卫星遥测频率整数位数不超过5");
			return false;
		}
		if(data.sat_ftm!="" 
			&& data.sat_ftm.split(".").length>1
			&&	data.sat_ftm.split(".")[1].length>8){
			$.ligerDialog.warn("卫星遥测频率小数位数不超过8");
			return false;
		}
		if(data.sat_ftc!="" && data.sat_ftc.split(".")[0]>0 
				&& data.sat_ftc.split(".")[0].length>5){
			$.ligerDialog.warn("卫星遥控频率整数位数不超过5");
			return false;
		}
		if(data.sat_ftc!="" 
			&& data.sat_ftc.split(".").length>1
			&& data.sat_ftc.split(".")[1].length>8){
			$.ligerDialog.warn("卫星遥控频率小数位数不超过8");
			return false;
		}
		if(data.sat_orbit_height!="" 
			&& data.sat_orbit_height.split(".")[0].length>8){
			$.ligerDialog.warn("轨道高度整数位数不超过8");
			return false;
		}
		if(data.sat_orbit_height!="" 
			&& data.sat_orbit_height.split(".").length>1
			&& data.sat_orbit_height.split(".")[1].length>8){
			$.ligerDialog.warn("轨道高度小数位数不超过8");
			return false;
		}
/*		if(data.sat_cycle!="" && data.sat_cycle.length>10){
			$.ligerDialog.warn("轨道周期输入长度不能超过10");
			return false;
		}*/
	}else{
		$.ligerDialog.warn("未获得表单数据！");
		return false;
	}
	return true;
}


function isFloat(param){
	var integerReg = /^[0-9]{0}([0-9]|[.])+$/;
	//var integerReg = /^\\d+(\\.\\d)?*$/;
	if(!integerReg.test(param)){
		return true;
	}else
		return false;
	
}

//验证
function toValidForm() {
	var vboolean = $("#form1").valid();
	if (vboolean) {
		return true;
	} else {
		return false;
	}
}



	