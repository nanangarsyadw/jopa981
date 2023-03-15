package com.zftlive.android.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * 表单隐藏项目
 * @author 曾繁添
 * @version 1.0
 *
 */
public class Hidden extends View {
  
  public Hidden(Context context) {
    this(context,null);
  }
  
  public Hidden(Context context, AttributeSet attrs) {
    this(context, attrs,0);
  }
  
  public Hidden(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setVisibility(GONE);
  }
}
