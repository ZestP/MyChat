package com.zest3k.demoandroid;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	ArrayList<ChatCardData> al;
	Activity context;
	int resId;
	View srcView;
	public MyAdapter(Activity ccontext,ArrayList<ChatCardData> data,int rresId)
	{
		al=data;
		resId=rresId;
		context=ccontext;
				
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return al.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return al.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null)
		{
			convertView=newView(parent);
		}
		
		bindView(position,convertView);
		return convertView;
	}
	
	private View newView(ViewGroup parent)
	{
		return LayoutInflater.from(context).inflate(resId,parent,false);
	}
	private void bindView(int position,View convertView)
	{
		TextView tvw=(TextView) (convertView.findViewById(R.id.ericsword));
		ViewHolder vh=new ViewHolder();
		vh.tv=tvw;
		convertView.setTag(vh);
		
		tvw.setText(al.get(position).chatContent);
		
	}
	class ViewHolder
	{
		TextView tv;
	}
}