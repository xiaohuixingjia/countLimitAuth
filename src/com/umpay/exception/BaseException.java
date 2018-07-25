package com.umpay.exception;

/**
 * 银联商务工程自定义异常 父类
 * 
 * @author xuxiaojia
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = 3702923111549541829L;
	/* 错误码 */
	private String errorCode;
	/* 错误信息 */
	private String errorMsg;
	/* 原异常 */
	private Exception exception;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public BaseException(String errorCode, String errorMsg) {
		super(errorCode + errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public BaseException(String errorCode, String errorMsg, Exception exception) {
		super(errorCode + errorMsg, exception);
		this.exception = exception;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
}
