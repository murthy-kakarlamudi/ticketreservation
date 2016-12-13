package com.demo.ticketreservation.exceptions;

public class ReservationException extends RuntimeException {

	public ReservationException(){super();}
	public ReservationException(String messageIn){super(messageIn);}
}
