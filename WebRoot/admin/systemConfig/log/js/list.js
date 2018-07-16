var alert = function (content)
{
	parent.Alert.tip(content);
};
var gridManager = null;
var arrParam = [{id:"0",text:"航天器"},{id:"1",text:"函数"},{id:"2",text:"引擎"},
				{id:"3",text:"用户"},{id:"4",text:"用户组"},{id:"5",text:"机构"},
				{id:"6",text:"角色"},{id:"7",text:"用户权限授权"},{id:"8",text:"用户数据授权"},
				{id:"9",text:"用户组数据授权"},{id:"10",text:"机构数据授权"},{id:"11",text:"门限及状态字"}];
$(function () {   
	var height = $(".l-layout-center").height();
   
    $("#begin_time").ligerDateEditor({
		format: "yyyy-MM-dd hh:mm:ss",
		showTime: true,
    	width: 150,
	});
	$("#end_time").ligerDateEditor({
		format: "yyyy-MM-dd hh:mm:ss",
		showTime: true,
    	width: 150,
	});
	$("#user_name").ligerTextBox({
	});
    
    //表格
    gridManager = $("#maingrid").ligerGrid({
        columns: [
	                  {display: '操作时间', name: 'starttime',align: 'center',width:160}
	                  ,
	                {display: '用户名称', name: 'user_name',align: 'center',width:100}
                    ,
        			{display: '所属机构', name: 'org_name',align: 'center',width:180,
                    	render:function(item){
                    		if(item.org_name != null){
                    			return '<p title="'+item.org_name+'">'+item.org_name+'</p>';
                    		}
                    	}
        			}
        			,
        			{display: '业务大类', name: 'operatetype',align: 'center',width:130}
        			,
        			{display: '操作对象编码', name: 'entitycode',align: 'center',width:130 ,
         				render:function(item){
         					if(item.entitycode != null){
         						return '<p title="'+item.entitycode+'">'+item.entitycode+'</p>';
         					}
         				}
         			}
        			,
        			{display: '操作对象名称', name: 'entityname',align: 'center',width:130 ,
         				render:function(item){
         					if(item.entityname != null){
         						return '<p title="'+item.entityname+'">'+item.entityname+'</p>';
         					}
         				}
         			}
        			,
        			{display: '操作IP', name: 'clientip',align: 'center',width:120}
        			,
        			{display: '操作内容', name: 'operatecontent',align: 'left',width:350}
        ], 
        dataAction:'server',
        width: '99.9%', 
        height: '99.9%', 
        pageSize: 30,
        rowHeigth:17,
        rownumbers:true,
        checkbox : false,
        frozen:false,
        //应用灰色表头
        cssClass: 'l-grid-gray', 
        url:basePath+'rest/LogAction/list'
    });
    //grid查询
    $("#searchbtn").ligerButton({ click: function ()
    {
    	var begin_time = $("#begin_time").val();
    	var end_time = $("#end_time").val();
//    	if(begin_time == "" || end_time == ""){
//    		Alert.tip("请选择时间区间！");
//    	}
    	
	    gridManager.set('parms', {
	    	user_name:$("#user_name").val(),
	    	begin_time:begin_time,
	    	end_time:end_time
	    });
		gridManager.set({newPage:1});
		gridManager.loadData();
    }  
	});
	
  	//重置搜索条件
	$("#resetbtn").ligerButton({ click: function ()
         {
              clearForm();   
           }
	}); 
});

//刷新grid
function f_reload()
{    
    gridManager.set({newPage:1});    
    gridManager.loadData(true);
}
function clearForm(){
   $('#form1').find(':input').each(  
   function(){
	   switch(this.type){
		   case 'passsword': 
		   case 'select-multiple':  
		   case 'select-one':  
		   case 'text':
		   case 'textarea': 
			    if(!this.readOnly){
		          $(this).val('');  
		          break;
		        }
		   case 'checkbox':  
		   case 'radio': 
		        this.checked = false;  
	   }
	}     
	);  
}
