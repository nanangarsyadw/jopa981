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
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.zftlive.android.library.widget.R;

public class RootLayout extends FrameLayout {

    private View mHeaderContainer;
    private View mListViewBackground;
    private boolean mInitialized = false;

    public RootLayout (Context context) {
        super(context);
    }

    public RootLayout (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RootLayout (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onLayout (boolean changed, int left, int top, int right, int bottom) {
        //at first find headerViewContainer and listViewBackground
        if (mHeaderContainer == null)
            mHeaderContainer = findViewById(R.id.fab__header_container);
        if (mListViewBackground == null)
            mListViewBackground = findViewById(R.id.fab__listview_background);

        //if there's no headerViewContainer then fallback to standard FrameLayout
        if (mHeaderContainer == null) {
            super.onLayout(changed, left, top, right, bottom);
            return;
        }

        if (!mInitialized) {
            super.onLayout(changed, left, top, right, bottom);
            //if mListViewBackground not exists or mListViewBackground exists
            //and its top is at headercontainer height then view is initialized
            if (mListViewBackground == null || mListViewBackground.getTop() == mHeaderContainer.getHeight())
                mInitialized = true;
            return;
        }

        //get last header and listViewBackground position
        int headerTopPrevious = mHeaderContainer.getTop();
        int listViewBackgroundTopPrevious = mListViewBackground != null ? mListViewBackground.getTop() : 0;

        //relayout
        super.onLayout(changed, left, top, right, bottom);

        //revert header top position
        int headerTopCurrent = mHeaderContainer.getTop();
        if (headerTopCurrent != headerTopPrevious) {
            mHeaderContainer.offsetTopAndBottom(headerTopPrevious - headerTopCurrent);
        }
        //revert listViewBackground top position
        int listViewBackgroundTopCurrent = mListViewBackground != null ? mListViewBackground.getTop() : 0;
        if (listViewBackgroundTopCurrent != listViewBackgroundTopPrevious) {
            mListViewBackground.offsetTopAndBottom(listViewBackgroundTopPrevious - listViewBackgroundTopCurrent);
        }
    }

}