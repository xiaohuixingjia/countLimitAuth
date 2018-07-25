package com.umpay.tactics.fileAnalz;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.tactics.writer.InfoWriteTatics;

/**
 * 下一步操作的抽象策略实现
 * 
 * @author xuxiaojia
 */
public abstract class AnalzFileAbstractTatics implements FileAnalzTactics {
	private static final Logger log = LoggerFactory.getLogger(AnalzFileAbstractTatics.class);
	private static final String CHARSET_NAME = "utf-8";

	@Override
	public void analz(File file, InfoWriteTatics writeTatics,Object... obs) throws Exception {
		Scanner sc =null;
		try {
			log.info("读入文件的文件全路径：" + file.getAbsolutePath());
			// 初始化
			sc= new Scanner(new FileInputStream(file), CHARSET_NAME);
			writeTatics.init(file,obs);
			int linuNum = 0;
			// 逐行读取解析
			while (sc.hasNextLine()) {
				String thisLine = sc.nextLine();
				//行自加并打印当前读取行日志
				linuNum++;
				if (isOk(thisLine, linuNum)) {
					// 转换
					thisLine = lineInfoTransfer(thisLine);
					// 写入
					writeTatics.writeIn(thisLine, linuNum);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(file.getAbsolutePath()+" 文件解析失败",e);
		}finally {
			if (sc != null) {
				try {
					sc.close();
				} catch (Exception e) {
					log.error("文件流关闭失败",e);
				}
			}
			writeTatics.end();
		}
	}

	/**
	 * 转换行
	 * @param thisLine
	 * @return
	 */
	protected String lineInfoTransfer(String thisLine) {
		return thisLine;
	}

	/**
	 * 默认不为空则满足，如果子类策略有不同要求可以重写次方法
	 * 
	 * @param lineString
	 *            当前行内容
	 * @param linuNum
	 *            当前行数
	 * @return
	 */
	protected boolean isOk(String lineString, int linuNum) {
		if (StringUtils.isNotEmpty(lineString))
			return true;
		else
			return false;
	}

}
