package com.learning.portfolio.models;

import org.springframework.stereotype.Component;

@Component
public class UserDetail {
	private String username;
	private String password;
	
	public UserDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserDetail(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
