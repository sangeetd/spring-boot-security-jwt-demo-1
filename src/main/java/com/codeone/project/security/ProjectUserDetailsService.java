package com.codeone.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProjectUserDetailsService implements UserDetailsService {

	@Autowired
	@Qualifier("DummyUsers")
	private IUserDetailsfetcher userDetailsFetcher;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userDetailsFetcher.loadUserDetails(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found..."));
	}

}
