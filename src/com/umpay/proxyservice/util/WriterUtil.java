package com.umpay.proxyservice.util;

import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.bean.WriteInfoBean;

/**
 * 书写的常用工具类
 * 
 * @author xuxiaojia
 */
public class WriterUtil {
	private static Logger log = LoggerFactory.getLogger("WriterUtil");

	/**
	 * 写出信息到文件中
	 * 
	 * @param writeInfoBean
	 *            封装了写出信息的对象 不抛异常
	 */
	public static void writer(WriteInfoBean infoBean) {

		FileWriter writer = null;
		try {
			writer = new FileWriter(infoBean.getFullFileName(), infoBean.isAppend());
			writer.write(infoBean.getInfo());
			writer.flush();
		} catch (Exception e) {
			log.error("写出信息到文件异常" + infoBean.toString(), e);
		}finally{
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
