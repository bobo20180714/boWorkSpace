/**
* jQuery ligerUI 1.2.4
* 
* http://ligerui.com
*  
* Author daomi 2014 [ gd_star@163.com ] 
* 
*/

(function ($)
{
    //气泡,可以在制定位置显示
    $.ligerTip = function (p)
    {
        return $.ligerui.run.call(null, "ligerTip", arguments);
    };

    //在指定Dom Element右侧显示气泡
    //target：将ligerui对象ID附加上
    $.fn.ligerTip = function (options)
    {
        this.each(function ()
        {
            var p = $.extend({}, $.ligerDefaults.ElementTip, options || {});
            p.target = p.target || this;
            //如果是自动模式：鼠标经过时显示，移开时关闭
            if (p.auto || options == undefined)
            {
                if (!p.content)
                {
                    p.content = this.title;
                    if (p.removeTitle)
                        $(this).removeAttr("title");
                }
                p.content = p.content || this.title;
                $(this).bind('mouseover.tip', function ()
                {  
                	//修改提示语框，若有父窗口，则出现在父窗口中。 yinxy 2015-2-2
	                var s1=0;
	                var s2=0;
//	                if(window!=window.parent){
//	                	 var pos_x1= $(window)[0].mozInnerScreenX || $(window)[0].screenLeft || 0;
//	                     var pos_x2= $(window.parent)[0].mozInnerScreenX || $(window.parent)[0].screenLeft || 0;
//	                     var pos_y1= $(window)[0].mozInnerScreenY || $(window)[0].screenTop || 0;
//	                     var pos_y2= $(window.parent)[0].mozInnerScreenY || $(window.parent)[0].screenTop || 0;
//	                      	 s1=pos_x1-pos_x2-$(window).scrollLeft();
//	                      	 s2=pos_y1-pos_y2-$(window).scrollTop();
//	                }
              
                    p.x = s1+ $(this).offset().left + $(this).width()/3 + (p.distanceX || 0);
                    p.y = s2+ $(this).offset().top + (p.distanceY || 0);
                    $.ligerTip(p);
                }).bind('mouseout.tip', function ()
                {

                    var tipmanager = $.ligerui.managers[this.ligeruitipid];
                    if (tipmanager)
                    {
                        tipmanager.remove();
                    }
                });
            }
            else
            {
                if (p.target.ligeruitipid) return;
              //修改提示语框，若有父窗口，则出现在父窗口中。 yinxy 2015-2-2
                var s1=0;
                var s2=0;
//                if(window!=window.parent){
//                	 var pos_x1= $(window)[0].mozInnerScreenX || $(window)[0].screenLeft || 0;
//                     var pos_x2= $(window.parent)[0].mozInnerScreenX || $(window.parent)[0].screenLeft || 0;
//                     var pos_y1= $(window)[0].mozInnerScreenY || $(window)[0].screenTop || 0;
//                     var pos_y2= $(window.parent)[0].mozInnerScreenY || $(window.parent)[0].screenTop || 0;
//                      	 s1=pos_x1-pos_x2-$(window).scrollLeft();
//                      	 s2=pos_y1-pos_y2-$(window).scrollTop();
//                }
                p.x =s1+ $(this).offset().left + $(this).width()/3 + (p.distanceX || 0);
                p.y =s2+ $(this).offset().top + (p.distanceY || 0);
                p.x = p.x || 0;
                p.y = p.y || 0;
                $.ligerTip(p);
            }
        });
        return $.ligerui.get(this, 'ligeruitipid');
    };
    //关闭指定在Dom Element(附加了ligerui对象ID,属性名"ligeruitipid")显示的气泡
    $.fn.ligerHideTip = function (options)
    {
        return this.each(function ()
        {
            var p = options || {};
            if (p.isLabel == undefined)
            {
                //如果是lable，将查找指定的input，并找到ligerui对象ID
                p.isLabel = this.tagName.toLowerCase() == "label" && $(this).attr("for") != null;
            }
            var target = this;
            if (p.isLabel)
            {
                var forele = $("#" + $(this).attr("for"));
                if (forele.length == 0) return;
                target = forele[0];
            }
            var tipmanager = $.ligerui.managers[target.ligeruitipid];
            if (tipmanager)
            {
                tipmanager.remove();
            }
        }).unbind('mouseover.tip').unbind('mouseout.tip');
    };


    $.fn.ligerGetTipManager = function ()
    {
        return $.ligerui.get(this);
    };


    $.ligerDefaults = $.ligerDefaults || {};


    //隐藏气泡
    $.ligerDefaults.HideTip = {};

    //气泡
    $.ligerDefaults.Tip = {
        content: null,
        callback: null,
        width: 150,
        height: null,
        x: 0,
        y: 0,
        appendIdTo: null,       //保存ID到那一个对象(jQuery)(待移除)
        target: null,
        auto: null,             //是否自动模式，如果是，那么：鼠标经过时显示，移开时关闭,并且当content为空时自动读取attr[title]
        removeTitle: true        //自动模式时，默认是否移除掉title
    };

    //在指定Dom Element右侧显示气泡,通过$.fn.ligerTip调用
    $.ligerDefaults.ElementTip = {
        distanceX: 1,
        distanceY: -3,
        auto: null,
        removeTitle: true
    };

    $.ligerMethos.Tip = {};

    $.ligerui.controls.Tip = function (options)
    {
        $.ligerui.controls.Tip.base.constructor.call(this, null, options);
    };
    $.ligerui.controls.Tip.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return 'Tip';
        },
        __idPrev: function ()
        {
            return 'Tip';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.Tip;
        },
        _render: function ()
        {
        	//修改提示框出现在父窗体中。  yinxy 2015-2-2
//        	var windowparent;
//        	if(window.parent){
//        		windowparent=$(window.parent.document).find("body");
//        	}else{
//        		windowparent=$(window).find("body");
//        	}
//        	 
            var g = this, p = this.options;
            var tip = $('<div class="l-verify-tip"><div class="l-verify-tip-corner"></div><div class="l-verify-tip-content"></div></div>');
            g.tip = tip;
            g.tip.attr("id", g.id);
            if (p.content)
            {
                $("> .l-verify-tip-content:first", tip).html(p.content);
                tip.appendTo('body');
                //tip.appendTo(windowparent);
            }
            else
            {
                return;
            }
            tip.css({ left: p.x, top: p.y }).show();
            p.width && $("> .l-verify-tip-content:first", tip).width(p.width - 8);
            p.height && $("> .l-verify-tip-content:first", tip).width(p.height);
            eee = p.appendIdTo;
            if (p.appendIdTo)
            {
                p.appendIdTo.attr("ligerTipId", g.id);
            }
            if (p.target)
            {
                $(p.target).attr("ligerTipId", g.id);
                p.target.ligeruitipid = g.id;
            }
            p.callback && p.callback(tip);
            g.set(p);
        },
        _setContent: function (content)
        {
            $("> .l-verify-tip-content:first", this.tip).html(content);
        },
        remove: function ()
        {
            if (this.options.appendIdTo)
            {
                this.options.appendIdTo.removeAttr("ligerTipId");
            }
            if (this.options.target)
            {
                $(this.options.target).removeAttr("ligerTipId");
                this.options.target.ligeruitipid = null;
            }
            this.tip.remove();
        }
    });
})(jQuery);