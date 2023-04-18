/*
 *
 *     Adapter多类型视图模板分离，模板化的经典案例，每个模板进行高度封装并且支持灵活扩展,减轻后期维护成本
 *
 *     来自京东金融团队Android基础开发库积累、沉淀、封装、共同整理分享
 *
 *     Copyright (c) 2017. @ 京东金融移动研发团队
 *
 *     技术支持：曾繁添<zengfantian@jd.com>
 *
 * /
 */

package com.zftlive.android.library.base.templet;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.zftlive.android.library.base.adapter.IAdapterModel;

/**
 * 模板基础抽象接口定义
 * <p/>
 * inflate-->bindLayout-->initView-->fillData
 *
 * @author 曾繁添
 * @version 1.0
 */
public interface IViewTemplet {

    /**
     * 模板View视图
     */
    View bindView();

    /**
     * 模板布局layout
     */
    int bindLayout();

    /**
     * 渲染UI布局
     *
     * @param viewType item对应的类型
     * @param position  当前数据行位置索引
     * @param parent   根节点，没有可以传null
     * @return 当前模板渲染的View
     */
    View inflate(int viewType, int position, ViewGroup parent);

    /**
     * 渲染UI布局
     *
     * @param viewType item对应的类型
     * @param position  当前数据行位置索引
     * @param rowData  当前数据行数据
     * @param parent   根节点，没有可以传null
     * @return 当前模板渲染的View
     */
    View inflate(int viewType, int position, IAdapterModel rowData, ViewGroup parent);

    /**
     * 初始化控件
     */
    void initView();

    /**
     * 装填数据
     */
    void fillData(IAdapterModel model, int position);

    /**
     * 获取当前item布局
     * @return
     */
    View getItemLayoutView();

    /**
     * 持有对应的Fragment
     * @param mFragment
     */
    void holdFragment(Fragment mFragment);

    /**
     * 设置模板与UI交互的桥接
     * @param mUIBridge
     */
    void setUIBridge(ITempletBridge mUIBridge);

    /**
     * 缓存住当前行对应的参数,供基类adapter的getView方法中调用
     *
     * @param viewType item对应的类型
     * @param position 当前数据所在位置索引
     * @param rowData  当前行数据
     */
    void holdCurrentParams(int viewType, int position, IAdapterModel rowData);
}
