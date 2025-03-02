package com.sivaprakash.loan.response;

public class CustomerResponse {
	private Long customerId;
	private String firstName;
	private String lastName;

	public CustomerResponse(Long customerId, String firstName, String lastName) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "CustomerResponse [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ "]";
	}

}
