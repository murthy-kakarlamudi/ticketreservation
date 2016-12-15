package com.demo.ticketreservation.models;

import com.demo.ticketreservation.utils.Constants;
import com.demo.ticketreservation.utils.SeatStatus;

/*
 * Singleton Class that contains the seats for a venue
 */
public class Venue {
	
	private static Venue venue;
	
	private Seat[][] seats;
	
	private int rowCount;
	
	private int seatsPerRow;
	
	private Venue(){
		
	}
	
	/*
	 * Lazy Initialization method to get an instance of Venue Object
	 */
	public static Venue getInstance(){
		if(venue == null)
			venue =  new Venue();
		
		return venue;
	}
	
	/*
	 * Method to initialize the seats in venue wit default values
	 */
	public void initSeats(){
		rowCount = Constants.ROWCOUNT;
		seatsPerRow = Constants.SEATS_PER_ROW;
		initializeSeats();
	}
	
	/*
	 * Method to initialize seats in the venue with the specified parameters for rows and seats per row
	 */
	public void initSeats(int rowCountIn, int seatsPerRowIn){
		this.rowCount = rowCountIn;
		this.seatsPerRow = seatsPerRowIn;
		initializeSeats();
	}
	
	/*
	 * Method to initialize the seats with seat numbers and default status of OPEN
	 */
	private void initializeSeats(){
		int seatNumber = 1;
		seats = new Seat[rowCount][seatsPerRow];
		for(int rowIndex=0; rowIndex < rowCount; rowIndex++){
			for(int seatsPerRowIndex = 0; seatsPerRowIndex < seatsPerRow; seatsPerRowIndex++){
				seats[rowIndex][seatsPerRowIndex] = new Seat(seatNumber++,SeatStatus.OPEN);
				
			}
		}
	}
	
	//Getter for seats data structure
	public Seat[][] getSeats() {
		return seats;
	}

	//Setter for seats data structure
	public void setSeats(Seat[][] seats) {
		this.seats = seats;
	}
	
	
	//Getter for number of rows
	public int getRowCount() {
		return rowCount;
	}

	//Setter for number of rows
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	//Getter for seats per row
	public int getSeatsPerRow() {
		return seatsPerRow;
	}

	//Setter for seats per row
	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}

	/*
	 * Method that returns the number of open seats in the venue
	 */
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
