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

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 数据工具类
 * 
 * @author 曾繁添
 * @version 1.0
 * @link http://www.cnblogs.com/fly100/
 * @email mengzhongyouni@gmail.com
 */
public class ToolData extends ToolBase{

  public static final String TAG = "ToolData";
  /**
   * 数据分页条数
   */
  public static Integer pageSize = 10;

//  static {
//    try {
//      String value = ToolProperties.readAssetsProp("config.properties", "pageSize");
//      if (null != value && !"".equals(value)) {
//        pageSize = Integer.valueOf(value);
//      }
//    } catch (Exception e) {
//      Log.w(TAG, "读取配置文件assets目录config.properties文件pageSize失败，原因：" + e.getMessage());
//    }
//  }

  /**
   * 判断单个输入框是否为空
   * 
   * @param mContext
   * @param input
   * @param mFieldName
   * @return
   */
  public static boolean hasEmpty(Context mContext, EditText input, String mFieldName) {
    if (null == mContext || null == input || TextUtils.isEmpty(mFieldName)) return false;

    if (TextUtils.isEmpty(input.getText().toString())) {
      Toast.makeText(mContext,mFieldName + "不能为空",Toast.LENGTH_SHORT).show();
      input.requestFocus();
      input.setFocusable(true);
      return true;
    }
    return false;
  }

  /**
   * 一起判断表单必须录入的项目，返回对应的提示信息
   * 
   * @param mContext
   * @param input
   * @param mFieldName
   * @return
   */
  public static boolean hasEmpty(Context mContext, EditText[] input, String[] mFieldName) {
    if (null == mContext || null == input || null == mFieldName) return false;
    String strResultMsg = "";
    for (int i = 0; i < input.length; i++) {
      String text = input[i].getText().toString();
      if (TextUtils.isEmpty(text)) {
        strResultMsg += mFieldName[i] + "不能为空\n";
      }
    }

    if (!TextUtils.isEmpty(strResultMsg)) {
      Toast.makeText(mContext,strResultMsg,Toast.LENGTH_SHORT).show();
      input[0].requestFocus();
      input[0].setFocusable(true);
      return true;
    }

    return false;
  }

  /**
   * 读取Assets目录的json文件,并转成指定的Bean
   * 
   * @param mContext 上下文
   * @param jsonFileName 不带扩展名的文件名
   * @param clazz 需要转成对应的Bean
   * @return
   */
  public static <T> void gainAssetsData(final Context mContext, final String jsonFileName,
      final T clazz, final IDataCallBackHandler handler) {
//    final Handler mCallHandler = new Handler() {
//      @Override
//      public void handleMessage(Message msg) {
//        super.handleMessage(msg);
//        if (null == handler) return;
//
//        if (msg.what == 1) {
//          handler.onSuccess((T) msg.obj);
//        } else {
//          handler.onFailure((String) msg.obj);
//        }
//      }
//    };
//    // 开启子线程初始化
//    new Thread(new Runnable() {
//
//      @Override
//      public void run() {
//        Looper.prepare();
//        final Message mgs = mCallHandler.obtainMessage();
//        try {
//          String strJsonData = ToolFile.readAssetsValue(mContext, jsonFileName);
//          // 判断是否含有resultData
//          JSONObject json = new JSONObject(strJsonData);
//          String data =
//              json.has("resultData") ? json.getJSONObject("resultData").toString() : strJsonData;
//
//          // JSON转成Bean
//          T result = null;
//          result = (T) new Gson().fromJson(data, (Type) clazz);
//          mgs.what = 1;
//          mgs.obj = result;
//        } catch (Exception e) {
//          Log.e(TAG, "JSONObject转Bean失败,原因：" + e.getMessage());
//          mgs.what = -1;
//          mgs.obj = e.getMessage();
//        }
//        // 将获取的消息利用Handler发送到主线程
//        mCallHandler.sendMessage(mgs);
//        Looper.loop();
//      }
//    }).start();
  }

  /**
   * 读取Assets目录的json文件
   * 
   * @param mContext 上下文
   * @param jsonFileName 不带扩展名的文件名称
   * @return
   */
  public static JSONObject gainAssetsData(Context mContext, String jsonFileName) {
    String strJsonData = ToolFile.readAssetsValue(mContext, jsonFileName);
    JSONObject result = null;
    try {
      result = new JSONObject(strJsonData);
    } catch (JSONException e) {
      Log.e(TAG, "构建JSONObject失败，原因：" + e.getMessage());
      result = new JSONObject();
    }
    return result;
  }

  /**
   * 将Bean反射只Map
   * 
   * @param dto 装载成员属性容器
   * @param bean 请求参数实例
   */
  public static void fillDTO(Map<String, Object> dto, Object bean) {
    if (null == dto || null == bean) return;
    // 反射获取属性
    // Field[] mFields =
    // requestParam.getClass().getDeclaredFields();//当前类的public属性
    Field[] mFields = bean.getClass().getFields();// 所有可见public的属性、包括父类、接口
    for (Field field : mFields) {
      field.setAccessible(true);
      try {
        dto.put(field.getName(), field.get(bean));
      } catch (Exception e) {
        Log.e(TAG, "反射" + bean.getClass().getName() + "成员属性失败，原因：" + e.getMessage());
      }
    }
  }

  /**
   * 将Map反射至Bean
   * 
   * @param dto 装载成员属性容器
   * @param clazz 请求参数实例
   */
//  public static <T> Object fillBean(Map<String, Object> dto, T clazz) {
//    // 数据合法性校验
//    if (null == dto || dto.isEmpty()) return null;
//    // 将Map转成json格式字符串
//    String strGson = new Gson().toJson(dto);
//    // 将json格式字符串转Bean
//    Object bean = new Gson().fromJson(strGson, (Type) clazz);
//
//    return bean;
    // //遍历Map
    // Iterator<Map.Entry<String, Object>> it = dto.entrySet().iterator();
    // while (it.hasNext()) {
    // Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
    // try {
    // String mWriteMethod = "set"+ToolString.capitalFirst(entry.getKey());
    // Method m = bean.getClass().getDeclaredMethod(mWriteMethod, entry.getValue().getClass());
    // m.setAccessible(true);
    // m.invoke(bean, entry.getValue());
    // } catch (Exception e) {
    // ToolLog.e(TAG, "装填属性-->"+entry.getKey()+"失败，原因："+e.getMessage());
    // }
    // }
//  }

  /**
   * 将对象转成json字符串
   * 
   * @param data
   * @return
   */
//  public static String beanToJson(Object data) {
//    if (null == data) return "";
//    return new Gson().toJson(data);
//  }

  /**
   * 将JSON转成Bean
   * 
   * @param strJsonData
   * @param clazz
   * @param handler
   */
  public static <T> void jsonToBean(final String strJsonData, final T clazz,
      final IDataCallBackHandler handler) {

//    final Handler mCallHandler = new Handler() {
//      @Override
//      public void handleMessage(Message msg) {
//        super.handleMessage(msg);
//        if (null == handler) return;
//
//        if (msg.what == 1) {
//          handler.onSuccess((T) msg.obj);
//        } else {
//          handler.onFailure((String) msg.obj);
//        }
//      }
//    };
//    // 开启子线程初始化
//    new Thread(new Runnable() {
//
//      @Override
//      public void run() {
//        Looper.prepare();
//        final Message mgs = mCallHandler.obtainMessage();
//        try {
//          // JSON转成Bean
//          T result = null;
//          result = (T) new Gson().fromJson(strJsonData, (Type) clazz);
//          mgs.what = 1;
//          mgs.obj = result;
//        } catch (Exception e) {
//          Log.e(TAG, "JSONObject转Bean失败,原因：" + e.getMessage());
//          mgs.what = -1;
//          mgs.obj = e.getMessage();
//        }
//        // 将获取的消息利用Handler发送到主线程
//        mCallHandler.sendMessage(mgs);
//        Looper.loop();
//      }
//    }).start();
  }

  /**
   * 读取AndroidManifest.xml配置的meta-data数据
   * 
   * @param mContext 上下文
   * @param target Activity/BroadcastReceiver/Service/Application
   * @param key 配置的name
   * @return
   */
  public static String gainMetaData(Context mContext, Class target, String key) {
    String result = "";
    try {
      Log.d(TAG, target.getSuperclass().getName());

      int flags = PackageManager.GET_META_DATA;
      Object obj = target.newInstance();
      if (obj instanceof Activity) {
        ActivityInfo info2 =
            mContext.getPackageManager().getActivityInfo(((Activity) mContext).getComponentName(),
                flags);
        result = info2.metaData.getString(key);
      } else if (obj instanceof Application) {
        ApplicationInfo info1 =
            mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), flags);
        result = info1.metaData.getString(key);
      } else if (obj instanceof Service) {
        ComponentName cn1 = new ComponentName(mContext, target);
        ServiceInfo info3 = mContext.getPackageManager().getServiceInfo(cn1, flags);
        result = info3.metaData.getString(key);
      } else if (obj instanceof BroadcastReceiver) {
        ComponentName cn2 = new ComponentName(mContext, target);
        ActivityInfo info4 = mContext.getPackageManager().getReceiverInfo(cn2, flags);
        result = info4.metaData.getString(key);
      }
    } catch (Exception e) {
      Log.e(TAG, "读取meta元数据失败，原因：" + e.getMessage());
    }
    return result;
  }

  public interface IDataCallBackHandler<T> {

    public void onSuccess(T result);

    public void onFailure(String errorMsg);
  }
}
