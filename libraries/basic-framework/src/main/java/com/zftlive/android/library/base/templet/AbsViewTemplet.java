/*
 *
 *     Adapter多类型视图模板分离，模板化的经典案例，每个模板进行高度封装并且支持灵活扩展,减轻后期维护成本
 *
 *     来自京东金融团队Android基础开发库积累、沉淀、封装、共同整理分享
 *
 *     Copyright (c) 2017. @ 京东金融移动研发团队
 *
 *     技术支持：曾繁添<zengfantian@jd.com>
 *
 * /
 */

package com.zftlive.android.library.base.templet;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.zftlive.android.library.Logger;
import com.zftlive.android.library.R;
import com.zftlive.android.library.base.IBaseConstant;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.common.IForward;
import com.zftlive.android.library.common.ITrack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

/**
 * 模板基础抽象类,具备处理跳转、点击事件能力
 * <p/>
 * inflate-->bindLayout-->initView-->fillData
 *
 * @author 曾繁添
 * @version 1.0
 */
public abstract class AbsViewTemplet implements IBaseConstant, IViewTemplet,ICallBackForListview,View.OnClickListener {

    /**
     * 当前模板
     */
    protected AbsViewTemplet mTemplet;

    /**
     * 上下文
     */
    protected Context mContext;

    /**
     * Fragment持有，建议使用ITempletBridge交互桥的方式解耦业务代码
     */
    @Deprecated
    protected Fragment mFragment;

    /**
     * 当前item对应的view视图
     */
    protected View mLayoutView;

    /**
     * item布局父控件
     */
    protected ViewGroup parent;

    /**
     * 当前item对应的type类型
     */
    protected int viewType;

    /**
     * 当前位置索引
     */
    protected int position;

    /**
     * 当前行数据
     */
    protected IAdapterModel rowData;

    /**
     * 屏幕宽度
     */
    protected int mScreenWidth = 1080, mScreenHeight = 1920;

    /**
     * UI交互桥接
     */
    protected ITempletBridge mUIBridge;

    /**
     * 埋点日志输出标识
     */
    protected final String TRACK_TAG = "Track";

    /**
     * 1dp对应的px值
     */
    protected float mDensity = 3.0f;

    /**
     * 是否可见
     */
    protected boolean isVisibleToUser = true;

    /**
     * 日志输出标识
     */
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 是否开启debug日志
     */
    protected static boolean DEBUG = false;

    public AbsViewTemplet(Context mContext) {
        mTemplet = this;
        this.mContext = mContext;
        mDensity = mContext.getResources().getDisplayMetrics().density;
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public void onClick(View v) {
        try {
            itemClick(v, position, rowData);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.d(TAG, "点击跳转发生异常，原因：" + e.getMessage());
        }
    }

    /**
     * 渲染UI布局
     *
     * @param viewType item对应的类型
     * @param position 当前数据行位置索引
     * @param parent   根节点，没有可以传null
     * @return 当前模板渲染的View
     */
    public View inflate(int viewType, int position, ViewGroup parent) {
        this.parent = parent;
        this.viewType = viewType;
        this.position = position;

        mLayoutView = bindView();
        if (null == mLayoutView) {
            int layout = bindLayout();
            if (null != parent) {
                mLayoutView = LayoutInflater.from(mContext).inflate(layout, parent, false);
            } else {
                mLayoutView = LayoutInflater.from(mContext).inflate(layout, parent);
            }
        }
        //AdapterView不能设置点击事件,否则底层会抛异常
        if (null != mLayoutView && !(mLayoutView instanceof AdapterView)) {
            mLayoutView.setOnClickListener(this);
        }
        return mLayoutView;
    }

    /**
     * 渲染UI布局
     *
     * @param viewType item对应的类型
     * @param position 当前数据行位置索引
     * @param rowData  当前数据行数据
     * @param parent   根节点，没有可以传null
     * @return 当前模板渲染的View
     */
    public View inflate(int viewType, int position, IAdapterModel rowData, ViewGroup parent) {
        this.rowData = rowData;
        return inflate(viewType, position, parent);
    }

    /**
     * 绑定代码创建的View
     *
     * @return
     */
    public View bindView() {
        return null;
    }

    /**
     * 缓存住当前行对应的参数,供基类adapter的getView方法中调用
     *
     * @param viewType item对应的类型
     * @param position 当前数据所在位置索引
     * @param rowData  当前行数据
     */
    public void holdCurrentParams(int viewType, int position, IAdapterModel rowData) {
        this.viewType = viewType;
        this.position = position;
        this.rowData = rowData;
    }

    /**
     * 持有Fragment
     *
     * @param mFragment
     */
    public void holdFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    /**
     * 设置当前模板是否在屏幕中可见
     * @param visible
     */
    public void setVisibleToUser(boolean visible) {
        this.isVisibleToUser = visible;
    }

    /**
     * 设置UI交互桥接
     *
     * @param mUIBridge
     */
    public void setUIBridge(ITempletBridge mUIBridge) {
        this.mUIBridge = mUIBridge;
    }

    /**
     * 获取当前item渲染的view
     *
     * @return
     */
    public View getItemLayoutView() {
        return mLayoutView;
    }

    @Override
    public void onMovedToScrapHeap(View view) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onScrollStateChanged(AbsListView mPageList, int scrollState) {

    }

    /**
     * 获取当前item对应的viewType
     *
     * @return
     */
    public int getViewType() {
        return viewType;
    }

    /**
     * item点击事件处理
     *
     * @param view     当前点击的view
     * @param position 位置
     * @param rowData  当前行数据
     */
    public void itemClick(View view, int position, IAdapterModel rowData) {
        Log.d(TAG, "view-->" + view + " position=" + position + " rowData=" + rowData);
        Object forward = view.getTag(R.id.anl_dynamic_jump_data);
        if (null != forward && forward instanceof IForward) {
            forward((IForward) forward,view,position,rowData);
        }
        Object track = view.getTag(R.id.anl_dynamic_analysis_data);
        if (null != track && track instanceof ITrack) {
            trackEvent(mContext, (ITrack) track, position);
        }
    }

    /**
     * 动态埋点
     *
     * @param mContext 上下文
     * @param mMTABean 埋点信息
     */
    protected void trackEvent(Context mContext, ITrack mMTABean) {
        trackEvent(mContext, mMTABean, 0);
    }

    /**
     * 动态埋点
     *
     * @param mContext 上下文
     * @param mMTABean 埋点信息
     * @param position 列表位置
     */
    protected void trackEvent(Context mContext, ITrack mMTABean, int position) {

    }

    /**
     * 跳转界面
     * @param forward
     * @param view
     * @param position
     * @param rowData
     */
    protected void forward(IForward forward,View view, int position, Object rowData){

    }

    /**
     * 转换dp单位
     *
     * @param dpValue
     * @return
     */
    protected int getPxValueOfDp(float dpValue) {
        return (int) (mDensity * dpValue + 0.5f);
    }

    /**
     * 查找控件
     *
     * @param id
     * @return
     */
    protected View findViewById(int id) {
        if (null != mLayoutView) {
            return mLayoutView.findViewById(id);
        }
        return null;
    }

    /**
     * 绑定跳转和埋点数据
     *
     * @param track
     */
    protected void bindJumpTrackData(ITrack track) {
        bindJumpTrackData(null, track);
    }

    /**
     * 绑定跳转和埋点数据
     *
     * @param forward
     */
    protected void bindJumpTrackData(IForward forward) {
        bindJumpTrackData(forward, null);
    }

    /**
     * 绑定跳转和埋点数据
     *
     * @param forward
     * @param track
     */
    protected void bindJumpTrackData(IForward forward, ITrack track) {
        mLayoutView.setTag(R.id.anl_dynamic_jump_data, forward);
        mLayoutView.setTag(R.id.anl_dynamic_analysis_data, track);
    }

    /**
     * 绑定跳转和埋点数据
     *
     * @param forward
     * @param track
     * @param targetView
     */
    protected void bindJumpTrackData(Object forward, Object track, View targetView) {
        if (null == targetView) return;
        targetView.setOnClickListener(this);
        targetView.setTag(R.id.anl_dynamic_jump_data, forward);
        targetView.setTag(R.id.anl_dynamic_analysis_data, track);
    }

    /**
     * 获取指定位置item对应的数据源
     * @param index
     * @param dataType
     * @return
     */
    public <D> Object getDataSourceAt(ViewGroup mItemConatiner, int index, Class<D> dataType){
        if(null == mItemConatiner || mItemConatiner.getChildCount() < index){
            return null;
        }
        View mView = mItemConatiner.getChildAt(index);
        Object mDataTag = mView.getTag(R.id.anl_templet_data_source);
        return (D)mDataTag;
    }

    /**
     * 获取数据源
     * @param mItemView
     * @param dataType
     * @return
     */
    public <D> Object getDataFormViewTag(View mItemView,Class<D> dataType){
        if(null == mItemView){
            return null;
        }
        Object mDataTag = mItemView.getTag(R.id.anl_templet_data_source);
        if(null != mDataTag){
            return (D)mDataTag;
        }
        return null;
    }

    /**
     * 获取数据源
     * @param mItemView
     * @return
     */
    public Object getDataFormViewTag(View mItemView){
        if(null == mItemView){
            return null;
        }
        Object mDataTag = mItemView.getTag(R.id.anl_templet_data_source);
        return mDataTag;
    }

    /**
     * 绑定item数据源
     * @param mItemView
     * @param data
     */
    protected void bindItemDataSource(View mItemView,Object data){
        if(null == mItemView)return;
        mItemView.setTag(R.id.anl_templet_data_source,data);
    }

    /**
     * 给View绑定模板
     * @param mItemView
     * @param mTemplet
     */
    protected void bindTempletTag(View mItemView,AbsViewTemplet mTemplet){
        if(null == mItemView)return;
        mItemView.setTag(R.id.anl_dynamic_view_templet, mTemplet);
    }

    /**
     * 判断从接口拿到的color是否可用，可用的话返回可用的color，否则返回默认的
     * @param strColor
     * @param defaultColor
     * @return
     */
    public int getColor(String strColor, String defaultColor) {
        String color = isColor(strColor) ? strColor.trim() : defaultColor;
        return Color.parseColor(color);
    }

    /**
     * 判断是否是Color的格式，即#后3、6、8位16进制的格式，返回true后使用str要做trim()过滤空格
     * @param str
     * @return 是否是颜色格式
     */
    public boolean isColor(String str) {
        if(TextUtils.isEmpty(str)) {
            return false;
        }
        str = str.trim();
        if(!str.startsWith("#") || (str.length() != 4 && str.length() != 7 && str.length() != 9)) {
            return false;
        }

        String pattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3}|[A-Fa-f0-9]{8})$";
        return Pattern.matches(pattern, str);
    }

    /**
     * 构建模板实例
     *
     * @param mViewTemplet
     * @param arguments    构造方法形参
     * @param <D>
     * @return
     * @throws Exception
     */
    public static <D extends IViewTemplet> D createViewTemplet(Class<D> mViewTemplet, Object... arguments) {
        Constructor<?> constructor = findConstructor(mViewTemplet, arguments);
        D mTemplet = null;
        try {
            mTemplet = (D) constructor.newInstance(arguments);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return mTemplet;
    }

    /**
     * 匹配构造器
     *
     * @param mClass
     * @param params
     * @return
     */
    private static Constructor<?> findConstructor(Class<?> mClass, Object... params) {
        for (Constructor<?> constructor : mClass.getConstructors()) {
            Class<?>[] paramsTypes = constructor.getParameterTypes();
            if (paramsTypes.length == params.length) {
                boolean match = true;
                for (int i = 0; i < paramsTypes.length; i++) {
                    if (!paramsTypes[i].isAssignableFrom(params[i].getClass())) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return constructor;
                }
            }
        }
        return null;
    }

    /**
     * 是否输出调试日志
     *
     * @param isDebug
     */
    public static void isDebug(boolean isDebug) {

        DEBUG = isDebug;
    }
}
