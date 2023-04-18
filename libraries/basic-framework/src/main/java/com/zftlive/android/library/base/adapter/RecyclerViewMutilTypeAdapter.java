package com.zftlive.android.library.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zftlive.android.library.R;
import com.zftlive.android.library.annotation.Config;
import com.zftlive.android.library.base.templet.CommonEmptyViewTemplet;

import java.util.Map;
import java.util.TreeMap;

/**
 * RecyclerView适配器多类型基类
 *
 * @author 曾繁添
 * @version 1.0
 */

public abstract class RecyclerViewMutilTypeAdapter extends RecyclerViewBaseAdapter {

    /**
     * viewType与Templet映射关系
     */
    private Map<Integer, Class<? extends RecyclerViewTemplet>> mViewTemplet = new TreeMap<>();

    /**
     * viewType与layout映射关系
     */
    private Map<Integer, Integer> mViewLayoutCache = new TreeMap<>();

    public RecyclerViewMutilTypeAdapter(Context mContext) {
        super(mContext);
        registeViewTemplet(mViewTemplet);
    }

    @Override
    public int getItemViewType(int position) {
        IAdapterModel model = getItem(position);
        return adjustItemViewType(model, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //获得对应的模板配置信息
        Class<? extends RecyclerViewTemplet> templet = mViewTemplet.get(viewType);

        if (null == templet) {
            Log.e(TAG, "onCreateViewHolder-->can not find ViewTempletConfig for viewType=" + viewType + " in mViewTemplet");
        }
        //优先从缓存中获取,然后在从注解中获取,否则使用默认站位view
        int layoutResId = -1;
        if(mViewLayoutCache.isEmpty() || layoutResId <= 0){
            if (null != templet && templet.isAnnotationPresent(Config.class)) {
                Config layout = (Config) templet.getAnnotation(Config.class);
                layoutResId = layout.layout();
            }else{
                layoutResId = R.layout.common_recycler_empty_view;
            }
            mViewLayoutCache.put(viewType,layoutResId);
        }
        layoutResId = mViewLayoutCache.get(viewType);
        //对应的视图
        View itemView = LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
        RecyclerViewTemplet mTemplet = RecyclerViewTemplet.createViewTemplet(templet, mContext, itemView);
        //容错处理
        if(null == mTemplet){
            mTemplet = new CommonEmptyViewTemplet(mContext,itemView);
        }
        mTemplet.initView();
        mTemplet.getItemLayoutView().setOnClickListener(mTemplet);
        mTemplet.getItemLayoutView().setOnLongClickListener(mTemplet);
        mTemplet.getItemLayoutView().setTag(R.id.anl_view_templet, mTemplet);
        return mTemplet;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //合法性校验
        if (null == holder || !(holder instanceof RecyclerViewTemplet)) {
            Log.e(TAG, "onBindViewHolder-->holder is null or holder is not instanceof RecyclerViewTemplet");
            return;
        }
        //填充数据
        IAdapterModel rowData = getItem(position);
        RecyclerViewTemplet mTemplet = (RecyclerViewTemplet) holder;
        mTemplet.fillData(rowData, position);
    }

    /**
     * 注册viewType以及绑定的Templet，一定要在listview.setAdapter方法之前调用
     *
     * @param mViewTemplet
     */
    protected abstract void registeViewTemplet(Map<Integer, Class<? extends RecyclerViewTemplet>> mViewTemplet);

    /**
     * 根据数据模型返回对应的ViewType
     *
     * @param model 数据模型
     * @param position 当前数据位置
     * @return
     */
    protected abstract int adjustItemViewType(IAdapterModel model, int position);
}
