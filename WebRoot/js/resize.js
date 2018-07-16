/**
 * 支持图片缩放、移动
 * 使用条件：jquery 1.10+,ligerUI,jqueryrotate
 * 版本：2.0
 * 作者：xjt
 * @param $container--图片容器
 * @param options--配置参数
 示例：
 var resize=new Resize($('.container'),{onAddVertex:function(img,id,pid){
		setTimeout(function(){
			img.attr('src','img/beauty.png')
		},1000)
	}}
 );
 resize.addButton($('#btn'),{
		img:'img/bg.jpg',
		w:100,
		h:100,
		pid:与数据关联数据库ID
	});
 resize.getSelected();
 */
function Resize($container,options,typeTemp){
	var offset=$container.offset(),left=offset.left,top=offset.top;
	var dragData=null,resizeSpan=null,moveOutline=null,handling=null;
	var uid={
		getId:function(pid){
			if(pid==undefined)
				return ['G',new Date().format('yyyyMMddhhmmss'),randomString(5)].join('');
			if(this.hasOwnProperty(pid)){
				return ++this[pid];
			}
			else{
				this[pid]=1;
				return 1;
			}
		},
		getCurVal:function(pid){
			return this[pid];
		}
	};
	var satId = null;
	var blobbuf={};//背景图片
	var blobs={};
	var dels=[];//删除背景图片
	var isUpdate=null;
	var isPreview=false;	
	var $resize=null;//当前图元jquery对象
	var statics={};//静态图元集合
	var isDebug=$container.data('debug')?true:false;
	var clipboard=null;//剪贴板
	var $canvas=$('<div class="canvas"><img></div>').appendTo($container.html(''))
				.css({	
					top:0,
					left:0,
					width:options.width+'px',
					height:options.height+'px'
				});
	var $outline=$('<div name="outline" style="position:absolute;z-index:999;display:none;" draggable="true"></div>').appendTo($canvas);
	var me=this;
	
	$container.scrollLeft((options.width-$container.width())/2);
	//添加拖拽按钮
	this.addButton=function($source,data){
		$source.attr('draggable',true).on('dragstart',function(e){
			data.id=uid.getId();
			data.title=data.name+uid.getId(data.pid);
			data.params=[];
			dragData=data;
		});
	};
	
	//是否计算结果图元
	this.isJsjg=function(){
		if($resize){
			return $resize.attr('type')*1 && jsManager[$resize.attr('id')].jsjg==1;
		}
	};
	//绑定参数
	this.bindParam=function(params){
		if(!$resize) return;
		var type=$resize.attr('type')*1;
		if(type){
			jsManager[$resize.attr('id')].bindParam(params);
		}
		else{
			if(params.length>1){
				$.ligerDialog.warn('只能选择一个参数！');
				throw new Error('只能选择一个参数！');
			}
			statics[$resize.attr('id')].params=params;
		}
		isUpdate=true;
	};
	//获取当前图元绑定参数
	this.getBindParam=function(){
		if(!$resize)return null;
		var type=$resize.attr('type')*1;
		if(type){
			return jsManager[$resize.attr('id')].getBindParam();
		}
		else{
			var stat=statics[$resize.attr('id')];
			if(!stat.params.length) return [];
			getValid(stat);
			return stat.params;
		}
	};
	//获取所有图元绑定参数
	this.getBindParams=function(){
		var binds=[];
		//1.静态图元
		$('.resize-unit',$container).each(function(){//图元
			var th=$(this);
			if(th.attr('type')*1==0){
				var stat=statics[th.attr('id')];
				if(!stat.params.length) return;
				getValid(stat);
				binds.push(stat.params[0]);
			}
		});
		//2.有范围动态图元
		$('.resize-unit',$container).each(function(){//图元
			var th=$(this);
			if(th.attr('type')*1){
				var params=jsManager[th.attr('id')].getBindParam();
				if(!params.length) return;
				if(params[0].max){
					for(var i=0,j;i<params.length;i++){
						for(j=0;j<binds.length;j++){
							if(binds[j].devId==params[i].devId && binds[j].parId==params[i].parId)
								break;
						}
						if(j<binds.length) continue;
						binds.push(params[i]);
					}					
				}				
			}
		});
		//3.无范围动态图元
		$('.resize-unit',$container).each(function(){//图元
			var th=$(this);
			if(th.attr('type')*1){
				var params=jsManager[th.attr('id')].getBindParam();
				for(var i=0,j;i<params.length;i++){
					for(j=0;j<binds.length;j++){//判断是否存在绑定参数
						if(params[i].satId){
							if(binds[j].satId==params[i].satId && binds[j].jsjgCode==params[i].jsjgCode && binds[j].code==params[i].code)
								break;
						}
						else{
							if(binds[j].devId==params[i].devId && binds[j].parId==params[i].parId)
								break;
						}
					}
					if(j<binds.length) continue;//存在则跳过
					binds.push(params[i]);
				}			
			}
		});
		return binds;
	};
	//根据表达式获取测试预览有效值
	function getValid(stat){
		var valid=[];			
		for(var i=0;i<stat.exps.length;i++){
			var e=stat.exps[i];
			var part=e.exp.split('||')[0].trim();
			if(part.indexOf(',')>-1){
				var p=part.split(',');
				if(p[0].indexOf('[')>-1){
					valid.push(p[0].val());
					continue;
				}
				if(p[1].indexOf(']')>-1){
					valid.push(p[1].val());
					continue;
				}
				valid.push((p[0].val()+p[1].val())/2);
				continue;
			}
			else{
				if(part.indexOf('=')>-1){
					valid.push(part.val());
					continue;
				}
				if(part.indexOf('>')>-1){
					valid.push(part.val()+1);
					continue;
				}
				if(part.indexOf('<')>-1){
					valid.push(part.val()-1);
					continue;
				}
			}
		}
		stat.params[0].valid=valid;
	}
	//显示数据
	this.setData=function(data){
		$('.resize-unit',$container).each(function(){//图元
			var th=$(this);
			if(th.attr('type')*1){//动态图元
				jsManager[th.attr('id')].setData(data);
			}
			else{//静态图元
				var stat=statics[th.attr('id')];
				var params=stat.params;
				if(!params.length) return;
				for(var i=0;i<data.length;i++){
					var e=data[i];
					if(e.devId==params[0].devId&&e.parId==params[0].parId){
						var exps=stat.exps;
						th.find('img').attr('src',getUrl(getImgByVal(exps,e.val)));
						break;
					}
				}
			}
		});
	};
	//返回去除运算符号后的值
	String.prototype.val=function(){
		var ret=this.replace(/\>|\=|\<|\(|\)|\[|\]/g,'');
		if(isNaN(ret)) throw new Error('表达式错误！');
		return ret*1;
	};
	//存储数据
	this.getData=function(isCopy){
		var isShowDevice=ShowDevice();//是否显示测站
		var graphs=[];
		$canvas.find('>img').each(function(){//背景图 TODO
			var th=$(this),id=th.attr('id');
			if(!id){
				return;
			}
			var graph={
					id:id,	
					type:2,
					top:th.css('margin-top').replace('px','')*1,
					w:th.width(),
					h:th.height()
				};
			if(th.attr('already')){
				graph.img=th.attr('img');
				graph.pid=th.attr('pid');
			}
			else if(blobbuf.hasOwnProperty(id)){//复制或者拖拽背景				
				graph.img=statics[id].img;
				th.attr('img',graph.img);
				blobs[graph.img]=blobbuf[id];
			}
			else{//静态图元背景
				var stat=statics[id];
				graph.pid=stat.pid;
				graph.img=stat.exps[0].img;
			}
			if(isCopy) graph.id=uid.getId();
			graphs.push(graph);
		});
		$('.resize-unit',$canvas).each(function(){//图元
			var th=$(this),graph=null;
			if(th.attr('type')*1){//动态图元
				graph=jsManager[th.attr('id')].getData();				
			}
			else{//静态图元
				if(!th.attr('pid'))	return;
				graph=th.data();
				graph.id=th.attr('id');
				graph.pid=th.attr('pid');			
				graph.type=0;
				graph.angle=th.find('>img').data('angle');
				var stat=statics[graph.id];
				graph.name=stat.name;
				graph.comment=stat.comment;
				graph.img=stat.img;
				graph.params=stat.params;
				graph.exps=stat.exps;
			}
			if(isCopy) graph.id=uid.getId();
			graphs.push(graph);
		});
		
		return JSON.stringify({width:options.width,height:options.height,isShowDevice:isShowDevice,graphs:graphs});
	};
	//返回背景图片
	this.getBlobImg=function(){
		return blobs;
	};
	//返回删除背景图片
	this.getDels=function(){
		return dels;
	};
	//加载数据
	this.load=function(file){
		if(!file.graphs || !file.graphs.length){
			hideMask();
			return;
		}
		var isBg=false,
			timer=null,
			w=file.width,
			h=file.height;
		
		for(var i=0;i<file.graphs.length;i++){
			var graph=file.graphs[i];
			switch(graph.type*1){
				case 0:
					graph.title=graph.name+uid.getId(graph.pid);
				case 1:
					dragData=graph;
					addVertex(graph.x,graph.y,graph.w,graph.h);					
					break;
				case 2:
					var graph1=graph;
					var img = new Image();
		            img.onload = function(){
		            	if(timer){
		            		clearTimeout(timer);
		            		timer=null;
		            	}			            				               			                
		                var imgWidth=img.width;
		                var imgHeight=img.height;
		                var w1,h1,img1;
		                //SVG,小背景图
		                if(graph1.img.indexOf('.svg')>-1 || imgWidth<=w && imgHeight<=h){
		                	$canvas.find('>img').remove();
		                	img1=$(img).css({
				    			marginTop:graph1.top+'px',
				    			width:graph1.w+'px',
				    			height:graph1.h+'px'
				    		}).prependTo($canvas);			                										
		                }
		                else{			                	
		                	w1=graph1.w;
		                	h1=graph1.h;
		                	var canvas = document.createElement('canvas');
			                var ctx = canvas.getContext("2d");
			                canvas.width =  w1; 
			                canvas.height = h1;
			                ctx.clearRect(0, 0, canvas.width, canvas.height);
			                ctx.drawImage(img, 0, 0, img.width, img.height,0,0,w1,h1);
			                img1=$canvas.find('>img').attr({src:canvas.toDataURL("image/jpeg")});
		                }
		                img1.attr({
		                		id:graph1.id,
		                		img:graph1.img,
		                		already:1,
		                		pid:graph1.pid
		                	}).css({
		                		marginTop:graph1.top+'px',
		                		zIndex:-1
		                	});								
		                hideMask();
		            };
		            img.src = getUrl(graph.img);
		            isBg=true;		            
					break;
			}
		}		
		if(dragData){
			select(dragData.id);
			dragData=null;
		}
		if(!isBg) hideMask();
		else{//启动定时器，30S内加载完毕；否则认为文件丢失
			/*timer=setTimeout(function(){
				hideMask();
				$.ligerDialog.warn('背景图片丢失，请重新上传！');
			},30000);*/
		}
		isUpdate=false;
		return file.isShowDevice;
	};	
	//是否修改
	this.isUpdate=function(){
		return isUpdate;
	};
	//重置isUpdate
	this.resetIsUpdate=function(){
		isUpdate = null;
	};
	//设置isUpdate
	this.setIsUpdate=function(){
		isUpdate = true;
	};
	this.setSatId = function(satIdTemp){
		satId = satIdTemp;
	};
	this.getSatId = function(){
		return satId;
	};
	//绘图区清空
	this.clear=function(){
		$canvas.children(':not([name=outline],img)').remove();
	};
	//是否存在图元
	this.hasGraph=function(){
		return $('.resize-unit',$container).length>0;
	};
	//返回container
	this.getContainer=function(){
		return $container;
	};
	//隐藏图元
	this.hideSelected=function(run){
		if(!run){
			isPreview=true;
			if(window.tab) tab('.c2').removeClass('bg');
		}
		if($resize){
			$('.resize-unit[id='+$resize.attr('id')+']',$container).css({'outline':0}).find('>span').hide();
		}
	};
	//显示图元
	this.showSelected=function(run){
		if(!run){
			isPreview=false;
			tab('.c2').addClass('bg');
		}
		$('.resize-unit[id='+$resize.attr('id')+']',$container).css({'outline':'1px dashed #0f0'}).find('>span').show();
	};
	//文件保存成功时，标记背景图为已经保存状态
	this.setBackgoundAlready=function(){
		$canvas.find('>img').each(function(){
			$(this).attr('already',1);
		});
		dels=[];
	};
	//设置背景
	this.setBack=function(blob){
		var id=uid.getId();		
        var w=options.width;
        var h=options.height;
		showMask();
		blobbuf={};
		blobbuf[id]=blob;		
		if(blob.type=='image/svg+xml'){//SVG
			statics[id]={
				img:uuid()+'.svg'
			};			
			window.URL = window.URL || window.webkitURL;
            var blobUrl = window.URL.createObjectURL(blob);
            var img=$canvas.find('>img');
			if(img.attr('already')){
				dels.push(img.attr('img'));
			}
			img.attr({id:id,src:blobUrl}).css({zIndex:-1}).removeAttr('already');        	
        	hideMask();
        	isUpdate=true;
		}
		else {
			statics[id]={
				img:uuid()
			};
			var reader = new FileReader();
	        reader.onload = function(e){        	 
	    		var img = new Image();
	            img.onload = function(){            	 
	                var imgWidth=img.width;
	                var imgHeight=img.height;
	                var img1=$canvas.find('>img');
        			if(img1.attr('already')){
        				dels.push(img1.attr('img'));
        			}
        			img1.remove();
	                if(imgWidth<=w && imgHeight<=h){//小背景图 
	                	$(img).attr({id:id}).css({zIndex:-1}).prependTo($canvas);
	    			}
	    			else{
	    				var w1,h1;
	    				if(w*imgHeight<=h*imgWidth){
	                    	w1=w;
	                		h1=w*imgHeight/imgWidth;			                	
	                    }
	                    else{
	                    	h1=h;
	                    	w1=h*imgWidth/imgHeight;
	                    }
	    				var canvas = document.createElement('canvas');
	                    var ctx = canvas.getContext("2d");
	                    canvas.width =  w1; 
	                    canvas.height = h1; 
	                    ctx.clearRect(0, 0, canvas.width, canvas.height);
	                    ctx.drawImage(img, 0, 0, img.width, img.height,0,0,w1,h1);
	                    var dataUrl=canvas.toDataURL("image/jpeg");
	                    $('<img>').attr({id:id,src:dataUrl}).css({zIndex:-1}).prependTo($canvas);
	    			}
	                hideMask();
	            	isUpdate=true;
	            };
	            img.src = e.target.result;
	        };
	        reader.readAsDataURL(blob);		
		}
	};
	//调整图元水平位置及大小
	this.addDx=function(dx){
		var w=$container.width(),
			xScale=w/(w-dx),
			getX=function(x){
				return x*xScale;
			};
		//$container.find('>img').css('width',w+'px');		
		$('.resize-unit',$container).each(function(){
			var th=$(this),
				x=getX(th.data('x')),
				w=getX(th.data('w'));
			th.css({left:x+'px',width:w+'px'}).data({x:x,w:w});
		});
	};
	
	//拖拽事件
	$container[0].addEventListener('dragover',function(e){	
		if($container.data('id')!=_curTabId)return;
		e.preventDefault();
		var offset=$canvas.offset(),
			x=e.clientX-offset.left,
			y=e.clientY-offset.top;		
		if(resizeSpan){
			resize(x,y);
		}
		else if(moveOutline){
			var data=$outline.data();
			$outline.css({
				left:$outline.position().left+x-data.x+'px',
				top:$outline.position().top+y-data.y+'px',
				border:'1px solid #000'
			}).data({
				x:x,
				y:y
			});			
		}
	});
	//拖拽目标事件
	$container[0].addEventListener('drop',function(e){
		if($container.data('id')!=_curTabId)return;
		e.preventDefault();		
		if(dragData){
			var w=dragData.w,
				h=dragData.h,
				offset=$canvas.offset(),
				x=e.clientX-offset.left-w/2,
				y=e.clientY-offset.top-h/2;			
			addVertex(x,y,w,h);
			select(dragData.id);
			if(options.onAddVertex)
				options.onAddVertex($('#'+dragData.id+'>img',$container),dragData.id,dragData.pid);
			dragData=null;
		}
		else if(resizeSpan){
			var container=resizeSpan.data('container');
			container.data({
				x:container.position().left,
				y:container.position().top,
				w:container.width(),
				h:container.height(),
				isResize:1
			});
			resizeSpan=null;	
		}
		else if(moveOutline){			
			$('#'+moveOutline).css({
				left:$outline.position().left+'px',
				top:$outline.position().top+'px'
			}).data({
				x:$outline.position().left,
				y:$outline.position().top
			});
			$outline.css('border-width',0).hide();
			moveOutline=null;
		}
		/*else if(e.dataTransfer&&e.dataTransfer.files.length){
			window.URL = window.URL || window.webkitURL;
			var blob=e.dataTransfer.files[0];
            var blobUrl = window.URL.createObjectURL(blob);
            var id=uid.getId();
            blobbuf[id]=blob;
            dragData={
    				id:id,
    				type:0,
    				blob:blobUrl,
    				img:blob.type=='image/svg+xml'?uuid()+'.svg':uuid()
    			};
            var w=200,h=200,x=e.clientX-left-w/2,y=e.clientY-top-h/2;	
            addVertex(x,y,w,h);
            select(dragData.id);
            dragData=null;
		}*/
		isUpdate=true;
	});
	//缩放处理
	function resize(x,y){
		var container=resizeSpan.data('container'),
			data=resizeSpan.data('data'),
			x1=data.x,
			x2=x1+data.w,
			y1=data.y,
			y2=y1+data.h;
		if(resizeSpan.hasClass('resize-handle-w')){
			container.css({
				left:(x<=x2?x:x2)+'px',
				width:(x<=x2?x2-x:x-x2)+'px'
			});
		}
		else if(resizeSpan.hasClass('resize-handle-e')){
			container.css({
				left:(x<=x1?x:x1)+'px',
				width:(x<=x1?x1-x:x-x1)+'px'
			});
		}
		else if(resizeSpan.hasClass('resize-handle-n')){
			container.css({
				top:(y<=y2?y:y2)+'px',
				height:(y<=y2?y2-y:y-y2)+'px'
			});
		}
		else if(resizeSpan.hasClass('resize-handle-s')){
			container.css({
				top:(y<=y1?y:y1)+'px',
				height:(y<=y1?y1-y:y-y1)+'px'
			});
		}
		else if(resizeSpan.hasClass('resize-handle-nw')){
			container.css({
				left:(x<=x2?x:x2)+'px',
				top:(y<=y2?y:y2)+'px',
				width:(x<=x2?x2-x:x-x2)+'px',
				height:(y<=y2?y2-y:y-y2)+'px'
			});
		}
		else if(resizeSpan.hasClass('resize-handle-sw')){
			container.css({
				left:(x<=x2?x:x2)+'px',
				top:(y<=y1?y:y1)+'px',
				width:(x<=x2?x2-x:x-x2)+'px',
				height:(y<=y1?y1-y:y-y1)+'px'
			});
		}
		else if(resizeSpan.hasClass('resize-handle-ne')){
			container.css({
				left:(x<=x1?x:x1)+'px',
				top:(y<=y2?y:y2)+'px',
				width:(x<=x1?x1-x:x-x1)+'px',
				height:(y<=y2?y2-y:y-y2)+'px'
			});
		}
		else if(resizeSpan.hasClass('resize-handle-se')){
			container.css({
				left:(x<=x1?x:x1)+'px',
				top:(y<=y1?y:y1)+'px',
				width:(x<=x1?x1-x:x-x1)+'px',
				height:(y<=y1?y1-y:y-y1)+'px'
			});
		}
	}
	//动态图元对象管理器
	var jsManager={
		setInstance:function(gid,instance){
			IDynamicBase.ensureImplements(instance);
			this[gid]=instance;
		}
	};
	//jar包管理器
	var jarManager={
		load:function(pid){
			if(!this.hasOwnProperty(pid)){
				if(isDebug){//调试
					$.ajax({  
					     url: 'rest/dynamic/getDebugFiles',  
					     type: 'POST',					     
					     async: false,  
					     success: function (ret) {
					         var files=eval("("+ret+")");
					         var csses=files.css;
					         for(var i=0;i<csses.length;i++){
					        	 loadCss('debug/css/'+csses[i]);
					         }
					         var jses=files.js;
					         for(var i=0;i<jses.length;i++){
					        	 loadJs('debug/js/'+jses[i]);
					         }
					     }
					});
				}
				else{//运行
					$.ajax({  
					     url: 'rest/dynamic/getRunFiles',  
					     type: 'POST',
					     data: {pid:pid},
					     async: false,  
					     success: function (ret) {
					         var files=eval("("+ret+")");
					         var csses=files.css;
					         for(var i=0;i<csses.length;i++){
					        	 loadCss('run/'+pid+'/css/'+csses[i]);
					         }
					         var jses=files.js;
					         for(var i=0;i<jses.length;i++){
					        	 loadJs('run/'+pid+'/js/'+jses[i]);
					         }
					     }
					});
				}				
				this[pid]=1;								
			}	
		}
	};	
	function addVertex(x,y,w,h){
		switch(dragData.type*1){
			case 0://静态图元 TODO
				var $img=$('<img>').attr({src:getUrl(dragData.img,dragData.blob)}).appendTo(create(x,y,w,h));
				if(dragData.angle==undefined)dragData.angle=0;
				$img.data('angle',dragData.angle).rotate(dragData.angle*1);
				statics[dragData.id]={
					pid:dragData.pid,
					name:dragData.name,
					comment:dragData.comment,
					params:dragData.params || [],
					exps:dragData.exps || [],
					img:dragData.img
				};
				break;
			case 1://动态图元
				jarManager.load(dragData.pid);
				var instance=createJs(isDebug?'debug/main.js':'run/'+dragData.pid+'/main.js');
				if(instance.ver==undefined){
					instance.ver='1.0';
				}
				jsManager.setInstance(dragData.id,instance);
				instance.create(create(x,y,w,h),clone(dragData));
				break;
		}		
	}
	$outline.on('mouseup',function(e){
		$outline.hide();
		moveOutline=null;
	});
	//新建节点
	var static_menu=null,dynamic_menu=null;
	function create(x,y,w,h){
		var pid=dragData.pid?' pid="'+dragData.pid+'"':'';
		return $('<div id="'+dragData.id+'"'+pid
				+'" mz="'+(dragData.title || (dragData.name+uid.getId(dragData.pid)))
				+'" comment="'+dragData.comment
				+'" type="'+dragData.type+'" class="resize-unit"></div>')
			.appendTo($canvas)
			.css({
				left:x+'px',
				top:y+'px',
				width:w+'px',
				height:h+'px'
			})
			.append('<span draggable="true" class="resize-handle-nw"></span>')
			.append('<span draggable="true" class="resize-handle-n"></span>')
	        .append('<span draggable="true" class="resize-handle-ne"></span>')
	        .append('<span draggable="true" class="resize-handle-e"></span>')
	        .append('<span draggable="true" class="resize-handle-se"></span>')
	        .append('<span draggable="true" class="resize-handle-s"></span>')
	        .append('<span draggable="true" class="resize-handle-sw"></span>')
	        .append('<span draggable="true" class="resize-handle-w"></span>')
	        .on('mousedown', 'span[class^=resize-handle-]', function(e){
	        	resizeSpan=$(e.target);	
				var c=resizeSpan.parent('.resize-unit');
				resizeSpan.data({
					container:c,
					data:c.data()
				});	  
	        }).data({
				x:x,
				y:y,
				w:w,
				h:h
			}).on('mousedown',function(e){
				if(!isPreview && !resizeSpan){
					var id=$(this).attr('id');
					var data=select(id).data();
					if(event.button==0){//左键
						moveOutline=id;
						var offset=$canvas.offset();
						$outline.css({
							left:data.x+'px',
							top:data.y+'px',
							width:data.w+'px',
							height:data.h+'px'
						}).show().data({
							x:e.clientX-offset.left,
			        		y:e.clientY-offset.top
						});
					}
				}
			}).on('contextmenu',function(e){
				if(isPreview) return false;
				if($(this).attr('type')*1){
					dynamic_menu.menu.items.children(':gt(5)').remove();
					var m=jsManager[$(this).attr('id')].menu;
					var dy=0;
					if(m!=undefined){
						if($.isArray(m)){
							for(var i=0;i<m.length;i++){
								if(m[i].text && !m[i].icon) m[i].icon='calendar';
								dynamic_menu.addItem(m[i]);
								dy+=25;
							}
						}
						else{
							if(!m.icon) m.icon='calendar';
							dynamic_menu.addItem(m);
							dy+=25;
						}						
					}
					var y=e.pageY;
					if(y-top+77+dy>$container.height())
						y-=77+dy;
					dynamic_menu.show({ top: y, left: e.pageX });
				}
				else{
					var y=e.pageY;
					if(y-top+200>$container.height())
						y-=200;
					static_menu.show({ top: y, left: e.pageX });
				}				
				return false;
			});	
	}
	//选择节点
	function select(id){
		$('.resize-unit[id!='+id+']',$container).css({'outline':0,'z-index':0}).find('>span[draggable]').hide();
		$resize=$('.resize-unit[id='+id+']',$container);
		$resize.css({'outline':'1px dashed #0f0','z-index':1}).find('span').show();		
		if(options.onSelected){		
			var comment=$resize.attr('comment');
			if(comment=='undefined') comment='';
			options.onSelected(id,$resize.attr('pid'),$resize.attr('type')*1,$resize.attr('mz'),comment);
		}
		return $resize;	
	}
	//键盘事件
	document.documentElement.addEventListener('keydown',function(e){
		if($container.data('id')!=_curTabId||handling||e.srcElement.tagName=='INPUT')
			return;
		switch(e.which){
			case 37://←
				$resize.css({
					left:$resize.position().left-2+'px'
				}).data({
					x:$resize.position().left
				});
				isUpdate=true;
				break;
			case 38://↑
				$resize.css({
					top:$resize.position().top-2+'px'
				}).data({
					y:$resize.position().top
				});
				isUpdate=true;
				break;
			case 39://→
				$resize.css({
					left:$resize.position().left+2+'px'
				}).data({
					x:$resize.position().left
				});
				isUpdate=true;
				break;
			case 40://↓
				$resize.css({
					top:$resize.position().top+2+'px'
				}).data({
					y:$resize.position().top
				});
				isUpdate=true;
				break;
			case 46://删除
				remove();
				isUpdate=true;
				break;
			case 67:
				if(e.ctrlKey){//CTRL+C
					clipboard=clone(jsManager[$resize.attr('id')].getData());
				}
				isUpdate=true;
				break;
			case 86:
				if(e.ctrlKey){//CTRL+V
					if(clipboard){
						if($(".l-window-mask")[0] && $(".l-window-mask")[0].style.display == "block"){
						}else{
							clipboard.id=uid.getId();
							clipboard.title=clipboard.title+uid.getId(clipboard.pid);
							clipboard.x=$resize.data('x')*1+10;
							clipboard.y=$resize.data('y')*1+10;
							dragData=clipboard;
							addVertex(clipboard.x,clipboard.y,clipboard.w,clipboard.h);		
							select(dragData.id);
							dragData=null;
							isUpdate=true;
						}
					}
				}
				isUpdate=true;
				break;
		}
		e.returnValue = false;
	});
	//粘贴事件
	/*document.documentElement.addEventListener('paste',function (e) {
		if($container.data('id')!=_curTabId)return;
        var items = e.clipboardData.items;
        for (var i = 0; i < items.length; ++i) {
            if (items[i].kind == 'file' && items[i].type.indexOf('image/') > -1) {
                var blob = items[i].getAsFile();
                window.URL = window.URL || window.webkitURL;
                var blobUrl = window.URL.createObjectURL(blob);
                var id=uid.getId();
                blobbuf[id]=blob;
                dragData={
        				id:id,
        				type:0,
        				blob:blobUrl,
        				img:uuid()
        			};
                addVertex(20,20,200,200);
                select(dragData.id);
                dragData=null;
                isUpdate=true;
                return;
            }
        }
	});*/
	//移除当前节点
	function remove(){
		if(!$resize)return;
		if($resize.attr('type')*1){
			delete jsManager[$resize.attr('id')];
		}
		else{
			delete statics[$resize.attr('id')];
			delete blobbuf[$resize.attr('id')];
		}		
		$resize.remove();
		if($('.resize-unit[style*="z-index: 0"]',$container).length)
			select($('.resize-unit[style*="z-index: 0"]:first',$container).attr('id'));
		else
			$resize=null;
	}
	//旋转图片
	function rotate(angle){
		var img=$resize.find('img');
		var arc=img.data('angle');
		arc=arc==undefined?angle:(arc*1+angle)%360;
		img.data('angle',arc).rotate(arc);
		isUpdate=true;
	}
	//设置背景
	function setBack(){
		var $img=$resize.find('img');
		var $c=$img.parent();
		$img.rotate(0);
		$('<img>').appendTo($container)
			.attr({id:$c.attr('id'),pid:$c.attr('pid'),src:$img.attr('src')})//TODO
			.css({
				position:'absolute',
				left:$resize.position().left+'px',
				top:$resize.position().top+'px',
				width:$resize.width()+'px',
				height:$resize.height()+'px',
				zIndex:-1
			});	
		$resize.remove();
		//delete statics[$resize.attr('id')];
		select($('.resize-unit[style*="z-index: 0"]:first',$container).attr('id'));
	}
	//显示属性对话框
	var propertis={
		create:function(owner,properties){
			numPress=function(float){
				var k = event.keyCode;    
			    if (k >= 48 && k <= 57 || 
			    		float && ('-'.charCodeAt()==k||'.'.charCodeAt()==k)){    
			        event.returnValue = true;    
			    } else {    
			        event.returnValue = false;    
			    } 
			};
			function getData(data){			
				var ret=[];
				for(var i=0;i<data.length;i++){
					ret.push({id:data[i],text:data[i]});
				}	
				return ret;				
			}
			function getLabelWidth(){
				if(!properties) return 60;
				var len=0;
				for(var i=0;properties.props && i<properties.props.length;i++){
					if(properties.props[i].name.replace(/\(|\)/g,'').length>len)
						len=properties.props[i].name.replace(/\(|\)/g,'').length;
				}
				if(!properties.parProps) return len>4?80:60;
				if($.type(properties.parProps) == 'object'){//兼容老版本
					properties.parProps=[properties.parProps];
				}
				for(var j=0;j<properties.parProps.length;j++){
					for(var i=0;i<properties.parProps[j].props.length;i++){
						if(properties.parProps[j].props[i].name.replace(/\(|\)/g,'').length>len)
							len=properties.parProps[j].props[i].name.replace(/\(|\)/g,'').length;
					}
				}				
				return len>4?80:60;
			}
			function color(el,p){
				var el1=el.parent().html(['<div class="l-labeltext" style="width: 222px; ">',
		                   '<div class="l-text-label" style="float: left; width: '+labelWidth+'px; text-align: right; ">'+p.name+':&nbsp;</div>',
		                   '<div class="l-text" style="width: '+(220-labelWidth)+'px; float: left; overflow:hidden;">',
			                   '<div name="'+p.code+'" style="margin:3px;border:1px solid #000;height:12px;cursor:pointer;background-color:'+p.val+'"></div>',
		                   '</div>',
	                   '</div>',
	                   '<br style="clear:both;">',
                   '</div>'].join(''));
				var c=el1.find('[name='+p.code+']').data('color',p.val);
				
				if(p.isTransparent){
					c.bigColorpicker(function(el,v){
						c.css("backgroundColor",v).data('color',v);
					},'P',6,function(checked){
						if(checked){
							c.css('backgroundImage','url(img/trans.jpg)').data('color','transparent');
						}
						else{
							c.css('backgroundImage','').data('color',c.css('backgroundColor'));
						}
					});
				}
				else{
					c.bigColorpicker(function(el,v){
						$(el).css("backgroundColor",v).data('color',v);
					});
				}
				

				this.getValue=function(){
					return c.data('color');
				};
				this.setValue=function(v){
					if(v=='transparent'){
						c.css('backgroundImage','url(img/trans.jpg)');
					}
					else{
						c.css('backgroundImage','').css("backgroundColor",v);
					}
					c.data('color',v);
				};
			}
			function img(el,p){
				var el1=el.parent().html(['<div class="l-labeltext" style="width: 222px; ">',
	                   '<div class="l-text-label" style="float: left; width: '+labelWidth+'px; text-align: right; ">'+p.name+':&nbsp;</div>',
	                   '<div class="l-text" style="width: 64px; height:64px;cursor:pointer;border:1px solid #AECAF0; float: left; overflow:hidden;">',
	                   '</div>',
	                   '<input type="file" style="display:none" accept="image/*">',
	                   '</div>',
	                   '<br style="clear:both;">',
                   '</div>'].join(''));
				el1.find('.l-text').click(function(){
					el1.find('input').click().change(function(){
						var file=this.files[0];
						if(file.size > 1024 * 1024){
			    			$.ligerDialog.warn('上传文件不能超过1M！');
			        		return;
			    		}
						var reader = new FileReader();
						reader.readAsDataURL(file);
						reader.onload = function(e){ 
							el1.find('.l-text').attr({title:'点击上传图片'}).html('<img src="'+this.result+'" style="width:100%;height:100%;">');
					    }; 
					});
				});
				this.getValue=function(){
					return el1.find('img').attr('src');
				};
				this.setValue=function(v){
					if(v){
						el1.find('.l-text').attr({title:'点击上传图片'}).html('<img src="'+v+'" style="width:100%;height:100%;">');
					}
					else{
						el1.find('.l-text').html('<div style="margin:12px 8px;text-align:center;">点击上传图片</div>');
					}
				};
			}
			
			var el=this.el=$('<table class="resize-properties"></table>');
			var me=this;
			var labelWidth=getLabelWidth();
			var oldCode={},parProps=null;
			function addProp(p,number){
				number=number!=undefined?
						' onkeypress="numPress()"':
							typeof p.val==='number'?
									' onkeypress="numPress(1)"':'';
				el.append('<tr><td><input name="'+p.code+'"'+number+'></td></tr>');
				var el1=el.find('[name='+p.code+']');			
				if(p.xtype=='file'){//图片
					me[p.code]=new img(el1,p);					
				}
				else if(typeof p.val==='string'&&p.val[0]=='#'){//颜色框
					me[p.code]=new color(el1,p);					
				}
				else if(p.range){//下拉框
					var opts={
							label:p.name,
							labelWidth:labelWidth,
							labelAlign:'right',
							width:220-labelWidth
						};
					if(p.isParProp){//参数列表
						opts.valueField='code';
						opts.textField='name';
						opts.data=p.range;						
						opts.onSelected=function(val,text){
							if(val=='')return;
							var propVals=null;
							if(oldCode[p.code]){
								propVals=parProps[p.code][oldCode[p.code]];
								for(var k in propVals){
									propVals[k]=me[k].getValue();
								}
							}
							if(parProps[p.code] == null){
								return;
							}
							propVals=parProps[p.code][val];
							for(var k in propVals){
								me[k].setValue(propVals[k]);
							}
							oldCode[p.code]=val;
						};
					}
					else{
						opts.data=getData(p.range);
					}
					me[p.code]=el1.ligerComboBox(opts);
					el1.hide();
					if(p.val) me[p.code].setValue(p.val);
				}
				else{//文本框
					me[p.code]=el1.ligerTextBox({
						label:p.name,
						labelWidth:labelWidth,
						labelAlign:'right',
						width:220-labelWidth,
						value:p.val
					});
				}				
			}
			var pos=owner.position();
			var arr = [];
			if(pos){
				arr = [{
					name:'x',
					code:'x',
					val:Math.round(pos.left)
				},{
					name:'y',
					code:'y',
					val:Math.round(pos.top)
				},{
					name:'宽度',
					code:'w',
					val:owner.width()
				},{
					name:'高度',
					code:'h',
					val:owner.height()
				}];
			}
			for(var i=0;i<arr.length;i++){
				addProp(arr[i],1);
			}
			if(!properties) return;
			var props=properties.props;
			for(var i=0;i<props.length;i++){
				addProp(props[i]);
			}	
			//参数属性
			if(!properties.parProps) return;
			parProps=this.parProps={};	
			for(var j=0,k=0;j<properties.parProps.length;j++){
				if(properties.parProps[j].name==undefined){//兼容老版本
					properties.parProps[j].name=!k?'参数名':'参数名'+k++;
				}
				if(properties.parProps[j].code==undefined){//兼容老版本
					properties.parProps[j].code='#param'+j;
				}
				var params=properties.parProps[j].params,
					paramName=properties.parProps[j].paramName||properties.parProps[j].name,
					paramCode=properties.parProps[j].code;
				for(var i=0;i<params.length;i++){
					var e=params[i];
					if(!parProps.hasOwnProperty(paramCode)){
						parProps[paramCode]={};
					}
					parProps[paramCode][e.code]=e.propVals;
				}
				addProp({name:paramName,code:paramCode,range:params,isParProp:true});
				var props=properties.parProps[j].props;
				for(var i=0;i<props.length;i++){
					addProp(props[i]);
				}
				if(params.length)
					this[paramCode].setValue(params[0].code);
			}			
		},
		get:function(code){
			return this[code].getValue();
		},
		height:function(properties){
			var h=185;
			if(properties.props)
				h+=25*properties.props.length;
			if(properties.parProps){
				for(var i=0;i<properties.parProps.length;i++){
					h+=25*properties.parProps[i].props.length+25;
				}
			}				
			return h;
		},
		show:function(owner,gid){
			var type=null,properties=null;
			if(gid){
				type=1;
				properties=jsManager[gid].getProperties();
			}
			else{
				type=0;
				var angle=owner.find('img').data('angle');
				if(angle==undefined)angle=0;
				properties={props:[{name:'角度',code:'angle',val:angle}]};
			}
			this.create(owner,properties);
			var me=this;
			handling=1;
			_dlg=$.ligerDialog.open({
				title:'属性',
				height:300,//this.height(properties),
				target:this.el,
				buttons:[{
					text:'确定',
					type:'save',
					width:80,
					onclick:function(item, dialog){
						var x=me.get('x')*1,
							y=me.get('y')*1,
							w=me.get('w')*1,
							h=me.get('h')*1;
						owner.css({
							left:x+'px',
							top:y+'px',
							width:w+'px',
							height:h+'px'
						}).data({
							x:x,
							y:y,
							w:w,
							h:h
						});
						//TODO:设置属性 未检查参数合法性
						if(type){
							if(properties.props){
								var props=properties.props;
								var ret={};
								for(var i=0;i<props.length;i++){
									ret[props[i].code]=me.get(props[i].code);
								}
								properties.props=ret;
							}
							//参数属性
							if(properties.parProps){
								for(var j=0;j<properties.parProps.length;j++){									
									var ret={};
									var props=properties.parProps[j].props;
									var parCode=properties.parProps[j].code;
									for(var i=0;i<props.length;i++){
										ret[props[i].code]=me.get(props[i].code);
									}
									if(me.parProps[parCode] == null){
										return;
									}
									me.parProps[parCode][me.get(parCode)]=ret;
									if(jsManager[gid].ver=='1.0'){//兼容老版本
										me.parProps=me.parProps['#param0'];
									}									
								}
								properties.parProps=me.parProps;
							}
							jsManager[gid].setProperties(clone(properties));
						}
						else{
							var angle=me.get('angle');
							if(isNaN(angle)){
								$.ligerDialog.warn('角度数字错误！');
								return;
							}
							angle=angle*1;
							owner.find('img').data('angle',angle).rotate(angle);
						}
						_dlg.close();
						isUpdate=true;
					}
				},{
					text:'取消',
					type:'close',
					width:80,
					onclick:function(item, dialog){
						_dlg.close();
					}
				}],
				onClose:function(){
					handling=null;
				}
			});
			$('.l-dialog-content',_dlg.element).css('overflow-y','auto');
		}
	};
	//静态图元右键菜单
	static_menu=$.ligerMenu({
		id:'static-menu',
		items:[{
			text:'旋转45°',
			icon:'x-rotate1',
			click:function(){
				rotate(45);
			}
		},{
			text:'旋转-45°',
			icon:'x-rotate2',
			click:function(){
				rotate(-45);
			}
		},{
			text:'旋转90°',
			icon:'x-rotate3',
			click:function(){
				rotate(90);
			}
		},{
			text:'旋转-90°',
			icon:'x-rotate4',
			click:function(){
				rotate(-90);
			}
		},{line:true},{
			text:'设为背景',
			icon:'x-background',
			click:function(){
				setBack();
				isUpdate=true;
			}
		},{line:true},{
			text:'删除',
			icon:'delete',
			click:function(){
				remove();
				isUpdate=true;
			}
		},{line:true},{
			text:'属性',
			icon:'x-properties',
			click:function(){
				propertis.show($resize);
			}
		},{line:true},{
			text:'绑定数据',
			icon:'x-ok',
			click:options.onBind
		}]
	});
	//动态图元右键菜单
	dynamic_menu=$.ligerMenu({
		id:'dynamic-menu',
		items:[{
			text:'复制',
			icon:'x-copy',
			click:function(){
				if($resize){
					clipboard=clone(jsManager[$resize.attr('id')].getData());
				}
			}
		},{
			text:'删除',
			icon:'delete',
			click:function(){
				$.ligerDialog.confirm('确认删除吗？', '系统提示',function (yes) { 
					if(yes){
						remove();
						isUpdate=true;
					}					
				});				
			}
		},{line:true},{
			text:'属性',
			icon:'x-properties',
			click:function(){
				if($resize){
					propertis.show($resize,$resize.attr('id'));
				}
			}
		},{line:true},{
			text:'绑定数据',
			icon:'x-ok',
			click:options.onBind
		}]
	});
	//背景设置菜单
	var bg_menu=$.ligerMenu({
		id:'bg-menu',
		items:[{
			text:'上传背景',
			icon:'in',
			click:function(){	
				$('<input type="file">').click().change(function(){
					var file=this.files[0];
					me.setBack(file);
				});					
			}
		},{
			text:'底图边距',
			icon:'x-page',
			click:function(){	
				me.setBackPos();					
			}
		},{
			text:'删除背景',
			icon:'busy',
			click:function(){	
				me.removeBackground();					
			}
		},{line:true},{
			text:'粘贴',
			icon:'x-paste',
			click:function(){
				if(clipboard){
					clipboard.id=uid.getId();
					clipboard.title=clipboard.title+uid.getId(clipboard.pid);
					var pos=$(bg_menu.element).position();
					clipboard.x=pos.left-left;
					clipboard.y=pos.top-top;
					dragData=clipboard;
					addVertex(clipboard.x,clipboard.y,clipboard.w,clipboard.h);		
					select(dragData.id);
					dragData=null;
					isUpdate=true;
				}				
			}
		}]
	});
	if(typeTemp == 'monitor'){
		return;
	}
	//绑定背景菜单
	$container.contextmenu(function(e){	
		if(isPreview) return false;
		bg_menu.show({ top: e.pageY, left: e.pageX });
		return false;
	});	
	this.removeUnit=function(){
		$.ligerDialog.confirm('确认删除吗？', '系统提示',function (yes) { 
			if(yes){
				remove();
				isUpdate=true;
			}					
		});
	};
	this.showProperties=function(){
		if($resize)
			propertis.show($resize,$resize.attr('id'));
	};
	this.removeBackground=function(){
		$.ligerDialog.confirm('确认删除吗？', '系统提示',function (yes) { 
			if(yes){
				$canvas.find('>img').each(function(){
					var th=$(this);
					if(th.attr('already') && !th.attr('pid')){
						dels.push(th.attr('img'));
					}
					th.remove();
				});
				blobbuf={};
				blobs={};
				isUpdate=true;
			}					
		});
	};
	this.setBackPos=function(){		
		var dlg=$(['<div id="pageDlg" style="position:absolute;left:50%;margin-left:-132px;top:40px;width:265px;height:115px;background:#fff;border:2px solid #04c;border-radius:8px;">',
		    '<table cellpadding="0" cellspacing="0" class="l-table-edit">',
	            '<tr>',
	                '<td align="right" class="l-table-edit-td" style="width:60px">上边距:</td>',
	                '<td align="left" class="l-table-edit-td"><input style="width:65px" name="top" type="text" onkeypress="return event.keyCode >= 48&&event.keyCode<= 57">&nbsp;像素</td>',
	                '<td align="left"><button name="increase" style="font-weight:bold;" title="增加">∧</button>&nbsp;<button name="decrease" style="font-weight:bold;" title="减小">∨</button></td>',
	            '</tr>',
	            '<tr>',
	                '<td align="right" class="l-table-edit-td">宽度:</td>',
	                '<td align="left" class="l-table-edit-td"><input style="width:65px" name="width" type="text" onkeypress="return event.keyCode >= 48&&event.keyCode<= 57">&nbsp;像素</td>',
	                '<td align="left"><button name="increase1" style="font-weight:bold;" title="增加">∧</button>&nbsp;<button name="decrease1" style="font-weight:bold;" title="减小">∨</button></td>',
	            '</tr>',
	        '</table>',
	        '<div style="margin-left:140px;margin-top:8px;"><button name="apply" style="font-weight:bold;">应用</button>&nbsp;<button name="close" style="font-weight:bold;">关闭</button></div>',
	    '</div>'].join('')).appendTo($container.parent());
		var img=$canvas.find('>img');
    	var r=img.height()/img.width();
    	var top=dlg.find('input[name=top]');
    	var width=dlg.find('input[name=width]');
    	top.val(img.css('margin-top').replace('px',''));
    	width.val(img.css('width').replace('px',''));
		function apply(){
    		img.css({
    			marginTop:top.val()+'px',
    			width:width.val()+'px',
    			height:width.val()*r+'px'
    		});
    	}
		dlg.find('button[name=apply]').click(apply);
    	dlg.find('button[name=close]').click(function(){
    		dlg.remove();
    	});
    	dlg.find('button[name=increase]').click(function(){
    		top.val(top.val()*1+10);
    		apply();
    	});
    	dlg.find('button[name=decrease]').click(function(){
    		top.val(top.val()*1-10);
    		apply();
    	});
    	dlg.find('button[name=increase1]').click(function(){
    		width.val(width.val()*1+10);
    		apply();
    	});
    	dlg.find('button[name=decrease1]').click(function(){
    		width.val(width.val()*1-10);
    		apply();
    	});	    			    	
    	dlg.show();
	};
}

//当前是否显示测站
function ShowDevice(){
	var isCheck=null;
	var flag=document.getElementById("isShowDevice").checked;
	if(flag){
		isCheck="Y";
	}else{
		isCheck="N";
	}
	return isCheck;
}