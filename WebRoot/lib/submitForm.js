function submitForm(form_name){
		 var mainform = $("#"+form_name);
		 if (mainform.valid())
         {
			 mainform.ajaxSubmit({
				 dataType: 'json',
				 
				 beforeSend: function (a, b, c)
				 {
					
				 },
				 complete: function ()
				 {
					
				 },
				 error: function (result)
				 {
					 Alert.tip('提交数据失败！');
				 },
				 success: function (result)
				 {
                    if (result.success == 'true')
                    {
						//$.ligerDialog.alert('保存数据成功！', '提示', 'success',function () { });
//						parent.$.ligerDialog.alert(result.message, '提示', 'success');
						//parent.currWin.closeDlgAndReload();
                    	Alert.tip("提交数据成功");
                    	return true;
                    } else{
                    	Alert.tip('提交数据失败！'+'<br/><font color="gray">'+result.message+'</font>');
                    	return false;
					}
					
				 }

			 });
		 }
}

function clearForm(form_name){
    $('#'+form_name).find(':input').each(  
    function(){
	  
       switch(this.type){
       case 'passsword': 
       case 'select-multiple':  
       case 'select-one':  
       case 'text':
       case 'textarea': 
		    if(!this.readOnly){
              $(this).val('');  
              break;
	         }
       case 'checkbox':  
       case 'radio': 
            this.checked = false;  
       }
    }     
  );  
}