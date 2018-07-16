var tree = null;
$(function ()
     {
	tree = $("#roleLimitTree").ligerTree({
			 	url:basePath+'rest/RoleAction/getResTreeByRoleCode?roleCode='+parent.currWin.gridManager.getCheckedRows()[0].pk_id,
		    	textFieldName:'res_name',
		    	idFieldName : 'pk_id',
		    	parentIDFieldName : 'res_father',
		    	checkbox:true,
		    	autoCheckboxEven: true
		});
		$("#role_name").text(parent.currWin.roleName);
     }); 
	   function formSubmit(){
            var childNodes = tree.getChecked();
		    /*if(childNodes.length==0){
		    	parent.currWin.Alert.tip('请选择资源节点');
		        return;
		    };*/
		    //获取所选节点res_code
		    var resCodes='';
		    var parentNodes = $(".l-checkbox-incomplete").parent().parent();
		    for(var n = 0; n<parentNodes.length;n++){
		          resCodes+= tree._getDataNodeByTreeDataIndex(tree.data,$(parentNodes[n]).attr("treedataindex")).pk_id+',';
		    }
		    for(var i = 0; i<childNodes.length; i++){
		       resCodes+= childNodes[i].data.pk_id+',';
		    }
		    resCodes=resCodes.substring(0,resCodes.length-1);
		    var roleId=parent.currWin.gridManager.selected[0].pk_id;
		    $.post(basePath+"rest/RoleAction/updateRoleRes",{"roleCode":roleId,"resCodes":resCodes},function(retObj){
				var dataObj = eval("("+ retObj + ")");
				if (dataObj.success == 'true'){
					parent.currWin.Alert.tip('授权成功！');
						window.parent.currWin.closeDlgAndReload();
				} else {
					parent.currWin.Alert.tip('授权失败！');
				}
			});
      } 
        //取消
     	function colseWindow(){
            parent.currWin.xdlg.close(); 
        }