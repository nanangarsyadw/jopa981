package com.zftlive.android.sample.chart.mpchart;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.BaseActivity;

/**
 * Created by Java on 2017/7/1.
 */
public class MPChartActivity extends BaseActivity implements View.OnClickListener {

    private Button mRadarBtn;

    @Override
    public int bindLayout() {
        return R.layout.activity_mpchart_main;
    }

    @Override
    public void initParams(Bundle parms) {
    }

    @Override
    public void initView(View view) {
        //初始化带返回按钮的标题栏
        mWindowTitle.initBackTitleBar("MPChart图表样例集");
        mRadarBtn = (Button) findViewById(R.id.btn_radar);
        mRadarBtn.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_radar:
                attachFirstFragment(new MPChartRadarFragment());
                break;
        }
    }
}
