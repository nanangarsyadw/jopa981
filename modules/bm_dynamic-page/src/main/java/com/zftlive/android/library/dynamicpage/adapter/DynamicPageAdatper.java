/*
 *     Android基础开发个人积累、沉淀、封装、整理共通
 *     Copyright (c) 2016. 曾繁添 <zftlive@163.com>
 *     Github：https://github.com/zengfantian || http://git.oschina.net/zftlive
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package com.zftlive.android.library.dynamicpage.adapter;

import android.app.Activity;
import android.content.Context;

import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.common.adapter.MultiTypeAdapter;
import com.zftlive.android.library.dynamicpage.IPageConstant;
import com.zftlive.android.library.dynamicpage.bean.PageListElementBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 * 动态页面数据适配器
 *
 * @author 曾繁添
 * @version 1.0
 */
public class DynamicPageAdatper extends MultiTypeAdapter implements IPageConstant {

    public DynamicPageAdatper(Activity mContext) {
        super(mContext);
//        Log.e("class",getClasses(mContext,"com.zftlive.android.library.dynamicpage.templet").toString());
    }

    @Override
    protected int adjustItemViewType(IAdapterModel model, int position) {
        if(null != model && model instanceof PageListElementBean){
            return ((PageListElementBean)model).itemType;
        }
        return 0;
    }

    private List<Class> getClasses(Context mContext, String packageName) {
        ArrayList classes = new ArrayList<>();
        try {
            String packageCodePath = mContext.getPackageCodePath();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            DexFile df = new DexFile(packageCodePath);
            String regExp = "^" + packageName + ".\\w+$";
            for (Enumeration iter = df.entries(); iter.hasMoreElements(); ) {
                String className = (String)iter.nextElement();
                try {
                    if (className.matches(regExp)) {
                        Class<?> target = classLoader.loadClass(className);
                        classes.add(target);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }
}
