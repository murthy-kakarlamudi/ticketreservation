package com.demo.ticketreservation.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;
import java.text.SimpleDateFormat;

public class SeatHold {

	//ID for the temporary hold put on the seats
	private int seatHoldId;
	
	//Datastructure that holds the seats that are put on hold
	private ArrayList<Seat> seatsPutInHold;
	
	//Timestamp at which the sets are put on hold
	private Timestamp holdCreateTime;
	
	//Datatructure to hold customer details
	private Customer customer;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	public SeatHold(){
		seatHoldId = (new Random()).nextInt(100);
		seatsPutInHold = new ArrayList<Seat>();
		holdCreateTime = new Timestamp(System.currentTimeMillis());
	}

	//Getter for seatHoldId
	public int getSeatHoldId() {
		return seatHoldId;
	}

	//Setter for seatHoldId
	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	//Getter for Seats that are put on hold datastructure
	public ArrayList<Seat> getSeatsPutInHold() {
		return seatsPutInHold;
	}

	//Setter for seats that are put on hold datastructure
	public void setSeatsPutInHold(ArrayList<Seat> seatsPutInHold) {
		this.seatsPutInHold = seatsPutInHold;
	}
	
	//Utility method to add to seats being put on hold
	public void addToSeatsPutInHold(Seat seatIn){
		seatsPutInHold.add(seatIn);
	}
	
	//Getter for creation time of hold
	public long getHoldCreateTime(){
		return holdCreateTime.getTime();
	}

	//Getter for creation time of hold in pretty format
	public String getHoldCreateTimePrettyFormat() {
		return sdf.format(holdCreateTime);
	}

	//Setter for creation time of hold
	public void setHoldCreateTime(Timestamp holdCreateTime) {
		this.holdCreateTime = holdCreateTime;
	}

	//Getter for Customer object
	public Customer getCustomer() {
		return customer;
	}

	//Setter for Customer object
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
}
