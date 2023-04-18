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

package com.zftlive.android;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.sample.MainActivity;

/**
 * 程序启动界面
 * @author 曾繁添,曾宪明
 * @version 1.0
 *
 */
public class Launcher extends CommonActivity {

	@Override
	public int bindLayout() {
		return R.layout.activity_launcher;
	}

	@Override
	public void initParams(Bundle parms) {
		
	}

	@Override
	public void initView(View view) {
        //隐藏标题栏
		mWindowTitle.hiddeTitleBar();

		//设置tag
//		BuglyReport.setUserSceneTag(getContext(),17473);
		try {
			String strText = null;

			int i = strText.length();

		}catch (Exception e){
//			BuglyReport.postCatchedException(e);
		}

		//添加动画效果
		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(2000);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				//跳转界面
				getOperation().forward(MainActivity.class);
				finish();
				//右往左推出效果
//				overridePendingTransition(R.anim.anl_push_left_in,R.anim.anl_push_left_out);
				//转动淡出效果1
//				overridePendingTransition(R.anim.anl_scale_rotate_in,R.anim.anl_alpha_out);
				//下往上推出效果
				overridePendingTransition(R.anim.anl_push_bottom_in,R.anim.anl_push_up_out);
			}
		});
		view.setAnimation(animation);
	}
}