package com.geili.adapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.geili.R;
import com.squareup.picasso.Picasso;


public class HSVAdapter extends BaseAdapter {

	private List<Map<String,Object>> list;
	private Context context;
	public HSVAdapter(Context context){
		this.context=context;
		this.list=new ArrayList<Map<String,Object>>();
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Map<String,Object> getItem(int location) {
		return list.get(location);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public void addObject(Map<String,Object> map){
		list.add(map);
		notifyDataSetChanged();
	}
	@Override
	public View getView(int location, View arg1, ViewGroup arg2) {
		View view = LayoutInflater.from(context).inflate(R.layout.hsv,null);
		ImageView image=(ImageView)view.findViewById(R.id.movie_image);
		image.setScaleType(ScaleType.FIT_CENTER);

		Map<String,Object> map=getItem(location);
		String urlString = map.get("image").toString();
		Log.i("url", urlString);
		Picasso.with(context).load(urlString).into(image);
		return view;
	}

}
