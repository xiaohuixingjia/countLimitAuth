package com.umpay.exception;

/**
 * 超过设定限制的访问次数异常
 * 
 * @author xuxiaojia
 */
public class BaseLimitException extends Exception {
	private static final long serialVersionUID = -1856302948111470475L;

	/* map中对应的key */
	private String key;
	/* 当前已经访问的次数 */
	private Integer curLimitCount;
	/* 配置的限制数量 */
	private Integer limitAmount;
	/* 相应异常对应的错误码 */
	private String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getCurLimitCount() {
		return curLimitCount;
	}

	public void setCurLimitCount(Integer curLimitCount) {
		this.curLimitCount = curLimitCount;
	}

	public Integer getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(Integer limitAmount) {
		this.limitAmount = limitAmount;
	}

	public BaseLimitException(String key, Integer curLimitCount, Integer limitAmount) {
		this.key = key;
		this.curLimitCount = curLimitCount;
		this.limitAmount = limitAmount;
	}

	public BaseLimitException(String key, Integer curLimitCount, Integer limitAmount, String errorMsg,
			String errorCode) {
		super(errorMsg);
		this.key = key;
		this.curLimitCount = curLimitCount;
		this.limitAmount = limitAmount;
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return this.getMessage()+" [key=" + key + ", 当前请求次数=" + curLimitCount + ", 限制次数=" + limitAmount
				+ ", 错误码=" + errorCode + "]";
	}
	
	

}
