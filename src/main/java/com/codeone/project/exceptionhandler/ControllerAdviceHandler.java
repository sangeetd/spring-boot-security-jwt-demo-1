package com.codeone.project.exceptionhandler;

import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceHandler {

	@ExceptionHandler(value = {UsernameNotFoundException.class})
	public ResponseEntity<Object> handleUsernameNotFound(UsernameNotFoundException e){
		
		System.out.println("ControllerAdviceHandler: invoked");
		
		HttpStatus status=HttpStatus.NOT_FOUND;
		
		ExceptionPayload payload=new ExceptionPayload(e.getMessage(), new Date(System.currentTimeMillis()), status);
		
		return new ResponseEntity<Object>(payload, status);
		
	}
	
}
