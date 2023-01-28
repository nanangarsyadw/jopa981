package com.zftlive.android.sample.progressbar;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zftlive.android.R;
import com.zftlive.android.library.base.BaseActivity;
import com.zftlive.android.library.common.ActionBarManager;
import com.zftlive.android.library.widget.progressbar.NumberProgressBar;
import com.zftlive.android.library.widget.progressbar.NumberProgressBar.OnProgressBarListener;

/**
 * 带数字的进度条样例
 * @author 曾繁添
 * @version 1.0
 *
 */
public class NumberPbActivity extends BaseActivity implements OnProgressBarListener{

    private Timer timer;
    private NumberProgressBar bnp;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_numberpb;
	}

	@Override
	public View bindView() {
		return null;
	}

	@Override
	public void initParms(Bundle parms) {

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
        initBackTitleBar(strCenterTitle);
	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {
		timer.cancel();
	}

	@Override
	public void onProgressChange(int current, int max) {
		if(current == max) {
            Toast.makeText(getApplicationContext(), getString(R.string.finish), Toast.LENGTH_SHORT).show();
            bnp.setProgress(0);
        }
	}

}
