package com.zest3k.demoandroid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MsgSvc extends Service {
	private MyBinder mBinder = new MyBinder();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i("ZX","Bind Svc");
		return mBinder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("ZX","Create Svc");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("ZX","Start cmd of Svc");
		return super.onStartCommand(intent, flags, startId);
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("ZX","Destroy Svc");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i("ZX","Unbind Svc");
		return super.onUnbind(intent);

	}
	public class MyBinder extends Binder {  
		public MsgSvc getService() {
			 return MsgSvc.this;
		}
	  
	}
}
