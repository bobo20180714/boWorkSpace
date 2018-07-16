var filePath = null;
var tTime = null;
$(function(){
});

/**
 * 校验文件类型
 */
function valiFile(){
	var filePath = $("#excel_file").val();
	var fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
	$("#fileName").val(fileName); 
	uploadFile();
}

/**
 * 上传文件
 */
function uploadFile(){
	var fileName = $("#fileName").val();
	var arr = fileName.split("\.");
	if(arr!=null && arr.length > 1 && arr[1] != "xls" && arr[1] != "xlsx"){
		parent.Alert.tip('请选择excel文件');
		return;
	}
	$("#form1").ajaxSubmit({
		success:function(response, status){
			var result1 = response.replace(/\\/ig, '/');
			var result = result1.replace(/<pre.*>(.*)<\/pre>/ig, '$1');
			 json = eval("(" + result + ")");
			 if(json.success){
				 filePath = json.fileUrl;
			 }
		}
	});
}

function getData(){
	if(!filePath){
		parent.Alert.tip("请选择文件！");
		return null;
	}
	return {
		filePath:filePath
	};
}