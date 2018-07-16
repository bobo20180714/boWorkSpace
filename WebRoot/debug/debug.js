//当前监视页面ID
var _curTabId=1;
//创建图元管理器
var resize=new Resize($('#container').data({id:1,debug:1}),{});
//添加拖拽按钮
resize.addButton($('.l-icon-x-clock').attr('draggable',true),{
	pid:1,//图元ID
	name:'图片',//图元名称
	type:1,//0--静态图元 1--动态图元
	comment:'测试',
	w:200,//图元宽度
	h:200//图元高度
});
//加载存储图元数据
var g=getCookie('test');
if(g){
	resize.load(getObject(g));
}