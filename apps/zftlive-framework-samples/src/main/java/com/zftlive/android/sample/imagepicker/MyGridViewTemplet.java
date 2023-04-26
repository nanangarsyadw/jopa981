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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zftlive.android.R;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.base.templet.AbsViewTemplet;
import com.zftlive.android.library.imageloader.ToolImage;

/**
 * 视图模板
 */
public class MyGridViewTemplet extends AbsViewTemplet {

    DisplayImageOptions option;
    ImageView iv_icon;
    TextView tv_title;

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
    }

    @Override
    public void fillData(IAdapterModel model, int postion) {

        ImageFileBean rowBean = (ImageFileBean) model;
        //ImageLoader.getInstance().displayImage((String) rowBean.imageURL, iv_icon,option);
        ToolImage.getInstance().displayFile(rowBean.imageURL, iv_icon);
        tv_title.setText(rowBean.title);
    }

    @Override
    public void itemClick(View view, int postion, IAdapterModel rowData) {
        try{
            ImageFileBean rowBean = (ImageFileBean) rowData;
            //ToolToast.showShort(mContext,"点击了"+ rowBean.title);

            //打开系统的播放器   ACTION_VIEW
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //得到视频
            //Environment.getExternalStorageDirectory() 得到Sdcard的根目录、
            Uri uri = Uri.parse(rowBean.path);
            //加载视频  和 类型
            intent.setDataAndType(uri, "video/*");
            mContext.startActivity(intent);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

}
