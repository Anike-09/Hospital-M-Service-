package com.HPS.Exception;

public class IdNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	
	public IdNotFoundException(String errMsg) {
		super(errMsg);
	}

	

	
}
