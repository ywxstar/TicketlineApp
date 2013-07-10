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
import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAndTextAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	Drawable drawable;
	Context ctx;
	private ArrayList<String> mStrings;
	//private ArrayList<String> mIcons;
	private ArrayList<String> mCaptions;
	private ArrayList<String> murls;
	private ArrayList<String> mnames;
	
	private int mViewResourceId;
	ImageManager im;
	
	public ImageAndTextAdapter(Context ctx, int viewResourceId,
			ArrayList<String> strings, ArrayList<String> icons,ArrayList<String> captions,ArrayList<String> urls,ArrayList<String> names) {
		super(ctx, viewResourceId, strings);
		this.ctx = ctx;
		mInflater = (LayoutInflater)ctx.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		mStrings = strings;
		//mIcons = icons;
		mCaptions = captions;
		murls =urls;
		mnames = names;
		mViewResourceId = viewResourceId;
		
		im = new ImageManager();
	//getImages();
	}

	public void getImages(){
		for(int i=0;i<murls.size();i++){
		//	drawable = LoadImageFromWeb(mIcons.get(i));
			im.DownloadFromUrl(murls.get(i), mnames.get(i));
		}
		
		
	}

	@Override
	public int getCount() {
		return mStrings.size();
	}

	@Override
	public String getItem(int position) {
		return mStrings.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(mViewResourceId, null);
		
		ImageView iv = (ImageView)convertView.findViewById(R.id.artistImage);
		 //drawable = LoadImageFromWeb(mIcons.get(position));
		im.DownloadFromUrl(murls.get(position), mnames.get(position));
		iv.setImageDrawable(Drawable.createFromPath("/sdcard/"+mnames.get(position)));

		TextView tv = (TextView)convertView.findViewById(R.id.txtName);
		tv.setText(mStrings.get(position));
		
		TextView cap = (TextView)convertView.findViewById(R.id.txtCaption);
		cap.setText(mCaptions.get(position));
		
		
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
	
	
}

