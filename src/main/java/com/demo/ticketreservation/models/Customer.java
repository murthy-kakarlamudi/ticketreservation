package com.demo.ticketreservation.models;

public class Customer {
	
	//Email address of the customer
	private String email;
	
	
	public Customer(String email){
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
