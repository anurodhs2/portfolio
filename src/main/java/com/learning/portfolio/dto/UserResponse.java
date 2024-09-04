package com.learning.portfolio.dto;

public class UserResponse {
	private String firstname;
	private String lastname;
	private String username;
	private String token;
	
    // Constructors
    public UserResponse(String firstname, String lastname, String username, String token) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.token = token;
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}