package com.umpay.proxyservice.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 消息资源文件信息的常用工具类
 * 
 * @author o
 *
 */
public final class PropUtil {
	private static final String RESOURCE_FLOD="resource/";

	/**
	 * 根据文件名加载属性文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties loadProp(String fileName) {
		Properties props = null;
		InputStream iStream = null;
		try {
			iStream = ClassUtil.getClassLoader().getResourceAsStream(fileName);
			if (iStream == null) {
				iStream = ClassUtil.getClassLoader().getResourceAsStream(RESOURCE_FLOD+fileName);
				if(iStream==null){
					throw new Exception(fileName + " file is not found");
				}
			}
			props = new Properties();
			props.load(iStream);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return props;
	}

	/**
	 * 根据配置的key名获取字符类型的value （可指定默认，在没有找到对应key时返回默认值）
	 * @param props
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Properties props,String key,String defaultValue){
		String value=defaultValue;
		if(props.containsKey(key)){
			value=props.getProperty(key);
		}
		return value;
	}
	
	/**
	 * 根据配置的key名获取字符类型的value （没有找到时返回空字符串）
	 * @param props
	 * @param key
	 * @return
	 */
	public static String getString(Properties props,String key){
		return getString(props, key, "");
	}
}
