package com.demo.ticketreservation.impl;

import java.util.HashMap;

import com.demo.ticketreservation.api.TicketService;
import com.demo.ticketreservation.models.Seat;
import com.demo.ticketreservation.models.SeatHold;
import com.demo.ticketreservation.models.Stadium;
import com.demo.ticketreservation.utils.SeatStatus;

public class TicketServiceImpl implements TicketService {
	
	private final int ROWCOUNT = 3;
	private final int COLUMNCOUNT = 4;
	private final int ONE_MINUTE = 1;
	
	private Stadium stadium;
	
	private HashMap<Double,SeatHold> seatsHoldMap;
	
	public TicketServiceImpl(){
		stadium = new Stadium(ROWCOUNT,COLUMNCOUNT);
		seatsHoldMap = new HashMap<Double,SeatHold>();
	}

	public int numSeatsAvailable() {
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

	public String reserveSeats(int seatHoldId, String customerEmail) {
		// TODO Auto-generated method stub
		return null;
	}
	
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
	
	private void validateHoldTime(){
		for(Double holdSetId : seatsHoldMap.keySet()){
			SeatHold seatHold = seatsHoldMap.get(holdSetId);
			if((System.currentTimeMillis() - seatHold.getHoldCreateTime())/(60*1000) >= ONE_MINUTE){
				seatsHoldMap.remove(holdSetId);
				for(Seat seat : seatHold.getSeatsPutInHold()){
					seat.setStatus(SeatStatus.OPEN);
				}
			}
		}
	}

}
