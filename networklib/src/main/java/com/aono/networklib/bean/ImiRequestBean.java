package com.aono.networklib.bean;

import java.util.Map;

/**
 * Created by Aono on 2018/3/26.
 */

public class ImiRequestBean {

	private long timeStamp;

	private Map<String, String> map;

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
}
