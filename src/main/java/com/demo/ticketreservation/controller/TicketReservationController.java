package com.demo.ticketreservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.ticketreservation.api.TicketService;
import com.demo.ticketreservation.impl.TicketServiceImpl;

@RestController
public class TicketReservationController {
	
	@Autowired
	private TicketService ticketService;
	
	@RequestMapping(value = "/venue", method = RequestMethod.GET)
	@ResponseBody
    public int getStadiumDetails() {
        return ticketService.numSeatsAvailable();
        
    }
}
