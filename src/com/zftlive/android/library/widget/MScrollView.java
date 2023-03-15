package com.zftlive.android.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * 自定义ScrollView，可以监听滚动高度
 * @author 曾繁添
 * @version 1.0
 *
 */
public class MScrollView extends ScrollView {

	public MScrollView(Context context) {
		super(context);
	}

	public MScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	/**
	 * 设置滚动接口
	 * 
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		Log.d(VIEW_LOG_TAG, "l=" + l + " t=" + t + " oldl=" + oldl + " oldt="
				+ oldt);
		if (onScrollListener != null) {
			onScrollListener.onScroll(t);
		}
	}
	
	/**
	 * 滚动的回调接口
	 */
	public interface OnScrollListener {
		/**
		 * 回调方法， 返回MyScrollView滑动的Y方向距离
		 * 
		 * @param scrollY
		 *            、
		 */
		public void onScroll(int scrollY);
	}

	private OnScrollListener onScrollListener;
}
