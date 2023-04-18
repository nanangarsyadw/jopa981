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

package com.zftlive.android.library.widget.fadingactionbar.view;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

/**
 * Adapted from: http://stackoverflow.com/a/14753235/244576 and http://stackoverflow.com/a/11442374/244576
 */
public class ObservableWebViewWithHeader extends WebView implements ObservableScrollable {
    private OnScrollChangedCallback mOnScrollChangedCallback;

    public ObservableWebViewWithHeader(Context context) {
        super(context);
    }

    public ObservableWebViewWithHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableWebViewWithHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*
     * Header
     */
    private int headerHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
       super.onMeasure(widthMeasureSpec, heightMeasureSpec);
       // determine height of title bar
       View title = getChildAt(0);
       headerHeight = title==null ? 0 : title.getMeasuredHeight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
       return true;   // don't pass our touch events to children (title bar), we send these in dispatchTouchEvent
    }

    private boolean touchInHeader;

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){

       boolean wasInTitle = false;
       switch(me.getActionMasked()){
       case MotionEvent.ACTION_DOWN:
          touchInHeader = (me.getY() <= visibleHeaderHeight());
          break;

       case MotionEvent.ACTION_UP:
       case MotionEvent.ACTION_CANCEL:
          wasInTitle = touchInHeader;
          touchInHeader = false;
          break;
       }
       if (touchInHeader || wasInTitle) {
          View title = getChildAt(0);
          if(title!=null) {
             // this touch belongs to title bar, dispatch it here
             me.offsetLocation(0, getScrollY());
             return title.dispatchTouchEvent(me);
          }
       }
       // this is our touch, offset and process
       me.offsetLocation(0, -headerHeight);
       return super.dispatchTouchEvent(me);
    }

    /**
     * @return visible height of title (may return negative values)
     */
    private int visibleHeaderHeight(){
       return headerHeight-getScrollY();
    }       

    @Override
    protected void onDraw(Canvas c){
       c.save();
       int tH = visibleHeaderHeight();
       if(tH > 0) {
          // clip so that it doesn't clear background under title bar
          int sx = getScrollX(), sy = getScrollY();
          c.clipRect(sx, sy+tH, sx+getWidth(), sy+getHeight());
       }
       c.translate(0, headerHeight);
       super.onDraw(c);
       c.restore();
    }

    /*
     * Scroll
     */

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        
        View title = getChildAt(0);
        if (title != null)   // undo horizontal scroll, so that title scrolls only vertically
           title.offsetLeftAndRight(l - title.getLeft());

        if (mOnScrollChangedCallback != null)
            mOnScrollChangedCallback.onScroll(l, t);
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    @Override
    public void setOnScrollChangedCallback(OnScrollChangedCallback callback) {
        mOnScrollChangedCallback = callback;
    }
}