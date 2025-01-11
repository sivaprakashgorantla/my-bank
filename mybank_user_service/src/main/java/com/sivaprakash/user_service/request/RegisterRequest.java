package com.sivaprakash.user_service.request;

public class RegisterRequest {
	private String username;
	private String firstname;
	private String lastname;
	private String phoneNumber;
	private String email;
	private String password;
	private String role;
	public RegisterRequest(String username, String firstname, String lastname, String phoneNumber, String email,
			String password, String role) {
		super();
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		this.role = role;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "RegisterRequest [username=" + username + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", password=" + password + ", role=" + role
				+ "]";
	}
	
}
