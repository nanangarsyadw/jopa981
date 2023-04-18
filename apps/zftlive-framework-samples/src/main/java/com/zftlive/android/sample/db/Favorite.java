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

package com.zftlive.android.sample.db;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zftlive.android.library.db.DBPersistentBean;

/**
 * [我的收藏]实体
 * @author 曾繁添
 */
@DatabaseTable(tableName="Favorite")
public class Favorite extends DBPersistentBean {

	/**
	 * 主键ID
	 */
	@DatabaseField(id=true)
	private String id;
	
	/**
	 * 发布日期yyyy-MM-dd
	 */
	@DatabaseField
	private String publishDate;
	
	/**
	 * 频道类别
	 */
	@DatabaseField
	private String category;
	
	/**
	 * 文章标题
	 */
	@DatabaseField
	private String title;
	
	/**
	 * 链接URL
	 */
	@DatabaseField
	private String linkURL ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}
}
