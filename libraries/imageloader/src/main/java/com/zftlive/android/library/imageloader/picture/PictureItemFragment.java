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

package com.zftlive.android.library.imageloader.picture;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zftlive.android.library.imageloader.R;
import com.zftlive.android.library.imageloader.photoview.PhotoViewAttacher;

/**
 * 单张图片显示Fragment
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class PictureItemFragment extends Fragment {

  /** 当前Fragment渲染的视图View **/
  private View mContextView = null;
  private PictureBean picture;
  private ImageView mImageView;
  private ProgressBar mPprogressBar;
  private PhotoViewAttacher mAttacher;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    picture =
        getArguments() != null
            ? (PictureBean) getArguments().getSerializable("picture")
            : new PictureBean();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // 渲染视图View
    if (null == mContextView) {
      mContextView = inflater.inflate(R.layout.anl_common_picture_item, container, false);
      mPprogressBar = (ProgressBar) mContextView.findViewById(R.id.loading);
      mImageView = (ImageView) mContextView.findViewById(R.id.image);
      mAttacher = new PhotoViewAttacher(mImageView);
      mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

        @Override
        public void onPhotoTap(View arg0, float arg1, float arg2) {
          getActivity().finish();
        }
      });
    }
    return mContextView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    // 判断文件类型
    String filePath = "";
    if (PictureBean.FILE_TYPE_SDCARD == picture.getFileType()) {
      if (!picture.getFilePath().startsWith("file://")) {
        filePath = "file://" + picture.getFilePath();
      }
    } else if (PictureBean.FILE_TYPE_ASSETS == picture.getFileType()) {
      if (!picture.getFilePath().startsWith("assets://")) {
        filePath = "assets://" + picture.getFilePath();
      }
    } else if (PictureBean.FILE_TYPE_DRAWABLE == picture.getFileType()) {
      if (!picture.getFilePath().startsWith("drawable://")) {
        filePath = "drawable://" + picture.getFilePath();
      }
    } else if (PictureBean.FILE_TYPE_CONTENT == picture.getFileType()) {
      if (!picture.getFilePath().startsWith("content://")) {
        filePath = "content://" + picture.getFilePath();
      }
    } else if (PictureBean.FILE_TYPE_NETWORK == picture.getFileType()) {
      filePath = picture.getFilePath();
    } else {
      filePath = picture.getFilePath();
    }

    ImageLoader.getInstance().displayImage(filePath, mImageView, new SimpleImageLoadingListener() {
      @Override
      public void onLoadingStarted(String imageUri, View view) {
        mPprogressBar.setVisibility(View.VISIBLE);
      }

      @Override
      public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        String message = null;
        switch (failReason.getType()) {
          case IO_ERROR:
            message = "下载错误";
            break;
          case DECODING_ERROR:
            message = "图片无法显示";
            break;
          case NETWORK_DENIED:
            message = "网络有问题，无法下载";
            break;
          case OUT_OF_MEMORY:
            message = "图片太大无法显示";
            break;
          case UNKNOWN:
            message = "未知的错误";
            break;
        }
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        mPprogressBar.setVisibility(View.GONE);
      }

      @Override
      public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        mPprogressBar.setVisibility(View.GONE);
        mAttacher.update();
      }
    });
  }

  /**
   * 创建Fragment实例
   * 
   * @param picture 图片对象
   * @return
   */
  public static PictureItemFragment newInstance(PictureBean picture) {
    final PictureItemFragment item = new PictureItemFragment();
    final Bundle args = new Bundle();
    args.putSerializable("picture", picture);
    item.setArguments(args);
    return item;
  }
}
