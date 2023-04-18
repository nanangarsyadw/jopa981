package com.zftlive.android.library.common.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.bean.TabBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于自定义View的滑动ViewPager适配器
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class BasicPagerAdapter extends PagerAdapter {

    protected List<View> mViewList = new ArrayList<View>();

    protected List<TabBean> mLabelList = new ArrayList<TabBean>();
    /**日志输出标志**/
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public CharSequence getPageTitle(int position) {
        TabBean info = mLabelList.get(position);
        if(null == info){
            Logger.w(TAG, "当前tab对应的label类为null");
            return "null";
        }

        return info.label;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public Object instantiateItem(View container, int position) {
        // 初始化标签页
        ((ViewPager) container).addView(mViewList.get(position), 0);
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        // 移除标签页
        ((ViewPager) container).removeView(mViewList.get(position));
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // 用于判断是否需要添加标签页
        return arg0 == arg1;
    }

    /**
     * 获取指定位置的Tab
     * @param position
     * @return
     */
    public View getItemView(int position){
        if(position < mViewList.size()){
            return mViewList.get(position);
        }
        return null;
    }

    /**
     * 获取指定位置的Tab数据
     * @param position
     * @return
     */
    public TabBean getTabItem(int position){
        if(position < mLabelList.size()){
            return mLabelList.get(position);
        }
        return null;
    }

    /**
     * 获取数据源
     * @return
     */
    public List<View> getDataSource(){
        return mViewList;
    }

    /**
     * 添加View标签页
     *
     * @param item
     *            View标签页
     */
    public void addViewItem(View item) {
        mViewList.add(item);
    }

    /**
     * 添加View标签页
     * @param tabBean
     *            tab名称
     * @param item
     *            View标签页
     */
    public void addViewItem(TabBean tabBean, View item) {
        mViewList.add(item);
        mLabelList.add(tabBean);
    }

//    /**
//     * 移除View视图item
//     * @param item
//     */
//    public void removeViewItem(View item){
//        mViewList.remove(item);
//    }

    /**
     * 移除View视图item
     * @param index 位置
     */
    public void removeViewItem(int index){
        mViewList.remove(index);
        mLabelList.remove(index);
    }

    /**
     * 移除全部View视图item
     */
    public void clearViewItem(){
        mViewList.clear();
        mLabelList.clear();
    }

    /**
     * 重新创建数据源
     */
    public void newAnList(){
        mViewList = new ArrayList<>();
        mLabelList = new ArrayList<>();
    }
}
