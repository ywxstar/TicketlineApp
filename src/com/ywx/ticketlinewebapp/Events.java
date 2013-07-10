package com.ywx.ticketlinewebapp;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Events extends Activity {

	 final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
     ArrayList<String> ids = new ArrayList<String>();
     ArrayList<String> lons = new ArrayList<String>();
     ArrayList<String> names = new ArrayList<String>();
     ArrayList<String> dates = new ArrayList<String>();
     ArrayList<String> available = new ArrayList<String>();
     ArrayList<String> trimmedDates = new ArrayList<String>();
     ArrayList<String> monthsArray = new ArrayList<String>();
     ArrayList<String> eventsArray = new ArrayList<String>();
     ArrayList<String> city = new ArrayList<String>();
     ArrayList<String> artistId = new ArrayList<String>();
     String Event;
     static ArrayList<String> ci = new ArrayList<String>();
     ListView list;
     ProgressDialog progressDialog;
     TextView title;
     Functions f;
     String s;
     Dialog myDialog;
     Typeface arialFont;
     Typeface folksFont;
	 public final static String ITEM_TITLE = "title";  
	 public final static String ITEM_CAPTION = "caption";  
	  
	 public Map<String,?> createItem(String title, String caption) {  
	        Map<String,String> item = new HashMap<String,String>();  
	        item.put(ITEM_TITLE, title);  
	        item.put(ITEM_CAPTION, caption);  
	        return item;  
	    }  
	  
	    
		@Override  
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.events);
	        f = new Functions();
	        
	        arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
	        folksFont = Typeface.createFromAsset(this.getAssets(), "Folks-Normal-webfont.ttf");
	        
	        title = (TextView)findViewById(R.id.VenueName);
	        title.setTypeface(arialFont);
	        title.setText(getIntent().getStringExtra("name"));

	 
	        monthsArray.add("January");
	        monthsArray.add("February");
	        monthsArray.add("March");
	        monthsArray.add("April");
	        monthsArray.add("May");
	        monthsArray.add("June");
	        monthsArray.add("July");
	        monthsArray.add("August");
	        monthsArray.add("September"); // DAZ 18/01/12 Spelt September Correctly
	        monthsArray.add("October");
	        monthsArray.add("November");
	        monthsArray.add("December");
	        grabURL("");

		   }
			
			   public void grabURL(String url) {
			        new GrabURL().execute(url);
			    }
			    
			    private class GrabURL extends AsyncTask<String, Void, Void> {

			        protected void onPreExecute() {
			        showLoadDialog("Loading...");
			        }

			        protected Void doInBackground(String... urls) {
			        makeRequest();
			        
					return null;
			            
			            
			        }
			        
			        protected void onPostExecute(Void unused) {
			        	populate();
			        	myDialog.dismiss();
			            
			        }
			        
			    }
			
			    public void showLoadDialog(String n){
			    	
				   	 myDialog = new Dialog(this);
				   	 myDialog.getWindow().setFlags( 
				   				WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
				   				WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
				   		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				   		myDialog.setContentView(R.layout.load_dialog);
				   		
				   		TextView tv = (TextView)myDialog.findViewById(R.id.txtLoad);
				   		tv.setText(n);

				   		myDialog.show();
				   }
			    
	public void makeRequest(){
		HttpGet get;
		//handle the on sale today function fro home screen
       if(getIntent().getStringExtra("class").equalsIgnoreCase("Home")){
    	   String from = getIntent().getStringExtra("from");
    	   String to = getIntent().getStringExtra("to");
    	  
    	   Log.e("dates",from+"     "+to);

    	   DefaultHttpClient client = new DefaultHttpClient();		    
	       get = new HttpGet("http://api.ticketline.co.uk/event?method=getByDateRange&api-key=NGNkZGRhYjkzY2Z&date-from="+from+"&date-to="+to);
 
           try {
                 HttpResponse response = client.execute(get);
                 Log.v("t", response.getStatusLine().toString());                 
                 ByteArrayOutputStream ostream = new ByteArrayOutputStream();                 
                 response.getEntity().writeTo(ostream);             
                // Log.e("HTTP CLIENT", ostream.toString());
                 
                  s="{\"categories\":"+ostream.toString()+"}";
   	           Log.i("response",s);
           
   	        parseSaleData(s);
   	        
               
          } catch (Exception e) 
           { 
                 e.printStackTrace();
                 
           } 
       }else{
		   
        DefaultHttpClient client = new DefaultHttpClient();
        /*
        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//event");
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("method", getIntent().getStringExtra("getByVenue")));
        nvps.add(new BasicNameValuePair("venue-id", getIntent().getStringExtra("venue-id")));
        nvps.add(new BasicNameValuePair("artist-id", getIntent().getStringExtra("artist-id")));
        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
        */
        
	    get = new HttpGet("http://api.ticketline.co.uk/event?method=getByVenue&api-key=NGNkZGRhYjkzY2Z&venue-id="+getIntent().getStringExtra("venue-id"));      
        try {
             HttpResponse response = client.execute(get);
              Log.v("t", response.getStatusLine().toString());

              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
              
              response.getEntity().writeTo(ostream);
          
              Log.e("HTTP CLIENT", ostream.toString());
              
               s="{\"categories\":"+ostream.toString()+"}";
	           Log.i("response",s);
	           
	           if(s.contains("no venues found")) {
	        	   
	        	   
	           }
        
	        parseData(s);
	        
            
       } catch (Exception e) 
        { 
              e.printStackTrace();
              
        } 
       
      Log.i("Trimmed dates",trimmedDates.toString());
       }//end else statement
	}
	
	public void parseData(String s) throws JSONException{
		 JSONObject jArray = new JSONObject(s);
         JSONArray  earthquakes = jArray.getJSONArray("categories");
      	
         for(int i=0;i<earthquakes.length();i++){							
				JSONObject e = earthquakes.getJSONObject(i);

				Log.i("event id",e.getString("id"));
				ids.add(e.getString("id"));
				
				Log.i("event name",e.getString("event_name"));
				eventsArray.add(e.getString("event_name"));
				
				Log.i("venue_name",e.getString("venue_space_name"));
				names.add(e.getString("venue_space_name"));
				Log.i("Event Date",e.getString("datetime"));
				dates.add(e.getString("datetime"));
				trimmedDates.add(e.getString("datetime").substring(0,7));
				Log.i("Seating Map Name",e.getString("seating_map_name"));
				
				Log.i("prices",e.getString("sql_min_price"));
				available.add(e.getString("sql_min_price"));
				
				Log.i("Event",e.getString("Event"));

	        	Event = e.getString("Event");
	        	
	        	
	        	Log.i("Event json",Event);

	        	  JSONObject eventArray = new JSONObject(Event);
			        JSONArray  event = eventArray.getJSONArray("Artists");
		        	for(int a=0;a<event.length();a++){
		        		JSONObject ee = event.getJSONObject(a);
		        		Log.i("Artist id",ee.getString("id"));
		        		artistId.add(ee.getString("id"));  
		        	}	

      	}
	}
	
	public void parseSaleData(String s) throws JSONException{
		 JSONObject jArray = new JSONObject(s);
        JSONArray  earthquakes = jArray.getJSONArray("categories");
     	Log.e("sale data ",earthquakes.length()+"");
        for(int i=0;i<earthquakes.length();i++){						
				JSONObject e = earthquakes.getJSONObject(i);
				
				Log.i("event id",e.getString("id"));
				ids.add(e.getString("id"));
				
				Log.i("event name",e.getString("event_name"));
				eventsArray.add(e.getString("event_name"));
				
				Log.i("venue_name",e.getString("venue_space_name"));
				names.add(e.getString("venue_space_name"));
				Log.i("Event Date",e.getString("datetime"));
				dates.add(e.getString("datetime"));
				trimmedDates.add(e.getString("datetime").substring(0,7));
				Log.i("Seating Map Name",e.getString("seating_map_name"));
				
				Log.i("prices",e.getString("sql_min_price"));
				available.add(e.getString("sql_min_price"));
				
     	}
	}
	
	public static void removeDuplicate(ArrayList arlList)
	{
	 HashSet h = new HashSet(arlList);
	 arlList.clear();
	 arlList.addAll(h);

	}

	
	public void populate(){
		SeparatedListAdapter adapter = new SeparatedListAdapter(this); // changed
		
		 removeDuplicate(trimmedDates);
		 Collections.sort(trimmedDates);

		   for(int i=0;i<trimmedDates.size();i++){
	        	final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	        	for(int a=0;a<dates.size();a++){
	        		if(dates.get(a).substring(0,7).equalsIgnoreCase(trimmedDates.get(i))){
	        			
	        			HashMap<String, String> map = new HashMap<String, String>();
			        	map.put("id",  ids.get(a));
			        	map.put("eventname",eventsArray.get(a));
			        	map.put("venue", names.get(a));
			        	map.put("date", f.createDate(dates.get(a).substring(0,10),dates.get(a).substring(11).trim()));
			        	map.put("available", "Tickets From \u00a3"+available.get(a)+" inc booking fee");
			        	
			        	if(!artistId.isEmpty()){
			        	map.put("artistId",artistId.get(a));
			        	}
			        	mylist.add(map);
			        	
	        		}
	        		
	        	}
	        	
	        	int monVal = Integer.parseInt(trimmedDates.get(i).substring(5,7));
	        	
	        	if(getIntent().getStringExtra("method").equalsIgnoreCase("getByArtist")){
	        	adapter.addSection(monthsArray.get(monVal-1)+" "+trimmedDates.get(i).substring(0,4), new SimpleAdapter(this, mylist , R.layout.event_list_item, 
		        		new String[] {"name","id","date","artistId"}, 
		                    new int[] { R.id.list_item_title,R.id.toptext,R.id.txtDate,R.id.txtArtistId}));
	        	
	        	
	        	}else if(getIntent().getStringExtra("method").equalsIgnoreCase("onSaleDate")){
	        		adapter.addSection(monthsArray.get(monVal-1)+" "+trimmedDates.get(i).substring(0,4), new SimpleAdapter(this, mylist , R.layout.event_list_item, 
			        		new String[] {"eventname","venue","date","available","artistId","id"}, 
			                    new int[] { R.id.list_item_title,R.id.txtVenue,R.id.txtDate,R.id.txtAvailable, R.id.txtArtistId, R.id.toptext}));
	        		
	        		/*TextView txtItemTitle = (TextView)findViewById(R.id.list_item_title);
	        		TextView txtVenue = (TextView)findViewById(R.id.txtVenue);
	        		TextView txtDate = (TextView)findViewById(R.id.txtDate);
	        		TextView txtAvailable = (TextView)findViewById(R.id.txtAvailable);
	        		txtItemTitle.setTypeface(folksFont);
	        		txtVenue.setTypeface(folksFont);
	        		txtDate.setTypeface(arialFont);
	        		txtAvailable.setTypeface(arialFont);*/
	        		
	        	}
	        	
	        	else{
	        		adapter.addSection(monthsArray.get(monVal-1)+" "+trimmedDates.get(i).substring(0,4), new SimpleAdapter(this, mylist , R.layout.event_list_item, 
			        		new String[] {"eventname","venue","date","available","artistId","id"}, // "artistId" "id"
			                    new int[] { R.id.list_item_title,R.id.txtVenue,R.id.txtDate,R.id.txtAvailable, R.id.txtArtistId,R.id.toptext }));
	        		
	        	}
	    
	        }
		   
      list = (ListView) findViewById(R.id.lstVenue); 
      list.setFastScrollEnabled(true);
      list.setAdapter(adapter);
      
        
        
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
				TextView tv = (TextView)arg1.findViewById(R.id.list_item_title);
				String s= tv.getText().toString();
				Log.i("Selected Name",s);
				
				TextView tv1 = (TextView)arg1.findViewById(R.id.toptext);
				String ss= tv1.getText().toString();
				Log.i("Selected Id",ss);
				
				TextView aid = (TextView)arg1.findViewById(R.id.txtArtistId);
				String arid= aid.getText().toString();
				Log.i("Selected Id",arid);
				
				TextView day = (TextView)arg1.findViewById(R.id.txtDate);
				String strDay= day.getText().toString();
				Log.i("Selected Id",strDay);
				
				
				if(getIntent().getStringExtra("method").equalsIgnoreCase("OnSaleDate")){
					Intent AR = new Intent(getApplicationContext(),  Prices.class);
					 AR.putExtra("event-id",ss );
					 AR.putExtra("ven_name",arid );
					 AR.putExtra("event_date",strDay );
					 AR.putExtra("TAB","");
					 AR.putExtra("ar_name",s);
			         startActivity(AR);
				}else{
				Intent AR = new Intent(getApplicationContext(),  Prices.class);
				 AR.putExtra("event-id",ss );
				 AR.putExtra("ven_name",title.getText().toString()  );
				 AR.putExtra("event_date",strDay );
				 AR.putExtra("artist_id",arid);
				 AR.putExtra("ar_name",s);
				 AR.putExtra("url",getIntent().getStringExtra("url") );
				 AR.putExtra("TAB", "0");
		         startActivity(AR);
				}
		 	
			}

        });
		
	}
	
}
