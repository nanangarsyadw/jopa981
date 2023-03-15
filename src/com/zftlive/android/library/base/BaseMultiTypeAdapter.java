package com.zftlive.android.library.base;

import android.view.View;
import android.view.ViewGroup;

/**
 * 多种类型Item的Adapter基类
 * 
 * @author 曾繁添
 * @version 1.0
 * @param <T> 数据模型Bean
 */
public abstract class BaseMultiTypeAdapter extends BaseMAdapter implements IBaseConstant {

  @Override
  public int getItemViewType(int position) {
    return itemViewType(position);
  }

  @Override
  public int getViewTypeCount() {
    return viewTypeCount();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    BaseMultiViewHolder mViewHolder = null;
    int viewType = getItemViewType(position);
    // 渲染item布局和装填数据
    convertView = inflateItemAndFillData(position, convertView, parent, viewType, mViewHolder);
    return convertView;
  }

  /**
   * 渲染item布局和装填数据
   * 
   * @param position
   * @param convertView
   * @param parent
   * @param viewType
   * @param mViewHolder
   * @return
   */
  private View inflateItemAndFillData(int position, View convertView, ViewGroup parent,
      int viewType, BaseMultiViewHolder mViewHolder) {
    // 渲染item布局
    View mView = inflateItem(position, convertView, parent, viewType, mViewHolder);
    // 装填数据
    BaseModelBean mBean = (BaseModelBean) getItem(position);
    fillData(position, viewType, mViewHolder, mBean);

    return mView;
  }

  /**
   * 根据数据模型返回对应的ViewType
   * 
   * @param position
   * @return
   */
  public abstract int itemViewType(int position);

  /**
   * 获取type类型总数
   * 
   * @return
   */
  public abstract int viewTypeCount();

  /**
   * 渲染UI
   * 
   * @param position
   * @param convertView
   * @param parent
   * @param viewType
   * @param mViewHolder
   * @return
   */
  public abstract View inflateItem(int position, View convertView, ViewGroup parent, int viewType,
      BaseMultiViewHolder mViewHolder);

  /**
   * 填充数据
   * 
   * @param position
   * @param viewType
   * @param rowData
   * @param mViewHolder
   */
  public abstract void fillData(int position, int viewType, BaseMultiViewHolder mViewHolder,
      BaseModelBean rowData);
}
