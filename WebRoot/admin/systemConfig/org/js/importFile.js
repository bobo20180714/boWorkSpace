var fileUril = "";
var win = parent.currWin_fwxm;
//上传文件
function picpathchange() {
	var filePath = $(".business_license_accessory").val();

	var filePath2 = filePath.substring(filePath.lastIndexOf("\\") + 1);
	var suffixName = filePath2.substring(filePath2.lastIndexOf(".") + 1,
			filePath2.length);
	if (suffixName == "csv") {
		$.ajaxFileUpload({
			url : basePath+'rest/upload/fileUpload', //需要链接到服务器地址  
			secureuri : false,
			fileElementId : 'import_file', //文件选择框的id属性  
			dataType : 'text', //服务器返回的格式，可以是json  
			success : function(data, status) //相当于java中try语句块的用法  
			{
				var result1 = data.replace(/\\/ig, '/');
				var result = result1.replace(/<pre.*>(.*)<\/pre>/ig, '$1');
				var jsonData = eval("(" + result + ")");
				fileUril = jsonData.fileUrl;
				$("#fileName1").val(fileUril);
			},
			error : function(data, status, e) //相当于java中catch语句块的用法  
			{
				$("#fileName1").val("");
				parent.Alert.tip("上传文件时失败！");
			}
		});
	} else {
		parent.Alert.tip("请上传csv文件！");
		return;
	}

}

//导入后台		
function importFile() {
	if (fileUril == "" || fileUril == null || $("#fileName1").val() == ""
			|| $("#fileName1").val() == null) {
		parent.Alert.tip("请上传文件！");
		return;
	}

	if (fileUril) {
		$.ajax({
			url:basePath + "rest/orguser/userdaoru",
			data:{
				uploadFilePath : fileUril,
				org_id : org_id
			},
			async:false,
			success:function(dataStr){
				var data = eval('('+dataStr+')');
				if (data.success == "true") {
					parent.Alert.tip("导入成功！");
					parent.currWin.closeDlgAndReload();
				} else {
					if(data.message != null){
						parent.Alert.tip(data.message.replace(/&/g, '<br>'));
					}else{
						parent.Alert.tip("导入失败！");
					}
					parent.currWin.closeDlgAndReload();
				}
			}
		});
	}
}
