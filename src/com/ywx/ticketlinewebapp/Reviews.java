package com.ywx.ticketlinewebapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Reviews extends Activity {
	String name;
	TextView tv;
	Button tickets;
	Button tour;
	Button bio;
	Button reviews;
	String id;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews);
        
        name = getIntent().getStringExtra("Name");
        id= getIntent().getStringExtra("artist-id");
        
        Typeface arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
        tv = (TextView) findViewById(R.id.txtName);
        tv.setTypeface(arialFont);
        tv.setText(name);
        
        tickets = (Button)findViewById(R.id.Button03);
        tour = (Button)findViewById(R.id.Button02);
        bio = (Button)findViewById(R.id.Button01);
        reviews = (Button)findViewById(R.id.Button04);
        
        tickets.setTypeface(arialFont);
        tour.setTypeface(arialFont);
        bio.setTypeface(arialFont);
        reviews.setTypeface(arialFont);
        
        ListView list = (ListView) findViewById(R.id.lstReviews);
		 
		 ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		 HashMap<String, String> map = new HashMap<String, String>();
		 map.put("Rev", "Great One");
		 map.put("Rate", "4");
		 map.put("User", "Mahesh");
		 map.put("Info", "11/10/2010");
		 mylist.add(map);
		 map = new HashMap<String, String>();
		 map.put("Rev", "Great One");
		 map.put("Rate", "4");
		 map.put("User", "Mahesh");
		 map.put("Info", "11/10/2010");
		 mylist.add(map);
		 map = new HashMap<String, String>();
		 map.put("Rev", "Great One");
		 map.put("Rate", "4");
		 map.put("User", "Mahesh");
		 map.put("Info", "11/10/2010");
		 mylist.add(map);
		 // ...
		 SimpleAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.cusreviewrow,
		             new String[] {"Rev", "User","Info"}, new int[] {R.id.txtReview, R.id.txtUser,R.id.txtInfo});
		 list.setAdapter(mSchedule);
        
        }
	
	public void back(View button){
		Intent SeeMore = new Intent(getParent(),SeeMore.class);
 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
 		parentActivity.startChildActivity("MoreViewActivity", SeeMore);
	}
	
	public void Tour(View button){
		Intent Tour = new Intent(getApplicationContext(),Tour.class);
		Tour.putExtra("Name", name);
		Tour.putExtra("artist-id", id);
		Tour.putExtra("saved","true");
		 startActivity(Tour);
		 finish();
	}
	
	public void Bio(View button){
		Intent Bio = new Intent(getApplicationContext(),Bio.class);
		Bio.putExtra("Name", name);
		Bio.putExtra("saved","true");
		Bio.putExtra("artist-id", id);
		 startActivity(Bio);
		 finish();
	}
	
	public void Tickets(View button){
		Intent Med = new Intent(getApplicationContext(),Tickets.class);
		Med.putExtra("Name", name);
		Med.putExtra("artist-id", id);
		Med.putExtra("saved","true");
		 startActivity(Med);
		 finish();
	}
	public void Links(View button){
		Intent Links = new Intent(getApplicationContext(),Links.class);
		Links.putExtra("Name", name);
		 startActivity(Links);
	}
	
	public void popup(View button){

		Intent Lin = new Intent(getParent(),PopUp.class);
		Lin.putExtra("Name", name);
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("LinksViewActivity", Lin);
	}
}
