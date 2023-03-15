package com.zftlive.android.library.base;

import java.io.Serializable;

/**
 * 基本的HTPP交互响应数据Bean基类
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class BaseResponse implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6871543834981122865L;

  /**
   * 响应状态码
   */
  public int resultCode = 200;

  /**
   * 操作响应消息
   */
  public String resultMsg = "";

  /**
   * 响应数据
   */
  public String resultData = "";

}
