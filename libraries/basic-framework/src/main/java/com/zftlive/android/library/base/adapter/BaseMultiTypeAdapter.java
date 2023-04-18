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

package com.zftlive.android.library.base.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.zftlive.android.library.Logger;
import com.zftlive.android.library.R;
import com.zftlive.android.library.base.templet.AbsViewTemplet;
import com.zftlive.android.library.base.templet.ITempletBridge;
import com.zftlive.android.library.base.templet.IViewTemplet;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 多种类型Item的Adapter基类
 * 
 * @author 曾繁添
 * @version 1.0
 */
public abstract class BaseMultiTypeAdapter extends BaseMAdapter{

  /**
   * 是否开启debug日志
   */
  protected static boolean DEBUG = false;

  /**
   * item模板映射<K=viewType,V=AbsViewTemplet>
   */
  private Map<Integer, Class<? extends IViewTemplet>> mViewTemplet = new TreeMap<>();

  /**
   * 持有Fragment，建议使用ITempletBridge交互桥的方式解耦业务代码
   */
  @Deprecated
  private Fragment mFragment;

  /**
   * UI交互桥接
   */
  protected ITempletBridge mUIBridge;

  public BaseMultiTypeAdapter(Activity mContext) {
    super(mContext);
    registeViewTemplet(mViewTemplet);
  }

  @Override
  public int getItemViewType(int position) {
    IAdapterModel model = getItem(position);
    return adjustItemViewType(model, position);
  }

  @Override
  public int getViewTypeCount() {
    //官方要求viewType必须从0索引开始
    int maxViewType = 0;
    for (Iterator<Integer> it = mViewTemplet.keySet().iterator(); it.hasNext(); ) {
      maxViewType = it.next();
    }
    //兼容跨越索引导致的ArrayIndexOutOfBoundsException,但是不推荐,因为会增加listview回收缓存view的数组长度开销
    return (maxViewType >= mViewTemplet.size()) ? (maxViewType + 1) : mViewTemplet.size();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    IViewTemplet mTemplet = null;
    int viewType = getItemViewType(position);
    if (null == convertView) {
      debugLog("第" + position + "个item,准备实例化viewType=" + viewType + "视图模板");
      mTemplet = AbsViewTemplet.createViewTemplet(mViewTemplet.get(viewType), getActivity());
      debugLog("第" + position + "个item,实例化完毕viewType=" + viewType + "视图模板-->" + mTemplet.getClass().getSimpleName());
      mTemplet.holdFragment(mFragment);
      mTemplet.setUIBridge(mUIBridge);
      mTemplet.inflate(viewType, position, parent);
      mTemplet.initView();
      convertView = mTemplet.getItemLayoutView();
      convertView.setTag(R.id.anl_dynamic_view_templet, mTemplet);
      convertView.setTag(R.id.anl_dynamic_elelemt_id, mTemplet.getClass().getName());
    } else {
      mTemplet = (AbsViewTemplet) convertView.getTag(R.id.anl_dynamic_view_templet);
      debugLog("第" + position + "个item,复用之前的viewType=" + viewType + "视图模板"+ mTemplet.getClass().getSimpleName());
    }

    //填充数据
    IAdapterModel rowData = getItem(position);

    mTemplet.holdCurrentParams(viewType, position, rowData);
    mTemplet.fillData(rowData, position);
    //绑定数据tag
    Object mDataTag = convertView.getTag(R.id.anl_templet_data_source);
    if (null == mDataTag) {
      convertView.setTag(R.id.anl_templet_data_source, rowData);
    }
    return convertView;
  }

  /**
   * 持有Fragment
   *
   * @param mFragment
   */
  public void holdFragment(Fragment mFragment) {
    this.mFragment = mFragment;
  }

  /**
   * 设置视图模板与UI交互的桥接
   *
   * @param mBridge 交互桥梁，可以自定义交互协议和方法
   */
  public void registeTempletBridge(ITempletBridge mBridge) {
    this.mUIBridge = mBridge;
  }

  /**
   * 获取视图模板与UI交互的桥接
   *
   * @return
   */
  public <B extends ITempletBridge> B getTempletBridge(Class<B> mBridge) {
    return (B) this.mUIBridge;
  }

  /**
   * 获取视图模板与UI交互的桥接
   *
   * @return
   */
  public Object getTempletBridge() {
    return this.mUIBridge;
  }

  /**
   * 输出调试日志
   *
   * @param msg
   */
  protected void debugLog(String msg) {
    if (!DEBUG) return;
    Logger.d(TAG, msg);
  }

  /**
   * 是否输出调试日志
   *
   * @param isDebug
   */
  public static void isDebug(boolean isDebug) {
    DEBUG = isDebug;
  }

  /**
   * 注册viewType以及绑定的Templet
   *
   * @param mViewTemplet
   */
  protected abstract void registeViewTemplet(Map<Integer, Class<? extends IViewTemplet>> mViewTemplet);

  /**
   * 根据数据模型返回对应的ViewType
   *
   * @param model    数据模型
   * @param position 当前数据位置
   * @return
   */
  protected abstract int adjustItemViewType(IAdapterModel model, int position);
}
