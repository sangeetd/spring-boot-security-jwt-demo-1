package com.codeone.project.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository("DummyUsers")
public class DummyUserDetails implements IUserDetailsfetcher{

	@Autowired
	private PasswordEncoderConfig passwordEncoder;
	
	@Override
	public Optional<UserDetails> loadUserDetails(String username) {
		return load(username);
	}
	
	private Optional<UserDetails> load(String username){
		
		List<UserDetails> users=new ArrayList<>();
		
		users.add(new ProjectUserDetails(
				"name1",
				passwordEncoder.getEncoder().encode("password"),
				UserRolesPermissionEnum.USER.getUserGrantedAuthority(),
				true,
				true,
				true,
				true
				));
		
		
		users.add(new ProjectUserDetails(
				"name2",
				passwordEncoder.getEncoder().encode("password"),
				UserRolesPermissionEnum.SUPER_ADMIN.getUserGrantedAuthority(),
				true,
				true,
				true,
				true
				));
		
		
		users.add(new ProjectUserDetails(
				"name2",
				passwordEncoder.getEncoder().encode("password"),
				UserRolesPermissionEnum.ADMIN.getUserGrantedAuthority(),
				true,
				true,
				true,
				true
				));
		
		return users.stream()
				.filter(user -> username.equals(user.getUsername()))
				.findFirst();
		
	}

}
