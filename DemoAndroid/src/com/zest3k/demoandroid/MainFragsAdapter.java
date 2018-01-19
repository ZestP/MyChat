package com.zest3k.demoandroid;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;





public class MainFragsAdapter extends FragmentPagerAdapter {
	private List<Fragment> mFragments;
	public MainFragsAdapter(FragmentManager fm,List<Fragment> fragments) {
		super(fm);
		// TODO Auto-generated constructor stub
		mFragments=fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return mFragments.get(arg0);	
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFragments.size();
	}

}
