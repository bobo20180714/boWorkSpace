function submitForms(){ 
	var sendData = {'userAccount':$.trim(($("#name").val())),
		     'password':$("#password").val()};
    $.ajax({
    	url:basePath+"rest/poplarLogin/loginUser",
		data:sendData,
		async:false,
		complete: function(data) {
			var retData = JSON2.parse(data.responseText);
		    if(retData.success == "true"){
				window.location.href =basePath + 'index/index.jsp';
			} else{
				Alert.tip(retData.message);
			}
	    }
    });
} 

document.onkeydown=keyDownSearch;  
function keyDownSearch(e) {    
    // 兼容FF和IE和Opera    
    var theEvent = e || window.event;    
    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
   	if (code == 13) {    
   		submitForms();
	}
}  