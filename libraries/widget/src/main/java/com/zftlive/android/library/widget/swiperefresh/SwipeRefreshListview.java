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

package com.zftlive.android.library.widget.swiperefresh;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zftlive.android.library.widget.R;

/**
 * 滑到底部自动翻页+下拉刷新的Listview控件
 *
 * @author 曾繁添
 * @version 1.0
 */
public class SwipeRefreshListview extends CustomSwipeRefreshLayout {

  private ListView mListview;

  private RefreshListener mRefreshListener;

  private AbsListView.OnScrollListener mOnScrollListener;

  private final String TAG = "SwipeRefreshListview";

  public SwipeRefreshListview(Context context) {
    this(context, null);
  }

  public SwipeRefreshListview(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    mListview = createListview(context, attrs);
    mListview.setId(R.id.widget_srl_listview);
    mListview.setOnScrollListener(mListScorllListener);
    mListview.setCacheColorHint(getResources().getColor(android.R.color.transparent));
    addView(mListview);
    setOnRefreshListener(mInnerRefreshListener);
  }

  protected ListView createListview(Context context, AttributeSet attrs){
    return  new InternalListView(context, attrs);
  }

  /**
   * 内部刷新监听器
   */
  private RefreshListener mInnerRefreshListener = new RefreshListener() {

    @Override
    public void onRefresh() {
      if (mRefreshListener != null) {
        mRefreshListener.onRefresh();
      }
    }

    @Override
    public void onLoadMore() {
      if (mRefreshListener != null) {
        mRefreshListener.onLoadMore();
      }
    }
  };

  /**
   * 列表滑动监听器
   */
  private AbsListView.OnScrollListener mListScorllListener = new AbsListView.OnScrollListener() {

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
      if (null != mOnScrollListener) {
        mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
      }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
      if (null != mOnScrollListener) {
        mOnScrollListener.onScrollStateChanged(view, scrollState);
      }
      if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && view.getLastVisiblePosition() == view.getCount() - 1) {
        if (mRefreshListener != null) {
          mRefreshListener.onLoadMore();
        }
      }
    }
  };

  /**
   * 获取刷新的listview
   *
   * @return
   */
  public ListView getRefreshableView() {
    return mListview;
  }

  /**
   * 获取listview滚动监听器事件，供外界使用例如ImageLoader延迟加载
   *
   * @return
   */
  public AbsListView.OnScrollListener getListScorllListener() {
    return mListScorllListener;
  }

  /**
   * 设置上拉/下拉刷新监听事件
   *
   * @param mRefreshListener
   */
  public void setRefreshListener(RefreshListener mRefreshListener) {
    this.mRefreshListener = mRefreshListener;
  }

  /**
   * 设置滚动监听事件
   *
   * @param mScorllListener
   */
  public void setOnScrollListener(AbsListView.OnScrollListener mScorllListener) {
    this.mOnScrollListener = mScorllListener;
  }

  /**
   * 解决事件冲突的Listview
   */
  protected class InternalListView extends ListView {

    private boolean mAddedLvFooter = false;
    private float xDistance, yDistance, lastX, lastY;

    public InternalListView(Context context) {
      super(context);
    }

    public InternalListView(Context context, AttributeSet attrs) {
      super(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
      /**
       * This is a bit hacky, but Samsung's ListView has got a bug in it
       * when using Header/Footer Views and the list is empty. This masks
       * the issue so that it doesn't cause an FC. See Issue #66.
       */
      try {
        super.dispatchDraw(canvas);
      } catch (IndexOutOfBoundsException e) {
        e.printStackTrace();
      } catch (Exception ex) {
        ex.printStackTrace();
        Log.e(TAG, ex.getMessage());
      }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
      /**
       * This is a bit hacky, but Samsung's ListView has got a bug in it
       * when using Header/Footer Views and the list is empty. This masks
       * the issue so that it doesn't cause an FC. See Issue #66.
       */
      try {
        return super.dispatchTouchEvent(ev);
      } catch (IndexOutOfBoundsException e) {
        e.printStackTrace();
        return false;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
      switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
          xDistance = yDistance = 0f;
          lastX = ev.getX();
          lastY = ev.getY();
          break;
        case MotionEvent.ACTION_MOVE:
          final float curX = ev.getX();
          final float curY = ev.getY();
          xDistance += Math.abs(curX - lastX);
          yDistance += Math.abs(curY - lastY);
          lastX = curX;
          lastY = curY;
          if (xDistance > yDistance) {
            return false;
          }
      }

      return super.onInterceptTouchEvent(ev);
    }

  }

  public interface RefreshListener extends SwipeRefreshLayout.OnRefreshListener {

    void onLoadMore();

  }

}
