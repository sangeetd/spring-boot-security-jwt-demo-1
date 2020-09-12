package com.codeone.project.security.jwtutils;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codeone.project.security.ProjectUserDetails;
import com.codeone.project.security.ProjectUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component
public class JwtOncePerRequestFilter extends OncePerRequestFilter{

	@Autowired
	private JwtSecretKey secretKey;
	
	@Autowired
	private ProjectUserDetailsService userDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			
			System.out.println("JwtOncePerRequestFilter: Secret key found: "+secretKey.getSecretKey());
			
			String authHeader=request.getHeader("Authorization");
			
			if(authHeader.startsWith("Bearer ")) {
				
				String token=authHeader.replace("Bearer ", "");
				
				Jws<Claims> claim=Jwts.parser()
				.setSigningKey("SomeSecuredKeyHere".getBytes())
				.parseClaimsJws(token);
				
				Claims claimBody=claim.getBody();
				
				String username=claimBody.getSubject();
				
				if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
					UserDetails userDetails=this.userDetailService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(auth);
					
				}
				
				
			}
			
			filterChain.doFilter(request, response);
			System.out.println("JwtOncePerRequestFilter: reached here");
			
			
		}catch(Exception e) {
			
			System.out.println("JwtOncePerRequestFilter: Token Parsing Exception "+e.getMessage());
			filterChain.doFilter(request, response);
		}
		
		
	}

}
