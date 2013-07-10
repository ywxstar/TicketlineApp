package com.ywx.ticketlinewebapp;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SeeMore extends Activity{
	
	 public String[] mListContent = {"Item 1", "Item 2", "Item 3"};
	 Drawable bm;
	 ListView lv;
	 Context ctx;
	 ProgressDialog progressDialog;
	 String arName;
	 String arId;
	 Dialog myDialog;
	 String url;
	 TextView title;
	 Typeface arialFont;
		
	 public ArrayList<String> imageArray = new ArrayList<String>();
	 public ArrayList<String> idsArray = new ArrayList<String>();
	 public ArrayList<String> namesArray = new ArrayList<String>();
	 public ArrayList<String> captionArray = new ArrayList<String>();
	 public ArrayList<String> urlsArray = new ArrayList<String>();
	 public ArrayList<String> filesArray = new ArrayList<String>();
	
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see);
        
        arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
        
        title = (TextView)findViewById(R.id.TextView01);
        title.setTypeface(arialFont);
        
   
        idsArray = getIntent().getStringArrayListExtra("ids");
        namesArray = getIntent().getStringArrayListExtra("names");
        captionArray = getIntent().getStringArrayListExtra("captions");
        imageArray = getIntent().getStringArrayListExtra("images");
        urlsArray = getIntent().getStringArrayListExtra("urls");
        filesArray = getIntent().getStringArrayListExtra("imageName");
        
    //    Log.i("Image Array",imageArray.toString());
        
       lv = (ListView)findViewById(R.id.lstArtists);
     //  lv.setVisibility(View.GONE);
       ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();        
        for(int i=0;i<namesArray.size();i++){
		 HashMap<String, String> map = new HashMap<String, String>();
		 map.put("image", imageArray.get(i));
		 map.put("name", namesArray.get(i));
		 map.put("id", idsArray.get(i));
		 map.put("caption", captionArray.get(i));
		 
		 mylist.add(map);
       }
	
        
        ctx = getApplicationContext();
		
		grabURL("");
       
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

   			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
   					long arg3) {
   				
   				String s = Integer.toString(arg2);
   				Log.i("no",s);
   				
   				Log.i("Name",namesArray.get(arg2));
   				
   				
   			 showDialog(idsArray.get(arg2),namesArray.get(arg2),imageArray.get(arg2));
   			}	
           });
	            
	        
	}
	
	 public void grabURL(String url) {
	        new GrabURL().execute(url);
	    }
	    
	    private class GrabURL extends AsyncTask<String, Void, Void> {
	     
	        private ProgressDialog Dialog = new ProgressDialog(SeeMore.this);
	        
	        protected void onPreExecute() {
	            Dialog.setMessage("Loading Recommended Artists..");
	            Dialog.show();
	        }

	        protected Void doInBackground(String... urls) {
	        	lv.setAdapter(new ImageAndTextAdapter(SeeMore.this, R.layout.cusseemorerow,
	    				namesArray, imageArray,captionArray,urlsArray,filesArray));
	        
			return null;
	            
	            
	        }
	        
	        protected void onPostExecute(Void unused) {
	        	  lv.setVisibility(View.VISIBLE);
	        	Dialog.dismiss();
	            
	        }
	        
	    }
	
	
	    public void showDialog(String id,String name,String u){
	    	this.arName = name;
	    	this.arId = id;
	    	this.url = u;
	    	 myDialog = new Dialog(SeeMore.this);
				myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				myDialog.setContentView(R.layout.artist_options_dialog);
				TextView tv = (TextView)myDialog.findViewById(R.id.txtName);
				tv.setText(name);
				
				Button button = (Button)myDialog.findViewById(R.id.btnBio);
				button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
				    // TODO Auto-generated method stub
				    showBio(arId,arName);
				}});
				
				Button button1 = (Button)myDialog.findViewById(R.id.btnEvents);
				button1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
				    // TODO Auto-generated method stub
				    showEvents(arId,arName,url);
				}});
				
				myDialog.show();
	    }

		public void showBio(String id,String name){
			Log.i("Bio","Bio clicked");
			Intent AR = new Intent(getApplicationContext(),  Bio.class);
			 AR.putExtra("name",name );
			 AR.putExtra("Id",id );
			 AR.putExtra("saved","false" );
	         startActivity(AR);
	         myDialog.dismiss();
		}
		
		public void showEvents(String id,String name,String url){
			Log.i("Events","Events clicked");
			Intent AR = new Intent(getApplicationContext(), Events.class);
			 AR.putExtra("name",name );
			 AR.putExtra("artist-id",id );
			 AR.putExtra("url", url);
			 AR.putExtra("method","getByArtist" );
	        startActivity(AR);
	         myDialog.dismiss();
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
		

}

