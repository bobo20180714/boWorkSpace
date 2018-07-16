/*
 * 徐老师 2015-5-16
 */
Ext.define("query.util.PreCondition", 
{
	/*
	 * 获取选项卡标题
	 */
	getPageTitle:function(){
		var tab=Ext.getCmp('center_tab');
		return tab?tab.getActiveTab().title:null;
	},
	/*
	 * 查询预制条件时，获取显示曲线的属性信息
	 */
	getStore:function(){
		return this.getLineCmp()?this.getLineCmp().getStore():null;
	},
	/*
	 * 判断曲线的显示方式：true--分栏显示 false--堆叠显示
	 */
	isTile:function(){
		var page=this.getPageTitle();
		switch(page){
			case '图形查询':
			case '图形关联查询':
				return Ext.getCmp('RCGXS').icon.indexOf('cg')<0;
			case '工程值曲线查询':
				return Ext.getCmp('CGXS').icon.indexOf('cg')<0;
			default:
				var name=page.split('-')[0];
				if(name=='图形关联查询'){
					return Ext.getDom(page).contentWindow.Ext.getCmp('RCGXS').icon.indexOf('cg')<0;
				}
				else if(name=='工程值曲线查询'){
					return Ext.getDom(page).contentWindow.Ext.getCmp('CGXS').icon.indexOf('cg')<0;
				}
				return false;
		}
	},
	/*
	 * 获取查询曲线管理对象
	 */
	getLineCmp:function(){
		var page=this.getPageTitle();
		switch(page){
			case '图形查询':
			case '图形关联查询':
				return Ext.getCmp('lineCmpGrid');
			case '工程值曲线查询':
				return Ext.getCmp('baseLineCmpGrid');
			default:
				var name=page.split('-')[0];
				if(name=='图形关联查询'){
					return Ext.getDom(page).contentWindow.Ext.getCmp('lineCmpGrid');
				}
				else if(name=='工程值曲线查询'){
					return Ext.getDom(page).contentWindow.Ext.getCmp('baseLineCmpGrid');
				}
				return null;
		}
	},
	/*
	 * 判断预制条件保存方式 true--相对存储 false--绝对存储
	 */
	isRelativeQuery:function(){
		var page=this.getPageTitle();
		switch(page){
			case '图形查询':
			case '图形关联查询':
				return Ext.getCmp('relation_toolbar_TIMECMP').getValue()!='自定义';
			case '工程值曲线查询':
				return Ext.getCmp('XTB_TIMECMP').getValue()!='自定义';
			default:
				var name=page.split('-')[0];
				if(name=='图形关联查询'){
					return Ext.getDom(page).contentWindow.Ext.getCmp('relation_toolbar_TIMECMP').getValue()!='自定义';
				}
				else if(name=='工程值曲线查询'){
					return Ext.getDom(page).contentWindow.Ext.getCmp('XTB_TIMECMP').getValue()!='自定义';
				}
				return false;
		}
	}
});
