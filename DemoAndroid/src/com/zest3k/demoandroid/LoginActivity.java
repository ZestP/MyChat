package com.zest3k.demoandroid;

import java.util.Timer;
import java.util.TimerTask;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	TextView tvName,tvPass,tvLogin;
	boolean isQuitting=false;
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getKeyCode()==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_UP)
		{
			if(!isQuitting)
			{
				Toast.makeText(getApplicationContext(),"Back again to quit.",
					     Toast.LENGTH_SHORT).show();
				isQuitting=true;
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						isQuitting=false;
					}}, 3000);  
				return true;
			}else
			{
				finish();
				return true;
			}
		}else {
			return super.dispatchKeyEvent(event);
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		
		

		
		
		tvLogin=(TextView) findViewById(R.id.loginbtn);
		tvName=(TextView) findViewById(R.id.username);
		tvPass=(TextView) findViewById(R.id.passcode);
		tvLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				
				String usrname=tvName.getText().toString();
				String passcode=tvPass.getText().toString();
				if(usrname.equals(""))
				{
					Toast.makeText(getApplicationContext(),"User name is empty.",
						     Toast.LENGTH_LONG).show();
					return;
				}
				if(passcode.equals(""))
				{
					Toast.makeText(getApplicationContext(),"Password is empty.",
						     Toast.LENGTH_LONG).show();
					return;
				}
				if(Validate(usrname,passcode))
				{
					View r=findViewById(R.id.login_root);
					ObjectAnimator animator1 = ObjectAnimator.ofFloat(r, "alpha", 1f, 0f);
					final ObjectAnimator animator2 = ObjectAnimator.ofFloat(r, "scaleX", 1f, 10f);
					final ObjectAnimator animator3 = ObjectAnimator.ofFloat(r, "scaleY", 1f, 10f);
					animator3.setDuration(5000);
					animator2.setDuration(5000);
					animator1.setDuration(5000);
					// 设置属性动画的监听事件（使用AnimatorListenerAdapter可以选择不监听所有事件）
					animator1.addListener(new AnimatorListenerAdapter() {
					    @Override
					    public void onAnimationEnd(Animator animation) {
							String usrname=tvName.getText().toString();
							String passcode=tvPass.getText().toString();
							Intent myIntent=new Intent();
							myIntent.setClass(LoginActivity.this, MainActivity.class);
							myIntent.putExtra(usrname,passcode);
							//myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
							LoginActivity.this.startActivity(myIntent);
							
					    }
					    @Override
					    public void onAnimationStart(Animator animation) {
					        animator2.start(); //在animator1执行的同时执行animator2
					        animator3.start(); 
					    }
					});
					animator1.start();
				}
			}
			
		});
	}
	boolean Validate(String usrname,String psw)
	{
		SharedPreferences sp=getSharedPreferences("login",Context.MODE_APPEND);
		String exist=sp.getString(usrname, "");
		if(!exist.equals(""))
		{
			if(!exist.equals(psw))
			{
				Toast.makeText(getApplicationContext(),"Password mismatch.",
					     Toast.LENGTH_LONG).show();
			}else{
				return true;
			}
		}else{
			Editor ed=sp.edit();
			ed.putString(usrname, psw);
			ed.commit();
			return true;
		}
		return false;
	}

}
