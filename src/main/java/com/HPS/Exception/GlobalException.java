package com.HPS.Exception;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.HPS.Controller.PatientController;

@RestControllerAdvice
public class GlobalException {
	
	private static final Logger logger = Logger.getLogger(PatientController.class);
	
	@ExceptionHandler(IdNotFoundException.class)
	// @ResponseStatus(HttpStatus.NOT_FOUND)
	public String runtimeExceptionHandler(IdNotFoundException ex) {
		logger.error(ex.getMessage());
		return ex.getMessage();
		
	}
	
	@ExceptionHandler(NameMissingException.class)
	public String NameMissingException(NameMissingException ex) {
		logger.error("NameMissingException occured "+ex);
		return ex.errMsg;
		
	}
	
	@ExceptionHandler(SomeThingWentWrongException.class)
	public String someThingWentWrongException(SomeThingWentWrongException ex) { 	
		logger.error("gavs"+ex.getMessage());
		return ex.getMessage();
		
	}
	
//	@ExceptionHandler(NoResourceFoundException.class)
//	public String noResourceFoundException(NoResourceFoundException ex) {	
//		logger.error(ex.getMessage());
//		return ex.getMessage();
//		
//	}
	

}
