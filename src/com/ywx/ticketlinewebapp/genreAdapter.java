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

import java.io.InputStream;
import java.net.URL;
 
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class genreAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	Drawable drawable;
	Context ctx;
	private String[] mStrings;
	TypedArray mIcons;
	//private ArrayList<String> mCaptions;
	//private ArrayList<String> murls;
	//private ArrayList<String> mnames;
	
	private int mViewResourceId;
	ImageManager im;
	
	public genreAdapter(Context ctx, int viewResourceId,
			String[] options, TypedArray icons) {
		super(ctx, viewResourceId, options);
		this.ctx = ctx;
		mInflater = (LayoutInflater)ctx.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		mStrings = options;
		mIcons = icons;
		
		mViewResourceId = viewResourceId;
		
		im = new ImageManager();

	}



	@Override
	public int getCount() {
		return mStrings.length;
	}

	@Override
	public String getItem(int position) {
		return mStrings[position];
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
	        holder.iv = (ImageView)convertView.findViewById(R.id.artistImage);
	        holder.tv = (TextView)convertView.findViewById(R.id.list_item_title);

    		convertView.setTag(holder);
    	}		
		
		 holder.iv.setImageDrawable(mIcons.getDrawable(position));
		 holder.tv.setText(mStrings[position].replaceAll("and", "&"));
		
		/*
		convertView = mInflater.inflate(mViewResourceId, null);
		
		ImageView iv = (ImageView)convertView.findViewById(R.id.artistImage);	   
		iv.setImageDrawable(mIcons.getDrawable(position));

		TextView tv = (TextView)convertView.findViewById(R.id.list_item_title);
		tv.setText(mStrings[position].replaceAll("and", "&"));
		*/
		
		
		
		return convertView;
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
	

	private ViewHolder holder = new ViewHolder();	
    static class ViewHolder 
    {
         public TextView tv;
         public ImageView iv;
    }		
	
	
}

