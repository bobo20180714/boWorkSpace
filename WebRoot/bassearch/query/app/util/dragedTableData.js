/************************************ dragedTableData.js *******************************/
/**
 * ********************************** dragedTableData.js
 * ******************************
 */
/*--------全局变量-----------*/
var dragedTable_x0, dragedTable_y0, dragedTable_x1, dragedTable_y1;
var dragedTable_movable = false;
var dragedTable_preCell = null;
var dragedTable_preCell_next ="" ;
var dragedTable_normalColor = null;
// 起始单元格的颜色
var dragedTable_preColor = "lavender";
// 目标单元格的颜色
var dragedTable_endColor = "#FFCCFF";
var dragedTable_movedDiv = "dragedTable_movedDiv";
var srcTable=null;
var tag;
function DragedTable(tableId,num) {
	dragedTable_tableId = tableId;
	var dragedTable_movedDivObj = document.getElementById("dragedTable_movedDiv");
	if(!dragedTable_movedDivObj){
		var oTempDiv = document.createElement("div");
		oTempDiv.id = dragedTable_movedDiv;
		oTempDiv.onselectstart = function() {
			return false;
		};
		oTempDiv.style.cursor = "pointer";
		oTempDiv.style.position = "absolute";
		oTempDiv.style.border = "1px solid black";
		oTempDiv.style.backgroundColor = dragedTable_endColor;
		oTempDiv.style.display = "none";
		oTempDiv.tmName="";
		oTempDiv.code="";
		oTempDiv.tmNum="";
		oTempDiv.tmId="";
		document.body.appendChild(oTempDiv);
	}
	var oTable = document.getElementById(tableId+num);
	oTable.onmousedown = showDiv;
	oTable.onmouseover=function(){
		for ( var i = 1; i < oTable.rows.length; i++){
			for(var j = 0;j<oTable.rows[i].cells.length;j+=2){
				oTable.rows[i].cells[j].style.cursor="pointer";
			}
		}
	};
	document.onmouseup = function() {
		var event=arguments[0] || window.event;
		hideDiv(event);
		for ( var i = 1; i < oTable.rows.length; i++){
			for(var j = 0;j<oTable.rows[i].cells.length;j++){
				if(j%2==0){
					oTable.rows[i].cells[j].style.backgroundColor = dragedTable_normalColor;
				}
			}
		}
	};
	oTable.onmousemove = function() {
		var event=arguments[0] || window.event;
		dragDiv(event);
	};
}

// 得到控件的绝对位置
function getPos(cell) {
	var pos = new Array();
	var t = cell.offsetTop;
	var l = cell.offsetLeft;
	while (cell = cell.offsetParent) {
		t += cell.offsetTop;
		l += cell.offsetLeft;
	}
	pos[0] = t;
	pos[1] = l;
	return pos;
}

// 显示图层
function showDiv() {
	var evt=arguments[0] || window.event;
	var obj = evt.srcElement||evt.target;
	var pos = new Array();
	// 获取过度图层
	var oDiv = document.getElementById(dragedTable_movedDiv);
	srcTable = obj.parentNode.parentNode;
	if (obj.tagName.toLowerCase() == "td" && obj.parentNode.rowIndex!=0 && obj.cellIndex%2==0) {
		obj.style.cursor = "pointer";
		pos = getPos(obj);
		// 计算中间过度层位置，赋值
		
		oDiv.style.width = obj.offsetWidth;
		oDiv.style.height = obj.offsetHeight;
		oDiv.style.top = pos[0];
		oDiv.style.left = pos[1];
		oDiv.innerHTML = obj.innerHTML;
		oDiv.tmName=obj.tmName;
		oDiv.code=obj.code;
		oDiv.tmNum=obj.tmNum;
		oDiv.tmId=obj.tmId;
		oDiv.style.display = "";
		dragedTable_x0 = pos[1];
		dragedTable_y0 = pos[0];
		var event=arguments[0] || window.event;
		dragedTable_x1 = event.clientX;
		dragedTable_y1 = event.clientY;
		// 记住原td
		dragedTable_normalColor = obj.style.backgroundColor;
		obj.style.backgroundColor = dragedTable_preColor;
		dragedTable_preCell = obj;
        dragedTable_preCell_next = obj.nextSibling.innerHTML;
		dragedTable_movable = true;
	}
}
function dragDiv(event) {
	if (dragedTable_movable) {
		var oDiv = document.getElementById(dragedTable_movedDiv);
		var pos = new Array();
		oDiv.style.top = event.clientY - dragedTable_y1 + dragedTable_y0;
		oDiv.style.left = event.clientX - dragedTable_x1 + dragedTable_x0;
		var oTable = srcTable;
		var mx = event.x||event.pageX;
		var my = event.y||event.pageY;
		for ( var i = 1; i < oTable.rows.length; i++) {
			for(var j = 0;j<oTable.rows[i].cells.length;j++){
			if (oTable.rows[i].cells[j].tagName.toLowerCase() == "td" && j%2==0) {
				pos = getPos(oTable.rows[i].cells[j]);
				if (mx > pos[1]
						&& mx < pos[1] + oTable.rows[i].cells[j].offsetWidth
						&& my> pos[0]
						&& my < pos[0] + oTable.rows[i].cells[j].offsetHeight) {
					if (oTable.rows[i].cells[j] != dragedTable_preCell){
						oTable.rows[i].cells[j].style.backgroundColor = dragedTable_endColor;
					}
						
				} else {
					if (oTable.rows[i].cells[j] != dragedTable_preCell)
						oTable.rows[i].cells[j].style.backgroundColor = dragedTable_normalColor;
				}
			}
		}
		}
	}
}

function hideDiv(event) {
	if (dragedTable_movable) {
		var oTable = srcTable;
		var pos = new Array();
		if (dragedTable_preCell != null) {
			var mx = event.x||event.pageX;
			var my = event.y||event.pageY;
			for ( var i = 1; i < oTable.rows.length; i++) {
				for(var j = 0;j<oTable.rows[i].cells.length;j++){
				pos = getPos(oTable.rows[i].cells[j]);
				// 计算鼠标位置，是否在某个单元格的范围之内
				if (mx  > pos[1]
						&& mx  < pos[1] + oTable.rows[i].cells[j].offsetWidth
						&& my> pos[0]
						&& my < pos[0] + oTable.rows[i].cells[j].offsetHeight) {
					if (oTable.rows[i].cells[j].tagName.toLowerCase() == "td" && j%2==0) {
						// 交换文本
						dragedTable_preCell.innerHTML = oTable.rows[i].cells[j].innerHTML;
						dragedTable_preCell.tmName=oTable.rows[i].cells[j].tmName;
						dragedTable_preCell.code=oTable.rows[i].cells[j].code;
						dragedTable_preCell.tmNum=oTable.rows[i].cells[j].tmNum;
						dragedTable_preCell.tmId=oTable.rows[i].cells[j].tmId;
						oTable.rows[i].cells[j].innerHTML = document.getElementById(dragedTable_movedDiv).innerHTML;
						oTable.rows[i].cells[j].tmName=document.getElementById(dragedTable_movedDiv).tmName;
						oTable.rows[i].cells[j].code=document.getElementById(dragedTable_movedDiv).code;
						oTable.rows[i].cells[j].tmNum=document.getElementById(dragedTable_movedDiv).tmNum;
						oTable.rows[i].cells[j].tmId=document.getElementById(dragedTable_movedDiv).tmId;
						
						dragedTable_preCell.nextSibling.innerHTML = oTable.rows[i].cells[j+1].innerHTML;
						oTable.rows[i].cells[j+1].innerHTML = dragedTable_preCell_next;
						// 清除原单元格和目标单元格的样式
						dragedTable_preCell.style.backgroundColor = dragedTable_normalColor;
						oTable.rows[i].cells[j].style.backgroundColor = dragedTable_normalColor;
						oTable.rows[i].cells[j].style.cursor = "";
						dragedTable_preCell.style.cursor = "";
						dragedTable_preCell.style.backgroundColor = dragedTable_normalColor;
					}else{
						dragedTable_preCell.style.backgroundColor = dragedTable_normalColor;
					}
				}else{
					dragedTable_preCell.style.backgroundColor = dragedTable_normalColor;
				}
			}
			}
		}
		dragedTable_movable = false;
		// 清除提示图层
		document.getElementById(dragedTable_movedDiv).style.display = "none";
	}
}
