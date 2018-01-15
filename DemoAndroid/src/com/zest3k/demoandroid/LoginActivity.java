package com.zest3k.demoandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		TextView tv=(TextView) findViewById(R.id.loginbtn);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent=new Intent();
				myIntent.setClass(LoginActivity.this, MainActivity.class);
				myIntent.putExtra("zest3k","Abc");
				//myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				LoginActivity.this.startActivity(myIntent);
			}
			
		});
	}

}
