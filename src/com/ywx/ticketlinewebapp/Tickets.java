package com.ywx.ticketlinewebapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Tickets extends Activity {
	
	String name;
	String slug;
	TextView tv;
	Database data;
	View arg1;
	ArrayList<Database>ques = new ArrayList<Database>();
	Functions f;
	String id;
	ImageView imageView;
	String Bio;
	String Tours;
	String Reviews;
	String Images;
	String imageUrl;
	String d;
	Dialog myDialog;
	Button tickets;
	Button tour;
	Button bio;
	
	DecimalFormat df = new DecimalFormat("#########0.00"); 
	
	ArrayList<String> eventsArray = new ArrayList<String>();
	ArrayList<String> ids = new ArrayList<String>();
	ArrayList<String> venids = new ArrayList<String>();
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> dates = new ArrayList<String>();
	ArrayList<String> available = new ArrayList<String>();
	ArrayList<String> statusArray = new ArrayList<String>();
	
	Typeface folksFont;
	Typeface arialFont;

	  
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets);
        
        folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
        f = new Functions();
        name = getIntent().getStringExtra("Name");
        slug = getIntent().getStringExtra("Slug");
        id = getIntent().getStringExtra("artist-id");
          
        arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
        tv = (TextView) findViewById(R.id.txtName);
        tv.setTypeface(arialFont);
        tv.setText(name);
        
        tickets = (Button)findViewById(R.id.Button03);
        tour = (Button)findViewById(R.id.Button02);
        bio = (Button)findViewById(R.id.Button01);
        
        tickets.setTypeface(arialFont);
        tour.setTypeface(arialFont);
        bio.setTypeface(arialFont);
        
        data = new Database("Test","Test","test","Check");
		 ques.add(data);
       
	
		  if(getIntent().getStringExtra("saved").equalsIgnoreCase("false")){
		      Log.i("Calling method","Calling ticket Methods");
		       grabURL("");

   
		        }else{
		        	
		        	parseData("");
		        }
	
	}
	
	public void populate(){
	 final ListView list = (ListView) findViewById(R.id.lstNew);
	 
		 
		 ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		 HashMap<String, String> map = new HashMap<String, String>();

		 for(int i=0;i<names.size();i++){
			
			 map = new HashMap<String, String>();
			 map.put("name", names.get(i));
			 map.put("date", dates.get(i).substring(0,10)+" "+dates.get(i).substring(10).trim());
			 map.put("available","Tickets From"+available.get(i)+" inc booking fee");
			 map.put("status", statusArray.get(i));
			 mylist.add(map); 
		 }
		
		
			list.setAdapter(new TicketAdapter(Tickets.this, R.layout.cusrow,
    				names,dates,available,statusArray,eventsArray,folksFont, arialFont));
	
	      list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
 
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.i("Clicked event id",ids.get(arg2));
				
				TextView tv = (TextView)arg1.findViewById(R.id.txtStatus);
				if(tv.getText().toString().equalsIgnoreCase("On Sale")){
				     
			        File file = new File(Environment.getExternalStorageDirectory()+"/user.xml");
				    try {
						FileInputStream fileIS = new FileInputStream(file);
						fileIS.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				    if(file.exists()){
				    	String dat = dates.get(arg2).substring(0,10);
						String time = dates.get(arg2).substring(11).trim();
				    	showPrices(ids.get(arg2),name,names.get(arg2),f.createDate(dat, time));
				    }else{
				    	showLoginDialog();
				    }
			   
				
				} else {
					
					LayoutInflater inflater = getLayoutInflater();
					View layout = inflater.inflate(R.layout.toast_layout,
					                               (ViewGroup) findViewById(R.id.toast_layout_root));

					TextView text = (TextView) layout.findViewById(R.id.text);
					text.setText("Sorry, these tickets are unavailable.");
					text.setTypeface(arialFont);
					text.setTextColor(Color.parseColor("#00B2FF"));

					Toast toast = new Toast(getApplicationContext());
					toast.setGravity(Gravity.CENTER, 0, 50);
					toast.setDuration(Toast.LENGTH_SHORT);
					toast.setView(layout);
					toast.show();
	
				}
				
			}
		});
	}
	
	public void showLoginDialog(){
	
		 Intent AR = new Intent(getApplicationContext(),  TicketlineActivity.class);
		 AR.putExtra("TAB", "1");
		           startActivity(AR);
		           finish();
	}
	
	public void makeRequest(){
		 f.getEventsByArtist(id);
	      getBio(slug); 
	}
	public void grabURL(String url) {
        new GrabURL().execute(url);
    }
    
    private class GrabURL extends AsyncTask<String, Void, Void> {
     
       
        
        protected void onPreExecute() {
        	Log.i("Load","Loading");
        	showLoadDialog("Loading...");
        }

        protected Void doInBackground(String... urls) {
        	  
        	makeRequest();
        	try {	
	    			 getEvents(f.getSavedEvents());
	    			
	    		} catch (JSONException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
        	
		return null;
            
            
        }
        
        protected void onPostExecute(Void unused) {
        	
        	populate();
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
	      
	public void getEvents(String s) throws JSONException{
	       JSONObject jArray = new JSONObject(s);
           JSONArray  earthquakes = jArray.getJSONArray("categories");
        	
           for(int i=0;i<earthquakes.length();i++){							
				JSONObject e = earthquakes.getJSONObject(i);
			
				Log.i("event id",e.getString("id"));
				
				ids.add(e.getString("id"));
				
				Log.i("event name",e.getString("event_name"));
				eventsArray.add(e.getString("event_name"));
				
				Log.i("venue_name",e.getString("venue_space_name"));
				names.add(e.getString("venue_space_name"));
				Log.i("Event Date",e.getString("datetime"));
				dates.add(e.getString("datetime"));
				available.add(e.getString("sql_min_price"));
				Log.i("sql_min_price",e.getString("sql_min_price"));
				Log.i("Seating Map Name",e.getString("seating_map_name"));

				String status = "{\"status\":["+e.getString("Status")+"]}";
				
				Log.i("Status",status);
	        
	      
				 JSONObject jArray1 = new JSONObject(status);
		           JSONArray  earthquakes1 = jArray1.getJSONArray("status");
		        	
		           for(int b=0;b<earthquakes1.length();b++){						
						
						JSONObject ee = earthquakes1.getJSONObject(b);
						Log.i("Status",ee.getString("name"));
						if(ee.getString("name").equalsIgnoreCase("Buy Tickets")){
							statusArray.add("On Sale");
						}else{
							// 
						   statusArray.add(ee.getString("name"));
						}
		           }
		           
        	}
	}
	
	public void back(View button){
		Intent SeeMore = new Intent(getParent(),SeeMore.class);
 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
 		parentActivity.startChildActivity("MoreViewActivity", SeeMore);
	}
	
	public void showPrices(String event,String Name,String VenName, String Date){
		Intent eve = new Intent(getApplicationContext(),Prices.class);
		
		eve.putExtra("event-id",event);
		eve.putExtra("ar_name",Name);
		eve.putExtra("ven_name",VenName);
		eve.putExtra("event_date",Date);
		eve.putExtra("TAB", "");
	
		 startActivity(eve);
	}
	
	public void Bio(View button){
		Intent Bio = new Intent(getApplicationContext(),Bio.class);
		Bio.putExtra("Name", name);
		Bio.putExtra("saved","true");
		Bio.putExtra("artist-id",id);
		 startActivity(Bio);
		 finish();
	}
	
	

	
	public void Reviews(View button){
		Intent Rev = new Intent(getApplicationContext(),Reviews.class);
		Rev.putExtra("Name", name);
		Rev.putExtra("saved","true");
		 startActivity(Rev);
		 finish();
	}
	
	public void Tour(View button){
		Intent Med = new Intent(getApplicationContext(),Tour.class);
		Med.putExtra("Name", name);
		Med.putExtra("saved","true");
		 startActivity(Med);
		 finish();
	}
	public void Links(View button){
		Intent Links = new Intent(getApplicationContext(),Links.class);
		Links.putExtra("Name", name);
		 startActivity(Links);
	}
	
	public void getBio(String s){

		Log.i("Bio artist id",s);
      //  final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        
        
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//artist");
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("method", "getBySlug"));
        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
        nvps.add(new BasicNameValuePair("slug", slug));

        try {
              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
HTTP.UTF_8);
              httppost.setEntity(p_entity);
              HttpResponse response = client.execute(httppost);
              Log.v("t", response.getStatusLine().toString());

              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
              
              response.getEntity().writeTo(ostream);
          
              Log.e("HTTP CLIENT", ostream.toString());
              
             d="{\"categories\":["+ostream.toString()+"]}";
	           Log.i("response",d);

	           JSONObject jArray = new JSONObject(d);
	           JSONArray  earthquakes = jArray.getJSONArray("categories");
	        	
		        for(int i=0;i<earthquakes.length();i++){							
					JSONObject e = earthquakes.getJSONObject(i);
					
					Log.i("name",e.getString("name"));
					Log.i("image url",e.getString("image_base_url"));
					imageUrl = e.getString("image_base_url");
					Log.i("Bio",e.getString("Bio"));
		        	Log.i("Tours",e.getString("Tours"));
		        	Log.i("Images",e.getString("Images"));
		        	Log.i("Reviews",e.getString("Reviews"));
		        	
		        	
		        	Bio = "{\"bio\":["+e.getString("Bio")+"]}";
		        	Tours = "{\"tours\":"+e.getString("Tours")+"}";
		        	Images = "{\"images\":"+e.getString("Images")+"}";
		        	Reviews = "{\"revies\":"+e.getString("Reviews")+"}";
		        	
		        	Log.i("bio json",Bio);
		        	Log.i("tours json",Tours);
		        	Log.i("images json",Images);
				
		        	
				}	
		        
		        JSONObject BioArray = new JSONObject(Bio);
		        JSONArray  bio = BioArray.getJSONArray("bio");
	        	for(int a=0;a<bio.length();a++){
	        		JSONObject ee = bio.getJSONObject(a);
	        		Log.i("description",ee.getString("description"));
	        	}
	        	
	        	 JSONObject imageArray = new JSONObject(Images);
			        JSONArray  img = imageArray.getJSONArray("images");
		        	for(int a=0;a<img.length();a++){
		        		JSONObject ee = img.getJSONObject(a);
		        		Log.i("image name",ee.getString("filename"));
		        		
		        	}
             
        } catch (Exception e) 
        { 
              e.printStackTrace();
              
        } 
        
        //save the bio info and tours to the xml file
        saveBio();
        
	}
	
	public void saveBio(){
    	//save the token to the xml file
 	File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/bio.xml");
        try{
                newxmlfile.createNewFile();
        }catch(IOException e1){
                Log.e("IOException", "exception in createNewFile() method");
        }
        //we have to bind the new file with a FileOutputStream
        FileOutputStream fileos = null;        
        try{
                fileos = new FileOutputStream(newxmlfile);
        }catch(FileNotFoundException e1){
                Log.e("FileNotFoundException", "can't create FileOutputStream");
        }
 	  XmlSerializer serializer = Xml.newSerializer();
	        try {
 	 serializer.setOutput(fileos, "UTF-8");
         //Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null)
         serializer.startDocument(null, Boolean.valueOf(true));
         //set indentation option
         serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
         //start a tag called "root"
         serializer.startTag(null, "artist");
         serializer.startTag(null, "info");
         serializer.startTag(null, "bio");
         serializer.text(Bio);
         serializer.endTag(null, "bio");
         serializer.startTag(null, "tours");
         serializer.text(Tours);
         serializer.endTag(null, "tours");  
         serializer.startTag(null, "reviews");
         serializer.text(Reviews);
         serializer.endTag(null, "reviews"); 
         serializer.startTag(null, "json");
         serializer.text(d);
         serializer.endTag(null, "json");
         
         serializer.endTag(null, "info");
	        
	        serializer.endTag(null, "artist");
         serializer.endDocument();
         //write xml data into the FileOutputStream
         serializer.flush();
         //finally we close the file stream
         fileos.close();
        

        Log.i("file has been created on SD card","Success");
 } catch (Exception e1) {
         Log.e("Exception","error occurred while creating xml file");
 }
 }
	
	public void parseData(String url) {
        new ParseData().execute(url);
    }
    
	 private class ParseData extends AsyncTask<String, Void, Void> {
	     
	       
	        
	        protected void onPreExecute() {
	         //   Dialog.setMessage("Loading...");
	         //   Dialog.show();
	        	Log.i("Load","Loading");
	        	showLoadDialog("Loading...");
	        }

	        protected Void doInBackground(String... urls) {
	        	  
	        	
	        	try {
		    			 getEvents(f.getSavedEvents());
		    			
		    		} catch (JSONException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
	        	
			return null;
	            
	            
	        }
	        
	        protected void onPostExecute(Void unused) {
	        	
	        	populate();
	        	myDialog.dismiss();
	            
	        }
	        
	    }
   
}
