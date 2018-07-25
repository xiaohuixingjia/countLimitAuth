package com.umpay.tactics.writer.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.proxyservice.util.CastUtil;
import com.umpay.tactics.writer.InfoWriterAbstractTatics;

/**
 * 总数限制写入内存策略
 * 
 * @author xuxiaojia
 */
public class TotalNumMemoryWriter extends InfoWriterAbstractTatics {
	private static final Logger log = LoggerFactory.getLogger(TotalNumMemoryWriter.class);

	/*
	 * 总数
	 */
	private Integer totalNum;
	/*
	 * 文件名
	 */
	private String fileName;

	@Override
	public void init(File file, Object... obs) throws Exception {
		totalNum = null;
		fileName = file.getAbsolutePath();
	}

	@Override
	public void writeIn(String lineInfo, int lineNum) throws Exception {
		try {
			totalNum = CastUtil.castIntError2throw(lineInfo);
		} catch (Exception e) {
			log.error("当前总数限制文件：" + fileName + " 的第" + lineNum + "行转换int类型出现异常，当前行内容为：" + lineInfo, e);
		}
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	@Override
	public void end() throws Exception {

	}

}
