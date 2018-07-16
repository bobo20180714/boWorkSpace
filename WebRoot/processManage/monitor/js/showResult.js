
//循环查询运行参数
var resultInterval = null;

var runParamData = null;

var textStyle = 'width:100px;height:25px;border:1px solid #7179a7;padding:0;';
var valueStyle = 'width:100px;height:25px;border:1px solid #7179a7;';

$(function(){
	
	//查询表格列信息
	queryResult();
	
	resultInterval = setInterval("queryResult()",2000);
	
});

//循环查询运行参数
function queryResult(){
	$.ajax({
		url:basePath+'rest/relationInfo/queryResult',
		data:{
			processCode:processCode
		},
		async:false,
		success:function(rsStr){
			runParamData = eval('('+rsStr+')');
			/*columns = new Array();
			for(var i=0;rsData != null && i<rsData.length;i++){
				columns.push({
					display : rsData[i].display,
					name : rsData[i].name,
					align : rsData[i].align,
					width : rsData[i].width
				});
			}*/
		}
	});
	if(runParamData != null && runParamData.length > 0){
		$("#bodyDiv").empty();
		var cols = 1;
		var vHtml = '<table  width="100%"  style="border-collapse: collapse;height:100%;">';
		for ( var i = 0; i < runParamData.length; i++) {
			var display = runParamData[i].text
			var value = runParamData[i].value
			//如果不是新的一行
			if(i % cols != 0){
				vHtml+='<td align="center" nowrap="nowrap" style="'+textStyle+'">'
					+ display
					+'</td><td align="center" nowrap="nowrap" style="'+valueStyle+'">'
					+value+'</td>';
				if((i % cols) == (cols-1)){//当每行到达最后一个参数，也就是第cols个的时候
					vHtml+='</tr>';
				}
			}else{
				vHtml+='<tr><td width="30%" align="center" nowrap="nowrap" style="'+textStyle+'">'
					+ display
					+'</td><td align="center" nowrap="nowrap" style="'+valueStyle+'">'
					+value+'</td>';
			}
			//如果走到了最后一个并且不是一行的最后一个，则需要再次添加</tr>标签
			if(i == (runParamData.length - 1) && (i % cols) != (cols-1)){
				vHtml+='</tr>';
			}
		}
		vHtml+='</table>';
		$("#bodyDiv").append(vHtml);
	}
}
