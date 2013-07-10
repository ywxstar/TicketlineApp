package com.ywx.ticketlinewebapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryDetails extends Activity {
	Functions f ;
	TextView transDate;
	TextView status;
	TextView desc;
	TextView event;
	TextView venue;
	TextView date;
	TextView delivery;
	Typeface first, folksFont;
	Dialog myDialog;
	String response;
	String seats;
	String shipping;
	TextView topic;
	ListView l;
	ArrayList<String> details = new ArrayList<String>();
	ArrayList<String> price = new ArrayList<String>();
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_details);
        f = new Functions();
        transDate = (TextView)findViewById(R.id.txtTransactionDate);
        status = (TextView)findViewById(R.id.txtStatus);
        desc = (TextView)findViewById(R.id.txtDesc);
        event = (TextView)findViewById(R.id.txtName);
        venue = (TextView)findViewById(R.id.txtVenue);
        date = (TextView)findViewById(R.id.txtDate);
        delivery = (TextView)findViewById(R.id.txtDelivery);
        topic = (TextView)findViewById(R.id.txtTopic);
        l = (ListView)findViewById(R.id.lstOrder);
        first = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
        folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");

        
        transDate.setTypeface(first);
        status.setTypeface(folksFont);
        desc.setTypeface(first);
        event.setTypeface(folksFont);
        venue.setTypeface(folksFont);
        date.setTypeface(first);
        delivery.setTypeface(first);
        topic.setTypeface(first);
        
        topic.setText("Order ref : "+getIntent().getStringExtra("order-id"));
        
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
	        	
	            response = f.getOrderHistoryDetails(getIntent().getStringExtra("order-id"));
	            
	            Log.e("order",getIntent().getStringExtra("order-id"));
	            
				return null;    
	        }
	        
	        

			protected void onPostExecute(Void unused) {

				try {
					validateResponse(response);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	    
	    public void validateResponse(String res) throws JSONException{
	    	
	    	Log.e("dfafaf","validateResponse"); 
	    	Log.e("dfafaf",res); 
	    	
	    	 JSONObject jArray = new JSONObject(res);
	           JSONArray  earthquakes = jArray.getJSONArray("categories");
	           String ssw = jArray.getString("status");
	           Log.e("dfafaf c",ssw);
	           //myDialog.dismiss();
	           if(earthquakes.toString().equalsIgnoreCase("[]")){
	        	   Log.i("empty",earthquakes.toString()); 
	        	   showAlertDialog("No Details Found");
	           }/*else if(jArray.getString("status")!="200"){
	        	   JSONObject e = earthquakes.getJSONObject(0);
	        	   Log.i("empty",e.getString("error"));
	        	   if(e.has("error") ){
	        		   Log.e("empty",e.getString("error"));
	        		   showAlertDialog(e.getString("error"));
	        		   event.setText("Order can not retrived");
	        		   
	        		   //onBackPressed();
	        	   }
	        	   
	           }*/else{
	        	   /*
 */  
	        	   
	        	   
	        	
	           for(int i=0;i<earthquakes.length();i++){	
	        	   Log.i("earthquakes","earthquakes");
					JSONObject e = earthquakes.getJSONObject(i);
					
					if(e.has("event_name")){
						event.setText(e.getString("event_name"));
					}
					if(e.has("venue_name")){
						venue.setText(e.getString("venue_name"));
					}
					if(e.has("transaction_date")){
						String dat = e.getString("transaction_date").substring(0,10);
						String time = e.getString("transaction_date").substring(11).trim();
						transDate.setText("Ordered on "+f.createDate(dat,time));
						
					}
					if(e.has("description")){
						
						String[] result=e.getString("description").split("<br />");
						String sta = result[0];
						String des = result[1];
						
						status.setText(sta);
						desc.setText(Html.fromHtml(des));
					}
					if(e.has("shipment_address")){
						delivery.setText(e.getString("shipment_address"));
					}else{
						//delivery.setText("Not Delivered");
					}
					if(e.has("session_date")){
						String dat = e.getString("session_date").substring(0,10);
						String time = e.getString("session_date").substring(11).trim();
						//Log.i("time",time);
						date.setText(f.createDate(dat,time));
						
					}
					
					if(e.has("seats")){
					   seats = "{\"seats\":"+e.getString("seats")+"}";
			           //get the seats for the order
			           JSONObject seatArray = new JSONObject(seats);
			           JSONArray  seat = seatArray.getJSONArray("seats");
			      
			           for(int b=0;b<seat.length();b++){						
							JSONObject ee = seat.getJSONObject(b);
							Log.i("seat name",ee.getString("zone_name"));
							details.add(ee.getString("zone_name")+" - Row:"+ee.getString("row")+" Seat "+ee.getString("number"));
							details.add("Booking Fee");
							price.add(ee.getString("face_value"));
							price.add(ee.getString("booking_fee"));
			           }					
					
					
					}
					
					if(e.has("shipment")){
					    shipping = "{\"shipping\":["+e.getString("shipment")+"]}";
					    //get the shipping info
			            JSONObject shipArray = new JSONObject(shipping);
			            JSONArray  ship = shipArray.getJSONArray("shipping");
			      
			            for(int c=0;c<ship.length();c++){							
							JSONObject s = ship.getJSONObject(c);
							details.add(s.getString("name"));
							price.add(s.getString("fee"));
			            }
			           
			            details.add("Total");
			            price.add(e.getString("value"));					
					}
					

				    

					
					

		        	   if(ssw!="200"){
			        	  // JSONObject k = earthquakes.getJSONObject(0);
			        	   Log.e("fuck fuck","fuck");
			        	   //status.setText("Order can't be retrieved");
			        	  // if(e.has("error") ){
			        		  // Log.e("empty",k.getString("error"));
			        		   //showAlertDialog(k.getString("error"));
			        		  // 
			        	   showSettingsAlert();
			        		   //onBackPressed();
			        	  // }
			        	   
			           }			           
				
	           }
	           
	           ConfirmAdapter	adapter = new ConfirmAdapter(HistoryDetails.this, R.layout.confirm_list_item,
	    				details, price,first, folksFont);
	                    l.setAdapter(adapter);
	        
	           }//end if statement
	           
	           
	    }
	    
		public void showSettingsAlert(){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
	   	 
	        // Setting Dialog Title
	        alertDialog.setTitle("Order");
	 
	        // Setting Dialog Message
	        alertDialog.setMessage("Order can not be retrieved");
	 
	        // On pressing Settings button
	        final Context context = this;
	        alertDialog.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog,int which) {
	            	onBackPressed();
	            }
	        });
	 
	        alertDialog.show();
		}		    
	    
	    public void showAlertDialog(String name){
	    	
		   	 myDialog = new Dialog(this);
		   	 myDialog.getWindow().setFlags( 
		   				WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
		   				WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
		   		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		   		myDialog.setContentView(R.layout.alert_dialog);
		   		
		   		TextView tv = (TextView)myDialog.findViewById(R.id.txtName);
		   		tv.setText(name);
		   		Button button = (Button)myDialog.findViewById(R.id.btnOk);
		   		button.setOnClickListener(new OnClickListener() {
		   		public void onClick(View v) {
		   		    // TODO Auto-generated method stub
		   			myDialog.dismiss();
		   		   
		        }});
		   		
		   		
		   		myDialog.show();
		}
}
