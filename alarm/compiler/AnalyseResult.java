package compiler;

import org.antlr.runtime.tree.BaseTree;

public class AnalyseResult extends ResultObj {		//语法分析结果类
	private BaseTree analyseTree;		//语法分析树
	
	public BaseTree getAnalyseTree() {
		return analyseTree;
	}

	public void setAnalyseTree(BaseTree analyseTree) {
		this.analyseTree = analyseTree;
	}

}
