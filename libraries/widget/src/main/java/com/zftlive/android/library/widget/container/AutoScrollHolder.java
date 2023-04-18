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

package com.zftlive.android.library.widget.container;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自动滚动孩子View的容器
 * 
 * @author 曾繁添
 * @version 1.0
 *
 */
public class AutoScrollHolder extends ViewGroup {

	public AutoScrollHolder(Context context) {
		super(context);
	}

	public AutoScrollHolder(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoScrollHolder(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int iChild = getChildCount();
		for (int i = 0; i < iChild; i++) {
			View childView = getChildAt(i);
			measureChild(childView, widthMeasureSpec, heightMeasureSpec);
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed)  
        {  
            int childCount = getChildCount();  
            // 设置主布局的高度  
//            MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();  
//            lp.height = mScreenHeight * childCount;  
//            setLayoutParams(lp);  
  
            for (int i = 0; i < childCount; i++)  
            {  
                View child = getChildAt(i);  
                if (child.getVisibility() != View.GONE)  
                {  
//                    child.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);// 调用每个自布局的layout 
                    child.layout(l, i , r, (i + 0) );// 调用每个自布局的layout  
                }  
            }  
  
        }  
	}
}
