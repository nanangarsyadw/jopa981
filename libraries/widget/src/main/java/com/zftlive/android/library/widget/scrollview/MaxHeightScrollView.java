/*
 *
 *     京东金融团队Android基础开发库积累、沉淀、封装、共同整理
 *
 *     Copyright (c) 2017. @ 京东金融移动研发团队
 *
 *     技术支持：曾繁添<zengfantian@jd.com>
 */

package com.zftlive.android.library.widget.scrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.zftlive.android.library.widget.R;

/**
 * 先判断是否设定了mMaxHeight，如果设定了mMaxHeight，则直接使用mMaxHeight的值，
 * 如果没有设定mMaxHeight，则判断是否设定了mMaxRatio，如果设定了mMaxRatio的值 则使用此值与屏幕高度的乘积作为最高高度
 *
 * 用法：
 * xml里使用属性
 * app:mhv_HeightRatio="0.3" // 优先级高  指定为屏幕高度的倍数
 * app:mhv_HeightDimen="500dp"
 *
 */
public class MaxHeightScrollView extends ScrollView {

    private static final float DEFAULT_MAX_RATIO = 0.6f;
    private static final float DEFAULT_MAX_HEIGHT = 0f;

    private float mMaxRatio = DEFAULT_MAX_RATIO;// 优先级高
    private float mMaxHeight = DEFAULT_MAX_HEIGHT;// 优先级低

    public MaxHeightScrollView(Context context) {
        super(context);
        init();
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.Widget_MaxHeightScrollView);

        final int count = a.getIndexCount();
        for (int i = 0; i < count; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.Widget_MaxHeightScrollView_maxRatioByScreenHeight) {
                mMaxRatio = a.getFloat(attr, DEFAULT_MAX_RATIO);
            } else if (attr == R.styleable.Widget_MaxHeightScrollView_maxHeight) {
                mMaxHeight = a.getDimension(attr, DEFAULT_MAX_HEIGHT);
            }
        }
        a.recycle();
    }

    private void init() {
        if (mMaxHeight <= 0) {
            mMaxHeight = mMaxRatio * (float) getScreenHeight(getContext());
        } else {
            mMaxHeight = Math.min(mMaxHeight, mMaxRatio * (float) getScreenHeight(getContext()));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }
        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }

    /**
     * 获取屏幕高度
     */
    private int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

}
