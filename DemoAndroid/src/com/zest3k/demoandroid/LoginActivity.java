package com.zest3k.demoandroid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	TextView tvName, tvPass, tvLogin;
	boolean isQuitting = false;

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

	private MsgSvc mService;
	private MsgSvc.MyBinder mBinder;
	private boolean mIsBind = false;
	private ServiceConnection conn = new ServiceConnection() {

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

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub

		super.onStop();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i("ZX", "ON Stop0");
//		this.unbindService(conn);
		super.onPause();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		
		Intent startintent = new Intent(this, MsgSvc.class);
		startService(startintent);
		
		Intent bindintent = new Intent(this, MsgSvc.class);
		bindService(bindintent, conn, Context.BIND_AUTO_CREATE);

		tvLogin = (TextView) findViewById(R.id.loginbtn);
		tvName = (TextView) findViewById(R.id.username);
		tvPass = (TextView) findViewById(R.id.passcode);
		tvLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String usrname = tvName.getText().toString();
				String passcode = tvPass.getText().toString();
				if (usrname.equals("")) {
					Toast.makeText(getApplicationContext(), "User name is empty.", Toast.LENGTH_LONG).show();
					return;
				}
				if (passcode.equals("")) {
					Toast.makeText(getApplicationContext(), "Password is empty.", Toast.LENGTH_LONG).show();
					return;
				}
				if (Validate(usrname, passcode)) {
					View r = findViewById(R.id.login_root);
					ObjectAnimator animator1 = ObjectAnimator.ofFloat(r, "alpha", 1f, 0f);
					final ObjectAnimator animator2 = ObjectAnimator.ofFloat(r, "scaleX", 1f, 10f);
					final ObjectAnimator animator3 = ObjectAnimator.ofFloat(r, "scaleY", 1f, 10f);
					animator3.setDuration(500);
					animator2.setDuration(500);
					animator1.setDuration(500);
					// 设置属性动画的监听事件（使用AnimatorListenerAdapter可以选择不监听所有事件）
					animator1.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							String usrname = tvName.getText().toString();
							String passcode = tvPass.getText().toString();
							Intent myIntent = new Intent();
							myIntent.setClass(LoginActivity.this, MainActivity.class);
							myIntent.putExtra(usrname, passcode);
							myIntent.putExtra("needRefresh", true);
							// myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
							LoginActivity.this.startActivity(myIntent);

						}

						@Override
						public void onAnimationStart(Animator animation) {
							animator2.start(); // 在animator1执行的同时执行animator2
							animator3.start();
						}
					});
					animator1.start();
				}
			}

		});
	}

	// 本地版验证

	boolean Validate(String usrname, String psw) {
		SharedPreferences sp = getSharedPreferences("login", Context.MODE_APPEND);
		String exist = sp.getString(usrname, "");
		if (!exist.equals("")) {
			if (!exist.equals(psw)) {
				Toast.makeText(getApplicationContext(), "Password mismatch.", Toast.LENGTH_LONG).show();
			} else {
				return true;
			}
		} else {
			Editor ed = sp.edit();
			ed.putString(usrname, psw);
			ed.commit();
			return true;
		}
		return false;
	}

	// 网络版验证
	// boolean Validate(String usrname, String psw) {
	// String exist = UploadData(usrname);
	// AsyncTask<String, Integer, String> at = new AsyncTask<String, Integer,
	// String>() {
	//
	// @Override
	// protected String doInBackground(String... params) {
	// // TODO Auto-generated method stub
	// return UploadData(params[0]);
	//
	// }
	// };
	// if (!exist.equals("")) {
	// if (!exist.equals(psw)) {
	// Toast.makeText(getApplicationContext(), "Password mismatch.",
	// Toast.LENGTH_LONG).show();
	// } else {
	// return true;
	// }
	// } else {
	// // Editor ed=sp.edit();
	// // ed.putString(usrname, psw);
	// // ed.commit();
	// return true;
	// }
	// return false;
	// }

	String UploadData(String message) {
		Socket socket = null;
		String result = "";
		try {

			// 创建Socket
			socket = new Socket("10.25.52.6", 8087);
			// 向服务器发送消息
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
					true);
			out.println(message);

			// 接收来自服务器的消息
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msg = br.readLine();

			if (msg != null) {
				result = msg;
			} else {
				result = "";
			}
			// 关闭流
			out.close();
			br.close();
			// 关闭Socket
			socket.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
}
