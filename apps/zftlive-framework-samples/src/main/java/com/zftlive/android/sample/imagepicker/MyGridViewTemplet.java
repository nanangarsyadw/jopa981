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

package com.zftlive.android.sample.imagepicker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zftlive.android.R;
import com.zftlive.android.library.base.IBaseConstant;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.base.templet.AbsViewTemplet;
import com.zftlive.android.library.imageloader.ToolImage;
import com.zftlive.android.library.imageloader.picture.PictureBean;
import com.zftlive.android.library.imageloader.picture.PictureViewerActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * 视图模板
 */
public class MyGridViewTemplet extends AbsViewTemplet {

    DisplayImageOptions option;
    ImageView iv_icon;
    TextView tv_title,tv_adddate,tv_size,tv_video_duration;
    View rl_video_area;

    public MyGridViewTemplet(Context mContext) {
        super(mContext);
        option = ToolImage.getFadeOptions(R.drawable.anl_common_default_picture);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_image_grid_item;
    }

    @Override
    public void initView() {
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_adddate = (TextView) findViewById(R.id.tv_adddate);
        tv_size = (TextView) findViewById(R.id.tv_size);
        tv_video_duration = (TextView)findViewById(R.id.tv_video_duration);
        rl_video_area = findViewById(R.id.rl_video_area);
    }

    @Override
    public void fillData(IAdapterModel model, int postion) {

        ImageFileBean rowBean = (ImageFileBean) model;
        //ToolImage.getInstance().displayFile(rowBean.imageURL, iv_icon);
        Glide.with(mContext).load(rowBean.imageURL).crossFade().centerCrop().into(iv_icon);
        tv_title.setText(rowBean.displayName);
        tv_adddate.setText(rowBean.addDate);
        tv_size.setText(getNetFileSizeDescription(rowBean.size));
        //视频类型
        rl_video_area.setVisibility(rowBean.isVideo?View.VISIBLE:View.GONE);
        tv_video_duration.setText(formatSeconds(rowBean.duration / 1000));
    }

    @Override
    public void itemClick(View view, int postion, IAdapterModel rowData) {
        try{
            ImageFileBean rowBean = (ImageFileBean) rowData;
            //ToolToast.showShort(mContext,"点击了"+ rowBean.title);
            if(rowBean.isVideo){
                playVideo(mContext,rowBean.path);
            }else{
                imageBrower(mContext,rowBean.path);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    /**
     * 打开图片查看器
     *
     * @param mContext
     * @param path
     */
    protected void imageBrower(Context mContext,String path) {
//        //打开系统的查看器
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri uri = Uri.parse(path);
//        //加载图片
//        intent.setDataAndType(uri, "image/*");
//        mContext.startActivity(intent);

        Intent mIntent = new Intent(mContext, PictureViewerActivity.class);
        ArrayList<PictureBean> dataSource = new ArrayList<PictureBean>();
        PictureBean image = new PictureBean(PictureBean.FILE_TYPE_SDCARD, path, path);
        dataSource.add(image);
        mIntent.putExtra(IBaseConstant.PICTURE_VIEWER_DEFAULT_POSTION, position);
        mIntent.putExtra(IBaseConstant.PICTURE_VIEWER_DATASOURCE, dataSource);
        mContext.startActivity(mIntent);
    }

    /**
     * 打开视频
     *
     * @param mContext
     * @param path
     */
    private void playVideo(Context mContext,String path){
        //打开系统的播放器   ACTION_VIEW
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //得到视频
        //Environment.getExternalStorageDirectory() 得到Sdcard的根目录、
        Uri uri = Uri.parse(path);
        //加载视频  和 类型
        intent.setDataAndType(uri, "video/*");
        mContext.startActivity(intent);
    }

    /**
     * 格式化时长
     *
     * @param seconds
     * @return
     */
    public static String formatSeconds(long seconds){
        String standardTime;
        if (seconds <= 0){
            standardTime = "00:00";
        } else if (seconds < 60) {
            standardTime = String.format(Locale.getDefault(), "00:%02d", seconds % 60);
        } else if (seconds < 3600) {
            standardTime = String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
        } else {
            standardTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60);
        }
        return standardTime;
    }

    /**
     * 格式化byte
     *
     * @param size
     * @return
     */
    public static String getNetFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        }
        else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        }
        else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        }
        else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            }
            else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }

}
