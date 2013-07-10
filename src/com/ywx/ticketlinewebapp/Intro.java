package com.ywx.ticketlinewebapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Intro extends Activity{
	 
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.video);
	   
	       Thread splashThread = new Thread() {
	          @Override
	          public void run() {
	             try {
	                int waited = 0;
	                while (waited < 3000) {
	                   sleep(100);
	                   waited += 100;
	                }
	             } catch (InterruptedException e) {
	                // do nothing
	             } finally {
	                finish();
	                Intent i = new Intent();
	                i.setClassName("com.ywx.ticketlinewebapp",
	                               "com.ywx.ticketlinewebapp.TicketlineActivity");
	                i.putExtra("class", "Home");
	                startActivity(i);
	             }
	          }
	       };
	       splashThread.start();
	    }
   
	    
	}
	
	
	
	
	
	
	

