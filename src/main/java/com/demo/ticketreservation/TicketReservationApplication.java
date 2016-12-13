package com.demo.ticketreservation;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.demo.ticketreservation.api.TicketService;
import com.demo.ticketreservation.impl.TicketServiceImpl;
import com.demo.ticketreservation.models.SeatHold;

import org.apache.commons.lang.exception.ExceptionUtils;

@SpringBootApplication
public class TicketReservationApplication {

	private static final Logger log = LoggerFactory.getLogger(TicketReservationApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TicketReservationApplication.class);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {

			// fetch all customers
			Scanner sc = new Scanner(System.in);
			log.info("Use below prompts to specify the Venue size:");
			log.info("Enter Required Rows:");
			int rows = sc.nextInt();
			log.info("Enter Seats per Row:");
			int seatsPerRow = sc.nextInt();

			TicketServiceImpl ticketService = new TicketServiceImpl(rows, seatsPerRow);
			usage();
			while (true) {
				try {
					String token = sc.next();
					if (token.equalsIgnoreCase("SEARCH")) {
						log.info("Total available seats: " + ticketService.numSeatsAvailable());
					} else if (token.equalsIgnoreCase("HOLD")) {
						log.info("Enter number of seats to HOLD.");
						int seatsHold = sc.nextInt();
						log.info("Enter your email address.");
						String email = sc.next();
						SeatHold seatHold = ticketService.findAndHoldSeats(seatsHold, email);
						log.info("Success. Your hold confirmation code is:" + seatHold.getSeatHoldId());
						log.info("Seats put on hold for you:");
						seatHold.getSeatsPutInHold().forEach(seat -> log.info(String.valueOf(seat.getSeatNumber())));
					} else if (token.equalsIgnoreCase("RESERVE")) {

					} else if (token.equalsIgnoreCase("QUIT")) {
						System.exit(0);
					} else {
						log.info(
								"Unrecognized pattern. Please enter one of acceptable keywords SEARCH/HOLD/RESERVE/QUIT");
					}
				} catch (RuntimeException re) {
					log.error(ExceptionUtils.getStackTrace(re));
					usage();
				}
			}

		};
	}
	
	public static void usage(){
		log.info("Usage: Enter SEARCH to get available seats");
		log.info("Usage: Enter HOLD to temporarily hold seats.");
		log.info("Usage: Enter RESERVE to reserve seats.");
		log.info("Usage: Enter QUIT to exit the application.");
	}

}
