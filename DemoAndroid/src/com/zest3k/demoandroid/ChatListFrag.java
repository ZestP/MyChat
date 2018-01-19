package com.zest3k.demoandroid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

public class ChatListFrag extends Fragment {
	private RelativeLayout root;
	private ListView ls;
	private Context context;
	ArrayList<ChatCardData> al;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context=container.getContext();
		root=(RelativeLayout) inflater.inflate(R.layout.chatlistlayout,container,false);
		InitChatList();
		return root;
	}
	
	
	private void InitChatList()
	{
		if(al==null)
		{
			al = new ArrayList<ChatCardData>();
		}
		ls = (ListView) root.findViewById(R.id.chat_list);
		MyAdapter ba = new MyAdapter((Activity) context, al, R.layout.chat_part);
		ls.setAdapter(ba);
		ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				view.getTag();
				Intent myIntent = new Intent();
				myIntent.putExtra("no", position);
				myIntent.setAction("GOTO_CHAT_ACTIVITY");
				myIntent.setClass(context, ChatActivity.class);
				context.startActivity(myIntent);
			}

		});
		Log.i("ZX", "setAdapter");
	}
	
	public void InitChatCardData()
	{
		al = new ArrayList<ChatCardData>();
		for (int i = 0; i < 100; i++) {
			ChatCardData tmp = new ChatCardData();
			tmp.chatContent = "TestContent" + i;
			tmp.time = new Date().getTime();
			al.add(tmp);
		}
		Log.i("ZX", "Init");
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		UpdateData();
		super.onStart();
	}

	private void UpdateData()
	{
		SortData();
	}
	public void UpdateData(int position,String chatContent,long time)
	{
		al.get(position).chatContent = chatContent;
		al.get(position).time = time;
		SortData();
	}
	
	private void SortData()
	{
		Collections.sort(al, new Comparator<ChatCardData>() {
			@Override
			public int compare(ChatCardData lhs, ChatCardData rhs) {
				// TODO Auto-generated method stub
				if (lhs.time < rhs.time)
					return 1;
				return -1;
			}

		});
	}
}
