package com.zest3k.demoandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends Activity {
	EditText sendingText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		sendingText=(EditText) findViewById(R.id.sendingtext);
		
		TextView send=(TextView) findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent=new Intent();
				String lastWords=sendingText.getText().toString();
				myIntent.putExtra("lastWords",lastWords);
				ChatActivity.this.setResult(1, myIntent);
				ChatActivity.this.finish();
			}
			
		});
		
	}
}
