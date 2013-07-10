package com.ywx.ticketlinewebapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Media extends Activity {
	String name;
	TextView tv;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media);
        
        name = getIntent().getStringExtra("Name");
       // Log.i("Item name",name);
        
        Typeface myTypeface = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
        tv = (TextView) findViewById(R.id.txtName);
        tv.setTypeface(myTypeface);
        tv.setText(name);
        
        ListView list = (ListView) findViewById(R.id.lstMedia);
		 
		 ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		 HashMap<String, String> map = new HashMap<String, String>();
		 map.put("from", "101");
		 map.put("one", "6:30 AM");
		 map.put("two", "7:40 AM");
		 map.put("three", "7:40 AM");
		 mylist.add(map);
		 map = new HashMap<String, String>();
		 map.put("from", "101");
		 map.put("one", "6:30 AM");
		 map.put("two", "7:40 AM");
		 map.put("three", "7:40 AM");
		 mylist.add(map);
		 map = new HashMap<String, String>();
		 map.put("from", "101");
		 map.put("one", "6:30 AM");
		 map.put("two", "7:40 AM");
		 map.put("three", "7:40 AM");
		 mylist.add(map);
		 // ...
		 SimpleAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.cusmediarow,
		             new String[] {"from", "one", "two","three"}, new int[] {R.id.txtName, R.id.txtAge, R.id.txtViews,R.id.txtLength});
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
		 startActivity(Tour);
	}
	
	public void Reviews(View button){
		Intent Rev = new Intent(getApplicationContext(),Reviews.class);
		Rev.putExtra("Name", name);
		 startActivity(Rev);
	}
	
	public void Bio(View button){
		Intent Bio = new Intent(getApplicationContext(),Bio.class);
		Bio.putExtra("Name", name);
		 startActivity(Bio);
	}
	public void Links(View button){
		Intent Links = new Intent(getApplicationContext(),Links.class);
		Links.putExtra("Name", name);
		 startActivity(Links);
	}
}
