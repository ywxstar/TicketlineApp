package com.ywx.ticketlinewebapp;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.sql.Date;
import java.util.ArrayList;
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
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccount extends Activity{
	
	String fname;
	String lname;
	String gender;
	String email;
	String password;
	String passwordc;
	String day;
	String month;
	String year;
	String add1;
	String add2;
	String city;
	String post;
	String country;
	String news;
	String phone;
	String deviceId;;
	String converted;
	String titleString;
	long ts;
	
	TextView title;
	TextView detailsTitle;
	TextView emailView;
	TextView passView;
	TextView passconView;
	TextView billingTitle;
	TextView titleView;
	TextView firstName;
	TextView folksFontName;
	TextView add1View;
	TextView add2View;
	TextView cityView;
	TextView postcodeView;
	TextView countryView;
	TextView phoneView;
	
	EditText txtFirst;
	EditText txtLast;
	EditText txtEmail;
	EditText txtPassword;
	EditText txtPasswordc;
	EditText txtDay;
	EditText txtMonth;
	EditText txtYear;
	EditText txtAdd1;
	EditText txtAdd2;
	EditText txtCity;
	EditText txtPost;
	EditText txtCountry;
	EditText txtPhone;
	Spinner lstTitle;
	Typeface arialFont;
	
	Dialog myDialog;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	       
		setContentView(R.layout.copy_create_account);
	    deviceId = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
	        
	    arialFont = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
	    
	    // Text Views
        title = (TextView)findViewById(R.id.textView3);
        title.setTypeface(arialFont);
        detailsTitle = (TextView)findViewById(R.id.TextView28);
        detailsTitle.setTypeface(arialFont);
   	    emailView = (TextView)findViewById(R.id.TextView18); 
   	    emailView.setTypeface(arialFont);
   	    passView = (TextView)findViewById(R.id.TextView16);
   	    emailView.setTypeface(arialFont);
   	    passconView = (TextView)findViewById(R.id.TextView19);
   	    passconView.setTypeface(arialFont);
   	    billingTitle = (TextView)findViewById(R.id.TextView29);
   	    billingTitle.setTypeface(arialFont);
   	    titleView = (TextView)findViewById(R.id.TextView23);
   	    titleView.setTypeface(arialFont);
   	    firstName = (TextView)findViewById(R.id.TextView21);
   	    firstName.setTypeface(arialFont);
	 	folksFontName = (TextView)findViewById(R.id.TextView22);
	    folksFontName.setTypeface(arialFont);
  	    add1View = (TextView)findViewById(R.id.TextView20);
   	    add1View.setTypeface(arialFont);
   	    add2View = (TextView)findViewById(R.id.TextView15);
   	    add2View.setTypeface(arialFont);
   	    cityView = (TextView)findViewById(R.id.TextView24);
   	    cityView.setTypeface(arialFont);
   	    postcodeView = (TextView)findViewById(R.id.TextView25);
   	    postcodeView.setTypeface(arialFont);
   	    countryView = (TextView)findViewById(R.id.TextView26);
   	    countryView.setTypeface(arialFont);
   	    phoneView = (TextView)findViewById(R.id.TextView27);
   	    phoneView.setTypeface(arialFont);
   	   
   	    // Edit text
        txtFirst = (EditText)findViewById(R.id.txtFname);
        txtFirst.setTypeface(arialFont);

        txtLast = (EditText)findViewById(R.id.txtLname);
        txtLast.setTypeface(arialFont);

        txtEmail = (EditText)findViewById(R.id.txtEmail);
	    txtEmail.setTypeface(arialFont);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtPassword.setTypeface(arialFont);

	    txtPassword.setTransformationMethod(new PasswordTransformationMethod());
	    txtPasswordc = (EditText)findViewById(R.id.txtPasswordC);
	    txtPasswordc.setTypeface(arialFont);

	    txtPasswordc.setTransformationMethod(new PasswordTransformationMethod());
	    txtDay = (EditText)findViewById(R.id.txtDay);
	    txtDay.setTypeface(arialFont);
	    txtMonth = (EditText)findViewById(R.id.txtMonth);
	    txtMonth.setTypeface(arialFont);
	    txtYear = (EditText)findViewById(R.id.txtYear);
	    txtYear.setTypeface(arialFont);
	    txtAdd1 = (EditText)findViewById(R.id.txtAdd1);
	    txtAdd1.setTypeface(arialFont);

	    txtAdd2 = (EditText)findViewById(R.id.txtAdd2);
	    txtAdd2.setTypeface(arialFont);
	    txtCity =  (EditText)findViewById(R.id.txtCity);
	    txtCity.setTypeface(arialFont);

        txtPost = (EditText)findViewById(R.id.txtPostCode);
        txtPost.setTypeface(arialFont);
 
        txtCountry = (EditText)findViewById(R.id.txtCountry);
        txtCountry.setTypeface(arialFont);
        txtPhone = (EditText)findViewById(R.id.txtPhone); 
        txtPhone.setTypeface(arialFont);
       
        lstTitle = (Spinner)findViewById(R.id.lstTitle);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.title_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        		
        		((TextView)lstTitle.getChildAt(0)).setTextColor(Color.rgb(0, 178, 255));
        		titleString = (String)parent.getItemAtPosition(pos);
    	   	}
        	public void onNothingSelected(AdapterView<?> parent){
        		
        		titleString = (String)parent.getItemAtPosition(0);
    	   	}
    	});
        lstTitle.setAdapter(adapter);
	 }
	
	 public void Create(View button) {
		 
		 Functions f = new Functions();
		 ts = convertDateToMilliSeconds(new Date(System.currentTimeMillis()));
		 
		 long st = Long.valueOf(f.getServerTime());
		 ts = ts+ (st-ts);
		 
		 getSha(String.valueOf(ts)+"YWFmOGMzNWJlNjk");
		 
		 fname = txtFirst.getText().toString();
		 lname = txtLast.getText().toString();
		 email = txtEmail.getText().toString();
		 password = txtPassword.getText().toString();
		 day = txtDay.getText().toString();
		 month = txtMonth.getText().toString();
		 year = txtYear.getText().toString();
		 add1 = txtAdd1.getText().toString();
		 add2 = txtAdd2.getText().toString();
		 city = txtCity.getText().toString();
		 post = txtPost.getText().toString();
		   
		 if(!txtEmail.getText().toString().contains("@")){
			 showAlert("'email' is required");
		 }
		 
		 if(txtEmail.getText().toString().length() < 6) {
			 showAlert("email is invalid");
		 }

		 if(txtPassword.length() < 8) {
			 showAlert("Password too short");
			 return;
		 }
		 
		 if(txtPasswordc.getText().length() < 1){
			 showAlert("'Confirm Password' is required");
			 return;
		 }else if(!txtPassword.getText().toString().equals(txtPasswordc.getText().toString())) {
			 
			 showAlert("Passwords do not match");
			 return;
		  }
		  
		  if(txtFirst.getText().toString().length() < 1) {
			 
			  showAlert("'first-name' is required");
			  return;
		  }
		  
		  if(txtLast.getText().toString().length() < 1) {
			  showAlert("'last-name' is required");
			  return;
		  }
		  
		  if(txtAdd1.getText().toString().length() < 1) {
			  showAlert("'address1' is required");
			  return;
		  }
		  
		  if(txtCountry.getText().toString().length() < 1) {
			  showAlert("'Country' is required");
			  return;
		  }
		  
		  if(txtPost.getText().toString().length() < 3) {
			  showAlert("Postcode too short");
			  return;
		  }
		
		   DefaultHttpClient client = new DefaultHttpClient();
	       HttpPost httppost = new HttpPost("http://api.ticketline.co.uk//user");
	       List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
	       nvps.add(new BasicNameValuePair("method", "create"));
	       nvps.add(new BasicNameValuePair("email",email ));
	       nvps.add(new BasicNameValuePair("password",password ));
	       nvps.add(new BasicNameValuePair("api-token",converted ));
	       nvps.add(new BasicNameValuePair("first-name",fname));
	       nvps.add(new BasicNameValuePair("last-name",lname ));
           nvps.add(new BasicNameValuePair("device-uuid",deviceId));
	       nvps.add(new BasicNameValuePair("address1",add1 ));
	       nvps.add(new BasicNameValuePair("address2",add2 ));
	       nvps.add(new BasicNameValuePair("city",city ));
	       nvps.add(new BasicNameValuePair("postal-code",post ));
	       nvps.add(new BasicNameValuePair("country-id","66009" ));
	       nvps.add(new BasicNameValuePair("phone",phone ));
	       nvps.add(new BasicNameValuePair("timestamp",String.valueOf(ts) )); 
	       nvps.add(new BasicNameValuePair("api-key", "NGNkZGRhYjkzY2Z"));
	       
	       try {
	              UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(nvps,HTTP.UTF_8);
	              httppost.setEntity(p_entity);
	              HttpResponse response = client.execute(httppost);
	              Log.v("t", response.getStatusLine().toString());
	              ByteArrayOutputStream ostream = new ByteArrayOutputStream(); 
	              response.getEntity().writeTo(ostream);

	              Log.e("HTTP CLIENT", ostream.toString());
	              
	              int responseCode = response.getStatusLine().getStatusCode();
	              String suc =  ostream.toString();
	              String s="{\"categories\":["+ostream.toString()+"]}";
		          Log.i("response",s);	              
	              switch(responseCode) {
	                  case 200:
							showAlert(suc);
							 Intent AR = new Intent(getApplicationContext(),TicketlineActivity.class);
							 AR.putExtra("TAB", "1");
							           startActivity(AR);
							           finish();	                  
	            	  break;
	                  case 400:
	    		          JSONObject jArray = new JSONObject(s);
	    		          JSONArray  earthquakes = jArray.getJSONArray("categories");
	    		        	
	    			      for(int i=0;i<earthquakes.length();i++){					
	    						String e = earthquakes.getString(i);
	    						
	    						if(e.contains("successfully")){

	    						} else {
	    							showAlert("signup failed");
	    						}
	    					}	                	  
	                  break;
	              }
	              
	              ////////////////////////////////////////////////////////////////////////


		    
	        	} catch (Exception e) { 
	        			e.printStackTrace();     
	        		} 
	 }
	 
	 

	public static long convertDateToMilliSeconds(Date date) {
		     
		 return date.getTime();
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
		 }   catch (Exception e) {
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
	 
	 public void cancel(View button){
		 Intent AR = new Intent(getApplicationContext(),TicketlineActivity.class);
		 AR.putExtra("TAB", "1");
		           startActivity(AR);
		           finish();
	 }
	 
	 private void showAlert(String message) {
			
		 	String alertText = "";
		 
		 	if(message.contains("'email' is required")) {
		 		alertText = "Email address is a required field, please give a suitable email address";
		 	} 
		 	
		 	if(message.contains("email is invalid")) {
		 		alertText = "Sorry, your email is invalid, please try again";
		 	}
		 			
		 	else if (message.contains("already exists")) {
		 		alertText = "Your Email is already in use, please login or use another email";
		 	}
		 	else if (message.contains("Passwords do not match")) {
		 		alertText = "Your password and password confirmation do not match, please try again";
		 	}
		 	else if (message.contains("Password too short")) {
		 		alertText = "Your password is too short, you must use at least 8 letters, numbers or special characters";
		 	}
		 	else if (message.contains("'first-name' is required")) {
		 		alertText = "Sorry, your First Name is invalid, please try again";
		 	}
		 	else if (message.contains("'last-name' is required")) {
		 		alertText = "Sorry, your Last Name is invalid, please try again";
		 	}
		 	else if (message.contains("'address1' is required")) {
		 		alertText = "Sorry, the first line of your address is invalid, please try again";
		 	}
		 	else if (message.contains("'Country' is required")) {
		 		alertText = "Sorry, the country field is invalid, please try again";
		 	}
		 	else if (message.contains("'postal-code' is required")) {
		 		alertText = "Sorry, the post-code field is invalid, please try again";
		 	}
		 	else if (message.contains("Postcode too short")) {
		 		alertText = "Sorry, the post-code you input is too short, please try again";
		 	}
		 	else if(message.contains("successfully")) {
		 		alertText = "Signup successful";
		 	}
		 	else if(message.contains("signup failed")) {
		 		alertText = "Sorry, we couldn't create an account at the moment, please try again later";
		 	}
		 	else if(message.contains("'Confirm Password' is required")) {
		 		alertText = "Sorry, your confirm password is invalid, please try again";
		 	}
		 	
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.toast_layout,
			                               (ViewGroup) findViewById(R.id.toast_layout_root));

			TextView text = (TextView) layout.findViewById(R.id.text);
			text.setText(alertText);
			text.setTypeface(arialFont);
			text.setTextColor(Color.parseColor("#00B2FF"));

			Toast toast = new Toast(getApplicationContext());
			toast.setGravity(Gravity.NO_GRAVITY, 0, 50);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setView(layout);
			toast.show();
		}
	  
}
