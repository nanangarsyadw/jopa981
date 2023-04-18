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

import android.view.View;
import android.widget.AbsListView;

/**
 * 对接Listview模板的一些特性回调事件,每一个模板可以继续消费
 * <p/>
 *
 * @author 曾繁添
 * @version 1.0
 */
public interface ICallBackForListview extends AbsListView.OnScrollListener {

    /**
     * View移除屏幕回调
     *
     * @param view
     */
    void onMovedToScrapHeap(View view);

    /**
     * 滚动惯性停止监听回调
     *
     * @param mPageList
     * @param scrollState
     */
    void onScrollStateChanged(AbsListView mPageList, int scrollState);

    /**
     * 滚动回调
     *
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
}
