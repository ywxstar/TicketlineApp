package com.ywx.ticketlinewebapp;

//http://stackoverflow.com/questions/5725338/how-to-time-out-gps-signal-acquisition
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
 

import android.support.v4.app.FragmentActivity;

public class LocationSearch extends Activity implements LocationListener {

	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	 
/*
	 private MapView mapView;
	 InputStream is = null;
	 String result = "";
	 JSONObject jArray = null;
	 HttpEntity responseEntity;
	 MapController mapController;
	 Double lat;
	 Double lon;
	 ProgressDialog pd;
     Dialog myDialog ;
     Functions f;
     ArrayList<String> lats = new ArrayList<String>();
     ArrayList<String> lons = new ArrayList<String>();
     ArrayList<String> ids = new ArrayList<String>();
     ArrayList<String> names = new ArrayList<String>();
     ArrayList<String> dates = new ArrayList<String>();
     ArrayList<String> trimmedDates = new ArrayList<String>();
     ArrayList<String> monthsArray = new ArrayList<String>();
     ArrayList<String> eventsArray = new ArrayList<String>();
     ArrayList<String> city = new ArrayList<String>();
     ArrayList<String> artistId = new ArrayList<String>();
     String Event;
     Typeface arialFont;// added
     Typeface folksFont;
     
     
 	// flag for GPS status
 	boolean isGPSEnabled = false;

 	// flag for network status
 	boolean isNetworkEnabled = false;

 	// flag for GPS status
 	boolean canGetLocation = false;     
	Location location; // location
	double latitude;
	double longitude;    
     LocationManager locationManager = null;
     
 	// The minimum distance to change Updates in meters
 	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

 	// The minimum time between updates in milliseconds
 	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute     
     
	public void onCreate(Bundle savedInstanceState) {
        
		super.onCreate(savedInstanceState);
        setContentView(R.layout.location_search);

      
        
        f = new Functions();
        arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
    	folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
    	
    	
         locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String bestProvider = locationManager.getBestProvider(criteria, true);
        
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 500.0f,this);
        
        //mapView = (MapView) findViewById(R.id.map_view);     
        //mapView.setBuiltInZoomControls(true);
    	

       // runDialog(5);
        showLoadDialog("Finding your current location..");
       
        Handler timeoutHandler = new Handler();
        timeoutHandler.postDelayed(new Runnable() {
            public void run() {
                stopSelf();
            }
        }, 30 * 1000); 
        
        getLocation();
	}
	

	public Location getLocation() {
		try {
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
				showSettingsAlert();
			} else {
				this.canGetLocation = true;
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (locationManager != null) {
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
								
						if (location != null) {
							
							latitude = location.getLatitude();
							longitude = location.getLongitude();
							Log.d("Network", latitude+"     "+longitude);
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								
								latitude = location.getLatitude();
								longitude = location.getLongitude();
								Log.d("GPS ", latitude+"      "+longitude);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}	
	
	*//**
	 * Function to show settings alert dialog
	 * On pressing Settings button will lauch Settings Options
	 * *//*
	public void showSettingsAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
   	 
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
 
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
 
        // On pressing Settings button
        final Context context = this;
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            	context.startActivity(intent);
            }
        });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
	}	
	
	void stopSelf(){
		if(myDialog!=null)
		myDialog.dismiss();
		if(locationManager!=null){
			Log.e("stop","stop all");
			locationManager.removeUpdates(this);
			
		}
	}
	
	   public void grabURL(String url) {
	        
		   new GrabURL().execute(url);
		   
	    }
	    
	    private class GrabURL extends AsyncTask<String, Void, Void> {
	        
	    	private final HttpClient Client = new DefaultHttpClient();
	        private String Content;
	        private String Error = null;
	        private ProgressDialog Dialog = new ProgressDialog(getParent());
	        
	        protected void onPreExecute() {

	        	showLoadDialog("Loading events from your geo location("+latitude+","+longitude+")");
	        }

	        protected Void doInBackground(String... urls) {
	        	
	        	doPost();
	        	return null;
	        }
	        
	        protected void onPostExecute(Void unused) {

	        	populate();
	        	myDialog.dismiss();
	        }
	        
	    }
	
	public void doPost(){
		  
		DefaultHttpClient client = new DefaultHttpClient();	        
	        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//event");
	        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
	        nvps.add(new BasicNameValuePair("method", "getByLocation"));
	        nvps.add(new BasicNameValuePair("lat",latitude+""));
	        nvps.add(new BasicNameValuePair("long",longitude+""));
	        nvps.add(new BasicNameValuePair("radius", "5.5"));
	        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
	        
	        try {
	              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
	              HTTP.UTF_8);
	              
	              httppost.setEntity(p_entity);
	              HttpResponse response = client.execute(httppost);
	              Log.v("t", response.getStatusLine().toString());

	              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
	              
	              response.getEntity().writeTo(ostream);
	          
	              Log.e("HTTP CLIENT", ostream.toString());
	              
	              String s="{\"categories\":"+ostream.toString()+"}";
		          Log.i("response",s);
		         
		          JSONObject jArray = new JSONObject(s);
		          JSONArray  earthquakes = jArray.getJSONArray("categories");
		          Log.i("response length",earthquakes.length()+"");
			        for(int i=0;i<earthquakes.length();i++) {
						JSONObject e = earthquakes.getJSONObject(i);
									
								Log.i("event id",e.getString("id"));
								ids.add(e.getString("id"));
								
								Log.i("event name",e.getString("event_name"));
								eventsArray.add(e.getString("event_name"));
								
								Log.i("venue_name",e.getString("venue_space_name"));
								names.add(e.getString("venue_space_name"));
								Log.i("Event Date",e.getString("datetime"));
								dates.add(e.getString("datetime"));
								trimmedDates.add(e.getString("datetime").substring(0,7));
								Log.i("Seating Map Name",e.getString("seating_map_name"));
								
					        	if(e.getString("latitude")==null){
						        	//Log.e("Null found","Nill Value");
						        	} else {
						        		//lats.add(e.getString("latitude"));
							        	//lons.add(e.getString("longitude"));
						        	 }

				      		//desc.setText(Html.fromHtml(ee.getString("description")));
				      }

			        	//Log.i("lat & long",e.getString("latitude") + " "+e.getString("longitude"));			
	        } catch (Exception e)  { 
	              e.printStackTrace();
	              
	        } 
		
	        
	        for( int i=0;i<lats.size();i++) {
	        	
	        Log.i("Lats",lats.get(i));	
	        
	        }		
	}
	
	public void populate() {
		 
		 SeparatedListAdapter adapter = new SeparatedListAdapter(this); //added
		// String[] some_array = getResources().getStringArray(R.array.alpha);
		
		 removeDuplicate(trimmedDates);
		 Collections.sort(trimmedDates);
		 //crate the month name
		
		 	for(int i=0;i<trimmedDates.size();i++) {
		 	//	String s = trimmedDates.get(i).substring(5,7);
		 }

		    for(int i=0;i<trimmedDates.size();i++) {
	        	
		    	final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	        	
		    	for(int a=0;a<dates.size();a++) {
		    		
	        		if(dates.get(a).substring(0,7).equalsIgnoreCase(trimmedDates.get(i))) {}
	        			
	        			HashMap<String, String> map = new HashMap<String, String>();
			        	map.put("id",  ids.get(a));
			        	map.put("name", names.get(a));
			        	map.put("date", f.createDate(dates.get(a).substring(0,10),dates.get(a).substring(11).trim()));
			        	map.put("eventname",eventsArray.get(a));
			        	mylist.add(map);
	        		
	        	}
	        	
	        	//int monVal = Integer.parseInt(trimmedDates.get(i).substring(5,7));

	        		adapter.addSection("Events Close To You", new SimpleAdapter(this, mylist , R.layout.event_list_item2, 
			        		new String[] {"eventname","id","date","name"}, 
			                    new int[] { R.id.list_item_title,R.id.toptext,R.id.txtDate,R.id.txtVenue}));
	        }
        
		    ListView list = (ListView) findViewById(R.id.lstEvents); 
		    list.setFastScrollEnabled(true);
		    list.setAdapter(adapter); 
        
        
		    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
				TextView tv = (TextView)arg1.findViewById(R.id.list_item_title);
				String s= tv.getText().toString();
				Log.i("Selected Name",s);
				
				TextView tv1 = (TextView)arg1.findViewById(R.id.toptext);
				String ss= tv1.getText().toString();
				Log.i("Selected Id",ss);
				
				TextView aidT = (TextView)arg1.findViewById(R.id.txtArtistId);
				String arid= aidT.getText().toString();
				Log.i("Selected venue spave",arid);
				
				TextView day = (TextView)arg1.findViewById(R.id.txtDate);
				String strDay= day.getText().toString();
				Log.i("Selected Id",strDay);
				
				Intent eve = new Intent(getParent(),  Prices.class);
			
				 
				 
				 eve.putExtra("event-id",ss);
					eve.putExtra("ar_name",arid);
					eve.putExtra("ven_name",s);
					eve.putExtra("event_date",strDay);
					eve.putExtra("TAB", "location");
					eve.putExtra("artist_id",arid);
		         startActivity(eve);
		         
		 	
			}

        });
		
	}

	
	public void createMap(){
		List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = getParent().getResources().getDrawable(R.drawable.pin);
		CustomItemizedOverlay itemizedOverlay = new CustomItemizedOverlay(drawable,getParent());
		
		for( int i=0;i<100;i++){
		
		if(lats.get(i).length()>5){
			 lat = (Double.parseDouble(lats.get(i))*1E6);
			 lon = (Double.parseDouble(lons.get(i))*1E6);
			 
			 GeoPoint point = new GeoPoint(lat.intValue(),lon.intValue());
				OverlayItem overlayitem = new OverlayItem(point,"" , lats.get(i)+"\nLatitude : "+lats.get(i)
						+"\nLongitude : "+lons.get(i));
				
				itemizedOverlay.addOverlay(overlayitem);
				mapOverlays.add(itemizedOverlay);
				
			
				
			 mapController = mapView.getController();
				
				mapController.animateTo(point);
			}
			
			//Log.i("lat & long",lats.get(i) + " "+lons.get(i));
			//	Double lat = 53.546663 * 1E6;
				//Double lon = -2.343546 *1E6;
		
			
			
			
			}
			
			
			mapController.setZoom(8);
	}
	
	public void Date(View button){
		Intent DT = new Intent(getParent(),DateSearch.class);
		//DT.putExtra("Name", );
 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
 		parentActivity.startChildActivity("DateSearchActivity", DT);
	}
	
	public void Genre(View button){
		Intent GE = new Intent(getParent(),GenreSearch.class);
		//DT.putExtra("Name", );
 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
 		parentActivity.startChildActivity("GenreSearchActivity", GE);
	}
	
	public void Location(View button){
		Intent LO = new Intent(getParent(),LocationSearch.class);
		//DT.putExtra("Name", );
 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
 		parentActivity.startChildActivity("LocationSearchActivity", LO);
	}
	
	public void Venue(View button){
		Intent VE = new Intent(getParent(),VenueSearch.class);
		//DT.putExtra("Name", );
 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
 		parentActivity.startChildActivity("VenueSearchActivity", VE);
	}
	
	public void Artist(View button){
		Intent AR = new Intent(getParent(),ArtistSearch.class);
		//DT.putExtra("Name", );
 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
 		parentActivity.startChildActivity("VenueSearchActivity", AR);
	}

	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		locationManager.removeUpdates(this);
		
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();

			Log.i("Latitude",String.valueOf(lat));
			Log.i("Longitude",String.valueOf(lng));
			
			myDialog.dismiss();
			 grabURL("");
			//latituteField.setText(String.valueOf(lat));
			//longitudeField.setText(String.valueOf(lng));
		} else {
			Log.i("Latitude","Cant find");
			Log.i("Longitude","Cant find");
			//latituteField.setText("GPS not available");
			//longitudeField.setText("GPS not available");
		}
	}

	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	
	private void runDialog(final int folksFonts)
    {
    	pd = ProgressDialog.show(getParent(), "Please wait....", "Here your message");
    	
    	new Thread(new Runnable(){
    		public void run(){
    			try {
					Thread.sleep(folksFonts * 1000);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					doPost();
					pd.dismiss();
				}
    		}
    	}).start();
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
	

	
	public static void removeDuplicate(ArrayList arlList)
	{
	 HashSet h = new HashSet(arlList);
	 arlList.clear();
	 arlList.addAll(h);
	// ci = arlList;
	}*/
}

