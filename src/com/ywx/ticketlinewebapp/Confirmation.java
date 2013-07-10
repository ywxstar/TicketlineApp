package com.ywx.ticketlinewebapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Confirmation extends Activity {

	ArrayList<String>stepList = new ArrayList<String>();
	ArrayList<String>details = new ArrayList<String>();
	ArrayList<String>price = new ArrayList<String>();
	ArrayList<String>info = new ArrayList<String>();
	Drawable drawable;
	Dialog myDialog;
	Resources resource;
	Typeface first;
	Typeface folksFont;
	String important;
	String res;
	String response;
	String eventid;
	String total;
	String imageURL;
	Functions f;
	ImageView seat;
	TextView arName;
	TextView venName;
	TextView date;
	TextView txtDelivery;
	TextView txtPayment;
	TextView txtNote;
	ConfirmAdapter adapter;
	ListView l;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);
        f = new Functions();
        
        resource = this.getResources();
     	first = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
     	folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
        arName = (TextView)findViewById(R.id.txtName);
        venName = (TextView)findViewById(R.id.txtVenue);
        date = (TextView)findViewById(R.id.txtDate);
        txtNote = (TextView)findViewById(R.id.textView1);
        seat = (ImageView)findViewById(R.id.imgSeat);
        l = (ListView)findViewById(R.id.lstOrder);
       
        arName.setTypeface(folksFont);
        venName.setTypeface(folksFont);
        date.setTypeface(folksFont);
        txtNote.setTypeface(folksFont);
       
        eventid = getIntent().getStringExtra("event-id");
        total = getIntent().getStringExtra("total");
        stepList = getIntent().getStringArrayListExtra("steps");
        arName.setText(getIntent().getStringExtra("ar_name"));
        venName.setText(getIntent().getStringExtra("ven_name"));
        date.setText(getIntent().getStringExtra("event_date"));
        info = getIntent().getStringArrayListExtra("info");
        important = info.get(0);
        imageURL = info.get(1);
       
        txtDelivery = (TextView)findViewById(R.id.txtDelivery);
        txtPayment = (TextView)findViewById(R.id.txtPayment);
       
        grabURL("");
	}
	
	public void moreInfo(View button) {

		myDialog = new Dialog(this);
		myDialog.getWindow().setFlags( 
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		myDialog.setContentView(R.layout.more_info_dialog);
			
		TextView tv = (TextView)myDialog.findViewById(R.id.txtName);
		tv.setTypeface(first);
			
		Button button1 = (Button)myDialog.findViewById(R.id.btnInfo);
		button1.setTypeface(first);
		button1.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			Intent AR = new Intent(getApplicationContext(), VenueDetail.class);
		  	AR.putExtra("event-id", getIntent().getStringExtra("event-id"));
		    startActivity(AR);
		}});
			
		Button button2 = (Button)myDialog.findViewById(R.id.btnImportant);
		button2.setTypeface(first);
		button2.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			myDialog.dismiss();
			showImportantInfo("Important Event Details",important);
		}});
			
		Button button3 = (Button)myDialog.findViewById(R.id.btnSummary);
		button3.setTypeface(first);
		button3.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
	       		myDialog.dismiss();
			showImportantInfo("Important Event Details",important);
		}});

		Button button6 = (Button)myDialog.findViewById(R.id.btnCancel);
		button6.setTypeface(first);
		button6.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		    // TODO Auto-generated method stub
			myDialog.dismiss();
		}});
			
		myDialog.show();
	}	
		
	public void showImportantInfo(String topic,String name) {
		
		myDialog = new Dialog(this);
		myDialog.getWindow().setFlags( 
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		myDialog.setContentView(R.layout.moreinfo_dialog);
			
		TextView tv = (TextView)myDialog.findViewById(R.id.txtTopic);
		tv.setText(topic);
		tv.setTypeface(first);
			
		WebView tv1 = (WebView)myDialog.findViewById(R.id.txtHtml);
		tv1.loadDataWithBaseURL(null, name,"text/html", "utf-8", null);
				
		myDialog.show();
	}
	
	public void back(View button) {
		finish();
	}	
		
	public void done(View button) {
	 Intent AR = new Intent(getApplicationContext(),  TicketlineActivity.class);
            AR.putExtra("TAB", "0");
		          startActivity(AR);
		           finish();
	}
	
	public void grabURL(String url) {
        new GrabURL().execute(url);
    }
    
    private class GrabURL extends AsyncTask<String, Void, Void> {
     
        protected void onPreExecute() {
        	 showLoadDialog("Loading...");
        }

        protected Void doInBackground(String... urls) {
        	
				 res = f.doOreder(eventid);	 
				 return null;  
        }

		protected void onPostExecute(Void unused) {
			myDialog.dismiss();
        	try {
				populate(res);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    public void populate(String s) throws JSONException {
		 
    	JSONObject jArray = new JSONObject(s);
        JSONArray  earthquakes = jArray.getJSONArray("categories");
       	
	        for(int i=0;i<earthquakes.length();i++) {						
				JSONObject e = earthquakes.getJSONObject(i);
				
				Log.i("transaction id",e.getString("transaction_id"));

				String sum = "{\"summary\":["+e.getString("summary")+"]}";
	        	JSONObject sumArray = new JSONObject(sum);
	            JSONArray  sAr = sumArray.getJSONArray("summary");
	            JSONObject ee = sAr.getJSONObject(0);
	            
	        	Log.i("total value",ee.getString("total_value"));
	        	Log.i("Delivery Method",ee.getString("delivery-method"));
	        	txtDelivery.setText(ee.getString("delivery-method"));
	        	
	        	String tic = "{\"tickets\":"+ee.getString("tickets")+"}";
	        	JSONObject ticArray = new JSONObject(tic);
	            JSONArray  tAr = ticArray.getJSONArray("tickets");
	            
	            for(int a=0;a<tAr.length();a++) {
	            	JSONObject eee = tAr.getJSONObject(a);
	            	Log.i("total details",eee.getString("details"));
	            	details.add(eee.getString("details"));
	            	Log.i("face value",eee.getString("face_value"));
	            	Log.i("booking fee",eee.getString("booking_fee"));
	            	double pri = Double.parseDouble(eee.getString("face_value")) + Double.parseDouble(eee.getString("booking_fee"));
	            	price.add(String.valueOf(pri));
	            }
	            
	            details.add("Total");
	            price.add(ee.getString("total_value"));
	            String paym = "{\"payment\":["+ee.getString("payment-method")+"]}";
	        	JSONObject payArray = new JSONObject(paym);
	            JSONArray  pAr = payArray.getJSONArray("payment");
	            JSONObject p = pAr.getJSONObject(0);
	            Log.i("User Label",p.getString("user_label"));
	            Log.i("Type",p.getString("type"));
	            Log.i("Last 4",p.getString("last_4_digits"));
	            Log.i("Expires",p.getString("expires_end"));
	            txtPayment.setText(p.getString("type")+" xxxx xxxx xxxx "+p.getString("last_4_digits"));
			}	
	    
        adapter = new ConfirmAdapter(Confirmation.this, R.layout.confirm_list_item,
    	    	 details, price, first, folksFont);
        l.setAdapter(adapter);
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
