package com.zftlive.android.library.base.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zftlive.android.library.base.templet.IRecyclerViewTemplet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * RecyclerView单个类型视图模板
 *
 * @author 曾繁添
 * @version 1.0
 */

public abstract class RecyclerViewTemplet extends RecyclerView.ViewHolder implements IRecyclerViewTemplet,View.OnClickListener, View.OnLongClickListener {

    /**
     * 上下文
     */
    protected Context mContext;

    public RecyclerViewTemplet(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public int bindLayout() {
        return 0;
    }

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 填充数据
     *
     * @param model
     * @param position
     */
    public abstract void fillData(IAdapterModel model, int position);

    /**
     * 获取当前item的布局
     *
     * @return
     */
    public View getItemLayoutView() {
        return itemView;
    }

    /**
     * 查找控件
     *
     * @param id
     * @return
     */
    protected View findViewById(@IdRes int id) {
        if (null == itemView) return null;
        View target = itemView.findViewById(id);
        return target;
    }

    /**
     * 构建模板实例
     *
     * @param mViewTemplet
     * @param arguments    构造方法形参
     * @param <VT>
     * @return
     * @throws Exception
     */
    public static <VT extends RecyclerViewTemplet> VT createViewTemplet(Class<VT> mViewTemplet, Object... arguments) {
        Constructor<?> constructor = findConstructor(mViewTemplet, arguments);
        VT mTemplet = null;
        try {
            mTemplet = (VT) constructor.newInstance(arguments);
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
}
