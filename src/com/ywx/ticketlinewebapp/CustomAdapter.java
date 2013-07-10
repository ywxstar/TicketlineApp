package com.ywx.ticketlinewebapp;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
//import android.view.View.OnClickListener;

class CustomAdapterView extends LinearLayout {  
	
	public CustomAdapterView(Context context, Database device) {
		super(context);		

		//container is a horizontal layer
		setOrientation(LinearLayout.HORIZONTAL);
		setPadding(0, 6, 0, 6);
		
		//image:params
		LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		Params.setMargins(6, 0, 6, 0);
		//image:itself
		ImageView ivLogo = new ImageView(context);
	
		// load image
		//if (device.getId() == "0")
		ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.icon));
		//else if (device.getId() == "1")
			//ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.icon));
		//image:add
		addView(ivLogo, Params);
		
		//vertical layer for text
		Params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout PanelV = new LinearLayout(context);
		PanelV.setOrientation(LinearLayout.VERTICAL);
		PanelV.setGravity(Gravity.BOTTOM);
		
		TextView textId = new TextView( context );
		textId.setTextSize(16);
		textId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		textId.setText("Record Id : "+ device.getId());
		PanelV.addView(textId);
		
		TextView textName = new TextView( context );
		textName.setTextSize(16);
		textName.setText("Question : "+ device.getName());
		PanelV.addView(textName);       
		
		TextView textType= new TextView( context );
		textType.setTextSize(16);
		textType.setText("Type : "+ device.getType());
		PanelV.addView(textType);  
		
		TextView textOptions = new TextView( context );
		textOptions.setTextSize(16);
		textOptions.setText("Answer Options : "+ device.getOptions());
		PanelV.addView(textOptions); 
		
		addView(PanelV, Params);
	}
}


public class CustomAdapter extends BaseAdapter /*implements OnClickListener*/ {
	
	/*private class OnItemClickListener implements OnClickListener{           
	    private int mPosition;
	    OnItemClickListener(int position){
	            mPosition = position;
	    }
	    public void onClick(View arg0) {
	            Log.v("ddd", "onItemClick at position" + mPosition);                      
	    }               
	}*/

	public static final String LOG_TAG = "BI::CA";
    private Context context;
    private ArrayList<Database> deviceList = new ArrayList<Database>();

    public CustomAdapter(Context context, ArrayList<Database> ques ) { 
        this.context = context;
        this.deviceList = ques;
    }

    public int getCount() {                        
        return deviceList.size();
    }

    public Object getItem(int position) {     
        return deviceList.get(position);
    }

    public long getItemId(int position) {  
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) { 
        Database device = deviceList.get(position);
        View v = new CustomAdapterView(this.context, device );
        
        v.setBackgroundColor((position % 2) == 1 ? Color.DKGRAY : Color.BLACK);
        
        /*v.setOnClickListener(new OnItemClickListener(position));*/
        return v;
    }

    /*public void onClick(View v) {
            Log.v(LOG_TAG, "Row button clicked");
    }*/

}
