package com.zftlive.android.sample.recyclerView;

import android.content.Context;

import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.base.adapter.RecyclerViewMutilTypeAdapter;
import com.zftlive.android.library.base.adapter.RecyclerViewTemplet;

import java.util.Map;

/**
 * Created by zengfantian on 2017/8/23.
 */

public class MutilTypeSampleAdapter extends RecyclerViewMutilTypeAdapter {

    public MutilTypeSampleAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void registeViewTemplet(Map<Integer, Class<? extends RecyclerViewTemplet>> mViewTemplet) {
        mViewTemplet.put(0,ArticleViewTemplet.class);
        mViewTemplet.put(1,ButtonViewTemplet.class);
    }

    @Override
    protected int adjustItemViewType(IAdapterModel model, int position) {
        if(model instanceof RowDataBean){
            return ((RowDataBean)model).viewType;
        }
        return 0;
    }
}
