package com.zftlive.android.library.imageloader;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * Created by zengfantian on 2017/9/24.
 */

public class ImageOptions {

    // 圆角------
    public static DisplayImageOptions optionsRound = new DisplayImageOptions.Builder()
            .displayer(new RoundedBitmapDisplayer(320))
            .bitmapConfig(Bitmap.Config.RGB_565)
//            .showImageForEmptyUri(R.drawable.default_img)
            .imageScaleType(ImageScaleType.EXACTLY)
            .cacheInMemory(true)
            .cacheOnDisk(true).build();

    public static DisplayImageOptions commonOption = new DisplayImageOptions.Builder()
//	.displayer(new FadeInBitmapDisplayer(100))
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .cacheInMemory(true)
            .cacheOnDisk(true).build();

    // 标题栏共通背景色，需要带透明度，需要使用特殊的Config
    public static DisplayImageOptions highQulityOption = new DisplayImageOptions.Builder()
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .imageScaleType(ImageScaleType.NONE)
            .cacheInMemory(true)
            .cacheOnDisk(true).build();

    /**
     * 工程本地Drawable资源加载option
     */
    public static DisplayImageOptions mDrawableOption = new DisplayImageOptions.Builder()
            .resetViewBeforeLoading(true)
            .cacheInMemory(false)
            .cacheOnDisk(false)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .displayer(new SimpleBitmapDisplayer()).build();

}
