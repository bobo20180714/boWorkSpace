var startWhere = 0;//从多少开始，默认为0
var orderType = "2";//顺序，1：正序，2：反序
var statusArr = null;//拆分状态数组
//级别数据
var gradeData = [{"id":"0","text":"正常"},{"id":"1","text":"重度"},
                         {"id":"2","text":"中度"},{"id":"3","text":"轻度"}];

var pwin = parent.currWin;
var rsObj = null;
//旧的是否报警
var oldCanalarm = "";
//报警次数数据
var comboData = null;

//表格所有数据
var gridAllData = null;

var conditionWin = null;

$(function(){
	
	comboData = new Array();
	for(var i=1; i<=10; i++)
    {    
		var obj= new Object();
		obj.id=i;
		obj.text=i;
		comboData.push(obj);
    }
    for(var i=20; i<=100; i=i+10)
    {
    	var obj= new Object();
		obj.id=i;
		obj.text=i;
		comboData.push(obj);
    }
	
	$("#status_word_name").ligerTextBox({
		width:250
	});
	$("#relation_condition").ligerPopupEdit({
		onButtonClick : add_relat_cond,
		width : 510,
		cannotEdit : false
	});
	$("#juddge_count").ligerComboBox({
		data:comboData,
		selectBoxWidth: 86,
		autocomplete: true,
		cancelable:false,
		width: 86
	});
	$("#juddge_count").ligerGetComboBoxManager().setValue(1);
	
	//显示复选框
	showCheckbox();
	if(type ==  "edit"){
			$.ajax({
				url:basePath+'rest/stateRule/querySonStateRule',
				data:{
					ruleId:ruleid
				},
				async:false,
				success:function(data){
					rsObj = eval('('+data+')');
				}
			});
		if(rsObj == null){
			return;
		}
		//填充编辑页数据
		loadEdited();
	}else{
		clearGridData();
	}
	//判断次数失去焦点
	$("#juddge_count").blur();
});

//添加关联条件
function add_relat_cond(){
	if(conditionWin != null){
		conditionWin.show();
		return;
	}
	setCurrWindows();
	conditionWin = parent.$.ligerDialog.open({
				title : '关联条件',
				width : 680,
				height : 500, 
				url : basePath+'alarm/alarmCfg/selectCondition/GraphicalIn.jsp'
				});
}

/**
 * 供弹出框调用
 * @param sqlWhere
 */
function closeWin_2(sqlWhere){
	if(sqlWhere != ""){
		//sql输入框填充值
		$("#relation_condition").val(sqlWhere);
	}
 	conditionWin.hide();
}

function closeWin(){
	conditionWin.close();
}

/**
 * 显示复选框
 */
function showCheckbox(){
	var crilNum = startWhere;//当前循环数字
	//判断是正序还是反序
	if(orderType == "2"){
		//反序
		crilNum = startWhere + 31;
	}
	//创建四行
	for ( var i = 0; i < 4; i++) {
		//创建行
		var row = $("#checkboxTable")[0].insertRow(i);
		//设置行高
		row.style.lineHeight = "22px";
		if(i == 2){
			//设置第三行行高
			row.style.lineHeight = "28px";
		}
		if(i == 1 || i == 3){
			if(orderType == "1"){
				//正序
				crilNum = crilNum - 16;
			}else if(orderType == "2"){
				//反序
				crilNum = crilNum + 16;
			}
		}
		for ( var j = 0; j < 16; j++) {
			//创建单元格
			var col = row.insertCell(j);
			//设置单元格宽度
			col.style.width = "35px";
			//设置单元格内容
			if(i == 0 || i == 2){
				col.innerHTML = crilNum;
			}else if(i == 1 || i == 3){
				col.innerHTML = '<input name="statusWordArr" type = "checkbox" id="B'+crilNum+'" value="B'+crilNum+'" onchange="AddBit(\'B'+crilNum+'\')"></input>';
			}
			if(orderType == "1"){
				crilNum++;
			}else{
				crilNum--;
			}
		}
	}
	$('input:checkbox').ligerCheckBox();
}

/**
 * 点击“正序”或“反序”按钮事件
 * @param orderNum 1:正序，2:反序
 */
function orderBtnClick(orderNum){
	if("1" == orderNum){
		$("#orderBtn_add").hide();
		$("#orderBtn_reduce").show();
	}else if("2" == orderNum){
		$("#orderBtn_add").show();
		$("#orderBtn_reduce").hide();
	}
	//设置顺序代号为正序或者反序
	orderType = orderNum;
	//移除复选框
	removeCheckbox();
	//重新显示浮选框
	showCheckbox();
	//清除表格内容
	clearGridData();
	
	//判断是显示“从1开始”还是“从0开始”
	if(startWhere == 0){
		$("#startWhereBtn_0").hide();
		$("#startWhereBtn_1").show();
		$("#startWhereBtn_-1").hide();
	}else{
		$("#startWhereBtn_0").show();
		$("#startWhereBtn_1").hide();
		$("#startWhereBtn_-1").hide();
	}
}

/**
 * 从多少开始按钮事件
 */
function startWhereBtnClick(startWhereNum){
	if("0" == startWhereNum){
		//点击从0开始，显示“从1开始”按钮，设置startWhere为0
		startWhere = 0;
		$("#startWhereBtn_0").hide();
		$("#startWhereBtn_1").show();
		$("#startWhereBtn_-1").hide();
	}else if("1" == startWhereNum){
		//点击从1开始，显示“从0开始”按钮，设置startWhere为1
		startWhere = 1;
		$("#startWhereBtn_0").show();
		$("#startWhereBtn_1").hide();
		$("#startWhereBtn_-1").hide();
	}else if("-1" == startWhereNum){
		//点击"全部清除"，显示“从多少开始”按钮
		startWhereBtnClick(startWhere);
	}
	//移除复选框
	removeCheckbox();
	//重新显示复选框
	showCheckbox();
	//清除表格内容
	clearGridData();
	//拆分状态数组置为空
	statusArr = null;
}

/**
 * 点击复选框事件
 */
function AddBit(checkBoxId){
	statusArr = new Array();
	var index = 0;
	//将选中的数据放入数组中
	if(orderType == "1"){
		//正序
		for(var i = startWhere;i < startWhere + 32; i++){
	        if($("#B"+i)[0].checked){
	        	statusArr[index] = "B"+i;
	            index++;
	        }
		}
	}else{
		//反序
		for(var i = startWhere + 31;i >= startWhere; i--){
	        if($("#B"+i)[0].checked){
	        	statusArr[index] = "B"+i;
	            index++;
	        }
		}
	}
	
	if(statusArr.length > 10 && checkBoxId != ""){
		$("#"+checkBoxId).ligerCheckBox().setValue(false);
		Alert.tip("最多选择10个状态字！");
		return;
	}
	
	if(statusArr.length==0){
		//根据从多少开始显示相应的按钮
		startWhereBtnClick(startWhere);
		//清除表格数据
		clearGridData();
        return;
    }
	$("#startWhereBtn_0").hide();
	$("#startWhereBtn_1").hide();
	$("#startWhereBtn_-1").show();
	
	/**创建列表 start*/
	var columnArr = new Array();
	columnArr.push({display: '级别', name: 'grade',width:80,
		 editor: { type: 'select', data: gradeData, valueField: 'id',
			 		textField: 'text'
			 	},render: function (item){
        	  for (var i = 0; i < gradeData.length; i++){
                  if (gradeData[i].id == item.grade){
                	  return gradeData[i].text
                  }
              }
         }
	})
	for(var i = 0 ;i < statusArr.length;i++){
		columnArr.push({display: statusArr[i], name: statusArr[i],width:60 })
    }
	columnArr.push({display: '状态', name: 'statusDesc',align: 'left',width:200, editor: { type: 'text' } });
	
	
	$("#stateTd").empty();
	$("#stateTd").append('<div id="stateGrid"></div>');
	gridManager = $("#stateGrid").ligerGrid({
        columns: columnArr,
        width: 800,
        cssClass: 'l-grid-gray',
        delayLoad:true,//第一次不加载
        height : 340,
		pageSize : 10,
		rownumbers:true,
		checkbox:false,
        heightDiff :13,
        frozen:false ,
        enabledEdit: true,
        onAfterEdit: updateData 
    });
	
	var gridData = new Array();
	for(var i=0;i<Math.pow(2,statusArr.length);i++){
		var row = {"pkid":i,"grade":"0","statusDesc":""};
		var bit = i;
		for(var j=0;j < statusArr.length;j++){
			row[statusArr[j]] = bit%2;
	  		bit = Math.floor(bit/2);
		}
		gridData.push(row);
	}
	
	gridAllData = gridData;
	gridManager.set({newPage:1});
	gridManager.loadData({"Rows":gridData,"Total":gridData.length});
	/**创建列表 end*/
}

//表格数据更改监听事件
function updateData(rowdata){
	if(gridAllData == null){
		return;
	}
	if(gridAllData.length > rowdata.record.pkid){
		gridAllData[rowdata.record.pkid].grade = rowdata.record.grade;
		gridAllData[rowdata.record.pkid].statusDesc = rowdata.record.statusDesc;
	}
}

//清除表格数据
function clearGridData(){
	$("#stateTd").empty();
	$("#stateTd").append('<div id="stateGrid"></div>');
	gridManager = $("#stateGrid").ligerGrid({
        columns: [
                  {display: '级别', name: 'grade',width:80},
                  {display: '状态', name: 'statusDesc',width:200}
        ],
        width: 800,
        cssClass: 'l-grid-gray',
        height : 340,
		pageSize : 10,
		rownumbers:true,
		checkbox:false,
        heightDiff :13,
        frozen:false ,
        data:{"Rows":[]}
    });
}

/**
 * 移除复选框
 */
function removeCheckbox(){
//	var rowLength = $("#checkboxTable")[0].rows.length;
//	for ( var i = 0; i < rowLength; i++) {
//		$("#checkboxTable")[0].deleteRow();
		$("#checkboxTable").empty();
//	}
}

/**
 * 保存状态字段
 */
function saveStatusWord(){
	var statusName = $("#status_word_name").val();
	if(statusName == ""){
		 Alert.tip("请输入状态名称！");
		 return false;
	}
	if(!/^[\u0391-\uFFE5a-zA-Z0-9_]+$/.test(statusName) || /^[αβδω]+$/.test(statusName))
    {
        Alert.tip("状态名称只允许输入汉字、英文字母、数字以及下划线！");
        return false;
    }
	
	//表格数据
	var datas = gridAllData;
	if(statusArr == null || statusArr.length < 1 || datas == null){
		 Alert.tip("请选择拆分状态位！");
		 return false;
	}
	
	var mask="";
	var subMask = new Array();
    for(var i = 0;i < datas.length;i++) subMask[i]="";
    var index=1;
    for(var i = startWhere+31; i >= startWhere; i--)
    {
        if($("#B"+i)[0].checked)
        {
            mask = mask+"1";
            for(var j = 0;j < datas.length;j++)
            {
                subMask[j] = subMask[j] + datas[j]["B"+i];
            }
            index++;
        }else{
            mask = mask+"0";
            for(var j = 0;j < datas.length;j++)
            {
                subMask[j] = subMask[j]+"0";
            }
        }
    }
    mask = parseInt(mask,2);
    
	var dataArr = new Array();
	for ( var i = 0; i < datas.length ; i++) {
		dataArr.push({
			"alarmLevel":datas[i].grade,
			"data":parseInt(subMask[i],2),
			"text":datas[i].statusDesc
		});
	}
	//获取sql是否有效
	var relationValid = $("input[name='isValid']:checked").val();
	if(relationValid == undefined){
		relationValid = "1";
	}
	
	//报警次数
	var judgecount = $("#juddge_count").ligerGetComboBoxManager().getValue();
	//获取是否报警
	var canalarm = $("input[name='canalarm']:checked").val();
	if(canalarm == undefined){
		canalarm = "1";
	}else if(judgecount == ""){
		 Alert.tip("请选择报警次数！");
		 return false;
	}
	
	//结果对象
	var rsObjs = {};
	if(type ==  "edit"){
		rsObjs = rsObj;
	}else{
		//新增
		rsObjs.tmcode = pwin.nowSelectTmcode;
		rsObjs.tmname = pwin.nowSelectTmname;
	}
	rsObjs.judgecount = judgecount;
	rsObjs.canalarm = canalarm;
	rsObjs.relationValid = relationValid,
	rsObjs.relation  =  $("#relation_condition").val();
	rsObjs.statusName  =  statusName;
	rsObjs.startWhere  =  startWhere;//从多少开始
	rsObjs.orderType  =  orderType;//顺序，1：正序，2：反序
	rsObjs.unit  =  dataArr; //状态描述列表数据
	rsObjs.mask  =  mask;
	rsObjs.tmid  =  nowSelectTmid;
	rsObjs.judgetype  =  "2";
	saveSonState(rsObjs);
}

/**
 * 设置编辑页数据
 */
function loadEdited(){
	//如果是编辑页面
	$("#relation_condition").val(rsObj.relation);
	$("#status_word_name").val(rsObj.status_name);
	
	//关联条件是否有效
	if(rsObj.relation_valid == "0"){
		$("#isValid").ligerCheckBox().setValue(true);
	}
	
	//是否报警
	oldCanalarm = rsObj.canalarm;
	if(rsObj.canalarm == "0"){
		$("#canalarm").ligerCheckBox().setValue(true);
	}
	
	//报警次数
	$("#juddge_count").ligerGetComboBoxManager().setValue(rsObj.judgecount);
	
	//从多少开始按钮事件
	startWhere =  parseFloat(rsObj.start_where);
	//按顺序显示复选框
	orderType =  parseFloat(rsObj.order_type);
	orderBtnClick(orderType);
	
	var mask = rsObj.mask;
	//列表数据
    var subStatus = rsObj.RULECONTENT;
    
    var pos = new Array();
    for(var j=0;subStatus!=null && j < subStatus.length;j++)
    {
        pos[j]="";
    }
    for(var i = startWhere;i < startWhere + 32;i++)
    {
        if(mask%2==1)
        {
            for(var j=0;j<subStatus.length;j++)
            {
                pos[j] = (subStatus[j].data)%2 + pos[j];
            }
            //选中复选框
            $("#B"+i)[0].previousSibling.className = "l-checkbox l-checkbox-checked";
            $("#B"+i)[0].checked = true;
        }
        mask = Math.floor(mask/2);
        if(mask==0) break;
    }
    //调用点击复选框事件
    AddBit('');

    //回显级别和描述
    for ( var i = 0;gridAllData != null && i < gridAllData.length; i++) {
    	gridAllData[i].grade = subStatus[i].alarmLevel;
    	gridAllData[i].statusDesc = subStatus[i].text;
	}
    gridManager.set({newPage:1});
    gridManager.loadData({"Rows":gridAllData,"Total":gridAllData.length});
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	parent.currWin = window;
}
//关闭对话框
function f_closeDlg() {
	xdlg.close();
}

/**
 * 保存拆分状态字
 */
function saveSonState(data){
	var url = '';
	var message = "操作成功！";
	if(type == "add"){
		url = basePath+'rest/stateRule/addStateRule';
		message = "新增成功！";
	}else if(type == "edit"){
		message = "编辑成功！";
		data.ruleid = ruleid;
		url = basePath+'rest/stateRule/updateStateRule';
		//若旧的不报警，新的也不报警，则不放入内存
		if(oldCanalarm == "1" && data.canalarm == "1"){
			//不放入内存
			$("#toCach").val("false");
		}else{
			//放入内存
			$("#toCach").val("true");
		}
	}
	data.satid = satid;
	var reqParam = JSON.stringify(data);
	$("#stateInfoStr").val(reqParam);
	$("#hideForm").ajaxSubmit({
		url:url,
		success:function(data){
			if(data == ""){
				parent.Alert.tip("请求出错！");
				return;
			}
			var jsobj = eval('('+data+')');
			if(jsobj.success == true){
				parent.Alert.tip(message);
         		//刷新表格
         		if(nowSelectTmid != ""){
         			pwin.gridStateBit.set("parms",{
         				"tmid":nowSelectTmid
         			});
         			pwin.gridStateBit.loadData();
         		}
//         		pwin.removeTab(type);
         		pwin.closeWin();
			}else{
				parent.Alert.tip(jsobj.message);
			}
		}
	});
}

function colseTab(){
	pwin.removeTab(type);
}
