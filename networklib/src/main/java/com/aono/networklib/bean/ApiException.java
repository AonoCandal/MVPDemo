package com.aono.networklib.bean;

/**
 * Created by Aono on 2018/3/26.
 */

public class ApiException extends Exception {

	private int code;
	private long timeStamp;

	public ApiException(String errorMsg, int code, long timeStamp) {
		super(errorMsg);
		this.code = code;
		this.timeStamp = timeStamp;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
