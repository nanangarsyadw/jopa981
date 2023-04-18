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

package com.zftlive.android.sample.progressbar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.widget.progressbar.NumberProgressBar;
import com.zftlive.android.library.widget.progressbar.NumberProgressBar.OnProgressBarListener;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 带数字的进度条样例
 * @author 曾繁添
 * @version 1.0
 *
 */
public class NumberPbActivity extends CommonActivity implements OnProgressBarListener{

    private Timer timer;
    private NumberProgressBar bnp;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_numberpb;
	}

	@Override
	public void initParams(Bundle parms) {

	}

	@Override
	public void initView(View view) {
		bnp = (NumberProgressBar)findViewById(R.id.numberbar1);
        bnp.setOnProgressBarListener(this);
	}

	@Override
	public void doBusiness(Context mContext) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bnp.incrementProgressBy(1);
                    }
                });
            }
        }, 1000, 100);
        
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.NumberPbActivity);
//      ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
		mWindowTitle.initBackTitleBar(strCenterTitle);
	}

	@Override
	protected void onDestroy() {
		timer.cancel();
		super.onDestroy();
	}

	@Override
	public void onProgressChange(int current, int max) {
		if(current == max) {
            Toast.makeText(getApplicationContext(), "结束进度", Toast.LENGTH_SHORT).show();
            bnp.setProgress(0);
        }
	}

}
