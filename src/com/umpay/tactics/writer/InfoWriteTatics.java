package com.umpay.tactics.writer;

import java.io.File;

/**
 * 写信息策略
 * 
 * @author xuxiaojia
 */
public interface InfoWriteTatics {
	/**
	 * 写策略初始化
	 */
	public void init(File file,Object... obs) throws Exception;

	/**
	 * 写入
	 * 
	 * @param lineInfo 行信息
	 * @param lineNum 当前行数
	 */
	public void writeIn(String lineInfo,int lineNum) throws Exception;

	/**
	 * 结束
	 */
	public void end() throws Exception;
}
