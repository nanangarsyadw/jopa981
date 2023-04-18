package com.zftlive.android.library.base.templet;

import android.content.Context;
import android.view.View;

import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.base.adapter.RecyclerViewTemplet;

/**
 * RecyclerView空类型视图模板
 *
 * @author 曾繁添
 * @version 1.0
 */

public class CommonEmptyViewTemplet extends RecyclerViewTemplet {

    public CommonEmptyViewTemplet(Context mContext, View itemView) {
        super(mContext, itemView);
    }

    @Override
    public void initView() {

    }

    @Override
    public void fillData(IAdapterModel model, int position) {

    }
}
