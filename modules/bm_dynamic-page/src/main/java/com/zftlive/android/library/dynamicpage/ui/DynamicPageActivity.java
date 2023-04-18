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

package com.zftlive.android.library.dynamicpage.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zftlive.android.library.base.ui.FragmentBaseActivity;
import com.zftlive.android.library.imageloader.CommonImageLoaderListener;
import com.zftlive.android.library.dynamicpage.IPageConstant;

/**
 * 动态页面Activity
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class DynamicPageActivity extends FragmentBaseActivity implements IPageConstant {

    /**
     * 页面id
     */
    private String mPageId = "";

    @Override
    public void initParams(Bundle params) {
        mPageId = params.getString(PARAM_PAGE_ID);
    }

    @Override
    public void doBusiness(Context mContext) {
        DynamicPageFragment mPageFragment = new DynamicPageFragment();
        //可以传递pageID请求接口拉取数据,或者直接传递整个页面数据
        Bundle mParams = new Bundle();
        mParams.putString(PARAM_PAGE_ID,mPageId);
        mPageFragment.setArguments(mParams);

        attachFirstFragment(mPageFragment);
    }

    @Override
    protected void onDestroy() {
        //清除缓存
        CommonImageLoaderListener.clearDisplayedImages();
        //清除内存
        ImageLoader.getInstance().clearMemoryCache();
        super.onDestroy();
    }
}
