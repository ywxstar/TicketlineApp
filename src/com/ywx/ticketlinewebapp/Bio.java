package com.ywx.ticketlinewebapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.util.Xml;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Bio extends Activity {
	
	
	Button bioButton;
	Button tourButton;
	Button ticketsButton;
	String name;
	TextView tv;
	TextView desc;
	String arName;
	String arId;
	ImageView imageView;
	String Bio;
	String Tours;
	String Reviews;
	String Images;
	String imageUrl;
	Functions f;
	String d;
	
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bio);
        f = new Functions();
        //get the display size to show the image in different screen sizes
        Display display = getWindowManager().getDefaultDisplay(); 
        int width = display.getWidth();
        int height = display.getHeight();
        String s = String.valueOf(width);
        Log.i("Screen Width",s);
        Log.i("Screen Width",String.valueOf(height));
        
        name = getIntent().getStringExtra("Name");
        arName = getIntent().getStringExtra("name");
        arId = getIntent().getStringExtra("artist-id");
        
        Typeface myTypeface = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
        
        bioButton = (Button)findViewById(R.id.Button01);
        bioButton.setTypeface(myTypeface);
        
        tourButton = (Button)findViewById(R.id.Button02);
        tourButton.setTypeface(myTypeface);
        
        ticketsButton = (Button)findViewById(R.id.Button03);
        ticketsButton.setTypeface(myTypeface);
        
        tv = (TextView) findViewById(R.id.txtName);
        tv.setTypeface(myTypeface);
       
        tv.setText(name);
        
        desc = (TextView) findViewById(R.id.txtDesc);
        
        imageView = (ImageView)findViewById(R.id.imgArtist);
        
        if(getIntent().getStringExtra("saved").equalsIgnoreCase("true")){
   	     try {
			readXML();
		 } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }else{

       }
        
       		   RotateAnimation rotateAnimation = new RotateAnimation(0,360,
    		   Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
    		   0.5f);

    		   rotateAnimation.setInterpolator(new LinearInterpolator());
    		   rotateAnimation.setDuration(700);
    		   rotateAnimation.setFillAfter(true);
        }

	public void Tour(View button) {
		Intent Tour = new Intent(getApplicationContext(),Tour.class);
		Tour.putExtra("Name", name);
		Tour.putExtra("artist-id", arId);
		Tour.putExtra("saved", "true");
		 startActivity(Tour);
		 finish();
	}
	
	public void Reviews(View button) {
		Intent Rev = new Intent(getApplicationContext(),Reviews.class);
		Rev.putExtra("Name", name);
		Rev.putExtra("artist-id", arId);
		Rev.putExtra("saved","true");
		 startActivity(Rev);
		 finish();
	}
	
	public void Tickets(View button) {
		Intent Tour = new Intent(getApplicationContext(),Tickets.class);
		Tour.putExtra("Name", name);
		Tour.putExtra("artist-id", arId);
		Tour.putExtra("saved","true");
		 startActivity(Tour);
		 finish();
	}
	public void Links(View button) {
		Intent Links = new Intent(getApplicationContext(),Links.class);
		Links.putExtra("Name", name);
		 startActivity(Links);
	}
	
	public void saveEvents() {
		f.getEventsByArtist(arId);
	}
	
	public void getBio(String s) {

		Log.i("Bio artist id",s);
        
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//artist");
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("method", "getBySlug"));
        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
        nvps.add(new BasicNameValuePair("slug", s.replaceAll(" ","-")));
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
	        		String des = ee.getString("description");
	        		if(des!=null&& des.length()>0){
	        		desc.setText(Html.fromHtml(ee.getString("description")));
	        		}else{
	        			desc.setGravity(Gravity.CENTER);
	        			desc.setText("No Bio available");
	        		}
	        	}
	        	
	        	 JSONObject imageArray = new JSONObject(Images);
			        JSONArray  img = imageArray.getJSONArray("images");
		        	for(int a=0;a<img.length();a++){
		        		JSONObject ee = img.getJSONObject(a);
		        		Log.i("image name",ee.getString("filename"));

		        		try {

		        		} catch (Exception e) {
		        		    //do something
		        		}
		        	}
             
        } catch (Exception e) { 
              e.printStackTrace();
              
        } 
        
        //save the bio info and tours to the xml file
        saveBio();
        
	}
	
	public void saveBio() {
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
	
	public void readXML() throws JSONException{
		 JSONObject jArray = new JSONObject(f.getArtistBio());
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
	        	tv.setText(e.getString("name"));
	        	 
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
	        
	        for(int a=0;a<bio.length();a++) {
	        	
      		JSONObject ee = bio.getJSONObject(a);
      		Log.i("description",ee.getString("description"));
      		String des = ee.getString("description");
      		
      		if(des!=null && des.length()>0) {
      			desc.setText(Html.fromHtml(des));
      		    } else {
      			desc.setText("No Bio Available");
      		  }
      		//getImageWide();
	        }
	        
		
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
	 
	 public void getImageWide() throws JSONException {
		 
			JSONObject imageArray = new JSONObject(Images);
 	        JSONArray  img = imageArray.getJSONArray("images");
 	        for(int b=0;b<imageArray.length();b++) {
       		
 	        	JSONObject eee = img.getJSONObject(b);
       		
 	        	String type = eee.getString("image_type_id");
 	        	Log.i("type",type);
       		
 	        	if(type.equalsIgnoreCase("2")) {
 	        		Log.i("filename",eee.getString("filename"));
 	        		String fname = eee.getString("filename");
 	        		Log.i("Bio image url",imageUrl+fname);
 	        		Drawable drawable = LoadImageFromWeb(imageUrl+fname);
 	        		imageView.setImageDrawable(drawable);
				
 	        	} else {
 	        		desc.setText("No Bio Available");
 	        	}
       		
 	        }
	}
	 
}
