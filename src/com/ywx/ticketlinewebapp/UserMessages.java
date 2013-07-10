package com.ywx.ticketlinewebapp;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class UserMessages extends Activity {
	String Name;
	TextView txtTitle,txtFname,txtLname,txtAdd1,txtAdd2,txtCity,txtPost,txtCountry,txtPhone;
	Functions f;
	Button yourDetails;
	Button payment;
	Button billing;
	Button history;
	Typeface arialFont;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_messages);
        f = new Functions();
        txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtFname = (TextView)findViewById(R.id.txtFname);
        txtLname = (TextView)findViewById(R.id.txtLname);
        txtAdd1 = (TextView)findViewById(R.id.txtAdd1);
        txtAdd2 = (TextView)findViewById(R.id.txtAdd2);
        txtCity = (TextView)findViewById(R.id.txtCity);
        txtPost = (TextView)findViewById(R.id.txtPost);
        txtCountry = (TextView)findViewById(R.id.txtCountry);
        txtPhone = (TextView)findViewById(R.id.txtPhone);
        yourDetails = (Button)findViewById(R.id.Button01);
    	payment = (Button)findViewById(R.id.Button02);
    	billing = (Button)findViewById(R.id.Button03);
    	history = (Button)findViewById(R.id.btnHistory);
    	
    	arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
        yourDetails.setTypeface(arialFont);
        payment.setTypeface(arialFont);
        billing.setTypeface(arialFont);
        history.setTypeface(arialFont);
        
        String  j = f.getUserInfo();
        try {
      		parseData(j);
      	} catch (JSONException e) {
      		// TODO Auto-generated catch block
      		e.printStackTrace();
      	}
      	
        Name = getIntent().getStringExtra("username");
        ListView list = (ListView) findViewById(R.id.lstMessages);
		 
		 ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		 HashMap<String, String> map = new HashMap<String, String>();
		 map.put("Name", "Great One");
		 map.put("Date", "11.03.2011");
		 map.put("Msg","Hello there");
		mylist.add(map);
		
		 map = new HashMap<String, String>();
		 map.put("Name", "Mahesh Weerapurage");
		 map.put("Date", "12.03.2011");
		 map.put("Msg","Hello there, How are you doing");
		mylist.add(map);
		
		map = new HashMap<String, String>();
		 map.put("Name", "Dinesh Weerapurage");
		 map.put("Date", "15.03.2011");
		 map.put("Msg","Hello there, How are you doing? Is everything ok?");
		mylist.add(map);
		
		map = new HashMap<String, String>();
		map.put("Name", "Dinesh Weerapurage");
		 map.put("Date", "15.03.2011");
		 map.put("Msg","Hello there, How are you doing? Is everything ok?");
		mylist.add(map);
		 // ...
		 SimpleAdapter mSchedule = new SimpleAdapter(this, mylist, R.layout.cus_user_message_row,
		             new String[] {"Name", "Date","Msg"}, new int[] {R.id.txtName, R.id.txtDate,R.id.txtMesage});
		 list.setAdapter(mSchedule);
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
 		
 		Log.e("dfafaf","History"); 
	}
	
	public void parseData(String json) throws JSONException{
		
		 JSONObject jArray = new JSONObject(json);
        JSONArray  earthquakes = jArray.getJSONArray("categories");
     	
	       // for(int i=0;i<earthquakes.length();i++){						
				//HashMap<String, String> map = new HashMap<String, String>();	
				JSONObject e = earthquakes.getJSONObject(0);
			
	        	txtTitle.setText(e.getString("title"));
	            txtFname.setText(e.getString("first_name"));
	            txtLname.setText(e.getString("last_name"));
	            txtAdd1.setText(e.getString("address1"));
	            txtAdd2.setText(e.getString("address2"));
	            txtCity.setText(e.getString("city"));
	           txtPost.setText(e.getString("postal_code"));
	            txtCountry.setText(e.getString("country"));
	            txtPhone.setText(e.getString("phone"));
	           // refNo.setText(e.getString("ticketware_customer_id"));
		 		
	}
}
