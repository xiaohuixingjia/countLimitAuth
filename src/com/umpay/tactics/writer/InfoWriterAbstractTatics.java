package com.umpay.tactics.writer;

/**
 * 信息写出的策略抽象实现
 * 
 * @author xuxiaojia
 */
public abstract class InfoWriterAbstractTatics implements InfoWriteTatics {
	/* 当前行 */
	protected int currentLineNum;

	public int getCurrentLineNum() {
		return currentLineNum;
	}

	public void setCurrentLineNum(int currentLineNum) {
		this.currentLineNum = currentLineNum;
	}

}
