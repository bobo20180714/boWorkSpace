//参数描述数组
var paramArr = null;

var textStyle = 'width:100px;height:25px;border:1px solid #7179a7;padding:0;';
var valueStyle = 'width:100px;height:25px;border:1px solid #7179a7;';

$(function(){
	
	//查询启动参数描述
	queryStartupParam();
	
});

//查询启动参数描述
function queryStartupParam(){
	$.ajax({
		url:basePath+'rest/processManager/viewProcess',
		data:{
			processCode:processCode
		},
		async:false,
		success:function(rsStr){
			processData = eval('('+rsStr+')');
		}
	});
	if(processData != null){
		var startup_param = processData.startupParam;
		if(startup_param){
			paramArr = startup_param.split(";");
			$("#bodyDiv").empty();
			var cols = 1;
			var vHtml = '<table  width="100%"  style="border-collapse: collapse;height:100%;">';
			for ( var i = 0; i < paramArr.length; i++) {
				var display = paramArr[i]
				//如果不是新的一行
				if(i % cols != 0){
					vHtml+='<td align="center" nowrap="nowrap" style="'+textStyle+'">'
					+ display
					+'</td><td align="center" nowrap="nowrap" style="'+valueStyle+'">'
					+'<input type="text" id="startParam_'+i+'"  style="width:96%;height:88%"/>'+'</td>';
					if((i % cols) == (cols-1)){//当每行到达最后一个参数，也就是第cols个的时候
						vHtml+='</tr>';
					}
				}else{
					vHtml+='<tr><td width="30%" align="center" nowrap="nowrap" style="'+textStyle+'">'
					+ display
					+'</td><td align="center" nowrap="nowrap" style="'+valueStyle+'">'
					+'<input type="text" id="startParam_'+i+'"  style="width:96%;height:88%"/>'+'</td>';
				}
				//如果走到了最后一个并且不是一行的最后一个，则需要再次添加</tr>标签
				if(i == (paramArr.length - 1) && (i % cols) != (cols-1)){
					vHtml+='</tr>';
				}
			}
			vHtml+='</table>';
			$("#bodyDiv").append(vHtml);
		}
	}
}

/**
 * 获取输入的启动参数
 */
function getParamData(){
	var paramData = "";
	for ( var i = 0; i < paramArr.length; i++) {
		if(i == 0){
			paramData = $("#startParam_"+i).val();
		}else{
			paramData = paramData + ";" + $("#startParam_"+i).val();
		}
	}
	return paramData;
}