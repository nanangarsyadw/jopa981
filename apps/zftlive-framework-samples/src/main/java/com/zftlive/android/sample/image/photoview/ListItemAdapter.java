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

package com.zftlive.android.sample.image.photoview;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zftlive.android.R;
import com.zftlive.android.library.base.IBaseConstant;
import com.zftlive.android.library.imageloader.picture.PictureBean;
import com.zftlive.android.library.imageloader.picture.PictureViewerActivity;

/**
 * 首页ListView的数据适配器
 * 
 * @author Administrator
 * 
 */
public class ListItemAdapter extends BaseAdapter {

  private Context mContext;
  private ArrayList<ItemEntity> items;

  public ListItemAdapter(Context ctx, ArrayList<ItemEntity> items) {
    this.mContext = ctx;
    this.items = items;
  }

  @Override
  public int getCount() {
    return items == null ? 0 : items.size();
  }

  @Override
  public Object getItem(int position) {
    return items.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      holder = new ViewHolder();
      convertView = View.inflate(mContext, R.layout.activity_photoview_list_item, null);
      holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
      holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
      holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
      holder.gridview = (GridView) convertView.findViewById(R.id.gridview);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    ItemEntity itemEntity = items.get(position);
    holder.tv_title.setText(itemEntity.getTitle());
    holder.tv_content.setText(itemEntity.getContent());
    // 使用ImageLoader加载网络图片
    DisplayImageOptions options = new DisplayImageOptions.Builder()//
        .showImageOnLoading(R.drawable.ic_launcher) // 加载中显示的默认图片
        .showImageOnFail(R.drawable.ic_launcher) // 设置加载失败的默认图片
        .cacheInMemory(true) // 内存缓存
        .cacheOnDisk(true) // sdcard缓存
        .bitmapConfig(Config.RGB_565)// 设置最低配置
        .build();//
    ImageLoader.getInstance().displayImage(itemEntity.getAvatar(), holder.iv_avatar, options);
    final ArrayList<String> imageUrls = itemEntity.getImageUrls();
    if (imageUrls == null || imageUrls.size() == 0) { // 没有图片资源就隐藏GridView
      holder.gridview.setVisibility(View.GONE);
    } else {
      holder.gridview.setAdapter(new NoScrollGridAdapter(mContext, imageUrls));
    }
    // 点击九宫格，查看大图
    holder.gridview.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        imageBrower(position, imageUrls);
      }
    });
    return convertView;
  }

  /**
   * 打开图片查看器
   * 
   * @param position
   * @param urls
   */
  protected void imageBrower(int position, ArrayList<String> urls) {
    Intent mIntent = new Intent(mContext, PictureViewerActivity.class);
    ArrayList<PictureBean> dataSource = new ArrayList<PictureBean>();
    for (String imageURL : urls) {
      PictureBean image = new PictureBean(PictureBean.FILE_TYPE_NETWORK, "张三", imageURL);
      dataSource.add(image);
    }
    mIntent.putExtra(IBaseConstant.PICTURE_VIEWER_DEFAULT_POSTION, position);
    mIntent.putExtra(IBaseConstant.PICTURE_VIEWER_DATASOURCE, dataSource);
    mContext.startActivity(mIntent);
  }

  /**
   * listview组件复用，防止“卡顿”
   * 
   * @author Administrator
   * 
   */
  class ViewHolder {
    private ImageView iv_avatar;
    private TextView tv_title;
    private TextView tv_content;
    private GridView gridview;
  }
}
