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

import com.zftlive.android.library.base.bean.AdapterModelBean;

/**
 * Created by AJava on 2016/8/31.
 */
public class ImageFileBean extends AdapterModelBean {

  public String imageURL = "";

  public String id = "";

  public String displayName = "";

  public String album = "";

  public String artist = "";

  public String mimeType = "";

  public String path = "";

  public String resolution = "";

  public String addDate = "";

  /**
   * 原图uri
   */
  public String originUri;

  /**
   * 原图path
   */
  public String originPath;

  /**
   * 缩略图uri
   */
  public String thumbnailUri;

  /**
   * 是否视频
   */
  public boolean isVideo = false;

  /**
   * 标题
   */
  public String title = "";

  /**
   * 视频时长 ms
   */
  public long duration = 0;

  /**
   * 视频时长文案
   */
  public String durationText = "";

  /**
   * 视频大小 byte
   */
  public long size = 0;

  public ImageFileBean() {

  }

  public ImageFileBean(String title, String imageURL) {
    this.title = title;
    this.imageURL = imageURL;
  }
}
