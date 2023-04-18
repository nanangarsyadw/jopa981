package com.zftlive.android.library.dynamicpage.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.templet.AbsViewTemplet;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.base.ui.BaseFragmentV4;
import com.zftlive.android.library.imageloader.ToolImage;
import com.zftlive.android.library.widget.container.SwipeRefreshListview;
import com.zftlive.android.library.dynamicpage.IPageConstant;
import com.zftlive.android.library.dynamicpage.PageBusinessControl;
import com.zftlive.android.library.dynamicpage.PageViewTempletBridge;
import com.zftlive.android.library.dynamicpage.R;
import com.zftlive.android.library.dynamicpage.adapter.DynamicPageAdatper;
import com.zftlive.android.library.dynamicpage.bean.Page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 动态页面Activity
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class DynamicPageFragment extends BaseFragmentV4 implements IPageConstant,AbsListView.RecyclerListener,SwipeRefreshListview.RefreshListener {

    /**
     * 下拉刷新
     */
    private SwipeRefreshListview mSRList;

    /**
     * 列表
     */
    private ListView mPageList;

    /**
     * 列表适配器
     */
    private DynamicPageAdatper mListAdapter;

    /**
     * 页面id
     */
    private String mPageId = "";

    /**
     * 页面数据
     */
    private Page mPageData;

    @Override
    public String initTitle() {
        return mPageData.title;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_dynamic_page;
    }

    @Override
    public void initParams(Bundle params) {
        mPageId = params.getString(PARAM_PAGE_ID);
        //本地模拟页面数据组装
        mPageData = PageBusinessControl.initPageData();
    }

    @Override
    public void initView(View view) {
        //下拉刷新Listview
        mSRList = (SwipeRefreshListview)findViewById(R.id.srl_page_container);
        mSRList.setRefreshListener(this);
        //下拉旋转圈颜色
        mSRList.setColorSchemeResources(R.color.common_module_blue_508cee);
        mPageList = mSRList.getRefreshableView();
        mPageList.setRecyclerListener(this);
        //ImageLoader滑动过程不加载
        mPageList.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doBusiness(mActivity);
    }

    /**
     * 业务操作
     * @param mContext
     */
    public void doBusiness(Context mContext) {
        //初始化ImageLoader
        ToolImage.init(mContext.getApplicationContext());

        //初始化标题栏右侧刷新按钮
        mWindowTitle.initBackTitleBar(mPageData.title);
        mWindowTitle.getTitleTextView().setTextColor(Color.WHITE);
        mWindowTitle.getDoneButton().setVisibility(View.VISIBLE);
        mWindowTitle.getDoneButton().setText("移除元素");
        mWindowTitle.getDoneButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<IAdapterModel> floorList = mListAdapter.gainDataSource();
                floorList.remove(0);
                mListAdapter.notifyDataSetChanged();
            }
        });

        //交互桥梁
        PageViewTempletBridge mBridge = new PageViewTempletBridge(mContext);
        mBridge.setPageData(mPageData);
        //列表Adapter
        mListAdapter = new DynamicPageAdatper(mActivity);
        mListAdapter.registeTempletBridge(mBridge);

        //添加数据源
        ArrayList mItemList = PageBusinessControl.convertPageDataToListItem(mPageData);
        mListAdapter.addItem(mItemList);

        //注册模板
        Iterator<Map.Entry<Integer, Class<? extends AbsViewTemplet>>> it = PageBusinessControl.mTempletMapper.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<Integer, Class<? extends AbsViewTemplet>> entry = it.next();
            mListAdapter.registeViewTemplet(entry.getKey(), entry.getValue());
        }
        mPageList.setAdapter(mListAdapter);
    }

    @Override
    public void onRefresh() {
        ArrayList mItemList = PageBusinessControl.convertPageDataToListItem(mPageData);
        mListAdapter.clear();
        mListAdapter.addItem(mItemList);
        mListAdapter.notifyDataSetChanged();
        mSRList.onRefreshComplete();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onMovedToScrapHeap(View view) {
        Logger.d(TAG,"listview开始回收View-->"+view.toString());
    }

}
