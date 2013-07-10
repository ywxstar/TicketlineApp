package com.ywx.ticketlinewebapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
 
  
public class VenueDetail {//extends MapActivity {
	/*TextView name;
	TextView desc;
	ImageView venImg;
	Drawable drawable;
	Functions f;
	String eventid;
	String venid;
	Dialog myDialog;
	String path;
	String description;
	String venName;
	String latitude;
	String longitude;
	
	TextView venueDetails,address,city,postcode,country,phone,fax,email,web,topic1;
	MapView map;
	ScrollView info,loc;
	Typeface folksFont, arialFont;
	String strAddress,strCity,strPostcode,strCountry,strPhone,strFax,strEmail,strWeb;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venue_details);
        f = new Functions();
        folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
        arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
        eventid = getIntent().getStringExtra("event-id");
        
        info = (ScrollView)findViewById(R.id.scrVenueInfo);
        loc = (ScrollView)findViewById(R.id.scrLocation);
        grabURL("");
       
        venueDetails = (TextView)findViewById(R.id.textView1);
        
        String n = getIntent().getStringExtra("name");
        name = (TextView)findViewById(R.id.txtVenueName);
        topic1 = (TextView)findViewById(R.id.txtVenueName1);
        name.setText(n);
        topic1.setText(n);
        
        name.setTypeface(folksFont);
        topic1.setTypeface(folksFont);
        
        desc =(TextView)findViewById(R.id.txtDesc);
        address = (TextView)findViewById(R.id.txtAddress);
        city = (TextView)findViewById(R.id.txtCity);
        postcode = (TextView)findViewById(R.id.txtPostCode);
        country = (TextView)findViewById(R.id.txtCountry);
        phone = (TextView)findViewById(R.id.txtPhone);
        fax = (TextView)findViewById(R.id.txtFax);
        email = (TextView)findViewById(R.id.txtEmail);
        web = (TextView)findViewById(R.id.txtWeb);
        map = (MapView)findViewById(R.id.map_view);
        
        
        desc.setTypeface(folksFont);
        address.setTypeface(folksFont);
        city.setTypeface(folksFont);
        postcode.setTypeface(folksFont);
        country.setTypeface(folksFont);
        phone.setTypeface(folksFont);
        fax.setTypeface(folksFont);
        email.setTypeface(folksFont);
        web.setTypeface(folksFont);
        venueDetails.setTypeface(arialFont);
        
	}
	
	public void parseJson(String s){
		try{
		       JSONObject jArray = new JSONObject(s);
	           JSONArray  earthquakes = jArray.getJSONArray("categories");
	        	
	           for(int i=0;i<earthquakes.length();i++){						
						
					JSONObject e = earthquakes.getJSONObject(i);
					strAddress = e.getString("address");
					strCity = e.getString("city");
					strPostcode = e.getString("postal_code");
					strCountry = e.getString("country_name");
					strPhone = e.getString("phone");
					strFax = e.getString("fax");
					strEmail = e.getString("email");
					strWeb = e.getString("homepage");
					longitude = e.getString("longitude");
					latitude = e.getString("latitude");
					Log.i("Venue address",e.getString("address"));
					Log.i("Venue city",e.getString("city"));
					Log.i("Venue post",e.getString("postal_code"));
					Log.i("Venue country",e.getString("country_name"));
					Log.i("Venue lat",e.getString("latitude"));
					Log.i("Venue lon",e.getString("longitude"));
				
					Log.i("Venue Description",e.getString("description"));
					description = e.getString("description");
				
	           }
		}catch(JSONException e)        {
       	 Log.e("log_tag", "Error parsing data "+e.toString());
        }
	}
	
	public void parseEvent(String r){
	     try{
	        	
	    	 	JSONObject jArray = new JSONObject(r);
	    	 	JSONArray  earthquakes = jArray.getJSONArray("categories");
	        	
		        for(int i=0;i<earthquakes.length();i++){						
		        	JSONObject e = earthquakes.getJSONObject(i);
					  path = e.getString("seating_plan_path");
					venName = e.getString("venue_space_name");
  					 
					 Log.i("url",path);
					
					 String ar ="{\"event\":["+e.getString("VenueSpace")+"]}";
					 JSONObject jArray1 = new JSONObject(ar);
			    	 	JSONArray  jar = jArray1.getJSONArray("event");
			    	 	
			    	 	for(int a=0;i<jar.length();a++){						
				        	JSONObject ee = jar.getJSONObject(a);
							 venid = ee.getString("venue_id");
							
							 Log.i("venue id",venid);
							 parseJson(f.getSingleVenue(venid));
			    	 	}
					 
				}		
	        }catch(JSONException e)        {
	        	 Log.e("log_tag", "Error parsing data "+e.toString());
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
	        	
	        	parseEvent(f.getEventDetails(eventid));
	            
	            venImg = (ImageView)findViewById(R.id.imgVenue);
	            
	            drawable = LoadImageFromWeb("http://api.ticketline.co.uk"+path);
	             
			return null;
	            
	            
	        }
	        
	        

			protected void onPostExecute(Void unused) {
				if(path.length()==0 || path.equalsIgnoreCase("null")){
					venImg.setVisibility(View.GONE);
				}else{
				venImg.setImageDrawable(drawable);
				}
				name.setText(venName);
				topic1.setText(venName);
				desc.setText(Html.fromHtml(description));
				if(desc.getText().toString().equalsIgnoreCase("null")){
					desc.setVisibility(View.GONE);
				}
				
				  
			        address.setText(strAddress);
			        city.setText(strCity);
			        postcode.setText(strPostcode);
			        country.setText(strCountry);
			       
			        phone.setText(strPhone);
			        if(phone.getText().toString().equalsIgnoreCase("null")){
			        	 phone.setText("No phone no available");
			        	
			        }
			        fax.setText(strFax);
			        if(fax.getText().toString().equalsIgnoreCase("null")){
			        	 fax.setText("No fax no available");
			        }
			        email.setText(strEmail);
			        if(email.getText().toString().equalsIgnoreCase("null")){
			        	 email.setText("No email available");
			        }
			        web.setText(strWeb);
			        if(web.getText().toString().equalsIgnoreCase("null")){
			        	 web.setText("No website available");
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
		 
		 public void location(View button){
			 info.setVisibility(View.GONE);
			 loc.setVisibility(View.VISIBLE);
			 createMap();
		 }
		 
		 public void info(View button){
			loc.setVisibility(View.GONE);
			 info.setVisibility(View.VISIBLE);
		 }
		 		 
		@Override
		protected boolean isRouteDisplayed() {
			// TODO Auto-generated method stub
			return false;
		}
		
		public void createMap(){
			 MapController mapController = map.getController();
			 Double lat;
			 Double lon;
			 // Added Geocoder to work with UK Locale only. DAZ: 4/02/13
			 Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.UK);
			 
			 try {
				List<Address> addresses = geoCoder.getFromLocationName(strPostcode, 5);
				List<Overlay> mapOverlays = map.getOverlays();
			    Drawable drawable = getResources().getDrawable(R.drawable.pin);
		    	CustomItemizedOverlay itemizedOverlay = new CustomItemizedOverlay(drawable,VenueDetail.this);
					
	            lat = (addresses.get(0).getLatitude()*1E6);
	     		lon = (addresses.get(0).getLongitude()*1E6);
						 
				GeoPoint point = new GeoPoint(lat.intValue(),lon.intValue());
				OverlayItem overlayitem = new OverlayItem(point,"" , latitude+"\nLatitude : "+latitude
									+"\nLongitude : "+longitude);
				
				itemizedOverlay.addOverlay(overlayitem);
				mapOverlays.add(itemizedOverlay);
				mapController.animateTo(point);
				map.invalidate();
				mapController.setZoom(14);
			 	 
			 	 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
		}*/
}
