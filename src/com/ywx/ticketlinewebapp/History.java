package com.ywx.ticketlinewebapp;

public class History {

	public String id;
	public String date;	
	public String event;
	public String venue;
	public String qt;
	public String total;
	public String status;

	
	public History(String id,String date,String event,String venue,String qt,String total,String status){
		
		
		this.id = id;
		this.date = date;
		this.event = event;
		this.venue = venue;
		this.qt = qt;
		this.total = total;
		this.status = status;
	}
}
