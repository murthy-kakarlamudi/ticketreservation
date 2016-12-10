package com.demo.ticketreservation.impl;

import java.util.HashMap;
import java.util.UUID;

import com.demo.ticketreservation.api.TicketService;
import com.demo.ticketreservation.models.Seat;
import com.demo.ticketreservation.models.SeatHold;
import com.demo.ticketreservation.models.Stadium;
import com.demo.ticketreservation.utils.SeatStatus;

/*
 * Implements TicketService Interface
 */
public class TicketServiceImpl implements TicketService {
	/*
	 * Holds the number of seat rows in the stadium
	 */
	private final int ROWCOUNT = 3;
	
	/*
	 * Holds the number of seats available in each row
	 */
	private final int COLUMNCOUNT = 4;
	
	/*
	 * Time to wait till the hold put on the seats is removed
	 */
	private final int ONE_MINUTE = 1;
	
	/*
	 * Object to hold the stadium with seats
	 */
	private Stadium stadium;
	
	/*
	 * Temporary data structure to hold information about the seats that were temporarily put on hold
	 */
	private HashMap<Integer,SeatHold> seatsHoldMap;
	
	private HashMap<Integer,SeatHold> expiredSeatsHoldMap;
	
	/*
	 * Default Constructor
	 */
	public TicketServiceImpl(){
		stadium = new Stadium(ROWCOUNT,COLUMNCOUNT);
		seatsHoldMap = new HashMap<Integer,SeatHold>();
		expiredSeatsHoldMap = new HashMap<Integer,SeatHold>();
	}

	/*
	 * (non-Javadoc)
	 * @see com.demo.ticketreservation.api.TicketService#numSeatsAvailable()
	 */
	public int numSeatsAvailable() {
		
		validateHoldTime();
		
		int retSeatCount = 0;
		Seat[][] seats = stadium.getSeats();
		for(int rowIndex=0; rowIndex < ROWCOUNT; rowIndex++){
			for(int columnIndex = 0; columnIndex < COLUMNCOUNT; columnIndex++){
				if(seats[rowIndex][columnIndex].getStatus() == SeatStatus.OPEN)
					retSeatCount++;
			}
		}
		return retSeatCount;
	}

	/*
	 * (non-Javadoc)
	 * @see com.demo.ticketreservation.api.TicketService#findAndHoldSeats(int, java.lang.String)
	 */
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail){
		
		validateHoldTime();
		
		SeatHold seatHold = new SeatHold();
		seatHold.setCustomerEmail(customerEmail);
		for(int rowIndex=0; rowIndex < ROWCOUNT; rowIndex++){
			for(int columnIndex = 0; columnIndex < COLUMNCOUNT; columnIndex++){
				boolean foundAdjacentSeats = findAdjacentSeats(rowIndex, columnIndex, numSeats, seatHold);
				if(foundAdjacentSeats){
					seatsHoldMap.put(seatHold.getSeatHoldId(), seatHold);
					return seatHold;
				}
			}
		}
		
		//Adjacent Seats are not found. So allocate OPEN seats wherever available.
		int seatsHold = 0;
		for(int rowIndex=0; rowIndex < ROWCOUNT; rowIndex++){
			for(int columnIndex = 0; columnIndex < COLUMNCOUNT; columnIndex++){
				if(stadium.getSeats()[rowIndex][columnIndex].getStatus() == SeatStatus.OPEN){
					seatsHold++;
					if(seatsHold <= numSeats){
						seatHold.addToSeatsPutInHold(stadium.getSeats()[rowIndex][columnIndex]);
						stadium.getSeats()[rowIndex][columnIndex].setStatus(SeatStatus.HOLD);
					}
					else
						break;
				}
			}
		}
		if(seatsHold!=numSeats)
			seatHold.setErrorMessage("Seats cannot be hold due to unavilability of seats");
		else 
			seatsHoldMap.put(seatHold.getSeatHoldId(), seatHold);
		
		return seatHold;
	}

	/*
	 * (non-Javadoc)
	 * @see com.demo.ticketreservation.api.TicketService#reserveSeats(int, java.lang.String)
	 */
	public String reserveSeats(int seatHoldId, String customerEmail) {
		SeatHold seatHold = seatsHoldMap.get(seatHoldId);
		if(seatHold == null){
			if(expiredSeatsHoldMap.get(seatHoldId) != null)
				return "Reservation cannot be done. The temporary hold put on the seats expired.";
			else
				return "Reservation cannot be done. Seat Hold Id that was supplied is not found.";
		} else if(!seatHold.getCustomerEmail().equalsIgnoreCase(customerEmail)) {
			return "Reservation cannot be done. The email on temporary hold: "+seatHold.getCustomerEmail()+" does not match the email provided: "+customerEmail;
		} else {
			seatsHoldMap.remove(seatHoldId);
			for(Seat seat : seatHold.getSeatsPutInHold())
				seat.setStatus(SeatStatus.RESERVED);
			return UUID.randomUUID().toString();
		}
		
	}
	
	/*
	 * Utility method that determines if adjacent seats are available and holds them
	 */
	private boolean findAdjacentSeats(int rowIndex, int columnIndex, int numSeats, SeatHold seatHold) {
		int adjEmptySeatCount = 0;
		if(columnIndex+numSeats <= COLUMNCOUNT){
			for(int numSeatIndex = 0; numSeatIndex < numSeats; numSeatIndex++){
				if(stadium.getSeats()[rowIndex][columnIndex+numSeatIndex].getStatus() == SeatStatus.OPEN){
					adjEmptySeatCount++;
				}
			}
			
			if(numSeats == adjEmptySeatCount){
				for(int numSeatIndex = 0; numSeatIndex < numSeats; numSeatIndex++){
					stadium.getSeats()[rowIndex][columnIndex+numSeatIndex].setStatus(SeatStatus.HOLD);
					seatHold.getSeatsPutInHold().add(stadium.getSeats()[rowIndex][columnIndex+numSeatIndex]);
				}
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Utility method that checks if the time at which seats that were put on hold 
	 */
	private void validateHoldTime(){
		for(Integer holdSetId : seatsHoldMap.keySet()){
			SeatHold seatHold = seatsHoldMap.get(holdSetId);
			if((System.currentTimeMillis() - seatHold.getHoldCreateTime())/(60*1000) >= ONE_MINUTE){
				seatsHoldMap.remove(holdSetId);
				expiredSeatsHoldMap.put(holdSetId,seatHold);
				for(Seat seat : seatHold.getSeatsPutInHold()){
					seat.setStatus(SeatStatus.OPEN);
				}
			}
		}
	}

}
