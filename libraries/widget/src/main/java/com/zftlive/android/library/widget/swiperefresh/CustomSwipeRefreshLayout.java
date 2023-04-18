package com.zftlive.android.library.widget.swiperefresh;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 下拉刷新控件,只在竖直方向才能下拉
 *
 * @author 曾繁添
 * @version 1.0
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {

  private float mDownX, mDownY;

  public CustomSwipeRefreshLayout(Context context) {
    super(context);
  }

  public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public boolean onInterceptTouchEvent(MotionEvent ev) {
    boolean intercept = super.onInterceptTouchEvent(ev);
    int action = MotionEventCompat.getActionMasked(ev);
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        mDownX = ev.getX();
        mDownY = ev.getY();
        break;
      case MotionEvent.ACTION_MOVE:
        float distanceX = Math.abs(ev.getX() - mDownX);
        float distanceY = Math.abs(ev.getY() - mDownY);

        if (distanceY < 10 || distanceY < distanceX) {
          intercept = false;
        }
        break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_CANCEL:
        mDownX = 0;
        mDownY = 0;
        break;
    }

    return intercept;
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    Log.e("TAG","CustomSwipeRefreshLayout.onTouchEvent");
    return super.onTouchEvent(ev);
  }

  /**
   * 刷新完毕
   */
  public void onRefreshComplete() {
    setRefreshing(false);
  }

}
