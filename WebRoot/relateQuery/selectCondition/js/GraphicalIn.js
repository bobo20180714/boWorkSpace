
//窗口
var condWin = parent.currWin;
var nowClickLineNo = "";
//条件数据
var conditionTypeData = [{"id":">","text":">"},{"id":"=","text":"="},{"id":"<","text":"<"}];
var count = 1;//条件个数
var lineNum = 1;//行号
var lineNumArr = [1];
//存放条件数组
var sqlWhereArr = new Array();
//模板
var templ = '<tr id="tr_{i}" style="height: 30px;">'+
			'   <td>{i}</td>'+
			'	<td width="190">'+
			'		<input name="device_input_{i}" id="device_input_{i}" type="text" ltype="popup" validate="{required:true}" />'+
			'	</td>'+
			'	<td width="190">'+
			'		<input name="device_param_{i}" id="device_param_{i}" type="text" '+
			'			validate="{required:true}"  readonly="readonly" />'+
			'		<input name="device_param_{i}_val" id="device_param_{i}_val" type="hidden"/>'+
			'	</td>'+
			'	<td width="112">'+
			'		<input name="condition_type_{i}" id="condition_type_{i}" type="text" ltype="select" validate="{required:true}" />'+
			'	</td>'+
			'	<td width="82">'+
			'		<input name="device_value_{i}" id="device_value_{i}" type="text" '+
			' 			validate="{required:true,number:true}" />'+
			'	</td>'+
			'	<td id="and_td_{i}" width="18">'+
			'	<div id="btn_and_{i}" class="button-class" '+
			'		onclick="btnAddFun(\'1\',{i})" align="center" title="与" '+
			'			style="width: 17px;height: 20px;margin-left: 2px;">与 '+
			'	</div>'+
			'	</td>'+
			'	<td id="or_td_{i}" width="18">'+
			' 		<div id="btn_or_{i}" class="button-class" '+
			'			onclick="btnAddFun(\'2\',{i})" align="center" title="或" '+
			'				style="width: 17px;height: 20px;margin-left: 0px;">或 '+
			'		</div>'+
			'	</td>'+
			'	<td width="18">'+
			'	<div id="btn_delete_{i}" class="button-class" '+
			'		onclick="deleteFun({i})" align="center" '+
			'				style="width: 17px;height: 17px;padding-top: 3px;margin-left: 0px;margin-right: 5px;"> '+
			'		<img src="'+basePath+'relateQuery/img/delete.gif"/> '+
			'	</div>'+
			'	</td>'+
			'</tr>';
$(function(){
	//Tab
	$("#frameTab").ligerTab({ height: "395px" });
	//sql条件form
	formData = $("#sqlForm").ligerForm({
        inputWidth: 550, 
        space:10,
        validate:true,
        fields: [
			{ display: "组合条件", name: "sqlTest",id:"sqlTest",width:150,labelWidth: 70, align:'right'},
			{ display: "最小段长度(s)", name: "minSize",id:"minSize",width:40,newline:false,labelWidth:90,value:0,align:'right'},
			{ display: "最小段间隔(s)", name: "time",id:"time",width:40,newline:false,labelWidth: 90,value:180, align:'right'},
			{ display: "查询超时(min)", name: "timeOut",id:"timeOut",width:40,newline:false,labelWidth: 90,value:3, align:'right'}
        ]
    });
	formDataSI = $("#sqlFormSI2").ligerForm({
        inputWidth: 550, 
        space:10,
        validate:true,
        fields: [
			{ display: "最小段长度(s)", name: "minLength",id:"minLength",width:40,newline:false,labelWidth:90,value:0,align:'right'},
			{ display: "最小段间隔(s)", name: "minInterval",id:"minInterval",width:40,newline:false,labelWidth: 90,value:180, align:'right'},
			{ display: "查询超时(min)", name: "queryTimeOut",id:"queryTimeOut",width:40,newline:false,labelWidth: 90,value:3, align:'right'}
		]
    });
	$("#device_input_1").ligerPopupEdit({
		width:178,
		onButtonClick:selectDevice
	});
	$("#device_param_1").ligerTextBox({
		width:178
	});
	//格式化条件下拉框
	$("#condition_type_1").ligerComboBox({
		width:100,
		data:conditionTypeData
	});
	$("#device_value_1").ligerTextBox({
		width:70
	});
});

/**
 * 选择航天器
 */
function selectDevice(){
	var obj = this;
	var objId = obj.id;
	var objId = objId.split("_");
	if(objId.length != 3){
		return;
	}
	//获取当前行号
	var lineNo = objId[2];
	nowClickLineNo = lineNo;
	setCurrWindows();
	//弹出选择航天器窗口
	selectDevWin = $.ligerDialog.open({ 
      	width : 600,
		height : 380, 
        title:'[选择遥测参数]信息',
		url: basePath+'relateQuery/selectCondition/selectParam.jsp',
		buttons :[
        { text: '确定', type:'save', width: 80 ,onclick:function(item, dialog){
        	//航天器code
        	var satCode = dialog.frame.satCode;
        	//航天器名称
        	var satName = dialog.frame.satName;
        	if(satCode == "" || satName == ""){
        		Alert.tip("请选择航天器！");
        		return;
        	}
        	var selectedParam = dialog.frame.paramManager.selected;
        	if(selectedParam.length < 1){
        		Alert.tip("请选择遥测参数！");
        		return;
        	}
        	//遥测参数
        	var tm_param_code = selectedParam[0].tm_param_code;
        	var tm_param_name = selectedParam[0].tm_param_name;
        	
         	//设备输入框填充值
         	$("#device_input_"+lineNo).val(satName+"("+satCode+")");
         	$("#device_input_"+lineNo+"_val").val(satCode);
         	//遥测参数信息填充值
         	$("#device_param_"+lineNo).val(tm_param_name+"("+tm_param_code+")");
         	$("#device_param_"+lineNo+"_val").val(tm_param_code);
         	selectDevWin.close();
         }},
        { text: '关闭', type:'close', width: 80, onclick:function(item, dialog){
        	selectDevWin.close();
        }}
	 ]
    });
	selectDevWin.show(); 
}

/**
 * 删除行方法
 * @param num
 */
function deleteFun(num){
	if(count == 1){
		//必须留一行条件
		return;
	}
	//若该行行号和行号数组中的最大一个数字相等，第二大的行号的按钮显示
	if(num == lineNumArr[lineNumArr.length-1]){
		$("#and_td_"+(lineNumArr[lineNumArr.length-2])).show();
		$("#or_td_"+(lineNumArr[lineNumArr.length-2])).show();
	}
	$("#tr_"+num).remove();
	//删除行号数组中的该行号
	this.deleteArrEle(num,lineNumArr);
	//删除条件数组中的元素
	this.deleteSqlArrEle(num,sqlWhereArr);
	//获取行号数组中此时最大的行号，将条件数组中对应的连接符修改为""
	this.updateRelation(lineNumArr[lineNumArr.length-1]);
	//显示sql语句
	this.showSQL();
	count--;
}

/**
 * 根据行号数组中此时最大的行号，
 * 将条件数组中对应的连接符修改为""
 */
function updateRelation(maxLineNo){
	if(sqlWhereArr == null){
		return;
	}
	var index = -1;
	for ( var i = 0; i < sqlWhereArr.length; i++) {
		if(maxLineNo == sqlWhereArr[i].lineNo){
			//获取元素所在下标
			index = i;
			break;
		}
	}
	if(index != -1){
		//连接符修改为""
		sqlWhereArr[index].relationCode = "";
		//显示sql语句
		this.showSQL();
	}
}

/**
 * 根据行号移除条件数组中的元素
 * @param lineNo
 * @param array
 * @returns
 */
function deleteSqlArrEle(lineNo,array){
	if(array == null){
		return;
	}
	var index = -1;
	for ( var i = 0; i < array.length; i++) {
		if(lineNo == array[i].lineNo){
			//获取元素所在下标
			index = i;
			break;
		}
	}
	if(index != -1){
		//移除元素
		array.splice(index,1);
	}
}

/**
 * 根据元素值移除数组元素
 * @param value
 * @param array
 * @returns
 */
function deleteArrEle(value,array){
	if(array == null){
		return;
	}
	var index = -1;
	for ( var i = 0; i < array.length; i++) {
		if(value == array[i]){
			//获取元素所在下标
			index = i;
			break;
		}
	}
	if(index != -1){
		//移除元素
		array.splice(index,1);
	}
	return array;
}

/**
 * 点击"与"或者"或"按钮
 * @param num
 */
function btnAddFun(type,num){
	
	//判断必输项
	var formFlag = $("#form_1").valid();
	if(!formFlag){
		return;
	}
	//替换模板中的行号{i}
	var templAnd = templ.replace(/{i}/g, lineNum+1);
	//添加到div
	$("#conditionTable").append(templAnd);
	//格式化input输入框
	$("#condition_type_"+(lineNum+1)).ligerComboBox({
		width:100,
		data:conditionTypeData
	});
	$("#device_input_"+(lineNum+1)).ligerPopupEdit({
		width:178,
		onButtonClick:selectDevice
	});
	$("#device_value_"+(lineNum+1)).ligerTextBox({
		width:70
	});
	$("#device_param_"+(lineNum+1)).ligerTextBox({
		width:178
	});
	//隐藏按钮
	$("#and_td_"+num).hide();
	$("#or_td_"+num).hide();
	//条件个数加1
	count++;
	//行号加1
	lineNum++;
	//将行号添加到行号数组中
	lineNumArr.push(lineNum);
	//更新sql
	this.replaceSql(type,num);
}

/**
 * 更新sql
 *	@param relation  1:与，2:或
 *	@param lineNo 行号
 */
function replaceSql(relation,lineNo){
	//获取数据
	//航天器code
	var deviceNo = $("#device_input_"+lineNo+"_val").val();
	deviceNo = deviceNo == undefined?"":deviceNo;
	//遥测参数code
	var paramCode = $("#device_param_"+lineNo+"_val").val();
	var condition = $("#condition_type_"+lineNo).val();
	var deviceValue = $("#device_value_"+lineNo).val();
	var relationCode = "";
	if("1" == relation){
		relationCode = "AND";
	}else if("2" == relation){
		relationCode = "OR";
	}
	//先移除
	this.deleteSqlArrEle(lineNo,sqlWhereArr);
	//将当前行条件，放入到条件数组中
	sqlWhereArr.push({
		deviceNo:deviceNo,
		paramCode:paramCode,
		condition:condition,
		deviceValue:deviceValue,
		relationCode:relationCode,
		lineNo:lineNo
	});
	//将下一个行条件，放入到条件数组中
	sqlWhereArr.push({
		deviceNo:"",
		paramCode:"",
		condition:"",
		deviceValue:"",
		relationCode:"",
		lineNo:lineNum
	});
	//显示sql语句
	this.showSQL();
}

/**
 * 显示sql语句
 */
function showSQL(){
	if(sqlWhereArr == null){
		return;
	}
	var sqlWhere = "";
	for ( var i = 0; i < sqlWhereArr.length; i++) {
		sqlWhere = sqlWhere + sqlWhereArr[i].lineNo + " " + 
				   sqlWhereArr[i].relationCode + " ";
	}
	liger.get("sqlTest").setValue(sqlWhere);
}

/**
 * 保存sql
 */

function saveSql(){
	if(lineNumArr == null){
		return;
	}
	var formFlag = $("#form_1").valid();
	if(!formFlag){
		return;
	}

	//结果	
	var sqlWhere = "";
	//将拼装的sql，分割
	var condition = liger.get("sqlTest").getValue().split(" ");
	var sql=[];
	for(var i = 0;i < condition.length; i++){
		if(condition[i].trim()=="") continue;
		sql.push(condition[i]);
	}
	if(sql.length == 0){
		//最后处理
		sql.push(lineNumArr[0].toString());
	}
	for(var i = 0;i < sql.length; i++){
		var right="",left="";
		if(sql[i]=="("||sql[i]==")") continue;
		if(sql[i].indexOf("AND")>-1){
			sql[i] = "&&";
			continue;
		}else if(sql[i].indexOf("OR")>-1){
			sql[i] = "||";
			continue;
		}
		//若第一个是"("
		if(sql[i].indexOf("(")>-1){
			sql[i] = sql[i].replace("(","");
			left=" (";
		}
		//若第一个是")"
		if(sql[i].indexOf(")")>-1){
			sql[i] = sql[i].replace(")","");
			right=") ";
		}
		//获取行号
		var lineNo = sql[i];
		//获取数据
		var deviceNo = $("#device_input_"+lineNo+"_val").val();
		deviceNo = deviceNo == undefined?"":deviceNo;
		//遥测参数code
		var paramCode = $("#device_param_"+lineNo+"_val").val();
		var condition = $("#condition_type_"+lineNo).val();
		var deviceValue = $("#device_value_"+lineNo).val();
		if(deviceNo == "" || condition == ""
			|| deviceValue == ""){
			continue;
		}
		
		sql[i] = left + "'" + deviceNo + "'.'TM'.'" + paramCode + "' " + condition + " " + deviceValue + right;
	}
	sqlWhere = sql.join(" ");
	return sqlWhere + ";";
}


function getConditions(){

	var sql = saveSql();
	var condition = liger.get("sqlTest").getValue();
	if(!/^(\s|\d*|AND|OR|\(|\))+(\s|\d*|AND|OR|\(|\))$/.test(condition)){
		Alert.tip("请检查组合条件是否正确!");
		return;
	}
	var data = formData.getData();
	return {
		"sql":sql,
		"time":data.time,
		"timeOut":data.timeOut,
		"minSize":data.minSize
	}
}

/**
 * 查询按钮事件
 */
function query(){
	var sqlArea = $("#sqlArea").val();
	if(sqlArea == ""){
		Alert.tip("请输入脚本!");
		return;
	}
	$.ajax({
		url:basePath+'rest/alarmInfo/checkRelation',
		data:{
			relation:sqlArea
		},
		async:false,
		success:function(data){
			var jsobj=eval('('+data+')');
			if(!jsobj.success){
				$("#resultArea").val(jsobj.message);
			}else{
				condWin.closeWin_2(sqlArea);
			}
		}
	});
	
}

/**
 * 关闭窗口
 */
function closeWin(){
	condWin.closeWin_2("");
}

//把当前操作句柄传递给父窗口
function setCurrWindows(){
  parent.parent.currWinFront = window;
}

/**
 * 供弹出框调用
 * @param satCode
 * @param satName
 * @param tm_param_code
 * @param tm_param_name
 */
function closeWin_2(satCode,satName,tm_param_code,tm_param_name){
	
	if(nowClickLineNo == ""){
		return;
	}
	
	//设备输入框填充值
 	$("#device_input_"+nowClickLineNo).val(satName);
 	$("#device_input_"+nowClickLineNo+"_val").val(satCode);
 	//遥测参数信息填充值
 	$("#device_param_"+nowClickLineNo).val(tm_param_name);
 	$("#device_param_"+nowClickLineNo+"_val").val(tm_param_code);
 	selectDevWin.close();
}


