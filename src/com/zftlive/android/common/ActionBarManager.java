package com.zftlive.android.common;

import com.zftlive.android.R;

import android.app.ActionBar;
import android.content.Context;

public class ActionBarManager {

	public static void initActionBar(Context mContext,ActionBar actionBar){
		actionBar.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.actionbar_bg));
	}
	
	public static void initBackTitle(Context mContext,ActionBar actionBar){
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
	}
}
