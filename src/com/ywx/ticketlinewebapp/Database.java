package com.ywx.ticketlinewebapp;

public class Database {
	
	private String name;
	private String id;
	private String type;
	private String opt;
	
	public Database(String name, String id,String type,String opt ){
		this.name = name;
		this.id = id;
		this.type = type;
		this.opt = opt;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getOptions(){
		return this.opt;
	}
}
