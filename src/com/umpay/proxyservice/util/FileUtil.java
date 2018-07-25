package com.umpay.proxyservice.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作常用工具类
 * 
 * @author xuxiaojia
 */
public class FileUtil {
	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);


	/**
	 * 移动指定文件夹内的全部文件
	 *
	 * @param fromDir
	 *            要移动的文件目录
	 * @param toDir
	 *            目标文件目录
	 * @param deleteOld
	 *            删除就得文件
	 * @throws Exception
	 */
	public static void fileMove(String from, String to, boolean deleteOld) throws Exception {
		try {
			File dir = new File(from);
			// 文件一览
			File[] files = dir.listFiles();
			if (files == null)
				return;
			// 目标
			File moveDir = new File(to);
			if (!moveDir.exists()) {
				moveDir.mkdirs();
			}
			// 文件移动
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					fileMove(files[i].getPath(), to + File.separator + files[i].getName(), deleteOld);
					// 成功，删除原文件
					if (deleteOld) {
						files[i].delete();
					}
				}
				File moveFile = new File(moveDir.getPath() + File.separator + files[i].getName());
				// 目标文件夹下存在的话，删除
				if (moveFile.exists()) {
					if (deleteOld) {
						moveFile.delete();
					}
				}
				files[i].renameTo(moveFile);
			}
			log.info("文件夹："+from+" 中的文件已转移到："+to+" 文件夹中");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 传入路径不存在就创建文件夹
	 * 
	 * @param generFilePath
	 */
	public static void generDir(String generFilePath) {
		File file = new File(generFilePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

}
