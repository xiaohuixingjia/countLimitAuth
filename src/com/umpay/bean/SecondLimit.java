package com.umpay.bean;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.exception.MoreThanSecondLimitException;
import com.umpay.proxyservice.util.DateUtil;

/**
 * 每秒限制规则
 * @author xuxiaojia
 */
public class SecondLimit extends BaseLimitBean {
	private static final Logger log = LoggerFactory.getLogger(SecondLimit.class);


	public SecondLimit(int secondLimitBean) {
		super(secondLimitBean);
	}

	@Override
	protected boolean have2clearLimit() {
		return DateUtil.isNotSameDayTime(this.getCurrDate(), new Date());
	}

	@Override
	protected void throwMoreThanLimitException(int curLimitCount) throws MoreThanSecondLimitException {
		throw new MoreThanSecondLimitException(this.getMapKey(),curLimitCount, this.getMaxLimit());
	}

	@Override
	protected void logLimit(int curLimitCount) {
		log.info(this.getMapKey()+"当前秒已达"+curLimitCount+"次访问，tps限制为："+this.getMaxLimit());
	}

}
