package com.umpay.test;

import java.util.Map;

import com.umpay.proxyservice.TaskHandler;

public class TestThread implements Runnable{
	private TaskHandler taskHandler;
	private Map<String, String> xmlMap;
	private int rundomTime;
	
	
	public TestThread(TaskHandler taskHandler, Map<String, String> xmlMap, int rundomTime) {
		super();
		this.taskHandler = taskHandler;
		this.xmlMap = xmlMap;
		this.rundomTime = rundomTime;
	}

	public TaskHandler getTaskHandler() {
		return taskHandler;
	}

	public void setTaskHandler(TaskHandler taskHandler) {
		this.taskHandler = taskHandler;
	}

	public int getRundomTime() {
		return rundomTime;
	}

	public void setRundomTime(int rundomTime) {
		this.rundomTime = rundomTime;
	}

	public Map<String, String> getXmlMap() {
		return xmlMap;
	}

	public void setXmlMap(Map<String, String> xmlMap) {
		this.xmlMap = xmlMap;
	}

	@Override
	public void run() {
		//	taskHandler.execute(xmlMap);
	}
	
	@SuppressWarnings("unused")
	private int sleepTime(){
		return (int)(Math.random()*rundomTime);
	}

}
