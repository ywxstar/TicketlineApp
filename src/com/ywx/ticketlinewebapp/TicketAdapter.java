package com.ywx.ticketlinewebapp;

/**
 * This file is part of AdvancedListViewDemo.
 * You should have downloaded this file from www.intransitione.com, if not, 
 * please inform me by writing an e-mail at the address below:
 *
 * Copyright [2011] [Marco Dinacci <marco.dinacci@gmail.com>]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.

 * The license text is available online and in the LICENSE file accompanying the distribution
 * of this program.
 */

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TicketAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	Drawable drawable;
	Context ctx;
	private ArrayList<String> names;
	
	private ArrayList<String> dates;
	private ArrayList<String> available;
	private ArrayList<String> status;
	private ArrayList<String> eventName;
	private int mViewResourceId;
	ImageManager im;
	Typeface typeface, arialFont;
	Functions f;
	
	public TicketAdapter(Context ctx, int viewResourceId,
			ArrayList<String> names, ArrayList<String> dates,ArrayList<String> available,ArrayList<String> status,
			ArrayList<String> event,Typeface folksFont, Typeface arialFont) {
		super(ctx, viewResourceId, names);
		this.ctx = ctx;
		mInflater = (LayoutInflater)ctx.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		this.names = names;
		this.dates = dates;
		this.available = available;
		this.status = status;
		this.eventName = event;
		this.typeface = folksFont;
		this.arialFont = arialFont;
		mViewResourceId = viewResourceId;
	}



	@Override
	public int getCount() {
		return names.size();
	}

	@Override
	public String getItem(int position) {
		return names.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(mViewResourceId, null);
		
		f = new Functions();

		TextView name = (TextView)convertView.findViewById(R.id.txtVenueName);
		name.setText(names.get(position));
		name.setTypeface(typeface); // Folks
		
		TextView ename = (TextView)convertView.findViewById(R.id.txtEventName);
		ename.setText(eventName.get(position));
		ename.setTypeface(typeface); // Folks
		
		TextView date = (TextView)convertView.findViewById(R.id.txtDate);
		
		String dat = dates.get(position).substring(0,10);
		String time = dates.get(position).substring(11).trim();
		//Log.i("time",time);
		date.setText(f.createDate(dat,time));
		date.setTypeface(arialFont); // Arial
		
		TextView avai = (TextView)convertView.findViewById(R.id.txtAvailable);
		if(available.get(position).toString().equalsIgnoreCase("null")) {
		
			avai.setVisibility(View.GONE);
		}
		
		else if(available.get(position).toString().equalsIgnoreCase("0.00")) {
			
			avai.setText("Press here for prices");
		}
		
		else{
		avai.setText("Tickets from \u00a3"+available.get(position)+" inc booking fee");
		}
		avai.setTypeface(typeface); // Folks
		
		
		TextView sta = (TextView)convertView.findViewById(R.id.txtStatus);
		if(status.get(position).equalsIgnoreCase("On Sale")){
			sta.setTextColor(Color.rgb(179, 0, 178));
		}
		sta.setText(status.get(position).toUpperCase());
		sta.setTypeface(typeface); // Folks
		
		return convertView;
	}
	
	
	
	
}

