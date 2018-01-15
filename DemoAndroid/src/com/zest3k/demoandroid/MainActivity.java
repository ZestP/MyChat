package com.zest3k.demoandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	String dots;
	TextView tv;
	int cnt;
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
		super.onCreate(savedInstanceState);
		Log.i("ZX", "onCreate");
		setContentView(R.layout.activity_main);
		//Log.i("AB",Boolean.toString(this==getApplicationContext()));
		tv = (TextView) findViewById(R.id.text1);
		RelativeLayout ll = (RelativeLayout) findViewById(R.id.mod);
		TextView tmp = new TextView(this);
//		tmp.setWidth(49);
//		tmp.setHeight(49);
		tmp.setText("You've already reached the end.");
		tmp.setTextColor(Color.GRAY);
		//tmp.setBackgroundColor(Color.DKGRAY);
		tmp.setGravity(Gravity.CENTER);
		RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		tmp.setLayoutParams(lp);
		ll.addView(tmp);
		Log.i("ZX", String.valueOf(ll.getChildCount()));
		tv.setSelected(true);
		TextView icon = (TextView) findViewById(R.id.icon);
		tv.setText("Zest3k is modifing the text.");
		RelativeLayout eric=(RelativeLayout) findViewById(R.id.ericcard);
		eric.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent=new Intent();
				myIntent.setClass(MainActivity.this, ChatActivity.class);
				myIntent.putExtra("zest3k","Abc");
//				myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				MainActivity.this.startActivityForResult(myIntent,1);
			}
			
		});
		// tv.setWidth(10);
		// icon.setOnClickListener(new OnClickListener()
		// {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// UpdateDots();
		// }
		//
		// });
		icon.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					UpdateDots();
					tv.setTextColor(Color.parseColor("#F5F5DC"));
					tv.setGravity(Gravity.CENTER);
					break;
				case MotionEvent.ACTION_UP:
					tv.setTextColor(Color.parseColor("#66CCFF"));
					break;
				}
				Log.i("ZX", "ABC");
				return true;
			}

		});
		cnt = 1;
		// Timer timer = new Timer();
		// timer.schedule(new TimerTask() {
		// public void run() {
		//
		// Message message = new Message();
		// message.what = 1;
		// mHandler.sendMessage(message);
		// }
		// }, 2000 , 1000);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1&&resultCode==1)
		{
			TextView last=(TextView) findViewById(R.id.ericswords);
			last.setText(data.getStringExtra("lastWords"));
			
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
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
		super.onStop();
		Log.i("ZX", "onStop");
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("ZX", "onDestroy");
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
