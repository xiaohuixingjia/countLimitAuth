package com.umpay.proxyservice;

/**
 * 返回码
 * 
 * @author huaxiaoqiang
 * @date 2016年6月15日 上午10:18:28
 */
public class RetCode {
	/**
	 * 成功
	 */
	public static final String SUCCESS = "0000";

	/**
	 * URL错误
	 */
	public static final String ERR_URL = "0101";

	/**
	 * funcode错误
	 */
	public static final String ERR_FUNCODE = "0102";

	/**
	 * datetime格式错误
	 */
	public static final String ERR_FORMAT_DT = "0103";

	/**
	 * merid格式错误
	 */
	public static final String ERR_FORMAT_MERID = "0104";

	/**
	 * transid格式错误
	 */
	public static final String ERR_FORMAT_TRANSID = "0105";

	/**
	 * count格式错误
	 */
	public static final String ERR_FORMAT_COUNT = "0106";

	/**
	 * querymonth格式错误
	 */
	public static final String ERR_FORMAT_QM = "0107";

	/**
	 * mobile格式错误
	 */
	public static final String ERR_FORMAT_MOB = "0108";

	/**
	 * sign格式错误
	 */
	public static final String ERR_FORMAT_SIGN = "0109";

	/**
	 * name格式错误
	 */
	public static final String ERR_FORMAT_NAME = "0110";

	/**
	 * identityNo格式错误
	 */
	public static final String ERR_FORMAT_ID = "0111";

	/**
	 * cardNo格式错误
	 */
	public static final String ERR_FORMAT_CARDNO = "0112";

	/**
	 * mobileAge格式错误
	 */
	public static final String ERR_FORMAT_MOBAGE = "0113";

	/**
	 * income格式错误
	 */
	public static final String ERR_FORMAT_INCOME = "0114";

	/**
	 * license格式错误
	 */
	public static final String ERR_FORMAT_LICENSE = "0115";

	/**
	 * risklevel错误
	 */
	public static final String ERR_FORMAT_RISKLEVEL = "0116";

	/**
	 * bankno格式错误
	 */
	public static final String ERR_FORMAT_BANKNO = "0117";

	/**
	 * card4No格式错误
	 */
	public static final String ERR_FORMAT_CARD4NO = "0118";

	/**
	 * 追溯周期格式错误
	 */
	public static final String ERR_FORMAT_PERIOD = "0119";

	/**
	 * cvv2格式错误
	 */
	public static final String ERR_FORMAT_CVV2 = "0120";

	/**
	 * 信用卡有效期格式错误
	 */
	public static final String ERR_FORMAT_ENDDATE = "0121";

	/**
	 * 
	 * transid重复
	 */
	public static final String TRANSID_DUP = "0201";

	/**
	 * merid没有配置
	 */
	public static final String MER_NOT_EXISTS = "0202";

	/**
	 * 手机号个数不匹配
	 */
	public static final String MOBILE_NUM_NOT_MATCH = "0203";

	/**
	 * 一次请求手机号大于100个
	 */
	public static final String MOBILE_NUM_GT_100 = "0204";

	/**
	 * 签名不正确
	 */
	public static final String ERROR_SIGN = "0205";

	/**
	 * license与数据库匹配错误
	 */
	public static final String ERROR_LICENSE = "0206";

	/**
	 * 超过频次限制
	 */
	public static final String EXCEED_FREQ_LIMIT = "0301";

	/**
	 * 超过（每日）次数限制
	 */
	public static final String EXCEED_TOTAL_LIMIT = "0302";

	/**
	 * 超过有效期限制
	 */
	public static final String EXCEED_DEADLINE_LIMIT = "0303";
	
	/**
	 * 超过总数限制
	 */
	public static final String EXCEED_TOTAL_COUNT_LIMIT = "0304";

	/**
	 * 查询超时
	 */
	public static final String REQ_TIMEOUT = "0401";

	/**
	 * 查无此号
	 */
	public static final String MOB_NOT_EXISTS = "0501";

	/**
	 * 输入参数不全
	 */
	public static final String ERROR_INPUT_INCOMPLETE = "0601";
	
	/**
	 * 错误的配置类型
	 */
	public static final String ERROR_INPUT_CONFIG_TYPE = "0602";

	/**
	 * 内部错误
	 */
	public static final String ERROR_OTHERS = "9999";

	/*  *//**
			 * tranid重复
			 */
	/*
	 * public static final String TRANSID_DUP = "0101";
	 *//**
		 * 商户号不存在
		 */
	/*
	 * public static final String MER_NOT_EXISTS = "0102";
	 * 
	 *//**
		 * 手机号个数不匹配
		 */
	/*
	 * public static final String MOBILE_NUM_NOT_MATCH = "0103";
	 *//**
		 * 一次请求手机号大于100个
		 */
	/*
	 * public static final String MOBILE_NUM_GT_100 = "0104";
	 * 
	 *//**
		 * 存在手机号格式不正确
		 */
	/*
	 * public static final String EXISTS_ERROR_FORMAT = "0105";
	 * 
	 * 
	 * 
	 *//**
		 * 签名不正确
		 */
	/*
	 * public static final String ERROR_SIGN = "0107";
	 *//**
		 * funcode不存在
		 */
	/*
	 * public static final String ERROR_FUNCODE = "0108";
	 *//**
		 * URL不正确
		 */
	/*
	 * public static final String ERROR_URL = "0109";
	 * 
	 *//**
		 * 超过限制
		 */
	/*
	 * public static final String EXCEED_LIMIT = "0110";
	 * 
	 *//**
		 * 访问数据访问层超时
		 */
	/*
	 * public static final String ERROR_OTHERS = "9999";
	 * 
	 *//**
		 * 访问YXT加密超时
		 *//*
		 * public static final String ERROR_ENCODE = "8888";
		 */

}
