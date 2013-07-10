package com.ywx.ticketlinewebapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Tour extends Activity {
	String name;
	String id;
	TextView tv;
	TextView tourDesc;
	Database data;
	View arg1;
	Typeface arialFont;
	ArrayList<Database>ques = new ArrayList<Database>();
	Functions f;
	Button tickets;
	Button tour;
	Button bio;
	ArrayList<String> eventsArray = new ArrayList<String>();
	ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> venids = new ArrayList<String>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> dates = new ArrayList<String>();
    Dialog myDialog;
	  
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour);
        showLoadDialog("Loading Tour..");
        f = new Functions();
        name = getIntent().getStringExtra("Name");
        id = getIntent().getStringExtra("artist-id");

        
        arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
        tv = (TextView) findViewById(R.id.txtName);
        tv.setText(name);
        tv.setTypeface(arialFont);
        
        tourDesc = (TextView)findViewById(R.id.txtTourDesc);
    //  tourDesc.setTypeface(myTypeface1); DAZ 22/1/12 Paragraphs set to standard font
       
        tickets = (Button)findViewById(R.id.Button03);
        tour = (Button)findViewById(R.id.Button02);
        bio = (Button)findViewById(R.id.Button01);
        
        tickets.setTypeface(arialFont);
        tour.setTypeface(arialFont);
        bio.setTypeface(arialFont);
        
        //change this later if required
        tourDesc.setText("No Tour Details Available");	
        
        data = new Database("Test","Test","test","Check");
		 ques.add(data);
       
		 makeRequest();
	}
	
	public void makeRequest(){
		
		 try {
				getTours(f.getArtistTours());
			//	 getEvents(f.getSavedEvents());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void getTours(String s) throws JSONException{
	       JSONObject jArray = new JSONObject(s);
           JSONArray  earthquakes = jArray.getJSONArray("tours");
        	
           for(int i=0;i<earthquakes.length();i++){							
				JSONObject e = earthquakes.getJSONObject(i);
				
				//Log.i("event id",e.getString("event_id"));
				//ids.add(e.getString("event_id"));
				
				Log.i("tour id",e.getString("id"));
				Log.i("Available Until",e.getString("available_until"));
				Log.i("Tour description",e.getString("description"));
				if(tourDesc !=null){
				tourDesc.setText(Html.fromHtml(e.getString("description")));
				}
				Log.i("On sale from",e.getString("on_sale_from"));
	        
	      
        	}
           myDialog.dismiss();
	}
	
	public void back(View button){
		Intent SeeMore = new Intent(getParent(),SeeMore.class);
 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
 		parentActivity.startChildActivity("MoreViewActivity", SeeMore);
	}
	
	public void Bio(View button){
		Intent Bio = new Intent(getApplicationContext(),Bio.class);
		Bio.putExtra("Name", name);
		Bio.putExtra("saved","true");
		Bio.putExtra("artist-id", id);
		 startActivity(Bio);
		 finish();
		//finish();
	}
	
	

	
	public void Reviews(View button){
		Intent Rev = new Intent(getApplicationContext(),Reviews.class);
		Rev.putExtra("Name", name);
		Rev.putExtra("artist-id", id);
		Rev.putExtra("saved","true");
		 startActivity(Rev);
		 finish();
	}
	
	public void Tickets(View button){
		Intent Med = new Intent(getApplicationContext(),Tickets.class);
		Med.putExtra("Name", name);
		Med.putExtra("artist-id", id);
		Med.putExtra("saved","true");
		 startActivity(Med);
	}
	public void Links(View button){
		Intent Links = new Intent(getApplicationContext(),Links.class);
		Links.putExtra("Name", name);
		 startActivity(Links);
	}
	
	public void parseData(String url) {
        new ParseData().execute(url);
    }
    
	 private class ParseData extends AsyncTask<String, Void, Void> {
	     
	       
	        
	        protected void onPreExecute() {
	         //   Dialog.setMessage("Loading...");
	         //   Dialog.show();
	        	Log.i("Load","Loading");
	        	showLoadDialog("Loading..");
	        }

	        protected Void doInBackground(String... urls) {
	        	  
	        	makeRequest();
	        	//	getEvents(f.getEventsByArtist(getIntent().getStringExtra("artist-id")));
				
	        	
			return null;
	            
	            
	        }
	        
	        protected void onPostExecute(Void unused) {
	        	
	        	
	        	myDialog.dismiss();
	        	//myDialog.cancel();
	            
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
	
}
