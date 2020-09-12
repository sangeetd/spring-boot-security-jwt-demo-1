package com.codeone.project.security.jwtutils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtSecretKey {

	@Value("${project.jwt.secure-key}")
	private String projectSecureKey;
	
	public String getSecretKey() {
		return projectSecureKey;
	}
	
}
