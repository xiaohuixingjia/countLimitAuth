package com.umpay.exception;

import com.umpay.proxyservice.RetCode;

/**
 * 超过每日限制的exception
 * 
 * @author xuxiaojia
 */
public class MoreThanDayLimitException extends BaseLimitException {
	/**
	 * 默认的超过每日限制的提示error信息
	 */
	public static final String DAY_LIMIT_ERR_INFO="超过了每日限制次数";
	public MoreThanDayLimitException(String key, Integer curLimitCount, Integer limitAmount, String errorMsg,String errorCode) {
		super(key, curLimitCount, limitAmount, errorMsg,errorCode);
	}

	public MoreThanDayLimitException(String key, Integer curLimitCount, Integer limitAmount) {
		super(key, curLimitCount, limitAmount,MoreThanDayLimitException.DAY_LIMIT_ERR_INFO,RetCode.EXCEED_TOTAL_LIMIT);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1856302948111470475L;

}
