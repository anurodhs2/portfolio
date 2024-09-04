package com.learning.portfolio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.learning.portfolio.entities.UserEntity;
import com.learning.portfolio.models.UserDetail;
import com.learning.portfolio.repositories.UserRepository;
import com.learning.portfolio.security.CustomUserDetails;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		UserEntity userEntity = userRepository.findByUsername(username);
		UserDetail userDetail = new UserDetail(userEntity.getUsername(), userEntity.getPassword()); 
		return new CustomUserDetails(userDetail);
	}
}