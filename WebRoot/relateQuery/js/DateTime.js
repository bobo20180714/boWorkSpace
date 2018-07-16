//时间控件

Ext.define('js.DateTime',{
	config: null,
	constructor:function(config){
		return new DateTime(config);
	}
});	

function DateTime(config) {
    var el = document.createElement("div");
    with (el.style) {
        width = '113px';
        height = '18px';
        border = '1px solid #B5B8C8';
        backgroundColor = '#fff';
    }
    var m = [
        '<table cellspacing="0" style="width:112px;height:16px;table-layout:fixed;margin:1 0 0 0;">',
            '<tr>',
                '<td style="width:28px;height:14px;"><input type="text" style="width:28px;height:14px;border-width:0;background-color:transparent;text-align:right;" value="1970" readonly v="1970" ty="y"></input></td>',
                '<td style="width:2px;height:14px;">-</td>',
                '<td style="width:13px;height:14px;"><input type="text" style="width:13px;height:14px;border-width:0;background-color:transparent;" value="01" readonly v="01" ty="m"></input></td>',
                '<td style="width:2px;height:14px;">-</td>',
                '<td style="width:13px;height:14px;"><input type="text" style="width:13px;height:14px;border-width:0;background-color:transparent;" value="01" readonly v="01" ty="d"></input></td>',
                '<td style="width:6px;height:14px;"></td>',
                '<td style="width:14px;height:14px;"><input type="text" style="width:14px;height:14px;border-width:0;background-color:transparent;" value="00" readonly v="00" ty="h"></input></td>',
                '<td style="width:3px;height:14px;">:</td>',
                '<td style="width:13px;height:14px;"><input type="text" style="width:13px;height:14px;border-width:0;background-color:transparent;" value="00" readonly v="00" ty="i"></input></td>',
                '<td style="width:18px;height:14px;background-image:url(img/spin.ico);border-left:1px solid #B5B8C8;">',
                    '<table cellspacing="0" style="width:14px;height:14px;">',
                        '<tr><td style="width:14px;height:6px;"></td></tr>',
                        '<tr><td style="width:14px;height:6px;"></td></tr>',
                    '</table>',
                '</td>',
            '</tr>',
        '</table>'
        ]
    el.innerHTML = m.join('');

    var tr = el.childNodes[0].childNodes[0].childNodes[0];
    var year = tr.childNodes[0].firstChild;
    var month = tr.childNodes[2].firstChild;
    var day = tr.childNodes[4].firstChild;
    var hour = tr.childNodes[6].firstChild;
    var minute = tr.childNodes[8].firstChild;
    var bg = tr.childNodes[9];
    var upArrow = bg.childNodes[0].childNodes[0].childNodes[0].firstChild;
    var downArrow = bg.childNodes[0].childNodes[0].childNodes[1].firstChild;
    var buf = [], tid = null, sel = day, eid = null,
        objs = [year, month, day, hour, minute],
        oldTime = '1970-01-01 00:00';

    bg.onmouseover = function () {
        this.style.cursor = 'pointer';
        this.style.backgroundColor = '#d0def0';
    }
    bg.onmouseout = function () {
        this.style.backgroundColor = 'transparent';
    }
    upArrow.onmousedown = function () {
        bg.style.backgroundImage = 'url(img/upspin.ico)';
    }
    downArrow.onmousedown = function () {
        bg.style.backgroundImage = 'url(img/downspin.ico)';
    }
    bg.onmouseup = function () {
        this.style.backgroundImage = 'url(img/spin.ico)';
    }
    new Ext.util.ClickRepeater(upArrow, {
        handler: function () {
            var v = parseInt(sel.getAttribute('v'), 10);
            v++;
            switch (sel.getAttribute('ty')) {
                case 'y':
                    if (v > 9999) v = 9999;
                    break;
                case 'm':
                    if (v > 12) v = 1;
                    if (v < 10) v = '0' + v;
                    break;
                case 'd':
                    if (v > getDays()) v = 1;
                    if (v < 10) v = '0' + v;
                    break;
                case 'h':
                    if (v > 23) v = 0;
                    if (v < 10) v = '0' + v;
                    break;
                case 'i':
                    if (v > 59) v = 0;
                    if (v < 10) v = '0' + v;
                    break;
            }
            sel.value = v;
            sel.focus();
            handle();
        }
    });
    new Ext.util.ClickRepeater(downArrow, {
        handler: function () {
            var v = parseInt(sel.getAttribute('v'), 10);
            v--;
            switch (sel.getAttribute('ty')) {
                case 'y':
                    if (v < 1970) v = 1970;
                    break;
                case 'm':
                    if (v < 1) v = 12;
                    if (v < 10) v = '0' + v;
                    break;
                case 'd':
                    if (v < 1) v = getDays();
                    if (v < 10) v = '0' + v;
                    break;
                case 'h':
                    if (v < 0) v = 23;
                    if (v < 10) v = '0' + v;
                    break;
                case 'i':
                    if (v < 0) v = 59;
                    if (v < 10) v = '0' + v;
                    break;
            }
            sel.value = v;
            sel.focus();
            handle();
        }
    });
    var onFocus = function () {
        if (eid) {
            clearTimeout(eid);
            eid = null;
        }
        el.style.border = '1px solid #99BBE8';
        el.IsActive = true;
        this.style.backgroundColor = '#00008B';
        this.style.color = '#fff';
        sel = this;
    }
    var onBlur = function () {
        this.style.backgroundColor = 'transparent';
        this.style.color = '#000';
        clearTimer();
        handle();
        eid = setTimeout(function () {
            el.style.border = '1px solid #B5B8C8';
            el.IsActive = false;
            if (el.change_ && el.change_(el.getValue())) oldTime = el.getValue();
            else el.setValue(oldTime);
        }, 10);
    }
    var onKey = function (evt) {
        var key = evt.getKey();
        if (key == 13) sel.blur();
        key -= 48;
        if (key < 0 || key > 9) return;
        if (this.getAttribute('ty') == 'y') {
            if (buf.length == 4) buf = [];
        }
        else {
            if (buf.length == 2) return;
        }
        clearTimer();
        buf.push(key);
        if (this.getAttribute('ty') == 'y') {
            switch (buf.length) {
                case 1:
                    this.value = '000' + buf[0];
                    break;
                case 2:
                    this.value = '00' + buf.join('');
                    break;
                case 3:
                    this.value = '0' + buf.join('');
                    break;
                case 4:
                    this.value = buf.join('');
                    break;
            }
        }
        else {
            switch (buf.length) {
                case 1:
                    this.value = '0' + buf[0];
                    break;
                case 2:
                    this.value = buf.join('');
                    break;
            }
        }
        tid = setTimeout(handle, 2000);
    }
    //绑定事件
    for (var i = 0; i < objs.length; i++) {
        var e = Ext.get(objs[i]);
        e.on('focus', onFocus, objs[i]);
        e.on('blur', onBlur, objs[i]);
        e.on('keypress', onKey, objs[i]);
    }
    function handle() {
        switch (sel.getAttribute('ty')) {
            case 'y':
                switch (buf.length) {
                    case 1:
                        year.value = '200' + buf[0];
                        break;
                    case 2:
                        if (parseInt(buf.join(''), 10) > 50) year.value = '19' + buf.join('');
                        else year.value = '20' + buf.join('');
                        break;
                    case 3:
                        year.value = year.getAttribute('v');
                        break;
                    case 4:
                        year.value = buf.join('');
                        if (parseInt(year.value) < 1970) year.value = 1970;
                        break;
                }
                if (month.getAttribute('v') == '02') {
                    var days = getDays();
                    if (day.getAttribute('v') - days > 0) {
                        day.value = days;
                        day.setAttribute('v', days);
                    }
                }
                break;
            case 'm':
                if (parseInt(buf.join(''), 10) > 12) month.value = month.getAttribute('v');
                days = getDays();
                if (day.getAttribute('v') - days > 0) {
                    day.value = days;
                    day.setAttribute('v', days);
                }
                break;
            case 'd':
                if (parseInt(buf.join(''), 10) > getDays()) day.value = day.getAttribute('v');
                break;
            case 'h':
                if (parseInt(buf.join(''), 10) > 23) hour.value = hour.getAttribute('v');
                break;
            case 'i':
                if (parseInt(buf.join(''), 10) > 59) minute.value = minute.getAttribute('v');
                break;
        }
        sel.setAttribute('v', sel.value);
        buf = [];
    }
    function isLeap() {
        var v = year.getAttribute('v') * 1;
        return v % 4 == 0 && v % 100 != 0 || v % 400 == 0;
    }
    function getDays() {
        var v = month.value;
        if (v == '04' || v == '06' || v == '09' || v == '11')
            return 30;
        if (v == '02') return isLeap() ? 29 : 28;
        return 31;
    }
    function clearTimer() {
        if (tid) {
            clearTimeout(tid);
            tid = null;
        }
    }
    el.setValue = function (t) {
        t = t.split(' ');
        var d = t[0].split('-');
        t = t[1].split(':');
        year.value = d[0];
        year.setAttribute('v', d[0]);
        month.value = d[1];
        month.setAttribute('v', d[1]);
        day.value = d[2];
        day.setAttribute('v', d[2]);
        hour.value = t[0];
        hour.setAttribute('v', t[0]);
        minute.value = t[1];
        minute.setAttribute('v', t[1]);
        oldTime = el.getValue();
    }
    el.getValue = function (ms) {
        var t = year.value + '-' + month.value + '-' + day.value
            + ' ' + hour.value + ':' + minute.value;
        return ms == undefined ? t : Ext.Date.parseDate(t, 'Y-m-d H:i').getTime();
    }
    el.change_ = null;
    el.IsActive = false;
    //配置参数
    if (typeof config == 'object') {
        if (config.value != undefined) el.setValue(config.value);
        if (config.change != undefined) el.change_ = config.change;
    }

    return el;
}