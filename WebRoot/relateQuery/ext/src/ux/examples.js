Ext.example = function(){
    var msgCt;
    function createBox(t, s){
       return '<div class="msg"><h3>' + t + '</h3><p>' + s + '</p></div>';
    }
    return {
        msg : function(title, format){
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
            var m = Ext.DomHelper.append(msgCt, {html:createBox(title, format)}, true);
            m.hide();
            m.slideIn('t').ghost("t", { delay: 1000, remove: true});
        },
        init : function(){
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
        }
    };
}();
Ext.onReady(Ext.example.init, Ext.example);