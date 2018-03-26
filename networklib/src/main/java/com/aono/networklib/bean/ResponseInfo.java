package com.aono.networklib.bean;

/**
 * Created by Aono on 2018/3/26.
 *
 * 返回结果类
 */

public class ResponseInfo<T> {

	private int code;
	private T data;
	private String msg;
	private long responseStamp;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getResponseStamp() {
		return responseStamp;
	}

	public void setResponseStamp(long responseStamp) {
		this.responseStamp = responseStamp;
	}

	@Override
	public String toString() {
		return "ResponseInfo{" +
				"code=" + code +
				", data=" + data +
				", msg='" + msg + '\'' +
				", responseStamp=" + responseStamp +
				'}';
	}
}
