var formData3 = null;
var sat_id = "";
var groupicon;
$(function (){
	initForm();
	getData();
});



/**
 * 加载渲染jsp页面
 */
function initForm(){
	formData3 = $("#form3").ligerForm({
		width:630,
    	labelAlign:'left',
        inputWidth: 150, 
        labelWidth: 120, 
        space: 20,//间隔宽度
        validate:true,//验证
        fields: [
			{ display: "卫星名称", name: "sat_name",id:"sat_name",type: "text",newline: false,readonly:true,group: "基础信息", groupicon: groupicon},
			{ display: "卫星编号", name: "sat_code",id:"sat_code",type: "text",newline: false,readonly:true},
			{ display: "任务代号", name: "mid",id:"mid",type: "text",newline: true,readonly:true},
			{ display: "状态", name: "status",id:"status",type: "hidden",newline: false,readonly:true},
			{ display: "设计单位", name: "design_org",id:"design_org",type: "text",newline: true,readonly:true, group: "详细信息", groupicon: groupicon},
			{ display: "用户单位", name: "user_org",id:"user_org",type: "text",newline: false,readonly:true},
			{ display: "设计寿命(年)", name: "design_life",id:"design_life",type: "text",newline: true,readonly:true},
			{ display: "发射时间", name: "launch_time",id:"launch_time",type: "date",newline: false,readonly:true},
			{ display: "超寿时间(年)", name: "over_life",id:"over_life",type: "text",newline: true,readonly:true},
			{display: '定点位置', id: 'location',name:'location',type:"text",newline: false,readonly:true},
			{display: '应用领域', id: 'domain',name:'domain',type:"text",newline: true,readonly:true},
			{display: '平台', id: 'platform',name:'platform',type:"text",newline: false,readonly:true},
			{display: '总师', id: 'first_designer',name:'first_designer',type:"text",newline: true,readonly:true},
			{display: '团队', id: 'team',name:'team',type:"text",newline: false,readonly:true},
			{display: '责任人', id: 'duty_officer',name:'duty_officer',type:"text",newline: true,readonly:true},
			{display: '卫星遥测频率(Mhz)', id: 'sat_ftm',name:'sat_ftm',type:"text",newline: false,readonly:true},
			{display: '卫星遥控频率(Mhz)', id: 'sat_ftc',name:'sat_ftc',type:"text",newline: true,readonly:true},
			{display: '轨道高度(千米)', id: 'sat_orbit_height',name:'sat_orbit_height',type:"text",newline: false,readonly:true},
			{display: '轨道周期(秒)', id: 'sat_cycle',name:'sat_cycle',type:"text",newline: true,readonly:true},
			{display: '地理经度±180°范围', id: 'sat_longtitude',name:'sat_longtitude',type:"textarea",newline: true,readonly:true,width:'440'}
        ]
    }); 
	
}


//页面提交
function submitForm(){
	var data = formData3.getData();
	//数据提交
	data.sat_id=getParam("sat_id");
	$.post(basePath+"rest/satinfo/satbasicinfoupdate",data, 
			 "json");
}

/**
*
*获取url里的参数
*@param 参数名
*/
function getParam(str){
	var reg = new RegExp("(^|&)"+str+"=([^&]*)(&|$)","i");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null){
		return unescape(r[2]);
	}
	return null;
}

function getData(){
	var id = getParam("sat_id");
	$.post(
			basePath+"rest/satinfo/getSatBasicInfoById",
			{
				sat_id:id
			},
			function(data)
			{
				var row = data;
				formData3.setData(row);
			},"json");
}











	