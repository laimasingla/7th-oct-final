package com.cg.ibs.cardmanagement.bean;

import java.math.BigInteger;

public class CustomerBean {

	BigInteger accountNumber;
	String UCI;
	private String firstName;
	private String lastName;
	private String emailId;
	private String aadharNumber;
	private String mobileNumber;
	
	
	
	
	public CustomerBean(BigInteger accountNumber, String UCI, String firstname, String lastName, String emailId,
			String aadharNumber, String mobileNumber) {
this.accountNumber=accountNumber;
this.UCI=UCI;
this.firstName = firstName;
this.lastName=lastName;
this.emailId =emailId;
this.aadharNumber=aadharNumber;
this.mobileNumber=mobileNumber;
		
	}
	public CustomerBean() {
		// TODO Auto-generated constructor stub
	}
	
	public CustomerBean(BigInteger bigInteger, BigInteger bigInteger2, String string, String string2, String string3,
			String string4, String string5) {
		// TODO Auto-generated constructor stub
	}
	public BigInteger getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(BigInteger accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getUCI() {
		return UCI;
	}
	public void setUCI(String uCI) {
		UCI = uCI;
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
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	
}
