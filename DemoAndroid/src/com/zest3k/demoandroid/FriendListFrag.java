package com.zest3k.demoandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class FriendListFrag extends Fragment {
	private RelativeLayout root;
	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = container.getContext();
		root=(RelativeLayout) inflater.inflate(R.layout.friendlistlayout,container,false);
		return root;
	}
}
