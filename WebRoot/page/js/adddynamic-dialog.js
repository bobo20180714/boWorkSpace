function save(){

		var name=$('[name=name]').val().trim();
		if(name==''){
			parent.Alert.tip('图元名称不能空！');
			return;
		}
		if(isEdit==0){
			if($('#file1')[0].files.length==0){
				parent.Alert.tip('图标不能空！');
				return;
			}
			if($('#file2')[0].files.length==0){
				parent.Alert.tip('JS包不能空！');
				return;
			}
			if(!/.jar$/.test($('#file2')[0].files[0].name)){
				parent.Alert.tip('JS包不是JAR文件！');
				return;
			}
		}		
		var form={
				name:name,
				comment:$('[name=comment]').val().trim(),
				state:1
			};
		var formData=new FormData($('form')[0]);
		if(isEdit) {
			formData.append('id',data.id);
			formData.append('icon',data.icon);
		}
		else{
			formData.append('icon',uuid());
		}
	     $.ajax({  
	          url: isEdit?'../rest/dynamic/editDynamic':'../rest/dynamic/addDynamic',  
	          type: 'POST',  
	          data: formData,  
	          processData: false,
	          contentType:false,
	          success: function (ret) {
	        	  if(ret=="F"){
	        		  if(isEdit){
		        		  parent.Alert.tip('编辑图元失败！');
	        		  }else{
		        		  parent.Alert.tip('添加图元失败！');
	        		  }
	        	  }   
	        	  else{
	        		  if(isEdit){
		        		  parent.Alert.tip('编辑图元成功！');
	        		  }else{
		        		  parent.Alert.tip('添加图元成功！');
	        		  }
	        		  parent.dynamicGrid.loadServerData({
	  					  url:'rest/dynamic/getDynamic'
	  				  });
	        		  parent._opener.close();
	        	  }
	          }
	     });
	}