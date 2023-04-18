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

package com.zftlive.android.widget.imageindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.zftlive.android.library.imageloader.ToolImage;
import com.zftlive.android.library.tools.ToolResource;

import java.util.List;

/**
 * Network ImageIndicatorView, by urls
 * 
 * @author steven-pan
 * 
 */
public class NetworkImageIndicatorView extends ImageIndicatorView {

	/**默认资源图片ID**/
	private int default_image = -1;
	
	public NetworkImageIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NetworkImageIndicatorView(Context context) {
		super(context);
	}

	/**
	 * 设置显示图片URL列表
	 * 
	 * @param urlList
	 *            URL列表
	 */
	public void setupLayoutByImageUrl(final List<String> urlList) {
		if (urlList == null)
			throw new NullPointerException();

		final int len = urlList.size();
		if (len > 0) {
			for (int index = 0; index < len; index++) {
				final ImageView pageItem = new ImageView(getContext());
				pageItem.setScaleType(ScaleType.CENTER_CROP);
				if(-1 != default_image){
					pageItem.setImageResource(default_image);
				}else{
					pageItem.setImageResource(ToolResource.getDrawableId("ic_launcher"));
				}
				ToolImage.init(getContext()).displayImage(urlList.get(index), pageItem);
				addViewItem(pageItem);
			}
		}
	}
	
	/**
	 * 设置默认图片
	 * @param resId 默认图片资源ID
	 */
	public void setDefaultImage(int resId){
		this.default_image = resId;
	}

}
