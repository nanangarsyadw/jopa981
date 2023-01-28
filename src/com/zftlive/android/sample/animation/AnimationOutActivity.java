package com.zftlive.android.sample.animation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.zftlive.android.R;
import com.zftlive.android.library.base.BaseActivity;
import com.zftlive.android.library.common.ActionBarManager;
import com.zftlive.android.library.widget.SwipeBackLayout;

/**
 * 动画启动右滑退出界面
 * @author 曾繁添
 * @version 1.0
 *
 */
public class AnimationOutActivity extends BaseActivity {

	/**右滑关闭当前Activity顶层容器**/
	protected SwipeBackLayout rootView;
	
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
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
        initBackTitleBar(strCenterTitle);
		
		//追加右滑关闭activity顶层View
		rootView = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.view_swipeback_root, null);
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
