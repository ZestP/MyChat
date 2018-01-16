package com.zest3k.demoandroid;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity {
	EditText sendingText;
	NotificationManager nm;
	int position=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		sendingText=(EditText) findViewById(R.id.sendingtext);

		Intent intent=getIntent();
		
		position=intent.getIntExtra("no", -1);
		ListView ls=(ListView) findViewById(R.id.msg_list);
		ArrayList<MsgData> al=new ArrayList<MsgData>();
		for(int i=0;i<100;i++)
		{
			MsgData tmp=new MsgData();
			tmp.msg="TestContent"+i;
			tmp.isMe=((i%2==0)?true:false);
			al.add(tmp);
		}
		MsgAdapter ba=new MsgAdapter(this,al,R.layout.msg_part);
		ls.setAdapter(ba);

		
		
		
		TextView send=(TextView) findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent=new Intent();
				String lastWords=sendingText.getText().toString();
				
				myIntent.putExtra("lastWords",lastWords);
				myIntent.putExtra("no",position);
				ChatActivity.this.setResult(1, myIntent);
				ChatActivity.this.finish();
			}
			
		});
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("ZX", "onStart1");
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.i("ZX", "onRestart1");
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("ZX", "onResume1");
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("ZX", "onPause1");
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("ZX", "onStop1");
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("ZX", "onDestroy1");
	}
	
}
