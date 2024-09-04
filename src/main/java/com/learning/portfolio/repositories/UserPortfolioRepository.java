package com.learning.portfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.learning.portfolio.entities.UserPortfolioEntity;

public interface UserPortfolioRepository extends JpaRepository<UserPortfolioEntity, Long> {
	UserPortfolioEntity findByUsername(String username);
}
