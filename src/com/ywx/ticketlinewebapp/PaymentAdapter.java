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
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PaymentAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	Resources resource;
	Context ctx;
	
	private ArrayList<String> cardName= new ArrayList<String>();

	private ArrayList<String> cardType =new ArrayList<String>();
	private ArrayList<String> cardToken=new ArrayList<String>();
	private ArrayList<String> cardLast =new ArrayList<String>();
	private ArrayList<String> cardExpiry =new ArrayList<String>();
	private ArrayList<String> image=new ArrayList<String>();
	private int mViewResourceId;
	Typeface first;
	Typeface folksFont;

	
	 

		public PaymentAdapter(Context ctx, int viewResourceId,
				ArrayList<String> name, ArrayList<String> type,ArrayList<String> token,ArrayList<String> last,
				ArrayList<String> expire,ArrayList<String> image,
				Resources res,Typeface first,Typeface folksFont) {
		super(ctx, viewResourceId,name);
		this.ctx = ctx;
		this.resource = res;
		mInflater = (LayoutInflater)ctx.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		this.cardName = name;
		this.cardType= type;
		this.cardToken = token;
		this.cardLast = last;
		this.cardExpiry = expire;
		this.image = image;
		this.first = first;
		this.folksFont = folksFont;
		
		mViewResourceId = viewResourceId;
		
		

	}



	@Override
	public int getCount() {
		return cardName.size();
	}

	@Override
	public String getItem(int position) {
		return cardName.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(mViewResourceId, null);
		
		ImageView iv = (ImageView)convertView.findViewById(R.id.imgCheck);
		
	   if(image.get(position)=="unchecked"){
		   iv.setImageDrawable(resource.getDrawable(R.drawable.uncheck));
	   }else{
		   iv.setImageDrawable(resource.getDrawable(R.drawable.check));  
	   }
		

		TextView tv = (TextView)convertView.findViewById(R.id.list_item_title);
		tv.setText(cardName.get(position));
		tv.setTypeface(folksFont); // Folks font
		
		TextView type = (TextView)convertView.findViewById(R.id.txtType);
		type.setText(cardType.get(position));
		type.setTypeface(folksFont); // Folks font
		
		TextView last = (TextView)convertView.findViewById(R.id.txtEnding);
		last.setText("Ending in "+cardLast.get(position));
		last.setTypeface(first); // Arial font
		
		TextView exp = (TextView)convertView.findViewById(R.id.txtExpire);
		exp.setText("Expires "+cardExpiry.get(position));
		exp.setTypeface(first); // Arial font
		
		TextView tok = (TextView)convertView.findViewById(R.id.toptext);
		tok.setText(cardToken.get(position));
		tok.setTypeface(first); // Arial font
		
		return convertView;
	}
	
	
	
	
}

