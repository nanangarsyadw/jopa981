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
 *
 */

package com.zftlive.android.library.common.adapter;

import android.app.Activity;

import com.zftlive.android.library.base.adapter.BaseMultiTypeAdapter;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.base.templet.IViewTemplet;

import java.util.Map;

/**
 * 普通(一种itemType)的Adapter
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class SingleTypeAdapter extends BaseMultiTypeAdapter {

  /**
   * 缓存视图模板集合
   */
  private Map<Integer, Class<? extends IViewTemplet>> mViewTemplet;

  public SingleTypeAdapter(Activity mContext) {
    super(mContext);
  }

  @Override
  protected void registeViewTemplet(Map<Integer, Class<? extends IViewTemplet>> mViewTemplet) {
    this.mViewTemplet = mViewTemplet;
  }

  @Override
  protected int adjustItemViewType(IAdapterModel model, int position) {
    return 0;
  }

  /**
   * 注册视图模板
   * @param mTemplet
   */
  public void registeViewTemplet(Class<? extends IViewTemplet> mTemplet){
    mViewTemplet.put(0,mTemplet);
  }
}
