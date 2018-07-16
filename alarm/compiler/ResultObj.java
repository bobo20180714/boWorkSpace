package compiler;

public class ResultObj {		//分析结果基类
	private boolean resultFlag=false;		//分析成功与否的标志

	public boolean isResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}
	public void MergeResult(ResultObj another){
		this.resultFlag=this.resultFlag && another.isResultFlag();
	}
}
