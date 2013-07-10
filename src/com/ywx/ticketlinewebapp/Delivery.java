package com.ywx.ticketlinewebapp;

import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Delivery extends Activity{
	
Functions f;
String eventid;
ArrayList<String>deliveryFee = new ArrayList<String>();
ArrayList<String>deliveryMethod = new ArrayList<String>();
ArrayList<String>deliveryId = new ArrayList<String>();
ArrayList<String>deliveryDesc = new ArrayList<String>();
ArrayList<String>imgArray = new ArrayList<String>();
ArrayList<String>stepList = new ArrayList<String>();
ArrayList<String>details = new ArrayList<String>();
ArrayList<String>price = new ArrayList<String>();
String selectedDelId;
Dialog myDialog;
ListView l;
Resources resource;
Typeface first,folksFont;
DecimalFormat df = new DecimalFormat("#########0.00"); 
String total,imageURL;
deliveryAdapter	adapter;
TextView arName,venName,date;
ImageView seat;
Drawable drawable;
String response;
Timer timer;
int delay1;
int period1 ;
int folksFonts;
int minutes;
TextView txtTimer;
ArrayList<String>info = new ArrayList<String>();
String important;

private Handler mHandler1 = new Handler();
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery);
        f = new Functions();
        first = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
        folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
        selectedDelId="";
        
        arName = (TextView)findViewById(R.id.txtName);
        venName = (TextView)findViewById(R.id.txtVenue);
        date = (TextView)findViewById(R.id.txtDate);
        seat = (ImageView)findViewById(R.id.imgSeat);
        txtTimer = (TextView)findViewById(R.id.txtTimer);
        
        arName.setTypeface(folksFont);
        venName.setTypeface(folksFont);
        date.setTypeface(folksFont);
        
        eventid = getIntent().getStringExtra("event-id");
        total = getIntent().getStringExtra("total");
        stepList = getIntent().getStringArrayListExtra("steps");
        arName.setText(getIntent().getStringExtra("ar_name"));
        venName.setText(getIntent().getStringExtra("ven_name"));
        date.setText(getIntent().getStringExtra("event_date"));
        
        info = getIntent().getStringArrayListExtra("info");
        important = info.get(0);
        imageURL = info.get(1);
        
        Log.i("event id",eventid);
        Log.i("step list",stepList.toString());
      
	
		TextView tv = (TextView)findViewById(R.id.txtTotalDel);
		tv.setText("Total So Far \u00a3"+df.format(Double.parseDouble(total)));
		tv.setTypeface(folksFont);
		minutes = 7;
    	folksFonts = 0;
    	showTimer();
		
    	grabURL("");
		//clear arrays
		if(deliveryMethod.size()>0){
		deliveryMethod.clear();
    	deliveryId.clear();
    	deliveryFee.clear();
    	deliveryDesc.clear();
    	imgArray.clear();
    	selectedDelId = "";
		}
		
		try {
			parseJSON(getIntent().getStringExtra("summary"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			 
	}
	
	 private Drawable LoadImageFromWeb(String url)
	   {
	  try
	  {
	   InputStream is = (InputStream) new URL(url).getContent();
	   Drawable d = Drawable.createFromStream(is, "src name");
	   return d;
	  }catch (Exception e) {
	   System.out.println("Exc="+e);
	   return null;
	  }
	 }
	 
	 public void grabURL(String url) {
	        new GrabURL().execute(url);
	    }
	    
	    private class GrabURL extends AsyncTask<String, Void, Void> {
	     
	        
	        protected void onPreExecute() {
	        	 showLoadDialog("Loading...");
	        }

	        protected Void doInBackground(String... urls) {
	        	drawable = LoadImageFromWeb(getIntent().getStringExtra("url"));
	        response = f.getDeliveryMethods(getIntent().getStringExtra("event-id"));

			return null;

	        }
	        
			protected void onPostExecute(Void unused) {
				myDialog.dismiss();
				  seat.setImageDrawable(drawable);
	        	parseData(response);
	        	
	        	seat.setImageDrawable(drawable);
	        	seat.setOnClickListener(new OnClickListener() {
		 
				    public void onClick(View v) {
				    	Intent AR = new Intent(getApplicationContext(), VenueDetail.class);
				  		AR.putExtra("event-id", getIntent().getStringExtra("event-id"));
				           startActivity(AR);
				    }
				});
	        }
	        
	    }
	    
	    public void populate(){
	    	
	    }
	    
	    public void parseData(String s){
	    	 JSONObject jArray;
	 		try {
	 			jArray = new JSONObject(s);
	          JSONArray  earthquakes = jArray.getJSONArray("categories");
	       	
	          for(int i=0;i<earthquakes.length();i++){						
	 				JSONObject e = earthquakes.getJSONObject(i);
	 				
	 		     	
	 				Log.i(" name",e.getString("name"));
	 				deliveryMethod.add(e.getString("name"));
	 	        	Log.i("delivery id",e.getString("id"));
	 	        	deliveryId.add(e.getString("id"));
	 	        	Log.i("fee",e.getString("fee"));
	 	        	deliveryFee.add("\u00a3"+e.getString("fee"));
	 	        	Log.i("description",e.getString("description"));
	 	        	deliveryDesc.add(e.getString("description"));
	 	        	imgArray.add("unchecked");
	 			}
	 		} catch (JSONException e1) {
	 			// TODO Auto-generated catch block
	 			e1.printStackTrace();
	 		}
	     
	 	        	final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	 	        	for(int a=0;a<deliveryMethod.size();a++){
	 	        		
	 	        			HashMap<String, String> map = new HashMap<String, String>();
	 			        	map.put("id",  deliveryId.get(a));
	 			        	map.put("name", deliveryMethod.get(a));
	 			        	map.put("desc", deliveryDesc.get(a));
	 			        	map.put("fee",deliveryFee.get(a));
	 			        	
	 			        	mylist.add(map);
	 	        	}
	 	        	
	 	        	
	           l = (ListView)findViewById(R.id.lstDelivery); 
	          
	      
	           	resource = this.getResources();
	           	 
	         	adapter = new deliveryAdapter(Delivery.this, R.layout.delivery_list_item,
	     				deliveryMethod, deliveryId,deliveryDesc,deliveryFee,imgArray,resource,first,folksFont);
	          l.setAdapter(adapter);
	 			
	 			 l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
 
	 					public void onItemClick(AdapterView<?> arg0, View arg1,
	 							int arg2, long arg3) {
	 						// TODO Auto-generated method stub
	 						final TextView delId = (TextView)arg1.findViewById(R.id.topText);
	 		
	 		  				selectedDelId = delId.getText().toString();
	 		  				for(int i=0;i<imgArray.size();i++){
	 		  				imgArray.set(i, "unchecked");
	 		  				}
	 		  				
	 		  				imgArray.set(arg2,"check");
	 		  				
	 		  				
	 		  	        	adapter = new deliveryAdapter(Delivery.this, R.layout.delivery_list_item,
	 		  	    				deliveryMethod, deliveryId,deliveryDesc,deliveryFee,imgArray,resource,first,folksFont);
	 		  	         l.setAdapter(adapter);
	 		
	 		  				
	 					}

	 		          });
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
	    
	    public void next(View button){
	    	if(selectedDelId.length()>0||!selectedDelId.equalsIgnoreCase("")){
	    		//set the delivery method
	    		String sum = f.selectDeliveyMethod(getIntent().getStringExtra("event-id"),selectedDelId);
	    		
	    		if(stepList.get(2).equalsIgnoreCase("Payment Plan Agreement")){
	    			//show the data capture screen
	    			Intent AR = new Intent(getApplicationContext(),DataCapture.class);
	    			 AR.putExtra("event-id", eventid);
	    			 AR.putExtra("total",String.valueOf(total));
	    			
	    		        AR.putExtra("ar_name", arName.getText()); 
	    		        AR.putExtra("ven_name", venName.getText());
	    		        AR.putExtra("event_date", date.getText());
	    		        AR.putExtra("url",imageURL);
	    		        AR.putStringArrayListExtra("info", info);
	    		        AR.putStringArrayListExtra("steps", stepList);
	    		        AR.putExtra("minutes",String.valueOf(minutes));
	    		        AR.putExtra("folksFonts",String.valueOf(folksFonts));
	    		        AR.putExtra("summary", sum);
	    	        startActivity(AR);
	    		}
	    		else if(stepList.get(2).equalsIgnoreCase("Payment Method")){
	    			Intent AR = new Intent(getApplicationContext(),Payment.class);
	    			 AR.putExtra("event-id", eventid);
	    			 AR.putExtra("total",String.valueOf(total));
	    			
	    		        AR.putExtra("ar_name", arName.getText()); 
	    		        AR.putExtra("ven_name", venName.getText());
	    		        AR.putExtra("event_date", date.getText());
	    		        AR.putExtra("url",imageURL);
	    		        AR.putStringArrayListExtra("info", info);
	    		        AR.putStringArrayListExtra("steps", stepList);
	    		        AR.putExtra("minutes",String.valueOf(minutes));
	    		        AR.putExtra("folksFonts",String.valueOf(folksFonts));
	    		        AR.putExtra("summary", sum);
	    	        startActivity(AR);
	    		}
	    }else{
	    	LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.toast_layout,
			                               (ViewGroup) findViewById(R.id.toast_layout_root));

			TextView text = (TextView) layout.findViewById(R.id.text);
			text.setText("Please select a delivery method");
			text.setTypeface(first);
			text.setTextColor(Color.parseColor("#00B2FF"));

			Toast toast = new Toast(getApplicationContext());
			toast.setGravity(Gravity.CENTER, 0, 50);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setView(layout);
			toast.show();
	    }
	    }
	    
		 public void back(View button){
			 finish(); 
		 }
		 
		 public void showTimer(){
	    		timer = new Timer();
	    		delay1 = 1000;
	    		 period1 = 1000; // repeat every 5 sec.
			       timer.scheduleAtFixedRate(new TimerTask() {
			        public void run() {
			        	updateTimeUi();
			        }
			        }, delay1, period1);
	    	}
	    	
	    	public void updateTimeUi(){
	    		mHandler1.postDelayed(mUpdateTimeLabel, 100);
	    		
	    	}
	    	
		    private Runnable mUpdateTimeLabel = new Runnable() {
		    	   public void run() {
		    		   
		    		   if(folksFonts>0){
		    			   folksFonts -=1;
		    			   
		    			   if(folksFonts<10){
			    		
			    				txtTimer.setText(String.valueOf(minutes)+":0"+String.valueOf(folksFonts));
			    			}else{
			    				txtTimer.setText(String.valueOf(minutes)+":"+String.valueOf(folksFonts));
			    			}//end if folksFonts < 10
			    		

			    			
		    		   }else if(folksFonts==0){
		    			  if(minutes>0){
		    			   minutes-=1;
		    			   folksFonts = 60;
		    			  }else{
		    				  showAlertDialog("Your time has expired.Please restart the Order");
			    			   Intent AR = new Intent(getApplicationContext(),  TicketlineActivity.class);
			    				 AR.putExtra("TAB", "0");
			    				           startActivity(AR);
			    				           finish();
			    				         
		    			  }
		    			  
		    		   }
		    		   else {
		    			
		    			  
		    			   timer.cancel();
		    			   
		    			
		    			   
		    		   }
		    	   }

				
		    	   
		    	};
		    	
		    	
		    	public void moreInfo(View button){
		    		
		    		

		    		 myDialog = new Dialog(this);
		    		 myDialog.getWindow().setFlags( 
		    					WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
		    					WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
		    			myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		    			myDialog.setContentView(R.layout.more_info_dialog);
		    			
		    			TextView tv = (TextView)myDialog.findViewById(R.id.txtName);
		    			tv.setTypeface(folksFont);
		    			
		    			Button button1 = (Button)myDialog.findViewById(R.id.btnInfo);
		    			button1.setTypeface(folksFont);
		    			button1.setOnClickListener(new OnClickListener() {
		    			public void onClick(View v) {
		    				Intent AR = new Intent(getApplicationContext(), VenueDetail.class);
		    		  		// AR.putExtra("venue-id",venueid );
		    		  		AR.putExtra("event-id", getIntent().getStringExtra("event-id"));
		    		           startActivity(AR);
		    			}});
		    			
		    			Button button2 = (Button)myDialog.findViewById(R.id.btnImportant);
		    			button2.setTypeface(folksFont);
		    			button2.setOnClickListener(new OnClickListener() {
		    			public void onClick(View v) {
		    				myDialog.dismiss();
		    				showImportantInfo("Important Event Details",important);
		    			}});
		    			
		    			Button button3 = (Button)myDialog.findViewById(R.id.btnSummary);
		    			button3.setTypeface(folksFont);
		    			button3.setOnClickListener(new OnClickListener() {
		    			public void onClick(View v) {
		    				myDialog.dismiss();
		    				//showImportantInfo("Important Event Details",important);
		    				Log.i("Order Summary",getIntent().getStringExtra("summary"));
		    				
		    			        showSummaryDialog();
		    				
		    			}});
		    			
		    		
		    			
		    			Button button6 = (Button)myDialog.findViewById(R.id.btnCancel);
		    			button6.setTypeface(folksFont);
		    			button6.setOnClickListener(new OnClickListener() {
		    			public void onClick(View v) {
		    			    // TODO Auto-generated method stub
		    			   // showEvents(arId,arName);
		    				myDialog.dismiss();
		    			}});
		    			
		    			myDialog.show();
		    		}
public void parseJSON(String r) throws JSONException{
	 JSONObject jArray = new JSONObject(getIntent().getStringExtra("summary"));
     JSONArray  earthquakes = jArray.getJSONArray("categories");
  	
       for(int i=0;i<earthquakes.length();i++){						
			JSONObject e = earthquakes.getJSONObject(i);

       	Log.i("total value",e.getString("total_value"));
       	
       	
       	String tic = "{\"tickets\":"+e.getString("tickets")+"}";
       	JSONObject ticArray = new JSONObject(tic);
           JSONArray  tAr = ticArray.getJSONArray("tickets");
           for(int a=0;a<tAr.length();a++){
           JSONObject eee = tAr.getJSONObject(a);
           Log.i("total details",eee.getString("details"));
           details.add(eee.getString("details"));
           Log.i("face value",eee.getString("face_value"));
           Log.i("booking fee",eee.getString("booking_fee"));
           double pri = Double.parseDouble(eee.getString("face_value"))+Double.parseDouble(eee.getString("booking_fee"));
           price.add(String.valueOf(pri));
           }
           
          
		}	
	
}
		    	public void showImportantInfo(String topic,String name){
		    		
		    		 myDialog = new Dialog(this);
		    		 myDialog.getWindow().setFlags( 
		    					WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
		    					WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
		    			myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		    			myDialog.setContentView(R.layout.moreinfo_dialog);
		    			
		    			TextView tv = (TextView)myDialog.findViewById(R.id.txtTopic);
		    			tv.setText(topic);
		    			tv.setTypeface(folksFont);
		    			
		    			TextView tv1 = (TextView)myDialog.findViewById(R.id.txtHtml);
		    			
		    			if(name.length()>0){
		    			tv1.setText(Html.fromHtml(name));
		    			}else{
		    				tv1.setText("No Details Found");
		    			}
		    		
		    			
		    			
		    			myDialog.show();
		    	}

		        public void showSummaryDialog(){
			    	
		   	   	 myDialog = new Dialog(this);
		   	   	 myDialog.getWindow().setFlags( 
		   	   				WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
		   	   				WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
		   	   		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		   	   		myDialog.setContentView(R.layout.summary_dialog);
		   	   		
		   	   		ListView l = (ListView)myDialog.findViewById(R.id.lstSummary);
		   	   		ConfirmAdapter adapter = new ConfirmAdapter(this, R.layout.confirm_list_item,
	    				details, price,first,folksFont);
		   	   			l.setAdapter(adapter);
		   	   		//tv.setText(name);
		   	   		Button button = (Button)myDialog.findViewById(R.id.btnOk);
		   	   		button.setOnClickListener(new OnClickListener() {
		   	   		public void onClick(View v) {
		   	   		    // TODO Auto-generated method stub
		   	   			myDialog.dismiss();
		   	   		   
		   	   		}});
		   	   		
		   	   		
		   	   	
		   	   		
		   	   		
		   	   		myDialog.show();
		   	   }
}
