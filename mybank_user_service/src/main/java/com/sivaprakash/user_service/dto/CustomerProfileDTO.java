package com.sivaprakash.user_service.dto;

import com.sivaprakash.user_service.enums.ProfileStatus;

public class CustomerProfileDTO {
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String emailId;
    private String customerId;
    private ProfileStatus profileStatus;
	
    public CustomerProfileDTO() {
		super();
	}

	public CustomerProfileDTO(String firstName, String lastName, String mobileNo, String emailId, String customerId,
			ProfileStatus profileStatus) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
		this.customerId = customerId;
		this.profileStatus = profileStatus;
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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public ProfileStatus getProfileStatus() {
		return profileStatus;
	}

	public void setProfileStatus(ProfileStatus profileStatus) {
		this.profileStatus = profileStatus;
	}

	@Override
	public String toString() {
		return "CustomerProfileDTO [firstName=" + firstName + ", lastName=" + lastName + ", mobileNo=" + mobileNo
				+ ", emailId=" + emailId + ", customerId=" + customerId + ", profileStatus=" + profileStatus + "]";
	}

    
}