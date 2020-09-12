package com.codeone.project.exceptionhandler;

public class UsernameNotFoundException extends RuntimeException{

	public UsernameNotFoundException(String message) {
		super(message);
	}
	
}
