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

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * 视图模板与UI交互业务桥基类
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public abstract class AbsTempletBridge implements ITempletBridge {

    protected WeakReference<Context> mContext;

    public AbsTempletBridge(Context mContext){
        this.mContext = new WeakReference<Context>(mContext);
    }

    @Override
    public Context getRefContext() {
        return mContext.get();
    }
}
