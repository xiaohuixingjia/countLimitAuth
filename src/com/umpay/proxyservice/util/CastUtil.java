package com.umpay.proxyservice.util;

/**
 * 转换操作的工具类
 * 
 * @author o
 *
 */
public class CastUtil {

	/**
	 * 将对象转换为string类型，如果为空时则默认返回空字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String castString(Object obj) {
		return CastUtil.castString(obj, "");
	}

	/**
	 * 将对象转换为string类型，如果为空时，则返回默认值
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static String castString(Object obj, String defaultVlaue) {
		return obj == null ? defaultVlaue : String.valueOf(obj);
	}

	/**
	 * 转换为double类型，如果为空时或转换出现异常时默认返回0
	 * 
	 * @param obj
	 * @return
	 */
	public static double castDouble(Object obj) {
		return castDouble(obj, 0);
	}

	/**
	 * 转换为double类型，如果为空或转换出现异常时返回默认值
	 * 
	 * @param obj
	 * @param defaultVlaue
	 * @return
	 */
	public static double castDouble(Object obj, double defaultVlaue) {
		double doubleValue = defaultVlaue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					doubleValue = Double.parseDouble(strValue);
				} catch (NumberFormatException e) {
					doubleValue = defaultVlaue;
				}
			}
		}
		return doubleValue;
	}

	/**
	 * 转换为long类型,如果为空或转换出现异常时默认返回0
	 * 
	 * @param obj
	 * @return
	 */
	public static long castLong(Object obj) {
		return castLong(obj, 0);
	}

	/**
	 * 转换为long类型，如果为空或转换出现异常时返回默认值
	 * 
	 * @param obj
	 * @param defalueValue
	 * @return
	 */
	public static long castLong(Object obj, long defalueValue) {
		long longValue = defalueValue;
		if (obj != null) {
			String value = castString(obj);
			if (StringUtil.isNotEmpty(value)) {
				try {
					longValue = Long.parseLong(value);
				} catch (NumberFormatException e) {
					longValue = defalueValue;
				}
			}
		}
		return longValue;
	}

	/**
	 * 转换为int类型，如果对象为空或转换出现异常时，返回0
	 * 
	 * @param obj
	 * @return
	 */
	public static int castInt(Object obj) {
		return castInt(obj, 0);
	}

	/**
	 * 转换为int类型，如果对象为空或转换出现异常时，返回默认值
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static int castInt(Object obj, int defaultValue) {
		int intValue = defaultValue;
		if (obj != null) {
			String value = castString(obj);
			if (StringUtil.isNotEmpty(value)) {
				try {
					intValue = Integer.parseInt(value);
				} catch (NumberFormatException e) {
					intValue = defaultValue;
				}
			}
		}
		return intValue;
	}
	
	/**
	 * 转换为int类型，转型失败会抛出异常
	 * 
	 * @param obj
	 * @return
	 */
	public static int castIntError2throw(Object obj) {
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * 转换为布尔类型，如果对象为空或者转换过程中出现异常，返回false
	 * @param obj
	 * @return
	 */
	public static boolean castBoolean(Object obj){
		return castBoolean(obj,false);
	}

	/**
	 * 转换为布尔类型，如果对象为空或者转换过程中出现异常，返回默认值
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue=defaultValue;
        if(obj!=null){
        	String value=castString(obj);
        	if(StringUtil.isNotEmpty(value)){
        		try{
        			booleanValue=Boolean.parseBoolean(value);
        		}catch(Exception e){
        			booleanValue=defaultValue;
        		}
        	}
        }
		return booleanValue;
	}
	
}
