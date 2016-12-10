package com.demo.ticketreservation.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class SeatHold {

	private double seatHoldId;
	
	private ArrayList<Seat> seatsPutInHold;
	
	private Timestamp holdCreateTime;
	
	private String customerEmail;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	public SeatHold(){
		seatHoldId = Math.random();
		seatsPutInHold = new ArrayList<Seat>();
		holdCreateTime = new Timestamp(System.currentTimeMillis());
	}

	public double getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(double seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	public ArrayList<Seat> getSeatsPutInHold() {
		return seatsPutInHold;
	}

	public void setSeatsPutInHold(ArrayList<Seat> seatsPutInHold) {
		this.seatsPutInHold = seatsPutInHold;
	}
	
	public void addToSeatsPutInHold(Seat seatIn){
		seatsPutInHold.add(seatIn);
	}

	public String getHoldCreateTimePrettyFormat() {
		return sdf.format(holdCreateTime);
	}

	public void setHoldCreateTime(Timestamp holdCreateTime) {
		this.holdCreateTime = holdCreateTime;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	
}
