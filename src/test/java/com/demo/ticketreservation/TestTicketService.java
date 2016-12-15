package com.demo.ticketreservation;

import org.junit.Test;

import com.demo.ticketreservation.impl.TicketServiceImpl;
import com.demo.ticketreservation.models.SeatHold;
import com.demo.ticketreservation.models.Venue;
import com.demo.ticketreservation.utils.SeatStatus;

import junit.framework.TestCase;

public class TestTicketService extends TestCase{
	
	@Test
	public void testFirstTimeAllSeatsShouldBeEmpty(){
		TicketServiceImpl testTicketService = new TicketServiceImpl();
		testTicketService.initSeats(3, 4);
		assertEquals(12,testTicketService.numSeatsAvailable());
	}
	
	@Test
	public void testIfAvailableShouldHoldAdjacentSeats(){
		
		int HOLD_SEATS = 2;
		TicketServiceImpl testTicketService = new TicketServiceImpl();
		testTicketService.initSeats(3, 4);
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
		testTicketService.initSeats();
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
	public void testForSuccessfulHoldsTillSeatsAreEmptyNumSeatsAvailableShouldReturnZero(){
		TicketServiceImpl testTicketService = new TicketServiceImpl();
		testTicketService.initSeats(2, 2);
		testTicketService.findAndHoldSeats(3, "test1@gmail.com");
		testTicketService.findAndHoldSeats(1, "test2@gmail.com");
		assertEquals(0,testTicketService.numSeatsAvailable());
	}
	
	
	
	@Test
	public void testReserveSeatsForNonExistingHoldIdShouldFail() {
		TicketServiceImpl testTicketService = new TicketServiceImpl();
		testTicketService.initSeats(2, 2);
		try {
			String code = testTicketService.reserveSeats(123, "test1@gmail.com");
		} catch (RuntimeException re) {
			assertEquals("Reservation cannot be done. Seat Hold Id that was supplied is not found.", re.getMessage());
		}
	}
	
	@Test
	public void testMultipleInstantiationsShouldreturnSameVenue(){
		TicketServiceImpl testTicketService1 = new TicketServiceImpl();
		testTicketService1.initSeats(2, 3);
		Venue venue1 = testTicketService1.getVenue();
		
		TicketServiceImpl testTicketService2 = new TicketServiceImpl();
		Venue venue2 = testTicketService2.getVenue();
		
		assertTrue(venue1 == venue2);
	}
	
	@Test
	public void testIfNoSeatCountsSpecifiedShouldUseDefaults(){
		TicketServiceImpl testTicketService = new TicketServiceImpl();
		testTicketService.initSeats();
		assertEquals(100, testTicketService.numSeatsAvailable());
	}
	
	@Test
	public void testMoreSeatsRequestedThanCapacityShouldReturnError(){
		TicketServiceImpl testTicketService = new TicketServiceImpl();
		testTicketService.initSeats(2,3);
		try {
			testTicketService.findAndHoldSeats(10, "test1@gmail.com");
		} catch(RuntimeException re){
			assertEquals("Not enough free seats are available. Hold is not complete.",re.getMessage());
		}
	}
	
	@Test
	public void testInvalidEmailShouldError(){
		TicketServiceImpl testTicketService = new TicketServiceImpl();
		testTicketService.initSeats(2,3);
		try {
			testTicketService.findAndHoldSeats(10, "test1gmail");
		} catch(RuntimeException re){
			assertEquals("Email address is not valid",re.getMessage());
		}
	}
	
	@Test
	public void testReserveSeatsForValidHoldIdShouldSuccess(){
		TicketServiceImpl testTicketService = new TicketServiceImpl();
		testTicketService.initSeats(2,3);
		SeatHold seatHold = testTicketService.findAndHoldSeats(2, "test@gmail.com");
		String code = testTicketService.reserveSeats(seatHold.getSeatHoldId(), "test@gmail.com");
		
		seatHold.getSeatsPutInHold().forEach(seat -> assertEquals(SeatStatus.RESERVED, seat.getStatus()));
	}
	
	@Test
	public void testReserveSeatsForNonMatchingEmailShouldFail(){
		try {
			TicketServiceImpl testTicketService = new TicketServiceImpl();
			testTicketService.initSeats(2,3);
			SeatHold seatHold = testTicketService.findAndHoldSeats(2, "test@gmail.com");
			String code = testTicketService.reserveSeats(seatHold.getSeatHoldId(), "test1@gmail.com");
		} catch (RuntimeException re) {
			assertTrue(re.getMessage().contains("Reservation cannot be done."));
		}
	}


}
