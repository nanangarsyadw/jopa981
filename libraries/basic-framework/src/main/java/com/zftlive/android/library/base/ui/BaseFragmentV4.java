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

package com.zftlive.android.library.base.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.zftlive.android.library.base.IBaseConstant;
import com.zftlive.android.library.base.IMaskClickListener;
import com.zftlive.android.library.base.Operation;
import com.zftlive.android.library.widget.WindowTitle;

/**
 * Fragment基类
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public abstract class BaseFragmentV4 extends Fragment implements IBaseFragment,IBaseConstant {

  /**
   * 是否已经加载过数据
   */
  private boolean mHasLoadedData = false;
  
  /**
   * 当前Fragment渲染的视图View 
   */
  private View mContentView = null;
  
  /**
   * 共通操作
   */
  protected Operation mOperation = null;
  
  /**
   * 依附的Activity
   */
  protected Activity mActivity = null;

  /**
   * 界面窗体
   */
  public WindowTitle mWindowTitle;
  
  /**
   * 日志输出标志
   */
  protected final String TAG = this.getClass().getSimpleName();

  /**
   * 每次切换都会调用判断当前Fragment是否展现在屏幕上,在onAttach、onCreateView之前分别调用一次
   */
  @Override  
  public void setUserVisibleHint(boolean isVisibleToUser) {
      super.setUserVisibleHint(isVisibleToUser);  
      Log.d(TAG, "BaseFragmentV4-->setUserVisibleHint()-->"+isVisibleToUser);
      //当Fragment在屏幕上可见并且没有加载过数据时调用
      if(isVisibleToUser && null != mContentView && !mHasLoadedData){
        loadDataOnce();
        Log.d(TAG, "BaseFragmentV4-->loadDataOnce()");
        mHasLoadedData = true;
      }
  }
  
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    // 缓存当前依附的activity
    mActivity = activity;
    Log.d(TAG, "BaseFragmentV4-->onAttach()");
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "BaseFragmentV4-->onCreate()");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.d(TAG, "BaseFragmentV4-->onCreateView()");
    // 渲染视图View
    if (null == mContentView) {
      // 初始化参数
      Bundle parms = getArguments();
      if (null == parms) {
        parms = new Bundle();
      }
      initParams(parms);

      //初始化标题
      String strTitle = initTitle();
      if(!TextUtils.isEmpty(strTitle) && mActivity instanceof BaseActivity){
        mWindowTitle = ((BaseActivity)mActivity).getWindowTitle();
        mWindowTitle.setWindowTitle(strTitle, Gravity.CENTER);
      }
      
      View mView = bindView();
      if (null == mView) {
        mContentView = inflater.inflate(bindLayout(), container, false);
      } else {
        mContentView = mView;
      }
      // 控件初始化
      initView(mContentView);
      // 实例化共通操作
      mOperation = new Operation(mActivity);
      //请求网络数据
      setUserVisibleHint(getUserVisibleHint());
    }

    return mContentView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    Log.d(TAG, "BaseFragmentV4-->onActivityCreated()");
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    Log.d(TAG, "BaseFragmentV4-->onViewCreated()");
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    Log.d(TAG, "BaseFragmentV4-->onSaveInstanceState()");
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onStart() {
    Log.d(TAG, "BaseFragmentV4-->onStart()");
    super.onStart();
  }

  @Override
  public void onResume() {
    Log.d(TAG, "BaseFragmentV4-->onResume()");
    super.onResume();
  }

  @Override
  public void onPause() {
    Log.d(TAG, "BaseFragmentV4-->onPause()");
    super.onPause();
  }

  @Override
  public void onStop() {
    Log.d(TAG, "BaseFragmentV4-->onStop()");
    super.onStop();
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "BaseFragmentV4-->onDestroy()");
    super.onDestroy();
  }

  @Override
  public void onDetach() {
    Log.d(TAG, "BaseFragmentV4-->onDetach()");
    super.onDetach();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (mContentView != null && mContentView.getParent() != null) {
      ((ViewGroup) mContentView.getParent()).removeView(mContentView);
    }

//    //监测Fragment内存泄露
//    if(mActivity.getApplicationContext() instanceof MApplication){
//      RefWatcher refWatcher = MApplication.getRefWatcher(mActivity);
//      refWatcher.watch(this);
//    }
  }

  /**
   * 一般情况下不适用，若不写XML布局文件，可以复写改方法返回代码生成的View
   */
  @Override
  public View bindView() {
    Log.d(TAG, "BaseFragmentV4-->bindView()");
    return null;
  }

  /**
   * 初始化Activity标题栏的标题文案
   */
  @Override
  public String initTitle() {
    Log.d(TAG, "BaseFragmentV4-->initTitle()");
    return null;
  }
  
  @Override
  public void loadDataOnce() {
    
  }

  /**
   * 设置状态栏背景色
   *
   * @return
   */
  public void setStatusBarBackground(@ColorInt int bgColor) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
      return;
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
      mActivity.getWindow().setStatusBarColor(bgColor);
    } else {
      mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    ViewGroup parent = (ViewGroup) findViewById(android.R.id.content);
    for (int i = 0, count = parent.getChildCount(); i < count; i++) {
      View childView = parent.getChildAt(i);
      if (childView instanceof ViewGroup) {
        childView.setFitsSystemWindows(true);
        ((ViewGroup) childView).setClipToPadding(true);
      }
    }

    return;
  }

  /**
   * 往当前界面最顶层增加蒙层、顶级View
   * @param mMaskView
   */
  public void addMaskView(final View mMaskView){
    addMaskView(mMaskView,null);
  }

  /**
   * 往当前界面最顶层增加蒙层、顶级View
   * @param mMaskView 需要追加的view
   * @param iMaskClickListener 点击回调监听器
   */
  public void addMaskView(final View mMaskView,final IMaskClickListener iMaskClickListener){
    if(mActivity instanceof BaseActivity){
      ((BaseActivity)mActivity).addMaskView(mMaskView,iMaskClickListener);
    }
  }

  /**
   * 往当前界面最顶层增加蒙层、顶级View
   * @param layoutId 布局文件
   * @param iMaskClickListener 点击回调监听器
   */
  public void addMaskView(final int layoutId,final IMaskClickListener iMaskClickListener){
    if(mActivity instanceof BaseActivity){
      ((BaseActivity)mActivity).addMaskView(layoutId,iMaskClickListener);
    }
  }

  /**
   * 往当前界面最顶层增加蒙层、顶级View
   * @param mRoot 追加view的根视图
   * @param mMaskView 需要追加的view
   * @param iMaskClickListener 点击回调监听器
   *
   */
  public void addMaskView(ViewGroup mRoot,View mMaskView,IMaskClickListener iMaskClickListener){
    if(mActivity instanceof BaseActivity){
      ((BaseActivity)mActivity).addMaskView(mRoot,mMaskView,iMaskClickListener);
    }
  }

  /**
   * 查找当前Fragment视图中的view
   * @param id
   * @return
   */
  protected View findViewById(int id){
    if(null == mContentView)return null;
    
    return mContentView.findViewById(id);
  }
  
  /**
   * 获取当前Fragment依附在的Activity
   * 
   * @return
   */
  protected Activity gainAttachActivity() {
    return mActivity;
  }

  /**
   * 获取Fragment存储的共享数据
   * @param strKey
   * @return
   */
  protected Object gainHostShareData(String strKey){
    Object value = null;
    if(mActivity instanceof BaseActivity){
      value = ((BaseActivity)mActivity).gainFragmentShareData(strKey);
    }
    return value;
  }

  /**
   * 获取共通操作机能
   */
  public Operation getOperation() {
    return mOperation;
  }
}
