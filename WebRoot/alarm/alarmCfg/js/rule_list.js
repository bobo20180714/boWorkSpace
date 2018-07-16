//tab页对象
var tab = null;
$(function () {
	//布局
	$("#layout1").ligerLayout({height: '100%',space:4, onHeightChanged: f_heightChanged });
	var height = $(".l-layout-center").height();
	//Tab
	$("#framecenter").ligerTab({ height: height });
	//tab页对象
    tab = $("#framecenter").ligerGetTabManager();
    if(rspType == "2"){
    	tab.selectTabItem("stateRule2");
    }
});

/**
 * 窗口变化事件用于高度调整
 */
function f_heightChanged(options) {
    if (tab) {
        tab.addHeight(options.diff);
    }
}

function f_addTab(tabid,text, url)
{ 
    tab.addTabItem({ tabid : tabid,text: text, url: url });
} 