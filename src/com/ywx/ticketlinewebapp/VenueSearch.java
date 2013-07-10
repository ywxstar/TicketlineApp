package com.ywx.ticketlinewebapp;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class VenueSearch extends Activity {
	
         ArrayList<String> lats = new ArrayList<String>();
         ArrayList<String> lons = new ArrayList<String>();
         ArrayList<String> names = new ArrayList<String>();
         ArrayList<String> city = new ArrayList<String>();
     	 ArrayList<String> ids = new ArrayList<String>();
  static ArrayList<String> ci = new ArrayList<String>();
     	 ListView list;
         ProgressDialog progressDialog;
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
	        setContentView(R.layout.venue_search);
	        
	        arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
	        folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
	        
	        Button artistButton = (Button)findViewById(R.id.Button01);
	        Button dateButton = (Button)findViewById(R.id.Button02);
	        Button venueButton = (Button)findViewById(R.id.Button03);
	        Button genreButton = (Button)findViewById(R.id.Button04);
	        Button locationButton = (Button)findViewById(R.id.Button05);
	        
	        artistButton.setTypeface(arialFont);
	        dateButton.setTypeface(arialFont);
	        venueButton.setTypeface(arialFont);
	        genreButton.setTypeface(arialFont);
	        locationButton.setTypeface(arialFont);
	        
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
			
	    
	public void makeRequest(){

       
		   
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//venue");
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("method", "getAllCities"));
        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
       
        try {
              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
HTTP.UTF_8);
              httppost.setEntity(p_entity);
              HttpResponse response = client.execute(httppost);
              Log.v("t", response.getStatusLine().toString());

              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
              
              response.getEntity().writeTo(ostream);
          
              Log.e("HTTP CLIENT", ostream.toString());
               
              String s=ostream.toString();
              s = s.replaceAll("\"", "").replace("[", "");
             
	           Log.i("response",s);
	           
	           StringTokenizer st = new StringTokenizer(s, ",");
				while (st.hasMoreTokens()) {
					names.add(st.nextToken());
				}
             
        } catch (Exception e) 
        { 
              e.printStackTrace();
              
        } 
       
    Collections.sort(city);
      
	}
	
	public static void removeDuplicate(ArrayList arlList)
	{
	 HashSet h = new HashSet(arlList);
	 arlList.clear();
	 arlList.addAll(h);
	 ci = arlList;
	}

	
	public void populate(){
		SeparatedListAdapter adapter = new SeparatedListAdapter(this); 
		 String[] some_array = getResources().getStringArray(R.array.alpha);
		 
	        for(int i=0;i<some_array.length;i++){
	        	 final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	        	for(int a=0;a<names.size();a++){
	        		if(names.get(a).startsWith(some_array[i])){
	        			
	        			HashMap<String, String> map = new HashMap<String, String>();
			        	map.put("name", names.get(a));
			        	mylist.add(map);
			        	
	        		}
	        		
	        	}
	        	
	        	Log.i("My Map",mylist.toString());
	
	        	adapter.addSection(some_array[i], new SimpleAdapter(this, mylist , R.layout.venue_list_item, 
		        		new String[] {"name"}, 
		                    new int[] { R.id.list_item_title}));
	    
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
				
				showVenueList(s);		 	
			}

        });
		
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
			
			 myDialog = new Dialog(getParent());
			 myDialog.getWindow().setFlags( 
						WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
						WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
				myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				myDialog.setContentView(R.layout.load_dialog);
				
				TextView tv = (TextView)myDialog.findViewById(R.id.txtLoad);
				tv.setText(n);
				myDialog.show();
		}
		
		public void showVenueList(String name){
			Intent AR = new Intent(getParent(),  VenueList.class); 
			 AR.putExtra("name",name );
			 AR.putExtra("TAB","2" );
	         startActivity(AR);
	     
		}
	  
}

