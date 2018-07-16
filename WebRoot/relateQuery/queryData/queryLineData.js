var queryLineData={
	getdrawdata:function(type,width,tmId,begin,end,Begins,tiye,dataType,mid,start){
		var flagData = null;
		$.ajax({
			url:basePath+'rest/drawdata/getdrawdata',
			data:{"type":type,"width":width,"tmId":tmId,"begin":begin+":00.000","end":end+":00.000",
				"Begins":Begins,"tiye":tiye,"dataType":dataType,"mid":mid,"start":start},
			async:false,
			success:function(data){
				var rsData = eval('('+data+')');
				flagData = rsData.drawData;
			}
		});
		return flagData;
	}
};