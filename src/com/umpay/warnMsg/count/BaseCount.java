package com.umpay.warnMsg.count;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计数器超类
 * 
 * @author xuxiaojia
 */
public abstract class BaseCount {
	/*
	 * 实时计数对象
	 */
	private AtomicInteger actualNum = new AtomicInteger(0);
	/*
	 * 数量上限
	 */
	private int maxLimit;

	public int getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(int maxLimit) {
		this.maxLimit = maxLimit;
	}

	/**
	 * 增加数量
	 * @return
	 */
	public int increaseNum(){
		int num = actualNum.getAndIncrement();
		if(num==maxLimit){
			reachLimit(num);
			actualNum.set(0);
		}
		return num;
	}
	/**
	 * 达到上限操作，由子类实现 
	 * @param num 达限数量
	 */
	public abstract void reachLimit(int num);
}
