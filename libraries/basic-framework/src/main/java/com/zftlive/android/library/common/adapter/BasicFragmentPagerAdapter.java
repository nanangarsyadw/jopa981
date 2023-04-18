package com.zftlive.android.library.common.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.bean.TabBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 固定数量tab的Fragment类型adapter
 *
 * @author 曾繁添
 * @version 1.0
 */
public class BasicFragmentPagerAdapter extends FragmentPagerAdapter {

    /**
     * 存储当前栈活动的Fragment
     **/
    private Map<Integer, Fragment> mPageReferenceMap = new HashMap<Integer, Fragment>();
    /**
     * 数据存储集合
     **/
    private List<TabBean> mTabs = new ArrayList<TabBean>();
    /**
     * Context上下文
     **/
    private Activity mActivity;
    /**
     * 当前Fragment
     **/
    private Fragment mCurrentFragment;
    /**
     * 当前 tab索引
     **/
    private int mCurrentIndex = 0;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    public BasicFragmentPagerAdapter(FragmentManager fm, Activity mActivity) {
        super(fm);
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public Fragment getItem(int position) {
        TabBean info = mTabs.get(position);
        if (null == info || null == info.getClss()) {
            Logger.w(TAG, "当前tab对应的Fragment类为null");
            return null;
        }
        Fragment mFragment = Fragment.instantiate(mActivity, info.getClss().getName(), info.getArgs());
        mPageReferenceMap.put(Integer.valueOf(position), mFragment);
        return mFragment;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        TabBean info = mTabs.get(position);
        if (null == info || null == info.getLabel()) {
            Logger.w(TAG, "当前tab对应的label类为null");
            return "null";
        }

        return info.getLabel();
    }

    /**
     * FragmentStatePagerAdapter在会在因POSITION_NONE触发调用的destroyItem()中真正的释放资源,重新建立一个新的 Fragment<br>
     * FragmentPagerAdapter 仅仅会在 destroyItem()中detach这个Fragment,在instantiateItem()时会使用旧的Fragment,并触发attach,因此没有释放资源及重建的过程
     *
     * @param object
     * @return
     * @link http://stackoverflow.com/questions/13453077/tabs-viewpager-fragmentstatepageradapter-how-to-remove-fragment
     * @link http://www.cnblogs.com/lianghui66/p/3607091.html
     */
//  @Override
//  public int getItemPosition(Object object){
//    return POSITION_NONE;
//  }
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentFragment = (Fragment) object;
        mCurrentIndex = position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
//        mPageReferenceMap.remove(Integer.valueOf(position));
    }

    /**
     * 获取指定位置的Fragment
     *
     * @param index 索引
     * @return
     */
    public Fragment getFragment(int index) {
//    Logger.d(TAG,"当前活动栈Fragment集合size="+mPageReferenceMap.size());
        return mPageReferenceMap.get(index);
    }

    /**
     * 获取当前Fragment
     *
     * @return
     */
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    /**
     * 获取当前tab的索引
     *
     * @return
     */
    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    /**
     * 获取指定位置的Tab
     *
     * @param position
     * @return
     */
    public TabBean getTab(int position) {
        if (position < mTabs.size()) {
            return mTabs.get(position);
        }
        return null;
    }

    /**
     * 根据TabId获取指定的tab
     *
     * @param tabId
     * @return
     */
    public TabBean getTab(String tabId) {
        if (TextUtils.isEmpty(tabId)) {
            return null;
        }
        for (int i = 0; i < mTabs.size(); i++) {
            TabBean tabBean = mTabs.get(i);
            if (tabId.equals(tabBean.getValue())) {
                return mTabs.get(i);
            }
        }
        return null;
    }

    /**
     * 添加数据
     *
     * @param object TAB数据项
     */
    public boolean addItem(TabBean object) {
        return mTabs.add(object);
    }

    /**
     * 在指定索引位置添加数据
     *
     * @param location 索引
     * @param object   数据
     */
    public void addItem(int location, TabBean object) {
        mTabs.add(location, object);
    }

    /**
     * 集合方式添加数据
     *
     * @param collection 集合
     */
    public boolean addItem(Collection<? extends TabBean> collection) {
        return mTabs.addAll(collection);
    }

    /**
     * 在指定索引位置添加数据集合
     *
     * @param location   索引
     * @param collection 数据集合
     */
    public boolean addItem(int location, Collection<? extends TabBean> collection) {
        return mTabs.addAll(location, collection);
    }

    /**
     * 移除指定对象数据
     *
     * @param object 移除对象
     * @return 是否移除成功
     */
    public boolean removeItem(TabBean object) {
        return mTabs.remove(object);
    }

    /**
     * 移除指定索引位置对象
     *
     * @param location 删除对象索引位置
     * @return 被删除的对象
     */
    public Object removeItem(int location) {
        return mTabs.remove(location);
    }

    /**
     * 移除指定集合对象
     *
     * @param collection 待移除的集合
     * @return 是否移除成功
     */
    public boolean removeAll(Collection<? extends TabBean> collection) {
        return mTabs.removeAll(collection);
    }

    /**
     * 获取当前标签页绑定的数据源
     *
     * @return
     */
    public List<TabBean> gainDataSource() {
        return this.mTabs;
    }

    /**
     * 获取所有的Fragment
     *
     * @return
     */
    public Collection<Fragment> gainFragments() {
        return mPageReferenceMap.values();
    }

    /**
     * 清空数据
     */
    public void clear() {
        mTabs.clear();
    }

    /**
     * 获取Activity方法
     *
     * @return Activity的子类
     */
    public Activity getActivity() {
        return mActivity;
    }
}
