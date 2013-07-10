package com.ywx.ticketlinewebapp;
   
import android.app.TabActivity;  
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;

 
@SuppressWarnings("deprecation")
public class TicketlineActivity extends TabActivity {
    /** Called when the activity is first created. */

	String className;
	TabHost tabHost;
  
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        className = getIntent().getStringExtra("class");
        // Log.e("classname",className);

        Resources res = getResources(); // Resource object to get Drawables
        tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab
         
        // Create an Intent to launch an Activity for the tab (to be reused)
       intent = new Intent().setClass(this, Home.class);
       
        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("home").setIndicator("Home",
                          res.getDrawable(R.drawable.ic_tab_home))
                      .setContent(intent);
        tabHost.addTab(spec);

        
        // Do the same for the other tabs
        intent = new Intent(getApplicationContext(), Account.class);
        intent.putExtra("class", className);
        
        spec = tabHost.newTabSpec("account").setIndicator("Account",
                          res.getDrawable(R.drawable.ic_tab_account))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Search.class);
        intent.putExtra("class", className);
       // intent = new Intent(getApplicationContext(), SongsActivity.class);
        spec = tabHost.newTabSpec("search").setIndicator("Search",
                          res.getDrawable(R.drawable.ic_tab_search))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, More.class);
        // intent = new Intent(getApplicationContext(), SongsActivity.class);
         spec = tabHost.newTabSpec("more").setIndicator("More",
                           res.getDrawable(R.drawable.ic_tab_more))
                       .setContent(intent);
         tabHost.addTab(spec);
         
         for(int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
        	 
             tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#2B0E2B"));
         }
         
         Bundle extras = getIntent().getExtras();
         if(extras !=null) {
        	 
        	 String value = extras.getString("TAB");
	         if(value != null){
	        	 
	        	 tabHost.setCurrentTab(Integer.parseInt(value));
	         }  else {
	        	 
	        	 tabHost.setCurrentTab(0);
	         }
         } 
    }   
}