package com.ywx.ticketlinewebapp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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


public class GenreSearch extends Activity {
	Drawable drawable;
	ImageManager im;
	 public final static String ITEM_TITLE = "title";  
	    public final static String ITEM_CAPTION = "caption";  
	    final ArrayList<String> idArray = new ArrayList<String>();
	    final ArrayList<String> idToPass = new ArrayList<String>();
	      final ArrayList<String> gen = new ArrayList<String>();
	      final ArrayList<String> newArray = new ArrayList<String>();
	      final ArrayList<Integer> imageArr = new ArrayList<Integer>() ;
	      Dialog myDialog;
	      TypedArray icons;
	      String[] options;
	      Button artist;
	      Button date;
	      Button venue;
	      Button genre;
	      Button location;
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
	        setContentView(R.layout.genre_search);
	        im = new ImageManager();
	        arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
	        folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
	        
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
	        
	      
	        Context ctx = getApplicationContext();
			Resources res = ctx.getResources();
			 options = res.getStringArray(R.array.genre_names);
			 icons = res.obtainTypedArray(R.array.genre_icons);
	    
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
	        
			return null;
	            
	            
	        }
	        
	        protected void onPostExecute(Void unused) {
	        	populate();
	        	myDialog.dismiss();
	        }
	        
	    }
	    
	    public void makeRequest(){
	   
	        JSONObject json = JSONfunctions.getJSONfromURL("http://api.ticketline.co.uk//tag?method=getAll&genre=:genre&api-key=NGNkZGRhYjkzY2Z");       
	        try{
	        	
	        	JSONArray  earthquakes = json.getJSONArray("categories");
	        	
		        for(int i=0;i<earthquakes.length();i++){						
					JSONObject e = earthquakes.getJSONObject(i);

		        	if(e.getString("is_ticketline_genre").equalsIgnoreCase("true")){
		        	gen.add(e.getString("name"));
		        	String n = e.getString("name").replaceAll(" ", "_").replaceAll("&", "and").toLowerCase();
		
		        	int resID = getResources().getIdentifier(n, "drawable", "com.Ticketline.Ticketline");
		      	imageArr.add(resID);

		        	idArray.add(e.getString("id"));
		        	
		        	}
				}		
	        }catch(JSONException e)        {
	        	 Log.e("log_tag", "Error parsing data "+e.toString());
	        }
	    }
	    public void populate(){

		        Log.i("Array List",gen.toString());
		        
		        SeparatedListAdapter adapter = new SeparatedListAdapter(this); //added
		 
		        
		        String[] some_array = getResources().getStringArray(R.array.alpha);
		       
		        for(int i=0;i<some_array.length;i++){
		        	final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		        	for(int a=0;a<gen.size();a++){
		        		if(gen.get(a).startsWith(some_array[i])){
		        			
		        			HashMap<String, String> map = new HashMap<String, String>();
				        	//map.put("id",  String.valueOf(i));
				        	map.put("name", gen.get(a));
				        	
				        	idToPass.add(gen.get(a));
				        	mylist.add(map);
				        	
		        		}
		        		
		        	}

		        	adapter.addSection(some_array[i], new SimpleAdapter(this, mylist , R.layout.genre_row, 
		        		new String[] {"name"}, 
		                    new int[] { R.id.list_item_title}));
		        	
		        	
		        }
		       
		        
		     
		       final ListView list = (ListView) findViewById(R.id.lstGenre); 
		       list.setFastScrollEnabled(true);
		       
		   	list.setAdapter(new genreAdapter(GenreSearch.this, R.layout.genre_row,
    				options, icons));
	        
		        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
							long arg3) {
						// TODO Auto-generated method stub
						
						
						TextView textView = (TextView) arg1.findViewById(R.id.list_item_title); 
					    String text = textView.getText().toString();
						Log.i("Character",text);
						
						Intent PR = new Intent(getApplicationContext(),ArtistFrom.class);
						PR.putExtra("char",text );
						PR.putExtra("TAB","2");
						startActivity(PR);
				 		
					}
		
		        });
		     
	    }
	    
	    public void Date(View button){
			Intent DT = new Intent(getParent(),DateSearch.class);
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("DateSearchActivity", DT);
		}
		
		public void Genre(View button){
			Intent GE = new Intent(getParent(),GenreSearch.class);
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("GenreSearchActivity", GE);
		}
		
		public void Location(View button){
			Intent LO = new Intent(getParent(),LocationSearch.class);
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("LocationSearchActivity", LO);
		}
		
		public void Venue(View button){
			Intent VE = new Intent(getParent(),VenueSearch.class);
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("VenueSearchActivity", VE);
		}
		
		public void Artist(View button){
			Intent AR = new Intent(getParent(),ArtistSearch.class);
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
	  
}
