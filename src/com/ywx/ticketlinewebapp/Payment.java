package com.ywx.ticketlinewebapp;

import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class Payment extends Activity {
	
	Functions f;
	TextView arName,venName,date;
	ImageView seat;
	String eventid,total,imageURL;
	ArrayList<String>stepList = new ArrayList<String>();
	ArrayList<String>vals = new ArrayList<String>();
	Drawable drawable;
	String response;
	Dialog myDialog;
	int minutes;
	int folksFonts;
	int delay1,period1;
	Timer timer;
	TextView txtTimer;
	private Handler mHandler1 = new Handler();
	ArrayList<String>info = new ArrayList<String>();
	String important;
	int saved;
	String error;
	  
	 ArrayList<String>name = new ArrayList<String>();
		ArrayList<String>type = new ArrayList<String>();
		ArrayList<String>token = new ArrayList<String>();
		ArrayList<String>last = new ArrayList<String>();
		ArrayList<String>expire = new ArrayList<String>();
		ArrayList<String>image = new ArrayList<String>();
		
		ArrayList<String>details = new ArrayList<String>();
		ArrayList<String>price = new ArrayList<String>();
		String selToken;
		Resources resource;
		Typeface first,folksFont;
		PaymentAdapter adapterPay;
		ListView lvPay,lstNew;
		String sec;
		 int termVal;
		 int cardType;
		 DecimalFormat df = new DecimalFormat("#########0.00"); 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        f = new Functions();
        saved = 0;
    	resource = this.getResources();
      	 first = Typeface.createFromAsset(this.getAssets(),"Arial.ttf");
      	 folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
      	 folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
        arName = (TextView)findViewById(R.id.txtName);
        venName = (TextView)findViewById(R.id.txtVenue);
        date = (TextView)findViewById(R.id.txtDate);
        seat = (ImageView)findViewById(R.id.imgSeat);
        txtTimer = (TextView)findViewById(R.id.txtTimer);
        
        arName.setTypeface(folksFont);
        venName.setTypeface(folksFont);
        date.setTypeface(folksFont);
        
        eventid = getIntent().getStringExtra("event-id");
        total = getIntent().getStringExtra("total");
        stepList = getIntent().getStringArrayListExtra("steps");
        arName.setText(getIntent().getStringExtra("ar_name"));
        venName.setText(getIntent().getStringExtra("ven_name"));
        date.setText(getIntent().getStringExtra("event_date"));
        
        info = getIntent().getStringArrayListExtra("info");
        important = info.get(0);
        imageURL = info.get(1);
       
        
     
       minutes = Integer.parseInt(getIntent().getStringExtra("minutes"));
       folksFonts = Integer.parseInt(getIntent().getStringExtra("folksFonts"));
       showTimer(); 
       
        TextView tot = (TextView)findViewById(R.id.txtTotalPayPay);
		tot.setText("Total So Far \u00a3"+df.format(Double.parseDouble(total)));
		selToken="";
		
		grabURL("");
		lstNew = (ListView)findViewById(R.id.lstNew);
		lvPay = (ListView)findViewById(R.id.lstPay);
    	ArrayList<String>newCard = new ArrayList<String>();
    	newCard.add("Add new card");
    	final ArrayList<HashMap<String, String>> recmylist = new ArrayList<HashMap<String, String>>();
		for(int i=0;i<newCard.size();i++){
         	 HashMap<String, String> map = new HashMap<String, String>();	
     	map.put("name", newCard.get(i));
     	recmylist.add(map);
         }
     	
 	       ListAdapter adapter =new SimpleAdapter(this, recmylist , R.layout.venue_list_item, 
 		        	new String[] {"name"} , 
 	                   new int[] { R.id.list_item_title});
 	      lstNew.setAdapter(adapter); 
 	      lstNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {

 	  		 public void onItemClick(AdapterView<?> arg0,  View arg1, final int arg2,
 						long arg3) {
 					showCardDialog();	 	
 				}
 	       });
 	      
 	 	try {
			parseJSON(getIntent().getStringExtra("summary"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	        drawable = LoadImageFromWeb(imageURL);
	        response = f.getPaymentMethods(eventid);
	        	Log.i("paymet method responses",response);
	     
			return null;
	            
	            
	        }

			protected void onPostExecute(Void unused) {
	        	
				myDialog.dismiss();
				  seat.setImageDrawable(drawable);
	        	parseData(response);
	        
	        	seat.setImageDrawable(drawable);
	        	seat.setOnClickListener(new OnClickListener() {
			 
				    public void onClick(View v) {
				    	Intent AR = new Intent(getApplicationContext(), VenueDetail.class);
				  		AR.putExtra("event-id", eventid);
				           startActivity(AR);
				    }
				});
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
		    
		   public void showAlertDialog(String name){
		    	
		   	 myDialog = new Dialog(this);
		   	 myDialog.getWindow().setFlags( 
		   				WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
		   				WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
		   		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		   		myDialog.setContentView(R.layout.alert_dialog);
		   		
		   		TextView tv = (TextView)myDialog.findViewById(R.id.txtName);
		   		tv.setText(name);
		   		tv.setTypeface(folksFont);
		   		Button button = (Button)myDialog.findViewById(R.id.btnOk);
		   		button.setOnClickListener(new OnClickListener() {
		   		public void onClick(View v) {
		   		    // TODO Auto-generated method stub
		   			myDialog.dismiss();
		   		   
		   		}}); 

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
			 
			 public void parseData(String s){
				 try {	           
			           JSONObject jArray = new JSONObject(s);
			           JSONArray  earthquakes = jArray.getJSONArray("saved_cards");
			        	
			           Log.i("Saved Cards Count",String.valueOf(earthquakes.length()));
			          
			           for(int i=0;i<earthquakes.length();i++){							
							JSONObject e = earthquakes.getJSONObject(i);
							
							if(e.getString("user_label").length()>0){
							name.add(e.getString("user_label"));
							}else{
								name.add("No Name");
							}
							type.add(e.getString("type"));
							token.add(e.getString("token"));
							last.add(e.getString("last_4_digits"));
							expire.add(e.getString("expires_end"));
							image.add("unchecked");
							selToken ="";
						}
			           
		       }catch (Exception e) 
		       { 
		             e.printStackTrace();
		            
		       } 
				
		      
		    	 adapterPay = new PaymentAdapter(Payment.this, R.layout.pay_list_item,
						name,type,token,last,expire,image,resource,first,folksFont);
		    
		    	 if(name.size()>0){
		    	lvPay.setAdapter(adapterPay);
		    	 
		    	lvPay.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				 
					public void onItemClick(AdapterView<?> arg0,  View arg1, final int arg2,
							long arg3) {
					
					  
						final TextView delId = (TextView)arg1.findViewById(R.id.toptext);
						selToken = delId.getText().toString();
						for(int i=0;i<image.size();i++){
						image.set(i, "unchecked");
						}
						
						image.set(arg2,"check");
						
						
						 adapterPay = new PaymentAdapter(Payment.this, R.layout.pay_list_item,
									name, type,token,last,expire,image,resource,first,folksFont);
					    
					    	lvPay.setAdapter(adapterPay);
					    	
					    //get the security details and select the payment method	
						showCvcDialog();
						 	
					}

		       });
		    	 }//end if
				
		    	
		    }
			 
			 public void showCardDialog(){
					
				 myDialog = new Dialog(this);
				 myDialog.getWindow().setFlags( 
							WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
							WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
					myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					myDialog.setContentView(R.layout.add_card);
					 Spinner spin = (Spinner)myDialog.findViewById(R.id.spnType);
				
					final TextView dialogTitle = (TextView)myDialog.findViewById(R.id.textView1);
					final TextView txtCardType = (TextView)myDialog.findViewById(R.id.TextView11); 
					final TextView cardNo = (TextView)myDialog.findViewById(R.id.TextView14);
					final TextView displayName = (TextView)myDialog.findViewById(R.id.TextView02);
					final TextView validFrom = (TextView)myDialog.findViewById(R.id.TextView01);
					final TextView expiryEnd = (TextView)myDialog.findViewById(R.id.TextView13);
					final TextView secNo = (TextView)myDialog.findViewById(R.id.TextView08);
					final TextView issueNo = (TextView)myDialog.findViewById(R.id.TextView07);
					final CheckBox chkCardTerms = (CheckBox)myDialog.findViewById(R.id.chkCardTerms);
					
					final EditText editTxtCardNo = (EditText)myDialog.findViewById(R.id.txtCardNo);
					final EditText editTxtDisplay = (EditText)myDialog.findViewById(R.id.txtDisplay);
					final EditText editTxtValidMonth = (EditText)myDialog.findViewById(R.id.txtValidMonth);
					final EditText editTxtValidYear = (EditText)myDialog.findViewById(R.id.txtValidYear);
					final EditText editTxtExpiryMonth = (EditText)myDialog.findViewById(R.id.txtExpiryMonth);
					final EditText editTxtExpiryYear = (EditText)myDialog.findViewById(R.id.txtExpiryYear);
					final EditText editTxtSecNo = (EditText)myDialog.findViewById(R.id.txtSecurityNo);
					final EditText editTxtIssueNo = (EditText)myDialog.findViewById(R.id.txtIssueNo);
	
					dialogTitle.setTypeface(first);
					txtCardType.setTypeface(first);
					cardNo.setTypeface(first);
					displayName.setTypeface(first);
					validFrom.setTypeface(first);
					expiryEnd.setTypeface(first);
					secNo.setTypeface(first);
					issueNo.setTypeface(first);
					chkCardTerms.setTypeface(first);
					
					editTxtCardNo.setTypeface(first);
					editTxtDisplay.setTypeface(first);
					editTxtValidMonth.setTypeface(first);
					editTxtValidYear.setTypeface(first);
					editTxtExpiryMonth.setTypeface(first);
					editTxtExpiryYear.setTypeface(first);
					editTxtSecNo.setTypeface(first);
					editTxtIssueNo.setTypeface(first);

					editTxtCardNo.addTextChangedListener(new TextWatcher() {
				      
				     	    public void afterTextChanged(Editable s) {
				     	        // TODO Auto-generated method stub
				     	    	editTxtCardNo.setTextColor(Color.rgb(0, 178, 255));
				     	    }
 
				     	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				     	        // TODO Auto-generated method stub
				     	    	editTxtCardNo.setTextColor(Color.rgb(0, 178, 255));
				     	    }
 
				     	    public void onTextChanged(CharSequence s, int start, int before, int count) {
				     	        // TODO Auto-generated method stub
				     	    	editTxtCardNo.setTextColor(Color.rgb(0, 178, 255));
				     	    	
				     	    }
				     	    
				     	});

				      editTxtDisplay.addTextChangedListener(new TextWatcher() {
				      
				     	    public void afterTextChanged(Editable s) {
				     	        // TODO Auto-generated method stub
				     	    	editTxtDisplay.setTextColor(Color.rgb(0, 178, 255));
				     	    }
 
				     	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				     	        // TODO Auto-generated method stub
				     	    	editTxtDisplay.setTextColor(Color.rgb(0, 178, 255));
				     	    }
 
				     	    public void onTextChanged(CharSequence s, int start, int before, int count) {
				     	        // TODO Auto-generated method stub
				     	    	editTxtDisplay.setTextColor(Color.rgb(0, 178, 255));
				     	    	
				     	    }
				     	    
				     	});
				      
				      editTxtIssueNo.setOnEditorActionListener(new OnEditorActionListener() {
 
				            public boolean onEditorAction(TextView v, int actionId,
				                    KeyEvent event) {
				                if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
				                	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				     	    		imm.hideSoftInputFromWindow(editTxtIssueNo.getWindowToken(), 0);
				                  
				                }
				                return false;
				            }
				        });
				      
					ArrayList<String> quanti = new ArrayList<String>();
					quanti.add("Visa");
					quanti.add("MasterCard");
					quanti.add("Maestro");
					quanti.add("American Express");
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				            R.layout.simple_spinner_item, quanti);
					
					adapter.setDropDownViewResource(R.layout.card_dropdown_item); 
					spin.setAdapter(adapter); 
					spin.setSelection(0);
					spin.setOnItemSelectedListener(new OnItemSelectedListener() { 

						public void onItemSelected(AdapterView<?> parent, View arg1,	int arg2, long arg3) { 

							cardType = arg2+1;
						} 
						public void onNothingSelected(AdapterView<?> arg0) { 
						// TODO Auto-generated method stub 
						} 
						});
					
					//checkbox handling
					
					Button button = (Button)myDialog.findViewById(R.id.btnCancel);
					button.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
					    // TODO Auto-generated method stub
						myDialog.dismiss();
					   
					}});
					
					Button button1 = (Button)myDialog.findViewById(R.id.btnAdd);
					button1.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
					    // TODO Auto-generated method stub
						
						String cNo = editTxtCardNo.getText().toString();
						String vm = editTxtValidMonth.getText().toString();
						String vy = editTxtValidYear.getText().toString();
						String em = editTxtExpiryMonth.getText().toString();
						String ey = editTxtExpiryYear.getText().toString();
						String iNo = editTxtIssueNo.getText().toString();
						String sNo = editTxtSecNo.getText().toString();
						String dis = editTxtDisplay.getText().toString();
						
						if(chkCardTerms.isChecked()){
							termVal = 1;
						}else{
							termVal = 0;
						}
						
						Log.i("Card Type",String.valueOf(cardType));
						Log.i("Card No",cNo);
						Log.i("Valid Month",vm);
						Log.i("Valid Year",vy);
						Log.i("Expiry Month",em);
						Log.i("Expiry Year",ey);
						Log.i("Issue No", iNo);
						Log.i("Security Coed",sNo);
						Log.i("terms",String.valueOf(termVal));
						
						
					   
						if(termVal==0){

							LayoutInflater inflater = getLayoutInflater();
							View layout = inflater.inflate(R.layout.toast_layout,
							                               (ViewGroup) findViewById(R.id.toast_layout_root));

							TextView text = (TextView) layout.findViewById(R.id.text);
							text.setText("Please accept terms & conditions");
							text.setTypeface(first);
							text.setTextColor(Color.parseColor("#00B2FF"));

							Toast toast = new Toast(getApplicationContext());
							toast.setGravity(Gravity.CENTER, 0, 50);
							toast.setDuration(Toast.LENGTH_SHORT);
							toast.setView(layout);
							toast.show();
							
							
						}else{
							
							vals.add(String.valueOf(cardType));
							vals.add(cNo);
							vals.add(vm);
							vals.add(vy);
							vals.add(em);
							vals.add(ey);
							vals.add(iNo);
							vals.add(sNo);
							vals.add(dis);
							
							selToken = "0";
							saved = 1;

							myDialog.dismiss();
							//remember to handle error
	
							//grabURL("");
						}
					}});

					myDialog.show();
			}
			 

			 public void showCvcDialog(){
					
				 myDialog = new Dialog(this);
				 myDialog.getWindow().setFlags( 
							WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
							WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
					myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					myDialog.setContentView(R.layout.cvs_dialog);
					
					final EditText cvc = (EditText)myDialog.findViewById(R.id.txtCvc);
					final CheckBox terms = (CheckBox)myDialog.findViewById(R.id.chkTerms);
					//tv.setText(name);
					Button button = (Button)myDialog.findViewById(R.id.btnCancel);
					button.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
					    // TODO Auto-generated method stub
						myDialog.dismiss();
					   
					}});
					
					Button login = (Button)myDialog.findViewById(R.id.btnPayDialog);
					login.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
					    // TODO Auto-generated method stub
						 sec = cvc.getText().toString();
						
						if(terms.isChecked()){
							termVal=1;
							//go to the next view
						}else{
							termVal=0;
							
							
						}
						
						Log.i("cvc",sec);
						Log.i("terms",String.valueOf(termVal));
						myDialog.dismiss();
						//reload the prices
					
					   
					}});
				
					
					
					myDialog.show();
			}
		    
			 public void back(View button){
				 finish(); 
			 }
			 
			 public void next(View button){
				 Log.i("next ","next clicked");
				 if(selToken.length()>0||!selToken.equalsIgnoreCase("")){
						if(termVal==0){
							LayoutInflater inflater = getLayoutInflater();
							View layout = inflater.inflate(R.layout.toast_layout,
							                               (ViewGroup) findViewById(R.id.toast_layout_root));

							TextView text = (TextView) layout.findViewById(R.id.text);
							text.setText("Please accept terms & conditions");
							text.setTypeface(first);
							text.setTextColor(Color.parseColor("#00B2FF"));

							Toast toast = new Toast(getApplicationContext());
							toast.setGravity(Gravity.CENTER, 0, 50);
							toast.setDuration(Toast.LENGTH_SHORT);
							toast.setView(layout);
							toast.show();
							
						}else{
					Log.i("Selected card token",selToken);
							if(saved==1){
								String res = f.addNewCard(vals, eventid);
								try {
									parseResponse(res);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}else{
								f.selectPaymentMethod(eventid, selToken, sec, 1);
								Intent AR = new Intent(getApplicationContext(),Confirmation.class);
								AR.putExtra("event-id", eventid);
								AR.putExtra("total",String.valueOf(total));
			    			
			    		        AR.putExtra("ar_name", arName.getText()); 
			    		        AR.putExtra("ven_name", venName.getText());
			    		        AR.putExtra("event_date", date.getText());
			    		        AR.putExtra("url",imageURL);
			    		        AR.putStringArrayListExtra("steps", stepList);
			    		        AR.putStringArrayListExtra("info", info);
			    		        startActivity(AR);
							}//end if saved
							
						}//end if term val
				 	}//end if selToken
				 else{
					 LayoutInflater inflater = getLayoutInflater();
						View layout = inflater.inflate(R.layout.toast_layout,
						                               (ViewGroup) findViewById(R.id.toast_layout_root));

						TextView text = (TextView) layout.findViewById(R.id.text);
						text.setText("Please select a payment method");
						text.setTypeface(first);
						text.setTextColor(Color.parseColor("#00B2FF"));

						Toast toast = new Toast(getApplicationContext());
						toast.setGravity(Gravity.CENTER, 0, 50);
						toast.setDuration(Toast.LENGTH_SHORT);
						toast.setView(layout);
						toast.show();
						
					}
				
			 }
			 
			 public void parseResponse(String s) throws JSONException{
				 JSONObject jArray = new JSONObject(s);
		           JSONArray  earthquakes = jArray.getJSONArray("categories");
		        	
			        for(int i=0;i<earthquakes.length();i++){							
						JSONObject e = earthquakes.getJSONObject(i);
						
						if(e.has("error")){
						Log.i("name",e.getString("error"));
						error = e.getString("error");
						}else{
							error="";
						}
					
			        	
					}	
			        
			        if(error.length()>0){
			        	LayoutInflater inflater = getLayoutInflater();
						View layout = inflater.inflate(R.layout.toast_layout,
						                               (ViewGroup) findViewById(R.id.toast_layout_root));

						TextView text = (TextView) layout.findViewById(R.id.text);
						text.setText(error);
						text.setTypeface(first);
						text.setTextColor(Color.parseColor("#00B2FF"));

						Toast toast = new Toast(getApplicationContext());
						toast.setGravity(Gravity.CENTER, 0, 50);
						toast.setDuration(Toast.LENGTH_SHORT);
						toast.setView(layout);
						toast.show();
			        }else{
			        	Intent AR = new Intent(getApplicationContext(),Confirmation.class);
						AR.putExtra("event-id", eventid);
						AR.putExtra("total",String.valueOf(total));
	    			
	    		        AR.putExtra("ar_name", arName.getText()); 
	    		        AR.putExtra("ven_name", venName.getText());
	    		        AR.putExtra("event_date", date.getText());
	    		        AR.putExtra("url",imageURL);
	    		        AR.putStringArrayListExtra("steps", stepList);
	    		        AR.putStringArrayListExtra("info", info);
	    		        startActivity(AR);
			        }
			        
			   
			 }
			 
			 public void showTimer(){
		    		timer = new Timer();
		    		delay1 = 1000;
		    		 period1 = 1000; // repeat every 5 sec.
				       timer.scheduleAtFixedRate(new TimerTask() {
				        public void run() {
				        	updateTimeUi();
				        }
				        }, delay1, period1);
		    	}
		    	
		    	public void updateTimeUi(){
		    		mHandler1.postDelayed(mUpdateTimeLabel, 100);
		    		
		    	}
		    	
			    private Runnable mUpdateTimeLabel = new Runnable() {
			    	   public void run() {
			    		   
			    		   if(folksFonts>0){
			    			   folksFonts -=1;
			    			   
			    			   if(folksFonts<10){
				    					
				    				txtTimer.setText(String.valueOf(minutes)+":0"+String.valueOf(folksFonts));
				    			}else{
				    				txtTimer.setText(String.valueOf(minutes)+":"+String.valueOf(folksFonts));
				    		
				    			}//end if folksFonts < 10
				    		

				    			
			    		   }else if(folksFonts==0){
			    			  if(minutes>0){
			    			   minutes-=1;
			    			   folksFonts = 60;
			    			  }else{
			    				  showAlertDialog("Your time has expired.Please restart the Order");
				    			   Intent AR = new Intent(getApplicationContext(),  TicketlineActivity.class);
				    				 AR.putExtra("TAB", "0");
				    				           startActivity(AR);
				    				           finish();
				    				         
			    			  }
			    			  
			    		   }
			    		   else {
			    			
			    			   timer.cancel();
			    		   }
			    	   }

					
			    	   
			    	};
			    	
			    	public void moreInfo(View button){

			    		 myDialog = new Dialog(this);
			    		 myDialog.getWindow().setFlags( 
			    					WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
			    					WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
			    			myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			    			myDialog.setContentView(R.layout.more_info_dialog);
			    			
			    			TextView tv = (TextView)myDialog.findViewById(R.id.txtName);
			    			tv.setTypeface(folksFont);
			    			
			    			Button button1 = (Button)myDialog.findViewById(R.id.btnInfo);
			    			button1.setTypeface(folksFont);
			    			button1.setOnClickListener(new OnClickListener() {
			    			public void onClick(View v) {
			    				Intent AR = new Intent(getApplicationContext(), VenueDetail.class);
			    		  		// AR.putExtra("venue-id",venueid );
			    		  		AR.putExtra("event-id", getIntent().getStringExtra("event-id"));
			    		           startActivity(AR);
			    			}});
			    			
			    			Button button2 = (Button)myDialog.findViewById(R.id.btnImportant);
			    			button2.setTypeface(folksFont);
			    			button2.setOnClickListener(new OnClickListener() {
			    			public void onClick(View v) {
			    				myDialog.dismiss();
			    				showImportantInfo("Important Event Details",important);
			    			}});
			    			
			    			Button button3 = (Button)myDialog.findViewById(R.id.btnSummary);
			    			button3.setTypeface(folksFont);
			    			button3.setOnClickListener(new OnClickListener() {
			    			public void onClick(View v) {
			    				myDialog.dismiss();
			    				showSummaryDialog();
			    			}});
			    			
			    		
			    			
			    			Button button6 = (Button)myDialog.findViewById(R.id.btnCancel);
			    			button6.setTypeface(folksFont);
			    			button6.setOnClickListener(new OnClickListener() {
			    			public void onClick(View v) {
			    			    // TODO Auto-generated method stub
			    			   // showEvents(arId,arName);
			    				myDialog.dismiss();
			    			}});
			    			
			    			myDialog.show();
			    		}

			    	public void showImportantInfo(String topic,String name){
			    		
			    		 myDialog = new Dialog(this);
			    		 myDialog.getWindow().setFlags( 
			    					WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
			    					WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
			    			myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			    			myDialog.setContentView(R.layout.moreinfo_dialog);
			    			
			    			TextView tv = (TextView)myDialog.findViewById(R.id.txtTopic);
			    			tv.setText(topic);
			    			tv.setTypeface(folksFont);
			    			String noDetailsFound = "No Details Found";
			    			WebView tv1 = (WebView)myDialog.findViewById(R.id.txtHtml);
			    			if(name.length()>0){
			    			tv1.loadDataWithBaseURL(null, name,"text/html", "utf-8", null);
			    			}else{
			    				tv1.loadDataWithBaseURL(null, noDetailsFound,"text/html", "utf-8", null);
			    			}
			    		
			    			
			    			
			    			myDialog.show();
			    	}
			    	
			    	public void parseJSON(String r) throws JSONException{
			    		 JSONObject jArray = new JSONObject(getIntent().getStringExtra("summary"));
			    	     JSONArray  earthquakes = jArray.getJSONArray("categories");
			    	  	
			    	       for(int i=0;i<earthquakes.length();i++){						
			    				JSONObject e = earthquakes.getJSONObject(i);
			    				
			    				
			    			
			    	           
			    	       	Log.i("total value",e.getString("total_value"));
			    	       	
			    	       	
			    	       	String tic = "{\"tickets\":"+e.getString("tickets")+"}";
			    	       	JSONObject ticArray = new JSONObject(tic);
			    	           JSONArray  tAr = ticArray.getJSONArray("tickets");
			    	           for(int a=0;a<tAr.length();a++){
			    	           JSONObject eee = tAr.getJSONObject(a);
			    	           Log.i("total details",eee.getString("details"));
			    	           details.add(eee.getString("details"));
			    	           Log.i("face value",eee.getString("face_value"));
			    	           Log.i("booking fee",eee.getString("booking_fee"));
			    	           double pri = Double.parseDouble(eee.getString("face_value"))+Double.parseDouble(eee.getString("booking_fee"));
			    	           price.add(String.valueOf(pri));
			    	           }
			    	           
			    	          
			    			}	
			    		
			    	}
			    	
			    	 public void showSummaryDialog(){
					    	
				   	   	 myDialog = new Dialog(this);
				   	   	 myDialog.getWindow().setFlags( 
				   	   				WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
				   	   				WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
				   	   		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				   	   		myDialog.setContentView(R.layout.summary_dialog);
				   	   		
				   	   		TextView tv = (TextView)myDialog.findViewById(R.id.txtTopic);
				   	   		tv.setTypeface(first);
				   	   		ListView l = (ListView)myDialog.findViewById(R.id.lstSummary);
				   	   		ConfirmAdapter adapter = new ConfirmAdapter(this, R.layout.confirm_list_item,
			    				details, price,first,folksFont);
				   	   			l.setAdapter(adapter);
				   	   		Button button = (Button)myDialog.findViewById(R.id.btnOk);
				   	   		button.setTypeface(first);
				   	   		button.setOnClickListener(new OnClickListener() {
				   	   		public void onClick(View v) {
				   	   		    // TODO Auto-generated method stub
				   	   			myDialog.dismiss();
				   	   		   
				   	   		}});
				   	   		
				   	   		
				   	   	
				   	   		
				   	   		
				   	   		myDialog.show();
				   	   }
}
