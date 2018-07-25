package com.umpay.bean;

/**
 * 写出信息的bean
 * 
 * @author xuxiaojia
 */
public class WriteInfoBean {
	/* 写出的文件名称 */
	private String fileName;
	/* 写出的文件路径 */
	private String filePath;
	/* 写出的文件内容 */
	private String info;
	/* 是否追加内容到文件中 true为追加，false为覆盖 */
	private boolean isAppend;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isAppend() {
		return isAppend;
	}

	public void setAppend(boolean isAppend) {
		this.isAppend = isAppend;
	}

	/**
	 * 创建写出信息的bean对象
	 * 
	 * @param fileName
	 *            写出的文件名
	 * @param filePath
	 *            写出的文件路径
	 * @param info
	 *            写出的信息
	 */
	public WriteInfoBean(String fileName, String filePath, String info) {
		this(fileName, filePath, info,false);
	}

	/**
	 * 获取写出文件的全路径文件名
	 */
	public String getFullFileName() {
		return this.filePath + this.fileName;
	}

	/**
	 * 创建写出信息的bean对象
	 * 
	 * @param fileName
	 *            写出的文件名
	 * @param filePath
	 *            写出的文件路径
	 * @param info
	 *            写出的信息
	 * @param isAppend
	 *            是否追加信息 true为追加，false为覆盖
	 */
	public WriteInfoBean(String fileName, String filePath, String info, boolean isAppend) {
		this.fileName = fileName;
		this.filePath = filePath;
		this.info = info;
		this.isAppend = isAppend;
	}

	@Override
	public String toString() {
		return "文件名全路径:"+getFullFileName()+" 写出信息："+this.info+" 是否追加："+isAppend;
	}
	
	

}
