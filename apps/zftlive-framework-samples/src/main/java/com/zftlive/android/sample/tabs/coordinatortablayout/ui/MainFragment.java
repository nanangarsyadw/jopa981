package com.zftlive.android.sample.tabs.coordinatortablayout.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zftlive.android.R;
import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.adapter.RecyclerViewBaseAdapter;
import com.zftlive.android.library.base.ui.BaseFragmentV4;
import com.zftlive.android.sample.recyclerView.MutilTypeSampleAdapter;
import com.zftlive.android.sample.tabs.coordinatortablayout.bean.RowDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * vp对应的每一个item
 */
public class MainFragment extends BaseFragmentV4 {
    private RecyclerViewBaseAdapter mListAdapter;
    private RecyclerView mRecyclerView;
    public static final String ARG_TITLE = "title";
    private String mTitle = "title";

    @Override
    public int bindLayout() {
        return R.layout.tab_fragment_main;
    }

    @Override
    public void initParams(Bundle params) {
        mTitle = params.getString(ARG_TITLE);
    }

    @Override
    public void initView(View view) {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        //滚动监听
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Logger.d(TAG, "onScrolled-->newState="+newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Logger.d(TAG, "onScrolled-->dx="+dx + " dy="+dy);
            }
        });
        //移除屏幕监听
        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                Logger.d(TAG, "onViewRecycled-->"+holder.getClass().getName());
            }
        });


        //准备数据源
        List<RowDataBean> mDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDataList.add(new RowDataBean((i==10?1:0),"标题-"+mTitle + i, "http://www.daqianduan.com/wp-content/uploads/2014/12/kanjian.jpg"));
        }
        //适配器
        mListAdapter = new MutilTypeSampleAdapter(mActivity);
        mListAdapter.addItem(mDataList);
        mRecyclerView.setAdapter(mListAdapter);
    }

}