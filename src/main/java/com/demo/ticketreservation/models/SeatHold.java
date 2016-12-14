package com.demo.ticketreservation.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;
import java.text.SimpleDateFormat;

public class SeatHold {

	private int seatHoldId;
	
	private ArrayList<Seat> seatsPutInHold;
	
	private Timestamp holdCreateTime;
	
	private Customer customer;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	public SeatHold(){
		seatHoldId = (new Random()).nextInt(100);
		seatsPutInHold = new ArrayList<Seat>();
		holdCreateTime = new Timestamp(System.currentTimeMillis());
	}

	public int getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(int seatHoldId) {
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
	
	public long getHoldCreateTime(){
		return holdCreateTime.getTime();
	}

	public String getHoldCreateTimePrettyFormat() {
		return sdf.format(holdCreateTime);
	}

	public void setHoldCreateTime(Timestamp holdCreateTime) {
		this.holdCreateTime = holdCreateTime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
}
