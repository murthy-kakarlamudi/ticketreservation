package com.demo.ticketreservation.models;

import com.demo.ticketreservation.utils.SeatStatus;	

public class Seat {
	
	private int seatNumber;
	
	private SeatStatus status;
	
	public Seat(int seatNumberIn, SeatStatus statusIn){
		this.seatNumber = seatNumberIn;
		this.status = statusIn;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public SeatStatus getStatus() {
		return status;
	}

	public void setStatus(SeatStatus status) {
		this.status = status;
	}
}
