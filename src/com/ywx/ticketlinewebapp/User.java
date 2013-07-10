package com.ywx.ticketlinewebapp;



public class User  {

	private String id;
	private String email;
	private String title;
	private String fname;
	private String lname;
	private String address1;
	private String address2;
	private String city;
	private String post;
	private String country;
	private String phone;
	
	public User(String id,String email,String title,String fname,String lname,String add1,String add2,String city,
			String post,String country,String phone){
		
		this.id = id;
		this.email = email;
		this.title = title;
		this.fname = fname;
		this.lname = lname;
		this.address1 = add1;
		this.address2 = add2;
		this.city = city;
		this.post = post;
		this.country = country;
		this.phone = phone;
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getTit(){
		return this.title;
	}
	
	public String getFname(){
		return this.fname;
	}
	
	public String getLname(){
		return this.lname;
	}
	
	public String getAdd1(){
		return this.address1;
	}
	
	public String getAdd2(){
		return this.address2;
	}
	
	public String getCity(){
		return this.city;
	}
	
	public String getPost(){
		return this.post;
	}
	
	public String getCountry(){
		return this.country;
	}
	
	public String getPhone(){
		return this.phone;
	}
	
	public void setId(String Id){
		this.id = Id;
	}
}
