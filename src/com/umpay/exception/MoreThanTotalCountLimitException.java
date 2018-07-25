package com.umpay.exception;

import com.umpay.proxyservice.RetCode;

/**
 * 超过总数限制
 * @author xuxiaojia
 */
public class MoreThanTotalCountLimitException extends BaseLimitException {
	private static final long serialVersionUID = -1146970007624863443L;
	/**
	 * 默认的超过每秒限制的提示error信息
	 */
	public static final String TOTAL_COUNT_LIMIT_ERR_INFO="超过了总数限制次数";

	public MoreThanTotalCountLimitException(String key, Integer curLimitCount, Integer limitAmount, String errorMsg,String errorCode) {
		super(key, curLimitCount, limitAmount, errorMsg,errorCode);
	}

	public MoreThanTotalCountLimitException(String key, Integer curLimitCount, Integer limitAmount) {
		super(key, curLimitCount, limitAmount,MoreThanTotalCountLimitException.TOTAL_COUNT_LIMIT_ERR_INFO,RetCode.EXCEED_TOTAL_COUNT_LIMIT);
	}

}
