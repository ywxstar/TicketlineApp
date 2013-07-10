package com.ywx.ticketlinewebapp;

import java.io.ByteArrayOutputStream;
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
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class VenueList extends Activity {
	String name;
	TextView topic;
	ListView list;
	Dialog myDialog;
	 ArrayList<String> names = new ArrayList<String>();
     ArrayList<String> slugs = new ArrayList<String>();
     ArrayList<String> ids = new ArrayList<String>();
     final ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
     Typeface arialFont;
     Typeface folksFont; // added
     
public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.venue_list);
	arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
	folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
	//intialize views
	topic = (TextView)findViewById(R.id.txtCity);
	list = (ListView)findViewById(R.id.lstVenues);
	
	topic.setTypeface(arialFont);
	name = getIntent().getStringExtra("name");
	topic.setText(name);
	
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
    makeRequest();
    
	return null;
                
    }
    
    protected void onPostExecute(Void unused) {
  
		populate();
        myDialog.dismiss();
    }
    
}

public void makeRequest(){

    DefaultHttpClient client = new DefaultHttpClient();
    HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//venue");
    List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
    nvps.add(new BasicNameValuePair("method", "getByCity"));
    nvps.add(new BasicNameValuePair("city", name));
    nvps.add(new BasicNameValuePair("order-by", "name"));
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
          
          String s="{\"venues\":"+ostream.toString()+"}";
         
         
           Log.i("response",s);
           
           JSONObject jArray = new JSONObject(s);
           JSONArray  earthquakes = jArray.getJSONArray("venues");
        	
	        for(int i=0;i<earthquakes.length();i++){						

				JSONObject e = earthquakes.getJSONObject(i);
				
				ids.add(e.getString("id"));
				names.add(e.getString("name"));
				slugs.add(e.getString("slug"));
			
			}	
			
    } catch (Exception e) 
    { 
          e.printStackTrace();
          
    } 

  
}

public void populate(){
	SeparatedListAdapter adapter = new SeparatedListAdapter(this); // added

        	for(int a=0;a<names.size();a++){
        		       			
        			HashMap<String, String> map = new HashMap<String, String>();
        			map.put("ids", ids.get(a));
		        	map.put("name", names.get(a));
		        	map.put("slug", slugs.get(a));
		        	mylist.add(map);
		        	       		
        	}
        	
        	Log.i("My Map",mylist.toString());
        	
        	if(mylist.toString() == "[]") {
        		
        		LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.toast_layout,
				                               (ViewGroup) findViewById(R.id.toast_layout_root));
				TextView text = (TextView) layout.findViewById(R.id.text);
				text.setText("Sorry, There are no events in this area at this time.");
				text.setTypeface(arialFont);
				text.setTextColor(Color.parseColor("#00B2FF"));

				Toast toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.CENTER, 0, 50);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.setView(layout);
				toast.show();
        		
        	}


        adapter.addSection("Venues", new SimpleAdapter(this, mylist , R.layout.venue_list_item, 
        		new String[] {"name","ids",}, 
                    new int[] { R.id.list_item_title, R.id.toptext}));
        
  
    list.setFastScrollEnabled(true);
    list.setAdapter(adapter); 
    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		
			TextView tv = (TextView)arg1.findViewById(R.id.list_item_title);
			String s= tv.getText().toString();
			Log.i("Selected Name",s);
			
			getEvents(ids.get(arg2-1));
		

			TextView tv1 = (TextView)arg1.findViewById(R.id.toptext);
			String ss= tv1.getText().toString();
			Log.i("Selected Id",ss);
			
			Intent AR = new Intent(getApplicationContext(),  Events.class);
			 AR.putExtra("venue-id",ss );
			 AR.putExtra("name",s );
			 AR.putExtra("class",s );
			 AR.putExtra("method","getByVenue");
	         startActivity(AR);
	         
	 	
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

public void getEvents(String id){

    DefaultHttpClient client = new DefaultHttpClient();
    HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//event");
    List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
    nvps.add(new BasicNameValuePair("method", "getByVenue"));
    nvps.add(new BasicNameValuePair("venue-id", id));
    nvps.add(new BasicNameValuePair("artist-id", getIntent().getStringExtra("artist-id")));
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

           
        
   } catch (Exception e) 
    { 
          e.printStackTrace();
          
    } 
   

}

public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
    	
    	if(getIntent().getStringExtra("TAB").equalsIgnoreCase("2")){
    		 Intent AR = new Intent(getApplicationContext(),  TicketlineActivity.class);
    		 AR.putExtra("TAB", "2");
    		 AR.putExtra("class","Venue");
    		           startActivity(AR);

    	}else{
    		finish();
    	}
        return true;
    }
    return super.onKeyDown(keyCode, event);
}

}
