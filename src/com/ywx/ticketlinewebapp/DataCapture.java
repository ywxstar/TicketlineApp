package com.ywx.ticketlinewebapp;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class DataCapture extends Activity {
	
	Functions f;
	TextView info;
	TextView arName;
	TextView venName;
	TextView date;
	TextView txtTimer;
	TextView timeRemainingTxtView;
	CheckBox terms;
	ImageView seat;
	Dialog myDialog;
	String eventid;
	String total;
	String imageURL;
	String response;
	String important;
	Drawable drawable;
	Typeface folksFont;
	Timer timer;
	int delay1;
	int period1 ;
	int folksFonts;
	int minutes;
	private Handler mHandler1 = new Handler();
	ArrayList<String>stepList = new ArrayList<String>();
	ArrayList<String>infoArray = new ArrayList<String>();
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_capture);
        folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
        f = new Functions();
      
        arName = (TextView)findViewById(R.id.txtName);
        venName = (TextView)findViewById(R.id.txtVenue);
        date = (TextView)findViewById(R.id.txtDate);
        seat = (ImageView)findViewById(R.id.imgSeat);
        info = (TextView)findViewById(R.id.txtInfo);
        terms = (CheckBox)findViewById(R.id.chkAccept);
        txtTimer = (TextView)findViewById(R.id.txtTimer);
        timeRemainingTxtView = (TextView)findViewById(R.id.TextView04);
        
        arName.setTypeface(folksFont);
        venName.setTypeface(folksFont);
        date.setTypeface(folksFont);
        timeRemainingTxtView.setTypeface(folksFont);
        
        eventid = getIntent().getStringExtra("event-id");
        total = getIntent().getStringExtra("total");
        stepList = getIntent().getStringArrayListExtra("steps");
        arName.setText(getIntent().getStringExtra("ar_name"));
        venName.setText(getIntent().getStringExtra("ven_name"));
        date.setText(getIntent().getStringExtra("event_date"));
        infoArray = getIntent().getStringArrayListExtra("info");
        important = infoArray.get(0);
        imageURL = infoArray.get(1);
        
        minutes = Integer.parseInt(getIntent().getStringExtra("minutes"));
        folksFonts = Integer.parseInt(getIntent().getStringExtra("folksFonts"));
        showTimer();
        TextView tv = (TextView)findViewById(R.id.txtTotalDel);
		tv.setText("Total So Far £"+total);
		tv.setTypeface(folksFont);
		grabURL("");
	}
	
	private Drawable LoadImageFromWeb(String url) {
	  
		try {
			 InputStream is = (InputStream) new URL(url).getContent();
			 Drawable d = Drawable.createFromStream(is, "src name");
			 return d;
			} catch (Exception e) {
			 System.out.println("Exc="+e);
			 return null;
			}
	}
	 
	public void grabURL(String url) {
	       new GrabURL().execute(url);
	}
	    
	    private class GrabURL extends AsyncTask<String, Void, Void> {
	     
	        
	        protected void onPreExecute() {
	         //   Dialog.setMessage("Loading...");
	         //   Dialog.show();
	        	 showLoadDialog("Loading...");
	        }

	        protected Void doInBackground(String... urls) {
	        	drawable = LoadImageFromWeb(getIntent().getStringExtra("url"));
	        response = f.getPaymentPlan(eventid);
	        	
	     
			return null;
	            
	            
	        }
	        
	        

			protected void onPostExecute(Void unused) {
	        	//arImage.setImageDrawable(drawable);
				myDialog.dismiss();
				  seat.setImageDrawable(drawable);
	        	parseData(response);
	        
	        	seat.setImageDrawable(drawable);
	        	seat.setOnClickListener(new OnClickListener() {
	 
				    public void onClick(View v) {
				    	Intent AR = new Intent(getApplicationContext(), VenueDetail.class);
				  		// AR.putExtra("venue-id",venueid );
				  		AR.putExtra("event-id", getIntent().getStringExtra("event-id"));
				           startActivity(AR);
				    }
				});
	        }
	        
	    }
	    
	    public void parseData(String s){

	    	info.setText(Html.fromHtml(s));
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
	    
	    public void back(View button){
	    	finish();
	    }
	    
	    public void next(View button){
	    	if(terms.isChecked()){
	    	Intent AR = new Intent(getApplicationContext(),Payment.class);
			 AR.putExtra("event-id", eventid);
			 AR.putExtra("total",String.valueOf(total));
			
		        AR.putExtra("ar_name", arName.getText()); 
		        AR.putExtra("ven_name", venName.getText());
		        AR.putExtra("event_date", date.getText());
		        AR.putExtra("url",imageURL);
		        AR.putStringArrayListExtra("info", infoArray);
		        AR.putStringArrayListExtra("steps", stepList);
		        AR.putExtra("minutes",String.valueOf(minutes));
		        AR.putExtra("folksFonts",String.valueOf(folksFonts));
	        startActivity(AR);
	    	}else{
	    		showAlertDialog("Please Accept The Term & Conditions");
	    	}
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
		    				
		    				//Log.i("Timer",String.valueOf(minutes)+":0"+String.valueOf(folksFonts));
		    				txtTimer.setText(String.valueOf(minutes)+":0"+String.valueOf(folksFonts));
		    			}else{
		    				txtTimer.setText(String.valueOf(minutes)+":"+String.valueOf(folksFonts));
		    				//Log.i("Timer",String.valueOf(minutes)+":"+String.valueOf(folksFonts));
		    			}//end if folksFonts < 10
		    		
		    		//timeView.setText(String.valueOf(folksFonts));
		    	//	timeView.setTextColor(Color.rgb(77, 77, 77));
		    			
	    		   }else if(folksFonts==0){
	    			  if(minutes>0){
	    			   minutes-=1;
	    			   folksFonts = 60;
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
	    			//	myDialog.dismiss();
	    				//showImportantInfo("Important Event Details",important);
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
	    			tv1.setText(Html.fromHtml(name));
	    			
	    		
	    			
	    			
	    			myDialog.show();
	    	}
}
