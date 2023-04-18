/*
 *     Android基础开发个人积累、沉淀、封装、整理共通
 *     Copyright (c) 2016. 曾繁添 <zftlive@163.com>
 *     Github：https://github.com/zengfantian || http://git.oschina.net/zftlive
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.zftlive.android.sample.animation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.widget.container.PullDownRemoveLayout;

/**
 * 动画启动下拉退出界面
 * @author 曾繁添
 * @version 1.0
 *
 */
public class AnimationOutPullActivity extends CommonActivity {

	/**下拉关闭当前Activity顶层容器**/
	protected PullDownRemoveLayout rootView;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_launcher;
	}

	@Override
	public void initParams(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.AnimationInActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle);
		
		//追加右滑关闭activity顶层View
		rootView = (PullDownRemoveLayout) LayoutInflater.from(this).inflate(R.layout.view_pullback_root, null);
		rootView.attachToActivity(this);
	}
}
