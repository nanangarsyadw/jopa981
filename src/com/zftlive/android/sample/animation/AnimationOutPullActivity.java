package com.zftlive.android.sample.animation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.zftlive.android.R;
import com.zftlive.android.library.base.BaseActivity;
import com.zftlive.android.library.common.ActionBarManager;
import com.zftlive.android.library.widget.PullDownRemoveLayout;

/**
 * 动画启动下拉退出界面
 * @author 曾繁添
 * @version 1.0
 *
 */
public class AnimationOutPullActivity extends BaseActivity {

	/**下拉关闭当前Activity顶层容器**/
	protected PullDownRemoveLayout rootView;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_launcher;
	}
	
	@Override
	public View bindView() {
		return null;
	}

	@Override
	public void initParms(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.AnimationInActivity);
		ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		
		//追加右滑关闭activity顶层View
		rootView = (PullDownRemoveLayout) LayoutInflater.from(this).inflate(R.layout.view_pullback_root, null);
		rootView.attachToActivity(this);
	}

	@Override
	public void doBusiness(Context mContext) {

	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

	}
}
