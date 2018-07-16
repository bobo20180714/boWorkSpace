package compiler;

import org.antlr.runtime.tree.BaseTree;

public class ParseResult {		//语法分析结果类
	private boolean resultFlag=false;		//语法分析成功与否的标志
	private BaseTree ResultTree;
	
	public boolean isResultFlag() {
		return resultFlag;
	}
	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}
	public BaseTree getResultTree() {
		return ResultTree;
	}
	public void setResultTree(BaseTree resultTree) {
		ResultTree = resultTree;
	}

}
