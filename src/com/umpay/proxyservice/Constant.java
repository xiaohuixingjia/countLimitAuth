package com.umpay.proxyservice;

/**
 * 常量
 * 
 * @author xuxiaojia
 */
public class Constant {

	public static final String NAME = "name";
	public static final String CARD_NO = "cardno";
	public static final String MOBILE = "mobile";

	public static final String TOKEN = "token";

	public static final String PY_RESULT = "result";
	public static final String UMPAY_RESULT = "umpayResult";

	public static final String PY_MSG = "msg";

	public static final String PRODUCT_CODE = "Pck3c001";

	public static final String DATA = "data";

	public static final String UMPAY_NAME = "name";
	public static final String UMPAY_IDENTITY = "identityNo";
	public static final String UMPAY_MOBILE = "mobileid";

	public static final String LOG_SEPARATOR = ",";
	public static final String LOG_PARAM_SEPARATOR = "&";
	/**
	 * 消息资源文件的values分隔符
	 */
	public static final String PROP_VALUE_SEPARATOR = ";";

	public static final String IDCARD_REG = "\\d{15}|\\d{18}|\\d{17}(\\d|X|x)";

	public static final String MOBILE_REG = "\\d{11}";

	public static final String UMP_NO_RECORD = "0";

	public static final String UMP_MATCH = "2";

	public static final String UMP_NO_MATCH = "1";

	/**
	 * 身份证+移动手机号验证
	 */
	public static final String CHECK_TYPE_CHINA_MOBILE = "01";
	/**
	 * 身份证+全网手机号验证
	 */
	public static final String CHECK_TYPE_ALL_MOBILE = "02";

	public static final String MOBILE_CHINA_MOBILE = "1";
	public static final String MOBILE_CHINA_UNICOM = "2";
	public static final String MOBILE_CHINA_TELECOM = "3";
	public static final String MOBILE_NOT_MATCH = "0";

	public static final String QUERYTYPE = "queryType";
	/**
	 * 新的一行
	 */
	public static final String NEW_LINE = "\r\n";
	/**
	 * 配置信息修改
	 */
	public static final String CONFIG_ALTER = "/configAlter";
	/**
	 * 清空当前配置的流量
	 */
	public static final String CLEAR_CUR_FLUS = "/clearCurFlus";
	/**
	 * 配置信息打印
	 */
	public static final String CONFIG_WRITER = "/configWriter";
	
	
	
	/**
	 * 查得请求url
	 */
	public static final String LIMIT_RES = "/limitRes";
	
	
	/**
	 * 请求类型
	 */
	public static final Integer   QUERY_TYPE_REQ=0;
	/**
	 * 查得类型
	 */
	public static final Integer   QUERY_TYPE_RES=1;
	
	
	public static final String TRUE="true";
}
