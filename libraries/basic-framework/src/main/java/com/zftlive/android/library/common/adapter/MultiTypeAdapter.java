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
import android.content.Context;

import com.zftlive.android.library.base.templet.AbsViewTemplet;
import com.zftlive.android.library.base.adapter.BaseMultiTypeAdapter;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.base.bean.AdapterModelBean;
import com.zftlive.android.library.base.templet.IViewTemplet;

import java.util.Map;

/**
 * 复杂类型(多种itemType)的Adapter
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class MultiTypeAdapter extends BaseMultiTypeAdapter {

  /**
   * 缓存视图模板集合
   */
  protected Map<Integer, Class<? extends IViewTemplet>> mViewTemplet;

  public MultiTypeAdapter(Activity mContext) {
    super(mContext);
  }

  @Override
  protected void registeViewTemplet(Map<Integer, Class<? extends IViewTemplet>> mViewTemplet) {
    this.mViewTemplet = mViewTemplet;
  }

  @Override
  protected int adjustItemViewType(IAdapterModel model, int position) {
    if(model instanceof AdapterModelBean){
      return ((AdapterModelBean)model).itemType;
    }
    return 0;
  }

  /**
   * 注册viewType以及绑定的Templet<br>
   * 注意：要在listview.setAdapter方法之前调用
   * @param viewType item对应的type,从0开始
   * @param mTemplet 视图模板
   */
  public void registeViewTemplet(int viewType,Class<? extends IViewTemplet> mTemplet){
    mViewTemplet.put(viewType,mTemplet);
  }

}
