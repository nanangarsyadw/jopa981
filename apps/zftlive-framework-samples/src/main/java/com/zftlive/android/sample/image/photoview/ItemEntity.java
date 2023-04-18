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

public class ItemEntity {
	private String avatar; // 用户头像URL
	private String title; // 标题
	private String content; // 内容
	private ArrayList<String> imageUrls; // 九宫格图片的URL集合

	public ItemEntity(String avatar, String title, String content, ArrayList<String> imageUrls) {
		super();
		this.avatar = avatar;
		this.title = title;
		this.content = content;
		this.imageUrls = imageUrls;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(ArrayList<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	@Override
	public String toString() {
		return "ItemEntity [avatar=" + avatar + ", title=" + title + ", content=" + content + ", imageUrls=" + imageUrls + "]";
	}

}
