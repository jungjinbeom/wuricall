package com.wurigo.socialService.domain;


public class ResultData {
	private String code;
	private String message;
	private String accesstoken;
	
	
	@Override
	public String toString() {
		return "ResultData [getCode()=" + getCode() + ", getMessage()=" + getMessage() + ", getAccesstoken()="
				+ getAccesstoken() + "]";
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	
}
