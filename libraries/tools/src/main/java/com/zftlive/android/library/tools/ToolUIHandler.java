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

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * UI更新相关Handler
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class ToolUIHandler extends Handler implements IToolConstant {

  /** 日志输出标志 **/
  protected final String TAG = this.getClass().getSimpleName();

  /**
   * 单例实例
   */
  private static ToolUIHandler mUIHandler = null;

  private ToolUIHandler() {}

  @Override
  public void handleMessage(Message msg) {
    super.handleMessage(msg);
    int what = msg.what;
    String text = null == msg.obj ? "" : String.valueOf(msg.obj);
    Log.d(TAG, "Main handler message code: " + what);

    switch (what) {

      case SHOW_LOADING: {
//        ToolAlert.loading(mContext, text);
        break;
      }
      case UPDATE_LOADING_MSG: {
//        ToolAlert.updateProgressText(text);
        break;
      }
      case CLOSE_LOADING: {
//        ToolAlert.closeLoading();
        break;
      }
    }
  }

  /**
   * 发送消息
   * @param strMessage
   * @param what
   */
  public void sendMainUIMessage(String strMessage,int what){
    Message message = mUIHandler.obtainMessage();
    message.what = what;
    message.obj = strMessage;
    mUIHandler.sendMessage(message);
  }
  
  /**
   * 获取实例
   * @return
   */
  public static ToolUIHandler getInstance() {
    if (mUIHandler == null) {
      synchronized (ToolUIHandler.class) {
        if (mUIHandler == null) {
          mUIHandler = new ToolUIHandler();
        }
      }
    }
    return mUIHandler;
  }

}
