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
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ArtistFrom extends Activity {
	
	 public final static String ITEM_TITLE = "title";  
	 public final static String ITEM_CAPTION = "caption";  
	 final ArrayList<String> gen = new ArrayList<String>();
	 final ArrayList<String> ids = new ArrayList<String>();
	 final ArrayList<String> slug = new ArrayList<String>();
	 	   ArrayList<HashMap<String, String>> mylist;
	 	   String s;
	 	   TextView genre;
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
	        setContentView(R.layout.ar_from);
	        arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
	    	folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
	        genre = (TextView)findViewById (R.id.txtGenre);
	        genre.setTypeface(arialFont); 
	        s = getIntent().getStringExtra("char");  
	        grabURL("");
	    }
	    
	    
	    // Create a JSON connection with ticketline server to acquire artist information
	    public void makeRequest() {
	    	genre.setText(s+" Artists"); 
	        mylist = new ArrayList<HashMap<String, String>>();  
		    
	        String modified = s.replaceAll(" ", "-").replaceAll("&", "and");  
		    JSONObject json = JSONfunctions.getJSONfromURL("http://api.ticketline.co.uk//artist?method=getByTag&tag-slug="+modified+"&api-key=NGNkZGRhYjkzY2Z&on-sale=true");       
		    
		    try {
		           JSONArray  earthquakes = json.getJSONArray("categories");
			       
		           for(int i=0;i<earthquakes.length();i++) {
						HashMap<String, String> map = new HashMap<String, String>();	
						JSONObject e = earthquakes.getJSONObject(i);

						map.put("id",  String.valueOf(i));
			        	map.put("name",  e.getString("name"));
			        	map.put("id",   e.getString("id"));
			        	map.put("slug",   e.getString("slug"));
			        	ids.add( e.getString("id"));
			        	gen.add(e.getString("name"));
			        	slug.add(e.getString("slug"));
			        	mylist.add(map);			
					}
		           
		        } catch(JSONException e) {
		        	 Log.e("log_tag", "Error parsing data "+e.toString());
		          }
	    }
	    
	    public void populate() {
	    	
	        SeparatedListAdapter adapter = new SeparatedListAdapter(this); // added   	        
	        String [] some_array = getResources().getStringArray(R.array.alpha);
		       
	        for(int i=0;i<some_array.length;i++) {
	        	final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	        	
	        	for(int a=0;a<gen.size();a++) {
	        		if(gen.get(a).startsWith(some_array[i])) {
	        			HashMap<String, String> map = new HashMap<String, String>();
			        	map.put("name", gen.get(a));
			        	map.put("id", ids.get(a));
			        	map.put("slug", slug.get(a));
			        	mylist.add(map);
			        	
	        	    }
	        	}
	        	
	        	adapter.addSection(some_array[i], new SimpleAdapter(this, mylist , R.layout.venue_list_item, 
    	        		new String[] {"name","id","slug"}, 
    	                    new int[] { R.id.list_item_title,R.id.toptext,R.id.txtArtistId}));
	        }

	       ListView list = (ListView) findViewById(R.id.lstArtists);  
	       list.setAdapter(adapter);  
	     
	       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub
					TextView name = (TextView)arg1.findViewById(R.id.list_item_title);
					TextView id = (TextView)arg1.findViewById(R.id.toptext);
					TextView slug = (TextView)arg1.findViewById(R.id.txtArtistId);
					
					final String selectedName1 = name.getText().toString();
					//selectedName = selectedName.replaceAll(" ", "-");
					final String selectedId1 =  id.getText().toString();
					final String selectedSlug =  slug.getText().toString();
					
					showTickets(selectedId1,selectedName1,selectedSlug);
				
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
		
		public void showEvents(String id,String name) {
			Log.i("Events","Events clicked");
			Intent AR = new Intent(getApplicationContext(), Events.class);
			AR.putExtra("name",name );
			AR.putExtra("artist-id",id );
			AR.putExtra("method","getByArtist" );
	        startActivity(AR);
	        myDialog.dismiss();
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
	    
	    // Create and run loading dialog
	    public void showLoadDialog(String n) {	
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
	    
	    // Bundle name, slug, artist-id and saved into intent and start tickets activity
	    public void showTickets(String id,String name, String Slug) {
			 Intent AR = new Intent(getApplicationContext(),  Tickets.class);
			 AR.putExtra("Name",name );
			 AR.putExtra("Slug", Slug);
			 AR.putExtra("artist-id",id );
			 AR.putExtra("saved", "false");
	         startActivity(AR);
		}
	    
	    // on back key being pressed 
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        
	    	if (keyCode == KeyEvent.KEYCODE_BACK){
	        	if(getIntent().getStringExtra("TAB").equalsIgnoreCase("2")){
	        		 Intent AR = new Intent(getApplicationContext(),TicketlineActivity.class);
	        		 AR.putExtra("TAB", "2");
	        		 AR.putExtra("class","Genre");
	        		 startActivity(AR);
	        	 } else {
	        		 finish();
	        	   }
	               return true;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
}
