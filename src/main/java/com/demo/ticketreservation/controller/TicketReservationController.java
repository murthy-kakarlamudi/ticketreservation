package com.demo.ticketreservation.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.ticketreservation.api.TicketService;
import com.demo.ticketreservation.models.SeatHold;
import com.demo.ticketreservation.rest.requests.HoldRequest;
import com.demo.ticketreservation.rest.requests.ReserveRequest;
import com.demo.ticketreservation.rest.responses.HoldResponse;
import com.demo.ticketreservation.rest.responses.ReserveResponse;
import com.demo.ticketreservation.rest.responses.VenueResponse;



@RestController
public class TicketReservationController {
	
	@Autowired
	private TicketService ticketService;
	
	@RequestMapping(value = "/venue", method = RequestMethod.GET)
	public VenueResponse getAvailableSeats() {
		return new VenueResponse(ticketService.numSeatsAvailable());
        
    }
	
	@RequestMapping(value = "/venue", method = RequestMethod.POST)
	public HoldResponse holdSeats(@RequestBody() HoldRequest request){
		SeatHold seatHold = ticketService.findAndHoldSeats(request.getNumSeats(), request.getEmail());
		ArrayList<Integer> seats = new ArrayList<Integer>();
		seatHold.getSeatsPutInHold().forEach(seat -> seats.add(seat.getSeatNumber()));
		return new HoldResponse(seatHold.getSeatHoldId(),seats);
	}
	
	@RequestMapping(value = "/venue", method = RequestMethod.PUT)
	public ReserveResponse reserveSeats(@RequestBody() ReserveRequest request){
		return new ReserveResponse(ticketService.reserveSeats(request.getHoldId(), request.getEmail()));
	}
		
	
}
