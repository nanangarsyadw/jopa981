package com.zftlive.android.library.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * UI更新相关Handler
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class UIHandler extends Handler implements IToolConstant {

  /** 日志输出标志 **/
  protected final String TAG = this.getClass().getSimpleName();

  /**
   * 上下文
   */
  private Activity mContext;

  /**
   * 单例实例
   */
  private static UIHandler mUIHandler = null;

  private UIHandler() {}

  @Override
  public void handleMessage(Message msg) {
    super.handleMessage(msg);
    int what = msg.what;
    String text = null == msg.obj ? "" : String.valueOf(msg.obj);
    ToolLog.d(TAG, "Main handler message code: " + what);

    switch (what) {

      case SHOW_LOADING: {
        ToolAlert.loading(mContext, text);
        break;
      }
      case UPDATE_LOADING_MSG: {
        ToolAlert.updateProgressText(text);
        break;
      }
      case CLOSE_LOADING: {
        ToolAlert.closeLoading();
        break;
      }
    }
  }

  /**
   * 发送消息
   * @param mContext
   * @param strMessage
   * @param what
   */
  public void sendMainUIMessage(Activity mContext,String strMessage,int what){
    this.mContext = mContext;
    Message message = mUIHandler.obtainMessage();
    message.what = what;
    message.obj = strMessage;
    mUIHandler.sendMessage(message);
  }
  
  /**
   * 获取实例
   * @return
   */
  public static UIHandler getInstance() {
    if (mUIHandler == null) {
      synchronized (UIHandler.class) {
        if (mUIHandler == null) {
          mUIHandler = new UIHandler();
        }
      }
    }
    return mUIHandler;
  }

}
