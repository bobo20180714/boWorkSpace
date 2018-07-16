/**
 * 所有提示信息的工具类
 */
var Alert={
		error:function(title_,content_,onBtnClick){
			$.ligerDialog.error(content, title, onBtnClick);
		},
		warn:function(title_,content_,onBtnClick){
			$.ligerDialog.warn(content, title, onBtnClick);
		},
		success:function(title_,content_,onBtnClick){
			$.ligerDialog.success(content, title, onBtnClick);
		},
		question:function(title_,content_){
			$.ligerDialog.question(content, title);
		},
		tip:function(content_){
			var tip_=$.ligerDialog.tip({title : '提示',content : content_});
			window.setTimeout(function(){
				tip_.close();
			}, 3000);
		}
};