package com.zest3k.demoandroid;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MsgAdapter extends BaseAdapter {
	ArrayList<MsgData> al;
	Activity context;
	int resId;
	View srcView;
	public MsgAdapter(Activity ccontext,ArrayList<MsgData> data,int rresId)
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
			convertView=LayoutInflater.from(context).inflate(resId,parent,false);
		}
		float dp2px= context.getResources().getDisplayMetrics().density;
		TextView head=(TextView) (convertView.findViewById(R.id.msg_part_headicon));
		TextView msg=(TextView) (convertView.findViewById(R.id.msg_part_msg));
		if(al.get(position).isMe)
		{
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.LEFT_OF,R.id.msg_part_headicon);
			lp.setMargins((int)(10*dp2px), (int)(10*dp2px), (int)(0*dp2px), (int)(10*dp2px));
			msg.setLayoutParams(lp);
			RelativeLayout.LayoutParams lp2=new RelativeLayout.LayoutParams((int)(40*dp2px),(int)(40*dp2px));
			lp2.addRule(RelativeLayout.ALIGN_PARENT_END);
			lp2.setMargins((int)(10*dp2px), (int)(10*dp2px), (int)(10*dp2px), (int)(10*dp2px));
			head.setLayoutParams(lp2);
		}else {
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.RIGHT_OF,R.id.msg_part_headicon);
			lp.setMargins((int)(0*dp2px), (int)(10*dp2px), (int)(10*dp2px), (int)(10*dp2px));
			msg.setLayoutParams(lp);
			RelativeLayout.LayoutParams lp2=new RelativeLayout.LayoutParams((int)(40*dp2px),(int)(40*dp2px));
			lp2.addRule(RelativeLayout.ALIGN_PARENT_START);
			lp2.setMargins((int)(10*dp2px), (int)(10*dp2px), (int)(10*dp2px), (int)(10*dp2px));
			head.setLayoutParams(lp2);
		}
		msg.setText(al.get(position).msg);
		return convertView;
	}

}
