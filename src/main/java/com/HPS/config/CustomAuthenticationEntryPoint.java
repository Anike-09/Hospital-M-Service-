package com.HPS.config;

import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationEntryPoint.class);  
	  
	   @Override  
	   public void commence(HttpServletRequest request, HttpServletResponse response,  
	        AuthenticationException authException) throws IOException, ServletException {  
	      logger.error("Unauthorized error: ", authException.getMessage());  
	      logger.error("Request URI: ", request.getRequestURI());  
	      logger.error("Authorization header: ", request.getHeader("Authorization"));  
	  
	      // Set the response status code to 401 (Unauthorized)  
	      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  
	  
	      // Set the response content type to JSON  
	      response.setContentType("application/json");  
	  
	      // Write the error message to the response output stream  
	      PrintWriter out = response.getWriter();  
	      out.println("{\"error\":\"Unauthorized\",\"message\":\"" + authException.getMessage() + "\"}");  
	      out.flush();  
	   }  
}