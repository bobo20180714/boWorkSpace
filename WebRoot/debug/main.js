//动态图元示例：曲线
new function(){
	//图元标识
	this.gid=null;	
	//属性
	var title=null;
	//内部变量
	var $chart=null;
	var owner1=null;
	var binds=null;
	var data1=null;
	var color = ["#ff0000", "#0000ff", "#008000", "#800080", "#a52a2a", "#6a5acd", "#008080", "#ffb6c1", "#ffa500", "#ffff00"];
	
	var backgroundColor = "#fff";
	var backgroundColor_tuli = "#fff";
	var backgroundColor_title = "#D8EEE1";
	
	var isChange=false;
	var canvas = null;
	var ctx=null;
	var ml=null;
	//创建图元 
	this.create=function(owner,data){
		debugger;
		this.gid=data.id;
		title=data.title;
		backgroundColor = data.backgroundColor==null?"#fff":data.backgroundColor;
		backgroundColor_tuli = data.backgroundColor_tuli==null?"#fff":data.backgroundColor_tuli;
		backgroundColor_title = data.backgroundColor_title==null?"#D8EEE1":data.backgroundColor_title;
		$chart=$('<div class="chart"><div name="title" style="background:'+backgroundColor_title+'">'
				+title+'</div><div name="content" style="background:'+backgroundColor+'"><div class="tick1"></div><div class="tick2"></div><div class="graph"></div></div></div>')
				.appendTo(owner)
				.find('[name=content]');
		owner1=owner;
		binds=data.params;
		data1=data;
		drawArea();
		addParams();
		return $chart;
	};	
	function getContext(owner){
		if(owner==undefined){
			owner=$chart.find('.graph')[0];
		}
		if(!canvas){
			canvas = document.createElement("canvas");
			owner.appendChild(canvas);
		}
		canvas.width = owner.clientWidth;
		canvas.height = owner.clientHeight;		
		return canvas.getContext("2d");
	}
	function drawArea(){
		var $graph=$chart.find('.graph');
		var $tick1=$chart.find('.tick1');
		var $tick2=$chart.find('.tick2');
		for(var i=1;i<4;i++){//网格线
			$graph.append('<div style="width:1px;height:100%;overflow:hidden;position:absolute;left:'
					+25*i+'%;top:0;border-right:1px dashed #808080;"></div>')
				.append('<div style="width:100%;height:1px;overflow:hidden;position:absolute;left:0;top:'
					+25*i+'%;border-bottom:1px dashed #808080;"></div>');
		}		
		for(i=0;i<=20;i++){//刻度
			$tick1.append('<div style="width:'+(i%5==0?8:4)+'px;height:1px;overflow:hidden;position:absolute;top:'
					+5*i+'%;right:0;border-top:1px solid #000;"></div>');
			$tick2.append('<div style="height:'+(i%5==0?8:4)+'px;width:1px;overflow:hidden;position:absolute;left:'
					+5*i+'%;top:0;border-left:1px solid #000;"></div>');
		}
		for(i=0;i<5;i++){//坐标值
			$tick1.append('<div name="y" style="width:32px;height:12px;overflow:hidden;position:absolute;top:'
					+25*i+'%;left:0;margin-top:-6px;padding:0;line-height:12px;text-align:right;"></div>');
			$tick2.append('<div name="x" style="width:60px;height:12px;overflow:hidden;position:absolute;left:'
					+25*i+'%;top:8px;margin-left:-30px;padding:0;line-height:12px;text-align:center;"></div>');
		}
		//图例
		$chart.append('<div name="legend" style="max-width:250px;overflow:hidden;position:absolute;right:'
				+'24px;top:14px;border:1px solid #000;display:none;background:#fff;"></div>');		
	}
	function addParams(){
		$chart.find('[name]').html('');
		var $legend=$chart.prevObject.find('[name=legend]');
		$legend[0].style.backgroundColor = backgroundColor_tuli;
		
		
		var $y=$chart.find('[name=y]');
		var setY=function(){//设置Y坐标值
			var e=getMainLine(binds);
			if(e){
				var max=e.max;
				var min=e.min;
				var dv=(max-min)/4;
				for(var i=0;i<5;i++){
					$y.eq(i).html('<font color="'+e.color+'" size="1">'+GetFmtVal(max - dv * i , dv)+'</font>');
				}			
			}
		};
		setY();
		//图例					
		for(var i=0;i<binds.length;i++){
			e=binds[i];
			$legend.append('<div style="display:flex;display:-webkit-flex;cursor:pointer;" ml="'+e.code+'">'
					+'<div style="width:20px;border-top:1px solid '
					+e.color+';margin:10px 4px 0 4px;"></div><div style="height:20px;line-height:20px;color:'
					+e.color+';font-size:12px;margin-right:4px;white-space:nowrap;">'+e.name+'</div></div>');
		}
		if(binds.length){
			$legend.show().on('mousedown','>div',function(){
				ml=$(this).attr('ml');
				setY();
			});
			ml=binds[0].code;
		}
		isChange=true;
	}
	function GetFmtVal(v,dv) {
		var numWidth=10;
    	function youxw(){//获取有效位
	        for(var rst=new Number(v).toFixed(p),i=0,n=0,isFirst=true;i<rst.length;i++){
	        	if(rst[i]=='-'||rst[i]=='.')continue;
	        	if(isFirst){
	        		if(rst[i]*1>0)isFirst=false;//第一个非0
	        		else continue;//0打头
	        	}
	        	n++;
	        }
	        return n;
        }    	
        if(v==undefined||v==null)return '';
        var nums = v > 0 ? numWidth - 1 : numWidth - 2,
            vs = new Number(v).toExponential() + '',
            exp = vs.split('e')[1] * 1 + 1;
        if (exp > nums || exp < -(nums - 1)) {
            var w = numWidth;
            return vs.length <= w ? vs : new Number(v).toExponential(w - 2 - vs.length + vs.indexOf('e'));
        }
        for (var i = 10, p = 0, j = exp; j <= nums; i /= 10, p++, j++) {
            if (dv >= i) break;
        }        
        for(;youxw()<3&&p<8;p++);//有效位至少为3
        var ret = new Number(v).toFixed(p);
        return ret.replace(/\.{1}0*$/g,'');
    }
	function getMainLine(binds){
		if(ml==null && binds.length)
			return binds[0];
		for(var i=0;i<binds.length;i++){
			if(binds[i].code==ml)
				return binds[i];
		}
		if(binds.length)
			return binds[0];
		return null;
	}
	//获取图元数据
	this.getData=function(){
		var p=owner1.data();
		data1.x=p.x;
		data1.y=p.y;
		data1.w=p.w;
		data1.h=p.h;
		data1.title=title;
		data1.backgroundColor=backgroundColor;
		data1.backgroundColor_tuli=backgroundColor_tuli;
		data1.backgroundColor_title=backgroundColor_title;
		var params=[];
		for(var i=0;i<binds.length;i++){
			var o={};
			for(var k in binds[i]){
				if(k=='points'||k=='t1'||k=='t2')continue;
				o[k]=binds[i][k];
			}
			params.push(o);
		}
		data1.params=params;
		return data1;
	};
	//显示图元数据
	this.setData=function(data){		
		var w=$chart.find('.graph').width(),h=$chart.find('.graph').height(),isClear=false;
		if(owner1.data('isResize')){
			isChange=true;
			owner1.data('isResize',0);
		}
		if(isChange) ctx=getContext();
		for(var i=0;i<data.length;i++){
			var e1=data[i];
			for(var j=0;j<binds.length;j++){
				var e2=binds[j];
				if(e1.code==e2.code){
					var cur=new Date().getTime();
					var val=e1.val;
					var max=e2.max*1;
					var min=e2.min*1;
					var dx=e2.span*60000/w;
					var dy=(max-min)/h;
					var reTime=function(){//更新时间
						if(e2.code==ml){
							var $x=$chart.find('[name=x]');
							var dt=(e2.t2-e2.t1)/4;
							for(var i=0;i<5;i++){
								$x.eq(i).html('<font color="'+e2.color+'" size="1">'+new Date(e2.t1+dt*i).format('hh:mm:ss')+'</font>')
							}
						}
						//$chart.find('[name=begin]>span').eq(j).text(new Date(e2.t1).format('hh:mm:ss'));
						//$chart.find('[name=end]>span').eq(j).text(new Date(e2.t2).format('hh:mm:ss'));
					};
					var reDraw=function(){//重绘
						ctx.moveTo((e2.points[0].t-e2.t1)/dx,(max-e2.points[0].v)/dy);
						for(var k=1;k<e2.points.length;k++){
							ctx.lineTo((e2.points[k].t-e2.t1)/dx,(max-e2.points[k-1].v)/dy);
							ctx.lineTo((e2.points[k].t-e2.t1)/dx,(max-e2.points[k].v)/dy);
						}
						var pt=e2.points[e2.points.length-1];
						ctx.lineTo((cur-e2.t1)/dx,(max-pt.v)/dy);
						ctx.lineTo((cur-e2.t1)/dx,(max-val)/dy);
					};
					if(!e2.points){//开始画线
						e2.points=[];
						e2.t1=cur;
						e2.t2=cur+e2.span*60000;
						reTime();
					}					
					else if(cur>e2.t2){//画满一屏
						e2.t2=cur;
						e2.t1=cur-e2.span*60000;
						reTime();						
						if(e2.points.length){
							//删除开始节点
							var last=null;
							while(e2.points.length&&e2.points[0].t<=e2.t1){
								last=e2.points.shift();
							}
							if(last)e2.points.unshift(last);
							//刷屏
							if(!isClear){
								ctx.clearRect(0, 0, w, h);
								isChange=true;
								isClear=true;
							}							
							ctx.strokeStyle=e2.color;
							ctx.beginPath();
							reDraw();
							ctx.stroke();
						}						
					}
					else{//未满一屏
						//往后添加曲线
						ctx.strokeStyle=e2.color;
						ctx.beginPath();
						if(isChange){
							e2.t2=e2.t1+e2.span*60000;
							reTime();
							reDraw();
						}
						else{
							var pt=e2.points[e2.points.length-1];
							ctx.moveTo((pt.t-e2.t1)/dx,(max-pt.v)/dy);
							ctx.lineTo((cur-e2.t1)/dx,(max-pt.v)/dy);
							ctx.lineTo((cur-e2.t1)/dx,(max-val)/dy);
						}						
						ctx.stroke();
					}
					e2.points.push({t:cur,v:val});					
					break;
				}//if
			}//for j
		}//for i
		isChange=false;
	};
	//绑定参数
	this.bindParam=function(params){
		for(var i=0;i<params.length;i++){
			var e=params[i];
			if(e.color==undefined)e.color=color[i];
			if(e.max==undefined)e.max=100;
			if(e.min==undefined)e.min=0;
			if(e.span==undefined)e.span=1;
		}
		binds=params;
		addParams();
	};
	//获取绑定参数
	this.getBindParam=function(){
		return binds;
	};
	//获取图元属性
	this.getProperties=function(){
		var params=[];
		for(var i=0;i<binds.length;i++){
			var e=binds[i];
			params.push({
				name:e.device+':'+e.name,
				code:e.code,
				propVals:{
					max:e.max,
					min:e.min,
					color:e.color,
					span:e.span
				}				
			});
		}
		var ret={
				props:[{
					name:'标题',
					code:'title',
					val:title
				},{
					name:'标题背景颜色',
					code:'backgroundColor_title',
					val:backgroundColor_title==null?"#D8EEE1":backgroundColor_title
				},{
					name:'图例背景颜色',
					code:'backgroundColor_tuli',
					val:backgroundColor_tuli==null?"#fff":backgroundColor_tuli
				},{
					name:'曲线背景颜色',
					code:'backgroundColor',
					val:backgroundColor==null?"#fff":backgroundColor
				}],
				parProps:{
					props:[{
						name:'最大值',
						code:'max',
						val:100.0
					},{
						name:'最小值',
						code:'min',
						val:0.0
					},{
						name:'时长(分)',
						code:'span',
						val:10
					},{
						name:'曲线颜色',
						code:'color',
						val:'#fff'
					}],
					params:params
				}
			};
		return ret;
	};
	//设置图元属性
	this.setProperties=function(data){
		if(!data){
			alert('图元属性不能空');
		}
		if(data.props){//属性
			var props=data.props;
			for(var k in props){
				switch(k){
					case 'title':
						title=props[k];
						owner1.find('[name=title]').text(title);
						break;
				}
			}
		}
		if(data.parProps){
			var parProps=data.parProps;
			debugger;
			//曲线背景颜色
			backgroundColor = data.props["backgroundColor"];
			var $content=$chart.find('[name=content]');
			$content.prevObject[0].style.backgroundColor = backgroundColor;
			//头部背景颜色
			backgroundColor_title = data.props["backgroundColor_title"];
			var $title=$chart.prevObject.find('[name=title]');
			$title[0].style.backgroundColor = backgroundColor_title;
			//图例背景颜色
			backgroundColor_tuli = data.props["backgroundColor_tuli"];
			var $legend=$chart.prevObject.find('[name=legend]');
			$legend[0].style.backgroundColor = backgroundColor_tuli;
			
			for(var i=0;i<binds.length;i++){
				var e=binds[i];
				var prop=parProps[e.code];
				e.max=prop.max;
				e.min=prop.min;
				e.color=prop.color;
				e.span=prop.span;
			}
			addParams();
		}
	};
}