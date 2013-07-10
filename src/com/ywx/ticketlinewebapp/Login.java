package com.ywx.ticketlinewebapp;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

	
public class Login extends Activity  {
	
	
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	long ts;
	
	String converted;
	String deviceId;
	
	String us;
	User user;
	Functions f;
	
	String username;
	//String j;
	EditText pass;
	EditText userna;
	
	Button yourDetails;
	Button payment;
	Button billing;
	Button history;
	
	Typeface arialFont;
	
	Dialog myDialog;		
	 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        f = new Functions();
        setContentView(R.layout.login);
       
        System.out.println("Date to MilliSeconds: " + convertDateToMilliSeconds(new Date(System.currentTimeMillis())));
       
        deviceId = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        Log.i("dev id",deviceId);
        arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
        
   	 	pass = (EditText)findViewById(R.id.txtPassword);
   	 	userna = (EditText) findViewById(R.id.txtUserName);
   	 	
   	 	yourDetails = (Button)findViewById(R.id.Button01);
   	 	payment = (Button)findViewById(R.id.Button02);
   	 	billing = (Button)findViewById(R.id.Button03);
   	 	history = (Button)findViewById(R.id.btnHistory);
 	  
   	 	/*
   	 	 * Set the font for your widget
   	 	 */
   	 	yourDetails.setTypeface(arialFont);
   	 	payment.setTypeface(arialFont);
   	 	billing.setTypeface(arialFont);
   	 	history.setTypeface(arialFont);
   	 	pass.setTypeface(arialFont);
   	 	userna.setTypeface(arialFont);
    }

	
	public static long convertDateToMilliSeconds(Date date) {
	     
		return date.getTime();
	}
	
    public static String now() {
      
    	Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }
	
    ///////////////////////////////////////////////////////////////
    public void Profile(View button){
		
    	showUserInvalidAlert();
	}
    
	public void Messages(View button){
		
		showUserInvalidAlert();
	}
	
	public void Payment(View button){
		
		showUserInvalidAlert();
	}
	
	public void History(View button){
		
		showUserInvalidAlert();
	}
	
	/*
	 * 显示未来登录警告
	 */
	private void showUserInvalidAlert() {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout,
		                               (ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText("Please login to your account.");
		text.setTypeface(arialFont);
		text.setTextColor(Color.parseColor("#00B2FF"));

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.NO_GRAVITY, 0, 50);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
	
	/*
	 * 显示授权警告
	 */
	private void showAuthAlert() {
		
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout,
		                               (ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText("Login failed. Please check your email and password.");
		text.setTypeface(arialFont);
		text.setTextColor(Color.parseColor("#00B2FF"));

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.NO_GRAVITY, 0, 50);

	
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

	//////////////////////////////////////////////////////
	/*
	 * log in by your user
	 */
	public void LoginMe(View button){
		
		System.out.println("Date to MillifolksFonts: " + convertDateToMilliSeconds(new Date(System.currentTimeMillis())));
		ts = convertDateToMilliSeconds(new Date(System.currentTimeMillis()));
		Log.i("Device token",deviceId);
		
		// DAZ 19/01/13: Added replaceAll functions to username and password to ignore spaces
		 
		username = userna.getText().toString().replaceAll(" ","");
		String password = pass.getText().toString().replaceAll(" ","");
		
		//get the srver time difference
		long st = Long.valueOf( f.getServerTime());
		
		ts = ts+ (st-ts);
		
		getSha(ts + "YWFmOGMzNWJlNjk");
		tryLogin(username, password, converted);
		
		//showLoadDialog("Loading...");
		
	}
	
	private void getSha(String in) {
		
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
        }catch (Exception e) {
        	System.out.println("Exception: "+e);
		}
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
	
	public void tryLogin(String u,String pass,String s){
		 
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
  		imm.hideSoftInputFromWindow(this.pass.getWindowToken(), 0);
  		
		DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//user");

        
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("method", "signIn"));
        nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
        nvps.add(new BasicNameValuePair("timestamp",String.valueOf(ts)));
        nvps.add(new BasicNameValuePair("email",u));
        nvps.add(new BasicNameValuePair("password",pass));
        nvps.add(new BasicNameValuePair("api-token",s));
        nvps.add(new BasicNameValuePair("device-uuid",deviceId));
        
		Log.e("Username",u);
		Log.e("Password",pass);
      
		try { 
        	UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,HTTP.UTF_8);
            httppost.setEntity(p_entity);
            Log.v("t", nvps.toString());	              
            HttpResponse response = client.execute(httppost);
          
          
            Log.v("t", response.getStatusLine().toString());
            ByteArrayOutputStream ostream = new ByteArrayOutputStream(); 
            response.getEntity().writeTo(ostream);
      
            Log.e("HTTP CLIENT", ostream.toString());
            us = "{\"categories\":["+ostream.toString()+"]}";
            Log.i("response",us);

            JSONObject jArray = new JSONObject(us);
            JSONArray  earthquakes = jArray.getJSONArray("categories");
		        	
			JSONObject e = earthquakes.getJSONObject(0);
			Log.i("Login handle",e.toString());
			saveAuth(us);
        	
		    Log.i("id",e.getString("id"));
        	Log.i("email",e.getString("email_address"));
        	Log.i("title",e.getString("title"));
        	Log.i("first name",e.getString("first_name"));
        	Log.i("last name",e.getString("last_name"));
        	Log.i("address 1",e.getString("address1"));
        	Log.i("address 2",e.getString("address2"));
        	Log.i("city",e.getString("city"));
        	Log.i("post",e.getString("postal_code"));
        	Log.i("country",e.getString("country"));
        	Log.i("phone",e.getString("phone"));		 		

        	Intent PR = new Intent(getParent(), UserProfile.class);
			PR.putExtra("username",e.getString("email_address") );
			PR.putExtra("fname",e.getString("first_name") );
			PR.putExtra("lname",e.getString("last_name") );
			PR.putExtra("add1",e.getString("address1") );
			PR.putExtra("add2",e.getString("address2") );
			PR.putExtra("city",e.getString("city") );
			PR.putExtra("post",e.getString("postal_code") );
			PR.putExtra("country",e.getString("country") );
			PR.putExtra("phone",e.getString("phone") );
			PR.putExtra("json",us );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("UserProfileActivity", PR);
	 		 
    	} catch (Exception e) {
    		
    		e.printStackTrace();
            File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/user.xml");
  	        newxmlfile.delete();
  	        showAuthAlert();
        } 
    }
	 
	//////////////////////////////////////////////////
	
	
	/*public void grabURL(String url) {
		
		new GrabURL().execute(url);
	}
	    
	private class GrabURL extends AsyncTask<String, Void, Void> {
		
		protected void onPreExecute() { 
			
		}
		
		protected Void doInBackground(String... urls) {
			Log.e("order",getIntent().getStringExtra("order-id")); 
				
			return null;    
	    }
		
		protected void onPostExecute(Void unused) {
	    }
	}	*/
	  
	 public void saveAuth(String json){
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
             serializer.text(f.getSha(deviceId + username + "YWFmOGMzNWJlNjk"));
             serializer.endTag(null, "token");
             serializer.startTag(null, "status");
             serializer.text("1");
             serializer.endTag(null, "status");                      
             serializer.startTag(null, "json");
             serializer.text(json);
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
	 
	 /*
	  * create a account for you.
	  */
	 public void createAccount(View button){

	 		Intent AR = new Intent(getApplicationContext(),  CreateAccount.class); 
	        startActivity(AR);
	 }
	 
	////////////////////////////////////////////////////////////
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
}
