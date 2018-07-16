var formdata = null;
var org_id;
$(function (){
	initForm();
    $("#txt2").ligerComboBox({
        width: 180,
        selectBoxWidth: 200,
        selectBoxHeight: 200, valueField: 'text',treeLeafOnly:false,
        tree: {
			isExpand: true,
			onExpand:onexpand,
			textFieldName:'org_name',
			idFieldName : 'id',
			checkbox:false,
			parentIDFieldName : 'parent_id',
			height: "100%",
			nodeWidth:100,
			url:basePath+'rest/orguser/findorgtree?parent_id=-1',
			isLeaf : function(data){
				return data.type == 6;
			},
			delay: function(e)
			{
			     var data = e.data;
			     return { url: basePath+'rest/orguser/findorgtree?parent_id=' + data.id };
			 },
			 onClick:function(data,target){
//				 alert(JSON.stringify(data.data.id));
				 org_id=data.data.id;
			 }
			}
    });
});

function initForm(){
	formData = $("#form4").ligerForm({
		width:320,
    	labelAlign:'center',
        inputWidth: 180, 
        labelWidth: 80, 
        space: 20,
        validate:true,//验证
        fields: [
			{ display: "组织机构", name: "org_name",id:"org_name",type: "select",
				valueField: 'id',
				textField: 'org_name',
//				selectBoxHeight:60,
				tree:{
					isExpand: true,
					onExpand:onexpand,
					textFieldName:'org_name',
					idFieldName : 'id',
					checkbox:false,
					parentIDFieldName : 'parent_id',
//					height: 30,
					nodeWidth:100,
					url:basePath+'rest/orguser/findorgtree?parent_id=-1',
					isLeaf : function(data){
						return data.type == 6;
					},
					delay: function(e)
					{
					     var data = e.data;
					     return { url: basePath+'rest/orguser/findorgtree?parent_id=' + data.id };
					 },
					 onClick:function(data,target){
//						 alert(JSON.stringify(data.data.id));
						 org_id=data.data.id;
					 }
					}
			}
        ]
    }); 
	
}

function onexpand(data,target){
	tree_manager = $("#tree1");
//	alert(data);
	$.ajax({
		url:basePath+'rest/orguser/findorgtree?parent_id=' + data.id,
		success:function(data){
			var nodes=eval('('+data+')');
			tree_manager.clear();
			tree_manager.setData(nodes);
		}
	});
}

/**
*
*获取url里的参数
*@param 参数名
*/
function getParam(name){

	var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)","i");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null){
		return unescape(r[2]);
	}
	return null;

}

//页面提交
function submitForm(){
	var ids = getParam("ids");
	var data = formData.getData();
//	if(check(data)){
	//数据提交
	data.org_id = org_id;
	data.ids=getParam("ids");
	$.post(basePath+"rest/orguser/userorgchange",data, 
			function(result,textStatus) {
		if (result.success == "true") {
			parent.currWin.Alert.tip(result.message);
			parent.currWin.f_reload();
			parent.currWin.f_closeDlg();
		} else {
			parent.currWin.Alert.tip(result.message);
			parent.currWin.closeDlgAndReload();
		}
	}, "json");
//	}
}

