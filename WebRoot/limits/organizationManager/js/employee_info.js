var gridManager = null;
var win = parent.currWin;
$(function ()
{
	$("#staff_code").ligerTextBox({
		
	});
	
	$("#staff_name").ligerTextBox({
		
	});
    //搜索
    $("#searchbtn").ligerButton({ click: function ()
        {
            gridManager.set('parms', { 
            	staffCode: $("#staffCode").val(),
            	staffName:$("#staffName").val()});
            gridManager.loadData();
        }
        
    }); 
    $("#resetbtn").ligerButton({ click: function ()
        {  
           $("#form1").get(0).reset();
        }
    }); 
    
    //表格
    $("#maingrid").ligerGrid({
        columns: [ 
        {display: 'pk_id',name: 'pk_id',hide:true},
		{ display: '员工工号', name: 'staff_code',type: 'text',width:100,
        	render: function(e){
            	if(e.staff_code !=undefined){
					var data="";
					data = e.staff_code;
					data = data.substr(0,6);
            		var h = "";
                    h += "<a style ='cursor: none;text-decoration: none;color:black' title = "+e.staff_code+" href=\"#\">"+data+"</a>";
             	 	return h;
            	}
            }},
		{ display: '员工姓名', name: 'staff_name',type: 'text',width:100,
            	render: function(e){
                	if(e.staff_name !=undefined){
    					var data="";
    					data = e.staff_name;
    					data = data.substr(0,6);
                		var h = "";
                        h += "<a style ='cursor: none;text-decoration: none;color:black' title = "+e.staff_name+" href=\"#\">"+data+"</a>";
                 	 	return h;
                	}
                }},
		{ display: '员工性别', name: 'sex_name',type: 'text',width:50
		},
		{ display: '入职日期', name: 'arrive_date',width:100},
		{ display: '联系方式', name: 'mobel_no',type: 'text',width:100},
		{ display: '岗位', name: 'job_name',type: 'text',width:100,
			render: function(e){
				if (e.job_name != undefined) {
					var data="";
					data = e.job_name;
					data = data.substr(0,6);
            		var h = "";
                    h += "<a style ='cursor: none;text-decoration: none;color:black' title = "+e.job_name+" href=\"#\">"+data+"</a>";
             	 	return h;
				}
			}
		}
		,
		{ display: '所属部门', name: 'org_name',type: 'text',width:150,
			render: function(e){
				if (e.org_name != undefined) {
					var data="";
					data = e.org_name;
					data = data.substr(0,10);
            		var h = "";
                    h += "<a style ='cursor: none;text-decoration: none;color:black' title = "+e.org_name+" href=\"#\">"+data+"</a>";
             	 	return h;
				}
			}
    	}
        ],
         
        url:basePath+'rest/GetStaffInfoAction/staffList', 
        height : '96%',
		pageSize : 30,
		rownumbers:true,
		checkbox:true,
		colDraggable:true,
        rowDraggable:true,
        heightDiff :13,
        frozen:false
    });
    gridManager = $("#maingrid").ligerGetGridManager();
    $("#pageloading").hide();
});

function getStrctureByID(org_id){
	var jsobj = null;
	$.ajax({
		url:basePath+"rest/organization/getStrctureByID",
		data:{
			org_id:org_id
		},
		async:false,
		success:function(data){
			jsobj = eval('('+data+')');
		}
	});
	if(jsobj != null){
		return jsobj[0].text;
	}
}

function submitForm(pk_ids,org_id){
	$.ajax({
		url:basePath+'rest/GetStaffInfoAction/selectStaff',
		data:{
			pk_ids:pk_ids,
			org_id:org_id
			},
		success:function(result) {
			var returnData=eval("("+result+")");
			if (returnData.success == 'true') {
				win.Alert.tip("添加成功！");
				win.loadDate(org_id);
				win.closeWindow("createWindow");
			} else {
				Alert.tip("添加失败！");
			}
		}
	});
}