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
import android.widget.ImageView;
import android.widget.TextView;

public class PriceAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	Drawable drawable;
	 Resources resource;
	Context ctx;
	//private String[] mStrings;
	TypedArray mIcons;
	private ArrayList<String> total= new ArrayList<String>();
	private ArrayList<String> type=new ArrayList<String>();
	private ArrayList<String> face =new ArrayList<String>();
	private ArrayList<String> nos =new ArrayList<String>();
	private ArrayList<String> display =new ArrayList<String>();
	DecimalFormat df = new DecimalFormat("#########0.00"); 
	
	private int mViewResourceId;
	ImageManager im;
	
	Typeface first;
	Typeface folksFont;
	
		public PriceAdapter(Context ctx, int viewResourceId,
				ArrayList<String> toal, ArrayList<String> type,ArrayList<String> face,ArrayList<String> nos,ArrayList<String> dis,Typeface folksFont) {
		super(ctx, viewResourceId,toal);
		this.ctx = ctx;
		//this.resource = res;
		mInflater = (LayoutInflater)ctx.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		this.total = toal;
		this.type = type;
		this.face = face;
		this.nos = nos;
		this.display = dis;
		this.folksFont = folksFont;
		
		mViewResourceId = viewResourceId;
		
		im = new ImageManager();

	}



	@Override
	public int getCount() {
		return total.size();
	}

	@Override
	public String getItem(int position) {
		return total.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		if(convertView != null && convertView.getTag() instanceof ViewHolder)
    		holder = (ViewHolder) convertView.getTag();
    	else
    	{
			holder = new ViewHolder();
			convertView = mInflater.inflate(mViewResourceId, null);
	        holder.tv = (TextView)convertView.findViewById(R.id.txtQuantity);
	        holder.ty = (TextView)convertView.findViewById(R.id.txtType);
	        holder.fv = (TextView)convertView.findViewById(R.id.txtFaceVal);
	        holder.price = (TextView)convertView.findViewById(R.id.txtPrice);
	        holder.displayText = (TextView)convertView.findViewById(R.id.txtDisplay);
    		convertView.setTag(holder);
    	}		
		
		 holder.tv.setText(nos.get(position));
		 holder.tv.setTypeface(first); // Arial Font
		 holder.ty.setText(type.get(position));
		 holder.ty.setTypeface(folksFont); // Folks Font		
		 holder.fv.setText("\u00a3"+df.format(Double.valueOf(face.get(position)))+" Face Value ("+(display.get(position))+")");
		 holder.fv.setTypeface(first); // Arial Font		
		 holder.price.setText("\u00a3"+df.format(Double.valueOf(total.get(position))));
		 holder.price.setTypeface(first); // Arial Font		
		 holder.displayText.setText(display.get(position));
		 holder.displayText.setTypeface(first); // Arial Font	
		 
		 /*
		convertView = mInflater.inflate(mViewResourceId, null);
		
		TextView tv = (TextView)convertView.findViewById(R.id.txtQuantity);
		tv.setText(nos.get(position));
		tv.setTypeface(first); // Arial Font
		
		TextView ty = (TextView)convertView.findViewById(R.id.txtType);
		ty.setText(type.get(position));
		ty.setTypeface(folksFont); // Folks Font
		
		TextView fv = (TextView)convertView.findViewById(R.id.txtFaceVal);
		//fv.setText(face.get(position));
		fv.setText("\u00a3"+df.format(Double.valueOf(face.get(position)))+" Face Value ("+(display.get(position))+")");
		fv.setTypeface(first); // Arial Font
		
		TextView price = (TextView)convertView.findViewById(R.id.txtPrice);
		//price.setText(total.get(position));
		price.setText("\u00a3"+df.format(Double.valueOf(total.get(position))));
		price.setTypeface(first); // Arial Font
		
		TextView displayText = (TextView)convertView.findViewById(R.id.txtDisplay);
		//price.setText(total.get(position));
		displayText.setText(display.get(position));
		displayText.setTypeface(first); // Arial Font
		*/
		
		return convertView;
	}
	
	private ViewHolder holder = new ViewHolder();
    static class ViewHolder 
    {
         public TextView tv;
         public TextView ty;
         public TextView fv;
         public TextView price;
         public TextView displayText;
    }	
	
	
}

