package com.ywx.ticketlinewebapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
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

public class ArtistList extends Activity {
	
	 public final static String ITEM_TITLE = "title";  
	 public final static String ITEM_CAPTION = "caption"; 
	 final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	    
	 	   ArrayList<String>names = new ArrayList<String>();
	 	   ArrayList<String>images = new ArrayList<String>();
	 	   ArrayList<String>slugs = new ArrayList<String>();
	 	   ArrayList<String>ids = new ArrayList<String>();
	 	   String s;
	 	   Dialog myDialog;
	 	   Typeface arialFont;
	 	   Typeface folksFont;
	    
	    public Map<String,?> createItem(String title, String caption) {  
	        Map<String,String> item = new HashMap<String,String>();  
	        item.put(ITEM_TITLE, title);  
	        item.put(ITEM_CAPTION, caption);  
	        return item;  
	    }  
	    
	    @Override  
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.ar_search);
	        arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf"); // added
	        folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
	        
	        s = getIntent().getStringExtra("char");
	        grabURL("");
	    }
	    
	    public void grabURL(String url) {
	        new GrabURL().execute(url);
	    }
	    
	    private class GrabURL extends AsyncTask<String, Void, Void> {
	      
	       // private ProgressDialog Dialog = new ProgressDialog(getParent());
	        
	        protected void onPreExecute() {
	            //Dialog.setMessage("Loading Artists..Please wiat...");
	           // Dialog.show();
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
	   
	        	JSONObject json = JSONfunctions.getJSONfromURL("http://api.ticketline.co.uk//artist?method=getByAtoZ&first-char="+s+"&api-key=NGNkZGRhYjkzY2Z&on-sale=true");       
	        	try{
	        	
	        		JSONArray  earthquakes = json.getJSONArray("categories");
	        	
	        		for(int i=0;i<earthquakes.length();i++){						
	        			HashMap<String, String> map = new HashMap<String, String>();	
	        			JSONObject e = earthquakes.getJSONObject(i);
					
	        			map.put("id",        String.valueOf(i));
	        			map.put("name",      e.getString("name"));
	        			map.put("magnitude", e.getString("id"));
	        			map.put ("url",      e.getString("image_base_url"));
	        			map.put("image",     e.getString("image_default"));
  		        	
	        		    ids.add(e.getString("id"));
	        			names.add(e.getString("name"));
		        	    slugs.add(e.getString("slug"));
		        	    images.add(e.getString("image_base_url")+e.getString("image_default"));
		        	    mylist.add(map);			
				    }		
	            }  catch(JSONException e) {
	        	   Log.e("log_tag", "Error parsing data "+e.toString());
	               }
	    }
	    
	    public void populate() {
	        
	        SeparatedListAdapter adapter = new SeparatedListAdapter(this); 
	        adapter.addSection(s, new SimpleAdapter(this, mylist , R.layout.venue_list_item, 
	        	new String[] { "name" }, 
                   new int[] { R.id.list_item_title})); 
	        
	        ListView list = (ListView) findViewById(R.id.lstArtists);  
	        list.setAdapter(adapter);  
	        list.setFastScrollEnabled(true);
	        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					
					String name = names.get(arg2-1);
					String id = ids.get(arg2-1);
					String slug = slugs.get(arg2-1);
					showTickets(id,name,slug);
			    }
	        });
	    }
	    
	    public void Date(View button) {
			Intent DT = new Intent(getParent(),DateSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("DateSearchActivity", DT);
		}
		
		public void Genre(View button) {
			Intent GE = new Intent(getParent(),GenreSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("GenreSearchActivity", GE);
		}
		
		public void Location(View button) {
			Intent LO = new Intent(getParent(),LocationSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("LocationSearchActivity", LO);
		}
		
		public void Venue(View button) {
			Intent VE = new Intent(getParent(),VenueSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("VenueSearchActivity", VE);
		}
		
		public void Artist(View button) {
			Intent AR = new Intent(getParent(),ArtistSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("VenueSearchActivity", AR);
		}
		
	
		public void showBio(String id,String name) {
			Log.i("Bio","Bio clicked");
			Intent AR = new Intent(getApplicationContext(),  Bio.class);
			AR.putExtra("name",name );
			AR.putExtra("Id",id );
			AR.putExtra("saved","false" );
	        startActivity(AR);
	        myDialog.dismiss();
		}
		
		public void showEvents(String id,String name,String url) {
			Log.i("Events","Events clicked");
			Intent AR = new Intent(getApplicationContext(), Events.class);
			AR.putExtra("name",name );
			AR.putExtra("artist-id",id );
			AR.putExtra("url", url);
			AR.putExtra("method","getByArtist" );
	        startActivity(AR);
	        myDialog.dismiss();
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
		
		public void showTickets(String id,String name, String Slug){
			
			 Intent AR = new Intent(getApplicationContext(),  Tickets.class);
			 AR.putExtra("Name",name );
			 AR.putExtra("Slug", Slug);
			 AR.putExtra("artist-id",id );
			 AR.putExtra("saved", "false");
	         startActivity(AR);
		}	
}
