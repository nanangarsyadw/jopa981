package com.zftlive.android.library.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * RecyclerView适配器基类
 *
 * @author 曾繁添
 * @version 1.0
 */

public abstract class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 数据源
     */
    private List<IAdapterModel> mDataSource = new ArrayList<>();

    /**
     * 上下文
     */
    protected Context mContext;

    /**
     * 日志输出标识
     */
    protected final static String TAG = "RecyclerViewBaseAdapter";

    public RecyclerViewBaseAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取当前ListView绑定的数据源
     * @return
     */
    public List<IAdapterModel> gainDataSource(){
        return mDataSource;
    }

    /**
     * 根据所在位置获取数据源
     * @param position
     * @return
     */
    public IAdapterModel getItem(int position) {
        if (position < mDataSource.size())
            return mDataSource.get(position);
        else
            return null;
    }

    /**
     * 添加数据
     * @param object 数据模型
     */
    public boolean addItem(IAdapterModel object){
        return mDataSource.add(object);
    }

    /**
     * 在指定索引位置添加数据
     * @param location 索引
     * @param object 数据模型
     */
    public void addItem(int location,IAdapterModel object){
        mDataSource.add(location, object);
    }

    /**
     * 集合方式添加数据
     * @param collection 集合
     */
    public boolean addItem(Collection< ? extends IAdapterModel> collection){
        return mDataSource.addAll(collection);
    }

    /**
     * 在指定索引位置添加数据集合
     * @param location 索引
     * @param collection 数据集合
     */
    public boolean addItem(int location,Collection< ? extends IAdapterModel> collection){
        return mDataSource.addAll(location,collection);
    }

    /**
     * 移除指定对象数据
     * @param object 移除对象
     * @return 是否移除成功
     */
    public boolean removeItem(IAdapterModel object){
        return mDataSource.remove(object);
    }

    /**
     * 移除指定索引位置对象
     * @param location 删除对象索引位置
     * @return 被删除的对象
     */
    public Object removeItem(int location){
        return mDataSource.remove(location);
    }

    /**
     * 移除指定集合对象
     * @param collection 待移除的集合
     * @return 是否移除成功
     */
    public boolean removeAll(Collection< ? extends IAdapterModel> collection){
        return mDataSource.removeAll(collection);
    }

    /**
     * 清空数据
     */
    public void clear() {
        mDataSource.clear();
    }

    /**
     * 获取Activity方法
     * @return Activity的子类
     */
    public Activity getActivity(){
        if(mContext instanceof Activity){
            return (Activity) mContext;
        }
        return null;
    }

    /**
     * 获取Context
     * @return
     */
    public Context getContext(){
        return mContext;
    }
}
