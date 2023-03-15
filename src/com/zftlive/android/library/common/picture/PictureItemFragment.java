package com.zftlive.android.library.common.picture;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar; 
import android.widget.Toast;

import com.zftlive.android.R;
import com.zftlive.android.library.third.universalimageloader.core.ImageLoader;
import com.zftlive.android.library.third.universalimageloader.core.assist.FailReason;
import com.zftlive.android.library.third.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zftlive.android.library.widget.photoview.PhotoViewAttacher;
import com.zftlive.android.library.widget.photoview.PhotoViewAttacher.OnPhotoTapListener;

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
      mContextView = inflater.inflate(R.layout.common_picture_item, container, false);
      mPprogressBar = (ProgressBar) mContextView.findViewById(R.id.loading);
      mImageView = (ImageView) mContextView.findViewById(R.id.image);
      mAttacher = new PhotoViewAttacher(mImageView);
      mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {

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
