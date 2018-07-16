var win = parent.currWin;
var formData=null;
var groupicon = basePath+"lib/ligerUI/skins/icons/communication.gif";
var flagFromValue = 1; //0:从0开始显示标志位,1:从1开始显示标志位, 默认=1；
var flagAscend = 1; //0:倒序显示（Bit32--Bit1）；1：正序显示（Bit1--Bit32）

var dataGrid0 = [
                { id: 1, name: '0'},
                { id: 2, name: '1'}, 
                { id: 3, name: '2'},
                { id: 4, name: '3'},
                { id: 5, name: '4'},
                { id: 6, name: '5'},
                { id: 7, name: '6'},
                { id: 8, name: '7'},
                { id: 9, name: '8'}, 
                { id: 10, name: '9'},
                { id: 11, name: '10'},
                { id: 12, name: '11'},
                { id: 13, name: '12'},
                { id: 14, name: '6'},
                { id: 15, name: '13'},
                { id: 16, name: '15'}, 
                { id: 17, name: '16'},
                { id: 18, name: '17'},
                { id: 19, name: '18'},
                { id: 20, name: '19'},
                { id: 21, name: '20'},
                { id: 22, name: '21'},
                { id: 23, name: '22'}, 
                { id: 24, name: '23'},
                { id: 25, name: '24'},
                { id: 26, name: '25'},
                { id: 27, name: '26'},
                { id: 28, name: '27'},
                { id: 29, name: '28'},
                { id: 30, name: '29'},
                { id: 31, name: '30'},
                { id: 32, name: '31'}
                ];

var dataGrid1 = [
                { id: 1, name: '1'}, 
                { id: 2, name: '2'},
                { id: 3, name: '3'},
                { id: 4, name: '4'},
                { id: 5, name: '5'},
                { id: 7, name: '6'},
                { id: 6, name: '7'},
                { id: 8, name: '8'}, 
                { id: 9, name: '9'},
                { id: 10, name: '10'},
                { id: 11, name: '11'},
                { id: 12, name: '12'},
                { id: 13, name: '6'},
                { id: 14, name: '13'},
                { id: 15, name: '15'}, 
                { id: 16, name: '16'},
                { id: 17, name: '17'},
                { id: 18, name: '18'},
                { id: 19, name: '19'},
                { id: 20, name: '20'},
                { id: 21, name: '21'},
                { id: 22, name: '22'}, 
                { id: 23, name: '23'},
                { id: 24, name: '24'},
                { id: 25, name: '25'},
                { id: 26, name: '26'},
                { id: 27, name: '27'},
                { id: 28, name: '28'},
                { id: 29, name: '29'},
                { id: 30, name: '30'},
                { id: 31, name: '31'},
                { id: 32, name: '32'}
                ];
/*
$(function (){
// liger.get("register_money").setValue(1);
}); 

*/
$(function (){
	$("#checkboxlist1").ligerCheckBoxList({
		rowSize:8,
		data:dataGrid1,
		textField:'name'
	});	
	
	$("#maingrid").ligerGrid({
        columns: [
                  	{display: '', name: 'pk_id',hide:true,width:0.1}
                  	,
                    {display: 'TMID', name: 'TmID',align: 'left',width:50 }
					,
					{display: '遥测名', name: 'TM_PARAM_NAME',align: 'left',width:80 }
					,
					{display: '遥测编号', name: 'TM_PARAM_CODE',align: 'left',width:80 }
				]
	/*
	// 创建表单结构
    formData=$("#form1").ligerForm({
    	labelAlign:'right',
        inputWidth: 100, 
        labelWidth: 100, 
        space: 20,
        validate:true,
        fields: [
            { name: "TmID", type: "hidden" },
            { display: "遥测参数", name: "TM_PARAM_NAME", newline: true, width: 380, type: "text" }, 
            { display: "有效值下限", name: "RangeValueLower", newline: true, type:"text"},
            { display: "有效值上限", name: "RangeValueUpper", newline: false, type:"text"},
            //重度报警门限 
            { display: "重度下限", name: "LowerFirst", newline: true, type:"number"},
            { display: "上限", name: "UpperThrid", newline: false, type:"number"},
            { display: "是否有效", name: "UpperThrid", newline: false, type:"checkbox"},
            //中度报警门限
            { display: "中度下限", name: "LowerSecond", newline: true, type:"number"},
            { display: "上限", name: "UpperSecond", newline: false, type:"number"},
            { display: "是否有效", name: "UpperThrid", newline: false, type:"checkbox"},
            //轻度报警门限
            { display: "重度下限", name: "LowerThrid", newline: true, type:"number"},
            { display: "上限", name: "UpperFirst", newline: false, type:"number"},
            { display: "是否有效", name: "UpperThrid", newline: false, type:"checkbox"},
            //是否浮点型、是否报警、判断次数
            { display: "是否浮点型", name: "TM_PARAM_TYPE", newline: true, type:"checkbox"},
            { display: "是否报警 ", name: "CanAlarm", newline: false, type:"checkbox"},
            { display: "判断次数 ", name: "JudgeCount", newline: false, width: 40, type: "number"  }
           ]
           */
       }); 
	
});

//正序或反序显示Bit位选择项
function setAscendOrder()
{
	var data_array = liger.get("checkboxlist1").data;
	for(var i=0; i < data_array.length/2; i++)
	{
		var tmp_data=data_array[i];
		data_array[i] = data_array[data_array.length - 1 - i];
		data_array[data_array.length - 1 - i] = tmp_data; 
	}
	if(1 == flagAscend) //当前是正序显示，修改为反序显示
	{
		flagAscend = 0;
		liger.get("checkboxlist1").setData(data_array);
	}
	else
	{
		flagAscend = 1;
		liger.get("checkboxlist1").setData(data_array);
	}
	return;
}

//设置Bit位选择项从0开始，还是从1开始
function setFromValue()
{
	//alert($("#set_from_value").html());
	if(1 == flagFromValue)// 当前是从1--32显示Bit位，修改为从0--31显示Bit位
	{
		liger.get("checkboxlist1").setData(dataGrid0);
		$("#set_from_value").html("从1开始");
		flagFromValue = 0;
	}
 	else //// 当前是从0--31显示Bit位，修改为从1--32显示Bit位
	{
		liger.get("checkboxlist1").setData(dataGrid1);
		$("#set_from_value").html("从0开始");
		flagFromValue = 1;
	}
	return;
}

//取消所有的Bit位选择
function setClearAll()
{
	liger.get("checkboxlist1").clear();
}
/*
        function getValue()
        {
            var value = liger.get("checkboxlist1").getValue();
            alert(value);
        }
        function setValue()
        {
            liger.get("checkboxlist1").setValue("2;4");
        }
*/
	
// 页面验证
function toValidForm() {
	var vboolean = $("#form1").valid();
	if (vboolean) {
		return true;
	}
	return false;
}
	
// 取消
function cancel(){
	/*
	var tabId = getParam("tabId");
	var navtab = window.parent.$("#framecenter").ligerGetTabManager();
	navtab.removeSelectedTabItem();
	*/
	win.f_closeDlg();
}

/* For the POST form, submit the form data*/
function submitForm() {
	if(toValidForm()){
		var data = formData.getData();
		var jsonFormat = {
				"summary": "Blogs",
				"blogrolls":[
				             {"title":"Explore JavaScript",
				            	 "link":"http://example.com/"
				            	 },
				             {
				            		 "title":"Explore JavaScript",
					            	 "link":"http://example.com/"	 
				             }
				             ]
		};
		$.ajax({/*
			url:"", //basePath+"rest/AlarmRuleAction/addAlarmRule",
			data:{
				userAccount:data.userAccount,
				userName:data.userName,
				userPwd:data.userPwd,
				state:"0"
			},*/
			
			asynsc:false,
			success:function(data){
				var jsobj=eval('('+jsonFormat+')'); //var jsobj=eval('('+data+')');				
				if(jsobj.success == 'true'){
					win.Alert.tip("新建信息成功!");
					win.closeDlgAndReload();
				}else{
					win.Alert.tip("新建信息失败!");
				}
			}
		});
	}
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
			         distanceX: 170,  // 2014-04-25 景科文修改 错误提示信息位置
			         distanceY: -20,
			         auto: true
			     } ));
			}
			flag = false;
			break;
		}
	}
	
} 

// 模拟参数报警设置信息的添加
function addAlarmRuleInfo(){
	/*
		var temp1 = $(".business_license_accessory").val();
		if(temp1 == "" || temp1 == null){
			parent.Alert.tip("营业执照附件不能为空!");
			return;
		}else{
			var fieldtype=temp1.substring(temp1.lastIndexOf(".")+1,temp1.length).toLocaleLowerCase();
			if(fieldtype!='png'&&fieldtype!='jpeg'&&fieldtype!='jpg'){
				parent.$.ligerDialog.alert('请选择正确的图片文件进行上传！','提示','warn');
			    return;
		    }
		}
		if(toValidForm()){
			var data = formData.getData();
			data.identity_card_accessory = temp1;
			var baseVal = $("#base").val() ;
			data.sqlId = "personInfo_add";
			data.tableSpace = "personManager";
			data.delete_state = "1";

			$.post(
				basePath+"rest/personAction/personNumAdd",data,
				function(data){
					if(data.success=="true"){
						var tabId = getParam("tabId");
						var navtab = window.parent.$("#framecenter").ligerGetTabManager();
						win.f_reload();
						navtab.removeSelectedTabItem();
					}else{
						alert("报名失败，请与管理员联系！");
					}
				},
				"json"
			);
		}else{
			var data = formData.getData();
			var marriage_condition = data.marriage_condition;
			if(marriage_condition == null||marriage_condition==""){
				alert("婚姻情况还没有选择!");
			}
		}
		*/
	alert("Will be processed later....");
}

// 身份证件附件
function valiImageSize(){
	var imgPath = $(".business_license_accessory").val();
	var fileName = imgPath.substring(imgPath.lastIndexOf("\\")+1);
	$("#fileName1").val(fileName); 
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