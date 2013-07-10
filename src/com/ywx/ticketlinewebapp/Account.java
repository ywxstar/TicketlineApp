package com.ywx.ticketlinewebapp;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

public class Account extends TabGroupActivity{
	
	Functions f;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        f = new Functions();
        File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/user.xml");
        
        if(newxmlfile.exists()){
        	
        	//String  j = f.getUserInfo();
            //Log.i("class name",getIntent().getStringExtra("class"));
        	String cName = getIntent().getStringExtra("class");
       
        	if(cName.equalsIgnoreCase("History")){
    	   
        		startChildActivity("MoreView", new Intent(this, UserExtras.class));    
        	}else if (cName.equalsIgnoreCase("Profile")) {
    	   
        		startChildActivity("MoreView", new Intent(this, UserProfile.class));
        	}else{
        		
        		startChildActivity("MoreView", new Intent(this, UserProfile.class)); 
        	}
        	
        } else {
        	
        	 startChildActivity("MoreView", new Intent(this, Login.class));
        }//end if file exist
    }
}
