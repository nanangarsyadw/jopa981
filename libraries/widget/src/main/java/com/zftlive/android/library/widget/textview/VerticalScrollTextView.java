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

package com.zftlive.android.library.widget.textview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 垂直方向自动滚动TextView
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class VerticalScrollTextView extends TextView {
	private Paint mPaint;
	private float mX;
	private Paint mPathPaint;
	public int index = 0;
	private List<Map> list;
	public float mTouchHistoryY;
	private int mY;
	private float middleY;// y轴中间
	private static final int DY = 40; // 每一行的间隔

	public VerticalScrollTextView(Context context) {
		super(context);
		init();
	}

	public VerticalScrollTextView(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}

	public VerticalScrollTextView(Context context, AttributeSet attr, int i) {
		super(context, attr, i);
		init();
	}

	private void init() {
		setFocusable(true);
		if (list == null) {
			list = new ArrayList<Map>();
			Map sen = new HashMap();
			list.add(0, sen);
		}

		// 非高亮部分
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(30);
		mPaint.setColor(Color.BLACK);
		mPaint.setTypeface(Typeface.SERIF);

		// 高亮部分 当前歌词
		mPathPaint = new Paint();
		mPathPaint.setAntiAlias(true);
		mPathPaint.setColor(Color.RED);
		mPathPaint.setTextSize(34);
		mPathPaint.setTypeface(Typeface.SANS_SERIF);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(0xEFeffff);
		Paint p = mPaint;
		Paint p2 = mPathPaint;
		p.setTextAlign(Paint.Align.CENTER);
		if (index == -1)
			return;
		p2.setTextAlign(Paint.Align.CENTER);
		// 先画当前行，之后再画他的前面和后面，这样就保持当前行在中间的位置
		canvas.drawText(String.valueOf(list.get(index).get("label")), mX,
				middleY, p2);
		float tempY = middleY;
		// 画出本句之前的句子
		for (int i = index - 1; i >= 0; i--) {
			tempY = tempY - DY;
			if (tempY < 0) {
				break;
			}
			canvas.drawText(String.valueOf(list.get(i).get("label")), mX,
					tempY, p);
		}
		tempY = middleY;
		// 画出本句之后的句子
		for (int i = index + 1; i < list.size(); i++) {
			// 往下推移
			tempY = tempY + DY;
			if (tempY > mY) {
				break;
			}
			canvas.drawText(String.valueOf(list.get(i).get("label")), mX,
					tempY, p);
		}
	}

	protected void onSizeChanged(int w, int h, int ow, int oh) {
		super.onSizeChanged(w, h, ow, oh);
		mX = w * 0.5f;
		mY = h;
		middleY = h * 0.5f;
	}

	public long updateIndex(int index) {
		if (index == -1)
			return -1;
		this.index = index;
		return index;
	}

	public List<Map> getList() {
		return list;
	}

	public void setList(List<Map> list) {
		this.list = list;
	}

	public void updateUI() {
		new Thread(new updateThread()).start();
	}

	class updateThread implements Runnable {
		long time = 2000; // 开始 的时间，不能为零，否则前面几句歌词没有显示出来
		int i = 0;

		public void run() {
			while (true) {
				long sleeptime = updateIndex(i);
				time += sleeptime;
				mHandler.post(mUpdateResults);
				if (sleeptime == -1)
					return;
				try {
					Thread.sleep(time);
					i++;
					if (i == getList().size())
						i = 0;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	Handler mHandler = new Handler();
	Runnable mUpdateResults = new Runnable() {
		public void run() {
			invalidate(); // 更新视图
		}
	};
}