package com.ywx.ticketlinewebapp;

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
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class UserPayment extends Activity {
	String Name;
	Functions f;
	String response;
	TableLayout tbl;
	TextView txtError;
	TextView type,name,no,expire;
	TextView noCards;
	RelativeLayout rel;
	Dialog myDialog;
	Button yourDetails;
	Button payment;
	Button billing;
	Button history;
	Typeface arialFont;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_payment);
        Name = getIntent().getStringExtra("username");
        txtError = (TextView)findViewById(R.id.txtError);
        tbl = (TableLayout)findViewById(R.id.tblInfo);
        type = (TextView)findViewById(R.id.txtType);
        name = (TextView)findViewById(R.id.txtDisplayName);
        no = (TextView)findViewById(R.id.txtCardNo);
        expire = (TextView)findViewById(R.id.txtExpiry);
        rel = (RelativeLayout)findViewById(R.id.relLayout);
        
        yourDetails = (Button)findViewById(R.id.Button01);
    	payment = (Button)findViewById(R.id.Button02);
    	billing = (Button)findViewById(R.id.Button03);
    	history = (Button)findViewById(R.id.btnHistory);
    	 f = new Functions();        
        
        /*  */ 
         
           response = f.getUserPaymentInfo();
       
     try {
		parseResponse(response);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
        
      //  grabURL("");
     
    
	}
	
	
	
	 public void grabURL(String url) {
	     new GrabURL().execute(url);
		 Log.i("empty","grabURL"); 
	 }
	    
	    private class GrabURL extends AsyncTask<String, Void, Void> {
	     
	       
	        
	        protected void onPreExecute() {

	        	 showLoadDialog("Loading...");
	        	 Log.i("empty","onPreExecute"); 
	        }

	        protected Void doInBackground(String... urls) {
	        	
	            response = f.getOrderHistoryDetails(getIntent().getStringExtra("order-id"));
	        	 f = new Functions();
	        	response = f.getUserPaymentInfo();
	            
	        	Log.i("empty","doInBackground"); 
				return null;    
	        }
	        
	        

			protected void onPostExecute(Void unused) {
				try {
					parseResponse(response);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				Log.i("empty","onPostExecute"); 
				//myDialog.dismiss();
				
	        }
	        
	    }	
	
	
	
	
	public void parseResponse(String s) throws JSONException{
		JSONObject jArray = new JSONObject(s);
        JSONArray  earthquakes = jArray.getJSONArray("categories");
        
        if(earthquakes.toString().equalsIgnoreCase("[]")){

     	   Log.i("empty",earthquakes.toString()); 
     	   tbl.setVisibility(View.INVISIBLE);
     	   txtError.setVisibility(View.VISIBLE);
     	   txtError.setGravity(Gravity.CENTER);
     	   txtError.setText("No Saved Cards");

        }else{
	        for(int i=0;i<earthquakes.length();i++){
				JSONObject e = earthquakes.getJSONObject(0);
				
				Log.i("user label",e.getString("user_label"));
				Log.i("type",e.getString("type"));
				Log.i("last",e.getString("last_4_digits"));
				Log.i("expires",e.getString("expires_end"));
				
				type.setText(e.getString("type"));
				no.setText("xxxx xxxx xxxx "+e.getString("last_4_digits"));
				name.setText(e.getString("user_label"));
				expire.setText(e.getString("expires_end"));
	        }
	        
        }
	        
	}
	
	public void Profile(View button){
		Intent PR = new Intent(getParent(),UserProfile.class);
		PR.putExtra("username",Name );
 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
 		parentActivity.startChildActivity("UserProfileActivity", PR);
	}
	public void Messages(View button){
		Intent MSG = new Intent(getParent(),UserMessages.class);
		MSG.putExtra("username",Name );
 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
 		parentActivity.startChildActivity("UserMessagesActivity", MSG);
	}
	
	public void Payment(View button){
		Intent PMT = new Intent(getParent(),UserPayment.class);
		PMT.putExtra("username",Name );
 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
 		parentActivity.startChildActivity("UserMessagesActivity", PMT);
	}
	
	public void History(View button){
		Intent EXT = new Intent(getParent(),UserExtras.class);
		EXT.putExtra("username",Name );
 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
 		parentActivity.startChildActivity("UserMessagesActivity", EXT);
 		
 		Log.e("dfafaf","go to  History"); 
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
