package com.zftlive.android.library.widget;

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
