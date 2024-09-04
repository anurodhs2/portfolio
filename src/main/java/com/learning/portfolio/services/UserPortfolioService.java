package com.learning.portfolio.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.learning.portfolio.entities.UserPortfolioEntity;
import com.learning.portfolio.repositories.UserPortfolioRepository;
 
@Service
public class UserPortfolioService {
 
    @Autowired
    private UserPortfolioRepository userPortfolioRepository;

    public UserPortfolioEntity getUserPortfolioByUsername(String username) {
        return userPortfolioRepository.findByUsername(username);
    }
    
    public UserPortfolioEntity updateUserPortfolio(UserPortfolioEntity updatedPortfolio) {
        return userPortfolioRepository.save(updatedPortfolio);
    }
}