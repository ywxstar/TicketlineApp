package com.ywx.ticketlinewebapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Search extends TabGroupActivity{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        try {
        
        String g = getIntent().getStringExtra("class");
        Log.i("searchinfo", g);
                
        	if(g.equalsIgnoreCase("Date")){
        		startChildActivity("MoreView", new Intent(this,DateSearch.class));
        	}else if(g.equalsIgnoreCase("Venue")){
        		startChildActivity("MoreView", new Intent(this,VenueSearch.class));
        	}else if(g.equalsIgnoreCase("Genre")){
        		startChildActivity("MoreView", new Intent(this,GenreSearch.class));
        	}else if(g.equalsIgnoreCase("Location")){
        		startChildActivity("MoreView", new Intent(this,LocationSearch.class));
        	} else{
        		startChildActivity("MoreView", new Intent(this,ArtistSearch.class));
        	}
        	
        } catch(Exception e) {
        	startChildActivity("MoreView", new Intent(this,ArtistSearch.class));
        }
    }
}
