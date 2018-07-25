package com.umpay.exception;

import com.umpay.proxyservice.RetCode;

/**
 * 超过每秒限制次数的异常
 * @author xuxiaojia
 */
public class MoreThanSecondLimitException extends BaseLimitException {
	/**
	 * 默认的超过每秒限制的提示error信息
	 */
	public static final String SECOND_LIMIT_ERR_INFO="超过了每秒限制次数";
	private static final long serialVersionUID = -8044891424632138256L;

	public MoreThanSecondLimitException(String key, Integer curLimitCount, Integer limitAmount, String errorMsg,String errorCode) {
		super(key, curLimitCount, limitAmount, errorMsg,errorCode);
	}

	public MoreThanSecondLimitException(String key, Integer curLimitCount, Integer limitAmount) {
		super(key, curLimitCount, limitAmount,MoreThanSecondLimitException.SECOND_LIMIT_ERR_INFO,RetCode.EXCEED_FREQ_LIMIT);
	}

}
