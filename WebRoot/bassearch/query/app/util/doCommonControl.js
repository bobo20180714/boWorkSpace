Ext.define("query.util.doCommonControl", {
	// 初始化窗口
	doAdd : function(win, url) {
		var w = Ext.create(win);
		w.url = url;
		if (w) {
			w.show();
		}
	},
	// 数据保存
	doSave : function(datagrid, form, win) {
		if (!datagrid) {
			Ext.example.msg('系统提示', "非datagrid,不能执行操作!");
			return;
		}
		if (form.isValid()) {
			form.submit({
				url : win.url,
				waitMsg : '请稍后',
				waitTitle : '正在进行数据交互...',
				success : function(form, action) {
					if(action.result.success){
						win.close();
						datagrid.getStore().loadPage(1);
					}
					Ext.example.msg('系统提示', action.result.message);
				},
				failure : function(form, action) {
					Ext.example.msg('系统提示', "数据操作失败!");
				}
			});
		}
	},
	// 表单加载数据
	doEdit : function(win, datagrid, url) {
		if (!datagrid) {
			Ext.example.msg('系统提示', "非datagrid,不能执行操作!");
			return;
		}
		var records = datagrid.getSelectionModel().getSelection();
		if (records.length != 1) {
			Ext.example.msg('系统提示', "请选择一条需要修改的数据!");
			return;
		}
		var w = Ext.create(win);
		w.down('form').getForm().loadRecord(records[0]);
		w.url = url;
		w.show();
	},
	// 数据删除
	doDelete : function(datagrid, url, id) {
		if (!datagrid) {
			Ext.example.msg('系统提示', "非datagrid,不能执行操作!");
			return;
		}
		// 得到数据集合
		var records = datagrid.getSelectionModel().getSelection();
		var data = [];
		Ext.Array.each(records, function(model) {
			data.push(model.get(id));
		});
		if (data.length == 0) {
			Ext.example.msg('系统提示', "请选择一条需要删除的数据!");
			return;
		}
		var el = datagrid.getEl();
		Ext.Msg.confirm("操作确认","是否确认进行该操作?",function(v){
			if(v=='yes'){
				el.mask("正在数据交互中……");
				Ajax.send(url,{
					ids : "'"+data.join("','")+"'"
				}, function (obj, opts) {
					if(obj.success){
						datagrid.getStore().loadPage(1);
						el.unmask();
					}
					Ext.example.msg('系统提示', obj.message);
			    });
			}
		});
	},
	//更新数据
	doRefresh : function(datagrid, url, id) {
		if (!datagrid) {
			Ext.example.msg('系统提示', "非datagrid,不能执行操作!");
			return;
		}
		// 得到数据集合
		var records = datagrid.getSelectionModel().getSelection();
		var data = [];
		Ext.Array.each(records, function(model) {
			data.push(model.get(id));
		});
		if (data.length == 0) {
			Ext.example.msg('系统提示', "请选择一条需要更新的数据!");
			return;
		}
		var el = datagrid.getEl();
		Ext.Msg.confirm("操作确认","是否确认进行该操作?",function(v){
			if(v=='yes'){
				el.mask("正在数据交互中……");
				Ajax.send(url,{
					engine_id : records[0].get('engine_id'),
					engine_class : records[0].get('engine_class'),
					engine_name : records[0].get('engine_name'),
					engine_comment : records[0].get('engine_comment'),
					engine_version : records[0].get('engine_version'),
					create_user : records[0].get('create_user'),
					create_user_name : records[0].get('create_user_name'),
					create_time : records[0].get('create_user_name')
				}, function (obj, opts) {
					if(obj.success){
						datagrid.getStore().loadPage(1);
					}
					el.unmask();
					Ext.example.msg('系统提示', obj.message);
			    });
			}
		});
	},
	// 数据查询
	doSearch : function(datagrid, form) {
		if (!datagrid) {
			Ext.example.msg('系统提示', "非datagrid,不能执行操作!");
			return;
		}
		datagrid.getStore().addListener({
			beforeload : function(store, records, options) {
				Ext.apply(store.proxy.extraParams, form.getValues());
			}
		});
		datagrid.getStore().load();

	},
	doClearSearch : function(datagrid, form) {
		if (!datagrid) {
			Ext.example.msg('系统提示', "非datagrid,不能执行操作!");
			return;
		}
		form.reset();
		datagrid.getStore().addListener({
			beforeload : function(store, records, options) {
				Ext.apply(store.proxy.extraParams, form.getValues());
			}
		});
		datagrid.getStore().load();
	},
	/**************************************************************************************
	 * ******************************            tree           **************************************
	 ***************************************************************************************/
	doSaveTree : function(tree, form, win) {
		if (!tree) {
			Ext.example.msg('系统提示', "非tree,不能执行操作!");
			return;
		}
		if (form.isValid()) {
			form.submit({
				url : win.url,
				waitMsg : '请稍后',
				waitTitle : '正在进行数据交互...',
				success : function(form, action) {
					if(action.result.success){
						win.close();
						tree.getStore().load();
					}
					Ext.example.msg('系统提示', action.result.message);
				},
				failure : function(form, action) {
					Ext.example.msg('系统提示', "数据操作失败!");
				}
			});
		}
	},
	// 表单加载数据
	doEditTree : function(win, tree, url) {
		if (!tree) {
			Ext.example.msg('系统提示', "非tree,不能执行操作!");
			return;
		}
		var records = tree.getSelectionModel().getSelection();
		if (records.length != 1) {
			Ext.example.msg('系统提示', "请选择一条需要修改的数据!");
			return;
		}
		var w = Ext.create(win);
		w.down('form').getForm().loadRecord(records[0]);
		w.url = url;
		w.show();
	},
	// 数据删除
	doDeleteTree : function(tree, url, id) {
		if (!datagrid) {
			Ext.example.msg('系统提示', "非datagrid,不能执行操作!");
			return;
		}
		var records = tree.getSelectionModel().getSelection();
		var data = [];
		Ext.Array.each(records, function(model) {
			data.push(model.get(id));
		});
		if (data.length == 0) {
			Ext.example.msg('系统提示', "请选择一条需要删除的数据!");
			return;
		}
		var el = tree.getEl();
		el.mask("正在数据交互中……");
		Ajax.send(url,{
			ids : "'"+data.join("','")+"'"
		}, function (obj, opts) {
			if(obj.success){
				datagrid.getStore().loadPage(1);
				el.unmask();
			}
			Ext.example.msg('系统提示', obj.message);
	    });
	},
	listenerCheck:function(node,checked){
		this.childHasChecked(node,checked);
		if(node.parentNode) this.parentCheck(node.parentNode);
	},
	parentCheck:function(node){
		if(node.get("id")=='root') return;
		var childNodes=node.childNodes;
		var flag=false;
		for(var i=0;i<childNodes.length;i++){
			if(childNodes[i].get("checked")) flag=true;
		}
		if(flag){
			node.set("checked",true);
		}else{
			node.set("checked",false);
		}
		if(node.parentNode) this.parentCheck(node.parentNode);
	},
	childHasChecked:function(node,checked){
		node.cascadeBy(function(child){
			child.set("checked",checked);
		});
	}
});
