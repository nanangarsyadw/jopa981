package com.zftlive.android.sample.basic;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.tools.ToolLocation;
import com.zftlive.android.tools.ToolPhone;

/**
 * 基本常用操作测试样例
 * @author 曾繁添
 * @version 1.0
 *
 */
public class BasicTestActivity extends BaseActivity implements View.OnClickListener{

	private Button btn_opengps, btn_call;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_basic_test;
	}

	@Override
	public void initView(View view) {
		btn_opengps = (Button) findViewById(R.id.btn_opengps);
		btn_opengps.setOnClickListener(this);

		btn_call = (Button) findViewById(R.id.btn_call);
		btn_call.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_opengps:
			ToolLocation.forceOpenGPS(this);
			break;
		case R.id.btn_call:
			ToolPhone.callPhone(this, "10086");
			break;
		default:
			break;
		}
	}

}
