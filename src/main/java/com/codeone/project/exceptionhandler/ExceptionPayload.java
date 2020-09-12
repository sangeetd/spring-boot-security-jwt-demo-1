package com.codeone.project.exceptionhandler;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ExceptionPayload {
	
	private String message;
	private Date timestamp;
	private HttpStatus status;
	
	public ExceptionPayload(String message, Date timestamp, HttpStatus status) {
		super();
		this.status=status;
		this.message = message;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
	
	
}
