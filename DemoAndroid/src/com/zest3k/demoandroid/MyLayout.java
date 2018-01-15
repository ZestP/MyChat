package com.zest3k.demoandroid;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MyLayout extends LinearLayout {

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.onInterceptTouchEvent(ev);
	}

	public MyLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

}
