package com.ywx.ticketlinewebapp;


import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserProfile extends Activity  {
	
	String Name;
	String Email;
	String LName;
	String Add1;
	String Add2;
	String City;
	String Post;
	String Country;
	String Phone;
	String Pass;
	TextView usernameTxt;
	TextView username;
	TextView refNoTxt;
	TextView refNo;
	TextView dobTxt;
	TextView dob;
	TextView genderTxt;
	TextView gender;
	Button yourDetails;
	Button payment;
	Button billing;
	Button history;
	Button logout;
	Typeface arialFont;
	
	User user;
	Functions f;
	 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
       
        f = new Functions();
       String  j = f.getUserInfo();
     
      arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");  
          
      yourDetails = (Button)findViewById(R.id.Button01);
  	  payment = (Button)findViewById(R.id.Button02);
  	  billing = (Button)findViewById(R.id.Button03);
  	  history = (Button)findViewById(R.id.btnHistory);
   	  usernameTxt = (TextView)findViewById(R.id.textView1);
  	  username = (TextView)findViewById(R.id.txtProfileName);
  	  refNoTxt = (TextView)findViewById(R.id.cusReftxtView);
  	  refNo = (TextView)findViewById(R.id.txtCusRef);
  	  dobTxt = (TextView)findViewById(R.id.TextView04);
  	  dob = (TextView)findViewById(R.id.txtDOB);
  	  genderTxt = (TextView)findViewById(R.id.TextView06);
  	  gender = (TextView)findViewById(R.id.txtGender);
  	  logout = (Button)findViewById(R.id.Button05);
  	  
  	  yourDetails.setTypeface(arialFont);
	  payment.setTypeface(arialFont);
	  billing.setTypeface(arialFont);
	  history.setTypeface(arialFont);
  	  usernameTxt.setTypeface(arialFont);
  	  username.setTypeface(arialFont);
  	  dobTxt.setTypeface(arialFont);
	  dob.setTypeface(arialFont);
	  genderTxt.setTypeface(arialFont);
	  gender.setTypeface(arialFont);
	  refNoTxt.setTypeface(arialFont);
	  refNo.setTypeface(arialFont);
	  logout.setTypeface(arialFont);
 	  
	  
  	  
  
  	  
      try {
  		parseData(j);

  	} catch (JSONException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
      
      
     
      Log.i("User prof class id",j);
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
	}
	
	public void logout(View button){
		File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/user.xml");
	    newxmlfile.delete();
	    
	    Intent PMT = new Intent(getParent(),Account.class);
 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
 		parentActivity.startChildActivity("UserMessagesActivity", PMT);
	    
	}
	
	
	public void parseData(String json) throws JSONException{
		
		 JSONObject jArray = new JSONObject(json);
         JSONArray  earthquakes = jArray.getJSONArray("categories");
      	
				JSONObject e = earthquakes.getJSONObject(0);
			
				username.setText(e.getString("email_address"));
				
				if(!e.getString("date_of_birth").equalsIgnoreCase("null")){
	           	dob.setText(f.createShortDate(e.getString("date_of_birth")));
				}
	            if(dob.getText().toString().equalsIgnoreCase("null")||(dob.getText().toString().equalsIgnoreCase("TextView"))){
	            	dob.setText("Not Available");
	            }
	            
	         
	            	gender.setText(e.getString("gender"));
	            if(gender.getText().toString().equalsIgnoreCase("null") ||(gender.getText().toString().equalsIgnoreCase("TextView"))){
	            	gender.setText("Not Available");
	            }
	            
	            	refNo.setText(e.getString("ticketware_customer_id"));
	            	if(refNo.getText().toString().equalsIgnoreCase("null") ||(refNo.getText().toString().equalsIgnoreCase("TextView"))){
		            	refNo.setText("Not Available");
		            }
	            
	            
		 		
	}
}
