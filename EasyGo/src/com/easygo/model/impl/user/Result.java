package com.easygo.model.impl.user;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private Mine result;
	
	
	
	public Result(String code, Mine result) {
		super();
		this.code = code;
		this.result = result;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Mine getResult() {
		return result;
	}
	public void setResult(Mine result) {
		this.result = result;
	}

}
