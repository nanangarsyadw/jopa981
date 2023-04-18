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

package com.zftlive.android.sample.scrollview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;

/**
 * Stretch ScrollView demo @link https://github.com/MarkMjw/PullScrollView
 * 
 * @author 曾繁添
 * @version 1.0
 */
public class StretchViewActivity extends CommonActivity {
	private TableLayout mMainLayout;

	@Override
	public int bindLayout() {
		return R.layout.activity_stretch_view;
	}

	@Override
	public void initParams(Bundle parms) {
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		mMainLayout = (TableLayout) findViewById(R.id.table_layout);
        //初始化带返回按钮的标题栏
//  		ActionBarManager.initBackTitle(this, getActionBar(), this.getClass().getSimpleName());
		mWindowTitle.initBackTitleBar(this.getClass().getSimpleName());
	}

	@Override
	public void doBusiness(Context mContext) {
		TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
				TableRow.LayoutParams.MATCH_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		layoutParams.leftMargin = 30;
		layoutParams.bottomMargin = 10;
		layoutParams.topMargin = 10;

		for (int i = 0; i < 40; i++) {
			TableRow tableRow = new TableRow(this);
			TextView textView = new TextView(this);
			textView.setText("Test stretch scroll view " + i);
			textView.setTextSize(20);
			textView.setPadding(15, 15, 15, 15);

			tableRow.addView(textView, layoutParams);
			if (i % 2 != 0) {
				tableRow.setBackgroundColor(Color.LTGRAY);
			} else {
				tableRow.setBackgroundColor(Color.WHITE);
			}

			final int n = i;
			tableRow.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(StretchViewActivity.this, "Click item " + n,
							Toast.LENGTH_SHORT).show();
				}
			});

			mMainLayout.addView(tableRow);
		}
	}
}
