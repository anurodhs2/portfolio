package com.learning.portfolio.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user_portfolio")
public class UserPortfolioEntity {
	@Id
	private String username;  // This is the primary key and also a foreign key to the user table
 
    @Column(nullable = false)
    private double investment;
 
    @Column(nullable = false)
    private double assets;
 
    @Column(nullable = false)
    private double debts;
 
    // Getters and Setters
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public double getInvestment() {
        return investment;
    }
 
    public void setInvestment(double investment) {
        this.investment = investment;
    }
 
    public double getAssets() {
        return assets;
    }
 
    public void setAssets(double assets) {
        this.assets = assets;
    }
 
    public double getDebts() {
        return debts;
    }
 
    public void setDebts(double debts) {
        this.debts = debts;
    }
}