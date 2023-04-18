package com.zftlive.android.sample.recyclerView;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zftlive.android.R;
import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.adapter.RecyclerViewBaseAdapter;
import com.zftlive.android.library.base.ui.CommonActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView样例代码
 *
 * @author 曾繁添
 * @version 1.0
 */

public class RecyclerViewActivity extends CommonActivity {

    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRecyclerView;
    private boolean isClick = false;
    private RecyclerViewBaseAdapter mListAdapter;

    @Override
    public int bindLayout() {
        return R.layout.activity_recycler_view;
    }

    @Override
    public void initView(View view) {
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipe.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipe.setRefreshing(false);
                    }
                }, 300);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        //初始化返回标题栏
        String strCenterTitle = getResources().getString(R.string.RecyclerViewActivity);
        mWindowTitle.initBackTitleBar(strCenterTitle);

        mWindowTitle.getDoneButton().setText("LayoutManager");
        final LinearLayoutManager mLLManager = new LinearLayoutManager(this);
        final GridLayoutManager mGridManager = new GridLayoutManager(this,3);
        mWindowTitle.getDoneButton().setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mRecyclerView.setLayoutManager(isClick?mLLManager:mGridManager);
                isClick = !isClick;
            }
        });
        mWindowTitle.getDoneButton().setVisibility(View.VISIBLE);
    }

    @Override
    public void doBusiness(Context mContext) {
        //准备数据源
        List<RowDataBean> mDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDataList.add(new RowDataBean((i==10?1:0),"标题" + i, "http://www.daqianduan.com/wp-content/uploads/2014/12/kanjian.jpg"));
        }

        //适配器
        mListAdapter = new MutilTypeSampleAdapter(mActivity);
        mListAdapter.addItem(mDataList);
        mRecyclerView.setAdapter(mListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
    }
}
