package com.demo.ticketreservation.impl;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.demo.ticketreservation.api.TicketService;
import com.demo.ticketreservation.exceptions.EmailValidationException;
import com.demo.ticketreservation.exceptions.ReservationException;
import com.demo.ticketreservation.exceptions.SeatHoldException;
import com.demo.ticketreservation.models.Customer;
import com.demo.ticketreservation.models.Seat;
import com.demo.ticketreservation.models.SeatHold;
import com.demo.ticketreservation.models.Venue;
import com.demo.ticketreservation.utils.Constants;
import com.demo.ticketreservation.utils.SeatStatus;
import org.apache.commons.validator.routines.EmailValidator;

/*
 * Implements TicketService Interface
 */
@Service
public class TicketServiceImpl implements TicketService {
	
	
	/*
	 * Object to hold the Venue with seats
	 */
	private Venue venue;
	
	
	/*
	 * Temporary data structure to hold information about the seats that were temporarily put on hold
	 */
	private HashMap<Integer,SeatHold> seatsHoldMap;
	
	/*
	 * Temporary data structure to hold expired SeatMap objects to report appropriate error 
	 */
	private HashMap<Integer,SeatHold> expiredSeatsHoldMap;
	
	/*
	 * Default Constructor
	 */
	public TicketServiceImpl(){
		venue = Venue.getInstance();
		seatsHoldMap = new HashMap<Integer,SeatHold>();
		expiredSeatsHoldMap = new HashMap<Integer,SeatHold>();
	}
	
	/*
	 * Initializes the venue with defaults
	 */
	public void initSeats(){
		venue.initSeats();
	}
	
	/*
	 * Initializes the venue with the specified rows and seats per row
	 */
	public void initSeats(int rowCount, int seatsPerRow){
		venue.initSeats(rowCount, seatsPerRow);
	}

	/*
	 * (non-Javadoc)
	 * @see com.demo.ticketreservation.api.TicketService#numSeatsAvailable()
	 */
	public int numSeatsAvailable() {
		
		validateHoldTime();
		
		return venue.getOpenSeats();
	}

	/*
	 * (non-Javadoc)
	 * @see com.demo.ticketreservation.api.TicketService#findAndHoldSeats(int, java.lang.String)
	 */
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail){
		//Check if the input email is valid
		if(!EmailValidator.getInstance().isValid(customerEmail))
			throw new EmailValidationException("Email address is not valid");
		
		validateHoldTime();
		
		if(numSeatsAvailable() < numSeats)
			throw new SeatHoldException("Not enough free seats are available. Hold is not complete.");
		
		SeatHold seatHold = new SeatHold();
		seatHold.setCustomer(new Customer(customerEmail));
		for(int rowIndex=0; rowIndex < venue.getRowCount(); rowIndex++){
			for(int columnIndex = 0; columnIndex < venue.getSeatsPerRow(); columnIndex++){
				boolean foundAdjacentSeats = findAdjacentSeats(rowIndex, columnIndex, numSeats, seatHold);
				if(foundAdjacentSeats){
					seatsHoldMap.put(seatHold.getSeatHoldId(), seatHold);
					return seatHold;
				}
			}
		}
		
		//Adjacent Seats are not found. So allocate OPEN seats wherever available.
		int seatsHold = 0;
		for(int rowIndex=0; rowIndex < venue.getRowCount(); rowIndex++){
			for(int columnIndex = 0; columnIndex < venue.getSeatsPerRow(); columnIndex++){
				if(venue.getSeats()[rowIndex][columnIndex].getStatus() == SeatStatus.OPEN){
					
					if(seatsHold < numSeats){
						seatsHold++;
						seatHold.addToSeatsPutInHold(venue.getSeats()[rowIndex][columnIndex]);
						venue.getSeats()[rowIndex][columnIndex].setStatus(SeatStatus.HOLD);
					}
					else
						break;
				}
			}
		}
		if(seatsHold!=numSeats)
			throw new SeatHoldException("Seats cannot be hold due to unavilability of seats");
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
				throw new ReservationException("Reservation cannot be done. The temporary hold put on the seats expired.");
			else
				throw new ReservationException("Reservation cannot be done. Seat Hold Id that was supplied is not found.");
		} else if(!seatHold.getCustomer().getEmail().equalsIgnoreCase(customerEmail)) {
			throw new ReservationException("Reservation cannot be done. The email on temporary hold: "+seatHold.getCustomer().getEmail()+" does not match the email provided: "+customerEmail);
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
		if(columnIndex+numSeats <= venue.getSeatsPerRow()){
			for(int numSeatIndex = 0; numSeatIndex < numSeats; numSeatIndex++){
				if(venue.getSeats()[rowIndex][columnIndex+numSeatIndex].getStatus() == SeatStatus.OPEN){
					adjEmptySeatCount++;
				}
			}
			
			if(numSeats == adjEmptySeatCount){
				for(int numSeatIndex = 0; numSeatIndex < numSeats; numSeatIndex++){
					venue.getSeats()[rowIndex][columnIndex+numSeatIndex].setStatus(SeatStatus.HOLD);
					seatHold.getSeatsPutInHold().add(venue.getSeats()[rowIndex][columnIndex+numSeatIndex]);
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
			if((System.currentTimeMillis() - seatHold.getHoldCreateTime())/(60*1000) >= Constants.THRESHOLD_TIME){
				seatsHoldMap.remove(holdSetId);
				expiredSeatsHoldMap.put(holdSetId,seatHold);
				for(Seat seat : seatHold.getSeatsPutInHold()){
					seat.setStatus(SeatStatus.OPEN);
				}
			}
		}
	}

	/*
	 * Getter Method that returns the member object venue
	 */
	public Venue getVenue() {
		return venue;
	}
	
}
