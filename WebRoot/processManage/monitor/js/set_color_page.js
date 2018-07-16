var win = parent.currWin;
var colorLevel = null;

//将报警颜色设置的颜色，固定调整为#XXXXXX 6个字符长度
function adjustColor(colorValue)
{
    var color=colorValue.substr(1);
    //color = color.toString(16);
	if (color.length < 6)
	{
	  var sTempString = "000000".substring(0,6-color.length);
	  color = sTempString.concat(color);
	}
	return ("#"+color);	
}

//确定 按钮后，确保不同级别的报警颜色设置生效
function Confirm(){
	var flag = false;
	if($("#color_1").val == "" || $("#color_2").val == "" 
		|| $("#color_3").val == "" ||$("#color_4").val == "" 
		|| $("#color_5").val == "" ||$("#color_0").val == "" ){
		Alert.tip("必须设置状态的显示颜色");
		return;
	}
	
	colorLevel = new Array();
	
    colorLevel[0] = $("#color_0").val(); //document.getElementById('level1').value;
    colorLevel[1] = $("#color_1").val();//Color.rows(2).cells(1).style.backgroundColor;
    colorLevel[2] = $("#color_2").val();
    colorLevel[3] = $("#color_3").val();
    colorLevel[4] = $("#color_4").val();
    colorLevel[5] = $("#color_5").val();

   /* $.ajax({ 
		url:basePath+"rest/configInfo/updateGlobalConfig",
		data:{
			configItem : "color",
			content : $("#normal").val() + ";" + $("#color3").val() + ";" +
					  $("#color2").val() + ";" + $("#color1").val() + ";"
		}, 
		async:false,
		success:function(data){
			var jsobj = eval('('+data+')');
			if(jsobj.success == true ){
				flag = true;
			}
		}
	});*/
    return flag;
}
function Cancel()
{
    window.returnValue=null;
    window.close();
}

$(function () {
	//从后台调用，取得当前的报警显示颜色设置
	$.ajax({ 
		url:basePath+"rest/configInfo/getGlobalConfig",
		data:{
			"configItem" : "color"
		},
		async:false,
		success:function(data){ 
			var jsobj = eval('('+data+')');
			var flag = true;
			if(jsobj != null){
				//颜色配置
				var configItem = jsobj.configItem;
				if(configItem == null){
					flag = false
				}else{
					colorLevel = configItem.split(";");
					if(colorLevel == null || colorLevel.length < 5){
						flag = false
					}
				}
			}else{
				flag = false
			}
			if(flag){
				//initiliase the color  //jQuery("#" + id).val(aaa).css("background", aaa);
				$("#normal").val(colorLevel[0]).css("background", colorLevel[0]);
				$("#color1").val(colorLevel[3]).css("background", colorLevel[1]);// = colorLevel[1];
				$("#color2").val(colorLevel[2]).css("background", colorLevel[2]);
				$("#color3").val(colorLevel[1]).css("background", colorLevel[3]);
			}else{
				//如果从后台取得  报警显示颜色设置  失败，则以默认颜色进行设置
				colorLevel = new Array();
				colorLevel[0] = "#000000";//normal color
				colorLevel[1] = "#9d382d";//color1
				colorLevel[2] = "#313199";
				colorLevel[3] = "#9d922d";
				
				//initiliase the color
				$("#normal").val(colorLevel[0]).css("background", colorLevel[0]);
				$("#color1").val(colorLevel[1]).css("background", colorLevel[1]);
				$("#color2").val(colorLevel[2]).css("background", colorLevel[2]);
				$("#color3").val(colorLevel[3]).css("background", colorLevel[3]);
			}
		}
	});
	
});


//把当前操作句柄传递给父窗口
function setCurrWindows() {
	currWin = window;
}
//关闭对话框
function f_closeDlg() {
	xdlg.close();
}
