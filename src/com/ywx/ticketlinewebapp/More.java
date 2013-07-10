package com.ywx.ticketlinewebapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class More extends Activity{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moretab);
    
     
           ListView list = (ListView) findViewById(R.id.lstInfo);
    		 
    		 ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
    		 
    		 HashMap<String, String> map = new HashMap<String, String>();
    		 map.put("Rev", "Privacy");
    		 mylist.add(map);
    		 
    		 map = new HashMap<String, String>();
    		 map.put("Rev", "Terms"); 
    		 mylist.add(map);
    		 
    		 map = new HashMap<String, String>();
    		 map.put("Rev", "FAQ");
    		 mylist.add(map);
    		 
    		 map = new HashMap<String, String>();
    		 map.put("Rev", "Contact");
    		 mylist.add(map);
    		 // ...
    		 SimpleAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.cusmorerow,
    		             new String[] {"Rev"}, new int[] {R.id.txtText});
    		 list.setAdapter(mSchedule);
    		 

    		 list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
 
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						TextView tv =(TextView)arg1.findViewById(R.id.txtText);
    	  				Intent AR = new Intent(getApplicationContext(), Info.class);
    	  		  		AR.putExtra("type",tv.getText().toString().toLowerCase());
    	  		  		 
    	  		           startActivity(AR);
						
					}
    	  			
    	  			
           
        });
        
    	}
}
