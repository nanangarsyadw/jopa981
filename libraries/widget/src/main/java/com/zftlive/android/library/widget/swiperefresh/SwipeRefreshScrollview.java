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
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.zftlive.android.library.widget.R;

/**
 * 监听滚动+下拉刷新的Scrollview控件
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class SwipeRefreshScrollview extends CustomSwipeRefreshLayout {

  private ScrollView mScrollView;

  private RefreshListener mRefreshListener;

  public SwipeRefreshScrollview(Context context) {
    this(context,null);
  }

  public SwipeRefreshScrollview(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context,attrs);
  }

  private void init(Context context,AttributeSet attrs){
    mScrollView = new InnerScrollView(context,attrs);
    mScrollView.setId(R.id.widget_srl_scrollview);
    addView(mScrollView);
    setOnRefreshListener(mInnerRefreshListener);
  }

  /**
   * 获取刷新的Scrollivew
   * @return
   */
  public ScrollView getRefreshableView(){
    return mScrollView;
  }

  /**
   * 设置上拉/下拉刷新监听事件
   * @param mRefreshListener
   */
  public void setRefreshListener(RefreshListener mRefreshListener){
    this.mRefreshListener = mRefreshListener;
  }

  /**
   * 内部刷新监听器
   */
  private RefreshListener mInnerRefreshListener = new RefreshListener(){

    @Override
    public void onRefresh() {
      if(mRefreshListener != null){
        mRefreshListener.onRefresh();
      }
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
      if(mRefreshListener != null){
        mRefreshListener.onScrollChanged(l,t,oldl,oldt);
      }
    }
  };

  protected class InnerScrollView extends ScrollView{

    public InnerScrollView(Context context) {
      super(context);
    }

    public InnerScrollView(Context context, AttributeSet attrs) {
      super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
      super.onScrollChanged(l, t, oldl, oldt);
      if(null != mRefreshListener){
        mRefreshListener.onScrollChanged(l,t,oldl,oldt);
      }
    }
  }

  public interface RefreshListener extends OnRefreshListener{

    void onScrollChanged(int l, int t, int oldl, int oldt);

  }

}
