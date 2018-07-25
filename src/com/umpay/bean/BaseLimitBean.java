package com.umpay.bean;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.umpay.exception.BaseLimitException;
import com.umpay.proxyservice.Constant;
import com.umpay.proxyservice.TaskHandler;

/**
 * 每秒超频和每日超过限制次数配置bean的基础配置
 * 
 * @author xuxiaojia
 */
public abstract class BaseLimitBean {
	/**
	 * 创建总流量限制的bean信息
	 */
	public static final int TOTAL_LIMIT_BEAN = 10;
	/**
	 * 创建每日限制的bean信息
	 */
	public static final int DAY_LIMIT_BEAN = 5;
	/**
	 * 创建每秒限制的bean信息
	 */
	public static final int SECOND_LIMIT_BEAN = 1;
	/**
	 * 初始化 atomicInteger技术器时的初始计数
	 */
	public static final int INIT_VALUE_4_ATOMIC_INTEGER = 0;
	/**
	 * 不限制
	 */
	public static final int NO_LIMIT = -1;
	/* 此限制策略对应的map中的key */
	private String mapKey;
	/* 最大限制数量 */
	private Integer MaxLimit;
	/* 当前限制的配置对象所属的类型 */
	private Integer limitType;
	/* 可以支持并发访问的计数对象 */
	private AtomicInteger atomicInteger;
	/* 当前时间 */
	private Date currDate;

	public BaseLimitBean(Integer limitType) {
		super();
		this.limitType = limitType;
	}

	public Integer getLimitType() {
		return limitType;
	}

	public void setLimitType(Integer limitType) {
		this.limitType = limitType;
	}

	public String getMapKey() {
		return mapKey;
	}

	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}

	public Integer getMaxLimit() {
		return MaxLimit;
	}

	public void setMaxLimit(Integer maxLimit) {
		MaxLimit = maxLimit;
	}

	public AtomicInteger getAtomicInteger() {
		return atomicInteger;
	}

	public void setAtomicInteger(AtomicInteger atomicInteger) {
		this.atomicInteger = atomicInteger;
	}

	public Date getCurrDate() {
		return currDate;
	}

	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
	}

	@Override
	public String toString() {
		return "BaseLimitBean [mapKey=" + mapKey + ", MaxLimit=" + MaxLimit + ", limitType=" + limitType
				+ ", atomicInteger=" + atomicInteger + ", currDate=" + currDate + "]";
	}

	
	/**
	 * 将计数器的所记得数值设置为初始化的数值
	 */
	public void clearLimit() {
		atomicInteger.set(BaseLimitBean.INIT_VALUE_4_ATOMIC_INTEGER);
	}

	/**
	 * 判断是否超过限制，如果超过了限制，则抛出自定义异常需要上层捕获
	 * 
	 * 对请求判断，对响应不判断
	 * 
	 * @throws BaseLimitException
	 */
	public void checkLimit() throws BaseLimitException {
		if (TaskHandler.QUERY_TYPE.get()==Constant.QUERY_TYPE_RES) {
			return;
		}
		// 如果配置为不限制，则直接返回不进行校验
		if (this.MaxLimit == NO_LIMIT) {
			return;
		}
		if (have2clearLimit()) {
			clearLimit();
			recordDateAgain();
		}
		int curLimitCount = this.getAtomicInteger().incrementAndGet();
		logLimit(curLimitCount);
		if (haveMoreThanLimit(curLimitCount)) {
			throwMoreThanLimitException(curLimitCount);
		}
	}

	protected abstract void logLimit(int curLimitCount);

	/**
	 * 重新记录当前时间
	 */
	private void recordDateAgain() {
		this.setCurrDate(new Date());
	}

	/**
	 * 是否超过限制
	 * 
	 * @param curLimitCount
	 *            当前请求累计的次数
	 * @return
	 */
	protected boolean haveMoreThanLimit(int curLimitCount) {
		if (curLimitCount > this.getMaxLimit()) {
			return true;
		}
		return false;
	}

	/**
	 * 获取当先配置的剩余访问笔数
	 * @return
	 */
	public int curOverplus(){
		int overplus=this.getMaxLimit()-this.getAtomicInteger().get();
		return overplus>0?overplus:0;
	}
		
	/**
	 * @param curLimitCount
	 *            当前请求累计的次数 抛出超过限制的自定义异常
	 */
	protected abstract void throwMoreThanLimitException(int curLimitCount) throws BaseLimitException;

	/**
	 * 是否需要清空当前所累积的数量 (交由子类来实现)
	 * 
	 * @return
	 */
	protected abstract boolean have2clearLimit();
}
