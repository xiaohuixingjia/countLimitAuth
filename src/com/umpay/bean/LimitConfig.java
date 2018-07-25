package com.umpay.bean;

import java.util.List;

import com.umpay.exception.BaseLimitException;

/**
 * 关于一个商户访问一个产品的整体配置规则bean
 * 
 * @author xuxiaojia
 */
public class LimitConfig {
	private List<BaseLimitBean> limitList;

	public List<BaseLimitBean> getLimitList() {
		return limitList;
	}

	public void setLimitList(List<BaseLimitBean> limitList) {
		this.limitList = limitList;
	}

	public LimitConfig(List<BaseLimitBean> limitList) {
		super();
		this.limitList = limitList;
	}

	@Override
	public String toString() {
		return "LimitConfig [limitList=" + limitList + "]";
	}
	
	public void checkLimit() throws BaseLimitException{
		for (BaseLimitBean baseLimitBean : limitList) {
			baseLimitBean.checkLimit();
		}
	}
	

}
