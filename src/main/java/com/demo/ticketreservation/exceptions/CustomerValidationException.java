package com.demo.ticketreservation.exceptions;

public class CustomerValidationException extends RuntimeException {
	public CustomerValidationException() { super(); }
	public CustomerValidationException(String message) {super(message);}
}
