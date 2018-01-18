package com.zest3k.demoandroid;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
	String dots;
	TextView tv;
	ListView ls;
	int cnt;
	ArrayList<ChatCardData> al;

	
	private MsgSvc mService;
	private MsgSvc.MyBinder mBinder;
	private boolean mIsBind = false;
	private ServiceConnection conn=new ServiceConnection()
	{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			Log.i("ZX", "ONSERVICECONN");
			mBinder = (MsgSvc.MyBinder) service;
			mService = mBinder.getService();
			mIsBind = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			Log.i("ZX", "ONSERVICEDISCONN");
			mIsBind = false;
		}

	};
	private BroadcastReceiver br = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.i("ZX", "OnBroadcastReceive");
			ListView ls = (ListView) findViewById(R.id.chat_list);
			MyAdapter ma = (MyAdapter) ls.getAdapter();
			int position = intent.getIntExtra("no", -1);
			if (position >= 0) {
				al.get(position).chatContent = intent.getStringExtra("lastWords");
				al.get(position).time = intent.getLongExtra("time", 0);
				Log.i("ZX", "Change" + position + " " + intent.getStringExtra("lastWords"));
			}
			//ma.UpdateData(al);
			ma.notifyDataSetChanged();
		}

	};

	private void RegisterReceiver() {
		IntentFilter inf = new IntentFilter();
		inf.addAction("GOTO_MAIN_ACTIVITY");
		this.registerReceiver(br, inf);
	}

	private void UnregisterReceiver() {
		this.unregisterReceiver(br);
	}

	boolean isQuitting = false, needRefresh = false;
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				UpdateDots();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent bindintent=new Intent(this,MsgSvc.class);
		bindService(bindintent,conn,Context.BIND_AUTO_CREATE);
		
		super.onCreate(savedInstanceState);
		Log.i("ZX", "onCreate");
		setContentView(R.layout.activity_main);
		
		
		
		
		
		RegisterReceiver();
		// ListView ls = (ListView) findViewById(R.id.chat_list);
		// TextView tmp = new TextView(this);
		//// tmp.setWidth(49);
		//// tmp.setHeight(49);
		// tmp.setText("You've already reached the end.");
		// tmp.setTextColor(Color.GRAY);
		// //tmp.setBackgroundColor(Color.DKGRAY);
		// tmp.setGravity(Gravity.CENTER);
		// RelativeLayout.LayoutParams lp=new
		// RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		// lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		// tmp.setLayoutParams(lp);

		// RelativeLayout eric=(RelativeLayout) findViewById(R.id.ericcard);

		Intent intent = getIntent();
		needRefresh = intent.getBooleanExtra("needRefresh", false);

		if (needRefresh) {
			al = new ArrayList<ChatCardData>();
			for (int i = 0; i < 100; i++) {
				ChatCardData tmp = new ChatCardData();
				tmp.chatContent = "TestContent" + i;
				tmp.time = new Date().getTime();
				al.add(tmp);
			}
			Log.i("ZX", "Init");
		}
		// SimpleAdapter sa=new SimpleAdapter(null, null, cnt, null, null);

		ls = (ListView) findViewById(R.id.chat_list);
		MyAdapter ba = new MyAdapter(this, al, R.layout.chat_part);
		ls.setAdapter(ba);
		ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				view.getTag();
				Intent myIntent = new Intent();
				
				myIntent.putExtra("no", position);
				myIntent.setAction("GOTO_CHAT_ACTIVITY");
				//MainActivity.this.startActivityForResult(myIntent, 1);
				myIntent.setClass(MainActivity.this, ChatActivity.class);
		
				MainActivity.this.startActivity(myIntent);
			}

		});
		Log.i("ZX", "setAdapter");

		// ls.addView(tmp);
		/*
		 * eric.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method stub
		 * Intent myIntent=new Intent(); myIntent.setClass(MainActivity.this,
		 * ChatActivity.class); myIntent.putExtra("zest3k","Abc");
		 * MainActivity.this.startActivityForResult(myIntent,1); }
		 * 
		 * });
		 */
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			if (!isQuitting) {
				Toast.makeText(getApplicationContext(), "Back again to quit.", Toast.LENGTH_SHORT).show();
				isQuitting = true;
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						isQuitting = false;
					}
				}, 3000);
				return true;
			} else {
				finish();
				return true;
			}
		} else {
			return super.dispatchKeyEvent(event);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
//		Log.i("ACT", "GETRES");
		super.onActivityResult(requestCode, resultCode, data);
//		if (requestCode == 1 && resultCode == 1) {
//			// TextView last=(TextView) findViewById(R.id.ericswords);
//			// last.setText(data.getStringExtra("lastWords"));
//			ListView ls = (ListView) findViewById(R.id.chat_list);
//			MyAdapter ma = (MyAdapter) ls.getAdapter();
//			int position = data.getIntExtra("no", -1);
//			if (position >= 0) {
//				al.get(position).chatContent = data.getStringExtra("lastWords");
//				al.get(position).time = data.getLongExtra("time", 0);
//				Log.i("ZX", "Change" + position + " " + data.getStringExtra("lastWords"));
//			}
//			ma.notifyDataSetChanged();
//		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		

		Collections.sort(al, new Comparator<ChatCardData>() {

			@Override
			public int compare(ChatCardData lhs, ChatCardData rhs) {
				// TODO Auto-generated method stub
				if (lhs.time < rhs.time)
					return 1;
				return -1;
			}

		});
		MyAdapter ma = (MyAdapter) ls.getAdapter();

		Log.i("ZX", "onStart");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.i("ZX", "onRestart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		View r = findViewById(R.id.rootLayout);
		ObjectAnimator animator1 = ObjectAnimator.ofFloat(r, "alpha", 0f, 1f);
		animator1.setDuration(1500);
		animator1.start();
		Log.i("ZX", "onResume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
		super.onPause();
		Log.i("ZX", "onPause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		MainActivity.this.unbindService(conn);
		super.onStop();
		Log.i("ZX", "onStop");

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("ZX", "onDestroy");
		UnregisterReceiver();
	}

	public void UpdateDots() {
		dots = "";
		for (int i = 0; i < cnt; i++) {
			dots += '.';
		}
		if (cnt < 3)
			cnt++;
		else
			cnt = 1;
		tv.setText(dots);
	}

	public class DotUpdater implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}

	}
}
