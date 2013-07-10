package com.ywx.ticketlinewebapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Links extends Activity {
	String name;
	TextView tv;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.links);
        
        name = getIntent().getStringExtra("Name");
        Log.i("Item name",name);
        
        Typeface myTypeface = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
        tv = (TextView) findViewById(R.id.txtName);
        tv.setTypeface(myTypeface);
        tv.setText(name);
        
        ListView list = (ListView) findViewById(R.id.lstLink);
		 
		 ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		 HashMap<String, String> map = new HashMap<String, String>();
		 map.put("Rev", "Deadmau5.com");
		
		 mylist.add(map);
		 map = new HashMap<String, String>();
		 map.put("Rev", "Facebook.com");
		 
		 mylist.add(map);
		 map = new HashMap<String, String>();
		 map.put("Rev", "Twitter.com");
		 
		 mylist.add(map);
		 // ...
		 SimpleAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.cuslinkrow,
		             new String[] {"Rev"}, new int[] {R.id.txtText});
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
	
	public void Media(View button){
		Intent Med = new Intent(getApplicationContext(),Media.class);
		Med.putExtra("Name", name);
		 startActivity(Med);
	}
	public void Bio(View button){
		Intent Bio = new Intent(getApplicationContext(),Bio.class);
		Bio.putExtra("Name", name);
		 startActivity(Bio);
	}
}
