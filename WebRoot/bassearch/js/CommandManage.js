/*
 *命令管理器
 */

Ext.define('js.CommandManage',{
	constructor:function(){
		return new CommandManage();
	}
});	

/**
 * 
 */
function CommandManage() {
	var isSel = false;
	
    this.Add = function (cmd) {      
        if (cmd.Type == '调整窗口尺寸') {
            execute(cmd);
            return true;
        }
        if (!_lines.MainLine && !(cmd.Type == '平铺显示' || cmd.Type == '添加曲线' || cmd.Type == '添加条件查询' || cmd.Type == '压缩曲线')) 
        	return false;
        if (cmd.Type == '选择曲线') {
            isSel = !isSel;
            execute(cmd);
            return true;
        }
        if (isSel) {
            if (cmd.Type == '设置主轴') execute(cmd);
            else return false;
        }
        else {
            return execute(cmd);
        }
        return true;
    };
    /**
     * 15-12-28周星陆添加管道剔野数据设置接口
     * 添加管道剔野数据
     * lower //下限
     * upper //上限
     */
    this.setPipeTiyes = function(lower, upper){
    	_lines.setPipeTiyes(lower, upper);
    };

    function execute(cmd) {
        //try {
            switch (cmd.Type) {
                case '添加曲线':
                    _lines.AddLine(cmd.Param);
                    break;
                case '放大曲线':
                    _lines.ExpandLine(cmd);
                    break;
                case '开始压缩曲线':
                	_lines.CompressingLine(cmd.fx,cmd.x);
                	break;
                case '压缩曲线':
                    _lines.CompressedLine(cmd.Begin,cmd.End,cmd.Id);
                    break;
                case '移动曲线':
                    _lines.Move(cmd);
                    break;
                case '缩放所有曲线':
                    _lines.ZoomAllLine();
                    break;
                case '设置主轴':
                    _lines.SetMainAxis(cmd.Id);
                    break;
                case '设置曲线颜色':
                    _lines.SetColor(cmd.Id, cmd.Color);
                    break;
                case '设置曲线宽度':
                    _lines.SetWidth(cmd.Id, cmd.Width);
                    break;
                case '显示曲线':
                    _lines.ShowLine(cmd.Id, cmd.Show);
                    break;
                case '设置曲线上下值':
                    _lines.SetMaxMinVal(cmd.Id, cmd.Max, cmd.Min);
                    break;
                case '移除曲线':
                    _lines.DelLine(cmd.Id);
                    break;
                case '同步曲线':
                    _lines.AsynLine();
                    break;
                case '曲线播放':
                    _lines.PlayLine(cmd.Param);
                    break;
                case '平铺显示':
                    _lines.TileShow(cmd.IsTile);
                    break;
                case '缩放当前曲线':
                    _lines.ZoomLine();
                    break;
                case '上移当前曲线':
                    _lines.MoveUpDown(1);
                    break;
                case '下移当前曲线':
                    _lines.MoveUpDown(-1);
                    break;
                case '放大当前曲线':
                    _lines.ZoomYLine(1);
                    break;
                case '缩小当前曲线':
                    _lines.ZoomYLine(-1);
                    break;
                case '上移所有曲线':
                    _lines.MoveAllUpDown(1);
                    break;
                case '下移所有曲线':
                    _lines.MoveAllUpDown(-1);
                    break;
                case '放大所有曲线':
                    _lines.ZoomAllYLine(1);
                    break;
                case '缩小所有曲线':
                    _lines.ZoomAllYLine(-1);
                    break;
                case '打印曲线':
                    _lines.Print();
                    break;
                case '选择曲线':
                    _lines.SelectLine(isSel);
                    break;
                case '撤销曲线':
                    _lines.Withdraw(true);
                    break;
                case '恢复曲线':
                    _lines.Withdraw(false);
                    break;
                case '调整窗口尺寸':
                    _lines.Resize(cmd.Width,cmd.Height);
                    break;
                case '选择曲线显示方式':
                    _lines.SetLineType(cmd.Id);
                    break;
                case '实时显示曲线':
                	_lines.RealShow();
                	break;
            	case '添加条件查询':
                	_lines.AddCondLine(cmd.Param,cmd.IsTile,cmd.isRelativeQuery);
                	break;
                case '显示原始数据':
                	_lines.ShowOrigin();
                	break;
                case '曲线剔野':
                	_lines.LineTiye(cmd.x1,cmd.x2,cmd.y1,cmd.y2);
                	break;
                case '统计分析':
                	_lines.ShowStats(cmd.Method,cmd.Tiye);
                	break;
            	case '添加标注':
            		_lines.AddTag();
            		break;
            	case '撤销剔野':
            		_lines.NoTiye();
            		break;
            	case '上移曲线':
            		_lines.MoveUpLine(cmd.Id,cmd.NewRec);
            		break;
            	case '下移曲线':
            		_lines.MoveDownLine(cmd.Id,cmd.NewRec);
            		break;
            }
            return true;
        /*
         * }
        catch (e) {
            Ext.Msg.show({ title: 'Js调试信息', msg: e, buttons: Ext.Msg.OK });
            return false;
        }
         */
    }
}