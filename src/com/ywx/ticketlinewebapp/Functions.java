package com.ywx.ticketlinewebapp;
 
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class Functions {

	private String converted;
    String  us;
	String token;
	String url;
	
	double ticketPrice = 1000;
	public String summary;
		
	public String getSha(String in){
		 try {
	        
			 MessageDigest md = MessageDigest.getInstance("SHA1");
	         System.out.println("Message digest object info: ");
	         System.out.println("   Algorithm = "+md.getAlgorithm());
	         System.out.println("   Provider = "+md.getProvider());
	         System.out.println("   toString = "+md.toString());
	         
	         md.update(in.getBytes()); 
	      	 byte[] output = md.digest();
	         System.out.println();
	         System.out.println("SHA1(\""+in+"\") =");
	         System.out.println("   "+bytesToHex(output));
	         converted = bytesToHex(output);
	         Log.i("Con",converted);
	         
		 } catch (Exception e) {
	         
			 System.out.println("Exception: "+e);
	     }
		 
		 return converted;
	 }
		 
	 public static String bytesToHex(byte[] b) {
	      char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
	                         '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	      StringBuffer buf = new StringBuffer();
	      for (int j=0; j<b.length; j++) {
	         buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
	         buf.append(hexDigit[b[j] & 0x0f]);
	      }
	      return buf.toString();
	  }
		 
		 
	 public String getServerTime(){
		 
		 DefaultHttpClient client = new DefaultHttpClient();
	     HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//server");
	     List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
	     nvps.add(new BasicNameValuePair("method", "getTime"));
	     nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
	     
	     try {
	    	 UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
	         httppost.setEntity(p_entity);
             HttpResponse response = client.execute(httppost);
             Log.v("t", response.getStatusLine().toString());
              
             ByteArrayOutputStream ostream = new ByteArrayOutputStream();
              
             response.getEntity().writeTo(ostream);
             us =ostream.toString();
             Log.e("HTTP CLIENT", ostream.toString());
        } catch (Exception e) { 
        	
              e.printStackTrace();
        } 
	        
	    Log.i("Server Time",us);
	    return us ;
	 }
	 
	 public long getDeviceTime(){
		
		 long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
		 return ts;
	 }
	 
	 public static long convertDateToMillifolksFonts(Date date){
		 return date.getTime();
	 }
	 
	 public String getUserToken(){
		 try{
			 
			 File f = new File(Environment.getExternalStorageDirectory()+"/user.xml");
    	     FileInputStream fileIS = new FileInputStream(f);

			 XmlPullParserFactory x = XmlPullParserFactory.newInstance();
			 x.setNamespaceAware(false);
			 XmlPullParser p = x.newPullParser();
			 p.setInput(new InputStreamReader(fileIS));
			 boolean parsing = true;
			 String curText="", name;
			 
			 while(parsing){
				 
				int next = p.next();
				switch(next){
				
				case XmlPullParser.START_TAG:
					name = p.getName();
					if(name.equals("user"))
						
					break;
				case XmlPullParser.END_TAG:
					
					name = p.getName();
					if(name.equals("auth")){	
						
					}else if(name.equals("token")){
						
						token = curText;
					}
					break;
				case XmlPullParser.CDSECT:
				case XmlPullParser.TEXT:
					curText = p.getText();
					break;
				case XmlPullParser.END_DOCUMENT:
					parsing = false;
					
				}
			}
			
		}catch(Exception e){
			
			Log.e("4sqrClient","Failed to get");
		}
		
		return token;
	 }
	 
	 public String getUserInfo(){ 
		 try{
			 File f = new File(Environment.getExternalStorageDirectory()+"/user.xml");
    	     FileInputStream fileIS = new FileInputStream(f);
    	    

			 XmlPullParserFactory x = XmlPullParserFactory.newInstance();
			 x.setNamespaceAware(false);
			 XmlPullParser p = x.newPullParser();
			 p.setInput(new InputStreamReader(fileIS));
			 boolean parsing = true;
			 String curText="", name;
			 while(parsing){
				 
				 int next = p.next();
				 switch(next){
				 case XmlPullParser.START_TAG:
					 name = p.getName();
					 if(name.equals("user"))
				  	 break;
					 
				 case XmlPullParser.END_TAG:
					name = p.getName();
					if(name.equals("auth")){
						
					}else if(name.equals("json")){
						
						token = curText;
					}
					break;
					
				 case XmlPullParser.CDSECT:
				 case XmlPullParser.TEXT:
					curText = p.getText();
					break;
					
				 case XmlPullParser.END_DOCUMENT:
						parsing = false;
				 }
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return token;
	}
		 
	public String getArtistImage(String id){
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//artist");
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("method", "getById"));
        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
        nvps.add(new BasicNameValuePair("id", id));
        
        try {
        	UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,HTTP.UTF_8);
            httppost.setEntity(p_entity);
            HttpResponse response = client.execute(httppost);
            Log.v("t", response.getStatusLine().toString());

            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
          
            response.getEntity().writeTo(ostream);
      
            Log.e("HTTP CLIENT", ostream.toString());
          
            String d="{\"categories\":["+ostream.toString()+"]}";
            Log.i("response",d);

            JSONObject jArray = new JSONObject(d);
            JSONArray  earthquakes = jArray.getJSONArray("categories");
        	
	        for(int i=0;i<earthquakes.length();i++){							
				JSONObject e = earthquakes.getJSONObject(i);
				
				Log.i("name",e.getString("name"));
				Log.i("image url",e.getString("image_base_url"));
				Log.i("image ",e.getString("image_default"));
	        	Log.i("image url",e.getString("image_base_url")+e.getString("image_default"));
	        	 url = e.getString("image_base_url")+e.getString("image_default");

			}	
        }catch (Exception e) { 
        	e.printStackTrace();
    	} 
        
        return url;
    }
	
	public String getArtistTours(){
		try{
			File f = new File(Environment.getExternalStorageDirectory()+"/bio.xml");
		    FileInputStream fileIS = new FileInputStream(f);
				    	    
			XmlPullParserFactory x = XmlPullParserFactory.newInstance();
			x.setNamespaceAware(false);
			XmlPullParser p = x.newPullParser();
			p.setInput(new InputStreamReader(fileIS));
		
			boolean parsing = true;
			String curText="", name;
			while(parsing) {
				int next = p.next();
				switch(next){
				case XmlPullParser.START_TAG:
					name = p.getName();
					if(name.equals("artist"))
						
					break;
				case XmlPullParser.END_TAG:
					name = p.getName();
					if(name.equals("info")){
						
					}else if(name.equals("tours")){
						token = curText;
					}
			
					break;
				case XmlPullParser.CDSECT:
				case XmlPullParser.TEXT:
					curText = p.getText();
					break;
				case XmlPullParser.END_DOCUMENT:
					parsing = false;
					
				}
			}
		}catch(Exception e){
			Log.e("4sqrClient","Failed to get");
		}
		
		return token;
	}
	
	public String getArtistBio(){
		try{
			
			File f = new File(Environment.getExternalStorageDirectory()+"/bio.xml");
    	    FileInputStream fileIS = new FileInputStream(f);
    	    

			XmlPullParserFactory x = XmlPullParserFactory.newInstance();
			x.setNamespaceAware(false);
			XmlPullParser p = x.newPullParser();
			p.setInput(new InputStreamReader(fileIS));
	
			boolean parsing = true;
			String curText="", name;
			
			while(parsing){
				int next = p.next();
				switch(next){
				case XmlPullParser.START_TAG:
					name = p.getName();
					if(name.equals("artist"))
						
					break;
				case XmlPullParser.END_TAG:
					name = p.getName();
					if(name.equals("info")){
						
					}else if(name.equals("json")){
						
						token = curText;
					}
			
					break;
				case XmlPullParser.CDSECT:
				case XmlPullParser.TEXT:
					curText = p.getText();
					break;
				case XmlPullParser.END_DOCUMENT:
					parsing = false;
					
				}
			}
		}catch(Exception e){
			Log.e("4sqrClient","Failed to get");
		}
		
		return token;
	}

	public String getEventsByArtist(String id){
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//event");
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("method", "getByArtist"));
        
        nvps.add(new BasicNameValuePair("artist-id", id));
        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
       
        try {
        	UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
            httppost.setEntity(p_entity);
            HttpResponse response = client.execute(httppost);
            Log.v("t", response.getStatusLine().toString());

            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
          
            response.getEntity().writeTo(ostream);
      
            Log.e("HTTP CLIENT", ostream.toString());
          
            token="{\"categories\":"+ostream.toString()+"}";
            Log.i("response",token);

            saveEvents(token);
        } catch (Exception e) { 
        	e.printStackTrace();
    	} 
        return token;
	}
	
	public void saveEvents(String j){
		//save the token to the xml file
	     
		File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/events.xml");
		
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
             serializer.startTag(null, "events");
             serializer.startTag(null, "info");
                        
             serializer.startTag(null, "json");
             serializer.text(j);
             serializer.endTag(null, "json"); 
            
             serializer.endTag(null, "info");
 	         serializer.endTag(null, "events");
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
		 
	public String getSavedEvents(){
		try{
			File f = new File(Environment.getExternalStorageDirectory()+"/events.xml");
			FileInputStream fileIS = new FileInputStream(f);
				    	    
			//	Log.i("Response",httpconn.getInputStream().toString());
			XmlPullParserFactory x = XmlPullParserFactory.newInstance();
			x.setNamespaceAware(false);
			XmlPullParser p = x.newPullParser();
			p.setInput(new InputStreamReader(fileIS));
			//p.setInput(new InputStreamReader(httpconn.getInputStream()));
			boolean parsing = true;
			String curText="", name;
			while(parsing){
				int next = p.next();
				switch(next){
				case XmlPullParser.START_TAG:
					name = p.getName();
					if(name.equals("events"))
						break;
				case XmlPullParser.END_TAG:
					name = p.getName();
					if(name.equals("info")){
						
					}else if(name.equals("json")){
						token = curText;
					}
					break;
				case XmlPullParser.CDSECT:
				case XmlPullParser.TEXT:
					curText = p.getText();
					break;
					
				case XmlPullParser.END_DOCUMENT:
					parsing = false;
				}
			}
		}catch(Exception e){
			Log.e("4sqrClient","Failed to get");
		}
		return token;
	}
		 
	public String getSingleVenue(String id){
		DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//venue");
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("method", "getById"));
        nvps.add(new BasicNameValuePair("id",id));
        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
	
        try {
              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
              httppost.setEntity(p_entity);
              HttpResponse response = client.execute(httppost);
              Log.v("t", response.getStatusLine().toString());

              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
              
              response.getEntity().writeTo(ostream);
          
              Log.e("HTTP CLIENT", ostream.toString());
              
             token="{\"categories\":["+ostream.toString()+"]}";
	           Log.i("response",token);
 
        } catch (Exception e)  { 
              e.printStackTrace(); 
        } 
        return token;
    }

	public String getByUserPreferredArtists(){
		
		long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
		long st = Long.valueOf(getServerTime());
		
		long i = ts+ (st-ts);
		
		getSha(i+"YWFmOGMzNWJlNjk");
		
	    DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//recommendation");
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("method", "getByUserPreferredArtists"));
        nvps.add(new BasicNameValuePair("user-token",getUserToken()));
        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
        nvps.add(new BasicNameValuePair("api-token",converted));
        nvps.add(new BasicNameValuePair("limit","25"));

        try {
        	UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
            httppost.setEntity(p_entity);
            HttpResponse response = client.execute(httppost);
            Log.v("t", response.getStatusLine().toString());

            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
          
            response.getEntity().writeTo(ostream);
      
            Log.e("HTTP CLIENT", ostream.toString());
          
            token="{\"categories\":["+ostream.toString()+"]}";
            Log.i("response",token);
        } catch (Exception e)  { 
        	e.printStackTrace();
    	} 
        return token;
    }

	public String getDeliveryMethods(String id){
		
		long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
		long st = Long.valueOf(getServerTime());
				
		long i = ts+ (st-ts);
		
		getSha(i+"YWFmOGMzNWJlNjk");
		DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//order");
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("method", "getDeliveryMethods"));
        nvps.add(new BasicNameValuePair("user-token",getUserToken()));
        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
        nvps.add(new BasicNameValuePair("api-token",converted));
        nvps.add(new BasicNameValuePair("event-id",id));

        try {
              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
              httppost.setEntity(p_entity);
              HttpResponse response = client.execute(httppost);
              Log.v("t", response.getStatusLine().toString());
              // HttpEntity responseEntity = response.getEntity();
              // Log.v(TAG, );
              
              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
              
              response.getEntity().writeTo(ostream);
          
              Log.e("HTTP CLIENT", ostream.toString());
              
              token="{\"categories\":"+ostream.toString()+"}";
	          Log.i("response",token);
          } catch (Exception e)  { 
        	  e.printStackTrace();
    	  } 
        return token;
    }
		 
		 
	 public String selectDeliveyMethod(String eventid,String id){
			long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
			long st = Long.valueOf(getServerTime());
			
			long i = ts+ (st-ts);
			
			getSha(i+"YWFmOGMzNWJlNjk");
			
				    DefaultHttpClient client = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//order");
			        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			        nvps.add(new BasicNameValuePair("method", "selectDeliveryMethod"));
			        nvps.add(new BasicNameValuePair("user-token",getUserToken()));
			        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
			        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
			        nvps.add(new BasicNameValuePair("api-token",converted));
			        nvps.add(new BasicNameValuePair("event-id",eventid));
			        nvps.add(new BasicNameValuePair("delivery-method",id));
			     
			        try {
			              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
			HTTP.UTF_8);
			              httppost.setEntity(p_entity);
			              HttpResponse response = client.execute(httppost);
			              Log.v("t", response.getStatusLine().toString());
		
			              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			              
			              response.getEntity().writeTo(ostream);
			          
			              Log.e("HTTP CLIENT", ostream.toString());
			              
			             token="{\"categories\":["+ostream.toString()+"]}";
			           
				           Log.i("response",token);

				          
				        	
					       
			             
			        } catch (Exception e) 
			        { 
			              e.printStackTrace();
			              
			        } 
			        
			        return token;
			
	 }
	 
	 public String setPostcode(String eventid,String post){
			long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
			long st = Long.valueOf(getServerTime());
			
			long i = ts+ (st-ts);
			
			getSha(i+"YWFmOGMzNWJlNjk");
			
				    DefaultHttpClient client = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//order");
			        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			        nvps.add(new BasicNameValuePair("method", "setPostcode"));
			        nvps.add(new BasicNameValuePair("user-token",getUserToken()));
			        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
			        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
			        nvps.add(new BasicNameValuePair("api-token",converted));
			        nvps.add(new BasicNameValuePair("event-id",eventid));
			        nvps.add(new BasicNameValuePair("postcode",post));
			    
			        try {
			              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
			HTTP.UTF_8);
			              httppost.setEntity(p_entity);
			              HttpResponse response = client.execute(httppost);
			              Log.v("t", response.getStatusLine().toString());
		
			              
			              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			              
			              response.getEntity().writeTo(ostream);
			          
			              Log.e("HTTP CLIENT", ostream.toString());
			              
			             token=ostream.toString();
				           Log.i("response",token);

				          
				        	
					       
			             
			        } catch (Exception e) 
			        { 
			              e.printStackTrace();
			              
			        } 
			        
			        return token;
			
	 }
	 
	 
	 public String setPassword(String eventid,String pass){
			long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
			long st = Long.valueOf(getServerTime());
			
			long i = ts+ (st-ts);
			
			getSha(i+"YWFmOGMzNWJlNjk");
			
				    DefaultHttpClient client = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//order");
			        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			        nvps.add(new BasicNameValuePair("method", "setPassword"));
			        nvps.add(new BasicNameValuePair("user-token",getUserToken()));
			        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
			        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
			        nvps.add(new BasicNameValuePair("api-token",converted));
			        nvps.add(new BasicNameValuePair("event-id",eventid));
			        nvps.add(new BasicNameValuePair("password",pass));
			      
			        try {
			              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
			HTTP.UTF_8);
			              httppost.setEntity(p_entity);
			              HttpResponse response = client.execute(httppost);
			              Log.v("t", response.getStatusLine().toString());
		
			              
			              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			              
			              response.getEntity().writeTo(ostream);
			          
			              Log.e("HTTP CLIENT", ostream.toString());
			              
			             token=ostream.toString();
				           Log.i("response",token);

				          
				        	
					       
			             
			        } catch (Exception e) 
			        { 
			              e.printStackTrace();
			              
			        } 
			        
			        return token;
			
	 }

	public String getPaymentMethods(String id) {
		// TODO Auto-generated method stub
		long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
		long st = Long.valueOf(getServerTime());
		
		long i = ts+ (st-ts);
		
		getSha(i+"YWFmOGMzNWJlNjk");
		
			    DefaultHttpClient client = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//order");
		        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		        nvps.add(new BasicNameValuePair("method", "getPaymentMethods"));
		        nvps.add(new BasicNameValuePair("user-token",getUserToken()));
		        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
		        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
		        nvps.add(new BasicNameValuePair("api-token",converted));
		        nvps.add(new BasicNameValuePair("event-id",id));
		 
		        try {
		              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
		HTTP.UTF_8);
		              httppost.setEntity(p_entity);
		              HttpResponse response = client.execute(httppost);
		              Log.v("t", response.getStatusLine().toString());
		             // HttpEntity responseEntity = response.getEntity();
		             // Log.v(TAG, );
		              
		              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		              
		              response.getEntity().writeTo(ostream);
		          
		              Log.e("HTTP CLIENT", ostream.toString());
		              
		             //token="{\"categories\":"+ostream.toString()+"}";
		            token=ostream.toString();
			           Log.i("response",token);

			          
			        	
				       
		             
		        } catch (Exception e) 
		        { 
		              e.printStackTrace();
		              
		        } 
			return token;
	}
	
	 public void selectPaymentMethod(String eventid,String selToken, String cvc, int terms){
			long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
			long st = Long.valueOf(getServerTime());
			
			long i = ts+ (st-ts);
			
			getSha(i+"YWFmOGMzNWJlNjk");
			
				    DefaultHttpClient client = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//order");
			        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			        nvps.add(new BasicNameValuePair("method", "selectPaymentMethod"));
			        nvps.add(new BasicNameValuePair("user-token",getUserToken()));
			        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
			        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
			        nvps.add(new BasicNameValuePair("api-token",converted));
			        nvps.add(new BasicNameValuePair("event-id",eventid));
			        nvps.add(new BasicNameValuePair("saved-card[token]",selToken));
			        nvps.add(new BasicNameValuePair("saved-card[cvc]",cvc));
			        nvps.add(new BasicNameValuePair("saved-card[terms-accepted]",String.valueOf(terms)));

			        try {
			              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
			HTTP.UTF_8);
			              httppost.setEntity(p_entity);
			              HttpResponse response = client.execute(httppost);
			              Log.v("t", response.getStatusLine().toString());

			              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			              
			              response.getEntity().writeTo(ostream);
			          
			              Log.e("HTTP CLIENT", ostream.toString());
			              
			             token="{\"categories\":"+ostream.toString()+"}";
				           Log.i("payment selection response",token);

				          
				        	
					       
			             
			        } catch (Exception e) 
			        { 
			              e.printStackTrace();
			              
			        } 
			
	 }
	 
	 
	 public String addNewCard(ArrayList<String> vals, String eventid){
			long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
			long st = Long.valueOf(getServerTime());
			
			long i = ts+ (st-ts);
			
			getSha(i+"YWFmOGMzNWJlNjk");
			
				    DefaultHttpClient client = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//order");
			        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			        nvps.add(new BasicNameValuePair("method", "selectPaymentMethod"));
			        nvps.add(new BasicNameValuePair("user-token",getUserToken()));
			        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
			        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
			        nvps.add(new BasicNameValuePair("api-token",converted));
			        nvps.add(new BasicNameValuePair("event-id",eventid));
			        nvps.add(new BasicNameValuePair("new-card[type_id]",vals.get(0)));
			        nvps.add(new BasicNameValuePair("new-card[number]",vals.get(1)));
			        
			        nvps.add(new BasicNameValuePair("new-card[expires_end][month]",vals.get(4)));
			        nvps.add(new BasicNameValuePair("new-card[expires_end][year]",vals.get(5)));
			        nvps.add(new BasicNameValuePair("new-card[user-label]",vals.get(8)));
			        nvps.add(new BasicNameValuePair("new-card[cvc]",vals.get(7)));
			        nvps.add(new BasicNameValuePair("new-card[save]","1"));
			        nvps.add(new BasicNameValuePair("new-card[terms-accepted]","1"));
			        
			        if(vals.get(1).equalsIgnoreCase("3")){
			        	nvps.add(new BasicNameValuePair("new-card[valid_from][month]",vals.get(2)));
				        nvps.add(new BasicNameValuePair("new-card[valid_from][year]",vals.get(3)));
				        nvps.add(new BasicNameValuePair("new-card[issue_number]",vals.get(6)));
			        }
			        
			     
			        try {
			              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
			HTTP.UTF_8);
			              httppost.setEntity(p_entity);
			              HttpResponse response = client.execute(httppost);
			              Log.v("t", response.getStatusLine().toString());
			       
			              
			              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			              
			              response.getEntity().writeTo(ostream);
			          
			              Log.e("HTTP CLIENT", ostream.toString());
			              
			             token="{\"categories\":["+ostream.toString()+"]}";
				           Log.i("payment selection response",token);

				          
				        	
					       
			             
			        } catch (Exception e) 
			        { 
			              e.printStackTrace();
			              
			        } 
			        
			        return token;
			
	 }
	
	 public String doOreder(String eventid){
			long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
			long st = Long.valueOf(getServerTime());
			
			long i = ts+ (st-ts);
			
			getSha(i+"YWFmOGMzNWJlNjk");
			
				    DefaultHttpClient client = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//order");
			        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			        nvps.add(new BasicNameValuePair("method", "doOrder"));
			        nvps.add(new BasicNameValuePair("user-token",getUserToken()));
			        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
			        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
			        nvps.add(new BasicNameValuePair("api-token",converted));
			        nvps.add(new BasicNameValuePair("event-id",eventid));
			      
	
			        try {
			              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
			HTTP.UTF_8);
			              httppost.setEntity(p_entity);
			              HttpResponse response = client.execute(httppost);
			              Log.v("t", response.getStatusLine().toString());

			              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			              
			              response.getEntity().writeTo(ostream);
			          
			              Log.e("HTTP CLIENT", ostream.toString());
			              
			             token="{\"categories\":["+ostream.toString()+"]}";
				           Log.i("do order response",token);

			
			             
			        } catch (Exception e) 
			        { 
			              e.printStackTrace();
			              
			        } 
			        return token;
			
	 }
	 
	 public void tryLogin(String u,String pass,String uuid){
			long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
			long st = Long.valueOf(getServerTime());
			
			long i = ts+ (st-ts);
			
			getSha(i+"YWFmOGMzNWJlNjk");
			
		 DefaultHttpClient client = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//user");
	        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
	        nvps.add(new BasicNameValuePair("method", "signIn"));
	        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
	      nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
	 
	        nvps.add(new BasicNameValuePair("email",u));
	        nvps.add(new BasicNameValuePair("password",pass));
	        nvps.add(new BasicNameValuePair("api-token",converted));
	   
	        nvps.add(new BasicNameValuePair("device-uuid",uuid));
	      
	        try {
	              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
	HTTP.UTF_8);
	              httppost.setEntity(p_entity);
	              HttpResponse response = client.execute(httppost);
	              Log.v("t", response.getStatusLine().toString());
	              
	              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
	              
	              response.getEntity().writeTo(ostream);
	          
	              Log.e("HTTP CLIENT", ostream.toString());
	              
	               us="{\"categories\":["+ostream.toString()+"]}";
		           Log.i("response",us);

		           JSONObject jArray = new JSONObject(us);
		           JSONArray  earthquakes = jArray.getJSONArray("categories");
		        	
						JSONObject e = earthquakes.getJSONObject(0);
				
						//create the xml file
	
			        	Log.i("id",e.getString("id"));
			        	if(Long.parseLong(e.getString("id"))>0){
			        		saveAuth(u,uuid,us);
			        	}

	        } catch (Exception e) 
	        { 
	              e.printStackTrace();
	     
	        } 
	       
	 }
	 
	 public void saveAuth(String u,String udid,String j){
	    	//save the token to the xml file
     	File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/user.xml");
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
             serializer.startTag(null, "user");
             serializer.startTag(null, "auth");
             serializer.startTag(null, "token");
             serializer.text(getSha(udid+u+"YWFmOGMzNWJlNjk"));
             serializer.endTag(null, "token");
             serializer.startTag(null, "status");
             serializer.text("1");
             serializer.endTag(null, "status");                      
             serializer.startTag(null, "json");
             serializer.text(j);
             serializer.endTag(null, "json"); 
             serializer.endTag(null, "auth");
 	        
 	        serializer.endTag(null, "user");
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
	 
	 
	 //get events by location
	 public String getPlayingNearMe(String lon,String lat,String radius){
			long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
			long st = Long.valueOf(getServerTime());
			
			long i = ts+ (st-ts);
			
			getSha(i+"YWFmOGMzNWJlNjk");
			
				    DefaultHttpClient client = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//event");
			        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			        nvps.add(new BasicNameValuePair("method", "getByLocation"));
			        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
			        nvps.add(new BasicNameValuePair("long",lon));
			        nvps.add(new BasicNameValuePair("lat",lat));
			        nvps.add(new BasicNameValuePair("radius",radius));

			        try {
			              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
			HTTP.UTF_8);
			              httppost.setEntity(p_entity);
			              HttpResponse response = client.execute(httppost);
			              Log.v("t", response.getStatusLine().toString());
			            
			              
			              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			              
			              response.getEntity().writeTo(ostream);
			          
			              Log.e("HTTP CLIENT", ostream.toString());
			              
			             token="{\"categories\":["+ostream.toString()+"]}";
				           Log.i("response",token);

				          
				        	
					       
			             
			        } catch (Exception e) 
			        { 
			              e.printStackTrace();
			              
			        } 
				return token;
				 
			 }
	 
	 
	 public String getPricesByEvent(String eventid){
		 long time =   getDeviceTime()+ (Long.valueOf(getServerTime())-getDeviceTime());
		 DefaultHttpClient client = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//order");
	        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
	        nvps.add(new BasicNameValuePair("method", "getPrices"));
	        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
	        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(time)));
	        nvps.add(new BasicNameValuePair("api-token",getSha(time+"YWFmOGMzNWJlNjk")));
	        nvps.add(new BasicNameValuePair("user-token",getUserToken()));
	        nvps.add(new BasicNameValuePair("event-id",eventid));
	    
	        try {
	              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
	HTTP.UTF_8);
	              httppost.setEntity(p_entity);
	              HttpResponse response = client.execute(httppost);
	              Log.v("t", response.getStatusLine().toString());

	              ByteArrayOutputStream ostream = new ByteArrayOutputStream();
	              
	              response.getEntity().writeTo(ostream);
	          
	              Log.e("HTTP CLIENT", ostream.toString());
	              
	              token="{\"categories\":"+ostream.toString()+"}";
		           Log.i("received proces",token);
		           
		           JSONObject jArray = new JSONObject(token);
		           JSONArray  earthquakes = jArray.getJSONArray("categories");
		        	
		           for(int i=0;i<earthquakes.length();i++){						
		
						JSONObject e = earthquakes.getJSONObject(i);
						
				     	
						double fVal = Double.parseDouble(e.getString("face_value"));
			        	Log.i("face_value",e.getString("face_value"));
			        	double bFee = Double.parseDouble(e.getString("booking_fee"));

			        	Log.i("booking fee",e.getString("booking_fee"));

			        	if(fVal+bFee<ticketPrice && i>0){
			        		ticketPrice = fVal+bFee;
			        		Log.i("ticket price ",String.valueOf(ticketPrice));
			        	}else{
			        		ticketPrice = fVal+bFee;
			        	}
		           }
		           
		           }catch (Exception e) 
		           	{ 
	        	//login=false;
	              e.printStackTrace();
	             Log.i("No prices found","failed");
	             
	            
	        } 
	        return String.valueOf(ticketPrice);
	 }
	 
	 public String getEventsByVenue(String id){

		    
		   
		    DefaultHttpClient client = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//event");
		    List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		    nvps.add(new BasicNameValuePair("method", "getByVenue"));
		    nvps.add(new BasicNameValuePair("venue-id", id));
		    nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
		  
		    try {
		          UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,
		HTTP.UTF_8);
		          httppost.setEntity(p_entity);
		          HttpResponse response = client.execute(httppost);
		          Log.v("t", response.getStatusLine().toString());

		          ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		          
		          response.getEntity().writeTo(ostream);
		          
		          token="{\"categories\":"+ostream.toString()+"}";
		           Log.i("response",token);
		         

		    
		        
		        
		   } catch (Exception e) 
		    { 
		          e.printStackTrace();
		          
		    } 
		   
		   return token;
		}
	 
	 public ArrayList<String> getSeatingPlanUrl(String id){
		 
		 ArrayList<String>in = new ArrayList<String>();
		 DefaultHttpClient client = new DefaultHttpClient();
		 HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//event");
	     
		 List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		 nvps.add(new BasicNameValuePair("method", "getById"));
		 nvps.add(new BasicNameValuePair("id", id));
		 nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
		 // nvps.add(new BasicNameValuePair("authtoken", "959f0622e82a87b1826929f8fe07cbdf"));
		 try {
			 UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
	         httppost.setEntity(p_entity);
	         HttpResponse response = client.execute(httppost);
	         Log.v("t", response.getStatusLine().toString());
	         // HttpEntity responseEntity = response.getEntity();
	         // Log.v(TAG, );
	          
	         ByteArrayOutputStream ostream = new ByteArrayOutputStream();
	          
	         response.getEntity().writeTo(ostream);
	      
	         // Log.e("HTTP CLIENT", ostream.toString());
	          
	         String s="{\"categories\":["+ostream.toString()+"]}";
             Log.i("response",s);
		          
		     JSONObject jArray = new JSONObject(s);
	         JSONArray  earthquakes = jArray.getJSONArray("categories");
		        	
		     for(int i=0;i<earthquakes.length();i++){						
		    	 //HashMap<String, String> map = new HashMap<String, String>();	
				 JSONObject e = earthquakes.getJSONObject(i);
						
				 String info="{\"Info\":["+e.getString("Info")+"]}";
				 Log.i("important info",info);
				 
				 JSONObject infoOBJ = new JSONObject(info);
		         JSONArray  infoArray = infoOBJ.getJSONArray("Info");
		         JSONObject ee = infoArray.getJSONObject(0);
		         String desc = ee.getString("description");
		         Log.i("Description", desc);
				     	
				 token = e.getString("seating_plan_path");
				 in.add(desc);
				 in.add(e.getString("seating_plan_path"));
	           }
		     
		   } catch (Exception e) { 
			   e.printStackTrace();
		   } 
		   return in;
	 }
	 
	 public String getDataCapture(String id) {
			// TODO Auto-generated method stub
			long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
			long st = Long.valueOf(getServerTime());
			
			long i = ts+ (st-ts);
			
			getSha(i+"YWFmOGMzNWJlNjk");
			DefaultHttpClient client = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//order");
	        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
	        nvps.add(new BasicNameValuePair("method", "getDataCapture"));
	        nvps.add(new BasicNameValuePair("user-token",getUserToken()));
	        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
	        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
	        nvps.add(new BasicNameValuePair("api-token",converted));
	        nvps.add(new BasicNameValuePair("event-id",id));
	       // nvps.add(new BasicNameValuePair("authtoken", "959f0622e82a87b1826929f8fe07cbdf"));
	        try {
	        	UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
                httppost.setEntity(p_entity);
                HttpResponse response = client.execute(httppost);
                Log.v("t", response.getStatusLine().toString());
                // HttpEntity responseEntity = response.getEntity();
                // Log.v(TAG, );
              
                ByteArrayOutputStream ostream = new ByteArrayOutputStream();
                response.getEntity().writeTo(ostream);
                Log.e("HTTP CLIENT", ostream.toString());
                token="{\"categories\":["+ostream.toString()+"]}";
	            Log.i("Data Capture",token);
            } catch (Exception e) { 
            	e.printStackTrace();
        	} 
	        return token;
	}
	 
	 public String getPostcodes(String id) {
		 // TODO Auto-generated method stub
		
		 long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
		 long st = Long.valueOf(getServerTime());
		
		 long i = ts+ (st-ts);
		
		 getSha(i+"YWFmOGMzNWJlNjk");
		
	    DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//order");
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("method", "getPostcodes"));
        nvps.add(new BasicNameValuePair("user-token",getUserToken()));
        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
        nvps.add(new BasicNameValuePair("api-token",converted));
        nvps.add(new BasicNameValuePair("event-id",id));
       // nvps.add(new BasicNameValuePair("authtoken", "959f0622e82a87b1826929f8fe07cbdf"));
        try {
        	UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
            httppost.setEntity(p_entity);
            HttpResponse response = client.execute(httppost);
            Log.v("t", response.getStatusLine().toString());
            // HttpEntity responseEntity = response.getEntity();
            // Log.v(TAG, );
          
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            response.getEntity().writeTo(ostream);
            Log.e("HTTP CLIENT", ostream.toString());   
            //token="{\"categories\":"+ostream.toString()+"}";
            token=ostream.toString();
	        Log.i("response",token);
        } catch (Exception e) { 
        	e.printStackTrace();
    	} 
		return token;
	}
	 
	 public String getPaymentPlan(String id) {
		 // TODO Auto-generated method stub
			
		 long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
		 long st = Long.valueOf(getServerTime());
			
		 long i = ts+ (st-ts);
			
	     getSha(i+"YWFmOGMzNWJlNjk");
	     
	     DefaultHttpClient client = new DefaultHttpClient();
         HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//order");
         List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
         nvps.add(new BasicNameValuePair("method", "getPaymentPlan"));
         nvps.add(new BasicNameValuePair("user-token",getUserToken()));
         nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
         nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
         nvps.add(new BasicNameValuePair("api-token",converted));
         nvps.add(new BasicNameValuePair("event-id",id));
         // nvps.add(new BasicNameValuePair("authtoken", "959f0622e82a87b1826929f8fe07cbdf"));
       
         try {
        	 UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,HTTP.UTF_8);
             httppost.setEntity(p_entity);
             HttpResponse response = client.execute(httppost);
             Log.v("t", response.getStatusLine().toString());
             // HttpEntity responseEntity = response.getEntity();
             // Log.v(TAG, );
          
             ByteArrayOutputStream ostream = new ByteArrayOutputStream();
             response.getEntity().writeTo(ostream);
             Log.e("HTTP CLIENT", ostream.toString());
             //token="{\"categories\":"+ostream.toString()+"}";
             token=ostream.toString();
             Log.i("response",token);
         } catch (Exception e){ 
        	 e.printStackTrace();
    	 } 
		 return token;
	}
	 
	 
	 public String getEventDetails(String id){
		 
		 DefaultHttpClient client = new DefaultHttpClient();
	     HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//event");
	     List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
	     nvps.add(new BasicNameValuePair("method", "getById"));
	     nvps.add(new BasicNameValuePair("id", id));
	     nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
	     // nvps.add(new BasicNameValuePair("authtoken", "959f0622e82a87b1826929f8fe07cbdf"));
	     
	     try {
	    	 UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
             httppost.setEntity(p_entity);
             HttpResponse response = client.execute(httppost);
             Log.v("t", response.getStatusLine().toString());
             // HttpEntity responseEntity = response.getEntity();
             // Log.v(TAG, );
	          
	         ByteArrayOutputStream ostream = new ByteArrayOutputStream();
	          
	         response.getEntity().writeTo(ostream);
	      
	         // Log.e("HTTP CLIENT", ostream.toString());
	          
	         token="{\"categories\":["+ostream.toString()+"]}";
	         Log.i("Event details",token);
	         
	     } catch (Exception e) { 
	    	 e.printStackTrace();
    	 } 
	     return token;
	 }
	 
	 public String getOnSaleToday(String from,String to){
		 
		 DefaultHttpClient client = new DefaultHttpClient();
	     HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//event");
	     List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
	     nvps.add(new BasicNameValuePair("method", "getByOnSaleDate"));
	     nvps.add(new BasicNameValuePair("date-from", from));
	     nvps.add(new BasicNameValuePair("date-to", to));
	     nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));

	     try {
	    	 UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
	         httppost.setEntity(p_entity);
	         HttpResponse response = client.execute(httppost);
	         Log.v("t", response.getStatusLine().toString());
	         // HttpEntity responseEntity = response.getEntity();
	         // Log.v(TAG, );
	          
	         ByteArrayOutputStream ostream = new ByteArrayOutputStream();
	         response.getEntity().writeTo(ostream);
	         Log.e("HTTP CLIENT", ostream.toString());
	         String s="{\"categories\":"+ostream.toString()+"}";
		     Log.i("response",s);
		     token = s;
	     } catch (Exception e) { 
	    	 e.printStackTrace();
	     } 
	     return token;
	 }
	 
	 public String getMoreInfo(String type){
		 
		 DefaultHttpClient client = new DefaultHttpClient();
		 HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//info");
		
		 List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		 nvps.add(new BasicNameValuePair("method", "getStaticInfo"));
		 nvps.add(new BasicNameValuePair("type", type));
		 nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
		 
		 try {
			 UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
             httppost.setEntity(p_entity);
             HttpResponse response = client.execute(httppost);
             Log.v("t", response.getStatusLine().toString());

             ByteArrayOutputStream ostream = new ByteArrayOutputStream();
             response.getEntity().writeTo(ostream);
      
             String s="{\"categories\":["+ostream.toString()+"]}";
             Log.i("response",s);
		     token = s;
		    
	     } catch (Exception e)   { 
	    	 e.printStackTrace();   
		 } 
		 return token;
	 }
	 
	 
	 public String getUserPaymentInfo() {
		 // TODO Auto-generated method stub
		 long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
		 long st = Long.valueOf(getServerTime());
			
	 	 long i = ts+ (st-ts);
			
		 getSha(i+"YWFmOGMzNWJlNjk");
			
	     DefaultHttpClient client = new DefaultHttpClient();
         HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//user");
         List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
         nvps.add(new BasicNameValuePair("method", "getPaymentCards"));
         nvps.add(new BasicNameValuePair("user-token",getUserToken()));
         nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
         nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
         nvps.add(new BasicNameValuePair("api-token",converted));

         try {
        	 UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
        	 httppost.setEntity(p_entity);
        	 HttpResponse response = client.execute(httppost);
        	 Log.v("t", response.getStatusLine().toString());

        	 ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        	 response.getEntity().writeTo(ostream);
        	 Log.e("HTTP CLIENT", ostream.toString());
        	 token="{\"categories\":"+ostream.toString()+"}";
        	 Log.i("response",token);
    	 } catch (Exception e){
    		 e.printStackTrace();  
		 } 
         return token;
     }
	 
	 public String getOrderHistory() {
		 
		 // TODO Auto-generated method stub
		 long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
		 long st = Long.valueOf(getServerTime());
		 long i = ts+ (st-ts);
		 
		 getSha(i+"YWFmOGMzNWJlNjk");
		 DefaultHttpClient client = new DefaultHttpClient();
         HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//user");
         List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
         nvps.add(new BasicNameValuePair("method", "getOrderHistory"));
         nvps.add(new BasicNameValuePair("user-token",getUserToken()));
         nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
         nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
         nvps.add(new BasicNameValuePair("api-token",converted));

         try {
        	 UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
             httppost.setEntity(p_entity);
             HttpResponse response = client.execute(httppost);
             Log.v("t", response.getStatusLine().toString());

              
             ByteArrayOutputStream ostream = new ByteArrayOutputStream();
             response.getEntity().writeTo(ostream);
             Log.e("HTTP CLIENT", ostream.toString());
             token="{\"categories\":"+ostream.toString()+"}";
             Log.i("response",token);
         } catch (Exception e)  { 
        	 e.printStackTrace();
    	 } 
         return token;
	}
	 
	 public String getOrderHistoryDetails(String id) {
		 
		 // TODO Auto-generated method stub
		long ts = convertDateToMillifolksFonts(new Date(System.currentTimeMillis()));
		long st = Long.valueOf(getServerTime());
			
		long i = ts+ (st-ts);
			
		getSha(i+"YWFmOGMzNWJlNjk");
			
	    DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//user");
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("method", "getOrderDetails"));
        nvps.add(new BasicNameValuePair("user-token",getUserToken()));
        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(i)));
        nvps.add(new BasicNameValuePair("api-token",converted));
        nvps.add(new BasicNameValuePair("order-id",id));
       // nvps.add(new BasicNameValuePair("authtoken", "959f0622e82a87b1826929f8fe07cbdf"));
        try {
        	UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
            httppost.setEntity(p_entity);
            HttpResponse response = client.execute(httppost);
            Log.v("t", response.getStatusLine().toString());
            // HttpEntity responseEntity = response.getEntity();
            // Log.v(TAG, );
            
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            response.getEntity().writeTo(ostream);
            Log.e("HTTP CLIENT", ostream.toString());
            
            //  token=ostream.toString();
            Log.i("response",token);
            int responseCode = response.getStatusLine().getStatusCode();
            token="{\"categories\":["+ostream.toString()+"],\"status\":"+responseCode+"}";
        } catch (Exception e) { 
        	e.printStackTrace();
    	} 
        return token;
	 }
	 
	 public String createShortDate(String s){
		 
		 if(s!=null){
			 ArrayList<String>vals1 = new ArrayList<String>();
			 StringTokenizer st = new StringTokenizer(s, "-");
			 
			 while (st.hasMoreTokens()) {
				 vals1.add(st.nextToken());
			 }
		 
			 return vals1.get(2)+"-"+vals1.get(1)+"-"+vals1.get(0);
		
		 }else{
			 
			 return "Not Found";
		 }
	 }
	 
	 public String createHeaderDate(String s) {
		
		 ArrayList<Integer>vals = new ArrayList<Integer>();
		 StringTokenizer st = new StringTokenizer(s, "-");
			
		 while (st.hasMoreTokens()) {
			 vals.add(Integer.parseInt(st.nextToken()));
		 }
			
		 Calendar cal = Calendar.getInstance();
		 int year = vals.get(0);
		 int month = vals.get(1);
		 String mon = getMonth(month);
		 int day = vals.get(2);
		 cal.set(year, month, day);
		 int day_of = cal.get(Calendar.DAY_OF_WEEK);
		 String name = null;
		 switch(day_of){
		 case 1:
			name = "Thu"; 
			 break;
		 case 2:
			 name = "Fri";
			 break;
			 
		 case 3:
			 name = "Sat";
			 break;
			 
		 case 4:
			 name ="Sun";
			 break;
			 
		 case 5:
			 name = "Mon";
			 break;
		
		 case 6:
			 name = "Tue";
			 break;
			 
		 case 7:
			 name =  "Wed";
			 break;
		 default:
			 break;
		 }
		 vals.clear();
		 return name+" "+String.valueOf(day)+" "+ mon+ " "+String.valueOf(year);
	 }
	 
	 
	 
	 public String createDate(String s,String Time) {
		 
		 ArrayList<Integer>vals = new ArrayList<Integer>();
		 ArrayList<Integer>times = new ArrayList<Integer>();
	     StringTokenizer st = new StringTokenizer(s, "-");
			
	     while (st.hasMoreTokens()) {
			vals.add(Integer.parseInt(st.nextToken()));
		 }
	     
	     StringTokenizer t = new StringTokenizer(Time, ":");
		 while (t.hasMoreTokens()) {
			 times.add(Integer.parseInt(t.nextToken()));
		 }
				
		 String hours = String.valueOf(times.get(0));
		 int tval = times.get(1);
		 String mi = null;
		 if (tval==0){ 
			 mi = "00";
		 }else{ 
			 mi = String.valueOf(times.get(1));
		 }
			
			
		 Calendar cal = Calendar.getInstance();
		 int year = vals.get(0);
		 int month = vals.get(1);
		 String mon = getMonth(month);
		 int day = vals.get(2);
		 cal.set(year, month, day);
		 int day_of = cal.get(Calendar.DAY_OF_WEEK);
		 String name = null;
		
		 switch(day_of){
		 case 1:
			name = "Thu"; 
			 break;
		
		 case 2:
			 name = "Fri";
			 break;
			 
		 case 3:
			 name = "Sat";
			 break;
			 
		 case 4:
			 name ="Sun";
			 break;
			 
		 case 5:
			 name = "Mon";
			 break;
		
		 case 6:
			 name = "Tue";
			 break;
		
		 case 7:
			 name =  "Wed";
			 break;
			 
		 default:
			 break;
		 }
		 
		 vals.clear();
		 times.clear();
		 return name+" "+String.valueOf(day)+" "+ mon+ " "+String.valueOf(year)+" At"+" "+hours+":"+ mi;
	 }

		
	public String getMonth(int m){
	
		String name = null;
		
		switch(m){
		
		case 1:
			name = "Jan"; 
			 break;
		
		case 2:
			 name = "Feb";
			 break;
			 
		 case 3:
			 name = "Mar";
			 break;
			 
		 case 4:
			 name ="Apr";
			 break;
			 
		 case 5:
			 name = "May";
			 break;
		 
		 case 6:
			 name = "Jun";
			 break;
		 
		 case 7:
			 name =  "Jul";
			 break;
		 
		 case 8: 
			 name = "Aug";
			 break;
		
		 case 9:
			 name = "Sep";
			 break;
		 
		 case 10:
			 name =  "Oct";
			 break;
		
		 case 11:
			 name = "Nov";
			 break;
		
		 case 12:
			 name =  "Dec";
			 break;
			 
		 default:
			 break;
		 }
		return name;
		
	}	
	
	 public String Search(String id){
		 
		 DefaultHttpClient client = new DefaultHttpClient();
		 HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//artist");
	     List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
	     nvps.add(new BasicNameValuePair("method", "search"));
	     nvps.add(new BasicNameValuePair("query", id));
	     nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
	     // nvps.add(new BasicNameValuePair("authtoken", "959f0622e82a87b1826929f8fe07cbdf"));
	   
	     try {
	    	 
	    	 UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
		     httppost.setEntity(p_entity);
		     HttpResponse response = client.execute(httppost);
		     Log.v("t", response.getStatusLine().toString());
		      
		     // HttpEntity responseEntity = response.getEntity();
		     // Log.v(TAG, );
		     
		     ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		     response.getEntity().writeTo(ostream);
		      
		     // Log.e("HTTP CLIENT", ostream.toString());
		     token="{\"categories\":"+ostream.toString()+"}";
		     Log.i("response",token);
	     } catch (Exception e) { 
	    	 e.printStackTrace();
    	 } 
	     return token;
	 }
		
}