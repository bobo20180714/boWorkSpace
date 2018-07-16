
$(function(){
	initFieldGrid();
});

//初始化字段信息表格
function initFieldGrid() {
	fieldGridObj = $("#fieldGrid").ligerGrid({
		columns : [
		           {
		        	   display : '字段名称',
		        	   name : 'field_name',
		        	   align : 'center',
		        	   width : 120
		           },
		           {
		        	   display : '字段类型',
		        	   name : 'field_type',
		        	   align : 'center',
		        	   width : 100,
		        	   render:function(item){
		        		   if(item.field_type == 1){
		        			   return '整型';
		        		   }else if(item.field_type == 2){
		        			   return '浮点型';
		        		   }else if(item.field_type == 3){
		        			   return '字符串';
		        		   }
		        	   }
		           },
		           {
		        	   display : "字段长度",
		        	   name : "field_length",
		        	   align : 'center',
		        	   width : 100
		           },
		           {
		        	   display : "字段精度",
		        	   name : "fiel_dscale",
		        	   align : 'center',
		        	   width : 100
		           },
		           {
		        	   display : "字段说明",
		        	   name : "field_comment",
		        	   align : 'center',
		        	   width : 130,
		        	   render:function(item){
		        		   if(item.field_comment){
		        			   return "<p title='"+item.field_comment+"'>"+item.field_comment+"</p>";
		        		   }
		        	   }
		           }
		           ],
		           height:"99.9%",
		           rownumbers : true,
		           checkbox : false,
		           isSingleCheck:true,
		           rowHeight : 27,
		           frozen : false ,
		           url:basePath + 'rest/field/list?fid='+fid
	});
}