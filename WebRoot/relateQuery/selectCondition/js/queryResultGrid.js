$(function(){
	queryResultArea.showGrid();
});
var queryResultArea={
	queryResultGrid:null,
	showGrid:function(){
		var t = this;
		t.queryResultGrid=$("#queryResultGrid").ligerGrid({
		    columns: [
	  		    { display: '开始时间', name: 'start',align:'left',width:300,mintWidth:200 },
	  		    { display: '结束时间', name: 'end', align:'left',width:300,mintWidth:200  }
	  		],
	  	    rownumbers:true,
	  	    usePager:false
	  	});	
	}
};

	
