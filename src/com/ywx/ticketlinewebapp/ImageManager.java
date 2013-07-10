package com.ywx.ticketlinewebapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImageManager {

	 
	        private final String PATH = "/sdcard/Ticketline/";  //put the downloaded file here
	       
	 
	        public void DownloadFromUrl(String imageURL, String fileName) {  //this is the downloader method
	        	
	        	
	        	try {
	                // Connect to the URL
	                URL myImageURL = new URL(imageURL+fileName);
	                HttpURLConnection connection = (HttpURLConnection)myImageURL.openConnection();
	                connection.setDoInput(true);
	                connection.connect();
	                InputStream input = connection.getInputStream();

	                // Get the bitmap
	                Bitmap myBitmap = BitmapFactory.decodeStream(input);

	                // Save the bitmap to the file
	                String path = Environment.getExternalStorageDirectory().toString();

	                OutputStream fOut = null;
	                File file = new File(path, fileName);
	               
	                Log.i("help", file.getAbsolutePath());
	                Log.i("help", file.toString());
	                Log.i("help", file.length() + "");
	                System.out.println("THIS IS A TEST OF SYSTEM.OUT.PRINTLN()");
	                
	                if(!file.exists()){
	                fOut = new FileOutputStream(file);

	                myBitmap.compress(Bitmap.CompressFormat.JPEG, 25, fOut);
	                
	                fOut.flush();
	                fOut.close();
	               }
	                System.out.println("file Path: " + file.getAbsolutePath());
	                Log.i("help2", file.getAbsolutePath());
	                Log.i("help2", file.toString());
	                Log.i("help2", file.length() + "");
	                
	                } catch (IOException e) {
	                        Log.d("ImageManager", "Error: " + e);
	                }
	 
	        }
}
