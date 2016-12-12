package com.demo.ticketreservation.models;

import com.demo.ticketreservation.utils.SeatStatus;

public class Stadium {
	
	private Seat[][] seats;
	
	public Stadium(int rowCount, int columnCount){
		seats = new Seat[rowCount][columnCount];
		initSeats(rowCount,columnCount);
	}
	
	
	public void initSeats(int rowCount, int columnCount){
		int seatNumber = 1;
		for(int rowIndex=0; rowIndex < rowCount; rowIndex++){
			for(int columnIndex = 0; columnIndex < columnCount; columnIndex++){
				seats[rowIndex][columnIndex] = new Seat(seatNumber++,SeatStatus.OPEN);
				
			}
		}
	}
	
	public Seat[][] getSeats() {
		return seats;
	}

	public void setSeats(Seat[][] seats) {
		this.seats = seats;
	}


}
