package com.ywx.ticketlinewebapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class Home extends TabGroupActivity{

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.e("HOME","started");
        startChildActivity("MoreView", new Intent(Home.this, MoreView.class));
    }
	
	
	// DAZ 23/12/12 Sends user to home menu when back button is pressed	 
	@Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	   
		 if ( keyCode == KeyEvent.KEYCODE_BACK ) {
	    	 Intent startMain = new Intent(Intent.ACTION_MAIN); // Create the intent
		        startMain.addCategory(Intent.CATEGORY_HOME); //  Add the home category
		        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Bring menu to front
		        startActivity(startMain); // Start the activity
	         return true;
	     }
	     
	     else if ( keyCode == KeyEvent.KEYCODE_MENU) {
	    	 
	    	 Log.d("MENU PRESSED", "MENU PRESS DETECTED");
	    	 
	    	 return true;
	     }
	     return super.onKeyDown(keyCode, event);
	 }

 


	
}
