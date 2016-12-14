package com.demo.ticketreservation.rest.responses;

public class ReserveResponse {

	private String code;
	
	protected ReserveResponse(){}
	
	public ReserveResponse(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
