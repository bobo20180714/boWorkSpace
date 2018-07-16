/*
 * 用于添加标注
 */

Ext.define('js.relationChart.Rtag',{
	constructor:Rcreate
});	

var _tagid = 1;
function Rcreate(c){
	var id = 'tag'+myId.getVal(),
		el = document.createElement("div"),
		txt = document.createElement("div"),
		ta = document.createElement("textarea"),
		readOnly = true,
		lock = false,
		aw = 'lb',
		lx,
		ly,
		left_=_relation_chart.getOffsetLeft(),
		top_=_relation_chart.getOffsetTop(),
	    getX = function(){
	    	return aw.match(/l/)?parseInt(el.style.left)+6:parseInt(el.style.left)+158;
	    },
	    getY = function(){
	    	return aw.match(/t/)?parseInt(el.style.top)+6:parseInt(el.style.top)+98;
	    },
	    setArrow = function(a){
	    	if(a==aw)return a;
	    	var x=getX(),y=getY();
	    	switch(a){
    		 	case 'lt':
    		 		el.style.background = 'url(img/tip-lt.ico) no-repeat center center'; 
    		 		el.style.left=x-6+'px';
    				el.style.top=y-6+'px';
    		 		break;
		 		case 'lb':
    		 		el.style.background = 'url(img/tip-lb.ico) no-repeat center center'; 
    		 		el.style.left=x-6+'px';
    				el.style.top=y-98+'px';
    		 		break;
		 		case 'rt':
    		 		el.style.background = 'url(img/tip-rt.ico) no-repeat center center'; 
    		 		el.style.left=x-158+'px';
    				el.style.top=y-6+'px';
    		 		break;
		 		case 'rb':
    		 		el.style.background = 'url(img/tip-rb.ico) no-repeat center center'; 
    		 		el.style.left=x-158+'px';
    				el.style.top=y-98+'px';
    		 		break;
	 		}
	 		return a;
	    },
		popMenu = new Ext.menu.Menu({
	        items: [{
	        	itemId:'lock',
	        	text:'锁定',
	        	icon:'img/unlock.ico'
	        },{
	        	itemId:'unlock',
	        	text:'解锁',
	        	icon:'img/lock.ico',
	        	hidden:true
	        },'-',{
	        	text: '编辑',
                icon: 'img/edit.ico'
	        },{
	        	text: '删除',
                icon: 'img/del.ico'
	        },'-',{
	        	text: '箭头左上',
                icon: 'img/arrow-lt.ico'
	        },{
	        	text: '箭头左下',
                icon: 'img/arrow-lb.ico'
	        },{
	        	text: '箭头右上',
                icon: 'img/arrow-rt.ico'
	        },{
	        	text: '箭头右下',
                icon: 'img/arrow-rb.ico'
	        }],
	        setDisabled_:function(b){
	        	var items=this.query('menuitem');
	        	for(var i=3;i<items.length;i++){
	        		items[i].setDisabled(b);
	        	}
	        },
        	listeners:{
        		'click':function(menu, item, e, eOpts){   
        			switch(item.text){
	        		 	case '锁定':
	        		 		item.hide();
		                	menu.down('#unlock').show();  
		                	lock = true;
		                	menu.setDisabled_(true);
		                	if(el.onlock_)el.onlock_(id,getX(),getY(),aw,txt.innerHTML,c);
	        		 		break;
        		 		case '解锁':
	        		 		item.hide();
		                	menu.down('#lock').show();
		                	lock = false;
		                	menu.setDisabled_(false);
		                	menu.show_=true;
		                	if(el.onunlock_)el.onunlock_(id);
	        		 		break;
        		 		case '编辑':		                	
		                	ta.value=txt.innerHTML;
		                	txt.innerHTML='';
		                	txt.style.padding = '0px';
		                	txt.appendChild(ta);
		                	ta.focus();
		                	readOnly = false;
	        		 		break;
	        		 	case '删除':	        		 		
	        		 		el.destroy();	    
	        		 		if(el.ondel_!=null)el.ondel_(id);
	        		 		break;
        		 		case '箭头左上':
	        		 		aw = setArrow('lt');
	        		 		break;
        		 		case '箭头左下':
	        		 		aw = setArrow('lb');
	        		 		break;
        		 		case '箭头右上':
	        		 		aw = setArrow('rt');
	        		 		break;
	        		 	case '箭头右下':
	        		 		aw = setArrow('rb');
	        		 		break;
    		 		}
        		},
	        	'beforehide':function(menu){
	        		if(menu.show_){
	        			menu.show_=false;
	        			return false;
	        		}
	        	}
        	}
	    });
	
    with (el.style) {
        position = "absolute";
        left = _relation_drawarea.Width/2-6+'px'
        top = _relation_drawarea.Height/2-98+'px';
        width = '160px';
        height = '100px';
        background = 'url(img/tip-lb.png) no-repeat center center';     
        zIndex = 2;
    }    
    _relation_chart.appendChild(el);
    with (txt.style) {
        margin = "13px 19px";
        wordWrap = 'break-word';
        wordBreak = 'break-all';
        height = '73px';
        overflowY = 'auto';  
        color = c;
        padding = '5px';
    }  
    txt.innerHTML='新标注'+_tagid++;
    el.appendChild(txt);
    ta.rows='4';
    ta.cols=Ext.isIE8m?'15':'14';
    with (ta.style) {
    	borderWidth='0px';
    	backgroundColor='#DFE8F6';
    	resize='none';   
    }    
    txt.onmousedown=function(e){
    	e = e || event;
    	if (e.button < 2) {
    		if(readOnly&&!lock){
    			this.style.cursor='move';
    			lx=e.clientX-left_;
    			ly=e.clientY-top_; 
    			_relation_owner.onmousemove=function(e){
    				e=e||event;
    				if(lx){
    					var x=e.clientX-left_,y=e.clientY-top_;
    					el.style.left=parseInt(el.style.left)+x-lx+'px';
    					el.style.top=parseInt(el.style.top)+y-ly+'px';
    					lx=x;
    					ly=y
    				}
    			}
    			_relation_owner.onmouseup=function(){
    				_relation_owner.onmousemove=null;
    				_relation_owner.onmouseup=null;
    				lx=null;
    				this.style.cursor='default'
    			}
    		}
    		else lx=null;
    		popMenu.hide();
    	}
    	else{
    		popMenu.showAt(e.clientX,e.clientY);
    	}
    	stopBubble(e);
    }
    
    ta.onblur=function(){
    	readOnly = true;
    	txt.style.padding = '5px';    	
    	txt.innerHTML=ta.value;
    	//txt.removeChild(ta);   	
    }
    el.show=function(){
    	this.style.visibility = 'visible';
    }
    el.hide=function(){
    	this.style.visibility = 'hidden';
    }
    el.setColor=function(ac){
    	txt.style.color = ac;  
    }
    el.destroy=function(){
    	_relation_chart.removeChild(el);
    }
    el.id=id;
    el.setXY=function(aw,x,y){   	
    	if(aw.match(/l/)){
    		if(x+140>_relation_drawarea.Width){
	    		x-=158;
	    		aw=aw.replace('l','r');
	    	}
	    	else{
	    		x-=6;
	    	}
    	}
    	else{
    		if(x<140){
    			x-=6;
    			aw=aw.replace('r','l');
    		}
    		else{
    			x-=158;
    		}
    	}
    	if(aw.match(/t/)){
    		if(y+84>_relation_drawarea.Height){
	    		y-=98;
	    		aw=aw.replace('t','b');
	    	}
	    	else{
	    		y-=6;
	    	}
    	}
    	else{
    		if(y<84){
    			y-=6;
    			aw=aw.replace('b','t');
    		}
    		else{
    			y-=98;
    		}
    	}
    	el.style.left=x+'px';
    	el.style.top=y+'px';
    	el.style.background = 'url(img/tip-'+aw+'.ico) no-repeat center center';
    }    
    el.onlock_=null;
    el.onunlock_=null;
    el.ondel_=null;
    
    return el;
}