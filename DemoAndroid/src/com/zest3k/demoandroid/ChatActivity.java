package com.zest3k.demoandroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
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
import android.os.Handler;
import android.os.Message;
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
	EditText sendingText, sendingText2;
	ListView ls;
	NotificationManager nm;
	ArrayList<MsgData> al;
	MsgAdapter ba;
	int position = -1;
	String filePath = "chatlog.txt";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("ZX", "onStart1");
		Intent intent = getIntent();
		position = intent.getIntExtra("no", -1);

		ListView ls = (ListView) findViewById(R.id.msg_list);
		al = new ArrayList<MsgData>();
		File file = new File(ChatActivity.this.getFilesDir() + filePath);
		FileInputStream fileInputStream = null;
		ObjectInputStream mOOS;
		if (file.exists()) {
			Log.i("ZX", "FileExists");
			try {
				fileInputStream = new FileInputStream(ChatActivity.this.getFilesDir() + filePath);
				mOOS = new ObjectInputStream(fileInputStream);
				al = (ArrayList<MsgData>) mOOS.readObject();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (fileInputStream != null) {
					try {
						fileInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} else {
			Log.i("ZX", "FileNotExists");
			for (int i = 0; i < 100; i++) {
				MsgData tmp = new MsgData();
				tmp.msg = "TestContent" + i;
				tmp.isMe = ((i % 2 == 0) ? true : false);
				tmp.time = new Date().getTime();
				al.add(tmp);
			}
		}
		ba = new MsgAdapter(this, al, R.layout.msg_part);
		ls.setAdapter(ba);

		sendingText = (EditText) findViewById(R.id.sendingtext);
		TextView send = (TextView) findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				MsgData tmp = new MsgData();
				tmp.msg = sendingText.getText().toString();
				tmp.isMe = true;
				tmp.time = new Date().getTime();
				al.add(tmp);
				ba.notifyDataSetChanged();

				WriteFile();

				sendingText.setText("");

			}

		});

		/// OPPOSITE

		sendingText2 = (EditText) findViewById(R.id.sendingtext2);
		TextView send2 = (TextView) findViewById(R.id.send2);
		send2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				MsgData tmp = new MsgData();
				tmp.msg = sendingText2.getText().toString();
				tmp.isMe = false;
				tmp.time = new Date().getTime();
				al.add(tmp);
				ba.notifyDataSetChanged();

				WriteFile();

				sendingText2.setText("");

			}

		});
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

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
			Intent myIntent = new Intent();
			String lastWords = al.get(al.size() - 1).msg;
			myIntent.putExtra("time", al.get(al.size() - 1).time);
			myIntent.putExtra("lastWords", lastWords);
			myIntent.putExtra("no", position);
			myIntent.setAction("GOTO_MAIN_ACTIVITY");
			ChatActivity.this.sendBroadcast(myIntent);
			ChatActivity.this.finish();
			return true;
		}
		return super.dispatchKeyEvent(event);

	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(ChatActivity.this, "Save successful!", Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	private void WriteFile() {

		Runnable r = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				FileOutputStream fileOutputStream = null;
				ObjectOutputStream mOOS = null;
				try {
					fileOutputStream = new FileOutputStream(ChatActivity.this.getFilesDir() + filePath);
					mOOS = new ObjectOutputStream(fileOutputStream);
					mOOS.writeObject(al);

				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (fileOutputStream != null) {
						try {
							fileOutputStream.close();
							Message m = new Message();
							m.what = 1;
							mHandler.sendMessage(m);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		Thread th = new Thread(r);
		th.start();

	}
}
