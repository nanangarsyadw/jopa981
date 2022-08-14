package com.zftlive.android.sample.scrollview;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.common.ActionBarManager;
import com.zftlive.android.view.scrollview.PullScrollView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Pull down ScrollView @link https://github.com/MarkMjw/PullScrollView
 * 
 * @author 曾繁添
 * @version 1.0
 */
public class PulldownViewActivity extends BaseActivity implements
		PullScrollView.OnTurnListener {
	private PullScrollView mScrollView;
	private ImageView mHeadImg;
	private TableLayout mMainLayout;

	@Override
	public int bindLayout() {
		return R.layout.activity_pull_down_scrollview;
	}

	@Override
	public void initView(View view) {
		mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
		mHeadImg = (ImageView) findViewById(R.id.background_img);
		mMainLayout = (TableLayout) findViewById(R.id.table_layout);
		mScrollView.setHeader(mHeadImg);
		mScrollView.setOnTurnListener(this);
        //初始化带返回按钮的标题栏
  		ActionBarManager.initBackTitle(this, getActionBar(), this.getClass().getSimpleName());  
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

		for (int i = 0; i < 30; i++) {
			TableRow tableRow = new TableRow(this);
			TextView textView = new TextView(this);
			textView.setText("Test pull down scroll view " + i);
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
					Toast.makeText(PulldownViewActivity.this,
							"Click item " + n, Toast.LENGTH_SHORT).show();
				}
			});

			mMainLayout.addView(tableRow);
		}
	}

	/**
	 * 翻转事件监听器
	 */
	@Override
	public void onTurn() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

	}

}
