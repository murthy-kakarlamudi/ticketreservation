package com.demo.ticketreservation.rest.requests;

public class HoldRequest {

	private int numSeats;
	
	private String email;
	
	protected HoldRequest(){}
	
	public HoldRequest(int numSeats, String email){
		this.numSeats = numSeats;
		this.email = email;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
