var form;
        $(function () {  
        	form=$("#form1").ligerForm({
				inputWidth: 200, 
	       		labelWidth: 100, 
	       		space: 20,
	       		labelAlign:"center",
	            validate:true,
                fields: [
                 { type: "hidden",id:"res_father", name: "res_father"},
                 {type: "hidden",id:"pk_id", name: "pk_id"},
                 { type: "text", display: "资源名称",id:"res_name", name: "res_name",validate: {required: true,minlength:2,maxlength:10}},
                 { type: "text", display: "资源编号",id:"res_code", name: "res_code",validate: {required: true,maxlength:20}},
                 { type: "select" ,display: "资源类型",id:"res_type", name: "res_type",data:[{id:'0',text:'资源菜单'},{id:'1',text:'页面元素'}],emptyText:false, validate: {required: true}},
                 { type: "select" ,display: "显示类型",id:"show_type", name: "show_type",data:[{id:'1',text:'子界面'},{id:'2',text:'选项卡'},{id:'3',text:'弹出窗口'}],
            		validate:{required:true},
           		 helpTip:{img:basePath+'resources/images/help.png',title:'选择弹出窗口时，资源表达式填写方法名称'}},
                 { type: "text" ,display: "资源表达式",id:"res_value", name: "res_value",validate: {required: false}
            	/*	,
           		 helpTip:{img:basePath+'resources/images/help.png',title:'一级菜单不需要输入'}*/	
                 },
                 { type: "text", display: "排序序号",id:"order_num", name: "order_num",validate: {required: true,justNumber:true}},
                 { type: "text" ,display: "上级资源",id:"res_father_name", name: "res_father_name",readonly:true}
			]});
        	 setData();
        });
        
        function setData(){
        	$.ajax({
		  		url:basePath+"rest/ResourcesAction/getRowRecord",
		  		data:{
		  			resCode:parent.currWin.editSelectId
		  		},
		  		async : false,
		  		success:function(data){
		  			var jsobj=eval('('+data+')');
		  			form.setData(jsobj);
		  			
		  			/*if(!jsobj.res_father_name){
		  				liger.get('res_value').setDisabled(true);
		  			}*/
		  		}
		  	  });
        }
        //提交
		function submitForm(){
			 if (form.valid()) {
				 var data = form.getData();
				 $.ajax({
			  		url:basePath+"rest/ResourcesAction/alter",
			  		data:{
			  			pkId:data.pk_id,
			  			resName:data.res_name,
			  			resCode:data.res_code,
			  			resType:data.res_type,
			  			resValue:data.res_value,
			  			resFather:data.res_father,
			  			showType:data.show_type,
			  			orderNum:data.order_num
			  		},
			  		async : false,
			  		success:function(data){
			  			var jsobj=eval('('+data+')');
			  			if (jsobj.success == 'true'){
			  				parent.currWin.Alert.tip(jsobj.message); 
	                        window.parent.currWin.closeDlgAndReload();
	                    } else{
	                    	parent.currWin.Alert.tip(jsobj.message);  
						}
			  		}
			  	  });
			 }
		}
//取消
function colseWindow(){
    parent.currWin.xdlg.close(); 
}