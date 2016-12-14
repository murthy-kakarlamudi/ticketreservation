package com.demo.ticketreservation.rest.requests;

public class ReserveRequest {
	
	private int holdId;
	
	private String email;
	
	protected ReserveRequest(){}
	
	public ReserveRequest(int holdId, String email){
		this.holdId = holdId;
		this.email = email;
	}

	public int getHoldId() {
		return holdId;
	}

	public void setHoldId(int holdId) {
		this.holdId = holdId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
