package com.zx.bean;

public class Code {
	private String code;
	private String message;
	
	public Code(String name, String pwd) {
		super();
		this.code = name;
		this.message = pwd;
	}
	public Code() {
		super();

	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getCode()+getMessage();
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
}
