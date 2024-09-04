package com.learning.portfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.portfolio.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByUsername(String username);
}
