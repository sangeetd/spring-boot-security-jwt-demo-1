package com.codeone.project.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.google.common.collect.Sets;

public enum UserRolesPermissionEnum {

	USER(Sets.newHashSet(Sets.newHashSet(
			PermissionEnum.UNDEFINED.name()))),
	SUPER_ADMIN(Sets.newHashSet(
			PermissionEnum.READ.name(),
			PermissionEnum.WRITE.name())),
	ADMIN(Sets.newHashSet(
			PermissionEnum.READ.name()));
	
	private final Set<String> permissions;
	
	UserRolesPermissionEnum(Set<String> permission){
		this.permissions=permission;
	}
	
	private Set<String> getPermissions(){
		return this.permissions;
	}
	
	public Set<SimpleGrantedAuthority> getUserGrantedAuthority(){
		
		Set<SimpleGrantedAuthority> grantedAuthorities=getPermissions()
				.stream()
				.map(permission -> new SimpleGrantedAuthority(permission))
				.collect(Collectors.toSet());
		
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
		return grantedAuthorities;
		
	}
	
}
