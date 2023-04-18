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

package com.zftlive.android.library.widget.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.zftlive.android.library.widget.R;

/**
 * 带显示文本的进度条，基于https://github.com/daimajia/NumberProgressBar改造
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class NumberProgressBar extends View {

  public enum ProgressTextVisibility {
    Visible, Invisible
  }

  /**
   * 显示文本
   */
  private String mDisplayLabel = "";
  
  private int mMaxProgress = 100;

  /**
   * Current progress, can not exceed the max progress.
   */
  private int mCurrentProgress = 0;

  /**
   * The progress area bar color.
   */
  private int mReachedBarColor;

  /**
   * The bar unreached area color.
   */
  private int mUnreachedBarColor;

  /**
   * The progress text color.
   */
  private int mTextColor;

  /**
   * The progress text background color.
   */
  private int mTextBgColor = Color.WHITE;

  /**
   * The progress text background stroke color.
   */
  private int mTextBgStrokeColor = Color.BLUE;

  /**
   * The progress text radius size.
   */
  private float mTextBgStrokeWidth = 1;

  /**
   * The progress text radius size.
   */
  private float mTextBgRadiusSize = 50;

  /**
   * The progress text size.
   */
  private float mTextSize;

  /**
   * The height of the reached area.
   */
  private float mReachedBarHeight;

  /**
   * The height of the unreached area.
   */
  private float mUnreachedBarHeight;

  /**
   * The suffix of the number.
   */
  private String mSuffix = "%";

  /**
   * The prefix.
   */
  private String mPrefix = "";

  private final int default_text_color = Color.rgb(66, 145, 241);
  private final int default_reached_color = Color.rgb(66, 145, 241);
  private final int default_unreached_color = Color.rgb(204, 204, 204);
  private final float default_progress_text_offset = 0f;
  private final float default_text_size;
  private final float default_reached_bar_height;
  private final float default_unreached_bar_height;

  /**
   * 存储偏好key
   */
  private static final String INSTANCE_STATE = "saved_instance";
  private static final String INSTANCE_TEXT_COLOR = "widget_text_color";
  private static final String INSTANCE_TEXT_BG_COLOR = "text_background_color";
  private static final String INSTANCE_TEXT_BG_STROKE_COLOR = "text_background_stroke_color";
  private static final String INSTANCE_TEXT_SIZE = "text_size";
  private static final String INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height";
  private static final String INSTANCE_REACHED_BAR_COLOR = "reached_bar_color";
  private static final String INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height";
  private static final String INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color";
  private static final String INSTANCE_MAX = "max";
  private static final String INSTANCE_PROGRESS = "progress";
  private static final String INSTANCE_SUFFIX = "suffix";
  private static final String INSTANCE_PREFIX = "prefix";
  private static final String INSTANCE_TEXT_VISIBILITY = "text_visibility";

  /**
   * The width of the text that to be drawn.
   */
  private float mDrawTextWidth;

  /**
   * The drawn text start.
   */
  private float mDrawTextStart;

  /**
   * The drawn text end.
   */
  private float mDrawTextEnd;

  /**
   * The text that to be drawn in onDraw().
   */
  private String mCurrentDrawText;

  /**
   * The Paint of the reached area.
   */
  private Paint mReachedBarPaint;

  /**
   * 已达进度的渐变色
   */
  private int[] mReachedBarColors = {Color.parseColor("#FF35D8F5"), Color.parseColor("#FF359DF5")};

  /**
   * The Paint of the unreached area.
   */
  private Paint mUnreachedBarPaint;
  /**
   * The Paint of the progress text.
   */
  private Paint mTextPaint;

  /**
   * The Paint of the progress text background.
   */
  private Paint mTextBgPaint;

  /**
   * The Paint of the progress text background stroke.
   */
  private Paint mTextBgStrokePaint;

  /**
   * Unreached bar area to draw rect.
   */
  private RectF mUnreachedRectF = new RectF(0, 0, 0, 0);
  /**
   * Reached bar area rect.
   */
  private RectF mReachedRectF = new RectF(0, 0, 0, 0);

  /**
   * Text area rect.
   */
  private RectF mTextBgRectF = new RectF(0, 0, 0, 0);

  /**
   * The progress text offset.
   */
  private float mOffset;

  /**
   * Determine if need to draw unreached area.
   */
  private boolean mDrawUnreachedBar = true;

  private boolean mDrawReachedBar = true;

  private boolean mIfDrawText = true;

  private boolean mIfDrawTextBg = true;

  public NumberProgressBar(Context context) {
    this(context, null);
  }

  public NumberProgressBar(Context context, AttributeSet attrs) {
    this(context, attrs, R.attr.anl_numberProgressBarStyle);
  }

  public NumberProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    // 初始化默认值
    default_reached_bar_height = dp2px(1.5f);
    default_unreached_bar_height = dp2px(1.0f);
    default_text_size = sp2px(10);
    mTextBgRadiusSize = dp2px(7.5f);

    // 获取xml自定义属性
    final TypedArray attributes =
        context.getTheme().obtainStyledAttributes(attrs, R.styleable.Widget_NumberProgressBar,
            defStyleAttr, 0);
    mDisplayLabel = attributes.getString(R.styleable.Widget_NumberProgressBar_anl_progress_label);
    mReachedBarColor =
        attributes.getColor(R.styleable.Widget_NumberProgressBar_anl_progress_reached_color,
            default_reached_color);
    mUnreachedBarColor =
        attributes.getColor(R.styleable.Widget_NumberProgressBar_anl_progress_unreached_color,
            default_unreached_color);
    mTextColor =
        attributes.getColor(R.styleable.Widget_NumberProgressBar_anl_progress_text_color, default_text_color);
    mTextBgColor =
        attributes.getColor(R.styleable.Widget_NumberProgressBar_anl_progress_text_background_color,
            default_text_color);
    mTextBgStrokeColor =
        attributes.getColor(R.styleable.Widget_NumberProgressBar_anl_progress_text_stroke_color,
            default_text_color);
    mTextBgStrokeWidth =
        attributes.getDimension(R.styleable.Widget_NumberProgressBar_anl_progress_text_stroke_width,
            mTextBgStrokeWidth);
    mTextBgRadiusSize =
        attributes.getDimension(R.styleable.Widget_NumberProgressBar_anl_progress_text_background_radius,
            mTextBgRadiusSize);
    mTextSize =
        attributes
            .getDimension(R.styleable.Widget_NumberProgressBar_anl_progress_text_size, default_text_size);
    mReachedBarHeight =
        attributes.getDimension(R.styleable.Widget_NumberProgressBar_anl_progress_reached_bar_height,
            default_reached_bar_height);
    mUnreachedBarHeight =
        attributes.getDimension(R.styleable.Widget_NumberProgressBar_anl_progress_unreached_bar_height,
            default_unreached_bar_height);
    mOffset =
        attributes.getDimension(R.styleable.Widget_NumberProgressBar_anl_progress_text_offset,
            default_progress_text_offset);
    int textVisible = attributes.getInt(R.styleable.Widget_NumberProgressBar_anl_progress_text_visibility, 0);
    if (textVisible != 0) {
      mIfDrawText = false;
    }
    mCurrentProgress = attributes.getInt(R.styleable.Widget_NumberProgressBar_anl_progress_current, 0);
    mMaxProgress = attributes.getInt(R.styleable.Widget_NumberProgressBar_anl_progress_max, 100);
    attributes.recycle();

    // 初始化工作
    init();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (mIfDrawText) {
      calcDrawRectHasProgressText();
    } else {
      calcDrawRectNoProgressText();
    }
    if (mDrawReachedBar) {
      /* 线性渐变 */
      int xWidth = (int) (mReachedRectF.right - mReachedRectF.left);
      int yHeight = (int) (mReachedRectF.bottom - mReachedRectF.top);
      Shader mShader =
          new LinearGradient(0, 0, xWidth, yHeight, mReachedBarColors, null, Shader.TileMode.CLAMP);
      mReachedBarPaint.setShader(mShader);
      canvas.drawRect(mReachedRectF, mReachedBarPaint);
    }
    if (mDrawUnreachedBar) {
      canvas.drawRect(mUnreachedRectF, mUnreachedBarPaint);
    }
    // 画文本背景
    if (mIfDrawTextBg) {
      // 先画背景，第二个参数是x半径，第三个参数是y半径
      canvas.drawRoundRect(mTextBgRectF, mTextBgRadiusSize, mTextBgRadiusSize, mTextBgPaint);
      canvas.drawRoundRect(mTextBgRectF, mTextBgRadiusSize, mTextBgRadiusSize, mTextBgStrokePaint);
    }
    // 后画文本
    if (mIfDrawText) {
      if(!TextUtils.isEmpty(mDisplayLabel)){
        mCurrentDrawText = mDisplayLabel;
      }
      canvas.drawText(mCurrentDrawText, mDrawTextStart, mDrawTextEnd, mTextPaint);
    }
  }

  @Override
  protected int getSuggestedMinimumWidth() {
    return (int) mTextSize;
  }

  @Override
  protected int getSuggestedMinimumHeight() {
    return Math.max((int) mTextSize, Math.max((int) mReachedBarHeight, (int) mUnreachedBarHeight));
  }

  @Override
  protected Parcelable onSaveInstanceState() {
    final Bundle bundle = new Bundle();
    bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
    bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
    bundle.putInt(INSTANCE_TEXT_BG_COLOR, getTextBackgroundColor());
    bundle.putInt(INSTANCE_TEXT_BG_STROKE_COLOR, getTextBackgroundStrokeColor());
    bundle.putFloat(INSTANCE_TEXT_SIZE, getProgressTextSize());
    bundle.putFloat(INSTANCE_REACHED_BAR_HEIGHT, getReachedBarHeight());
    bundle.putFloat(INSTANCE_UNREACHED_BAR_HEIGHT, getUnreachedBarHeight());
    bundle.putInt(INSTANCE_REACHED_BAR_COLOR, getReachedBarColor());
    bundle.putInt(INSTANCE_UNREACHED_BAR_COLOR, getUnreachedBarColor());
    bundle.putInt(INSTANCE_MAX, getMax());
    bundle.putInt(INSTANCE_PROGRESS, getProgress());
    bundle.putString(INSTANCE_SUFFIX, getSuffix());
    bundle.putString(INSTANCE_PREFIX, getPrefix());
    bundle.putBoolean(INSTANCE_TEXT_VISIBILITY, getProgressTextVisibility());
    return bundle;
  }

  @Override
  protected void onRestoreInstanceState(Parcelable state) {
    if (state instanceof Bundle) {
      final Bundle bundle = (Bundle) state;
      mTextColor = bundle.getInt(INSTANCE_TEXT_COLOR);
      mTextBgColor = bundle.getInt(INSTANCE_TEXT_BG_COLOR);
      mTextBgStrokeColor = bundle.getInt(INSTANCE_TEXT_BG_STROKE_COLOR);
      mTextSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
      mReachedBarHeight = bundle.getFloat(INSTANCE_REACHED_BAR_HEIGHT);
      mUnreachedBarHeight = bundle.getFloat(INSTANCE_UNREACHED_BAR_HEIGHT);
      mReachedBarColor = bundle.getInt(INSTANCE_REACHED_BAR_COLOR);
      mUnreachedBarColor = bundle.getInt(INSTANCE_UNREACHED_BAR_COLOR);
      init();
      setMax(bundle.getInt(INSTANCE_MAX));
      setProgress(bundle.getInt(INSTANCE_PROGRESS));
      setPrefix(bundle.getString(INSTANCE_PREFIX));
      setSuffix(bundle.getString(INSTANCE_SUFFIX));
      setProgressTextVisibility(bundle.getBoolean(INSTANCE_TEXT_VISIBILITY)
          ? View.VISIBLE
          : View.GONE);
      super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
      return;
    }
    super.onRestoreInstanceState(state);
  }

  /**
   * 测量View的宽度
   * 
   * @param measureSpec
   * @param isWidth
   * @return
   */
  private int measure(int measureSpec, boolean isWidth) {
    int result;
    int mode = MeasureSpec.getMode(measureSpec);
    int size = MeasureSpec.getSize(measureSpec);
    int padding =
        isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
    if (mode == MeasureSpec.EXACTLY) {
      result = size;
    } else {
      result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
      result += padding;
      if (mode == MeasureSpec.AT_MOST) {
        if (isWidth) {
          result = Math.max(result, size);
        } else {
          result = Math.min(result, size);
        }
      }
    }
    return result;
  }

  /**
   * 初始化画笔
   */
  private void init() {
    // 当前已达到进度画笔
    mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mReachedBarPaint.setColor(mReachedBarColor);
    // 剩余进度画笔
    mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mUnreachedBarPaint.setColor(mUnreachedBarColor);

    // 进度文本画笔
    mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mTextPaint.setColor(mTextColor);
    mTextPaint.setTextSize(mTextSize);

    // 进度文本背景画笔
    mTextBgPaint = new Paint();
    mTextBgPaint.setStyle(Paint.Style.FILL);// 充满
    mTextBgPaint.setColor(mTextBgColor);
    mTextBgPaint.setAntiAlias(true);

    // 进度文本描边画笔
    mTextBgStrokePaint = new Paint();
    mTextBgStrokePaint.setStyle(Paint.Style.STROKE);// 空心
    mTextBgStrokePaint.setColor(mTextBgStrokeColor);
    mTextBgStrokePaint.setStrokeWidth(mTextBgStrokeWidth);
    mTextBgStrokePaint.setAntiAlias(true);

    setProgress(mCurrentProgress);
    setMax(mMaxProgress);
  }

  /**
   * 计算没有显示文本时的绘制区域
   */
  private void calcDrawRectNoProgressText() {
    mReachedRectF.left = getPaddingLeft();
    mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
    mReachedRectF.right =
        (getWidth() - getPaddingLeft() - getPaddingRight()) / (getMax() * 1.0f) * getProgress()
            + getPaddingLeft();
    mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;

    mUnreachedRectF.left = mReachedRectF.right;
    mUnreachedRectF.right = getWidth() - getPaddingRight();
    mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight / 2.0f;
    mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;

    // 文本背景区域
    mTextBgRectF.left = mDrawTextStart;
    mTextBgRectF.top = 0;
    mTextBgRectF.right = mDrawTextStart;
    if(!TextUtils.isEmpty(mCurrentDrawText)){
      mTextBgRectF.right = mDrawTextStart + mTextPaint.measureText(mCurrentDrawText);
    }
    mTextBgRectF.bottom = getHeight();
  }

  /**
   * 计算存在进度文本的绘制区域
   */
  private void calcDrawRectHasProgressText() {
    mCurrentDrawText = String.format("%d", getProgress() * 100 / getMax());
    mCurrentDrawText = mPrefix + mCurrentDrawText + mSuffix;
    mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);

    if (getProgress() == 0) {
      mDrawReachedBar = false;
      mDrawTextStart = getPaddingLeft();
    } else {
      mDrawReachedBar = true;
      mReachedRectF.left = getPaddingLeft();
      mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
      mReachedRectF.right =
          (getWidth() - getPaddingLeft() - getPaddingRight()) / (getMax() * 1.0f) * getProgress()
              - mOffset + getPaddingLeft();
      mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;
      mDrawTextStart = (mReachedRectF.right + mOffset);
    }
    mDrawTextEnd =
        (int) ((getHeight() / 2.0f) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2.0f));

    if ((mDrawTextStart + mDrawTextWidth) >= getWidth() - getPaddingRight()) {
      mDrawTextStart = getWidth() - getPaddingRight() - mDrawTextWidth;
      mReachedRectF.right = mDrawTextStart - mOffset;
    }

    float unreachedBarStart = mDrawTextStart + mDrawTextWidth + mOffset;
    if (unreachedBarStart >= getWidth() - getPaddingRight()) {
      mDrawUnreachedBar = false;
    } else {
      mDrawUnreachedBar = true;
      mUnreachedRectF.left = unreachedBarStart;
      mUnreachedRectF.right = getWidth() - getPaddingRight();
      mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight / 2.0f;
      mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;
    }

    // 文本背景区域
    mTextBgRectF.left = mDrawTextStart;
    mTextBgRectF.top = 0;
    mTextBgRectF.right = mDrawTextStart + mTextPaint.measureText(mCurrentDrawText);
    mTextBgRectF.bottom = getHeight();
  }

  public int getTextColor() {
    return mTextColor;
  }

  public int getTextBackgroundColor() {
    return mTextBgColor;
  }

  public int getTextBackgroundStrokeColor() {
    return mTextBgStrokeColor;
  }

  public float getProgressTextSize() {
    return mTextSize;
  }

  public int getUnreachedBarColor() {
    return mUnreachedBarColor;
  }

  public int getReachedBarColor() {
    return mReachedBarColor;
  }

  public int getProgress() {
    return mCurrentProgress;
  }

  public int getMax() {
    return mMaxProgress;
  }

  public float getReachedBarHeight() {
    return mReachedBarHeight;
  }

  public float getUnreachedBarHeight() {
    return mUnreachedBarHeight;
  }

  public void setProgressTextSize(float textSize) {
    this.mTextSize = textSize;
    mTextPaint.setTextSize(mTextSize);
    invalidate();
  }

  public void setProgressTextColor(int textColor) {
    this.mTextColor = textColor;
    mTextPaint.setColor(mTextColor);
    invalidate();
  }

  public void setProgressTextRadius(float radius) {
    this.mTextBgRadiusSize = radius;
    invalidate();
  }

  public void setProgressTextBgColor(int textBgColor) {
    this.mTextBgColor = textBgColor;
    mTextBgPaint.setColor(mTextColor);
    invalidate();
  }

  public void setProgressTextBgStrokeColor(int textBgStrokeColor) {
    this.mTextBgStrokeColor = textBgStrokeColor;
    mTextBgStrokePaint.setColor(textBgStrokeColor);
    invalidate();
  }

  public void setUnreachedBarColor(int barColor) {
    this.mUnreachedBarColor = barColor;
    mUnreachedBarPaint.setColor(mReachedBarColor);
    invalidate();
  }

  public void setReachedBarColor(int progressColor) {
    this.mReachedBarColor = progressColor;
    mReachedBarPaint.setColor(mReachedBarColor);
    invalidate();
  }

  public void setReachedBarHeight(float height) {
    mReachedBarHeight = height;
  }

  public void setUnreachedBarHeight(float height) {
    mUnreachedBarHeight = height;
  }

  public void setMax(int maxProgress) {
    if (maxProgress > 0) {
      this.mMaxProgress = maxProgress;
      invalidate();
    }
  }

  /**
   * 设置显示文本
   * @param strDisplay 需要显示的文本
   */
  public void setDiaplayLabel(String strDisplay){
    this.mDisplayLabel = strDisplay;
    invalidate();
  }
  
  public void setSuffix(String suffix) {
    if (suffix == null) {
      mSuffix = "";
    } else {
      mSuffix = suffix;
    }
  }

  public String getSuffix() {
    return mSuffix;
  }

  public void setPrefix(String prefix) {
    if (prefix == null)
      mPrefix = "";
    else {
      mPrefix = prefix;
    }
  }

  public String getPrefix() {
    return mPrefix;
  }

  public void incrementProgressBy(int by) {
    if (by > 0) {
      setProgress(getProgress() + by);
    }
    if (mListener != null) {
      mListener.onProgressChange(getProgress(), getMax());
    }
  }

  public void setProgress(int progress) {
    if (progress <= getMax() && progress >= 0) {
      this.mCurrentProgress = progress;
      invalidate();
    }
  }

  public void setProgressTextVisibility(int visibility) {
    mIfDrawText = visibility == View.VISIBLE;
    invalidate();
  }

  public void setProgressTextBgVisibility(int visibility) {
    mIfDrawTextBg = visibility == View.VISIBLE;
    invalidate();
  }

  public boolean getProgressTextVisibility() {
    return mIfDrawText;
  }

  public void setOnProgressBarListener(OnProgressBarListener listener) {
    mListener = listener;
  }

  public float dp2px(float dp) {
    final float scale = getResources().getDisplayMetrics().density;
    return dp * scale + 0.5f;
  }

  public float sp2px(float sp) {
    final float scale = getResources().getDisplayMetrics().scaledDensity;
    return sp * scale;
  }

  /**
   * 设置已达进度的渐变色
   * 
   * @param color
   */
  public void setReachedBarColor(int startColor, int endColor) {
    mReachedBarColors[0] = startColor;
    mReachedBarColors[1] = endColor;
    invalidate();
  }

  private OnProgressBarListener mListener;

  public interface OnProgressBarListener {
    void onProgressChange(int current, int max);
  }
}
