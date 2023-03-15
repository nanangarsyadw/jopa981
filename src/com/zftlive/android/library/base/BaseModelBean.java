package com.zftlive.android.library.base;

import com.zftlive.android.library.data.DTB;

import java.io.Serializable;

/**
 * 基础数据模型Bean
 * 
 * @author 曾繁添
 * @version 1.0
 *
 * @param <K>
 * @param <V>
 */
public class BaseModelBean extends DTB implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -2268555463658186721L;

  /**
   * item类型
   */
  public int itemType = 0;
  
}
