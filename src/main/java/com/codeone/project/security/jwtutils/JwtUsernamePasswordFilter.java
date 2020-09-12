package com.codeone.project.security.jwtutils;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.codeone.project.exceptionhandler.UsernameNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUsernamePasswordFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authManager;

	private JwtSecretKey secretKey;
	
	public JwtUsernamePasswordFilter(AuthenticationManager authManager, JwtSecretKey secretKey) {
		this.authManager = authManager;
		this.secretKey=secretKey;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, 
			HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			
			JwtRequestPayload requestPayload=new ObjectMapper()
					.readValue(request.getInputStream(), JwtRequestPayload.class);
			
			Authentication auth=new UsernamePasswordAuthenticationToken(
					requestPayload.getUsername(), requestPayload.getPassword());
			
			Authentication authResult=authManager.authenticate(auth);
			
			return authResult;
			
			
		}catch(Exception e) {
			System.out.println("JwtUsernamePasswordFilter: username-password validation filter exception "+e.getMessage());
			throw new UsernameNotFoundException(e.getMessage());
		}
		
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, 
			HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		System.out.println("JwtUsernamePasswordFilter: Secret key found: "+secretKey.getSecretKey());
		
		String token=Jwts.builder()
				.setSubject(authResult.getName())
				.claim("authorities", authResult.getAuthorities())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*3))
				.signWith(SignatureAlgorithm.HS512, secretKey.getSecretKey().getBytes()) //"SomeSecuredKeyHere"
				.compact();
		
		System.out.println("token generated: "+token);
		
		response.addHeader("Authorization", "Bearer "+token);
		
		
	}

	
}
