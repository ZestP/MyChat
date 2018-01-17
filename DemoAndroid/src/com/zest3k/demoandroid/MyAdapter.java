package com.zest3k.demoandroid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
		View convertView=LayoutInflater.from(context).inflate(resId,parent,false);
		ViewHolder vh=new ViewHolder();
		TextView tvw=(TextView) (convertView.findViewById(R.id.ericsword));
		TextView tvTime=(TextView) (convertView.findViewById(R.id.chat_card_time));
		vh.tv_chatContent=tvw;
		vh.tv_chatCardTime=tvTime;
		convertView.setTag(vh);
		return convertView;

	}
	private void bindView(int position,View convertView)
	{
		((ViewHolder)convertView.getTag()).tv_chatContent.setText(al.get(position).chatContent);
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date date=new Date(al.get(position).time);
		if(isToday(date))
		{
			sdf = new SimpleDateFormat("HH:mm"); 
		}else {
			sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		}
		String str = sdf.format(date);  
		((ViewHolder)convertView.getTag()).tv_chatCardTime.setText(str);
	}
	class ViewHolder
	{
		TextView tv_chatContent;
		TextView tv_chatCardTime;
	}
	private boolean isToday(Date d)
	{
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		String dn=sdf.format(new Date());
		String dc=sdf.format(d);
		return dn.equals(dc);
	}
	public void UpdateData(ArrayList<ChatCardData> myal)
	{
		al=myal;
	}
}