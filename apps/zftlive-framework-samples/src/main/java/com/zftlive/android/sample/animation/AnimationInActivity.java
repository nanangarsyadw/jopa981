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
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 动画启动进入界面
 * @author 曾繁添
 * @version 1.0
 *
 */
public class AnimationInActivity extends CommonActivity implements View.OnClickListener {

	private Spinner mAnimSp;
	private Button mButton1,mButton2;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_animation_in;
	}
	
	@Override
	public void initParams(Bundle parms) {
		
	}

	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
        mAnimSp = (Spinner) findViewById(R.id.animation_sp);
        mButton1=(Button) findViewById(R.id.other_button1);
        mButton2=(Button) findViewById(R.id.other_button2);
		mButton1.setOnClickListener(this);
		mButton2.setOnClickListener(this);
		
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.AnimationInActivity);
//		ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle);
	}

	@Override
	public void doBusiness(Context mContext) {
		String[] ls = getResources().getStringArray(R.array.anim_type);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < ls.length; i++) {
			list.add(ls[i]);
		}
		ArrayAdapter<String> animType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		animType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mAnimSp.setAdapter(animType);
		mAnimSp.setSelection(0);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.other_button1:
			//跳转界面 
			getOperation().forward(AnimationOutActivity.class);
			break;
		case R.id.other_button2:
			//跳转界面 
			getOperation().forward(AnimationOutPullActivity.class);
			break;
		default:
			break;
		}
		settingAnimation();
	}
	
	private void settingAnimation(){
		/**
		 * 设置过场动画:注意此方法只能在startActivity和finish方法之后调用
		 * 第一个参数为第一个Activity离开时的动画，第二参数为所进入的Activity的动画效果
		 */
		switch (mAnimSp.getSelectedItemPosition()) {
		case 0:
			//淡入淡出效果
			overridePendingTransition(R.anim.anl_fade_in, R.anim.anl_fade_out);
			//Android内置的
			//overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			break;
		case 1:
			//放大淡出效果
			overridePendingTransition(R.anim.anl_scale_in,R.anim.anl_alpha_out);
			break;
		case 2:
			//转动淡出效果1
			overridePendingTransition(R.anim.anl_scale_rotate_in,R.anim.anl_alpha_out);
			break;
		case 3:
			//转动淡出效果2
			overridePendingTransition(R.anim.anl_scale_translate_rotate,R.anim.anl_alpha_out);
			break;
		case 4:
			//左上角展开淡出效果
			overridePendingTransition(R.anim.anl_scale_translate,R.anim.anl_alpha_out);
			break;
		case 5:
			//压缩变小淡出效果
			overridePendingTransition(R.anim.anl_hyperspace_in,R.anim.anl_hyperspace_out);
			break;
		case 6:
			//右往左推出效果
			overridePendingTransition(R.anim.anl_push_left_in,R.anim.anl_push_left_out);
			//Android内置的
			//overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			break;
		case 7:
			//下往上推出效果
			overridePendingTransition(R.anim.anl_push_up_in,R.anim.anl_push_up_out);
			break;
		case 8:
			//左右交错效果
			overridePendingTransition(R.anim.anl_slide_left,
					R.anim.anl_slide_right);
			break;
		case 9:
			//放大淡出效果
			overridePendingTransition(R.anim.anl_wave_scale,R.anim.anl_alpha_out);
			break;
		case 10:
			//缩小效果
			overridePendingTransition(R.anim.anl_zoom_enter,
					R.anim.anl_zoom_exit);
			break;
		case 11:
			//上下交错效果
			overridePendingTransition(R.anim.anl_slide_up_in,R.anim.anl_slide_down_out);
			break;
		}
	}
}
