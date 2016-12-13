package com.demo.ticketreservation.exceptions;

public class EmailValidationException extends RuntimeException {

	public EmailValidationException(){super();}
	
	public EmailValidationException(String messageIn){
		super(messageIn);
	}
}
