package com.demo.ticketreservation.exceptions;

public class SeatHoldException extends RuntimeException {

	public SeatHoldException(){super();}
	
	public SeatHoldException(String messageIn){
		super(messageIn);
	}
}
