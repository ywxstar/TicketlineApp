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

public class deliveryAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	Drawable drawable;
	 Resources resource;
	Context ctx;
	private String[] mStrings;
	TypedArray mIcons;
	private ArrayList<String> deliveryMethod= new ArrayList<String>();
	private ArrayList<String> deliveryId=new ArrayList<String>();
	private ArrayList<String> deliveryFee =new ArrayList<String>();
	private ArrayList<String> deliveryDesc=new ArrayList<String>();
	private ArrayList<String> imgArray =new ArrayList<String>();
	
	private int mViewResourceId;
	ImageManager im;
	
	Typeface first;
	Typeface folksFont;
	
		public deliveryAdapter(Context ctx, int viewResourceId,
				ArrayList<String> delMethod, ArrayList<String> delId,ArrayList<String> delFee,ArrayList<String> delDesc,ArrayList<String> imgArr,
				Resources res,Typeface first,Typeface folksFont) {
		super(ctx, viewResourceId,delMethod);
		this.ctx = ctx;
		this.resource = res;
		mInflater = (LayoutInflater)ctx.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		this.deliveryMethod = delMethod;
		this.deliveryId = delId;
		this.deliveryFee = delFee;
		this.deliveryDesc = delDesc;
		this.imgArray = imgArr;
		this.first = first;
		this.folksFont = folksFont;
		
		mViewResourceId = viewResourceId;
		
		im = new ImageManager();

	}



	@Override
	public int getCount() {
		return deliveryMethod.size();
	}

	@Override
	public String getItem(int position) {
		return deliveryMethod.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(mViewResourceId, null);
		
		ImageView iv = (ImageView)convertView.findViewById(R.id.imgUncheckDel);
		
	   if(imgArray.get(position)=="unchecked"){
		   iv.setImageDrawable(resource.getDrawable(R.drawable.uncheck));
	   }else{
		   iv.setImageDrawable(resource.getDrawable(R.drawable.check));  
	   }
		

		TextView tv = (TextView)convertView.findViewById(R.id.list_item_title);
		tv.setText(deliveryMethod.get(position));
		tv.setTypeface(first); // Arial Font 
		
		TextView desc = (TextView)convertView.findViewById(R.id.txtDetails);
		desc.setText(deliveryDesc.get(position));
		desc.setTypeface(first); // Arial Font
		
		TextView id = (TextView)convertView.findViewById(R.id.topText);
		id.setText(deliveryId.get(position));
		
		TextView fee = (TextView)convertView.findViewById(R.id.txtFee);
		fee.setText(deliveryFee.get(position));
		fee.setTypeface(first); // Arial Font
		
		
		
		return convertView;
	}
	
	
	
	
}

