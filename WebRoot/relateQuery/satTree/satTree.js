/**
 * 卫星树区域对象
 */

var satArea = {
	satTree:null,
	initSatTree : function(){
		var t = this;
		t.satTree = $("#satTree").ligerTree({
			nodeWidth : 150,
			url : basePath + 'rest/satinfoLimit/findgrantusergroupequipmenttree?sys_resource_id=-1',
			checkbox : false,
			idFieldName : 'sys_resource_id',
			parentIDFieldName : 'owner_id',
			textFieldName : 'name',
			isExpand : false,
			slide : false,
			onselect : t.onselects,
			onCancelselect : t.onCancelselects,
			isLeaf : function(data) {
				return data.type == 6;
			},
			delay : function(e) {
				var data = e.data;
				return {
					url : basePath + 'rest/satinfoLimit/findgrantusergroupequipmenttree?sys_resource_id='
							+ data.sys_resource_id
				};
			}
		});
	},
	initSatQuery:function(){
		$("#satKeyInput").ligerTextBox({
			width: 100
		});
	},
	initButtons:function(){
		var t = this;
		$("#satKeySearch").click(function(){	
			t.satTree.clear();
			t.satTree.loadData(
					null, 
					basePath + 'rest/satinfoLimit/findgrantusergroupequipmenttree', 
					{
						sys_resource_id:-1,
						key:liger.get("satKeyInput").getValue()
					});
			t.onCancelselects();
		});
	},
	//获取选定节点的数据
	getSelectedSatData:function(){
		var t = this;
		var satNode = t.satTree.getSelected();
		if(satNode==null){
			return null;
		}
		return satNode.data;
	},
	
	//选择节点
	onselects:function(e){
		//展示参数
		var sys_resource_id = e.data.sys_resource_id;
		tmArea.tmGrid.set('parms', {
			owner_id : sys_resource_id
		});
		tmArea.tmGrid.set({
			newPage : 1
		});
		tmArea.tmGrid.loadData();
		//航天器相关信息展示
		if(showRelateInfoGrid){
			
			jsjgArea.relateGrid.set('parms', {
				sat_id : sys_resource_id
			});
			jsjgArea.relateGrid.loadData();
		}
		
	},
	
	//取消选择
	onCancelselects:function(){
		
		//隐藏参数表格中的数据
		tmArea.tmGrid.set('parms', {
			owner_id : '-1'
		});
		tmArea.tmGrid.set({
			newPage : 1
		});
		tmArea.tmGrid.loadData();
		
		//航天器相关信息展示
		if(showRelateInfoGrid){
			//隐藏航天器相关信息
			jsjgArea.relateGrid.set('parms', {
				sat_id : '-1'
			});
			jsjgArea.relateGrid.loadData();
		}
	}
};
