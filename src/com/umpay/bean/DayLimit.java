package com.umpay.bean;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.exception.MoreThanDayLimitException;
import com.umpay.proxyservice.util.DateUtil;

/**
 * 每日限制规则
 * 
 * @author xuxiaojia
 */
public class DayLimit extends BaseLimitBean {
	private static final Logger log = LoggerFactory.getLogger(DayLimit.class);

	public DayLimit(int dayLimitBean) {
		super(dayLimitBean);
	}

	@Override
	protected boolean have2clearLimit() {
		return DateUtil.isNotSameDay(this.getCurrDate(), new Date());
	}

	@Override
	protected void throwMoreThanLimitException(int curLimitCount) throws MoreThanDayLimitException {
		throw new MoreThanDayLimitException(this.getMapKey(), curLimitCount, this.getMaxLimit());
	}

	@Override
	protected void logLimit(int curLimitCount) {
		log.info(this.getMapKey() + "当前日已达" + curLimitCount + "次访问，每日限制为：" + this.getMaxLimit());
	}

}
