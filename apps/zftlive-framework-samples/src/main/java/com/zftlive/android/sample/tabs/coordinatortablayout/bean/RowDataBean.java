package com.zftlive.android.sample.tabs.coordinatortablayout.bean;

/**
 * Created by zengfantian on 2017/8/23.
 */

import com.zftlive.android.library.base.adapter.IAdapterModel;

/**
 * 数据模型
 */
public class RowDataBean implements IAdapterModel {

    public RowDataBean(){

    }
    public RowDataBean(int viewType, String title, String imageURL) {
        this.viewType = viewType;
        this.title = title;
        this.imageURL = imageURL;
    }

    public int viewType = 0;

    public String title = "";

    public String imageURL = "";
}
