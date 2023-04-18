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
 *
 */

package com.zftlive.android.library.dynamicpage.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * 自定义ViewPager
 */
public class CustomViewPager extends ViewPager {

	private float mLastMotionX;
	private float mLastMotionY;
	private float mInitialMotionX;
	private float mInitialMotionY;

	private int mTouchSlop;

	private int mActivePointerId = -1;

	public CustomViewPager(Context context) {
		super(context);

		init(context);
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(context);
	}

	private void init(Context context) {
		final ViewConfiguration configuration = ViewConfiguration.get(context);
		mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int hSize = MeasureSpec.getSize(heightMeasureSpec);
		int hMode = MeasureSpec.getMode(heightMeasureSpec);
		if (hSize == ViewGroup.LayoutParams.WRAP_CONTENT || hMode == MeasureSpec.AT_MOST) {
			int height = 0;
			// 下面遍历所有child的高度
			for (int i = 0; i < getChildCount(); i++) {
				int hMeasureSpec;
				View child = getChildAt(i);
				int h = child.getLayoutParams().height;

				if (h > 0) {
					hMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
				} else {
					hMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
				}

				child.measure(widthMeasureSpec, hMeasureSpec);
				int H = child.getMeasuredHeight();
				if (H > height) {
					height = H;
				}
			}
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
					MeasureSpec.EXACTLY);
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}


	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return super.onTouchEvent(arg0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean isDragging = false;
		switch (ev.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				mLastMotionX = mInitialMotionX = ev.getX();
				mLastMotionY = mInitialMotionY = ev.getY();
				mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
				break;
			case MotionEvent.ACTION_MOVE:
				final int activePointerId = mActivePointerId;
				if (activePointerId == -1) {
					break;
				}

				final int pointerIndex = MotionEventCompat.findPointerIndex(ev, activePointerId);
				final float x = MotionEventCompat.getX(ev, pointerIndex);
				final float dx = x - mLastMotionX;
				final float xDiff = Math.abs(dx);
				final float y = MotionEventCompat.getY(ev, pointerIndex);
				final float yDiff = Math.abs(y - mInitialMotionY);

				if (xDiff > mTouchSlop && xDiff * 0.5f > yDiff) {
					if (getParent() != null) {
						getParent().requestDisallowInterceptTouchEvent(true);
					}
					mLastMotionX = dx > 0 ? mInitialMotionX + mTouchSlop : mInitialMotionX - mTouchSlop;
					mLastMotionY = y;

					isDragging = true;
				}
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				mActivePointerId = -1;
				break;
		}
		boolean result = super.onInterceptTouchEvent(ev);
		return isDragging ? true : result;
	}
}
