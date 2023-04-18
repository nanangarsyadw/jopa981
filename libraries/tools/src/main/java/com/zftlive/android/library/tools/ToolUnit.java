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

package com.zftlive.android.library.tools;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * 单位换算工具类
 * 
 * @author 曾繁添<br>
 *         px ：像素 <br>
 *         in ：英寸<br>
 *         mm ：毫米<br>
 *         pt ：磅，1/72 英寸<br>
 *         dp ：一个基于density的抽象单位，如果一个160dpi的屏幕，1dp=1px<br>
 *         dip ：等同于dp<br>
 *         sp ：同dp相似，但还会根据用户的字体大小偏好来缩放。<br>
 *         建议使用sp作为文本的单位，其它用dip<br>
 *         布局时尽量使用单位dip，少使用px <br>
 */
public class ToolUnit extends ToolBase{

  /**
   * 获取指定列数+间隙的正方形边长（以当前屏幕宽度为总长）
   * 
   * @param context 上下文
   * @param columns 列数
   * @param itemSpaceDp item之间的间隙
   * @return
   */
  public static int gainSquareItemLength(Context context, int columns, int itemSpaceDp) {
    int widthHeight = 0;
    // 屏幕宽度
    int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
    // 抛开间隙space除于列数
    widthHeight =
        (screenWidth - ToolUnit.getRawSize(context, TypedValue.COMPLEX_UNIT_DIP, itemSpaceDp)
            * (columns + 1))
            / columns;
    return widthHeight;
  }

  /**
   * 获取当前分辨率下指定单位对应的像素大小（根据设备信息） px,dip,sp -> px
   * 
   * Paint.setTextSize()单位为px
   * 
   * 代码摘自：Widget_TextView.setTextSize()
   * 
   * @param unit TypedValue.COMPLEX_UNIT_*
   * @param size
   * @return
   */
  public static int getRawSize(Context mContext, int unit, float size) {
    Resources r;
    if (mContext == null)
      r = Resources.getSystem();
    else
      r = mContext.getResources();

    return (int) TypedValue.applyDimension(unit, size, r.getDisplayMetrics());
  }

  /**
   * @Description 根据手机的分辨率从 dip 的单位 转成为 px(像素)
   * @param context
   *            环境
   * @param dipValue
   *            需要转化的dip值
   * @return int 转化后的px值
   */
  public static int dipToPx(Context context, float dipValue) {
      final float scale = context.getResources().getDisplayMetrics().density;
      return (int) (dipValue * scale + 0.5f);
  }

  /**
   * @Description 根据手机的分辨率从 px(像素) 的单位 转成为 dip
   * @param context
   *            环境
   * @param pxValue
   *            需要转换的像素值
   * @return int 转化后的dip值
   */
  public static int pxToDip(Context context, float pxValue) {
      final float scale = context.getResources().getDisplayMetrics().density;
      return (int) (pxValue / scale + 0.5f);
  }

  /**
   * 将px值转换为sp值，保证文字大小不变
   * @param context
   * @param pxValue
   */
  public static int px2sp(Context context, float pxValue) {
      final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
      return (int) (pxValue / fontScale + 0.5f);
  }

  /**
   * 将sp值转换为px值，保证文字大小不变
   * @param context
   * @param spValue
   *            （DisplayMetrics类中属性scaledDensity）
   */
  public static int sp2px(Context context, float spValue) {
      final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
      return (int) (spValue * fontScale + 0.5f);
  }
  
  /**
   * sp转换px
   * 
   * @param spValue sp数值
   * @return px数值
   */
  public static int spTopx(Context context,float spValue) {
    return (int) (spValue * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
  }

  /**
   * px转换sp
   * 
   * @param pxValue px数值
   * @return sp数值
   */
  public static int pxTosp(Context context,float pxValue) {
    return (int) (pxValue / context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
  }

  /**
   * dip转换px
   * 
   * @param dipValue dip数值
   * @return px数值
   */
  public static int dipTopx(Context context,int dipValue) {
    return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f);
  }

  /**
   * px转换dip
   * 
   * @param pxValue px数值
   * @return dip数值
   */
  public static int pxTodip(Context context,float pxValue) {
    return (int) (pxValue / context.getResources().getDisplayMetrics().density + 0.5f);
  }
}
