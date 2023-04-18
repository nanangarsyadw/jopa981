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

package com.zftlive.android.sample.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.tools.ToolDateTime;
import com.zftlive.android.library.tools.ToolFile;
import com.zftlive.android.library.tools.ToolPhone;
import com.zftlive.android.library.tools.ToolPicture;
import com.zftlive.android.library.widget.dialog.OperationDialog;
import com.zftlive.android.library.widget.dialog.OperationDialog.OpItemBean;

import java.io.File;

/**
 * 从相册中选择/拍照裁剪头像示例
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class CamreaPictureDemoActivity extends CommonActivity implements View.OnClickListener {

  /**
   * 拍照/相册选择按钮
   */
  private Button mAlbumBtn, mCameraBtn;

  /**
   * 图片
   */
  private ImageView mPhoto;

  /**
   * 临时拍照存储的照片
   */
  private String SOURCE_IMAGE_FILE = "";

  /**
   * 图片文件源URI
   */
  private Uri SOURCE_IMAGE_URI = null;

  /**
   * 临时拍照存储的照片
   */
  private String OUTPUT_IMAGE_FILE = "";

  /**
   * 裁剪后输出文件URI
   */
  private Uri OUTPUT_IMAGE_URI = null;

  @Override
  public int bindLayout() {
    return R.layout.activity_camrea_picture;
  }

  @Override
  public void initParams(Bundle parms) {

  }

  @Override
  public void initView(View view) {
    mAlbumBtn = (Button) findViewById(R.id.btn_album);
    mCameraBtn = (Button) findViewById(R.id.btn_camera);
    mAlbumBtn.setOnClickListener(this);
    mCameraBtn.setOnClickListener(this);

    mPhoto = (ImageView) findViewById(R.id.iv_photo);
    mPhoto.setOnClickListener(this);
  }

  @Override
  public void doBusiness(Context mContext) {
    // 初始化带返回按钮的标题栏
    String strCenterTitle = getResources().getString(R.string.CamreaPictureDemoActivity);
    mWindowTitle.initBackTitleBar(strCenterTitle);

    // 初始化拍照存储的照片路径
    initFilePath();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_album:
        ToolPhone.toImagePickerActivity(getContext(), ALBUM_REQUEST_CODE, 800, 800, SOURCE_IMAGE_URI,
            OUTPUT_IMAGE_URI);
        break;
      case R.id.btn_camera:
        ToolPhone.toCameraActivity(getContext(), CAMERA_REQUEST_CODE, SOURCE_IMAGE_URI);
        break;
      case R.id.iv_photo:

        // 点击头像弹出选择对话框
        openPhotoOptionDialog(getContext(), new OperationDialog.ItemClickListener() {

          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
            // 立即拍照
              case 0:
                ToolPhone.toCameraActivity(getContext(), CAMERA_REQUEST_CODE, SOURCE_IMAGE_URI);
                break;
              // 相册选择
              case 1:
                ToolPhone.toImagePickerActivity(getContext(), ALBUM_REQUEST_CODE, 800, 800, SOURCE_IMAGE_URI,
                    OUTPUT_IMAGE_URI);
                break;
              default:
                break;
            }
          }
        });
        break;
      default:
        break;
    }
  }

  /**
   * 选择返回界面
   */
  protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (Activity.RESULT_OK != resultCode) return;
    switch (requestCode) {
    // 立即拍照
      case CAMERA_REQUEST_CODE:
        ToolPhone.toCropImageActivity(getContext(), SOURCE_IMAGE_URI, OUTPUT_IMAGE_URI, 800, 800,
            CROPER_REQUEST_CODE);

        break;
      // 相册选择
      case ALBUM_REQUEST_CODE:
        if (OUTPUT_IMAGE_URI != null) {
          Bitmap bitmap = ToolPicture.decodeUriAsBitmap(getContext(), OUTPUT_IMAGE_URI);
          mPhoto.setImageBitmap(bitmap);
        }
        break;
      // 裁剪照片
      case CROPER_REQUEST_CODE:

        if (OUTPUT_IMAGE_URI != null) {
          Bitmap bitmap = ToolPicture.decodeUriAsBitmap(getContext(), OUTPUT_IMAGE_URI);
          mPhoto.setImageBitmap(bitmap);
        }
        break;

      default:
        break;
    }
  }

  /**
   * 打开相册/拍照选择对话框
   * 
   * @param mContext 上下文
   * @param mItemClickListener item点击选择监听器
   */
  private void openPhotoOptionDialog(Activity mContext,
      OperationDialog.ItemClickListener mItemClickListener) {
    new OperationDialog.DialogBuilder(mContext).setCanceledOnTouchOutside(false)
        .setFillScreenWith(false).setGravity(Gravity.CENTER).showButtomFooter(false)
        .showTitleClose(false).showTopHeader(false)
        .addItemData(new OpItemBean("立即拍照", "#000000", false))
        .addItemData(new OpItemBean("相册选择", "#000000", false))
        .addItemData(new OpItemBean("取消", "#359df5", false)).setItemClickListener(mItemClickListener)
        .build().show();
  }

  /**
   * 初始化文件
   */
  private void initFilePath() {
    SOURCE_IMAGE_FILE = ToolFile.gainSDCardPath() + "/CamreaImages/temp.jpg";
    OUTPUT_IMAGE_FILE =
        ToolFile.gainSDCardPath() + "/CamreaImages/Output/"
            + ToolDateTime.gainCurrentDate("yyyyMMddhhmmss") + ".jpg";

    File srcFile = new File(SOURCE_IMAGE_FILE);
    if (!srcFile.exists()) {
      srcFile.getParentFile().mkdirs();
    }
    File outFile = new File(OUTPUT_IMAGE_FILE);
    if (!outFile.exists()) {
      outFile.getParentFile().mkdirs();
    }

    SOURCE_IMAGE_URI = Uri.parse("file://" + SOURCE_IMAGE_FILE);
    OUTPUT_IMAGE_URI = Uri.parse("file://" + OUTPUT_IMAGE_FILE);
  }
}
