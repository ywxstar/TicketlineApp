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

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ConfirmAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	private ArrayList<String> details= new ArrayList<String>();
	private ArrayList<String> price=new ArrayList<String>();
	private int mViewResourceId;
	private String[] mStrings;

	Drawable drawable;
	DecimalFormat df = new DecimalFormat("#########0.00"); 
	Resources resource;
	Context ctx;
	TypedArray mIcons;
	Typeface first;
	Typeface folksFont;
	
	public ConfirmAdapter(Context ctx, int viewResourceId,
		ArrayList<String> details, ArrayList<String> price,Typeface first, Typeface folksFont) {
			
			super(ctx, viewResourceId,details);
			this.ctx = ctx;
			this.details = details;
			this.price = price;
			this.first = first;
			this.folksFont = folksFont;
			mInflater = (LayoutInflater)ctx.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
		
			mViewResourceId = viewResourceId;
	}

	@Override
	public int getCount() {
		return details.size();
	}

	@Override
	public String getItem(int position) {
		return details.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = mInflater.inflate(mViewResourceId, null);
		TextView tv = (TextView)convertView.findViewById(R.id.list_item_title);
		String d = details.get(position).replace("[", "").replace("]", "");
		d = d.replace("|", "-");
		tv.setText(d);
		tv.setTypeface(folksFont); // Folks Font
		
		
		
		TextView desc = (TextView)convertView.findViewById(R.id.txtDetails);
		desc.setText("\u00a3"+df.format(Double.parseDouble(price.get(position))));
		desc.setTypeface(first); // Arial Font
		
		TextView fee = (TextView)convertView.findViewById(R.id.txtFee);
		fee.setVisibility(View.GONE);
			
		return convertView;
	}	
}

