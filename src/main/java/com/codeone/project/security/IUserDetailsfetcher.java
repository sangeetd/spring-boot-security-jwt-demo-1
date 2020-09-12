package com.codeone.project.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

public interface IUserDetailsfetcher {
	Optional<UserDetails> loadUserDetails(String username);
}
