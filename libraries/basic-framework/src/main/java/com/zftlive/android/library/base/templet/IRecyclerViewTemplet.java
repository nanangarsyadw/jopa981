package com.zftlive.android.library.base.templet;

import android.view.View;

import com.zftlive.android.library.base.adapter.IAdapterModel;

/**
 * 模板基础抽象接口定义
 * <p/>
 * inflate-->bindLayout-->initView-->fillData
 *
 * @author 曾繁添
 * @version 1.0
 */
public interface IRecyclerViewTemplet {

    /**
     * 模板布局layout
     */
    int bindLayout();

    /**
     * 初始化控件
     */
    void initView();

    /**
     * 装填数据
     */
    void fillData(IAdapterModel model, int postion);

    /**
     * 获取当前item布局
     * @return
     */
    View getItemLayoutView();

}