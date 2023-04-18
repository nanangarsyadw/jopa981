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
 */

package com.zftlive.android.library.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;


/**
 * 图片加载工具类
 *
 * @author 曾繁添
 * @version 1.0
 */
public class ToolImage {

    private static ToolImage instance;

    private ImageSize targetSize;

    private ImageLoader mImageLoader;

    /**
     * 日志输出标识
     */
    protected final static String TAG = ToolImage.class.getClass().getSimpleName();

    private ToolImage() {
        mImageLoader = ImageLoader.getInstance();
    }

    public static ToolImage getInstance() {
        if (instance == null) {
            synchronized (ToolImage.class) {
                if (instance == null) {
                    instance = new ToolImage();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化ImageLoader
     *
     * @param mContext 上下文，建议用Application
     * @return
     */
    public static ImageLoader init(Context mContext) {
        // 获取到缓存的目录地址
        File cacheDir = StorageUtils.getOwnCacheDirectory(mContext, "ImageLoader/Cache");
        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(mContext.getApplicationContext())
                        // .memoryCacheExtraOptions(1280, 1920) // default = device screen dimensions
                        // .diskCacheExtraOptions(1280, 1920, null)
                        .threadPoolSize(3)
                        // default
                        .threadPriority(Thread.NORM_PRIORITY - 2)
                        // default
                        .tasksProcessingOrder(QueueProcessingType.FIFO)
                        // default ，不需要多个尺寸大小在内存中
                        // .denyCacheImageMultipleSizesInMemory()
                        /**
                         * UIL中的内存缓存策略 1. 只使用的是强引用缓存
                         * LruMemoryCache（这个类就是这个开源框架默认的内存缓存类，缓存的是bitmap的强引用，下面我会从源码上面分析这个类） 2.使用强引用和弱引用相结合的缓存有
                         * UsingFreqLimitedMemoryCache（如果缓存的图片总量超过限定值，先删除使用频率最小的bitmap）
                         * LRULimitedMemoryCache（这个也是使用的lru算法，和LruMemoryCache不同的是，他缓存的是bitmap的弱引用）
                         * FIFOLimitedMemoryCache（先进先出的缓存策略，当超过设定值，先删除最先加入缓存的bitmap）
                         * LargestLimitedMemoryCache(当超过缓存限定值，先删除最大的bitmap对象) LimitedAgeMemoryCache（当
                         * bitmap加入缓存中的时间超过我们设定的值，将其删除） 3.只使用弱引用缓存
                         * WeakMemoryCache（这个类缓存bitmap的总大小没有限制，唯一不足的地方就是不稳定，缓存的图片容易被回收掉）
                         */
                        .memoryCache(new UsingFreqLimitedMemoryCache(8 * 1024 * 1024))
                        // 限制内存大小
                        .memoryCacheSize(8 * 1024 * 1024)
                        .memoryCacheSizePercentage(10)
                        // default
                        .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                        .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
                        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                        .imageDownloader(new BaseImageDownloader(mContext)) // default
                        .imageDecoder(new BaseImageDecoder(true)) // default
                        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                        .build();

        // 全局初始化此配置
        ImageLoader.getInstance().init(config);

        return ImageLoader.getInstance();
    }

    /**
     * 清除缓存
     */
    public static void clearCache() {
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
    }

    /**
     * 显示图片
     *
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public void displayImage(String url, ImageView imageView) {
        mImageLoader.displayImage(url, imageView);
    }

    /**
     * 显示图片
     *
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public void displayImage(String url, ImageView imageView, DisplayImageOptions options) {
        mImageLoader.displayImage(url, imageView, options);
    }

    /**
     * 显示图片
     *
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public void displayImage(String url, ImageView imageView, ImageLoadingListener mLoadingListenner) {
        mImageLoader.displayImage(url, imageView, mLoadingListenner);
    }

    /**
     * 显示图片
     *
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public void displayImage(String url, ImageView imageView, DisplayImageOptions options, ImageLoadingListener mLoadingListenner) {
        mImageLoader.displayImage(url, imageView, options, mLoadingListenner);
    }

    /**
     * 显示图片
     *
     * @param mContext  上下文
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public void displayImage(Context mContext, String url, ImageView imageView) {
        displayImage(mContext, url, imageView, null, null);
    }

    /**
     * 显示图片
     *
     * @param mContext  上下文
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public void displayImage(Context mContext, String url, ImageView imageView, DisplayImageOptions options) {
        displayImage(mContext, url, imageView, options, null);
    }

    /**
     * 显示图片
     *
     * @param mContext  上下文
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public void displayImage(Context mContext, String url, ImageView imageView, ImageLoadingListener mLoadingListenner) {
        displayImage(mContext, url, imageView, null, mLoadingListenner);
    }

    /**
     * 显示图片，优先从内存中获取，解决闪烁问题
     *
     * @param mContext          上下文
     * @param url               图片地址
     * @param imageView         图片控件
     * @param options           图片显示option
     * @param mLoadingListenner 图片下载监听器
     */
    public void displayImage(Context mContext, String url, ImageView imageView,
                             DisplayImageOptions options, ImageLoadingListener mLoadingListenner) {
        try {
            if (null == targetSize) {
                DisplayMetrics display = mContext.getResources().getDisplayMetrics();
                targetSize = new ImageSize(display.widthPixels, display.heightPixels);
            }
            if (!TextUtils.isEmpty(url)) {
                String key = MemoryCacheUtils.generateKey(url, targetSize);
                Bitmap bitmap = mImageLoader.getMemoryCache().get(key);
                if (bitmap != null && !bitmap.isRecycled()) {
                    imageView.setImageBitmap(bitmap);
                    if (mLoadingListenner != null) {
                        mLoadingListenner.onLoadingComplete(url, imageView, bitmap);
                    }
                }else{
                    mImageLoader.displayImage(url, imageView, options, mLoadingListenner);
                }
            } else {
                mImageLoader.displayImage(url, imageView, options, mLoadingListenner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载drawable工程本地资源图片,可以防止OOM崩溃,option不要随意更改
     *
     * @param fileName
     * @param target
     */
    public void displayDrawable(int fileName, ImageView target) {
        String filePath = "drawable://" + fileName;
        displayLocalImage(filePath, target, ImageOptions.mDrawableOption);
    }

    public void displayDrawable(int fileName, ImageView target, ImageLoadingListener listener) {
        String filePath = "drawable://" + fileName;
        displayLocalImage(filePath, target, ImageOptions.mDrawableOption, listener);
    }

    /**
     * 加载Assets工程本地资源图片,可以防止OOM崩溃,option不要随意更改
     *
     * @param fileName
     * @param target
     */
    public void displayAssets(String fileName, ImageView target) {
        String filePath = "assets://" + fileName;
        displayLocalImage(filePath, target, ImageOptions.mDrawableOption);
    }

    /**
     * 加载指定路径资源图片,可以防止OOM崩溃,option不要随意更改
     *
     * @param fileName
     * @param target
     */
    public void displayFile(String fileName, ImageView target) {
        displayFile(fileName, target, ImageOptions.mDrawableOption);
    }

    /**
     * 加载指定路径资源图片,可以防止OOM崩溃,option不要随意更改
     *
     * @param fileName
     * @param target
     */
    public void displayFile(String fileName, ImageView target, DisplayImageOptions mOption) {
        String filePath = "file://" + fileName;
        displayLocalImage(filePath, target, mOption);
    }

    /**
     * 加载ContentPrivider资源图片,可以防止OOM崩溃,option不要随意更改
     *
     * @param fileName
     * @param target
     */
    public void displayContentPrivider(String fileName, ImageView target, DisplayImageOptions mOption) {
        String filePath = "content://" + fileName;
        displayLocalImage(filePath, target, mOption);
    }

    /**
     * 加载本地图片资源
     *
     * @param filePath
     * @param target
     * @param mOption
     */
    public void displayLocalImage(String filePath, ImageView target, DisplayImageOptions mOption) {
        ImageLoader.getInstance().displayImage(filePath, target, mOption);
    }

    public void displayLocalImage(String filePath, ImageView target, DisplayImageOptions options, ImageLoadingListener listener) {
        ImageLoader.getInstance().displayImage(filePath, target, options, listener);
    }

    /**
     * 获取渐现显示选项
     *
     * @param defaultImgResId 加载/出错/空时默认图
     * @return
     */
    public static DisplayImageOptions getFadeOptions(int defaultImgResId) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(defaultImgResId)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(defaultImgResId)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(true)
                /**设置图片缩放方式：
                 EXACTLY :图像将完全按比例缩小到目标大小
                 EXACTLY_STRETCHED:图片会缩放到目标大小完全
                 IN_SAMPLE_INT:图像将被二次采样的整数倍
                 IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
                 NONE:图片不会调整
                 ***/
                .imageScaleType(ImageScaleType.EXACTLY)
                // 设置图片的解码类型
                .bitmapConfig(Bitmap.Config.RGB_565)
                // 设置图片下载前的延迟
                .delayBeforeLoading(100)
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                /**
                 * 图片显示方式：
                 *  RoundedBitmapDisplayer（int roundPixels）设置圆角图片
                 *  FakeBitmapDisplayer（）这个类什么都没做
                 *  FadeInBitmapDisplayer（int durationMillis）设置图片渐显的时间
                 　　　　  	 *　   SimpleBitmapDisplayer()正常显示一张图片
                 **/
                .displayer(new FadeInBitmapDisplayer(300))// 渐显--设置图片渐显的时间
                .build();
        return options;
    }

    /**
     * 圆形加载图片
     *
     * @param defaultImage 默认图片
     * @return
     */
    public static DisplayImageOptions getRoundOptions(int defaultImage) {
        return
                new DisplayImageOptions.Builder()
                        .displayer(new RoundedBitmapDisplayer(320))
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .showImageOnFail(defaultImage)
                        .showImageForEmptyUri(defaultImage)
                        .imageScaleType(ImageScaleType.EXACTLY)
                        .cacheInMemory(true)
                        .cacheOnDisk(true).build();
    }

    /**
     * 获取默认显示配置选项
     */
    public static DisplayImageOptions getDefaultOptions() {
        return DisplayImageOptions.createSimple();
    }

    /**
     * 解决闪烁option
     */
    public static DisplayImageOptions gainSampleOption(int defaultResId) {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(defaultResId)
                .showImageOnFail(defaultResId)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .delayBeforeLoading(100)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer()).build();
    }


    /**
     * 圆形Option
     */
    public static DisplayImageOptions gainRoundOption(int defaultResId) {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(defaultResId)
                .showImageOnFail(defaultResId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .delayBeforeLoading(100)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(320)).build();
    }


    /**
     * EXACTLY类型option-RGB_565
     */
    public static DisplayImageOptions gainExactlyOption(int defaultResId) {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(defaultResId)
                .showImageOnFail(defaultResId)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true).build();
    }

    /**
     * EXACTLY类型option-ARGB_8888
     */
    public static DisplayImageOptions gainExactlyAlphaOption(int defaultResId) {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(defaultResId)
                .showImageOnFail(defaultResId)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true).build();
    }

    /**
     * 解决闪烁+压缩4倍RGB_565
     */
    public static DisplayImageOptions gainSample4ImageOption(int defaultResId) {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(defaultResId)
                .showImageOnFail(defaultResId)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer())
                .build();
    }

    /**
     * 解决闪烁+压缩4倍RGB_565-IN_SAMPLE_INT
     */
    public static DisplayImageOptions gainSample2ImageOption(int defaultResId) {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(defaultResId)
                .showImageOnFail(defaultResId)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer())
                .build();
    }

}
