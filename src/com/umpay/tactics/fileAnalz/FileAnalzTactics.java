package com.umpay.tactics.fileAnalz;

import java.io.File;

import com.umpay.tactics.writer.InfoWriteTatics;

/**
 * 文件解析策略
 * 
 * @author xuxiaojia
 */
public interface FileAnalzTactics {
	/**
	 * * 解析请求信息中的文件内容
	 * 
	 * @param queryContent
	 *            封装者请求信息的对象
	 * @param writeTatics
	 *            文件内容写出策略
	 * @return 返回文件的解析策略
	 * @throws Exception
	 */
	public void analz(File file, InfoWriteTatics writeTatics,Object... obs ) throws Exception;
}
