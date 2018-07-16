package compiler;

public class CompilerInfoUnit {			//编译信息单元
	private int Type;		//信息类型
	private int Line;		//产生信息的源代码行号
	private int Row;		//产生信息的源代码列号
	private String Text;	//信息内容
	
	public String toString()
	{
		return getText();
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	public int getLine() {
		return Line;
	}
	public void setLine(int line) {
		Line = line;
	}
	public int getRow() {
		return Row;
	}
	public void setRow(int row) {
		Row = row;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
}
