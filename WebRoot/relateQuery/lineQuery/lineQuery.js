var conditionWin = null;
var timeTypeArr = [
                   { text: '自定义', id: '1' },
                   { text: '最近1分钟', id: '2' },
                   { text: '最近5分钟', id: '3' },
                   { text: '最近10分钟', id: '4' },
                   { text: '最近15分钟', id: '5' },
                   { text: '最近30分钟', id: '6' },
                   { text: '最近1小时', id: '7' },
                   { text: '最近2小时', id: '8' },
                   { text: '最近4小时', id: '9' },
                   { text: '最近8小时', id: '10' },
                   { text: '最近12小时', id: '11' },
                   { text: '最近1天', id: '12' },
                   { text: '最近2天', id: '13' },
                   { text: '最近1周', id: '14' },
                   { text: '最近2周', id: '15' },
                   { text: '最近1个月', id: '16' },
                   { text: '最近2个月', id: '17' },
                   { text: '最近3个月', id: '18' },
                   { text: '最近1年', id: '19' }
               ];
//曲线查询区域
 
var lineQueryArea={
	toolBar:null,
	
	showtoolBar:function(){
		var t=this;
	    t.toolBar=$("#lineToolbar").ligerToolBar({ items: [
            {
            	editor:{
            		tempId:"timeType",
            		typeTemp:"select",
            		data:timeTypeArr,
                    selectBoxWidth: 80,//宽度
            		value:1,
            		onSelected:t.select,
                    width:80
            	}
            },
           {
            	editor:{
            		tempId:"start",
            		showTime: true,
            		typeTemp:"date",
            		value:start,
            		onChangeDate:t.changeStartDate,
            		width:140
            	}
            },
            {
            	text:'到'
            },
            {
            	editor:{
            		tempId:"end",
            		showTime: true,
            		typeTemp:"date",
            		value:time,
            		onChangeDate:t.changeEndDate,
            		width:140
            	}
            },
            {
            	id : 'refresh',
                click:itemclick,
                img:basePath + 'relateQuery/img/pic/page-refresh.gif'
            },
            { line:true },
            {
            	id : 'bcx',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/bcx.ico'
            },
            {
            	id : 'bhf',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/bhf.ico'
            },
            { line:true },
            {
            	id : 'cg',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/cg.ico'
	        },
	        {
	        	id : 'discnn',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/discnn.ico'
	        },
	        { line:true },
	        {
	        	id : 'tiaojian',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/tiaojian.ico'
	        },
	        {
	        	id : 'bnoshield',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/bnoshield.png'
	        },
	        { line:true },
	        {
	        	id : 'tiye',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/tiye.ico'
	        },
	        
	        { line:true },
	        {
	        	id : 'zsy',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/zsy.ico'
	        },
	        
	        {
	        	id : 'xy',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/xy.ico'
	        },
	        {
	        	id : 'fd',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/fd.ico'
	        },
	        {
	        	id : 'sx',
                click:itemclick,
	            img:basePath + '/relateQuery/img/pic/sx.ico'
	        },
	        { line:true },
	        {
	        	id : 'zsy0',
                click:itemclick,
	            img:basePath + '/relateQuery/img/pic/zsy0.ico'
	        },
	        {
	        	id : 'sy0',
                click:itemclick,
	            img:basePath + '/relateQuery/img/pic/sy0.ico'
	        },
	        
	        {
	        	id : 'xy0',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/xy0.ico'
	        },
	        {
	        	id : 'fd0',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/fd0.ico'
	        },
	        {
	        	id : 'sy0',
                click:itemclick,
	            img:basePath + '/relateQuery/img/pic/sy0.ico'
	        },
	        { line:true },
	        {
	        	id : 'tag',
                click:itemclick,
	            img:basePath + '/relateQuery/img/pic/tag.ico'
	        },
	        {
	        	id : 'tongji',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/tongji.ico'
	        },
	        { line:true },
	        {
	        	id : 'dy',
                click:itemclick,
                img:basePath + '/relateQuery/img/pic/dy.ico'
	        }]
    
         });
	},
	select:function(id){
		startComb = $("#comb_start").ligerGetDateEditorManager();
		var endComb = $("#comb_end").ligerGetDateEditorManager();
		var time=new Date();
		var start=null;
		switch (id) {
		case "1":
			var startTemp = time - 2*60*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "2":
			var startTemp = time - 1*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "3":
			var startTemp = time - 5*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			
			break;
		case "4":
			var startTemp = time - 10*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "5":
			var startTemp = time - 15*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "6":
			var startTemp = time - 30*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "7":
			var startTemp = time - 1*60*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "8":
			var startTemp = time - 2*60*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "9":
			var startTemp = time - 4*60*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "10":
			var startTemp = time - 8*60*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "11":
			var startTemp = time - 12*60*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "12":
			var startTemp = time - 24*60*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "13":
			var startTemp = time - 2*24*60*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "14":
			var startTemp = time - 7*24*60*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "15":
			var startTemp = time - 14*24*60*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "16":
			var startTemp = time - 30*24*60*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "17":
			var startTemp = time - 2*30*24*60*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "18":
			var startTemp = time - 3*30*24*60*60*1000;
			start = new Date(startTemp);	
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
			break;
		case "19":
			var startTemp = time - 365*24*60*60*1000;
			start = new Date(startTemp);
			$("#comb_start").val(startComb.getFormatDate(start));
			$("#comb_end").val(endComb.getFormatDate(time));
		};
		lineQueryArea.changeStartDate();
		lineQueryArea.changeEndDate();
	},
	changeStartDate:function(){
		var start=$("#comb_start").val();
		var rows=linearManageArea.linearGrid.rows;
		for(var i=0;i<rows.length;i++){
			linearManageArea.linearGrid.updateCell("Begin",start,rows[i]);
		}
	},
	changeEndDate:function(){
		var end=$("#comb_end").val();
		var rows=linearManageArea.linearGrid.rows;
		for(var i=0;i<rows.length;i++){
			linearManageArea.linearGrid.updateCell("End",end,rows[i]);
		}

	}
};
 
//工具条点击事件
function itemclick(item){
	if (item.id) {
		switch (item.id) {
			case "tiaojian":
				if(conditionWin != null){
					conditionWin.show();
					return;
				}
				var startTime = $("#comb_start").val();
				var endTime=$("#comb_end").val();
				setCurrWindows2();
				conditionWin = parent.$.ligerDialog.open({
					title : '关联条件',
					width : 680,
					height : 500,
					url : basePath+'relateQuery/selectCondition/GraphicalIn.jsp?start='+startTime+'&end='+endTime
					
				});
				break;
			case "tongji":
				
				break;
		}
	}
}

//选择关联条件使用
function setCurrWindows2() {
	parent.currWin = window;
}
