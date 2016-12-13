package com.demo.ticketreservation.models;

import com.demo.ticketreservation.utils.Constants;
import com.demo.ticketreservation.utils.SeatStatus;

public class Venue {
	
	private static Venue venue;
	
	private Seat[][] seats;
	
	private int rowCount;
	
	private int seatsPerRow;
	
	private Venue(){
		
	}
	
	public static Venue getInstance(){
		if(venue == null)
			venue =  new Venue();
		
		return venue;
	}
	
	public void initSeats(){
		rowCount = Constants.ROWCOUNT;
		seatsPerRow = Constants.SEATS_PER_ROW;
		initializeSeats();
	}
	
	public void initSeats(int rowCountIn, int seatsPerRowIn){
		this.rowCount = rowCountIn;
		this.seatsPerRow = seatsPerRowIn;
		initializeSeats();
	}
	
	private void initializeSeats(){
		int seatNumber = 1;
		seats = new Seat[rowCount][seatsPerRow];
		for(int rowIndex=0; rowIndex < rowCount; rowIndex++){
			for(int seatsPerRowIndex = 0; seatsPerRowIndex < seatsPerRow; seatsPerRowIndex++){
				seats[rowIndex][seatsPerRowIndex] = new Seat(seatNumber++,SeatStatus.OPEN);
				
			}
		}
	}
	
	public Seat[][] getSeats() {
		return seats;
	}

	public void setSeats(Seat[][] seats) {
		this.seats = seats;
	}
	
	
	
	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getSeatsPerRow() {
		return seatsPerRow;
	}

	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}

	public int getOpenSeats(){
		int retCount = 0;
		for(int rowIndex=0; rowIndex < rowCount; rowIndex++){
			for(int seatsPerRowIndex = 0; seatsPerRowIndex < seatsPerRow; seatsPerRowIndex++){
				if(seats[rowIndex][seatsPerRowIndex].getStatus() == SeatStatus.OPEN)
				retCount++;
			}
		}
		return retCount;
	}


}
