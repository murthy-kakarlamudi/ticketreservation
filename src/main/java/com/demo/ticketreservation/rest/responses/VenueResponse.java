package com.demo.ticketreservation.rest.responses;

public class VenueResponse {
	
	private int numSeatsAvailable;
	
	public VenueResponse(int numSeatsAvailable){
		this.numSeatsAvailable = numSeatsAvailable;
	}

	public int getNumSeatsAvailable() {
		return numSeatsAvailable;
	}

	public void setNumSeatsAvailable(int numSeatsAvailable) {
		this.numSeatsAvailable = numSeatsAvailable;
	}
	
	
}
