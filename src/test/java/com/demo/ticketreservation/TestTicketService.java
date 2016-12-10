package com.demo.ticketreservation;

import com.demo.ticketreservation.impl.TicketServiceImpl;
import com.demo.ticketreservation.models.SeatHold;

public class TestTicketService {

	public static void main(String[] args) {
		TicketServiceImpl testTicketService = new TicketServiceImpl();
		System.out.println(testTicketService.numSeatsAvailable());
		
		SeatHold seatHold = testTicketService.findAndHoldSeats(3, "test1@gmail.com");
		seatHold.getSeatsPutInHold().forEach(seat -> System.out.println(seat.getSeatNumber()));
		
		seatHold = testTicketService.findAndHoldSeats(4, "test2@gmail.com");
		seatHold.getSeatsPutInHold().forEach(seat -> System.out.println(seat.getSeatNumber()));
	}

}
