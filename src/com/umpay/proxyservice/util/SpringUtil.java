package com.umpay.proxyservice.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 获得spring 实例
 * @author huaxiaoqiang
* @date 2016年6月15日 上午10:28:56
 */
public class SpringUtil {
	private ApplicationContext ctx_producer = null;
	private static SpringUtil instance = null;
	private SpringUtil(){
			if(ctx_producer == null){
				String[] configLocations = new String[] {"applicationContext.xml"};
				ctx_producer = new ClassPathXmlApplicationContext(configLocations);
			}
	}

	public ApplicationContext getContext() {
		return ctx_producer;
	}
	
	public static SpringUtil getInstance(){
		if(instance == null)instance = new SpringUtil();
		return instance;
	}
}
