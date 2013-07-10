package com.ywx.ticketlinewebapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class DateSearch extends Activity {
	 public final static String ITEM_TITLE = "title";  
	    public final static String ITEM_CAPTION = "caption"; 
	    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	    
	    ListView list ;
	    String date_selected;
	    String date_to;
	    Functions f;
	    Dialog loadDialog;
	    Button artist;
	    Button date;
	    Button venue;
	    Button genre;
	    Button location;
	    Typeface folksFont;
	    Typeface arialFont; //changed
	    
	    static final int DATE_DIALOG_ID = 0;
	    
	   ArrayList<String> datesArray = new ArrayList<String>();
	   ArrayList<String> passDates = new ArrayList<String>();
	   ArrayList<String> namesArray = new ArrayList<String>();
	   ArrayList<String> idsArray = new ArrayList<String>();
	   ArrayList<String> venueArray = new ArrayList<String>();
	   ArrayList<String> available = new ArrayList<String>();
	   ArrayList<String> statusArray = new ArrayList<String>();
	   
	   public Map<String,?> createItem(String title, String caption) {  
	        Map<String,String> item = new HashMap<String,String>();  
	        item.put(ITEM_TITLE, title);  
	        item.put(ITEM_CAPTION, caption);  
	        return item;  
	    }  
	  
	    public static String now() {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
            return sdf.format(cal.getTime());

          }
	    
	    @Override  
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.dt_search);
	        f = new Functions();
	        folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
		    arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
	        Log.i("time",now());
	        artist = (Button)findViewById(R.id.Button01);
	        date = (Button)findViewById(R.id.Button02);
	        venue = (Button)findViewById(R.id.Button03);
	        genre = (Button)findViewById(R.id.Button04);
	        location = (Button)findViewById(R.id.Button05);
	        
	        artist.setTypeface(arialFont);
	        date.setTypeface(arialFont);
	        venue.setTypeface(arialFont);
	        genre.setTypeface(arialFont);
	        location.setTypeface(arialFont);
	        	        
	        String deviceId = Settings.System.getString(getContentResolver(),
                    Settings.System.ANDROID_ID);
	        Log.i("dev id",deviceId);
	        
	        if(namesArray.size()>0){
	        	
	        	namesArray.clear();
	        	passDates.clear();
	        	datesArray.clear();
	        	idsArray.clear();
	        	venueArray.clear();
	        }
	        
	        showDialog(DATE_DIALOG_ID);
	    }  
	    
	 // Creating dialog
	    @Override
	    protected Dialog onCreateDialog(int id) {
	    Calendar c = Calendar.getInstance();
	    int cyear = c.get(Calendar.YEAR);
	    int cmonth = c.get(Calendar.MONTH);
	    int cday = c.get(Calendar.DAY_OF_MONTH);
	    switch (id) {
	    case DATE_DIALOG_ID:
	    return new DatePickerDialog(getParent(),  mDateSetListener,  cyear, cmonth, cday);
	    }
	    return null;
	    }
	    
	    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
	    // onDateSet method
	    	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	    
	    	date_selected = String.valueOf(year)+"-"+String.valueOf(monthOfYear+1)+"-"+String.valueOf(dayOfMonth);
	    	date_to = String.valueOf(year)+"-"+String.valueOf(monthOfYear+1)+"-"+String.valueOf(dayOfMonth+1);
	    	grabURL("");
	    	}
	    	
	    };
	  
	   
	    public void grabURL(String url) {
	        new GrabURL().execute(url);
	    }
	    
	    private class GrabURL extends AsyncTask<String, Void, Void> {
	        
	        protected void onPreExecute() {
	        	showLoadDialog("Loading...");
	        }

	        protected Void doInBackground(String... urls) {
	        makeRequest(f.getSavedEvents());
	        
			return null;

	        }
	        
	        protected void onPostExecute(Void unused) {
	        	populate();
	        	loadDialog.dismiss();
	            
	        }
	        
	    }
	    
	    public void makeRequest(String s){
	 
	        JSONObject json = JSONfunctions.getJSONfromURL("http://api.ticketline.co.uk//event?method=getByDateRange&date-from=" + date_selected + "&date-to=" + date_to + "&api-key=NGNkZGRhYjkzY2Z");       
	        try{

	        	JSONArray  earthquakes = json.getJSONArray("categories");
	        	
		        for(int i=0;i<earthquakes.length();i++){						
					JSONObject e = earthquakes.getJSONObject(i);
					idsArray.add(e.getString("id"));
					venueArray.add(e.getString("venue_space_name"));
		        	datesArray.add(e.getString("datetime"));
		        	passDates.add(e.getString("datetime").substring(0,10));
		        	namesArray.add(e.getString("event_name"));
		        	available.add(e.getString("sql_min_price"));
	
		        /*	String status = "{\"status\":["+e.getString("status_code")+"]}";
					
					Log.i("Status",status);
		        
		      
					 JSONObject jArray1 = new JSONObject(status);
			           JSONArray  earthquakes1 = jArray1.getJSONArray("status_code");
			        	
			           for(int b=0;b<earthquakes1.length();b++){						
							
							JSONObject ee = earthquakes1.getJSONObject(b);
							Log.i("Status",ee.getString("status_code"));

							if(ee.getString("Status").equalsIgnoreCase("A")){
								statusArray.add("On Sale");
							}
							
							else if (ee.getString("Status").equalsIgnoreCase("C")){
							statusArray.add("CANCELLED");
							}
							
							else if (ee.getString("Status").equalsIgnoreCase("D")){
								statusArray.add("DELETED");
								}
							else if (ee.getString("Status").equalsIgnoreCase("H")){
								statusArray.add("TRY AGAIN LATER");
								}
							else if (ee.getString("Status").equalsIgnoreCase("L")){
									statusArray.add("PHONE VENUE TO BOOK");
									}
							else if (ee.getString("Status").equalsIgnoreCase("O")){
								statusArray.add("SOLD OUT");
								}
							else if (ee.getString("Status").equalsIgnoreCase("P")){
								statusArray.add("PHONE TICKETLINE TO BOOK");
								}
							else if (ee.getString("Status").equalsIgnoreCase("R")){
								statusArray.add("OFF SALE");
								}
							else if (ee.getString("Status").equalsIgnoreCase("S")){
								statusArray.add("ON SALE SOON");
								}
			           }*/
				}	
	        }catch(JSONException e){
	        	 Log.e("log_tag", "Error parsing data "+e.toString());
	        }
	    }
	    
	    public void populate(){
	   
	    	SeparatedListAdapter adapter = new SeparatedListAdapter(this); 
	    	removeDuplicate(passDates);
			 
		        for(int i=0;i<passDates.size();i++){
		        	final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		        	  
		        	for(int a=0;a<datesArray.size();a++){
		        		if(datesArray.get(a).substring(0,10).equalsIgnoreCase(passDates.get(i))){
	
		        			HashMap<String, String> map = new HashMap<String, String>();
				        	map.put("id",idsArray.get(a));
				        	map.put("name", namesArray.get(a));
				        	map.put("venue", venueArray.get(a));
				        	map.put("available","Tickets From "+available.get(a)+" inc booking fee");
				        //	map.put("status", statusArray.get(i));
				        	map.put("date", f.createDate(datesArray.get(a).substring(0,10),datesArray.get(a).substring(11).trim()));
				        	mylist.add(map);
		        		}
		        		
		        	}
		        adapter.addSection(f.createHeaderDate(passDates.get(i)), new SimpleAdapter(this, mylist , R.layout.event_list_item, 
			        	new String[] {"name","venue","date","available","id"}, 
			            new int[] { R.id.list_item_title,R.id.txtVenue,R.id.txtDate,R.id.txtAvailable,R.id.toptext}));
		        
		        }
	
	        
		    list = (ListView) findViewById(R.id.lstDate); 
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
					
					TextView tv2 = (TextView)arg1.findViewById(R.id.txtDate);
					String date= tv2.getText().toString();
					Log.i("date",date);
					
			        Intent eve = new Intent(getApplicationContext(),  Prices.class);
			        eve.putExtra("event-id",ss);
			 		eve.putExtra("ar_name",s);
			 		eve.putExtra("ven_name",venueArray.get(arg2-1));
			 		eve.putExtra("event_date",date);
					eve.putExtra("TAB","2");
			        startActivity(eve);
			        // finish();
			 	
				}

	        });
	        	        
	    }
	    
	    public static void removeDuplicate(ArrayList arlList)
		{
		 HashSet h = new HashSet(arlList);
		 arlList.clear();
		 arlList.addAll(h);
		}
	    
	    public void Date(View button){
			Intent DT = new Intent(getParent(),DateSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("DateSearchActivity", DT);
		}
		
		public void Genre(View button){
			Intent GE = new Intent(getParent(),GenreSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("GenreSearchActivity", GE);
		}
		
		public void Location(View button){
			Intent LO = new Intent(getParent(),LocationSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("LocationSearchActivity", LO);
		}
		
		public void Venue(View button){
			Intent VE = new Intent(getParent(),VenueSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("VenueSearchActivity", VE);
		}
		
		public void Artist(View button){
			Intent AR = new Intent(getParent(),ArtistSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("VenueSearchActivity", AR);
		}
		
		public void showLoadDialog(String n){
			
			 loadDialog = new Dialog(getParent());
			 loadDialog.getWindow().setFlags( 
						WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
						WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
				loadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				loadDialog.setContentView(R.layout.load_dialog);
				
				TextView tv = (TextView)loadDialog.findViewById(R.id.txtLoad);
				tv.setText(n);
			
				loadDialog.show();
		}
		
	    
	    
	  
}
