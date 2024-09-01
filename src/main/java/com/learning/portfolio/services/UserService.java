package com.learning.portfolio.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.learning.portfolio.entities.UserEntity;
import com.learning.portfolio.repositories.UserRepository;

@Service
public class UserService {
 
	Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null); // Handle the case where the user is not found
    }
 
    public boolean authenticate(String username, String password) {
		logger.info("username "+ username);
        UserEntity user = findByUsername(username);
        if (user != null) {
    		logger.info("user found");
            return passwordEncoder.matches(password, user.getPassword());
        }
		logger.info("user not found");
        return false;
    }
 
    public void save(UserEntity user) {
		logger.info("user "+ user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
		logger.info("user data saved in db");
    }
}