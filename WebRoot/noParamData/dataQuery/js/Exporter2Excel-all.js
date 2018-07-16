var getExcelUrl = (function() {
		//Base64编码转换函数，用法Base64.encode(string)
	var Base64 = (function() {
		// private property
		var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
		// private method for UTF-8 encoding
		function utf8Encode(string) {
			string = string.replace(/\r\n/g, "\n");
			var utftext = "";
			for (var n = 0; n < string.length; n++) {
				var c = string.charCodeAt(n);
				if (c < 128) {
					utftext += String.fromCharCode(c);
				} else if ((c > 127) && (c < 2048)) {
					utftext += String.fromCharCode((c >> 6) | 192);
					utftext += String.fromCharCode((c & 63) | 128);
				} else {
					utftext += String.fromCharCode((c >> 12) | 224);
					utftext += String.fromCharCode(((c >> 6) & 63) | 128);
					utftext += String.fromCharCode((c & 63) | 128);
				}
			}
			return utftext;
		}

		// public method for encoding
		return {
			// This was the original line, which tries to use Firefox's built in
			// Base64 encoder, but this kept throwing exceptions....
			// encode : (typeof btoa == 'function') ? function(input) { return
			// btoa(input); } : function (input) {

			encode : function(input) {
				var output = "";
				var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
				var i = 0;
				input = utf8Encode(input);
				while (i < input.length) {
					chr1 = input.charCodeAt(i++);
					chr2 = input.charCodeAt(i++);
					chr3 = input.charCodeAt(i++);
					enc1 = chr1 >> 2;
					enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
					enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
					enc4 = chr3 & 63;
					if (isNaN(chr2)) {
						enc3 = enc4 = 64;
					} else if (isNaN(chr3)) {
						enc4 = 64;
					}
					output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
							+ keyStr.charAt(enc3) + keyStr.charAt(enc4);
				}
				return output;
			}
		};
	})();
	var getType = (function() {
		return {
			getType : function(value) {
				var type = typeof(value);
				var result = "";
				switch (type) {
					case "number" :
						result = "Number";
						break;
					case "int" :
						result = "Number";
						break;
					case "float" :
						result = "Number";
						break;
					case "bool" :
					case "boolean" :
						result = "String";
						break;
					case "date" :
						result = "DateTime";
						break;
					default :
						result = "String";
						break;
				}
				return result;
			}
		};
	})();
	var getClass = (function() {
		return {
			getClass : function(value) {
				var type = typeof(value);
				var result = "";
				switch (type) {
					case "number" :
						result = "float";
						break;
					case "int" :
						result = "int";
						break;
					case "float" :
						result = "float";
						break;
					case "bool" :
					case "boolean" :
						result = "";
						break;
					case "date" :
						result = "date";
						break;
					default :
						result = "";
						break;
				}
				return result;
			}
		};
	})();
	var getValue = (function() {
		return {
			getValue : function(temp,value,cellClass) {	
				temp += '<ss:Cell ss:StyleID="' + cellClass+ getClass.getClass(value)+ '"><ss:Data ss:Type="'+ getType.getType(value) + '">';
				temp += value;
				temp += '</ss:Data></ss:Cell>';
				return temp;
			}
		};
	})();
	var storeToXml = (function() {
		return {
			storeToXml : function(grid, title, sql) {
				var gridData = grid.rows;
				var columns = grid.columns;
				var temp = '';
				var temp = '<ss:Worksheet ss:Name="'+title+'">';
				var headerXml = '<ss:Cell ss:StyleID="headercell" ss:MergeAcross="'+ (columns.length - 2)	+ '">'
						+ '<ss:Data ss:Type="String">'	+ title+ '</ss:Data>'
						+ '<ss:NamedCell ss:Name="Print_Titles" />'
						+ '</ss:Cell>';
				temp += '<ss:Table ss:DefaultColumnWidth="150">' + '<ss:Column ss:AutoFitWidth="1"/>'
						+'<ss:Row ss:AutoFitHeight="1">' + headerXml
						+ '</ss:Row>';
				
				temp += '<ss:Row>';
				for (var k = 0; k < columns.length; k++) {
					if(columns[k].display == null){
						continue;
					}
					temp += '<ss:Cell  ss:StyleID="headercell"><ss:Data ss:Type="String">';
					temp += columns[k].display;
					temp += '</ss:Data></ss:Cell>';
				}
				temp += '</ss:Row>';

				for (var i = 0; i < gridData.length; i++) {
					var cellClass = (i & 1) ? 'odd' : 'even';
					temp += '<ss:Row ss:Height="20">';
					for(var j = 0; j < columns.length; j++) {
						if(columns[j].display == null){
							continue;
						}
						var columnName = columns[j].columnname;
						temp = getValue.getValue(temp,gridData[i][columnName],cellClass);
					}
					temp += '</ss:Row>';
				}
				temp += '</ss:Table>';
				temp += '</ss:Worksheet>';

				var main = '<xml version="1.0" encoding="utf-8">'
						+ '<ss:Workbook xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns:o="urn:schemas-microsoft-com:office:office">'
						+ '<o:DocumentProperties><o:Title>'
						+ 'title111111111111111111111'
						+ '</o:Title></o:DocumentProperties>'
						+ '<ss:ExcelWorkbook>'
						+ '<ss:WindowHeight>'
						+ 100
						+ '</ss:WindowHeight>'
						+ '<ss:WindowWidth>'
						+ 500
						+ '</ss:WindowWidth>'
						+ '<ss:ProtectStructure>False</ss:ProtectStructure>'
						+ '<ss:ProtectWindows>False</ss:ProtectWindows>'
						+ '</ss:ExcelWorkbook>'
						+ '<ss:Styles>'
						+ '<ss:Style ss:ID="Default">'
						+ '<ss:Alignment ss:Vertical="Top" ss:WrapText="1" />'
						+ '<ss:Font ss:FontName="arial" ss:Size="10" />'
						+ '<ss:Borders>'
						+ '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Top" />'
						+ '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Bottom" />'
						+ '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Left" />'
						+ '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Right" />'
						+ '</ss:Borders>'
						+ '<ss:Interior />'
						+ '<ss:NumberFormat />'
						+ '<ss:Protection />'
						+ '</ss:Style>'
						+ '<ss:Style ss:ID="title">'
						+ '<ss:Borders />'
						+ '<ss:Font />'
						+ '<ss:Alignment ss:WrapText="1" ss:Vertical="Center" ss:Horizontal="Center" />'
						+ '<ss:NumberFormat ss:Format="@" />'
						+ '</ss:Style>'
						+ '<ss:Style ss:ID="headercell">'
						+ '<ss:Font ss:Bold="1" ss:Size="10" />'
						+ '<ss:Alignment ss:WrapText="1" ss:Horizontal="Center" />'
						+ '<ss:Interior ss:Pattern="Solid" ss:Color="#A3C9F1" />'
						+ '</ss:Style>'
						+ '<ss:Style ss:ID="even">'
						+ '<ss:Interior ss:Pattern="Solid" ss:Color="#CCFFFF" />'
						+ '</ss:Style>'
						//重度
						+ '<ss:Style ss:Parent="even" ss:ID="evenlevel1">'
						+ '<ss:Interior ss:Pattern="Solid" ss:Color="red" />'
						+ '</ss:Style>'
						//中度
						+ '<ss:Style ss:Parent="even" ss:ID="evenlevel2">'
						+ '<ss:Interior ss:Pattern="Solid" ss:Color="#AF7F30" />'
						+ '</ss:Style>'
						//轻度
						+ '<ss:Style ss:Parent="even" ss:ID="evenlevel3">'
						+ '<ss:Interior ss:Pattern="Solid" ss:Color="#FFEF8A" />'
						+ '</ss:Style>'
						+ '<ss:Style ss:Parent="even" ss:ID="evendate">'
						+ '<ss:NumberFormat ss:Format="yyyy-mm-dd" />'
						+ '</ss:Style>'
						+ '<ss:Style ss:Parent="even" ss:ID="evenint">'
						+ '<ss:NumberFormat ss:Format="0" />'
						+ '</ss:Style>'
						+ '<ss:Style ss:Parent="even" ss:ID="evenfloat">'
						+ '<ss:NumberFormat ss:Format="0.00" />'
						+ '</ss:Style>'
						+ '<ss:Style ss:ID="odd">'
						+ '<ss:Interior ss:Pattern="Solid" ss:Color="#CCCCFF" />'
						+ '</ss:Style>'
						+ '<ss:Style ss:Parent="odd" ss:ID="odddate">'
						+ '<ss:NumberFormat ss:Format="yyyy-mm-dd" />'
						+ '</ss:Style>'
						+ '<ss:Style ss:Parent="odd" ss:ID="oddint">'
						+ '<ss:NumberFormat ss:Format="0" />' + '</ss:Style>'
						+ '<ss:Style ss:Parent="odd" ss:ID="oddfloat">'
						+ '<ss:NumberFormat ss:Format="0.00" />'
						+ '</ss:Style>' + '</ss:Styles>' + temp
						+ '</ss:Workbook>';
				return main;
			}
		};
	})();
	return {
		getExcelUrl : function(inputGrid, inputTitle, sql) {
			var content = storeToXml.storeToXml(inputGrid, inputTitle, sql);
			var url = 'data:application/vnd.ms-excel;base64,'+ Base64.encode(content);
			return url;
		},
		storeToXml : function(inputGrid, inputTitle, sql) {
			var content = storeToXml.storeToXml(inputGrid, inputTitle, sql);
			return content;
		}
	};
})();
