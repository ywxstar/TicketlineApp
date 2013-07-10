package com.ywx.ticketlinewebapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;



public class ArtistSearch extends Activity {
	 
	public final static String ITEM_TITLE = "title";  
	public final static String ITEM_CAPTION = "caption";  
	    String[] alpha;
	    EditText search;
	    Functions f;
	    Typeface folksFont;
	    Typeface arialFont;
	    RelativeLayout searchLayout;
	    ListView searchList;
	    String response;
	    Button artist;
	    Button date;
	    Button venue;
	    Button genre;
	    Button location;
	    ArrayList<String> arName = new ArrayList<String>();
	    ArrayList<String> arId = new ArrayList<String>();
	    ArrayList<String> arSlug = new ArrayList<String>();
	    ArrayList<HashMap<String, String>> mylist;
	    Dialog myDialog;
	    Button b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16,b17,b18,b19,b20,b21,b22,b23,b24,b25,b26,
	    b27,b28,b29,b30,b31,b32,b33,b34,b35;
	    
	    public Map<String,?> createItem(String title, String caption) {  
	        Map<String,String> item = new HashMap<String,String>();  
	        item.put(ITEM_TITLE, title);  
	        item.put(ITEM_CAPTION, caption);  
	        return item;  
	    }  
	  
	    @Override  
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	     // setContentView(R.layout.ar_search);
	        setContentView(R.layout.copy_of_ar_search);
	        f = new Functions();
	        folksFont = Typeface.createFromAsset(this.getAssets(),"Folks-Normal-webfont.ttf");
	        arialFont = Typeface.createFromAsset(this.getAssets(), "Arial.ttf");
	        setButtons();
	        search = (EditText)findViewById(R.id.txtSearch);
	        searchLayout = (RelativeLayout)findViewById(R.id.searchResults);
	        searchList = (ListView)findViewById(R.id.lstResults);
	        
	        artist = (Button)findViewById(R.id.Button01);
	        date = (Button)findViewById(R.id.Button02);
	        venue = (Button)findViewById(R.id.Button03);
	        genre = (Button)findViewById(R.id.Button04);
	        location = (Button)findViewById(R.id.Button05);
	        
	        artist.setTypeface(arialFont);
	        date.setTypeface(arialFont);
	        venue.setTypeface(arialFont);
	        genre.setTypeface(arialFont);
	        location.setTypeface(arialFont);
	      
	        getApplicationContext();
	        
	        
	        search.addTextChangedListener(new TextWatcher() {
	     
	     	    public void afterTextChanged(Editable s) {
	     	        // TODO Auto-generated method stub
	     	    }
 
	     	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	     	     
	     	    }
 
	     	    public void onTextChanged(CharSequence s, int start, int before, int count) {

	     	    	Log.i("Text field value",search.getText().toString());
	     	    	if(search.length()>0){
	     	    	//GetSearchResults(search.getText().toString());
	     	    		searchLayout.setVisibility(View.VISIBLE);
	     	    		response = f.Search(search.getText().toString());
	     	    		
	     	    		try {
	     	    			repopulateList(response);
	     	    				} catch (JSONException e) {
	     	    					// TODO Auto-generated catch block
	     	    						e.printStackTrace();
	     	    			}
	     	    	} else {
	     	    		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	     	    		imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
	     	    		searchLayout.setVisibility(View.GONE);
	     	    	  }
	     	     }
	     	});
	        
	        search.setOnEditorActionListener(new OnEditorActionListener() {
	        
	            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	            	
	                 if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
	                	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	     	    		imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
	                 }
	                 return false;
	            }
	        });
	     
	 }  
	    
	    void repopulateList(String response) throws JSONException {
	    	
	    	  if(response.equalsIgnoreCase("{\"categories\":null}")){
		        	 Log.i("Result","null val");
		        	 showAlertDialog("No results found for your search \""+search.getText().toString()+"\"");
		         } else {
		        	 JSONObject jArray = new JSONObject(response);
			         JSONArray  earthquakes = jArray.getJSONArray("categories");
			      	
			       //clear the arrays
			         if(arId.size()>0) {
			        	 arId.clear();
			        	 arName.clear();
			        	 arSlug.clear();
			        	 mylist.clear();
			         }
			         
			         for(int i=0;i<earthquakes.length();i++) {						
							
							JSONObject e = earthquakes.getJSONObject(i);
							arId.add(e.getString("id"));
							arName.add(e.getString("name"));
							arSlug.add(e.getString("slug"));
			         }
			         
			         SeparatedListAdapter adapter = new SeparatedListAdapter(this);  
				     mylist = new ArrayList<HashMap<String, String>>();
				      	    for(int i=0;i<arId.size();i++){
				      	    HashMap<String, String> map1 = new HashMap<String, String>();
					        // map1.put("id",arId.get(i));
					        map1.put("name",arName.get(i));
					        mylist.add(map1);
				           }
				        
				     adapter.addSection("Search Results", new SimpleAdapter(this, mylist , R.layout.list_item, 
			                    new String[] {"name"} , 
			                    new int[] { R.id.list_item_title})); 
				         
			         searchList.setAdapter(adapter);
			         searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
								
								String name = arName.get(arg2-1);
								String id = arId.get(arg2-1);
								String slug = arSlug.get(arg2-1);
								showTickets(id,name,slug);
							}
			         });
		         }
	    }

	    public void showTickets(String id,String name, String Slug) {
			
	    	search.setText("");
	    	searchLayout.setVisibility(View.GONE);
			Intent AR = new Intent(getParent(),  Tickets.class);
			AR.putExtra("Name",name );
			AR.putExtra("Slug", Slug);
			AR.putExtra("artist-id",id );
			AR.putExtra("saved", "false");
	        startActivity(AR);
		}
	    
	    public void Date(View button) {
			
	    	Intent DT = new Intent(getParent(),DateSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("DateSearchActivity", DT);
		}
		
		public void Genre(View button) {
			
			Intent GE = new Intent(getParent(),GenreSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("GenreSearchActivity", GE);
		}
		
		public void Location(View button) {
			Intent LO = new Intent(getParent(),LocationSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("LocationSearchActivity", LO);
		}
		
		public void Venue(View button) {
			Intent VE = new Intent(getParent(),VenueSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("VenueSearchActivity", VE);
		}
		
		public void Artist(View button) {
			Intent AR = new Intent(getParent(),ArtistSearch.class);
			//DT.putExtra("Name", );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("VenueSearchActivity", AR);
		}
		
	    
	    public void showArtist(View button) {
	    	Log.i("Tag",button.getTag().toString());
	    	String s = button.getTag().toString();
	    	Intent PR = new Intent(getParent(),ArtistList.class);
			PR.putExtra("char",s );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("UserProfileActivity", PR);
	    }
	    
	    public void showArtistSpecial(View button) {
	    	String s = button.getTag().toString();
	    	Intent PR = new Intent(getParent(),ArtistList.class);
			PR.putExtra("char",s );
	 		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
	 		parentActivity.startChildActivity("UserProfileActivity", PR);
	    }
	    //
	    public void setButtons() {
	    	b6 = (Button)findViewById(R.id.Button06);
	    	b7 = (Button)findViewById(R.id.Button07);
	    	b8 = (Button)findViewById(R.id.Button08);
	    	b9 = (Button)findViewById(R.id.Button09);
	    	b10 = (Button)findViewById(R.id.Button10);
	    	b11 = (Button)findViewById(R.id.Button11);
	    	b12= (Button)findViewById(R.id.Button12);
	    	b13 = (Button)findViewById(R.id.Button13);
	    	b14= (Button)findViewById(R.id.Button14);
	    	b15= (Button)findViewById(R.id.Button15);
	    
	    	b19= (Button)findViewById(R.id.Button19);
	    	b20= (Button)findViewById(R.id.Button20);
	    	b21= (Button)findViewById(R.id.Button21);
	    	b22= (Button)findViewById(R.id.Button22);
	    	b23= (Button)findViewById(R.id.Button23);
	    	b24= (Button)findViewById(R.id.Button24);
	    	b25= (Button)findViewById(R.id.Button25);
	    	b26= (Button)findViewById(R.id.Button26);
	    	b27= (Button)findViewById(R.id.Button27);
	    	b28= (Button)findViewById(R.id.Button28);
	    	b29= (Button)findViewById(R.id.Button29);
	    	b30= (Button)findViewById(R.id.Button30);
	    	b31= (Button)findViewById(R.id.Button31);
	    	b32= (Button)findViewById(R.id.Button32);
	    	b33= (Button)findViewById(R.id.Button33);
	    	b34= (Button)findViewById(R.id.Button34);
	    	b35= (Button)findViewById(R.id.Button35);
	    	
	    	b6.setTypeface(folksFont);
	    	b7.setTypeface(folksFont);
	    	b8.setTypeface(folksFont);
	    	b9.setTypeface(folksFont);
	    	b10.setTypeface(folksFont);
	    	b11.setTypeface(folksFont);
	    	b12.setTypeface(folksFont);
	    	b13.setTypeface(folksFont);
	    	b14.setTypeface(folksFont);
	    	b15.setTypeface(folksFont);
	    
	    	b19.setTypeface(folksFont);
	    	b20.setTypeface(folksFont);
	    	b21.setTypeface(folksFont);
	    	b22.setTypeface(folksFont);
	    	b23.setTypeface(folksFont);
	    	b24.setTypeface(folksFont);
	    	b25.setTypeface(folksFont);
	    	b26.setTypeface(folksFont);
	    	b27.setTypeface(folksFont);
	    	b28.setTypeface(folksFont);
	    	b29.setTypeface(folksFont);
	    	b30.setTypeface(folksFont);
	    	b31.setTypeface(folksFont);
	    	b32.setTypeface(folksFont);
	    	b33.setTypeface(folksFont);
	    	b34.setTypeface(folksFont);
	    	b35.setTypeface(folksFont);
	    	
	    }
	    
	    public void showAlertDialog(String name){
	    	
	   	 myDialog = new Dialog(getParent());
	   	 myDialog.getWindow().setFlags( 
	   				WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
	   				WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
	   		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	   		myDialog.setContentView(R.layout.alert_dialog);
	   		
	   		TextView tv = (TextView)myDialog.findViewById(R.id.txtName);
	   		tv.setText(name);
	   		Button button = (Button)myDialog.findViewById(R.id.btnOk);
	   		button.setOnClickListener(new OnClickListener() {
	   		public void onClick(View v) {
	   		    // TODO Auto-generated method stub
	   			myDialog.dismiss();
	   		   
	   		}});

	   		myDialog.show();
	   }
}
