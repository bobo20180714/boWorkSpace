var gridManager = null;
$(function () {   
	//maingrid initiliase
    init_maingrid();
    initQueryForm();
    //search and reset button click_event
    $("#searchbtn").ligerButton({type:'one',click: search_btn_click});	
  	//重置搜索条件
	$("#resetbtn").ligerButton({ type:'two',click: clearForm}); 
	
});

function initQueryForm(){
    formData1 = $("#form1").ligerForm({
    		labelAlign:'right',
        	inputWidth: 120, 
            labelWidth: 80, 
            space: 5,
            validate:true,
            fields: [
				{display: '关键字', id: 'sat_id',name:'sat_id',type:"text",newline: false},
			]
    });
}

function init_maingrid(){
	gridManager = $("#maingrid").ligerGrid({
        columns: [
                  	{ name: 'pk_id',hide:true,width:0.1}
                  	,
                    {display: '编号', name: 'sys_resource_id',width:80 }
					,
					{display: '卫星名', name: 'name',width:150 }
					,
					{display: '卫星编号', name: 'code',width:120 }
					,
					{display: '卫星业务编号', name: 'mid',width:90 }
        ],
        dataAction:'server',
        width: '99.7%',
        height: '99.9%',
        pageSize: 10,
        rownumbers:true,
        checkbox : false,        
        frozen:false,
        usePager:true,
        rowHeight:27,
        //应用灰色表头
        cssClass: 'l-grid-gray',
        onlyOneCheck: true,        
        url:basePath+"rest/satinfoLimit/satList"
    });
	
}

//查找按钮的单击事件处理
function search_btn_click(){
	var queryData = formData1.getData();
	var sat_id = queryData.sat_id;	
	gridManager.set('parms', {
		key:sat_id
	});
	gridManager.set({newPage:1});
	gridManager.loadData();
}

//Clear the search form
function clearForm(){
	formData1.setData({
		sat_id:""
	});
}

//将卫星的选择信息，返回到调用的窗口;
function submitForm(){
	debugger;
	var flag=true;
	if (!gridManager.getSelectedRow()) { 
		flag=false;
		Alert.tip("请至少选择一条数据！");
		return flag; 
	}
	if (gridManager.selected.length > 1) { 
		flag=false;
		Alert.tip("只能选择一条数据！");
		return flag; 
	}
	
	//取得卫星名称
	var sat_pkid = gridManager.selected[0].sys_resource_id;
	var sat_name = gridManager.selected[0].name;
	var sat_code = gridManager.selected[0].code;
	
	parent.sat_pkid = sat_pkid;
	parent.sat_name = sat_name;
	parent.sat_code = sat_code;
	return flag;
}

//把当前操作句柄传递给父窗口
function setCurrWindows() {
	currWin = window;
}
