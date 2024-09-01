package com.learning.portfolio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.portfolio.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
}
