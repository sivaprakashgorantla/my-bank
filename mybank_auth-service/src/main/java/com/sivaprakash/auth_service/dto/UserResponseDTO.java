package com.sivaprakash.auth_service.dto;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String customerId;

    public UserResponseDTO() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "UserResponseDTO [id=" + id + ", username=" + username + ", email=" + email + ", customerId="
				+ customerId + "]";
	}
    
	
    
}