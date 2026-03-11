package com.api.pojo;

public class UserCredentials {
	
	private String username;
	private String password;
	
	public UserCredentials(String name, String password) {
		super();
		this.username = name;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserCredentials [username=" + username + ", password=" + password + "]";
	}
	
}
