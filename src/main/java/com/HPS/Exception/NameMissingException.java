package com.HPS.Exception;

public class NameMissingException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	String errMsg;
	
	public NameMissingException(String errMsg) {
		this.errMsg =errMsg;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
