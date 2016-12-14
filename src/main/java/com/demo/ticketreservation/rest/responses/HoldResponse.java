package com.demo.ticketreservation.rest.responses;

import java.util.ArrayList;
import java.util.List;

public class HoldResponse {
	
	private int holdId;
	
	private List<Integer> seatNumbersList;
	
	public HoldResponse(int holdId, List<Integer> seatNumberList){
		this.holdId = holdId;
		this.seatNumbersList = seatNumberList;
	}

	public int getHoldId() {
		return holdId;
	}

	public void setHoldId(int holdId) {
		this.holdId = holdId;
	}

	public List<Integer> getSeatNumbersList() {
		return seatNumbersList;
	}

	public void setSeatNumbersList(ArrayList<Integer> seatNumbersList) {
		this.seatNumbersList = seatNumbersList;
	}
	
	
}
