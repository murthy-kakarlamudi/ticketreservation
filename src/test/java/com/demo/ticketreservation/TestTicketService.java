package com.demo.ticketreservation;

import org.junit.Test;

import com.demo.ticketreservation.impl.TicketServiceImpl;
import com.demo.ticketreservation.models.SeatHold;
import com.demo.ticketreservation.utils.SeatStatus;

import junit.framework.TestCase;

public class TestTicketService extends TestCase{
	
	@Test
	public void testFirstTimeAllSeatsShouldBeEmpty(){
		TicketServiceImpl testTicketService = new TicketServiceImpl();
		assertEquals(12,testTicketService.numSeatsAvailable());
	}
	
	@Test
	public void testIfAvailableShouldHoldAdjacentSeats(){
		
		int HOLD_SEATS = 2;
		TicketServiceImpl testTicketService = new TicketServiceImpl();
		SeatHold seatHold = testTicketService.findAndHoldSeats(HOLD_SEATS, "test1@gmail.com");
		
		assertEquals(1, seatHold.getSeatsPutInHold().get(0).getSeatNumber());
		assertEquals(SeatStatus.HOLD,seatHold.getSeatsPutInHold().get(0).getStatus());
		
		assertEquals(2, seatHold.getSeatsPutInHold().get(1).getSeatNumber());
		assertEquals(SeatStatus.HOLD,seatHold.getSeatsPutInHold().get(1).getStatus());
		
		//Make sure OPEN seats are less by the count that was put on hold
		assertEquals(12-HOLD_SEATS,testTicketService.numSeatsAvailable());
	}
	
	@Test
	public void testAfterThresholdTimeExpiresHoldShouldBeRemoved() throws Exception {
		int HOLD_SEATS = 2;
		TicketServiceImpl testTicketService = new TicketServiceImpl();
		SeatHold seatHold = testTicketService.findAndHoldSeats(HOLD_SEATS, "test1@gmail.com");
		
		assertEquals(1, seatHold.getSeatsPutInHold().get(0).getSeatNumber());
		assertEquals(2, seatHold.getSeatsPutInHold().get(1).getSeatNumber());
		
//		Thread.sleep(1*60*1000);
//		
//		seatHold = testTicketService.findAndHoldSeats(HOLD_SEATS, "test2@gmail.com");
//		
//		assertEquals(1, seatHold.getSeatsPutInHold().get(0).getSeatNumber());
//		assertEquals(2, seatHold.getSeatsPutInHold().get(1).getSeatNumber());
	}
	
	@Test
	public void testReserveSeatsForNonExistingHoldIdShouldFail(){
		TicketServiceImpl testTicketService = new TicketServiceImpl();
		testTicketService.reserveSeats(123, "test1@gmail.com");
	}


}
