package com.zftlive.android.sample.scrollview;

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
import com.zftlive.android.base.BaseActivity;

/**
 * Stretch ScrollView demo @link https://github.com/MarkMjw/PullScrollView
 * 
 * @author 曾繁添
 * @version 1.0
 */
public class StretchViewActivity extends BaseActivity {
	private TableLayout mMainLayout;

	@Override
	public int bindLayout() {
		return R.layout.activity_stretch_view;
	}

	@Override
	public void initView(View view) {
		mMainLayout = (TableLayout) findViewById(R.id.table_layout);
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

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

	}
}
