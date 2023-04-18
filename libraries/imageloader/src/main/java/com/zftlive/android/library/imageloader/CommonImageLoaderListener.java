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

package com.zftlive.android.library.imageloader;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 常用图片加载监听器,加载取消或者失败会将ImageView的ScaleType设置成FIX_XY
 *
 * @author 曾繁添
 * @version 1.0
 */
public class CommonImageLoaderListener extends SimpleImageLoadingListener {

    /**
     * 是否第一次加载集合
     */
    protected final static List<String> displayedImages = Collections
            .synchronizedList(new LinkedList<String>());
    /**
     * 是否需要渐现
     */
    private boolean isFade = false;

    /**
     * 日志输出标识
     */
    protected final static String TAG = ToolImage.class.getClass().getSimpleName();

//    private static CommonImageLoaderListener instance;
//
//    private CommonImageLoaderListener() {
//
//    }
//
//    public static CommonImageLoaderListener getInstance() {
//        if (instance == null) {
//            synchronized (CommonImageLoaderListener.class) {
//                if (instance == null) {
//                    instance = new CommonImageLoaderListener();
//                }
//            }
//        }
//        return instance;
//    }

    public CommonImageLoaderListener() {
    }

    public CommonImageLoaderListener(boolean isFade) {
        this.isFade = isFade;
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        super.onLoadingComplete(imageUri, view, loadedImage);
        if (null != view && null != loadedImage) {
            ImageView imageView = (ImageView) view;
            // 是否第一次显示
            if (isFade && !displayedImages.contains(imageUri)) {
                FadeInBitmapDisplayer.animate(imageView, 200);
                displayedImages.add(imageUri);
            }
        }
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        super.onLoadingFailed(imageUri, view, failReason);
        if (null != view && null != view) {
            ImageView imageView = (ImageView) view;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    /**
     * 内存溢出
     *
     * @param imageUri
     * @param view
     */
    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        super.onLoadingCancelled(imageUri, view);
        if (null != view && null != view) {
            ImageView imageView = (ImageView) view;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    /**
     * 清除图片
     */
    public static void clearDisplayedImages() {
        displayedImages.clear();
    }

    /**
     * 清除图片
     */
    public static void clearDisplayedImages(String imageURL) {
        displayedImages.remove(imageURL);
    }

}
