package com.learning.portfolio.dto;

public class UserPortfolioResponse {

	private String username;
	private Boolean hasData;
	private Double investment;
	private Double assets;
	private Double debts;
	
	// Constructors
    public UserPortfolioResponse(String username) {
        this.username = username;
        this.setHasData(false);
    }
	
    // Constructors
    public UserPortfolioResponse(String username, Double investment, Double assets, Double debts) {
        this.username = username;
        this.setHasData(true);
        this.setInvestment(investment);
        this.setAssets(assets);
        this.setDebts(debts);
    }
    
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Double getInvestment() {
		return investment;
	}
	
	public void setInvestment(Double investment) {
		this.investment = investment;
	}
	
	public Double getAssets() {
		return assets;
	}
	
	public void setAssets(Double assets) {
		this.assets = assets;
	}
	
	public Double getDebts() {
		return debts;
	}
	
	public void setDebts(Double debts) {
		this.debts = debts;
	}

	public Boolean getHasData() {
		return hasData;
	}

	public void setHasData(Boolean hasData) {
		this.hasData = hasData;
	}
}