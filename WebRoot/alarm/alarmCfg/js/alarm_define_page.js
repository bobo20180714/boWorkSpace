 $(function ()
 { 
	 var dataGrid1 = [
	                 { id: 1, SatId: 'BD1001', SatName: '北斗1号01星' },
	                 { id: 2, SatId: 'BD1002', SatName: '北斗1号02星' },
	                 { id: 3, SatId: 'FH1002', SatName: '烽火1号02星' },
	                 { id: 4, SatId: 'ZY2001', SatName: '资源2号01星' }
	                 ];
	 var dataGrid2 =[];
	 
	 var dataGridColumns = [
	                 { header: 'ID', name: 'id', width: 20 },
	                 { header: '卫星代号', name: 'SatId' },
	                 { header: '卫星名称', name: 'SatName' }
	             ];
     $("#listboxSatInfo").ligerListBox({
         data: dataGrid1,
         textField: 'SatId',
         columns: dataGridColumns,
         isMultiSelect: true,
         isShowCheckBox: true,
         width: 200,height:340
     });
     
     $("#listboxSelectedSatInfo").ligerListBox({
         data: dataGrid2,
         textField: 'SatId',
         columns: dataGridColumns,
         isMultiSelect: true,
         isShowCheckBox: true,
         width: 200,height:340
     });

     
     //liger.get("listbox1").setData(data);
 });
 
 //增加选择的卫星信息
 function moveToRight()
 {
     var box1 = liger.get("listboxSatInfo"), box2 = liger.get("listboxSelectedSatInfo");
     var selecteds = box1.getSelectedItems();
     if (!selecteds || !selecteds.length){
    	 return;
     }
     box1.removeItems(selecteds);
     box2.addItems(selecteds);
 }
 
 //删除选中的卫星信息
 function moveToLeft()
 {
	 var box1 = liger.get("listboxSatInfo"), box2 = liger.get("listboxSelectedSatInfo");
     var selecteds = box2.getSelectedItems();
     if (!selecteds || !selecteds.length){
    	 return;
     }
     box2.removeItems(selecteds);
     box1.addItems(selecteds);
 }
 
 //删除所有选中的卫星信息
 function moveAllToLeft()
 { 
	 var box1 = liger.get("listboxSatInfo"), box2 = liger.get("listboxSelectedSatInfo");
     var selecteds = box2.data;
     if (!selecteds || !selecteds.length){
    	 return;
     }
     box1.addItems(selecteds);
     box2.removeItems(selecteds); 
 }
 